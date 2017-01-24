package br.com.tiagods.view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import br.com.tiagods.view.interfaces.DefaultComboBox;

import java.awt.Font;

public class PessoasView extends JInternalFrame {
	public static DefaultComboBox cbAtendente;
    public static DefaultComboBox cbCategoria;
    public static DefaultComboBox cbOrigem;
    public static DefaultComboBox cbEmpresa;
    public static DefaultComboBox cbProdServicos;
    public static JComboBox cbLogradouro;
    public static DefaultComboBox cbEstado;
    public static DefaultComboBox cbAtendenteCad;
    public static DefaultComboBox cbCidade;
    public static DefaultComboBox cbCategoriaCad;
    public static DefaultComboBox cbOrigemCad;
    public static DefaultComboBox cbProdServicosCad;
    public static DefaultComboBox cbNivel;
    public static DefaultComboBox cbNivelCad;
    public static javax.swing.JPanel pnVisao;
    public static javax.swing.JPanel pnPrincipal;
    public static javax.swing.JPanel pnCabecalho;
    public static javax.swing.JPanel pnAuxiliar;
    public static javax.swing.JPanel pnPrivacidade;
    public static javax.swing.JButton btnNegocios, btnHistorico, btnEmpresas, btEsconder;
    public static javax.swing.JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnCancelar;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel label;
	public static JDateChooser data1,data2;
	public static JLabel label_1, txCadastradoPor, txDataCadastro;
	public static JTextField txLogradouro;
	public static JTextField txComplemento;
	public static JLabel txContador;
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
	public static JTable tbAuxiliar;
	public static JTextField txBuscar;
	public static JTable tbPrincipal;
	public static JLabel txCodigo;
	int i = 0 ;
	ControllerPessoas controller = new ControllerPessoas();
	/**
	 * Create the frame.
	 */
	public PessoasView(Pessoa pessoa) {
		initComponents();
		pnAuxiliar.setVisible(false);
		pnPrivacidade.setVisible(false);

		txContador = new JLabel("");
		txContador.setBounds(780, 235, 150, 14);
		pnVisao.add(txContador);
		controller.iniciar(pessoa);
		btnEmpresas.setEnabled(false);
		
	}
	private void initComponents() {
        pnVisao = new javax.swing.JPanel();
        pnCabecalho = new javax.swing.JPanel();
        pnCabecalho.setBounds(0, 0, 1240, 69);
        cbAtendente = new DefaultComboBox();
        cbCategoria = new DefaultComboBox();
        cbOrigem = new DefaultComboBox();
        cbEmpresa = new DefaultComboBox();
        cbEmpresa.setEnabled(false);
        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        pnVisao.setBackground(new java.awt.Color(250, 250, 250));

        pnCabecalho.setBackground(new java.awt.Color(250, 250, 250));

        cbAtendente.setBackground(new java.awt.Color(250, 250, 250));
        cbAtendente.setName("Atendente");
        cbAtendente.addItemListener(controller);
        cbAtendente.setModel(new DefaultComboBoxModel(new String[] {"Atendente"}));

        cbCategoria.setBackground(new java.awt.Color(250, 250, 250));
        cbCategoria.setName("Categoria");
        cbCategoria.addItemListener(controller);
        cbCategoria.setModel(new DefaultComboBoxModel(new String[] {"Categoria"}));

        cbOrigem.setBackground(new java.awt.Color(250, 250, 250));
        cbOrigem.setName("Origem");
        cbOrigem.addItemListener(controller);
        cbOrigem.setModel(new DefaultComboBoxModel(new String[] {"Origem"}));

        cbEmpresa.setBackground(new java.awt.Color(250, 250, 250));
        cbEmpresa.setName("Empresa");
        cbEmpresa.addItemListener(controller);
        cbEmpresa.setModel(new DefaultComboBoxModel(new String[] {"Empresa"}));

        cbProdServicos = new DefaultComboBox();
        cbProdServicos.setName("Produtos/Servicos");
        cbProdServicos.addItemListener(controller);
        cbProdServicos.setModel(new DefaultComboBoxModel(new String[] {"Produtos/Servicos"}));
        cbProdServicos.setBackground(new Color(250, 250, 250));

        panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));

        label = new JLabel();
        label.setBounds(160, 11, 22, 20);
        label.setText("at\u00E9");
        label.setHorizontalAlignment(SwingConstants.LEFT);


        label_1 = new JLabel();
        label_1.setHorizontalAlignment(SwingConstants.LEFT);
        label_1.setBounds(10, 11, 22, 20);
        label_1.setText("de:");

        data1 = new JDateChooser();
        data1.setBounds(36, 11, 100, 20);
        //data1.addPropertyChangeListener(controller);
        data2 = new JDateChooser();
        //data2.addPropertyChangeListener(controller);
        data2.setBounds(186, 11, 100, 20);
        
    	cbNivel = new DefaultComboBox();
        cbNivel.setModel(new DefaultComboBoxModel(new String[] {"Nivel"}));
        cbNivel.setName("Nivel");
        cbNivel.setBackground(new Color(250, 250, 250));
        
        JLabel lbTitulo = new JLabel("Cadastro de Pessoas");
        lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));

        javax.swing.GroupLayout gl_pnCabecalho = new javax.swing.GroupLayout(pnCabecalho);
        gl_pnCabecalho.setHorizontalGroup(
        	gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnCabecalho.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        				.addComponent(lbTitulo, GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
        				.addGroup(gl_pnCabecalho.createSequentialGroup()
        					.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addGap(10)
        					.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cbProdServicos, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addGap(77)
        					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        gl_pnCabecalho.setVerticalGroup(
        	gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnCabecalho.createSequentialGroup()
        			.addComponent(lbTitulo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGap(8)
        			.addGroup(gl_pnCabecalho.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_pnCabecalho.createParallelGroup(Alignment.BASELINE)
        					.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbProdServicos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
        );
        panel.setLayout(null);
        panel.add(label_1);
        panel.add(label);
        panel.add(data2);
        panel.add(data1);
        pnCabecalho.setLayout(gl_pnCabecalho);

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
        pnVisao.add(pnCabecalho);

        pnPrincipal = new JPanel();
        pnPrincipal.setLayout(null);
        pnPrincipal.setBackground((Color) null);
        pnPrincipal.setBounds(10, 260, 760, 363);
        pnVisao.add(pnPrincipal);

        JLabel lblEstados = new JLabel();
        lblEstados.setText("Estado:");
        lblEstados.setBounds(10, 278, 56, 17);
        pnPrincipal.add(lblEstados);

        JLabel lbTelefone = new JLabel();
        lbTelefone.setText("Telefone:");
        lbTelefone.setBounds(10, 154, 56, 20);
        pnPrincipal.add(lbTelefone);

        cbLogradouro = new JComboBox<>();
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

        txLogradouro = new JTextField();
        txLogradouro.setBounds(107, 214, 201, 20);
        pnPrincipal.add(txLogradouro);

        txComplemento = new JTextField();
        txComplemento.setBounds(252, 245, 56, 20);
        pnPrincipal.add(txComplemento);

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

        cbEstado = new DefaultComboBox();
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
        lblCadastro.setText("Criado em:");
        lblCadastro.setBounds(385, 14, 73, 14);
        pnPrincipal.add(lblCadastro);

        txDataCadastro = new JLabel();
        txDataCadastro.setText("");
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
        btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNovo.setText("Novo");
        btnNovo.setActionCommand("Novo");
        btnNovo.setBounds(59, 306, 90, 23);
        btnNovo.addActionListener(controller);
        pnPrincipal.add(btnNovo);

        btnEditar = new JButton();
        btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnEditar.setText("Editar");
        btnEditar.setActionCommand("Editar");
        btnEditar.addActionListener(controller);
        btnEditar.setBounds(153, 306, 90, 23);
        pnPrincipal.add(btnEditar);

        btnSalvar = new JButton();
        btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnSalvar.setText("Salvar");
        btnSalvar.setBounds(249, 306, 90, 23);
        btnSalvar.setActionCommand("Salvar");
        btnSalvar.addActionListener(controller);
        pnPrincipal.add(btnSalvar);

        btnCancelar = new JButton();
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(controller);
        btnCancelar.setBounds(345, 306, 90, 23);
        pnPrincipal.add(btnCancelar);

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

        cbAtendenteCad = new DefaultComboBox();
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
        	formatterCep = new MaskFormatter("#####-###");
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
		}
        txDataNascimento = new JFormattedTextField(formatterNascimento);
        txDataNascimento.setBounds(438, 68, 50, 20);
        pnPrincipal.add(txDataNascimento);

        cbCidade = new DefaultComboBox();
        cbCidade.setName("Cidade");
        cbCidade.setBounds(438, 276, 128, 20);
        pnPrincipal.add(cbCidade);

        btnEmpresas = new JButton();
        btnEmpresas.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnEmpresas.setText("Empresas");
        btnEmpresas.setActionCommand("Empresas");
        btnEmpresas.setBounds(441, 340, 87, 23);
        btnEmpresas.addActionListener(controller);
        pnPrincipal.add(btnEmpresas);

        btnNegocios = new JButton();
        btnNegocios.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNegocios.setText("Neg\u00F3cios");
        btnNegocios.setActionCommand("Negocios");
        btnNegocios.setBounds(344, 340, 90, 23);
        btnNegocios.addActionListener(controller);
        pnPrincipal.add(btnNegocios);

        JButton btTarefa = new JButton();
        btTarefa.setFont(new Font("Dialog", Font.PLAIN, 10));
        btTarefa.setBounds(59, 340, 115, 23);
        btTarefa.setText("Nova Tarefa");
        btTarefa.setName("Nova Tarefa");
        btTarefa.setActionCommand("Nova Tarefa");
        btTarefa.addActionListener(controller);
        pnPrincipal.add(btTarefa);
        
        btnHistorico = new JButton();
        btnHistorico.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnHistorico.setText("Historico");
        btnHistorico.setActionCommand("Historico");
        btnHistorico.setBounds(248, 340, 90, 23);
        btnHistorico.addActionListener(controller);
        pnPrincipal.add(btnHistorico);

        btnExcluir = new JButton();
        btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnExcluir.setText("Excluir");
        btnExcluir.setActionCommand("Excluir");
        btnExcluir.addActionListener(controller);
        btnExcluir.setBounds(438, 306, 90, 23);
        pnPrincipal.add(btnExcluir);
        
        txCodigo = new JLabel("");
        txCodigo.setBounds(107, 14, 109, 14);
        pnPrincipal.add(txCodigo);
        
        JLabel lbCategoriaCad = new JLabel();
        lbCategoriaCad.setText("Categoria:");
        lbCategoriaCad.setBounds(10, 96, 87, 18);
        pnPrincipal.add(lbCategoriaCad);
        
        cbCategoriaCad = new DefaultComboBox();
        cbCategoriaCad.setName("CategoriaCad");
        cbCategoriaCad.setBounds(107, 96, 87, 20);
        pnPrincipal.add(cbCategoriaCad);
        
        JButton btnCategoriaCad = new JButton();
        btnCategoriaCad.setText("ADC");
        btnCategoriaCad.setActionCommand("CriarCategoria");
        btnCategoriaCad.setBounds(203, 93, 36, 23);
        btnCategoriaCad.addActionListener(controller);
        pnPrincipal.add(btnCategoriaCad);
        
        JButton btnOrigemCad = new JButton();
        btnOrigemCad.setText("ADC");
        btnOrigemCad.setActionCommand("CriarOrigem");
        btnOrigemCad.setBounds(203, 123, 36, 23);
        btnOrigemCad.addActionListener(controller);
        pnPrincipal.add(btnOrigemCad);
        
        cbOrigemCad = new DefaultComboBox();
        cbOrigemCad.setName("OrigemCad");
        cbOrigemCad.setBounds(107, 126, 87, 20);
        pnPrincipal.add(cbOrigemCad);
        
        JLabel lbOrigemCad = new JLabel();
        lbOrigemCad.setText("Origem:");
        lbOrigemCad.setBounds(10, 126, 87, 18);
        pnPrincipal.add(lbOrigemCad);
        
        JLabel lbServicosCad = new JLabel();
        lbServicosCad.setText("Produtos/Servi\u00E7os:");
        lbServicosCad.setBounds(319, 126, 109, 17);
        pnPrincipal.add(lbServicosCad);
        
        cbProdServicosCad = new DefaultComboBox();
        cbProdServicosCad.setName("ServicosCad");
        cbProdServicosCad.setBounds(440, 126, 116, 20);
        pnPrincipal.add(cbProdServicosCad);
        
        JButton btnServicosCad = new JButton();
        btnServicosCad.setText("ADC");
        btnServicosCad.setActionCommand("CriarServico");
        btnServicosCad.setBounds(566, 123, 36, 23);
        btnServicosCad.addActionListener(controller);
        pnPrincipal.add(btnServicosCad);
        
        cbNivelCad = new DefaultComboBox();
        cbNivelCad.setName("NivelCad");
        cbNivelCad.setBounds(440, 94, 116, 20);
        pnPrincipal.add(cbNivelCad);
        
        JLabel lbNivelCad = new JLabel();
        lbNivelCad.setText("Nivel:");
        lbNivelCad.setBounds(348, 94, 89, 18);
        pnPrincipal.add(lbNivelCad);
        
        JButton btnNivelCad = new JButton();
        btnNivelCad.setText("ADC");
        btnNivelCad.setActionCommand("CriarNivel");
        btnNivelCad.setBounds(566, 93, 36, 23);
        btnNivelCad.addActionListener(controller);
        pnPrincipal.add(btnNivelCad);

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
        
        JScrollPane scrollPrincipal = new JScrollPane();
        scrollPrincipal.setBounds(10, 107, 760, 142);
        pnVisao.add(scrollPrincipal);

        tbPrincipal = new JTable();
        tbPrincipal.addMouseListener(controller);
        scrollPrincipal.setViewportView(tbPrincipal);

        txBuscar = new JTextField();
        txBuscar.setActionCommand("Buscar");
        txBuscar.addKeyListener(controller);
        txBuscar.setBounds(74, 80, 139, 20);
        pnVisao.add(txBuscar);

        JLabel lbBuscar = new JLabel("Buscar");
        lbBuscar.setBounds(10, 83, 53, 14);
        pnVisao.add(lbBuscar);

        setBounds(0, 0, 1250, 660);
    }
}
