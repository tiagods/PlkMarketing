package br.com.tiagods.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class GerarCidades {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(new InputStreamReader(
				new FileInputStream("D:\\Users\\Tiago\\Desktop\\munic.csv"),"UTF-8"))
                .useDelimiter("\n");
		List<Cidade> cidades = new ArrayList<Cidade>();
		
		scanner.nextLine();
		
		while(scanner.hasNext()){
			Cidade c = new Cidade();
			String[] dados = scanner.nextLine().split(";");
			c.setEstado(dados[0]);
			c.setIdExtra(dados[1]);
			c.setNome(dados[2]);
			cidades.add(c);
		}
		Session session = HibernateFactory.getSession();
		int i=0;
		session.beginTransaction();
		
		do{
			try{
				session.save(cidades.get(i));
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				session.getTransaction().rollback();
			}
			if(i%100==0){
				session.flush();
				session.clear();
			}
			i++;
		}while(i<cidades.size());
		session.getTransaction().commit();
		session.close();

	}

}
