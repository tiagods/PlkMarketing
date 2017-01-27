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

import br.com.tiagods.controller.ControllerEmpresas;
import br.com.tiagods.model.Empresa;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import br.com.tiagods.view.interfaces.DefaultComboBox;
import br.com.tiagods.view.interfaces.DefaultEnumModel.Logradouro;

public class EmpresasView extends JInternalFrame {
	public static DefaultComboBox cbAtendente;
    public static DefaultComboBox cbCategoria;
    public static DefaultComboBox cbCategoriaCad;
    public static DefaultComboBox cbOrigem;
    public static DefaultComboBox cbEmpresa;
    public static DefaultComboBox cbProdServicos;
    public static javax.swing.JComboBox cbLogradouro;
    public static DefaultComboBox cbOrigemCad;
    public static DefaultComboBox cbNivel;
    public static DefaultComboBox cbNivelCad;
    public static DefaultComboBox cbEstado;
    public static DefaultComboBox cbAtendenteCad;
    public static DefaultComboBox cbProdServicosCad;
    public static DefaultComboBox cbCidade;
    public static javax.swing.JPanel pnVisao;
    public static javax.swing.JPanel pnPrincipal;
    public static javax.swing.JPanel pnCabecalho;
    public static javax.swing.JPanel pnAuxiliar;
    public static javax.swing.JPanel pnPrivacidade;
    public static javax.swing.JButton btnNegocios, btnHistorico, btnPessoas,btEsconder;
    public static javax.swing.JButton btnCategoriaAdd,btnNivelAdd,btnOrigemAdd,btnServicoAdd;
    public static javax.swing.JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnCancelar;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel label;
	public static JDateChooser data1,data2;
	public static JLabel label_1, txCadastradoPor, txDataCadastro;
	public static JTextField txLogradouro;
	public static JTextField txComplemento;
	public static JLabel txCodigo;
	public static JLabel txContador;
	public static JTextField txNome;
	public static JTextField txNum;
	public static JTextField txTelefone;
	public static JTextField txCelular;
	public static JTextField txBairro;
	public static JFormattedTextField txCep;
	public static JFormattedTextField txCnpj;
	public static JTextField txEmail;
	public static JTextField txSite;
	public static JTable tbAuxiliar;
	public static JTextField txBuscar;
	public static JTable tbPrincipal;
	public static JButton btnEmail, btnLink,btnNovaTarefa,btnImportar,btnExportar ;
	private JLabel lbTitulo;
	public static JTextField txRazaoSocial;
	public static JTextField txApelido;
	
	ControllerEmpresas controller = new ControllerEmpresas();

	/**
	 * Create the frame.
	 */
	public EmpresasView(Empresa empresa) {
		initComponents();
		pnAuxiliar.setVisible(false);
		pnPrivacidade.setVisible(false);
		txContador = new JLabel("");
		txContador.setBounds(780, 235, 150, 14);
		pnVisao.add(txContador);
		controller.iniciar(empresa);
		btnPessoas.setEnabled(false);
		
		btnImportar.setEnabled(false);
		btnExportar.setEnabled(false);
		
	}
	private void initComponents() {
        pnVisao = new javax.swing.JPanel();
        pnCabecalho = new javax.swing.JPanel();
        pnCabecalho.setBounds(0, 0, 1240, 69);
        cbAtendente = new DefaultComboBox();
        cbCategoria = new DefaultComboBox();
        cbOrigem = new DefaultComboBox();
        cbEmpresa = new DefaultComboBox();
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
        label.setBounds(158, 11, 22, 20);
        label.setText("at\u00E9");
        label.setHorizontalAlignment(SwingConstants.LEFT);


        label_1 = new JLabel();
        label_1.setHorizontalAlignment(SwingConstants.LEFT);
        label_1.setBounds(10, 11, 22, 20);
        label_1.setText("de:");

        data1 = new JDateChooser();
        data1.setBounds(36, 11, 100, 20);
        data1.addPropertyChangeListener(controller);
        data2 = new JDateChooser();
        data2.addPropertyChangeListener(controller);
        data2.setBounds(184, 11, 100, 20);

        cbNivel = new DefaultComboBox();
        cbNivel.setModel(new DefaultComboBoxModel(new String[] {"Nivel"}));
        cbNivel.setName("Nivel");
        cbNivel.setBackground(new Color(250, 250, 250));
        
        lbTitulo = new JLabel("Cadastro de Empresas");
        lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));

        javax.swing.GroupLayout gl_pnCabecalho = new javax.swing.GroupLayout(pnCabecalho);
        gl_pnCabecalho.setHorizontalGroup(
        	gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnCabecalho.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        				.addComponent(lbTitulo, GroupLayout.PREFERRED_SIZE, 1230, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_pnCabecalho.createSequentialGroup()
        					.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cbProdServicos, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addGap(77)
        					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_pnCabecalho.setVerticalGroup(
        	gl_pnCabecalho.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnCabecalho.createSequentialGroup()
        			.addComponent(lbTitulo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
        			.addGroup(gl_pnCabecalho.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_pnCabecalho.createParallelGroup(Alignment.BASELINE)
        					.addComponent(cbCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbNivel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbOrigem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbEmpresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbProdServicos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cbAtendente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
        );
        panel.setLayout(null);
        panel.add(label_1);
        panel.add(label);
        panel.add(data2);
        panel.add(data1);
        pnCabecalho.setLayout(gl_pnCabecalho);

        btnImportar = new JButton();
        btnImportar.setToolTipText("Importe um novo registro a partir de uma planilha Modelo Excel");
		btnImportar.setText("Importar");
		btnImportar.setName("Importar");
		btnImportar.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnImportar.setActionCommand("Importar");
		btnImportar.addActionListener(controller);
		btnImportar.setBounds(780, 90, 130, 25);
		pnVisao.add(btnImportar);
		
		btnExportar = new JButton();
		btnExportar.setToolTipText("Exporte o registro atual ou todos os registros da tabela para uma planilha Modelo Excel");
		btnExportar.setText("Exportar");
		btnExportar.setName("Exportar");
		btnExportar.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnExportar.setActionCommand("Exportar");
		btnExportar.addActionListener(controller);
		btnExportar.setBounds(780, 126, 130, 25);
		pnVisao.add(btnExportar);
        
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

        txRazaoSocial = new JTextField();
		txRazaoSocial.setBounds(107, 68, 201, 20);
		pnPrincipal.add(txRazaoSocial);
		
		JLabel lbRazao = new JLabel();
		lbRazao.setText("Raz\u00E3o Social:");
		lbRazao.setBounds(10, 69, 87, 17);
		pnPrincipal.add(lbRazao);
		
		txApelido = new JTextField();
		txApelido.setBounds(462, 40, 92, 20);
		pnPrincipal.add(txApelido);
		
		JLabel lblApelido = new JLabel();
		lblApelido.setText("Apelido:");
		lblApelido.setBounds(384, 41, 52, 14);
		pnPrincipal.add(lblApelido);
        
        JLabel lblEstados = new JLabel();
        lblEstados.setText("Estado:");
        lblEstados.setBounds(10, 278, 56, 17);
        pnPrincipal.add(lblEstados);

        JLabel lbTelefone = new JLabel();
        lbTelefone.setText("Telefone:");
        lbTelefone.setBounds(10, 154, 56, 20);
        pnPrincipal.add(lbTelefone);

        cbLogradouro = new DefaultComboBox();
        cbLogradouro.setModel(new DefaultComboBoxModel(Logradouro.values()));
        cbLogradouro.setName("Logradouro");
        cbLogradouro.setBounds(10, 214, 87, 20);
        pnPrincipal.add(cbLogradouro);

        JLabel lbNome = new JLabel();
        lbNome.setText("Nome:");
        lbNome.setBounds(10, 40, 56, 17);
        pnPrincipal.add(lbNome);

        JLabel lbCnpj = new JLabel();
        lbCnpj.setText("CNPJ:");
        lbCnpj.setBounds(384, 71, 44, 14);
        pnPrincipal.add(lbCnpj);

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

        txCodigo = new JLabel();
        txCodigo.setBounds(10, 9, 87, 20);
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
        txCadastradoPor.setBounds(268, 15, 56, 14);
        pnPrincipal.add(txCadastradoPor);

        JLabel lblCadastro = new JLabel();
        lblCadastro.setText("Criado em:");
        lblCadastro.setBounds(107, 15, 73, 14);
        pnPrincipal.add(lblCadastro);

        txDataCadastro = new JLabel();
        txDataCadastro.setBounds(185, 15, 73, 14);
        pnPrincipal.add(txDataCadastro);

        MaskFormatter formatterCnpj=null;
        try{
        	formatterCnpj = new MaskFormatter("##.###.###/###-##");
        }catch(Exception e){
        }
        txCnpj = new JFormattedTextField(formatterCnpj);
        txCnpj.setBounds(440, 68, 114, 20);
        pnPrincipal.add(txCnpj);

        btnNovo = new JButton();
        btnNovo.setToolTipText("Novo");
        btnNovo.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnNovo.setActionCommand("Novo");
        btnNovo.setBounds(59, 306, 90, 25);
        btnNovo.addActionListener(controller);
        pnPrincipal.add(btnNovo);

        btnEditar = new JButton();
        btnEditar.setToolTipText("Editar");
        btnEditar.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnEditar.setActionCommand("Editar");
        btnEditar.addActionListener(controller);
        btnEditar.setBounds(153, 306, 90, 25);
        pnPrincipal.add(btnEditar);

        btnSalvar = new JButton();
        btnSalvar.setToolTipText("Salvar");
        btnSalvar.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnSalvar.setBounds(249, 306, 90, 25);
        btnSalvar.setActionCommand("Salvar");
        btnSalvar.addActionListener(controller);
        pnPrincipal.add(btnSalvar);

        btnCancelar = new JButton();
        btnCancelar.setToolTipText("Cancelar");
        btnCancelar.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnCancelar.setActionCommand("Cancelar");
        btnCancelar.addActionListener(controller);
        btnCancelar.setBounds(345, 306, 90, 25);
        pnPrincipal.add(btnCancelar);

        txEmail = new JTextField();
        txEmail.setBounds(107, 183, 201, 20);
        pnPrincipal.add(txEmail);

        JLabel lbEmail1 = new JLabel();
        lbEmail1.setText("E-mail");
        lbEmail1.setBounds(10, 183, 56, 20);
        pnPrincipal.add(lbEmail1);

        txSite = new JTextField();
        txSite.setBounds(440, 184, 114, 20);
        pnPrincipal.add(txSite);

        JLabel lbSite = new JLabel();
        lbSite.setText("Site");
        lbSite.setBounds(385, 184, 43, 20);
        pnPrincipal.add(lbSite);

        
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

        MaskFormatter formatterNascimento=null;
        try{
        	formatterNascimento = new MaskFormatter("##/##");
        }catch (Exception e) {
		}

        cbAtendenteCad = new DefaultComboBox();
        cbAtendenteCad.setName("AtendenteCad");
        cbAtendenteCad.setBounds(462, 9, 92, 20);
        pnPrincipal.add(cbAtendenteCad);

        JLabel lbAtendente = new JLabel();
        lbAtendente.setText("Atendente:");
        lbAtendente.setBounds(384, 12, 87, 17);
        pnPrincipal.add(lbAtendente);

        cbCategoriaCad = new DefaultComboBox();
        cbCategoriaCad.setName("CategoriaCad");
        cbCategoriaCad.setBounds(107, 96, 87, 20);
        pnPrincipal.add(cbCategoriaCad);
        
        btnCategoriaAdd = new JButton();
        btnCategoriaAdd.setBounds(203, 93, 36, 25);
        btnCategoriaAdd.setActionCommand("CriarCategoria");
        btnCategoriaAdd.addActionListener(controller);
        pnPrincipal.add(btnCategoriaAdd);
        
        JLabel lbCategoriaCad = new JLabel();
        lbCategoriaCad.setText("Categoria:");
        lbCategoriaCad.setBounds(10, 96, 87, 18);
        pnPrincipal.add(lbCategoriaCad);
        
        cbNivelCad = new DefaultComboBox();
        cbNivelCad.setName("NivelCad");
        cbNivelCad.setBounds(438, 96, 116, 20);
        pnPrincipal.add(cbNivelCad);
        
        btnNivelAdd = new JButton();
        btnNivelAdd.setBounds(564, 95, 36, 25);
        btnNivelAdd.setActionCommand("CriarNivel");
        btnNivelAdd.addActionListener(controller);
        pnPrincipal.add(btnNivelAdd);
        
        JLabel lbNivel = new JLabel();
        lbNivel.setText("Nivel:");
        lbNivel.setBounds(384, 96, 51, 18);
        pnPrincipal.add(lbNivel);
        
        cbOrigemCad = new DefaultComboBox();
        cbOrigemCad.setName("OrigemCad");
        cbOrigemCad.setBounds(107, 126, 87, 20);
        pnPrincipal.add(cbOrigemCad);
        
        btnOrigemAdd = new JButton();
        btnOrigemAdd.setBounds(203, 123, 36, 25);
        btnOrigemAdd.setActionCommand("CriarOrigem");
        btnOrigemAdd.addActionListener(controller);
        pnPrincipal.add(btnOrigemAdd);

        JLabel lbOrigemCad = new JLabel();
        lbOrigemCad.setText("Origem:");
        lbOrigemCad.setBounds(10, 126, 87, 18);
        pnPrincipal.add(lbOrigemCad);

        cbProdServicosCad = new DefaultComboBox();
        cbProdServicosCad.setName("ServicosCad");
        cbProdServicosCad.setBounds(438, 126, 116, 20);
        pnPrincipal.add(cbProdServicosCad);
        
        btnServicoAdd = new JButton();
        btnServicoAdd.setBounds(564, 123, 36, 25);
        btnServicoAdd.setActionCommand("CriarServico");
        btnServicoAdd.addActionListener(controller);
        pnPrincipal.add(btnServicoAdd);
        
        JLabel lbProdServicosCad = new JLabel();
        lbProdServicosCad.setText("Produtos/Servi\u00E7os:");
        lbProdServicosCad.setBounds(317, 126, 109, 17);
        pnPrincipal.add(lbProdServicosCad);
        
        cbCidade = new DefaultComboBox();
        cbCidade.setName("Cidade");
        cbCidade.setBounds(438, 276, 128, 20);
        pnPrincipal.add(cbCidade);

        btnPessoas = new JButton();
        btnPessoas.setToolTipText("Pessoas");
        btnPessoas.setActionCommand("Pessoas");
        btnPessoas.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnPessoas.setBounds(442, 338, 87, 25);
        btnPessoas.addActionListener(controller);
        pnPrincipal.add(btnPessoas);

        btnNegocios = new JButton();
        btnNegocios.setToolTipText("Neg\u00F3cios");
        btnNegocios.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnNegocios.setActionCommand("Negocios");
        btnNegocios.setBounds(345, 338, 90, 25);
        btnNegocios.addActionListener(controller);
        pnPrincipal.add(btnNegocios);

        btnHistorico = new JButton();
        btnHistorico.setToolTipText("Tarefas");
        btnHistorico.setActionCommand("Historico");
        btnHistorico.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnHistorico.setBounds(249, 338, 90, 25);
        btnHistorico.addActionListener(controller);
        pnPrincipal.add(btnHistorico);

        btnExcluir = new JButton();
        btnExcluir.setToolTipText("Excluir");
        btnExcluir.setFont(new Font("Dialog", Font.PLAIN, 9));
        btnExcluir.setActionCommand("Excluir");
        btnExcluir.addActionListener(controller);
        btnExcluir.setBounds(438, 306, 90, 25);
        pnPrincipal.add(btnExcluir);

        pnAuxiliar = new JPanel();
        pnAuxiliar.setBackground(new Color(250, 250, 250));
        pnAuxiliar.setBounds(780, 260, 460, 363);
        pnVisao.add(pnAuxiliar);
        
        btnNovaTarefa = new JButton();
        btnNovaTarefa.setBounds(10, 11, 130, 25);
        pnAuxiliar.add(btnNovaTarefa);
        btnNovaTarefa.setHorizontalAlignment(SwingConstants.LEADING);
        btnNovaTarefa.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btnNovaTarefa.setText("Nova Tarefa");
        btnNovaTarefa.setName("Nova Tarefa");
        btnNovaTarefa.setActionCommand("Nova Tarefa");
        btnNovaTarefa.addActionListener(controller);
        
        JScrollPane scrolAuxiliar = new JScrollPane();
        scrolAuxiliar.setBounds(0, 52, 450, 308);

        btEsconder = new JButton("Esconder");
        btEsconder.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btEsconder.setBounds(330, 11, 120, 25);
        btEsconder.setActionCommand("Esconder");
        btEsconder.addActionListener(controller);
        pnAuxiliar.setLayout(null);

        tbAuxiliar = new JTable();
        scrolAuxiliar.setViewportView(tbAuxiliar);
        pnAuxiliar.add(scrolAuxiliar);
        pnAuxiliar.add(btEsconder);
        ButtonGroup group = new ButtonGroup();


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

        btnEmail = new JButton();
		btnEmail.setToolTipText("Enviar e-mail");
		btnEmail.setBounds(318, 181, 35, 25);
		pnPrincipal.add(btnEmail);
		btnEmail.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnEmail.setActionCommand("MailTo");
		btnEmail.addActionListener(controller);
		
		btnLink = new JButton();
		btnLink.setToolTipText("Abrir P\u00E1gina");
		btnLink.setBounds(565, 181, 35, 25);
		pnPrincipal.add(btnLink);
		btnLink.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnLink.setActionCommand("OpenURL");
		btnLink.addActionListener(controller);
        
        setBounds(0, 0, 1250, 660);
    }
}
