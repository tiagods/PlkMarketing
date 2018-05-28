package br.com.tiagods.config.enums;

import java.net.URL;

public enum IconsEnum {
	EDIT("button_edit"),
	REMOVE("button_trash"),
	PROPOSTA("button_negocios"),
	CONTATO("button_empresas"),
	SELECIONAR("button_ok");
	
	private String localizacao;
	private IconsEnum(String localizacao) {
		this.localizacao=localizacao;
	}
	public URL getLocalizacao() {
		return getClass().getResource("/fxml/imagens/"+localizacao+".png");
	}
}
