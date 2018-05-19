package br.com.tiagods.controller;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.tiagods.config.JPAConfig;

public class PersistenciaController {
	private EntityManager entityManager;
    //private boolean ownsPersistenceContext;
    private static Logger log = LoggerFactory.getLogger(PersistenciaController.class);
    protected synchronized void loadFactory() {
    	loadFactory(null);
    }
    protected synchronized void loadFactory(EntityManager entityManager) {
    	if (entityManager == null || !entityManager.isOpen()) {
            debug("Nova persistencia");
            this.entityManager = JPAConfig.getInstance().createManager();
            //this.ownsPersistenceContext = true;
        } else {
            debug("Utilizando persistencia");
            this.entityManager = entityManager;
            //this.ownsPersistenceContext = false;
        }
    }
    public synchronized EntityManager getManager() {
		return this.entityManager;
	}

    public void commit() {
    	this.entityManager.getTransaction().commit();
    }
    protected void close() {
        //if (ownsPersistenceContext && getPersistenceContext().isOpen()) {
    	try{
	    	if (getManager().isOpen()) {
	        	debug("Fechando Persistencia");
	        	getManager().close();
	        }
    	}catch (Exception e) {
    		if(log.isErrorEnabled())
    			debug(e.getMessage());
		}
        //super.cleanUp();
    }
    private void debug(String message) {
    	if(log.isDebugEnabled()) {
    		log.debug(message);            
    	}
    }
}
