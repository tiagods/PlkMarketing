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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JMenuItem;

public class MenuView extends JFrame {
	ControllerMenu controller = new ControllerMenu();
    public static javax.swing.JDesktopPane jDBody;
    private javax.swing.JPanel jPanel1;
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
        jDBody = new javax.swing.JDesktopPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

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
        		.addComponent(jDBody, GroupLayout.DEFAULT_SIZE, 1263, Short.MAX_VALUE)
        );
        gl_jPanel1.setVerticalGroup(
        	gl_jPanel1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addComponent(jDBody, GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        			.addContainerGap())
        );
        jPanel1.setLayout(gl_jPanel1);

        setSize(new Dimension(1280, 718));
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(250,250,250));
        setJMenuBar(menuBar);
        
        JMenu mnInicio = new JMenu("Inicio");
        mnInicio.setName("Inicio");
        mnInicio.addMouseListener(controller);
        menuBar.add(mnInicio);
        
        
        JMenu mnTarefas = new JMenu("Tarefas");
        menuBar.add(mnTarefas);
        
        JMenuItem itemNovaTarefa = new JMenuItem("Nova Tarefa");
        itemNovaTarefa.setActionCommand("TarefasSave");
        itemNovaTarefa.addActionListener(controller);
        mnTarefas.add(itemNovaTarefa);
        
        JMenuItem itemListarTarefas = new JMenuItem("Listar Tarefas");
        itemListarTarefas.setActionCommand("Tarefas");
        itemListarTarefas.addActionListener(controller);
        mnTarefas.add(itemListarTarefas);
        
        JMenu mnEmpresas = new JMenu("Empresas");
        mnEmpresas.setName("Empresas");
        mnEmpresas.addMouseListener(controller);
        menuBar.add(mnEmpresas);
        
        JMenu mnNegocios = new JMenu("Neg\u00F3cios");
        mnNegocios.setName("Negocios");
        mnNegocios.addMouseListener(controller);
        
        JMenu mnPessoas = new JMenu("Pessoas");
        mnPessoas.setName("Pessoas");
        mnPessoas.addMouseListener(controller);
        menuBar.add(mnPessoas);
        menuBar.add(mnNegocios);
        
        JMenu mnRelatorios = new JMenu("Relat\u00F3rios");
        mnRelatorios.setName("Relatorios");
        mnRelatorios.addMouseListener(controller);
        menuBar.add(mnRelatorios);
        
        JMenu mnExtra = new JMenu("Extra");
        menuBar.add(mnExtra);

        
	}
}
