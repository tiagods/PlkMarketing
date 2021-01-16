package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class ImplantacaoProcessosEtapasImpl {

    @PersistenceContext
    EntityManager manager;

    private ImplantacaoProcessosEtapasImpl(){}

    public Optional<ImplantacaoProcessoEtapa> findById(Long id) {
        Query query = manager.createQuery(
                "SELECT a from ImplantacaoProcessoEtapa as a "
                + "LEFT JOIN FETCH a.historico "
                + "where a.id=:id");
        query.setParameter("id", id);
        return Optional.ofNullable((ImplantacaoProcessoEtapa)query.getSingleResult());
    }

    public List<ImplantacaoProcessoEtapa> filtrar(Departamento departamento,
                                                  ImplantacaoProcesso processo,
                                                  ImplantacaoAtividade atividade,
                                                  ImplantacaoEtapa.Etapa etapa,
                                                  ImplantacaoProcessoEtapa.Status status,
                                                  boolean exibirApenasProcessoAberto){
        Criteria criteria = manager.unwrap(Session.class).createCriteria(ImplantacaoProcessoEtapa.class);
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
        if(exibirApenasProcessoAberto) {
            criteria.createAlias("processo", "pro");
            criteria.add(Restrictions.eq("pro.finalizado", false));
        }
        return criteria.list();
    }
}
