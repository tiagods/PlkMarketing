package br.com.tiagods.modeldao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Etapa;
import br.com.tiagods.model.Funcao;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.ServicoAgregado;
import br.com.tiagods.model.Status;
import br.com.tiagods.model.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.view.MenuView;

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
	public void gerarDefault(){
		criarDepartamento();
		criarFuncao();
		criarUsuario();

		criarTipoTarefa();
		criarCidade();

		criarNivel();
		criarOrigem();
		criarCategoria();
		criarEtapa();

		criarServico();

		//criarTarefa();
		criarEmpresa();
		criarPessoa();

		criarStatus();
		criarServicoAgregado();
		//criarNegocio();
	}
	private void criarPessoa() {
		PfPj pj = new PfPj();
		pj.setAtendente(usuario);
		pj.setCategoria(categoria);
		pj.setCriadoPor(usuario);
		pj.setOrigem(origem);
		pj.setServico(servico);
		pj.setCriadoEm(new Date());

		Pessoa pessoa = new Pessoa();
		pessoa.setCpf("11111111111");
		pessoa.setEndereco(endereco);
		pessoa.setNome("Fabiano Alves Ferreira");
		pessoa.setPessoaFisica(pj);
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		session.save(pessoa);
		session.getTransaction().commit();
		session.close();

	}
	private void criarEmpresa() {
		endereco = new Endereco();
		endereco.setLogradouro("Rua");
		endereco.setNome("Rua 10");

		PfPj pj = new PfPj();
		pj.setAtendente(usuario);
		pj.setCategoria(categoria);
		pj.setCriadoPor(usuario);
		pj.setOrigem(origem);
		pj.setServico(servico);
		pj.setCriadoEm(new Date());
		pj.setNivel(nivel);
		
		endereco.setCidade(c1);
		endereco.setBairro("jd paulista");
		endereco.setNumero("14");
		endereco.setCep("00000000");

		Empresa empresa = new Empresa();
		empresa.setCnpj("00000000000000");
		empresa.setEndereco(endereco);
		empresa.setNome("Empresa Teste Ltda");
		empresa.setPessoaJuridica(pj);
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		session.save(empresa);
		session.getTransaction().commit();
		session.close();
	}
	private void criarServico() {
		servico = new Servico();
		servico.setNome("Consultoria");
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		session.save(servico);
		session.getTransaction().commit();
		session.close();
	}
	private void criarEtapa() {
		List<Etapa> lStatus = new ArrayList<>();
		Etapa n1= new Etapa();
		n1.setNome("Contato");

		Etapa n2 = new Etapa();
		n2.setNome("Envio de Proposta");

		Etapa n3 = new Etapa();
		n3.setNome("Follow-up");

		Etapa n4 = new Etapa();
		n4.setNome("Fechamento");
		
		Etapa n5 = new Etapa();
		n5.setNome("Indefinida");

		lStatus.add(n1);
		lStatus.add(n2);
		lStatus.add(n3);
		lStatus.add(n4);
		Session session = HibernateFactory.getSession();
		session.beginTransaction();

		lStatus.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
				session.getTransaction().rollback();
			}
		});
		session.getTransaction().commit();
		session.close();
	}
	private void criarStatus() {
		List<Status> lStatus = new ArrayList<>();
		Status n1 = new Status();
		n1.setNome("Em Andamento");

		Status n2 = new Status();
		n2.setNome("Ganho");

		Status n3 = new Status();
		n3.setNome("Perdido");

		lStatus.add(n1);
		lStatus.add(n2);
		lStatus.add(n3);
		Session session = HibernateFactory.getSession();
		session.beginTransaction();

		lStatus.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
				session.getTransaction().rollback();
			}
		});
		session.getTransaction().commit();
		session.close();
	}
	private void criarCategoria() {
		List<Categoria> lista = new ArrayList<>();
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
		Session session = HibernateFactory.getSession();
		session.beginTransaction();

		lista.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
			}
		});
		session.getTransaction().commit();
		session.close();
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

		Session session = HibernateFactory.getSession();
		session.beginTransaction();

		niveis.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
			}
		});
		session.getTransaction().commit();
		session.close();
	}
	private void criarOrigem() {
		List<Origem> origens = new ArrayList<Origem>();
		origem = new Origem();
		origem.setNome("Site");
		Origem o2 = new Origem();
		o2.setNome("Feira");
		Origem o3 = new Origem();
		o3.setNome("Seminario");
		origens.add(origem);
		origens.add(o2);
		origens.add(o3);
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		origens.forEach(c->{
			session.save(c);
		});
		session.getTransaction().commit();
		session.close();
	}
	@SuppressWarnings("resource")
	private void criarCidade() {
		Scanner scanner = null;
		
		int escolha = JOptionPane.showConfirmDialog(MenuView.jDBody, "Cadastro de Municipios! Coloque o arquivo munic.csv na pasta do programa e clique em OK","Importação de Cidades!",JOptionPane.OK_OPTION);
		
		if(escolha==JOptionPane.OK_OPTION){
		try{
		scanner = new Scanner(new InputStreamReader(
				new FileInputStream(System.getProperty("user.dir")+"/munic.csv"),"UTF-8"))
                .useDelimiter("\n");
		}catch (IOException e) {
		}
		List<Cidade> cidades = new ArrayList<Cidade>();

		scanner.nextLine();
		
		while(scanner.hasNext()){
			Cidade c = new Cidade();
			String[] dados = scanner.nextLine().split(",");
			c.setEstado(dados[0]);
			c.setIdExtra(dados[1]);
			c.setNome(dados[2]);
			cidades.add(c);
		}

		int i=0;
		Session session = HibernateFactory.getSession();
		session.beginTransaction();

		do{
			try{
				session.save(cidades.get(i));
			}catch (Exception e) {
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
		receberCidade();
		}
	}
	private void receberCidade(){
		Session session = HibernateFactory.getSession();
		c1=(Cidade)session.createQuery("from Cidade c where c.id=1").getSingleResult();
		session.close();
	}
	private void criarTipoTarefa() {
		List <String> tipoTarefa = new ArrayList<String>();
		tipoTarefa.add("Visita");
		tipoTarefa.add("Reuniao");
		tipoTarefa.add("Proposta");
		tipoTarefa.add("Telefone");
		tipoTarefa.add("Email");
		Session session = HibernateFactory.getSession();
		session.beginTransaction();

		tipoTarefa.forEach(c->{
			TipoTarefa tT = new TipoTarefa();
			tT.setNome(c);
			session.save(tT);
		});
		session.getTransaction().commit();
		session.close();
	}
	public void criarUsuario(){
		List<Usuario> lista = new ArrayList<>();
		usuario = new Usuario();
		usuario.setLogin("Isabelle");
		usuario.setNome("Isabelle Souza");
		usuario.setSenha("isabelle");
		usuario.setEmail("suporte.ti@prolinkcontabil.com.br");
		usuario.setDepartamento(departamento);
		usuario.setFuncao(funcao);
		usuario.setTotalVendas(new BigDecimal("0.00"));
		lista.add(usuario);

		Usuario usuario2 = new Usuario();
		usuario2.setLogin("Tiago");
		usuario2.setNome("Tiago");
		usuario2.setSenha("tiago");
		usuario2.setEmail("tiago.dias@prolinkcontabil.com.br");
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
		
		Usuario usuario4 = new Usuario();
		usuario4.setLogin("Monary");
		usuario4.setNome("Monary Torres");
		usuario4.setSenha("Monary");
		usuario4.setEmail("suporte.ti@prolinkcontabil.com.br");
		usuario4.setDepartamento(departamento);
		usuario4.setFuncao(funcao);
		usuario4.setTotalVendas(new BigDecimal("0.00"));
		lista.add(usuario4);
		
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		lista.forEach(c->{
			try{
				session.save(c);
			}catch(HibernateException e){
			}
		});
		session.getTransaction().commit();
		session.close();
	}

	public void criarDepartamento(){
		departamento = new Departamento();
		departamento.setNome("Tecnologia");
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		try{
			session.save(departamento);
			session.getTransaction().commit();

		}catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.close();
	}
	public void criarFuncao(){
		funcao = new Funcao();
		funcao.setNome("Analista");
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		try{
			session.save(funcao);
			session.getTransaction().commit();

		}catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.close();
	}
	private void criarServicoAgregado(){
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		
		ServicoAgregado sa1 = new ServicoAgregado();
		sa1.setNome("Abertura");
		
		ServicoAgregado sa2 = new ServicoAgregado();
		sa2.setNome("Implantação");
		
		session.save(sa1);
		session.save(sa2);
		
		try{
			session.getTransaction().commit();
		}catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.close();
	}
}
