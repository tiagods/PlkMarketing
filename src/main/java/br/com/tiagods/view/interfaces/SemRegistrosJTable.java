package br.com.tiagods.view.interfaces;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SemRegistrosJTable {
	public SemRegistrosJTable(JTable table, String titulo){
		DefaultTableModel model = new DefaultTableModel(
				new Object[][]{{"Nenhum Registro foi Localizado"}},
				new String[]{titulo}){
			boolean[] canEdit = new boolean[]{
					false
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
		table.setModel(model);
		TableColumn column = table.getColumnModel().getColumn(0);
		column.setCellRenderer(new CentralizarColumnJTable());
	}
	
}
