package br.com.tiagods.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerNegocios;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.view.interfaces.DefaultComboBox;
import br.com.tiagods.view.interfaces.DefaultEnumModel.Modelos;
import javax.swing.JTabbedPane;

public class NegociosView extends JInternalFrame {
    /**
	 *
	 */
	private static final long serialVersionUID = 2406280053484370906L;
	public static DefaultComboBox cbAtendente;
    public static DefaultComboBox cbStatus;
    public static DefaultComboBox cbEtapa;
    public static DefaultComboBox cbEmpresa;
    public static DefaultComboBox cbPessoa;
    private javax.swing.JPanel pnVisao;
    public static JPanel pnPrincipal;
    public static JPanel pnAndamento;
    public static JPanel pnServicosContratados;
    @SuppressWarnings("rawtypes")
	public static JComboBox cbObject;
	private JPanel panel;
	private JLabel label;
	public static JDateChooser data2;
	private JLabel label_1;
	public static JDateChooser data1;
	public static JDateChooser dataInicio;
	public static JDateChooser dataFim;
	public static DefaultComboBox cbOrigem;
	public static DefaultComboBox cbCategoria;
	public static DefaultComboBox cbNivel;
	public static DefaultComboBox cbServicos;
	public static DefaultComboBox cbAtendenteCad;
	public static DefaultComboBox cbStatusCad;
	public static DefaultComboBox cbCategoriaCad;
	public static DefaultComboBox cbNivelCad;
	public static DefaultComboBox cbOrigemCad;
	public static DefaultComboBox cbServicosCad;
	public static DefaultComboBox cbServicosAgregados;
	public static JPanel pnAuxiliar;
	public static JPanel pnCadastro;
	private JLabel label_7;
	private JLabel lblDescrio;
	public static JTextField txCodigo;
	public static JLabel txValorNegocios;
	public static JTextField txNome;
	public static JTextField txCadastradoPor;
	private JLabel label_16;
	public static JTextField txDataCadastro;
	public static JLabel txContadorRegistros;
	public static JFormattedTextField txHonorario,txValorServico;
	public static JRadioButton rbContato, rbEnvioProposta, rbFollowup, rbFechamento, rbIndefinida;
	public static JButton btnNovo;
	public static JButton btnEditar;
	public static JButton btnSalvar;
	public static JButton btnExcluir;
	public static JButton btnCancelar;
	public static JButton btnHistorico;
	public static JButton btEsconder,btnNovaTarefa;
	public static JButton btAddServicoAgregado,btnCategoriaAdd, btnOrigemAdd,btnNivelAdd,btnServicoAdd;
	private JLabel lbAtendenteCad;
	private JPanel pnPrivacidade;
	public static JCheckBox checkBox;
	public static JCheckBox checkBox_1;
	public static JCheckBox checkBox_2;
	private JLabel label_21;
	public static JTextField txCodObjeto;
	public static JTextField txBuscar;
	public static JTable tbPrincipal, tbAuxiliar;
	public static JButton btAddEmpresaPessoa;
	private JPanel pnTotalizador;
	private JLabel lblValorTotalDe;
	public static JTextField txNomeObjeto;
	public static JTextArea txDescricao;
	public static JScrollPane scrollServicos;
	public static JTable tbServicosContratados;
	public static JTextField txIdServicoContratado;
	public static JButton btnNovoServicoAgregado;
	private JLabel lbTitulo;
	public static JButton btnLink,btnEmail,btnImportar,btnExportar,btnVerPerda,btnAnexarDocumento,btnEnviarArquivo;
	public static JLabel txIconFone;
	public static JLabel txIconCelular;
	public static JTextPane txFone, txCelular;
	public static JTextPane txEmail;
	private JPanel pnCadastros;
	public static JPanel pnFiltros;
	public static JTabbedPane tabbedPane,tpCadastroExtra;
	private JPanel pnDocumentos;
	public static JPanel pnPesquisa;
	private JPanel panel_5;
	private JPanel panel_6;
	public static JTable tbDocumentos;
	public static JTextField txDocumentoPath;
	public static JTextField txDocumentoNome;
	private JLabel lblOutros;
	private JLabel lblPrincipal;
	private JLabel lblDescrio_1;
	public static JTextField txDocumentoDescricao;
	public static JComboBox<String> cbOrdenacao, cbBuscarPor;
	public static JRadioButton rbCrescente, rbDecrescente;
	ControllerNegocios controller = new ControllerNegocios();
	private JLabel lblPesquisarenter;
	
	/**
	 * Create the frame.
	 */
	public NegociosView(Negocio negocio) {
		initComponents();
		controller.iniciar(negocio);                                                                                                                                                                                                       lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
                                                                                                                                                                                                                                                                                                                                                                        lbTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {

		setBorder(null);
		setClosable(true);
		setPreferredSize(new java.awt.Dimension(880, 450));

		pnVisao = new javax.swing.JPanel();
		GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		ButtonGroup groupEtapas = new ButtonGroup();
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        
        tabbedPane.setOpaque(true);
        pnPesquisa = new JPanel();
        cbAtendente = new DefaultComboBox();
        cbStatus = new DefaultComboBox();
        pnPrincipal = new javax.swing.JPanel();
        pnPrincipal.setOpaque(false);
        cbEmpresa = new DefaultComboBox();
		cbPessoa = new DefaultComboBox();
		cbOrigem = new DefaultComboBox();
		cbCategoria = new DefaultComboBox();
		cbNivel = new DefaultComboBox();
		cbServicos = new DefaultComboBox();
		cbEtapa = new DefaultComboBox();
		JLabel lbBuscar = new JLabel("Buscar");
		txBuscar = new JTextField();
		pnCadastro = new JPanel();
		

		pnVisao.setBackground(new java.awt.Color(250, 250, 250));
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnVisao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnVisao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnVisao.setLayout(null);
        
        pnCadastro.setOpaque(false);
        
		tabbedPane.setBounds(10, 47, 1230, 575);
        tabbedPane.setBackground(new Color(250,250,250));
		pnVisao.add(tabbedPane);

		tabbedPane.addTab("Pesquisar", null, pnPesquisa, null);
		pnPesquisa.setLayout(null);
		pnPesquisa.setBackground(new Color(250,250,250));
		pnPrincipal.setBounds(10, 11, 1205, 60);
		pnPesquisa.add(pnPrincipal);
		cbAtendente.setModel(new DefaultComboBoxModel(new String[] { "Atendente" }));
		cbStatus.setModel(new DefaultComboBoxModel(new String[] { "Status" }));
		cbEtapa.setName("Etapa");
		cbEmpresa.setModel(new DefaultComboBoxModel(new String[] { "Empresa" }));
		cbEmpresa.setName("Empresa");
		pnPrincipal.setBackground(new java.awt.Color(250, 250, 250));

		cbAtendente.setBackground(new java.awt.Color(250, 250, 250));
		cbAtendente.setName("Atendente");

		cbStatus.setBackground(new java.awt.Color(250, 250, 250));
		cbStatus.setName("Status");
		cbEtapa.setBackground(new java.awt.Color(250, 250, 250));
		cbEtapa.setModel(new DefaultComboBoxModel(
				new String[] { "Etapa", "Indefinida", "Contato", "Envio de Proposta", "Follow-up", "Fechamento" }));

		cbEmpresa.setBackground(new java.awt.Color(250, 250, 250));

		cbPessoa.setModel(new DefaultComboBoxModel(new String[] { "Pessoa" }));
		cbPessoa.setName("Pessoa");
		cbPessoa.setBackground(new Color(250, 250, 250));

		cbOrigem.setModel(new DefaultComboBoxModel(new String[] { "Origem" }));
		cbOrigem.setName("Origem");
		cbOrigem.setBackground(new Color(250, 250, 250));

		cbCategoria.setModel(new DefaultComboBoxModel(new String[] { "Categoria" }));
		cbCategoria.setName("Categoria");
		cbCategoria.setBackground(new Color(250, 250, 250));

		cbNivel.setModel(new DefaultComboBoxModel(new String[] { "Nivel" }));
		cbNivel.setName("Nivel");
		cbNivel.setBackground(new Color(250, 250, 250));

		cbServicos.setModel(new DefaultComboBoxModel(new String[] { "Produtos/Servicos" }));
		cbServicos.setName("Produtos/Servicos");
		cbServicos.setBackground(new Color(250, 250, 250));

		javax.swing.GroupLayout gl_pnPrincipal = new javax.swing.GroupLayout(pnPrincipal);
		gl_pnPrincipal.setHorizontalGroup(gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnPrincipal.createSequentialGroup().addContainerGap()
						.addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cbEtapa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE).addGap(10)
						.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbServicos, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cbPessoa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addGap(221)));
		gl_pnPrincipal.setVerticalGroup(gl_pnPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnPrincipal.createSequentialGroup().addGap(33)
						.addGroup(gl_pnPrincipal.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbEtapa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbPessoa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbServicos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		pnPrincipal.setLayout(gl_pnPrincipal);

		lbBuscar.setBounds(10, 100, 53, 20);
		pnPesquisa.add(lbBuscar);

		txBuscar.setBounds(74, 100, 139, 20);
		pnPesquisa.add(txBuscar);
		txBuscar.setColumns(10);

		panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(816, 82, 146, 61);
		pnPesquisa.add(panel);
		panel.setBackground(new Color(250, 250, 250));

		label = new JLabel();
		label.setBounds(10, 33, 22, 20);
		label.setText("at\u00E9");
		label.setHorizontalAlignment(SwingConstants.LEFT);

		data2 = new JDateChooser();
		data2.setBounds(36, 33, 100, 20);

		label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setBounds(10, 11, 22, 20);
		label_1.setText("de:");

		data1 = new JDateChooser();
		data1.setBounds(36, 11, 100, 20);
		panel.setLayout(null);
		panel.add(label_1);
		panel.add(label);
		panel.add(data2);
		panel.add(data1);

		btnExportar = new JButton();
		btnExportar.setBounds(972, 118, 130, 25);
		pnPesquisa.add(btnExportar);
		btnExportar.setToolTipText(
				"Exporte o registro atual ou todos os registros da tabela para uma planilha Modelo Excel");
		btnExportar.setText("Exportar");
		btnExportar.setName("Exportar");
		btnExportar.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnExportar.setActionCommand("Exportar");

		btnImportar = new JButton();
		btnImportar.setEnabled(false);
		btnImportar.setBounds(972, 82, 130, 25);
		pnPesquisa.add(btnImportar);
		btnImportar.setToolTipText("Importe um novo registro a partir de uma planilha Modelo Excel");
		btnImportar.setText("Importar");
		btnImportar.setName("Importar");
		btnImportar.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnImportar.setActionCommand("Importar");

		JScrollPane scrollPrincipal = new JScrollPane();
		scrollPrincipal.setBounds(10, 156, 952, 380);
		pnPesquisa.add(scrollPrincipal);

		tbPrincipal = new JTable();
		scrollPrincipal.setViewportView(tbPrincipal);

		pnTotalizador = new JPanel();
		pnTotalizador.setBounds(972, 156, 243, 142);
		pnPesquisa.add(pnTotalizador);
		pnTotalizador.setBackground(new Color(250, 250, 250));

		lblValorTotalDe = new JLabel("Valor total dos seus neg\u00F3cios esse m\u00EAs");
		lblValorTotalDe.setHorizontalAlignment(SwingConstants.CENTER);

		txValorNegocios = new JLabel("R$1.000,00");
		txValorNegocios.setFont(new Font("Tahoma", Font.BOLD, 12));
		txValorNegocios.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNegcios = new JLabel("[#] Neg\u00F3cios + Servi\u00E7os");
		lblNegcios.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_pnTotalizador = new GroupLayout(pnTotalizador);
		gl_pnTotalizador.setHorizontalGroup(
			gl_pnTotalizador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnTotalizador.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnTotalizador.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNegcios, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addComponent(lblValorTotalDe, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addComponent(txValorNegocios, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnTotalizador.setVerticalGroup(
			gl_pnTotalizador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnTotalizador.createSequentialGroup()
					.addGap(8)
					.addComponent(lblValorTotalDe, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(txValorNegocios, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNegcios, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnTotalizador.setLayout(gl_pnTotalizador);
		txContadorRegistros = new JLabel("");
		txContadorRegistros.setBounds(972, 522, 152, 14);
		pnPesquisa.add(txContadorRegistros);
		
		cbBuscarPor = new JComboBox();
		cbBuscarPor.setModel(new DefaultComboBoxModel(new String[] {"C\u00F3digo", "Nome"}));
		cbBuscarPor.setBounds(223, 100, 100, 20);
		pnPesquisa.add(cbBuscarPor);
		
		ButtonGroup group = new ButtonGroup();
		
		rbCrescente = new JRadioButton("Crescente");
		rbCrescente.setActionCommand("Ordenar");
		rbCrescente.addActionListener(controller);
		rbCrescente.setOpaque(false);
		rbCrescente.setBounds(582, 100, 109, 23);
		pnPesquisa.add(rbCrescente);
		group.add(rbCrescente);
		
		rbDecrescente = new JRadioButton("Decrescente");
		rbDecrescente.setActionCommand("Odernar");
		rbDecrescente.addActionListener(controller);
		rbDecrescente.setOpaque(false);
		rbDecrescente.setBounds(582, 127, 109, 23);
		rbDecrescente.setSelected(true);
		pnPesquisa.add(rbDecrescente);
		group.add(rbDecrescente);
		
		JLabel lblOrdenarPor = new JLabel("Ordenar Por:");
		lblOrdenarPor.setBounds(333, 100, 90, 20);
		pnPesquisa.add(lblOrdenarPor);
		
		cbOrdenacao = new JComboBox();
		cbOrdenacao.setModel(new DefaultComboBoxModel(new String[] {"C\u00F3digo", "Nome", "Data Cria\u00E7\u00E3o", "Data Vencimento", "Data Finaliza\u00E7\u00E3o"}));
		cbOrdenacao.setBounds(433, 100, 130, 20);
		pnPesquisa.add(cbOrdenacao);
		
		lblPesquisarenter = new JLabel("Pressione <Enter>");
		lblPesquisarenter.setHorizontalAlignment(SwingConstants.CENTER);
		lblPesquisarenter.setBounds(74, 131, 139, 14);
		pnPesquisa.add(lblPesquisarenter);

		pnCadastros = new JPanel();
		pnCadastros.setBackground(new Color(250,250,250));
		tabbedPane.addTab("Cadastro", null, pnCadastros, null);
		pnCadastros.setLayout(null);


		pnAndamento = new JPanel();
		pnAndamento.setOpaque(false);
		pnAndamento.setBounds(10, 11, 735, 40);
		pnCadastros.add(pnAndamento);
		pnAndamento.setBackground(new Color(250, 250, 250));

		rbContato = new JRadioButton("Contato");
		rbContato.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rbContato.setOpaque(false);
		pnAndamento.add(rbContato);

		rbEnvioProposta = new JRadioButton("Envio de Proposta");
		rbEnvioProposta.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rbEnvioProposta.setOpaque(false);
		pnAndamento.add(rbEnvioProposta);

		rbFollowup = new JRadioButton("Follow-up");
		rbFollowup.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rbFollowup.setOpaque(false);
		pnAndamento.add(rbFollowup);

		rbFechamento = new JRadioButton("Fechamento");
		rbFechamento.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rbFechamento.setOpaque(false);
		pnAndamento.add(rbFechamento);

		rbIndefinida = new JRadioButton("Indefinida");
		rbIndefinida.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rbIndefinida.setOpaque(false);
		pnAndamento.add(rbIndefinida);

		JLabel lblStatus = new JLabel();
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnAndamento.add(lblStatus);
		lblStatus.setText("Status:");

		rbContato.setSelected(true);

		cbStatusCad = new DefaultComboBox();
		cbStatusCad.setFont(new Font("Tahoma", Font.PLAIN, 10));
		cbStatusCad.setName("StatusCad");
		pnAndamento.add(cbStatusCad);
		cbStatusCad.setModel(new DefaultComboBoxModel(new String[] { "Em Andamento", "Ganho", "Perdido" }));

		btnVerPerda = new JButton();
		btnVerPerda.setActionCommand("VerPerda");
		btnVerPerda.addActionListener(controller);
		pnAndamento.add(btnVerPerda);
		groupEtapas.add(rbContato);
		groupEtapas.add(rbEnvioProposta);
		groupEtapas.add(rbFollowup);
		groupEtapas.add(rbFechamento);
		groupEtapas.add(rbIndefinida);


		pnCadastro.setBounds(10, 62, 735, 481);
		pnCadastros.add(pnCadastro);
		pnCadastro.setLayout(null);
		pnCadastro.setBackground((Color) null);

		label_7 = new JLabel();
		label_7.setBounds(new Rectangle(0, 40, 0, 0));
		label_7.setText("Nome:");
		label_7.setBounds(10, 30, 56, 17);
		pnCadastro.add(label_7);

		lblDescrio = new JLabel();
		lblDescrio.setText("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(10, 158, 78, 14);
		pnCadastro.add(lblDescrio);

		txCodigo = new JTextField();
		txCodigo.setEnabled(false);
		txCodigo.setBounds(10, 5, 87, 20);
		pnCadastro.add(txCodigo);

		txNome = new JTextField();
		txNome.setBounds(new Rectangle(0, 40, 0, 0));
		txNome.setBounds(107, 30, 201, 20);
		pnCadastro.add(txNome);

		txCadastradoPor = new JTextField();
		txCadastradoPor.setEnabled(false);
		txCadastradoPor.setBounds(278, 5, 119, 20);
		pnCadastro.add(txCadastradoPor);

		label_16 = new JLabel();
		label_16.setText("Cadastro em:");
		label_16.setBounds(107, 5, 78, 14);
		pnCadastro.add(label_16);

		txDataCadastro = new JTextField();
		txDataCadastro.setEnabled(false);
		txDataCadastro.setBounds(195, 5, 73, 20);
		pnCadastro.add(txDataCadastro);

		btnNovo = new JButton();
		btnNovo.setToolTipText("Novo");
		btnNovo.setFont(new Font("Dialog", Font.PLAIN, 9));
		btnNovo.setActionCommand("Novo");
		btnNovo.setName("Novo");
		btnNovo.addActionListener(controller);
		btnNovo.setBounds(207, 445, 90, 25);
		pnCadastro.add(btnNovo);

		btnEditar = new JButton();
		btnEditar.setToolTipText("Editar");
		btnEditar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btnEditar.setActionCommand("Editar");
		btnEditar.setName("Editar");
		btnEditar.addActionListener(controller);
		btnEditar.setBounds(307, 445, 90, 25);
		pnCadastro.add(btnEditar);

		btnSalvar = new JButton();
		btnSalvar.setToolTipText("Salvar");
		btnSalvar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btnSalvar.setActionCommand("Salvar");
		btnSalvar.setName("Salvar");
		btnSalvar.addActionListener(controller);
		btnSalvar.setBounds(407, 445, 90, 25);
		pnCadastro.add(btnSalvar);

		btnCancelar = new JButton();
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btnCancelar.setActionCommand("Cancelar");
		btnCancelar.setName("Cancelar");
		btnCancelar.addActionListener(controller);
		btnCancelar.setBounds(507, 445, 90, 25);
		pnCadastro.add(btnCancelar);

		lbAtendenteCad = new JLabel();
		lbAtendenteCad.setBounds(new Rectangle(0, 40, 0, 0));
		lbAtendenteCad.setText("Atendente:");
		lbAtendenteCad.setBounds(345, 30, 78, 20);
		pnCadastro.add(lbAtendenteCad);

		cbAtendenteCad = new DefaultComboBox();
		cbAtendenteCad.setBounds(new Rectangle(0, 40, 0, 0));
		cbAtendenteCad.setName("AtendenteCad");
		cbAtendenteCad.setBounds(436, 28, 115, 20);
		pnCadastro.add(cbAtendenteCad);

		btnHistorico = new JButton();
		btnHistorico.setToolTipText("Tarefas");
		btnHistorico.setFont(new Font("Dialog", Font.PLAIN, 9));
		btnHistorico.setActionCommand("Historico");
		btnHistorico.setBounds(107, 445, 90, 25);
		btnHistorico.addActionListener(controller);
		pnCadastro.add(btnHistorico);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(109, 153, 314, 70);
		pnCadastro.add(scrollPane_2);

		txDescricao = new JTextArea();
		txDescricao.setWrapStyleWord(true);
		txDescricao.setLineWrap(true);
		scrollPane_2.setViewportView(txDescricao);

		JLabel lblEmppessoa = new JLabel();
		lblEmppessoa.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblEmppessoa.setText("Empresa/Pessoa:");
		lblEmppessoa.setBounds(10, 58, 87, 17);
		pnCadastro.add(lblEmppessoa);

		btAddEmpresaPessoa = new JButton();
		btAddEmpresaPessoa.setActionCommand("VincularObjeto");
		btAddEmpresaPessoa.addActionListener(controller);
		btAddEmpresaPessoa.setBounds(203, 58, 36, 25);
		pnCadastro.add(btAddEmpresaPessoa);

		txCodObjeto = new JTextField();
		txCodObjeto.setEnabled(false);
		txCodObjeto.setBackground(new Color(250, 250, 250));
		txCodObjeto.setBounds(246, 61, 29, 17);
		pnCadastro.add(txCodObjeto);

		JLabel lbFim = new JLabel();
		lbFim.setText("Concluir em:");
		lbFim.setHorizontalAlignment(SwingConstants.LEFT);
		lbFim.setBounds(249, 122, 75, 20);
		pnCadastro.add(lbFim);

		dataFim = new JDateChooser();
		dataFim.setBounds(325, 122, 98, 20);
		pnCadastro.add(dataFim);

		dataInicio = new JDateChooser();
		dataInicio.setBounds(109, 122, 98, 20);
		pnCadastro.add(dataInicio);

		JLabel lbInicio = new JLabel();
		lbInicio.setText("Data Inicio:");
		lbInicio.setHorizontalAlignment(SwingConstants.LEFT);
		lbInicio.setBounds(10, 122, 87, 20);
		pnCadastro.add(lbInicio);

		txNomeObjeto = new JTextField();
		txNomeObjeto.setEnabled(false);
		txNomeObjeto.setBackground(new Color(250, 250, 250));
		txNomeObjeto.setBounds(278, 61, 145, 17);
		pnCadastro.add(txNomeObjeto);

		btnExcluir = new JButton();
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setFont(new Font("Dialog", Font.PLAIN, 9));
		btnExcluir.setActionCommand("Excluir");
		btnExcluir.setName("Excluir");
		btnExcluir.setBounds(607, 445, 90, 25);
		btnExcluir.addActionListener(controller);
		pnCadastro.add(btnExcluir);

		cbObject = new JComboBox();
		cbObject.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cbObject.setModel(new DefaultComboBoxModel(Modelos.values()));
		cbObject.setBounds(107, 58, 87, 20);
		cbObject.setName("Objeto");
		pnCadastro.add(cbObject);

		btnLink = new JButton();
		btnLink.setToolTipText("Abrir P\u00E1gina");
		btnLink.setActionCommand("OpenURL");
		btnLink.setBounds(377, 86, 50, 25);
		btnLink.addActionListener(controller);
		pnCadastro.add(btnLink);

		btnEmail = new JButton();
		btnEmail.setToolTipText("Enviar e-mail");
		btnEmail.setActionCommand("MailTo");
		btnEmail.setBounds(317, 86, 50, 25);
		btnEmail.addActionListener(controller);
		pnCadastro.add(btnEmail);

		txFone = new JTextPane();
		txFone.setEnabled(false);
		txFone.setFont(new Font("Tahoma", Font.BOLD, 12));
		txFone.setBounds(476, 58, 124, 19);
		pnCadastro.add(txFone);

		txCelular = new JTextPane();
		txCelular.setEnabled(false);
		txCelular.setFont(new Font("Tahoma", Font.BOLD, 12));
		txCelular.setBounds(476, 92, 124, 17);
		pnCadastro.add(txCelular);

		txIconFone = new JLabel();
		txIconFone.setBounds(437, 58, 29, 25);
		pnCadastro.add(txIconFone);

		txIconCelular = new JLabel();
		txIconCelular.setBounds(439, 89, 29, 25);
		pnCadastro.add(txIconCelular);

		JLabel lbEmail = new JLabel();
		lbEmail.setText("E-mail");
		lbEmail.setBounds(10, 86, 56, 20);
		pnCadastro.add(lbEmail);

		txEmail = new JTextPane();
		txEmail.setEditable(false);
		txEmail.setBounds(107, 86, 201, 20);
		pnCadastro.add(txEmail);
		
		tpCadastroExtra = new JTabbedPane(JTabbedPane.TOP);
		tpCadastroExtra.setBounds(10, 234, 715, 200);
		pnCadastro.add(tpCadastroExtra);
		
		pnFiltros = new JPanel();
		pnFiltros.setBackground(new Color(250,250,250));
		tpCadastroExtra.addTab("Filtros", null, pnFiltros, null);
		pnFiltros.setLayout(null);

		JLabel lbCategoriaCad = new JLabel();
		lbCategoriaCad.setBounds(48, 25, 87, 18);
		pnFiltros.add(lbCategoriaCad);
		lbCategoriaCad.setText("Categoria:");

		JLabel lbOrigemCad = new JLabel();
		lbOrigemCad.setBounds(48, 55, 87, 18);
		pnFiltros.add(lbOrigemCad);
		lbOrigemCad.setText("Origem:");

		cbOrigemCad = new DefaultComboBox();
		cbOrigemCad.setBounds(140, 55, 116, 20);
		pnFiltros.add(cbOrigemCad);
		cbOrigemCad.setName("OrigemCad");

		cbCategoriaCad = new DefaultComboBox();
		cbCategoriaCad.setBounds(140, 25, 116, 20);
		pnFiltros.add(cbCategoriaCad);
		cbCategoriaCad.setName("CategoriaCad");

		btnCategoriaAdd = new JButton();
		btnCategoriaAdd.setBounds(266, 25, 36, 25);
		pnFiltros.add(btnCategoriaAdd);
		btnCategoriaAdd.setActionCommand("CriarCategoria");

		btnOrigemAdd = new JButton();
		btnOrigemAdd.setBounds(266, 55, 36, 25);
		pnFiltros.add(btnOrigemAdd);
		btnOrigemAdd.setActionCommand("CriarOrigem");

		JLabel lbServicosCad = new JLabel();
		lbServicosCad.setBounds(48, 115, 80, 17);
		pnFiltros.add(lbServicosCad);
		lbServicosCad.setText("Ramo:");

		JLabel lbNivelCad = new JLabel();
		lbNivelCad.setBounds(48, 85, 78, 18);
		pnFiltros.add(lbNivelCad);
		lbNivelCad.setText("Nivel:");

		cbNivelCad = new DefaultComboBox();
		cbNivelCad.setBounds(140, 85, 116, 20);
		pnFiltros.add(cbNivelCad);
		cbNivelCad.setName("NivelCad");

		cbServicosCad = new DefaultComboBox();
		cbServicosCad.setBounds(140, 115, 116, 20);
		pnFiltros.add(cbServicosCad);
		cbServicosCad.setName("ServicosCad");

		btnServicoAdd = new JButton();
		btnServicoAdd.setBounds(266, 112, 36, 25);
		pnFiltros.add(btnServicoAdd);
		btnServicoAdd.setActionCommand("CriarServico");

		btnNivelAdd = new JButton();
		btnNivelAdd.setBounds(266, 84, 36, 25);
		pnFiltros.add(btnNivelAdd);
		btnNivelAdd.setActionCommand("CriarNivel");

		panel_5 = new JPanel();
		panel_5.setBackground(new Color(250,250,250));
		tpCadastroExtra.addTab("Valores", null, panel_5, null);
		panel_5.setLayout(null);

		txHonorario = new JFormattedTextField();
		txHonorario.setBounds(99, 29, 78, 20);
		panel_5.add(txHonorario);

		JLabel lblValor = new JLabel();
		lblValor.setBounds(0, 30, 87, 17);
		panel_5.add(lblValor);
		lblValor.setText("Honor\u00E1rio:(R$)");

		pnServicosContratados = new JPanel();
		pnServicosContratados.setOpaque(false);
		pnServicosContratados.setBounds(231, 29, 469, 33);
		panel_5.add(pnServicosContratados);
		pnServicosContratados.setBackground(new Color(250, 250, 250));
		pnServicosContratados.setLayout(null);

		JLabel lblServio = new JLabel("Servi\u00E7o:");
		lblServio.setBounds(31, 1, 52, 23);
		pnServicosContratados.add(lblServio);

		cbServicosAgregados = new DefaultComboBox();
		cbServicosAgregados.setBounds(82, 1, 101, 22);
		cbServicosAgregados.setName("ServicoAgregadoCad");
		pnServicosContratados.add(cbServicosAgregados);

		btAddServicoAgregado = new JButton("");
		btAddServicoAgregado.setBounds(414, 1, 45, 23);
		pnServicosContratados.add(btAddServicoAgregado);
		btAddServicoAgregado.setActionCommand("AdicionarServicoAgregado");
		btAddServicoAgregado.addActionListener(controller);

		txIdServicoContratado = new JTextField();
		txIdServicoContratado.setEnabled(false);
		txIdServicoContratado.setBounds(0, 0, 21, 23);
		pnServicosContratados.add(txIdServicoContratado);

		btnNovoServicoAgregado = new JButton("");
		btnNovoServicoAgregado.setActionCommand("NovoServicoContratado");
		btnNovoServicoAgregado.addActionListener(controller);
		btnNovoServicoAgregado.setBounds(200, 1, 45, 22);
		pnServicosContratados.add(btnNovoServicoAgregado);

		JLabel lblValor_1 = new JLabel("Valor:");
		lblValor_1.setBounds(255, 1, 54, 23);
		pnServicosContratados.add(lblValor_1);

		txValorServico = new JFormattedTextField();
		txValorServico.setBounds(319, 2, 85, 20);
		pnServicosContratados.add(txValorServico);
		txValorServico.setHorizontalAlignment(SwingConstants.CENTER);
		txValorServico.setText("0,00");

		scrollServicos = new JScrollPane();
		scrollServicos.setBounds(231, 73, 469, 88);
		panel_5.add(scrollServicos);
		scrollServicos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		tbServicosContratados = new JTable();
		scrollServicos.setViewportView(tbServicosContratados);
		
		lblOutros = new JLabel();
		lblOutros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOutros.setText("Outros:");
		lblOutros.setBounds(231, 0, 183, 17);
		panel_5.add(lblOutros);
		
		lblPrincipal = new JLabel();
		lblPrincipal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrincipal.setText("Principal:");
		lblPrincipal.setBounds(0, 1, 177, 17);
		panel_5.add(lblPrincipal);

		pnDocumentos = new JPanel();
		pnDocumentos.setBackground(new Color(250,250,250));
		tpCadastroExtra.addTab("Documentos", null, pnDocumentos, null);
		pnDocumentos.setLayout(null);
		
		JScrollPane spDocumentos = new JScrollPane();
		spDocumentos.setBounds(10, 75, 690, 86);
		pnDocumentos.add(spDocumentos);
		
		tbDocumentos = new JTable();
		spDocumentos.setViewportView(tbDocumentos);
		
		txDocumentoPath = new JTextField();
		txDocumentoPath.setEnabled(false);
		txDocumentoPath.setBounds(109, 42, 471, 20);
		pnDocumentos.add(txDocumentoPath);
		txDocumentoPath.setColumns(10);
		
		btnAnexarDocumento = new JButton("");
		btnAnexarDocumento.setToolTipText("Anexar Documento");
		btnAnexarDocumento.setActionCommand("Anexar Documento");
		btnAnexarDocumento.addActionListener(controller);
		btnAnexarDocumento.setBounds(590, 29, 50, 34);
		pnDocumentos.add(btnAnexarDocumento);
		
		txDocumentoNome = new JTextField();
		txDocumentoNome.setToolTipText("O que \u00E9? Proposta, Comprovantes Diversos ...etc");
		txDocumentoNome.setBounds(109, 11, 132, 20);
		pnDocumentos.add(txDocumentoNome);
		txDocumentoNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 14, 70, 14);
		pnDocumentos.add(lblNome);
		
		JLabel lblLocalizao = new JLabel("Localiza\u00E7\u00E3o");
		lblLocalizao.setBounds(10, 45, 70, 14);
		pnDocumentos.add(lblLocalizao);
		
		btnEnviarArquivo = new JButton("");
		btnEnviarArquivo.setToolTipText("Salvar Documento");
		btnEnviarArquivo.setActionCommand("Enviar Documento");
		btnEnviarArquivo.addActionListener(controller);
		btnEnviarArquivo.setBounds(650, 29, 50, 34);
		pnDocumentos.add(btnEnviarArquivo);
		
		lblDescrio_1 = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio_1.setBounds(251, 14, 70, 14);
		pnDocumentos.add(lblDescrio_1);
		
		txDocumentoDescricao = new JTextField();
		txDocumentoDescricao.setColumns(10);
		txDocumentoDescricao.setBounds(331, 11, 249, 20);
		pnDocumentos.add(txDocumentoDescricao);

		panel_6 = new JPanel();
		tpCadastroExtra.addTab("Privacidade", null, panel_6, null);
		tpCadastroExtra.setEnabledAt(3, false);
		panel_6.setLayout(null);
		pnPrivacidade = new JPanel();
		pnPrivacidade.setBounds(10, 11, 172, 150);
		panel_6.add(pnPrivacidade);

		pnPrivacidade.setVisible(false);


		pnPrivacidade.setBackground((Color) null);

		checkBox = new JCheckBox("Outros");
		checkBox.setBackground((Color) null);

		checkBox_1 = new JCheckBox("Eu");
		checkBox_1.setBackground((Color) null);

		checkBox_2 = new JCheckBox("Todos");
		checkBox_2.setBackground((Color) null);

		label_21 = new JLabel("Privacidade:");
		label_21.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel_3 = new GroupLayout(pnPrivacidade);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel_3.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
						.addComponent(checkBox_1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
						.addComponent(checkBox_2, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
						.addComponent(label_21, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
				.addContainerGap()));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup().addComponent(label_21)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(checkBox_2)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(checkBox_1)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(checkBox)
						.addContainerGap(46, Short.MAX_VALUE)));
		pnPrivacidade.setLayout(gl_panel_3);
		btnNivelAdd.addActionListener(controller);
		btnServicoAdd.addActionListener(controller);
		btnOrigemAdd.addActionListener(controller);
		btnCategoriaAdd.addActionListener(controller);

		pnAuxiliar = new JPanel();
		pnAuxiliar.setOpaque(false);
		pnAuxiliar.setBounds(755, 11, 460, 363);
		pnCadastros.add(pnAuxiliar);
		pnAuxiliar.setBackground(new Color(250, 250, 250));

		JScrollPane scrolAuxiliar = new JScrollPane();
		scrolAuxiliar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrolAuxiliar.setBounds(0, 52, 450, 308);

		btEsconder = new JButton("Esconder");
		btEsconder.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btEsconder.setBounds(330, 11, 120, 25);
		btEsconder.setActionCommand("Esconder");
		btEsconder.addActionListener(controller);
		pnAuxiliar.setLayout(null);

		tbAuxiliar = new JTable();
		scrolAuxiliar.setViewportView(tbAuxiliar);
		pnAuxiliar.add(scrolAuxiliar);
		pnAuxiliar.add(btEsconder);

		btnNovaTarefa = new JButton();
		btnNovaTarefa.setBounds(10, 11, 130, 25);
		pnAuxiliar.add(btnNovaTarefa);
		btnNovaTarefa.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnNovaTarefa.setText("Nova Tarefa");
		btnNovaTarefa.setName("Nova Tarefa");
		btnNovaTarefa.setActionCommand("Nova Tarefa");
		btnNovaTarefa.addActionListener(controller);
		btnImportar.addActionListener(controller);
		btnExportar.addActionListener(controller);

		lbTitulo = new JLabel("Cadastro de Negocios");
		lbTitulo.setBounds(10, 11, 1230, 25);
		pnVisao.add(lbTitulo);
		
        pack();
        setBounds(0, 0, 1250, 660);
    }
}
