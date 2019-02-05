package br.com.tiagods.config.init;

public class VersaoSistema {
	private final String nome="Controle de Processos";
	private final String versao="2.0.10";
	private final String data="31/01/2019";
	private final String versaoBanco="1.1.3";
	private final String detalhes="Versao 2.1";
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
