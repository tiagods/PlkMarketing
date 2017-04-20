package br.com.tiagods.model;

public class VersaoSistema {
	private String nome="Negócios";
	private String versao="1.0.13";//versao 1.0.13
	private String data="19.04.2017";
	private String versaoBanco="1.0.2";
	private String detalhes="Corrigido problema de desempenho\nMelhoria no Filtro de Pesquisa na tela Negocios\nIncluindo data de finalização do Negócio\nAjustes para Trabalhar com Alertas via E-Mail";
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
