package br.com.tiagods.view.interfaces;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class LabelTable extends DefaultTableCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6409253765888639848L;

	@SuppressWarnings("unused")
	protected void setValue(Object value){  
        if (value instanceof ImageIcon){  
            if (value != null){  
                ImageIcon d = (ImageIcon) value;  
                setIcon(d);  
                setHorizontalAlignment(SwingConstants.CENTER);
            } 
            else{  
                setText("");  
                setIcon(null);  
                setHorizontalAlignment(SwingConstants.CENTER);
            }  
        } else {  
            super.setValue(value);  
        }  
    }
}
