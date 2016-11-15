package br.com.tiagods.view;

import java.awt.Color;

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
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerPessoas;
import br.com.tiagods.model.Pessoa;

public class PessoasView extends JInternalFrame {
	public static javax.swing.JComboBox<Object> cbAtendente;
    public static javax.swing.JComboBox<String> cbCategoria,cbCategoriaCad;
    public static javax.swing.JComboBox<String> cbOrigem;
    public static javax.swing.JComboBox<String> cbEmpresa;
    public static javax.swing.JComboBox<String> cbProdServicos;
    public static javax.swing.JComboBox<String> cbLogradouro;
    public static javax.swing.JComboBox<String> cbOrigemCad;
    public static javax.swing.JComboBox cbEstado;   
    public static javax.swing.JComboBox<String> cbAtendenteCad;
    public static javax.swing.JComboBox<String> cbProdServicosCad;
    public static javax.swing.JComboBox<String> cbCidade;
    public static javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel pnPrincipal;
    public static javax.swing.JPanel pnCabecalho;
    public static javax.swing.JPanel pnAuxiliar;
    public static javax.swing.JPanel pnPrivacidade;
    public static javax.swing.JButton btnNegocios, btnHistorico, btnEmpresas;
    public static javax.swing.JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnCancelar;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel label;
	public static JDateChooser data1,data2;
	public static JLabel label_1, txCadastradoPor, txDataCadastro;
	private JButton button_1;
	public static JTextField txLogradouro;
	public static JTextField txComplemento;
	public static JTextField txCodigo;
	public static JTextField txNome;
	public static JTextField txNum;
	public static JTextField txTelefone;
	public static JTextField txCelular;
	public static JTextField txBairro;
	public static JFormattedTextField txDataNascimento;
	public static JFormattedTextField txCep;
	public static JFormattedTextField txCpf;
	public static JTextField txEmail;
	public static JTextField txSite;
	public static JTextField textField_11;
	public static JTable tbAuxiliar;
	public static JTextField txBuscar;
	public static JTable tbPrincipal;
	ControllerPessoas controller = new ControllerPessoas();
	

	/**
	 * Create the frame.
	 */
	public PessoasView(Pessoa pessoa) {
		initComponents();
		pnAuxiliar.setVisible(false);
		pnPrivacidade.setVisible(false);
		controller.iniciar(pessoa);
	}
	private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        pnCabecalho = new javax.swing.JPanel();
        pnCabecalho.setBounds(0, 0, 1240, 69);
        cbAtendente = new javax.swing.JComboBox<>();
        cbCategoria = new javax.swing.JComboBox<>();
        cbOrigem = new javax.swing.JComboBox<>();
        cbEmpresa = new javax.swing.JComboBox<>();
        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        
        pnCabecalho.setBackground(new java.awt.Color(250, 250, 250));

        cbAtendente.setBackground(new java.awt.Color(250, 250, 250));
        cbAtendente.setName("Atendente");
        cbAtendente.setModel(new DefaultComboBoxModel(new String[] {"Atendente"}));
        
        cbCategoria.setBackground(new java.awt.Color(250, 250, 250));
        cbCategoria.setName("Categoria");
        cbCategoria.setModel(new DefaultComboBoxModel(new String[] {"Categoria"}));

        cbOrigem.setBackground(new java.awt.Color(250, 250, 250));
        cbOrigem.setName("Origem");
        cbOrigem.setModel(new DefaultComboBoxModel(new String[] {"Origem"}));

        cbEmpresa.setBackground(new java.awt.Color(250, 250, 250));
        cbEmpresa.setName("Empresa");
        cbEmpresa.setModel(new DefaultComboBoxModel(new String[] {"Empresa"}));
        
        cbProdServicos = new JComboBox<String>();
        cbProdServicos.setName("Produtos/Serviços");
        cbProdServicos.setModel(new DefaultComboBoxModel(new String[] {"Produtos/Servicos"}));
        cbProdServicos.setBackground(new Color(250, 250, 250));
        
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
        
        javax.swing.GroupLayout gl_pnCabecalho = new javax.swing.GroupLayout(pnCabecalho);
        gl_pnCabecalho.setHorizontalGroup(
        	gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnCabecalho.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbProdServicos, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addGap(148)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(138, Short.MAX_VALUE))
        );
        gl_pnCabecalho.setVerticalGroup(
        	gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnCabecalho.createSequentialGroup()
        			.addGroup(gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_pnCabecalho.createSequentialGroup()
        					.addGap(23)
        					.addGroup(gl_pnCabecalho.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbProdServicos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(null);
        panel.add(label_1);
        panel.add(label);
        panel.add(data2);
        panel.add(data1);
        panel.add(button_1);
        pnCabecalho.setLayout(gl_pnCabecalho);

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
        jPanel1.add(pnCabecalho);
        
        pnPrincipal = new JPanel();
        pnPrincipal.setLayout(null);
        pnPrincipal.setBackground((Color) null);
        pnPrincipal.setBounds(10, 260, 760, 363);
        jPanel1.add(pnPrincipal);
        
        JLabel lbCodigo = new JLabel();
        lbCodigo.setText("{COD###}");
        lbCodigo.setBounds(10, 14, 56, 14);
        pnPrincipal.add(lbCodigo);
        
        JLabel lblEstados = new JLabel();
        lblEstados.setText("Estado:");
        lblEstados.setBounds(10, 278, 56, 17);
        pnPrincipal.add(lblEstados);
        
        JLabel lbTelefone = new JLabel();
        lbTelefone.setText("Telefone:");
        lbTelefone.setBounds(10, 154, 56, 20);
        pnPrincipal.add(lbTelefone);
        
        cbLogradouro = new JComboBox<String>();
        cbLogradouro.setName("Logradouro");
        cbLogradouro.setBounds(10, 214, 87, 20);
        pnPrincipal.add(cbLogradouro);
        
        JLabel lbNome = new JLabel();
        lbNome.setText("Nome:");
        lbNome.setBounds(10, 40, 56, 17);
        pnPrincipal.add(lbNome);
        
        JLabel lbCpf = new JLabel();
        lbCpf.setText("CPF:");
        lbCpf.setBounds(10, 71, 56, 14);
        pnPrincipal.add(lbCpf);
        
        JLabel lbComplemento = new JLabel();
        lbComplemento.setText("Compl:");
        lbComplemento.setBounds(203, 245, 43, 20);
        pnPrincipal.add(lbComplemento);
        
        JLabel lbMeio = new JLabel();
        lbMeio.setText("Origem:");
        lbMeio.setBounds(385, 96, 50, 18);
        pnPrincipal.add(lbMeio);
        
        cbOrigemCad = new JComboBox<String>();
        cbOrigemCad.setName("OrigemCad");
        cbOrigemCad.setBounds(438, 96, 92, 20);
        pnPrincipal.add(cbOrigemCad);
        
        txLogradouro = new JTextField();
        txLogradouro.setBounds(107, 214, 201, 20);
        pnPrincipal.add(txLogradouro);
        
        txComplemento = new JTextField();
        txComplemento.setBounds(252, 245, 56, 20);
        pnPrincipal.add(txComplemento);
        
        txCodigo = new JTextField();
        txCodigo.setBounds(107, 11, 87, 20);
        pnPrincipal.add(txCodigo);
        
        txNome = new JTextField();
        txNome.setBounds(107, 39, 201, 20);
        pnPrincipal.add(txNome);
        
        txTelefone = new JTextField();
        txTelefone.setBounds(107, 154, 87, 20);
        pnPrincipal.add(txTelefone);
        
        JLabel lbEstado = new JLabel();
        lbEstado.setText("Cidade:");
        lbEstado.setBounds(384, 275, 52, 19);
        pnPrincipal.add(lbEstado);
        
        cbEstado = new JComboBox<String>();
        cbEstado.setName("Estado");
        cbEstado.addItemListener(controller);
        cbEstado.setBounds(107, 276, 52, 20);
        pnPrincipal.add(cbEstado);
        
        JLabel lbNum = new JLabel();
        lbNum.setText("N\u00BA:");
        lbNum.setBounds(385, 213, 51, 20);
        pnPrincipal.add(lbNum);
        
        JLabel lbCelular = new JLabel();
        lbCelular.setText("Celular");
        lbCelular.setBounds(385, 154, 51, 19);
        pnPrincipal.add(lbCelular);
        
        JLabel label_13 = new JLabel();
        label_13.setText("Bairro:");
        label_13.setBounds(385, 244, 43, 20);
        pnPrincipal.add(label_13);
        
        txCelular = new JTextField();
        txCelular.setBounds(440, 154, 95, 20);
        pnPrincipal.add(txCelular);
        
        txNum = new JTextField();
        txNum.setBounds(440, 213, 35, 20);
        pnPrincipal.add(txNum);
        
        txBairro = new JTextField();
        txBairro.setBounds(440, 244, 90, 20);
        pnPrincipal.add(txBairro);
        
        txCadastradoPor = new JLabel();
        txCadastradoPor.setText("{Usuario}");
        txCadastradoPor.setBounds(546, 14, 56, 14);
        pnPrincipal.add(txCadastradoPor);
        
        JLabel lblCadastro = new JLabel();
        lblCadastro.setText("Cadastro em:");
        lblCadastro.setBounds(385, 14, 73, 14);
        pnPrincipal.add(lblCadastro);
        
        txDataCadastro = new JLabel();
        txDataCadastro.setText("{Date###}");
        txDataCadastro.setBounds(463, 14, 73, 14);
        pnPrincipal.add(txDataCadastro);
        
        MaskFormatter formatterCpf=null;
        try{
        	formatterCpf = new MaskFormatter("###.###.###-##");
        }catch(Exception e){
        }
        txCpf = new JFormattedTextField(formatterCpf);
        txCpf.setBounds(107, 68, 109, 20);
        pnPrincipal.add(txCpf);
        
        btnNovo = new JButton();
        btnNovo.setText("Novo");
        btnNovo.setBounds(57, 306, 90, 23);
        pnPrincipal.add(btnNovo);
        
        btnEditar = new JButton();
        btnEditar.setText("Editar");
        btnEditar.setBounds(153, 306, 90, 23);
        pnPrincipal.add(btnEditar);
        
        btnSalvar = new JButton();
        btnSalvar.setText("Salvar");
        btnSalvar.setBounds(249, 306, 90, 23);
        pnPrincipal.add(btnSalvar);
        
        btnExcluir = new JButton();
        btnExcluir.setText("Excluir");
        btnExcluir.setBounds(345, 306, 90, 23);
        pnPrincipal.add(btnExcluir);
        
        txEmail = new JTextField();
        txEmail.setBounds(107, 183, 201, 20);
        pnPrincipal.add(txEmail);
        
        JLabel lbEmail1 = new JLabel();
        lbEmail1.setText("E-mail");
        lbEmail1.setBounds(10, 183, 56, 20);
        pnPrincipal.add(lbEmail1);
        
        txSite = new JTextField();
        txSite.setBounds(440, 184, 126, 20);
        pnPrincipal.add(txSite);
        
        JLabel lbSite = new JLabel();
        lbSite.setText("Site");
        lbSite.setBounds(385, 184, 43, 20);
        pnPrincipal.add(lbSite);
        
        JLabel lbAtendente = new JLabel();
        lbAtendente.setText("Atendente:");
        lbAtendente.setBounds(385, 42, 87, 17);
        pnPrincipal.add(lbAtendente);
        
        cbAtendenteCad = new JComboBox<String>();
        cbAtendenteCad.setName("AtendenteCad");
        cbAtendenteCad.setBounds(476, 40, 92, 20);
        pnPrincipal.add(cbAtendenteCad);
        
        pnPrivacidade = new JPanel();
        pnPrivacidade.setBackground((Color) null);
        pnPrivacidade.setBounds(601, 0, 159, 363);
        pnPrincipal.add(pnPrivacidade);
        
        JCheckBox checkBox = new JCheckBox("Outros");
        checkBox.setBackground((Color) null);
        
        JCheckBox checkBox_1 = new JCheckBox("Eu");
        checkBox_1.setBackground((Color) null);
        
        JCheckBox checkBox_2 = new JCheckBox("Todos");
        checkBox_2.setBackground((Color) null);
        
        JLabel label_20 = new JLabel("Privacidade:");
        label_20.setHorizontalAlignment(SwingConstants.CENTER);
        GroupLayout gl_pnPrivacidade = new GroupLayout(pnPrivacidade);
        gl_pnPrivacidade.setHorizontalGroup(
        	gl_pnPrivacidade.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 159, Short.MAX_VALUE)
        		.addGroup(gl_pnPrivacidade.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnPrivacidade.createParallelGroup(Alignment.LEADING)
        				.addComponent(checkBox, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(checkBox_1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(checkBox_2, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(label_20, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_pnPrivacidade.setVerticalGroup(
        	gl_pnPrivacidade.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_pnPrivacidade.createSequentialGroup()
        			.addGap(43)
        			.addComponent(label_20)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(checkBox_2)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(checkBox_1)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(checkBox)
        			.addContainerGap(195, Short.MAX_VALUE))
        );
        pnPrivacidade.setLayout(gl_pnPrivacidade);
        
        JLabel lbCep = new JLabel();
        lbCep.setText("CEP:");
        lbCep.setBounds(10, 247, 35, 20);
        pnPrincipal.add(lbCep);
        
        MaskFormatter formatterCep=null;
        try{
        	formatterCep = new MaskFormatter("######-###");
        }catch(Exception e){
        	
        }
        txCep = new JFormattedTextField(formatterCep);
        txCep.setBounds(107, 245, 78, 20);
        pnPrincipal.add(txCep);
        
        JLabel lbNascimento = new JLabel();
        lbNascimento.setText("Nasc:");
        lbNascimento.setBounds(385, 71, 35, 14);
        pnPrincipal.add(lbNascimento);
        
        MaskFormatter formatterNascimento=null;
        try{
        	formatterNascimento = new MaskFormatter("##/##");
        }catch (Exception e) {
			// TODO: handle exception
		}
        txDataNascimento = new JFormattedTextField(formatterNascimento);
        txDataNascimento.setBounds(438, 68, 50, 20);
        pnPrincipal.add(txDataNascimento);
        
        JButton btMeioCad = new JButton();
        btMeioCad.setText("ADC");
        btMeioCad.setBounds(540, 94, 36, 23);
        pnPrincipal.add(btMeioCad);
        
        JButton btAddProdServicos = new JButton();
        btAddProdServicos.setText("ADC");
        btAddProdServicos.setBounds(564, 123, 36, 23);
        pnPrincipal.add(btAddProdServicos);
        
        cbProdServicosCad = new JComboBox();
        cbProdServicosCad.setName("ServicosCad");
        cbProdServicosCad.setBounds(438, 126, 116, 20);
        pnPrincipal.add(cbProdServicosCad);
        
        JLabel lbProdServicosCad = new JLabel();
        lbProdServicosCad.setText("Produtos/Servi\u00E7os:");
        lbProdServicosCad.setBounds(317, 126, 109, 17);
        pnPrincipal.add(lbProdServicosCad);
        
        JButton button_13 = new JButton();
        button_13.setText("ADC");
        button_13.setBounds(203, 123, 36, 23);
        pnPrincipal.add(button_13);
        
        cbCategoriaCad = new JComboBox<String>();
        cbCategoriaCad.setName("CategoriaCad");
        cbCategoriaCad.setBounds(107, 126, 87, 20);
        pnPrincipal.add(cbCategoriaCad);
        
        JLabel lbCategoria = new JLabel();
        lbCategoria.setText("Categoria:");
        lbCategoria.setBounds(10, 126, 87, 18);
        pnPrincipal.add(lbCategoria);
        
        cbCidade = new JComboBox<String>();
        cbCidade.setName("Cidade");
        cbCidade.setBounds(438, 276, 128, 20);
        pnPrincipal.add(cbCidade);
        
        btnEmpresas = new JButton();
        btnEmpresas.setText("Empresas");
        btnEmpresas.setBounds(346, 340, 87, 23);
        pnPrincipal.add(btnEmpresas);
        
        btnNegocios = new JButton();
        btnNegocios.setText("Neg\u00F3cios");
        btnNegocios.setBounds(249, 340, 90, 23);
        pnPrincipal.add(btnNegocios);
        
        btnHistorico = new JButton();
        btnHistorico.setText("Historico");
        btnHistorico.setBounds(153, 340, 90, 23);
        pnPrincipal.add(btnHistorico);
        
        JButton button = new JButton();
        button.setText("Excluir");
        button.setBounds(438, 306, 90, 23);
        pnPrincipal.add(button);
        
        pnAuxiliar = new JPanel();
        pnAuxiliar.setBackground(new Color(250, 250, 250));
        pnAuxiliar.setBounds(780, 260, 460, 363);
        jPanel1.add(pnAuxiliar);
        
        JScrollPane scrolAuxiliar = new JScrollPane();
        
        textField_11 = new JTextField();
        textField_11.setColumns(10);
        
        JLabel label_7 = new JLabel("Historico");
        
        JButton btEsconder = new JButton("Esconder");
        
        JButton button_9 = new JButton("Novo");
        
        JButton button_10 = new JButton("Alterar");
        
        JScrollPane scrollPane_1 = new JScrollPane();
        GroupLayout gl_pnAuxiliar = new GroupLayout(pnAuxiliar);
        gl_pnAuxiliar.setHorizontalGroup(
        	gl_pnAuxiliar.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 284, Short.MAX_VALUE)
        		.addGroup(gl_pnAuxiliar.createSequentialGroup()
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.TRAILING)
        				.addComponent(scrolAuxiliar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        				.addGroup(Alignment.LEADING, gl_pnAuxiliar.createSequentialGroup()
        					.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.LEADING)
        						.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        						.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
        					.addComponent(btEsconder))
        				.addGroup(Alignment.LEADING, gl_pnAuxiliar.createSequentialGroup()
        					.addContainerGap(138, Short.MAX_VALUE)
        					.addComponent(button_9, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(button_10))
        				.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_pnAuxiliar.setVerticalGroup(
        	gl_pnAuxiliar.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_pnAuxiliar.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label_7)
        				.addComponent(btEsconder))
        			.addGap(4)
        			.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(7)
        			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(13)
        			.addGroup(gl_pnAuxiliar.createParallelGroup(Alignment.BASELINE)
        				.addComponent(button_9)
        				.addComponent(button_10))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(scrolAuxiliar, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        
        tbAuxiliar = new JTable();
        scrolAuxiliar.setViewportView(tbAuxiliar);
        
        JTextArea textArea = new JTextArea();
        scrollPane_1.setViewportView(textArea);
        pnAuxiliar.setLayout(gl_pnAuxiliar);
        
        JComboBox<String> cbOrdenar = new JComboBox<String>();
        cbOrdenar.setModel(new DefaultComboBoxModel(new String[] {"Ordem Alfab\u00E9tica", "Data de Cadastro", "Atualiza\u00E7\u00E3o"}));
        cbOrdenar.setBounds(780, 107, 110, 20);
        jPanel1.add(cbOrdenar);
        
        JRadioButton btCrescente = new JRadioButton();
        btCrescente.setText("Crescente");
        btCrescente.setBackground(new Color(250, 250, 250));
        btCrescente.setBounds(780, 134, 110, 23);
        jPanel1.add(btCrescente);
        
        JRadioButton btDecrescente = new JRadioButton();
        btDecrescente.setText("Decrescente");
        btDecrescente.setBackground(new Color(250, 250, 250));
        btDecrescente.setBounds(780, 164, 110, 23);
        jPanel1.add(btDecrescente);
        ButtonGroup group = new ButtonGroup();
        group.add(btCrescente);
        group.add(btDecrescente);
        
        
        JScrollPane scrollPrincipal = new JScrollPane();
        scrollPrincipal.setBounds(10, 107, 760, 142);
        jPanel1.add(scrollPrincipal);
        
        tbPrincipal = new JTable();
        scrollPrincipal.setViewportView(tbPrincipal);
        
        txBuscar = new JTextField();
        
        txBuscar.setActionCommand("Buscar");
        txBuscar.addKeyListener(controller);
        txBuscar.setBounds(74, 80, 139, 20);
        jPanel1.add(txBuscar);
        
        JLabel lbBuscar = new JLabel("Buscar");
        lbBuscar.setBounds(10, 83, 53, 14);
        jPanel1.add(lbBuscar);

        setBounds(0, 0, 1250, 660);
    }
}
