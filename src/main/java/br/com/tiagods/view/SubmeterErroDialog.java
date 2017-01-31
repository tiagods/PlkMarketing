package br.com.tiagods.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import br.com.tiagods.controller.ControllerErro;

public class SubmeterErroDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	public static JTextArea txResumo,txDetalhes;
	
	ControllerErro controller = new ControllerErro();
	
//	public static void main(String[] args) {
//		try {
//			SubmeterErroView dialog = new SubmeterErroView();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public SubmeterErroDialog(String detalhes,File file) {
		controller.iniciar(detalhes,file);
	}
	public void initComponents(){
		setBounds(100, 100, 600, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 564, 306);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lbDetalhes = new JLabel("Detalhes do Erro:");
		lbDetalhes.setBounds(10, 263, 112, 14);
		panel.add(lbDetalhes);

		JLabel lbDescricao = new JLabel("Problema:");
		lbDescricao.setBounds(10, 209, 112, 14);
		panel.add(lbDescricao);

		JLabel lbLogo = new JLabel("");
		lbLogo.setBounds(368, 0, 186, 109);
		int novaAlturaLogo = lbLogo.getHeight();
		ImageIcon iconLogo = new ImageIcon(SubmeterErroDialog.class.getResource("/br/com/tiagods/utilitarios/manutencao.jpg"));
		iconLogo.setImage(iconLogo.getImage().getScaledInstance(iconLogo.getIconWidth()/iconLogo.getIconHeight()*novaAlturaLogo, novaAlturaLogo, 100));
		lbLogo.setIcon(iconLogo);
		panel.add(lbLogo);

		JLabel lbOps = new JLabel("");
		lbOps.setBounds(0, 0, 356, 109);
		panel.add(lbOps);
		int novaAltura = lbOps.getHeight();
		ImageIcon icon = new ImageIcon(SubmeterErroDialog.class.getResource("/br/com/tiagods/utilitarios/ops.png"));
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/icon.getIconHeight()*novaAltura, novaAltura, 100));
		lbOps.setIcon(icon);
		
		JTextArea txtrHouveUmErro = new JTextArea();
		txtrHouveUmErro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrHouveUmErro.setOpaque(false);
		txtrHouveUmErro.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtrHouveUmErro.setBounds(10, 120, 544, 58);
		panel.add(txtrHouveUmErro);
		txtrHouveUmErro.setText("Houve um erro inesperado. A equipe de desenvolvimento ir\u00E1 receber e analizar este problema e realizar uma corre\u00E7\u00E3o se necess\u00E1rio. Descreva brevemente o que ocorreu e clique em ENVIAR ERRO.");
		txtrHouveUmErro.setWrapStyleWord(true);
		txtrHouveUmErro.setLineWrap(true);
		
		JScrollPane scrollResumo = new JScrollPane();
		scrollResumo.setOpaque(false);
		scrollResumo.setBounds(129, 209, 435, 43);
		panel.add(scrollResumo);
		
		txResumo = new JTextArea();
		scrollResumo.setViewportView(txResumo);
		txResumo.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBounds(129, 263, 435, 43);
		panel.add(scrollPane);
		
		txDetalhes = new JTextArea();
		txDetalhes.setEditable(false);
		scrollPane.setViewportView(txDetalhes);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Enviar Erro");
				okButton.setActionCommand("OK");
				okButton.addActionListener(controller);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}
