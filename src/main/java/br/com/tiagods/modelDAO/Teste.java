package br.com.tiagods.modelDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Categoria;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		
		Map<String,Categoria> categorias = new HashMap<String,Categoria>();
		List<Categoria> lista = (List<Categoria>) session.createQuery("from Categoria").getResultList();
		
		lista.forEach(c->{
			categorias.put(c.getNome(), c);
			System.out.print("Id: "+c.getId() +"\tNome: "+c.getNome()+"\n");
		});
		
		Categoria c = categorias.get("Fornecedor");
		
		System.out.println("ID encontrado: "+c.getId()+"\t"+c.getNome());
		
		session.close();
		
	}

}
