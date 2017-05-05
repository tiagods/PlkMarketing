package br.com.tiagods.view;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerRelatorios;
import br.com.tiagods.view.interfaces.DefaultUtilities;


@SuppressWarnings("serial")
public class RelatorioView extends JInternalFrame implements DefaultUtilities {
	public static JPanel pnPrincipal;
	ControllerRelatorios controller	= new ControllerRelatorios();
	@Override
	public Color getColor() {
		return DefaultUtilities.super.getColor();		
	}
	public RelatorioView() {
		initComponents();
		controller.iniciar();
	}
	private void initComponents() {
		
		pnPrincipal = new javax.swing.JPanel();
        setBounds(0, 0, 1250, 660);
        setBorder(null);
        pnPrincipal.setBackground(getColor());
        
        JPanel pnModulos = new JPanel();
        pnModulos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Escolha um M\u00F3dulo:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        JPanel pnTarefas = new JPanel();
        
        javax.swing.GroupLayout gl_pnPrincipal = new javax.swing.GroupLayout(pnPrincipal);
        gl_pnPrincipal.setHorizontalGroup(
        	gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(pnModulos, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
        			.addGap(31)
        			.addComponent(pnTarefas, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(417, Short.MAX_VALUE))
        );
        gl_pnPrincipal.setVerticalGroup(
        	gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addGroup(gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pnPrincipal.createSequentialGroup()
        					.addGap(21)
        					.addComponent(pnModulos, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnPrincipal.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(pnTarefas, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(396, Short.MAX_VALUE))
        );
        pnTarefas.setLayout(null);
        
        JPanel pnTarefasRelatorioTipo = new JPanel();
        pnTarefasRelatorioTipo.setLayout(null);
        pnTarefasRelatorioTipo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tipo:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnTarefasRelatorioTipo.setBounds(220, 130, 175, 80);
        pnTarefas.add(pnTarefasRelatorioTipo);
        
        JRadioButton rdbtnResumo = new JRadioButton("Resumida");
        rdbtnResumo.setBounds(17, 19, 109, 23);
        pnTarefasRelatorioTipo.add(rdbtnResumo);
        
        JRadioButton rdbtnDetalhada = new JRadioButton("Detalhada");
        rdbtnDetalhada.setBounds(17, 45, 109, 23);
        pnTarefasRelatorioTipo.add(rdbtnDetalhada);
        
        JPanel pnTarefasOrdenar = new JPanel();
        pnTarefasOrdenar.setLayout(null);
        pnTarefasOrdenar.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ordenar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnTarefasOrdenar.setBounds(220, 10, 175, 80);
        pnTarefas.add(pnTarefasOrdenar);
        
        JRadioButton rbTarefasAtendenteOrdenar = new JRadioButton("Atendente");
        rbTarefasAtendenteOrdenar.setBounds(17, 19, 109, 23);
        pnTarefasOrdenar.add(rbTarefasAtendenteOrdenar);
        
        JRadioButton rbTarefasStatusOrdenar = new JRadioButton("Status");
        rbTarefasStatusOrdenar.setBounds(17, 45, 109, 23);
        pnTarefasOrdenar.add(rbTarefasStatusOrdenar);
        
        JPanel pnTarefasDatar = new JPanel();
        pnTarefasDatar.setBorder(new TitledBorder(null, "Per\u00EDodo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTarefasDatar.setLayout(null);
        pnTarefasDatar.setOpaque(false);
        pnTarefasDatar.setBackground(new Color(250, 250, 250));
        pnTarefasDatar.setBounds(430, 10, 175, 80);
        pnTarefas.add(pnTarefasDatar);
        
        JLabel lbData1 = new JLabel();
        lbData1.setText("de:");
        lbData1.setHorizontalAlignment(SwingConstants.LEFT);
        lbData1.setBounds(10, 27, 22, 20);
        pnTarefasDatar.add(lbData1);
        
        JLabel lbData2 = new JLabel();
        lbData2.setText("at\u00E9");
        lbData2.setHorizontalAlignment(SwingConstants.LEFT);
        lbData2.setBounds(10, 49, 22, 20);
        pnTarefasDatar.add(lbData2);
        
        JDateChooser data1 = new JDateChooser();
        data1.setBounds(36, 27, 100, 20);
        pnTarefasDatar.add(data1);
        
        JDateChooser data2 = new JDateChooser();
        data2.setBounds(36, 49, 100, 20);
        pnTarefasDatar.add(data2);
        
        JButton btnTarefasExportar = new JButton("Exportar");
        btnTarefasExportar.setBounds(510, 187, 89, 23);
        pnTarefas.add(btnTarefasExportar);
        
        JButton btnTarefasRelatorio = new JButton("Relat\u00F3rio");
        btnTarefasRelatorio.setBounds(510, 153, 89, 23);
        pnTarefas.add(btnTarefasRelatorio);
        
        JScrollPane spTarefasFiltro = new JScrollPane();
        spTarefasFiltro.setBounds(10, 10, 200, 200);
        pnTarefas.add(spTarefasFiltro);
        
        JPanel pnTarefasFiltro = new JPanel();
        spTarefasFiltro.setViewportView(pnTarefasFiltro);
        pnTarefasFiltro.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filtro:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        JCheckBox ckTarefasVisita = new JCheckBox("Visita");
        
        JCheckBox ckTarefasReuniao = new JCheckBox("Reuni\u00E3o");
        
        JCheckBox ckTarefasProposta = new JCheckBox("Proposta");
        
        JCheckBox ckTarefasLigacao = new JCheckBox("Liga\u00E7\u00E3o");
        
        JCheckBox ckTarefasEmailFiltro = new JCheckBox("E-mail");
        
        JCheckBox ckTarefasFinalizadasFiltro = new JCheckBox("Tarefas Finalizadas");
        
        JCheckBox ckTarefasPendentesFiltro = new JCheckBox("Tarefas Pendentes");
        
        JCheckBox ckTarefasEmpresasFiltro = new JCheckBox("Empresas");
        
        JCheckBox ckTarefaNegociosFiltro = new JCheckBox("Neg\u00F3cios");
        
        JCheckBox ckTarefasPessoasFiltro = new JCheckBox("Pessoas");
        
        JCheckBox ckTarefasProspeccaoFiltro = new JCheckBox("Prospec\u00E7\u00E3o");
        
        JButton btnTarefasIncluir = new JButton("Avancar");
        GroupLayout gl_pnTarefasFiltro = new GroupLayout(pnTarefasFiltro);
        gl_pnTarefasFiltro.setHorizontalGroup(
        	gl_pnTarefasFiltro.createParallelGroup(Alignment.LEADING)
        		.addComponent(ckTarefasEmailFiltro, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasLigacao, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasProposta, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasReuniao, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasVisita, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasFinalizadasFiltro, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasPendentesFiltro, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasEmpresasFiltro, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefaNegociosFiltro, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasPessoasFiltro, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
        		.addComponent(ckTarefasProspeccaoFiltro, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
        		.addGroup(Alignment.TRAILING, gl_pnTarefasFiltro.createSequentialGroup()
        			.addContainerGap(39, Short.MAX_VALUE)
        			.addComponent(btnTarefasIncluir, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
        			.addGap(35))
        );
        gl_pnTarefasFiltro.setVerticalGroup(
        	gl_pnTarefasFiltro.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnTarefasFiltro.createSequentialGroup()
        			.addComponent(btnTarefasIncluir)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(ckTarefasEmailFiltro)
        			.addComponent(ckTarefasLigacao)
        			.addComponent(ckTarefasProposta)
        			.addComponent(ckTarefasReuniao)
        			.addComponent(ckTarefasVisita)
        			.addGap(3)
        			.addComponent(ckTarefasFinalizadasFiltro)
        			.addGap(2)
        			.addComponent(ckTarefasPendentesFiltro)
        			.addGap(3)
        			.addComponent(ckTarefasEmpresasFiltro)
        			.addGap(3)
        			.addComponent(ckTarefaNegociosFiltro)
        			.addGap(3)
        			.addComponent(ckTarefasPessoasFiltro)
        			.addGap(3)
        			.addComponent(ckTarefasProspeccaoFiltro))
        );
        pnTarefasFiltro.setLayout(gl_pnTarefasFiltro);
        pnModulos.setLayout(null);
        ButtonGroup groupModulos = new ButtonGroup();
        JRadioButton rbEmpresas = new JRadioButton("Empresas");
        rbEmpresas.setBounds(16, 52, 109, 23);
        pnModulos.add(rbEmpresas);
        groupModulos.add(rbEmpresas);
        JRadioButton rbNegocios = new JRadioButton("Neg\u00F3cios");
        rbNegocios.setBounds(16, 78, 109, 23);
        pnModulos.add(rbNegocios);
        groupModulos.add(rbNegocios);
        JRadioButton rbPessoas = new JRadioButton("Pessoas");
        rbPessoas.setBounds(16, 104, 109, 23);
        pnModulos.add(rbPessoas);
        
        JRadioButton rbProspeccao = new JRadioButton("Prospec\u00E7\u00E3o");
        rbProspeccao.setBounds(16, 130, 109, 23);
        pnModulos.add(rbProspeccao);
        
        JRadioButton rbTarefas = new JRadioButton("Tarefas");
        rbTarefas.setBounds(16, 156, 109, 23);
        pnModulos.add(rbTarefas);
        
        JButton btnAvancarModulo = new JButton("Avancar");
        btnAvancarModulo.setBounds(76, 22, 89, 23);
        pnModulos.add(btnAvancarModulo);
        
        pnPrincipal.setLayout(gl_pnPrincipal);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
}
