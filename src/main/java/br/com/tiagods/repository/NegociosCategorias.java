package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosCategorias extends JpaRepository<NegocioCategoria,Long> {
}
