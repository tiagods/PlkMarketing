package br.com.tiagods.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import br.com.tiagods.controller.ControllerMenu;
import br.com.tiagods.view.interfaces.DefaultUtilities;

public class MenuView extends JFrame implements DefaultUtilities{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ControllerMenu controller = ControllerMenu.getInstance();
	
    public static javax.swing.JDesktopPane jDBody;
    private javax.swing.JPanel pnPrincipal;
    
    static MenuView instance;
    
    public static MenuView getInstance(){
    	return instance;
    }
    
    @Override
    public Color getColor() {
    	return DefaultUtilities.super.getColor();
    }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					instance = new MenuView();
					instance.setVisible(true);
				} catch (RuntimeException e) {e.getMessage();}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuView() {
		initComponents();
		controller.iniciar();
	}
	public void initComponents(){
		getContentPane().setBackground(getColor());
		//setBounds(100, 100, 450, 300);

        pnPrincipal = new javax.swing.JPanel();
        jDBody = new javax.swing.JDesktopPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnPrincipal.setBackground(getColor());

        jDBody.setBackground(getColor());
        jDBody.setBounds(10, 0, 100, 100);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        GroupLayout groupLayoutPrincipal = new GroupLayout(pnPrincipal);
        groupLayoutPrincipal.setHorizontalGroup(
        	groupLayoutPrincipal.createParallelGroup(Alignment.LEADING)
        		.addComponent(jDBody, GroupLayout.DEFAULT_SIZE, 1263, Short.MAX_VALUE)
        );
        groupLayoutPrincipal.setVerticalGroup(
        	groupLayoutPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayoutPrincipal.createSequentialGroup()
        			.addComponent(jDBody, GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        			.addContainerGap())
        );
        pnPrincipal.setLayout(groupLayoutPrincipal);

        setSize(new Dimension(1280, 723));
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(getColor());
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
        
        JMenuItem itemListarTarefa = new JMenuItem("Listar Tarefas");
        itemListarTarefa.setActionCommand("Tarefas");
        itemListarTarefa.addActionListener(controller);
        mnTarefas.add(itemListarTarefa);
        
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
        mnExtra.setName("Extra");
        mnExtra.addMouseListener(controller);
        menuBar.add(mnExtra);
	}
}
