create table log_registro (
    id serial,
    tipo varchar(100),
    titulo varchar,
    descricao text,
    data timestamp,
    primary key(id)
)
alter table usuario add column senha_resetada boolean default false;
update usuario set senha_resetada=false;