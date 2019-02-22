package br.com.tiagods.config.enums;

import javafx.scene.control.Tooltip;

import java.net.URL;

public enum IconsEnum {
	BUTTON_ADD("button_add","Novo Registro"),
	BUTTON_DEVOLVER("button_advance","Devolver"),
	BUTTON_DEADLINE("button_deadline","Aguardando"),
	BUTTON_DOWNLOAD("button_download","Baixar"),
	BUTTON_EDIT("button_edit","Editar Registro"),
	BUTTON_REMOVE("button_trash","Excluir"),
	BUTTON_RETURN("button_return","Voltar"),
	BUTTON_PROPOSTA("button_negocios","Negocio"),
	BUTTON_EMPRESA("button_empresas","Empresa"),
	BUTTON_MAIL("button_mail","E-Mail"),
	BUTTON_CLIP("button_clip","Visualizar Formulario"),
	BUTTON_OK("button_ok","OK"),
	BUTTON_CONTATO("button_people","Abrir Contato"),
	BUTTON_PARTILHAR("button_partilhar","Abrir Partilha"),
	BUTTON_RETUITAR("button_retuitar","Redirecionar"),
	BUTTON_SEARCH("button_search","Buscar"),
	BUTTON_TAREFA_EMAIL("tarefas_email","E-Mail"),
	BUTTON_TAREFA_FONE("tarefas_fone","Telefone"),
	BUTTON_TAREFA_PROPOSTA("tarefas_proposta","Abrir Proposta"),
	BUTTON_TAREFA_REUNIAO("tarefas_reuniao","Reunião"),
	BUTTON_TAREFA_WHATSAPP("tarefas_whatsapp","WhatsApp"),
	BUTTON_NEGOCIO_CONTATO("negocio_fone","Contato"),
	BUTTON_NEGOCIO_PROPOSTA("negocio_proposta","Envio de Proposta"),
	BUTTON_NEGOCIO_FOLLOWUP("negocio_followup","Follow-Up"),
	BUTTON_NEGOCIO_FECHAMENTO("negocio_fechamento","Fechamento"),
	BUTTON_NEGOCIO_INDEFINIDA("negocio_question","Indefinida"),
	BUTTON_VIEW("button_view","Consultar"),
	BUTTON_UP("button_up","UP"),
	MENU_CHECKLIST("menu_checklist","Abrir CheckList"),
	MENU_CONTATO("button_people","Abrir Contatos"),
	MENU_FRANQUIA("menu_franquia","Abrir Franquias"),
	MENU_NEGOCIO("menu_negocios","Abrir Negocios"),
	MENU_TAREFA("menu_task","Abrir Tarefas"),
	MENU_USUARIO("menu_user","Abrir Usuarios");
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
