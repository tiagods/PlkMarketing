/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.EmpresasView.*;
import static br.com.tiagods.view.MenuView.jDBody;

import java.awt.Color;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modelDAO.EmpresaDAO;
import br.com.tiagods.modelDAO.ItemsDAO;
import br.com.tiagods.view.SelecaoObjeto;
import br.com.tiagods.view.interfaces.DefaultEnumModel;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;
/**
 *
 * @author Tiago
 */
public class ControllerEmpresas implements ActionListener,KeyListener,ItemListener,MouseListener,PropertyChangeListener{
	
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();
	
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
    	preencherTabela(listaEmpresas, tbPrincipal,txContador);
    	if(!listaEmpresas.isEmpty() && empresa==null){
    		this.empresa = listaEmpresas.get(0);
    		preencherFormulario(this.empresa);
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
		JComboBox[] combos=null;
		
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
			invocarExclusao();
			telaEmEdicao = false;
			break;
		case "Salvar":
			invocarSalvamento();
			telaEmEdicao = false;
			desbloquerFormulario(false, pnPrincipal);
			break;
		case "Historico":
			pnAuxiliar.setVisible(true);
			boolean open = recebeSessao();
			Criterion criterion = Restrictions.eq("empresa", empresa);
			Order order = Order.desc("dataEvento");		
			List<Tarefa> tarefas = (List<Tarefa>) new ItemsDAO().items(Tarefa.class, session, criterion, order);
			new AuxiliarTabela(new Tarefa(),tbAuxiliar, tarefas);
			fechaSessao(open);
			break;
		case "Pessoas":
			pnAuxiliar.setVisible(true);
			//new AuxiliarTabela(new Pessoa(),tbAuxiliar, pessoas);
			break;
		case "Negocios":
			pnAuxiliar.setVisible(true);
			open = recebeSessao();
			criterion = Restrictions.eq("empresa", empresa);
			order = Order.desc("id");		
			List<Negocio> negocios = (List<Negocio>) new ItemsDAO().items(Negocio.class, session, criterion, order);
			new AuxiliarTabela(new Negocio(),tbAuxiliar, negocios);
			fechaSessao(open);
			break;
		case "Esconder":
			pnAuxiliar.setVisible(false);
			break;
		case "CriarCategoria":
			combos = new JComboBox[]{cbCategoria,cbCategoriaCad};
			SelecaoObjeto dialog = new SelecaoObjeto(new Categoria(), new JLabel(), new JLabel(), combos);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarNivel":
			combos = new JComboBox[]{cbNivel,cbNivelCad};
			dialog = new SelecaoObjeto(new Nivel(), new JLabel(), new JLabel(), combos);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarOrigem":
			combos = new JComboBox[]{cbOrigem,cbOrigemCad};
			dialog = new SelecaoObjeto(new Origem(), new JLabel(), new JLabel(), combos);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarServico":
			combos = new JComboBox[]{cbProdServicos,cbProdServicosCad};
			dialog = new SelecaoObjeto(new Servico(), new JLabel(), new JLabel(), combos);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		default:
			break;
		}
	}
	@SuppressWarnings("rawtypes")
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component instanceof JComboBox){
				padrao.preencherCombo((JComboBox)component,session, cbEstado);
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
		cbNivelCad.setSelectedItem(empresa.getPessoaJuridica().getNivel()==null?"":empresa.getPessoaJuridica().getNivel().getNome());
		cbOrigemCad.setSelectedItem(empresa.getPessoaJuridica().getOrigem()==null?"":empresa.getPessoaJuridica().getOrigem().getNome());
		cbCategoriaCad.setSelectedItem(empresa.getPessoaJuridica().getCategoria()==null?"":empresa.getPessoaJuridica().getCategoria().getNome());
		cbProdServicosCad.setSelectedItem(empresa.getPessoaJuridica().getServico()==null?"":empresa.getPessoaJuridica().getServico().getNome());
		txTelefone.setText(empresa.getPessoaJuridica().getTelefone());
		txCelular.setText(empresa.getPessoaJuridica().getCelular());
		txEmail.setText(empresa.getPessoaJuridica().getEmail());
		txSite.setText(empresa.getPessoaJuridica().getSite());
		
		Endereco end = empresa.getEndereco();
		cbLogradouro.setSelectedItem(DefaultEnumModel.Logradouro.valueOf(end.getLogradouro()));
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
		List<Empresa> lista = new ArrayList<>();
		for(int i =0;i<listaEmpresas.size();i++){
			String texto = txBuscar.getText().trim().toUpperCase();
			if(listaEmpresas.get(i).getNome().substring(0,texto.length()).equalsIgnoreCase(texto)){
				lista.add(listaEmpresas.get(i));
			}
		}
		if(!lista.isEmpty()){
			preencherTabela(lista, tbPrincipal,txContador);
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
					padrao.preencherCombo(cbCidade,session,cbEstado);
				}
				else
					realizarFiltro();
				fechaSessao(openHere);
			}
		}
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
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
				e.getMessage();
			}
			List<Empresa> lista = criteria.list();
			preencherTabela(lista, tbPrincipal, txContador);
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
			pessoaJuridica.setNivel(padrao.getNiveis((String)cbNivelCad.getSelectedItem()));
		if(!"".equals(cbProdServicosCad.getSelectedItem()))
				pessoaJuridica.setServico(padrao.getServicos((String)cbProdServicosCad.getSelectedItem()));
		if(!"".equals(cbCategoriaCad.getSelectedItem()))
				pessoaJuridica.setCategoria(padrao.getCategorias((String)cbCategoriaCad.getSelectedItem()));
		if(!"".equals(cbOrigemCad.getSelectedItem()))
				pessoaJuridica.setOrigem(padrao.getOrigens((String)cbOrigemCad.getSelectedItem()));
		empresa.setNome(txNome.getText());
		empresa.setCnpj("".equals(txCnpj.getText().replace(".", "").replace("-", "").replace("/",""))?"":txCnpj.getText().replace(".", "").replace("-", "").replace("/", ""));
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
			preencherFormulario(empresa);
	    	preencherTabela(listaEmpresas, tbPrincipal, txContador);
	    	fechaSessao(openHere);
	    	salvarCancelar();
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
		    	preencherTabela(listaEmpresas, tbPrincipal, txContador);
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
	@SuppressWarnings({ "serial"})
	public void preencherTabela(List<Empresa> lista, JTable table, JLabel txContadorRegistros){
		if(lista.isEmpty()){
			new SemRegistrosJTable(table,"Relação de Empresas");
		}
		else{
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
			for(int i=0;i<lista.size();i++){
				Empresa em= lista.get(i);
				Object[] linha = new Object[7];
				linha[0] = ""+em.getId(); 
				linha[1] = em.getNome();
				linha[2] = em.getPessoaJuridica().getNivel()==null?"":em.getPessoaJuridica().getNivel().getNome();
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
			}
			txContadorRegistros.setText("Total: "+lista.size()+" registros");
			table.setModel(model);
			table.setAutoCreateRowSorter(true);
			table.setSelectionBackground(Color.orange);
		}
		
	}
}

