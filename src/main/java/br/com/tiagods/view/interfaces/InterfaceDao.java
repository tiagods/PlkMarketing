package br.com.tiagods.view.interfaces;

import java.util.List;

import org.hibernate.Session;

public interface InterfaceDao {
	public boolean salvar(Object classe,Session session);
	public boolean excluir(Object object,Session session);
	@SuppressWarnings("rawtypes")
	public List listar(Class classe, Session session);
	@SuppressWarnings("rawtypes")
	public Object receberObjeto(Class classe, int id,Session session);
}
