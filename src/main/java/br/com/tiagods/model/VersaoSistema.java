package br.com.tiagods.model;

public class VersaoSistema {
	private String nome="Negócios";
	private String versao="1.0.24";
	private String data="04/08/2017";
	private String versaoBanco="1.0.3";
	private String detalhes="Permitindo alteração do nome em Negocios";
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
