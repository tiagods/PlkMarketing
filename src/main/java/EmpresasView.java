

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class EmpresasView extends JInternalFrame {
    private javax.swing.ButtonGroup group_situacao;
    private javax.swing.JButton button;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPanePrincipal;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jtableAux;;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private JLabel lblPessoas;

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
        jScrollPanePrincipal = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jTextField8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        button = new JButton();
        scrollPane = new JScrollPane();
        jtableAux = new javax.swing.JTable();
        setBorder(null);
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(880, 450));

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nome", "Nivel"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPanePrincipal.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(80);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(2).setMaxWidth(80);
        }
        
        jtableAux.setModel(new DefaultTableModel(
        		new Object[][]{
        			
        		}, 
        		new String[]{
        				"Codigo", "Nome"
        		}){
        		boolean[] canEdit = new boolean[]{
        				false, false
        		};
        		public boolean isCellEditable(int rowIndex, int columnIndex){
        			return canEdit[columnIndex];
        		}
        		}
        		);
        scrollPane.setViewportView(jtableAux);
        jButton1.setText("Neg\u00F3cios");

        jButton2.setText("Pessoas");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ordem Alfabética", "Data de Cadastro", "Atualização" }));

        jButton3.setText("Editar");

        jButton4.setText("Salvar");

        jRadioButton1.setBackground(new java.awt.Color(250, 250, 250));
        jRadioButton1.setText("Crescente");

        jRadioButton2.setBackground(new java.awt.Color(250, 250, 250));
        jRadioButton2.setText("Decrescente");

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));

        jLabel6.setText("Responsavel");

        jComboBox4.setBackground(new java.awt.Color(250, 250, 250));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos" }));
        
        jComboBox7.setBackground(new java.awt.Color(250, 250, 250));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos" }));
        
        jLabel18.setText("Situacao:");

        jLabel19.setFont(new Font("Tahoma", Font.PLAIN, 14)); // NOI18N
        jLabel19.setText("Filtro:");

        jLabel20.setText("Setor:");

        jComboBox8.setBackground(new java.awt.Color(250, 250, 250));
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos" }));
        
        jLabel21.setText("Classificação:");

        jComboBox9.setBackground(new java.awt.Color(250, 250, 250));
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos" }));
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(42, 42, 42))
        );

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        jLabel16.setText("Buscar:");

        jLabel17.setText("{Usuario}");

        jLabel14.setText("Cadastro em:");

        jLabel15.setText("{Date###}");

        jLabel10.setText("{COD###}");

        jLabel1.setText("Nome:");

        jTextField3.setText("00.000.000/000-91");

        jLabel2.setText("CNPJ:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Nivel:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Meio");

        jFormattedTextField1.setText("(99)3204-0000");

        jLabel5.setText("Telefone:");

        jFormattedTextField2.setText("(00)99999-0000");

        jLabel7.setText("Celular");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Nº:");

        jLabel12.setText("Compl:");

        jLabel13.setText("Bairro:");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Estado:");

        jLabel8.setText("Cidade:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4Layout.setHorizontalGroup(
        	jPanel4Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel4Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        					.addComponent(jComboBox5, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jLabel1)
        					.addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        					.addComponent(jLabel16)
        					.addComponent(jLabel10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jLabel12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        			.addGap(41)
        			.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jTextField5, 217, 217, 217)
        				.addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addGap(2)
        					.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jFormattedTextField1, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel4Layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jComboBox6, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
        				.addGroup(jPanel4Layout.createParallelGroup(Alignment.TRAILING, false)
        					.addGroup(jPanel4Layout.createSequentialGroup()
        						.addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addGap(10)
        						.addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
        					.addGroup(jPanel4Layout.createSequentialGroup()
        						.addGroup(jPanel4Layout.createParallelGroup(Alignment.TRAILING, false)
        							.addComponent(jLabel11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addGroup(jPanel4Layout.createSequentialGroup()
        								.addComponent(jLabel13, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
        								.addGap(8)))
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        							.addComponent(jFormattedTextField2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        							.addComponent(jTextField8, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        							.addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
        				.addComponent(jLabel17, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addComponent(jLabel14)
        					.addGap(6)
        					.addComponent(jLabel15))))
        );
        jPanel4Layout.setVerticalGroup(
        	jPanel4Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel4Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addGroup(jPanel4Layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel17)
        						.addGroup(jPanel4Layout.createSequentialGroup()
        							.addComponent(jLabel16, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
        							.addGap(2)))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(jPanel4Layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(jLabel14)
        							.addComponent(jLabel10))
        						.addComponent(jLabel15))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
        					.addGap(16)
        					.addComponent(jLabel2)
        					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
        					.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jFormattedTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addGap(29)
        					.addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jFormattedTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jComboBox5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(jPanel4Layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addComponent(jLabel13, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
        						.addComponent(jLabel12, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
        					.addGap(9)
        					.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(jPanel4Layout.createSequentialGroup()
        							.addGap(3)
        							.addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))
        						.addGroup(jPanel4Layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(jComboBox6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addGap(9)
        					.addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addGap(14))
        );
        jPanel4Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jTextField1, jTextField2, jTextField3, jComboBox2, jComboBox3, jFormattedTextField1, jFormattedTextField2, jTextField5, jTextField8, jTextField7, jTextField4, jComboBox6, jTextField6});
        jPanel4.setLayout(jPanel4Layout);
        
        lblPessoas = new JLabel("Pessoas");
        
        
        
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2Layout.setHorizontalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addComponent(lblPessoas)
        			.addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblPessoas)
        			.addPreferredGap(ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
        );
        jPanel2.setLayout(jPanel2Layout);
        
        button.setText("Historico");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(0, 108, Short.MAX_VALUE)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED))
        						.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        							.addComponent(button, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
        							.addGap(7)
        							.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
        							.addGap(9)))
        					.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        				.addComponent(jScrollPanePrincipal, GroupLayout.PREFERRED_SIZE, 723, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jRadioButton1)
        				.addComponent(jRadioButton2))
        			.addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(Alignment.TRAILING, jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(jButton2)
        							.addComponent(jButton1)
        							.addComponent(jButton3)
        							.addComponent(jButton4))
        						.addComponent(button, Alignment.TRAILING))
        					.addGap(7)))
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPanePrincipal, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jRadioButton1)
        					.addGap(7)
        					.addComponent(jRadioButton2)))
        			.addContainerGap())
        );
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
