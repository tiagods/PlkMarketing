package br.com.tiagods.repository.helpers;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.UsuarioDAO;

public class UsuariosImpl extends AbstractRepository<Usuario, Long> implements UsuarioDAO{
	public UsuariosImpl(EntityManager manager) {
		super(manager);
	}

	@Override
	public Usuario findByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike(ConstantesTemporarias.pessoa_nome, nome));
		return (Usuario) criteria.uniqueResult();
	}
	@Override
	public Usuario findByEmail(String email){
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike(ConstantesTemporarias.pessoa_email, email));
		return (Usuario) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getUsuariosByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.addOrder(Order.asc(ConstantesTemporarias.pessoa_nome));
		return (List<Usuario>) criteria.list();
	}

	@Override
	public Usuario findByLoginAndSenha(String login, String senha) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("login", login));
		criteria.add(Restrictions.ilike("senha", senha));
		return (Usuario) criteria.uniqueResult();
	}

	public Usuario findByLogin(String login) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("login", login));
		return (Usuario) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrar(String nome, int ativo, String ordem) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		if (!nome.trim().equals("")) {
			Criterion criterion = Restrictions.ilike(ConstantesTemporarias.pessoa_nome, nome, MatchMode.ANYWHERE);
			Criterion criterion2 = Restrictions.ilike(ConstantesTemporarias.pessoa_telefone, nome, MatchMode.ANYWHERE);
			Criterion criterion3 = Restrictions.ilike(ConstantesTemporarias.pessoa_celular, nome, MatchMode.ANYWHERE);
			Criterion c = Restrictions.or(criterion,criterion2,criterion3);
			criteria.add(c);
			//criteria.add(Restrictions.ilike("nome", nome, MatchMode.START));
		}
		if (ativo == 1 || ativo == 0)
			criteria.add(Restrictions.eq("ativo", ativo));
		criteria.addOrder(Order.asc(ordem));
		return (List<Usuario>) criteria.list();
	}

	@Override
    public List<Usuario> listarAtivos() {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("ativo", 1));
		criteria.addOrder(Order.asc("nome"));
		return criteria.list();
	}
}
