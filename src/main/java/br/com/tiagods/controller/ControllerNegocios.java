/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Etapa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.ServicoAgregado;
import br.com.tiagods.model.ServicoContratado;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modeldao.*;
import br.com.tiagods.view.EmpresasView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.PerdaNegocio;
import br.com.tiagods.view.PessoasView;
import br.com.tiagods.view.SelecaoObjeto;
import br.com.tiagods.view.interfaces.ButtonColumnModel;
import br.com.tiagods.view.interfaces.DefaultEnumModel.Modelos;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;

import static br.com.tiagods.view.MenuView.jDBody;
import static br.com.tiagods.view.NegociosView.*;
/**
 *
 * @author Tiago
 */
public class ControllerNegocios implements ActionListener,ItemListener,MouseListener, PropertyChangeListener, KeyListener{
	
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();
	
	Session session = null;
	Negocio negocio = null;
	Negocio negocioBackup = null;
	boolean telaEmEdicao = false;
	List<Negocio> listarNegocios;
	PerdaNegocio dialogPerda;
	
	@SuppressWarnings("unchecked")
	public void iniciar(Negocio negocio){
		this.negocio=negocio;
		session = HibernateFactory.getSession();
		session.beginTransaction();
		JPanel[] panels = {pnPrincipal,pnAndamento,pnCadastro,pnServicosContratados};
		for (JPanel panel : panels) {
			preencherComboBox(panel);
		}
		List<Criterion> criterion = new ArrayList<>();
		listarNegocios = new GenericDao().items(Negocio.class, session, criterion, Order.desc("id"));
		preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
		if(this.negocio==null && !listarNegocios.isEmpty()){
			this.negocio=listarNegocios.get(0);
			preencherFormulario(this.negocio);
		}
		else if(this.negocio!=null)
			preencherFormulario(this.negocio);
		tbPrincipal.addMouseListener(this);
		session.close();
		definirAcoes();
		desbloquerFormulario(false, pnCadastro);
		desbloquerFormulario(false, pnAndamento);
		salvarCancelar();
    }
	public void definirAcoes(){
		data1.addPropertyChangeListener(this);
		data2.addPropertyChangeListener(this);
		cbStatus.addItemListener(this);
		cbEtapa.addItemListener(this);
		cbCategoria.addItemListener(this);
		cbOrigem.addItemListener(this);
		cbNivel.addItemListener(this);
		cbServicos.addItemListener(this);
		cbEmpresa.addItemListener(this);
		cbPessoa.addItemListener(this);
		cbAtendente.addItemListener(this);
		cbObject.addItemListener(this);
		cbStatusCad.addItemListener(new InvocarDialogPerda());
		txBuscar.addKeyListener(this);
		tbServicosContratados.addMouseListener(new AcaoTabelaServicosContratados());
	}
	public class InvocarDialogPerda implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.DESELECTED && telaEmEdicao && "Perdido".equals(cbStatusCad.getSelectedItem())){
				if(dialogPerda!=null) 
					dialogPerda.dispose();
				dialogPerda = new PerdaNegocio(negocio);
				dialogPerda.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogPerda.setVisible(true);
			}
		}
		
	}
	
	public void preencherFormulario(Negocio n){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		enviarEtapa(n.getEtapa());
		cbStatusCad.setSelectedItem(n.getStatus().getNome());
		cbObject.setSelectedItem(n.getClasse());
		
		txCodObjeto.setOpaque(true);
		txCodObjeto.setForeground(Color.ORANGE);
		txNomeObjeto.setOpaque(true);
		txNomeObjeto.setForeground(Color.ORANGE);
		if(n.getClasse().equals(Empresa.class.getSimpleName())){
			txCodObjeto.setText(""+n.getEmpresa().getId());
			txNomeObjeto.setText(n.getEmpresa().getNome());
		}
		else if(n.getClasse().equals(Pessoa.class.getSimpleName())){
			txCodObjeto.setText(""+n.getPessoa().getId());
			txNomeObjeto.setText(n.getPessoa().getNome());
		}
		txCodigo.setText(""+n.getId());
		txNome.setText(n.getNome());
		txDataCadastro.setText(sdf.format(n.getCriadoEm()));
		txCadastradoPor.setText(n.getCriadoPor().getLogin());
		cbAtendenteCad.setSelectedItem(n.getAtendente()==null?"":n.getAtendente().getLogin());
		if(n.getPessoaFisicaOrJuridica()!=null){
			cbNivelCad.setSelectedItem(n.getPessoaFisicaOrJuridica().getNivel()==null?"":n.getPessoaFisicaOrJuridica().getNivel().getNome());
			cbOrigemCad.setSelectedItem(n.getPessoaFisicaOrJuridica().getOrigem()==null?"":n.getPessoaFisicaOrJuridica().getOrigem().getNome());
			cbCategoriaCad.setSelectedItem(n.getPessoaFisicaOrJuridica().getCategoria()==null?"":n.getPessoaFisicaOrJuridica().getCategoria().getNome());
			cbServicosCad.setSelectedItem(n.getPessoaFisicaOrJuridica().getServico()==null?"":n.getPessoaFisicaOrJuridica().getServico().getNome());
		}
		if(n.getDataInicio()!=null){
			dataInicio.setDate(n.getDataInicio());
		}
		if(n.getDataFim()!=null){
			dataFim.setDate(n.getDataFim());
		}
		else
			dataFim.setDate(null);
		String honorario = ""+n.getHonorario();
		txHonorario.setText(honorario.replace(".", ","));
		txDescricao.setText(n.getDescricao());
		Set<ServicoContratado> servicos = n.getServicosContratados();
		preencherServicos(servicos);
	}
	@SuppressWarnings("unchecked")
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component instanceof JComboBox)
				padrao.preencherCombo((JComboBox<String>)component, session, null);
		}
	}
	
	@SuppressWarnings("unchecked")
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
			limparFormulario(pnCadastro);
			novoEditar();
			telaEmEdicao = true;
			negocio = new Negocio();
			negocio.setHonorario(new BigDecimal("0.00"));
			txHonorario.setText("0,00");
			dataInicio.setDate(new Date());
			break;
		case "Editar":
			telaEmEdicao = true;
			novoEditar();
			break;
		case "Cancelar":
			if(negocioBackup!=null)
				preencherFormulario(negocioBackup);
			salvarCancelar();
			telaEmEdicao = false;
			break;
		case "Excluir":
			invocarExclusao();
			telaEmEdicao = false;
			break;
		case "Salvar":
			if(verificarCondicao()) {
				invocarSalvamento();
				telaEmEdicao = false;
			}
			break;
		case "Historico":
			pnAuxiliar.setVisible(true);
			boolean open = recebeSessao();
			List<Criterion>criterios = new ArrayList<>();
			Criterion criterion = Restrictions.eq("negocio", negocio);
			criterios.add(criterion);
			Order order = Order.desc("dataEvento");		
			List<Tarefa> tarefas = (List<Tarefa>) new GenericDao().items(Tarefa.class, session, criterios, order);
			new AuxiliarTabela(new Tarefa(),tbAuxiliar, tarefas);
			fechaSessao(open);
			break;
		case "Esconder":
			pnAuxiliar.setVisible(false);
			break;
		case "VincularObjeto":
			SelecaoObjeto dialog = null;
			if(cbObject.getSelectedItem().equals(Modelos.Empresa.toString())){
				combos = new JComboBox[]{cbEmpresa};
				dialog =new SelecaoObjeto(new Empresa(),txCodObjeto,txNomeObjeto,combos);
			}
			else if(cbObject.getSelectedItem().equals(Modelos.Pessoa.toString())){
				combos = new JComboBox[]{cbPessoa};
				dialog = new SelecaoObjeto(new Pessoa(),txCodObjeto,txNomeObjeto,combos);
			}
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			txCodObjeto.addPropertyChangeListener(new MudarCliente());
			break;
		case "CriarCategoria":
			combos = new JComboBox[]{cbCategoria,cbCategoriaCad};
			dialog = new SelecaoObjeto(new Categoria(), new JLabel(), new JLabel(), combos);
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
			combos = new JComboBox[]{cbServicos,cbServicosCad};
			dialog = new SelecaoObjeto(new Servico(), new JLabel(), new JLabel(), combos);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "AdicionarServicoAgregado":
			if(telaEmEdicao){
				DefaultTableModel model = (DefaultTableModel) tbServicosContratados.getModel();
				
				Object[] o = new Object[4];
				o[0]=txIdServicoContratado.getText();
				ServicoAgregado sa = padrao.getServicosAgregados((String) cbServicosAgregados.getSelectedItem());
				o[1]=sa.getNome();
				o[2]=txValorServico.getText();
				o[3]="Excluir";
				if(!"".equals(o[0])){
					int linha=0;
					while(linha<model.getRowCount()){
						Object valor = model.getValueAt(linha, 0);
						if(o[0].equals(valor.toString())){
							System.out.println("Chegou aqui");
							model.removeRow(linha);
							break;
						}
						linha++;
					}
				}
				model.addRow(o);
				tbServicosContratados.setModel(model);
				cbServicosAgregados.setSelectedItem("");
				txValorServico.setText("0,00");
				txIdServicoContratado.setText("");
			}else JOptionPane.showMessageDialog(MenuView.jDBody, "Clique em editar antes de tentar qualquer alteção!");
			break;
		case "NovoServicoContratado":
			txIdServicoContratado.setText("");
			cbServicosAgregados.setSelectedItem("");
			txValorServico.setText("0,00");
			break;
		default:
			break;
		}
	}
	private void realizarFiltro() {
		List<Criterion> criterios = new ArrayList<>();
		Order order = Order.desc("id");
		if(!"Status".equals(cbStatus.getSelectedItem())){
			Criterion c = Restrictions.eq("status", padrao.getStatus((String)cbStatus.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Etapa".equals(cbEtapa.getSelectedItem())){
			Criterion c = Restrictions.eq("etapa", padrao.getEtapa((String)cbEtapa.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Categoria".equals(cbCategoria.getSelectedItem())){
			Criterion c = Restrictions.eq("pessoaFisicaOrJuridica.categoria", padrao.getCategorias((String)cbCategoria.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Origem".equals(cbOrigem.getSelectedItem())){
			Criterion c = Restrictions.eq("pessoaFisicaOrJuridica.origem", padrao.getOrigens((String)cbOrigem.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Nivel".equals(cbNivel.getSelectedItem())){
			Criterion c = Restrictions.eq("pessoaFisicaOrJuridica.nivel", padrao.getNiveis((String)cbNivel.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Produtos/Servicos".equals(cbServicos.getSelectedItem())){
			Criterion c = Restrictions.eq("pessoaFisicaOrJuridica.servico", padrao.getServicos((String)cbServicos.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Empresa".equals(cbEmpresa.getSelectedItem())){
			Criterion c = Restrictions.eq("empresa", padrao.getEmpresas((String)cbEmpresa.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Pessoa".equals(cbPessoa.getSelectedItem())){
			Criterion c = Restrictions.eq("pessoa", padrao.getPessoas((String)cbPessoa.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Atendente".equals(cbAtendente.getSelectedItem())){
			Criterion c = Restrictions.eq("atendente", padrao.getAtendentes((String)cbAtendente.getSelectedItem()));
			criterios.add(c);
		}
		try{
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if(data02.compareTo(data01)>=0){
				criterios.add(Restrictions.between("criadoEm", data01, data02));
			}
		}catch(NullPointerException e){
		}
		if("".equals(txBuscar.getText().trim())){
			Criterion c = Restrictions.ilike("nome", txBuscar.getText().trim()+"%");
			criterios.add(c);
		}
		listarNegocios = new GenericDao().items(Negocio.class, session, criterios, order);
		preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
	}
	public class MudarCliente implements PropertyChangeListener{

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if(!evt.getOldValue().equals(evt.getNewValue()))
				receberObjeto();
		}
		
	}
	public void receberObjeto(){
		PfPj pfpj = new PfPj();
		session = HibernateFactory.getSession();
		session.beginTransaction();
		String nome = "";
		if(cbObject.getSelectedItem().equals(Modelos.Empresa.toString())){
			Empresa empresa = (Empresa)new EmpresaDao().receberObjeto(Empresa.class, Integer.parseInt(txCodObjeto.getText()), session);
			nome = empresa.getNome();
			pfpj = empresa.getPessoaJuridica();
		}
		else if(cbObject.getSelectedItem().equals(Modelos.Pessoa.toString())){
			Pessoa pessoa = (Pessoa)new PessoaDao().receberObjeto(Pessoa.class, Integer.parseInt(txCodObjeto.getText()), session);
			nome = pessoa.getNome();
			pfpj = pessoa.getPessoaFisica();
		}
		cbCategoriaCad.setSelectedItem(pfpj.getCategoria()==null?"":pfpj.getCategoria().getNome());
		cbOrigemCad.setSelectedItem(pfpj.getOrigem()==null?"":pfpj.getOrigem().getNome());
		cbNivelCad.setSelectedItem(pfpj.getNivel()==null?"":pfpj.getNivel().getNome());
		cbServicosCad.setSelectedItem(pfpj.getServico()==null?"":pfpj.getServico().getNome());
		if("".equals(txNome.getText().trim()))
			txNome.setText(nome);	
		session.close();
	}
	@SuppressWarnings("unchecked")
	public void invocarSalvamento(){
		if("".equals(txCodigo.getText())){	
			negocio.setCriadoEm(new Date());
			negocio.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		}
		negocio.setNome(txNome.getText().trim());
		negocio.setStatus(padrao.getStatus((String)cbStatusCad.getSelectedItem()));//
		if("".equals(cbAtendenteCad.getSelectedItem()))
			negocio.setAtendente(UsuarioLogado.getInstance().getUsuario());//
		else
			negocio.setAtendente(padrao.getAtendentes((String)cbAtendenteCad.getSelectedItem()));
		session = HibernateFactory.getSession();
		session.beginTransaction();
		if(cbObject.getSelectedItem().equals(Modelos.Empresa.toString())){
			Object o = new EmpresaDao().receberObjeto(Empresa.class, Integer.parseInt(txCodObjeto.getText()), session);
			negocio.setClasse("Empresa");
			negocio.setEmpresa((Empresa)o);
			negocio.setPessoa(null);
		}
		else if(cbObject.getSelectedItem().equals(Modelos.Pessoa.toString())){
			Object o = new PessoaDao().receberObjeto(Pessoa.class, Integer.parseInt(txCodObjeto.getText()), session);
			negocio.setClasse("Pessoa");
			negocio.setPessoa((Pessoa)o);
			negocio.setEmpresa(null);
		}
		negocio.setEtapa(receberEtapa(negocio));
		negocio.setDataInicio(dataInicio.getDate());
		if(dataFim.getDate()!=null)
			negocio.setDataFim(dataFim.getDate());
		negocio.setHonorario(new BigDecimal(txHonorario.getText().replace(".","").replace(",", ".")));
		negocio.setDescricao(txDescricao.getText().trim());
		
		PfPj pessoaFisicaOrJuridica = new PfPj();
		if(!cbCategoriaCad.getSelectedItem().equals("")){
			pessoaFisicaOrJuridica.setCategoria(padrao.getCategorias((String)cbCategoriaCad.getSelectedItem()));
		}
		if(!cbNivelCad.getSelectedItem().equals("")){
			pessoaFisicaOrJuridica.setNivel(padrao.getNiveis((String)cbNivelCad.getSelectedItem()));
		}
		if(!cbOrigemCad.getSelectedItem().equals("")){
			pessoaFisicaOrJuridica.setOrigem(padrao.getOrigens((String)cbOrigemCad.getSelectedItem()));
		}
		if(!cbServicosCad.getSelectedItem().equals("")){
			pessoaFisicaOrJuridica.setServico(padrao.getServicos((String)cbServicosCad.getSelectedItem()));
		}
		negocio.setPessoaFisicaOrJuridica(pessoaFisicaOrJuridica);
		negocio.setStatus(padrao.getStatus((String)cbStatusCad.getSelectedItem()));
		
		Set<ServicoContratado> servicosContratados = new HashSet<>();
		for(int i = 0; i< tbServicosContratados.getRowCount(); i++){
			ServicoContratado sc = new ServicoContratado();
			if(!tbServicosContratados.getValueAt(i, 0).equals(""))
				sc.setId((int) tbServicosContratados.getValueAt(i, 0));
			sc.setServicosAgregados(padrao.getServicosAgregados((String) tbServicosContratados.getValueAt(i, 1)));
			String valor = tbServicosContratados.getValueAt(i, 2).toString().replace(",", ".");
			sc.setValor(new BigDecimal(valor));
			sc.setNegocios(negocio);
			servicosContratados.add(sc);
		}
		negocio.setServicosContratados(servicosContratados);
		NegocioDao dao = new NegocioDao();
		if(dao.salvar(negocio, session)){
			listarNegocios = dao.listar(Negocio.class,session);
			preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
			preencherFormulario(negocio);
			salvarCancelar();
		}
		session.close();
	}
	public void enviarEtapa(Etapa e){
		String etapa = e.getNome();
		switch(etapa){
		case "Contato":
			rbContato.setSelected(true);
			break;
		case "Envio de Proposta":
			rbEnvioProposta.setSelected(true);
			break;
		case "Fechamento":
			rbFechamento.setSelected(true);
			break;
		case "Follow-up":
			rbFollowup.setSelected(true);
			break;
		case "Indefinida":
			rbIndefinida.setSelected(true);
			break;
		default:
			break;
		}
	}
	private Etapa receberEtapa(Negocio r){
		String etapa="";
		if(rbContato.isSelected()){
			etapa="Contato";
			r.setContato(new Date());
		}
		else if(rbEnvioProposta.isSelected()){
			etapa="Envio de Proposta";
			r.setEnvioProposta(new Date());
		}
		else if(rbFechamento.isSelected()){
			etapa="Fechamento";
			r.setFechamento(new Date());
		}
		else if(rbFollowup.isSelected()){
			etapa="Follow-up";
			r.setFollowUp(new Date());
		}
		else if (rbIndefinida.isSelected()){
			etapa="Indefinida";
			r.setIndefinida(new Date());
		}
		return padrao.getEtapa(etapa);
	}
	private boolean verificarCondicao(){
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
		if("".equals(txCodObjeto.getText())){
			builder.append("Primeiro vincule uma Pessoa ou uma Empresa e tente salvar novamente!");
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, builder.toString());
			return false;
		}
		else if(dataInicio.getDate()==null){
			builder.append("A data de inicio não pode ficar em branco ou não esta correta!");
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, builder.toString());
			return false;
		}
		else if(dataFim.getDate()!=null && !sdf.format(dataInicio.getDate()).equals(sdf.format(dataFim.getDate()))
				&& dataInicio.getDate().after(dataFim.getDate())){
			builder.append("Data de Fim não pode ser superior a Data de Inicio!\n");
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, builder.toString());
			return false;
		}
		try{
			Double.parseDouble(txHonorario.getText().trim().replace(".","").replace(",", "."));
		}catch (NumberFormatException e) {
			builder.append("O valor do honorário informado está incorreto!\n");
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, builder.toString());
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private void invocarExclusao(){
    	int escolha = JOptionPane.showConfirmDialog(br.com.tiagods.view.MenuView.jDBody, "Você deseja excluir esse registro? "
				+ "\nTodos os historicos serão perdidos, lembre-se que essa ação não terá mais volta!",
				"Pedido de Exclusao", JOptionPane.YES_NO_OPTION);
		if(escolha==JOptionPane.YES_OPTION){
			NegocioDao dao = new NegocioDao();
			boolean openHere = recebeSessao();
			boolean excluiu = dao.excluir(negocio,session);
			if(excluiu){
				limparFormulario(pnPrincipal);
				listarNegocios = (List<Negocio>)dao.listar(Negocio.class, session);
		    	preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
		    }
			fechaSessao(openHere);
		}
    }
	private void salvarCancelar(){
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		if("".equals(txCodigo.getText()))
			btnExcluir.setEnabled(false);
		desbloquerFormulario(false, pnCadastro);
		desbloquerFormulario(false, pnAndamento);
		desbloquerFormulario(false, pnServicosContratados);
	}
	private void pesquisar(){
		List<Negocio> lista = new ArrayList<>();
		for(int i =0;i<listarNegocios.size();i++){
			String texto = txBuscar.getText().trim().toUpperCase();
			if(listarNegocios.get(i).getNome().substring(0,texto.length()).equalsIgnoreCase(texto)){
				lista.add(listarNegocios.get(i));
			}
		}
		if(!lista.isEmpty()){
			preencherTabela(lista, tbPrincipal,txContadorRegistros);
		}
	}
	private void novoEditar(){
		desbloquerFormulario(true, pnCadastro);
		desbloquerFormulario(true, pnAndamento);
		desbloquerFormulario(true, pnServicosContratados);
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnExcluir.setEnabled(false);
		DefaultTableModel model = (DefaultTableModel) tbServicosContratados.getModel();
		while(model.getRowCount()>0){
			model.removeRow(0);
		}
		tbServicosContratados.setModel(model);
		if(this.negocio!=null)
			negocioBackup=negocio;
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
			else if(c instanceof JTable){
				DefaultTableModel model = (DefaultTableModel)((JTable)c).getModel();
				while(model.getRowCount()>0){
					model.removeRow(0);
				}
				((JTable)c).setModel(model);
			}
			else if(c instanceof JDateChooser){
				((JDateChooser)c).setDate(null);
			}
			else if(c instanceof JScrollPane){
				limparFormulario((Container)c);
			}
		}
		txCodigo.setText("");
		txCadastradoPor.setText("");
		txDataCadastro.setText("");
		txDescricao.setText("");
		txCodObjeto.setText("");
		txNomeObjeto.setText("");
		cbObject.setSelectedItem("Empresa");
		rbContato.setSelected(true);
		cbStatusCad.setSelectedItem("Em Andamento");
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
			else if(c instanceof JDateChooser){
				((JDateChooser)c).setEnabled(desbloquear);
			}
			else if(c instanceof JRadioButton){
				((JRadioButton)c).setEnabled(desbloquear);
			}
			else if(c instanceof JScrollPane){
				desbloquerFormulario(desbloquear,(Container)c);
			}
			txDescricao.setEditable(desbloquear);
			
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
	public void preencherTabela(List<Negocio> lista, JTable table, JLabel txContadorRegistros){
		if(lista.isEmpty()){
			new SemRegistrosJTable(table,"Relação de Negócios");
		}
		else{
			Object [] tableHeader = {"ID","NOME","STATUS","ETAPA","ORIGEM","NIVEL","TEMPO","CRIADO EM","ATENDENTE","ABRIR"};
			DefaultTableModel model = new DefaultTableModel(tableHeader,0){
				
				private static final long serialVersionUID = -8716692364710569296L;
				boolean[] canEdit = new boolean[]{
						false,false,false,false,false,false,false,false,false,true
				};
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit [columnIndex];
				}
			};
			for(int i=0;i<lista.size();i++){
				Negocio n = lista.get(i);
				Object[] linha = new Object[10];
				linha[0] = ""+n.getId();
				linha[1] = n.getNome();
				linha[2] = n.getStatus()==null?"":n.getStatus().getNome();
				linha[3] = n.getEtapa()==null?"":n.getEtapa().getNome();
				linha[4] = "";
				linha[5] = "";
				if(n.getPessoaFisicaOrJuridica()!=null){
					linha[4] = n.getPessoaFisicaOrJuridica().getOrigem()==null?"":n.getPessoaFisicaOrJuridica().getOrigem().getNome();
					linha[5] = n.getPessoaFisicaOrJuridica().getNivel()==null?"":n.getPessoaFisicaOrJuridica().getNivel().getNome();
				}
				linha[6] = "";
				if("Em Andamento".equals(n.getStatus().getNome()) 
						&& n.getDataFim()!=null 
						&& new Date().compareTo(n.getDataFim())==1){
					long diferenca = (new Date().getTime() - n.getDataFim().getTime()) + 3600000;
					linha[6]=(diferenca / 86400000L)+" dias atrasados";
				}
				try{
					Date criadoEm = n.getCriadoEm();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					linha[7] = sdf.format(criadoEm);
				}catch (NumberFormatException e) {
					linha[7] = "";
				}
				linha[8] = n.getAtendente()==null?"":n.getAtendente().getLogin();
				linha[9] = n.getClasse();
				model.addRow(linha);
			}
			table.setModel(model);
			table.setAutoCreateRowSorter(true);
			table.setSelectionBackground(Color.ORANGE);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			
			JButton btAbrir = new ButtonColumnModel(table,9).getButton();
			btAbrir.setActionCommand("Abrir");
			btAbrir.addActionListener(new AcaoInTable());
			table.getColumnModel().getColumn(9).setPreferredWidth(90);
		}
		txContadorRegistros.setText("Total: "+lista.size()+" registro(s)");
	}
	private void preencherServicos(Set<ServicoContratado> servicos){
		DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","NOME","VALOR","EXCLUIR"},0){
			boolean[] canEdit = new boolean[]{
					false,false,false,true
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
		Iterator<ServicoContratado> iterator = servicos.iterator();
		while(iterator.hasNext()){
			ServicoContratado s = iterator.next();
			Object[] o = new Object[4];
			o[0] = s.getId();
			o[1] = s.getServicosAgregados().getNome();
			o[2] = s.getValor().toString().replace(".", ",");
			o[3] = "Excluir";
			model.addRow(o);
		}
		tbServicosContratados.setModel(model);
		JButton btRem  = new ButtonColumnModel(tbServicosContratados,3).getButton();
		btRem.addActionListener(new AcaoInTableServicosContratados());
		tbServicosContratados.getColumnModel().getColumn(0).setPreferredWidth(40);
		tbServicosContratados.setAutoCreateRowSorter(true);
	}
	public class AcaoInTable implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			session = HibernateFactory.getSession();
			session.beginTransaction();
			String value = ((JButton)e.getSource()).getText();
			int id = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0));
			Negocio neg = (Negocio) new NegocioDao().receberObjeto(Negocio.class, id, session);
			if("Empresa".equals(value)){
				Empresa empresa = neg.getEmpresa();
				EmpresasView viewEmpresas = new EmpresasView(empresa);
				ControllerMenu.getInstance().abrirCorpo(viewEmpresas);
			}
			else if("Pessoa".equalsIgnoreCase(value)){
				Pessoa pessoa = neg.getPessoa();
				PessoasView viewPessoa = new PessoasView(pessoa);
				ControllerMenu.getInstance().abrirCorpo(viewPessoa);
			}
			session.close();
		}

	}
	
	public class AcaoInTableServicosContratados implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(telaEmEdicao){
				int row = tbServicosContratados.getSelectedRow();
				Object value = tbServicosContratados.getValueAt(row, 0);
				int i = JOptionPane.showConfirmDialog(br.com.tiagods.view.MenuView.jDBody, 
						"Deseja excluir o seguinte serviço: \n"+tbServicosContratados.getValueAt(row, 0)+" \nNome: "+tbServicosContratados.getValueAt(row, 1)+
						"\nValor: "+tbServicosContratados.getValueAt(row, 2)+"?","Pedido de remoção!",JOptionPane.YES_NO_OPTION);
				if(i==JOptionPane.OK_OPTION){
					if(!"".equals(value.toString())){
						session = HibernateFactory.getSession();
						session.beginTransaction();
						GenericDao dao = new GenericDao();
						ServicoContratado sec = (ServicoContratado) dao.receberObjeto(ServicoContratado.class, Integer.parseInt(value.toString()), session);
						if(dao.excluir(sec, session)){
							removerServico(row);
						}
						session.close();
					}
					else{
						removerServico(row);
					}
				}
				cbServicosAgregados.setSelectedItem("");
				txValorServico.setText("0,00");
				txIdServicoContratado.setText("");
			}
			else JOptionPane.showMessageDialog(MenuView.jDBody, "Clique em editar antes de tentar qualquer alteção!");
		}
	}
	public void removerServico(int row){
		DefaultTableModel model = (DefaultTableModel) tbServicosContratados.getModel();
		model.removeRow(row);
		tbServicosContratados.setModel(model);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED){
			//JComboBox combo = (JComboBox)e.getSource();
			boolean openHere = recebeSessao();
			realizarFiltro();
			fechaSessao(openHere);
			txCodObjeto.setText("");
			txCodObjeto.setBackground(new Color(250,250,250));
			txNomeObjeto.setText("");
			txNomeObjeto.setBackground(new Color(250,250,250));
			cbCategoriaCad.setSelectedItem("");
			cbOrigemCad.setSelectedItem("");
			cbNivelCad.setSelectedItem("");
			cbServicosCad.setSelectedItem("");
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() instanceof JTable && tbPrincipal.getSelectedRow()>=0 && 
				tbPrincipal.getColumnCount()>1 && !telaEmEdicao){
			boolean open = recebeSessao();
			int id = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(),0));
			this.negocio = (Negocio) new NegocioDao().receberObjeto(Negocio.class, id, session);
			preencherFormulario(this.negocio);
			if(open)
				fechaSessao(open);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!",
					"Em edicao...",JOptionPane.INFORMATION_MESSAGE);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		new UnsupportedOperationException();
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
	public class AcaoTabelaServicosContratados implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			int row = tbServicosContratados.getSelectedRow();
			txIdServicoContratado.setText(""+tbServicosContratados.getValueAt(row, 0));
			cbServicosAgregados.setSelectedItem((String)tbServicosContratados.getValueAt(row, 1));
			txValorServico.setText((String)tbServicosContratados.getValueAt(row, 2));
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		
	}
	
}
