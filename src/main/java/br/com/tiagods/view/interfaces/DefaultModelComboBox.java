package br.com.tiagods.view.interfaces;

import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;

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
	
	default Object getObject(String valor){
		Object object = null;
		switch(valor){
		case "Empresas":
			Empresa empresa = null;
			object = empresa;
			break;
		case "Negocios":
			Negocio negocio = null;
			object = negocio;
			break;
		case "Pessoas":
			Pessoa pessoa = null;
			object = pessoa;
			break;
		default:
			break;
		}
		return object;
	}
	default String getEnumModelos(String valor){
		switch(valor){
		case "Pessoa":
			return Modelos.Pessoas.toString();
		case "Empresa":
			return Modelos.Empresas.toString();
		case "Negocio":
			return Modelos.Negocios.toString();
		default:
			return "";
		}
	}
	
	default String getName(String valor){
		return valor;
	}
}
