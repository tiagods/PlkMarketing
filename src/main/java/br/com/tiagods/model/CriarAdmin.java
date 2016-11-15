package br.com.tiagods.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class CriarAdmin {
	
	Usuario usuario;
	Departamento departamento;
	Funcao funcao;
	Endereco endereco;
	Cidade c1;
	Categoria categoria;
	Nivel nivel;
	Origem origem;
	Servico servico;
	
	Session session = null;
	
	static CriarAdmin instance;
	
	public static CriarAdmin getInstance(){
		if(instance==null){
			instance = new CriarAdmin();
		}
		return instance;
	}
	public Usuario getUsuario(){
		return usuario;
	}
	public void gerarDefault(Session session){
		this.session=session;
		criarDepartamento();
		criarFuncao();
		criarUsuario();

		criarTipoTarefa();
		criarCidade();
		//criarEndereco();

		criarNivel();
		criarOrigem();
		criarCategoria();
		criarEtapa();

		criarServico();

		//	criarTarefa();
		criarEmpresa();
		criarPessoa();

		criarStatus();
		//criarNegocio();

		//criarServicoContratado();
	}
	private void criarPessoa() {
		PfPj pj = new PfPj();
		pj.setAtendente(usuario);
		pj.setCategoria(categoria);
		pj.setCriadoPor(usuario);
		pj.setNivel(nivel);
		pj.setOrigem(origem);
		pj.setServico(servico);
		
		Pessoa pessoa = new Pessoa();
		pessoa.setCpf("11111111111");
		pessoa.setEndereco(endereco);
		pessoa.setNome("Fabiano Alves Ferreira");
		pessoa.setPessoaFisica(pj);
		pessoa.setCriadoEm(new Date());
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		session.save(pessoa);
		session.getTransaction().commit();
		session.clear();
		
	}
	private void criarEmpresa() {
		endereco = new Endereco();
		endereco.setLogradouro("Rua");
		endereco.setNome("Rua 10");
		
		PfPj pj = new PfPj();
		pj.setAtendente(usuario);
		pj.setCategoria(categoria);
		pj.setCriadoPor(usuario);
		pj.setNivel(nivel);
		pj.setOrigem(origem);
		pj.setServico(servico);
		
		endereco.setCidade(c1);
		endereco.setBairro("jd paulista");
		endereco.setNumero("14");
		endereco.setCep("00000000");
		
		Empresa empresa = new Empresa();
		empresa.setCnpj("00000000000000");
		empresa.setEndereco(endereco);
		empresa.setNome("Castelao");
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		session.save(empresa);
		session.getTransaction().commit();
		session.clear();
	}
	private void criarServico() {
		servico = new Servico();
		servico.setNome("Consultoria");
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		session.save(servico);
		session.getTransaction().commit();
		session.clear();
	}
	private void criarEtapa() {
		List<Etapa> lStatus = new ArrayList<Etapa>();
		Etapa n1= new Etapa();
		n1.setNome("Contato");

		Etapa n2 = new Etapa();
		n2.setNome("Envio de Proposta");

		Etapa n3 = new Etapa();
		n3.setNome("Followup");

		Etapa n4 = new Etapa();
		n3.setNome("Fechamento");
		
		lStatus.add(n1);
		lStatus.add(n2);
		lStatus.add(n3);
		lStatus.add(n4);
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		lStatus.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
				session.getTransaction().rollback();
			}
		});
		session.getTransaction().commit();
		session.clear();
	}
	private void criarStatus() {
		List<Status> lStatus = new ArrayList<Status>();
		Status n1 = new Status();
		n1.setNome("Em Andamento");

		Status n2 = new Status();
		n2.setNome("Ganho");

		Status n3 = new Status();
		n3.setNome("Perdido");

		lStatus.add(n1);
		lStatus.add(n2);
		lStatus.add(n3);
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		lStatus.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
				session.getTransaction().rollback();
			}
		});
		session.getTransaction().commit();
		session.clear();
	}
	private void criarCategoria() {
		List<Categoria> lista = new ArrayList<Categoria>();
		categoria = new Categoria();
		categoria.setNome("Indefinida");
		
		Categoria n2 = new Categoria();
		n2.setNome("Cliente efetivo");
		
		Categoria n3 = new Categoria();
		n3.setNome("Cliente em potencial");
		
		Categoria n4 = new Categoria();
		n4.setNome("Concorrente");
		
		Categoria n5 = new Categoria();
		n5.setNome("Fornecedor");
		
		Categoria n6 = new Categoria();
		n6.setNome("Parceiro");
			
		lista.add(categoria);
		lista.add(n2);
		lista.add(n3);
		lista.add(n4);
		lista.add(n5);
		lista.add(n6);
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		lista.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
			}
		});
		session.getTransaction().commit();
		session.clear();
	}
	private void criarNivel() {
		List<Nivel> niveis = new ArrayList<Nivel>();
		nivel = new Nivel();
		nivel.setNome("Ouro");
		
		Nivel n2 = new Nivel();
		n2.setNome("Prata");
		
		Nivel n3 = new Nivel();
		n3.setNome("Platina");
		
		niveis.add(nivel);
		niveis.add(n2);
		niveis.add(n3);
		
		session.beginTransaction();
		niveis.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
			}
		});
		session.getTransaction().commit();
		session.clear();
	}
	private void criarOrigem() {
		List<Origem> origens = new ArrayList<Origem>();
		origem = new Origem();
		origem.setNome("Site");
		Origem o2 = new Origem();
		o2.setNome("Feira");
		Origem o3 = new Origem();
		o3.setNome("Seminario");
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		origens.forEach(c->{
			session.save(c);
		});
		session.getTransaction().commit();	
		session.clear();
	}
	private void criarCidade() {
		c1=(Cidade)session.createQuery("from Cidade").getSingleResult();
		
	}
	private void criarTipoTarefa() {
		List <String> tipoTarefa = new ArrayList<String>();
		tipoTarefa.add("Visita");
		tipoTarefa.add("Reuniao");
		tipoTarefa.add("Proposta");
		tipoTarefa.add("Ligacao");
		tipoTarefa.add("Email");
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		tipoTarefa.forEach(c->{
			TipoTarefa tT = new TipoTarefa();
			tT.setNome(c);
			session.save(tT);
		});
		session.getTransaction().commit();
		session.clear();
	}
	public void criarUsuario(){
		List<Usuario> lista = new ArrayList<>();
		usuario = new Usuario();
		usuario.setLogin("admin");
		usuario.setNome("Administrador");
		usuario.setSenha("admin");
		usuario.setEmail("suporte.ti@prolinkcontabil.com.br");
		usuario.setDepartamento(departamento);
		usuario.setFuncao(funcao);
		usuario.setTotalVendas(new BigDecimal("0.00"));
		lista.add(usuario);
		
		Usuario usuario2 = new Usuario();
		usuario2.setLogin("tiago");
		usuario2.setNome("Tiago");
		usuario2.setSenha("tiago");
		usuario2.setEmail("suporte.ti@prolinkcontabil.com.br");
		usuario2.setDepartamento(departamento);
		usuario2.setFuncao(funcao);
		usuario2.setTotalVendas(new BigDecimal("0.00"));
		lista.add(usuario2);
		Usuario usuario3 = new Usuario();
		usuario3.setLogin("usuario");
		usuario3.setNome("Usuario");
		usuario3.setSenha("usuario");
		usuario3.setEmail("suporte.ti@prolinkcontabil.com.br");
		usuario3.setDepartamento(departamento);
		usuario3.setFuncao(funcao);
		usuario3.setTotalVendas(new BigDecimal("0.00"));
		lista.add(usuario3);
	
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		lista.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
			}
		});
		session.getTransaction().commit();
		session.clear();
	}
	
	public void criarDepartamento(){
		departamento = new Departamento();
		departamento.setNome("Tecnologia");
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		try{
			session.save(departamento);
			session.getTransaction().commit();
			session.clear();
		}catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	public void criarFuncao(){
		funcao = new Funcao();
		funcao.setNome("Analista");
		if(!session.getTransaction().isActive())
			session.beginTransaction();
		try{
			session.save(funcao);
			session.clear();
		session.getTransaction().commit();
		}catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}	
}