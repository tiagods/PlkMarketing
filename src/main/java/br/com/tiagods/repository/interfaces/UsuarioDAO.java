package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.Departamento;

public interface UsuarioDAO {
	Usuario save(Usuario usuario);
	void remove(Usuario usuario);
	List<Usuario> getAll();

    Usuario findByEmail(String email);

    List<Usuario> getUsuariosByNome(String nome);
	Usuario findById(Long id);
	Usuario findByEmailAndSenha(String login, String senha);

    List<Usuario> listarAtivos();

    List<Usuario> getUsuariosByDepartamento(Departamento departamento);
}
