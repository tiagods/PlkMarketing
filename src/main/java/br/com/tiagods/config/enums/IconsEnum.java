package br.com.tiagods.config.enums;

import java.net.URL;

public enum IconsEnum {
	BUTTON_EDIT("button_edit"),
	BUTTON_REMOVE("button_trash"),
	BUTTON_PROPOSTA("button_negocios"),
	BUTTON_CONTATO("button_empresas"),
	BUTTON_OK("button_ok"),
	BUTTON_SEARCH("button_search");
	
	private String localizacao;
	private IconsEnum(String localizacao) {
		this.localizacao=localizacao;
	}
	public URL getLocalizacao() {
		return getClass().getResource("/fxml/imagens/"+localizacao+".png");
	}
}
