package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioTarefaContato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosTarefasContatos extends JpaRepository<NegocioTarefaContato,Long> {
}
