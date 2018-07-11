select tar_cod,'' AS mes ,tar_dataevento,tipo,
(case when tar_finalizado=1 then 'Encerrado' when tar_finalizado=0 then 'Pendente' end) as Status,
tar_nome, (
	CASE WHEN tarefa_type='contato' then 
	(select nome from contato where id = contato_id)
	WHEN tarefa_type='proposta' then
	(select neg_nome from negocio where neg_cod = proposta_id)
	END
) as nome, tarefa_type as origem from tarefa where tar_atendente_cod = 9