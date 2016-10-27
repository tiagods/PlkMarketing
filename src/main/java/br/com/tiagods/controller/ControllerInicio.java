package br.com.tiagods.controller;

import static br.com.tiagods.view.InicioView.jData1;
import static br.com.tiagods.view.InicioView.jData2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class ControllerInicio implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()){
		case "Filtrar":
			if(validate())
			break;
		default:
			JOptionPane.showMessageDialog(null, "heelo");
			break;
		}
	}
	
	public void iniciar(){
		carregarDataAgora();
		carregarAtendentes();
		carregarTarefasHoje();
	}

	private void carregarTarefasHoje() {
		//verificar permissão e carregar tarefas do's usuarios
	}

	private void carregarAtendentes() {
		//
	}

	private void carregarDataAgora() {
		jData1.setDate(new Date());
		jData2.setDate(new Date());
        
	}
	private boolean validate(){
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try{
			calendar.setTime(jData1.getDate());
			calendar2.setTime(jData2.getDate());

			JOptionPane.showMessageDialog(null, "Data valida");
			if(calendar.before(calendar2)){
				//JOptionPane.showMessageDialog(null, "Data informada é superior ao 2 periodo");
				return false;
			}
			return true;
		}catch(NullPointerException e){
			//JOptionPane.showMessageDialog(null, "Data informada está incorreta,\n Tente novamente!");
			return false;
		}
	}

}
