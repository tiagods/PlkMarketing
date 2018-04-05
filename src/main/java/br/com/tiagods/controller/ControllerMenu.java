/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.MenuView.*;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import br.com.tiagods.model.VersaoSistema;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.modeldao.VerificarAtualizacao;
import br.com.tiagods.view.EmpresasView;
import br.com.tiagods.view.InicioView;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.NegociosView;
import br.com.tiagods.view.PessoasView;
import br.com.tiagods.view.ProspeccaoView;
import br.com.tiagods.view.RelatorioView;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.TarefasView;
import br.com.tiagods.view.dialog.SobreDialog;

/**
 *
 * @author User
 */
public class ControllerMenu implements MouseListener{
    InicioView inicio;
    EmpresasView empresas;
    PessoasView pessoas;
    TarefasSaveView tarefasSave;
    TarefasView tarefas;
    NegociosView negocios;
    MenuView view;
    ProspeccaoView prospeccao;
    RelatorioView relatorio;
    VersaoSistema descricao = new VersaoSistema();
    VerificarAtualizacao atualizacao = new VerificarAtualizacao();
    boolean atualizar = false;
    
    static ControllerMenu instance;
    
    public static ControllerMenu getInstance(){
    	if(instance==null){
    		instance = new ControllerMenu();
    	}
    	return instance;
    }
    public void iniciar(MenuView view){
    	this.view = view;
    	try{
    		setarIcones();
    	}catch (NullPointerException e) {
		}
    	try{
    		trabalharAtualizador();
    	}catch (Exception e) {
		}
    	inicio = new InicioView();
    	abrirCorpo(inicio);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    	switch(e.getComponent().getName()){
    	case "Inicio":
    		invocarLoading();
    		Runnable run = ()->{
    			inicio = new InicioView();
    			abrirCorpo(inicio);
    		};
    		new Thread(run).start();
            break;
        case "Empresas":
        	invocarLoading();
        	run = ()->{
        		empresas = new EmpresasView(null);
        		abrirCorpo(empresas);
        	};
        	new Thread(run).start();
            break;
        case "Tarefas":
        	invocarLoading();
        	run = ()->{
        		tarefas = new TarefasView(new Date(), new Date(), UsuarioLogado.getInstance().getUsuario());
        		abrirCorpo(tarefas);
        	};
        	new Thread(run).start();
            break;
        case "TarefasSave":
        	invocarLoading();
        	run = ()->{
        		tarefasSave = new TarefasSaveView(null,null,null,MenuView.getInstance(),true,null,false);
        		tarefasSave.setVisible(true);
        	};
        	new Thread(run).start();
        	break;
        case "Negocios":
        	invocarLoading();
        	run = ()->{
        		negocios = new NegociosView(null,null);
        		abrirCorpo(negocios);
        	};
        	new Thread(run).start();
            break;
        case "Pessoas":
        	invocarLoading();
        	run = ()->{
        	pessoas = new PessoasView(null);
            abrirCorpo(pessoas);
        	};
        	new Thread(run).start();
        	break;
        case "Prospeccao":
        	invocarLoading();
        	run = ()->{
        	prospeccao = new ProspeccaoView(null);
            abrirCorpo(prospeccao);
        	};
        	new Thread(run).start();
        	break;
        case "Relatorios":
        	//invocarLoading();
        	run = ()->{
        	relatorio = new RelatorioView();
            abrirCorpo(relatorio);
        	};
        	new Thread(run).start();
        	JOptionPane.showMessageDialog(jDBody, "Esse modulo ainda esta em fase de desenvolvimento, mas você pode visualizar o layout e dar sugestões...!","Modulo em Desenvolvimento!",JOptionPane.OK_OPTION);
        	break;
        case "Atualizar":
        	if(atualizar)
        		atualizarAgora();
        	else{
        		SobreDialog sobre = new SobreDialog(view,true);
        		sobre.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        		sobre.setVisible(true);
        	}	
        	break;
        default:
        	JOptionPane.showMessageDialog(jDBody, "Esse modulo esta em fase de desenvolvimento, aguarde...!","Modulo em Desenvolvimento!",JOptionPane.OK_OPTION);
        	break;
    	}
    }
    @SuppressWarnings("static-access")
	public void invocarLoading(){
    	LoadingView loading = LoadingView.getInstance();
    	loading.inicializar(false);
    }
    @Override
    public void mousePressed(MouseEvent e) {
    	new UnsupportedOperationException();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    	new UnsupportedOperationException();
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    	new UnsupportedOperationException();
    }
    @Override
    public void mouseExited(MouseEvent e) {
    	new UnsupportedOperationException();
    }
    
    public static void abrirCorpo(JInternalFrame jframe){
        jDBody.removeAll();
        ((BasicInternalFrameUI)jframe.getUI()).setNorthPane(null);
        jDBody.add(jframe);
        jframe.setVisible(true);
    }

    public void setarIcones() throws NullPointerException{
    	ImageIcon iconHome = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_home.png"));
    	mnInicio.setIcon(recalculate(iconHome));
    	ImageIcon iconTask = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_task.png"));
    	mnTarefas.setIcon(recalculate(iconTask));
    	ImageIcon iconEmpresas = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_empresas.png"));
    	mnEmpresas.setIcon(recalculate(iconEmpresas));
    	ImageIcon iconPessoas = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_people.png"));
    	mnPessoas.setIcon(recalculate(iconPessoas));
    	ImageIcon iconProspeccao = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_prospeccao.png"));
        mnProspeccao.setIcon(recalculate(iconProspeccao));
        ImageIcon iconNegocios = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_negocios.png"));
    	mnNegocios.setIcon(recalculate(iconNegocios));
    	ImageIcon iconRelatorios = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_report.png"));
        mnRelatorios.setIcon(recalculate(iconRelatorios));
    	ImageIcon iconExtra = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_chave.png"));
        mnExtra.setIcon(recalculate(iconExtra));
        ImageIcon iconAbout = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/menu_about.png"));
        mnAtualizacao.setIcon(recalculate(iconAbout));
        
        //https://icons8.com/web-app/category/all/User-Interface

        //https://icons8.com/web-app/category/all/Business

        //https://icons8.com/web-app/category/all/Time-And-Date
    }
    public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
    public void trabalharAtualizador() throws Exception{
    	view.setTitle("Negocios " +descricao.getVersao());
    	if(verificarVersao(atualizacao,descricao)){
    		try{
    			ImageIcon iconExclamation = new ImageIcon(MenuView.class.getResource("/br/com/tiagods/utilitarios/exclamation.png"));
    			mnAtualizacao.setIcon(recalculate(iconExclamation));
    		}catch (Exception e) {
			}
    		relatarAtualizacaoDisponivel(atualizacao);
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
    	}
    	else{
    		Agendador();
    	}
    }
        //comparação de versoes, retorna false se estiver desatualizado
        public boolean verificarVersao(VerificarAtualizacao atualizacao, VersaoSistema versao){
        	return atualizacao.receberStatus(versao).equals("Desatualizado");
        }
        //agendador para verificar se existe uma nova versao
        private void Agendador(){
        	Cronometro cron = new Cronometro();
        	Thread thread = new Thread(cron);
        	thread.start();
        }
        public class Cronometro implements Runnable{
        	public void run() {
        		try{
        				Thread.sleep(600000);//10 minutos
        			if(verificarVersao(atualizacao,descricao)){
        				relatarAtualizacaoDisponivel(atualizacao);
        			}
        			else
        				Agendador();
        		}catch(InterruptedException e){
        			e.printStackTrace();
        		}
        	}
        }
        public void AlertarAtualizacao(){
        	Alertas al = new Alertas();
        	Thread thread = new Thread(al);
        	thread.start();
        }
        public class Alertas implements Runnable{
        	@Override
        	public void run(){
        		try{
        			mnAtualizacao.setBackground(Color.WHITE);
        			mnAtualizacao.setForeground(Color.RED);
        			mnAtualizacao.setText("Versão "+atualizacao.versaoDisponivel()+" Disponível");
                	Thread.sleep(2000);
        			mnAtualizacao.setText("Clique aqui para atualizar!");
                	
        			Thread.sleep(2000);
        			AlertarAtualizacao();
        		}catch(InterruptedException e){
        			e.printStackTrace();
        		}
        	}
        }
    
        public void relatarAtualizacaoDisponivel(VerificarAtualizacao atualizacao){
        	atualizar = true;
        	mnAtualizacao.setText("Versão "+atualizacao.versaoDisponivel()+" Disponível");
        	AlertarAtualizacao();
        	//piscar labels para alertar sobre atualiação pendente
        }
        public void atualizarAgora(){
        	try{
        		System.out.println("Atualização invocada!");
        		Runtime.getRuntime().exec("java -jar update.jar plk*link815");
        		System.exit(0);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
}
