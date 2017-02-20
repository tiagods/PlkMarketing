package br.com.tiagods.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import br.com.tiagods.model.DescricaoVersao;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
	/**
	 * 
	 */
	public static void main(String[] args) {
		new LoginDialog().setVisible(true);
	}
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	DescricaoVersao versao = new DescricaoVersao();
	public static JTextField txUsuario;
	public static JPasswordField txSenha;
	private JLabel lbNome;
	public static JPasswordField txNovaSenha;
	public static JPasswordField txConfirmarSenha;
	
	public LoginDialog() {
		initComponents();
	}
	public void initComponents(){
		setBounds(100, 100, 500, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(250,250,250));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbTitulo = new JLabel(versao.getNome()+" "+versao.getVersao());
		lbTitulo.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitulo.setBounds(10, 11, 464, 25);
		contentPanel.add(lbTitulo);
		
		JLabel lbVersao = new JLabel("Versão do Sistema: "+versao.getVersao()+" em "+versao.getDate());
		lbVersao.setHorizontalAlignment(SwingConstants.CENTER);
		lbVersao.setBounds(10, 47, 464, 14);
		contentPanel.add(lbVersao);
		
		JLabel lbIcon = new JLabel("");
		lbIcon.setHorizontalAlignment(SwingConstants.CENTER);
    	lbIcon.setBounds(15, 107, 158, 153);
		
		ImageIcon iconTheme = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/theme.png"));
		int nAlt = lbIcon.getHeight();
		iconTheme.setImage(iconTheme.getImage().getScaledInstance(iconTheme.getIconWidth()/iconTheme.getIconHeight()*nAlt, nAlt, 100));
		lbIcon.setIcon(iconTheme);
		contentPanel.add(lbIcon);
		
		JPanel pnLogin = new JPanel();
		pnLogin.setBackground(Color.WHITE);
		pnLogin.setVisible(false);
		
		JPanel pnGerarSenha = new JPanel();
		pnGerarSenha.setBackground(Color.WHITE);
		pnGerarSenha.setBounds(183, 72, 291, 228);
		contentPanel.add(pnGerarSenha);
		pnGerarSenha.setLayout(null);
		
		txNovaSenha = new JPasswordField();
		txNovaSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txNovaSenha.setBounds(88, 104, 130, 25);
		pnGerarSenha.add(txNovaSenha);
		txNovaSenha.setColumns(10);
		
		JTextPane txtpnParaDeixarmosO = new JTextPane();
		txtpnParaDeixarmosO.setEditable(false);
		txtpnParaDeixarmosO.setText("Para sua seguran\u00E7a, voc\u00EA deve cadastrar uma nova senha.\r\nInsira uma senha de no minimo 8 caracteres, com n\u00FAmeros, letras mai\u00FAsculas e min\u00FAsculas.");
		txtpnParaDeixarmosO.setBounds(0, 0, 291, 74);
		pnGerarSenha.add(txtpnParaDeixarmosO);
		
		txConfirmarSenha = new JPasswordField();
		txConfirmarSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txConfirmarSenha.setBounds(88, 159, 130, 25);
		pnGerarSenha.add(txConfirmarSenha);
		
		JLabel lblNovaSenha = new JLabel("Nova Senha:");
		lblNovaSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNovaSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblNovaSenha.setBounds(88, 84, 130, 14);
		pnGerarSenha.add(lblNovaSenha);
		
		JLabel lblConfirmarSenha = new JLabel("Confirmar Senha:");
		lblConfirmarSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblConfirmarSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmarSenha.setBounds(88, 140, 130, 14);
		pnGerarSenha.add(lblConfirmarSenha);
		
		
		
		pnLogin.setBounds(183, 72, 291, 228);
		contentPanel.add(pnLogin);
		pnLogin.setLayout(null);
		
		JButton okButton = new JButton("");
		okButton.setBounds(231, 192, 50, 25);
		pnLogin.add(okButton);
		okButton.setActionCommand("OK");
		ImageIcon iconOk = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/button_ok.png"));
		okButton.setIcon(recalculate(iconOk));
		
		JButton btnSubmeterSenha = new JButton("");
		btnSubmeterSenha.setBounds(246, 195, 35, 25);
		btnSubmeterSenha.setIcon(iconOk);
		pnGerarSenha.add(btnSubmeterSenha);
		
		ImageIcon iconCancelar = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/button_exit.png"));
		
		JButton btnCancelarSenha = new JButton("");
		btnCancelarSenha.setBounds(201, 195, 35, 25);
		btnCancelarSenha.setIcon(recalculate(iconCancelar));
		pnGerarSenha.add(btnCancelarSenha);
		
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getRootPane().setDefaultButton(okButton);
		
		txUsuario = new JTextField();
		txUsuario.setBounds(10, 60, 95, 25);
		pnLogin.add(txUsuario);
		txUsuario.setColumns(10);
		
		txSenha = new JPasswordField();
		txSenha.setColumns(10);
		txSenha.setBounds(10, 119, 271, 25);
		pnLogin.add(txSenha);
		
		JLabel lbEsqueci = new JLabel("Problemas para acessar a conta?");
		lbEsqueci.setForeground(Color.RED);
		lbEsqueci.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbEsqueci.setBounds(10, 192, 211, 25);
		pnLogin.add(lbEsqueci);
		
		lbNome = new JLabel("Nome:");
		lbNome.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbNome.setBounds(10, 35, 271, 14);
		pnLogin.add(lbNome);
		
		JLabel lbSenha = new JLabel("Senha:");
		lbSenha.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbSenha.setBounds(10, 94, 271, 14);
		pnLogin.add(lbSenha);
		
		JLabel lblprolinkcontabilcombr = new JLabel("@prolinkcontabil.com.br");
		lblprolinkcontabilcombr.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblprolinkcontabilcombr.setBounds(114, 65, 167, 14);
		pnLogin.add(lblprolinkcontabilcombr);
		
		setLocationRelativeTo(null);
	}
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
}
