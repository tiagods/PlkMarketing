package br.com.tiagods.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import br.com.tiagods.controller.ControllerMenu;
import java.awt.Dimension;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuView extends JFrame {
	ControllerMenu controller = new ControllerMenu();
	
    private javax.swing.JButton btInicio;
    private javax.swing.JButton btnEmpresas;
    private javax.swing.JButton btnNegocio;
    private javax.swing.JButton btnPessoas;
    private javax.swing.JButton btnRelatorios;
    private javax.swing.JButton btnTarefas;
    public static javax.swing.JDesktopPane jDBody;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuView frame = new MenuView();
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
	public MenuView() {
		initComponents();
		controller.Inicia();
	}
	public void initComponents(){
		setAlwaysOnTop(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300);

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btInicio = new javax.swing.JButton();
        btnTarefas = new javax.swing.JButton();
        btnNegocio = new javax.swing.JButton();
        
        btnEmpresas = new javax.swing.JButton();
        btnPessoas = new javax.swing.JButton();
        btnRelatorios = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jDBody = new javax.swing.JDesktopPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setPreferredSize(new java.awt.Dimension(136, 430));

        btInicio.setText("Inicio");
        btInicio.setActionCommand("Inicio");
        btInicio.addActionListener(controller);

        btnTarefas.setText("Tarefas");
        btnTarefas.setActionCommand("Tarefas");
        btnTarefas.addActionListener(controller);

        btnNegocio.setText("Neg\u00F3cios");
        btnNegocio.setActionCommand("Negocios");
        btnNegocio.addActionListener(controller);

        btnEmpresas.setText("Empresas");
        btnEmpresas.setActionCommand("Empresas");
        btnEmpresas.addActionListener(controller);

        btnPessoas.setText("Pessoas");
        btnPessoas.setActionCommand("Pessoas");
        btnPessoas.addActionListener(controller);

        btnRelatorios.setText("Relat\u00F3rios");
        btnRelatorios.setActionCommand("Relatorio");
        btnRelatorios.addActionListener(controller);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Marketing");

        jDBody.setBackground(new java.awt.Color(250, 250, 250));
        jDBody.setBounds(10, 0, 100, 100);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        GroupLayout gl_jPanel1 = new GroupLayout(jPanel1);
        gl_jPanel1.setHorizontalGroup(
        	gl_jPanel1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 1272, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_jPanel1.createSequentialGroup()
        					.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(jDBody, GroupLayout.PREFERRED_SIZE, 1080, GroupLayout.PREFERRED_SIZE)))
        			.addGap(2))
        );
        gl_jPanel1.setVerticalGroup(
        	gl_jPanel1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_jPanel1.createSequentialGroup()
        					.addGap(137)
        					.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 505, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_jPanel1.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jDBody, GroupLayout.PREFERRED_SIZE, 620, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        
        JButton btnFuncoes = new JButton();
        btnFuncoes.setText("Fun\u00E7\u00F5es");
        GroupLayout gl_jPanel2 = new GroupLayout(jPanel2);
        gl_jPanel2.setHorizontalGroup(
        	gl_jPanel2.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanel2.createSequentialGroup()
        			.addGap(21)
        			.addGroup(gl_jPanel2.createParallelGroup(Alignment.LEADING)
        				.addComponent(btnFuncoes, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_jPanel2.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(btnEmpresas, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        					.addComponent(btnNegocio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(btnTarefas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(btInicio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(btnPessoas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(btnRelatorios, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        			.addContainerGap(28, Short.MAX_VALUE))
        );
        gl_jPanel2.setVerticalGroup(
        	gl_jPanel2.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanel2.createSequentialGroup()
        			.addGap(75)
        			.addComponent(btInicio, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnTarefas, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnNegocio, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnEmpresas, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnPessoas, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnRelatorios, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnFuncoes, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel2.setLayout(gl_jPanel2);
        jPanel1.setLayout(gl_jPanel1);

        setSize(new Dimension(1280, 718));
        setLocationRelativeTo(null);

	}
}
