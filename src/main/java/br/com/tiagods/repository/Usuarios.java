package br.com.tiagods.repository;

import br.com.tiagods.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByAtivoOrderByNome(int ativo);
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAllByNomeContainingIgnoreCaseAndAtivoOrderByNome(String nome, int ativo);
    List<Usuario> findAllByNomeContainingIgnoreCaseOrderByNome(String trim);
}
