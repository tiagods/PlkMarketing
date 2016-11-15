package br.com.tiagods.modelDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.tiagods.controller.UsuarioLogado;
import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;

public class Padrao {
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
	
	public void preencherTabela(List list, JTable tbPessoas){
		if(!list.isEmpty()){
			if(list.get(0) instanceof Pessoa){
				List<Pessoa> lista = (List<Pessoa>)list;
				String[] tableHeader = {"ID","NOME","CATEGORIA","ORIGEM","CRIADO EM","ATENDENTE"};
				String[][] data = new String[lista.size()][tableHeader.length];
				for(int i=0;i<lista.size();i++){
					Pessoa p = lista.get(i);
					data[i][0] = ""+p.getId(); 
					data[i][1] = p.getNome();
					data[i][2] = p.getCategoria()==null?"":p.getCategoria().getNome();
					data[i][3] = p.getOrigem()==null?"":p.getOrigem().getNome();
					data[i][4] = ""+p.getCriadoEm()==null?"":""+p.getCriadoEm();
					data[i][5] = p.getAtendente()==null?"":p.getAtendente().getLogin();
				}
				DefaultTableModel model = new DefaultTableModel(data,tableHeader);
				tbPessoas.setModel(model);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void preencherCombo(JComboBox combo, Session session, Usuario usuario, Nivel nivel,
			Categoria categoria, Origem origem, Servico servico, Endereco endereco,JComboBox comboEstado){
    	combo.removeAllItems();
    	combo.addItem(combo.getName());
    	combo.addItem("Todos");
    	switch(combo.getName()){
    	case "Categoria":
    		listarCategorias = new MyDao().listar("Categoria", session);
    		categorias = new HashMap<String,Categoria>();
    		if(!listarCategorias.isEmpty()){
    			listarCategorias.forEach(c->{
    				categorias.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Nivel":
    		listarNiveis =	new MyDao().listar("Nivel", session);
    		niveis = new HashMap<String,Nivel>();
    		if(!listarNiveis.isEmpty()){
    			listarNiveis.forEach(c->{
    				niveis.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Origem":
    		listarOrigens =	new MyDao().listar("Origem", session);
    		origens = new HashMap<String,Origem>();
    		if(!listarOrigens.isEmpty()){
    			listarOrigens.forEach(c->{
    				origens.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Empresa":
    		listarEmpresas = new MyDao().listar("Empresa", session);
    		empresas = new HashMap<String,Empresa>();
    		if(!listarEmpresas.isEmpty()){
    			listarEmpresas.forEach(c->{
    				empresas.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Produtos/Serviços":
    		listarServicos = new MyDao().listar("Servico", session);
    		servicos = new HashMap<String,Servico>();
    		if(!listarServicos.isEmpty()){
    			listarServicos.forEach(c->{
    				servicos.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    		}
        	combo.setSelectedItem(combo.getName());
    		break;
    	case "Atendente":
    		listarUsuarios= new MyDao().listar("Usuario", session);
    		atendentes = new HashMap<String,Usuario>();
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
    		combo.removeAllItems();
    		combo.addItem("");
    		if(!comboEstado.getSelectedItem().toString().equals("")){
    			List<Cidade> listarCidades = session.createQuery("from Cidade c where c.estado=:nomeEstado")
    					.setParameter("nomeEstado", comboEstado.getSelectedItem().toString()).getResultList();
    					cidades = new HashMap<String,Cidade>();
    			listarCidades.forEach(c->{
    				cidades.put(c.getNome(), c);
    				combo.addItem(c.getNome());
    			});
    			if(endereco!=null){
    				combo.setSelectedItem(endereco.getCidade().getNome());
    			}
    		}
    		break;
    	case "Estado":
    		combo.removeAllItems();
    		combo.addItem("");
    		combo.setModel(new DefaultComboBoxModel<>(DefaultModelComboBox.Estados.values()));
    		if(endereco!=null){
    			combo.setSelectedItem(DefaultModelComboBox.Estados.valueOf(endereco.getCidade().getEstado()));
    		}
    		else
    			combo.setSelectedItem(DefaultModelComboBox.Estados.valueOf("SP"));
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
	 * @return the atendentes
	 */
	public Map<String, Usuario> getAtendentes() {
		return atendentes;
	}
}
