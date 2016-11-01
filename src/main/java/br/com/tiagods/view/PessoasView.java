package br.com.tiagods.view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JRadioButton;

public class PessoasView extends JInternalFrame {
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
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTable jTableAux;
	private JTextField textField_3;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PessoasView frame = new PessoasView();
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
	public PessoasView() {
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
        jComboBox8.setModel(new DefaultComboBoxModel(new String[] {"Meio"}));

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
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3Layout.setHorizontalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jComboBox7, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jComboBox8, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jComboBox9, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        			.addGap(148)
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
        
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBackground((Color) null);
        panel_1.setBounds(10, 260, 760, 363);
        jPanel1.add(panel_1);
        
        JLabel label_2 = new JLabel();
        label_2.setText("{COD###}");
        label_2.setBounds(10, 14, 56, 14);
        panel_1.add(label_2);
        
        JLabel label_3 = new JLabel();
        label_3.setText("Cidade:");
        label_3.setBounds(10, 278, 56, 17);
        panel_1.add(label_3);
        
        JLabel label_4 = new JLabel();
        label_4.setText("Nivel:");
        label_4.setBounds(10, 96, 56, 18);
        panel_1.add(label_4);
        
        JLabel label_5 = new JLabel();
        label_5.setText("Telefone:");
        label_5.setBounds(10, 154, 56, 20);
        panel_1.add(label_5);
        
        JComboBox<String> comboBox_1 = new JComboBox<String>();
        comboBox_1.setBounds(10, 214, 56, 20);
        panel_1.add(comboBox_1);
        
        JLabel label_6 = new JLabel();
        label_6.setText("Nome:");
        label_6.setBounds(10, 40, 56, 17);
        panel_1.add(label_6);
        
        JLabel lblCpf = new JLabel();
        lblCpf.setText("CPF:");
        lblCpf.setBounds(10, 71, 56, 14);
        panel_1.add(lblCpf);
        
        JLabel label_8 = new JLabel();
        label_8.setText("Compl:");
        label_8.setBounds(203, 245, 43, 20);
        panel_1.add(label_8);
        
        JComboBox<String> comboBox_2 = new JComboBox<String>();
        comboBox_2.setBounds(107, 96, 87, 20);
        panel_1.add(comboBox_2);
        
        JLabel lblMeio = new JLabel();
        lblMeio.setText("Meio:");
        lblMeio.setBounds(385, 98, 35, 14);
        panel_1.add(lblMeio);
        
        JComboBox<String> comboBox_3 = new JComboBox<String>();
        comboBox_3.setBounds(438, 95, 92, 20);
        panel_1.add(comboBox_3);
        
        textField = new JTextField();
        textField.setBounds(107, 214, 201, 20);
        panel_1.add(textField);
        
        textField_1 = new JTextField();
        textField_1.setBounds(107, 276, 107, 20);
        panel_1.add(textField_1);
        
        textField_2 = new JTextField();
        textField_2.setBounds(252, 245, 56, 20);
        panel_1.add(textField_2);
        
        textField_4 = new JTextField();
        textField_4.setBounds(107, 11, 87, 20);
        panel_1.add(textField_4);
        
        textField_5 = new JTextField();
        textField_5.setBounds(107, 39, 201, 20);
        panel_1.add(textField_5);
        
        JFormattedTextField formattedTextField = new JFormattedTextField();
        formattedTextField.setText("(99)3204-0000");
        formattedTextField.setBounds(107, 154, 87, 20);
        panel_1.add(formattedTextField);
        
        JLabel label_10 = new JLabel();
        label_10.setText("Estado:");
        label_10.setBounds(384, 275, 52, 19);
        panel_1.add(label_10);
        
        JComboBox<String> comboBox_4 = new JComboBox<String>();
        comboBox_4.setBounds(440, 275, 52, 20);
        panel_1.add(comboBox_4);
        
        JLabel label_11 = new JLabel();
        label_11.setText("N\u00BA:");
        label_11.setBounds(385, 213, 51, 20);
        panel_1.add(label_11);
        
        JLabel label_12 = new JLabel();
        label_12.setText("Celular");
        label_12.setBounds(385, 153, 51, 20);
        panel_1.add(label_12);
        
        JLabel label_13 = new JLabel();
        label_13.setText("Bairro:");
        label_13.setBounds(385, 244, 43, 20);
        panel_1.add(label_13);
        
        JFormattedTextField formattedTextField_1 = new JFormattedTextField();
        formattedTextField_1.setText("(00)99999-0000");
        formattedTextField_1.setBounds(440, 153, 90, 20);
        panel_1.add(formattedTextField_1);
        
        textField_6 = new JTextField();
        textField_6.setBounds(440, 213, 35, 20);
        panel_1.add(textField_6);
        
        textField_7 = new JTextField();
        textField_7.setBounds(440, 244, 90, 20);
        panel_1.add(textField_7);
        
        JLabel label_14 = new JLabel();
        label_14.setText("{Usuario}");
        label_14.setBounds(546, 14, 56, 14);
        panel_1.add(label_14);
        
        JLabel lblCadastro = new JLabel();
        lblCadastro.setText("Cadastro:");
        lblCadastro.setBounds(385, 14, 56, 14);
        panel_1.add(lblCadastro);
        
        JLabel label_16 = new JLabel();
        label_16.setText("{Date###}");
        label_16.setBounds(438, 14, 73, 14);
        panel_1.add(label_16);
        
        textField_8 = new JTextField();
        textField_8.setText("000.000.000-00");
        textField_8.setBounds(107, 68, 109, 20);
        panel_1.add(textField_8);
        
        JButton button = new JButton();
        button.setText("Novo");
        button.setBounds(107, 307, 90, 23);
        panel_1.add(button);
        
        JButton button_2 = new JButton();
        button_2.setText("Editar");
        button_2.setBounds(203, 307, 90, 23);
        panel_1.add(button_2);
        
        JButton button_3 = new JButton();
        button_3.setText("Salvar");
        button_3.setBounds(299, 307, 90, 23);
        panel_1.add(button_3);
        
        JButton button_4 = new JButton();
        button_4.setText("Excluir");
        button_4.setBounds(395, 307, 90, 23);
        panel_1.add(button_4);
        
        textField_9 = new JTextField();
        textField_9.setBounds(107, 183, 201, 20);
        panel_1.add(textField_9);
        
        JLabel label_17 = new JLabel();
        label_17.setText("E-mail");
        label_17.setBounds(10, 183, 56, 20);
        panel_1.add(label_17);
        
        textField_10 = new JTextField();
        textField_10.setBounds(440, 184, 126, 20);
        panel_1.add(textField_10);
        
        JLabel label_18 = new JLabel();
        label_18.setText("Site");
        label_18.setBounds(385, 184, 43, 20);
        panel_1.add(label_18);
        
        JLabel label_19 = new JLabel();
        label_19.setText("Atendente:");
        label_19.setBounds(385, 42, 87, 17);
        panel_1.add(label_19);
        
        JComboBox<String> comboBox_5 = new JComboBox<String>();
        comboBox_5.setBounds(476, 40, 92, 20);
        panel_1.add(comboBox_5);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground((Color) null);
        panel_2.setBounds(601, 0, 159, 363);
        panel_1.add(panel_2);
        
        JCheckBox checkBox = new JCheckBox("Outros");
        checkBox.setBackground((Color) null);
        
        JCheckBox checkBox_1 = new JCheckBox("Eu");
        checkBox_1.setBackground((Color) null);
        
        JCheckBox checkBox_2 = new JCheckBox("Todos");
        checkBox_2.setBackground((Color) null);
        
        JLabel label_20 = new JLabel("Privacidade:");
        label_20.setHorizontalAlignment(SwingConstants.CENTER);
        GroupLayout gl_panel_2 = new GroupLayout(panel_2);
        gl_panel_2.setHorizontalGroup(
        	gl_panel_2.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 159, Short.MAX_VALUE)
        		.addGroup(gl_panel_2.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        				.addComponent(checkBox, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(checkBox_1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(checkBox_2, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        				.addComponent(label_20, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel_2.setVerticalGroup(
        	gl_panel_2.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_panel_2.createSequentialGroup()
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
        panel_2.setLayout(gl_panel_2);
        
        JButton button_5 = new JButton();
        button_5.setText("Historico");
        button_5.setBounds(203, 340, 90, 23);
        panel_1.add(button_5);
        
        JButton button_6 = new JButton();
        button_6.setText("Neg\u00F3cios");
        button_6.setBounds(299, 340, 90, 23);
        panel_1.add(button_6);
        
        JLabel label_21 = new JLabel();
        label_21.setText("CEP:");
        label_21.setBounds(10, 247, 35, 20);
        panel_1.add(label_21);
        
        JFormattedTextField formattedTextField_2 = new JFormattedTextField();
        formattedTextField_2.setText("00000-000");
        formattedTextField_2.setBounds(107, 245, 78, 20);
        panel_1.add(formattedTextField_2);
        
        JLabel lblNasc = new JLabel();
        lblNasc.setText("Nasc:");
        lblNasc.setBounds(385, 71, 35, 14);
        panel_1.add(lblNasc);
        
        JDateChooser dateChooser_2 = new JDateChooser();
        dateChooser_2.setBounds(438, 68, 100, 20);
        panel_1.add(dateChooser_2);
        
        JButton button_11 = new JButton();
        button_11.setText("ADC");
        button_11.setBounds(540, 94, 36, 23);
        panel_1.add(button_11);
        
        JButton button_12 = new JButton();
        button_12.setText("ADC");
        button_12.setBounds(203, 94, 36, 23);
        panel_1.add(button_12);
        
        JLabel lblCategoria = new JLabel();
        lblCategoria.setText("Categoria:");
        lblCategoria.setBounds(10, 123, 56, 18);
        panel_1.add(lblCategoria);
        
        JComboBox<String> comboBox_7 = new JComboBox<String>();
        comboBox_7.setBounds(107, 123, 87, 20);
        panel_1.add(comboBox_7);
        
        JButton button_7 = new JButton();
        button_7.setText("ADC");
        button_7.setBounds(203, 121, 36, 23);
        panel_1.add(button_7);
        
        JComboBox comboBox_8 = new JComboBox();
        comboBox_8.setBounds(438, 124, 116, 20);
        panel_1.add(comboBox_8);
        
        JButton button_13 = new JButton();
        button_13.setText("ADC");
        button_13.setBounds(564, 123, 36, 23);
        panel_1.add(button_13);
        
        JLabel lblProdutosservios = new JLabel();
        lblProdutosservios.setText("Produtos/Servi\u00E7os:");
        lblProdutosservios.setBounds(317, 126, 107, 17);
        panel_1.add(lblProdutosservios);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(250, 250, 250));
        panel_3.setBounds(780, 260, 460, 363);
        jPanel1.add(panel_3);
        
        JScrollPane scrollPane = new JScrollPane();
        
        textField_11 = new JTextField();
        textField_11.setColumns(10);
        
        JLabel label_7 = new JLabel("Historico");
        
        JButton button_8 = new JButton("Esconder");
        
        JButton button_9 = new JButton("Novo");
        
        JButton button_10 = new JButton("Alterar");
        
        JScrollPane scrollPane_1 = new JScrollPane();
        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
        gl_panel_3.setHorizontalGroup(
        	gl_panel_3.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 284, Short.MAX_VALUE)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
        				.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        				.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
        					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
        						.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        						.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
        					.addComponent(button_8))
        				.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
        					.addContainerGap(138, Short.MAX_VALUE)
        					.addComponent(button_9, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(button_10))
        				.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel_3.setVerticalGroup(
        	gl_panel_3.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 334, Short.MAX_VALUE)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label_7)
        				.addComponent(button_8))
        			.addGap(4)
        			.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(7)
        			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(13)
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
        				.addComponent(button_9)
        				.addComponent(button_10))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        
        jTableAux = new JTable();
        jTableAux.setModel(new DefaultTableModel(
            	new Object[][] {
            	},
            	new String[] {
            		"Codigo", "Detahes", "Data"
            	}
            ) {
            	boolean[] columnEditables = new boolean[] {
            		false, false, false
            	};
            	public boolean isCellEditable(int row, int column) {
            		return columnEditables[column];
            	}
            }
            		);
        scrollPane.setViewportView(jTableAux);
        
        JTextArea textArea = new JTextArea();
        scrollPane_1.setViewportView(textArea);
        panel_3.setLayout(gl_panel_3);
        
        JComboBox<String> comboBox_6 = new JComboBox<String>();
        comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"Ordem Alfab\u00E9tica", "Data de Cadastro", "Atualiza\u00E7\u00E3o"}));
        comboBox_6.setBounds(780, 107, 110, 20);
        jPanel1.add(comboBox_6);
        
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
        
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(10, 107, 760, 142);
        jPanel1.add(scrollPane_2);
        
        table = new JTable();
        scrollPane_2.setViewportView(table);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(74, 80, 139, 20);
        jPanel1.add(textField_3);
        
        JLabel label_15 = new JLabel("Buscar");
        label_15.setBounds(10, 83, 53, 14);
        jPanel1.add(label_15);

        setBounds(0, 0, 1250, 660);
    }
}
