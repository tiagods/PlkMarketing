package br.com.tiagods.view.interfaces;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.plaf.metal.MetalIconFactory;

public class Main1 {

	public static void main(String args[]) {
		JFrame f = new JFrame("JToolbar Sample");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = f.getContentPane();
		JToolBar toolbar = new JToolBar();
		Icon icon = MetalIconFactory.getFileChooserDetailViewIcon();
		JToggleButton button = new JToggleButton(icon);
		toolbar.add(button);
		icon = MetalIconFactory.getFileChooserHomeFolderIcon();
		button = new JToggleButton(icon);
		toolbar.add(button);
		icon = MetalIconFactory.getFileChooserListViewIcon();
		button = new JToggleButton(icon);
		toolbar.add(button);
		content.add(toolbar, BorderLayout.NORTH);
		f.setSize(300, 100);
		f.setVisible(true);
	}

}
