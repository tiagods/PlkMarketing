package br.com.tiagods.view;

import java.awt.Color;
import java.awt.EventQueue;

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

public class NegociosView extends JInternalFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2406280053484370906L;
	public static DefaultComboBox cbAtendente;
    public static DefaultComboBox cbStatus;
    public static DefaultComboBox cbEtapa;
    public static DefaultComboBox cbEmpresa;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel pnPrincipal;
	private JPanel panel;
	private JLabel label;
	public static JDateChooser data2;
	private JLabel label_1;
	public static JDateChooser data1;
	public static JButton button_1;
	public static DefaultComboBox cbOrigem;
	public static JPanel pnAuxiliar;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JLabel label_2;
	private JButton button;
	private JButton button_2;
	private JButton button_3;
	private JScrollPane scrollPane_1;
	public static JPanel pnCadastro;
	private JLabel label_3;
	private JLabel label_7;
	private JLabel lblDescrio;
	private JTextField textField_5;
	private JTextField textField_6;
	private JLabel txCadastradoPor;
	private JLabel label_16;
	private JLabel txDataCadastro;
	public static JButton btNovo;
	public static JButton btEditar;
	public static JButton btnSalvar;
	public static JButton btnCancelar;
	private JLabel label_20;
	public static DefaultComboBox cbAtendenteCad;
	private JPanel pnPrivacidade;
	public static JCheckBox checkBox;
	public static JCheckBox checkBox_1;
	public static JCheckBox checkBox_2;
	private JLabel label_21;
	public static JButton btnHistorico;
	public static JLabel txCod;
	private JTextArea textArea_1;
	private JTable table;
	private JTextField textField_2;
	public static JTable tbNegocios;
	public static JButton btAddServicos;
	private JPanel panel_5;
	private JLabel lblValorTotalDe;

	ControllerNegocios controller = new ControllerNegocios();
	/**
	 * Create the frame.
	 */
	public NegociosView(Negocio negocio) {
		initComponents();
		controller.iniciar(negocio);
		pnPrivacidade.setVisible(false);
		pnAuxiliar.setVisible(false);
		JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, "Essa tela ainda não esta pronta! Modo somente leitura");
	}
	private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        pnPrincipal = new javax.swing.JPanel();
        pnPrincipal.setBounds(0, 0, 1240, 69);
        cbAtendente = new DefaultComboBox();
        cbStatus = new DefaultComboBox();
        cbEtapa = new DefaultComboBox();
        cbEmpresa = new DefaultComboBox();
        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        
        pnPrincipal.setBackground(new java.awt.Color(250, 250, 250));

        cbAtendente.setBackground(new java.awt.Color(250, 250, 250));
        cbAtendente.setName("Atendente");
        
        cbStatus.setBackground(new java.awt.Color(250, 250, 250));
        cbStatus.setModel(new DefaultComboBoxModel(new String[] {"Status", "Em Andamento", "Ganho", "Perdido"}));
        cbStatus.setName("Status");
        cbEtapa.setBackground(new java.awt.Color(250, 250, 250));
        cbEtapa.setModel(new DefaultComboBoxModel(new String[] {"Etapa", "Indefinida", "Contato", "Envio de Proposta", "Follow-up", "Fechamento"}));

        cbEmpresa.setBackground(new java.awt.Color(250, 250, 250));
        cbEmpresa.setModel(new DefaultComboBoxModel(new String[] {"Empresa", "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos"}));
        
        DefaultComboBox cbPessoa = new DefaultComboBox();
        cbPessoa.setModel(new DefaultComboBoxModel(new String[] {"Pessoa", "Todos"}));
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
        
        button_1 = new JButton();
        button_1.setBounds(142, 37, 51, 20);
        button_1.setText("OK");
        
        cbOrigem = new DefaultComboBox();
        cbOrigem.setModel(new DefaultComboBoxModel(new String[] {"Meio"}));
        cbOrigem.setBackground(new Color(250, 250, 250));
        
        javax.swing.GroupLayout gl_pnPrincipal = new javax.swing.GroupLayout(pnPrincipal);
        gl_pnPrincipal.setHorizontalGroup(
        	gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbEtapa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbPessoa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addGap(37)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(138, Short.MAX_VALUE))
        );
        gl_pnPrincipal.setVerticalGroup(
        	gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addGroup(gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_pnPrincipal.createSequentialGroup()
        					.addGap(23)
        					.addGroup(gl_pnPrincipal.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbEtapa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbPessoa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(null);
        panel.add(label_1);
        panel.add(label);
        panel.add(data2);
        panel.add(data1);
        panel.add(button_1);
        pnPrincipal.setLayout(gl_pnPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1.setLayout(null);
        jPanel1.add(pnPrincipal);
        
        pnAuxiliar = new JPanel();
        pnAuxiliar.setBackground(new Color(250, 250, 250));
        pnAuxiliar.setBounds(780, 289, 460, 334);
        jPanel1.add(pnAuxiliar);
        
        scrollPane = new JScrollPane();
        
        textField = new JTextField();
        textField.setColumns(10);
        
        label_2 = new JLabel("Historico");
        
        button = new JButton("Esconder");
        
        button_2 = new JButton("Novo");
        
        button_3 = new JButton("Alterar");
        
        scrollPane_1 = new JScrollPane();
        GroupLayout gl_pnAuxiliar = new GroupLayout(pnAuxiliar);
        gl_pnAuxiliar.setHorizontalGroup(
        	gl_pnAuxiliar.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 284, Short.MAX_VALUE)
        		.addGroup(gl_pnAuxiliar.createSequentialGroup()
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.LEADING)
        				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        				.addGroup(gl_pnAuxiliar.createSequentialGroup()
        					.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.LEADING)
        						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
        					.addComponent(button))
        				.addGroup(gl_pnAuxiliar.createSequentialGroup()
        					.addContainerGap(138, Short.MAX_VALUE)
        					.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(button_3))
        				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_pnAuxiliar.setVerticalGroup(
        	gl_pnAuxiliar.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_pnAuxiliar.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label_2)
        				.addComponent(button))
        			.addGap(4)
        			.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(7)
        			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(13)
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.BASELINE)
        				.addComponent(button_2)
        				.addComponent(button_3))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        textArea_1 = new JTextArea();
        scrollPane_1.setViewportView(textArea_1);
        pnAuxiliar.setLayout(gl_pnAuxiliar);
        
        pnCadastro = new JPanel();
        pnCadastro.setLayout(null);
        pnCadastro.setBackground((Color) null);
        pnCadastro.setBounds(10, 289, 760, 334);
        jPanel1.add(pnCadastro);
        
        label_3 = new JLabel();
        label_3.setText("{COD###}");
        label_3.setBounds(10, 14, 56, 14);
        pnCadastro.add(label_3);
        
        label_7 = new JLabel();
        label_7.setText("Nome:");
        label_7.setBounds(10, 40, 56, 17);
        pnCadastro.add(label_7);
        
        lblDescrio = new JLabel();
        lblDescrio.setText("Descri\u00E7\u00E3o:");
        lblDescrio.setBounds(10, 193, 56, 14);
        pnCadastro.add(lblDescrio);
        
        textField_5 = new JTextField();
        textField_5.setBounds(107, 11, 87, 20);
        pnCadastro.add(textField_5);
        
        textField_6 = new JTextField();
        textField_6.setBounds(107, 39, 201, 20);
        pnCadastro.add(textField_6);
        
        txCadastradoPor = new JLabel();
        txCadastradoPor.setText("{Usuario}");
        txCadastradoPor.setBounds(546, 14, 56, 14);
        pnCadastro.add(txCadastradoPor);
        
        label_16 = new JLabel();
        label_16.setText("Cadastro em:");
        label_16.setBounds(385, 14, 78, 14);
        pnCadastro.add(label_16);
        
        txDataCadastro = new JLabel();
        txDataCadastro.setText("{Date###}");
        txDataCadastro.setBounds(463, 14, 73, 14);
        pnCadastro.add(txDataCadastro);
        
        btNovo = new JButton();
        btNovo.setText("Novo");
        btNovo.setBounds(107, 278, 90, 23);
        pnCadastro.add(btNovo);
        
        btEditar = new JButton();
        btEditar.setText("Editar");
        btEditar.setBounds(203, 278, 90, 23);
        pnCadastro.add(btEditar);
        
        btnSalvar = new JButton();
        btnSalvar.setText("Salvar");
        btnSalvar.setBounds(299, 278, 90, 23);
        pnCadastro.add(btnSalvar);
        
        btnCancelar = new JButton();
        btnCancelar.setText("Cancelar");
        btnCancelar.setBounds(395, 278, 90, 23);
        pnCadastro.add(btnCancelar);
        
        label_20 = new JLabel();
        label_20.setText("Atendente:");
        label_20.setBounds(385, 42, 78, 17);
        pnCadastro.add(label_20);
        
        cbAtendenteCad = new DefaultComboBox();
        cbAtendenteCad.setBounds(476, 40, 115, 20);
        pnCadastro.add(cbAtendenteCad);
        
        pnPrivacidade = new JPanel();
        pnPrivacidade.setBackground((Color) null);
        pnPrivacidade.setBounds(601, 0, 159, 334);
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
        btnHistorico.setText("Historico");
        btnHistorico.setBounds(203, 311, 90, 23);
        pnCadastro.add(btnHistorico);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(107, 187, 268, 80);
        pnCadastro.add(scrollPane_2);
        
        JTextArea txDescricao = new JTextArea();
        scrollPane_2.setViewportView(txDescricao);
        
        JFormattedTextField txHonorario = new JFormattedTextField();
        txHonorario.setBounds(107, 156, 78, 20);
        pnCadastro.add(txHonorario);
        
        JLabel lblValor = new JLabel();
        lblValor.setText("Valor Honor\u00E1rio:");
        lblValor.setBounds(10, 159, 87, 17);
        pnCadastro.add(lblValor);
        
        JLabel lblEmppessoa = new JLabel();
        lblEmppessoa.setText("Empresa/Pessoa:");
        lblEmppessoa.setBounds(10, 68, 87, 17);
        pnCadastro.add(lblEmppessoa);
        
        JButton btAddEmpresaPessoa = new JButton();
        btAddEmpresaPessoa.setBounds(190, 66, 33, 23);
        pnCadastro.add(btAddEmpresaPessoa);
        
        txCod = new JLabel("{COD}");
        txCod.setBounds(239, 69, 36, 17);
        pnCadastro.add(txCod);
        
        JLabel lblDataDeInicio = new JLabel();
        lblDataDeInicio.setText("Data de Inicio:");
        lblDataDeInicio.setHorizontalAlignment(SwingConstants.LEFT);
        lblDataDeInicio.setBounds(10, 96, 87, 20);
        pnCadastro.add(lblDataDeInicio);
        
        JDateChooser dataInicio = new JDateChooser();
        dataInicio.setBounds(107, 96, 100, 20);
        pnCadastro.add(dataInicio);
        
        JDateChooser dataFim = new JDateChooser();
        dataFim.setBounds(107, 125, 100, 20);
        pnCadastro.add(dataFim);
        
        JLabel lblDataDeConcluso = new JLabel();
        lblDataDeConcluso.setText("Data Conclus\u00E3o:");
        lblDataDeConcluso.setHorizontalAlignment(SwingConstants.LEFT);
        lblDataDeConcluso.setBounds(10, 125, 87, 20);
        pnCadastro.add(lblDataDeConcluso);
        
        JLabel lblProdutosserviosRelacionados = new JLabel();
        lblProdutosserviosRelacionados.setText("Produtos/Servi\u00E7os Relacionados:");
        lblProdutosserviosRelacionados.setBounds(385, 96, 193, 17);
        pnCadastro.add(lblProdutosserviosRelacionados);
        
        JButton btnAddProdServicos = new JButton();
        btnAddProdServicos.setText("ADC");
        btnAddProdServicos.setBounds(555, 123, 36, 23);
        pnCadastro.add(btnAddProdServicos);
        
        JComboBox cbProdServicos = new JComboBox();
        cbProdServicos.setBounds(385, 124, 160, 20);
        pnCadastro.add(cbProdServicos);
        
        JLabel lblIncluirValores = new JLabel();
        lblIncluirValores.setText("Incluir Valores:");
        lblIncluirValores.setBounds(243, 159, 87, 17);
        pnCadastro.add(lblIncluirValores);
        
        btAddServicos = new JButton();
        btAddServicos.setText("ADC");
        btAddServicos.setBounds(340, 155, 36, 23);
        pnCadastro.add(btAddServicos);
        
        JPanel panel_6 = new JPanel();
        panel_6.setBounds(385, 156, 206, 111);
        pnCadastro.add(panel_6);
        
        JScrollPane scrollServicos = new JScrollPane();
        scrollServicos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollServicos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        GroupLayout gl_panel_6 = new GroupLayout(panel_6);
        gl_panel_6.setHorizontalGroup(
        	gl_panel_6.createParallelGroup(Alignment.LEADING)
        		.addComponent(scrollServicos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );
        gl_panel_6.setVerticalGroup(
        	gl_panel_6.createParallelGroup(Alignment.LEADING)
        		.addComponent(scrollServicos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );
        panel_6.setLayout(gl_panel_6);
        
        JLabel lbOrigem = new JLabel();
        lbOrigem.setText("Origem:");
        lbOrigem.setBounds(385, 68, 78, 17);
        pnCadastro.add(lbOrigem);
        
        DefaultComboBox cbOrigemCad = new DefaultComboBox();
        cbOrigemCad.setBounds(476, 66, 73, 20);
        pnCadastro.add(cbOrigemCad);
        
        JButton btAddOrigem = new JButton();
        btAddOrigem.setText("ADC");
        btAddOrigem.setBounds(555, 65, 36, 23);
        pnCadastro.add(btAddOrigem);
        
        JLabel label_5 = new JLabel();
        label_5.setBounds(203, 68, 105, 17);
        pnCadastro.add(label_5);
        
        JButton btnExcluir = new JButton();
        btnExcluir.setText("Excluir");
        btnExcluir.setBounds(495, 278, 90, 23);
        pnCadastro.add(btnExcluir);
        
        DefaultComboBox defaultComboBox = new DefaultComboBox();
        defaultComboBox.setModel(new DefaultComboBoxModel(new String[] {"Empresa", "Pessoa"}));
        defaultComboBox.setBounds(107, 66, 73, 20);
        pnCadastro.add(defaultComboBox);
        
        DefaultComboBox comboBox_3 = new DefaultComboBox();
        comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"Status", "Etapa", "Alfabetica", "Data Cadastro", "Data Atualiza\u00E7\u00E3o", "Data Conclus\u00E3o", "Valor"}));
        comboBox_3.setBounds(780, 107, 110, 20);
        jPanel1.add(comboBox_3);
        
        JLabel label_4 = new JLabel("Buscar");
        label_4.setBounds(10, 83, 53, 14);
        jPanel1.add(label_4);
        
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(74, 80, 139, 20);
        jPanel1.add(textField_2);
        
        JScrollPane scrollPrincipal = new JScrollPane();
        scrollPrincipal.setBounds(10, 107, 760, 142);
        jPanel1.add(scrollPrincipal);
        
        tbNegocios = new JTable();
        scrollPrincipal.setViewportView(tbNegocios);
        
        JRadioButton radioButton = new JRadioButton();
        radioButton.setText("Crescente");
        radioButton.setBackground(new Color(250, 250, 250));
        radioButton.setBounds(780, 134, 110, 23);
        jPanel1.add(radioButton);
        
        JRadioButton radioButton_1 = new JRadioButton();
        radioButton_1.setText("Decrescente");
        radioButton_1.setBackground(new Color(250, 250, 250));
        radioButton_1.setBounds(780, 164, 110, 23);
        jPanel1.add(radioButton_1);
        
        JPanel pnAndamento = new JPanel();
        pnAndamento.setBounds(11, 253, 600, 30);
        jPanel1.add(pnAndamento);
        
        JRadioButton rbContato = new JRadioButton("Contato");
        pnAndamento.add(rbContato);
        
        JRadioButton rbEnvioProposta = new JRadioButton("Envio de Proposta");
        pnAndamento.add(rbEnvioProposta);
        
        JRadioButton rbFollowup = new JRadioButton("Follow-up");
        pnAndamento.add(rbFollowup);
        
        JRadioButton rbFechamento = new JRadioButton("Fechamento");
        pnAndamento.add(rbFechamento);
        
        JLabel lblStatus = new JLabel();
        pnAndamento.add(lblStatus);
        lblStatus.setText("Status:");
        
        DefaultComboBox cbStatusCad = new DefaultComboBox();
        pnAndamento.add(cbStatusCad);
        cbStatusCad.setModel(new DefaultComboBoxModel(new String[] {"Em Andamento", "Ganho", "Perdido"}));
        
        panel_5 = new JPanel();
        panel_5.setBounds(980, 107, 260, 142);
        jPanel1.add(panel_5);
        
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

        setBounds(0, 0, 1250, 660);
    }
}
