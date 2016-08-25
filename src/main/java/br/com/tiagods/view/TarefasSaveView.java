package br.com.tiagods.view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import com.toedter.calendar.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class TarefasSaveView extends JInternalFrame {
	private JDateChooser jData;
    private javax.swing.ButtonGroup group_situacao;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton btnNewButton;
    private JButton btnCancelar;
    private JButton btnEditar;
    private JTextField textField;
    private JTextField textField_1;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TarefasSaveView frame = new TarefasSaveView();
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
	public TarefasSaveView() {
		initComponents();
	}
	private void initComponents() {

        group_situacao = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton3.setBackground(new java.awt.Color(250, 250, 250));
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton4.setBackground(new java.awt.Color(250, 250, 250));
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton5.setBackground(new java.awt.Color(250, 250, 250));
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton6.setBackground(new java.awt.Color(250, 250, 250));
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton7.setBackground(new java.awt.Color(250, 250, 250));
        
        jData = new JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        group_situacao.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton3.setText("Visita");

        group_situacao.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton4.setText("Visita");

        group_situacao.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton5.setText("Proposta");

        group_situacao.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton6.setText("Telefone");

        group_situacao.add(jRadioButton7);
        jRadioButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton7.setText("E-mail");

        jLabel1.setText("Reponsável:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setToolTipText("O que foi feito e qual \u00E9 o proximo passo!");
        jScrollPane2.setViewportView(jTextArea1);

        jTextField1.setEditable(false);

        jComboBox2.setModel(new DefaultComboBoxModel(new String[] {"Empresa", "Neg\u00F3cio", "Pessoa"}));

        jLabel2.setText("Tipo:");
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        JFormattedTextField formattedTextField = new JFormattedTextField();
        
        JLabel lblNewLabel = new JLabel("Hora:");
        
        JLabel lblData = new JLabel("Data:");
        
        btnNewButton = new JButton("Salvar");
        
        btnCancelar = new JButton("Cancelar");
        
        btnEditar = new JButton("Editar");
        
        JPanel panel_1 = new JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jLabel2)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(jLabel1)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
        					.addGap(33)
        					.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jData, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(lblNewLabel)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(formattedTextField, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(btnEditar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 851, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addGap(348))
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(203)
        			.addComponent(jRadioButton3)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jRadioButton4)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jRadioButton5)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jRadioButton6)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jRadioButton7)
        			.addContainerGap(637, Short.MAX_VALUE))
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(1050, Short.MAX_VALUE))
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(920, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
        			.addGap(7)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jRadioButton3)
        				.addComponent(jRadioButton4)
        				.addComponent(jRadioButton5)
        				.addComponent(jRadioButton6)
        				.addComponent(jRadioButton7))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(jLabel2)
        					.addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
        				.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(jData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(formattedTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(btnNewButton)
        						.addComponent(btnCancelar)
        						.addComponent(btnEditar))
        					.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(120, Short.MAX_VALUE))
        );
        
        JLabel lblCod = new JLabel("Cod:");
        panel_1.add(lblCod);
        
        textField = new JTextField();
        panel_1.add(textField);
        textField.setColumns(10);
        
        JLabel lblClass = new JLabel("Class:");
        panel_1.add(lblClass);
        
        textField_1 = new JTextField();
        panel_1.add(textField_1);
        textField_1.setColumns(10);
        
        scrollPane = new JScrollPane();
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
        			.addContainerGap(32, Short.MAX_VALUE)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Codigo", "Nome"
        	}
        ) {
        	boolean[] columnEditables = new boolean[] {
        		false, false
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        });
        table.setBackground(new Color(250,250,250));
        scrollPane.setViewportView(table);
        panel.setLayout(gl_panel);
        jPanel1.setLayout(jPanel1Layout);

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

        setBounds(0, 0, 1080, 620);
    }// </editor-fold>//GEN-END:initComponents
}
