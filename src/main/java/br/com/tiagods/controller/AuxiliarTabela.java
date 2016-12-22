package br.com.tiagods.controller;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;

public class AuxiliarTabela {
	Object object;
	JTable table;
	@SuppressWarnings("rawtypes")
	List lista;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	
	@SuppressWarnings("rawtypes")
	public AuxiliarTabela(Object classe, JTable tabela, List lista){
		this.object=classe;
		this.table=tabela;
		this.lista=lista;
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
		return new DefaultTableModel(new Object[]{"ID","DATA","TIPO","ATENDENTE","CONCLUIDO"},0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false,true
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
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
			if(t.getFinalizado()==1)
				o[4]="Fechado";
			else
				o[4]="Aberto";
			model.addRow(o);
		}
		table.setModel(model);
	}

}
