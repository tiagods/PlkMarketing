package br.com.tiagods.controller;

import static br.com.tiagods.view.dialog.SubmeterErroDialog.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ControllerErro implements ActionListener{

	File print;
	
	public void iniciar(String detalhes, File file){
		txDetalhes.setText(detalhes);
		this.print = file;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if("OK".equals(e.getActionCommand())){
			enviarEmail();
		}
		
	}
	public void enviarEmail(){
		
	}
}
