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
	finalizado boolean,
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
	finalizado boolean,
	finalizado_em date,
	primary key(id)
);

alter table imp_processo add constraint fk_cliente_id foreign key (cliente_id) references cliente(COD);

alter table imp_processo add constraint fk_criado_por_id foreign key (criado_por_id) references usuario(id);

ALTER TABLE imp_processo OWNER TO prolink;

create table imp_pro_etapa(
	id serial,
	etapa varchar,
	departamento_id integer,
	tempo integer,
	descricao varchar,
	finalizado boolean,
	atividade_id integer,
	processo_id integer,
	criado_em timestamp,
    criado_por_id integer,
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
