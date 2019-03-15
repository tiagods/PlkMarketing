create table imp_atividade(
	id serial,
    nome varchar,
    descricao text,
    primary key(id)
);

create table imp_pacote(
    id serial,
    nome varchar,
    descricao text,
    criado_em timestamp,
    criado_por_id integer,
    primary key(id)
);
alter table imp_pacote add constraint fk_criado_por_id foreign key (criado_por_id) references usuario(id);

create table imp_pac_etapa(
	id serial,
	etapa varchar,
	departamento_id integer,
	tempo integer,
	descricao varchar,
	atividade_id integer,
	pacote_id integer,
	criado_em timestamp,
    criado_por_id integer,
	primary key(id)
);

alter table imp_pac_etapa add constraint fk_departamento_id foreign key (departamento_id) references USUARIO_DEPARTAMENTO(USU_DEP_COD);
alter table imp_pac_etapa add constraint fk_atividade_id foreign key (atividade_id) references imp_atividade(id);
alter table imp_pac_etapa add constraint fk_pacote_id foreign key (pacote_id) references imp_pacote(id);


ALTER TABLE imp_atividade OWNER TO prolink;
ALTER TABLE imp_pacote OWNER TO prolink;
ALTER TABLE imp_pac_etapa OWNER TO prolink;

create table imp_processo(
	id serial,
	cliente_id integer,
	criado_por_id integer,
	criado_em timestamp,
	pacote_id integer,
	status varchar,
	finalizado boolean,
	finalizado_em date,
	primary key(id)
);

alter table imp_processo add constraint fk_cliente_id foreign key (cliente_id) references cliente(COD);

alter table imp_processo add constraint fk_criado_por_id foreign key (criado_por_id) references usuario(id);

alter table imp_processo add constraint fk_pacote_id foreign key (pacote_id) references imp_pacote(id);

ALTER TABLE imp_processo OWNER TO prolink;

create table imp_pro_etapa(
	id serial,
	etapa varchar,
	departamento_id integer,
	tempo integer,
	descricao varchar,
	finalizado boolean,
	data_liberacao date,
	data_atualizacao date,
	atividade_id integer,
	processo_id integer,
	criado_em timestamp,
    criado_por_id integer,
    status varchar,
	primary key(id)
);

alter table imp_pro_etapa add constraint fk_departamento_id foreign key (departamento_id) references USUARIO_DEPARTAMENTO(USU_DEP_COD);
alter table imp_pro_etapa add constraint fk_atividade_id foreign key (atividade_id) references imp_atividade(id);
alter table imp_pro_etapa add constraint fk_processo_id foreign key (processo_id) references imp_processo(id);

ALTER TABLE imp_pro_etapa OWNER TO prolink;

create table imp_pro_eta_status(
	id serial,
	criado_por_id integer,
	criado_em timestamp,
	finalizado boolean,
	processo_etapa_id integer,
	descricao text,
	primary key(id)
);

alter table imp_pro_eta_status add constraint fk_criado_por_id foreign key (criado_por_id) references usuario(id);
alter table imp_pro_eta_status add constraint fk_processo_etapa_id foreign key (processo_etapa_id) references imp_pro_etapa(id);

ALTER TABLE imp_pro_eta_status OWNER TO prolink;

ALTER TABLE usuario_departamento add column email text;

-- Copiando dados para a tabela public.imp_atividade: 0 rows
/*!40000 ALTER TABLE "imp_atividade" DISABLE KEYS */;
INSERT INTO "imp_atividade" ("id", "nome", "descricao") VALUES
	(1, E'Ato Constitutivo', E''),
	(2, E'Documentos dos Socios', E''),
	(3, E'Comprovantes dos Socios', E''),
	(4, E'Pis Socio e/ou Administrativo', E''),
	(5, E'Habilitação NF-e PMSP', E''),
	(6, E'Senha Posto Fiscal', E''),
	(7, E'Senha Simples Nacional', E''),
	(8, E'Perfil Fiscal Prefeitura', E''),
	(9, E'Balanço e DRE', E''),
	(10, E'Contas Patrimoniais', E''),
	(11, E'Plano de Contas', E''),
	(12, E'Balancete', E''),
	(13, E'Folha de Pagamento', E''),
	(14, E'Ficha de Funcionários', E''),
	(15, E'CAGED', E''),
	(16, E'Recisões', E''),
	(17, E'Recibos de Férias', E''),
	(18, E'Afastamentos', E''),
	(19, E'Senha Receita Federal', E''),
	(20, E'Termo de Responsabilidade Tecnica', E''),
	(21, E'Senha do INSS', E'');

-- Copiando dados para a tabela public.imp_pacote: 1 rows
/*!40000 ALTER TABLE "imp_pacote" DISABLE KEYS */;
INSERT INTO "imp_pacote" ("id", "nome", "descricao", "criado_em", "criado_por_id") VALUES
	(1, E'Padrão', E' ', E'2019-02-23 12:43:50.542', 1);
/*!40000 ALTER TABLE "imp_pacote" ENABLE KEYS */;

-- Copiando dados para a tabela public.imp_pac_etapa: 63 rows
/*!40000 ALTER TABLE "imp_pac_etapa" DISABLE KEYS */;
INSERT INTO "imp_pac_etapa" ("etapa", "departamento_id", "tempo", "descricao", "atividade_id", "pacote_id", "criado_em", "criado_por_id") VALUES
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 2, 1, E'2019-02-23 13:19:49.062', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 1, 1, E'2019-02-23 13:18:01.532', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 3, 1, E'2019-03-12 14:35:47.396', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 5, 1, E'2019-03-12 15:03:47.901', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 5, 1, E'2019-03-12 15:03:47.902', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 4, 1, E'2019-03-12 15:03:47.904', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 4, 1, E'2019-03-12 15:03:47.905', 1),
	(E'TERCEIRA', 5, 15, E'Validar Documento', 4, 1, E'2019-03-12 15:03:47.907', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 3, 1, E'2019-03-12 14:35:47.408', 1),
	(E'TERCEIRA', 5, 15, E'Validar Documento', 3, 1, E'2019-03-12 14:35:47.41', 1),
	(E'TERCEIRA', 5, 15, E'Validar Documento', 2, 1, E'2019-03-12 14:35:47.411', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 1, 1, E'2019-02-23 13:18:01.519', 1),
	(E'TERCEIRA', 5, 15, E'Validar Documento', 1, 1, E'2019-02-23 13:18:01.534', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 2, 1, E'2019-03-03 15:54:59.838', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 6, 1, E'2019-03-12 15:06:34.177', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 6, 1, E'2019-03-12 15:06:34.178', 1),
	(E'TERCEIRA', 5, 15, E'Validar Documento', 6, 1, E'2019-03-12 15:06:34.179', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 7, 1, E'2019-03-12 15:08:14.697', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 7, 1, E'2019-03-12 15:08:14.698', 1),
	(E'TERCEIRA', 5, 15, E'Validar Documento', 7, 1, E'2019-03-12 15:08:14.699', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 8, 1, E'2019-03-12 15:18:08.76', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 8, 1, E'2019-03-12 15:18:08.763', 1),
	(E'TERCEIRA', 5, 15, E'Validar Documento', 8, 1, E'2019-03-12 15:18:08.764', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 10, 1, E'2019-03-12 15:20:40.163', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 9, 1, E'2019-03-12 15:20:40.165', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 9, 1, E'2019-03-12 15:20:40.166', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 10, 1, E'2019-03-12 15:20:40.167', 1),
	(E'TERCEIRA', 4, 15, E'Validar Documento', 10, 1, E'2019-03-12 15:20:40.168', 1),
	(E'TERCEIRA', 4, 15, E'Validar Documento', 9, 1, E'2019-03-12 15:20:40.169', 1),
	(E'TERCEIRA', 5, 15, E'Validar Documento', 5, 1, E'2019-03-12 15:26:50.89', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 11, 1, E'2019-03-12 15:38:07.824', 1),
	(E'TERCEIRA', 4, 15, E'Validar Documento', 11, 1, E'2019-03-12 15:38:07.826', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 11, 1, E'2019-03-12 15:38:07.828', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 12, 1, E'2019-03-12 15:46:28.815', 1),
	(E'TERCEIRA', 4, 15, E'Validar Documento', 12, 1, E'2019-03-12 15:46:28.817', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 12, 1, E'2019-03-12 15:46:28.818', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 14, 1, E'2019-03-12 16:44:12.733', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 13, 1, E'2019-03-12 16:44:12.736', 1),
	(E'TERCEIRA', 8, 15, E'Validar Documento', 13, 1, E'2019-03-12 16:44:12.738', 1),
	(E'TERCEIRA', 8, 15, E'Validar Documento', 14, 1, E'2019-03-12 16:44:12.739', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 13, 1, E'2019-03-12 16:44:12.741', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 14, 1, E'2019-03-12 16:44:12.742', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 15, 1, E'2019-03-12 16:45:12.69', 1),
	(E'TERCEIRA', 8, 15, E'Validar Documento', 15, 1, E'2019-03-12 16:45:12.691', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 15, 1, E'2019-03-12 16:45:12.693', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 16, 1, E'2019-03-12 16:47:13.587', 1),
	(E'TERCEIRA', 8, 15, E'Validar Documento', 16, 1, E'2019-03-12 16:47:13.588', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 16, 1, E'2019-03-12 16:47:13.589', 1),
	(E'TERCEIRA', 8, 15, E'Validar Documento', 17, 1, E'2019-03-12 16:48:17.616', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 17, 1, E'2019-03-12 16:48:17.617', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 17, 1, E'2019-03-12 16:48:17.618', 1),
	(E'TERCEIRA', 8, 15, E'Validar Documento', 18, 1, E'2019-03-12 16:52:20.163', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 18, 1, E'2019-03-12 16:52:20.164', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 18, 1, E'2019-03-12 16:52:20.165', 1),
	(E'TERCEIRA', 9, 15, E'Validar Documento', 19, 1, E'2019-03-12 16:54:00.092', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 19, 1, E'2019-03-12 16:54:00.093', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 19, 1, E'2019-03-12 16:54:00.095', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 20, 1, E'2019-03-12 16:56:04.799', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 20, 1, E'2019-03-12 16:56:04.8', 1),
	(E'TERCEIRA', 9, 15, E'Validar Documento', 20, 1, E'2019-03-12 16:57:01.106', 1),
	(E'PRIMEIRA', 2, 15, E'Solicitar Documento', 21, 1, E'2019-03-12 16:57:01.108', 1),
	(E'TERCEIRA', 9, 15, E'Validar Documento', 21, 1, E'2019-03-12 16:59:33.304', 1),
	(E'SEGUNDA', 2, 15, E'Receber Documento', 21, 1, E'2019-03-12 16:59:33.307', 1);