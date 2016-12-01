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
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modeldao.CategoriaDAO;
import br.com.tiagods.modeldao.EmpresaDAO;
import br.com.tiagods.modeldao.NivelDAO;
import br.com.tiagods.modeldao.OrigemDAO;
import br.com.tiagods.modeldao.ServicoDAO;
import br.com.tiagods.modeldao.UsuarioDAO;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;
/*
 * Essa classe é invocada pelos controllers de pessoa e empresa
 */
public class ControllerEmpresaPessoa {
	Map <String,Categoria> categorias;
	Map <String,Nivel> niveis;
	Map <String,Origem> origens;
	Map <String,Servico> servicos;
	Map <String,Usuario> atendentes;
	Map <String,Cidade> cidades;
	Map <String,Empresa> empresas;
	
	List<Usuario> listarUsuarios;
	List<Categoria> listarCategorias;
	List<Nivel> listarNiveis;
	List<Origem> listarOrigens;
	List<Servico> listarServicos;
	List<Empresa> listarEmpresas;
	
	public void preencherTabela(List list, JTable table, Class object,JLabel txContadorRegistros){
		if(object == Pessoa.class){
			List<Pessoa> lista = (List<Pessoa>)list;
			String[] tableHeader = {"ID","NOME","CATEGORIA","ORIGEM","CRIADO EM","ATENDENTE"};
			
			DefaultTableModel model = new DefaultTableModel(tableHeader,0){
				boolean[] canEdit = new boolean[]{
					false,false,false,false,false,false
				};
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit [columnIndex];
				}
			};
			if(!lista.isEmpty()){
				for(int i=0;i<lista.size();i++){
					Pessoa p = lista.get(i);
					Object[] linha = new Object[6];
					linha[0] = ""+p.getId(); 
					linha[1] = p.getNome();
					linha[2] = p.getPessoaFisica().getCategoria()==null?"":p.getPessoaFisica().getCategoria().getNome();
					linha[3] = p.getPessoaFisica().getOrigem()==null?"":p.getPessoaFisica().getOrigem().getNome();
					try{
						Date criadoEm = p.getPessoaFisica().getCriadoEm();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						linha[4] = sdf.format(criadoEm);
					}catch (NumberFormatException e) {
						linha[4] = "";
					}
					linha[5] = p.getPessoaFisica().getAtendente()==null?"":p.getPessoaFisica().getAtendente().getLogin();
					model.addRow(linha);
					txContadorRegistros.setText("Total: "+lista.size()+" registros");
					table.setModel(model);
				}
			}
		}else{
			List<Empresa> lista = (List<Empresa>)list;
			String[] tableHeader = {"ID","NOME","NIVEL","CATEGORIA","ORIGEM","CRIADO EM","ATENDENTE"};
			
			DefaultTableModel model = new DefaultTableModel(tableHeader,0){
				boolean[] canEdit = new boolean[]{
					false,false,false,false,false,false,false
				};
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit [columnIndex];
				}
			};
			if(!lista.isEmpty()){
				for(int i=0;i<lista.size();i++){
					Empresa em= lista.get(i);
					Object[] linha = new Object[7];
					linha[0] = ""+em.getId(); 
					linha[1] = em.getNome();
					linha[2] = em.getNivel()==null?"":em.getNivel().getNome();
					linha[3] = em.getPessoaJuridica().getCategoria()==null?"":em.getPessoaJuridica().getCategoria().getNome();
					linha[4] = em.getPessoaJuridica().getOrigem()==null?"":em.getPessoaJuridica().getOrigem().getNome();
					try{
						Date criadoEm = em.getPessoaJuridica().getCriadoEm();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						linha[5] = sdf.format(criadoEm);
					}catch (NumberFormatException e) {
						linha[5] = "";
					}
					linha[6] = em.getPessoaJuridica().getAtendente()==null?"":em.getPessoaJuridica().getAtendente().getLogin();
					model.addRow(linha);
					txContadorRegistros.setText("Total: "+lista.size()+" registros");
					table.setModel(model);
				}
			}
		}
		table.setAutoCreateRowSorter(true);
		table.setSelectionBackground(Color.CYAN);

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void preencherCombo(JComboBox combo, Session session, Usuario usuario, Nivel nivel,
			Categoria categoria, Origem origem, Servico servico, Endereco endereco,JComboBox comboEstado){
    	combo.removeAllItems();
    	combo.addItem(combo.getName());
    	//ombo.addItem("Todos");
    	switch(combo.getName()){
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
    	case "Produtos/Serviços":
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
    		if(usuario!=null){
    			combo.setSelectedItem(usuario.getLogin());
    		}
    		else
    			combo.setSelectedItem(UsuarioLogado.getInstance().getUsuario());
    		break;
    	case "NivelCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarNiveis.isEmpty()){
    			listarNiveis.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		if(nivel!=null){
    			combo.setSelectedItem(nivel.getNome());
    		}
    		else
    			combo.setSelectedItem("");
    		break;
    	case "CategoriaCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarCategorias.isEmpty()){
    			listarCategorias.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		if(categoria!=null){
    			combo.setSelectedItem(categoria.getNome());
    		}
    		else
    			combo.setSelectedItem("");
    		break;
    	case "OrigemCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarOrigens.isEmpty()){
    			listarOrigens.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		if(origem!=null){
    			combo.setSelectedItem(origem.getNome());
    		}
    		else
    			combo.setSelectedItem("");
    		break;
    	case "ServicosCad":
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!listarServicos.isEmpty()){
    			listarServicos.forEach(c->{
    				combo.addItem(c.getNome());
    			});
    		}
    		if(servico!=null){
    			combo.setSelectedItem(servico.getNome());
    		}
    		else
    			combo.setSelectedItem("");
    		break;
    	case "Cidade":
    		combo.addItem("");
    		if(comboEstado.getSelectedItem()!=null){
    			combo.removeAllItems();
				List<Cidade> listarCidades = session.createQuery("from Cidade c where c.estado=:nomeEstado")
						.setParameter("nomeEstado", (String)comboEstado.getSelectedItem()).getResultList();
				cidades = new HashMap();
				combo.addItem("");
				if(!listarCidades.isEmpty()){
					listarCidades.forEach(c->{
						combo.addItem(c.getNome());
						cidades.put(c.getNome(), c);
					});
				}
				combo.setSelectedItem("");
					
				if(endereco!=null){
    				combo.setSelectedItem(endereco.getCidade().getNome());
    			}
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
    		if(endereco!=null){
    			combo.setSelectedItem(endereco.getCidade().getEstado());
    		}
    		else
    			combo.setSelectedItem("");
    		break;
    	case "Logradouro":
    		combo.removeAllItems();
    		combo.addItem("");
    		combo.setModel(new DefaultComboBoxModel<>(DefaultModelComboBox.Logradouro.values()));
    		combo.setSelectedItem("Rua");
    		if(endereco!=null){
    			combo.setSelectedItem(endereco.getLogradouro());
    		}
    		else
    			combo.setSelectedItem(DefaultModelComboBox.Logradouro.valueOf("Rua"));
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
	 * @return the atendentes
	 */
	public Usuario getAtendentes(String key) {
		return atendentes.get(key);
	}
}
