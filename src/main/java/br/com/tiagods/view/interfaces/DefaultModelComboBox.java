package br.com.tiagods.view.interfaces;

import br.com.tiagods.model.*;
public interface DefaultModelComboBox {
	
	public enum Modelos{
		Empresas, Negocios, Pessoas
	}
	//classe empresas, pessoas
	public enum Logradouro{
		Aeroporto, Alameda, Área, Avenida, Campo, Chácara, Colônia, Condomínio, Conjunto, Distrito, 
		Esplanada, Estação, Estrada, Favela, Feira, Jardim, Ladeira, Lago, Lagoa, Largo, Loteamento, Morro, 
		Núcleo, Parque, Passarela, Pátio, Praça, Quadra, Recanto, Residencial, Rodovia, Rua, Setor, Sítio, 
		Travessa, Trecho, Trevo, Vale, Vereda, Via, Viaduto, Viela, Vila
	}
	//empresas, pessoas, negocios
	public enum Estados{
		AC, AL, AP, AM, BA, CE, DF, ES, GO, MA, MT, MS, MG, PA, PB, PR, PE, PI, RJ, RN, RS, RO, RR, SC, SP, SE, TO
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
	default String getName(String valor){
		return valor;
	}
}
