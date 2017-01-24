package br.com.tiagods.controller;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
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
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	
	@SuppressWarnings("rawtypes")
	public AuxiliarTabela(Object classe, JTable tabela, List lista,List<Criterion> criterios, Order order){
		this.object=classe;
		this.table=tabela;
		this.lista=lista;
		this.criterios = criterios;
		this.order = order;
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
	private DefaultTableModel gerarModel(Tarefa tarefas){
		return new DefaultTableModel(new Object[]{"ID","DATA","TIPO","ATENDENTE","STATUS","ANDAMENTO","EDITAR","EXCLUIR"},0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false,false,true,true,true
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
		Iterator<Tarefa> iterator = lista.iterator();
		while(iterator.hasNext()){
			Tarefa t = iterator.next();
			Object[] o = new Object[model.getColumnCount()];
			o[0]=t.getId();
			o[1]=sdf.format(t.getDataEvento());
			o[2]=t.getTipoTarefa().getNome();
			o[3]=t.getAtendente().getNome();
			o[4]=t.getFinalizado()==0?"Aberto":"Finalizado";
			if(t.getFinalizado()==0)
				o[5]=Boolean.FALSE;
			else
				o[5]=Boolean.TRUE;
			o[6] = "Editar";
			o[7] = "Excluir";
			model.addRow(o);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.setFont(new Font("Tahoma", Font.PLAIN, 10));
		table.setRowHeight(30);
		
		JCheckBox ckFinalizar = new JCheckBox();
		TableColumn col = table.getColumnModel().getColumn(5);
		col.setCellEditor(new DefaultCellEditor(ckFinalizar));
		ckFinalizar.setActionCommand("Finalizar");
		ckFinalizar.addActionListener(new AcaoInTable());
		
		JButton btEdit = new ButtonColumnModel(table,6).getButton();
		btEdit.setActionCommand("Editar");
		btEdit.addActionListener(new AcaoInTable());

		JButton btExcluir =new ButtonColumnModel(table,7).getButton();
		btExcluir.setActionCommand("Excluir");
		btExcluir.addActionListener(new AcaoInTable());
	}
	
	public class AcaoInTable implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Session session = HibernateFactory.getSession();
			session.beginTransaction();
			switch(e.getActionCommand()){
			case "Finalizar":
				finalizar(session);
				break;
			case "Editar":
				int valor = (int)table.getModel().getValueAt(table.getSelectedRow(), 0);
				Tarefa tarefa = (Tarefa)new TarefaDao().receberObjeto(Tarefa.class, valor, session);
				TarefasSaveView viewTarefas = new TarefasSaveView(tarefa,object,MenuView.getInstance(),true);
				viewTarefas.setVisible(true);
				break;
			case "Excluir":
				excluir(session);
				break;
			default:
				break;
			}
			session.close();
		}
	}
	public void excluir(Session session){
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
	}
	String pendente = "Aberto";
	String fechado = "Finalizado";
	
	public void finalizar(Session session){
		boolean value = (boolean)table.getValueAt(table.getSelectedRow(), 5);
		int id = (int)table.getValueAt(table.getSelectedRow(), 0);
		String status = (String)table.getValueAt(table.getSelectedRow(), 4);
		if(!value && pendente.equals(status)){
			TarefaDao dao = new TarefaDao();
			Tarefa thisTar = (Tarefa) dao.receberObjeto(Tarefa.class, id, session);
			thisTar.setFinalizado(1);
			if(dao.salvar(thisTar, session)){
				table.setValueAt(fechado, table.getSelectedRow(), 4);
			}

		}
		else if(value && fechado.equals(status)){
			TarefaDao dao = new TarefaDao();
			Tarefa thisTar = (Tarefa) dao.receberObjeto(Tarefa.class, id, session);
			thisTar.setFinalizado(0);
			if(dao.salvar(thisTar, session)){
				table.setValueAt(pendente, table.getSelectedRow(), 4);
			}
		}
		//buscar(session);
	}

	private void buscar(Session session){
		List<Tarefa> tarefas = (List<Tarefa>) new GenericDao().items(Tarefa.class, session, criterios, order);
		AuxiliarTabela aux = new AuxiliarTabela(object, table, tarefas, criterios, order);
		aux.analizarEntidades();
	}
	
}
