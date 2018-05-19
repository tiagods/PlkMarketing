package br.com.tiagods.controller;

import static br.com.tiagods.view.InicioView.btnOk;
import static br.com.tiagods.view.InicioView.btnOkResumo;
import static br.com.tiagods.view.InicioView.cbAtendentes;
import static br.com.tiagods.view.InicioView.dataResumo1;
import static br.com.tiagods.view.InicioView.dataResumo2;
import static br.com.tiagods.view.InicioView.jData1;
import static br.com.tiagods.view.InicioView.jData2;
import static br.com.tiagods.view.InicioView.lbInfoTarefas;
import static br.com.tiagods.view.InicioView.tbAcessos;
import static br.com.tiagods.view.InicioView.tbNegocios;
import static br.com.tiagods.view.InicioView.tbTarefas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.config.VersaoSistema;
import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.UsuarioAcesso;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.modeldao.VerificarAtualizacao;
import br.com.tiagods.view.InicioView;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.TarefasView;

public class ControllerInicio implements ActionListener,MouseListener{
		
	Session session = null;
	HashMap<String, Usuario> atendentes = new HashMap<>();
	boolean liberar = false;
	
	VersaoSistema descricao = new VersaoSistema();
	VerificarAtualizacao atualizacao = new VerificarAtualizacao();
	GenericDao dao = new GenericDao();
	boolean atualizar = false;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if("Filtrar".equals(arg0.getActionCommand()) && validarDatas(jData1, jData2)){
			abrirTarefasView(jData1.getDate(),jData2.getDate(), atendentes.get(cbAtendentes.getSelectedItem()));
		}
		else if("FiltrarResumo".equals(arg0.getActionCommand()) && validarDatas(dataResumo1, dataResumo2)){
			session = HibernateFactory.getSession();
			session.beginTransaction();
			preencherTabelaNegocios(session);
			preencherTabelaTarefas(session);
			session.close();
		}
	}
	public void iniciar(){
		session = HibernateFactory.getSession();
		session.beginTransaction();
		long inicio = System.currentTimeMillis();
		carregarDataAgora();
		carregarAtendentes();
		carregarTarefasHoje(UsuarioLogado.getInstance().getUsuario());
		preencherUltimosAcessos(session, UsuarioLogado.getInstance().getUsuario());
		preencherTabelaNegocios(session);
		preencherTabelaTarefas(session);
		long fim = System.currentTimeMillis();
		
		System.out.println("Tempo de execucao: "+(fim-inicio));
		session.close();
		LoadingView in = LoadingView.getInstance();
		in.fechar();
		try{
			setarIcons();
		}catch (NullPointerException e) {
		}
	}
	//carregar tarefas pendentes
	private void carregarTarefasHoje(Usuario usuario) {
		//verificar permiss�o e carregar tarefas do's usuarios
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.HOUR_OF_DAY, 23);
		calendar2.set(Calendar.MINUTE, 59);
		calendar2.set(Calendar.SECOND, 59);
		
		List<Criterion> criterios = new ArrayList<>();
		criterios.add(Restrictions.eq("atendente", usuario));
		criterios.add(Restrictions.eq("finalizado", 0));
		criterios.add(Restrictions.between("dataEvento", calendar1.getTime(), calendar2.getTime()));
		
		int quant = new GenericDao().uniqueResult(Tarefa.class, session, criterios);
		
		String[] nome = usuario.getNome().split(" ");
		LocalDateTime time = LocalDateTime.now();
		String hey;
		if(time.getHour()>=0 &&time.getHour()<12){
			hey="Bom dia";
		}
		else if(time.getHour()>=12 && time.getHour()<18){
			hey="Boa tarde";
		}
		else
			hey = "Boa noite";
		switch(quant){
		case 0:
			String v1 = hey+" "+nome[0]+", você não tem tarefas pendentes para hoje!";
			lbInfoTarefas.setText(v1);
			break;
		case 1:
			String v2 = hey+" "+nome[0]+",  você tem 1 tarefa pendente para hoje! Clique aqui...";
			lbInfoTarefas.setText(v2);
			break;
		default:
			String v3 = hey+" "+nome[0]+", você tem "+quant+" tarefas pendentes para hoje! Clique aqui...";
			lbInfoTarefas.setText(v3);
			break;
		}
	}
	//carregar lista de atendentes
	@SuppressWarnings("unchecked")
	private void carregarAtendentes() {
		List<Usuario> lista = new UsuarioDao().listar(Usuario.class, session);
		cbAtendentes.removeAllItems();
		lista.forEach(c->{
			cbAtendentes.addItem(c.getNome());
			atendentes.put(c.getNome(), c);
		});
		cbAtendentes.setSelectedItem(UsuarioLogado.getInstance().getUsuario().getNome());
	}
	//enviar data atual
	private void carregarDataAgora() {
		jData1.setDate(new Date());
		jData2.setDate(new Date());
		
		Calendar data1 = Calendar.getInstance();
		Calendar data2 = Calendar.getInstance();
		data1.set(data1.get(Calendar.YEAR), data1.get(Calendar.MONTH), 1, 0, 0, 0);
		data2.set(data1.get(Calendar.YEAR), data1.get(Calendar.MONTH), data1.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		
		dataResumo1.setDate(data1.getTime());
		dataResumo2.setDate(data2.getTime());
	}
	//validar datas
	private boolean validarDatas(JDateChooser data1, JDateChooser data2){
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try{
			calendar.setTime(data1.getDate());
			calendar2.setTime(data2.getDate());
			if(calendar.after(calendar2)){
				JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, 
						"O intervalo entre as datas está incorreto\n"
						+ "A data 1 deve ser igual ou menor que a data 2!",
						"Intervalo entre datas incorreto!", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else
				return true;
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, 
					"Data incorreta\nPor favor verifique a(s) data(s) informada(s)!",
					"Entrada incorreta!", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	//abrir nova tela
	@SuppressWarnings("static-access")
	private void abrirTarefasView(Date data1, Date data2, Usuario usuario){
		 ControllerMenu menu = ControllerMenu.getInstance();
		 TarefasView tView = new TarefasView(data1, data2, usuario);
		 menu.abrirCorpo(tView);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getComponent().getName()){
		case "OK":	
		if(validarDatas(jData1,jData2))
				abrirTarefasView(jData1.getDate(),jData2.getDate(), atendentes.get(cbAtendentes.getSelectedItem()));
		break;
		default:
				abrirTarefasView(jData1.getDate(),jData2.getDate(), atendentes.get(cbAtendentes.getSelectedItem()));	
			break;
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	private void preencherTabelaNegocios(Session session){
		Usuario usuario = UsuarioLogado.getInstance().getUsuario();
		
		Object [] tableHeader = {"STATUS",usuario.getNome().toUpperCase(),"TODOS"};
		DefaultTableModel model = new DefaultTableModel(tableHeader,0){
			boolean[] canEdit = new boolean[]{
					false,false,false
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
		List<Criterion> criterios = new ArrayList<>();
		criterios.add(Restrictions.between("dataInicio", dataResumo1.getDate(), dataResumo2.getDate()));
		List<NegocioProposta> listarNegocios = dao.items(NegocioProposta.class, session, criterios, Order.asc("id"));
		
		int meuAndamento=0;
		int meuGanho=0;
		int meuPerdido=0;
		int todosAndamentos=0;
		int todosGanhos=0;
		int todosPerdidos=0;
		
		for(int h = 0; h<listarNegocios.size(); h++){
			NegocioProposta n = listarNegocios.get(h);
			if("Em Andamento".equals(n.getStatus().getNome())){
				todosAndamentos++;
				if(n.getAtendente().equals(usuario))
					meuAndamento++;
			}
			else if("Ganho".equals(n.getStatus().getNome())){
				todosGanhos++;
				if(n.getAtendente().equals(usuario))
					meuGanho++;
			}
			else if("Perdido".equals(n.getStatus().getNome())){
				todosPerdidos++;
				if(n.getAtendente().equals(usuario))
					meuPerdido++;
			}
		}
		
		for(int i = 0; i<5;i++){
			Object[] o = new Object[3];
			if(i==0){
				o[0] = "Em Andamento"; 
				o[1] = meuAndamento;
				o[2] = todosAndamentos;
			}
			else if(i==1){
				o[0] = "Ganho";
				o[1] = meuGanho;
				o[2] = todosGanhos;
			}
			else if(i==2){
				o[0] = "Perdido";
				o[1] = meuPerdido;
				o[2] = todosPerdidos;
			}
			else if(i==4){
				o[0] = "Total";
				o[1] = meuAndamento+meuGanho+meuPerdido;
				o[2] = listarNegocios.size();
				
			}
			model.addRow(o);
		}
		tbNegocios.setModel(model);
	}
	@SuppressWarnings({ "serial", "unchecked" })
	private void preencherTabelaTarefas(Session session){
		Usuario usuario = UsuarioLogado.getInstance().getUsuario();
		Object [] tableHeader = {"TIPO",usuario.getNome().toUpperCase(),"TODOS"};
		DefaultTableModel model = new DefaultTableModel(tableHeader,0){
			boolean[] canEdit = new boolean[]{
					false,false,false
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
		
		List<Criterion> criterios = new ArrayList<>();
		
		Calendar data1 = dataResumo1.getCalendar();
		data1.set(Calendar.HOUR, 0);
		data1.set(Calendar.MINUTE,0);
		data1.set(Calendar.SECOND,0);
		data1.set(Calendar.MILLISECOND, 0);
		Calendar data2 = dataResumo2.getCalendar();
		data2.set(Calendar.HOUR, 23);
		data2.set(Calendar.MINUTE,59);
		data2.set(Calendar.SECOND,59);
		data2.set(Calendar.MILLISECOND, 999);
		criterios.add(Restrictions.between("dataEvento", data1.getTime(), data2.getTime()));
		
		List<Tarefa> listarTarefas = dao.items(Tarefa.class, session, criterios, Order.asc("id"));
		
		int meuEmail=0,todosEmails=0,meuProposta=0,todosPropostas=0,meuReuniao=0,todosReuniao=0,
				meuTelefone=0,todosTelefone=0,meuVisita=0,todosVisita=0,meuPendentes=0,todosPendentes=0,
				meuConcluido=0,todosConcluidos=0;
		
		for(int h = 0; h<listarTarefas.size(); h++){
			Tarefa tarefa = listarTarefas.get(h);
			if(tarefa.getFinalizado()==0){
				todosPendentes++;
				if(tarefa.getAtendente().equals(usuario))
					meuPendentes++;
			}
			else{
				todosConcluidos++;
				if(tarefa.getAtendente().equals(usuario))
					meuConcluido++;
			}
			if("Email".equals(tarefa.getTipoTarefa().getNome())){
				todosEmails++;
				if(tarefa.getAtendente().equals(usuario))
					meuEmail++;
			}
			else if("Proposta".equals(tarefa.getTipoTarefa().getNome())){
				todosPropostas++;
				if(tarefa.getAtendente().equals(usuario))
					meuProposta++;
			}
			else if("Reuniao".equals(tarefa.getTipoTarefa().getNome())){
				todosReuniao++;
				if(tarefa.getAtendente().equals(usuario))
					meuReuniao++;
			}
			else if("Telefone".equals(tarefa.getTipoTarefa().getNome())){
				todosTelefone++;
				if(tarefa.getAtendente().equals(usuario))
					meuTelefone++;
			}
			else if("Visita".equals(tarefa.getTipoTarefa().getNome())){
				todosVisita++;
				if(tarefa.getAtendente().equals(usuario))
					meuVisita++;
			}
		}
		for(int i = 0; i<10;i++){
			Object[] o = new Object[3];
			if(i==0){
				o[0] = "Email"; 
				o[1] = meuEmail;
				o[2] = todosEmails;
			}
			else if(i==1){
				o[0] = "Proposta";
				o[1] = meuProposta;
				o[2] = todosPropostas;
			}
			else if(i==2){
				o[0] = "Reuniao";
				o[1] = meuReuniao;
				o[2] = todosReuniao;
			}
			else if(i==3){
				o[0] = "Telefone";
				o[1] = meuTelefone;
				o[2] = todosTelefone;
			}
			else if(i==4){
				o[0] = "Visita";
				o[1] = meuVisita;
				o[2] = todosVisita;
			}
			
			else if(i==6){
				o[0] = "T.Pendentes";
				o[1] = meuPendentes;
				o[2] = todosPendentes;
			}
			else if(i==7){
				o[0] = "T.Concluidas";
				o[1] = meuConcluido;
				o[2] = todosConcluidos;
			}
			
			else if(i==9){
				o[0] = "Total";
				o[1] = meuEmail+meuProposta+meuReuniao+meuTelefone+meuVisita;
				o[2] = listarTarefas.size();
				
			}
			model.addRow(o);
		}
		tbTarefas.setModel(model);
	}
	@SuppressWarnings({ "serial", "unchecked" })
	private void preencherUltimosAcessos(Session session, Usuario usuario) {
		Object [] tableHeader = {"ULTIMOS ACESSOS"};
		DefaultTableModel model = new DefaultTableModel(tableHeader,0){
			boolean[] canEdit = new boolean[]{
					false
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
		List<Criterion> criterios = new ArrayList<>();
		criterios.add(Restrictions.eq("usuario", usuario));
		List<UsuarioAcesso> lista = dao.items(UsuarioAcesso.class, session, criterios, Order.desc("data"));
		for(int i = 0; i<lista.size(); i++) {
			if(i==10) break;
			model.addRow(new Object[] {
					new SimpleDateFormat("dd/MM/yyy HH:mm:ss").format(lista.get(i).getData())		
			});
		}
		tbAcessos.setModel(model);		
	}
	private void setarIcons() throws NullPointerException {
		ImageIcon iconOk = new ImageIcon(InicioView.class.getResource("/br/com/tiagods/utilitarios/button_ok.png"));
        btnOk.setIcon(recalculate(iconOk));
        btnOkResumo.setIcon(iconOk);
	}
	private ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
}
