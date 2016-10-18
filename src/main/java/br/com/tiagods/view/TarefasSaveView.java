package br.com.tiagods.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;
/*
 */

import br.com.tiagods.utilitarios.DefaultUtilities;

public class TarefasSaveView extends JInternalFrame implements DefaultUtilities {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JDateChooser dateChooser;

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return DefaultUtilities.super.getColor();
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TarefasSaveView frame = new TarefasSaveView();
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
	public TarefasSaveView() {

        setBounds(0, 0, 1250, 660);
        setBorder(null);
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setBackground(getColor());
        panel.setLayout(null);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 11, 132, 96);
        panel.add(panel_1);
        
        JLabel lblCod = new JLabel("Cod");
        panel_1.add(lblCod);
        
        textField = new JTextField();
        panel_1.add(textField);
        textField.setColumns(10);
        
        JLabel lblClasse = new JLabel("Classe");
        panel_1.add(lblClasse);
        
        textField_1 = new JTextField();
        panel_1.add(textField_1);
        textField_1.setColumns(10);
        
        dateChooser = new JDateChooser();
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(174, 192, 846, 118);
        panel.add(scrollPane);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(174, 148, 846, 33);
        panel.add(panel_2);
        
        JRadioButton rdbtnVisita = new JRadioButton("Visita");
        panel_2.add(rdbtnVisita);
        
        JRadioButton rdbtnReunio = new JRadioButton("Reuni\u00E3o");
        panel_2.add(rdbtnReunio);
        
        JRadioButton rdbtnProposta = new JRadioButton("Proposta");
        panel_2.add(rdbtnProposta);
        
        JRadioButton rdbtnTelefone = new JRadioButton("Telefone");
        panel_2.add(rdbtnTelefone);
        
        JRadioButton rdbtnEmail = new JRadioButton("E-mail");
        panel_2.add(rdbtnEmail);
        
        JPanel panel_3 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
        panel_3.setBounds(174, 332, 846, 33);
        panel.add(panel_3);
        
        JLabel lblTipo = new JLabel("Tipo:");
        panel_3.add(lblTipo);
        
        textField_2 = new JTextField();
        panel_3.add(textField_2);
        textField_2.setColumns(5);
        
        JLabel lblResponsavel = new JLabel("Responsavel:");
        panel_3.add(lblResponsavel);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Antonio"}));
        panel_3.add(comboBox);
        
        JLabel lblData = new JLabel("Data:");
        panel_3.add(lblData);
        
        panel_3.add(dateChooser);
        
        JLabel lblHora = new JLabel("Hora:");
        panel_3.add(lblHora);
        
        JFormattedTextField formattedTextField = new JFormattedTextField();
        formattedTextField.setColumns(5);
        panel_3.add(formattedTextField);
        
        JButton btnEditar = new JButton("Editar");
        panel_3.add(btnEditar);
        
        JButton btnCancelar = new JButton("Cancelar");
        panel_3.add(btnCancelar);
        
        JButton btnSalvar = new JButton("Salvar");
        panel_3.add(btnSalvar);

	}
}
