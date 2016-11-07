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

public class SelecaoObjeto extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tbRelacao;

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
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "name_49072879791600");
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
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
		setLocationRelativeTo(null);
	}

}
