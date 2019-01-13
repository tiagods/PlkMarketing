alter table negocio drop column neg_sta_cod;
alter table negocio drop column neg_eta_cod;
alter table negocio drop column neg_prospeccao_cod;
alter table negocio drop column neg_empresa_cod;
alter table negocio drop column neg_pessoa_cod;

alter table tarefa drop column tar_pessoa_cod;
alter table tarefa drop column tar_empresa_cod;
alter table tarefa drop column tar_negocio_cod;
alter table tarefa drop column tar_prospeccao_cod;
alter table tarefa drop column TAR_CLASSE;

alter table prospeccao_rel_lista drop column pro_rel_cod;

drop table prospeccao_rel_lista;
drop table empresa;
drop table pessoa;
drop table prospeccao;
drop table NEGOCIO_STATUS;
drop table NEGOCIO_ETAPA;