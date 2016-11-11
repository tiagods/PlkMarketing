/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;


import static br.com.tiagods.view.PessoasView.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.MyDao;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;
/**
 *
 * @author Tiago
 */
public class ControllerPessoas implements ActionListener,KeyListener{
	String[] tableHeader = {"ID","NOME","CATEGORIA","ORIGEM","CRIADO EM","ATENDENTE"};
	String[][] values;

	Session session=null;
	public void iniciar(){
    	long inicio = System.currentTimeMillis();
    	session = HibernateFactory.getSession();
    	session.beginTransaction();
    	String[] args = {"Categoria","Origem","Empresa","Produtos/Servicos","Responsavel"};
    	cbLogradouro.setSelectedItem(DefaultModelComboBox.Logradouro.Rua);
    	for(String a:args)
    		invocarCombo(a);
    	
    	
    	//preencherTabela(null, teste);
    	long fim = System.currentTimeMillis();
    	session.close();
    	System.out.println("Fim da view Pessoas: "+(fim-inicio));
    	
    	
    }
    public void invocarCombo(String value){
    	switch(value){
    	case "Categoria":
    		List<Categoria> listaCategorias = new MyDao().listar("Categoria", session);
    		cbCategoria.removeAllItems();
    		cbCategoria.addItem(value);
    		cbCategoria.addItem("Todos");
    		listaCategorias.forEach(c->{
    			cbCategoria.addItem(c.getNome());
    		});
    		cbCategoria.setSelectedItem(value);
    		break;
    	case "Origem":
    		List<Origem> listaOrigems =	new MyDao().listar("Origem", session);
    		cbOrigem.removeAllItems();
    		cbOrigem.addItem(value);
    		cbOrigem.addItem("Todos");
    		listaOrigems.forEach(c->{
    			cbOrigem.addItem(c.getNome());
    		});
    		cbOrigem.setSelectedItem(value);
    		break;
    	case "Empresa":
    		List<Empresa> listaEmpresas = new MyDao().listar("Empresa", session);
    		cbEmpresa.removeAllItems();
    		cbEmpresa.addItem(value);
    		cbEmpresa.addItem("Todos");
    		listaEmpresas.forEach(c->{
    			cbEmpresa.addItem(c.getNome());
    		});
    		cbEmpresa.setSelectedItem(value);
    		break;
    	case "Produtos/Servicos":
    		List<Servico> listaServicos = new MyDao().listar("Servico", session);
    		cbProdServicos.removeAllItems();;
    		cbProdServicos.addItem(value);
    		cbProdServicos.addItem("Todos");
    		listaServicos.forEach(c->{
    			cbProdServicos.addItem(c.getNome());
    		});
    		cbProdServicos.setSelectedItem(value);
    		break;
    	case "Responsavel":
    		List<Usuario> listarUsuario = new MyDao().listar("Usuario", session);
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
				
			}			
			break;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(txBuscar.getText().trim().length()>=2){
			
		}else if(txBuscar.getText().trim().length()==1){
			
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
