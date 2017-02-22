package br.com.tiagods.modeldao;

import br.com.tiagods.model.Usuario;

public class UsuarioLogado {
	private Usuario usuario;
	
	static UsuarioLogado instance;
	
	public static UsuarioLogado getInstance(){
		if(instance==null){
			instance = new UsuarioLogado();
		}
		return instance;
	}
	public void receberUsuario(){
//			Session session = HibernateFactory.getSession();
//			session.beginTransaction();
//			List<Usuario> users = session.createQuery("from Usuario").getResultList();
//			if(users.isEmpty()){
//				CriarAdmin.getInstance().gerarDefault();
//				this.usuario = CriarAdmin.getInstance().getUsuario();
//			}	
//			else{
//				boolean encontrado = false;
//				String[] username = System.getProperty("user.name").split(" ");
//				for(Usuario user : users){
//					if(user.getNome().toUpperCase().contains(username[0].toUpperCase())){
//						this.usuario = user;
//						encontrado = true;
//						break;
//					}
//				}
//				if(!encontrado) 
//					this.usuario = users.get(0);
//			}
//			session.close();
	}
	
	public void setUsuario(Usuario usuario){
		this.usuario=usuario;
	}
	public Usuario getUsuario(){
		return usuario;
	}
}
