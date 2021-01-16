package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.repository.interfaces.AbstractRepositoryImpl;
import br.com.tiagods.repository.interfaces.Paginacao;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Service
public class ProtocolosEntradasImpl {

    @PersistenceContext
    EntityManager manager;
    @Autowired
    AbstractRepositoryImpl abstractRepository;

    private ProtocolosEntradasImpl(){}

    public Optional<ProtocoloEntrada> findById(Long id) {
        Query query = manager.createQuery(
                "SELECT a FROM ProtocoloEntrada as a "
                + "LEFT JOIN FETCH a.items "
                + "where a.id=:id");
        query.setParameter("id", id);
        ProtocoloEntrada a = (ProtocoloEntrada)query.getSingleResult();
        return Optional.ofNullable(a);
    }

    public Pair<List<ProtocoloEntrada>,Paginacao> filtrar(Paginacao paginacao, ProtocoloEntradaFilter filter) {
        List<Criterion> criterios = new ArrayList<>();
        Criteria criteria = manager.unwrap(Session.class).createCriteria(ProtocoloEntrada.class);

        if(filter.getRecebimento().equals(ProtocoloEntrada.StatusRecebimento.ABERTO) && filter.getDevolucao().equals(ProtocoloEntrada.StatusDevolucao.NAO)){
            Criterion c1 = Restrictions.eq("recebido",false);
            Criterion c2 = Restrictions.eq("devolver",true);
            Criterion c3 = Restrictions.eq("devolvido",false);
            LogicalExpression expression = Restrictions.and(c2,c3);
            criterios.add(Restrictions.or(c1,expression));
        }
        else if(!filter.getRecebimento().equals(ProtocoloEntrada.StatusRecebimento.STATUS))
            criterios.add(Restrictions.eq("recebido",filter.getRecebimento().equals(ProtocoloEntrada.StatusRecebimento.FECHADO)));
        else if(!filter.getDevolucao().equals(ProtocoloEntrada.StatusDevolucao.DEVOLVIDO)){
            criterios.add(Restrictions.eq("devolver",true));
            if(filter.getDevolucao().equals(ProtocoloEntrada.StatusDevolucao.NAOSEENQUADA))
                criterios.add(Restrictions.eq("devolver",false));
            else if(filter.getDevolucao().equals(ProtocoloEntrada.StatusDevolucao.NAO))
                criterios.add(Restrictions.eq("devolvido",false));
            else if(filter.getDevolucao().equals(ProtocoloEntrada.StatusDevolucao.SIM))
                criterios.add(Restrictions.eq("devolvido",true));
        }
        if(filter.getDataInicial()!=null && filter.getDataFinal()!=null) criterios.add(Restrictions.between("dataEntrada",
                GregorianCalendar.from(filter.getDataInicial().atStartOfDay(ZoneId.systemDefault())),
                GregorianCalendar.from(filter.getDataFinal().atStartOfDay(ZoneId.systemDefault()))));

        if(filter.getClassificacao().equals(ProtocoloEntrada.Classificacao.USUARIO) && filter.getUsuario()!=null){
            if(filter.getUsuario().getId()!=-1L){
                Criterion c1 = Restrictions.eq("paraQuem",filter.getUsuario());
                Criterion c2 = Restrictions.eq("quemRecebeu",filter.getUsuario());
                criterios.add(Restrictions.or(c1,c2));
            }
        }
        else if(filter.getClassificacao().equals(ProtocoloEntrada.Classificacao.PROTOCOLO) && filter.getPesquisa().length()>0){
            try{
                Long value = Long.parseLong(filter.getPesquisa());
                criterios.add(Restrictions.eq("id",value));
            }catch (Exception e){}

        }
        else if(filter.getClassificacao().equals(ProtocoloEntrada.Classificacao.CLIENTE) && filter.getPesquisa().trim().length()>0){
            try{
                Long value = Long.parseLong(filter.getPesquisa());
                criterios.add(Restrictions.eq("cliente.id",value));

            }catch (Exception e){
            }
        }
        criterios.forEach(c-> criteria.add(c));
        criteria.addOrder(Order.desc("id"));
        Pair<List, Paginacao> pair = abstractRepository.filterWithPagination(ProtocoloEntrada.class, paginacao, criteria, criterios);
        return new Pair<>(pair.getKey(), pair.getValue());
    }
}
