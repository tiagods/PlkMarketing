package br.com.tiagods.model;

public class DescricaoVersao {
	private String nome="Negócios";
	private String versao="1.0.2";
	private String data="22.02.2017";
	private String versaoBanco="1.0";
	private String detalhes="Inclusão de Tela de Login para operadores, \nInclusão de senha com criptografia\nOtimização da Tela de Novos Negocios";
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
