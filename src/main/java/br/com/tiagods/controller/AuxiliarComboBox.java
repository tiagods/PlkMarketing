package br.com.tiagods.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Etapa;
import br.com.tiagods.model.Lista;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Prospeccao;
import br.com.tiagods.model.ProspeccaoTipoContato;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.ServicoAgregado;
import br.com.tiagods.model.Status;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.view.interfaces.ComboBoxComplete;
import br.com.tiagods.view.interfaces.DefaultEnumModel;
/*
 * Essa classe eh invocada pelos controllers de pessoa e empresa
 */
public class AuxiliarComboBox {
	Map <String,Usuario> atendentes;
	Map <String,Categoria> categorias;
	Map <String,Cidade> cidades;
	Map <String,Empresa> empresas;
	Map <String,Etapa> etapas;
	Map <String,Nivel> niveis;
	Map <String,Origem> origens;
	Map <String, Pessoa> pessoas;
	Map <String,Servico> servicos;
	Map <String,Status> statusMapa;
	Map <String,ServicoAgregado> servicosAgregados;
	Map <String,Prospeccao> prospeccoes;
	Map <String,ProspeccaoTipoContato> tipoContatos;
	Map <String,Lista> listas;

	List<Usuario> listarUsuarios;
	List<Categoria> listarCategorias;
	List<Nivel> listarNiveis;
	List<Origem> listarOrigens;
	List<Servico> listarServicos;
	List<Empresa> listarEmpresas;
	List<Pessoa> listarPessoas;
	List<Etapa> listarEtapas;
	List<Status> listarStatus;
	List<ServicoAgregado> listaServicosAgregados;
	List<Prospeccao> listarProspeccoes;
	List<ProspeccaoTipoContato> listarTipoContatos;
	List<Lista> listarListas;
	
	private static AuxiliarComboBox instance;
	
	GenericDao dao = new GenericDao();
	
	static AuxiliarComboBox getInstance(){
		if(instance==null){
			instance = new AuxiliarComboBox();
		}
		return instance;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void preencherCombo(JComboBox combo, Session session, JComboBox comboEstado){
    	combo.removeAllItems();
    	combo.addItem(combo.getName());
    	switch(combo.getName()){
    	case "Atendente":
    		listarUsuarios = dao.items(Usuario.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		atendentes = new HashMap();
    		if(!listarUsuarios.isEmpty()){
    			listarUsuarios.forEach(c->{
    				atendentes.put(c.getNome(),c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "AtendenteCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarUsuarios.isEmpty()){
    			listarUsuarios.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Categoria":
    		listarCategorias = dao.items(Categoria.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		categorias = new HashMap();
    		if(!listarCategorias.isEmpty()){
    			listarCategorias.forEach(c->{
    				categorias.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "CategoriaCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarCategorias.isEmpty()){
    			listarCategorias.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Empresa":
    		listarEmpresas = dao.items(Empresa.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		empresas = new HashMap();
    		if(!listarEmpresas.isEmpty()){
    			listarEmpresas.forEach(c->{
    				empresas.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Etapa":
    		listarEtapas = dao.items(Etapa.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		etapas = new HashMap();
    		if(!listarEtapas.isEmpty()){
    			listarEtapas.forEach(c->{
    				etapas.put(c.getNome(),c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Lista":
    		listarListas = dao.items(Lista.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		listas = new HashMap();
    		if(!listarListas.isEmpty()){
    			listarListas.forEach(c->{
    				listas.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "ListaCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarListas.isEmpty()){
    			listarListas.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Logradouro":
    		combo.removeAllItems();
    		combo.addItem("");
    		combo.setModel(new DefaultComboBoxModel<>(DefaultEnumModel.Logradouro.values()));
    		combo.setSelectedItem(DefaultEnumModel.Logradouro.valueOf("Rua"));
    		break;
    	case "Nivel":
    		listarNiveis = dao.items(Nivel.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		niveis = new HashMap();
    		if(!listarNiveis.isEmpty()){
    			listarNiveis.forEach(c->{
    				niveis.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "NivelCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarNiveis.isEmpty()){
    			listarNiveis.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Origem":
    		listarOrigens = dao.items(Origem.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		origens = new HashMap();
    		if(!listarOrigens.isEmpty()){
    			listarOrigens.forEach(c->{
    				origens.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "OrigemCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarOrigens.isEmpty()){
    			listarOrigens.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Pessoa":
    		listarPessoas = dao.items(Pessoa.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		pessoas = new HashMap();
    		if(!listarPessoas.isEmpty()){
    			listarPessoas.forEach(c->{
    				pessoas.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Produtos/Servicos":
    		listarServicos = dao.items(Servico.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		servicos = new HashMap();
    		if(!listarServicos.isEmpty()){
    			listarServicos.forEach(c->{
    				servicos.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Prospeccao":
    		listarProspeccoes = dao.items(Prospeccao.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		prospeccoes = new HashMap();
    		if(!listarProspeccoes.isEmpty()){
    			listarProspeccoes.forEach(c->{
    				prospeccoes.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "TipoContato":
    		listarTipoContatos = dao.items(ProspeccaoTipoContato.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		tipoContatos = new HashMap();
    		if(!listarTipoContatos.isEmpty()){
    			listarTipoContatos.forEach(c->{
    				tipoContatos.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	
    	case "ServicoAgregadoCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		listaServicosAgregados = dao.items(ServicoAgregado.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		servicosAgregados = new HashMap();
    		if(!listaServicosAgregados.isEmpty()){
    			listaServicosAgregados.forEach(c->{
    				servicosAgregados.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "ServicosCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarServicos.isEmpty()){
    			listarServicos.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Status":
    		listarStatus  = dao.items(Status.class, session, new ArrayList<Criterion>(), Order.asc("nome"));
    		statusMapa = new HashMap<>();
    		if(!listarStatus.isEmpty()){
    			listarStatus.forEach(c->{
    				statusMapa.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
    		combo.setSelectedItem(combo.getName());
    		break;
    	case "StatusCad":
    		combo.removeAllItems();
    		if(!listarStatus.isEmpty()){
    			listarStatus.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Cidade":
    		combo.addItem("");
    		if(comboEstado.getSelectedItem()!=null){
    			combo.removeAllItems();
    			List<Criterion> criterios = new ArrayList<>();
    			criterios.add(Restrictions.eq("estado", comboEstado.getSelectedItem().toString()));
    			List<Cidade> listarCidades = dao.items(Cidade.class, session, criterios,Order.asc("nome"));
				cidades = new HashMap();
				combo.addItem("");
				if(!listarCidades.isEmpty()){
					listarCidades.forEach(c->{
						combo.addItem(c.getNome());
						cidades.put(c.getNome(), c);
					});
				}
				combo.setSelectedItem("");
				combo.setEditable(true);
				new ComboBoxComplete(combo);
    		}
    		break;
    	case "Estado":
    		combo.removeAllItems();
    		combo.addItem("");
    		List<Cidade> listaCidades = dao.listar(Cidade.class, session);
    		Set<String> listaEstados = new TreeSet();
    		listaCidades.forEach(c->{
    			listaEstados.add(c.getEstado());
    		});
    		Iterator<String> i = listaEstados.iterator();
    		while(i.hasNext()){
    			combo.addItem(i.next());
    		}
    		break;
    	case "Objeto":
    		combo.removeAllItems();
    		combo.addItem("Empresa");
    		combo.addItem("Pessoa");
    		combo.addItem("Prospeccao");
    		break;
    	default:
    		break;
    	}
    }
		/**
	 * @return the categorias
	 */
	public Categoria getCategorias(String key) {
		return categorias.get(key);
	}

	/**
	 * @return the niveis
	 */
	public Nivel getNiveis(String key) {
		return niveis.get(key);
	}

	/**
	 * @return the origens
	 */
	public Origem getOrigens(String key) {
		return origens.get(key);
	}

	/**
	 * @return the servicos
	 */
	public Servico getServicos(String key) {
		return servicos.get(key);
	}

	/**
	 * @return the cidades
	 */
	public Cidade getCidades(String key) {
		return cidades.get(key);
	}

	/**
	 * @return the empresas
	 */
	public Empresa getEmpresas(String key) {
		return empresas.get(key);
	}
	/**
	 * @return the etapas
	 */
	public Etapa getEtapa(String key){
		return etapas.get(key);
	}
	/**
	 * @return the status
	 */
	public Status getStatus(String key){
		return statusMapa.get(key);
	}
	/**
	 * @return the atendentes
	 */
	public Usuario getAtendentes(String key) {
		return atendentes.get(key);
	}
	/**
	 * 
	 * @return the pessoas
	 */
	public Pessoa getPessoas(String key){
		return pessoas.get(key);
	}
	/**
	 * @return the servicos agregados
	 * 
	 */
	 public ServicoAgregado getServicosAgregados(String key){
		 return servicosAgregados.get(key);
	 }
}
