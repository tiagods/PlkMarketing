package br.com.tiagods.modeldao;

import java.util.List;

import org.hibernate.Session;

public interface InterfaceDao {
	public boolean salvar(Object classe,Session session);
	public boolean excluir(Object object,Session session);
	public List listar(Class classe, Session session);
	public Object receberObjeto(Class classe, int id,Session session);
}
