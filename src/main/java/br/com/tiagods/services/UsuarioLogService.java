package br.com.tiagods.services;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.UsuarioLog;
import br.com.tiagods.repository.UsuariosLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UsuarioLogService {

    @Autowired
    UsuariosLogs logs;

    public void salvarLog( String menu, String acao, String descricao) {
        UsuarioLog log = new UsuarioLog();
        log.setData(Calendar.getInstance());
        log.setUsuario(UsuarioLogado.getInstance().getUsuario());
        log.setMenu(menu);
        log.setAcao(acao);
        log.setDescricao(descricao);
        logs.save(log);
    }
}
