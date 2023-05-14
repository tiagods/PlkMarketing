package br.com.tiagods.repository;

import br.com.tiagods.model.RegisterApp;
import br.com.tiagods.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Registers extends JpaRepository<RegisterApp, Long> {

}
