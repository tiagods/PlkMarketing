package br.com.tiagods.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.com.tiagods.config.FTPConfig;
import br.com.tiagods.controller.ControllerLogin;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.VersaoSistema;
import br.com.tiagods.view.MenuView;

public class LoginDialog extends JDialog {
	
	VersaoSistema versao = new VersaoSistema();
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public static JPanel pnLogin,pnGerarSenha,pnRecuperarConta;
	public static JComboBox<Usuario> cbUsuario;
	public static JTextField txEmail;
	public static JPasswordField txSenha;
	public static JLabel lbNome, lbIcon;
	public static JPasswordField txNovaSenha;
	public static JPasswordField txConfirmarSenha;
	public static JButton btnCancelarSenha,btnOk,btnSubmeterSenha,btnMinhaConta,btnBack;
	public static JLabel lbEsqueciAConta,lbEsqueciASenha,lbRecuperar;

	private JLabel lblprolinkcontabilcombr;
	
	ControllerLogin controller = new ControllerLogin();
	public LoginDialog() {
		initComponents();
		setTitle("Negocios");
		try{
			ImageIcon ion = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/theme.png"));
			ion.setImage(ion.getImage().getScaledInstance(100, 100, 100));
			this.setIconImage(ion.getImage());
		}catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		controller.iniciar(this);
		
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
		
		lbVersao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbVersao.setForeground(Color.BLACK);
		lbVersao.setHorizontalAlignment(SwingConstants.CENTER);
		lbVersao.setBounds(10, 47, 464, 14);
		contentPanel.add(lbVersao);
		
		lbIcon = new JLabel("");
		lbIcon.setHorizontalAlignment(SwingConstants.CENTER);
    	lbIcon.setBounds(15, 107, 158, 153);
		contentPanel.add(lbIcon);
		
		pnRecuperarConta = new JPanel();
		pnRecuperarConta.setBounds(183, 72, 291, 228);
		contentPanel.add(pnRecuperarConta);
		pnRecuperarConta.setLayout(null);
		pnRecuperarConta.setBackground(new Color(250,250,250));
		
		btnMinhaConta = new JButton("");
		btnMinhaConta.setActionCommand("Esqueci");
		btnMinhaConta.setBounds(231, 192, 50, 25);
		btnMinhaConta.addActionListener(controller);
		pnRecuperarConta.add(btnMinhaConta);
		
		txEmail = new JTextField();
		txEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		txEmail.setColumns(10);
		txEmail.setBounds(10, 109, 104, 25);
		pnRecuperarConta.add(txEmail);
		
		lbRecuperar = new JLabel("");
		lbRecuperar.setHorizontalAlignment(SwingConstants.CENTER);
		lbRecuperar.setFont(new Font("Tahoma", Font.BOLD, 10));
		lbRecuperar.setBounds(10, 71, 271, 14);
		pnRecuperarConta.add(lbRecuperar);
		
		lblprolinkcontabilcombr = new JLabel("@prolinkcontabil.com.br");
		lblprolinkcontabilcombr.setHorizontalAlignment(SwingConstants.CENTER);
		lblprolinkcontabilcombr.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblprolinkcontabilcombr.setBounds(124, 115, 157, 14);
		pnRecuperarConta.add(lblprolinkcontabilcombr);
		
		btnBack = new JButton("");
		btnBack.setActionCommand("Back");
		btnBack.addActionListener(controller);
		btnBack.setBounds(171, 192, 50, 25);
		pnRecuperarConta.add(btnBack);
		
		pnLogin = new JPanel();
		pnLogin.setBackground(new Color(250,250,250));
		
		pnLogin.setBounds(183, 72, 291, 228);
		contentPanel.add(pnLogin);
		pnLogin.setLayout(null);
		
		btnOk = new JButton("");
		btnOk.setBounds(231, 192, 50, 25);
		pnLogin.add(btnOk);
		btnOk.setActionCommand("Entrar");
		btnOk.addActionListener(controller);
		
		
		getRootPane().setDefaultButton(btnOk);
		cbUsuario = new JComboBox<>();
		cbUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		cbUsuario.setBounds(71, 60, 150, 25);
		pnLogin.add(cbUsuario);
		
		txSenha = new JPasswordField();
		txSenha.setEchoChar('*');
		txSenha.setFont(new Font("Tahoma", Font.BOLD, 12));
		txSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txSenha.setColumns(10);
		txSenha.setBounds(71, 119, 150, 25);
		pnLogin.add(txSenha);
		
		lbEsqueciASenha = new JLabel("Esqueci a minha senha...");
		lbEsqueciASenha.setForeground(Color.RED);
		lbEsqueciASenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbEsqueciASenha.setBounds(10, 192, 211, 25);
		lbEsqueciASenha.addMouseListener(controller);
		pnLogin.add(lbEsqueciASenha);
		
		lbNome = new JLabel("Nome:");
		lbNome.setHorizontalAlignment(SwingConstants.CENTER);
		lbNome.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbNome.setBounds(10, 35, 271, 14);
		pnLogin.add(lbNome);
		
		JLabel lbSenha = new JLabel("Senha:");
		lbSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lbSenha.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbSenha.setBounds(10, 94, 271, 14);
		pnLogin.add(lbSenha);
		
		lbEsqueciAConta = new JLabel("N\u00E3o sei minha conta...");
		lbEsqueciAConta.setForeground(Color.RED);
		lbEsqueciAConta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbEsqueciAConta.setBounds(10, 156, 211, 25);
		lbEsqueciAConta.addMouseListener(controller);
		pnLogin.add(lbEsqueciAConta);
		
		pnGerarSenha = new JPanel();
		pnGerarSenha.setBackground(new Color(250,250,250));
		pnGerarSenha.setBounds(183, 72, 291, 228);
		contentPanel.add(pnGerarSenha);
		pnGerarSenha.setLayout(null);
		
		txNovaSenha = new JPasswordField();
		txNovaSenha.setEchoChar('*');
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
		txConfirmarSenha.setEchoChar('*');
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
		
		btnSubmeterSenha = new JButton("");
		btnSubmeterSenha.setActionCommand("Alterar");
		btnSubmeterSenha.setBounds(246, 195, 35, 25);
		btnSubmeterSenha.addActionListener(controller);
		
		pnGerarSenha.add(btnSubmeterSenha);
		
		btnCancelarSenha = new JButton("");
		btnCancelarSenha.setBounds(201, 195, 35, 25);
		btnCancelarSenha.setActionCommand("Close");
		btnCancelarSenha.addActionListener(controller);
		pnGerarSenha.add(btnCancelarSenha);
		
		setLocationRelativeTo(null);
	}
}
