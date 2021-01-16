package br.com.tiagods;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class TesteEtapas2 {

    EntityManager manager;

    @Rule
    public ExpectedException erro = ExpectedException.none();

    //@Before
    public void banco(){
    }

    //@After
    public void fechaBanco(){
        manager.close();
    }

    //@Test
    public void teste2() throws  Exception{
        erro.expect(NoResultException.class);
    }
}
