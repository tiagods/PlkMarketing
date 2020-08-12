package br.com.tiagods.repository;

import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolosEntradas extends JpaRepository<ProtocoloEntrada, Long> {
}
