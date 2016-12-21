package br.com.tiagods.view.interfaces;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CentralizarColumnJTable extends DefaultTableCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
 			boolean isSelected, boolean hasFocus, int row, int column) {
 		this.setHorizontalAlignment(CENTER);
 		return super.getTableCellRendererComponent(table, value, isSelected,
 				hasFocus, row, column);
 	}

}
