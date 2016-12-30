package br.com.tiagods.controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JRadioButton;

import br.com.tiagods.model.Negocio;
import br.com.tiagods.view.PerdaNegocio;

import static br.com.tiagods.view.PerdaNegocio.*;

public class ControllerNegocioCancelado implements ActionListener{

	private PerdaNegocio jdialog;
	private Negocio negocio;
	
	public void iniciar(Negocio negocio,PerdaNegocio dialog){
		this.jdialog=dialog;
		this.negocio = negocio;
		txData.setDate(new Date());
		rbPreco.setSelected(true);
		preencherFormulario();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Definir":
			if(txData.getDate()!=null){
				negocio.setMotivoPerda(receberRadio());
				negocio.setDataPerda(txData.getDate());
				negocio.setDetalhesPerda(txDescricao.getText());
			}
			break;
		case "Cancelar":
			this.jdialog.dispose();
			break;
		default:
			break;
		}
	}
	public void setarRadio(String motivo){
		for(Component c : jdialog.getComponents()){
			if(c instanceof JRadioButton){
				JRadioButton radio = (JRadioButton)c;
				if(motivo.equals(radio.getName())){
					radio.setSelected(true);
					break;
				}
			}
		}
	}
	public String receberRadio(){
		for(Component c : jdialog.getComponents()){
			if(c instanceof JRadioButton){
				JRadioButton radio = (JRadioButton)c;
				if(radio.isSelected()){
					return radio.getName();
				}
			}
		}
		return "";
	}
	public void preencherFormulario(){
		if(!"".equals(negocio.getMotivoPerda()) || !negocio.getMotivoPerda().equals(null))
			setarRadio(negocio.getMotivoPerda());
		txDescricao.setText(negocio.getDetalhesPerda());
		if(negocio.getDataPerda()!=null)
			txData.setDate(negocio.getDataPerda());
	}
}
