package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.negocio.Franquia;
import br.com.tiagods.model.Usuario;


public interface FranquiaDAO {
	List<Franquia> getAll();
	Franquia findById(Long id);
	Franquia findByNome(String nome);
	List<Franquia> filtrar(String nome, Franquia.Tipo tipo, Usuario atendente);
}
