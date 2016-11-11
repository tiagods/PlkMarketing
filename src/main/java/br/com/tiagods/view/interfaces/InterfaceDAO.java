package br.com.tiagods.view.interfaces;

import java.util.List;

import org.hibernate.Session;

public interface InterfaceDAO {
	public boolean salvar(Object classe,Session session);
	public boolean excluir(Session session);
	public List listar(String classe, Session session);
	public Object receberObjeto(String classe, int id,Session session);
}
