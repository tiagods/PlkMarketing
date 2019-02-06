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
	status boolean,
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

