package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.ProtocoloEntrada;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import br.com.tiagods.repository.interfaces.ProtocoloEntradaDAO;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ProtocolosEntradasImpl extends AbstractRepository<ProtocoloEntrada,Long> implements ProtocoloEntradaDAO {
    public ProtocolosEntradasImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public ProtocoloEntrada findById(Long id) {
        Query query = getEntityManager().createQuery("from ProtocoloEntrada as a "
                + "LEFT JOIN FETCH a.items "
                + "where a.id=:id");
        query.setParameter("id", id);
        ProtocoloEntrada a = (ProtocoloEntrada)query.getSingleResult();
        return a;
    }

    @Override
    public Pair<List<ProtocoloEntrada>,Paginacao> filtrar(Paginacao paginacao, ProtocoloEntradaFilter filter) {
        List<Criterion> criterios = new ArrayList<>();
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(ProtocoloEntrada.class);

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
        return super.filterWithPagination(paginacao, criteria, criterios);
    }
}
