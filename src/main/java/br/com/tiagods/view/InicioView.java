package br.com.tiagods.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerInicio;
import br.com.tiagods.view.interfaces.DefaultComboBox;
import br.com.tiagods.view.interfaces.DefaultUtilities;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
@SuppressWarnings("serial")
public class InicioView extends JInternalFrame implements DefaultUtilities {
	private JPanel jPanel1;
    private JLabel lblAte;
	
    public static JLabel lbInfoTarefas;
    public static JPanel pnStatus;
    public static JDateChooser jData1,jData2,dataResumo1, dataResumo2;
	public static DefaultComboBox cbAtendentes;
	public static JButton btnOk, btnOkResumo;
	public static JTable tbNegocios, tbTarefas,tbAcessos;
	
	ControllerInicio controller = new ControllerInicio();
	@Override
	public Color getColor() {
		return DefaultUtilities.super.getColor();		
	}
	public InicioView() {
	
		initComponents();
		controller.iniciar();
	}
	private void initComponents() {
		
		jPanel1 = new javax.swing.JPanel();
        jData1 = new JDateChooser();
        jData1.setPreferredSize(new Dimension(100, 20));
        jData2 = new JDateChooser();        
        jData2.setPreferredSize(new Dimension(100, 20));
        setBounds(0, 0, 1250, 660);
        setBorder(null);

        JLabel lblCriadoPor = new JLabel("Criado por:");
        jPanel1.setBackground(getColor());
        
        pnStatus = new JPanel();
        
        pnStatus.setBackground(getColor());
        
        JPanel pnDetalhes = new JPanel();
        
        pnDetalhes.setBackground(getColor());
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(pnDetalhes, GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
        				.addComponent(pnStatus, GroupLayout.PREFERRED_SIZE, 525, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(18)
        			.addComponent(pnStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(pnDetalhes, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addContainerGap())
        );
        
        lbInfoTarefas = new JLabel();
        lbInfoTarefas.setBounds(0, 5, 916, 547);
        lbInfoTarefas.setHorizontalAlignment(SwingConstants.CENTER);
        lbInfoTarefas.setName("Info");
        lbInfoTarefas.addMouseListener(controller);
        pnDetalhes.setLayout(null);
        pnDetalhes.add(lbInfoTarefas);
        
        lbInfoTarefas.setFont(new Font("Dialog", Font.BOLD, 26)); // NOI18N
        lbInfoTarefas.setText("Voc\u00EA tem ### tarefas hoje");
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Resumo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(926, 11, 294, 541);
        panel.setBackground(new Color(250,250,250));
        pnDetalhes.add(panel);
        
        JScrollPane spNegocios = new JScrollPane();
        spNegocios.setBounds(12, 84, 272, 115);
        spNegocios.setOpaque(false);
        
        tbNegocios = new JTable();
        tbNegocios.setOpaque(false);
        spNegocios.setViewportView(tbNegocios);
        
        JLabel lblNewLabel = new JLabel("Neg\u00F3cios");
        lblNewLabel.setBounds(12, 59, 272, 14);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblTarefas = new JLabel("Tarefas");
        lblTarefas.setBounds(12, 210, 272, 14);
        lblTarefas.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTarefas.setHorizontalAlignment(SwingConstants.CENTER);
        
        JScrollPane spTarefas = new JScrollPane();
        spTarefas.setBounds(12, 230, 272, 200);
        spTarefas.setOpaque(false);
        
        tbTarefas = new JTable();
        tbTarefas.setOpaque(false);
        spTarefas.setViewportView(tbTarefas);
        panel.setLayout(null);
        panel.add(lblNewLabel);
        panel.add(spNegocios);
        panel.add(lblTarefas);
        panel.add(spTarefas);
        
        dataResumo1 = new JDateChooser();
        dataResumo1.setPreferredSize(new Dimension(100, 20));
        dataResumo1.setBounds(12, 28, 100, 20);
        panel.add(dataResumo1);
        
        JLabel label = new JLabel("at\u00E9:");
        label.setBounds(117, 31, 33, 14);
        panel.add(label);
        
        dataResumo2 = new JDateChooser();
        dataResumo2.setPreferredSize(new Dimension(100, 20));
        dataResumo2.setBounds(152, 28, 100, 20);
        panel.add(dataResumo2);
        
        btnOkResumo = new JButton("");
        btnOkResumo.setActionCommand("FiltrarResumo");
        btnOkResumo.addActionListener(controller);
        btnOkResumo.setBounds(252, 21, 33, 27);
        panel.add(btnOkResumo);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.setBounds(12, 441, 272, 89);
        panel.add(scrollPane);
        
        tbAcessos = new JTable();
        scrollPane.setViewportView(tbAcessos);
        
        pnStatus.add(lblCriadoPor);
        
        cbAtendentes = new DefaultComboBox();
        pnStatus.add(cbAtendentes);
        
        JLabel lblDe = new JLabel("de:");
        pnStatus.add(lblDe);
        
        pnStatus.add(jData1);
        
        lblAte = new JLabel("at\u00E9:");
        pnStatus.add(lblAte);
        pnStatus.add(jData2);
        
        btnOk = new JButton("");
        btnOk.setActionCommand("Filtrar");
        btnOk.addActionListener(controller);
        pnStatus.add(btnOk);
        
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
    }
}
