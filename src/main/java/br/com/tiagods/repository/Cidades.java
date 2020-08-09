package br.com.tiagods.repository;

import br.com.tiagods.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNome(String localidade);
    List<Cidade> findAllByEstado(Cidade.Estado uf);
}
