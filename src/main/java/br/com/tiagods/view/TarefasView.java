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
import br.com.tiagods.view.interfaces.DefaultUtilities;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;;

public class TarefasView extends JInternalFrame implements DefaultUtilities{
	public static JDateChooser jData1;
	public static JDateChooser jData2;
	public static javax.swing.JButton btNovaTarefa;
	public static javax.swing.JButton jButton1;
	public static javax.swing.JCheckBox ckVisita;
	public static javax.swing.JCheckBox ckReuniao;
	public static javax.swing.JCheckBox ckProposta;
	public static javax.swing.JCheckBox ckTelefone;
	public static javax.swing.JCheckBox ckEmail;
	public static javax.swing.JComboBox<String> cbAtendentes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel pnData;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JRadioButton rbDefinirData;
    public static javax.swing.JRadioButton rbPendentes;
    public static javax.swing.JRadioButton rbFinalizadas;
    public static javax.swing.JRadioButton rbTudo;
    public static javax.swing.JRadioButton rbEssaSemana;
    public static javax.swing.JRadioButton rbHoje;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JTable tbPrincipal;
    private javax.swing.ButtonGroup group;
    
    ControllerTarefas controller = new ControllerTarefas();
    
    @Override
    public Color getColor() {
    	// TODO Auto-generated method stub
    	return DefaultUtilities.super.getColor();
    }

    public TarefasView(Date data1, Date data2, Usuario usuario) {
        initComponents();
        controller.iniciar(data1,data2,usuario);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	group = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel4.setBounds(10, 281, 1060, 323);
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane3.setBounds(84, 11, 966, 308);
        tbPrincipal = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel15.setBounds(5, 273, 69, 45);
        rbPendentes = new javax.swing.JRadioButton();
        rbPendentes.setBounds(420, 95, 93, 23);
        rbFinalizadas = new javax.swing.JRadioButton();
        rbFinalizadas.setBounds(515, 95, 93, 23);
        btNovaTarefa = new javax.swing.JButton();
        btNovaTarefa.setBounds(10, 52, 105, 24);
        rbTudo = new javax.swing.JRadioButton();
        rbTudo.setBounds(121, 53, 60, 23);
        group.add(rbTudo);
        rbEssaSemana = new javax.swing.JRadioButton();
        rbEssaSemana.setBounds(251, 53, 105, 23);
        group.add(rbEssaSemana);
        rbHoje = new javax.swing.JRadioButton();
        rbHoje.setBounds(183, 53, 60, 23);
        group.add(rbHoje);
        rbDefinirData = new javax.swing.JRadioButton();
        rbDefinirData.setBounds(362, 53, 73, 23);
        group.add(rbDefinirData);
        jLabel1 = new javax.swing.JLabel();
        jLabel1.setBounds(669, 10, 90, 23);
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setBounds(782, 10, 30, 23);
        cbAtendentes = new javax.swing.JComboBox<>();
        cbAtendentes.setBounds(822, 11, 105, 20);
        pnData = new javax.swing.JPanel();
        pnData.setBounds(441, 52, 331, 23);
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        ckEmail = new javax.swing.JCheckBox();
        ckEmail.setBounds(392, 10, 73, 23);
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setBounds(10, 14, 40, 14);
        ckVisita = new javax.swing.JCheckBox();
        ckVisita.setBounds(56, 10, 73, 23);
        ckReuniao = new javax.swing.JCheckBox();
        ckReuniao.setBounds(131, 10, 73, 23);
        ckProposta = new javax.swing.JCheckBox();
        ckProposta.setBounds(208, 10, 90, 23);
        ckTelefone = new javax.swing.JCheckBox();
        ckTelefone.setBounds(300, 10, 90, 23);
        jData1 = new JDateChooser();
        jData2 = new JDateChooser();
        
        setBorder(null);

        jPanel1.setBackground(getColor());

        jPanel4.setBackground(getColor());

        tbPrincipal.setGridColor(getColor());
        jScrollPane3.setViewportView(tbPrincipal);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("{###}");

        rbPendentes.setBackground(getColor());
        rbPendentes.setText("Pendentes");

        rbFinalizadas.setBackground(getColor());
        rbFinalizadas.setText("Finalizadas");
        ButtonGroup groupEstado = new ButtonGroup();
        groupEstado.add(rbFinalizadas);
        groupEstado.add(rbPendentes);
        
        btNovaTarefa.setText("Criar Tarefa");
        btNovaTarefa.setActionCommand("Criar Tarefa");
        btNovaTarefa.addActionListener(controller);
        rbTudo.setBackground(getColor());
        rbTudo.setText("Tudo");
        
        rbEssaSemana.setBackground(getColor());
        rbEssaSemana.setText("Essa Semana");
        
        rbHoje.setBackground(getColor());
        rbHoje.setText("Hoje");
        
        rbDefinirData.setBackground(getColor());
        rbDefinirData.setText("Definir");
        
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("{#### Tarefas}");

        jLabel2.setText("de:");

        cbAtendentes.setBackground(getColor());
        cbAtendentes.setModel(new DefaultComboBoxModel(new String[] {"Todos"}));
        
        pnData.setBackground(getColor());

        jLabel4.setText("de:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("até");

        jButton1.setText("OK");

        javax.swing.GroupLayout gl_pnData = new javax.swing.GroupLayout(pnData);
        gl_pnData.setHorizontalGroup(
        	gl_pnData.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnData.createSequentialGroup()
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
        gl_pnData.setVerticalGroup(
        	gl_pnData.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, gl_pnData.createSequentialGroup()
        			.addGroup(gl_pnData.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jData1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        				.addComponent(jButton1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 12, Short.MAX_VALUE)
        				.addComponent(jData2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jLabel5, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
        			.addGap(11))
        );
        pnData.setLayout(gl_pnData);

        ckEmail.setBackground(getColor());
        ckEmail.setText("E-mail");

        jLabel3.setText("Filtro:");

        ckVisita.setBackground(getColor());
        ckVisita.setText("Visita");

        ckReuniao.setBackground(getColor());
        ckReuniao.setText("Reunião");

        ckProposta.setBackground(getColor());
        ckProposta.setText("Proposta");

        ckTelefone.setBackground(getColor());
        ckTelefone.setText("Telefone");
        jPanel4.setLayout(null);
        jPanel4.add(jLabel15);
        jPanel4.add(jScrollPane3);

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
        jPanel1.add(jLabel3);
        jPanel1.add(ckVisita);
        jPanel1.add(ckReuniao);
        jPanel1.add(ckProposta);
        jPanel1.add(ckTelefone);
        jPanel1.add(ckEmail);
        jPanel1.add(jLabel1);
        jPanel1.add(jLabel2);
        jPanel1.add(cbAtendentes);
        jPanel1.add(rbPendentes);
        jPanel1.add(rbFinalizadas);
        jPanel1.add(btNovaTarefa);
        jPanel1.add(rbTudo);
        jPanel1.add(rbHoje);
        jPanel1.add(rbEssaSemana);
        jPanel1.add(rbDefinirData);
        jPanel1.add(pnData);
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setBounds(766, 142, 171, 126);
        jPanel1.add(jScrollPane1);
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
                jScrollPane1.setVisible(false);
        setBounds(0, 0, 1250, 660);
    }

}
