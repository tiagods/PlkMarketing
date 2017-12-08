package br.com.tiagods.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Lista;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Prospeccao;
import br.com.tiagods.model.ProspeccaoTipoContato;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.ServicoContratado;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.dialog.SelecaoDialog;
import br.com.tiagods.view.interfaces.ButtonColumnModel;
import br.com.tiagods.view.interfaces.ExcelGenerico;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;
import jxl.write.WriteException;

import static br.com.tiagods.view.ProspeccaoView.*;

public class ControllerProspeccao implements ActionListener,ItemListener,MouseListener,PropertyChangeListener,KeyListener{
	private Session session = null;
	boolean telaEmEdicao = false;
	Prospeccao prospeccao;
	List<Prospeccao> listarProspeccao = new ArrayList<>();
	Prospeccao prospeccaoBackup;
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();

	GenericDao dao = new GenericDao();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	SimpleDateFormat sdh = new SimpleDateFormat("HH:mm");

	@SuppressWarnings("unchecked")
	public void iniciar(Prospeccao prospeccao){
		this.prospeccao=prospeccao;
		boolean aberta = abrirSessao();
		rbDecrescente.setSelected(true);		
		JPanel[] panels = {pnPesquisa,pnCadastro,pnCadastroOrigem,pnLista};
		for (JPanel panel : panels) {
			preencherComboBox(panel);
		}
		List<Criterion> criterion = new ArrayList<>();
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
	private void abrirModelo(){

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
	private void abrirUrl(){
		URI browser = null;
		try{
			if(txSite.getText().trim().length()>0){
				browser = new URI(txSite.getText().trim());
				Desktop.getDesktop().browse(browser);
			}
			else
				JOptionPane.showMessageDialog(MenuView.jDBody, "Não possui site",
						"Nenhuma Pagina foi encontrada",JOptionPane.INFORMATION_MESSAGE);
		}catch(IOException | URISyntaxException ex){
			ex.printStackTrace();
		}
	}
	private void adicionarLista(){
		DefaultTableModel model = (DefaultTableModel) tbLista.getModel();
		boolean contains = false;
		boolean open = abrirSessao();
		Lista lista = padrao.getListas(cbListaCad.getSelectedItem().toString());
		for(int i = 0 ; i<model.getRowCount();i++){
			if(Integer.parseInt(String.valueOf(model.getValueAt(i, 0)))==lista.getId()){
				contains = true;
			}
		}
		if(!contains){
			Object[] o = new Object[5];
			o[0]=""+lista.getId();
			o[1]=lista.getNome();
			o[2]=lista.getDetalhes();
			o[3]=lista.getCriadoEm()==null?"":sdf.format(lista.getCriadoEm());
			o[4] = recalculate(new ImageIcon(ControllerProspeccao.class
					.getResource("/br/com/tiagods/utilitarios/button_trash.png")));
			model.addRow(o);
			tbLista.setModel(model);
		}
		else
			JOptionPane.showMessageDialog(MenuView.jDBody, "Registro ja se encontra na lista selecionada!");
		fecharSessao(open);
	}
	public class AcaoTabelaListaContatos implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(telaEmEdicao){
				int row = tbLista.getSelectedRow();
				Object value = tbLista.getValueAt(row, 0);
				int i = JOptionPane.showConfirmDialog(br.com.tiagods.view.MenuView.jDBody,
						"Deseja excluir a seguinte lista: \n"+tbLista.getValueAt(row, 0)+" \nNome: "+tbLista.getValueAt(row, 1)+
						"\nDetalhes: "+tbLista.getValueAt(row, 2)+"?","Pedido de remoção!",JOptionPane.YES_NO_OPTION);
				if(i==JOptionPane.OK_OPTION){
					if(!"".equals(value.toString())){
						boolean abrir = abrirSessao();
						Prospeccao sec = (Prospeccao) dao.receberObjeto(Prospeccao.class, Integer.parseInt(value.toString()), session);
						if(dao.excluir(sec, session)){
							removerDaLista(row);
						}
						fecharSessao(abrir);
					}
					else{
						removerDaLista(row);
					}
				}
			}
			else JOptionPane.showMessageDialog(MenuView.jDBody, "Clique em editar antes de realizar qualquer alteração!");
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Ordenar":
			boolean open = abrirSessao();
			realizarFiltro();
			fecharSessao(open);
			break;
		case "Novo":
			limparFormulario(pnCadastro);
			novoEditar();
			telaEmEdicao = true;
			prospeccao = new Prospeccao();		
			pnAuxiliar.setVisible(false);
			break;
		case "Editar":
			telaEmEdicao = true;
			if(!"".equals(txCodigo.getText()))
				novoEditar();
			break;
		case "Cancelar":
			telaEmEdicao = false;
			if(prospeccaoBackup!=null){
				prospeccao = prospeccaoBackup;
				open = abrirSessao();
				prospeccao = (Prospeccao)dao.receberObjeto(Prospeccao.class, prospeccao.getId(), session);
				preencherFormulario(prospeccao);
				fecharSessao(open);
			}
			salvarCancelar();
			break;
		case "Excluir":
			if(UsuarioLogado.getInstance().getUsuario()==prospeccao.getPfpj().getCriadoPor()){
				invocarExclusao();
			}
			else
				JOptionPane.showMessageDialog(MenuView.jDBody, "Esse registro so pode ser excluido pelo criador( "+txCadastradoPor.getText()+" )!","Exclusão não autorizada",JOptionPane.WARNING_MESSAGE);
			telaEmEdicao = false;
			break;
		case "Salvar":
			invocarSalvamento();
			telaEmEdicao = false;
			break;
		case "Historico":
			if(!txCodigo.getText().equals("") && !telaEmEdicao){
				pnAuxiliar.setVisible(true);
				open = abrirSessao();
				List<Criterion>criterios = new ArrayList<>();
				Criterion criterion = Restrictions.eq("prospeccao", prospeccao);
				criterios.add(criterion);
				Order order = Order.desc("dataEvento");
				List<Tarefa> tarefas = (List<Tarefa>) dao.items(Tarefa.class, session, criterios, order);
				new AuxiliarTabela(new Tarefa(),tbAuxiliar, tarefas, criterios,order,null);
				fecharSessao(open);
			}
			break;
		case "Esconder":
			pnAuxiliar.setVisible(false);
			break;
		case "CriarOrigem":
			SelecaoDialog dialog = new SelecaoDialog(new Origem(), null, null, new JComboBox[]{cbOrigem,cbOrigemCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarLista":
			dialog = new SelecaoDialog(new Lista(), null, null, new JComboBox[]{cbLista,cbListaCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarTipo":
			dialog = new SelecaoDialog(new ProspeccaoTipoContato(), null, null, new JComboBox[]{cbTipoContatoPesquisa,cbTipoContatoCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarServico":
			dialog = new SelecaoDialog(new Servico(), null, null, new JComboBox[]{cbServicos,cbServicosCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "Nova Tarefa":
			if("".equals(txCodigo.getText())){
				String valor;
				if(telaEmEdicao){
					valor="Salve o registro atual e depois clique no Botao Nova Tarefa para criar uma nova tarefa para esse cadastro!\nOu selecione um registro da tabela!";
				}
				else
					valor="Selecione um registro da tabela ou crie um Novo Registro";
				JOptionPane.showMessageDialog(MenuView.jDBody, "Não é possivel criar uma tarefa pois o registro ainda não existe!\n"
						+ valor);
			}
			else{
				TarefasSaveView taskView = new TarefasSaveView(null, this.prospeccao,null, MenuView.getInstance(),true,null,false);
				taskView.setVisible(true);
				
				taskView.addWindowListener(new WindowListener() {
					@Override
					public void windowOpened(WindowEvent e) {

					}

					@Override
					public void windowIconified(WindowEvent e) {

					}

					@Override
					public void windowDeiconified(WindowEvent e) {
					}

					@Override
					public void windowDeactivated(WindowEvent e) {
					}

					@Override
					public void windowClosing(WindowEvent e) {
					}
					@Override
					public void windowClosed(WindowEvent e) {
						boolean open = abrirSessao();
						prospeccao = (Prospeccao)dao.receberObjeto(Prospeccao.class, Integer.parseInt(txCodigo.getText()), session);
						preencherTarefas(prospeccao);
						fecharSessao(open);
					}
					@Override
					public void windowActivated(WindowEvent e) {

					}
				});
			}
			break;
		case "AdicionarLista":
			if(telaEmEdicao)
				adicionarLista();
			else JOptionPane.showMessageDialog(MenuView.jDBody, "Clique em editar antes de realizar qualquer alteração!");
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
			abrirUrl();
			break;
		case "ExportarEMailMarketingLocaweb":
			if(validarQuantidadeRegistros(tbPrincipal))
				exportarTxtEmails();
			else
				JOptionPane.showMessageDialog(MenuView.jDBody,"Nenhum registro foi encontrado!");
			break;
		case "Exportar":
			if(validarQuantidadeRegistros(tbPrincipal))
				exportarExcel();
			else
				JOptionPane.showMessageDialog(MenuView.jDBody,"Nenhum registro foi encontrado!");
			break;
		case "ImportarNovosContatos":
			int escolha = JOptionPane.showConfirmDialog(MenuView.jDBody,"Antes de realizar a importação é importante usar um modelo padrão. \n"
					+ "No formato xls e xlsx, colunas com nomes e identificadores corretos.\n Deseja abrir um Modelo?","",JOptionPane.YES_NO_OPTION);
			if(escolha==JOptionPane.YES_OPTION)
				abrirModelo();
			else
				iniciarImportacao();
			break;
		case "Lote":
			Set<Integer> lotes = receberLotes();
			if(lotes!=null && !lotes.isEmpty())
				new TarefasSaveView(null, new Prospeccao(),null, MenuView.getInstance(),true,lotes,true)
				.setVisible(true);
			break;
		default:
			if(!telaEmEdicao){
				open = abrirSessao();
				realizarFiltro();
				fecharSessao(open);
			}
			break;
		}	
	}
	private Set<Integer> receberLotes(){
		Set<Integer> lotes = new HashSet<>();
		Object[] opcao = {"Filtro do Sistema","Digitar registros","Cancelar"};
		int n = JOptionPane.showOptionDialog(null, "Deseja usar os registros filtrados pelo sistema ou informar manualmente\nos registros que devem ser atualizados?", 
				"Escolha uma opção", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[2]);
		if(n == JOptionPane.NO_OPTION) {
			JTextArea ta = new JTextArea(20, 10);
			Object[] acao = {"Concluir","Cancelar"};
			int escolha = JOptionPane.showOptionDialog(null, new JScrollPane(ta),"Informe o texto dentro da caixa",JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_OPTION, null, acao, acao[1]);
			if(escolha == JOptionPane.YES_OPTION) {
				//System.out.println(ta.getText());
				String[] value = ta.getText().split(" |\n|,|;");
				session = HibernateFactory.getSession();
				session.beginTransaction();
				try {
					boolean naoExiste = false;
					for(String s : value) {
						try {
							int i = Integer.parseInt(s);
							Prospeccao p = (Prospeccao)dao.receberObjeto(Prospeccao.class, i, session);
							if(p!=null)
								lotes.add(i);
							else
								naoExiste = true;
						}catch(Exception ex) {
							JOptionPane.showMessageDialog(MenuView.jDBody,"Os registros devem ser informados como numero ex:{1,2,3,4,5}!\n"+ex);
						}
					}
					if(naoExiste) 
						JOptionPane.showMessageDialog(MenuView.jDBody,"Algun(s) registros não foram reconhecidos pois não existem no sistema!");
				}catch (Exception e) {
					JOptionPane.showMessageDialog(MenuView.jDBody,"Os registros devem ser informados como numero ex:{1,2,3,4,5}!");
				}finally {
					session.close();
				}
				
			}
		}
		else if(n ==  JOptionPane.YES_OPTION){
			DefaultTableModel md2 = (DefaultTableModel)tbPrincipal.getModel();
			int i = 0;
			if(md2.getRowCount()==0) {
				JOptionPane.showMessageDialog(MenuView.jDBody,"Nenhum registro foi encontrado na tabela!");
				return null;
			}
			while(md2.getRowCount()>i) {
				lotes.add(Integer.parseInt(String.valueOf(md2.getValueAt(i, 0))));
				i++;
			}
		}		
		else return null;
		return lotes;
	}
	private String carregarArquivo(String title){
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle(title);
		String local = "";
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Planilha do Excel (*.xls)", ".xls"));
		int retorno = chooser.showSaveDialog(null);
		if(retorno==JFileChooser.APPROVE_OPTION){
			local = chooser.getSelectedFile().getAbsolutePath(); //
		}
		return local;
	}
	private void definirAcoes(){
		data1.addPropertyChangeListener(this);
		data2.addPropertyChangeListener(this);
		tbPrincipal.addMouseListener(this);
		cbTipoContatoPesquisa.addItemListener(this);
		cbOrigem.addItemListener(this);
		cbServicos.addItemListener(this);
		cbOrdenacao.addItemListener(this);
		cbBuscarPor.addItemListener(this);
		cbAtendente.addItemListener(this);
		cbLista.addItemListener(this);
		ckConviteEventosPesquisa.addActionListener(this);
		ckMaterialPesquisa.addActionListener(this);
		ckNewsletterPesquisa.addActionListener(this);
		rbCrescente.addActionListener(this);
		rbDecrescente.addActionListener(this);
		txBuscar.addKeyListener(this);
		tabbedPane.addMouseListener(this);
		btCancelar.addActionListener(this);
		btEditar.addActionListener(this);
		btEmail.addActionListener(this);
		btEsconder.addActionListener(this);
		btExcluir.addActionListener(this);
		btExportarMalaDireta.addActionListener(this);
		btHistorico.addActionListener(this);
		btnExpMailmktLocaweb.addActionListener(this);
		btNovaTarefa.addActionListener(this);
		btNovo.addActionListener(this);
		btOrigemAdd.addActionListener(this);
		btSalvar.addActionListener(this);
		btSite.addActionListener(this);
		btTipoContatoAdd.addActionListener(this);
		btServicosAdd.addActionListener(this);
		btListaAdd.addActionListener(this);
		btAdicionarALista.addActionListener(this);
		txBuscar.addKeyListener(this);
		tabbedPane.addMouseListener(this);
		btnExportar.addActionListener(this);
		btnLote.addActionListener(this);
	}
	@SuppressWarnings("rawtypes")
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
			else if(c instanceof JCheckBox){
				((JCheckBox)c).setEnabled(desbloquear);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void exportarExcel(){
		int opcao = JOptionPane.showConfirmDialog(MenuView.jDBody, "Para exportar, realize um filtro\n"
				+"Todos os registros serão exportados para o formato excel, deseja imprimir o filtro realizado",
				"Exportar Negocios",JOptionPane.YES_NO_OPTION);
		if(opcao==JOptionPane.YES_OPTION){
			String export = carregarArquivo("Salvar arquivo");
			if(!"".equals(export)){
				session = HibernateFactory.getSession();
				session.beginTransaction();
				realizarFiltro();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				ArrayList<ArrayList> listaImpressao = new ArrayList<>();

				Integer[] colunasLenght = new Integer[]{6,29,9,13,5,15,13,14,14,13,12,11,14,10,10,16,10,19,16,30,16,10,19,16,30};
				String[] cabecalho = new String[]{
						"Cod","Nome", "Responsavel","Departamento","Tipo de Contato","Convite para Eventos","Material","Newsletter",
						"E-mail","Site","Telefone","Celular","Criado Em","Atendente","Endereço",
						"Servicos/Produtos","Origem","Detalhes da Origem","Resumo","Apresentação","Listas","Qtde Negócios",
						"Status Negocio","Honorário","Outros Serviços"};
				listaImpressao.add(new ArrayList<>());
				for(String c : cabecalho){
					listaImpressao.get(0).add(c);
				}
				for(int i = 0;i<listarProspeccao.size();i++){
					listaImpressao.add(new ArrayList());
					Prospeccao p = listarProspeccao.get(i);
					listaImpressao.get(i+1).add(p.getId());
					listaImpressao.get(i+1).add(p.getNome());
					listaImpressao.get(i+1).add(p.getResponsavel());
					listaImpressao.get(i+1).add(p.getDepartamento());
					listaImpressao.get(i+1).add(p.getTipoContato().getNome());
					listaImpressao.get(i+1).add(p.getConviteParaEventos()==1?"Sim":"Não");
					listaImpressao.get(i+1).add(p.getMaterial()==1?"Sim":"Não");
					listaImpressao.get(i+1).add(p.getNewsletter()==1?"Sim":"Não");

					PfPj pfpj = p.getPfpj();
					listaImpressao.get(i+1).add(pfpj.getEmail());
					listaImpressao.get(i+1).add(pfpj.getSite());
					listaImpressao.get(i+1).add(pfpj.getTelefone());
					listaImpressao.get(i+1).add(pfpj.getCelular());
					listaImpressao.get(i+1).add(sdf.format(pfpj.getCriadoEm()));
					listaImpressao.get(i+1).add(pfpj.getAtendente().getNome());
					listaImpressao.get(i+1).add(p.getEndereco());

					listaImpressao.get(i+1).add(pfpj.getServico()==null?"":pfpj.getServico().getNome());
					listaImpressao.get(i+1).add(pfpj.getOrigem()==null?"":pfpj.getOrigem().getNome());
					listaImpressao.get(i+1).add(pfpj.getDetalhesOrigem());

					listaImpressao.get(i+1).add(pfpj.getResumo());
					listaImpressao.get(i+1).add(pfpj.getApresentacao());
					StringBuilder listas = new StringBuilder();
					Iterator<Lista> iterator = p.getListas().iterator();
					int qtdListas = 0;
					while(iterator.hasNext()){
						listas.append(iterator.next().getNome());
						listas.append("\n");
					}
					listaImpressao.get(i+1).add(listas.toString());		

					int qtdNegocios=0;
					StringBuilder valorHonorario = new StringBuilder();
					StringBuilder statusNegocio = new StringBuilder();
					StringBuilder statusServicosNegocios = new StringBuilder();

					List<Criterion> criterios = new ArrayList<>();
					criterios.add(Restrictions.eq("prospeccao", p));
					List<Negocio> listaNegocios = dao.items(Negocio.class, session, criterios, Order.asc("id"));
					if(!listaNegocios.isEmpty()){
						for(Negocio negocio : listaNegocios){
							qtdNegocios++;

							valorHonorario.append(negocio.getId());
							valorHonorario.append("-");
							valorHonorario.append(NumberFormat.getCurrencyInstance().format(negocio.getHonorario()));
							valorHonorario.append("\n ");

							statusNegocio.append(negocio.getId());
							statusNegocio.append("-");
							statusNegocio.append(negocio.getStatus().getNome());
							statusNegocio.append("\n ");

							Set<ServicoContratado> servicos = negocio.getServicosContratados();
							for(ServicoContratado sc : servicos){
								statusServicosNegocios.append(negocio.getId());
								statusServicosNegocios.append(" - ");
								statusServicosNegocios.append(sc.getServicosAgregados().getNome());
								statusServicosNegocios.append(" - ");
								statusServicosNegocios.append(NumberFormat.getCurrencyInstance().format(sc.getValor()));
								statusServicosNegocios.append("\n ");
							}

						}
					}
					listaImpressao.get(i+1).add(qtdNegocios);//possui negocio
					listaImpressao.get(i+1).add(statusNegocio.toString());//status do negocio
					listaImpressao.get(i+1).add(valorHonorario.toString());//valor do negocio
					listaImpressao.get(i+1).add(statusServicosNegocios.toString());//valor do negocio
				}
				ExcelGenerico planilha = new ExcelGenerico(export+".xls",listaImpressao,colunasLenght);
				try {
					planilha.gerarExcel();
					dao.salvarLog(session, UsuarioLogado.getInstance().getUsuario(), "Prospeccao", "Exportar", "Exportou relatorio xls");
					JOptionPane.showMessageDialog(null, "Gerado com sucesso em : "+export+".xls");
					Desktop.getDesktop().open(new File(export+".xls"));
				} catch (WriteException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao criar a planilha "+e1);
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao criar a planilha "+e1);
				} finally{
					session.close();
				}
			}
		}
	}
	private void exportarTxtEmails() {
		boolean open = abrirSessao();
		realizarFiltro();
		if(listarProspeccao.size()>0){
			String separator = System.getProperty("line.separator");
			StringBuilder builder = new StringBuilder();
			builder.append("email");
			builder.append("\t");
			builder.append("nome");
			listarProspeccao.forEach(p->{
				if(p.getPfpj().getEmail().trim().length()>0){
					builder.append(separator);
					builder.append(p.getPfpj().getEmail());
					builder.append("\t");
					builder.append(p.getNome());
				}
			});
			JOptionPane.showMessageDialog(MenuView.jDBody, "Se houver Contatos com e-mails em branco, ele não serão exportados");
			File txtTemp = new File(
					System.getProperty("java.io.tmpdir")
					+"/contacts"
					+new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date())+".txt");
			try{
				txtTemp.createNewFile();
				FileWriter fw = new FileWriter(txtTemp);
				fw.write(builder.toString());
				fw.flush();
				fw.close();
				dao.salvarLog(session, UsuarioLogado.getInstance().getUsuario(), "Prospeccao", "Exportar", "Exportou relatorio txt de Mala Direta");
				Desktop.getDesktop().open(txtTemp);
				int escolha = JOptionPane.showConfirmDialog(MenuView.jDBody, 
						"Deseja abrir a página E-Mail Marketing Locaweb?",
						"Abrir URL",JOptionPane.YES_NO_OPTION);
				if(escolha==JOptionPane.YES_OPTION){
					Desktop.getDesktop().browse(new URI("http://emailmkt2.locaweb.com.br/admin"));
				}
			}catch(IOException ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(MenuView.jDBody, "Não foi possivel gerar o arquivo solicitado:\nInforme o erro ao seu administrador de sistema:\n"+ex, "Ops...",JOptionPane.ERROR_MESSAGE);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		else
			JOptionPane.showMessageDialog(MenuView.jDBody, "Nenhum registro foi encontrado usando o filtro atual.");
		fecharSessao(open);
	}
	private void fecharSessao(boolean fechar){
		if(fechar) session.close();
	}
	private void iniciarImportacao() {
		// TODO Auto-generated method stub

	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED && !telaEmEdicao){
			boolean openHere = abrirSessao();
			realizarFiltro();
			fecharSessao(openHere);
		}
	}
	@SuppressWarnings("unchecked")
	private void invocarExclusao(){
		int escolha = JOptionPane.showConfirmDialog(br.com.tiagods.view.MenuView.jDBody, "Você deseja excluir esse registro? "
				+ "\nTodos os historicos serão perdidos, lembre-se que essa ação não terá mais volta!","Tentativa de Exclusao", JOptionPane.YES_NO_OPTION);
		if(escolha==JOptionPane.YES_OPTION){
			boolean openHere = abrirSessao();
			boolean excluiu = dao.excluir(prospeccao,session);
			if(excluiu){
				limparFormulario(pnPesquisa);
				listarProspeccao = (List<Prospeccao>)dao.listar(Prospeccao.class, session);
				preencherTabela(listarProspeccao, tbPrincipal, txContadorRegistros);
			}
			fecharSessao(openHere);
		}
	}
	private void invocarSalvamento(){
		abrirSessao();
		PfPj pfpj = new PfPj();
		if("".equals(txCodigo.getText())){
			prospeccao = new Prospeccao();
			pfpj.setAtendente(UsuarioLogado.getInstance().getUsuario());
			pfpj.setCriadoEm(new Date());
			pfpj.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		}
		else
			pfpj = prospeccao.getPfpj();
		prospeccao.setNome(txEmpresa.getText());
		prospeccao.setResponsavel(txNomeContato.getText());
		prospeccao.setDepartamento(txDepartamento.getText());
		pfpj.setAtendente(padrao.getAtendentes(cbAtendenteCad.getSelectedItem().toString()));
		pfpj.setTelefone(txTelefone.getText());
		pfpj.setCelular(txCelular.getText());
		pfpj.setEmail(txEmail.getText());
		pfpj.setSite(txSite.getText());
		pfpj.setServico(padrao.getServicos(cbServicosCad.getSelectedItem().toString()));
		pfpj.setOrigem(padrao.getOrigens(cbOrigemCad.getSelectedItem().toString()));
		pfpj.setDetalhesOrigem(txDetalhesDaOrigem.getText());
		pfpj.setResumo(txResumoContato.getText());
		pfpj.setApresentacao(txApresentacao.getText());
		prospeccao.setEndereco(txEndereco.getText());
		prospeccao.setTipoContato(padrao.getTipoContatos(cbTipoContatoCad.getSelectedItem().toString()));
		prospeccao.setConviteParaEventos(ckConviteEventosCad.isSelected()?1:0);
		prospeccao.setMaterial(ckMaterialCad.isSelected()?1:0);
		prospeccao.setNewsletter(ckNewsletterCad.isSelected()?1:0);
		prospeccao.setPfpj(pfpj);

		DefaultTableModel model = (DefaultTableModel) tbLista.getModel();
		Set<Lista> listas = new HashSet<>();
		for(int i = 0; i<model.getRowCount(); i++){
			String valor = String.valueOf(model.getValueAt(i, 1));
			Lista l = padrao.getListas(valor);
			listas.add(l);
		}
		prospeccao.setListas(listas);
		if(dao.salvar(prospeccao, session)){
			session.beginTransaction();
			telaEmEdicao = false;
			realizarFiltro();
			preencherFormulario(prospeccao);
			salvarCancelar();
		}
		fecharSessao(true);
	}
	@SuppressWarnings("rawtypes")
	private void limparFormulario(Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JScrollPane){
				JViewport viewPort = ((JScrollPane)c).getViewport();
				limparFormulario((Container)viewPort);
			}
			else if(c instanceof JTabbedPane || c instanceof JPanel){
				limparFormulario((Container)c);
			}
			else if(c instanceof JCheckBox){
				((JCheckBox)c).setSelected(false);
			}
			else if(c instanceof JComboBox){
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
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			boolean aberto = abrirSessao();
			realizarFiltro();
			fecharSessao(aberto);
		}
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
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() instanceof JTable && tbPrincipal.getSelectedRow()>=0 &&
				tbPrincipal.getColumnCount()>1 && !telaEmEdicao){
			boolean open = abrirSessao();
			DefaultTableModel model = (DefaultTableModel) tbLista.getModel();
			while(model.getRowCount()>0){
				model.removeRow(0);
			}
			tbLista.setModel(model);
			int id = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(),0));
			this.prospeccao = (Prospeccao) dao.receberObjeto(Prospeccao.class, id, session);
			if(!pnAuxiliar.isVisible()) 
				pnAuxiliar.setVisible(true);
			limparFormulario(pnCadastro);
			preencherFormulario(this.prospeccao);
			tabbedPane.setSelectedIndex(1);
			fecharSessao(open);
			salvarCancelar();
		}
		else if(e.getComponent() instanceof JTabbedPane && telaEmEdicao && ((JTabbedPane)e.getComponent()).getSelectedIndex()==0){
			JOptionPane.showMessageDialog(MenuView.jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!",
					"Em edicao...",JOptionPane.INFORMATION_MESSAGE);
			tabbedPane.setSelectedIndex(1);
		}
		else if(e.getComponent() instanceof JRadioButton || e.getComponent() instanceof JCheckBox){
			boolean open = abrirSessao();
			realizarFiltro();
			fecharSessao(open);
		}


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
		if(this.prospeccao!=null && this.prospeccao.getId()>0)
			prospeccaoBackup=prospeccao;	
	}
	@SuppressWarnings("unchecked")
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component.getName()!=null && component instanceof JComboBox)
				padrao.preencherCombo((JComboBox<String>)component, session, null);

		}
	}
	private void preencherFormulario(Prospeccao p){
		txCodigo.setText(""+p.getId());

		txCadastradoPor.setText(p.getPfpj().getCriadoPor().getNome());
		txDataCadastro.setText(p.getPfpj().getCriadoEm()==null?"":sdf.format(p.getPfpj().getCriadoEm()));

		txEmpresa.setText(p.getNome());
		txNomeContato.setText(p.getResponsavel());
		txDepartamento.setText(p.getDepartamento());
		txEndereco.setText(p.getEndereco());
		PfPj pp = p.getPfpj();  
		cbAtendenteCad.setSelectedItem(pp.getAtendente().getNome());
		txTelefone.setText(pp.getTelefone());
		txCelular.setText(pp.getCelular());
		txEmail.setText(pp.getEmail());
		txSite.setText(pp.getSite());

		cbTipoContatoCad.setSelectedItem(p.getTipoContato()!=null?p.getTipoContato().getNome():"");
		ckConviteEventosCad.setSelected(p.getConviteParaEventos()==1?true:false);
		ckMaterialCad.setSelected(p.getMaterial()==1?true:false);
		ckNewsletterCad.setSelected(p.getNewsletter()==1?true:false);

		cbServicosCad.setSelectedItem(pp.getServico()==null?"":pp.getServico().getNome());
		cbOrigemCad.setSelectedItem(pp.getOrigem()==null?"":pp.getOrigem().getNome());
		txDetalhesDaOrigem.setText(pp.getDetalhesOrigem());

		txResumoContato.setText(pp.getResumo());
		txApresentacao.setText(pp.getApresentacao());
		Set<Lista> listas = p.getListas();
		preencherListas(listas, tbLista);
		preencherTarefas(p);		
	}
	private void preencherListas(Set<Lista> lista, JTable tabela){
		DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","NOME","DETALHES","CRIADO EM","EXCLUIR"},0){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] canEdit = new boolean[]{
					false,false,false,false,true
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}

		};
		Iterator<Lista> iterator = lista.iterator();
		while(iterator.hasNext()){
			Lista s = iterator.next();
			Object[] o = new Object[5];
			o[0] = s.getId();
			o[1] = s.getNome();
			o[2] = s.getDetalhes();
			o[3] = s.getCriadoEm()==null?"":sdf.format(s.getCriadoEm());
			o[4] = recalculate(new ImageIcon(ControllerProspeccao.class
					.getResource("/br/com/tiagods/utilitarios/button_trash.png")));
			model.addRow(o);
		}
		tabela.setRowHeight(25);
		tabela.setModel(model);
		JButton btRem  = new ButtonColumnModel(tabela,4).getButton();
		btRem.setToolTipText("Clique para remover o registro");
		btRem.addActionListener(new AcaoTabelaListaContatos());
		tabela.getColumnModel().getColumn(0).setMaxWidth(0);
		tabela.getColumnModel().getColumn(0).setMinWidth(0);
		tabela.getColumnModel().getColumn(0).setMaxWidth(0);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(40);
		tabela.setAutoCreateRowSorter(true);
	}
	private void preencherTarefas(Prospeccao p){
		if(pnAuxiliar.isVisible()){
			List<Criterion>criterios = new ArrayList<>();
			Criterion criterion = Restrictions.eq("prospeccao", p);
			criterios.add(criterion);
			new AuxiliarTabela(new Tarefa(),tbAuxiliar, new ArrayList<>(p.getPfpj().getTarefas()),
					criterios,
					Order.desc("dataEvento"),null);
		}
	}
	private void preencherTabela(List<Prospeccao> lista, JTable table, JTextField txContadorRegistros){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(lista.isEmpty()){
			new SemRegistrosJTable(table,"Relação de Negócios");
		}
		else{
			Object [] tableHeader = {"ID","NOME","RESPONSAVEL","TIPO","ORIGEM","PRODUTOS/SERVICOS","CONVITE P/EVENTOS","MATERIAL","NEWSLETTER","LISTAS",
					"CRIADO EM","ATENDENTE"};
			DefaultTableModel model = new DefaultTableModel(tableHeader,0){

				private static final long serialVersionUID = -8716692364710569296L;
				boolean[] canEdit = new boolean[]{
						false,false,false,false,false,false,false,false,false,false,false,false
				};
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit [columnIndex];
				}
			};
			for(int i=0;i<lista.size();i++){
				Prospeccao n = lista.get(i);
				Object[] linha = new Object[12];
				linha[0] = ""+n.getId();
				linha[1] = n.getNome();
				linha[2] = n.getResponsavel();
				linha[3] = n.getTipoContato().getNome();
				linha[4] = n.getPfpj().getOrigem()==null?"":n.getPfpj().getOrigem().getNome();
				linha[5] = n.getPfpj().getServico()==null?"":n.getPfpj().getServico().getNome();
				linha[6] = n.getConviteParaEventos()==1?"Sim":"Não";
				linha[7] = n.getMaterial()==1?"Sim":"Não";
				linha[8] = n.getNewsletter()==1?"Sim":"Não";
				linha[9] = n.getListas().size();
				try{
					Date criadoEm = n.getPfpj().getCriadoEm();
					linha[10] = sdf.format(criadoEm);
				}catch (NumberFormatException e) {
					linha[10] = "";
				}
				linha[11] = n.getPfpj().getAtendente()==null?"":n.getPfpj().getAtendente().getNome();
				model.addRow(linha);
			}
			table.setRowHeight(30);
			table.setModel(model);
			table.setAutoCreateRowSorter(true);
			table.setSelectionBackground(Color.ORANGE);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getColumnModel().getColumn(0).setMaxWidth(40);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);;
		}
		txContadorRegistros.setText("Total: "+lista.size()+" registro(s)");
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try{
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if(data02.compareTo(data01)>=0){
				boolean open  = abrirSessao();
				realizarFiltro();
				fecharSessao(open);
			}
		}catch (NullPointerException e) {
		}
	}
	private Criterion realizarBusca(){
		Criterion c = null;
		if(!"".equals(txBuscar.getText().trim())){
			if("Código".equals(cbBuscarPor.getSelectedItem().toString())){
				try{
					int num = Integer.parseInt(txBuscar.getText());
					c = Restrictions.eq("id",num);
				}catch(NumberFormatException e){
					c = null;
				} 
			}
			else if("Nome".equals(cbBuscarPor.getSelectedItem().toString())) {
				c = Restrictions.ilike("nome", txBuscar.getText().trim()+"%");
			}
			else {
				c = Restrictions.ilike("responsavel", txBuscar.getText().trim()+"%");
			}
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	private void realizarFiltro() {
		if(!telaEmEdicao){
			List<Criterion> criterios = new ArrayList<>();
			Order order = receberOrdenacao();
			criterios.addAll(receberFiltroSuperior());
			Criterion c = realizarBusca();
			if(c!=null) 
				criterios.add(c);
			if(!"Lista".equals(cbLista.getSelectedItem())){
				Conjunction e = Restrictions.conjunction();
				e.add(Restrictions.in("l.nome", cbLista.getSelectedItem().toString()));
				listarProspeccao = dao.items(Prospeccao.class, session, criterios, new String[]{"listas","l"}, e, order);
			}else
				listarProspeccao = dao.items(Prospeccao.class, session, criterios, order);
			preencherTabela(listarProspeccao, tbPrincipal, txContadorRegistros);
		}
		else
			JOptionPane.showMessageDialog(MenuView.jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!","Em edicao...",JOptionPane.INFORMATION_MESSAGE);
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
			Criterion c = Restrictions.eq("pfpj.atendente", padrao.getAtendentes((String)cbAtendente.getSelectedItem()));
			criterios.add(c);
		}
		if(!"TipoContato".equals(cbTipoContatoPesquisa.getSelectedItem())){
			Criterion c = Restrictions.eq("tipoContato", padrao.getAtendentes((String)cbTipoContatoPesquisa.getSelectedItem()));
			criterios.add(c);
		}
		if(ckConviteEventosPesquisa.isSelected())
			criterios.add(Restrictions.eq("conviteParaEventos", 1));
		if(ckMaterialPesquisa.isSelected())
			criterios.add(Restrictions.eq("material", 1));
		if(ckNewsletterPesquisa.isSelected())
			criterios.add(Restrictions.eq("newsletter", 1));
		try{
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if(data02.compareTo(data01)>=0){
				criterios.add(Restrictions.between("pfpj.criadoEm", data01, data02));
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
			campo = "pfpj.criadoEm";
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
	public void removerDaLista(int row){
		DefaultTableModel model = (DefaultTableModel) tbLista.getModel();
		model.removeRow(row);
		tbLista.setModel(model);
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
		btServicosAdd.setIcon(iconNovo);
		btListaAdd.setIcon(iconNovo);

		ImageIcon iconEdit = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_edit.png"));
		btEditar.setIcon(recalculate(iconEdit));
		ImageIcon iconSave = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_save.png"));
		btSalvar.setIcon(recalculate(iconSave));
		btAdicionarALista.setIcon(iconSave);

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
		ImageIcon iconExp = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_export.png"));
		btnExpMailmktLocaweb.setIcon(recalculate(iconExp));
		btnExportar.setIcon(iconExp);
		ImageIcon iconLote = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_cascata.png"));
		btnLote.setIcon(recalculate(iconLote));
	}
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
		return icon;
	}
	public ImageIcon recalculate(ImageIcon icon, int valor) throws NullPointerException{
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()-valor, icon.getIconHeight()-valor, 100));
		return icon;
	}
	private boolean validarQuantidadeRegistros(JTable table){
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		return model.getColumnCount()>1;
	}
}
