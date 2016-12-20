package br.com.tiagods.controller;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Etapa;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.Status;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelDAO.CategoriaDAO;
import br.com.tiagods.modelDAO.EmpresaDAO;
import br.com.tiagods.modelDAO.EtapaDAO;
import br.com.tiagods.modelDAO.NivelDAO;
import br.com.tiagods.modelDAO.OrigemDAO;
import br.com.tiagods.modelDAO.PessoaDAO;
import br.com.tiagods.modelDAO.ServicoDAO;
import br.com.tiagods.modelDAO.StatusDAO;
import br.com.tiagods.modelDAO.UsuarioDAO;
import br.com.tiagods.view.interfaces.Autocomplete;
import br.com.tiagods.view.interfaces.DefaultEnumModel;
/*
 * Essa classe � invocada pelos controllers de pessoa e empresa
 */
public class PadraoMap {
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
	
	List<Usuario> listarUsuarios;
	List<Categoria> listarCategorias;
	List<Nivel> listarNiveis;
	List<Origem> listarOrigens;
	List<Servico> listarServicos;
	List<Empresa> listarEmpresas;
	List<Pessoa> listarPessoas;
	List<Etapa> listarEtapas;
	List<Status> listarStatus;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void preencherCombo(JComboBox combo, Session session, JComboBox comboEstado){
    	combo.removeAllItems();
    	combo.addItem(combo.getName());
    	switch(combo.getName()){
    	case "Atendente":
    		listarUsuarios= new UsuarioDAO().listar(Usuario.class, session);
    		atendentes = new HashMap();
    		if(!listarUsuarios.isEmpty()){
    			listarUsuarios.forEach(c->{
    				atendentes.put(c.getLogin(),c);
    				combo.addItem(c.getLogin());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "AtendenteCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarUsuarios.isEmpty()){
    			listarUsuarios.forEach(c->{
    				combo.addItem(c.getLogin());
    			});
    		}
    		break;
    	case "Categoria":
    		listarCategorias = new CategoriaDAO().listar(Categoria.class, session);
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
    		listarEmpresas = new EmpresaDAO().listar(Empresa.class, session);
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
    		listarEtapas = new EtapaDAO().listar(Etapa.class, session);
    		etapas = new HashMap();
    		if(!listarEtapas.isEmpty()){
    			listarEtapas.forEach(c->{
    				etapas.put(c.getNome(),c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Logradouro":
    		combo.removeAllItems();
    		combo.addItem("");
    		combo.setModel(new DefaultComboBoxModel<>(DefaultEnumModel.Logradouro.values()));
    		combo.setSelectedItem(DefaultEnumModel.Logradouro.valueOf("Rua"));
    		break;
    	case "Nivel":
    		listarNiveis =	new NivelDAO().listar(Nivel.class, session);
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
    		listarOrigens =	new OrigemDAO().listar(Origem.class, session);
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
    		listarPessoas = new PessoaDAO().listar(Pessoa.class, session);
    		pessoas = new HashMap();
    		if(!listarPessoas.isEmpty()){
    			listarPessoas.forEach(c->{
    				pessoas.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
    		break;
    	case "Produtos/Servicos":
    		listarServicos = new ServicoDAO().listar(Servico.class,session);
    		servicos = new HashMap();
    		if(!listarServicos.isEmpty()){
    			listarServicos.forEach(c->{
    				servicos.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
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
    		listarStatus = new StatusDAO().listar(Status.class, session);
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
    			List<Cidade> listarCidades = session.createQuery("from Cidade c where c.estado=:nomeEstado")
						.setParameter("nomeEstado", comboEstado.getSelectedItem().toString()).getResultList();
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
				new Autocomplete(combo);
    		}
    		break;
    	case "Estado":
    		combo.removeAllItems();
    		combo.addItem("");
    		List<Cidade> listaCidades = session.createQuery("from Cidade").getResultList();
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
	
	public Etapa getEtapa(String key){
		return etapas.get(key);
	}

	public Status getStatus(String key){
		return statusMapa.get(key);
	}
	/**
	 * @return the atendentes
	 */
	public Usuario getAtendentes(String key) {
		return atendentes.get(key);
	}
}
