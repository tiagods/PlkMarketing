package br.com.tiagods.controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

import br.com.tiagods.model.Prospeccao;
import static br.com.tiagods.view.ProspeccaoView.*;

public class ControllerProspeccao implements ActionListener{
	public void iniciar(Prospeccao prospeccao){
		preencherFormulario(prospeccao);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Editar":
			//desbloquearFormulario();
			break;
		case "Excluir":
			//invocarExclusao();
			break;
		case "Novo":
			//limpar();
			break;
		default:
			break;
		}
		
	}
	private void desbloquearFormulario(){
		
	}
	@SuppressWarnings({ "rawtypes", "rawtypes" })
	private void limparFormulario(Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JScrollPane){
				JViewport viewPort = ((JScrollPane)c).getViewport();
				limparFormulario((Container)viewPort);
			}
			else if(c instanceof JTabbedPane || c instanceof JPanel){
				limparFormulario((Container)c);
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setSelectedIndex(0);
				((JComboBox)c).setSelectedItem("");
			}
			else if(c instanceof JTable){
				DefaultTableModel model = (DefaultTableModel)((JTable)c).getModel();
				while(model.getRowCount()>0){
					model.removeRow(0);
				}
				((JTable)c).setModel(model);
			}
			else if(c instanceof JTextArea){
				((JTextArea)c).setText("");
			}
			else if(c instanceof JTextField){
				((JTextField)c).setText("");
			}
			else if(c instanceof JFormattedTextField){
				((JFormattedTextField)c).setText("");
			}			
		}
		
		
	}
	private void preencherFormulario(Prospeccao prospeccao){
		txCodigo.setText(""+prospeccao.getId());
		
	}
//	private void salvarCancelar(){
//		btnSalvar.setEnabled(false);
//		btnCancelar.setEnabled(false);
//		btnNovo.setEnabled(true);
//		btnEditar.setEnabled(true);
//		btnExcluir.setEnabled(true);
//		if("".equals(txCodigo.getText()))
//			btnExcluir.setEnabled(false);
//		desbloquerFormulario(false, pnCadastro);
//		desbloquerFormulario(false, pnAndamento);
//		desbloquerFormulario(false, pnServicosContratados);
//		
//	}
//	private void novoEditar(){
//		desbloquerFormulario(true, pnCadastro);
//		desbloquerFormulario(true, pnAndamento);
//		desbloquerFormulario(true, pnServicosContratados);
//		pnAuxiliar.setVisible(true);
//		btnNovo.setEnabled(false);
//		btnEditar.setEnabled(false);
//		btnSalvar.setEnabled(true);
//		btnCancelar.setEnabled(true);
//		btnExcluir.setEnabled(false);
//		cbAtendenteCad.setSelectedItem(UsuarioLogado.getInstance().getUsuario().getNome());
//		txEmail.setEditable(false);
//		if(this.negocio.getId()>0)
//			negocioBackup=negocio;
//		
//	}
	
}
