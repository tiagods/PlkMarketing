package br.com.tiagods.util.storage;

public enum PathStorageEnum {
	NEGOCIO_DOCUMENTO("negocio"),
	TAREFA_DOCUMENTO("tarefa");
	private String descricao;
	PathStorageEnum(String descricao) {
		this.descricao=descricao;
	}
	public String getDescricao() {
		return descricao;
	}
}
