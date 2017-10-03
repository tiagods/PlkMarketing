package br.com.tiagods.controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Prospeccao;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.TarefaDao;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.interfaces.ButtonColumnModel;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;

public class AuxiliarTabela {
	Object object;
	JTable table;
	@SuppressWarnings("rawtypes")
	List lista;
	List<Criterion> criterios;
	Order order;
	Map<String,JRadioButton> rbNegocioAndamento;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	SimpleDateFormat sdh = new SimpleDateFormat("HH:mm");
	
	@SuppressWarnings("rawtypes")
	public AuxiliarTabela(Object classe, JTable tabela, List lista,List<Criterion> criterios, Order order, Map<String,JRadioButton> rbNegocioAndamento){
		this.object=classe;
		this.table=tabela;
		this.lista=lista;
		this.criterios = criterios;
		this.order = order;
		this.rbNegocioAndamento = rbNegocioAndamento;
		analizarEntidades();
	}
	@SuppressWarnings("unchecked")
	public void analizarEntidades(){
			if(object instanceof Empresa){
				if(lista.isEmpty())
					new SemRegistrosJTable(this.table,"Registro de Empresas");
				else{
					DefaultTableModel model = gerarModel((Empresa)object);
					popularTabela(lista,(Empresa)object, model);
				}
			}
			else if(object instanceof Negocio){
				if(lista.isEmpty())
					new SemRegistrosJTable(this.table,"Registro de Negocio");
				else{
					DefaultTableModel model = gerarModel((Negocio)object);
					popularTabela(lista,(Negocio)object, model);
				}
			}
			else if(object instanceof Pessoa){
				if(lista.isEmpty())
					new SemRegistrosJTable(this.table,"Registro de Pessoa");
				else{
					DefaultTableModel model = gerarModel((Pessoa)object);
					popularTabela(lista,(Pessoa)object, model);
				}
			}
			else if(object instanceof Prospeccao){
				if(lista.isEmpty())
					new SemRegistrosJTable(this.table,"Registro de Prospeccao");
				else{
					DefaultTableModel model = gerarModel((Prospeccao)object);
					popularTabela(lista,(Prospeccao)object, model);
				}
			}
			else if(object instanceof Tarefa){
				if(lista.isEmpty())
					new SemRegistrosJTable(this.table,"Registro de Tarefa");
				else{
					DefaultTableModel model = gerarModel((Tarefa)object);
					popularTabela(lista, (Tarefa)object, model);

				}
			}
	}
	@SuppressWarnings("serial")
	private DefaultTableModel gerarModel(Empresa empresas){
		return new DefaultTableModel(new Object[]{"ID","NOME","CRIADO EM","ATENDENTE"},0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
	}
	@SuppressWarnings("serial")
	private DefaultTableModel gerarModel(Negocio negocios){
		return new DefaultTableModel(new Object[]{"ID","NOME","DATA INICIO","DATA FIM","ETAPA","STATUS","ATENDENTE"},0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false,false,false,false,false
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
	}
	@SuppressWarnings("serial")
	private DefaultTableModel gerarModel(Pessoa pessoas){
		return new DefaultTableModel(new Object[]{"ID","NOME","CRIADO EM","ATENDENTE"},0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
	}
	@SuppressWarnings("serial")
	private DefaultTableModel gerarModel(Prospeccao prospeccao){
		return new DefaultTableModel(new Object[]{"ID","NOME","CRIADO EM","ATENDENTE"},0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
	}
	@SuppressWarnings("serial")
	private DefaultTableModel gerarModel(Tarefa tarefas){
		return new DefaultTableModel(new Object[]{"ID","DATA","TIPO","ATENDENTE","DESCRICAO","EDITAR","EXCLUIR"},0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false,false,true,true
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return getValueAt(0, columnIndex).getClass();
			}
		};
	}
	private void popularTabela(List<Empresa> lista, Empresa empresa, DefaultTableModel model){
		Iterator<Empresa> iterator = lista.iterator();
		while(iterator.hasNext()){
			Empresa e = iterator.next();
			Object[] o = new Object[model.getColumnCount()];
			o[0]=e.getId();
			o[1]=e.getNome();
			o[2]=sdf.format(e.getPessoaJuridica().getCriadoEm());
			o[3]=e.getPessoaJuridica().getAtendente().getNome();
			model.addRow(o);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
	}
	private void popularTabela(List<Negocio> lista, Negocio negocio, DefaultTableModel model){
		Iterator<Negocio> iterator = lista.iterator();
		while(iterator.hasNext()){
			Negocio n = iterator.next();
			Object[] o = new Object[model.getColumnCount()];
			o[0]=n.getId();
			o[1]=n.getNome();
			o[2]=sdf.format(n.getDataInicio());
			if(n.getDataFim()==null)
				o[3]="";
			else
				o[3]=sdf.format(n.getDataFim());
			o[4]=n.getEtapa().getNome();
			o[5]=n.getStatus().getNome();
			o[6]=n.getAtendente().getNome();
			model.addRow(o);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
	}
	private void popularTabela(List<Prospeccao> lista, Prospeccao prospeccao, DefaultTableModel model){
		Iterator<Prospeccao> iterator = lista.iterator();
		while(iterator.hasNext()){
			Prospeccao p = iterator.next();
			Object[] o = new Object[model.getColumnCount()];
			o[0]=p.getId();
			o[1]=p.getNome();
			o[2]=sdf.format(p.getPfpj().getCriadoEm());
			o[3]=p.getPfpj().getAtendente().getNome();
			model.addRow(o);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
	}
	private void popularTabela(List<Pessoa> lista, Pessoa pessoa, DefaultTableModel model){
		Iterator<Pessoa> iterator = lista.iterator();
		while(iterator.hasNext()){
			Pessoa p = iterator.next();
			Object[] o = new Object[model.getColumnCount()];
			o[0]=p.getId();
			o[1]=p.getNome();
			o[2]=sdf.format(p.getPessoaFisica().getCriadoEm());
			o[3]=p.getPessoaFisica().getAtendente().getNome();
			model.addRow(o);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
	}
	private void popularTabela(List<Tarefa> lista, Tarefa tarefa, DefaultTableModel model){
		lista.sort((a,b)-> b.getDataEvento().compareTo(a.getDataEvento()));//ordenar por ordem data decrescente
		Iterator<Tarefa> iterator = lista.iterator();
		while(iterator.hasNext()){
			Tarefa t = iterator.next();
			Object[] o = new Object[model.getColumnCount()];
			o[0]=t.getId();
			o[1]=sdf.format(t.getDataEvento()) + " às "+ sdh.format(t.getDataEvento());
			String statusValue = "";
			switch(t.getTipoTarefa().getId()){
			case 1:
				statusValue = "tarefas_visita";
				break;
			case 2:
				statusValue = "tarefas_reuniao";
				break;
			case 3:
				statusValue = "tarefas_proposta";
				break;
			case 4:
				statusValue = "tarefas_fone";
				break;
			case 5:
				statusValue = "tarefas_email";
				break;
			case 6:
				statusValue = "tarefas_whatsapp";
				break;
			default:
				statusValue = "button_question";
				break;
			}
			o[2] = recalculate(new ImageIcon(ControllerNegocios.class
					.getResource("/br/com/tiagods/utilitarios/"+statusValue+".png")),15);
			
			o[3]=t.getAtendente().getNome();
			o[4]=t.getDescricao();
//			o[5]=t.getFinalizado()==0?"Aberto":"Finalizado";
//			if(t.getFinalizado()==0)
//				o[6]=Boolean.FALSE;
//			else
//				o[6]=Boolean.TRUE;
			o[5] = recalculate(new ImageIcon(AuxiliarTabela.class
					.getResource("/br/com/tiagods/utilitarios/button_edit.png")));
			o[6] = recalculate(new ImageIcon(AuxiliarTabela.class
					.getResource("/br/com/tiagods/utilitarios/button_trash.png")));
			model.addRow(o);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().getColumn(6).setPreferredWidth(30);
		table.setFont(new Font("Tahoma", Font.PLAIN, 10));
		table.setRowHeight(30);
				
		JButton btEdit = new ButtonColumnModel(table,5).getButton();
		btEdit.setActionCommand("Editar");
		btEdit.addActionListener(new AcaoInTable());

		JButton btExcluir =new ButtonColumnModel(table,6).getButton();
		btExcluir.setActionCommand("Excluir");
		btExcluir.addActionListener(new AcaoInTable());
	}
	
	public class AcaoInTable implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand()){
			case "Editar":
				int valor = (int)table.getModel().getValueAt(table.getSelectedRow(), 0);
				Session session = HibernateFactory.getSession();
				session.beginTransaction();
				Tarefa tarefa = (Tarefa)new TarefaDao().receberObjeto(Tarefa.class, valor, session);
				TarefasSaveView viewTarefas = new TarefasSaveView(tarefa,object,rbNegocioAndamento,MenuView.getInstance(),true,null, false);
				viewTarefas.setVisible(true);
				viewTarefas.addWindowListener(new WindowListener() {
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						Session session = HibernateFactory.getSession();
						session.beginTransaction();
						buscar(session);
						session.close();
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				if(session.isOpen())
					session.close();
				break;
			case "Excluir":
				excluir();
				break;
			default:
				break;
			}
		}
	}
	public void excluir(){
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		int row = table.getSelectedRow();
		int i = JOptionPane.showConfirmDialog(br.com.tiagods.view.MenuView.jDBody, 
				"Deseja excluir a seguinte tarefa: "+table.getValueAt(row, 0)+"?",
				"Pedido de remoção!",JOptionPane.YES_NO_OPTION);
		if(i==JOptionPane.OK_OPTION){
			TarefaDao dao = new TarefaDao();
			int id = (int)table.getValueAt(table.getSelectedRow(), 0);
			Tarefa tRemove = (Tarefa)dao.receberObjeto(Tarefa.class, id, session);
			if(dao.excluir(tRemove, session))
				buscar(session);
		}
		session.close();
	}
	@SuppressWarnings("unchecked")
	private void buscar(Session session){
		List<Tarefa> tarefas = (List<Tarefa>) new GenericDao().items(Tarefa.class, session, criterios, order);
		this.lista = tarefas;
		analizarEntidades();
	}
	
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }	
	public ImageIcon recalculate(ImageIcon icon, int valor) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()-valor, icon.getIconHeight()-valor, 100));
    	return icon;
    }
}
