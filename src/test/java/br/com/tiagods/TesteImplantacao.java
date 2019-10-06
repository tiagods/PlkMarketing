package br.com.tiagods;

import br.com.tiagods.config.init.JPAConfig;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;
import br.com.tiagods.util.alerta.AlertaImplantacao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TesteImplantacao {

    static AlertaImplantacao implantacao;

    @Before
    public void init(){
        implantacao = new AlertaImplantacao();
    }

    //@Test
    public void testar(){
        EntityManager manager = JPAConfig.getInstance().createManager();
        try {
            ImplantacaoProcessoEtapasImpl etapasImp = new ImplantacaoProcessoEtapasImpl(manager);

            List<ImplantacaoProcessoEtapa> list = etapasImp
                    .filtrar(null,
                            new ImplantacaoProcesso(9L),
                            null,
                            null,
                            null,
                            false);

            /*
            List<ImplantacaoProcessoEtapa> list = etapasImp
                    .filtrarMultProcessos(null,
                            Arrays.asList(new ImplantacaoProcesso(9L)).stream().collect(Collectors.toSet()),
                            null,
                            null,
                            null,
                            false);
*/
            list.forEach(c->c.getProcesso().getId());
            Assert.assertFalse(list.isEmpty());
            /*
            implantacao.gerarExcel(Arrays.asList(new ImplantacaoProcesso(9L)).stream().collect(Collectors.toSet()),
                    null,
                    null, null, true);
                    */


        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }finally {
            manager.close();
        }
    }

    @Test
    public void testarHistorico(){
        try {
            implantacao.gerarHtml(new ImplantacaoProcesso(9L),
                    null,
                    null, null, true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    @Test
    public void testarSemHistorico(){
        try {
            implantacao.gerarHtml(new ImplantacaoProcesso(9L),
                    null,
                    null, null, false);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    @Test
    public void testarExcelSemHistorico(){
        try {
            implantacao.gerarExcel(new ImplantacaoProcesso(9L),
                    null,
                    null, null, false);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    @Test
    public void testarExcelHistorico(){
        try {
            implantacao.gerarExcel(new ImplantacaoProcesso(9L),
                    null,
                    null, null, true);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
