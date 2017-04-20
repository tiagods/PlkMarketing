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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class ProspeccaoView extends JInternalFrame {
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTable table_1;
	private JTextField textField_7;
	private JTextField textField_8;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProspeccaoView frame = new ProspeccaoView();
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
	public ProspeccaoView() {
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
		
		JLabel lblResponsavel = new JLabel("Responsavel:");
		lblResponsavel.setBounds(10, 91, 86, 14);
		pnCadastro.add(lblResponsavel);
		
		JLabel lblDepartamento = new JLabel("Departamento:");
		lblDepartamento.setBounds(333, 60, 86, 14);
		pnCadastro.add(lblDepartamento);
		
		JLabel lblSite = new JLabel("Site:");
		lblSite.setBounds(10, 144, 86, 14);
		pnCadastro.add(lblSite);
		
		JLabel lblEmail = new JLabel("E-Mail:");
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
		textField_6.setBounds(421, 57, 86, 20);
		pnCadastro.add(textField_6);
		
		JLabel lblTipoContato = new JLabel("Tipo de Contato:");
		lblTipoContato.setBounds(333, 147, 86, 14);
		pnCadastro.add(lblTipoContato);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Nenhum", "E-Mail", "Mala Direta", "Mala Direta e E-Mail"}));
		comboBox.setBounds(421, 144, 86, 20);
		pnCadastro.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(421, 169, 86, 20);
		pnCadastro.add(comboBox_1);
		
		JLabel lblLista = new JLabel("Lista:");
		lblLista.setBounds(333, 172, 86, 14);
		pnCadastro.add(lblLista);
		
		JButton btnExportarMalaDireta = new JButton("Exportar Mala Direta");
		btnExportarMalaDireta.setBounds(572, 10, 150, 23);
		pnCadastro.add(btnExportarMalaDireta);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(228, 331, 279, 174);
		pnCadastro.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		textField_7 = new JTextField();
		textField_7.setBounds(228, 274, 43, 20);
		pnCadastro.add(textField_7);
		textField_7.setColumns(10);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Apresenta\u00E7\u00F5es", "Material", "Newsletter"}));
		comboBox_2.setBounds(281, 274, 187, 20);
		pnCadastro.add(comboBox_2);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(472, 273, 35, 23);
		pnCadastro.add(btnNewButton);
		
		JLabel lblModeloAceito = new JLabel("Perfil de Contato:");
		lblModeloAceito.setBounds(228, 241, 279, 14);
		pnCadastro.add(lblModeloAceito);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(106, 169, 86, 20);
		pnCadastro.add(textField_8);
		
		JButton button = new JButton("New button");
		button.setBounds(472, 305, 35, 23);
		pnCadastro.add(button);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(432, 305, 35, 23);
		pnCadastro.add(button_1);
	}
}
