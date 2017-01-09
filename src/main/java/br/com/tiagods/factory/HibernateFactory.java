package br.com.tiagods.factory;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import br.com.tiagods.view.MenuView;

public class HibernateFactory {
	public static SessionFactory fabrica;
	static{
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try{
			fabrica = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}catch(Exception e){
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
			JOptionPane.showMessageDialog(MenuView.jDBody, 
					"Erro ao acessar o Banco de Dados!\nVerifique sua conexao com a internet ou entre em contato com o administrador.", 
					"Conexão recusada",JOptionPane.ERROR_MESSAGE);
			fabrica = null;
		}
	}
	public static Session getSession() throws HibernateException{
		return fabrica.openSession();
		
	}	
}