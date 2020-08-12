package br.com.tiagods.repository;

import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.negocio.Franquia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Franquias extends JpaRepository<Franquia, Long> {
    List<Franquia> filtrar(String nome, Franquia.Tipo tipo, Usuario atendente);
}
