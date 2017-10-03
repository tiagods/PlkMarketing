package br.com.tiagods.model;

public class VersaoSistema {
	private String nome="Negócios";
	private String versao="1.0.35";
	private String data="03/10/2017";
	private String versaoBanco="1.0.4";
	private String detalhes=
			"Possibilidade de tabalhar com lotes de tarefas - Empresas, Pessoas e Prospeccao";
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
