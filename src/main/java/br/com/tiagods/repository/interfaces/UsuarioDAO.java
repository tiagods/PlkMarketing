package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.Usuario;

public interface UsuarioDAO {
	Usuario save(Usuario usuario);
	void remove(Usuario usuario);
	List<Usuario> getAll();

    Usuario findByEmail(String email);

    List<Usuario> getUsuariosByNome(String nome);
	Usuario findById(Long id);
	Usuario findByLoginAndSenha(String login, String senha);

    List<Usuario> listarAtivos();
}
