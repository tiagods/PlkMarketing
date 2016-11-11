package br.com.tiagods.controller;

import static br.com.tiagods.view.TarefasSaveView.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

import br.com.tiagods.model.CriarAdmin;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.EmpresaDao;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.NegocioDao;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PessoaDao;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.TarefaDao;
import br.com.tiagods.model.TipoTarefa;
import br.com.tiagods.model.TipoTarefaDao;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.UsuarioDao;
import static br.com.tiagods.view.MenuView.jDBody;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;

public class ControllerTarefasSave implements DefaultModelComboBox, ActionListener, ItemListener{
	
	Tarefa tarefa = null;
	Tarefa tarefaBackup;
	String item = "";
	Object minhaClasse;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()){
		case "Novo":
			this.tarefaBackup=tarefa;
			novoEditar();
			break;
		case "Editar":
			novoEditar();
			break;
		case "Salvar":
			boolean continuar = true;
			StringBuilder builder = new StringBuilder();
			TarefaDao tdao = new TarefaDao();
			if(tarefa==null){
				tarefa = new Tarefa();
				tarefa.setCriadoEm(new Date());
				tarefa.setCriadoPor(CriarAdmin.getInstance().getUsuario());
			}
			UsuarioDao usu = new UsuarioDao();
			Usuario atendente = usu.getUsuario((String)cbAtendente.getSelectedItem());
			
			tarefa.setAtendente(atendente);
			
			tarefa.setClasse((String)cbObject.getSelectedItem());
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
					try{
						if(Integer.parseInt(horas)>0 && Integer.parseInt(minutos)<24){
							if(Integer.parseInt(horas)>0 && Integer.parseInt(minutos)<60){
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
				TipoTarefaDao tipoDao = new TipoTarefaDao();
				TipoTarefa tipoTarefa = tipoDao.getTipoTarefa(item);
				tarefa.setTipoTarefa(tipoTarefa);
				if(txCodigo.getText().equals("")){
					continuar=false;
					builder.append("Nenhuma Empresa/Pessoa ou Negocio foi escolhido");
					builder.append("\n");
				}
				else{
					Object object = getObject((String)cbObject.getSelectedItem());
					if(object instanceof Empresa){
						object = new EmpresaDao().getById(Integer.parseInt(txCodigo.getText()));
						tarefa.setEmpresa((Empresa)object);
					}
					else if(object instanceof Negocio){
						object = new NegocioDao().getById(Integer.parseInt(txCodigo.getText()));
						tarefa.setNegocio((Negocio)object);
					}
					else if(object instanceof Pessoa){
						object = new PessoaDao().getById(Integer.parseInt(txCodigo.getText()));
						tarefa.setPessoa((Pessoa)object);
					}
				}
				if(continuar){
					boolean salvou = tdao.salvarTarefa(CriarAdmin.getInstance().getUsuario(), tarefa);
					if(salvou)
						salvarCancelar();
				}else{
					JOptionPane.showConfirmDialog(jDBody,builder.toString(),"Erro ao salvar", JOptionPane.ERROR_MESSAGE);
				}
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
	//se for null o formulario nao sera preenchido
	public void iniciar(Tarefa tarefa){
		this.tarefa = tarefa;
		carregarAtendentes();
		if(tarefa==null){
			limparCampos(panel);
			novoEditar();
			cbAtendente.setSelectedItem(CriarAdmin.getInstance().getUsuario().getLogin());
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
	}
	@SuppressWarnings("unchecked")
	private void carregarAtendentes() {
		UsuarioDao funcDao = new UsuarioDao();
		List<Usuario> lista = funcDao.getLista();
		cbAtendente.removeAllItems();
		lista.forEach(c->{
			cbAtendente.addItem(c.getNome());
		});		
		cbAtendente.setSelectedItem(CriarAdmin.getInstance().getUsuario().getLogin());
	}
	
	private void preencherFormulario(Tarefa tarefa){
		if(tarefa!=null){
			JRadioButton radio = recuperaRadio(tarefa.getTipoTarefa().getNome());
			int id=0;
			String[] nome = null;
			
			switch(tarefa.getClasse()){
			case "Empresa":
				id = tarefa.getEmpresa().getId();
				nome = tarefa.getEmpresa().getNome().split(" ");
				break;
			case "Negocio":
				id = tarefa.getNegocio().getId();
				nome = tarefa.getNegocio().getNome().split(" ");
				break;
			case "Pessoa":
				id = tarefa.getPessoa().getId();
				nome = tarefa.getPessoa().getNome().split(" ");
				break;
			}
			enviarDados(radio, tarefa.getDescricao(), tarefa.getClasse(), id, nome[0], tarefa.getAtendente().getLogin(), tarefa.getDataEvento(), tarefa.getHoraEvento());
			
		}
	}
	
	private JRadioButton recuperaRadio(String item){
		if(item.equals("Email"))
			return rdbtnEmail;
		else if(item.equals("Proposta"))
			return rdbtnProposta;
		else if(item.equals("Reuniao"))
			return rdbtnReuniao;
		else if(item.equals("Telefone"))
			return rdbtnTelefone;
		else
			return rdbtnVisita;
	}
	private void enviarDados(JRadioButton rb, String detalhes, String tipo, int id, String nome,String atendente, Date data, Date hora){
		rb.setSelected(true);
		txDetalhes.setText(detalhes);
		cbObject.setSelectedItem(tipo);
		txCodigo.setText(""+id);
		txNome.setText(nome);
		cbAtendente.setSelectedItem(atendente);
		txData.setDate(data);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		txHora.setText(sdf.format(hora));
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
		// TODO Auto-generated method stub
		txCodigo.setText("");
		txNome.setText("");
	}
}
