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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Lista;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Prospeccao;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.TarefasSaveView;

import static br.com.tiagods.view.ProspeccaoView.*;

public class ControllerProspeccao implements ActionListener,ItemListener,MouseListener,PropertyChangeListener,KeyListener{
	private Session session = null;
	boolean telaEmEdicao = false;
	Prospeccao prospeccao;
	List<Prospeccao> listarProspeccao = new ArrayList<Prospeccao>();
	Prospeccao prospeccaoBackup;
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();
	
	String site;
	GenericDao dao = new GenericDao();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	SimpleDateFormat sdh = new SimpleDateFormat("HH:mm");
	
	@SuppressWarnings("unchecked")
	public void iniciar(Prospeccao prospeccao){
		this.prospeccao=prospeccao;
		boolean aberta = abrirSessao();
		JPanel[] panels = {pnPesquisa,pnCadastro,pnCadastroOrigem};
		for (JPanel panel : panels) {
			preencherComboBox(panel);
		}
		List<Criterion> criterion = new ArrayList<>();
		//criterion.add(Restrictions.eq("atendente", UsuarioLogado.getInstance().getUsuario()));  //departamento não se acostumou com a nova regra
		
		listarProspeccao = dao.items(Prospeccao.class, session, criterion, Order.desc("id"));
		preencherTabela(listarProspeccao, tbPrincipal, txContadorRegistros);
		if(this.prospeccao==null && !listarProspeccao.isEmpty()){
			this.prospeccao=listarProspeccao.get(0);
			preencherFormulario(this.prospeccao);
			tabbedPane.setSelectedIndex(0);
		}
		else if(this.prospeccao!=null){
			preencherFormulario(this.prospeccao);
			tabbedPane.setSelectedIndex(1);
		}
		fecharSessao(aberta);
		setarIcones();
		definirAcoes();
		salvarCancelar();
		LoadingView loading = LoadingView.getInstance();
		loading.fechar();
	}
	private boolean abrirSessao(){
		try{
			this.session = HibernateFactory.getSession();
			session.beginTransaction();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox[] combos = null;
		switch(e.getActionCommand()){
		case "Ordenar":
			recebeSessao();
			realizarFiltro();
			fechaSessao(true);
			break;
		case "Novo":
			site="";
			limparFormulario(pnCadastro);
			novoEditar();
			telaEmEdicao = true;
			negocio = new Negocio();
			negocio.setHonorario(new BigDecimal("0.00"));
			txHonorario.setText("0,00");
			dataInicio.setDate(new Date());
			txEmail.setText("");
			txCelular.setText("");
			txFone.setText("");
//			DefaultTableModel serv = (DefaultTableModel)tbServicosContratados.getModel();
//			while(serv.getRowCount()>0)
//				serv.removeRow(0);
			pnAuxiliar.setVisible(false);
			break;
		case "Editar":
			telaEmEdicao = true;
			if(!"".equals(txCodigo.getText()))
				novoEditar();
			break;
		case "Cancelar":
			telaEmEdicao = false;
			if(negocioBackup!=null){
				negocio = negocioBackup;
				session = HibernateFactory.getSession();
				session.beginTransaction();
				negocio = (Negocio)dao.receberObjeto(Negocio.class, negocio.getId(), session);
				preencherFormulario(negocio);
				session.close();
			}
			salvarCancelar();
			break;
		case "Excluir":
			String mensagem = "Se quiser usar o mesmo numero da proposta "+txCodigo.getText()+",\n"
					+ "prefira alterar as informações desse negocio usando a proposta de numero "+txCodigo.getText()+" no lugar de Excluir\n"
							+ "Para cancelar a exclusão clique em Não ou Sim para continuar!";
			int escolha = JOptionPane.showConfirmDialog(MenuView.jDBody, mensagem,"Alerta de Exclusão",JOptionPane.YES_NO_OPTION);
			if(escolha==JOptionPane.YES_OPTION){
				if(UsuarioLogado.getInstance().getUsuario()==negocio.getCriadoPor()){
					invocarExclusao();
				}
				else
					JOptionPane.showMessageDialog(MenuView.jDBody, "Esse negocio so pode ser excluido pelo criador( "+txCadastradoPor.getText()+" )!","Exclusão não autorizada",JOptionPane.WARNING_MESSAGE);
			}
			telaEmEdicao = false;
			break;
		case "Salvar":
			if(verificarCondicao()) {
				invocarSalvamento();
				telaEmEdicao = false;
			}
			break;
		case "Historico":
			if(!txCodigo.getText().equals("") && !telaEmEdicao){
				pnAuxiliar.setVisible(true);
				boolean open = recebeSessao();
				List<Criterion>criterios = new ArrayList<>();
				Criterion criterion = Restrictions.eq("negocio", negocio);
				criterios.add(criterion);
				Order order = Order.desc("dataEvento");
				List<Tarefa> tarefas = (List<Tarefa>) dao.items(Tarefa.class, session, criterios, order);
				new AuxiliarTabela(new Tarefa(),tbAuxiliar, tarefas, criterios,order);
				fechaSessao(open);
			}
			break;
		case "Esconder":
			pnAuxiliar.setVisible(false);
			break;
		case "VincularObjeto":
			SelecaoDialog dialog = null;
			if(!telaEmEdicao){
				JOptionPane.showMessageDialog(jDBody, "Para selecionar uma Empresa ou Pessoa, clique em Novo ou Editar para uma nova associação!","Somente em edição...",JOptionPane.INFORMATION_MESSAGE);
			}
			else{

				JComboBox[] comboNegocios = new JComboBox[]{cbCategoriaCad,cbOrigemCad,cbNivelCad,cbServicosCad};
				if(cbObject.getSelectedItem().equals(Modelos.Empresa.toString())){
					combos = new JComboBox[]{cbEmpresa};
					dialog =new SelecaoDialog(new Empresa(),txCodObjeto,txNomeObjeto,combos,comboNegocios,MenuView.getInstance(),true);
				}
				else if(cbObject.getSelectedItem().equals(Modelos.Pessoa.toString())){
					combos = new JComboBox[]{cbPessoa};
					dialog = new SelecaoDialog(new Pessoa(),txCodObjeto,txNomeObjeto,combos,comboNegocios,MenuView.getInstance(),true);
				}
				else if(cbObject.getSelectedItem().equals(Modelos.Prospeccao.toString())){
					combos = new JComboBox[]{cbProspeccao};
					dialog = new SelecaoDialog(new Prospeccao(),txCodObjeto,txNomeObjeto,combos,comboNegocios,MenuView.getInstance(),true);
				}
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				txCodObjeto.addPropertyChangeListener(new MudarCliente());
			}
			break;
		case "CriarCategoria":
			dialog = new SelecaoDialog(new Categoria(), null, null, new JComboBox[]{cbCategoria,cbCategoriaCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarNivel":
			dialog = new SelecaoDialog(new Nivel(), null, null, new JComboBox[]{cbNivel,cbNivelCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarOrigem":
			dialog = new SelecaoDialog(new Origem(), null, null, new JComboBox[]{cbOrigem,cbOrigemCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarServico":
			dialog = new SelecaoDialog(new Servico(), null, null, new JComboBox[]{cbServicos,cbServicosCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "AdicionarServicoAgregado":
			if(telaEmEdicao){
				try{
					Double.parseDouble(txValorServico.getText().trim().replace(",", "."));
					adicionarServico();
				}catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(MenuView.jDBody, "Valor informado está incorreto! User o padrao 0,00");
				}
			}
			else 
				JOptionPane.showMessageDialog(MenuView.jDBody, "Clique em editar antes de tentar qualquer alteração!");
			break;
		case "NovoServicoContratado":
			dialog = new SelecaoDialog(new ServicoAgregado(), null, null, new JComboBox[]{cbServicosAgregados},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "Nova Tarefa":
			if("".equals(txCodigo.getText())){
				String valor;
				if(telaEmEdicao){
					valor="Salve o registro atual e depois clique no Botao Nova Tarefa para criar uma nova tarefa para esse negocio!\nOu selecione um registro da tabela!";
				}
				else
					valor="Selecione um registro da tabela ou crie um Novo Negocio";
				JOptionPane.showMessageDialog(MenuView.jDBody, "Não pode criar uma tarefa para um negócio que ainda não existe!\n"
						+ valor);
			}
			else{
				TarefasSaveView taskView = new TarefasSaveView(null, this.negocio, MenuView.getInstance(),true);
				taskView.setVisible(true);
			}
			break;
		case "MailTo":
			URI urlMail = null;
			try{
				if(txEmail.getText().trim().length()>0){
					urlMail = new URI("mailto", txEmail.getText().trim(), null);
					Desktop.getDesktop().mail(urlMail);
				}
				else
					Desktop.getDesktop().mail();
			}catch(IOException | URISyntaxException ex){
				ex.printStackTrace();
			}
			break;
		case "OpenURL":
			URI browser = null;
			try{
				if(site.trim().length()>0){
					browser = new URI(site);
					Desktop.getDesktop().browse(browser);
				}
				else
					JOptionPane.showMessageDialog(MenuView.jDBody, "Não possui site",
							"Nenhuma Pagina foi encontrada",JOptionPane.INFORMATION_MESSAGE);
			}catch(IOException | URISyntaxException ex){
				ex.printStackTrace();
			}
			break;
		case "Exportar":
			DefaultTableModel model = (DefaultTableModel)tbPrincipal.getModel();
			if(model.getColumnCount()>1){
				exportarExcel();
			}
			else{
				JOptionPane.showMessageDialog(MenuView.jDBody,"Nenhum registro foi encontrado!");
			}
			break;
		case "VerPerda":
			if("Perdido".equals(cbStatusCad.getSelectedItem()) && !"".equals(txCodigo.getText())){
				if(dialogPerda!=null)
					dialogPerda.dispose();
				dialogPerda = new NegocioPerdaDialog(MenuView.getInstance(),true,negocio);
				dialogPerda.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogPerda.setVisible(true);
			}
			break;
		case "Enviar Documento":
			if("".equals(txDocumentoNome.getText())){
				JOptionPane.showMessageDialog(null, "Anexe um documento antes de enviar!","Não existe arquivo...",JOptionPane.ERROR_MESSAGE);
			}
			else{
				session = HibernateFactory.getSession();
				session.beginTransaction();
		        Documento doc = gerarSerial(new File(txDocumentoPath.getText()));
				if(doc!=null){
					Set<Documento> documentos = negocio.getDocumentos();
					documentos.add(doc);
					negocio.setDocumentos(documentos);
					dao.salvar(negocio, session);
					preencherDocumentos(negocio.getDocumentos());
					
					txDocumentoNome.setText("");
					txDocumentoPath.setText("");
					txDocumentoDescricao.setText("");
				}
				session.close();
			}
			break;
		case "Anexar Documento":
			 if("".equals(txCodigo.getText())){
		         JOptionPane.showMessageDialog(null, "Antes, salve o Negocio para depois anexar itens!");
		     }
			 else if(!telaEmEdicao){
				 JOptionPane.showMessageDialog(null, "Clique em editar para anexar um documento!");	 
			 }
			 else if("".equals(txDocumentoNome.getText().trim())){
		            JOptionPane.showMessageDialog(null, "Insira um nome!");
		     }
			 else{
		    	 File file = carregarArquivo();
		    	 txDocumentoPath.setText(file.getAbsolutePath());
		     }
			break;
		default:
			break;
		}	
	}
	private void definirAcoes(){
		data1.addPropertyChangeListener(this);
		data2.addPropertyChangeListener(this);
		tbPrincipal.addMouseListener(this);
		cbOrigem.addItemListener(this);
		cbServicos.addItemListener(this);
		cbOrdenacao.addItemListener(this);
		cbBuscarPor.addItemListener(this);
		rbCrescente.addItemListener(this);
		rbDecrescente.addItemListener(this);
		cbAtendente.addItemListener(this);
		txBuscar.addKeyListener(this);
		rbCrescente.addItemListener(this);
		tabbedPane.addMouseListener(this);
	}
	private void desbloquearFormulario(boolean desbloquear,Container container){
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
				JViewport viewPort = ((JScrollPane)c).getViewport();
				desbloquearFormulario(desbloquear,(Container)viewPort);
			}
			else if(c instanceof JTabbedPane || c instanceof JPanel){
				desbloquearFormulario(desbloquear,(Container)c);
			}
		}
	}
	private void fecharSessao(boolean fechar){
		if(fechar) session.close();
	}
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
	}
	private void limparFormulario(Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JScrollPane){
				JViewport viewPort = ((JScrollPane)c).getViewport();
				limparFormulario((Container)viewPort);
			}
			else if(c instanceof JTabbedPane || c instanceof JPanel){
				limparFormulario((Container)c);
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setSelectedIndex(0);
				((JComboBox)c).setSelectedItem("");
			}
			else if(c instanceof JTable){
				DefaultTableModel model = (DefaultTableModel)((JTable)c).getModel();
				while(model.getRowCount()>0){
					model.removeRow(0);
				}
				((JTable)c).setModel(model);
			}
			else if(c instanceof JTextArea){
				((JTextArea)c).setText("");
			}
			else if(c instanceof JTextField){
				((JTextField)c).setText("");
			}
			else if(c instanceof JFormattedTextField){
				((JFormattedTextField)c).setText("");
			}			
		}
		
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void novoEditar(){
		desbloquearFormulario(true, pnCadastro);
		pnAuxiliar.setVisible(true);
		btNovo.setEnabled(false);
		btEditar.setEnabled(false);
		btSalvar.setEnabled(true);
		btCancelar.setEnabled(true);
		btExcluir.setEnabled(false);
		cbAtendenteCad.setSelectedItem(UsuarioLogado.getInstance().getUsuario().getNome());
		txEmail.setEditable(false);
		if(this.prospeccao.getId()>0)
			prospeccaoBackup=prospeccao;	
	}
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component.getName()!=null && component instanceof JComboBox)
				padrao.preencherCombo((JComboBox<String>)component, session, null);

		}
	}
	private void preencherFormulario(Prospeccao p){
		txCodigo.setText(""+p.getId());
		txEmpresa.setText(p.getNome());
		txNomeContato.setText(p.getResponsavel());
		txDepartamento.setText(p.getDepartamento());
		
		txEndereco.setText(p.getEndereco());
		PfPj pp = p.getPfpj();  
		txTelefone.setText(pp.getTelefone());
		txCelular.setText(pp.getCelular());
		txEmail.setText(pp.getEmail());
		txSite.setText(pp.getSite());
		
		cbTipoContatoCad.setSelectedItem(p.getTipoContato()!=null?p.getTipoContato().getNome():"");
		ckConviteEventos.setSelected(p.getConviteParaEventos()==1?true:false);
		ckMaterial.setSelected(p.getMaterial()==1?true:false);
		ckNewsletter.setSelected(p.getNewsletter()==1?true:false);
		
		cbOrigemCad.setSelectedItem(pp.getOrigem()==null?"":pp.getOrigem().getNome());
		txDetalhesDaOrigem.setText(pp.getDetalhesOrigem());
		Set<Lista> listas = p.getListas();
		preencherListas(listas);
		Set<Tarefa> tarefas = pp.getTarefas();
		preencherTarefas(tarefas);		
	}
	private void preencherListas(Set<Lista> listas){
		
	}
	private void preencherTarefas(Set<Tarefa> tarefas){
		
	}
	private void preencherTabela(List<Prospeccao> listaProspects, JTable tabela, JTextField txContador){
		
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}
	private Criterion realizarBusca(){
		Criterion c = null;
		if(!"".equals(txBuscar.getText().trim())){
			if("Código".equals(cbBuscarPor.getSelectedItem())){
				try{
					int num = Integer.parseInt(txBuscar.getText());
					c = Restrictions.eq("id",num);
				}catch(NumberFormatException e){
					c = null;
				} 
			}
			else
				c = Restrictions.ilike("nome", txBuscar.getText().trim()+"%");
		}
		return c;
	}

	private void realizarFiltro() {
		if(!telaEmEdicao){
			List<Criterion> criterios = new ArrayList<>();
			Order order = receberOrdenacao();
			criterios.addAll(receberFiltroSuperior());
			Criterion c = realizarBusca();
			if(c!=null) 
				criterios.add(c);
			listarNegocios = dao.items(Negocio.class, session, criterios, order);
			preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!","Em edicao...",JOptionPane.INFORMATION_MESSAGE);
	}
	private List<Criterion> receberFiltroSuperior(){
		List<Criterion> criterios = new ArrayList<>();
		if(!"Origem".equals(cbOrigem.getSelectedItem())){
			Criterion c = Restrictions.eq("pfpj.origem", padrao.getOrigens((String)cbOrigem.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Produtos/Servicos".equals(cbServicos.getSelectedItem())){
			Criterion c = Restrictions.eq("pfpj.servico", padrao.getServicos((String)cbServicos.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Atendente".equals(cbAtendente.getSelectedItem())){
			Criterion c = Restrictions.eq("atendente", padrao.getAtendentes((String)cbAtendente.getSelectedItem()));
			criterios.add(c);
		}
		if(!"TipoContato".equals(cbTipoContatoPesquisa.getSelectedItem())){
			Criterion c = Restrictions.eq("tipoContato", padrao.getAtendentes((String)cbAtendente.getSelectedItem()));
			criterios.add(c);
		}
		if(!"Lista".equals(cbLista.getSelectedItem())){
			Criterion c = Restrictions.eq("tipoContato", padrao.getAtendentes((String)cbAtendente.getSelectedItem()));
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
		return criterios;
	}
	
	private Order receberOrdenacao(){
		Order order=null;
		String campo = "";
		switch((String)cbOrdenacao.getSelectedItem()){
		case "Código":
			campo = "id";
			break;
		case "Nome":
			campo = "nome";
			break;
		case "Data Criação":
			campo = "criadoEm";
			break;
		case "Data Vencimento":
			campo = "dataFim";
			break;
		case "Data Finalização":
			campo = "dataFinalizacao";
			break;
		default:
			campo="id";
			break;
		}
		if(rbCrescente.isSelected())
			order = Order.asc(campo);
		else
			order = Order.desc(campo);
		return order;
	}
	private void salvarCancelar(){
		btSalvar.setEnabled(false);
		btCancelar.setEnabled(false);
		btNovo.setEnabled(true);
		btEditar.setEnabled(true);
		btExcluir.setEnabled(true);
		if("".equals(txCodigo.getText()))
			btExcluir.setEnabled(false);
		desbloquearFormulario(false, pnCadastro);
		
	}
	public void setarIcones() throws NullPointerException{
    	ImageIcon iconNovo = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
    	btNovo.setIcon(recalculate(iconNovo));
    	btOrigemAdd.setIcon(iconNovo);
    	btTipoContatoAdd.setIcon(iconNovo);
    	ImageIcon iconEdit = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_edit.png"));
    	btEditar.setIcon(recalculate(iconEdit));
    	ImageIcon iconSave = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_save.png"));
    	btSalvar.setIcon(recalculate(iconSave));
    	ImageIcon iconCancel = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_cancel.png"));
    	btCancelar.setIcon(recalculate(iconCancel));
    	ImageIcon iconTrash = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_trash.png"));
    	btExcluir.setIcon(recalculate(iconTrash));

    	ImageIcon iconNewTask = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_addtask.png"));
    	btNovaTarefa.setIcon(recalculate(iconNewTask));
    	ImageIcon iconTask = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_task.png"));
    	btHistorico.setIcon(recalculate(iconTask));

    	ImageIcon iconEsconder = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_nofixar.png"));
    	btEsconder.setIcon(recalculate(iconEsconder));

        ImageIcon iconMail = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_mail.png"));
    	btEmail.setIcon(recalculate(iconMail));
    	ImageIcon iconURL = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_chrome.png"));
    	btSite.setIcon(recalculate(iconURL));

//    	ImageIcon iconImp = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_import.png"));
//    	btnImportar.setIcon(recalculate(iconImp));
//
//    	ImageIcon iconExp = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_export.png"));
//    	btnExportar.setIcon(recalculate(iconExp));
    }
    public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
    public ImageIcon recalculate(ImageIcon icon, int valor) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()-valor, icon.getIconHeight()-valor, 100));
    	return icon;
    }
	
	
}
