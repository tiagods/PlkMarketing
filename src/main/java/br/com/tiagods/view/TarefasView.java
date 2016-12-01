package br.com.tiagods.view;

import java.awt.Color;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerTarefas;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.view.interfaces.DefaultUtilities;;

public class TarefasView extends JInternalFrame implements DefaultUtilities{
	public static JDateChooser jData1;
	public static JDateChooser jData2;
	public static javax.swing.JButton btTarefa;
	public static javax.swing.JButton jButton1;
	public static javax.swing.JButton jButton2;
	public static javax.swing.JButton jButton3;
	public static javax.swing.JCheckBox jCheckBox1;
	public static javax.swing.JCheckBox jCheckBox12;
	public static javax.swing.JCheckBox jCheckBox2;
	public static javax.swing.JCheckBox jCheckBox3;
	public static javax.swing.JCheckBox jCheckBox4;
	public static javax.swing.JCheckBox jCheckBox5;
	public static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPData;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JRadioButton jRDefinir;
    public static javax.swing.JRadioButton jRadioButton1;
    public static javax.swing.JRadioButton jRadioButton2;
    public static javax.swing.JRadioButton jRadioButton3;
    public static javax.swing.JRadioButton jRadioButton4;
    public static javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JTable tbPrincipal;
    public static javax.swing.JTextField jTextField4;
    private javax.swing.ButtonGroup group;
    public static JButton btnNewButton;
    
    ControllerTarefas controller = new ControllerTarefas();
    
    @Override
    public Color getColor() {
    	// TODO Auto-generated method stub
    	return DefaultUtilities.super.getColor();
    }

    public TarefasView(Date data1, Date data2, Usuario sessao) {
        initComponents();
        controller.iniciar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	group = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel4.setBounds(10, 281, 1060, 323);
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane3.setBounds(84, 11, 668, 308);
        tbPrincipal = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        jTextField4.setBounds(762, 11, 171, 20);
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox12.setBounds(762, 130, 100, 20);
        jLabel36 = new javax.swing.JLabel();
        jLabel36.setBounds(5, 50, 69, 14);
        jLabel15 = new javax.swing.JLabel();
        jLabel15.setBounds(5, 70, 69, 248);
        jLabel37 = new javax.swing.JLabel();
        jLabel37.setBounds(5, 16, 69, 28);
        jButton2 = new javax.swing.JButton();
        jButton2.setBounds(762, 37, 115, 23);
        jButton3 = new javax.swing.JButton();
        jButton3.setBounds(762, 69, 115, 23);
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton1.setBounds(420, 95, 93, 23);
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton2.setBounds(515, 95, 93, 23);
        btTarefa = new javax.swing.JButton();
        btTarefa.setBounds(10, 52, 105, 24);
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton3.setBounds(121, 53, 60, 23);
        group.add(jRadioButton3);
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton4.setBounds(251, 53, 105, 23);
        group.add(jRadioButton4);
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton5.setBounds(183, 53, 60, 23);
        group.add(jRadioButton5);
        jRDefinir = new javax.swing.JRadioButton();
        jRDefinir.setBounds(362, 53, 73, 23);
        group.add(jRDefinir);
        jLabel1 = new javax.swing.JLabel();
        jLabel1.setBounds(669, 10, 90, 23);
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setBounds(782, 10, 30, 23);
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox1.setBounds(822, 11, 105, 20);
        jPData = new javax.swing.JPanel();
        jPData.setBounds(441, 52, 331, 23);
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox5.setBounds(392, 10, 73, 23);
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setBounds(10, 14, 40, 14);
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox1.setBounds(56, 10, 73, 23);
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox2.setBounds(131, 10, 73, 23);
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox3.setBounds(208, 10, 90, 23);
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox4.setBounds(300, 10, 90, 23);
        jData1 = new JDateChooser();
        jData2 = new JDateChooser();
        
        setBorder(null);

        jPanel1.setBackground(getColor());

        jPanel4.setBackground(getColor());

        tbPrincipal.setGridColor(getColor());
        jScrollPane3.setViewportView(tbPrincipal);

        jCheckBox12.setBackground(getColor());
        jCheckBox12.setText("Finalizar");

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Quantidade:");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("{###}");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("{typ#}");

        jButton2.setText("Abrir #negocio empresa or pessoa#");

        jButton3.setText("Abrir Tarefa");

        jRadioButton1.setBackground(getColor());
        jRadioButton1.setText("Pendentes");

        jRadioButton2.setBackground(getColor());
        jRadioButton2.setText("Finalizadas");

        btTarefa.setText("Criar Tarefa");
        btTarefa.setActionCommand("Criar Tarefa");

        jRadioButton3.setBackground(getColor());
        jRadioButton3.setText("Tudo");
        
        jRadioButton4.setBackground(getColor());
        jRadioButton4.setText("Essa Semana");
        
        jRadioButton5.setBackground(getColor());
        jRadioButton5.setText("Hoje");
        
        jRDefinir.setBackground(getColor());
        jRDefinir.setText("Definir");
        
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("{#### Tarefas}");

        jLabel2.setText("de:");

        jComboBox1.setBackground(getColor());
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "#Pessoa 1", "#Pessoa 2", "#Pessoa 3", "#Pessoa 4", "Todos" }));
        
        jPData.setBackground(getColor());

        jLabel4.setText("de:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("até");

        jButton1.setText("OK");

        javax.swing.GroupLayout jPDataLayout = new javax.swing.GroupLayout(jPData);
        jPDataLayout.setHorizontalGroup(
        	jPDataLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPDataLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel4)
        			.addGap(4)
        			.addComponent(jData1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
        			.addGap(8)
        			.addComponent(jData2, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        			.addGap(6)
        			.addComponent(jButton1))
        );
        jPDataLayout.setVerticalGroup(
        	jPDataLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, jPDataLayout.createSequentialGroup()
        			.addGroup(jPDataLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jData1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        				.addComponent(jButton1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 12, Short.MAX_VALUE)
        				.addComponent(jData2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jLabel5, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
        			.addGap(11))
        );
        jPData.setLayout(jPDataLayout);

        jCheckBox5.setBackground(getColor());
        jCheckBox5.setText("E-mail");

        jLabel3.setText("Filtro:");

        jCheckBox1.setBackground(getColor());
        jCheckBox1.setText("Visita");

        jCheckBox2.setBackground(getColor());
        jCheckBox2.setText("Reunião");

        jCheckBox3.setBackground(getColor());
        jCheckBox3.setText("Proposta");

        jCheckBox4.setBackground(getColor());
        jCheckBox4.setText("Telefone");
        jPanel4.setLayout(null);
        jPanel4.add(jLabel37);
        jPanel4.add(jLabel36);
        jPanel4.add(jLabel15);
        jPanel4.add(jScrollPane3);
        jPanel4.add(jTextField4);
        jPanel4.add(jButton2);
        jPanel4.add(jCheckBox12);
        jPanel4.add(jButton3);
        
        btnNewButton = new JButton("Excluir");
        btnNewButton.setBounds(762, 100, 115, 23);
        jPanel4.add(btnNewButton);

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
        jPanel1.add(jPanel4);
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setBounds(762, 163, 171, 126);
        jPanel4.add(jScrollPane1);
        jTable1 = new javax.swing.JTable();
        
                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {
        
                    },
                    new String [] {
                        "Tipo", "Qtde"
                    }
                ) {
                    boolean[] canEdit = new boolean [] {
                        false, false
                    };
        
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                });
                jTable1.setGridColor(new java.awt.Color(255, 255, 255));
                jScrollPane1.setViewportView(jTable1);
        jPanel1.add(jLabel3);
        jPanel1.add(jCheckBox1);
        jPanel1.add(jCheckBox2);
        jPanel1.add(jCheckBox3);
        jPanel1.add(jCheckBox4);
        jPanel1.add(jCheckBox5);
        jPanel1.add(jLabel1);
        jPanel1.add(jLabel2);
        jPanel1.add(jComboBox1);
        jPanel1.add(jRadioButton1);
        jPanel1.add(jRadioButton2);
        jPanel1.add(btTarefa);
        jPanel1.add(jRadioButton3);
        jPanel1.add(jRadioButton5);
        jPanel1.add(jRadioButton4);
        jPanel1.add(jRDefinir);
        jPanel1.add(jPData);

        setBounds(0, 0, 1250, 660);
    }

}
