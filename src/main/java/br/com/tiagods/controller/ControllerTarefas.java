/*
 * Todos direitos reservados a Tiago Dias de Souza.
  */
package br.com.tiagods.controller;

import static br.com.tiagods.view.TarefasView.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelDAO.TarefaDAO;
import br.com.tiagods.modelDAO.UsuarioDAO;
import br.com.tiagods.view.interfaces.ButtonColumn;
/*
* @author Tiago
*/
public class ControllerTarefas implements ActionListener, MouseListener {
	Date data1;
	Date data2;
	Usuario userSessao;
    Session session;
    Map<String,Usuario> atendentes = new HashMap();

	public void iniciar(Date data1, Date data2, Usuario usuario){
    	this.data1=data1;
    	this.data2=data2;
    	this.userSessao=usuario;
			ativarBotao(ckVisita);
			ativarBotao(ckReuniao);
			ativarBotao(ckProposta);
			ativarBotao(ckTelefone);
			ativarBotao(ckEmail);
			try{
    		jData1.setDate(data1);
    		jData2.setDate(data2);
    	}catch(Exception e){
    	}
    	boolean hoje = verificarSeHoje(data1,data2);
    	if(hoje){
    		rbHoje.setSelected(true);
    		mostrarDatas(pnData, false);
    	}
    	else{
    		rbDefinirData.setSelected(true);
    		mostrarDatas(pnData, true);
    	}
    	session = HibernateFactory.getSession();
    	carregarAtendentes();
    	List<Criterion> lista = new ArrayList();
    	Criterion criterio =  Restrictions.eq("atendente", usuario);
    	Criterion criterio2 = Restrictions.between("dataEvento", data1, data2);
    	Criterion criterio3 = Restrictions.eq("finalizado", 0);
    	lista.add(criterio);
    	lista.add(criterio2);
    	lista.add(criterio3);
    	List<Tarefa> tarefas = new TarefaDAO().filtrar(lista, session);
    	preencherTabela(tbPrincipal, tarefas, new JTextField());
    	session.close();
    	//JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, "Essa tela ainda n�o esta pronta! Modo somente leitura");
    }
	public void ativarBotao(JCheckBox radio){
		radio.setSelected(true);
	}
	public void mouseClicked(MouseEvent e) {
		switch(e.getComponent().getName()){
		case "Tudo":
			mostrarDatas(pnData, false);
			break;
		case "EssaSemana":
			mostrarDatas(pnData, false);
			break;
		case "Hoje":
			mostrarDatas(pnData, false);
			break;
		case "Definir":
			mostrarDatas(pnData, true);
			break;
		default:
			break;
		}

	}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void actionPerformed(ActionEvent e) {

    }
    private void carregarAtendentes(){
    	List<Usuario> lista = new UsuarioDAO().listar(Usuario.class,session);
    	cbAtendentes.removeAllItems();
    	cbAtendentes.addItem("Todos");

    	Set<String> arvore = new TreeSet();
    	Iterator<Usuario> iterator = lista.listIterator();
    	while(iterator.hasNext()){
    		Usuario u = iterator.next();
    		arvore.add(u.getLogin());
    		atendentes.put(u.getLogin(), u);
    	}
    	arvore.forEach(a->{
    		cbAtendentes.addItem(a);
    	});
    }
    public class Finalize implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch(arg0.getActionCommand()){

			}
			//System.out.println("Valor da linha: "+tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(),0));
		}

    }
    private void preencherTabela(JTable table, List<Tarefa> lista, JTextField txContador){
    	Object[] tableHeader = {"ID","PRAZO","ANDAMENTO","TIPO","NOME","STATUS",
    			"DETALHES","ATENDENTE", "FINALIZADO","ABRIR","EDITAR","EXCLUIR"};
    	DefaultTableModel model = new DefaultTableModel(tableHeader,0){
    		boolean[] canEdit = new boolean[]{
    				false,false,false,false,false,false,false,false,true,true,true,true
    		};
    		@Override
    		public boolean isCellEditable(int rowIndex, int columnIndex) {
    			return canEdit [columnIndex];
    		}
    		@Override
    		public Class getColumnClass(int columnIndex) {
    			return getValueAt(0, columnIndex).getClass();
    		}
    	};
    	if(!lista.isEmpty()){
    		for(int i=0;i<lista.size();i++){
    			Tarefa t = lista.get(i);
    			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    			SimpleDateFormat sdH = new SimpleDateFormat("HH:mm");

    			Object[] o = new Object[12];
    			o[0] = t.getId();
    			o[1] = sdf.format(t.getDataEvento())+" "+sdH.format(t.getHoraEvento());
    			o[2] = t.getTipoTarefa().getNome();
    			o[3] = t.getClasse();
    			if(Empresa.class.getSimpleName().equals(t.getClasse()))
    				o[4] = t.getEmpresa().getNome();
    			else if(Negocio.class.getSimpleName().equals(t.getClasse()))
    				o[4] = t.getNegocio().getNome();
    			else if(Pessoa.class.getSimpleName().equals(t.getClasse()))
    				o[4] = t.getPessoa().getNome();
    			else
    				o[4] = "Erro";
    			o[5] = "Aberto";
    			o[6] = t.getDescricao();
    			o[7] = t.getAtendente().getLogin();
    			if(t.getFinalizado()==0)
    				o[8] =Boolean.FALSE;
    			else
    				o[8]=Boolean.TRUE;
    			o[9]= t.getClasse();
    			o[10] ="Editar";
    			o[11] ="Excluir";
    			model.addRow(o);
    		}
    		txContador.setText("Total: "+lista.size()+" registros");
    		table.setModel(model);
    		new ButtonColumn(table,9);
    		new ButtonColumn(table,10);
    		new ButtonColumn(table,11);

    	}
    }
    private boolean validarDatas(){
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try{
			calendar.setTime(jData1.getDate());
			calendar2.setTime(jData2.getDate());
			return true;
		}catch(NullPointerException e){
			return false;
		}
	}
    private boolean verificarSeHoje(Date data1,Date data2){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	return sdf.format(data1).equals(sdf.format(new Date())) && sdf.format(data1).equals(sdf.format(data2));
    }
    private void mostrarDatas(JPanel panel, boolean esconder){
    	panel.setVisible(esconder);
    }
}
