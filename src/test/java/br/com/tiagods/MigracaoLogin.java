package br.com.tiagods;

import br.com.tiagods.config.init.JPAConfig;
import br.com.tiagods.migracao.usuario.UsuarioDao;
import br.com.tiagods.migracao.usuario.UsuarioMysql;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.CriptografiaUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MigracaoLogin{

    private static CriptografiaUtil util = new CriptografiaUtil();

    public static void main(String[] args) {
        UsuarioDao dao = new UsuarioDao();
        List<UsuarioMysql> usuarioList = dao.listarTodos();

        EntityManager em = JPAConfig.getInstance().createManager();
        em.getTransaction().begin();
        UsuariosImpl usuarios = new UsuariosImpl(em);

        List<Usuario> salvar = new ArrayList<>();

        List<Usuario> update = new ArrayList<>();
        for(UsuarioMysql u : usuarioList){
            Usuario result = usuarios.findByEmail(u.getEmail().trim());
            if(result==null){
                Usuario r = new Usuario();
                r.setAtivo(u.getAtivo());
                r.setNome(u.getNome());
                r.setLogin(u.getUsuario());
                r.setCriadoEm(Calendar.getInstance());
                r.setSenha(util.criptografar(u.getSenha()));
                r.setSenhaAnterior(u.getSenha());
                r.setCodigoAnterior(u.getCod());
                r.setEmail(u.getEmail().trim());
                salvar.add(r);
            }
            else{
                result.setCodigoAnterior(u.getCod());
                result.setSenhaAnterior(u.getSenha());
                update.add(result);
            }
        }
        for(Usuario u : salvar){
            System.out.println("Salvo >>> "+u.getLogin());
            //m.persist(u);
        }
        for(Usuario u : update){
            System.out.println("Update >>> "+u.getLogin());
            //em.merge(u);
        }
        em.getTransaction().commit();
    }

}
