package br.com.tiagods.model;

import java.util.HashSet;
import java.util.Set;

import br.com.tiagods.view.interfaces.Permissao;

public class Funcionario implements Permissao{
	public Set<Funcionario> getUsuarios(){
		Set<Funcionario> lista = new HashSet();
		return lista;
	}
	
	public void checkPermission(){
		
	}
}
