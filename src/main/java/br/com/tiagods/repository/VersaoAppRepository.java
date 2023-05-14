package br.com.tiagods.repository;

import br.com.tiagods.model.VersaoApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersaoAppRepository extends JpaRepository<VersaoApp, Long> {
    List<VersaoApp> findAllByOrderByIdDesc();
}
