package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.Cidade;

public interface CidadeDAO {
	List<Cidade> getAll();
	Cidade findById(Long id);
	Cidade findByNome(String nome);
	List<Cidade> findByEstado(Cidade.Estado estado);
}
