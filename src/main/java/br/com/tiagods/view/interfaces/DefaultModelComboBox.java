package br.com.tiagods.view.interfaces;

import br.com.tiagods.model.*;
public interface DefaultModelComboBox {
	
	public enum Modelos{
		Empresas, Negocios, Pessoas
	}
	
	default Object getObject(String valor){
		Object object = null;
		switch(valor){
		case "Empresas":
			Empresa empresa = new Empresa();
			object = empresa;
		case "Negocios":
			Negocio negocio = new Negocio();
			object = negocio;
		case "Pessoas":
			Pessoa pessoa = new Pessoa();
			object = pessoa;
		}
		return object;
	}
}
