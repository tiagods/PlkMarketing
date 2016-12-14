package br.com.tiagods.view.interfaces;
import javax.swing.*; 
import java.awt.*; 
import java.util.Vector; 
 
// got this workaround from the following bug: 
//      http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4618607 
public class DefaultComboBox extends JComboBox{ 
 
    public DefaultComboBox() { 
    } 
 
    public DefaultComboBox(final Object items[]){ 
        super(items); 
    } 
 
    public DefaultComboBox(Vector items) { 
        super(items); 
    } 
 
    public DefaultComboBox(ComboBoxModel aModel) { 
        super(aModel); 
    } 
 
    private boolean layingOut = false; 
 
    public void doLayout(){ 
        try{ 
            layingOut = true; 
            super.doLayout(); 
        }finally{ 
            layingOut = false; 
        } 
    } 
 
    public Dimension getSize(){ 
        Dimension dim = super.getSize(); 
        if(!layingOut) 
            dim.width = Math.max(dim.width, getPreferredSize().width); 
        return dim; 
    } 
}
