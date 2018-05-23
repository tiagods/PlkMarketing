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
alter table tarefa rename column

