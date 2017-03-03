package br.com.tiagods.model;

public class DescricaoVersao {
	private String nome="Negócios";
	private String versao="1.0.6";
	private String data="03.03.2017";
	private String versaoBanco="1.0";
	private String detalhes="Inclusão do exportador para excel\nInclusão de Tela de Login para operadores, \nInclusão de senha com criptografia";
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
