package br.com.tiagods.view.interfaces;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
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
        String text;
        String imagePath = "/br/com/tiagods/utilitarios/";
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
            if (hasFocus)
            {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            else if (isSelected)
            {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            }
            else
            {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            renderButton.setText( (value == null) ? "" : value.toString() );
            return renderButton;
        }
        public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
        {
            text = (value == null) ? "" : value.toString();
            editButton.setText( text );
            return editButton;
        }
        public Object getCellEditorValue()
        {
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
        
        public JButton getButtonIcon(JButton button) throws NullPointerException{
        	String imageName = "";
        	switch(text){
        	case "Editar":
        		imageName="edit.png";
        		break;
        	case "Excluir":
        		imageName="remove.png";
        		break;
        	case "Pessoa":
        		imageName="people.png";
        		break;
        	case "Empresa":
        		imageName="empresas.png";
        		break;
        	case "Negocio":
        		imageName="negocios.png";
        		break;
        	default:
        		break;
        	}
        	ImageIcon icon = new ImageIcon(ButtonColumnModel.class.getResource(imagePath+imageName));
        	button.setIcon(icon);
        	return button;
        }
        
        public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
        	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
        	return icon;
        }
    }

