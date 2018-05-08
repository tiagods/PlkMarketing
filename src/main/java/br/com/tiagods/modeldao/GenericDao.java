package br.com.tiagods.modeldao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.UsuarioLog;
import br.com.tiagods.view.interfaces.InterfaceDao;

public class GenericDao implements InterfaceDao{

	@Override
	public boolean salvar(Object classe, Session session) {
		try{
			session.saveOrUpdate(classe);
			session.getTransaction().commit();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao salvar o registro!\nEntre em contato com o administrador do sistema:\nInforme o erro:"+e);
			return false;
		}
	}
	
	@Override
	public boolean excluir(Object object, Session session) {
		try{
			session.delete(object);
			session.getTransaction().commit();
			return true;
		}catch (HibernateException e) {
			session.getTransaction().rollback();
			JOptionPane.showMessageDialog(null, "Erro ao excluir o registro!\nEntre em contato com o administrador do sistema:\nInforme o erro:"+e);
			//relatarErro(object.getClass().getSimpleName(),"Excluir",e.getMessage());
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List listar(Class classe, Session session) {
		return session.createQuery("from "+classe.getSimpleName()).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		try{
			return session.get(classe, id);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Erro ao carregar o registro!\nEntre em contato com o administrador do sistema:\nInforme o erro:"+e);
			return null;
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public Object receberObjeto(Class classe, Criterion[] criterions, Session session){
		Criteria criteria = session.createCriteria(classe);
		for(Criterion c : criterions)
			criteria.add(c);
		return criteria.uniqueResult();
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public List items(Class classe, Session session, List<Criterion> criterion, Order order){
		Criteria criteria = session.createCriteria(classe);
		if(!criterion.isEmpty()){
			for(Criterion c : criterion)
				criteria.add(c);
		}
		criteria.addOrder(order);
		return criteria.list();
	}
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public List items(Class classe, Session session, List<Criterion> criterion, String[] alias, Conjunction conjunction, Order order){
		Criteria criteria = session.createCriteria(classe).createAlias(alias[0], alias[1]);
		if(!criterion.isEmpty()){
			for(Criterion c : criterion)
				criteria.add(c);
		}
		criteria.add(conjunction);
		criteria.addOrder(order);
		return criteria.list();
	}
	public boolean salvarLog(Session session,Usuario u, String menu, String acao, String descricao) {
		UsuarioLog log = new UsuarioLog();
		log.setData(new Date());
		log.setUsuario(u);
		log.setMenu(menu);
		log.setAcao(acao);
		log.setDescricao(descricao);
		try {
			InetAddress net = InetAddress.getLocalHost();
			log.setMaquina(net.getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return salvar(log, session);
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public int uniqueResult(Class classe, Session session, List<Criterion> criterion){
        Criteria criteria = session.createCriteria(classe);
        criterion.forEach(c -> criteria.add(c));
        return criteria.setProjection(Projections.rowCount()).uniqueResult().hashCode();
    }
}
