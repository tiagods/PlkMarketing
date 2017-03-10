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

import br.com.tiagods.view.interfaces.DefaultUtilities;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class RelatorioView extends JInternalFrame implements DefaultUtilities {
	private JPanel jPanel1;
		
	@Override
	public Color getColor() {
		return DefaultUtilities.super.getColor();		
	}
	public RelatorioView() {
	
		initComponents();
	}
	private void initComponents() {
		
		jPanel1 = new javax.swing.JPanel();
        setBounds(0, 0, 1250, 660);
        setBorder(null);
        jPanel1.setBackground(getColor());
        
        JPanel pnDetalhes = new JPanel();
        
        pnDetalhes.setBackground(getColor());
        
        JScrollPane scrollPane = new JScrollPane();
        
        JScrollPane scrollPane_1 = new JScrollPane();
        
        JCheckBox chckbxVisita = new JCheckBox("Visita");
        
        JCheckBox chckbxReunio = new JCheckBox("Reuni\u00E3o");
        
        JCheckBox chckbxProposta = new JCheckBox("Proposta");
        
        JCheckBox chckbxLigao = new JCheckBox("Liga\u00E7\u00E3o");
        
        JCheckBox chckbxEmail = new JCheckBox("E-mail");
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(pnDetalhes, GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        					.addGap(122)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(chckbxEmail, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        						.addComponent(chckbxLigao, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        						.addComponent(chckbxProposta, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        						.addComponent(chckbxReunio, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        						.addComponent(chckbxVisita, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(chckbxEmail)
        					.addComponent(chckbxLigao)
        					.addComponent(chckbxProposta)
        					.addComponent(chckbxReunio)
        					.addComponent(chckbxVisita))
        				.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
        			.addComponent(pnDetalhes, GroupLayout.PREFERRED_SIZE, 363, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        
        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        JCheckBox chckbxQuantidadeDeEmpresas = new JCheckBox("Quantidade de Empresas");
        JCheckBox chckbxQuantidadeDeNegocios = new JCheckBox("Quantidade de Negocios");
        JCheckBox chckbxQuantidadeDePessoas = new JCheckBox("Quantidade de Pessoas");
        JCheckBox chckbxQuantidadeDeTarefas = new JCheckBox("Quantidade de Tarefas");
        JCheckBox chckbxValoresDosNegcios = new JCheckBox("Valores dos Neg\u00F3cios");
        JCheckBox chckbxValoresDasTarefas = new JCheckBox("Valores das Tarefas");
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(chckbxQuantidadeDeEmpresas)
        				.addComponent(chckbxQuantidadeDeNegocios, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxQuantidadeDePessoas, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxQuantidadeDeTarefas, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxValoresDosNegcios, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxValoresDasTarefas, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(7)
        			.addComponent(chckbxQuantidadeDeEmpresas)
        			.addComponent(chckbxQuantidadeDeNegocios)
        			.addComponent(chckbxQuantidadeDePessoas)
        			.addComponent(chckbxQuantidadeDeTarefas)
        			.addComponent(chckbxValoresDosNegcios)
        			.addComponent(chckbxValoresDasTarefas))
        );
        panel.setLayout(gl_panel);
        
        JPanel panel_1 = new JPanel();
        scrollPane_1.setViewportView(panel_1);
        
        JCheckBox chckbxDepartamento = new JCheckBox("Departamento");
        
        JCheckBox chckbxUsurio = new JCheckBox("Usu\u00E1rio");
        
        JCheckBox chckbxAtendente = new JCheckBox("Atendente");
        
        JCheckBox chckbxEmpresa = new JCheckBox("Empresa");
        
        JCheckBox chckbxNegcio = new JCheckBox("Neg\u00F3cio");
        
        JCheckBox chckbxPessoa = new JCheckBox("Pessoa");
        
        JCheckBox chckbxMs = new JCheckBox("M\u00EAs");
        
        JCheckBox chckbxAno = new JCheckBox("Ano");
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        				.addComponent(chckbxDepartamento)
        				.addComponent(chckbxUsurio, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxAtendente, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxEmpresa, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxNegcio, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxPessoa, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxMs, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chckbxAno, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
        );
        gl_panel_1.setVerticalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addGap(7)
        			.addComponent(chckbxDepartamento)
        			.addComponent(chckbxUsurio)
        			.addComponent(chckbxAtendente)
        			.addComponent(chckbxEmpresa)
        			.addComponent(chckbxNegcio)
        			.addComponent(chckbxPessoa)
        			.addComponent(chckbxMs)
        			.addComponent(chckbxAno))
        );
        panel_1.setLayout(gl_panel_1);
        pnDetalhes.setLayout(null);
        
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
