package br.com.tiagods.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.modelDAO.MyDAO;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;

import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelecaoObjeto extends JDialog implements DefaultModelComboBox{
	
	@Override
	public Object getObject(String valor) {
		// TODO Auto-generated method stub
		return DefaultModelComboBox.super.getObject(valor);
	}
	private final JPanel contentPanel = new JPanel();
	private JTable tbRelacao;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JTextField textField;
/**
	 * Create the dialog.
	 */
	
	public SelecaoObjeto(Object object, JLabel labelId, JLabel labelNome) {
		initComponents(labelId, labelNome);
		if(object != null){
			if(object instanceof Empresa){
				Session session = HibernateFactory.getSession();
				session.beginTransaction();
				List<Empresa> lista = new MyDAO().listar("Empresa", session);
				String[] colunas = {"ID", "Nome"};
				String[][] linhas = new String[lista.size()][colunas.length];
				
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				tbRelacao.setModel(new DefaultTableModel(linhas,colunas));
				session.close();
			}
			else if(object instanceof Negocio){
				Session session = HibernateFactory.getSession();
				session.beginTransaction();
				List<Negocio> lista = new MyDAO().listar("Negocio", session);
				String[] colunas = {"ID", "Nome"};
				String[][] linhas = new String[lista.size()][colunas.length];
				
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				tbRelacao.setModel(new DefaultTableModel(linhas,colunas));
				session.close();
			}
			else if (object instanceof Pessoa){
				Session session = HibernateFactory.getSession();
				session.beginTransaction();
				List<Pessoa> lista = new MyDAO().listar("Pessoa", session);
				String[] colunas = {"ID", "Nome"};
				String[][] linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				tbRelacao.setModel(new DefaultTableModel(linhas,colunas));
				session.close();
			}
		}
	}
	public void initComponents(JLabel labelId, JLabel labelNome){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 38, 434, 191);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			scrollPane = new JScrollPane();
			{
				tbRelacao = new JTable();
				tbRelacao.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						int column = tbRelacao.getSelectedColumn();
						int row = tbRelacao.getSelectedRow();
						
						String texto="";
						texto+=tbRelacao.getModel().getValueAt(row, column)+",";
					}
				});
				tbRelacao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tbRelacao.setFillsViewportHeight(true);
				tbRelacao.setSelectionBackground(Color.GREEN);
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
				JButton btOkDialog = new JButton("OK");
				btOkDialog.setActionCommand("OK");
				btOkDialog.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(tbRelacao.getSelectedRow()!=-1){
							labelId.setText((String)tbRelacao.getValueAt(tbRelacao.getSelectedRow(), 0));
							labelNome.setText((String)tbRelacao.getValueAt(tbRelacao.getSelectedRow(), 1));
							dispose();
						}
					}});
				buttonPane.add(btOkDialog);
				getRootPane().setDefaultButton(btOkDialog);
			}
			{
				JButton btCancelDialog = new JButton("Cancel");
				btCancelDialog.setActionCommand("Cancel");
				btCancelDialog.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dispose();
					}
				});
				buttonPane.add(btCancelDialog);
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
