package br.com.tiagods.modeldao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Etapa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.ServicoAgregado;
import br.com.tiagods.model.ServicoContratado;
import br.com.tiagods.model.Status;
import br.com.tiagods.model.Usuario;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcessarPlanilha {

	public static void main(String[] args) throws BiffException{
		//criarTarefas();
		
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		
		java.util.Map<String, Origem> origens = new HashMap<>();
		java.util.Map<String, Categoria> categorias = new HashMap<>();
		java.util.Map<String, Pessoa> pessoas = new HashMap();
		java.util.Map<String, Empresa> empresas = new HashMap();
		java.util.Map<Integer, ServicoAgregado> servicosAgregados = new HashMap();
		
		List<Origem> org = new GenericDao().listar(Origem.class, session);
		org.forEach(c->{
			origens.put(c.getNome(), c);
		});
		
		List<Categoria> cat = new GenericDao().listar(Categoria.class, session);
		cat.forEach(c->{
			categorias.put(c.getNome(), c);
		});
		
		List<Pessoa> pes = new GenericDao().listar(Pessoa.class, session);
		pes.forEach(c->{
			pessoas.put(c.getId()+"", c);
		});
		
		List<Empresa> emp = new GenericDao().listar(Empresa.class, session);
		emp.forEach(c->{
			empresas.put(c.getId()+"", c);
		});
		
		List<ServicoAgregado> srv = new GenericDao().listar(ServicoAgregado.class, session);
		srv.forEach(c->{
			servicosAgregados.put(c.getId(), c);
		});
		
		Usuario usuario = (Usuario) new GenericDao().receberObjeto(Usuario.class, 1, session);
		Etapa etapa = (Etapa) new GenericDao().receberObjeto(Etapa.class, 1, session);
		Status status = (Status) new GenericDao().receberObjeto(Status.class, 1, session);
		
		
		Workbook workbook = null;
		try{
			FileInputStream file = new FileInputStream(System.getProperty("user.home")+"/Dropbox/novos_negocios_pen_09.xls");
			workbook = Workbook.getWorkbook(file);
		}catch(IOException e){
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(0);
		int numberRows = sheet.getRows();
		
		int i = 1;
		do{
			String codigoPfOuPj = sheet.getCell(0,i).getContents();
			String pfOupj = sheet.getCell(2,i).getContents();
			String id = sheet.getCell(3,i).getContents();
			String nome = sheet.getCell(4,i).getContents();
			String meio = sheet.getCell(5,i).getContents();
			String interesse = sheet.getCell(10,i).getContents();
			String vlrServico = sheet.getCell(11,i).getContents();
			String vlrHonorario = sheet.getCell(12,i).getContents();
			String descricao = sheet.getCell(14,i).getContents();
			String cadastro = sheet.getCell(16, i).getContents();
			
			Negocio negocio = new Negocio();
			try{
				negocio.setId(Integer.parseInt(id));
				negocio.setNome(nome);
				if(pfOupj.equals("PESSOA")){
					Pessoa p = pessoas.get(codigoPfOuPj);
					negocio.setPessoa(p);
					System.out.println("Pessoa id:"+p.getId()+" nome: "+p.getNome());
					negocio.setClasse("Pessoa");
				}
				else if(pfOupj.equals("EMPRESA")){
					Empresa e = empresas.get(codigoPfOuPj);
					System.out.println("Empresa id:"+e.getId()+" nome: "+e.getNome());
					negocio.setEmpresa(e);
					negocio.setClasse("Empresa");
				}
				negocio.setDescricao(descricao);
				negocio.setDataInicio(new SimpleDateFormat("dd/MM/yy").parse(cadastro));
				negocio.setHonorario(new BigDecimal(vlrHonorario.replace(",", ".")));
				negocio.setCriadoEm(new SimpleDateFormat("dd/MM/yy").parse(cadastro));
				negocio.setCriadoPor(usuario);
				negocio.setAtendente(usuario);
				negocio.setEtapa(etapa);
				negocio.setStatus(status);
				
				Set<ServicoContratado> contratado = new HashSet<>();
				ServicoContratado sc = new ServicoContratado();
				sc.setNegocios(negocio);
				sc.setValor(new BigDecimal(vlrServico.replace(",", ".")));
				
				if(interesse.equals("ABERTURA")){
					sc.setServicosAgregados(servicosAgregados.get(1));
				}
				else{
					sc.setServicosAgregados(servicosAgregados.get(2));					
				}
				contratado.add(sc);
				
				PfPj pj = new PfPj();
				pj.setCategoria(categorias.get("Cliente em potencial"));
				pj.setOrigem(origens.get(meio));
				
				negocio.setPessoaFisicaOrJuridica(pj);
				negocio.setServicosContratados(contratado);
				session.saveOrUpdate(negocio);
				if(i%100==0){
					session.flush();
					session.clear();
				}
			}catch (HibernateException | ParseException e) {
				e.printStackTrace();
				break;
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
//	public static void atualizarPessoas(Pessoa p){
//		p.setId(Integer.parseInt(id));
//		p.setNome(nome);
//		PfPj pf = new PfPj();
//		pf.setOrigem(origens.get(meio));
//		pf.setTelefone(telefone.replace("-", "").replace("(", "").replace(")", "").replace(" ", "").replace(".", ""));
//		pf.setEmail(email.toLowerCase());
//		pf.setCelular(celular.replace("-", "").replace("(", "").replace(")", "").replace(" ", "").replace(".", ""));
//		pf.setCategoria(categorias.get("Cliente em potencial"));
//		pf.setCriadoEm(new SimpleDateFormat("dd/MM/yy").parse(cadastro));
//		pf.setCriadoPor(usuario);
//		pf.setAtendente(usuario);
//		p.setPessoaFisica(pf);
//	}
//	public static void atualizarEmpresas(Empresa empresa){
//		empresa.setId(Integer.parseInt(id));
//		empresa.setNome(nome);
//		PfPj pj = new PfPj();
//		pj.setOrigem(origens.get(meio));
//		pj.setTelefone(telefone.replace("-", "").replace("(", "").replace(")", "").replace(" ", "").replace(".", ""));
//		pj.setEmail(email.toLowerCase());
//		pj.setCelular(celular.replace("-", "").replace("(", "").replace(")", "").replace(" ", "").replace(".", ""));
//		pj.setCategoria(categorias.get("Cliente em potencial"));
//		pj.setCriadoEm(new SimpleDateFormat("dd/MM/yy").parse(cadastro));
//		pj.setCriadoPor(usuario);
//		pj.setAtendente(usuario);
//		empresa.setPessoaJuridica(pj);
//		session.saveOrUpdate(empresa);
//		if(i%100==0){
//			session.flush();
//			session.clear();
//		}
//	}
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
//	public static void atualizarNegocios(){
//		Session session = HibernateFactory.getSession();
//		session.beginTransaction();
//		
//		java.util.Map<String, Origem> origens = new HashMap<>();
//		java.util.Map<String, Categoria> categorias = new HashMap<>();
//		java.util.Map<String, Pessoa> pessoas = new HashMap();
//		java.util.Map<String, Empresa> empresas = new HashMap();
//		java.util.Map<String, ServicoAgregado> servicosAgregados = new HashMap();
//		
//		List<Origem> org = new GenericDao().listar(Origem.class, session);
//		org.forEach(c->{
//			origens.put(c.getNome(), c);
//		});
//		
//		List<Categoria> cat = new GenericDao().listar(Categoria.class, session);
//		cat.forEach(c->{
//			categorias.put(c.getNome(), c);
//		});
//		
//		List<Pessoa> pes = new GenericDao().listar(Pessoa.class, session);
//		pes.forEach(c->{
//			pessoas.put(c.getId()+"", c);
//		});
//		
//		List<Empresa> emp = new GenericDao().listar(Empresa.class, session);
//		emp.forEach(c->{
//			empresas.put(c.getId()+"", c);
//		});
//		
//		List<ServicoAgregado> srv = new GenericDao().listar(ServicoAgregado.class, session);
//		srv.forEach(c->{
//			servicosAgregados.put(c.getId()+"", c);
//		});
//		
//		Usuario usuario = (Usuario) new GenericDao().receberObjeto(Usuario.class, 1, session);
//		Etapa etapa = (Etapa) new GenericDao().receberObjeto(Etapa.class, 1, session);
//		Status status = (Status) new GenericDao().receberObjeto(Status.class, 1, session);
//		
//		
//		Workbook workbook = null;
//		try{
//			FileInputStream file = new FileInputStream(System.getProperty("user.home")+"/Dropbox/novos_negocios_pen_09.xls");
//			workbook = Workbook.getWorkbook(file);
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		Sheet sheet = workbook.getSheet(0);
//		int numberRows = sheet.getRows();
//		
//		int i = 1;
//		do{
//			String codigoPfOuPj = sheet.getCell(0,i).getContents();
//			String pfOupj = sheet.getCell(2,i).getContents();
//			String id = sheet.getCell(3,i).getContents();
//			String nome = sheet.getCell(4,i).getContents();
//			String meio = sheet.getCell(5,i).getContents();
//			String interesse = sheet.getCell(10,i).getContents();
//			String vlrServico = sheet.getCell(11,i).getContents();
//			String vlrHonorario = sheet.getCell(12,i).getContents();
//			String descricao = sheet.getCell(14,i).getContents();
//			String cadastro = sheet.getCell(16, i).getContents();
//			
//			Negocio negocio = new Negocio();
//			try{
//				negocio.setId(Integer.parseInt(id));
//				negocio.setNome(nome);
//				if(pfOupj.equals("PESSOA")){
//					negocio.setPessoa(pessoas.get(codigoPfOuPj));
//					negocio.setClasse("Pessoa");
//				}
//				else if(pfOupj.equals("EMPRESA")){
//					Empresa e = empresas.get(codigoPfOuPj);
//					System.out.println("Empresa id:"+e.getId()+" nome: "+e.getNome());
//					negocio.setEmpresa(e);
//					negocio.setClasse("Empresa");
//				}
//				negocio.setDescricao(descricao);
//				negocio.setDataInicio(new SimpleDateFormat("dd/MM/yy").parse(cadastro));
//				negocio.setHonorario(new BigDecimal(vlrHonorario.replace(",", ".")));
//				negocio.setCriadoEm(new SimpleDateFormat("dd/MM/yy").parse(cadastro));
//				negocio.setCriadoPor(usuario);
//				negocio.setAtendente(usuario);
//				negocio.setEtapa(etapa);
//				negocio.setStatus(status);
//				
//				Set<ServicoContratado> contratado = new HashSet<>();
//				ServicoContratado sc = new ServicoContratado();
//				sc.setNegocios(negocio);
//				sc.setValor(new BigDecimal(vlrServico.replace(",", ".")));
//				if(interesse.equals("ABERTURA")){
//					sc.setServicosAgregados(servicosAgregados.get(1));
//				}
//				else{
//					sc.setServicosAgregados(servicosAgregados.get(2));					
//				}
//				PfPj pj = new PfPj();
//				pj.setCategoria(categorias.get("Cliente em potencial"));
//				pj.setOrigem(origens.get(meio));
//				
//				negocio.setPessoaFisicaOrJuridica(pj);
//				negocio.setServicosContratados(contratado);
//				session.saveOrUpdate(negocio);
//				if(i%100==0){
//					session.flush();
//					session.clear();
//				}
//			}catch (HibernateException | ParseException e) {
//				e.printStackTrace();
//				break;
//			}
//			i++;
//		}while(i<numberRows);
//		session.getTransaction().commit();
//		workbook.close();
//		session.close();
//	}
}
