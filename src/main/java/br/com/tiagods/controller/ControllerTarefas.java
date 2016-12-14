/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.TarefasView.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelDAO.EmpresaDAO;
import br.com.tiagods.modelDAO.PessoaDAO;
import br.com.tiagods.modelDAO.TarefaDAO;
import br.com.tiagods.modelDAO.TipoTarefaDAO;
import br.com.tiagods.modelDAO.UsuarioDAO;
import br.com.tiagods.view.EmpresasView;
import br.com.tiagods.view.NegociosView;
import br.com.tiagods.view.PessoasView;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.interfaces.ButtonColumn;
import br.com.tiagods.view.interfaces.ButtonColumnModel;
/*
 * @author Tiago
 */
public class ControllerTarefas implements ActionListener, MouseListener,PropertyChangeListener,ItemListener {
	Calendar data1 = Calendar.getInstance();
	Calendar data2 = Calendar.getInstance();
	Usuario userSessao;
	Session session;
	int finalizado=0;

	Set<TipoTarefa> tipoTarefas =  new HashSet<>();
	Map<String,Usuario> atendentes = new HashMap<>();
	Map<String,TipoTarefa> tipoTarefasMapa = new HashMap<>();
	
	String pendente = "Aberto";
	String fechado = "Finalizado";
	
	public void iniciar(Date data1, Date data2, Usuario usuario){
		this.userSessao=usuario;
		long inicio = System.currentTimeMillis();
		ativarBotao(ckVisita);
		ativarBotao(ckReuniao);
		ativarBotao(ckProposta);
		ativarBotao(ckTelefone);
		ativarBotao(ckEmail);
		ckPendentes.setSelected(true);
		try{
			jData1.setDate(data1);
			jData2.setDate(data2);
		}catch(Exception e){
		}
		boolean hoje = verificarSeHoje(data1,data2);
		if(hoje){
			rbHoje.setSelected(true);
			mostrarDatas(pnData, false);
		}
		else{
			rbDefinirData.setSelected(true);
			mostrarDatas(pnData, true);
		}
		session = HibernateFactory.getSession();
		carregarAtendentes();
		cbAtendentes.setSelectedItem(usuario.getLogin());
		carregarTipoTarefas();
		List<Criterion> lista = new ArrayList();
		Criterion criterio =  Restrictions.eq("atendente", usuario);
		Criterion criterio2 = Restrictions.between("dataEvento", data1, data2);
		Criterion criterio3 = Restrictions.eq("finalizado", 0);
		lista.add(criterio);
		lista.add(criterio2);
		lista.add(criterio3);
		List<Tarefa> listaTarefas = new TarefaDAO().filtrar(lista, session);
		preencherTabela(tbPrincipal, listaTarefas, new JTextField());
		long fim = System.currentTimeMillis();
		session.close();
		System.out.println("Tarefas : "+(fim-inicio)+" ms");
		definirAcoes();
	}
	private void definirAcoes(){
		jData1.addPropertyChangeListener(this);
		jData2.addPropertyChangeListener(this);
		cbAtendentes.addItemListener(this);
	}
	public void ativarBotao(JCheckBox radio){
		radio.setSelected(true);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(validarDatas())
			buscar();
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED){
			buscar();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() instanceof JTable && tbPrincipal.getSelectedRow()>=0 && tbPrincipal.getSelectedColumn()==6){
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, 
					tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), tbPrincipal.getSelectedColumn()), 
					"Detalhes", 
					JOptionPane.DEFAULT_OPTION);
		}
		else if(e.getComponent() instanceof JCheckBox){
			JCheckBox ck = (JCheckBox)e.getComponent();
			if(ck.isSelected()){
				tipoTarefas.add(tipoTarefasMapa.get(ck.getName()));
				buscar();
			}
			else{
				tipoTarefas.remove(tipoTarefasMapa.get(ck.getName()));
				buscar();
			}
		}

	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "CriarTarefa":
			TarefasSaveView view = new TarefasSaveView(null);
			ControllerMenu.getInstance().abrirCorpo(view);
			break;
		case "Status":
			buscar();
			break;
		case "Prazo":
			boolean value =false;
			if(rbDefinirData.isSelected()){
				jData1.setDate(new Date());
				jData2.setDate(new Date());
				value = true;
			}
			mostrarDatas(pnData,value);
			buscar();
			break;
		default:
			break;
		}
		
	}
	@SuppressWarnings("unchecked")
	private void carregarAtendentes(){
		List<Usuario> lista = new UsuarioDAO().listar(Usuario.class,session);
		cbAtendentes.removeAllItems();
		cbAtendentes.addItem("Todos");
		Set<String> arvore = new TreeSet<>();
		Iterator<Usuario> iterator = lista.listIterator();
		while(iterator.hasNext()){
			Usuario u = iterator.next();
			arvore.add(u.getLogin());
			atendentes.put(u.getLogin(), u);
		}
		arvore.forEach(c->{cbAtendentes.addItem(c);});
	}
	private void carregarTipoTarefas(){
		List<TipoTarefa> lista = new TipoTarefaDAO().listar(TipoTarefa.class, session);
		lista.forEach(c->{
			tipoTarefas.add(c);
			tipoTarefasMapa.put(c.getNome(), c);
		});
	}
	public boolean buscar(){
		List<Criterion> criterios = new ArrayList<>();
		if(!tipoTarefas.isEmpty()){
			Criterion criterion = Restrictions.in("tipoTarefa", tipoTarefas.toArray());
			criterios.add(criterion);
		}
		else{
			Criterion criterion = Restrictions.isNull("tipoTarefa");
			criterios.add(criterion);
		}
		if(rbHoje.isSelected()){
			Criterion criterion = Restrictions.between("dataEvento", new Date(), new Date());
			criterios.add(criterion);
		}
		else if(rbEssaSemana.isSelected()){
			receberDatasSemana();
			Criterion criterion = Restrictions.between("dataEvento", data1.getTime(),data2.getTime());
			criterios.add(criterion);
		}
		else if(rbDefinirData.isSelected() && validarDatas()){
				Criterion criterion = Restrictions.between("dataEvento", jData1.getDate(), jData2.getDate());
				criterios.add(criterion);
		}
		else if(!validarDatas()){
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody,
					"Data informada esta incorreta, \nVerifique o valor informado e tente novamente",
					"Erro na data!",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(ckPendentes.isSelected() && !ckFinalizados.isSelected()){
			Criterion criterion = Restrictions.eq("finalizado", 0);
			criterios.add(criterion);
		}
		else if(!ckPendentes.isSelected() && ckFinalizados.isSelected()){
			Criterion criterion = Restrictions.eq("finalizado", 1);
			criterios.add(criterion);
		}
		else
			ckPendentes.setSelected(true);
		
		if(!"Todos".equalsIgnoreCase((String)cbAtendentes.getSelectedItem())){
			Criterion criterion = Restrictions.eq("atendente", atendentes.get(cbAtendentes.getSelectedItem()));
			criterios.add(criterion);
		}
		session = HibernateFactory.getSession();
		session.beginTransaction();
		TarefaDAO dao = new TarefaDAO();
		List<Tarefa> lista = dao.filtrar(criterios, session);
		System.out.println("Tamanho: "+lista.size());
		preencherTabela(tbPrincipal, lista, new JTextField());
		tbPrincipal.addMouseListener(this);
		session.close();
		return true;
	}
	
	private void receberDatasSemana(){
		LocalDate dataHoje = LocalDate.now();
		DayOfWeek day = dataHoje.getDayOfWeek();
		switch(day.getValue()){
		case 1:
			processarSemana(1,7);
			break;
		case 2:
			processarSemana(0,6);
			break;
		case 3:
			processarSemana(-1,5);
			break;
		case 4:
			processarSemana(-2,4);
			break;
		case 5:
			processarSemana(-3,3);
			break;
		case 6:
			processarSemana(-4,2);
			break;
		case 7:
			processarSemana(-5,1);
			break;
		default:
			break;
		}
	}
	private void processarSemana(int diaSegunda, int diaDomingo){
		LocalDate dataHoje = LocalDate.now();
		LocalDate novaDataHoje = dataHoje.plusDays(diaSegunda);
		data1.set(novaDataHoje.getYear(), novaDataHoje.getMonthValue()-1, novaDataHoje.getDayOfMonth()-1);
		LocalDate novaDataFimDeSemana = dataHoje.plusDays(diaDomingo);
		data2.set(novaDataFimDeSemana.getYear(), novaDataFimDeSemana.getMonthValue()-1, novaDataFimDeSemana.getDayOfMonth()-1);
	}

	public void preencherTabela(JTable table, List<Tarefa> lista, JTextField txContador){
		Object[] tableHeader = {"ID","PRAZO","ANDAMENTO","TIPO","NOME","STATUS",
				"DETALHES","ATENDENTE", "FINALIZADO","ABRIR","EDITAR","EXCLUIR"};
		DefaultTableModel model = new DefaultTableModel(tableHeader,0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false,false,false,false,false,true,true,true,true
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
			@Override
			public Class getColumnClass(int columnIndex) {
				return getValueAt(0, columnIndex).getClass();
			}
		};
		if(!lista.isEmpty()){
			for(int i=0;i<lista.size();i++){
				Tarefa t = lista.get(i);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdH = new SimpleDateFormat("HH:mm");

				Object[] o = new Object[12];
				o[0] = t.getId();
				String hora = sdf.format(t.getDataEvento())+" "+sdH.format(t.getHoraEvento());
				
				o[1] = hora;
				o[2] = t.getTipoTarefa().getNome();
				o[3] = t.getClasse();
				if(Empresa.class.getSimpleName().equals(t.getClasse()))
					o[4] = t.getEmpresa().getNome();
				else if(Negocio.class.getSimpleName().equals(t.getClasse()))
					o[4] = t.getNegocio().getNome();
				else if(Pessoa.class.getSimpleName().equals(t.getClasse()))
					o[4] = t.getPessoa().getNome();
				else
					o[4] = "Erro";
				if(t.getFinalizado()==0)
					o[5] = pendente;
				else
					o[5] = fechado;
				o[6] = t.getDescricao();
				o[7] = t.getAtendente().getLogin();
				if(t.getFinalizado()==0)
					o[8] =Boolean.FALSE;
				else
					o[8]=Boolean.TRUE;
				o[9]= t.getClasse();
				o[10] ="Editar";
				o[11] ="Excluir";
				model.addRow(o);
			}
		}
		txContador.setText("Total: "+lista.size()+" registros");
		table.setModel(model);

		JCheckBox ckFinalize = new JCheckBox();
		TableColumn col = table.getColumnModel().getColumn(8);
		col.setCellEditor(new DefaultCellEditor(ckFinalize));
		ckFinalize.setActionCommand("Finalizar");
		ckFinalize.addActionListener(new AcaoInTable());

		JButton btAbrir = new ButtonColumnModel(table,9).getButton();
		btAbrir.setActionCommand("Abrir");
		btAbrir.addActionListener(new AcaoInTable());

		JButton btEdit = new ButtonColumnModel(table,10).getButton();
		btEdit.setActionCommand("Editar");
		btEdit.addActionListener(new AcaoInTable());

		JButton btExcluir  =new ButtonColumnModel(table,11).getButton();
		btExcluir.setActionCommand("Excluir");
		btExcluir.addActionListener(new AcaoInTable());
	}
	public class AcaoInTable implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			session = HibernateFactory.getSession();
			session.beginTransaction();
			switch(e.getActionCommand()){
			case "Finalizar":
				finalizar(session);
				//System.out.println( e.getActionCommand() + " : " + tbPrincipal.getSelectedRow());
				break;
			case "Abrir":
				abrirCadastro(session);
				System.out.println( e.getActionCommand() + " : " + tbPrincipal.getSelectedRow());
				break;
			case "Editar":
				int valor = (int) tbPrincipal.getModel().getValueAt(tbPrincipal.getSelectedRow(), 0);
				Tarefa tarefa = (Tarefa)new TarefaDAO().receberObjeto(Tarefa.class, valor, session);
				TarefasSaveView viewTarefas = new TarefasSaveView(tarefa);
				ControllerMenu.getInstance().abrirCorpo(viewTarefas);
				System.out.println( e.getActionCommand() + " : " + tbPrincipal.getSelectedRow());
				break;
			case "Excluir":
				excluir(session);
				System.out.println( e.getActionCommand() + " : " + tbPrincipal.getSelectedRow());
				break;
			default:
				break;
			}
			session.close();
		}

	}
	public void finalizar(Session session){
		boolean value = (boolean)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 8);
		int id = (int)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0);
		String status = (String)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5);

		if(!value && pendente.equals(status)){
			TarefaDAO dao = new TarefaDAO();
			Tarefa thisTar = (Tarefa) dao.receberObjeto(Tarefa.class, id, session);
			thisTar.setFinalizado(1);
			if(dao.salvar(thisTar, session)){
				tbPrincipal.setValueAt(fechado, tbPrincipal.getSelectedRow(), 5);
			}

		}
		else if(value && fechado.equals(status)){
			TarefaDAO dao = new TarefaDAO();
			Tarefa thisTar = (Tarefa) dao.receberObjeto(Tarefa.class, id, session);
			thisTar.setFinalizado(0);
			if(dao.salvar(thisTar, session)){
				tbPrincipal.setValueAt(pendente, tbPrincipal.getSelectedRow(), 5);
			}
		}
		
		try{
			Thread.sleep(2000);
			buscar();
		}catch (InterruptedException e) {
		}
	}
	public void excluir(Session session){
		int row = tbPrincipal.getSelectedRow();
		int i = JOptionPane.showConfirmDialog(br.com.tiagods.view.MenuView.jDBody, 
				"Deseja excluir a seguinte tarefa: "+tbPrincipal.getValueAt(row, 0)+" andamento: "+tbPrincipal.getValueAt(row, 2)+
				" relacionado a :"+tbPrincipal.getValueAt(row, 3)+" de nome:"+tbPrincipal.getValueAt(row, 4)+
				" com status :"+tbPrincipal.getValueAt(row, 5)+"?","Pedido de remoção!",JOptionPane.YES_NO_OPTION);
		if(i==JOptionPane.OK_OPTION){
			TarefaDAO dao = new TarefaDAO();
			int id = (int)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0);
			Tarefa tRemove = (Tarefa)dao.receberObjeto(Tarefa.class, id, session);
			if(dao.excluir(tRemove, session))
				buscar();
		}
	}
	public void abrirCadastro(Session session){
		String value = (String)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3);
		int id = (int)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0);
		Tarefa transfer = (Tarefa) new TarefaDAO().receberObjeto(Tarefa.class, id, session);
		
		if("Empresa".equals(value)){
			Empresa empresa = transfer.getEmpresa();
			EmpresasView viewEmpresas = new EmpresasView(empresa);
			ControllerMenu.getInstance().abrirCorpo(viewEmpresas);
		}
		else if("Negocio".equals(value)){
			Negocio negocio = transfer.getNegocio();
			NegociosView viewNegocios = new NegociosView(negocio);
			ControllerMenu.getInstance().abrirCorpo(viewNegocios);
		}
		else if("Pessoa".equals(value)){
			Pessoa pessoa = transfer.getPessoa();
			PessoasView viewPessoa = new PessoasView(pessoa);
			ControllerMenu.getInstance().abrirCorpo(viewPessoa);
		}
	}
	private boolean validarDatas(){
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try{
			calendar.setTime(jData1.getDate());
			calendar2.setTime(jData2.getDate());
			return calendar2.compareTo(calendar)>=0;
		}catch(NullPointerException e){
			return false;
		}
	}
	private boolean verificarSeHoje(Date data1,Date data2){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data1).equals(sdf.format(new Date())) && sdf.format(data1).equals(sdf.format(data2));
	}
	public void mostrarDatas(JPanel panel, boolean esconder){
		panel.setVisible(esconder);
	}
}
