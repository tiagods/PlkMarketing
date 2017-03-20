/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.MenuView.jDBody;
import static br.com.tiagods.view.NegociosView.*;

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
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

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
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.view.EmpresasView;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.NegocioPerdaDialog;
import br.com.tiagods.view.PessoasView;
import br.com.tiagods.view.SelecaoDialog;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.interfaces.ButtonColumnModel;
import br.com.tiagods.view.interfaces.DefaultEnumModel.Modelos;
import br.com.tiagods.view.interfaces.ExcelGenerico;
import br.com.tiagods.view.interfaces.LabelTable;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;
import jxl.write.WriteException;
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
	NegocioPerdaDialog dialogPerda;

	String site;

	GenericDao dao = new GenericDao();

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
		//criterion.add(Restrictions.eq("atendente", UsuarioLogado.getInstance().getUsuario()));  //departamento não se acostumou com a nova regra
		
		listarNegocios = dao.items(Negocio.class, session, criterion, Order.desc("id"));
		preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
		if(this.negocio==null && !listarNegocios.isEmpty()){
			this.negocio=listarNegocios.get(0);
			preencherFormulario(this.negocio);
		}
		else if(this.negocio!=null)
			preencherFormulario(this.negocio);
		tbPrincipal.addMouseListener(this);
		session.close();
		setarIcones();
		definirAcoes();
//		desbloquerFormulario(false, pnCadastro);
//		desbloquerFormulario(false, pnAndamento);
		salvarCancelar();

		LoadingView loading = LoadingView.getInstance();
		loading.fechar();
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
				dialogPerda = new NegocioPerdaDialog(MenuView.getInstance(),true,negocio);
				dialogPerda.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogPerda.setVisible(true);
				dialogPerda.addWindowListener(new FechandoDialogPerda());
			}
		}

	}
	public class FechandoDialogPerda implements WindowListener{

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			Negocio negocioPerda = dialogPerda.getNegocio();
			negocio.setMotivoPerda(negocioPerda.getMotivoPerda()!=null?negocioPerda.getMotivoPerda():"");
			negocio.setDataPerda(negocioPerda.getDataPerda());
			negocio.setDetalhesPerda(negocioPerda.getDetalhesPerda()!=null?negocioPerda.getDetalhesPerda():"");
			
		}
		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}
		
	}

	public void preencherFormulario(Negocio n){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		enviarEtapa(n.getEtapa());
		cbStatusCad.setSelectedItem(n.getStatus().getNome());
		cbObject.setSelectedItem(n.getClasse());

		txCodObjeto.setOpaque(true);
		txCodObjeto.setForeground(Color.BLUE);
		txNomeObjeto.setOpaque(true);
		txNomeObjeto.setForeground(Color.BLUE);
		if(n.getClasse().equals(Empresa.class.getSimpleName())){
			txCodObjeto.setText(""+n.getEmpresa().getId());
			txNomeObjeto.setText(n.getEmpresa().getNome());
			txEmail.setText(n.getEmpresa().getPessoaJuridica().getEmail());
			site = n.getEmpresa().getPessoaJuridica().getSite();
			txFone.setText(n.getEmpresa().getPessoaJuridica().getTelefone());
			txCelular.setText(n.getEmpresa().getPessoaJuridica().getCelular());
		}
		else if(n.getClasse().equals(Pessoa.class.getSimpleName())){
			txCodObjeto.setText(""+n.getPessoa().getId());
			txNomeObjeto.setText(n.getPessoa().getNome());
			txEmail.setText(n.getPessoa().getPessoaFisica().getEmail());
			site = n.getPessoa().getPessoaFisica().getSite();
			txFone.setText(n.getPessoa().getPessoaFisica().getTelefone());
			txCelular.setText(n.getPessoa().getPessoaFisica().getCelular());
		}
		txCodigo.setText(""+n.getId());
		txNome.setText(n.getNome());
		txDataCadastro.setText(n.getCriadoEm()!=null?sdf.format(n.getCriadoEm()):"");
		txCadastradoPor.setText(n.getCriadoPor()!=null?n.getCriadoPor().getNome():"");
		cbAtendenteCad.setSelectedItem(n.getAtendente()==null?"":n.getAtendente().getNome());
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
		if(pnAuxiliar.isVisible()){
			List<Criterion>criterios = new ArrayList<>();
			Criterion criterion = Restrictions.eq("negocio", n);
			criterios.add(criterion);
			new AuxiliarTabela(new Tarefa(),tbAuxiliar, new ArrayList<>(n.getTarefas()),
					criterios,
					Order.desc("dataEvento"));
		}
	}
	@SuppressWarnings("unchecked")
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component instanceof JComboBox)
				padrao.preencherCombo((JComboBox<String>)component, session, null);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
			DefaultTableModel serv = (DefaultTableModel)tbServicosContratados.getModel();
			while(serv.getRowCount()>0)
				serv.removeRow(0);
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
				preencherFormulario(negocioBackup);
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
		default:
			break;
		}
	}
	private void adicionarServico(){
		DefaultTableModel model = (DefaultTableModel) tbServicosContratados.getModel();
		Object[] o = new Object[4];
		
		o[0]=txIdServicoContratado.getText();
		ServicoAgregado sa = padrao.getServicosAgregados((String) cbServicosAgregados.getSelectedItem());
		o[1]=sa.getNome();
	
		String servicoValue = txValorServico.getText().trim().replace(".", "").replace(",", ".");
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		o[2]=nf.format(Double.parseDouble(servicoValue));
		o[3]="";
		if(!"".equals(o[0])){
			int linha=0;
			while(linha<model.getRowCount()){
				Object valor = model.getValueAt(linha, 0);
				if(o[0].equals(valor.toString())){
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
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void exportarExcel(){
		int opcao = JOptionPane.showConfirmDialog(MenuView.jDBody, "Para exportar, realize um filtro\n"
				+"Todos os registros serão exportados para o formato excel, deseja imprimir o filtro realizado",
				"Exportar Negocios",JOptionPane.YES_NO_OPTION);
		if(opcao==JOptionPane.YES_OPTION){
			session = HibernateFactory.getSession();
			session.beginTransaction();
			realizarFiltro();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			ArrayList<ArrayList> listaImpressao = new ArrayList<>();
			
			Integer[] colunasLenght = new Integer[]{8,26,11,11,8,14,16,14,13,17,9,12,19,30,10,10,10,30,11};
			String[] cabecalho = new String[]{
					"Negocio","Nome", "E-mail","Fone","Celular",
					"Data Inicio","Data Limite","Tipo","Criador","Etapa","Status","Atendente",
					"Origem","Categoria","Nivel","Servicos/Produtos","Descricao",
					"Honorario","Servicos Contratados","Motivo da Perda","Detalhe da Perda","Data da Perda"};
			listaImpressao.add(new ArrayList<>());
			for(String c : cabecalho){
				listaImpressao.get(0).add(c);
			}
			
			for(int i = 0;i<listarNegocios.size();i++){
				listaImpressao.add(new ArrayList());
				Negocio n = listarNegocios.get(i);
				listaImpressao.get(i+1).add(n.getId());
				listaImpressao.get(i+1).add(n.getNome());
				
				PfPj pfpj = n.getPessoa()!=null?n.getPessoa().getPessoaFisica():n.getEmpresa().getPessoaJuridica();
				
				listaImpressao.get(i+1).add(pfpj.getEmail());
				listaImpressao.get(i+1).add(pfpj.getTelefone());
				listaImpressao.get(i+1).add(pfpj.getCelular());
				listaImpressao.get(i+1).add(dateFormat.format(n.getDataInicio()));
				listaImpressao.get(i+1).add(n.getDataFim()==null?"":dateFormat.format(n.getDataFim()));
				listaImpressao.get(i+1).add(n.getClasse());
				listaImpressao.get(i+1).add(n.getCriadoPor().getNome());
				listaImpressao.get(i+1).add(n.getEtapa().getNome());
				listaImpressao.get(i+1).add(n.getStatus().getNome());
				listaImpressao.get(i+1).add(n.getAtendente().getNome());
				listaImpressao.get(i+1).add(n.getPessoaFisicaOrJuridica().getOrigem()==null?"":n.getPessoaFisicaOrJuridica().getOrigem().getNome());
				listaImpressao.get(i+1).add(n.getPessoaFisicaOrJuridica().getCategoria()==null?"":n.getPessoaFisicaOrJuridica().getCategoria().getNome());
				listaImpressao.get(i+1).add(n.getPessoaFisicaOrJuridica().getNivel()==null?"":n.getPessoaFisicaOrJuridica().getNivel().getNome());
				listaImpressao.get(i+1).add(n.getPessoaFisicaOrJuridica().getServico()==null?"":n.getPessoaFisicaOrJuridica().getServico().getNome());
				
				listaImpressao.get(i+1).add(n.getDescricao());
				listaImpressao.get(i+1).add(NumberFormat.getCurrencyInstance().format(n.getHonorario()));

				double vlrServicos = 0.00;
				Iterator<ServicoContratado> iterator = n.getServicosContratados().iterator();
				while(iterator.hasNext()){
					ServicoContratado sc = iterator.next();
					vlrServicos+=sc.getValor().doubleValue();
				}
				listaImpressao.get(i+1).add(NumberFormat.getCurrencyInstance().format(vlrServicos));
				listaImpressao.get(i+1).add(n.getMotivoPerda());
				listaImpressao.get(i+1).add(n.getDetalhesPerda());
				listaImpressao.get(i+1).add(n.getDataPerda()==null?"":dateFormat.format(n.getDataPerda()));
				
			}
			String export = carregarArquivo("Salvar arquivo");
			if(!"".equals(export)){
				ExcelGenerico planilha = new ExcelGenerico(export+".xls",listaImpressao,colunasLenght);
				try {
					planilha.gerarExcel();
					JOptionPane.showMessageDialog(null, "Gerado com sucesso em : "+export+".xls");
				} catch (WriteException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao criar a planilha "+e1);
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao criar a planilha "+e1);
				}
			}
			session.close();
		}
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
	@SuppressWarnings("unchecked")
	private void realizarFiltro() {
		if(!telaEmEdicao){
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
			listarNegocios = dao.items(Negocio.class, session, criterios, order);
			preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!","Em edicao...",JOptionPane.INFORMATION_MESSAGE);
	}
	public class MudarCliente implements PropertyChangeListener{
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if(telaEmEdicao && evt.getNewValue()!=null)
				receberObjeto();
		}
	}
	public void receberObjeto(){
		if(telaEmEdicao && !"".equals(txCodObjeto.getText())){
			if("".equals(txNome.getText().trim()))
					txNome.setText(txNomeObjeto.getText());
		}

	}
	private void invocarSalvamento(){
		if("".equals(txCodigo.getText())){
			negocio.setCriadoEm(new Date());
			negocio.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
			negocio.setNome("".equals(txNome.getText().trim())?txNomeObjeto.getText():txNome.getText());
		}
		negocio.setStatus(padrao.getStatus((String)cbStatusCad.getSelectedItem()));//
		if("".equals(cbAtendenteCad.getSelectedItem()))
			negocio.setAtendente(UsuarioLogado.getInstance().getUsuario());//
		else
			negocio.setAtendente(padrao.getAtendentes((String)cbAtendenteCad.getSelectedItem()));
		session = HibernateFactory.getSession();
		session.beginTransaction();

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

		if(cbObject.getSelectedItem().equals(Modelos.Empresa.toString())){
			Empresa o = (Empresa) dao.receberObjeto(Empresa.class, Integer.parseInt(txCodObjeto.getText()), session);
			negocio.setClasse("Empresa");
			negocio.setEmpresa(o);
			negocio.setPessoa(null);
		}
		else if(cbObject.getSelectedItem().equals(Modelos.Pessoa.toString())){
			Object o = dao.receberObjeto(Pessoa.class, Integer.parseInt(txCodObjeto.getText()), session);
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

		negocio.setStatus(padrao.getStatus((String)cbStatusCad.getSelectedItem()));
		
		Set<ServicoContratado> servicosContratados = new HashSet<>();
		for(int i = 0; i< tbServicosContratados.getRowCount(); i++){
			ServicoContratado sc = new ServicoContratado();
			if(!"".equals(tbServicosContratados.getValueAt(i, 0)))
				sc.setId((int) tbServicosContratados.getValueAt(i, 0));
			sc.setServicosAgregados(padrao.getServicosAgregados((String) tbServicosContratados.getValueAt(i, 1)));
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			Number number = null;
			try {
				number = nf.parse(tbServicosContratados.getValueAt(i, 2).toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			BigDecimal bigdecimal = new BigDecimal(number.doubleValue());
			sc.setValor(bigdecimal);
			//Double valor = Double.parseDouble(tbServicosContratados.getValueAt(i, 2).toString().replace(",", "."));
			sc.setNegocios(negocio);
			servicosContratados.add(sc);
		}
		negocio.setServicosContratados(servicosContratados);
		if(dao.salvar(negocio, session)){
			session.beginTransaction();
			telaEmEdicao = false;
			realizarFiltro();
			preencherFormulario(negocio);
			salvarCancelar();
		}
		session.close();
	}
	public void enviarEtapa(Etapa e){
		switch(e.getNome()){
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
				+ "\nTodos os historicos serão perdidos, lembre-se que essa ação não terá mais volta!","Tentativa de Exclusao", JOptionPane.YES_NO_OPTION);
		if(escolha==JOptionPane.YES_OPTION){
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
			if(listarNegocios.get(i).getNome().trim().length()>texto.length() && listarNegocios.get(i).getNome().substring(0,texto.length()).equalsIgnoreCase(texto)){
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
		pnAuxiliar.setVisible(true);
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnExcluir.setEnabled(false);
		cbAtendenteCad.setSelectedItem(UsuarioLogado.getInstance().getUsuario().getNome());
		txEmail.setEditable(false);
		if(this.negocio.getId()>0)
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
				
				if(n.getStatus()!=null){
					if("Ganho".equals(n.getStatus().getNome())){
						linha[2] =recalculate(new ImageIcon(ControllerNegocios.class
								.getResource("/br/com/tiagods/utilitarios/button_winner.png")),15);
					}
					else if("Perdido".equals(n.getStatus().getNome())){
						linha[2] = recalculate(new ImageIcon(ControllerNegocios.class
								.getResource("/br/com/tiagods/utilitarios/button_sad.png")),15);
					}	
					else if("Em Andamento".equals(n.getStatus().getNome())){
						linha[2] = recalculate(new ImageIcon(ControllerNegocios.class
								.getResource("/br/com/tiagods/utilitarios/button_deadline.png")),15);
					}
					else{
						linha[2] = "";
					}
				}
				else
					linha[2] = null;
				
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
					long qtd = (diferenca / 86400000L);
					if(qtd>0)
					linha[6]=qtd+" dia(s) atrasado(s)";
				}
				try{
					Date criadoEm = n.getCriadoEm();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					linha[7] = sdf.format(criadoEm);
				}catch (NumberFormatException e) {
					linha[7] = "";
				}
				linha[8] = n.getAtendente()==null?"":n.getAtendente().getNome();
				String imageName ="";
				if("Empresa".equals(n.getClasse())){
					imageName ="button_empresas.png";
				}
				else
					imageName ="button_people.png";
				linha[9]= recalculate(new ImageIcon(ControllerTarefas.class
						.getResource("/br/com/tiagods/utilitarios/"+imageName)));
				model.addRow(linha);
			}
			table.setRowHeight(30);
			table.setModel(model);
			table.setAutoCreateRowSorter(true);
			table.setSelectionBackground(Color.ORANGE);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(2).setMaxWidth(60);
			
			TableCellRenderer tcr2 = new LabelTable();
			TableColumn column2 = tbPrincipal.getColumnModel().getColumn(2);
			column2.setCellRenderer(tcr2);
			
//			TableCellRenderer tcr2 = new Colorir();
//	        TableColumn column2 =
//	        tbPrincipal.getColumnModel().getColumn(2);
//	        column2.setCellRenderer(tcr2);

			JButton btAbrir = new ButtonColumnModel(table,9).getButton();
			btAbrir.setActionCommand("Abrir");
			btAbrir.addActionListener(new AcaoInTable());
			table.getColumnModel().getColumn(9).setPreferredWidth(40);
		}
		txContadorRegistros.setText("Total: "+lista.size()+" registro(s)");
	}
	@SuppressWarnings("serial")
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
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			o[2] = nf.format(s.getValor());
			//o[2] = s.getValor().toString().replace(".", ",");
			o[3] = recalculate(new ImageIcon(ControllerNegocios.class
					.getResource("/br/com/tiagods/utilitarios/button_trash.png")));
			model.addRow(o);
		}
		tbServicosContratados.setRowHeight(25);
		tbServicosContratados.setModel(model);
		JButton btRem  = new ButtonColumnModel(tbServicosContratados,3).getButton();
		btRem.setToolTipText("Clique para remover o registro");
		btRem.addActionListener(new AcaoInTableServicosContratados());
		
		tbServicosContratados.getColumnModel().getColumn(0).setMaxWidth(40);
		tbServicosContratados.getColumnModel().getColumn(2).setPreferredWidth(40);
		tbServicosContratados.getColumnModel().getColumn(3).setPreferredWidth(40);
		tbServicosContratados.setAutoCreateRowSorter(true);
	}
	public class AcaoInTable implements ActionListener{

		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent e) {
			session = HibernateFactory.getSession();
			session.beginTransaction();
			Icon value = ((JButton)e.getSource()).getIcon();

			Icon icoEmp = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_empresas.png"));
			Icon icoPes = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_people.png"));

			int id = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0));
			Negocio neg = (Negocio) dao.receberObjeto(Negocio.class, id, session);
			if(icoEmp.toString().equals(value.toString())){
				Empresa empresa = neg.getEmpresa();
				EmpresasView viewEmpresas = new EmpresasView(empresa);
				ControllerMenu.getInstance().abrirCorpo(viewEmpresas);
			}
			else if(icoPes.toString().equals(value.toString())){
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
			else JOptionPane.showMessageDialog(MenuView.jDBody, "Clique em editar antes de realizar qualquer alteração!");
		}
	}
	public void removerServico(int row){
		DefaultTableModel model = (DefaultTableModel) tbServicosContratados.getModel();
		model.removeRow(row);
		tbServicosContratados.setModel(model);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED && !e.getSource().equals(cbObject) ){
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
			DefaultTableModel model = (DefaultTableModel) tbServicosContratados.getModel();
			while(model.getRowCount()>0){
				model.removeRow(0);
			}
			tbServicosContratados.setModel(model);
			int id = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(),0));
			this.negocio = (Negocio) dao.receberObjeto(Negocio.class, id, session);
			if(!pnAuxiliar.isVisible()) 
				pnAuxiliar.setVisible(true);
			preencherFormulario(this.negocio);
			fechaSessao(open);
			salvarCancelar();
		}
//		else
//			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!",
//					"Em edicao...",JOptionPane.INFORMATION_MESSAGE);
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
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			Number number;
			try {
				number = nf.parse((String)tbServicosContratados.getValueAt(row, 2));
				txValorServico.setText(number.toString().replace(".", ","));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
	public void setarIcones() throws NullPointerException{
    	ImageIcon iconNovo = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
    	btnNovo.setIcon(recalculate(iconNovo));
    	btnCategoriaAdd.setIcon(iconNovo);
    	btnNivelAdd.setIcon(iconNovo);
    	btnOrigemAdd.setIcon(iconNovo);
    	btnServicoAdd.setIcon(iconNovo);

    	ImageIcon iconEdit = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_edit.png"));
    	btnEditar.setIcon(recalculate(iconEdit));
    	ImageIcon iconSave = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_save.png"));
    	btnSalvar.setIcon(recalculate(iconSave));
    	ImageIcon iconCancel = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_cancel.png"));
    	btnCancelar.setIcon(recalculate(iconCancel));
    	ImageIcon iconTrash = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_trash.png"));
    	btnExcluir.setIcon(recalculate(iconTrash));

    	ImageIcon iconNewTask = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_addtask.png"));
    	btnNovaTarefa.setIcon(recalculate(iconNewTask));
    	ImageIcon iconTask = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_task.png"));
    	btnHistorico.setIcon(recalculate(iconTask));

    	btAddServicoAgregado.setIcon(iconSave);
    	btnNovoServicoAgregado.setIcon(iconNovo);

    	ImageIcon iconEsconder = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_nofixar.png"));
    	btEsconder.setIcon(recalculate(iconEsconder));

    	ImageIcon iconPhone = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/negocio_fone.png"));
        rbContato.setIcon(recalculate(iconPhone));
        rbContato.setBorderPainted(true);
        ImageIcon iconProposta = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/negocio_proposta.png"));
        rbEnvioProposta.setIcon(recalculate(iconProposta));
        rbEnvioProposta.setBorderPainted(true);
        ImageIcon iconFollowup = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/negocio_followup.png"));
        rbFollowup.setIcon(recalculate(iconFollowup));
        rbFollowup.setBorderPainted(true);

        ImageIcon iconClose = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/negocio_fechamento.png"));
        rbFechamento.setIcon(recalculate(iconClose));
        rbFechamento.setBorderPainted(true);

        ImageIcon iconIndefinida = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/negocio_question.png"));
        rbIndefinida.setIcon(recalculate(iconIndefinida));
        rbIndefinida.setBorderPainted(true);

        ImageIcon iconImportant = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/exclamation.png"));
        btAddEmpresaPessoa.setIcon(recalculate(iconImportant));
        btAddEmpresaPessoa.setBorderPainted(true);

    	ImageIcon iconMail = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_mail.png"));
    	btnEmail.setIcon(recalculate(iconMail));
    	ImageIcon iconURL = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_chrome.png"));
    	btnLink.setIcon(recalculate(iconURL));

    	ImageIcon iconImp = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_import.png"));
    	btnImportar.setIcon(recalculate(iconImp));

    	ImageIcon iconExp = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_export.png"));
    	btnExportar.setIcon(recalculate(iconExp));

    	ImageIcon iconContact = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_phone.png"));
    	txFone.setIcon(recalculate(iconContact));
    	txFone.setForeground(Color.blue);

    	ImageIcon iconCellPhone = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_cellphone.png"));
    	txCelular.setIcon(recalculate(iconCellPhone));
    	txCelular.setForeground(Color.BLUE);
    	
    	ImageIcon iconPerda = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/menu_about.png"));
    	btnVerPerda.setIcon(recalculate(iconPerda));

    }
    public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
    public ImageIcon recalculate(ImageIcon icon, int valor) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()-valor, icon.getIconHeight()-valor, 100));
    	return icon;
    }
    class Colorir extends JLabel implements TableCellRenderer{
        /**
		 *
		 */
		private static final long serialVersionUID = 3906288238715470468L;
		public Colorir(){
            this.setOpaque(true);
        }

        public Component getTableCellRendererComponent(
            JTable table,
            Object value, boolean isSelected, boolean hasFocus,
               int row, int column){

            if(value.toString().equals("Perdido")){
                setBackground(Color.RED);
                setForeground(Color.WHITE);
            }
            else if(value.toString().equals("Ganho")){
                setBackground(Color.GREEN);
                setForeground(Color.black);
            }
            else{
            	setBackground(Color.YELLOW);
                setForeground(tbPrincipal.getForeground());
            }
            setFont(tbPrincipal.getFont());
            setText(value.toString());
            return this;
        }

      public void validate() {}
      public void revalidate() {}
      protected void firePropertyChange(String propertyName,
         Object oldValue, Object newValue) {}
      public void firePropertyChange(String propertyName,
         boolean oldValue, boolean newValue) {}
    }
}
