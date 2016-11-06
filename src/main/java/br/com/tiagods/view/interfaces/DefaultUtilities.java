package br.com.tiagods.view.interfaces;

import java.awt.Color;

public interface DefaultUtilities{
	Color color = new Color(250,250,250);
	Color panelColor = new Color(250,250,250);
	Color buttonColor = new Color(250, 250, 250);
	
	public default Color getColor(){
		return color;
	}
	public default Color getPanelColor(){
		return panelColor;
	}
	public default Color getButtonColor(){
		return buttonColor;
	}
}