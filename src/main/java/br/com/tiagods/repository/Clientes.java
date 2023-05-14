package br.com.tiagods.repository;

import br.com.tiagods.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long> {
}
