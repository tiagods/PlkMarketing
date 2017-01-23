package br.com.tiagods.controller;

import static br.com.tiagods.view.TarefasSaveView.*;
import static br.com.tiagods.view.MenuView.jDBody;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modeldao.*;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.SelecaoObjetoDialog;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.TarefasView;
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
	TarefasSaveView view;
	SelecaoObjetoDialog dialog = null;
	AuxiliarComboBox padrao = new AuxiliarComboBox();

	HashMap<String, TipoTarefa> tipoTarefas = new HashMap<>();  
	HashMap<String, Usuario> usuarios = new HashMap<>();  

	Session session = null;
	//se for null o formulario nao sera preenchido
	public void iniciar(TarefasSaveView view,Tarefa tarefa, Object object){
		this.tarefa = tarefa;
		this.object = object;
		this.view = view;
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
		verificarObjeto();
		session.close();
		txQuantidade.setText("Total "+txDetalhes.getText().trim().length()+" caracteres");
		txDetalhes.addKeyListener(new contadorDigitos());
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
	private void carregarTipoTarefasEAtendentes(){
		List<TipoTarefa> listTiposTarefas = new TipoTarefaDao().listar(TipoTarefa.class, session);
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
		JPanel[] panels = new JPanel[]{pnBotoes, pnDetalhes,pnItem,pnRelacionamento};
		switch(arg0.getActionCommand()){
		case "Novo":
			if(tarefa!=null){
				this.tarefaBackup=tarefa;
			}
			for(JPanel p : panels){
				limparCampos(p);
				desbloquearCampos(p, true);
			}
			novoEditar();
			break;
		case "Editar":
			if(tarefa!=null)
				this.tarefaBackup=tarefa;
			for(JPanel p : panels){
				desbloquearCampos(p, true);
			}
			novoEditar();
			break;
		case "ChamarDialog":
			if(dialog!=null){
				dialog.dispose();
			}
			if(cbObject.getSelectedItem().equals(Modelos.Empresa))
				dialog =new SelecaoObjetoDialog(new Empresa(),txCodigoObjeto,txNomeObjeto,null,MenuView.getInstance(),true);
			else if(cbObject.getSelectedItem().equals(Modelos.Pessoa))
				dialog =new SelecaoObjetoDialog(new Pessoa(),txCodigoObjeto,txNomeObjeto,null,MenuView.getInstance(),true);
			else if(cbObject.getSelectedItem().equals(Modelos.Negocio))
				dialog =new SelecaoObjetoDialog(new Negocio(),txCodigoObjeto,txNomeObjeto,null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "Salvar":
			boolean continuar;
			StringBuilder builder = new StringBuilder();
			if(tarefa==null){
				tarefa = new Tarefa();
				tarefa.setCriadoEm(new Date());
				tarefa.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
			}
			tarefa.setAtendente(usuarios.get(cbAtendente.getSelectedItem()));
			tarefa.setClasse(cbObject.getSelectedItem().toString());
			try{
				Date dataEvento = txData.getDate();
				continuar=true;
			}catch(Exception e){
				continuar=false;
				builder.append("Data incorreta");
				builder.append("\n");
			}
			tarefa.setDescricao(txDetalhes.getText());
			if(ckFinalizado.isSelected())
				tarefa.setFinalizado(1);
			else
				tarefa.setFinalizado(0);
			if(continuar){
				invocarSalvamento(tarefa,builder.toString());
			}
			break;
		case "Cancelar":
			if(tarefaBackup!=null){
				preencherFormulario(tarefa);
				salvarCancelar();
				for(JPanel p : panels){
					desbloquearCampos(p, false);
				}
			}
			else
				view.dispose();
			break;
		default:
			setarSelecao();
			break;
		}
	}
	private void invocarSalvamento(Tarefa tarefa,String texto){
		StringBuilder builder = new StringBuilder();
		builder.append(texto);
		String hora = txHora.getText();
		boolean continuar = true;
		if(hora.length()==5){
			String horas = hora.substring(0, 2);
			String minutos = hora.substring(3);
			try{
				if(Integer.parseInt(horas)>=0 && Integer.parseInt(horas)<24){
					if(Integer.parseInt(minutos)>=0 && Integer.parseInt(minutos)<60){
						continuar=true;
						LocalTime lt = LocalTime.of(Integer.parseInt(horas), Integer.parseInt(minutos));
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(txData.getDate());

						Calendar calendar2 = Calendar.getInstance();
						calendar2.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 
								lt.getHour(), lt.getMinute());
						tarefa.setDataEvento(calendar2.getTime());
					}else{
						builder.append("Valor da Hora incorreta");
						builder.append("\n");
						continuar=false;
					}
				}else{
					builder.append("Hora incorreta");
					builder.append("\n");
					continuar=false;
				}
			}catch (NumberFormatException e) {
				builder.append("Hora incorreta");
				builder.append("\n");
				continuar=false;
			}	
		}
		session = HibernateFactory.getSession();
		session.beginTransaction();
		setarSelecao();
		tarefa.setTipoTarefa(tipoTarefas.get(item));
		if("".equals(txCodigoObjeto.getText())){
			continuar=false;
			builder.append("Nenhuma Empresa/Pessoa ou Negocio foi escolhido");
			builder.append("\n");
		}
		else{
			Object object = getObject(cbObject.getSelectedItem().toString());
			if(object instanceof Empresa){
				Empresa empresa = (Empresa) new EmpresaDao().receberObjeto(Empresa.class,Integer.parseInt(txCodigoObjeto.getText()),session);
				tarefa.setEmpresa(empresa);
				tarefa.setNegocio(null);
				tarefa.setPessoa(null);
			}
			else if(object instanceof Negocio){
				Negocio negocio = (Negocio) new NegocioDao().receberObjeto(Negocio.class,Integer.parseInt(txCodigoObjeto.getText()),session);
				tarefa.setNegocio(negocio);
				tarefa.setEmpresa(null);
				tarefa.setPessoa(null);
			}
			else if(object instanceof Pessoa){
				Pessoa pessoa = (Pessoa) new PessoaDao().receberObjeto(Pessoa.class,Integer.parseInt(txCodigoObjeto.getText()),session);
				tarefa.setPessoa(pessoa);
				tarefa.setEmpresa(null);
				tarefa.setNegocio(null);
			}
		}
		if(continuar){
			if(new TarefaDao().salvar(tarefa,session)){
				salvarCancelar();
				JPanel[] panels = new JPanel[]{pnBotoes, pnDetalhes,pnItem,pnRelacionamento};
				for(JPanel p : panels){
					desbloquearCampos(p, false);
				}
			}
		}else{
			JOptionPane.showMessageDialog(jDBody,builder.toString(),"Erro ao salvar", JOptionPane.ERROR_MESSAGE);
		}
		session.close();
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
			return rdbtnVisita;
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
		JPanel[] panels = new JPanel[]{pnBotoes, pnDetalhes,pnItem,pnRelacionamento};
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
			cbObject.setEnabled(false);
			btnAssociacao.setEnabled(false);
		}
	}

	@SuppressWarnings("rawtypes")
	private void limparCampos(Object painel){
		Container container = null;
		if (painel instanceof JPanel) {
			container = (JPanel) painel;
		}
		if (painel instanceof JScrollPane) {
			container = (JScrollPane) painel;
		}
		if (painel instanceof JViewport) {
			container = (JViewport) painel;
		}
		if(container!=null){
			for(int i=0;i<container.getComponentCount();i++){
				Component c = container.getComponent(i);
				if (c instanceof JScrollPane || c instanceof JPanel || c instanceof JViewport) {
					limparCampos(c);
					continue;
				}
				if (c instanceof JTextField) {
					JTextField field = (JTextField) c;
					field.setText("");
				}
				if (c instanceof JFormattedTextField) {
					JFormattedTextField field = (JFormattedTextField) c;
					field.setText("");
				}
				if (c instanceof JTextArea) {
					JTextArea field = (JTextArea) c;
					field.setText("");
				}
				if (c instanceof JComboBox) {
					JComboBox field = (JComboBox) c;
					field.setSelectedItem("");
				}
			}
		}
	}
	private void desbloquearCampos(Object painel,boolean bloquear){
		Container container = null;
		if (painel instanceof JPanel) {
			container = (JPanel) painel;
		}
		if (painel instanceof JScrollPane) {
			container = (JScrollPane) painel;
		}
		if (painel instanceof JViewport) {
			container = (JViewport) painel;
		}
		if(container!=null){
			for(int i=0;i<container.getComponentCount();i++){
				Component c = container.getComponent(i);
				if (c instanceof JScrollPane || c instanceof JPanel || c instanceof JViewport) {
					limparCampos(c);
					continue;
				}
				if (c instanceof JTextField) {
					JTextField field = (JTextField) c;
					field.setEditable(bloquear);
				}
				if (c instanceof JFormattedTextField) {
					JFormattedTextField field = (JFormattedTextField) c;
					field.setEditable(bloquear);
				}
				if (c instanceof JTextArea) {
					JTextArea field = (JTextArea) c;
					field.setEditable(bloquear);
				}
				if (c instanceof JComboBox) {
					JComboBox field = (JComboBox) c;
					field.setEnabled(bloquear);
				}
			}
		}
	}
	//pegar radioButton selecionado
	private void setarSelecao(){
		if(rdbtnEmail.isSelected()){
			item = "Email";
		}else if(rdbtnProposta.isSelected()){
			item = "Proposta";
		}else if(rdbtnReuniao.isSelected()){
			item = "Reuniao";
		}else if(rdbtnTelefone.isSelected()){
			item = "Telefone";
		}else if(rdbtnVisita.isSelected()){
			item = "Visita";
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		txCodigoObjeto.setText("");
		txNomeObjeto.setText("");
	}
}
