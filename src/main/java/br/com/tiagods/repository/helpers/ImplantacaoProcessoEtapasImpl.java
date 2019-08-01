package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ImplantacaoProcessoEtapaDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ImplantacaoProcessoEtapasImpl extends AbstractRepository<ImplantacaoProcessoEtapa,Long> implements ImplantacaoProcessoEtapaDAO {
    public ImplantacaoProcessoEtapasImpl(EntityManager manager) {
        super(manager);
    }
    @Override
    public ImplantacaoProcessoEtapa findById(Long id) {
        Query query = getEntityManager().createQuery("from ImplantacaoProcessoEtapa as a "
                + "LEFT JOIN FETCH a.historico "
                + "where a.id=:id");
        query.setParameter("id", id);
        return (ImplantacaoProcessoEtapa)query.getSingleResult();
    }

    public List<ImplantacaoProcessoEtapa> filtrar(Departamento departamento, ImplantacaoProcesso processo, ImplantacaoAtividade atividade, ImplantacaoEtapa.Etapa etapa,ImplantacaoProcessoEtapa.Status status){
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(ImplantacaoProcessoEtapa.class);
        if(departamento!=null && departamento.getId()!=-1L){
            criteria.add(Restrictions.eq("etapa.departamento",departamento));
        }
        if(processo!=null && processo.getId()!=-1L){
            criteria.add(Restrictions.eq("processo",processo));
        }
        if(atividade!=null && atividade.getId()!=-1L){
            criteria.add(Restrictions.eq("etapa.atividade",atividade));
        }
        if(etapa!=null){
            criteria.add(Restrictions.eq("etapa.etapa",etapa));
        }
        if(status!=null && status!=ImplantacaoProcessoEtapa.Status.STATUS){
            criteria.add(Restrictions.eq("status",status));
        }
        criteria.createAlias("processo","pro");
        criteria.add(Restrictions.eq("pro.finalizado",false));
        return criteria.list();
    }
}
