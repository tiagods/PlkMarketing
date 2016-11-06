package br.com.tiagods.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerInicio;
import br.com.tiagods.view.interfaces.DefaultUtilities;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;

public class InicioView extends JInternalFrame implements DefaultUtilities {
	private JPanel jPanel1;
    private JLabel lblAte;
	
    public static JLabel lbInfoTarefas;
    public static JPanel pnStatus;
    public static JDateChooser jData1;
	public static JDateChooser jData2;
	public static JComboBox cbAtendentes;
	
	ControllerInicio controller = new ControllerInicio();
	
	@Override
	public Color getColor() {
		return DefaultUtilities.super.getColor();		
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioView frame = new InicioView();
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
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGap(43)
        					.addComponent(pnStatus, GroupLayout.PREFERRED_SIZE, 525, GroupLayout.PREFERRED_SIZE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(pnDetalhes, GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(23)
        			.addComponent(pnStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pnDetalhes, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        			.addContainerGap())
        );
        lbInfoTarefas = new JLabel();
        lbInfoTarefas.setBounds(0, 5, 1230, 549);
        lbInfoTarefas.setHorizontalAlignment(SwingConstants.CENTER);
        lbInfoTarefas.addMouseListener(controller);
        pnDetalhes.setLayout(null);
        pnDetalhes.add(lbInfoTarefas);
        
        lbInfoTarefas.setFont(new Font("Dialog", Font.BOLD, 26)); // NOI18N
        lbInfoTarefas.setText("Voc\u00EA tem ### tarefas hoje");
        
        pnStatus.add(lblCriadoPor);
        
        cbAtendentes = new JComboBox();
        pnStatus.add(cbAtendentes);
        
        JLabel lblDe = new JLabel("de:");
        pnStatus.add(lblDe);
        
        pnStatus.add(jData1);
        
        lblAte = new JLabel("at\u00E9:");
        pnStatus.add(lblAte);
        pnStatus.add(jData2);
        
        JButton btnOk = new JButton("OK");
        btnOk.setActionCommand("Filtrar");
        btnOk.setName("OK");
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
