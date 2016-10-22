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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.utilitarios.DefaultUtilities;
import javax.swing.JRadioButton;
import javax.swing.JTable;

public class EmpresasView extends JInternalFrame implements DefaultUtilities{
	private javax.swing.ButtonGroup group_situacao;
    private javax.swing.JButton button;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private JButton btnExcluir;
    private JButton btnNovo_1;
    private JTextField textField;
    private JLabel lblEmail;
    private JTextField textField_2;
    private JLabel lblWebsite;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel label;
	private JDateChooser dateChooser;
	private JLabel label_1;
	private JDateChooser dateChooser_1;
	private JButton button_1;
	private JComboBox<String> comboBox_1;
	private JLabel lblAtendente;
	private JComboBox<String> comboBox_2;
	private JLabel lblCep;
	private JTextField textField_3;
	private JPanel panel_2;
	private JScrollPane scrollPane;
	private JTextField textField_1;
	private JLabel label_3;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JScrollPane scrollPane_1;
	private JTextArea textArea;
	private JTable table;
	private JTable table_1;

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return DefaultUtilities.super.getColor();
	}
	@Override
	public Color getComponentColor() {
		// TODO Auto-generated method stub
		return DefaultUtilities.super.getComponentColor();
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
	}
    private void initComponents() {
    	group_situacao = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel3.setBounds(0, 0, 1240, 69);
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jComboBox9 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel4.setBounds(10, 289, 760, 334);
        jTextField1 = new javax.swing.JTextField();
        jTextField1.setBounds(107, 11, 87, 20);
        jLabel17 = new javax.swing.JLabel();
        jLabel17.setBounds(546, 14, 56, 14);
        jLabel14 = new javax.swing.JLabel();
        jLabel14.setBounds(385, 14, 78, 14);
        jLabel15 = new javax.swing.JLabel();
        jLabel15.setBounds(463, 14, 73, 14);
        jLabel10 = new javax.swing.JLabel();
        jLabel10.setBounds(10, 14, 56, 14);
        jTextField2 = new javax.swing.JTextField();
        jTextField2.setBounds(107, 39, 201, 20);
        jLabel1 = new javax.swing.JLabel();
        jLabel1.setBounds(10, 40, 56, 17);
        jTextField3 = new javax.swing.JTextField();
        jTextField3.setBounds(107, 68, 109, 20);
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setBounds(10, 71, 56, 14);
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox2.setBounds(107, 96, 87, 20);
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setBounds(10, 96, 56, 18);
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox3.setBounds(438, 95, 92, 20);
        jLabel4 = new javax.swing.JLabel();
        jLabel4.setBounds(385, 98, 35, 14);
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField1.setBounds(107, 125, 87, 20);
        jLabel5 = new javax.swing.JLabel();
        jLabel5.setBounds(10, 125, 56, 20);
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jFormattedTextField2.setBounds(440, 124, 90, 20);
        jLabel7 = new javax.swing.JLabel();
        jLabel7.setBounds(385, 124, 51, 20);
        jTextField5 = new javax.swing.JTextField();
        jTextField5.setBounds(107, 185, 201, 20);
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox5.setBounds(10, 185, 56, 20);
        jTextField8 = new javax.swing.JTextField();
        jTextField8.setBounds(440, 184, 35, 20);
        jLabel11 = new javax.swing.JLabel();
        jLabel11.setBounds(385, 184, 51, 20);
        jTextField7 = new javax.swing.JTextField();
        jTextField7.setBounds(252, 216, 56, 20);
        jLabel12 = new javax.swing.JLabel();
        jLabel12.setBounds(203, 216, 43, 20);
        jTextField4 = new javax.swing.JTextField();
        jTextField4.setBounds(440, 215, 90, 20);
        jLabel13 = new javax.swing.JLabel();
        jLabel13.setBounds(385, 215, 43, 20);
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox6.setBounds(440, 246, 52, 20);
        jLabel9 = new javax.swing.JLabel();
        jLabel9.setBounds(384, 246, 52, 19);
        jTextField6 = new javax.swing.JTextField();
        jTextField6.setBounds(107, 247, 107, 20);
        jLabel8 = new javax.swing.JLabel();
        jLabel8.setBounds(10, 249, 56, 17);
        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        jPanel3.setBackground(getColor());

        jComboBox4.setBackground(getComponentColor());
        jComboBox4.setModel(new DefaultComboBoxModel(new String[] {"Responsavel", "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos"}));
        
        jComboBox7.setBackground(getComponentColor());
        jComboBox7.setModel(new DefaultComboBoxModel(new String[] {"Status", "Em Andamento", "Ganho", "Perdido"}));

        jComboBox8.setBackground(getComponentColor());
        jComboBox8.setModel(new DefaultComboBoxModel(new String[] {"Meio"}));

        jComboBox9.setBackground(getComponentColor());
        jComboBox9.setModel(new DefaultComboBoxModel(new String[] {"Empresa", "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos"}));
        
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Pessoa", "Todos"}));
        comboBox.setBackground(getComponentColor());
        
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
        
        comboBox_1 = new JComboBox<String>();
        comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Classifica\u00E7\u00E3o"}));
        comboBox_1.setBackground(getComponentColor());
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3Layout.setHorizontalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jComboBox7, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jComboBox8, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jComboBox9, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
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
        						.addComponent(jComboBox7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jComboBox8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jComboBox9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(null);
        panel.add(label_1);
        panel.add(label);
        panel.add(dateChooser);
        panel.add(dateChooser_1);
        panel.add(button_1);
        jPanel3.setLayout(jPanel3Layout);

        jPanel4.setBackground(getComponentColor());

        jLabel17.setText("{Usuario}");

        jLabel14.setText("Cadastro em:");

        jLabel15.setText("{Date###}");

        jLabel10.setText("{COD###}");

        jLabel1.setText("Nome:");

        jTextField3.setText("00.000.000/000-91");

        jLabel2.setText("CNPJ:");

        jComboBox2.setModel(new DefaultComboBoxModel(new String[] {"Ouro", "Prata", "Diamante"}));

        jLabel3.setText("Nivel:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Meio");

        jFormattedTextField1.setText("(99)3204-0000");

        jLabel5.setText("Telefone:");

        jFormattedTextField2.setText("(00)99999-0000");

        jLabel7.setText("Celular");

        jComboBox5.setModel(new DefaultComboBoxModel(new String[] {"Rua", "Av."}));

        jLabel11.setText("Nº:");

        jLabel12.setText("Compl:");

        jLabel13.setText("Bairro:");

        jComboBox6.setModel(new DefaultComboBoxModel(new String[] {"SP", "MG", "RS"}));

        jLabel9.setText("Estado:");

        jLabel8.setText("Cidade:");
        jButton3 = new javax.swing.JButton();
        jButton3.setBounds(203, 278, 90, 23);
        
        jButton3.setText("Editar");
        jButton4 = new javax.swing.JButton();
        jButton4.setBounds(299, 278, 90, 23);
        
        jButton4.setText("Salvar");
        
        btnExcluir = new JButton();
        btnExcluir.setBounds(395, 278, 90, 23);
        btnExcluir.setText("Excluir");
        
        btnNovo_1 = new JButton();
        btnNovo_1.setBounds(107, 278, 90, 23);
        btnNovo_1.setText("Novo");

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
        jPanel4.add(jLabel10);
        jPanel4.add(jLabel8);
        jPanel4.add(jLabel3);
        jPanel4.add(jLabel5);
        jPanel4.add(jComboBox5);
        jPanel4.add(jLabel1);
        jPanel4.add(jLabel2);
        jPanel4.add(jLabel12);
        jPanel4.add(jComboBox2);
        jPanel4.add(jLabel4);
        jPanel4.add(jComboBox3);
        jPanel4.add(jTextField5);
        jPanel4.add(jTextField6);
        jPanel4.add(jTextField7);
        jPanel4.add(jTextField1);
        jPanel4.add(jTextField2);
        jPanel4.add(jFormattedTextField1);
        jPanel4.add(jLabel9);
        jPanel4.add(jComboBox6);
        jPanel4.add(jLabel11);
        jPanel4.add(jLabel7);
        jPanel4.add(jLabel13);
        jPanel4.add(jFormattedTextField2);
        jPanel4.add(jTextField8);
        jPanel4.add(jTextField4);
        jPanel4.add(jLabel17);
        jPanel4.add(jLabel14);
        jPanel4.add(jLabel15);
        jPanel4.add(jTextField3);
        jPanel4.add(btnNovo_1);
        jPanel4.add(jButton3);
        jPanel4.add(jButton4);
        jPanel4.add(btnExcluir);
        
        textField = new JTextField();
        textField.setBounds(107, 154, 201, 20);
        jPanel4.add(textField);
        
        lblEmail = new JLabel();
        lblEmail.setText("E-mail");
        lblEmail.setBounds(10, 154, 56, 20);
        jPanel4.add(lblEmail);
        
        textField_2 = new JTextField();
        textField_2.setBounds(440, 155, 126, 20);
        jPanel4.add(textField_2);
        
        lblWebsite = new JLabel();
        lblWebsite.setText("Site");
        lblWebsite.setBounds(385, 155, 43, 20);
        jPanel4.add(lblWebsite);
        
        lblAtendente = new JLabel();
        lblAtendente.setText("Atendente:");
        lblAtendente.setBounds(385, 42, 87, 17);
        jPanel4.add(lblAtendente);
        
        comboBox_2 = new JComboBox<String>();
        comboBox_2.setBounds(476, 40, 92, 20);
        jPanel4.add(comboBox_2);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(getColor());
        panel_1.setBounds(601, 0, 159, 334);
        jPanel4.add(panel_1);
        
        JLabel lblNewLabel = new JLabel("Privacidade:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JCheckBox chckbxTodos = new JCheckBox("Todos");
        chckbxTodos.setBackground(getComponentColor());
        
        JCheckBox chckbxEu = new JCheckBox("Eu");
        chckbxEu.setBackground(getComponentColor());
        
        JCheckBox chckbxOutros = new JCheckBox("Outros");
        chckbxOutros.setBackground(getComponentColor());
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        				.addComponent(chckbxOutros, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        				.addComponent(chckbxEu, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        				.addComponent(chckbxTodos, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
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
        panel_1.setLayout(gl_panel_1);
        button = new JButton();
        button.setBounds(203, 311, 90, 23);
        jPanel4.add(button);
        
        button.setText("Historico");
        jButton1 = new javax.swing.JButton();
        jButton1.setBounds(299, 311, 90, 23);
        jPanel4.add(jButton1);
        jButton1.setText("Neg\u00F3cios");
        jButton2 = new javax.swing.JButton();
        jButton2.setBounds(396, 311, 87, 23);
        jPanel4.add(jButton2);

        jButton2.setText("Pessoas");

        lblCep = new JLabel();
        lblCep.setText("CEP:");
        lblCep.setBounds(10, 218, 35, 20);
        jPanel4.add(lblCep);

        JFormattedTextField formattedTextField = new JFormattedTextField();
        formattedTextField.setText("00000-000");
        formattedTextField.setBounds(107, 216, 78, 20);
        jPanel4.add(formattedTextField);
        
        JLabel label_2 = new JLabel("Buscar");
        label_2.setBounds(10, 83, 53, 14);
        jPanel1.add(label_2);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(74, 80, 139, 20);
        jPanel1.add(textField_3);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(10, 107, 760, 142);
        jPanel1.add(scrollPane_2);
        
        table_1 = new JTable();
        scrollPane_2.setViewportView(table_1);
        
        JRadioButton radioButton = new JRadioButton();
        radioButton.setText("Decrescente");
        radioButton.setBackground(new Color(250, 250, 250));
        radioButton.setBounds(780, 164, 110, 23);
        jPanel1.add(radioButton);
        
        JRadioButton radioButton_1 = new JRadioButton();
        radioButton_1.setText("Crescente");
        radioButton_1.setBackground(new Color(250, 250, 250));
        radioButton_1.setBounds(780, 134, 110, 23);
        jPanel1.add(radioButton_1);
        
        JComboBox<String> comboBox_3 = new JComboBox<String>();
        comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"Ordem Alfab\u00E9tica", "Data de Cadastro", "Atualiza\u00E7\u00E3o"}));
        comboBox_3.setBounds(780, 107, 110, 20);
        jPanel1.add(comboBox_3);
        
        panel_2 = new JPanel();
        panel_2.setBackground(new Color(250, 250, 250));
        panel_2.setBounds(780, 289, 460, 334);
        jPanel1.add(panel_2);
        
        scrollPane = new JScrollPane();
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        
        label_3 = new JLabel("Historico");
        
        button_2 = new JButton("Esconder");
        
        button_3 = new JButton("Novo");
        
        button_4 = new JButton("Alterar");
        
        scrollPane_1 = new JScrollPane();
        GroupLayout gl_panel_2 = new GroupLayout(panel_2);
        gl_panel_2.setHorizontalGroup(
        	gl_panel_2.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 460, Short.MAX_VALUE)
        		.addGroup(gl_panel_2.createSequentialGroup()
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        				.addGroup(gl_panel_2.createSequentialGroup()
        					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
        					.addComponent(button_2))
        				.addGroup(gl_panel_2.createSequentialGroup()
        					.addContainerGap(314, Short.MAX_VALUE)
        					.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(button_4))
        				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel_2.setVerticalGroup(
        	gl_panel_2.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_panel_2.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label_3)
        				.addComponent(button_2))
        			.addGap(4)
        			.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(7)
        			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(13)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(button_3)
        				.addComponent(button_4))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        textArea = new JTextArea();
        scrollPane_1.setViewportView(textArea);
        panel_2.setLayout(gl_panel_2);

        setBounds(0, 0, 1250, 660);
    }// </editor-fold>//GEN-END:initComponents
}
