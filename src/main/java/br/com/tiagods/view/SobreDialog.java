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

import br.com.tiagods.model.VersaoSistema;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;

public class SobreDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	VersaoSistema versao = new VersaoSistema();
	
	public SobreDialog(MenuView view, boolean modal) {
		super(view,modal);
		initComponents();
	}
	public void initComponents(){
		setBounds(100, 100, 500, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(250,250,250));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbTitulo = new JLabel(versao.getNome());
		lbTitulo.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitulo.setBounds(10, 11, 464, 25);
		contentPanel.add(lbTitulo);
		
		JLabel lbVersao = new JLabel("Versão do Sistema: "+versao.getVersao()+" de "+versao.getDate());
		lbVersao.setBounds(185, 89, 289, 14);
		contentPanel.add(lbVersao);
		

		JLabel lbVersaoBanco = new JLabel("Versão do Banco: "+versao.getVersaoBanco());
		lbVersaoBanco.setBounds(185, 114, 289, 14);
		contentPanel.add(lbVersaoBanco);
		
		JTextPane textPane = new JTextPane();
		textPane.setOpaque(false);
		textPane.setEditable(false);
		textPane.setBackground(new Color(240,240,240));
		textPane.setBounds(20, 173, 454, 70);
		textPane.setText(versao.getDetalhes());
		contentPanel.add(textPane);
		
		
		JLabel lblProlinkContabil = new JLabel("Prolink Contabil 2017 - Todos os direitos reservados - Autor: github.com/tiagods");
		lblProlinkContabil.setHorizontalAlignment(SwingConstants.CENTER);
		lblProlinkContabil.setBounds(10, 254, 464, 14);
		contentPanel.add(lblProlinkContabil);
		
		ImageIcon iconTheme = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/theme.png"));
		int Nalt = 120;
		iconTheme.setImage(iconTheme.getImage().getScaledInstance(iconTheme.getIconWidth()/iconTheme.getIconHeight()*Nalt, Nalt, 100));
		JLabel lbIcon = new JLabel("");
    	lbIcon.setHorizontalAlignment(SwingConstants.CENTER);
    	lbIcon.setIcon(iconTheme);
		lbIcon.setBounds(10, 42, 120, 120);
		contentPanel.add(lbIcon);
		
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
		setLocationRelativeTo(null);
	}
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
}
