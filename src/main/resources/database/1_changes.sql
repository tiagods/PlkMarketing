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
	tipo varchar,
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
	primary key(id)
)

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
)

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

update negocio set neg_motivoperda='INDEFINIDO' where tipo_status='PERDIDO'

