/*
 * Todos direitos reservados a Tiago Dias de Souza.
  */
package br.com.tiagods.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modelDAO.TarefaDAO;
import static br.com.tiagods.view.TarefasView.*;
/*
* @author Tiago
*/
public class ControllerTarefas implements ActionListener, MouseListener {
    public void iniciar(){
    	Session sessao = HibernateFactory.getSession();
    	List<Tarefa> tarefas = new TarefaDAO().listar(Tarefa.class, sessao);
    	preencherTabela(tbPrincipal, tarefas, new JTextField());
    	sessao.close();
    }
	public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void actionPerformed(ActionEvent e) {
    }
    private void preencherTabela(JTable table, List<Tarefa> lista, JTextField txContador){
    			Object[] tableHeader = {"ID","PRAZO","ANDAMENTO","TIPO","STATUS","NOME","ATENDENTE", "FINALIZADO"};
    			DefaultTableModel model = new DefaultTableModel(tableHeader,0){
    				boolean[] canEdit = new boolean[]{
    					false,false,false,false,false,false,false,true
    				};
    				@Override
    				public boolean isCellEditable(int rowIndex, int columnIndex) {
    					return canEdit [columnIndex];
    				}
    			};
    			if(!lista.isEmpty()){
    				for(int i=0;i<lista.size();i++){
    					Tarefa t = lista.get(i);
    					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						model.addRow(new Object[]{
    							t.getId(), 
    	    					sdf.format(t.getDataEvento()),
    	    					t.getTipoTarefa().getNome(),
    	    					t.getClasse(),
    	    					"Aberto",
    	    					t.getDescricao(),
    	    					t.getAtendente().getLogin(),
    	    					Boolean.FALSE
    					});
    				}
    				txContador.setText("Total: "+lista.size()+" registros");
					table.setModel(model);
    			}
    }
}
