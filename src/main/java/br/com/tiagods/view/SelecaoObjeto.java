package br.com.tiagods.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.EmpresaDao;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;

public class SelecaoObjeto extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tbRelacao;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SelecaoObjeto dialog = new SelecaoObjeto(new Empresa());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SelecaoObjeto(Object object) {
		initComponents();
		if(object != null){
			if(object instanceof Empresa){
				EmpresaDao eDao = new EmpresaDao();
				List<Empresa> lista = eDao.getLista();
				String[] colunas = {"ID", "Nome"};
				String[][] linhas = new String[lista.size()][colunas.length];
				
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				tbRelacao.setModel(new DefaultTableModel(linhas,colunas));
			}
			else if(object instanceof Negocio){
				
			}
			else if (object instanceof Pessoa){
				
			}
		}
	}
	public void initComponents(){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 38, 434, 191);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			scrollPane = new JScrollPane();
			{
				tbRelacao = new JTable();
				tbRelacao.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {

						}
						)
				{
					boolean[] canEdit = new boolean [] {
							false, false, false, false, false, false
					};

					public boolean isCellEditable(int rowIndex, int columnIndex) {
						return canEdit [columnIndex];
					}
				});
				scrollPane.setViewportView(tbRelacao);
			}
		}
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 229, 434, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			lblNewLabel = new JLabel("Buscar:");
			lblNewLabel.setBounds(10, 7, 48, 20);
			getContentPane().add(lblNewLabel);
		}
		
		textField = new JTextField();
		textField.setBounds(68, 7, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		setLocationRelativeTo(null);
	}
}
