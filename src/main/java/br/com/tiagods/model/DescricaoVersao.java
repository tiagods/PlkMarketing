package br.com.tiagods.model;

public class DescricaoVersao {
	private String nome="Negócios";
	private String versao="1.0.7";
	private String data="14.03.2017";
	private String versaoBanco="1.0";
	private String detalhes="Inclusão de Tabelas na Tela Inicial\nNovos icones dentro da tabela Negocios\nMelhorado a visualização da Planilha Exportada";
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
