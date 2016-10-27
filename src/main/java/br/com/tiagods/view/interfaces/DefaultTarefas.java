package br.com.tiagods.view.interfaces;

public interface DefaultTarefas {
	public enum Tipos{
		Visita, Reuniao, Proposta, Telefone, Email
	}
	public default String getObject(String valor){
		String object = "";
		switch(valor){
		case "Visita":
			object = "Visita";
		case "Reuniao":
			object = "Reuniao";
		case "Proposta":
			object = "Pessoas";
		}
		return object;
	}
}
