package br.com.tiagods;

import br.com.tiagods.config.init.JPAConfig;
import br.com.tiagods.migracao.protocolo.ProtocoloDao;
import br.com.tiagods.migracao.protocolo.ProtocoloEntradaBean;
import br.com.tiagods.migracao.protocolo.ProtocoloItemBean;
import br.com.tiagods.model.Cliente;
import br.com.tiagods.model.ProtocoloEntrada;
import br.com.tiagods.model.ProtocoloItem;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.util.*;

public class MigracaoProtocoloEntrada {

    public static void main(String[] args) {
        ProtocoloDao protocoloDao = new ProtocoloDao();
        List<ProtocoloEntradaBean> itens = protocoloDao.receberLista("select * from documentos_recebidos order by cod asc");

        EntityManager em = JPAConfig.getInstance().createManager();
        em.getTransaction().begin();

        itens.forEach(c->{
            ProtocoloEntrada p = new ProtocoloEntrada();
            p.setId(null);
            Date date = c.getDataRecebimento();
            Time hora = c.getHora();
            if(date!=null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                if(hora!=null) {
                    calendar.set(Calendar.HOUR,hora.getHours());
                    calendar.set(Calendar.MINUTE,hora.getMinutes());
                    calendar.set(Calendar.SECOND,hora.getSeconds());
                }
                p.setDataEntrada(calendar);
            }
            p.setQuemEntregou(c.getQuemEntregou()==null?"":c.getQuemEntregou());
            System.out.println(c.getId()+">>>"+c.getApelido()+" >>> "+p.getDataEntrada());
            if(c.getApelido()!=null && !c.getApelido().trim().equals(""))
                p.setCliente(new Cliente(Long.parseLong(c.getApelido().trim())));
            else p.setCliente(null);
            p.setHistorico(c.getHistorico()==null?"":c.getHistorico());

            Date recebimento = c.getDataFuncionarioRecebeu();
            if(recebimento!=null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(recebimento);
                p.setDataRecebimento(calendar);
            }
            p.setObservacao(c.getObservacao()==null?"":c.getObservacao());
            p.setAlerta(c.getAlerta().equalsIgnoreCase("S"));
            p.setRecebido(c.getRecebido().equalsIgnoreCase("S"));
            p.setDevolver(c.getPassivelDevolucao()==1);
            p.setDevolvido(c.getDevolvido()==1);
            Date prazo = c.getDevolverAte();
            if(prazo!=null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(prazo);
                p.setPrazo(calendar);
            }
            p.setAdiado(c.getAdiado()==1);
            p.setMotivo(c.getAdiadoMotivo()==null?"":c.getAdiadoMotivo());
            p.setParaQuem(null);
            p.setQuemRecebeu(null);

            //p.setParaQuemId(c.getParaQuemId());
            //p.setQuemRecebeuId(c.getQuemRecebeuId());

            Set<ProtocoloItem> items = new HashSet<>();
            for(ProtocoloItemBean bean : c.getItems()){
                ProtocoloItem item= new ProtocoloItem();
                item.setNome(bean.getNome());
                item.setQuantidade(bean.getQuantidade());
                item.setDetalhes(bean.getDetalhe()==null?"":bean.getDetalhe());

                Date entrada = bean.getDataEntrada();
                if(entrada!=null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(entrada);
                    item.setDataEntrada(calendar);
                }
                Date saida = bean.getDataSaida();
                if(saida!=null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(saida);
                    item.setDataSaida(calendar);
                }
                item.setCliente(p.getCliente());
                item.setEntrada(p);
                items.add(item);
            }
            p.setItems(items);
            //em.persist(p);
        });
        System.out.println(itens.size());
        em.getTransaction().commit();

        System.exit(0);
    }
}
