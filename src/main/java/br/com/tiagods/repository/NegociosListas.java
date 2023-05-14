package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosListas extends JpaRepository<NegocioLista,Long> {
}
