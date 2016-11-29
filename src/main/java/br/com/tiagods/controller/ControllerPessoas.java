/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.MenuView.jDBody;
import static br.com.tiagods.view.PessoasView.btnCancelar;
import static br.com.tiagods.view.PessoasView.btnEditar;
import static br.com.tiagods.view.PessoasView.btnNovo;
import static br.com.tiagods.view.PessoasView.btnExcluir;
import static br.com.tiagods.view.PessoasView.btnSalvar;
import static br.com.tiagods.view.PessoasView.cbAtendenteCad;
import static br.com.tiagods.view.PessoasView.cbCategoriaCad;
import static br.com.tiagods.view.PessoasView.cbCidade;
import static br.com.tiagods.view.PessoasView.cbEstado;
import static br.com.tiagods.view.PessoasView.cbLogradouro;
import static br.com.tiagods.view.PessoasView.cbOrigemCad;
import static br.com.tiagods.view.PessoasView.cbProdServicosCad;
import static br.com.tiagods.view.PessoasView.pnCabecalho;
import static br.com.tiagods.view.PessoasView.pnPrincipal;
import static br.com.tiagods.view.PessoasView.tbPrincipal;
import static br.com.tiagods.view.PessoasView.txBairro;
import static br.com.tiagods.view.PessoasView.txBuscar;
import static br.com.tiagods.view.PessoasView.txCadastradoPor;
import static br.com.tiagods.view.PessoasView.txCelular;
import static br.com.tiagods.view.PessoasView.txCep;
import static br.com.tiagods.view.PessoasView.txCodigo;
import static br.com.tiagods.view.PessoasView.txComplemento;
import static br.com.tiagods.view.PessoasView.txCpf;
import static br.com.tiagods.view.PessoasView.txDataCadastro;
import static br.com.tiagods.view.PessoasView.txDataNascimento;
import static br.com.tiagods.view.PessoasView.txEmail;
import static br.com.tiagods.view.PessoasView.txLogradouro;
import static br.com.tiagods.view.PessoasView.txNome;
import static br.com.tiagods.view.PessoasView.txNum;
import static br.com.tiagods.view.PessoasView.txSite;
import static br.com.tiagods.view.PessoasView.txTelefone;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.modelDAO.EmpresaPessoaDAO;
import br.com.tiagods.modelDAO.PessoaDAO;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;
/**
 *
 * @author Tiago
 */
public class ControllerPessoas implements ActionListener,KeyListener,ItemListener,MouseListener{
	EmpresaPessoaDAO padrao = new EmpresaPessoaDAO();
	List<Pessoa> listaPessoas;
	
	Session session=null;
	Pessoa pessoa= null;
	Pessoa pessoaBackup;
	
	public void iniciar(Pessoa pessoa){
		this.pessoa=pessoa;
    	session = HibernateFactory.getSession();
    	session.beginTransaction();
		JPanel[] panels = {pnCabecalho,pnPrincipal};
    	for(JPanel panel : panels){
    		preencherComboBox(panel);
    	}
    	listaPessoas = new PessoaDAO().listar("Pessoa", session);
    	padrao.preencherTabela(listaPessoas, tbPrincipal, Pessoa.class);
    	if(!listaPessoas.isEmpty() && pessoa==null){
    		preencherFormulario(listaPessoas.get(0));
    	}
    	else if(pessoa!=null){
    		preencherFormulario(pessoa);
    	}
    	session.close();
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
			pessoa = null;
			novoEditar();
			break;
		case "Editar":
			novoEditar();
			break;
		case "Cancelar":
			salvarCancelar();
			break;
		case "Excluir":
			if(pessoa!=null){
				invocarExclusao();
			}
			break;
		case "Salvar":
			invocarSalvamento();
	    	break;
			default:
				break;
		}
	}
	private void preencherComboBox(JPanel panel){
		for(int i=0;i<panel.getComponentCount();i++){
			if(panel.getComponent(i) instanceof JComboBox){
				if(pessoa==null){
					padrao.preencherCombo((JComboBox)panel.getComponent(i),session, null,
							null,null,null,null,null,cbEstado);
				}
				else{
					padrao.preencherCombo((JComboBox)panel.getComponent(i),session, 
							pessoa.getPessoaFisica().getAtendente(),
							null,pessoa.getPessoaFisica().getCategoria(),
							pessoa.getPessoaFisica().getOrigem(),
							pessoa.getPessoaFisica().getServico(),
							pessoa.getEndereco(), cbEstado);
				}
			}
		}
	}
	private void preencherFormulario(Pessoa pessoa){
		txCodigo.setText(""+pessoa.getId());
		SimpleDateFormat conversor = new SimpleDateFormat("dd/MM/yyyy");
		txCadastradoPor.setText(pessoa.getPessoaFisica().getCriadoPor()==null?"":pessoa.getPessoaFisica().getCriadoPor().getLogin());
		
		txDataCadastro.setText(conversor.format(pessoa.getPessoaFisica().getCriadoEm()));
		txNome.setText(pessoa.getNome());
		cbAtendenteCad.setSelectedItem(pessoa.getPessoaFisica().getAtendente().getLogin());
		txCpf.setText(pessoa.getCpf());
		txDataNascimento.setText(pessoa.getDataNascimento());
		cbOrigemCad.setSelectedItem(pessoa.getPessoaFisica().getOrigem()==null?"":pessoa.getPessoaFisica().getOrigem().getNome());
		cbCategoriaCad.setSelectedItem(pessoa.getPessoaFisica().getCategoria()==null?"":pessoa.getPessoaFisica().getCategoria().getNome());
		cbProdServicosCad.setSelectedItem(pessoa.getPessoaFisica().getServico()==null?"":pessoa.getPessoaFisica().getServico().getNome());
		txTelefone.setText(pessoa.getPessoaFisica().getTelefone());
		txCelular.setText(pessoa.getPessoaFisica().getCelular());
		txEmail.setText(pessoa.getPessoaFisica().getEmail());
		txSite.setText(pessoa.getPessoaFisica().getSite());
		
		Endereco end = pessoa.getEndereco();
		cbLogradouro.setSelectedItem(DefaultModelComboBox.Logradouro.valueOf(end.getLogradouro()));
		txLogradouro.setText(end.getNome());
		txNum.setText(end.getNumero());
		txComplemento.setText(end.getComplemento());
		
		cbEstado.setSelectedItem(end.getCidade()==null?"":end.getCidade().getEstado());
		cbCidade.setSelectedItem(end.getCidade()==null?"":end.getCidade().getNome());
	}
	private void salvarCancelar(){
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		desbloquerFormulario(false, pnPrincipal);
		if(pessoaBackup!=null){
			pessoa=pessoaBackup;
			preencherFormulario(pessoa);
		}
	}
	private void novoEditar(){
		limparFormulario(pnPrincipal);
		desbloquerFormulario(true, pnPrincipal);
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnExcluir.setEnabled(false);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(txBuscar.getText().trim().length()>=2 && e.getKeyCode()!=KeyEvent.VK_BACK_SPACE){
			pesquisar();
		}	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		new UnsupportedOperationException();
	}
	private void pesquisar(){
		List<Pessoa> lista = new ArrayList<>();
		for(int i =0;i<listaPessoas.size();i++){
			String texto = txBuscar.getText().trim().toUpperCase();
			if(listaPessoas.get(i).getNome().substring(0,texto.length()).equalsIgnoreCase(texto)){
				lista.add(listaPessoas.get(i));
			}
		}
		if(!lista.isEmpty()){
			padrao.preencherTabela(lista, tbPrincipal, Pessoa.class);
		}else{
			JOptionPane.showMessageDialog(jDBody,"Não foi encontrado registros com o criterio informado",
					"Nenhum registro!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//atualizador de combo box
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED){
			JComboBox combo = (JComboBox)e.getSource();
			if(cbEstado.getSelectedItem()!=null){
				boolean openHere = recebeSessao();
				switch(combo.getName()){
					case "Estado":
						padrao.preencherCombo(cbCidade,session, null,null,null,null,null,null,cbEstado);
						break;
					default:
						break;
				}
				fechaSessao(openHere);
			}
		}
		
	}
	private boolean recebeSessao(){
		boolean open = false;
		if(!session.isOpen()){
			session = HibernateFactory.getSession();
			session.beginTransaction();
			open = true;
		}
		return open;
	}
	private void fechaSessao(boolean close){
		if(close){ 
			session.close();
		}
	}
	
	private void limparFormulario(Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JTextField){
				((JTextField)c).setText("");
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setSelectedItem("");
			}
			else if(c instanceof JTextArea){
				((JTextArea)c).setText("");
			}
			else if(c instanceof JScrollPane){
				limparFormulario((Container)c);
			}	
		}
		txCodigo.setText("");
		txCadastradoPor.setText("");
		txDataCadastro.setText("");
	}
	private void desbloquerFormulario(boolean desbloquear,Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JTextField){
				((JTextField)c).setEditable(desbloquear);;
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setEnabled(desbloquear);;
			}
			else if(c instanceof JTextArea){
				((JTextArea)c).setEnabled(desbloquear);;
			}
			else if(c instanceof JScrollPane){
				desbloquerFormulario(desbloquear,(Container)c);
			}	
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int valor = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0));
		if(valor>0){
			boolean open = recebeSessao();
			PessoaDAO dao = new PessoaDAO();
			pessoa = (Pessoa)dao.receberObjeto(Pessoa.class, valor, session);
			preencherFormulario(pessoa);
			fechaSessao(open);
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		new UnsupportedOperationException();
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		new UnsupportedOperationException();
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		new UnsupportedOperationException();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		new UnsupportedOperationException();
	}
    private void invocarSalvamento(){
    	PfPj pessoaFisica;
		Endereco endereco = new Endereco();
		if("".equals(txCodigo.getText())) {
			pessoa = new Pessoa();
			pessoaFisica = new PfPj();
			pessoaFisica.setCriadoEm(new Date());
			pessoaFisica.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		}
		else{
			pessoaFisica = pessoa.getPessoaFisica();
		}
		pessoaFisica.setTelefone(txTelefone.getText());
		pessoaFisica.setCelular(txCelular.getText());
		pessoaFisica.setEmail(txEmail.getText());
		pessoaFisica.setSite(txSite.getText());
		//se nao tem usuario cadastrado
		if("".equals(cbAtendenteCad.getSelectedItem())){
			pessoaFisica.setAtendente(UsuarioLogado.getInstance().getUsuario());
		}
		else
			pessoaFisica.setAtendente(padrao.getAtendentes((String)cbAtendenteCad.getSelectedItem()));
		
		if(!"".equals(cbProdServicosCad.getSelectedItem()))
				pessoaFisica.setServico(padrao.getServicos((String)cbProdServicosCad.getSelectedItem()));
		if(!"".equals(cbCategoriaCad.getSelectedItem()))
				pessoaFisica.setCategoria(padrao.getCategorias((String)cbCategoriaCad.getSelectedItem()));
		if(!"".equals(cbOrigemCad.getSelectedItem()))
				pessoaFisica.setOrigem(padrao.getOrigens((String)cbOrigemCad.getSelectedItem()));
		pessoa.setNome(txNome.getText());
		pessoa.setCpf("".equals(txCpf.getText().replace(".", "").replace("-", ""))?"":txCpf.getText().replace(".", "").replace("-", ""));
		pessoa.setDataNascimento("".equals(txDataNascimento.getText().trim().replace("/", ""))?"":txDataNascimento.getText());
		//endereco
		endereco.setLogradouro(cbLogradouro.getSelectedItem().toString());
		endereco.setNome(txLogradouro.getText());
		endereco.setComplemento(txComplemento.getText());
		endereco.setNumero(txNum.getText());
		endereco.setCep(txCep.getText().replace("-", ""));
		endereco.setBairro(txBairro.getText());
		
		if(!"".equals(cbCidade.getSelectedItem())){
			Cidade cidade = padrao.getCidades((String)cbCidade.getSelectedItem());
			endereco.setCidade(cidade);
		}
		pessoa.setEndereco(endereco);
		pessoa.setPessoaFisica(pessoaFisica);
		PessoaDAO dao = new PessoaDAO();
		boolean openHere = recebeSessao();
		boolean salvo = dao.salvar(pessoa, session);
		fechaSessao(openHere);
		if(salvo) {
			openHere = recebeSessao();
			listaPessoas = new PessoaDAO().listar("Pessoa", session);
	    	padrao.preencherTabela(listaPessoas, tbPrincipal, Pessoa.class);
	    	fechaSessao(openHere);
		}
    }
    private void invocarExclusao(){
    	int escolha = JOptionPane.showConfirmDialog(jDBody, "Você deseja excluir esse registro? "
				+ "\nTodos os historicos serão perdido, lembre-se que essa ação não terá mais volta!",
				"Pedido de Exclusao", JOptionPane.YES_NO_OPTION);
		if(escolha==JOptionPane.YES_OPTION){
			PessoaDAO dao = new PessoaDAO();
			boolean openHere = recebeSessao();
			boolean excluiu = dao.excluir(pessoa,session);
			fechaSessao(openHere);
			if(excluiu){
				limparFormulario(pnPrincipal);
				openHere = recebeSessao();
				listaPessoas = dao.listar("Pessoa", session);
		    	padrao.preencherTabela(listaPessoas, tbPrincipal, Pessoa.class);
		    	fechaSessao(openHere);
			}
		}
    }
}
