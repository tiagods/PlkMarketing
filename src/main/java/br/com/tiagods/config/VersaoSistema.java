package br.com.tiagods.config;

public class VersaoSistema {
	private final String nome="Negócios";
	private final String versao="2.0.0";
	private final String data="26/06/2018";
	private final String versaoBanco="1.1.0";
	private final String detalhes="Versao 2.0";
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
