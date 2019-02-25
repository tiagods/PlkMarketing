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
/*!40000 ALTER TABLE "imp_atividade" ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
