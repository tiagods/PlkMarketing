package br.com.tiagods.modelDAO;

import java.util.List;

import org.hibernate.Session;

public class PessoaDAO implements InterfaceDAO {

	@Override
	public boolean salvar(Object classe, Session session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(Session session, int id) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public List listar(String classe, Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		// TODO Auto-generated method stub
		return null;
	}

}
