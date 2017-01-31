package br.com.tiagods.modeldao;

import java.util.List;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Usuario;

@SuppressWarnings("unchecked")
public class UsuarioLogado {
	private Usuario usuario;
	
	static UsuarioLogado instance;
	
	public static UsuarioLogado getInstance(){
		if(instance==null){
			instance = new UsuarioLogado();
			instance.receberUsuario();
		}
		return instance;
	}
	private void receberUsuario(){
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		
		List<Usuario> users = session.createQuery("from Usuario").getResultList();
		if(users.isEmpty()){
			CriarAdmin.getInstance().gerarDefault();
			usuario = CriarAdmin.getInstance().getUsuario();
		}	
		else{
			boolean encontrado = false;
			String[] username = System.getProperty("user.name").split(" ");
			for(Usuario user : users){
				if(user.getNome().toUpperCase().contains(username[0].toUpperCase())){
					usuario = user;
					encontrado = true;
					break;
				}
			}
			if(!encontrado) 
				usuario = users.get(0);
		}
		session.close();
	}
	
	public void setUsuario(Usuario usuario){
		this.usuario=usuario;
	}
	public Usuario getUsuario(){
		return usuario;
	}
}
