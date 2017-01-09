package br.com.tiagods.view.interfaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiago
 */
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class JTextAreaTableExample extends JFrame {

	public JTextAreaTableExample() {
		super( "JTextAreaTableExample Example" );

		DefaultTableModel dtm = new DefaultTableModel() {
			//make first cell uneditable
			public boolean isCellEditable(int row, int column)
			{
				return !(column == 0);
			}
		};

		dtm.setDataVector(new Object[][]{{ "JTextArea1", "This is a testnon long linesn" },
			{ "JTextArea2", "Hello, world!" }},
				new Object[]{ "String","JTextArea"});

		JTable table = new JTable(dtm);
		table.getColumn("JTextArea").setCellRenderer(new TextAreaRenderer());
		table.getColumn("JTextArea").setCellEditor(new TextAreaEditor());

		table.setRowHeight(80);
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);

		setSize( 400, 250 );
		setVisible(true);
	}

	public static void main(String[] args) {
		JTextAreaTableExample frame = new JTextAreaTableExample();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
class TextAreaRenderer extends JScrollPane implements TableCellRenderer
{
	JTextArea textarea;

	public TextAreaRenderer() {
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setBorder(new TitledBorder("This is a JTextArea"));
		getViewport().add(textarea);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus,
			int row, int column)
	{
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
			textarea.setForeground(table.getSelectionForeground());
			textarea.setBackground(table.getSelectionBackground());
		}else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
			textarea.setForeground(table.getForeground());
			textarea.setBackground(table.getBackground());
		}

		textarea.setText((String) value); 
		textarea.setCaretPosition(0);
		return this;
	}
}
class TextAreaEditor extends DefaultCellEditor {
	protected JScrollPane scrollpane;
	protected JTextArea textarea; 

	public TextAreaEditor() {
		super(new JCheckBox());
		scrollpane = new JScrollPane();
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setBorder(new TitledBorder("This is a JTextArea"));
		scrollpane.getViewport().add(textarea);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		textarea.setText((String) value);

		return scrollpane;
	}

	public Object getCellEditorValue() {
		return textarea.getText();
	}
}
