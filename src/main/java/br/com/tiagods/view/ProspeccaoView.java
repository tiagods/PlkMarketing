package br.com.tiagods.view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.tiagods.model.Prospeccao;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class ProspeccaoView extends JInternalFrame {
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_8;
	private JTextField textField_7;
	private JTextField txBuscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProspeccaoView frame = new ProspeccaoView(null);
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
	public ProspeccaoView(Prospeccao prospeccao) {
		setBounds(0, 0, 1250, 660);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 1214, 608);
		panel.add(tabbedPane);
		
		JPanel pnPesquisa = new JPanel();
		tabbedPane.addTab("Pesquisa", null, pnPesquisa, null);
		pnPesquisa.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 116, 947, 263);
		pnPesquisa.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setName("Tipo de Contato");
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"Tipo de Contato"}));
		comboBox_3.setBounds(10, 11, 110, 20);
		pnPesquisa.add(comboBox_3);
		
		JButton button = new JButton("Exportar Mala Direta");
		button.setEnabled(false);
		button.setBounds(807, 56, 150, 23);
		pnPesquisa.add(button);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setName("Origem");
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"Origem"}));
		comboBox_4.setBounds(130, 11, 97, 20);
		pnPesquisa.add(comboBox_4);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setName("Lista");
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"Lista"}));
		comboBox_5.setBounds(237, 11, 97, 20);
		pnPesquisa.add(comboBox_5);
		
		txBuscar = new JTextField();
		txBuscar.setBounds(66, 57, 86, 20);
		pnPesquisa.add(txBuscar);
		txBuscar.setColumns(10);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setBounds(10, 63, 46, 14);
		pnPesquisa.add(lblBuscar);
		
		JLabel lblPor = new JLabel("por:");
		lblPor.setHorizontalAlignment(SwingConstants.CENTER);
		lblPor.setBounds(162, 63, 46, 14);
		pnPesquisa.add(lblPor);
		
		JComboBox cbPesquisarPor = new JComboBox();
		cbPesquisarPor.setModel(new DefaultComboBoxModel(new String[] {"ID", "Nome", "Responsavel"}));
		cbPesquisarPor.setBounds(218, 57, 103, 20);
		pnPesquisa.add(cbPesquisarPor);
		
		JLabel lblClassificarPor = new JLabel("Classificar por:");
		lblClassificarPor.setBounds(366, 60, 110, 14);
		pnPesquisa.add(lblClassificarPor);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setToolTipText("Voc\u00EA consegue organizar com duplo clique no nome da coluna dentro da tabela abaixo, mas campos como DATA E CODIGO n\u00E3o s\u00E3o possiveis de ordenar, por isso dispon\u00EDvel essa fun\u00E7\u00E3o atrav\u00E9s dessa caixa.");
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"C\u00F3digo", "Data de Cadastro"}));
		comboBox_6.setBounds(494, 57, 121, 20);
		pnPesquisa.add(comboBox_6);
		
		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setName("Atendente");
		comboBox_7.setModel(new DefaultComboBoxModel(new String[] {"Atendente"}));
		comboBox_7.setBounds(344, 11, 97, 20);
		pnPesquisa.add(comboBox_7);
		
		JCheckBox chckbxApresentao = new JCheckBox("Convite para Eventos");
		chckbxApresentao.setBounds(447, 10, 142, 23);
		pnPesquisa.add(chckbxApresentao);
		
		JCheckBox chckbxMaterial_1 = new JCheckBox("Material");
		chckbxMaterial_1.setBounds(591, 11, 97, 23);
		pnPesquisa.add(chckbxMaterial_1);
		
		JCheckBox chckbxNewsletter_1 = new JCheckBox("Newsletter");
		chckbxNewsletter_1.setBounds(690, 11, 97, 23);
		pnPesquisa.add(chckbxNewsletter_1);
		
		JRadioButton rdbtnCrescente = new JRadioButton("Crescente");
		rdbtnCrescente.setBounds(623, 56, 109, 23);
		pnPesquisa.add(rdbtnCrescente);
		
		JRadioButton rdbtnDecrescente = new JRadioButton("Decrescente");
		rdbtnDecrescente.setBounds(623, 82, 109, 23);
		pnPesquisa.add(rdbtnDecrescente);
		
		JButton btnExpMailmktLocaweb = new JButton("Exp. EmailMkt Locaweb");
		btnExpMailmktLocaweb.setEnabled(false);
		btnExpMailmktLocaweb.setBounds(807, 82, 150, 23);
		pnPesquisa.add(btnExpMailmktLocaweb);
		
		JPanel pnCadastro = new JPanel();
		tabbedPane.addTab("Cadastro", null, pnCadastro, null);
		pnCadastro.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 86, 20);
		pnCadastro.add(textField);
		textField.setColumns(10);
		
		JLabel lblEmpresa = new JLabel("Empresa:");
		lblEmpresa.setBounds(10, 60, 86, 14);
		pnCadastro.add(lblEmpresa);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(10, 116, 86, 14);
		pnCadastro.add(lblTelefone);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(10, 172, 86, 14);
		pnCadastro.add(lblEndereo);
		
		JLabel lblResponsavel = new JLabel("Nome Contato:");
		lblResponsavel.setBounds(10, 91, 86, 14);
		pnCadastro.add(lblResponsavel);
		
		JLabel lblDepartamento = new JLabel("Departamento:");
		lblDepartamento.setBounds(333, 88, 86, 14);
		pnCadastro.add(lblDepartamento);
		
		JLabel lbMail = new JLabel("E-Mail:");
		lbMail.setBounds(10, 144, 86, 14);
		pnCadastro.add(lbMail);
		
		JLabel lblEmail = new JLabel("Celular:");
		lblEmail.setBounds(333, 116, 86, 14);
		pnCadastro.add(lblEmail);
		
		textField_1 = new JTextField();
		textField_1.setBounds(106, 57, 86, 20);
		pnCadastro.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(106, 85, 86, 20);
		pnCadastro.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(106, 113, 86, 20);
		pnCadastro.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(106, 141, 86, 20);
		pnCadastro.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(421, 113, 86, 20);
		pnCadastro.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(421, 85, 86, 20);
		pnCadastro.add(textField_6);
		
		JLabel lblTipoContato = new JLabel("Tipo de Contato:");
		lblTipoContato.setBounds(333, 175, 86, 14);
		pnCadastro.add(lblTipoContato);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Nenhum", "E-Mail", "Mala Direta", "E-Mail e Mala Direta"}));
		comboBox.setBounds(421, 172, 86, 20);
		pnCadastro.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(421, 197, 86, 20);
		pnCadastro.add(comboBox_1);
		
		JLabel lblLista = new JLabel("Lista:");
		lblLista.setBounds(333, 200, 86, 14);
		pnCadastro.add(lblLista);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(106, 169, 86, 20);
		pnCadastro.add(textField_8);
		
		JCheckBox chckbxApresentaes = new JCheckBox("Convite para Eventos");
		chckbxApresentaes.setBounds(333, 231, 140, 23);
		pnCadastro.add(chckbxApresentaes);
		
		JCheckBox chckbxMaterial = new JCheckBox("Material");
		chckbxMaterial.setBounds(333, 257, 97, 23);
		pnCadastro.add(chckbxMaterial);
		
		JCheckBox chckbxNewsletter = new JCheckBox("Newsletter");
		chckbxNewsletter.setBounds(333, 283, 97, 23);
		pnCadastro.add(chckbxNewsletter);
		
		JLabel lbSite = new JLabel("Site:");
		lbSite.setBounds(333, 144, 86, 14);
		pnCadastro.add(lbSite);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(421, 141, 86, 20);
		pnCadastro.add(textField_7);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 329, 497, 240);
		pnCadastro.add(tabbedPane_1);
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("Origem", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblOrigem = new JLabel("Origem:");
		lblOrigem.setBounds(10, 81, 46, 14);
		panel_2.add(lblOrigem);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(175, 78, 130, 20);
		panel_2.add(comboBox_2);
		
		JLabel lblNewLabel = new JLabel("Detalhes da Origem:");
		lblNewLabel.setBounds(10, 109, 155, 14);
		panel_2.add(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setToolTipText("");
		scrollPane_1.setBounds(175, 109, 307, 92);
		panel_2.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		JTextPane txtpncomoTeveAcesso = new JTextPane();
		txtpncomoTeveAcesso.setOpaque(false);
		txtpncomoTeveAcesso.setEditable(false);
		txtpncomoTeveAcesso.setText("Como teve acesso ao contato?\r\nLigou para empresa, enviou e-mail, indica\u00E7\u00E3o de algu\u00E9m, assinou newsletter, foi passado pelo marketing etc.");
		txtpncomoTeveAcesso.setBounds(10, 11, 472, 59);
		panel_2.add(txtpncomoTeveAcesso);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("Resumo", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setToolTipText("");
		scrollPane_2.setBounds(175, 70, 307, 131);
		panel_1.add(scrollPane_2);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_2.setViewportView(textArea_1);
		
		JLabel lblResumoDoContato = new JLabel("Resumo do Contato:");
		lblResumoDoContato.setBounds(10, 70, 155, 14);
		panel_1.add(lblResumoDoContato);
		
		JTextPane txtpnTenteIrPara = new JTextPane();
		txtpnTenteIrPara.setText("Tente ir para a reuni\u00E3o j\u00E1 sabendo o que espera por voc\u00EA, o que o cliente deseja e anote aqui um resumo de sua impress\u00E3o.");
		txtpnTenteIrPara.setOpaque(false);
		txtpnTenteIrPara.setEditable(false);
		txtpnTenteIrPara.setBounds(10, 11, 472, 48);
		panel_1.add(txtpnTenteIrPara);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Apresenta\u00E7\u00E3o sob Medida", null, panel_3, null);
		panel_3.setLayout(null);
		
		JTextPane txtpnTentePrepararUm = new JTextPane();
		txtpnTentePrepararUm.setText("Tente preparar um material que se encaixe perfeitamente com as necessidades do cliente, resuma aqui os pontos principais de sua argumenta\u00E7\u00E3o.");
		txtpnTentePrepararUm.setOpaque(false);
		txtpnTentePrepararUm.setEditable(false);
		txtpnTentePrepararUm.setBounds(10, 11, 472, 48);
		panel_3.add(txtpnTentePrepararUm);
		
		JLabel lblApresentao = new JLabel("Apresenta\u00E7\u00E3o:");
		lblApresentao.setBounds(10, 70, 155, 14);
		panel_3.add(lblApresentao);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setToolTipText("");
		scrollPane_3.setBounds(175, 70, 307, 131);
		panel_3.add(scrollPane_3);
		
		JTextArea textArea_2 = new JTextArea();
		scrollPane_3.setViewportView(textArea_2);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(517, 196, 35, 23);
		pnCadastro.add(btnNewButton);
	}
}
