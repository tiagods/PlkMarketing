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

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modelDAO.TarefaDAO;
import br.com.tiagods.view.interfaces.*;
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
    	JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, "Essa tela ainda não esta pronta! Modo somente leitura");
    }
	public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void actionPerformed(ActionEvent e) {
    	
    }
    public class Finalize implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Valor da linha: "+tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(),0));
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
}
