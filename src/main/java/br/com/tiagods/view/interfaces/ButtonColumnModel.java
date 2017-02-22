package br.com.tiagods.view.interfaces;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class ButtonColumnModel extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor{
        /**
	 * 
	 */
	private static final long serialVersionUID = 2516017885760546736L;
		JTable table;
        JButton renderButton;
        JButton editButton;
        Object text;
        public ButtonColumnModel(JTable table, int column)
        {
            super();
            this.table = table;
            renderButton = new JButton();
            editButton = new JButton();
            editButton.setFocusPainted( false );
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer( this );
            columnModel.getColumn(column).setCellEditor( this );
        }
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
//            if(isSelected){
//                renderButton.setForeground(table.getSelectionForeground());
//                renderButton.setBackground(table.getSelectionBackground());
//            }
//            else{
//                renderButton.setForeground(table.getForeground());
//                renderButton.setBackground(UIManager.getColor("Button.background"));
//            }
        	if(value instanceof String){
        		renderButton.setText( (value == null) ? "" : value.toString() );
        	}
        	else if(value instanceof ImageIcon){
        		renderButton.setIcon((ImageIcon)value);
        	}
            return renderButton;
        }
        public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column){
        	if(value instanceof String){
        		text = (value == null) ? "" : value;
        		editButton.setText(text.toString());
        	}
        	else if(value instanceof ImageIcon){
        		text = value;
        		editButton.setIcon((ImageIcon)text);
        	}
        	return editButton;
        }
        public Object getCellEditorValue(){
            return text;
        }
//        public void actionPerformed(ActionEvent e)
//        {
//            fireEditingStopped();
//            //System.out.println( e.getActionCommand() + " : " + table.getSelectedRow());
//        }
        public JButton getButton(){
        	return this.editButton;
        }
        
    }

