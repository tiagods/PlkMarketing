/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.EmpresasView.txCodigo;
import static br.com.tiagods.view.MenuView.jDBody;
import static br.com.tiagods.view.PessoasView.*;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.modelDAO.PessoaDAO;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;
/**
 *
 * @author Tiago
 */
public class ControllerPessoas implements ActionListener,KeyListener,ItemListener,MouseListener,PropertyChangeListener{
	ControllerEmpresaPessoa padrao = new ControllerEmpresaPessoa();
	List<Pessoa> listaPessoas;
	
	Session session=null;
	Pessoa pessoa= null;
	Pessoa pessoaBackup;
	boolean telaEmEdicao = false;
	@SuppressWarnings("unchecked")
	public void iniciar(Pessoa pessoa){
		cbEmpresa.setEnabled(false);
		cbEmpresa.setToolTipText("Filtro não criado: Aguardando programação");
		this.pessoa=pessoa;
    	session = HibernateFactory.getSession();
    	session.beginTransaction();
		JPanel[] panels = {pnCabecalho,pnPrincipal};
    	for(JPanel panel : panels){
    		preencherComboBox(panel);
    	}
    	listaPessoas = (List<Pessoa>)(new PessoaDAO().listar(Pessoa.class, session));
    	padrao.preencherTabela(listaPessoas, tbPrincipal, Pessoa.class,txContador);
    	if(!listaPessoas.isEmpty() && pessoa==null){
    		pessoa = listaPessoas.get(0);
    		preencherFormulario(pessoa);
    	}
    	else if(pessoa!=null){
    		preencherFormulario(pessoa);
    	}
    	salvarCancelar();
    	desbloquerFormulario(false, pnPrincipal);
    	session.close();
    	String toolTip = "Essa opção não esta liberado porque depende da conclusão de outro modulo, aguarde...";
    	btnHistorico.setEnabled(false);
    	btnHistorico.setToolTipText(toolTip);
    	btnNegocios.setEnabled(false);
    	btnHistorico.setToolTipText(toolTip);
    	btnEmpresas.setEnabled(false);
    	btnEmpresas.setToolTipText(toolTip);
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
			limparFormulario(pnPrincipal);
			telaEmEdicao = true;
			pessoa = null;
			novoEditar();
			break;
		case "Editar":
			telaEmEdicao = true;
			novoEditar();
			break;
		case "Cancelar":
			salvarCancelar();
			telaEmEdicao = false;
			break;
		case "Excluir":
			invocarExclusao();
			telaEmEdicao = false;
			break;
		case "Salvar":
			invocarSalvamento();
			telaEmEdicao = false;
			desbloquerFormulario(false, pnPrincipal);
			break;
			default:
				boolean open = recebeSessao();
				realizarFiltro();
				fechaSessao(open);
				break;
		}
	}
	@SuppressWarnings("rawtypes")
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
		if("".equals(txCodigo.getText()))
			btnExcluir.setEnabled(false);
	}
	private void novoEditar(){
		desbloquerFormulario(true, pnPrincipal);
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnExcluir.setEnabled(false);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		pesquisar();
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
			padrao.preencherTabela(lista, tbPrincipal, Pessoa.class,txContador);
		}else{
			JOptionPane.showMessageDialog(jDBody,"Não foi encontrado registros com o criterio informado",
					"Nenhum registro!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//atualizador de combo box
	@SuppressWarnings("rawtypes")
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED){
			JComboBox combo = (JComboBox)e.getSource();
			if(cbEstado.getSelectedItem()!=null){
				boolean openHere = recebeSessao();
				if("Estado".equals(combo.getName())){
					padrao.preencherCombo(cbCidade,session, null,null,null,null,null,null,cbEstado);
				}
				else
					realizarFiltro();
				fechaSessao(openHere);
			}
		}
		
	}
	@SuppressWarnings("deprecation")
	private void realizarFiltro(){
		if(!telaEmEdicao){
		Criteria criteria = session.createCriteria(Pessoa.class);
		criteria.addOrder(Order.asc("id"));
		if(!cbCategoria.getSelectedItem().equals(cbCategoria.getName()))
			criteria.add(Restrictions.eq("pessoaFisica.categoria", padrao.getCategorias((String)cbCategoria.getSelectedItem())));
		if(!cbOrigem.getSelectedItem().equals(cbOrigem.getName()))	
			criteria.add(Restrictions.eq("pessoaFisica.origem", padrao.getOrigens((String)cbOrigem.getSelectedItem())));
		if(!cbProdServicos.getSelectedItem().equals(cbProdServicos.getName()))
			criteria.add(Restrictions.eq("pessoaFisica.servico", padrao.getServicos((String)cbProdServicos.getSelectedItem())));
		if(!cbAtendente.getSelectedItem().equals(cbAtendente.getName()))
			criteria.add(Restrictions.eq("pessoaFisica.atendente", padrao.getAtendentes((String)cbAtendente.getSelectedItem())));
		if(!"".equals(txBuscar.getText().trim()))
			criteria.add(Restrictions.like("nome", txBuscar.getText().trim()+"%"));
		try{
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if(data02.compareTo(data01)>=0){
				criteria.add(Restrictions.between("pessoaFisica.criadoEm", data01, data02));
			}
		}catch(NullPointerException e){
			
		}
		List<Pessoa> lista = criteria.list();
		padrao.preencherTabela(lista, tbPrincipal, Pessoa.class,txContador);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edição ou cancele para poder realizar novas buscas!","Em edição...",JOptionPane.INFORMATION_MESSAGE);
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
	
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings("rawtypes")
	private void desbloquerFormulario(boolean desbloquear,Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JTextField){
				((JTextField)c).setEditable(desbloquear);
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setEnabled(desbloquear);
			}
			else if(c instanceof JTextArea){
				((JTextArea)c).setEnabled(desbloquear);
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
    @SuppressWarnings("unchecked")
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
			listaPessoas = (List<Pessoa>)new PessoaDAO().listar(Pessoa.class, session);
	    	padrao.preencherTabela(listaPessoas, tbPrincipal, Pessoa.class,txContador);
	    	fechaSessao(openHere);
		}
    }
    @SuppressWarnings("unchecked")
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
				listaPessoas = (List<Pessoa>)dao.listar(Pessoa.class, session);
		    	padrao.preencherTabela(listaPessoas, tbPrincipal, Pessoa.class,txContador);
		    	fechaSessao(openHere);
			}
		}
    }
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try{
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if(data02.compareTo(data01)>=0){
				boolean open  = recebeSessao();
				realizarFiltro();
				fechaSessao(open);
			}
		}catch (NullPointerException e) {
		}
		
		
	}
}
