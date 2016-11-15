/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;


import static br.com.tiagods.view.PessoasView.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.hibernate.Session;
import static br.com.tiagods.view.MenuView.jDBody;
import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.modelDAO.MyDao;
import br.com.tiagods.modelDAO.Padrao;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;
/**
 *
 * @author Tiago
 */
public class ControllerPessoas implements ActionListener,KeyListener,ItemListener{
	Padrao padrao = new Padrao();
	List<Pessoa> listaPessoas;
	
	Session session=null;
	
	Pessoa pessoa= null;
	Pessoa pessoaBackup;
	
	public void iniciar(Pessoa pessoa){
		this.pessoa=pessoa;
		long inicio = System.currentTimeMillis();
    	session = HibernateFactory.getSession();
    	session.beginTransaction();
    	
    	JPanel[] panels = {pnCabecalho,pnPrincipal};
    	for(JPanel panel : panels){
    		for(int i=0;i<panel.getComponentCount();i++){
    			if(panel.getComponent(i) instanceof JComboBox){
    				if(pessoa==null){
    					padrao.preencherCombo((JComboBox)panel.getComponent(i),session, null,
    							null,null,null,null,null,cbEstado);
    				}
    				else{
    					padrao.preencherCombo((JComboBox)panel.getComponent(i),session, pessoa.getAtendente(),
    							pessoa.getNivel(),pessoa.getCategoria(),pessoa.getOrigem(),pessoa.getServico(),pessoa.getEndereco(), cbEstado);
    				}
    			}
    		}
    	}
    	listaPessoas = new MyDao().listar("Pessoa", session);
    	padrao.preencherTabela(listaPessoas, tbPrincipal);
    	preencherFormulario(listaPessoas.get(0));
    	long fim = System.currentTimeMillis();
    	session.close();
    	System.out.println("Fim da view Pessoas: "+(fim-inicio));
    }
	private void preencherFormulario(Pessoa pessoa){
		txCodigo.setText(""+pessoa.getId());
		SimpleDateFormat conversor = new SimpleDateFormat("dd/MM/yyyy");
		txCadastradoPor.setText(pessoa.getCriadoPor()==null?"":pessoa.getCriadoPor().getNome());
		//txDataCadastro.setText(conversor.format(pessoa.getCriadoEm()));
		
		txNome.setText(pessoa.getNome());
		
		cbAtendenteCad.setSelectedItem(pessoa.getAtendente());
		
		txCpf.setText(pessoa.getCpf());
		txDataNascimento.setText(pessoa.getDataNascimento());
		
		cbOrigemCad.setSelectedItem(pessoa.getOrigem()==null?"":pessoa.getOrigem().getNome());
		cbCategoriaCad.setSelectedItem(pessoa.getCategoria()==null?"":pessoa.getCategoria().getNome());
		cbProdServicosCad.setSelectedItem(pessoa.getServico()==null?"":pessoa.getServico().getNome());
		
		txTelefone.setText(pessoa.getTelefone());
		txCelular.setText(pessoa.getCelular());
		txEmail.setText(pessoa.getEmail());
		txSite.setText(pessoa.getSite());
		
		Endereco end = pessoa.getEndereco();
		cbLogradouro.setSelectedItem(DefaultModelComboBox.Logradouro.valueOf(end.getLogradouro()));
		txLogradouro.setText(end.getNome());
		txNum.setText(end.getNumero());
		txComplemento.setText(end.getComplemento());
		
		cbEstado.setSelectedItem(end.getCidade()==null?"":DefaultModelComboBox.Estados.valueOf(end.getCidade().getEstado()));
		cbCidade.setSelectedItem(end.getCidade()==null?"":end.getCidade().getNome());
	}
    
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Buscar":
			if(txBuscar.getText().trim().length()>=3){
				pesquisar();
			}			
			break;
		case "Novo":
			this.pessoaBackup=pessoa;
			novoEditar();
			break;
		case "Editar":
			novoEditar();
			break;
		case "Cancelar":
			if(pessoaBackup!=null){
				preencherFormulario(pessoaBackup);
				salvarCancelar();
			}
			break;
		case "Excluir":
			int escolha = JOptionPane.showConfirmDialog(jDBody, "Você deseja excluir esse registro? "
					+ "\nTodos os historicos serão perdido, lembre-se que essa ação não terá mais volta!",
					"Pedido de Exclusao", JOptionPane.YES_NO_OPTION);
			if(escolha==JOptionPane.YES_OPTION){
				//remove
			}
			break;
			
		}
	}
	private void salvarCancelar(){
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnEditar.setEnabled(true);
	}
	private void novoEditar(){
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(txBuscar.getText().trim().length()>=3 && e.getKeyCode()!=KeyEvent.VK_BACK_SPACE){
			pesquisar();
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
	private void pesquisar(){
		List<Pessoa> lista = new ArrayList<Pessoa>();
		for(int i =0;i<listaPessoas.size();i++){
			String texto = txBuscar.getText().trim().toUpperCase();
			if(listaPessoas.get(i).getNome().substring(0,texto.length()).toUpperCase().equals(texto)){
				lista.add(listaPessoas.get(i));
			}
		}
		if(!lista.isEmpty()){
			padrao.preencherTabela(lista, tbPrincipal);
		}else{
			JOptionPane.showMessageDialog(jDBody,"Não foi encontrado registros com o criterio informado",
					"Nenhum registro!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		switch(e.getSource().toString()){
		case "Estado":
			padrao.preencherCombo(cbCidade,session, null,
					null,null,null,null,null,cbEstado);
			break;
		}
		
	}
    
}
