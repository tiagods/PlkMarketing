package br.com.tiagods.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.tiagods.view.PerdaNegocio;

public class ControllerNegocioCancelado implements ActionListener{

	private PerdaNegocio jdialog;
	public void iniciar(PerdaNegocio dialog){
		this.jdialog=dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Definir":
			break;
		case "Cancelar":
			this.jdialog.dispose();
			break;
		}
	}
	
}
