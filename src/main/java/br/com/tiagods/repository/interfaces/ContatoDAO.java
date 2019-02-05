package br.com.tiagods.repository.interfaces;

import java.time.LocalDate;
import java.util.List;

import br.com.tiagods.model.negocio.Contato;
import br.com.tiagods.model.negocio.Contato.ContatoTipo;
import br.com.tiagods.model.negocio.Contato.PessoaTipo;
import br.com.tiagods.model.negocio.NegocioCategoria;
import br.com.tiagods.model.negocio.NegocioLista;
import br.com.tiagods.model.negocio.NegocioNivel;
import br.com.tiagods.model.negocio.NegocioOrigem;
import br.com.tiagods.model.negocio.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.Paginacao;
import javafx.util.Pair;

public interface ContatoDAO {
	List<Contato> getAll();
	Contato findById(Long id);
	Contato findByNome(String nome);
	List<Contato> filtrar(String nome, NegocioCategoria categoria, NegocioNivel nivel, NegocioOrigem origem,
			NegocioServico servico);
	Pair<List<Contato>,Paginacao>filtrar(Paginacao paginacao,PessoaTipo pessoaTipo, ContatoTipo contatoTipo, NegocioLista lista,
			NegocioCategoria categoria, NegocioNivel nivel, NegocioOrigem origem, NegocioServico servico,
			Usuario usuario, String malaDireta, LocalDate inicio, LocalDate fim, String nome);	
}
