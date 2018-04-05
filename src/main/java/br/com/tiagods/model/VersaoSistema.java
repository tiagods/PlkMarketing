package br.com.tiagods.model;

public class VersaoSistema {
	private String nome="Negocios";
	private String versao="1.0.43";
	private String data="05/04/2017";
	private String versaoBanco="1.0.6";
	private String detalhes="Liberando acesso remoto";
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
