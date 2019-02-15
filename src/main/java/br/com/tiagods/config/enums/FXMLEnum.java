package br.com.tiagods.config.enums;

import java.net.URL;

public enum FXMLEnum {
	CONTATO_PESQUISA("ContatoPesquisa"),
	CONTATO_CADASTRO("ContatoCadastro"),
	FRANQUIA_PESQUISA("FranquiaPesquisa"),
	FRANQUIA_CADASTRO("FranquiaCadastro"),
	IMPLATACAO_ETAPA("ImplantacaoEtapa"),
	IMPLATACAO_PACOTE("ImplantacaoPacote"),
	IMPLANTACAO_PROCESSO_CADASTRO("ImplantacaoProcessoCadastro"),
	IMPLANTACAO_PROCESSO_PESQUISA("ImplantacaoProcessoPesquisa"),
	LOGIN("Login"),
	MAIN("Main"),
	NEGOCIO_PESQUISA("NegocioPesquisa"),
	NEGOCIO_CADASTRO("NegocioCadastro"),
	PROTOCOLO_ENTRADA_CADASTRO("ProtocoloEntradaCadastro"),
	PROTOCOLO_ENTRADA_PESQUISA("ProtocoloEntradaPesquisa"),
	USUARIO_PESQUISA("UsuarioPesquisa"),
	USUARIO_CADASTRO("UsuarioCadastro"),
	TAREFA_PESQUISA("TarefaPesquisa"),
	TAREFA_CADASTRO("TarefaCadastro"),
	TAREFA_DIALOG_CONTATO("TarefaDialogContato"),
	TAREFA_DIALOG_PROPOSTA("TarefaDialogProposta"),
	PROGRESS_SAMPLE("Progress");
    private String localizacao;
	private FXMLEnum(String localizacao) {
		this.localizacao=localizacao;
	}
	public URL getLocalizacao() {
		return getClass().getResource("/fxml/"+localizacao+".fxml");
	}
}
