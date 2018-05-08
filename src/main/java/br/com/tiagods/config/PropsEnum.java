package br.com.tiagods.config;

public enum PropsEnum {
	FTP("/credentials/ftp.properties"),
	MAIL("/credentials/mail.properties"),
	DB("/credentials/database_other.properties");
	private String descricao;
	private PropsEnum(String descricao) {
		this.descricao=descricao;
	}
	public String getDescricao() {
		return descricao;
	}

}
