package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.repository.AbstractRepositoryImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UsuariosImpl {

	@PersistenceContext
	EntityManager manager;

	@Autowired
	AbstractRepositoryImpl abstractRepository;

	public List<Usuario> filtrar(String nome, int ativo, String ordem) {
		Criteria criteria = abstractRepository
				.getSession()
				.createCriteria(Usuario.class);
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

	public List<Usuario> listarAtivos() {
		Criteria criteria = abstractRepository
				.getSession()
				.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("ativo", 1));
		criteria.addOrder(Order.asc("nome"));
		return criteria.list();
	}

	public List<Usuario> getUsuariosByDepartamento(Departamento departamento) {
		Criteria criteria = abstractRepository
				.getSession()
				.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("ativo", 1));
		criteria.add(Restrictions.eq("departamento", departamento));
		criteria.addOrder(Order.asc("nome"));
		return criteria.list();
	}
}
