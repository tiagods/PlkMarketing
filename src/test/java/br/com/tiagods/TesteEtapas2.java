package br.com.tiagods;

import br.com.tiagods.config.init.JPAConfig;
import br.com.tiagods.controller.utils.PersistenciaController;
import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapaStatus;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;
import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TesteEtapas2 {

    EntityManager manager;

    @Rule
    public ExpectedException erro = ExpectedException.none();

    @Before
    public void banco(){
        manager = JPAConfig.getInstance().createManager();
    }
    @After
    public void fechaBanco(){
        manager.close();
    }

    @Test
    public void teste1(){
        ImplantacaoProcessoEtapasImpl etapas = new ImplantacaoProcessoEtapasImpl(manager);
        List<ImplantacaoProcessoEtapa> lista = etapas.getAll();
        Assert.assertEquals(false,lista.isEmpty());
    }

    @Test
    public void teste2() throws  Exception{
        ImplantacaoProcessoEtapasImpl etapas = new ImplantacaoProcessoEtapasImpl(manager);

        erro.expect(NoResultException.class);

        ImplantacaoProcessoEtapa pe = etapas.findById(326L);
/*
        List<ImplantacaoProcessoEtapa> list = etapas.filtrar(null,pe.getProcesso(),pe.getEtapa().getAtividade(),null,null);

        List<ImplantacaoProcessoEtapaStatus> result = list.stream()
                .map(ImplantacaoProcessoEtapa::getHistorico)
                .flatMap(c->c.stream()).collect(Collectors.toList());

        Collections.sort(result, Comparator.comparing(ImplantacaoProcessoEtapaStatus::getCriadoEm));

        result.forEach(c->System.out.println(c.getId()));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,15);

        System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
        */
    }
}
