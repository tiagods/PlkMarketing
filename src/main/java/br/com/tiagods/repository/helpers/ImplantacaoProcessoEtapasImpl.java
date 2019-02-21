package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ImplantacaoProcessoEtapaDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import java.util.List;

public class ImplantacaoProcessoEtapasImpl extends AbstractRepository<ImplantacaoProcessoEtapa,Long> implements ImplantacaoProcessoEtapaDAO {
    public ImplantacaoProcessoEtapasImpl(EntityManager manager) {
        super(manager);
    }
    public List<ImplantacaoProcessoEtapa> filtrar(Departamento departamento, ImplantacaoProcesso processo){
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(ImplantacaoProcessoEtapa.class);
        if(departamento!=null && departamento.getId()!=-1L){
            criteria.add(Restrictions.eq("departamento",departamento));
        }
        if(departamento!=null && processo.getId()!=-1L){
            criteria.add(Restrictions.eq("processo",processo));
        }
        return criteria.list();
    }
}
