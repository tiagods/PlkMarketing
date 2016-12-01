/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.MenuView.jDBody;
import static br.com.tiagods.view.EmpresasView.*;

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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.modeldao.EmpresaDAO;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;
/**
 *
 * @author Tiago
 */
public class ControllerEmpresas implements ActionListener,KeyListener,ItemListener,MouseListener,PropertyChangeListener{
	ControllerEmpresaPessoa padrao = new ControllerEmpresaPessoa();
	List<Empresa> listaEmpresas;
	
	Session session=null;
	Empresa empresa= null;
	Empresa empresaBackup;
	boolean telaEmEdicao = false;
	@SuppressWarnings("unchecked")
	public void iniciar(Empresa empresa){
		cbEmpresa.setEnabled(false);
		cbEmpresa.setToolTipText("Filtro não criado: Aguardando programação");
		this.empresa=empresa;
    	session = HibernateFactory.getSession();
    	session.beginTransaction();
		JPanel[] panels = {pnCabecalho,pnPrincipal};
    	for(JPanel panel : panels){
    		preencherComboBox(panel);
    	}
    	listaEmpresas = (List<Empresa>)(new EmpresaDAO().listar(Empresa.class, session));
    	padrao.preencherTabela(listaEmpresas, tbPrincipal, Empresa.class,txContador);
    	if(!listaEmpresas.isEmpty() && empresa==null){
    		preencherFormulario(listaEmpresas.get(0));
    	}
    	else if(empresa!=null){
    		preencherFormulario(empresa);
    	}
    	salvarCancelar();
    	desbloquerFormulario(false, pnPrincipal);
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
			limparFormulario(pnPrincipal);
			telaEmEdicao = true;
			empresa = null;
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
			if(empresa!=null){
				invocarExclusao();
			}
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
				if(empresa==null){
					padrao.preencherCombo((JComboBox)panel.getComponent(i),session, null,
							null,null,null,null,null,cbEstado);
				}
				else{
					padrao.preencherCombo((JComboBox)panel.getComponent(i),session, 
							empresa.getPessoaJuridica().getAtendente(),
							null,empresa.getPessoaJuridica().getCategoria(),
							empresa.getPessoaJuridica().getOrigem(),
							empresa.getPessoaJuridica().getServico(),
							empresa.getEndereco(), cbEstado);
				}
			}
		}
	}
	private void preencherFormulario(Empresa empresa){
		txCodigo.setText(""+empresa.getId());
		SimpleDateFormat conversor = new SimpleDateFormat("dd/MM/yyyy");
		txCadastradoPor.setText(empresa.getPessoaJuridica().getCriadoPor()==null?"":empresa.getPessoaJuridica().getCriadoPor().getLogin());
		
		txDataCadastro.setText(conversor.format(empresa.getPessoaJuridica().getCriadoEm()));
		txNome.setText(empresa.getNome());
		cbAtendenteCad.setSelectedItem(empresa.getPessoaJuridica().getAtendente()==null?"":empresa.getPessoaJuridica().getAtendente().getLogin());
		txCnpj.setText(empresa.getCnpj());
		cbNivelCad.setSelectedItem(empresa.getNivel()==null?"":empresa.getNivel().getNome());
		cbOrigemCad.setSelectedItem(empresa.getPessoaJuridica().getOrigem()==null?"":empresa.getPessoaJuridica().getOrigem().getNome());
		cbCategoriaCad.setSelectedItem(empresa.getPessoaJuridica().getCategoria()==null?"":empresa.getPessoaJuridica().getCategoria().getNome());
		cbProdServicosCad.setSelectedItem(empresa.getPessoaJuridica().getServico()==null?"":empresa.getPessoaJuridica().getServico().getNome());
		txTelefone.setText(empresa.getPessoaJuridica().getTelefone());
		txCelular.setText(empresa.getPessoaJuridica().getCelular());
		txEmail.setText(empresa.getPessoaJuridica().getEmail());
		txSite.setText(empresa.getPessoaJuridica().getSite());
		
		Endereco end = empresa.getEndereco();
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
		if(empresaBackup!=null){
			empresa=empresaBackup;
			preencherFormulario(empresa);
		}
		else
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
		List<Empresa> lista = new ArrayList<>();
		for(int i =0;i<listaEmpresas.size();i++){
			String texto = txBuscar.getText().trim().toUpperCase();
			if(listaEmpresas.get(i).getNome().substring(0,texto.length()).equalsIgnoreCase(texto)){
				lista.add(listaEmpresas.get(i));
			}
		}
		if(!lista.isEmpty()){
			padrao.preencherTabela(lista, tbPrincipal, Empresa.class,txContador);
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
		Criteria criteria = session.createCriteria(Empresa.class);
		criteria.addOrder(Order.asc("id"));
		if(!cbCategoria.getSelectedItem().equals(cbCategoria.getName()))
			criteria.add(Restrictions.eq("pessoaJuridica.categoria", padrao.getCategorias((String)cbCategoria.getSelectedItem())));
		if(!cbNivel.getSelectedItem().equals(cbNivel.getName()))
			criteria.add(Restrictions.eq("nivel", padrao.getNiveis((String)cbNivel.getSelectedItem())));
		if(!cbOrigem.getSelectedItem().equals(cbOrigem.getName()))	
			criteria.add(Restrictions.eq("pessoaJuridica.origem", padrao.getOrigens((String)cbOrigem.getSelectedItem())));
		if(!cbProdServicos.getSelectedItem().equals(cbProdServicos.getName()))
			criteria.add(Restrictions.eq("pessoaJuridica.servico", padrao.getServicos((String)cbProdServicos.getSelectedItem())));
		if(!cbAtendente.getSelectedItem().equals(cbAtendente.getName()))
			criteria.add(Restrictions.eq("pessoaJuridica.atendente", padrao.getAtendentes((String)cbAtendente.getSelectedItem())));
		if(!"".equals(txBuscar.getText().trim()))
			criteria.add(Restrictions.like("nome", txBuscar.getText().trim()+"%"));
		try{
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if(data02.compareTo(data01)>=0){
				criteria.add(Restrictions.between("pessoaJuridica.criadoEm", data01, data02));
			}
		}catch(NullPointerException e){
			
		}
		List<Empresa> lista = criteria.list();
		padrao.preencherTabela(lista, tbPrincipal, Empresa.class,txContador);
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
			EmpresaDAO dao = new EmpresaDAO();
			empresa = (Empresa)dao.receberObjeto(Empresa.class, valor, session);
			preencherFormulario(empresa);
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
    	PfPj pessoaJuridica;
		Endereco endereco = new Endereco();
		if("".equals(txCodigo.getText())) {
			empresa = new Empresa();
			pessoaJuridica = new PfPj();
			pessoaJuridica.setCriadoEm(new Date());
			pessoaJuridica.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		}
		else{
			pessoaJuridica = empresa.getPessoaJuridica();
		}
		pessoaJuridica.setTelefone(txTelefone.getText());
		pessoaJuridica.setCelular(txCelular.getText());
		pessoaJuridica.setEmail(txEmail.getText());
		pessoaJuridica.setSite(txSite.getText());
		//se nao tem usuario cadastrado
		if("".equals(cbAtendenteCad.getSelectedItem())){
			pessoaJuridica.setAtendente(UsuarioLogado.getInstance().getUsuario());
		}
		else
			pessoaJuridica.setAtendente(padrao.getAtendentes((String)cbAtendenteCad.getSelectedItem()));
		if(!"".equals(cbNivelCad.getSelectedItem()))
			empresa.setNivel(padrao.getNiveis((String)cbNivelCad.getSelectedItem()));
		if(!"".equals(cbProdServicosCad.getSelectedItem()))
				pessoaJuridica.setServico(padrao.getServicos((String)cbProdServicosCad.getSelectedItem()));
		if(!"".equals(cbCategoriaCad.getSelectedItem()))
				pessoaJuridica.setCategoria(padrao.getCategorias((String)cbCategoriaCad.getSelectedItem()));
		if(!"".equals(cbOrigemCad.getSelectedItem()))
				pessoaJuridica.setOrigem(padrao.getOrigens((String)cbOrigemCad.getSelectedItem()));
		empresa.setNome(txNome.getText());
		empresa.setCnpj("".equals(txCnpj.getText().replace(".", "").replace("-", "").replace("/",""))?"":txCnpj.getText().replace(".", "").replace("-", ""));
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
		empresa.setEndereco(endereco);
		empresa.setPessoaJuridica(pessoaJuridica);
		EmpresaDAO dao = new EmpresaDAO();
		boolean openHere = recebeSessao();
		boolean salvo = dao.salvar(empresa, session);
		fechaSessao(openHere);
		if(salvo) {
			openHere = recebeSessao();
			listaEmpresas = (List<Empresa>)new EmpresaDAO().listar(Empresa.class, session);
	    	padrao.preencherTabela(listaEmpresas, tbPrincipal, Empresa.class,txContador);
	    	fechaSessao(openHere);
		}
    }
    @SuppressWarnings("unchecked")
	private void invocarExclusao(){
    	int escolha = JOptionPane.showConfirmDialog(jDBody, "Você deseja excluir esse registro? "
				+ "\nTodos os historicos serão perdido, lembre-se que essa ação não terá mais volta!",
				"Pedido de Exclusao", JOptionPane.YES_NO_OPTION);
		if(escolha==JOptionPane.YES_OPTION){
			EmpresaDAO dao = new EmpresaDAO();
			boolean openHere = recebeSessao();
			boolean excluiu = dao.excluir(empresa,session);
			fechaSessao(openHere);
			if(excluiu){
				limparFormulario(pnPrincipal);
				openHere = recebeSessao();
				listaEmpresas = (List<Empresa>)dao.listar(Empresa.class, session);
		    	padrao.preencherTabela(listaEmpresas, tbPrincipal, Empresa.class,txContador);
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

