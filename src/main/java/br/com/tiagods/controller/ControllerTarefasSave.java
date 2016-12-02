package br.com.tiagods.controller;

import static br.com.tiagods.view.TarefasSaveView.btnCancelar;
import static br.com.tiagods.view.TarefasSaveView.btnEditar;
import static br.com.tiagods.view.TarefasSaveView.btnNovo;
import static br.com.tiagods.view.TarefasSaveView.btnSalvar;
import static br.com.tiagods.view.TarefasSaveView.cbAtendente;
import static br.com.tiagods.view.TarefasSaveView.cbObject;
import static br.com.tiagods.view.TarefasSaveView.panel;
import static br.com.tiagods.view.TarefasSaveView.rdbtnEmail;
import static br.com.tiagods.view.TarefasSaveView.rdbtnProposta;
import static br.com.tiagods.view.TarefasSaveView.rdbtnReuniao;
import static br.com.tiagods.view.TarefasSaveView.rdbtnTelefone;
import static br.com.tiagods.view.TarefasSaveView.rdbtnVisita;
import static br.com.tiagods.view.TarefasSaveView.txCodigo;
import static br.com.tiagods.view.TarefasSaveView.txData;
import static br.com.tiagods.view.TarefasSaveView.txDetalhes;
import static br.com.tiagods.view.TarefasSaveView.txHora;
import static br.com.tiagods.view.TarefasSaveView.txNome;
import static br.com.tiagods.view.MenuView.jDBody;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import br.com.tiagods.modelDAO.EmpresaDAO;
import br.com.tiagods.modelDAO.NegocioDAO;
import br.com.tiagods.modelDAO.PessoaDAO;
import br.com.tiagods.modelDAO.TarefaDAO;
import br.com.tiagods.modelDAO.TipoTarefaDAO;
import br.com.tiagods.modelDAO.UsuarioDAO;
import br.com.tiagods.view.SelecaoObjeto;
import br.com.tiagods.view.TarefasView;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;

public class ControllerTarefasSave implements DefaultModelComboBox, ActionListener, ItemListener{
	@Override
	public Object getObject(String valor) {
		return DefaultModelComboBox.super.getObject(valor);
	}
	Tarefa tarefa = null;
	Tarefa tarefaBackup;
	String item = "";
	
	HashMap<String, TipoTarefa> tipoTarefas = new HashMap();  
	HashMap<String, Usuario> usuarios = new HashMap();  
	
	Session session = null;
	//se for null o formulario nao sera preenchido
		public void iniciar(Tarefa tarefa){
			this.tarefa = tarefa;
			session = HibernateFactory.getSession();
			session.beginTransaction();
			carregarAtendentes();
			carregarTipoTarefasEAtendentes();
			if(tarefa==null){
				limparCampos(panel);
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
			session.close();
		}
		private void carregarTipoTarefasEAtendentes(){
			List<TipoTarefa> listTiposTarefas = new TipoTarefaDAO().listar(TipoTarefa.class, session);
			listTiposTarefas.forEach(t->{
				tipoTarefas.put(t.getNome(), t);
			});
			List<Usuario> listUsuarios = new UsuarioDAO().listar(Usuario.class, session);
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
			SelecaoObjeto dialog = null;
			if(cbObject.getSelectedItem().equals(Modelos.Empresa))
				dialog =new SelecaoObjeto(new Empresa(),txCodigo,txNome);
			else if(cbObject.getSelectedItem().equals(Modelos.Pessoa))
				dialog =new SelecaoObjeto(new Pessoa(),txCodigo,txNome);
			else if(cbObject.getSelectedItem().equals(Modelos.Negocio))
				dialog =new SelecaoObjeto(new Negocio(),txCodigo,txNome);
				
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
				Date data = txData.getDate();
				tarefa.setDataEvento(data);//validar data
				continuar=true;
			}catch(Exception e){
				continuar=false;
				builder.append("Data incorreta");
				builder.append("\n");
			}
			tarefa.setDescricao(txDetalhes.getText());
			if(continuar){
				String hora = txHora.getText();
				if(hora.length()==5){
					String horas = hora.substring(0, 2);
					String minutos = hora.substring(3);
					System.out.println(horas+":"+minutos);
					try{
						if(Integer.parseInt(horas)>0 && Integer.parseInt(horas)<24){
							if(Integer.parseInt(minutos)>0 && Integer.parseInt(minutos)<60){
								continuar=true;
								Calendar calendar = Calendar.getInstance();
								calendar.set(1900, 01, 01, Integer.parseInt(horas), Integer.parseInt(minutos));
								tarefa.setHoraEvento(calendar.getTime());
							}else{
								builder.append("Hora incorreta");
								builder.append("\n");
								continuar=false;
							}
						}else{
							builder.append("Hora incorreta");
							builder.append("\n");
							continuar=false;
						}
					}catch (Exception e) {
						builder.append("Hora incorreta");
						builder.append("\n");
						continuar=false;
					}	
				}
				session = HibernateFactory.getSession();
				session.beginTransaction();
				setarSelecao();
				tarefa.setTipoTarefa(tipoTarefas.get(item));
				if("".equals(txCodigo.getText())){
					continuar=false;
					builder.append("Nenhuma Empresa/Pessoa ou Negocio foi escolhido");
					builder.append("\n");
				}
				else{
					Object object = getObject(cbObject.getSelectedItem().toString());
					if(object instanceof Empresa){
						Empresa empresa = (Empresa) new EmpresaDAO().receberObjeto(Empresa.class,Integer.parseInt(txCodigo.getText()),session);
						tarefa.setEmpresa(empresa);
					}
					else if(object instanceof Negocio){
						Negocio negocio = (Negocio) new NegocioDAO().receberObjeto(Negocio.class,Integer.parseInt(txCodigo.getText()),session);
						tarefa.setNegocio(negocio);
					}
					else if(object instanceof Pessoa){
						Pessoa pessoa = (Pessoa) new PessoaDAO().receberObjeto(Pessoa.class,Integer.parseInt(txCodigo.getText()),session);
						tarefa.setPessoa(pessoa);
					}
				}
				
				if(continuar){
					boolean salvou = new TarefaDAO().salvar(tarefa,session);
					if(salvou){
						salvarCancelar();
						 TarefasView tView = new TarefasView(new Date(), new Date(), UsuarioLogado.getInstance().getUsuario());
						 ControllerMenu.getInstance().abrirCorpo(tView);
					}
				}else{
					JOptionPane.showMessageDialog(jDBody,builder.toString(),"Erro ao salvar", JOptionPane.ERROR_MESSAGE);
				}

				session.close();
			}
			break;
		case "Cancelar":
			if(tarefaBackup!=null){
				preencherFormulario(tarefa);
				salvarCancelar();
			}
			break;
		case "Email":
			setarSelecao();
			break;
		case "Proposta":
			setarSelecao();
			break;
		case "Reuniao":
			setarSelecao();
			break;
		case "Telefone":
			setarSelecao();
			break;
		case "Visita":
			setarSelecao();
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarAtendentes() {
		List<Usuario> lista = new UsuarioDAO().listar(Usuario.class, session);
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
			}
			else if("Negocio".equals(tarefa.getClasse())){
				id = tarefa.getNegocio().getId();
				nome = tarefa.getNegocio().getNome().split(" ");
			}
			else if("Pessoa".equals(tarefa.getClasse())){
				id = tarefa.getPessoa().getId();
				nome = tarefa.getPessoa().getNome().split(" ");
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
		txCodigo.setText(id);
		txNome.setText(nome);
		cbAtendente.setSelectedItem(tarefa.getAtendente().getLogin());
		txData.setDate(tarefa.getDataEvento());
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		txHora.setText(sdf.format(tarefa.getHoraEvento()));
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
		txCodigo.setText("");
		txNome.setText("");
	}
}
