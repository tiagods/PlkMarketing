/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.MenuView.jDBody;
import static br.com.tiagods.view.PessoasView.*;

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
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.PessoaDao;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.SelecaoObjetoDialog;
import br.com.tiagods.view.interfaces.DefaultEnumModel;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;
/**
 *
 * @author Tiago
 */
public class ControllerPessoas implements ActionListener,KeyListener,ItemListener,MouseListener,PropertyChangeListener{
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();
	
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
    	List<Criterion> criterion = new ArrayList<>();
    	Order order = Order.desc("id");
    	listaPessoas = (List<Pessoa>)(new GenericDao().items(Pessoa.class, session, criterion, order));
    	preencherTabela(listaPessoas, tbPrincipal,txContador);
    	if(!listaPessoas.isEmpty() && this.pessoa==null){
    		this.pessoa = listaPessoas.get(0);
    		preencherFormulario(this.pessoa);
    	}
    	else if(this.pessoa!=null){
    		preencherFormulario(this.pessoa);
    	}
    	salvarCancelar();
    	desbloquerFormulario(false, pnPrincipal);
    	session.close();
    	definirAcoes();
    }
	private void definirAcoes(){
		data1.addPropertyChangeListener(this);
		data2.addPropertyChangeListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox[] combos = null;
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
		case "Historico":
			pnAuxiliar.setVisible(true);
			boolean open = recebeSessao();
			List<Criterion> criterios = new ArrayList<>();
			Criterion criterion = Restrictions.eq("pessoa", pessoa);
			criterios.add(criterion);
			Order order = Order.desc("dataEvento");		
			List<Tarefa> tarefas = (List<Tarefa>) new GenericDao().items(Tarefa.class, session, criterios, order);
			new AuxiliarTabela(new Tarefa(),tbAuxiliar, tarefas);
			fechaSessao(open);
			break;
		case "Empresas":
			pnAuxiliar.setVisible(true);
			//new AuxiliarTabela(new Pessoa(),tbAuxiliar, pessoas);
			break;
		case "Negocios":
			pnAuxiliar.setVisible(true);
			open = recebeSessao();
			criterios = new ArrayList<>();
			criterion = Restrictions.eq("pessoa", pessoa);
			criterios.add(criterion);
			order = Order.desc("id");		
			List<Negocio> negocios = (List<Negocio>) new GenericDao().items(Negocio.class, session, criterios, order);
			new AuxiliarTabela(new Negocio(),tbAuxiliar, negocios);
			fechaSessao(open);
			break;
		case "Esconder":
			pnAuxiliar.setVisible(false);
			break;
			case "CriarCategoria":
				combos = new JComboBox[]{cbCategoria,cbCategoriaCad};
				SelecaoObjetoDialog dialog = new SelecaoObjetoDialog(new Categoria(), new JLabel(), new JLabel(), combos,MenuView.getInstance(),true);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				break;
			case "CriarOrigem":
				combos = new JComboBox[]{cbOrigem,cbOrigemCad};
				dialog = new SelecaoObjetoDialog(new Origem(), new JLabel(), new JLabel(), combos,MenuView.getInstance(),true);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				break;
			case "CriarServico":
				combos = new JComboBox[]{cbProdServicos,cbProdServicosCad};
				dialog = new SelecaoObjetoDialog(new Servico(), new JLabel(), new JLabel(), combos,MenuView.getInstance(),true);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				break;
			default:
				break;
		}
	}
	@SuppressWarnings("rawtypes")
	private void preencherComboBox(JPanel panel){
		for(int i=0;i<panel.getComponentCount();i++){
			if(panel.getComponent(i) instanceof JComboBox){
				padrao.preencherCombo((JComboBox)panel.getComponent(i),session,cbEstado);
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
		if(end!=null){
			cbLogradouro.setSelectedItem(DefaultEnumModel.Logradouro.valueOf(end.getLogradouro()));
			txLogradouro.setText(end.getNome());
			txNum.setText(end.getNumero());
			txComplemento.setText(end.getComplemento());

			cbEstado.setSelectedItem(end.getCidade()==null?"":end.getCidade().getEstado());
			cbCidade.setSelectedItem(end.getCidade()==null?"":end.getCidade().getNome());
		}
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
			if(listaPessoas.get(i).getNome().trim().length()>texto.length() && listaPessoas.get(i).getNome().substring(0,texto.length()).equalsIgnoreCase(texto)){
				lista.add(listaPessoas.get(i));
			}
		}
		if(!lista.isEmpty()){
			preencherTabela(lista, tbPrincipal,txContador);
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
	@SuppressWarnings("deprecation")
	private void realizarFiltro(){
		if(!telaEmEdicao){
		Criteria criteria = session.createCriteria(Pessoa.class);
		criteria.addOrder(Order.desc("id"));
		if(!cbCategoria.getSelectedItem().equals(cbCategoria.getName()))
			criteria.add(Restrictions.eq("pessoaFisica.categoria", padrao.getCategorias((String)cbCategoria.getSelectedItem())));
		if(!cbOrigem.getSelectedItem().equals(cbOrigem.getName()))
			criteria.add(Restrictions.eq("pessoaFisica.origem", padrao.getOrigens((String)cbOrigem.getSelectedItem())));
		if(!cbProdServicos.getSelectedItem().equals(cbProdServicos.getName()))
			criteria.add(Restrictions.eq("pessoaFisica.servico", padrao.getServicos((String)cbProdServicos.getSelectedItem())));
		if(!cbAtendente.getSelectedItem().equals(cbAtendente.getName()))
			criteria.add(Restrictions.eq("pessoaFisica.atendente", padrao.getAtendentes((String)cbAtendente.getSelectedItem())));
		if(!"".equals(txBuscar.getText().trim()))
			criteria.add(Restrictions.ilike("nome", txBuscar.getText().trim()+"%"));
		try{
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if(data02.compareTo(data01)>=0){
				criteria.add(Restrictions.between("pessoaFisica.criadoEm", data01, data02));
			}
		}catch(NullPointerException e){

		}
		List<Pessoa> lista = criteria.list();
		preencherTabela(lista, tbPrincipal, txContador);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!","Em edicao...",JOptionPane.INFORMATION_MESSAGE);
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
		if(valor>0 && !telaEmEdicao){
			boolean open = recebeSessao();
			PessoaDao dao = new PessoaDao();
			pessoa = (Pessoa)dao.receberObjeto(Pessoa.class, valor, session);
			preencherFormulario(pessoa);
			fechaSessao(open);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!","Em edição...",JOptionPane.INFORMATION_MESSAGE);
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
		PessoaDao dao = new PessoaDao();
		boolean openHere = recebeSessao();
		boolean salvo = dao.salvar(pessoa, session);
		fechaSessao(openHere);
		if(salvo) {
			openHere = recebeSessao();
			List<Criterion> criterion = new ArrayList<>();
	    	Order order = Order.desc("id");
	    	listaPessoas = (List<Pessoa>)(new GenericDao().items(Pessoa.class, session, criterion, order));
	    	preencherFormulario(pessoa);
			preencherTabela(listaPessoas, tbPrincipal, txContador);
	    	fechaSessao(openHere);
	    	salvarCancelar();
		}
    }
    @SuppressWarnings("unchecked")
	private void invocarExclusao(){
    	int escolha = JOptionPane.showConfirmDialog(jDBody, "Você deseja excluir esse registro? "
				+ "\nTodos os históricos serão perdidos, lembre-se que essa ação não terá mais volta!",
				"Pedido de Exclusão", JOptionPane.YES_NO_OPTION);
		if(escolha==JOptionPane.YES_OPTION){
			PessoaDao dao = new PessoaDao();
			boolean openHere = recebeSessao();
			boolean excluiu = dao.excluir(pessoa,session);
			fechaSessao(openHere);
			if(excluiu){
				limparFormulario(pnPrincipal);
				openHere = recebeSessao();
				List<Criterion> criterion = new ArrayList<>();
		    	Order order = Order.desc("id");
		    	listaPessoas = (List<Pessoa>)(new GenericDao().items(Pessoa.class, session, criterion, order));
		    	preencherTabela(listaPessoas, tbPrincipal, txContador);
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
	public void preencherTabela(List<Pessoa> lista, JTable table, JLabel txContadorRegistros){
		if(lista.isEmpty()){
			new SemRegistrosJTable(table,"Relação de Pessoas");
		}
		else{
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
			}
			txContadorRegistros.setText("Total: "+lista.size()+" registros");
			table.setModel(model);
			table.setAutoCreateRowSorter(true);
			table.setSelectionBackground(Color.orange);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
		}
	}
}
