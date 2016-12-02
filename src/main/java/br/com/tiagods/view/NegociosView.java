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

public class NegociosView extends JInternalFrame {
	private javax.swing.ButtonGroup group_situacao;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel label;
	private JDateChooser dateChooser;
	private JLabel label_1;
	private JDateChooser dateChooser_1;
	private JButton button_1;
	private JComboBox<String> comboBox_1;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JLabel label_2;
	private JButton button;
	private JButton button_2;
	private JButton button_3;
	private JScrollPane scrollPane_1;
	private JPanel panel_2;
	private JLabel label_3;
	private JLabel label_7;
	private JLabel lblDescrio;
	private JTextField textField_5;
	private JTextField textField_6;
	private JLabel label_15;
	private JLabel label_16;
	private JLabel label_17;
	private JButton button_4;
	private JButton button_5;
	private JButton button_6;
	private JButton button_7;
	private JLabel label_20;
	private JComboBox<String> comboBox_6;
	private JPanel panel_3;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JLabel label_21;
	private JButton button_8;
	private JLabel lblNewLabel;
	private JTextArea textArea_1;
	private JTable table;
	private JTextField textField_2;
	private JTable table_1;
	private JButton button_9;
	private JPanel panel_5;
	private JLabel lblValorTotalDe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NegociosView frame = new NegociosView();
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
	public NegociosView() {
		initComponents();
		JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, "Essa tela ainda não esta pronta! Modo somente leitura");
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
        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        
        jPanel3.setBackground(new java.awt.Color(250, 250, 250));

        jComboBox4.setBackground(new java.awt.Color(250, 250, 250));
        jComboBox4.setModel(new DefaultComboBoxModel(new String[] {"Responsavel", "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos"}));
        
        jComboBox7.setBackground(new java.awt.Color(250, 250, 250));
        jComboBox7.setModel(new DefaultComboBoxModel(new String[] {"Status", "Em Andamento", "Ganho", "Perdido"}));

        jComboBox8.setBackground(new java.awt.Color(250, 250, 250));
        jComboBox8.setModel(new DefaultComboBoxModel(new String[] {"Etapa", "Indefinida", "Contato", "Envio de Proposta", "Follow-up", "Fechamento"}));

        jComboBox9.setBackground(new java.awt.Color(250, 250, 250));
        jComboBox9.setModel(new DefaultComboBoxModel(new String[] {"Empresa", "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos"}));
        
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Pessoa", "Todos"}));
        comboBox.setBackground(new Color(250, 250, 250));
        
        panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        
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
        comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Meio"}));
        comboBox_1.setBackground(new Color(250, 250, 250));
        
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
        jPanel1.add(jPanel3);
        
        panel_1 = new JPanel();
        panel_1.setBackground(new Color(250, 250, 250));
        panel_1.setBounds(780, 289, 460, 334);
        jPanel1.add(panel_1);
        
        scrollPane = new JScrollPane();
        
        textField = new JTextField();
        textField.setColumns(10);
        
        label_2 = new JLabel("Historico");
        
        button = new JButton("Esconder");
        
        button_2 = new JButton("Novo");
        
        button_3 = new JButton("Alterar");
        
        scrollPane_1 = new JScrollPane();
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
        	gl_panel_1.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 284, Short.MAX_VALUE)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        				.addGroup(gl_panel_1.createSequentialGroup()
        					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
        					.addComponent(button))
        				.addGroup(gl_panel_1.createSequentialGroup()
        					.addContainerGap(138, Short.MAX_VALUE)
        					.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(button_3))
        				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
        	gl_panel_1.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label_2)
        				.addComponent(button))
        			.addGap(4)
        			.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(7)
        			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(13)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
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
        panel_1.setLayout(gl_panel_1);
        
        panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBackground((Color) null);
        panel_2.setBounds(10, 289, 760, 334);
        jPanel1.add(panel_2);
        
        label_3 = new JLabel();
        label_3.setText("{COD###}");
        label_3.setBounds(10, 14, 56, 14);
        panel_2.add(label_3);
        
        label_7 = new JLabel();
        label_7.setText("Nome:");
        label_7.setBounds(10, 40, 56, 17);
        panel_2.add(label_7);
        
        lblDescrio = new JLabel();
        lblDescrio.setText("Descri\u00E7\u00E3o:");
        lblDescrio.setBounds(10, 193, 56, 14);
        panel_2.add(lblDescrio);
        
        textField_5 = new JTextField();
        textField_5.setBounds(107, 11, 87, 20);
        panel_2.add(textField_5);
        
        textField_6 = new JTextField();
        textField_6.setBounds(107, 39, 201, 20);
        panel_2.add(textField_6);
        
        label_15 = new JLabel();
        label_15.setText("{Usuario}");
        label_15.setBounds(546, 14, 56, 14);
        panel_2.add(label_15);
        
        label_16 = new JLabel();
        label_16.setText("Cadastro em:");
        label_16.setBounds(385, 14, 78, 14);
        panel_2.add(label_16);
        
        label_17 = new JLabel();
        label_17.setText("{Date###}");
        label_17.setBounds(463, 14, 73, 14);
        panel_2.add(label_17);
        
        button_4 = new JButton();
        button_4.setText("Novo");
        button_4.setBounds(107, 278, 90, 23);
        panel_2.add(button_4);
        
        button_5 = new JButton();
        button_5.setText("Editar");
        button_5.setBounds(203, 278, 90, 23);
        panel_2.add(button_5);
        
        button_6 = new JButton();
        button_6.setText("Salvar");
        button_6.setBounds(299, 278, 90, 23);
        panel_2.add(button_6);
        
        button_7 = new JButton();
        button_7.setText("Excluir");
        button_7.setBounds(395, 278, 90, 23);
        panel_2.add(button_7);
        
        label_20 = new JLabel();
        label_20.setText("Atendente:");
        label_20.setBounds(385, 42, 78, 17);
        panel_2.add(label_20);
        
        comboBox_6 = new JComboBox<String>();
        comboBox_6.setBounds(476, 40, 115, 20);
        panel_2.add(comboBox_6);
        
        panel_3 = new JPanel();
        panel_3.setBackground((Color) null);
        panel_3.setBounds(601, 0, 159, 334);
        panel_2.add(panel_3);
        
        checkBox = new JCheckBox("Outros");
        checkBox.setBackground((Color) null);
        
        checkBox_1 = new JCheckBox("Eu");
        checkBox_1.setBackground((Color) null);
        
        checkBox_2 = new JCheckBox("Todos");
        checkBox_2.setBackground((Color) null);
        
        label_21 = new JLabel("Privacidade:");
        label_21.setHorizontalAlignment(SwingConstants.CENTER);
        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
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
        panel_3.setLayout(gl_panel_3);
        
        button_8 = new JButton();
        button_8.setText("Historico");
        button_8.setBounds(203, 311, 90, 23);
        panel_2.add(button_8);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(107, 187, 268, 80);
        panel_2.add(scrollPane_2);
        
        JTextArea textArea = new JTextArea();
        scrollPane_2.setViewportView(textArea);
        
        JFormattedTextField formattedTextField = new JFormattedTextField();
        formattedTextField.setBounds(107, 156, 78, 20);
        panel_2.add(formattedTextField);
        
        JLabel lblValor = new JLabel();
        lblValor.setText("Valor Honor\u00E1rio:");
        lblValor.setBounds(10, 159, 87, 17);
        panel_2.add(lblValor);
        
        JLabel lblEmppessoa = new JLabel();
        lblEmppessoa.setText("Empresa/Pessoa:");
        lblEmppessoa.setBounds(10, 68, 87, 17);
        panel_2.add(lblEmppessoa);
        
        JButton button_11 = new JButton();
        button_11.setBounds(107, 65, 33, 23);
        panel_2.add(button_11);
        
        lblNewLabel = new JLabel("{COD}");
        lblNewLabel.setBounds(161, 68, 36, 17);
        panel_2.add(lblNewLabel);
        
        JLabel lblDataDeInicio = new JLabel();
        lblDataDeInicio.setText("Data de Inicio:");
        lblDataDeInicio.setHorizontalAlignment(SwingConstants.LEFT);
        lblDataDeInicio.setBounds(10, 96, 87, 20);
        panel_2.add(lblDataDeInicio);
        
        JDateChooser dateChooser_2 = new JDateChooser();
        dateChooser_2.setBounds(107, 96, 100, 20);
        panel_2.add(dateChooser_2);
        
        JDateChooser dateChooser_3 = new JDateChooser();
        dateChooser_3.setBounds(107, 125, 100, 20);
        panel_2.add(dateChooser_3);
        
        JLabel lblDataDeConcluso = new JLabel();
        lblDataDeConcluso.setText("Data Conclus\u00E3o:");
        lblDataDeConcluso.setHorizontalAlignment(SwingConstants.LEFT);
        lblDataDeConcluso.setBounds(10, 125, 87, 20);
        panel_2.add(lblDataDeConcluso);
        
        JLabel lblProdutosserviosRelacionados = new JLabel();
        lblProdutosserviosRelacionados.setText("Produtos/Servi\u00E7os Relacionados:");
        lblProdutosserviosRelacionados.setBounds(385, 96, 193, 17);
        panel_2.add(lblProdutosserviosRelacionados);
        
        JButton btnAdc = new JButton();
        btnAdc.setText("ADC");
        btnAdc.setBounds(555, 123, 36, 23);
        panel_2.add(btnAdc);
        
        JComboBox comboBox_2 = new JComboBox();
        comboBox_2.setBounds(385, 124, 160, 20);
        panel_2.add(comboBox_2);
        
        JLabel lblIncluirValores = new JLabel();
        lblIncluirValores.setText("Incluir Valores:");
        lblIncluirValores.setBounds(243, 159, 87, 17);
        panel_2.add(lblIncluirValores);
        
        button_9 = new JButton();
        button_9.setText("ADC");
        button_9.setBounds(340, 155, 36, 23);
        panel_2.add(button_9);
        
        JPanel panel_6 = new JPanel();
        panel_6.setBounds(385, 156, 206, 111);
        panel_2.add(panel_6);
        
        JScrollPane scrollPane_4 = new JScrollPane();
        scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        GroupLayout gl_panel_6 = new GroupLayout(panel_6);
        gl_panel_6.setHorizontalGroup(
        	gl_panel_6.createParallelGroup(Alignment.LEADING)
        		.addComponent(scrollPane_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );
        gl_panel_6.setVerticalGroup(
        	gl_panel_6.createParallelGroup(Alignment.LEADING)
        		.addComponent(scrollPane_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );
        panel_6.setLayout(gl_panel_6);
        
        JLabel lblNivel = new JLabel();
        lblNivel.setText("Nivel:");
        lblNivel.setBounds(385, 68, 78, 17);
        panel_2.add(lblNivel);
        
        JComboBox<String> comboBox_5 = new JComboBox<String>();
        comboBox_5.setBounds(476, 66, 73, 20);
        panel_2.add(comboBox_5);
        
        JButton button_10 = new JButton();
        button_10.setText("ADC");
        button_10.setBounds(555, 65, 36, 23);
        panel_2.add(button_10);
        
        JLabel label_5 = new JLabel();
        label_5.setBounds(203, 68, 105, 17);
        panel_2.add(label_5);
        
        JComboBox<String> comboBox_3 = new JComboBox<String>();
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
        
        JScrollPane scrollPane_3 = new JScrollPane();
        scrollPane_3.setBounds(10, 107, 760, 142);
        jPanel1.add(scrollPane_3);
        
        table_1 = new JTable();
        scrollPane_3.setViewportView(table_1);
        
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
        
        JPanel panel_4 = new JPanel();
        panel_4.setBounds(11, 253, 600, 30);
        jPanel1.add(panel_4);
        
        JRadioButton rdbtnNewRadioButton = new JRadioButton("Contato");
        panel_4.add(rdbtnNewRadioButton);
        
        JRadioButton rdbtnEnvioDeProposta = new JRadioButton("Envio de Proposta");
        panel_4.add(rdbtnEnvioDeProposta);
        
        JRadioButton rdbtnFollow = new JRadioButton("Follow-up");
        panel_4.add(rdbtnFollow);
        
        JRadioButton rdbtnFechamento = new JRadioButton("Fechamento");
        panel_4.add(rdbtnFechamento);
        
        JLabel lblStatus = new JLabel();
        panel_4.add(lblStatus);
        lblStatus.setText("Status:");
        
        JComboBox<String> comboBox_4 = new JComboBox<String>();
        panel_4.add(comboBox_4);
        comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"Em Andamento", "Ganho", "Perdido"}));
        
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
