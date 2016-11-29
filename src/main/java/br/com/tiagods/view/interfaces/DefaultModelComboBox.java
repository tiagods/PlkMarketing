package br.com.tiagods.view.interfaces;

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
			object = "Empresa";
		case "Negocios":
			object = "Negocio";
		case "Pessoas":
			object = "Pessoa";
		}
		return object;
	}
	default Enum getEnumModelos(String valor){
		return Modelos.valueOf(valor);
	}
	
	default String getName(String valor){
		return valor;
	}
}
