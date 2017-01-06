package br.com.tiagods.modeldao;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Teste {

	public static void main(String[] args) {
		String[] nivel = {"PLATINA","Advocacia/Outros","Proponente","PRATA 2","OURO 3","OURO 2",
				"Consultoria","PRATA 3","CONJUR","OURO 1","BRONZE","PRATA 1","Exceção","Inativa","Desligada",
				"Jurídico","Em andamento","DIAMANTE","Regularização","SUSPENSA","Extinta"};
		System.out.println(nivel.length);
		
		Set<String> lista = new TreeSet<>();
		for(String s : nivel)
			lista.add(s.toLowerCase());
		
		Iterator<String> i = lista.iterator();
		System.out.println(lista.size());
		
		while(i.hasNext())
			System.out.println(i.next());
	}

}
