package br.com.tiagods.controller;

import static br.com.tiagods.view.InicioView.cbAtendentes;
import static br.com.tiagods.view.InicioView.jData1;
import static br.com.tiagods.view.InicioView.jData2;
import static br.com.tiagods.view.InicioView.lbInfoTarefas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modeldao.*;
import br.com.tiagods.view.TarefasView;

public class ControllerInicio implements ActionListener,MouseListener{
		
	Session session = null;
	HashMap<String, Usuario> atendentes = new HashMap();
	boolean liberar = false;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()){
		case "Filtrar":
			if(validarDatas())
				abrirTarefasView(jData1.getDate(),jData2.getDate(), atendentes.get(cbAtendentes.getSelectedItem()));
			break;
		default:
			
			break;
		}
	}
	public void iniciar(){
		session = HibernateFactory.getSession();
		session.beginTransaction();
		long inicio = System.currentTimeMillis();
		carregarDataAgora();
		carregarAtendentes();
		carregarTarefasHoje(UsuarioLogado.getInstance().getUsuario());
		long fim = System.currentTimeMillis();
		System.out.println("Tempo de execucao: "+(fim-inicio));
		session.close();
	}
	//carregar tarefas pendentes
	private void carregarTarefasHoje(Usuario usuario) {
		//verificar permiss�o e carregar tarefas do's usuarios
		int quant = new TarefaDao().getQuantidade(usuario, new Date(), new Date(),session);
		String[] nome = usuario.getNome().split(" ");
		
		LocalDateTime time = LocalDateTime.now();
		String hey;
		if(time.getHour()>=0 &&time.getHour()<12){
			hey="Bom dia";
		}
		else if(time.getHour()>=12 && time.getHour()<18){
			hey="Boa tarde";
		}
		else
			hey = "Boa noite";
		
		switch(quant){
		case 0:
			String v1 = hey+" "+nome[0]+", voc� n�o tem tarefas pendentes para hoje!";
			lbInfoTarefas.setText(v1);
			break;
		case 1:
			String v2 = hey+" "+nome[0]+",  voc� tem 1 tarefa pendente para hoje! Clique aqui...";
			lbInfoTarefas.setText(v2);
			break;
		case 3:
			String v3 = hey+" "+nome[0]+", voc� tem "+quant+" tarefas pendentes para hoje! Clique aqui...";
			lbInfoTarefas.setText(v3);
			break;
		default:
			break;
		}
	}
	//carregar lista de atendentes
	@SuppressWarnings("unchecked")
	private void carregarAtendentes() {
		List<Usuario> lista = new UsuarioDao().listar(Usuario.class, session);
		cbAtendentes.removeAllItems();
		lista.forEach(c->{
			cbAtendentes.addItem(c.getLogin());
			atendentes.put(c.getLogin(), c);
		});
		cbAtendentes.setSelectedItem(UsuarioLogado.getInstance().usuario.getLogin());
	}
	//enviar data atual
	private void carregarDataAgora() {
		jData1.setDate(new Date());
		jData2.setDate(new Date());
	}
	//validar datas
	private boolean validarDatas(){
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try{
			calendar.setTime(jData1.getDate());
			calendar2.setTime(jData2.getDate());
			if(calendar.after(calendar2)){
				JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, 
						"O intervalo entre as datas est� incorreto\n"
						+ "A data 1 deve ser igual ou menor que a data 2!",
						"Intervalo entre datas incorreto!", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else
				return true;
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, 
					"Data incorreta\nPor favor verifique a(s) data(s) informada(s)!",
					"Entrada incorreta!", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	//abrir nova tela
	@SuppressWarnings("static-access")
	private void abrirTarefasView(Date data1, Date data2, Usuario usuario){
		 ControllerMenu menu = ControllerMenu.getInstance();
		 TarefasView tView = new TarefasView(data1, data2, usuario);
		 menu.abrirCorpo(tView);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getComponent().getName()){
		case "OK":	
		if(validarDatas())
				abrirTarefasView(jData1.getDate(),jData2.getDate(), atendentes.get(cbAtendentes.getSelectedItem()));
		break;
		default:
				abrirTarefasView(jData1.getDate(),jData2.getDate(), atendentes.get(cbAtendentes.getSelectedItem()));	
			break;
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
