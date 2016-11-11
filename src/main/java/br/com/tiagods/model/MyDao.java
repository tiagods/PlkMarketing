package br.com.tiagods.model;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import br.com.tiagods.view.interfaces.InterfaceDAO;
import static br.com.tiagods.view.MenuView.jDBody;

public class MyDao implements InterfaceDAO {

	@Override
	public boolean salvar(Object classe, Session session) {
		// TODO Auto-generated method stub
		try{
			session.saveOrUpdate(classe);
			session.getTransaction().commit();
			session.clear();
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(jDBody,
					"Não foi possivel salvar os dados da tabela "+classe+"\n"+e.getMessage(),
					"Erro ao salvar!",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}

	@Override
	public boolean excluir(Session session) {
		// TODO Auto-generated method stub
		return false;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listar(String classe, Session session) {
		// TODO Auto-generated method stub
		if(session.getTransaction().getStatus()==TransactionStatus.NOT_ACTIVE)
			session.beginTransaction();
		List lista = session.createQuery("from "+classe).getResultList();
		session.clear();
		return lista;
	}

	@Override
	public Object receberObjeto(String classe, int id, Session session) {
		if(session.getTransaction().getStatus()==TransactionStatus.NOT_ACTIVE)
			session.beginTransaction();
		return session.get(classe, id);
	}


}
