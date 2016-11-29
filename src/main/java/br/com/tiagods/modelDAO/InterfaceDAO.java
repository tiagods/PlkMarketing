package br.com.tiagods.modelDAO;

import java.util.List;

import org.hibernate.Session;

public interface InterfaceDAO {
	public boolean salvar(Object classe,Session session);
	public boolean excluir(Object object,Session session);
	public List listar(Object object, Session session);
	public Object receberObjeto(Class classe, int id,Session session);
}
