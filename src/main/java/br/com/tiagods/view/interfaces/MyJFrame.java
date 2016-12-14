package br.com.tiagods.view.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.CardLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import java.awt.List;

public class MyJFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyJFrame frame = new MyJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		DefaultComboBox comboBox = new DefaultComboBox();
		comboBox.addItem("ajsfd");
		comboBox.addItem("asdfasjdfkjsalfdjlasdjkflskjf");
		
		DefaultListModel model = new DefaultListModel();
		JList list = new JList(model);
		
		model.add(0,"item 1");
		model.add(1,"item 2");
		model.add(2,"item 3");
		
		JScrollPane jscroll = new JScrollPane(list);
		GroupLayout gPanel = new GroupLayout(panel);
		gPanel.setHorizontalGroup(
			gPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gPanel.createSequentialGroup()
					.addContainerGap(170, Short.MAX_VALUE)
					.addGroup(gPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(jscroll, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBox, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
					.addGap(152))
		);
		gPanel.setVerticalGroup(
			gPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gPanel.createSequentialGroup()
					.addGap(91)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(jscroll, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(101, Short.MAX_VALUE))
		);
		panel.setLayout(gPanel);
	}
}
