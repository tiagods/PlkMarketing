package br.com.tiagods.repository;

import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long> {
    @Override
    @Query("select u from Usuario u order by u.nome")
    List<Usuario> findAll();
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAllByNomeContainingIgnoreCaseAndAtivoOrderByNome(String nome, int ativo);
    List<Usuario> findAllByNomeContainingIgnoreCaseOrderByNome(String trim);
    //ativo == 1
    List<Usuario> findAllByAtivoOrderByNome(int ativo);
    Usuario filtrar(String nome, int ativo, String ordem);
    Usuario getUsuariosByDepartamento(Departamento departamento);
}
