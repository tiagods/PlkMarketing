package br.com.tiagods.config.enums;

import javafx.scene.control.Tooltip;

import java.net.URL;

public enum IconsEnum {
	BUTTON_ADD("button_add","Novo Registro"),
	BUTTON_EDIT("button_edit","Editar Registro"),
	BUTTON_REMOVE("button_trash","Excluir"),
	BUTTON_PROPOSTA("button_negocios","Negocio"),
	BUTTON_EMPRESA("button_empresas","Empresa"),
	BUTTON_MAIL("button_mail","E-Mail"),
	BUTTON_CLIP("button_clip","Visualizar Formulario"),
	BUTTON_OK("button_ok","OK"),
	BUTTON_CONTATO("button_people","Abrir Contato"),
	BUTTON_SEARCH("button_search","Buscar"),
	BUTTON_TAREFA_EMAIL("tarefas_email","E-Mail"),
	BUTTON_TAREFA_FONE("tarefas_fone","Telefone"),
	BUTTON_TAREFA_PROPOSTA("tarefas_proposta","Abrir Proposta"),
	BUTTON_TAREFA_REUNIAO("tarefas_reuniao","Reunião"),
	BUTTON_TAREFA_WHATSAPP("tarefas_whatsapp","WhatsApp");
	
	private String localizacao;
	private String tooltip;

	IconsEnum(String localizacao, String tooltip) {
		this.localizacao=localizacao;
		this.tooltip=tooltip;
	}
	public URL getLocalizacao() {
		return getClass().getResource("/fxml/imagens/"+localizacao+".png");
	}

	public Tooltip getTooltip() {
		return new Tooltip(this.tooltip);
	}
}
