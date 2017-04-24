package br.com.tiagods.model;

public class VersaoSistema {
	private String nome="Negócios";
	private String versao="1.0.14";
	private String data="24.04.2017";
	private String versaoBanco="1.0.2";
	private String detalhes="Habilitando Notificações\nCorrigido problema de desempenho\nRemovendo bug no painel Tarefas";
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
