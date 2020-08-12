package br.com.tiagods.repository;

import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.negocio.*;
import br.com.tiagods.repository.interfaces.Paginacao;
import javafx.util.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Contatos extends JpaRepository<Contato, Long> {
    List<Contato> filtrar(String nome, NegocioCategoria categoria, NegocioNivel nivel, NegocioOrigem origem, NegocioServico servico);

    Pair<List<Contato>, Paginacao> filtrar(Paginacao pagination, Contato.PessoaTipo pessoaTipo, Contato.ContatoTipo contatoTipo, NegocioLista lista, NegocioCategoria categoria,
                                           NegocioNivel nivel, NegocioOrigem origem, NegocioServico servico, Usuario usuario, String malaDireta, LocalDate inicio, LocalDate fim, String nome);
}
