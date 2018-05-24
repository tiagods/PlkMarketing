/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.MenuView.jDBody;
import static br.com.tiagods.view.NegociosView.btAddEmpresaPessoa;
import static br.com.tiagods.view.NegociosView.btAddServicoAgregado;
import static br.com.tiagods.view.NegociosView.btEsconder;
import static br.com.tiagods.view.NegociosView.btnAnexarDocumento;
import static br.com.tiagods.view.NegociosView.btnCancelar;
import static br.com.tiagods.view.NegociosView.btnCategoriaAdd;
import static br.com.tiagods.view.NegociosView.btnEditar;
import static br.com.tiagods.view.NegociosView.btnEmail;
import static br.com.tiagods.view.NegociosView.btnEnviarArquivo;
import static br.com.tiagods.view.NegociosView.btnExcluir;
import static br.com.tiagods.view.NegociosView.btnExportar;
import static br.com.tiagods.view.NegociosView.btnHistorico;
import static br.com.tiagods.view.NegociosView.btnImportar;
import static br.com.tiagods.view.NegociosView.btnLink;
import static br.com.tiagods.view.NegociosView.btnNivelAdd;
import static br.com.tiagods.view.NegociosView.btnNovaTarefa;
import static br.com.tiagods.view.NegociosView.btnNovo;
import static br.com.tiagods.view.NegociosView.btnNovoServicoAgregado;
import static br.com.tiagods.view.NegociosView.btnOrigemAdd;
import static br.com.tiagods.view.NegociosView.btnSalvar;
import static br.com.tiagods.view.NegociosView.btnServicoAdd;
import static br.com.tiagods.view.NegociosView.btnVerPerda;
import static br.com.tiagods.view.NegociosView.cbAtendente;
import static br.com.tiagods.view.NegociosView.cbAtendenteCad;
import static br.com.tiagods.view.NegociosView.cbBuscarPor;
import static br.com.tiagods.view.NegociosView.cbCategoria;
import static br.com.tiagods.view.NegociosView.cbCategoriaCad;
import static br.com.tiagods.view.NegociosView.cbEmpresa;
import static br.com.tiagods.view.NegociosView.cbEtapa;
import static br.com.tiagods.view.NegociosView.cbNivel;
import static br.com.tiagods.view.NegociosView.cbNivelCad;
import static br.com.tiagods.view.NegociosView.cbObject;
import static br.com.tiagods.view.NegociosView.cbOrdenacao;
import static br.com.tiagods.view.NegociosView.cbOrigem;
import static br.com.tiagods.view.NegociosView.cbOrigemCad;
import static br.com.tiagods.view.NegociosView.cbPessoa;
import static br.com.tiagods.view.NegociosView.cbProspeccao;
import static br.com.tiagods.view.NegociosView.cbServicos;
import static br.com.tiagods.view.NegociosView.cbServicosAgregados;
import static br.com.tiagods.view.NegociosView.cbServicosCad;
import static br.com.tiagods.view.NegociosView.cbStatus;
import static br.com.tiagods.view.NegociosView.cbStatusCad;
import static br.com.tiagods.view.NegociosView.data1;
import static br.com.tiagods.view.NegociosView.data2;
import static br.com.tiagods.view.NegociosView.dataFim;
import static br.com.tiagods.view.NegociosView.dataInicio;
import static br.com.tiagods.view.NegociosView.pnAndamento;
import static br.com.tiagods.view.NegociosView.pnAuxiliar;
import static br.com.tiagods.view.NegociosView.pnCadastro;
import static br.com.tiagods.view.NegociosView.pnFiltros;
import static br.com.tiagods.view.NegociosView.pnPrincipal;
import static br.com.tiagods.view.NegociosView.pnServicosContratados;
import static br.com.tiagods.view.NegociosView.rbContato;
import static br.com.tiagods.view.NegociosView.rbCrescente;
import static br.com.tiagods.view.NegociosView.rbDecrescente;
import static br.com.tiagods.view.NegociosView.rbEnvioProposta;
import static br.com.tiagods.view.NegociosView.rbFechamento;
import static br.com.tiagods.view.NegociosView.rbFollowup;
import static br.com.tiagods.view.NegociosView.rbIndefinida;
import static br.com.tiagods.view.NegociosView.tabbedPane;
import static br.com.tiagods.view.NegociosView.tbAuxiliar;
import static br.com.tiagods.view.NegociosView.tbDocumentos;
import static br.com.tiagods.view.NegociosView.tbPrincipal;
import static br.com.tiagods.view.NegociosView.tbServicosContratados;
import static br.com.tiagods.view.NegociosView.txBuscar;
import static br.com.tiagods.view.NegociosView.txCadastradoPor;
import static br.com.tiagods.view.NegociosView.txCelular;
import static br.com.tiagods.view.NegociosView.txCodObjeto;
import static br.com.tiagods.view.NegociosView.txCodigo;
import static br.com.tiagods.view.NegociosView.txContadorRegistros;
import static br.com.tiagods.view.NegociosView.txDataCadastro;
import static br.com.tiagods.view.NegociosView.txDescricao;
import static br.com.tiagods.view.NegociosView.txDocumentoDescricao;
import static br.com.tiagods.view.NegociosView.txDocumentoNome;
import static br.com.tiagods.view.NegociosView.txDocumentoPath;
import static br.com.tiagods.view.NegociosView.txEmail;
import static br.com.tiagods.view.NegociosView.txFone;
import static br.com.tiagods.view.NegociosView.txHonorario;
import static br.com.tiagods.view.NegociosView.txIconCelular;
import static br.com.tiagods.view.NegociosView.txIconFone;
import static br.com.tiagods.view.NegociosView.txIdServicoContratado;
import static br.com.tiagods.view.NegociosView.txNome;
import static br.com.tiagods.view.NegociosView.txNomeObjeto;
import static br.com.tiagods.view.NegociosView.txValorNegocios;
import static br.com.tiagods.view.NegociosView.txValorServico;

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
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioDocumento;
import br.com.tiagods.model.NegocioEtapa;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.NegocioStatus;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.modelcollections.NegocioEmpresa;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.modelcollections.NegocioPessoa;
import br.com.tiagods.modelcollections.NegocioPadrao;
import br.com.tiagods.modelcollections.NegocioProspeccao;
import br.com.tiagods.modelcollections.ServicoAgregado;
import br.com.tiagods.modelcollections.ServicoContratado;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.util.FTPDownload;
import br.com.tiagods.util.FTPUpload;
import br.com.tiagods.util.Randomico;
import br.com.tiagods.view.EmpresasView;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.PessoasView;
import br.com.tiagods.view.ProspeccaoView;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.dialog.NegocioPerdaDialog;
import br.com.tiagods.view.dialog.SelecaoDialog;
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
	NegocioProposta negocio = null;
	NegocioProposta negocioBackup = null;
	Session session = null;
	boolean telaEmEdicao = false;
	List<NegocioProposta> listarNegocios;
	Map<String,JRadioButton> radiosAndamento = new HashMap<>();
		
	NegocioPerdaDialog dialogPerda;
	String site;
	GenericDao dao = new GenericDao();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	SimpleDateFormat sdh = new SimpleDateFormat("HH:mm");
	
	@SuppressWarnings("unchecked")
	public void iniciar(NegocioProposta negocio, Object objeto){
		this.negocio=negocio;
		session = HibernateFactory.getSession();
		session.beginTransaction();
		
		JPanel[] panels = {pnPrincipal,pnAndamento,pnCadastro,pnServicosContratados,pnFiltros};
		for (JPanel panel : panels) {
			preencherComboBox(panel);
		}
		radiosAndamento.put("Contato", rbContato);
		radiosAndamento.put("Envio de Proposta", rbEnvioProposta);
		radiosAndamento.put("Follow-up", rbFollowup);
		
		salvarCancelar();
		
		List<Criterion> criterion = new ArrayList<>();
		//criterion.add(Restrictions.eq("atendente", UsuarioLogado.getInstance().getUsuario()));  //departamento não se acostumou com a nova regra
		
		listarNegocios = dao.items(NegocioProposta.class, session, criterion, Order.desc("id"));
		preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
		if(this.negocio==null && !listarNegocios.isEmpty()){
			if(!listarNegocios.isEmpty() && objeto==null) {
				this.negocio=listarNegocios.get(0);
				preencherFormulario(this.negocio);
				tabbedPane.setSelectedIndex(0);
			}
			else if(objeto!=null) {
				novo();
				cbStatusCad.setSelectedIndex(0);
				if(objeto instanceof NegocioEmpresa) {
					cbObject.setSelectedItem("Empresa");
					txCodObjeto.setText(((NegocioEmpresa)objeto).getId()+"");
					txNomeObjeto.setText(((NegocioEmpresa)objeto).getNome());
					txEmail.setText(((NegocioEmpresa)objeto).getPessoaJuridica().getEmail());
					site = ((NegocioEmpresa)objeto).getPessoaJuridica().getSite();
					txFone.setText(((NegocioEmpresa)objeto).getPessoaJuridica().getTelefone());
					txCelular.setText(((NegocioEmpresa)objeto).getPessoaJuridica().getCelular());
				}
				else if(objeto instanceof NegocioPessoa) {
					cbObject.setSelectedItem("Pessoa");
					txCodObjeto.setText(((NegocioPessoa)objeto).getId()+"");
					txNomeObjeto.setText(((NegocioPessoa)objeto).getNome());
					txEmail.setText(((NegocioPessoa)objeto).getPessoaFisica().getEmail());
					site = ((NegocioPessoa)objeto).getPessoaFisica().getSite();
					txFone.setText(((NegocioPessoa)objeto).getPessoaFisica().getTelefone());
					txCelular.setText(((NegocioPessoa)objeto).getPessoaFisica().getCelular());
				}
				else if(objeto instanceof NegocioProspeccao) {
					cbObject.setSelectedItem("Prospeccao");
					txCodObjeto.setText(((NegocioProspeccao)objeto).getId()+"");
					txNomeObjeto.setText(((NegocioProspeccao)objeto).getNome());
					txEmail.setText(((NegocioProspeccao)objeto).getPfpj().getEmail());
					site = ((NegocioProspeccao)objeto).getPfpj().getSite();
					txFone.setText(((NegocioProspeccao)objeto).getPfpj().getTelefone());
					txCelular.setText(((NegocioProspeccao)objeto).getPfpj().getCelular());
				}
				txCodObjeto.setOpaque(true);
				txCodObjeto.setForeground(Color.BLUE);
				txNomeObjeto.setOpaque(true);
				txNomeObjeto.setForeground(Color.BLUE);
				tabbedPane.setSelectedIndex(1);
			}
		}
		else if(this.negocio!=null){
			preencherFormulario(this.negocio);
			tabbedPane.setSelectedIndex(1);
		}
		tbPrincipal.addMouseListener(this);
		calcularNegociosDoUsuario(session);
		session.close();
		setarIcones();
		definirAcoes();
//		desbloquerFormulario(false, pnCadastro);
//		desbloquerFormulario(false, pnAndamento);
		LoadingView loading = LoadingView.getInstance();
		loading.fechar();
    }
	@SuppressWarnings("unchecked")
	private void calcularNegociosDoUsuario(Session session) {
		List<Criterion> criterios = new ArrayList<>();
		Calendar inicio = Calendar.getInstance();
		inicio.set(Calendar.DAY_OF_MONTH, 1);
		Calendar fim = Calendar.getInstance();
		fim.set(Calendar.DAY_OF_MONTH, fim.getMaximum(Calendar.DAY_OF_MONTH));
		NegocioStatus status = session.get(NegocioStatus.class, 2);
		criterios.add(Restrictions.or(Restrictions.between("fechamento", inicio.getTime(), fim.getTime()),
				Restrictions.between("followUp", inicio.getTime(), fim.getTime()),
				Restrictions.between("envioProposta", inicio.getTime(), fim.getTime()),
				Restrictions.between("contato", inicio.getTime(), fim.getTime())));
		criterios.add(Restrictions.eq("atendente.id", 2));
		criterios.add(Restrictions.eq("status", status));
		List<NegocioProposta> calculos = dao.items(NegocioProposta.class, session, criterios, Order.asc("id"));
		double valor = 0.00;
		for(NegocioProposta neg : calculos){
			valor+= neg.getHonorario().doubleValue();
			Set<ServicoContratado> servicos = neg.getServicosContratados();
			for(ServicoContratado s : servicos){
				valor+=s.getValor().doubleValue();
			}
		}
		txValorNegocios.setText(NumberFormat.getCurrencyInstance().format(valor));		
		
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
		cbOrdenacao.addItemListener(this);
		cbBuscarPor.addItemListener(this);
		rbCrescente.addItemListener(this);
		rbDecrescente.addItemListener(this);
		cbAtendente.addItemListener(this);
		cbObject.addItemListener(this);
		cbStatusCad.addItemListener(new InvocarDialogPerda());
		txBuscar.addKeyListener(this);
		rbCrescente.addItemListener(this);
		tbServicosContratados.addMouseListener(new AcaoTabelaServicosContratados());
		tabbedPane.addMouseListener(this);
		
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
			NegocioProposta negocioPerda = dialogPerda.getNegocio();
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

	public void preencherFormulario(NegocioProposta n){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		enviarEtapa(n.getEtapa());
		cbStatusCad.setSelectedItem(n.getStatus().getNome());
		cbObject.setSelectedItem(n.getClasse());

		txCodObjeto.setOpaque(true);
		txCodObjeto.setForeground(Color.BLUE);
		txNomeObjeto.setOpaque(true);
		txNomeObjeto.setForeground(Color.BLUE);
		
		if(n.getClasse().equals(NegocioEmpresa.class.getSimpleName())){
			txCodObjeto.setText(""+n.getEmpresa().getId());
			txNomeObjeto.setText(n.getEmpresa().getNome());
			txEmail.setText(n.getEmpresa().getPessoaJuridica().getEmail());
			site = n.getEmpresa().getPessoaJuridica().getSite();
			txFone.setText(n.getEmpresa().getPessoaJuridica().getTelefone());
			txCelular.setText(n.getEmpresa().getPessoaJuridica().getCelular());
		}
		else if(n.getClasse().equals(NegocioPessoa.class.getSimpleName())){
			txCodObjeto.setText(""+n.getPessoa().getId());
			txNomeObjeto.setText(n.getPessoa().getNome());
			txEmail.setText(n.getPessoa().getPessoaFisica().getEmail());
			site = n.getPessoa().getPessoaFisica().getSite();
			txFone.setText(n.getPessoa().getPessoaFisica().getTelefone());
			txCelular.setText(n.getPessoa().getPessoaFisica().getCelular());
		}
		else if(n.getClasse().equals(NegocioProspeccao.class.getSimpleName())){
			txCodObjeto.setText(""+n.getProspeccao().getId());
			txNomeObjeto.setText(n.getProspeccao().getNome());
			txEmail.setText(n.getProspeccao().getPfpj().getEmail());
			site = n.getProspeccao().getPfpj().getSite();
			txFone.setText(n.getProspeccao().getPfpj().getTelefone());
			txCelular.setText(n.getProspeccao().getPfpj().getCelular());
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
		Set<NegocioDocumento> documentos = n.getDocumentos();
		preencherServicos(servicos);
		preencherDocumentos(documentos);
		preencherTarefas(n);
	}
	private void preencherTarefas(NegocioProposta n){
		if(pnAuxiliar.isVisible()){
			List<Criterion>criterios = new ArrayList<>();
			Criterion criterion = Restrictions.eq("negocio", n);
			criterios.add(criterion);
			new AuxiliarTabela(new NegocioTarefa(),tbAuxiliar, new ArrayList<>(n.getTarefas()),
					criterios,
					Order.desc("dataEvento"),radiosAndamento);
		}
	}
	private void preencherDocumentos(Set<NegocioDocumento> documentos) {
		DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","DATA","NOME","DESCRICAO","ATENDENTE","VISUALIZAR","EXCLUIR"},0){
			/**
			 * 
			 */
			private static final long serialVersionUID = -8716692364710569296L;
			boolean[] canEdit = new boolean[]{
					false,false,false,false,false,true,true
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
		Iterator<NegocioDocumento> iterator = documentos.iterator();
		while(iterator.hasNext()){
			NegocioDocumento d = iterator.next();
			Object[] o = new Object[7];
			o[0] = d.getId();
			o[1] = sdf.format(d.getData())+" às "+sdh.format(d.getData());
			o[2] = d.getNome();
			o[3] = d.getDescricao();
			o[4] = d.getUsuario().getNome();
			o[5] = recalculate(new ImageIcon(ControllerNegocios.class
					.getResource("/br/com/tiagods/utilitarios/button_viewfile.png")));
			o[6] = recalculate(new ImageIcon(ControllerNegocios.class
					.getResource("/br/com/tiagods/utilitarios/button_trash.png")));
			model.addRow(o);
		}
		tbDocumentos.setRowHeight(25);
		tbDocumentos.setModel(model);
		JButton btView  = new ButtonColumnModel(tbDocumentos,5).getButton();
		btView.setToolTipText("Clique abrir o arquivo");
		btView.addActionListener( new AbrirDocumentos());
		JButton btRem  = new ButtonColumnModel(tbDocumentos,6).getButton();
		btRem.setToolTipText("Clique para remover o registro");
		btRem.addActionListener(new AcaoInTableDocumentos());
		
		tbDocumentos.getColumnModel().getColumn(0).setMaxWidth(30);
		tbDocumentos.getColumnModel().getColumn(4).setPreferredWidth(40);
		tbDocumentos.getColumnModel().getColumn(5).setPreferredWidth(40);
		tbDocumentos.getColumnModel().getColumn(6).setPreferredWidth(40);
		tbDocumentos.setAutoCreateRowSorter(true);
	}
	
	public class AcaoInTableDocumentos implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(telaEmEdicao){
				int row = tbDocumentos.getSelectedRow();
				Object value = tbDocumentos.getValueAt(row, 0);
				int i = JOptionPane.showConfirmDialog(br.com.tiagods.view.MenuView.jDBody,
						"Deseja excluir o seguinte serviço: \n"+tbDocumentos.getValueAt(row, 0)+" \nNome: "+tbDocumentos.getValueAt(row, 2)+
						"?","Pedido de remoção!",JOptionPane.YES_NO_OPTION);
				if(i==JOptionPane.OK_OPTION){
					if(!"".equals(value.toString())){
						session = HibernateFactory.getSession();
						session.beginTransaction();
						NegocioDocumento doc = (NegocioDocumento)dao.receberObjeto(NegocioDocumento.class, Integer.parseInt(value.toString()), session);
						if(dao.excluir(doc, session)){
							removerDocumento(row);
						}
						session.close();
					}
					else{
						removerDocumento(row);
					}
				}
			}
			else JOptionPane.showMessageDialog(MenuView.jDBody, "Clique em editar antes de realizar qualquer alteração!");
		}
	}
	public class AbrirDocumentos implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel model =  (DefaultTableModel)tbDocumentos.getModel();
			int valor = Integer.parseInt(String.valueOf(model.getValueAt(tbDocumentos.getSelectedRow(), 0)));
			FTPDownload download = new FTPDownload();
            session = HibernateFactory.getSession();
			session.beginTransaction();
			NegocioDocumento doc = session.get(NegocioDocumento.class, valor);
			if(download.downloadFile(doc.getUrl())){
                try {
                    Desktop.getDesktop().open(download.returnFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
			session.close();
		}

	}
	public void removerDocumento(int row){
		DefaultTableModel model = (DefaultTableModel) tbDocumentos.getModel();
		model.removeRow(row);
		tbDocumentos.setModel(model);
	}
	@SuppressWarnings("unchecked")
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component.getName()!=null && component instanceof JComboBox)
				padrao.preencherCombo((JComboBox<String>)component, session, null);
		}
	}

	@SuppressWarnings({ "rawtypes" })
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
			novo();
			break;
		case "Editar":
			telaEmEdicao = true;
			if(!"".equals(txCodigo.getText())) {
				recebeSessao();
				novoEditar();
				fechaSessao(true);
			}
			break;
		case "Cancelar":
			telaEmEdicao = false;
			if(negocioBackup!=null){
				negocio = negocioBackup;
				session = HibernateFactory.getSession();
				session.beginTransaction();
				negocio = (NegocioProposta)dao.receberObjeto(NegocioProposta.class, negocio.getId(), session);
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
				if(UsuarioLogado.getInstance().getUsuario().getId()==negocio.getCriadoPor().getId()){
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
				preencherTarefas(negocio);
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
				if(cbObject.getSelectedItem().equals(Modelos.NegocioEmpresa.toString())){
					combos = new JComboBox[]{cbEmpresa};
					dialog =new SelecaoDialog(new NegocioEmpresa(),txCodObjeto,txNomeObjeto,combos,comboNegocios,MenuView.getInstance(),true);
				}
				else if(cbObject.getSelectedItem().equals(Modelos.NegocioPessoa.toString())){
					combos = new JComboBox[]{cbPessoa};
					dialog = new SelecaoDialog(new NegocioPessoa(),txCodObjeto,txNomeObjeto,combos,comboNegocios,MenuView.getInstance(),true);
				}
				else if(cbObject.getSelectedItem().equals(Modelos.NegocioProspeccao.toString())){
					combos = new JComboBox[]{cbProspeccao};
					dialog = new SelecaoDialog(new NegocioProspeccao(),txCodObjeto,txNomeObjeto,combos,comboNegocios,MenuView.getInstance(),true);
				}
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				txCodObjeto.addPropertyChangeListener(new MudarCliente());
			}
			break;
		case "CriarCategoria":
			dialog = new SelecaoDialog(new NegocioCategoria(), null, null, new JComboBox[]{cbCategoria,cbCategoriaCad},null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarNivel":
			dialog = new SelecaoDialog(new NegocioNivel(), null, null, new JComboBox[]{cbNivel,cbNivelCad},null,MenuView.getInstance(),true);
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
				
				TarefasSaveView taskView = new TarefasSaveView(null, this.negocio, radiosAndamento, MenuView.getInstance(),true,null,false);
				taskView.setVisible(true);
				taskView.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						boolean open = recebeSessao();
						negocio = (NegocioProposta) dao.receberObjeto(NegocioProposta.class, Integer.parseInt(txCodigo.getText()), session);
						preencherTarefas(negocio);
						fechaSessao(open);
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
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
				if(site!=null && site.trim().length()>0){
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
		        NegocioDocumento doc = gerarSerial(new File(txDocumentoPath.getText()));
				if(doc!=null){
					Set<NegocioDocumento> documentos = negocio.getDocumentos();
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
		case "Lote":
			Set<Integer> lotes = new HashSet<>();
			DefaultTableModel md2 = (DefaultTableModel)tbPrincipal.getModel();
			int i = 0;
			while(md2.getRowCount()>i) {
				lotes.add(Integer.parseInt(String.valueOf(md2.getValueAt(i, 0))));
				i++;
			}
			TarefasSaveView taskView = new TarefasSaveView(null, new NegocioProposta(),null, MenuView.getInstance(),true,lotes,true);
			taskView.setVisible(true);
			break;
		default:
			break;
		}
	}
	private NegocioDocumento gerarSerial(File arquivo){
		Randomico gerador = new Randomico();
        String nomeArquivo = gerador.gerarSerial(txCodigo.getText());
        FTPUpload upload = new FTPUpload();
        nomeArquivo+=arquivo.getName().substring(arquivo.getName().lastIndexOf("."), arquivo.getName().length());//inserindo extensao no nome do arquivo
        NegocioDocumento documento = null;
        if(upload.uploadFile(arquivo, nomeArquivo)){
        	documento = new NegocioDocumento();
        	documento.setNegocio(negocio);
        	documento.setNome(txDocumentoNome.getText());
        	documento.setDescricao(txDocumentoDescricao.getText());
        	documento.setData(new Date());
        	documento.setUrl(nomeArquivo);
        	documento.setUsuario(UsuarioLogado.getInstance().getUsuario());
        	preencherDocumentos(negocio.getDocumentos());
        }
        return documento;
    }
	private File carregarArquivo(){
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setDialogTitle("Selecione um arquivo");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Documento PDF", "pdf"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office","doc","docx","xls", "xlsx", "pptx"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
        int retorno = chooser.showOpenDialog(null);
        if(retorno==JFileChooser.OPEN_DIALOG){
            return chooser.getSelectedFile();
        }
        return null;
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
			String export = carregarArquivo("Salvar arquivo");
			if(!"".equals(export)){
				session = HibernateFactory.getSession();
				session.beginTransaction();
				realizarFiltro();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				ArrayList<ArrayList> listaImpressao = new ArrayList<>();
				
				Integer[] colunasLenght = new Integer[]{8,26,11,11,8,14,16,14,13,17,9,12,19,30,10,10,10,30,11,11};
				String[] cabecalho = new String[]{
						"Negocio","Nome", "E-mail","Fone","Celular",
						"Data Inicio","Data Limite","Tipo","Criador","Etapa","Status","Atendente",
						"Origem","Categoria","Nivel","Servicos/Produtos","Descricao",
						"Honorario","Servicos Contratados","Motivo da Perda","Detalhe da Perda","Data da Perda","Data Finalização"};
				listaImpressao.add(new ArrayList<>());
				for(String c : cabecalho){
					listaImpressao.get(0).add(c);
				}
				for(int i = 0;i<listarNegocios.size();i++){
					listaImpressao.add(new ArrayList());
					NegocioProposta n = listarNegocios.get(i);
					listaImpressao.get(i+1).add(n.getId());
					listaImpressao.get(i+1).add(n.getNome());
					
					NegocioPadrao pfpj = n.getPessoa()!=null?n.getPessoa().getPessoaFisica():
						(n.getEmpresa()!=null?n.getEmpresa().getPessoaJuridica():n.getProspeccao().getPfpj());
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
					listaImpressao.get(i+1).add(n.getDataFinalizacao()==null?"":dateFormat.format(n.getDataFinalizacao()));
				}
				ExcelGenerico planilha = new ExcelGenerico(export+".xls",listaImpressao,colunasLenght);
				try {
					planilha.gerarExcel();
					dao.salvarLog(session, UsuarioLogado.getInstance().getUsuario(), "Negocio", "Exportar", "Exportou relatorio xls");
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
			Order order = receberOrdenacao();
			criterios.addAll(receberFiltroSuperior());
			Criterion c = realizarBusca();
			if(c!=null) 
				criterios.add(c);
			listarNegocios = dao.items(NegocioProposta.class, session, criterios, order);
			preencherTabela(listarNegocios, tbPrincipal, txContadorRegistros);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!","Em edicao...",JOptionPane.INFORMATION_MESSAGE);
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
	private List<Criterion> receberFiltroSuperior(){
		List<Criterion> criterios = new ArrayList<>();
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
				Criterion crit1 = Restrictions.between("dataPerda", data01, data02);
				Criterion crit2 = Restrictions.between("dataFinalizacao", data01, data02);
				criterios.add(Restrictions.or(crit1,crit2));
			}
		}catch(NullPointerException e){
		}
		return criterios;
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
	public void novo() {
		site="";
		limparFormulario(pnCadastro);
		novoEditar();
		telaEmEdicao = true;
		negocio = new NegocioProposta();
		negocio.setHonorario(new BigDecimal("0.00"));
		txHonorario.setText("0,00");
		dataInicio.setDate(new Date());
		txEmail.setText("");
		txCelular.setText("");
		txFone.setText("");
//		DefaultTableModel serv = (DefaultTableModel)tbServicosContratados.getModel();
//		while(serv.getRowCount()>0)
//			serv.removeRow(0);
		pnAuxiliar.setVisible(false);
	}
	private void invocarSalvamento(){
		if("".equals(txCodigo.getText())){
			negocio.setCriadoEm(new Date());
			negocio.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
			negocio.setNome("".equals(txNome.getText().trim())?txNomeObjeto.getText():txNome.getText());
		}
		else
			negocio.setNome(txNome.getText().trim());
		NegocioStatus s = padrao.getStatus((String)cbStatusCad.getSelectedItem());
		if(s!=null && s.getId()!=1 && negocio.getStatus()!=s ){
			negocio.setDataFinalizacao(new Date());
		}
		else
			negocio.setDataFinalizacao(null);
		negocio.setStatus(s);//
		if("".equals(cbAtendenteCad.getSelectedItem()))
			negocio.setAtendente(UsuarioLogado.getInstance().getUsuario());//
		else
			negocio.setAtendente(padrao.getAtendentes((String)cbAtendenteCad.getSelectedItem()));
		session = HibernateFactory.getSession();
		session.beginTransaction();

		NegocioPadrao pessoaFisicaOrJuridica = new NegocioPadrao();
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

		if(cbObject.getSelectedItem().equals(Modelos.NegocioEmpresa.toString())){
			NegocioEmpresa o = (NegocioEmpresa) dao.receberObjeto(NegocioEmpresa.class, Integer.parseInt(txCodObjeto.getText()), session);
			negocio.setClasse("Empresa");
			negocio.setEmpresa(o);
			negocio.setPessoa(null);
			negocio.setProspeccao(null);
		}
		else if(cbObject.getSelectedItem().equals(Modelos.NegocioPessoa.toString())){
			Object o = dao.receberObjeto(NegocioPessoa.class, Integer.parseInt(txCodObjeto.getText()), session);
			negocio.setClasse("Pessoa");
			negocio.setPessoa((NegocioPessoa)o);
			negocio.setEmpresa(null);
			negocio.setProspeccao(null);
		}
		else if(cbObject.getSelectedItem().equals(Modelos.NegocioProspeccao.toString())){
			Object o = dao.receberObjeto(NegocioProspeccao.class, Integer.parseInt(txCodObjeto.getText()), session);
			negocio.setClasse("Prospeccao");
			negocio.setPessoa(null);
			negocio.setProspeccao((NegocioProspeccao)o);
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
		
		Set<NegocioDocumento> documentos = new HashSet<>();
		DefaultTableModel modelDocs = (DefaultTableModel)tbDocumentos.getModel();
		for(int i = 0 ; i< tbDocumentos.getRowCount(); i++){
			NegocioDocumento doc = (NegocioDocumento)dao.receberObjeto(NegocioDocumento.class, Integer.parseInt(String.valueOf(modelDocs.getValueAt(i, 0))), session);
			documentos.add(doc);
		}
		negocio.setDocumentos(documentos);		
		if(dao.salvar(negocio, session)){
			session.beginTransaction();
			telaEmEdicao = false;
			realizarFiltro();
			preencherFormulario(negocio);
			salvarCancelar();
		}
		session.close();
	}
	public void enviarEtapa(NegocioEtapa e){
		if(e!=null){
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
	}
	private NegocioEtapa receberEtapa(NegocioProposta r){
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
				limparFormulario(pnCadastro);
				limparFormulario(pnAuxiliar);
				listarNegocios = (List<NegocioProposta>)dao.listar(NegocioProposta.class, session);
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
		if(this.negocio!=null && this.negocio.getId()>0)
			negocioBackup=negocio;
		
	}
	@SuppressWarnings("rawtypes")
	private void limparFormulario(Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JTextField){
				((JTextField)c).setText("");
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setSelectedIndex(0);
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
				JViewport viewPort = ((JScrollPane)c).getViewport();
				limparFormulario((Container)viewPort);
			}
			else if(c instanceof JTabbedPane || c instanceof JPanel){
				limparFormulario((Container)c);
			}
		}
		//cbObject.setSelectedItem("Empresa");
		rbContato.setSelected(true);
		//cbStatusCad.setSelectedItem("Em Andamento");
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
				JViewport viewPort = ((JScrollPane)c).getViewport();
				desbloquerFormulario(desbloquear,(Container)viewPort);
			}
			else if(c instanceof JTabbedPane || c instanceof JPanel){
				desbloquerFormulario(desbloquear,(Container)c);
			}
			txDocumentoNome.setEnabled(true);
			txDocumentoDescricao.setEnabled(true);
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
	public void preencherTabela(List<NegocioProposta> lista, JTable table, JLabel txContadorRegistros){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(lista.isEmpty()){
			new SemRegistrosJTable(table,"Relação de Negócios");
		}
		else{
			Object [] tableHeader = {"ID","NOME","STATUS","ETAPA","ORIGEM","NIVEL","CRIADO EM","VENCIMENTO","CONCLUSAO","ATENDENTE","ABRIR"};
			DefaultTableModel model = new DefaultTableModel(tableHeader,0){

				private static final long serialVersionUID = -8716692364710569296L;
				boolean[] canEdit = new boolean[]{
						false,false,false,false,false,false,false,false,false,false,true
				};
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit [columnIndex];
				}
			};
			for(int i=0;i<lista.size();i++){
				NegocioProposta n = lista.get(i);
				Object[] linha = new Object[11];
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
				linha[6]=n.getCriadoEm()==null?"":sdf.format(n.getCriadoEm());
				linha[7]=n.getDataFim()==null?"":sdf.format(n.getDataFim());
//				if("Em Andamento".equals(n.getStatus().getNome())
//				&& n.getDataFim()!=null
//				&& new Date().compareTo(n.getDataFim())==1){
//			long diferenca = (new Date().getTime() - n.getDataFim().getTime()) + 3600000;
//			long qtd = (diferenca / 86400000L);
//			if(qtd>0)
//			linha[6]=qtd+" dia(s) atrasado(s)";
//		}
				
				linha[8] = n.getDataFinalizacao()!=null? sdf.format(n.getDataFinalizacao()):
					(n.getDataPerda()!=null?sdf.format(n.getDataPerda()):"");
				linha[9] = n.getAtendente()==null?"":n.getAtendente().getNome();
				
				String imageName ="";
				if("Empresa".equals(n.getClasse())){
					imageName ="button_empresas.png";
				}
				else
					imageName ="button_people.png";
				linha[10]= recalculate(new ImageIcon(ControllerTarefas.class
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

			JButton btAbrir = new ButtonColumnModel(table,10).getButton();
			btAbrir.setActionCommand("Abrir");
			btAbrir.addActionListener(new AcaoInTable());
			table.getColumnModel().getColumn(10).setPreferredWidth(40);
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
			Icon icoPro = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_prospeccao.png"));
			
			int id = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0));
			NegocioProposta neg = (NegocioProposta) dao.receberObjeto(NegocioProposta.class, id, session);
			if(icoEmp.toString().equals(value.toString())){
				NegocioEmpresa empresa = neg.getEmpresa();
				EmpresasView viewEmpresas = new EmpresasView(empresa);
				ControllerMenu.getInstance().abrirCorpo(viewEmpresas);
			}
			else if(icoPes.toString().equals(value.toString())){
				NegocioPessoa pessoa = neg.getPessoa();
				PessoasView viewPessoa = new PessoasView(pessoa);
				ControllerMenu.getInstance().abrirCorpo(viewPessoa);
			}
			else if(icoPro.toString().equals(value.toString())){
				NegocioProspeccao prospeccao = neg.getProspeccao();
				ProspeccaoView viewProspeccao = new ProspeccaoView(prospeccao);
				ControllerMenu.getInstance().abrirCorpo(viewProspeccao);
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
			this.negocio = (NegocioProposta) dao.receberObjeto(NegocioProposta.class, id, session);
			if(!pnAuxiliar.isVisible()) 
				pnAuxiliar.setVisible(true);
			limparFormulario(pnCadastro);
			preencherFormulario(this.negocio);
			tabbedPane.setSelectedIndex(1);
			fechaSessao(open);
			salvarCancelar();
		}
		else if(e.getComponent() instanceof JTabbedPane && telaEmEdicao && ((JTabbedPane)e.getComponent()).getSelectedIndex()==0){
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!",
					"Em edicao...",JOptionPane.INFORMATION_MESSAGE);
			tabbedPane.setSelectedIndex(1);
		}
		else if(e.getComponent() instanceof JRadioButton){
			session = HibernateFactory.getSession();
			session.beginTransaction();
			realizarFiltro();
			session.close();
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
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			session = HibernateFactory.getSession();
			session.beginTransaction();
			realizarFiltro();
			session.close();
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
    	txIconFone.setIcon(recalculate(iconContact));
    	txIconFone.setForeground(Color.blue);

    	ImageIcon iconCellPhone = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_cellphone.png"));
    	txIconCelular.setIcon(recalculate(iconCellPhone));
    	txIconCelular.setForeground(Color.BLUE);
    	
    	ImageIcon iconPerda = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/menu_about.png"));
    	btnVerPerda.setIcon(recalculate(iconPerda));
    	
    	ImageIcon iconDoc = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_upload.png"));
    	btnAnexarDocumento.setIcon(recalculate(iconDoc));
    	
    	ImageIcon iconDocEnviar = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
    	btnEnviarArquivo.setIcon(recalculate(iconDocEnviar));
    	
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
