package br.com.tiagods.view.interfaces;

import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Prospeccao;

public interface DefaultEnumModel {
	
	public enum Modelos{
		Empresa, Negocio, Pessoa, Prospeccao
	}
	//classe empresas, pessoas
	public enum Logradouro{
		Aeroporto, Alameda, Area, Avenida, Campo, Chácara, Colônia, Condomínio, Conjunto, Distrito, 
		Esplanada, Estação, Estrada, Favela, Feira, Jardim, Ladeira, Lago, Lagoa, Largo, Loteamento, Morro, 
		Núcleo, Parque, Passarela, Pátio, Praia, Quadra, Recanto, Residencial, Rodovia, Rua, Setor, Sítio, 
		Travessa, Trecho, Trevo, Vale, Vereda, Via, Viaduto, Viela, Vila
	}
	//empresas, pessoas, negocios
	
	default Object getObject(String valor){
		Object object = null;
		switch(valor){
		case "Empresa":
			Object novo = new Empresa();
			return novo;
		case "Negocio":
			novo = new Negocio();
			return novo;
		case "Pessoa":
			novo = new Pessoa();
			return novo;
		case "Prospeccao":
			novo = new Prospeccao();
			return novo;
		default:
			break;
		}
		return object;
	}
	default String getEnumModelos(String valor){
		switch(valor){
		case "Pessoa":
			return Modelos.Pessoa.toString();
		case "Empresa":
			return Modelos.Empresa.toString();
		case "Negocio":
			return Modelos.Negocio.toString();
		case "Prospeccao":
			return Modelos.Prospeccao.toString();
		default:
			return "";
		}
	}
	
	default String getName(String valor){
		return valor;
	}
}
