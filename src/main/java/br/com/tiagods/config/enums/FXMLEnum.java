package br.com.tiagods.config.enums;

import java.net.URL;

public enum FXMLEnum {
	CONTATO_PESQUISA("ContatoPesquisa"),
	CONTATO_CADASTRO("ContatoCadastro"),
	FRANQUIA_PESQUISA("FranquiaPesquisa"),
	FRANQUIA_CADASTRO("FranquiaCadastro"),
	LOGIN("Login"),
	MAIN("Main"),
	NEGOCIO_PESQUISA("NegocioPesquisa"),
	NEGOCIO_CADASTRO("NegocioCadastro"),
	USUARIO_PESQUISA("UsuarioPesquisa"),
	USUARIO_CADASTRO("UsuarioCadastro"),
	TAREFA_PESQUISA("TarefaPesquisa"),
	TAREFA_CADASTRO("TarefaCadastro"),
	TAREFA_DIALOG_CONTATO("TarefaDialogContato"),
	TAREFA_DIALOG_PROPOSTA("TarefaDialogProposta");
	private String localizacao;
	private FXMLEnum(String localizacao) {
		this.localizacao=localizacao;
	}
	public URL getLocalizacao() {
		return getClass().getResource("/fxml/"+localizacao+".fxml");
	}
}
