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
        
        JPanel panel_2 = new JPanel();
        
        JPanel panel_1 = new JPanel();
        
        JCheckBox chckbxDepartamento = new JCheckBox("Departamento");
        
        JCheckBox chckbxUsurio = new JCheckBox("Criado Por");
        
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
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
        						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addComponent(pnDetalhes, GroupLayout.PREFERRED_SIZE, 363, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        panel_2.setLayout(null);
        
        JCheckBox chckbxVisita = new JCheckBox("Visita");
        chckbxVisita.setBounds(6, 99, 145, 23);
        panel_2.add(chckbxVisita);
        
        JCheckBox chckbxReunio = new JCheckBox("Reuni\u00E3o");
        chckbxReunio.setBounds(6, 76, 145, 23);
        panel_2.add(chckbxReunio);
        
        JCheckBox chckbxProposta = new JCheckBox("Proposta");
        chckbxProposta.setBounds(6, 53, 145, 23);
        panel_2.add(chckbxProposta);
        
        JCheckBox chckbxLigao = new JCheckBox("Liga\u00E7\u00E3o");
        chckbxLigao.setBounds(6, 30, 145, 23);
        panel_2.add(chckbxLigao);
        
        JCheckBox chckbxEmail = new JCheckBox("E-mail");
        chckbxEmail.setBounds(6, 7, 145, 23);
        panel_2.add(chckbxEmail);
        
        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        JCheckBox chckbxQuantidadeDeEmpresas = new JCheckBox("Quantidade de Empresas");
        chckbxQuantidadeDeEmpresas.setBounds(6, 40, 145, 23);
        JCheckBox chckbxQuantidadeDeNegocios = new JCheckBox("Quantidade de Negocios");
        chckbxQuantidadeDeNegocios.setBounds(6, 63, 145, 23);
        JCheckBox chckbxQuantidadeDePessoas = new JCheckBox("Quantidade de Pessoas");
        chckbxQuantidadeDePessoas.setBounds(6, 86, 145, 23);
        JCheckBox chckbxQuantidadeDeTarefas = new JCheckBox("Quantidade de Tarefas");
        chckbxQuantidadeDeTarefas.setBounds(6, 109, 145, 23);
        JCheckBox chckbxValoresDosNegcios = new JCheckBox("Valores dos Neg\u00F3cios");
        chckbxValoresDosNegcios.setBounds(6, 132, 145, 23);
        JCheckBox chckbxValoresDasTarefas = new JCheckBox("Descri\u00E7\u00E3o das Tarefas");
        chckbxValoresDasTarefas.setBounds(6, 155, 145, 23);
        panel.setLayout(null);
        panel.add(chckbxQuantidadeDeEmpresas);
        panel.add(chckbxQuantidadeDeNegocios);
        panel.add(chckbxQuantidadeDePessoas);
        panel.add(chckbxQuantidadeDeTarefas);
        panel.add(chckbxValoresDosNegcios);
        panel.add(chckbxValoresDasTarefas);
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
