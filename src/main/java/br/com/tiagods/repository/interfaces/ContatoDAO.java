package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.Contato;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;

public interface ContatoDAO {
	List<Contato> getAll();
	Contato findById(Long id);
	Contato findByNome(String nome);
	List<Contato> filtrar(String nome, NegocioCategoria categoria, NegocioNivel nivel, NegocioOrigem origem,
			NegocioServico servico);	
}
