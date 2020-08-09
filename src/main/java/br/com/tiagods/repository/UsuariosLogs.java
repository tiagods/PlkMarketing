package br.com.tiagods.repository;

import br.com.tiagods.model.UsuarioLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosLogs extends JpaRepository<UsuarioLog, Long> {
}
