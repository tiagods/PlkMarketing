package br.com.tiagods.config.init;

import br.com.tiagods.model.Usuario;

import java.io.IOException;

public class UsuarioLogado {
    private static UsuarioLogado instance;
    private static Usuario usuario = null;

    public static UsuarioLogado getInstance() {
        if (instance == null) instance = new UsuarioLogado();
        return instance;
    }
    private UsuarioLogado(){
    }
    public static Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) { 
    	this.usuario = usuario;
    	UsuarioCache cache = UsuarioCache.getInstance();
    	try {
    		cache.save(usuario.getEmail());
    	}catch(IOException e) {    		
    	}
    }
    public String lastLogin() {
    	UsuarioCache cache = UsuarioCache.getInstance();
    	try {
    		return cache.load();
    	}catch(IOException e) {    		
    		return "";
    	}
    }
}
