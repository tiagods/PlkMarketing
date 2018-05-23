package br.com.tiagods.config.enums;

import java.net.URL;

public enum FXMLEnum {
	LOGIN("Login"),
	MAIN("Main"),
	USUARIOPESQUISA("UsuarioPesquisa"),
	USUARIOCADASTRO("UsuarioCadastro"),
	TAREFAPESQUISA("TarefaPesquisa"),
	TAREFACADASTRO("TarefaCadastro");
	
	private String localizacao;
	
	private FXMLEnum(String localizacao) {
		this.localizacao=localizacao;
	}
	public URL getLocalizacao() {
		return getClass().getResource("/fxml/"+localizacao+".fxml");
	}
}
