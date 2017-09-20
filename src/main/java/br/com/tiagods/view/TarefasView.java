package br.com.tiagods.view;

import java.awt.Color;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerTarefas;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.view.interfaces.DefaultComboBox;
import br.com.tiagods.view.interfaces.DefaultUtilities;

import javax.swing.DefaultComboBoxModel;
import java.awt.Font;;

public class TarefasView extends JInternalFrame implements DefaultUtilities{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2621283794064432106L;
	public static JDateChooser jData1;
	public static JDateChooser jData2;
	public static javax.swing.JButton btNovaTarefa, btExportarFiltro;
	public static javax.swing.JCheckBox ckEmail;
	public static javax.swing.JCheckBox ckProposta;
	public static javax.swing.JCheckBox ckReuniao;
	public static javax.swing.JCheckBox ckTelefone;
	public static javax.swing.JCheckBox ckWhatsApp;
	public static DefaultComboBox cbAtendentes;
    public static javax.swing.JLabel txContador;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public static javax.swing.JPanel pnData;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JRadioButton rbDefinirData;
    public static javax.swing.JCheckBox ckPendentes;
    public static javax.swing.JCheckBox ckFinalizados;
    public static javax.swing.JRadioButton rbTudo;
    public static javax.swing.JRadioButton rbEssaSemana;
    public static javax.swing.JRadioButton rbHoje;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable tbPrincipal;
    private javax.swing.ButtonGroup group;
    
    ControllerTarefas controller = new ControllerTarefas();
    
    @Override
    public Color getColor() {
    	return DefaultUtilities.super.getColor();
    }

    public TarefasView(Date data1, Date data2, Usuario usuario) {
        initComponents();
        controller.iniciar(data1,data2,usuario);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	group = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel4.setBounds(89, 281, 1057, 323);
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane3.setBounds(10, 11, 1040, 308);
        tbPrincipal = new javax.swing.JTable();
        ckPendentes = new javax.swing.JCheckBox();
        ckPendentes.setBounds(481, 125, 105, 25);
        ckFinalizados = new javax.swing.JCheckBox();
        ckFinalizados.setBounds(610, 125, 105, 25);
        btNovaTarefa = new javax.swing.JButton();
        btNovaTarefa.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btNovaTarefa.setBounds(98, 245, 126, 25);
        rbTudo = new javax.swing.JRadioButton();
        rbTudo.setBounds(135, 58, 73, 25);
        group.add(rbTudo);
        rbEssaSemana = new javax.swing.JRadioButton();
        rbEssaSemana.setBounds(294, 58, 120, 25);
        group.add(rbEssaSemana);
        rbHoje = new javax.swing.JRadioButton();
        rbHoje.setBounds(214, 58, 78, 25);
        group.add(rbHoje);
        rbDefinirData = new javax.swing.JRadioButton();
        rbDefinirData.setBounds(429, 58, 73, 25);
        group.add(rbDefinirData);
        txContador = new javax.swing.JLabel();
        txContador.setBounds(832, 14, 144, 25);
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setBounds(1001, 14, 30, 25);
        cbAtendentes = new DefaultComboBox();
        cbAtendentes.setBounds(1041, 15, 105, 25);
        pnData = new javax.swing.JPanel();
        pnData.setVisible(false);
        pnData.setBounds(508, 59, 331, 25);
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        
        ckWhatsApp = new javax.swing.JCheckBox();
        ckWhatsApp.setBounds(523, 10, 120, 25);
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setBounds(89, 14, 40, 14);
        ckEmail = new javax.swing.JCheckBox();
        ckEmail.setBounds(135, 10, 73, 25);
        ckProposta = new javax.swing.JCheckBox();
        ckProposta.setBounds(219, 10, 90, 25);
        ckReuniao = new javax.swing.JCheckBox();
        ckReuniao.setBounds(317, 10, 90, 25);
        ckTelefone = new javax.swing.JCheckBox();
        ckTelefone.setBounds(421, 10, 90, 25);
        jData1 = new JDateChooser();
        jData2 = new JDateChooser();
        
        setBorder(null);

        jPanel1.setBackground(getColor());

        jPanel4.setBackground(getColor());

        tbPrincipal.setGridColor(getColor());
        jScrollPane3.setViewportView(tbPrincipal);

        ckPendentes.setBackground(getColor());
        ckPendentes.setText("Pendentes");
        ckPendentes.setActionCommand("Status");
        ckPendentes.addActionListener(controller);
        
        ckFinalizados.setBackground(getColor());
        ckFinalizados.setText("Finalizadas");
        ckFinalizados.setActionCommand("Status");
        ckFinalizados.addActionListener(controller);
       
        btNovaTarefa.setText("Criar Tarefa");
        btNovaTarefa.setName("CriarTarefa");
        btNovaTarefa.setActionCommand("CriarTarefa");
        btNovaTarefa.addActionListener(controller);
        
        String commandPrazo ="Prazo";
        
        rbTudo.setBackground(getColor());
        rbTudo.setText("Tudo");
        rbTudo.setActionCommand(commandPrazo);
        rbTudo.addActionListener(controller);
        
        rbEssaSemana.setBackground(getColor());
        rbEssaSemana.setText("Essa Semana");
        rbEssaSemana.setActionCommand(commandPrazo);
        rbEssaSemana.addActionListener(controller);
        
        rbHoje.setBackground(getColor());
        rbHoje.setText("Hoje");
        rbHoje.setActionCommand(commandPrazo);
        rbHoje.addActionListener(controller);
        
        rbDefinirData.setBackground(getColor());
        rbDefinirData.setText("Definir");
        rbDefinirData.setActionCommand(commandPrazo);
        rbDefinirData.addActionListener(controller);
        
        txContador.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel2.setText("de:");

        cbAtendentes.setBackground(getColor());
        cbAtendentes.setModel(new DefaultComboBoxModel(new String[] {"Todos"}));
        
        pnData.setBackground(getColor());

        jLabel4.setText("de:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("at\u00E9");

        javax.swing.GroupLayout grLayout = new javax.swing.GroupLayout(pnData);
        grLayout.setHorizontalGroup(
        	grLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(grLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel4)
        			.addGap(4)
        			.addComponent(jData1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        			.addGap(13)
        			.addComponent(jLabel5)
        			.addGap(8)
        			.addComponent(jData2, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        			.addGap(53))
        );
        grLayout.setVerticalGroup(
        	grLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(grLayout.createSequentialGroup()
        			.addGroup(grLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jData1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
        				.addComponent(jData2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
        				.addComponent(jLabel4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
        			.addGap(11))
        		.addGroup(Alignment.LEADING, grLayout.createSequentialGroup()
        			.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        pnData.setLayout(grLayout);
        
        jLabel3.setText("Filtro:");

        String commandFiltro= "TipoTarefa";
        ckEmail.setBackground(getColor());
        ckEmail.setText("E-mail");
        ckEmail.setName("Email");
        ckEmail.setActionCommand(commandFiltro);
        ckEmail.addMouseListener(controller);

        ckProposta.setBackground(getColor());
        ckProposta.setText("Proposta");
        ckProposta.setName("Proposta");
        ckProposta.setActionCommand(commandFiltro);
        ckProposta.addMouseListener(controller);
        
        ckReuniao.setBackground(getColor());
        ckReuniao.setText("Reuni\u00E3o");
        ckReuniao.setName("Reuniao");
        ckReuniao.setActionCommand(commandFiltro);
        ckReuniao.addMouseListener(controller);
        
        ckTelefone.setBackground(getColor());
        ckTelefone.setText("Telefone");
        ckTelefone.setName("Telefone");
        ckTelefone.setActionCommand(commandFiltro);
        ckTelefone.addMouseListener(controller);
        
        ckWhatsApp.setBackground(getColor());
        ckWhatsApp.setText("WhatsApp");
        ckWhatsApp.setName("WhatsApp");
        ckWhatsApp.setActionCommand(commandFiltro);
        ckWhatsApp.addMouseListener(controller);
        
        jPanel4.setLayout(null);
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
        jPanel1.add(ckEmail);
        jPanel1.add(ckProposta);
        jPanel1.add(ckReuniao);
        jPanel1.add(ckTelefone);
        jPanel1.add(ckWhatsApp);
        jPanel1.add(txContador);
        jPanel1.add(jLabel2);
        jPanel1.add(cbAtendentes);
        jPanel1.add(ckPendentes);
        jPanel1.add(ckFinalizados);
        jPanel1.add(btNovaTarefa);
        jPanel1.add(rbTudo);
        jPanel1.add(rbHoje);
        jPanel1.add(rbEssaSemana);
        jPanel1.add(rbDefinirData);
        jPanel1.add(pnData);
        btExportarFiltro = new JButton("Exportar");
        btExportarFiltro.setActionCommand("Exportar");
        btExportarFiltro.setBounds(1001, 59, 145, 23);
        btExportarFiltro.addActionListener(controller);
        jPanel1.add(btExportarFiltro);
        setBounds(0, 0, 1250, 660);
    }
}
