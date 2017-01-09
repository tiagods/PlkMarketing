package br.com.tiagods.view.interfaces;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JTableCelulaColor extends JLabel implements TableCellRenderer{
	    boolean finalizado;
		public JTableCelulaColor(boolean finalizado){
	        this.finalizado=finalizado;
			this.setOpaque(true);	        
	    }
		@Override
	    public Component getTableCellRendererComponent(
	        JTable table, 
	        Object value, boolean isSelected, boolean hasFocus,
	           int row, int column){

	    	Date data = new Date();
	        
	    	Date dataForm=null;
			try {
				dataForm = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(value.toString()) ;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	if(data.compareTo(dataForm)>0 && !finalizado){
	    		setBackground(Color.RED);
	    		setForeground(Color.WHITE);
	    	}
	    	else{
	    		setBackground(Color.GREEN);
	    		setForeground(Color.WHITE);
	    		//setBackground(table.getBackground());		
	    	}
	    	
//	    	if(Integer.parseInt(value.toString()) < 20){
//	          setBackground(Color.YELLOW);	
//	        }
//	        else{
//	          setBackground(table.getBackground());		
//	        }
	        setText(value.toString());
	        return this;   	
	    }
	  @Override
	  public void validate() {}
	  @Override
	  public void revalidate() {}
	  @Override
	  protected void firePropertyChange(String propertyName,
	     Object oldValue, Object newValue) {}
	  @Override
	  public void firePropertyChange(String propertyName,
	     boolean oldValue, boolean newValue) {}

	}
