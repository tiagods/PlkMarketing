package br.com.tiagods.controller;

import static br.com.tiagods.view.dialog.NegocioPerdaDialog.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.dialog.NegocioPerdaDialog;

public class ControllerNegocioCancelado implements ActionListener{

	private NegocioPerdaDialog view;
	private NegocioProposta negocio;
		
	public void iniciar(NegocioProposta negocio,NegocioPerdaDialog dialog){
		this.view=dialog;
		this.negocio = negocio;
		txData.setDate(new Date());
		rbPreco.setSelected(true);
		preencherFormulario();
		try{
			setarIcones();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Definir":
			if(txData.getDate()!=null){
				negocio.setMotivoPerda(receberRadio());
				negocio.setDataPerda(txData.getDate());
				negocio.setDetalhesPerda(txDescricao.getText());
				view.setNegocio(this.negocio);
				this.view.dispose();
			}
			else
				JOptionPane.showMessageDialog(MenuView.jDBody, "Data informada esta incorreta!", "Validação de data!",
						JOptionPane.ERROR_MESSAGE);
			break;
		case "Cancelar":
			this.view.dispose();
			break;
		default:
			break;
		}
	}
	public void setarRadio(String motivo){
		for(Component c : view.getComponents()){
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
		for(Component c : view.getComponents()){
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
		txDescricao.setText(negocio.getDetalhesPerda()==null?"":negocio.getDetalhesPerda());
		if(negocio.getDataPerda()!=null)
			txData.setDate(negocio.getDataPerda());
	}
	public void setarIcones() throws NullPointerException{
		ImageIcon iconMoney = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_money.png"));
		rbPreco.setIcon(recalculate(iconMoney));
		ImageIcon iconDeadline = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_deadline.png"));
		rbPrazo.setIcon(recalculate(iconDeadline));
		ImageIcon iconProduct = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_product.png"));
		rbProdServico.setIcon(recalculate(iconProduct));
		ImageIcon iconExist = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_exit.png"));
		rbDesistencia.setIcon(recalculate(iconExist));
	}
	
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
}
