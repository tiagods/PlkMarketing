package br.com.tiagods;

import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapaStatus;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;

import java.util.*;
import java.util.stream.Collectors;

public class TesteEtapas2 extends UtilsController {
    public static void main(String[] args) throws Exception{
        TesteEtapas2 t = new TesteEtapas2();
        t.teste();
    }
    public void teste() throws  Exception{
        loadFactory();
        ImplantacaoProcessoEtapasImpl etapas = new ImplantacaoProcessoEtapasImpl(getManager());
        ImplantacaoProcessoEtapa pe = etapas.findById(326L);

        List<ImplantacaoProcessoEtapa> list = etapas.filtrar(null,pe.getProcesso(),pe.getEtapa().getAtividade(),null,null);

        List<ImplantacaoProcessoEtapaStatus> result = list.stream()
                .map(ImplantacaoProcessoEtapa::getHistorico)
                .flatMap(c->c.stream()).collect(Collectors.toList());

        Collections.sort(result, Comparator.comparing(ImplantacaoProcessoEtapaStatus::getCriadoEm));

        result.forEach(c->System.out.println(c.getId()));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,15);

        System.out.println(sdf.format(calendar.getTime()));

        close();
    }
}
