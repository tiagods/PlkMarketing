package br.com.tiagods.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import br.com.tiagods.model.VersaoSistema;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class TarefasDialog extends JDialog {

	/**
	 * 
	 */
	private JDateChooser jdateChooser;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	VersaoSistema versao = new VersaoSistema();
	
	public TarefasDialog(MenuView view, boolean modal) {
		super(view,modal);
		initComponents();
	}
	public void initComponents(){
		setBounds(100, 100, 500, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbTitulo = new JLabel("Rela\u00E7\u00E3o de Tarefas");
		lbTitulo.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitulo.setBounds(10, 11, 464, 25);
		contentPanel.add(lbTitulo);
		
		ImageIcon iconTheme = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/theme.png"));
		int Nalt = 120;
		iconTheme.setImage(iconTheme.getImage().getScaledInstance(iconTheme.getIconWidth()/iconTheme.getIconHeight()*Nalt, Nalt, 100));
		JButton okButton = new JButton("");
		okButton.setBounds(427, 279, 50, 25);
		contentPanel.add(okButton);
		okButton.setActionCommand("OK");
		
		ImageIcon iconOk = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/button_ok.png"));
		okButton.setIcon(recalculate(iconOk));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getRootPane().setDefaultButton(okButton);
		
		JRadioButton rdbtnMs = new JRadioButton("Atendente");
		rdbtnMs.setBounds(247, 84, 109, 23);
		contentPanel.add(rdbtnMs);
		
		JButton btnTxt_1 = new JButton("txt");
		btnTxt_1.setActionCommand("OK");
		btnTxt_1.setBounds(367, 279, 50, 25);
		contentPanel.add(btnTxt_1);
		
		JButton btnTxt = new JButton("xls");
		btnTxt.setActionCommand("OK");
		btnTxt.setBounds(307, 279, 50, 25);
		contentPanel.add(btnTxt);
		
		JButton btnXls = new JButton("");
		btnXls.setEnabled(false);
		btnXls.setBounds(247, 279, 50, 25);
		contentPanel.add(btnXls);
		
		JRadioButton rdbtnData = new JRadioButton("Data");
		rdbtnData.setBounds(247, 110, 109, 23);
		contentPanel.add(rdbtnData);
		
		JRadioButton radioButton_1 = new JRadioButton("Negocio");
		radioButton_1.setBounds(247, 136, 109, 23);
		contentPanel.add(radioButton_1);
		setLocationRelativeTo(null);
		
		jdateChooser = new JDateChooser();
		jdateChooser.setBounds(362, 84, 110, 25);
		contentPanel.add(jdateChooser);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(362, 122, 110, 25);
		contentPanel.add(dateChooser);
		
		JCheckBox chckbxTudo = new JCheckBox("Tudo");
		chckbxTudo.setBounds(6, 84, 97, 23);
		contentPanel.add(chckbxTudo);
		
		JCheckBox chckbxEmpresas = new JCheckBox("Empresas");
		chckbxEmpresas.setBounds(6, 110, 97, 23);
		contentPanel.add(chckbxEmpresas);
		
		JCheckBox chckbxPessoas = new JCheckBox("Pessoas");
		chckbxPessoas.setBounds(6, 136, 97, 23);
		contentPanel.add(chckbxPessoas);
		
		JCheckBox chckbxNegocios = new JCheckBox("Negocios");
		chckbxNegocios.setBounds(6, 162, 97, 23);
		contentPanel.add(chckbxNegocios);
		
		JPanel panel = new JPanel();
		panel.setBounds(229, 193, 245, 61);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(100, 11, 117, 20);
		panel.add(comboBox);
		
		JCheckBox chckbxIncluirAnexo = new JCheckBox("Incluir Excel");
		chckbxIncluirAnexo.setBounds(6, 38, 97, 23);
		panel.add(chckbxIncluirAnexo);
		
		JCheckBox chckbxAnexarPdf = new JCheckBox("Incluir PDF");
		chckbxAnexarPdf.setBounds(136, 38, 97, 23);
		panel.add(chckbxAnexarPdf);
		
		JLabel lblDestinatrio = new JLabel("Destinat\u00E1rio:");
		lblDestinatrio.setBounds(6, 14, 84, 14);
		panel.add(lblDestinatrio);
	}
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
}
