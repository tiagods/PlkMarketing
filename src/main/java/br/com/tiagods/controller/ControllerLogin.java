package br.com.tiagods.controller;

import static br.com.tiagods.view.dialog.LoginDialog.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.config.IconsConfig;
import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.VersaoSistema;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.UsuarioAcesso;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.SendEmail;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.modeldao.VerificarAtualizacao;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.dialog.LoginDialog;

public class ControllerLogin implements ActionListener, MouseListener {
	String caracteres = "abcdefghijklmnopqrstuvwxyz";
	String maiusculas = caracteres.toUpperCase();
	String numeros = "0123456789";
	
	private LoginDialog view;
	
	GenericDao dao = new GenericDao();
	VerificarAtualizacao atualizacao = new VerificarAtualizacao();
	VersaoSistema descricao = new VersaoSistema();
    private Usuario usuario;
    int esqueci = -1; //0 para recuperar conta e 1 para redefinir senha
	
	public void iniciar(LoginDialog view){
		this.view = view;
		setarIcones();
		atualizacao();
		verificacao();//verificar se possui senha, senão abrir painel de cadastro
		LoadingView loading = LoadingView.getInstance();
		loading.fechar();
	}
	private void verificacao() {
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		pnGerarSenha.setVisible(false);
		pnLogin.setVisible(true);
		String[] nome = System.getProperty("user.name").split(" ");
		if(!"user".equalsIgnoreCase(nome[0])){
			Criterion[] criterion = new Criterion[]{Restrictions.eq("login", nome[0]),Restrictions.eqOrIsNull("senha", "")};
			usuario = (Usuario) dao.receberObjeto(Usuario.class, criterion, session);
			if(usuario!=null){
				pnGerarSenha.setVisible(true);
				pnLogin.setVisible(false);
				txUsuario.setText(usuario.getLogin());	
				String mensagem = "Inserimos uma senha de acesso ao sistema\n"
						+ "que impossibilita o acesso de outras pessoas usando sua conta.\n"
						+ "Portanto seu acesso é criptografado e confidencial.\n"
						+ "Você deve criar uma nova senha para ter o acesso liberado!";
				JOptionPane.showMessageDialog(null, "Olá "+System.getProperty("user.name")+"!\n"+mensagem);
			}
		}
		pnRecuperarConta.setVisible(false);
		session.close();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Entrar":
			if("".equals(txUsuario.getText().trim()) || "".equals(new String(txSenha.getPassword()))){
				JOptionPane.showMessageDialog(null, "Usuario e/ou senha incorreta", "Erro de Credenciais", JOptionPane.ERROR_MESSAGE);
			}
			else
				logar();
			break;
		case "Alterar":
			alterar();
			break;
		case "Esqueci":
			if(txEmail.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Por favor informe o e-mail");
			}
			else{
				if(esqueci==1)
					esqueciAConta();
				else if(esqueci==0)
					esqueciASenha();
			}
			break;
		case "Close":
			view.dispose();
			break;
		case "Back":
			pnLogin.setVisible(true);
			pnGerarSenha.setVisible(false);
			pnRecuperarConta.setVisible(false);
			break;
		default:
			break;
		}
	}
	@SuppressWarnings("static-access")
	private void alterar(){
		String senha1 = new String(txNovaSenha.getPassword());
		String senha2 = new String(txConfirmarSenha.getPassword());
		if(senha1.equals(senha2)){
			if(validar(senha1)){
				String senha = criptografar(senha1);
				usuario.setSenha(senha);
				Session session = HibernateFactory.getSession();
				session.beginTransaction();
				if(dao.salvar(usuario, session)){
					session.beginTransaction();
					usuario.setUltimoAcesso(new Date());
					dao.salvar(usuario, session);
					UsuarioLogado.getInstance().setUsuario(usuario);
					LoadingView loading = LoadingView.getInstance();
					loading.inicializar(false);
					Runnable run = ()->{
						MenuView.getInstance();
					};
					new Thread(run).start();
					view.dispose();
				}
				session.close();	
				
			}
			else
				JOptionPane.showMessageDialog(null, "A senha deve conter números, e ao menos uma letra!\n"
						+ "Não é permitido senhas que contenham {123456,00000,11111,987654,prolink}",
						"Senha não permitida!", JOptionPane.ERROR_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(null, "As senhas não são iguais!");
	}
	private void esqueciAConta(){
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		usuario = (Usuario)dao.receberObjeto(Usuario.class, 
				new Criterion[]{Restrictions.eq("email", 
						txEmail.getText().toLowerCase().replace("@prolinkcontabil.com.br","")+"@prolinkcontabil.com.br")}, session);
		if(usuario!=null){
			pnGerarSenha.setVisible(false);
			pnLogin.setVisible(true);
			pnRecuperarConta.setVisible(false);
			txEmail.setText("");
			txUsuario.setText(usuario.getLogin());
		}
		else
			JOptionPane.showMessageDialog(null, "E-mail incorreto ou não existe");
		session.close();
	};
	//recuperar a conta do usuario
	private void esqueciASenha(){
		int escolha = JOptionPane.showConfirmDialog(null, "Se você esqueceu sua senha clique em OK",
				"Solicitar senha!",
				JOptionPane.OK_CANCEL_OPTION);
		if(escolha == JOptionPane.OK_OPTION){
			Session session = HibernateFactory.getSession();
			session.beginTransaction();
			String conta = txEmail.getText().toLowerCase().replace("@prolinkcontabil.com.br","")+"@prolinkcontabil.com.br";
			usuario = (Usuario)dao.receberObjeto(Usuario.class, new Criterion[]{Restrictions.eq("email", 
					conta)}, session);
			if(usuario!=null){
				pnGerarSenha.setVisible(false);
				pnLogin.setVisible(true);
				pnRecuperarConta.setVisible(false);
				txEmail.setText("");
				txUsuario.setText(usuario.getLogin());
				String novaSenha = gerarSenha();
				String senhaCripto = criptografar(novaSenha);
				usuario.setSenha(senhaCripto);
				if(dao.salvar(usuario, session)){
					StringBuilder mensagem = new StringBuilder();
							mensagem.append("<body><div style=\"text-align: left;\"><var><samp><big>Ol&aacute; <strong>").append(usuario.getNome()).append("</strong>;</big></samp></var></div>");
							mensagem.append("<div style=\"text-align: left;\">&nbsp;</div>");
							mensagem.append("<div style=\"text-align: left;\"><var><samp><big>Algu&eacute;m solicitou um reenvio de senha em seu nome,</big></samp></var></div>");
							mensagem.append("<div style=\"text-align: left;\"><var><samp><big>Para sua seguran&ccedil;a voc&ecirc; receber&aacute; uma nova senha de acesso ao sistema Neg&oacute;cios.&nbsp;</big></samp></var></div>");
							mensagem.append("<div style=\"text-align: left;\">&nbsp;</div>");
							mensagem.append("<div style=\"text-align: left;\"><var><samp><big>Usuario: <strong>").append(usuario.getLogin()).append("</strong></big></samp></var></div>");
							mensagem.append("<div style=\"text-align: left;\"><var><samp><big>Senha: <strong>").append(novaSenha).append("</strong></big></samp></var></div>");
							mensagem.append("<div style=\"text-align: left;\">&nbsp;</div>");
							mensagem.append("<div style=\"text-align: left;\"><var><samp><big>Atencisamente,</big></samp></var></div>");
							mensagem.append("<div style=\"text-align: left;\"><var><samp><big>Suporte Prolink</big></samp></var></div></body>");
					boolean email = new SendEmail().enviaAlerta(usuario.getEmail(), 
							"Recuperação de Senha Sistema Negocios Prolink", 
							mensagem.toString());
					if(email)
						JOptionPane.showMessageDialog(null, "Foi enviado uma nova senha para o seu e-mail!\nVerifique sua caixa de entrada");
				}
			}
			else
				JOptionPane.showMessageDialog(null, "E-mail incorreto ou não existe");
			session.close();
		}
	}
	@SuppressWarnings("static-access")
	private void logar(){
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		Criterion[] criterions = new Criterion[]{Restrictions.eq("login", txUsuario.getText().trim()),
				Restrictions.eq("senha", criptografar(new String(txSenha.getPassword())))};
		usuario = (Usuario)dao.receberObjeto(Usuario.class, criterions, session);
		if(usuario!=null){
			usuario.setUltimoAcesso(new Date());
			dao.salvar(usuario, session);
			session.beginTransaction();
			UsuarioAcesso acesso = new UsuarioAcesso();
			acesso.setData(new Date());
			acesso.setUsuario(usuario);
			try {acesso.setMaquina(InetAddress.getLocalHost().getHostName());}catch (UnknownHostException e) {}
			dao.salvar(acesso, session);
			UsuarioLogado.getInstance().setUsuario(usuario);
			LoadingView loading = LoadingView.getInstance();
			loading.inicializar(false);
			Runnable run = ()->{
				MenuView.getInstance();
			};
			new Thread(run).start();
			view.dispose();
		}
		else{
			JOptionPane.showMessageDialog(null, "Usuario ou senha incorreta!\nTente novamente!","Falha no Acesso", JOptionPane.ERROR_MESSAGE);
			txSenha.setText("");
		}
		session.close();
	}
	public boolean validar(String senha){
		if(senha.length()<8)
			return false;
		else{
			boolean letras=false; 
			boolean number=false;
			char[] lista = senha.toCharArray();
			for(char c : lista){
				if(letras && number) 
					break;
				if(caracteres.contains(String.valueOf(c))) 
					letras = true;
				if(numeros.contains(String.valueOf(c))) 
					number = true;
			}
			if(letras && number) 
				return true;
		}
		return false;
	}
	private String gerarSenha(){
		Random random = new Random();
		List<String> lista = new ArrayList<>();
		int index = -1;
		for(int i=0;i<6; i++){
			index = random.nextInt(numeros.length());
			lista.add(numeros.substring(index,  index+1));
		}
		index = random.nextInt(maiusculas.length());
		lista.add(maiusculas.substring(index,  index+1));
		index = random.nextInt(caracteres.length());
		lista.add(caracteres.substring(index,  index+1));
		
		Collections.shuffle(lista);
		
		StringBuilder builder = new StringBuilder();
		lista.forEach(cons->{
			builder.append(cons);
		});
		return builder.toString();
	}
	private String criptografar(String senha){
		String criptografia;
		StringBuilder builder = new StringBuilder();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
			for (byte b : messageDigest) {
				builder.append(String.format("%02X", 0xFF & b));
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		criptografia = builder.toString();
		return criptografia;
	}
	private void setarIcones(){
		
		try {
		IconsConfig icons = IconsConfig.getInstance();
		
		ImageIcon iconTheme = icons.getIconName("theme.png");
		int nAlt = lbIcon.getHeight();
		iconTheme.setImage(iconTheme.getImage().getScaledInstance(iconTheme.getIconWidth()/iconTheme.getIconHeight()*nAlt, nAlt, 100));
		lbIcon.setIcon(iconTheme);
		
		ImageIcon iconOk = icons.getIconName("button_ok.png");
		btnOk.setIcon(recalculate(iconOk));
		btnSubmeterSenha.setIcon(iconOk);
		btnMinhaConta.setIcon(iconOk);
		
		ImageIcon iconCancelar = icons.getIconName("button_exit.png");
		btnCancelarSenha.setIcon(recalculate(iconCancelar));
		
		ImageIcon iconReturn = icons.getIconName("button_return.png");
		btnBack.setIcon(recalculate(iconReturn));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
	public void atualizacao(){
		if(verificarVersao(atualizacao,descricao)){
			File updateNew = new File("update-1.jar");
			if(updateNew.exists()){
				Path pathI = Paths.get(updateNew.getAbsolutePath());
				File update = new File("update.jar");
				Path pathO = Paths.get(update.getAbsolutePath());
				try {
					Files.copy(pathI, pathO, StandardCopyOption.REPLACE_EXISTING);
					updateNew.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			atualizarAgora();
		}
	}
	public void atualizarAgora(){
    	try{
    		Runtime.getRuntime().exec("java -jar update.jar plk*link815");
    		System.exit(0);
    	}catch(IOException e){
    	}
    }
	//comparação de versoes, retorna false se estiver desatualizado
    public boolean verificarVersao(VerificarAtualizacao atualizacao, VersaoSistema versao){
    	return atualizacao.receberStatus(versao).equals("Desatualizado");
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() == lbEsqueciASenha){
			esqueci = 0;
			pnGerarSenha.setVisible(false);
			pnLogin.setVisible(false);
			pnRecuperarConta.setVisible(true);
			lbRecuperar.setText("Informe seu e-mail para recuperar a sua senha");
		}
		else if(e.getComponent() == lbEsqueciAConta){
			esqueci = 1;
			pnGerarSenha.setVisible(false);
			pnLogin.setVisible(false);
			pnRecuperarConta.setVisible(true);
			lbRecuperar.setText("Informe seu e-mail para recuperar a sua conta");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
