package br.com.tiagods.config.enums;

import java.net.URL;

public enum FXMLEnum {
	CONTATO_PESQUISA("ContatoPesquisa"),
	CONTATO_CADASTRO("ContatoCadastro"),
	DEPARTAMENTO("Departamento"),
	FRANQUIA_PESQUISA("FranquiaPesquisa"),
	FRANQUIA_CADASTRO("FranquiaCadastro"),
	IMPLATACAO_ETAPA("ImplantacaoEtapa"),
	IMPLANTACAO_ETAPA_STATUS("ImplantacaoEtapaStatus"),
	IMPLATACAO_PACOTE_CADASTRO("ImplantacaoPacoteCadastro"),
	IMPLATACAO_PACOTE_PESQUISA("ImplantacaoPacotePesquisa"),
	IMPLANTACAO_PROCESSO_CADASTRO("ImplantacaoProcessoCadastro"),
	IMPLANTACAO_PROCESSO_PESQUISA("ImplantacaoProcessoPesquisa"),
	LOGIN("Login"),
	MAIN("Main"),
	NEGOCIO_PESQUISA("NegocioPesquisa"),
	NEGOCIO_CADASTRO("NegocioCadastro"),
	PROGRESS_SAMPLE("Progress"),
	PROTOCOLO_ENTRADA_CADASTRO("ProtocoloEntradaCadastro"),
	PROTOCOLO_ENTRADA_PESQUISA("ProtocoloEntradaPesquisa"),
	RECUPERACAO_SENHA("RecuperacaoSenha"),
	SOBRE("Sobre"),
	USUARIO_PESQUISA("UsuarioPesquisa"),
	USUARIO_CADASTRO("UsuarioCadastro"),
	TAREFA_PESQUISA("TarefaPesquisa"),
	TAREFA_CADASTRO("TarefaCadastro"),
	TAREFA_DIALOG_CONTATO("TarefaDialogContato"),
	TAREFA_DIALOG_PROPOSTA("TarefaDialogProposta"),
	TROCA_SENHA("TrocaSenha");
	private String localizacao;
	private FXMLEnum(String localizacao) {
		this.localizacao=localizacao;
	}
	public URL getLocalizacao() {
		return getClass().getResource("/fxml/"+localizacao+".fxml");
	}
}
