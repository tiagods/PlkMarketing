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
	public static JPanel pnPrincipal,pnTarefas,pnModulos;
    public static JRadioButton rbEmpresas,rbNegocios,rbPessoas,rbProspeccao,rbTarefas;
    public static JRadioButton rbTarefasAtendenteOrdenar,rbTarefasStatusOrdenar,rdTarefasResumo,rbTarefasDetalhes;
    public static JCheckBox ckTarefasVisita,ckTarefasReuniao,ckTarefasProposta,ckTarefasLigacao,
    ckTarefasEmailFiltro,ckTarefasFinalizadasFiltro,ckTarefasPendentesFiltro,
    ckTarefasEmpresasFiltro,ckTarefaNegociosFiltro,ckTarefasPessoasFiltro,ckTarefasProspeccaoFiltro;
    public static JScrollPane spTarefasFiltro;
    public static JPanel pnTarefasFiltro;

    
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
        
        pnModulos = new JPanel();
        pnModulos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Escolha um M\u00F3dulo:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        pnTarefas = new JPanel();
        pnTarefas.setOpaque(false);
        
        JButton btnTarefasExportar = new JButton("Exportar");
        JButton btnTarefasRelatorio = new JButton("Relat\u00F3rio");
        
        javax.swing.GroupLayout gl_pnPrincipal = new javax.swing.GroupLayout(pnPrincipal);
        gl_pnPrincipal.setHorizontalGroup(
        	gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(pnModulos, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
        			.addGap(31)
        			.addComponent(pnTarefas, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        				.addComponent(btnTarefasRelatorio, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btnTarefasExportar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(318, Short.MAX_VALUE))
        );
        gl_pnPrincipal.setVerticalGroup(
        	gl_pnPrincipal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addGap(21)
        			.addComponent(pnModulos, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(pnTarefas, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE))
        		.addGroup(gl_pnPrincipal.createSequentialGroup()
        			.addGap(164)
        			.addComponent(btnTarefasRelatorio)
        			.addGap(11)
        			.addComponent(btnTarefasExportar))
        );
        pnTarefas.setLayout(null);
        
        spTarefasFiltro = new JScrollPane();
        spTarefasFiltro.setOpaque(false);
        spTarefasFiltro.setBounds(10, 10, 200, 200);
        pnTarefas.add(spTarefasFiltro);
        
        pnTarefasFiltro = new JPanel();
        pnTarefasFiltro.setOpaque(false);
        spTarefasFiltro.setViewportView(pnTarefasFiltro);
        pnTarefasFiltro.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filtro:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        ckTarefasVisita = new JCheckBox("Visita");
        ckTarefasReuniao = new JCheckBox("Reuni\u00E3o");
        ckTarefasProposta = new JCheckBox("Proposta");
        ckTarefasLigacao = new JCheckBox("Liga\u00E7\u00E3o");
        ckTarefasEmailFiltro = new JCheckBox("E-mail");
        ckTarefasFinalizadasFiltro = new JCheckBox("Tarefas Finalizadas");
        ckTarefasPendentesFiltro = new JCheckBox("Tarefas Pendentes");
        ckTarefasEmpresasFiltro = new JCheckBox("Empresas");
        ckTarefaNegociosFiltro = new JCheckBox("Neg\u00F3cios");
        ckTarefasPessoasFiltro = new JCheckBox("Pessoas");
        ckTarefasProspeccaoFiltro = new JCheckBox("Prospec\u00E7\u00E3o");
        
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
        JPanel pnTarefasOrdenar = new JPanel();
        pnTarefasOrdenar.setOpaque(false);
        pnTarefasOrdenar.setLayout(null);
        pnTarefasOrdenar.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
        		"Ordenar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnTarefasOrdenar.setBounds(220, 10, 175, 80);
        pnTarefas.add(pnTarefasOrdenar);
        
        ButtonGroup groupTarefaOrdenacao = new ButtonGroup();
        
        rbTarefasAtendenteOrdenar = new JRadioButton("Atendente");
        rbTarefasAtendenteOrdenar.setBounds(17, 19, 109, 23);
        pnTarefasOrdenar.add(rbTarefasAtendenteOrdenar);
        groupTarefaOrdenacao.add(rbTarefasAtendenteOrdenar);
        
        rbTarefasStatusOrdenar = new JRadioButton("Status");
        rbTarefasStatusOrdenar.setBounds(17, 45, 109, 23);
        pnTarefasOrdenar.add(rbTarefasStatusOrdenar);
        groupTarefaOrdenacao.add(rbTarefasStatusOrdenar);
        
        JPanel pnTarefasRelatorioTipo = new JPanel();
        pnTarefasRelatorioTipo.setOpaque(false);
        pnTarefasRelatorioTipo.setLayout(null);
        pnTarefasRelatorioTipo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
        		"Tipo:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnTarefasRelatorioTipo.setBounds(220, 130, 175, 80);
        pnTarefas.add(pnTarefasRelatorioTipo);
        
        ButtonGroup groupTarefaTipo = new ButtonGroup();
        rdTarefasResumo = new JRadioButton("Resumida");
        rdTarefasResumo.setBounds(17, 19, 109, 23);
        pnTarefasRelatorioTipo.add(rdTarefasResumo);
        groupTarefaTipo.add(rdTarefasResumo);
        
        rbTarefasDetalhes = new JRadioButton("Detalhada");
        rbTarefasDetalhes.setBounds(17, 45, 109, 23);
        pnTarefasRelatorioTipo.add(rbTarefasDetalhes);
        groupTarefaTipo.add(rbTarefasDetalhes);
        
        JPanel pnTarefasData = new JPanel();
        pnTarefasData.setBorder(new TitledBorder(null, "Per\u00EDodo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTarefasData.setLayout(null);
        pnTarefasData.setOpaque(false);
        pnTarefasData.setBackground(new Color(250, 250, 250));
        pnTarefasData.setBounds(430, 10, 175, 80);
        pnTarefas.add(pnTarefasData);
        
        JLabel lbTarefasData1 = new JLabel();
        lbTarefasData1.setText("de:");
        lbTarefasData1.setHorizontalAlignment(SwingConstants.LEFT);
        lbTarefasData1.setBounds(10, 27, 22, 20);
        pnTarefasData.add(lbTarefasData1);
        
        JLabel lbTarefasData2 = new JLabel();
        lbTarefasData2.setText("at\u00E9");
        lbTarefasData2.setHorizontalAlignment(SwingConstants.LEFT);
        lbTarefasData2.setBounds(10, 49, 22, 20);
        pnTarefasData.add(lbTarefasData2);
        
        JDateChooser dataTarefas1 = new JDateChooser();
        dataTarefas1.setBounds(36, 27, 100, 20);
        pnTarefasData.add(dataTarefas1);
        
        JDateChooser dataTarefas2 = new JDateChooser();
        dataTarefas2.setBounds(36, 49, 100, 20);
        pnTarefasData.add(dataTarefas2);
        pnModulos.setLayout(null);
        
        ButtonGroup groupModulos = new ButtonGroup();
        
        rbEmpresas = new JRadioButton("Empresas");
        rbEmpresas.setEnabled(false);
        rbEmpresas.setBounds(16, 52, 109, 23);
        pnModulos.add(rbEmpresas);
        groupModulos.add(rbEmpresas);
        rbNegocios = new JRadioButton("Neg\u00F3cios");
        rbNegocios.setEnabled(false);
        rbNegocios.setBounds(16, 78, 109, 23);
        pnModulos.add(rbNegocios);
        groupModulos.add(rbNegocios);
        rbPessoas = new JRadioButton("Pessoas");
        rbPessoas.setEnabled(false);
        rbPessoas.setBounds(16, 104, 109, 23);
        pnModulos.add(rbPessoas);
        groupModulos.add(rbPessoas);
        rbProspeccao = new JRadioButton("Prospec\u00E7\u00E3o");
        rbProspeccao.setEnabled(false);
        rbProspeccao.setBounds(16, 130, 109, 23);
        pnModulos.add(rbProspeccao);
        groupModulos.add(rbProspeccao);
        rbTarefas = new JRadioButton("Tarefas");
        rbTarefas.setBounds(16, 156, 109, 23);
        pnModulos.add(rbTarefas);
        groupModulos.add(rbTarefas);
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
