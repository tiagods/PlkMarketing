/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;


import static br.com.tiagods.view.PessoasView.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.CategoriaDao;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.EmpresaDao;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.OrigemDao;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PessoaDao;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.ServicoDao;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.UsuarioDao;
	
/**
 *
 * @author Tiago
 */
public class ControllerPessoas implements ActionListener,KeyListener{
	String[] tableHeader = {"ID","NOME","CATEGORIA","ORIGEM","CRIADO EM","ATENDENTE"};
	String[][] values;

	ArrayList<ArrayList> teste;
	
    public void iniciar(){
    	long inicio = System.currentTimeMillis();
    	
    	String[] args = {"Categoria","Origem","Empresa","Produtos/Servicos","Responsavel"};
    	for(String a:args)
    		invocarCombo(a);
    	List<Pessoa>pessoas = new PessoaDao().getLista();
    	teste = criarTeste();
    	preencherTabela(pessoas, teste);
    	
    	long fim = System.currentTimeMillis();
    	
    	System.out.println("Fim da view Pessoas: "+(fim-inicio));
    	
    	
    }
    private ArrayList<ArrayList> criarTeste(){
    	ArrayList<ArrayList> lista = new ArrayList<ArrayList>();
    	lista.add(new ArrayList());
    	lista.get(0).add("1");
    	lista.get(0).add("Antonio Goncalves");
    	lista.get(0).add("Cliente em potencial");
    	lista.get(0).add("Site");
    	lista.get(0).add("18/11/2015");
    	lista.get(0).add("Tiago");
    	
    	lista.add(new ArrayList());
    	lista.get(1).add("2");
    	lista.get(1).add("Tiago Martins");
    	lista.get(1).add("Cliente efetivo");
    	lista.get(1).add("Feira");
    	lista.get(1).add("11/04/2015");
    	lista.get(1).add("Admin");
    	
    	lista.add(new ArrayList());
    	lista.get(2).add("3");
    	lista.get(2).add("Diego Souza");
    	lista.get(2).add("Parceiro");
    	lista.get(2).add("Outros");
    	lista.get(2).add("11/04/2015");
    	lista.get(2).add("Admin");
    	
    	return lista;
    	
    }
    public void invocarCombo(String value){
    	switch(value){
    	case "Categoria":
    		List<Categoria> listaCategorias = new CategoriaDao().getLista();
    		cbCategoria.removeAllItems();
    		cbCategoria.addItem(value);
    		cbCategoria.addItem("Todos");
    		listaCategorias.forEach(c->{
    			cbCategoria.addItem(c.getNome());
    		});
    		cbCategoria.setSelectedItem(value);
    		break;
    	case "Origem":
    		List<Origem> listaOrigems =	new OrigemDao().getLista();
    		cbOrigem.removeAllItems();
    		cbOrigem.addItem(value);
    		cbOrigem.addItem("Todos");
    		listaOrigems.forEach(c->{
    			cbOrigem.addItem(c.getNome());
    		});
    		cbOrigem.setSelectedItem(value);
    		break;
    	case "Empresa":
    		List<Empresa> listaEmpresas = new EmpresaDao().getLista();
    		cbEmpresa.removeAllItems();
    		cbEmpresa.addItem(value);
    		cbEmpresa.addItem("Todos");
    		listaEmpresas.forEach(c->{
    			cbEmpresa.addItem(c.getNome());
    		});
    		cbEmpresa.setSelectedItem(value);
    		break;
    	case "Produtos/Servicos":
    		List<Servico> listaServicos = new ServicoDao().getLista();
    		cbProdServicos.removeAllItems();;
    		cbProdServicos.addItem(value);
    		cbProdServicos.addItem("Todos");
    		listaServicos.forEach(c->{
    			cbProdServicos.addItem(c.getNome());
    		});
    		cbProdServicos.setSelectedItem(value);
    		break;
    	case "Responsavel":
    		List<Usuario> listarUsuario = new UsuarioDao().getLista();
    		cbAtendente.removeAllItems();;
    		cbAtendente.addItem(value);
    		cbAtendente.addItem("Todos");
    		listarUsuario.forEach(c->{
    			cbAtendente.addItem(c.getNome());
    		});
    		cbAtendente.setSelectedItem(value);
    		break;
    	}	
    }
    
    private void preencherTabela(List<Pessoa> pessoas, ArrayList<ArrayList> lista){
    	tbPrincipal.setModel(new DefaultTableModel(new String[][]{}, tableHeader));
    	DefaultTableModel tbm = (DefaultTableModel)tbPrincipal.getModel();
    	for(int i=tbm.getRowCount()-1; i>=0; i--){
	        tbm.removeRow(i);
	    }
    	for(int i=0;i<lista.size();i++){
    		tbm.addRow(new Object[1]);
    		for(int j=0;j<lista.get(i).size();j++){
    			tbm.setValueAt(lista.get(i).get(j).toString(), i, j);
    		}
    	}
    	
//    	if(pessoas.size()>=1){
//    		for(int i=0;i<pessoas.size();i++){
//    			tbm.addRow(new String[1]);
//    			String data= "";
//    			if(pessoas.get(i).getCriadoEm()!=null){
//    				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//    				data = sdf.format(pessoas.get(i).getCriadoEm());
//    			}
//    			tbm.setValueAt(pessoas.get(i).getId(), i, 0);
//    			tbm.setValueAt(pessoas.get(i).getNome(), i, 1);
//    			tbm.setValueAt(pessoas.get(i).getCategoria().getNome(), i, 2);
//    			tbm.setValueAt(data, i, 3);
//    			tbm.setValueAt(pessoas.get(i).getAtendente(), i, 4);
//    		}
//    	}
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Buscar":
			if(txBuscar.getText().trim().length()>=3){
				List<Integer> lista = new ArrayList<Integer>();
				
				for(int i=0;i<teste.size();i++){
					if(teste.get(i).get(1).toString().toUpperCase().contains(txBuscar.getText().trim().toUpperCase()))
						lista.add(i);
				}
				DefaultTableModel tbm = (DefaultTableModel)tbPrincipal.getModel();
				for(int i=tbm.getRowCount()-1; i>=0; i--){
			        tbm.removeRow(i);
			    }
				for(int i=0;i<lista.size();i++){
					tbm.addRow(new Object[1]);
					for(int j=0;j<teste.get(i).size();j++){
						tbm.setValueAt(teste.get(lista.get(i)).get(j).toString(), i, j);
					}
				}

			}
			break;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(txBuscar.getText().trim().length()>=2){
			List<Integer> lista = new ArrayList<Integer>();
			
			for(int i=0;i<teste.size();i++){
				if(teste.get(i).get(1).toString().toUpperCase().contains(txBuscar.getText().trim().toUpperCase()))
					lista.add(i);
			}
			DefaultTableModel tbm = (DefaultTableModel)tbPrincipal.getModel();
			for(int i=tbm.getRowCount()-1; i>=0; i--){
		        tbm.removeRow(i);
		    }
			for(int i=0;i<lista.size();i++){
				tbm.addRow(new Object[1]);
				for(int j=0;j<teste.get(i).size();j++){
					tbm.setValueAt(teste.get(lista.get(i)).get(j).toString(), i, j);
				}
			}
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
