package br.com.tiagods.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerLogin implements ActionListener {
	String caracteres = "abcdefghijklmnopqrstuvwxyz";
	String maiusculas = caracteres.toUpperCase();
	String numeros = "0123456789";
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Entrar":
			logar();
			break;
		case "Alterar":
			alterar();
			break;
		case "Esqueci":
			esqueci();
			break;
		default:
			break;
		}
	}
	private void alterar(){
		validar("1234");
	}
	private void esqueci(){
		
	}
	private void logar(){
		
	}
	public boolean validar(String senha){
		if(senha.length()<8)
			return false;
		else{
			boolean upper=false;
			boolean lower=false; 
			boolean number=false;
			char[] lista = senha.toCharArray();
			for(char c : lista){
				if(upper && lower && number) 
					break;
				if(caracteres.contains(String.valueOf(c))) 
					lower = true;
				if(maiusculas.contains(String.valueOf(c))) 
					upper = true;
				if(numeros.contains(String.valueOf(c))) 
					number = true;
			}
			if(upper && lower && number) 
				return true;
		}
		return false;
	}
}
