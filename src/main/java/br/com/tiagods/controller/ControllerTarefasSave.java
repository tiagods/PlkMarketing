package br.com.tiagods.controller;

import static br.com.tiagods.view.TarefasSaveView.btnAssociacao;
import static br.com.tiagods.view.TarefasSaveView.btnCancelar;
import static br.com.tiagods.view.TarefasSaveView.btnEditar;
import static br.com.tiagods.view.TarefasSaveView.btnNovo;
import static br.com.tiagods.view.TarefasSaveView.btnSalvar;
import static br.com.tiagods.view.TarefasSaveView.cbAtendente;
import static br.com.tiagods.view.TarefasSaveView.cbObject;
import static br.com.tiagods.view.TarefasSaveView.ckFinalizado;
import static br.com.tiagods.view.TarefasSaveView.lbDetalhesLote;
import static br.com.tiagods.view.TarefasSaveView.pnLote;
import static br.com.tiagods.view.TarefasSaveView.pnRelacionamento;
import static br.com.tiagods.view.TarefasSaveView.rdbtnEmail;
import static br.com.tiagods.view.TarefasSaveView.rdbtnProposta;
import static br.com.tiagods.view.TarefasSaveView.rdbtnReuniao;
import static br.com.tiagods.view.TarefasSaveView.rdbtnTelefone;
import static br.com.tiagods.view.TarefasSaveView.rdbtnWhatsApp;
import static br.com.tiagods.view.TarefasSaveView.txCodigoObjeto;
import static br.com.tiagods.view.TarefasSaveView.txData;
import static br.com.tiagods.view.TarefasSaveView.txDetalhes;
import static br.com.tiagods.view.TarefasSaveView.txHora;
import static br.com.tiagods.view.TarefasSaveView.txNomeObjeto;
import static br.com.tiagods.view.TarefasSaveView.txQuantidade;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Etapa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Prospeccao;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.dialog.SelecaoDialog;
import br.com.tiagods.view.interfaces.DefaultEnumModel;

public class ControllerTarefasSave implements DefaultEnumModel, ActionListener, ItemListener{
	@Override
	public Object getObject(String valor) {
		return DefaultEnumModel.super.getObject(valor);
	}
	Tarefa tarefa = null;
	Tarefa tarefaBackup;
	String item = "";
	Object object;
	Set<Integer> listaLote;
	boolean salvarLote = false;
	TarefasSaveView view;
	Map<String,JRadioButton> parametroNegocios;
	SelecaoDialog dialog = null;
	AuxiliarComboBox padrao = new AuxiliarComboBox();

	HashMap<String, TipoTarefa> tipoTarefas = new HashMap<>();  
	HashMap<String, Usuario> usuarios = new HashMap<>();  
	Map<String,Etapa> etapas = new HashMap<>();
	
	Session session = null;
	
	GenericDao dao = new GenericDao();
	//se for null o formulario nao sera preenchido
	@SuppressWarnings("unchecked")
	public void iniciar(TarefasSaveView view, Tarefa tarefa, Object object, Map<String,JRadioButton> parametroNegocios,Set<Integer> listaLote, boolean salvarLote){
		this.tarefa = tarefa;
		this.object = object;
		this.view = view;
		this.parametroNegocios = parametroNegocios;//atualiza andamento negocios caso seja dele a tela aberta
		this.listaLote = listaLote;
		this.salvarLote = salvarLote;//salvar tarefas em lote
		session = HibernateFactory.getSession();
		session.beginTransaction();
		carregarAtendentes();
		carregarTipoTarefasEAtendentes();
		if(tarefa==null){
			novoEditar();
			cbAtendente.setSelectedItem(UsuarioLogado.getInstance().getUsuario().getLogin());
			rdbtnEmail.setSelected(true);
			Date data = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			txData.setDate(data);
			txHora.setText(sdf.format(data));
		}
		else{
			preencherFormulario(tarefa);
			novoEditar();
		}
		if(salvarLote) {
			pnRelacionamento.setVisible(false);
			pnLote.setVisible(true);
			lbDetalhesLote.setText("Foram escolhidos: "+listaLote.size()+" registro(s)");
		}
		else {
			pnRelacionamento.setVisible(true);
			pnLote.setVisible(false);
			lbDetalhesLote.setText("");
			verificarObjeto();
		}
		List<Etapa> lista = dao.listar(Etapa.class, session);
		lista.forEach(c->{
			etapas.put(c.getNome(),c);
		});
		session.close();
		setarSelecao();
		txQuantidade.setText("Total "+txDetalhes.getText().trim().length()+" caracteres");
		txDetalhes.addKeyListener(new contadorDigitos());
		try{
			setarIcons();
		}catch (NullPointerException e) {
		}
	}
	public class contadorDigitos implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
			txQuantidade.setText("Total "+txDetalhes.getText().trim().length()+" caracteres");
		}
	}
	@SuppressWarnings("unchecked")
	private void carregarTipoTarefasEAtendentes(){
		List<TipoTarefa> listTiposTarefas =dao.listar(TipoTarefa.class, session);
		listTiposTarefas.forEach(t->{
			tipoTarefas.put(t.getNome(), t);
		});
		List<Usuario> listUsuarios = new UsuarioDao().listar(Usuario.class, session);
		listUsuarios.forEach(u->{
			usuarios.put(u.getLogin(), u);
		});
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()){
		case "Novo":
			if(tarefa!=null){
				this.tarefaBackup=tarefa;
			}
			novoEditar();
			break;
		case "Editar":
			if(tarefa!=null)
				this.tarefaBackup=tarefa;
			novoEditar();
			break;
		case "ChamarDialog":
			if(dialog!=null){
				dialog.dispose();
			}
			if(cbObject.getSelectedItem().equals(Modelos.Empresa))
				dialog =new SelecaoDialog(new Empresa(),txCodigoObjeto,txNomeObjeto,null,null,MenuView.getInstance(),true);
			else if(cbObject.getSelectedItem().equals(Modelos.Pessoa))
				dialog =new SelecaoDialog(new Pessoa(),txCodigoObjeto,txNomeObjeto,null,null,MenuView.getInstance(),true);
			else if(cbObject.getSelectedItem().equals(Modelos.Negocio))
				dialog =new SelecaoDialog(new Negocio(),txCodigoObjeto,txNomeObjeto,null,null,MenuView.getInstance(),true);
			else if(cbObject.getSelectedItem().equals(Modelos.Prospeccao))
				dialog =new SelecaoDialog(new Prospeccao(),txCodigoObjeto,txNomeObjeto,null,null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "Salvar":
			invocarSalvamento();
			break;
		case "Cancelar":
			if(tarefaBackup!=null){
				preencherFormulario(tarefa);
				salvarCancelar();
			}
			else
				view.dispose();
			break;
		default:
			setarSelecao();
			break;
		}
	}
	private void invocarSalvamento() {
		Calendar calendar = validarHora();
		if(calendar==null) return;
		if(salvarLote) {
			List<Integer> lista = new ArrayList<>();
			lista.addAll(listaLote);
			lbDetalhesLote.setText("Salvando");
			session = HibernateFactory.getSession();
			session.beginTransaction();
			for(int i = 0 ; i<lista.size(); i++) {
				if(i%100==0) {
					session.flush();
					session.clear();
				}
				lbDetalhesLote.setText("Recebendo "+(i+1)+" de "+lista);
				int valor = lista.get(i);
				if(calendar!=null) {
					salvar(object,calendar,null,valor);
				}
			}
			try {
				lbDetalhesLote.setText("Salvando...");
				session.getTransaction().commit();
				salvarCancelar();
				JOptionPane.showMessageDialog(MenuView.jDBody, "Salvo com sucesso!");
				view.dispose();
			}catch(Exception e ) {
				JOptionPane.showMessageDialog(MenuView.jDBody, "Erro ao salvar em lote: \n"+e);
			}finally {
				session.close();
			}
		}
		else {
			if("".equals(txCodigoObjeto.getText())){
				JOptionPane.showMessageDialog(MenuView.jDBody, "Nenhuma Empresa/Pessoa/Proscepçção ou Negocio foi escolhido");
				return;
			}
			Object object = getObject(cbObject.getSelectedItem().toString());
			try {
				session = HibernateFactory.getSession();
				session.beginTransaction();
				if(object instanceof Negocio)
					object = (Negocio) dao.receberObjeto(Negocio.class,Integer.parseInt(txCodigoObjeto.getText()),session);
				if(salvar(object,calendar,tarefa,Integer.parseInt(txCodigoObjeto.getText()))) {
					if(object instanceof Negocio) atualizaNegocios(session,(Negocio)object,item);
					session.getTransaction().commit();
					salvarCancelar();
					JOptionPane.showMessageDialog(MenuView.jDBody, "Salvo com sucesso!");
					view.dispose();
				}
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(MenuView.jDBody, "Erro ao salvar a Tarefa\n "+ex);
			}finally {
				session.close();
			}
		}
	}
	private boolean salvar(Object obj, Calendar calendar, Tarefa tarefa, int cod){//obt  classe, calendar= data evento, cod = id da classe pai
		StringBuilder builder = new StringBuilder();
		if(tarefa==null){
			tarefa = new Tarefa();
			tarefa.setCriadoEm(new Date());
			tarefa.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
			tarefa.setAlertaEnviado(0);
		}
		tarefa.setAtendente(usuarios.get(cbAtendente.getSelectedItem()));
		tarefa.setClasse(cbObject.getSelectedItem().toString());
		tarefa.setDescricao(txDetalhes.getText());
		if(ckFinalizado.isSelected())
			tarefa.setFinalizado(1);
		else
			tarefa.setFinalizado(0);
		tarefa.setDataEvento(calendar.getTime());
//		session = HibernateFactory.getSession();
//		session.beginTransaction();
		setarSelecao();
		tarefa.setTipoTarefa(tipoTarefas.get(item));
			if(obj instanceof Empresa){
				//object = dao.receberObjeto(Empresa.class,Integer.parseInt(txCodigoObjeto.getText()),session);
				Empresa empresa = new Empresa();
				empresa.setId(cod);
				//tarefa.setEmpresa((Empresa)object);
				tarefa.setEmpresa(empresa);
				tarefa.setNegocio(null);
				tarefa.setPessoa(null);
				tarefa.setProspeccao(null);
			}
			else if(obj instanceof Negocio){
				//tarefa.setNegocio((Negocio)object);
//				Negocio negocio = new Negocio();
//				negocio.setId(cod);
				tarefa.setNegocio((Negocio)obj);
				tarefa.setEmpresa(null);
				tarefa.setPessoa(null);
				tarefa.setProspeccao(null);
			}
			else if(obj instanceof Pessoa){
				//Pessoa pessoa = (Pessoa) dao.receberObjeto(Pessoa.class,Integer.parseInt(txCodigoObjeto.getText()),session);
				//tarefa.setPessoa(pessoa);
				Pessoa pessoa = new Pessoa();
				pessoa.setId(cod);
				tarefa.setPessoa(pessoa);
				tarefa.setEmpresa(null);
				tarefa.setNegocio(null);
				tarefa.setProspeccao(null);
			}
			else if(obj instanceof Prospeccao){
				//Prospeccao prospeccao = (Prospeccao) dao.receberObjeto(Prospeccao.class,Integer.parseInt(txCodigoObjeto.getText()),session);
				//tarefa.setProspeccao(prospeccao);
				Prospeccao prospeccao = new Prospeccao();
				prospeccao.setId(cod);
				tarefa.setProspeccao(prospeccao);
				tarefa.setEmpresa(null);
				tarefa.setNegocio(null);
				tarefa.setPessoa(null);
			}
		try {
			session.saveOrUpdate(tarefa);
			return true;
		}catch(Exception e){
			JOptionPane.showMessageDialog(MenuView.jDBody,builder.toString(),"Erro ao salvar", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	private void atualizaNegocios(Session session,Negocio negocio,String escolha){
		if(!"Fechamento".equals(negocio.getEtapa().getNome()) && !"Indefinida".equals(negocio.getEtapa().getNome())){
			switch(escolha){
			case "Email":
			case "Telefone":
			case "WhatsApp":
				if(negocio.getContato()==null) {
					negocio.setEtapa(etapas.get("Contato"));
					negocio.setContato(new Date());
					session.update(negocio);
					if(parametroNegocios!=null) parametroNegocios.get("Contato").setSelected(true);
				}
				break;
			case "Proposta":
				if(negocio.getEnvioProposta()==null) {
					negocio.setEtapa(etapas.get("Envio de Proposta"));
					negocio.setEnvioProposta(new Date());
					session.update(negocio);
					if(parametroNegocios!=null) parametroNegocios.get("Envio de Proposta").setSelected(true);
				}
				break;
			case "Reuniao":
				if(negocio.getFollowUp()==null) {
					negocio.setEtapa(etapas.get("Follow-up"));
					negocio.setFollowUp(new Date());
					session.update(negocio);
					if(parametroNegocios!=null) parametroNegocios.get("Follow-up").setSelected(true);
				}
				break;
			default:
				break;
			}
		}
		etapas.clear();
	}
	@SuppressWarnings("unchecked")
	private void carregarAtendentes() {
		List<Usuario> lista = new UsuarioDao().listar(Usuario.class, session);
		cbAtendente.removeAllItems();
		lista.forEach(c->{
			cbAtendente.addItem(c.getLogin());
		});		
		cbAtendente.setSelectedItem(UsuarioLogado.getInstance().getUsuario().getLogin());	
	}
	private void preencherFormulario(Tarefa tarefa){
		if(tarefa!=null){
			JRadioButton radio = recuperaRadio(tarefa.getTipoTarefa().getNome());
			String[] nome = null;
			int id=-1;
			if("Empresa".equals(tarefa.getClasse())){
				id = tarefa.getEmpresa().getId();
				nome = tarefa.getEmpresa().getNome().split(" ");
				cbObject.setSelectedItem(Modelos.valueOf("Empresa"));
			}
			else if("Negocio".equals(tarefa.getClasse())){
				id = tarefa.getNegocio().getId();
				nome = tarefa.getNegocio().getNome().split(" ");
				cbObject.setSelectedItem(Modelos.valueOf("Negocio"));
			}
			else if("Pessoa".equals(tarefa.getClasse())){
				id = tarefa.getPessoa().getId();
				nome = tarefa.getPessoa().getNome().split(" ");
				cbObject.setSelectedItem(Modelos.valueOf("Pessoa"));
			}
			else if("Prospeccao".equals(tarefa.getClasse())){
				id = tarefa.getProspeccao().getId();
				nome = tarefa.getProspeccao().getResponsavel().split("11");
				cbObject.setSelectedItem(Modelos.valueOf("Prospeccao"));
			}
			enviarDados(radio, tarefa, nome[0],String.valueOf(id));
		}
	}
	private JRadioButton recuperaRadio(String item){
		if("Email".equals(item))
			return rdbtnEmail;
		else if("Proposta".equals(item))
			return rdbtnProposta;
		else if("Reuniao".equals(item))
			return rdbtnReuniao;
		else if("Telefone".equals(item))
			return rdbtnTelefone;
		else
			return rdbtnWhatsApp;
	}
	private void enviarDados(JRadioButton rb, Tarefa tarefa, String nome,String id){
		rb.setSelected(true);
		txDetalhes.setText(tarefa.getDescricao());
		cbObject.setSelectedItem(getEnumModelos(tarefa.getClasse()));
		txCodigoObjeto.setText(id);
		txNomeObjeto.setText(nome);
		cbAtendente.setSelectedItem(tarefa.getAtendente().getLogin());
		txData.setDate(tarefa.getDataEvento());
		if(tarefa.getFinalizado()==1)
			ckFinalizado.setSelected(true);
		else
			ckFinalizado.setSelected(false);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		txHora.setText(sdf.format(tarefa.getDataEvento()));
	}
	private void salvarCancelar(){
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnEditar.setEnabled(true);
	}
	private void novoEditar(){
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		verificarObjeto();
	}
	private void verificarObjeto(){
		if(object!=null){
			if(object instanceof Empresa){
				cbObject.setSelectedItem(Modelos.valueOf("Empresa"));
				txCodigoObjeto.setText(((Empresa)object).getId()+"");
				txNomeObjeto.setText(((Empresa)object).getNome());
			}
			else if(object instanceof Negocio){
				cbObject.setSelectedItem(Modelos.valueOf("Negocio"));
				txCodigoObjeto.setText(((Negocio)object).getId()+"");
				txNomeObjeto.setText(((Negocio)object).getNome());
			}
			else if(object instanceof Pessoa){
				cbObject.setSelectedItem(Modelos.valueOf("Pessoa"));
				txCodigoObjeto.setText(((Pessoa)object).getId()+"");
				txNomeObjeto.setText(((Pessoa)object).getNome());
			}
			else if(object instanceof Prospeccao){
				cbObject.setSelectedItem(Modelos.valueOf("Prospeccao"));
				txCodigoObjeto.setText(((Prospeccao)object).getId()+"");
				txNomeObjeto.setText(((Prospeccao)object).getNome());
			}
			cbObject.setEnabled(false);
			btnAssociacao.setEnabled(false);
		}
	}

	
	//pegar radioButton selecionado
	private void setarSelecao(){
		Color color = Color.orange;
		if(rdbtnEmail.isSelected()){
			item = "Email";
			rdbtnEmail.setOpaque(true);
			rdbtnEmail.setBackground(color);
		}
		else{
			rdbtnEmail.setOpaque(false);
		}
		if(rdbtnProposta.isSelected()){
			item = "Proposta";
			rdbtnProposta.setOpaque(true);
			rdbtnProposta.setBackground(color);
		}
		else{
			rdbtnProposta.setOpaque(false);
		}
		if(rdbtnReuniao.isSelected()){
			item = "Reuniao";
			rdbtnReuniao.setOpaque(true);
			rdbtnReuniao.setBackground(color);
		}
		else{
			rdbtnReuniao.setOpaque(false);
		}
		if(rdbtnTelefone.isSelected()){
			item = "Telefone";
			rdbtnTelefone.setOpaque(true);
			rdbtnTelefone.setBackground(color);
		}
		else{
			rdbtnTelefone.setOpaque(false);
		}
		if(rdbtnWhatsApp.isSelected()){
			item = "WhatsApp";
			rdbtnWhatsApp.setOpaque(true);
			rdbtnWhatsApp.setBackground(color);
		}
		else{
			rdbtnWhatsApp.setOpaque(false);
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		txCodigoObjeto.setText("");
		txNomeObjeto.setText("");
	}
	public void setarIcons() throws NullPointerException {
		ImageIcon iconEmail = new ImageIcon(TarefasSaveView.class.getResource("/br/com/tiagods/utilitarios/tarefas_email.png"));
        rdbtnEmail.setIcon(recalculate(iconEmail));
        rdbtnEmail.setBorderPainted(true);
        ImageIcon iconProposta = new ImageIcon(TarefasSaveView.class.getResource("/br/com/tiagods/utilitarios/tarefas_proposta.png"));
        rdbtnProposta.setIcon(recalculate(iconProposta));
        rdbtnProposta.setBorderPainted(true);
        ImageIcon iconReuniao = new ImageIcon(TarefasSaveView.class.getResource("/br/com/tiagods/utilitarios/tarefas_reuniao.png"));
        rdbtnReuniao.setIcon(recalculate(iconReuniao));
        rdbtnReuniao.setBorderPainted(true);
        ImageIcon iconPhone = new ImageIcon(TarefasSaveView.class.getResource("/br/com/tiagods/utilitarios/tarefas_fone.png"));
        rdbtnTelefone.setIcon(recalculate(iconPhone));
        rdbtnTelefone.setBorderPainted(true);
        
        ImageIcon iconWhatsApp = new ImageIcon(TarefasSaveView.class.getResource("/br/com/tiagods/utilitarios/tarefas_whatsapp.png"));
        rdbtnWhatsApp.setIcon(recalculate(iconWhatsApp));        
        rdbtnWhatsApp.setBorderPainted(true);
        
        ImageIcon iconNew = new ImageIcon(ControllerTarefasSave.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
        btnNovo.setIcon(recalculate(iconNew));
        ImageIcon iconEdit = new ImageIcon(ControllerTarefasSave.class.getResource("/br/com/tiagods/utilitarios/button_edit.png"));
    	btnEditar.setIcon(recalculate(iconEdit));
    	ImageIcon iconSave = new ImageIcon(ControllerTarefasSave.class.getResource("/br/com/tiagods/utilitarios/button_save.png"));
    	btnSalvar.setIcon(recalculate(iconSave));
    	ImageIcon iconCancel = new ImageIcon(ControllerTarefasSave.class.getResource("/br/com/tiagods/utilitarios/button_cancel.png"));
    	btnCancelar.setIcon(recalculate(iconCancel));
    	
    	ImageIcon iconAdd = new ImageIcon(ControllerTarefasSave.class.getResource("/br/com/tiagods/utilitarios/arrow_rigth.png"));
    	btnAssociacao.setIcon(recalculate(iconAdd));
	}
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
	private Calendar validarHora() {
		if(txData.getDate()== null) return null;
		String hora = txHora.getText();
		if(hora.length()==5){
			String horas = hora.substring(0, 2);
			String minutos = hora.substring(3);
			try{
				if(Integer.parseInt(horas)>=0 && Integer.parseInt(horas)<24){
					if(Integer.parseInt(minutos)>=0 && Integer.parseInt(minutos)<60){
						LocalTime lt = LocalTime.of(Integer.parseInt(horas), Integer.parseInt(minutos));
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(txData.getDate());
						Calendar calendar2 = Calendar.getInstance();
						calendar2.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 
								lt.getHour(), lt.getMinute());
						calendar2.set(Calendar.SECOND, 0);
						calendar2.set(Calendar.MILLISECOND, 0);
						return calendar2;
						//tarefa.setDataEvento(calendar2.getTime());
					}else{
						JOptionPane.showMessageDialog(MenuView.jDBody, "Valor da Hora incorreta");
						return null;
					}
				}else{
					JOptionPane.showMessageDialog(MenuView.jDBody, "Valor da Hora incorreta");
					return null;
				}
			}catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(MenuView.jDBody, "Valor da Hora incorreta");
				return null;
			}	
		}
		else
			return null;
	}
}
