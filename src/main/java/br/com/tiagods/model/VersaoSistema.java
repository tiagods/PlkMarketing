package br.com.tiagods.model;

public class VersaoSistema {
	private String nome="Negócios";
	private String versao="1.0.9";
	private String data="20.03.2017";
	private String versaoBanco="1.0";
	private String detalhes="Corrigido bug que criava dois Negocios ao cadastrar Perda\n Inclusão de Filtro na Tela inicial com Tabelas de Contadores\nNovos icones dentro da tabela Negocios\nMelhorado a visualização da Planilha Exportada";
	/*
	 */
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @return the versao
	 */
	public String getVersao() {
		return versao;
	}
	
	public String getDate(){
		return data;
	}
	/**
	 * @return the versaoBanco
	 */
	public String getVersaoBanco() {
		return versaoBanco;
	}
	public String getDetalhes(){
		return this.detalhes;
	}
}
