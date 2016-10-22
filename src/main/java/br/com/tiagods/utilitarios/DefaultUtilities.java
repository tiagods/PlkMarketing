package br.com.tiagods.utilitarios;

import java.awt.Color;

public interface DefaultUtilities{
	Color color = new Color(250,250,250);
	Color componentColor = new Color(250,250,250);
	
	public default Color getColor(){
		return color;
	}
	public default Color getComponentColor(){
		return componentColor;
	}
}