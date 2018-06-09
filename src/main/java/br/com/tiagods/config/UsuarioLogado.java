package br.com.tiagods.config;

import br.com.tiagods.model.Usuario;

public class UsuarioLogado {
    private static UsuarioLogado instance;
    private static Usuario usuario = new Usuario(1L, "Tiago");

    public static UsuarioLogado getInstance() {
        if (instance == null) instance = new UsuarioLogado();
        return instance;
    }
    private UsuarioLogado(){
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) { this.usuario = usuario;}
}
