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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.view.interfaces.DefaultUtilities;
import br.com.tiagods.view.interfaces.DefaultModelComboBox.Logradouro;
import br.com.tiagods.view.interfaces.DefaultModelComboBox.Estados;

public class EmpresasView extends JInternalFrame implements DefaultUtilities{
	private javax.swing.ButtonGroup group_situacao;
    public static javax.swing.JButton btHistorico;
    public static javax.swing.JButton btNegocios;
    public static javax.swing.JButton btPessoas;
    public static javax.swing.JButton btEditar;
    public static javax.swing.JButton btSalvar;
    public static javax.swing.JComboBox<String> cbNivelCad;
    public static javax.swing.JComboBox<String> cbMeioCad;
    public static javax.swing.JComboBox<String> cbAtendente;
    public static javax.swing.JComboBox<String> cbLogradouro;
    public static javax.swing.JComboBox<String> cbEstado;
    public static javax.swing.JComboBox<String> cbCategoria;
    public static javax.swing.JComboBox<String> cbMeio;
    public static javax.swing.JComboBox<String> cbProdServicos;
    public static javax.swing.JFormattedTextField txTelefone;
    public static javax.swing.JFormattedTextField txCelular;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbCod;
    private javax.swing.JLabel lnNum;
    private javax.swing.JLabel lbComplemento;
    private javax.swing.JLabel lbBairro;
    public static javax.swing.JLabel lbCadatroEm;
    public static javax.swing.JLabel txDataCadastro;
    public static javax.swing.JLabel txQuemCadastrou;
    private javax.swing.JLabel lbCnpj;
    private javax.swing.JLabel lbNivel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbTelefone;
    private javax.swing.JLabel lbCelular;
    private javax.swing.JLabel lbCidade;
    private javax.swing.JLabel lbEstado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;;
    public static javax.swing.JTextField txCod;
    public static javax.swing.JTextField txNome;
    public static javax.swing.JTextField txCnpj;
    public static javax.swing.JTextField txBairro;
    public static javax.swing.JTextField txLogradouro;
    public static javax.swing.JTextField txComplemento;
    public static javax.swing.JTextField txNum;
    public static JButton btExcluir;
    public static JButton btNovo;
    public static JTextField txEmail;
    private JLabel lbEmail;
    public static JTextField txSite;
    private JLabel lbSite;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel label;
	public static JDateChooser dateChooser;
	private JLabel label_1;
	public static JDateChooser dateChooser_1;
	public static JButton button_1;
	public static JComboBox<String> cbNivel;
	private JLabel lbAtendente;
	public static JComboBox<String> cbAtendenteCad;
	private JLabel lbCep;
	public static JTextField txtBusca;
	public static JPanel pnAuxiliar;
	public static JPanel pnPrivacidade;
	private JScrollPane scrollPane;
	public static JTextField textField_1;
	private JLabel label_3;
	public static JButton button_2;
	public static JButton button_3;
	public static JButton button_4;
	private JScrollPane scrollPane_1;
	public static JTextArea textArea;
	public static JTable tbAuxiliar;
	public static JTable tbPrincipal;
	public static JButton btAddMeio;
	public static JButton btAddNivel;
	private JLabel lbCategoria;
	public static JComboBox<String> cbCategoriaCad;
	public static JButton btCategoriaAdd;
	private JLabel lbProdServicos;
	public static JComboBox cbProdServicosCad;
	public static JButton btnAddProdServicos;

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return DefaultUtilities.super.getColor();
	}
	@Override
	public Color getPanelColor() {
		// TODO Auto-generated method stub
		return DefaultUtilities.super.getPanelColor();
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpresasView frame = new EmpresasView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmpresasView() {
		initComponents();
		pnAuxiliar.setVisible(false);
		pnPrivacidade.setVisible(false);
	}
    private void initComponents() {
    	group_situacao = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel3.setBounds(0, 0, 1240, 69);
        cbAtendente = new javax.swing.JComboBox<>();
        cbCategoria = new javax.swing.JComboBox<>();
        cbMeio = new javax.swing.JComboBox<>();
        cbProdServicos = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel4.setBounds(10, 260, 760, 363);
        txCod = new javax.swing.JTextField();
        txCod.setBounds(107, 11, 87, 20);
        txQuemCadastrou = new javax.swing.JLabel();
        txQuemCadastrou.setBounds(546, 14, 56, 14);
        lbCadatroEm = new javax.swing.JLabel();
        lbCadatroEm.setBounds(385, 14, 78, 14);
        txDataCadastro = new javax.swing.JLabel();
        txDataCadastro.setBounds(463, 14, 73, 14);
        lbCod = new javax.swing.JLabel();
        lbCod.setBounds(10, 14, 56, 14);
        txNome = new javax.swing.JTextField();
        txNome.setBounds(107, 39, 201, 20);
        lbNome = new javax.swing.JLabel();
        lbNome.setBounds(10, 40, 56, 17);
        txCnpj = new javax.swing.JTextField();
        txCnpj.setBounds(107, 68, 109, 20);
        lbCnpj = new javax.swing.JLabel();
        lbCnpj.setBounds(10, 71, 56, 14);
        cbNivelCad = new javax.swing.JComboBox<>();
        cbNivelCad.setBounds(107, 96, 87, 20);
        lbNivel = new javax.swing.JLabel();
        lbNivel.setBounds(10, 96, 56, 18);
        cbMeioCad = new javax.swing.JComboBox<>();
        cbMeioCad.setBounds(438, 96, 92, 20);
        jLabel4 = new javax.swing.JLabel();
        jLabel4.setBounds(385, 98, 35, 14);
        txTelefone = new javax.swing.JFormattedTextField();
        txTelefone.setBounds(107, 154, 87, 20);
        lbTelefone = new javax.swing.JLabel();
        lbTelefone.setBounds(10, 154, 56, 20);
        txCelular = new javax.swing.JFormattedTextField();
        txCelular.setBounds(440, 154, 95, 20);
        lbCelular = new javax.swing.JLabel();
        lbCelular.setBounds(385, 154, 51, 19);
        txLogradouro = new javax.swing.JTextField();
        txLogradouro.setBounds(107, 214, 201, 20);
        cbLogradouro = new javax.swing.JComboBox<>();
        cbLogradouro.setBounds(10, 214, 92, 20);
        txNum = new javax.swing.JTextField();
        txNum.setBounds(440, 213, 35, 20);
        lnNum = new javax.swing.JLabel();
        lnNum.setBounds(385, 213, 51, 20);
        txComplemento = new javax.swing.JTextField();
        txComplemento.setBounds(252, 245, 56, 20);
        lbComplemento = new javax.swing.JLabel();
        lbComplemento.setBounds(203, 245, 43, 20);
        txBairro = new javax.swing.JTextField();
        txBairro.setBounds(440, 244, 90, 20);
        lbBairro = new javax.swing.JLabel();
        lbBairro.setBounds(385, 244, 43, 20);
        cbEstado = new javax.swing.JComboBox<>();
        cbEstado.setBounds(440, 275, 52, 20);
        lbEstado = new javax.swing.JLabel();
        lbEstado.setBounds(384, 275, 52, 19);
        lbCidade = new javax.swing.JLabel();
        lbCidade.setBounds(10, 278, 56, 17);
        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        jPanel3.setBackground(getColor());

        cbAtendente.setBackground(getPanelColor());
        cbAtendente.setModel(new DefaultComboBoxModel(new String[] {"Atendente", "Todos"}));
        
        cbCategoria.setBackground(getPanelColor());
        cbCategoria.setModel(new DefaultComboBoxModel(new String[] {"Categoria"}));

        cbMeio.setBackground(getPanelColor());
        cbMeio.setModel(new DefaultComboBoxModel(new String[] {"Meio"}));

        cbProdServicos.setBackground(getPanelColor());
        cbProdServicos.setModel(new DefaultComboBoxModel(new String[] {"Produto/Servi\u00E7os"}));
        
        JComboBox<String> cbPessoas = new JComboBox<String>();
        cbPessoas.setModel(new DefaultComboBoxModel(new String[] {"Pessoa", "Todos"}));
        cbPessoas.setBackground(getPanelColor());
        
        panel = new JPanel();
        panel.setBackground(getColor());
        
        label = new JLabel();
        label.setBounds(10, 37, 22, 20);
        label.setText("at\u00E9");
        label.setHorizontalAlignment(SwingConstants.LEFT);
        
        dateChooser = new JDateChooser();
        dateChooser.setBounds(36, 37, 100, 20);
        
        label_1 = new JLabel();
        label_1.setHorizontalAlignment(SwingConstants.LEFT);
        label_1.setBounds(10, 11, 22, 20);
        label_1.setText("de:");
        
        dateChooser_1 = new JDateChooser();
        dateChooser_1.setBounds(36, 11, 100, 20);
        
        button_1 = new JButton();
        button_1.setBounds(142, 37, 51, 20);
        button_1.setText("OK");
        
        cbNivel = new JComboBox<String>();
        cbNivel.setModel(new DefaultComboBoxModel(new String[] {"Nivel"}));
        cbNivel.setBackground(getPanelColor());
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3Layout.setHorizontalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbMeio, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(cbProdServicos, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbPessoas, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addGap(37)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(138, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        				.addGroup(jPanel3Layout.createSequentialGroup()
        					.addGap(23)
        					.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbMeio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbProdServicos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbPessoas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(null);
        panel.add(label_1);
        panel.add(label);
        panel.add(dateChooser);
        panel.add(dateChooser_1);
        panel.add(button_1);
        jPanel3.setLayout(jPanel3Layout);

        jPanel4.setBackground(getPanelColor());

        txQuemCadastrou.setText("{Usuario}");

        lbCadatroEm.setText("Cadastro em:");

        txDataCadastro.setText("{Date###}");

        lbCod.setText("{COD###}");

        lbNome.setText("Nome:");

        txCnpj.setText("00.000.000/000-91");

        lbCnpj.setText("CNPJ:");

        cbNivelCad.setModel(new DefaultComboBoxModel(new String[] {"Ouro", "Prata", "Diamante"}));

        lbNivel.setText("Nivel:");

        cbMeioCad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Meio");

        txTelefone.setText("(99)3204-0000");

        lbTelefone.setText("Telefone:");

        txCelular.setText("(00)99999-0000");

        lbCelular.setText("Celular");

        cbLogradouro.setModel(new DefaultComboBoxModel(Logradouro.values()));

        lnNum.setText("Nº:");

        lbComplemento.setText("Compl:");

        lbBairro.setText("Bairro:");

        cbEstado.setModel(new DefaultComboBoxModel(Estados.values()));

        lbEstado.setText("Estado:");

        lbCidade.setText("Cidade:");
        btEditar = new javax.swing.JButton();
        btEditar.setBounds(203, 307, 90, 23);
        
        btEditar.setText("Editar");
        btSalvar = new javax.swing.JButton();
        btSalvar.setBounds(299, 307, 90, 23);
        
        btSalvar.setText("Salvar");
        
        btExcluir = new JButton();
        btExcluir.setBounds(395, 307, 90, 23);
        btExcluir.setText("Excluir");
        
        btNovo = new JButton();
        btNovo.setBounds(107, 307, 90, 23);
        btNovo.setText("Novo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addComponent(jPanel1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1250, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addComponent(jPanel1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
        );
        getContentPane().setLayout(layout);
        jPanel1.setLayout(null);
        jPanel1.add(jPanel3);
        jPanel1.add(jPanel4);
        jPanel4.setLayout(null);
        jPanel4.add(lbCod);
        jPanel4.add(lbCidade);
        jPanel4.add(lbNivel);
        jPanel4.add(lbTelefone);
        jPanel4.add(cbLogradouro);
        jPanel4.add(lbNome);
        jPanel4.add(lbCnpj);
        jPanel4.add(lbComplemento);
        jPanel4.add(cbNivelCad);
        jPanel4.add(jLabel4);
        jPanel4.add(cbMeioCad);
        jPanel4.add(txLogradouro);
        jPanel4.add(txComplemento);
        jPanel4.add(txCod);
        jPanel4.add(txNome);
        jPanel4.add(txTelefone);
        jPanel4.add(lbEstado);
        jPanel4.add(cbEstado);
        jPanel4.add(lnNum);
        jPanel4.add(lbCelular);
        jPanel4.add(lbBairro);
        jPanel4.add(txCelular);
        jPanel4.add(txNum);
        jPanel4.add(txBairro);
        jPanel4.add(txQuemCadastrou);
        jPanel4.add(lbCadatroEm);
        jPanel4.add(txDataCadastro);
        jPanel4.add(txCnpj);
        jPanel4.add(btNovo);
        jPanel4.add(btEditar);
        jPanel4.add(btSalvar);
        jPanel4.add(btExcluir);
        
        txEmail = new JTextField();
        txEmail.setBounds(107, 183, 201, 20);
        jPanel4.add(txEmail);
        
        lbEmail = new JLabel();
        lbEmail.setText("E-mail");
        lbEmail.setBounds(10, 183, 56, 20);
        jPanel4.add(lbEmail);
        
        txSite = new JTextField();
        txSite.setBounds(440, 184, 126, 20);
        jPanel4.add(txSite);
        
        lbSite = new JLabel();
        lbSite.setText("Site");
        lbSite.setBounds(385, 184, 43, 20);
        jPanel4.add(lbSite);
        
        lbAtendente = new JLabel();
        lbAtendente.setText("Atendente:");
        lbAtendente.setBounds(385, 42, 87, 17);
        jPanel4.add(lbAtendente);
        
        cbAtendenteCad = new JComboBox<String>();
        cbAtendenteCad.setBounds(476, 40, 92, 20);
        jPanel4.add(cbAtendenteCad);
        
        pnPrivacidade = new JPanel();
        pnPrivacidade.setBackground(getColor());
        pnPrivacidade.setBounds(601, 0, 159, 363);
        jPanel4.add(pnPrivacidade);
        
        JLabel lblNewLabel = new JLabel("Privacidade:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JCheckBox chckbxTodos = new JCheckBox("Todos");
        chckbxTodos.setBackground(getPanelColor());
        
        JCheckBox chckbxEu = new JCheckBox("Eu");
        chckbxEu.setBackground(getPanelColor());
        
        JCheckBox chckbxOutros = new JCheckBox("Outros");
        chckbxOutros.setBackground(getPanelColor());
        GroupLayout gl_pnPrivacidade = new GroupLayout(pnPrivacidade);
        gl_pnPrivacidade.setHorizontalGroup(
        	gl_pnPrivacidade.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrivacidade.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnPrivacidade.createParallelGroup(Alignment.LEADING)
        				.addComponent(chckbxOutros, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        				.addComponent(chckbxEu, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        				.addComponent(chckbxTodos, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_pnPrivacidade.setVerticalGroup(
        	gl_pnPrivacidade.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrivacidade.createSequentialGroup()
        			.addGap(43)
        			.addComponent(lblNewLabel)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(chckbxTodos)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(chckbxEu)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(chckbxOutros)
        			.addContainerGap(162, Short.MAX_VALUE))
        );
        pnPrivacidade.setLayout(gl_pnPrivacidade);
        btHistorico = new JButton();
        btHistorico.setBounds(203, 340, 90, 23);
        jPanel4.add(btHistorico);
        
        btHistorico.setText("Historico");
        btNegocios = new javax.swing.JButton();
        btNegocios.setBounds(299, 340, 90, 23);
        jPanel4.add(btNegocios);
        btNegocios.setText("Neg\u00F3cios");
        btPessoas = new javax.swing.JButton();
        btPessoas.setBounds(396, 340, 87, 23);
        jPanel4.add(btPessoas);

        btPessoas.setText("Pessoas");

        lbCep = new JLabel();
        lbCep.setText("CEP:");
        lbCep.setBounds(10, 247, 35, 20);
        jPanel4.add(lbCep);

        JFormattedTextField txCep = new JFormattedTextField();
        txCep.setText("00000-000");
        txCep.setBounds(107, 245, 78, 20);
        jPanel4.add(txCep);
        
        btAddMeio = new JButton();
        btAddMeio.setText("ADC");
        btAddMeio.setBounds(540, 94, 36, 23);
        jPanel4.add(btAddMeio);
        
        btAddNivel = new JButton();
        btAddNivel.setText("ADC");
        btAddNivel.setBounds(203, 94, 36, 23);
        jPanel4.add(btAddNivel);
        
        lbCategoria = new JLabel();
        lbCategoria.setText("Categoria:");
        lbCategoria.setBounds(10, 126, 87, 18);
        jPanel4.add(lbCategoria);
        
        cbCategoriaCad = new JComboBox<String>();
        cbCategoriaCad.setBounds(107, 126, 87, 20);
        jPanel4.add(cbCategoriaCad);
        
        btCategoriaAdd = new JButton();
        btCategoriaAdd.setText("ADC");
        btCategoriaAdd.setBounds(203, 123, 36, 23);
        jPanel4.add(btCategoriaAdd);
        
        lbProdServicos = new JLabel();
        lbProdServicos.setText("Produtos/Servi\u00E7os:");
        lbProdServicos.setBounds(317, 126, 109, 17);
        jPanel4.add(lbProdServicos);
        
        cbProdServicosCad = new JComboBox();
        cbProdServicosCad.setBounds(438, 126, 116, 20);
        jPanel4.add(cbProdServicosCad);
        
        btnAddProdServicos = new JButton();
        btnAddProdServicos.setText("ADC");
        btnAddProdServicos.setBounds(564, 123, 36, 23);
        jPanel4.add(btnAddProdServicos);
        
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setBounds(107, 276, 87, 20);
        jPanel4.add(comboBox);
        
        JLabel lbBusca = new JLabel("Buscar");
        lbBusca.setBounds(10, 83, 53, 14);
        jPanel1.add(lbBusca);
        
        txtBusca = new JTextField();
        txtBusca.setColumns(10);
        txtBusca.setBounds(74, 80, 139, 20);
        jPanel1.add(txtBusca);
        
        JScrollPane scrollPrincipal = new JScrollPane();
        scrollPrincipal.setBounds(10, 107, 760, 142);
        jPanel1.add(scrollPrincipal);
        
        tbPrincipal = new JTable();
        scrollPrincipal.setViewportView(tbPrincipal);
        
        JRadioButton rbDecrescente = new JRadioButton();
        rbDecrescente.setText("Decrescente");
        rbDecrescente.setBackground(new Color(250, 250, 250));
        rbDecrescente.setBounds(780, 164, 110, 23);
        jPanel1.add(rbDecrescente);
        
        JRadioButton rbCrescente = new JRadioButton();
        rbCrescente.setText("Crescente");
        rbCrescente.setBackground(new Color(250, 250, 250));
        rbCrescente.setBounds(780, 134, 110, 23);
        jPanel1.add(rbCrescente);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbCrescente);
        group.add(rbDecrescente);
        
        JComboBox<String> cbOrganizacao = new JComboBox<String>();
        cbOrganizacao.setModel(new DefaultComboBoxModel(new String[] {"Ordem Alfab\u00E9tica", "Data de Cadastro", "Atualiza\u00E7\u00E3o"}));
        cbOrganizacao.setBounds(780, 107, 110, 20);
        jPanel1.add(cbOrganizacao);
        
        pnAuxiliar = new JPanel();
        pnAuxiliar.setBackground(new Color(250, 250, 250));
        pnAuxiliar.setBounds(780, 260, 460, 363);
        jPanel1.add(pnAuxiliar);
        
        scrollPane = new JScrollPane();
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        
        label_3 = new JLabel("Historico");
        
        button_2 = new JButton("Esconder");
        
        button_3 = new JButton("Novo");
        
        button_4 = new JButton("Alterar");
        
        scrollPane_1 = new JScrollPane();
        GroupLayout gl_pnAuxiliar = new GroupLayout(pnAuxiliar);
        gl_pnAuxiliar.setHorizontalGroup(
        	gl_pnAuxiliar.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 460, Short.MAX_VALUE)
        		.addGroup(gl_pnAuxiliar.createSequentialGroup()
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.LEADING)
        				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        				.addGroup(gl_pnAuxiliar.createSequentialGroup()
        					.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.LEADING)
        						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
        					.addComponent(button_2))
        				.addGroup(gl_pnAuxiliar.createSequentialGroup()
        					.addContainerGap(314, Short.MAX_VALUE)
        					.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(button_4))
        				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_pnAuxiliar.setVerticalGroup(
        	gl_pnAuxiliar.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_pnAuxiliar.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label_3)
        				.addComponent(button_2))
        			.addGap(4)
        			.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(7)
        			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(13)
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.BASELINE)
        				.addComponent(button_3)
        				.addComponent(button_4))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        
        tbAuxiliar = new JTable();
        scrollPane.setViewportView(tbAuxiliar);
        
        textArea = new JTextArea();
        scrollPane_1.setViewportView(textArea);
        pnAuxiliar.setLayout(gl_pnAuxiliar);

        setBounds(0, 0, 1250, 660);
    }// </editor-fold>//GEN-END:initComponents
}
