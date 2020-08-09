package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.Franquia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Franquias extends JpaRepository<Franquia, Long> {
}
