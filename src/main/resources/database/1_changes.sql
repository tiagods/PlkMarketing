alter table usuario add column telefone varchar;
alter table usuario add column celular varchar;
alter table usuario add column email varchar;
alter table usuario add column site varchar;
alter table usuario add column cep varchar;
alter table usuario add column endereco varchar;
alter table usuario add column numero varchar(50);
alter table usuario add column bairro varchar;
alter table usuario add column complemento varchar(50);
alter table usuario add column cidade_id integer;
alter table usuario add column estado varchar(2);
alter table usuario add column data_criacao timestamp;
alter table usuario add column criado_por_id integer;
alter table usuario add column rg varchar(50);
alter table usuario add column cpf varchar(50);
alter table usuario add column aniversario varchar(10);

alter table usuario rename column USU_COD to id;
alter table usuario rename column USU_NOME to nome;
alter table usuario rename column USU_LOGIN to login;
alter table usuario rename column USU_SENHA to senha;
alter table usuario rename column USU_ULTIMOACESSO to ultimo_acesso;
alter table usuario rename column USU_DEPARTAMENTO_COD to departamento_id;
alter table usuario rename column USU_TOTALVENDAS to total_vendas;
alter table usuario rename column USU_ATIVO to ativo;
alter table usuario rename column usu_funcao_cod to funcao_id;

alter table tarefa add column contato_id integer;
alter table tarefa add column proposta_id integer;
alter table tarefa add column tarefa_type varchar(50);

alter table tarefa add column tipo varchar;
update tarefa set tipo = 'VISITA' where tar_tip_tar_cod=1;
update tarefa set tipo = 'REUNIAO' where tar_tip_tar_cod=2;
update tarefa set tipo = 'PROPOSTA' where tar_tip_tar_cod=3;
update tarefa set tipo = 'TELEFONE' where tar_tip_tar_cod=4;
update tarefa set tipo = 'EMAIL' where tar_tip_tar_cod=5;
update tarefa set tipo = 'WHATSAPP' where tar_tip_tar_cod=6;

update tarefa set tarefa_type='contato' where tar_classe != 'Negocio';
update tarefa set tarefa_type='proposta' where tar_classe = 'Negocio';

create table contato(
	id serial,
	rg varchar(50),
	cpf varchar(50),
	aniversario varchar(50),
	razao varchar,
	cnpj varchar,
	im varchar,
	ie varchar,
	responsavel varchar,
	apelido varchar,
	pessoa_tipo varchar,
	contato_tipo varchar,
	ultimo_negocio_id integer,
	origem_id integer,
	atendente_id integer,
	detalhes_origem text,
	resumo text,
	apresentacao text,
	servico_id integer,
	categoria_id integer,
	nivel_id integer,
	material boolean,
	convite boolean,
	newsletter boolean,
	mala_direta_id integer;
	nome varchar,
	telefone varchar,
	celular varchar,
	email varchar,
	site varchar,
	cep varchar,
	endereco varchar,
	numero varchar,
	bairro varchar,
	complemento varchar,
	cidade_id integer,
	estado varchar(2),
	data_criacao timestamp,
	criado_por_id integer,
	tabela_anterior varchar,
	codigo_anterior integer,
	primary key(id)
);

alter table lista rename column LIS_COD to id;
alter table lista rename column LIS_NOME to nome;
alter table lista rename column LIS_DETALHES to detalhes;
alter table lista rename column LIS_CRIADOEM to criado_em;
alter table lista rename column LIS_CRIADOPOR_COD to criado_por_id;

create table contato_lista(
  contato_id integer NOT NULL,
  lista_id integer NOT NULL,
  CONSTRAINT contato_lista_pkey PRIMARY KEY (contato_id, lista_id),
  CONSTRAINT contato_lista_fk FOREIGN KEY (contato_id)
      REFERENCES contato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT lista_contato_fk FOREIGN KEY (lista_id)
      REFERENCES lista (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter table negocio add column contato_id integer;
alter table negocio add column tipo_etapa varchar;
alter table negocio add column tipo_status varchar;

alter table empresa add column departamento varchar;
alter table empresa add column estado varchar(2);
alter table empresa add column ie varchar;
alter table empresa add column im varchar;
alter table empresa add column responsavel varchar;
alter table empresa add column apelido  varchar;

alter table pessoa add column departamento varchar;
alter table pessoa add column estado varchar(2);
alter table pessoa add column rg varchar;
alter table pessoa add column cpf varchar;

alter table prospeccao add column razao varchar;
alter table prospeccao add column cnpj varchar;
alter table prospeccao add column ie varchar;
alter table prospeccao add column im  varchar;
alter table prospeccao add column apelido  varchar;

update negocio set tipo_etapa = 'CONTATO' where neg_eta_cod=1;
update negocio set tipo_etapa = 'PROPOSTA' where neg_eta_cod=2;
update negocio set tipo_etapa = 'FOLLOWUP' where neg_eta_cod=3;
update negocio set tipo_etapa = 'FECHAMENTO' where neg_eta_cod=4;
update negocio set tipo_etapa = 'INDEFINIDA' where neg_eta_cod=5;

update negocio set tipo_status = 'ANDAMENTO' where neg_sta_cod=1;
update negocio set tipo_status = 'GANHO' where neg_sta_cod=2;
update negocio set tipo_status = 'PERDIDO' where neg_sta_cod=3;
update negocio set tipo_status = 'SEMMOVIMENTO' where neg_sta_cod=4;

update negocio set neg_motivoperda='INDEFINIDO' where tipo_status='PERDIDO';

create table register_app(
	id serial,
	nome varchar,
	primary key(id)
);

create table franquia(
	id serial,
	ativo boolean,
	criado_em timestamp,
	last_update timestamp,
	nome varchar,
	tipo varchar,
	primary key(id)
);
create table franquia_pacote(
	id serial,
	nome varchar,
	base_icms decimal(19,2),
	criado_em timestamp,
	custo decimal(19,2),
	faturamento decimal(19,2),
	icms decimal(19,2),
	investimento decimal(19,2),
	last_update timestamp,
	previsao varchar,
	pro_labore decimal(19,2),
	franquia_id integer,
	primary key(id)
);

insert into contato (rg,cpf,aniversario,razao,cnpj,im,ie,responsavel,pessoa_tipo,origem_id,
atendente_id,detalhes_origem,resumo,apresentacao,servico_id,categoria_id,nivel_id,nome,
telefone,celular,email,site,cep,endereco,numero,bairro,complemento,cidade_id,estado,data_criacao,
criado_por_id,contato_tipo,apelido,material,convite,newsletter,mala_direta_id,tabela_anterior,codigo_anterior)
select '','','',emp_razao_cod,emp_cnpj,'','',responsavel,'EMPRESA',emp_origem_cod,
emp_atendente_cod, '','','',emp_servico_cod,emp_categoria_cod,emp_nivel_cod,emp_nome,
emp_telefone,emp_celular,emp_email,emp_site,emp_end_cep,emp_end_nome,
emp_end_numero,emp_end_bairro,emp_end_complemento,emp_cidade_cod,estado,emp_criadoem,
emp_criadopor_cod,'SONDAGEM',apelido,false,false,false,null,'EMPRESA', emp_cod from empresa;

insert into contato (rg,cpf,aniversario,razao,cnpj,im,ie,responsavel,pessoa_tipo,origem_id,
atendente_id,detalhes_origem,resumo,apresentacao,servico_id,categoria_id,nivel_id,nome,
telefone,celular,email,site,cep,endereco,numero,bairro,complemento,cidade_id,estado,data_criacao,
criado_por_id,contato_tipo,apelido,material,convite,newsletter,mala_direta_id,tabela_anterior,codigo_anterior)
select rg,cpf,pes_nasc,'','','','','','PESSOA',pes_origem_cod,
pes_atendente_cod, '','','',pes_servico_cod,pes_categoria_cod,pes_nivel_cod,pes_nome,
pes_telefone,pes_celular,pes_email,pes_site,pes_end_cep,pes_end_nome,
pes_end_numero,pes_end_bairro,pes_end_complemento,pes_cidade_cod,estado,pes_criadoem,
pes_criadopor_cod,'SONDAGEM','',false,false,false,null,'PESSOA', pes_cod from pessoa;

insert into contato (rg,cpf,aniversario,razao,cnpj,im,ie,responsavel,pessoa_tipo,origem_id,
atendente_id,detalhes_origem,resumo,apresentacao,servico_id,categoria_id,nivel_id,nome,
telefone,celular,email,site,cep,endereco,numero,bairro,complemento,cidade_id,estado,data_criacao,
criado_por_id,contato_tipo,apelido,material,convite,newsletter,mala_direta_id,tabela_anterior,codigo_anterior)
select '','','',razao,cnpj,im,ie,pro_responsavel,'EMPRESA',pro_origem_cod,
pro_atendente_cod, pro_origem_detalhes,pro_resumo,pro_apresentacao,pro_servico_cod,null,null,pro_nome,
pro_telefone,pro_celular,pro_email,pro_site,'',pro_endereco,'','','',null,'',pro_criadoem,
pro_criadopor_cod,'PROSPECCAO',apelido,
	(CASE WHEN pro_material=1 THEN true WHEN pro_material=0 THEN false end),
	(CASE WHEN pro_convite_eventos=1 THEN true ELSE false end),
	(CASE WHEN pro_newsletter=1 THEN true ELSE false end),pro_tipo_contato,'PROSPECCAO', pro_cod from prospeccao;

insert into contato_lista (contato_id,lista_id) 
select (select id from contato where tabela_anterior='PROSPECCAO' and codigo_anterior=pro_rel_cod),lis_rel_cod from prospeccao_rel_lista;

update negocio set contato_id = 
(select id from contato where tabela_anterior='EMPRESA' and codigo_anterior=neg_empresa_cod)
where neg_empresa_cod is not null;

update negocio set contato_id = 
(select id from contato where tabela_anterior='PESSOA' and codigo_anterior=neg_pessoa_cod)
where neg_pessoa_cod is not null;

update negocio set contato_id = 
(select id from contato where tabela_anterior='PROSPECCAO' and codigo_anterior=neg_prospeccao_cod)
where neg_prospeccao_cod is not null;

update contato set estado=(select cid_estado from cidade where cid_cod=cidade_id) where cidade_id is not null;
update contato set estado='SP', cidade_id=3831 where cidade_id is null;

update tarefa set proposta_id=tar_negocio_cod where tar_classe = 'Negocio';

update tarefa set contato_id = 
(select id from contato where tabela_anterior='EMPRESA' and codigo_anterior=tar_empresa_cod)
where tar_empresa_cod is not null and tar_classe='Empresa';

update tarefa set contato_id = 
(select id from contato where tabela_anterior='PESSOA' and codigo_anterior=tar_pessoa_cod)
where tar_pessoa_cod is not null and tar_classe='Pessoa';

update tarefa set contato_id = 
(select id from contato where tabela_anterior='PROSPECCAO' and codigo_anterior=tar_prospeccao_cod)
where tar_prospeccao_cod is not null and tar_classe='Prospeccao';
