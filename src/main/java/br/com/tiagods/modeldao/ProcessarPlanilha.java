package br.com.tiagods.modeldao;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.TipoTarefa;
import br.com.tiagods.model.Usuario;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcessarPlanilha {

	public static void main(String[] args) throws BiffException{
		
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		
		session = HibernateFactory.getSession();
		session.beginTransaction();
		java.util.Map<String, Usuario> usuarios = new HashMap();
		java.util.Map<String, Negocio> negocios = new HashMap();
		java.util.Map<String, Tarefa> tarefas = new HashMap();
		java.util.Map<String, TipoTarefa> tipos = new HashMap();
				
		List<Usuario> listaUsuario = new GenericDao().listar(Usuario.class, session);
		listaUsuario.forEach(c->{
			usuarios.put(c.getLogin(), c);
		});
		List<Negocio> listaNegocios = new GenericDao().listar(Negocio.class, session);
		listaNegocios.forEach(c->{
			negocios.put(c.getId()+"", c);
		});
		List<Tarefa> listaTarefas = new GenericDao().listar(Tarefa.class, session);
		listaTarefas.forEach(c->{
			tarefas.put(c.getId()+"", c);
		});
		
		List<TipoTarefa> listaTipos = new GenericDao().listar(TipoTarefa.class, session);
		listaTipos.forEach(c->{
			tipos.put(c.getNome(), c);
		});
		
		Workbook workbook = null;
		try{
			FileInputStream file = new FileInputStream(System.getProperty("user.home")+"/Desktop/novos_negocios_pen_09.xls");
			workbook = Workbook.getWorkbook(file);
		}catch(IOException e){
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(1);
		int numberRows = sheet.getRows();
		int i = 1;
		do{
			String id = sheet.getCell(0,i).getContents();
			String tipoTarefa = sheet.getCell(3,i).getContents();
			String descricao = sheet.getCell(4,i).getContents();
			String data = sheet.getCell(5,i).getContents();
			String idNegocio = sheet.getCell(7,i).getContents();
			String user = sheet.getCell(8,i).getContents();
			
			try{
				
				Tarefa task = tarefas.get(id);
				Usuario u = usuarios.get(user);
				Negocio n = negocios.get(idNegocio);
				
				task.setAtendente(u);
				task.setClasse("Negocio");
				Date nd = new SimpleDateFormat("dd MM yyyy").parse(data);
				nd.setHours(8);
				nd.setMinutes(0);
				nd.setSeconds(0);
				task.setCriadoEm(nd);
				task.setCriadoPor(u);
				task.setDataEvento(nd);
				task.setDescricao(descricao);
				task.setFinalizado(1);
				task.setNegocio(n);
				
				switch(tipoTarefa){
				case "E-mail":
					task.setTipoTarefa(tipos.get("Email"));
					break;
				case "Reunião":
					task.setTipoTarefa(tipos.get("Reuniao"));
					break;
				case "Visita":
					task.setTipoTarefa(tipos.get("Visita"));
					break;
				case "Proposta":
					task.setTipoTarefa(tipos.get("Proposta"));
					break;
				default:
					task.setTipoTarefa(tipos.get("Telefone"));
					break;
				}
				
				session.saveOrUpdate(task);
				if(i%100==0){
					session.flush();
					session.clear();
				}
				
			}catch (HibernateException e) {
				e.printStackTrace();
				break;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}while(i<numberRows);
		session.getTransaction().commit();
		workbook.close();
		session.close();
		System.exit(0);
	}
	

//	private static void criarOrigem() {
//		String[] origens = {"Outros","Internet","Feira do Empreendedor 2015","Cliente",
//				"Porto Seguro","Parceria","Marketing","Bioqualynet","Funcionários"};
//		Set<String> lista = new TreeSet();
//		for(String s : origens)
//			lista.add(s);
//		
//		java.util.Iterator<String> iterator = lista.iterator();
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		
//		try{
//			while(iterator.hasNext()){
//				Origem o = new Origem();
//				o.setNome(iterator.next());
//				session.saveOrUpdate(o);
//			}	
//			session.getTransaction().commit();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		session.close();
//	}
//	private static void criarNivel() {
//		String[] niveis = {"PLATINA","PRATA 2","OURO 3","OURO 2","PRATA 3","OURO 1","BRONZE","PRATA 1","DIAMANTE"};
//		Set<String> lista = new TreeSet<>();
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		for(String s : niveis)
//			lista.add(s.toLowerCase());
//		lista.forEach(c->{
//			try{
//				Nivel nivel = new Nivel();
//				nivel.setNome(c);
//				session.save(nivel);
//			}catch(HibernateException e){
//				e.printStackTrace();
//			}
//		});
//		session.getTransaction().commit();
//		session.close();
//	}
//	public static void criarEmpresas(){
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		for(int i = 0; i<201; i++){
//			Empresa empresa = new Empresa();
//			session.save(empresa);
//			if(i%100==0){
//				session.flush();
//				session.clear();
//			}
//		}
//		session.getTransaction().commit();
//		session.close();
//	}
//	public static void criarPessoas(){
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		for(int i = 0; i<307; i++){
//			Pessoa pessoa = new Pessoa();
//			session.save(pessoa);
//			if(i%100==0){
//				session.flush();
//				session.clear();
//			}
//		}
//		session.getTransaction().commit();
//		session.close();
//	}
//	public static void Empresas throws BiffException{
//		
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		
//		java.util.Map<String, Usuario> usuarios = new HashMap();
//		java.util.Map<String, Empresa> empresas = new HashMap();
//		
//		List<Usuario> listaUsuario = new GenericDao().listar(Usuario.class, session);
//		listaUsuario.forEach(c->{
//			usuarios.put(c.getLogin(), c);
//		});
//		List<Empresa> listaEmpresas = new GenericDao().listar(Empresa.class, session);
//		listaEmpresas.forEach(c->{
//			empresas.put(c.getId()+"", c);
//		});
//		Workbook workbook = null;
//		try{
//			FileInputStream file = new FileInputStream(System.getProperty("user.home")+"/Desktop/novos_negocios_pen_09.xls");
//			workbook = Workbook.getWorkbook(file);
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		Sheet sheet = workbook.getSheet(4);
//		int numberRows = sheet.getRows();
//		int i = 1;
//		do{
//			String id = sheet.getCell(0,i).getContents();
//			String usuario = sheet.getCell(18,i).getContents();
//			try{
//				Empresa empresa = empresas.get(id);
//				
//				Usuario u = usuarios.get(usuario);
//				PfPj pj = empresa.getPessoaJuridica();
//				pj.setCriadoPor(u);
//				pj.setAtendente(u);
//				empresa.setPessoaJuridica(pj);
//				
//				session.saveOrUpdate(empresa);
//				if(i%100==0){
//					session.flush();
//					session.clear();
//				}
//			}catch (HibernateException e) {
//				e.printStackTrace();
//				break;
//			}
//			i++;
//		}while(i<numberRows);
//		session.getTransaction().commit();
//		workbook.close();
//		session.close();
//		System.exit(0);
//	}
//public static void atualizarPessoas throws BiffException{
//		
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		
//		java.util.Map<String, Usuario> usuarios = new HashMap();
//		java.util.Map<String, Pessoa> pessoas = new HashMap();
//		
//		List<Usuario> listaUsuario = new GenericDao().listar(Usuario.class, session);
//		listaUsuario.forEach(c->{
//			usuarios.put(c.getLogin(), c);
//		});
//		List<Pessoa> listaPessoas = new GenericDao().listar(Pessoa.class, session);
//		listaPessoas.forEach(c->{
//			pessoas.put(c.getId()+"", c);
//		});
//		Workbook workbook = null;
//		try{
//			FileInputStream file = new FileInputStream(System.getProperty("user.home")+"/Desktop/novos_negocios_pen_09.xls");
//			workbook = Workbook.getWorkbook(file);
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		Sheet sheet = workbook.getSheet(3);
//		int numberRows = sheet.getRows();
//		int i = 1;
//		do{
//			String id = sheet.getCell(0,i).getContents();
//			String usuario = sheet.getCell(18,i).getContents();
//			try{
//				Pessoa pessoa = pessoas.get(id);
//				
//				Usuario u = usuarios.get(usuario);
//				PfPj pf = pessoa.getPessoaFisica();
//				pf.setCriadoPor(u);
//				pf.setAtendente(u);
//				pessoa.setPessoaFisica(pf);
//				
//				session.saveOrUpdate(pessoa);
//				if(i%100==0){
//					session.flush();
//					session.clear();
//				}
//			}catch (HibernateException e) {
//				e.printStackTrace();
//				break;
//			}
//			i++;
//		}while(i<numberRows);
//		session.getTransaction().commit();
//		workbook.close();
//		session.close();
//		System.exit(0);
//	}
//	
//	public static void criarNegocios(){
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		for(int i = 0; i<509; i++){
//			Negocio negocio = new Negocio();
//			session.save(negocio);
//			if(i%100==0){
//				session.flush();
//				session.clear();
//			}
//		}
//		session.getTransaction().commit();
//		session.close();
//	}

//	public void atualizarNegocios(){
//	//criarTarefas();
//		
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		
//		java.util.Map<String, Status> status = new HashMap();
//		java.util.Map<String, Usuario> usuarios = new HashMap();
//		java.util.Map<String, Negocio> negocios = new HashMap();
//		
//		List<Status> listaStatus = new GenericDao().listar(Status.class, session);
//		listaStatus.forEach(c->{
//			status.put(c.getNome(), c);
//			System.out.println(c.getNome());
//		});
//		List<Usuario> listaUsuario = new GenericDao().listar(Usuario.class, session);
//		listaUsuario.forEach(c->{
//			usuarios.put(c.getLogin(), c);
//		});
//		List<Negocio> listaNegocios = new GenericDao().listar(Negocio.class, session);
//		listaNegocios.forEach(c->{
//			negocios.put(c.getId()+"", c);
//		});
//		Workbook workbook = null;
//		try{
//			FileInputStream file = new FileInputStream(System.getProperty("user.home")+"/Desktop/novos_negocios_pen_09.xls");
//			workbook = Workbook.getWorkbook(file);
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		Sheet sheet = workbook.getSheet(0);
//		int numberRows = sheet.getRows();
//		
//		int i = 1;
//		do{
//			String id = sheet.getCell(4,i).getContents();
//			String st = sheet.getCell(18,i).getContents();
//			String usuario = sheet.getCell(22,i).getContents();
//			
//			try{
//				Negocio negocio = negocios.get(id);
//				negocio.setId(Integer.parseInt(id));
////				
//				String statusC="";
//				switch(st){
//				case "Em Negociação":
//					statusC="Em Andamento";
//					break;
//				case "Sem Movimento":
//					statusC="Sem Movimento";
//					break;
//				case "Negócio Fechado":
//					statusC="Ganho";
//					break;
//				case "Negócio Cancelado":
//					statusC="Perdido";
//					break;
//				default:
//					break;
//				}
//				
//				Usuario u = usuarios.get(usuario);
//				negocio.setCriadoPor(u);
//				negocio.setAtendente(u);
//				negocio.setDataFim(negocio.getDataInicio());
////				//negocio.setEtapa(etapa);
////				
//				negocio.setStatus(status.get(statusC));
//				session.saveOrUpdate(negocio);
//				if(i%100==0){
//					session.flush();
//					session.clear();
//				}
//			}catch (HibernateException e) {
//				e.printStackTrace();
//				break;
//			}
//			i++;
//		}while(i<numberRows);
//		session.getTransaction().commit();
//		workbook.close();
//		session.close();
//		System.exit(0);
//	}
}
