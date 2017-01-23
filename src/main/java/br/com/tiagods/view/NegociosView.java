package br.com.tiagods.view;

import java.awt.Color;
import java.awt.EventQueue;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerNegocios;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.view.interfaces.DefaultComboBox;
import br.com.tiagods.view.interfaces.DefaultEnumModel.Modelos;
import java.awt.Rectangle;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

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
	private JScrollPane scrollPane;
	private JButton button;
	public static JPanel pnCadastro;
	private JLabel label_7;
	private JLabel lblDescrio;
	public static JLabel txCodigo;
	public static JTextField txNome;
	public static JLabel txCadastradoPor;
	private JLabel label_16;
	public static JLabel txDataCadastro;
	public static JLabel txContadorRegistros;
	public static JFormattedTextField txHonorario,txValorServico;
	public static JRadioButton rbContato, rbEnvioProposta, rbFollowup, rbFechamento, rbIndefinida;
	public static JButton btnNovo;
	public static JButton btnEditar;
	public static JButton btnSalvar;
	public static JButton btnExcluir;
	public static JButton btnCancelar;
	public static JButton btnHistorico;
	public static JButton btEsconder;
	public static JButton btAddServicoAgregado;
	private JLabel lbAtendenteCad;
	private JPanel pnPrivacidade;
	public static JCheckBox checkBox;
	public static JCheckBox checkBox_1;
	public static JCheckBox checkBox_2;
	private JLabel label_21;
	public static JLabel txCodObjeto;
	private JTable table;
	public static JTextField txBuscar;
	public static JTable tbPrincipal, tbAuxiliar;
	public static JButton btAddEmpresaPessoa;
	private JPanel panel_5;
	private JLabel lblValorTotalDe;
	public static JLabel txNomeObjeto;
	public static JTextArea txDescricao;
	public static JScrollPane scrollServicos;
	ControllerNegocios controller = new ControllerNegocios();
	public static JTable tbServicosContratados;
	public static JLabel txIdServicoContratado;
	private JButton btReturn;
	/**
	 * Create the frame.
	 */
	public NegociosView(Negocio negocio) {
		initComponents();
		controller.iniciar(negocio);
		pnPrivacidade.setVisible(false);
		pnAuxiliar.setVisible(false);
		rbContato.setSelected(true);
		panel_5.setVisible(false);
        
	}
	private void initComponents() {
        pnVisao = new javax.swing.JPanel();
        pnPrincipal = new javax.swing.JPanel();
        pnPrincipal.setBounds(0, 0, 1240, 60);
        cbAtendente = new DefaultComboBox();
        cbAtendente.setModel(new DefaultComboBoxModel(new String[] {"Atendente"}));
        cbStatus = new DefaultComboBox();
        cbStatus.setModel(new DefaultComboBoxModel(new String[] {"Status"}));
        cbEtapa = new DefaultComboBox();
        cbEtapa.setName("Etapa");
        cbEmpresa = new DefaultComboBox();
        cbEmpresa.setModel(new DefaultComboBoxModel(new String[] {"Empresa"}));
        cbEmpresa.setName("Empresa");
        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        pnVisao.setBackground(new java.awt.Color(250, 250, 250));

        pnPrincipal.setBackground(new java.awt.Color(250, 250, 250));

        cbAtendente.setBackground(new java.awt.Color(250, 250, 250));
        cbAtendente.setName("Atendente");

        cbStatus.setBackground(new java.awt.Color(250, 250, 250));
        cbStatus.setName("Status");
        cbEtapa.setBackground(new java.awt.Color(250, 250, 250));
        cbEtapa.setModel(new DefaultComboBoxModel(new String[] {"Etapa", "Indefinida", "Contato", "Envio de Proposta", "Follow-up", "Fechamento"}));

        cbEmpresa.setBackground(new java.awt.Color(250, 250, 250));
        
        cbPessoa = new DefaultComboBox();
        cbPessoa.setModel(new DefaultComboBoxModel(new String[] {"Pessoa"}));
        cbPessoa.setName("Pessoa");
        cbPessoa.setBackground(new Color(250, 250, 250));

        panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));

        label = new JLabel();
        label.setBounds(10, 37, 22, 20);
        label.setText("at\u00E9");
        label.setHorizontalAlignment(SwingConstants.LEFT);

        data2 = new JDateChooser();
        data2.setBounds(36, 37, 100, 20);

        label_1 = new JLabel();
        label_1.setHorizontalAlignment(SwingConstants.LEFT);
        label_1.setBounds(10, 11, 22, 20);
        label_1.setText("de:");

        data1 = new JDateChooser();
        data1.setBounds(36, 11, 100, 20);

        cbOrigem = new DefaultComboBox();
        cbOrigem.setModel(new DefaultComboBoxModel(new String[] {"Origem"}));
        cbOrigem.setName("Origem");
        cbOrigem.setBackground(new Color(250, 250, 250));
        
        cbCategoria = new DefaultComboBox();
        cbCategoria.setModel(new DefaultComboBoxModel(new String[] {"Categoria"}));
        cbCategoria.setName("Categoria");
        cbCategoria.setBackground(new Color(250, 250, 250));
        
        cbNivel = new DefaultComboBox();
        cbNivel.setModel(new DefaultComboBoxModel(new String[] {"Nivel"}));
        cbNivel.setName("Nivel");
        cbNivel.setBackground(new Color(250, 250, 250));
        
        cbServicos = new DefaultComboBox();
        cbServicos.setModel(new DefaultComboBoxModel(new String[] {"Produtos/Servicos"}));
        cbServicos.setName("Produtos/Servicos");
        cbServicos.setBackground(new Color(250, 250, 250));

        javax.swing.GroupLayout gl_pnPrincipal = new javax.swing.GroupLayout(pnPrincipal);
        gl_pnPrincipal.setHorizontalGroup(
        	gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbEtapa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addGap(10)
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
        			.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        gl_pnPrincipal.setVerticalGroup(
        	gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addGroup(gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pnPrincipal.createSequentialGroup()
        					.addGap(23)
        					.addGroup(gl_pnPrincipal.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbEtapa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbPessoa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbServicos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 58, Short.MAX_VALUE))
        			.addContainerGap())
        );
        panel.setLayout(null);
        panel.add(label_1);
        panel.add(label);
        panel.add(data2);
        panel.add(data1);
        pnPrincipal.setLayout(gl_pnPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
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
        pnVisao.add(pnPrincipal);

        pnAuxiliar = new JPanel();
        pnAuxiliar.setBackground(new Color(250, 250, 250));
        pnAuxiliar.setBounds(780, 260, 460, 363);
        pnVisao.add(pnAuxiliar);

        JScrollPane scrolAuxiliar = new JScrollPane();
        scrolAuxiliar.setBounds(0, 52, 450, 308);

        btEsconder = new JButton("Esconder");
        btEsconder.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btEsconder.setBounds(369, 11, 83, 23);
        btEsconder.setActionCommand("Esconder");
        btEsconder.addActionListener(controller);
        pnAuxiliar.setLayout(null);

        tbAuxiliar = new JTable();
        scrolAuxiliar.setViewportView(tbAuxiliar);
        pnAuxiliar.add(scrolAuxiliar);
        pnAuxiliar.add(btEsconder);

        pnCadastro = new JPanel();
        pnCadastro.setLayout(null);
        pnCadastro.setBackground((Color) null);
        pnCadastro.setBounds(10, 268, 760, 355);
        pnVisao.add(pnCadastro);

        label_7 = new JLabel();
        label_7.setBounds(new Rectangle(0, 40, 0, 0));
        label_7.setText("Nome:");
        label_7.setBounds(10, 30, 56, 17);
        pnCadastro.add(label_7);

        lblDescrio = new JLabel();
        lblDescrio.setText("Descri\u00E7\u00E3o:");
        lblDescrio.setBounds(8, 245, 78, 14);
        pnCadastro.add(lblDescrio);

        txCodigo = new JLabel();
        txCodigo.setBounds(107, 5, 87, 14);
        pnCadastro.add(txCodigo);

        txNome = new JTextField();
        txNome.setBounds(new Rectangle(0, 40, 0, 0));
        txNome.setBounds(107, 30, 201, 20);
        pnCadastro.add(txNome);

        txCadastradoPor = new JLabel();
        txCadastradoPor.setBounds(517, 5, 83, 14);
        pnCadastro.add(txCadastradoPor);

        label_16 = new JLabel();
        label_16.setText("Cadastro em:");
        label_16.setBounds(346, 5, 78, 14);
        pnCadastro.add(label_16);

        txDataCadastro = new JLabel();
        txDataCadastro.setBounds(434, 5, 73, 14);
        pnCadastro.add(txDataCadastro);

        btnNovo = new JButton();
        btnNovo.setFont(new Font("Dialog", Font.PLAIN, 10));
        btnNovo.setActionCommand("Novo");
        btnNovo.setName("Novo");
        btnNovo.setText("Novo");
        btnNovo.addActionListener(controller);
        btnNovo.setBounds(179, 321, 90, 23);
        pnCadastro.add(btnNovo);

        btnEditar = new JButton();
        btnEditar.setFont(new Font("Dialog", Font.PLAIN, 10));
        btnEditar.setActionCommand("Editar");
        btnEditar.setName("Editar");
        btnEditar.setText("Editar");
        btnEditar.addActionListener(controller);
        btnEditar.setBounds(275, 321, 90, 23);
        pnCadastro.add(btnEditar);

        btnSalvar = new JButton();
        btnSalvar.setFont(new Font("Dialog", Font.PLAIN, 10));
        btnSalvar.setActionCommand("Salvar");
        btnSalvar.setName("Salvar");
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(controller);
        btnSalvar.setBounds(371, 321, 90, 23);
        pnCadastro.add(btnSalvar);

        btnCancelar = new JButton();
        btnCancelar.setFont(new Font("Dialog", Font.PLAIN, 10));
        btnCancelar.setActionCommand("Cancelar");
        btnCancelar.setName("Cancelar");
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(controller);
        btnCancelar.setBounds(467, 321, 90, 23);
        pnCadastro.add(btnCancelar);

        lbAtendenteCad = new JLabel();
        lbAtendenteCad.setBounds(new Rectangle(0, 40, 0, 0));
        lbAtendenteCad.setText("Atendente:");
        lbAtendenteCad.setBounds(346, 30, 78, 17);
        pnCadastro.add(lbAtendenteCad);

        cbAtendenteCad = new DefaultComboBox();
        cbAtendenteCad.setBounds(new Rectangle(0, 40, 0, 0));
        cbAtendenteCad.setName("AtendenteCad");
        cbAtendenteCad.setBounds(437, 30, 115, 20);
        pnCadastro.add(cbAtendenteCad);

        pnPrivacidade = new JPanel();
        pnPrivacidade.setBackground((Color) null);
        pnPrivacidade.setBounds(601, 0, 159, 142);
        pnCadastro.add(pnPrivacidade);

        checkBox = new JCheckBox("Outros");
        checkBox.setBackground((Color) null);

        checkBox_1 = new JCheckBox("Eu");
        checkBox_1.setBackground((Color) null);

        checkBox_2 = new JCheckBox("Todos");
        checkBox_2.setBackground((Color) null);

        label_21 = new JLabel("Privacidade:");
        label_21.setHorizontalAlignment(SwingConstants.CENTER);
        GroupLayout gl_panel_3 = new GroupLayout(pnPrivacidade);
        gl_panel_3.setHorizontalGroup(
        	gl_panel_3.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 159, Short.MAX_VALUE)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
        				.addComponent(checkBox, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(checkBox_1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(checkBox_2, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(label_21, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel_3.setVerticalGroup(
        	gl_panel_3.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addGap(43)
        			.addComponent(label_21)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(checkBox_2)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(checkBox_1)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(checkBox)
        			.addContainerGap(195, Short.MAX_VALUE))
        );
        pnPrivacidade.setLayout(gl_panel_3);

        btnHistorico = new JButton();
        btnHistorico.setFont(new Font("Dialog", Font.PLAIN, 10));
        btnHistorico.setActionCommand("Historico");
        btnHistorico.setText("Historico");
        btnHistorico.setBounds(660, 321, 90, 23);
        btnHistorico.addActionListener(controller);
        pnCadastro.add(btnHistorico);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(107, 240, 268, 70);
        pnCadastro.add(scrollPane_2);

        txDescricao = new JTextArea();
        txDescricao.setWrapStyleWord(true);
        txDescricao.setLineWrap(true);
        scrollPane_2.setViewportView(txDescricao);

        txHonorario = new JFormattedTextField();
        txHonorario.setBounds(107, 209, 78, 20);
        pnCadastro.add(txHonorario);

        JLabel lblValor = new JLabel();
        lblValor.setText("Honor\u00E1rio:(R$)");
        lblValor.setBounds(8, 210, 87, 17);
        pnCadastro.add(lblValor);

        JLabel lblEmppessoa = new JLabel();
        lblEmppessoa.setFont(new Font("Tahoma", Font.PLAIN, 9));
        lblEmppessoa.setText("Empresa/Pessoa:");
        lblEmppessoa.setBounds(10, 58, 87, 17);
        pnCadastro.add(lblEmppessoa);

        btAddEmpresaPessoa = new JButton();
        btAddEmpresaPessoa.setActionCommand("VincularObjeto");
        btAddEmpresaPessoa.setText("...");
        btAddEmpresaPessoa.addActionListener(controller);
        btAddEmpresaPessoa.setBounds(203, 58, 36, 23);
        pnCadastro.add(btAddEmpresaPessoa);

        txCodObjeto = new JLabel("");
        txCodObjeto.setBackground(new Color(250,250,250));
        txCodObjeto.setBounds(246, 61, 29, 17);
        pnCadastro.add(txCodObjeto);

        JLabel lbFim = new JLabel();
        lbFim.setText("Concluir em:");
        lbFim.setHorizontalAlignment(SwingConstants.LEFT);
        lbFim.setBounds(8, 178, 87, 20);
        pnCadastro.add(lbFim);

        dataFim = new JDateChooser();
        dataFim.setBounds(107, 178, 98, 20);
        pnCadastro.add(dataFim);

        dataInicio = new JDateChooser();
        dataInicio.setBounds(107, 150, 98, 20);
        pnCadastro.add(dataInicio);

        JLabel lbInicio = new JLabel();
        lbInicio.setText("Data Inicio:");
        lbInicio.setHorizontalAlignment(SwingConstants.LEFT);
        lbInicio.setBounds(8, 150, 87, 20);
        pnCadastro.add(lbInicio);
        
        scrollServicos = new JScrollPane();
        scrollServicos.setBounds(395, 197, 365, 113);
        pnCadastro.add(scrollServicos);
        scrollServicos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollServicos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        tbServicosContratados = new JTable();
        scrollServicos.setViewportView(tbServicosContratados);

        txNomeObjeto = new JLabel();
        txNomeObjeto.setBackground(new Color(250,250,250));
        txNomeObjeto.setBounds(285, 61, 311, 17);
        pnCadastro.add(txNomeObjeto);

        btnExcluir = new JButton();
        btnExcluir.setFont(new Font("Dialog", Font.PLAIN, 10));
        btnExcluir.setActionCommand("Excluir");
        btnExcluir.setName("Excluir");
        btnExcluir.setText("Excluir");
        btnExcluir.setBounds(563, 321, 90, 23);
        btnExcluir.addActionListener(controller);
        pnCadastro.add(btnExcluir);

        cbObject = new JComboBox();
        cbObject.setFont(new Font("Tahoma", Font.PLAIN, 10));
        cbObject.setModel(new DefaultComboBoxModel(Modelos.values()));
        cbObject.setBounds(107, 58, 87, 20);
        cbObject.setName("Objeto");
        pnCadastro.add(cbObject);
        
        JLabel lbServicosCad = new JLabel();
        lbServicosCad.setText("Produtos/Servi\u00E7os:");
        lbServicosCad.setBounds(317, 119, 109, 17);
        pnCadastro.add(lbServicosCad);
        
        cbServicosCad = new DefaultComboBox();
        cbServicosCad.setName("ServicosCad");
        cbServicosCad.setBounds(438, 119, 116, 20);
        pnCadastro.add(cbServicosCad);
        
        JButton btnServicosCad = new JButton();
        btnServicosCad.setText("ADC");
        btnServicosCad.setActionCommand("CriarServico");
        btnServicosCad.setBounds(564, 116, 36, 23);
        btnServicosCad.addActionListener(controller);
        pnCadastro.add(btnServicosCad);
        
        JButton btnNivelCad = new JButton();
        btnNivelCad.setText("ADC");
        btnNivelCad.setActionCommand("CriarNivel");
        btnNivelCad.setBounds(564, 88, 36, 23);
        btnNivelCad.addActionListener(controller);
        pnCadastro.add(btnNivelCad);
        
        cbNivelCad = new DefaultComboBox();
        cbNivelCad.setName("NivelCad");
        cbNivelCad.setBounds(438, 89, 116, 20);
        pnCadastro.add(cbNivelCad);
        
        JLabel lbNivelCad = new JLabel();
        lbNivelCad.setText("Nivel:");
        lbNivelCad.setBounds(346, 89, 89, 18);
        pnCadastro.add(lbNivelCad);
        
        JButton btnOrigemCad = new JButton();
        btnOrigemCad.setText("ADC");
        btnOrigemCad.setActionCommand("CriarOrigem");
        btnOrigemCad.setBounds(203, 116, 36, 23);
        btnOrigemCad.addActionListener(controller);
        pnCadastro.add(btnOrigemCad);
        
        cbOrigemCad = new DefaultComboBox();
        cbOrigemCad.setName("OrigemCad");
        cbOrigemCad.setBounds(107, 119, 87, 20);
        pnCadastro.add(cbOrigemCad);
        
        JLabel lbOrigemCad = new JLabel();
        lbOrigemCad.setText("Origem:");
        lbOrigemCad.setBounds(10, 119, 87, 18);
        pnCadastro.add(lbOrigemCad);
        
        JLabel lbCategoriaCad = new JLabel();
        lbCategoriaCad.setText("Categoria:");
        lbCategoriaCad.setBounds(10, 89, 87, 18);
        pnCadastro.add(lbCategoriaCad);
        
        cbCategoriaCad = new DefaultComboBox();
        cbCategoriaCad.setName("CategoriaCad");
        cbCategoriaCad.setBounds(107, 89, 87, 20);
        pnCadastro.add(cbCategoriaCad);
        
        JButton btnCategoriaCad = new JButton();
        btnCategoriaCad.setText("ADC");
        btnCategoriaCad.setActionCommand("CriarCategoria");
        btnCategoriaCad.setBounds(203, 86, 36, 23);
        btnCategoriaCad.addActionListener(controller);
        pnCadastro.add(btnCategoriaCad);
        
        pnServicosContratados = new JPanel();
        pnServicosContratados.setBackground(new Color(250,250,250));
        pnServicosContratados.setBounds(395, 153, 365, 33);
        pnCadastro.add(pnServicosContratados);
        pnServicosContratados.setLayout(null);
        
        JLabel lblServio = new JLabel("Servi\u00E7o:");
        lblServio.setBounds(37, 9, 52, 14);
        pnServicosContratados.add(lblServio);
        
        cbServicosAgregados = new DefaultComboBox();
        cbServicosAgregados.setBounds(99, 6, 62, 20);
        cbServicosAgregados.setName("ServicoAgregadoCad");
        pnServicosContratados.add(cbServicosAgregados);
        
        JLabel lblValor_1 = new JLabel("Valor:");
        lblValor_1.setBounds(171, 9, 38, 14);
        pnServicosContratados.add(lblValor_1);
        
        txValorServico = new JFormattedTextField();
        txValorServico.setHorizontalAlignment(SwingConstants.CENTER);
        txValorServico.setBounds(212, 6, 41, 20);
        txValorServico.setText("0,00");
        pnServicosContratados.add(txValorServico);
        
        btAddServicoAgregado = new JButton("+");
        btAddServicoAgregado.setBounds(318, 5, 45, 23);
        pnServicosContratados.add(btAddServicoAgregado);
        btAddServicoAgregado.setActionCommand("AdicionarServicoAgregado"); 
        btAddServicoAgregado.addActionListener(controller);
        
        txIdServicoContratado = new JLabel("");
        txIdServicoContratado.setBounds(10, 9, 16, 14);
        pnServicosContratados.add(txIdServicoContratado);
        
        btReturn = new JButton("Novo");
        btReturn.setActionCommand("NovoServicoContratado");
        btReturn.addActionListener(controller);
        btReturn.setBounds(263, 5, 45, 23);
        pnServicosContratados.add(btReturn);
        
        JButton btTarefa = new JButton();
        btTarefa.setFont(new Font("Dialog", Font.PLAIN, 10));
        btTarefa.setBounds(60, 321, 115, 23);
        btTarefa.setText("Nova Tarefa");
        btTarefa.setName("Nova Tarefa");
        btTarefa.setActionCommand("Nova Tarefa");
        btTarefa.addActionListener(controller);
        pnCadastro.add(btTarefa);
        
        JLabel lbBuscar = new JLabel("Buscar");
        lbBuscar.setBounds(10, 65, 53, 14);
        pnVisao.add(lbBuscar);

        txBuscar = new JTextField();
        txBuscar.setColumns(10);
        txBuscar.setBounds(74, 62, 139, 20);
        pnVisao.add(txBuscar);

        JScrollPane scrollPrincipal = new JScrollPane();
        scrollPrincipal.setBounds(10, 90, 760, 142);
        pnVisao.add(scrollPrincipal);

        tbPrincipal = new JTable();
        scrollPrincipal.setViewportView(tbPrincipal);

        pnAndamento = new JPanel();
        pnAndamento.setBackground(new Color(250,250,250));
        pnAndamento.setBounds(10, 235, 760, 30);
        pnVisao.add(pnAndamento);

        rbContato = new JRadioButton("Contato");
        rbContato.setOpaque(false);
        pnAndamento.add(rbContato);

        rbEnvioProposta = new JRadioButton("Envio de Proposta");
        rbEnvioProposta.setOpaque(false);
        pnAndamento.add(rbEnvioProposta);

        rbFollowup = new JRadioButton("Follow-up");
        rbFollowup.setOpaque(false);
        pnAndamento.add(rbFollowup);

        rbFechamento = new JRadioButton("Fechamento");
        rbFechamento.setOpaque(false);
        pnAndamento.add(rbFechamento);
        
        rbIndefinida = new JRadioButton("Indefinida");
        rbIndefinida.setOpaque(false);
        pnAndamento.add(rbIndefinida);

        JLabel lblStatus = new JLabel();
        pnAndamento.add(lblStatus);
        lblStatus.setText("Status:");

        cbStatusCad = new DefaultComboBox();
        cbStatusCad.setName("StatusCad");
        pnAndamento.add(cbStatusCad);
        cbStatusCad.setModel(new DefaultComboBoxModel(new String[] {"Em Andamento", "Ganho", "Perdido"}));

        panel_5 = new JPanel();
        panel_5.setBackground(new Color(250,250,250));
        panel_5.setBounds(980, 90, 260, 142);
        pnVisao.add(panel_5);
        
        lblValorTotalDe = new JLabel("Valor total dos seus neg\u00F3cios");
        lblValorTotalDe.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblR = new JLabel("R$1.000,00");
        lblR.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNegcios = new JLabel("[#] Neg\u00F3cios");
        lblNegcios.setHorizontalAlignment(SwingConstants.CENTER);
        GroupLayout gl_panel_5 = new GroupLayout(panel_5);
        gl_panel_5.setHorizontalGroup(
        	gl_panel_5.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_5.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblR, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblValorTotalDe, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        				.addComponent(lblNegcios, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        gl_panel_5.setVerticalGroup(
        	gl_panel_5.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_5.createSequentialGroup()
        			.addGap(8)
        			.addComponent(lblValorTotalDe, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(lblR, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(lblNegcios, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_5.setLayout(gl_panel_5);
        
        txContadorRegistros = new JLabel("");
        txContadorRegistros.setBounds(780, 218, 152, 14);
        pnVisao.add(txContadorRegistros);
        
        ButtonGroup groupEtapas = new ButtonGroup();
        groupEtapas.add(rbContato);
        groupEtapas.add(rbEnvioProposta);
        groupEtapas.add(rbFollowup);
        groupEtapas.add(rbFechamento);
        groupEtapas.add(rbIndefinida);
        pack();
        setBounds(0, 0, 1250, 660);
    }
}
