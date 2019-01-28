package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.ProtocoloEntrada;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.interfaces.ProtocoloEntradaDAO;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
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
    public Pair<List<ProtocoloEntrada>,Paginacao> filtrar(Paginacao paginacao, ProtocoloEntrada.StatusRecebimento recebimento,
                                                          ProtocoloEntrada.StatusDevolucao devolucao,
                                                          ProtocoloEntrada.Classificacao classificacao,
                                                          Usuario usuario, LocalDate inicio,
                                                          LocalDate fim, String pesquisa) {
        List<Criterion> criterios = new ArrayList<>();
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(ProtocoloEntrada.class);

        if(!recebimento.equals(ProtocoloEntrada.StatusRecebimento.STATUS))
            criterios.add(Restrictions.eq("recebido",recebimento.equals(ProtocoloEntrada.StatusRecebimento.FECHADO)));
        if(!devolucao.equals(ProtocoloEntrada.StatusDevolucao.DEVOLVIDO)){
            criterios.add(Restrictions.eq("devolver",true));
            if(devolucao.equals(ProtocoloEntrada.StatusDevolucao.NAOSEENQUADA))
                criterios.add(Restrictions.eq("devolver",false));
            else if(devolucao.equals(ProtocoloEntrada.StatusDevolucao.NAO))
                criterios.add(Restrictions.eq("devolvido",false));
            else if(devolucao.equals(ProtocoloEntrada.StatusDevolucao.SIM))
                criterios.add(Restrictions.eq("devolvido",true));
        }
        if(inicio!=null && fim!=null) criterios.add(Restrictions.between("dataEntrada",
                GregorianCalendar.from(inicio.atStartOfDay(ZoneId.systemDefault())),
                GregorianCalendar.from(fim.atStartOfDay(ZoneId.systemDefault()))));

        if(classificacao.equals(ProtocoloEntrada.Classificacao.USUARIO)){
            if(usuario.getId()!=-1L){
                Criterion c1 = Restrictions.eq("paraQuem",usuario);
                Criterion c2 = Restrictions.eq("quemRecebeu",usuario);
                criterios.add(Restrictions.or(c1,c2));
            }
        }
        else if(classificacao.equals(ProtocoloEntrada.Classificacao.PROTOCOLO) && pesquisa.trim().length()>0){
            try{
                Long value = Long.parseLong(pesquisa.trim());
                criterios.add(Restrictions.eq("id",value));
            }catch (Exception e){}

        }
        else if(classificacao.equals(ProtocoloEntrada.Classificacao.CLIENTE)){
            criteria.createAlias("cliente","cli");
            Criterion c2 = Restrictions.ilike("cli.nome",pesquisa.trim(),MatchMode.ANYWHERE);
            try{
                Long value = Long.parseLong(pesquisa.trim());
                Criterion c1 = Restrictions.eq("cli.id",value);
                criterios.add(Restrictions.or(c1,c2));
            }catch (Exception e){
                criterios.add(c2);
            }
        }
        criterios.forEach(c-> criteria.add(c));
        criteria.addOrder(Order.desc("id"));
        return super.filterWithPagination(paginacao, criteria, criterios);
    }
}
