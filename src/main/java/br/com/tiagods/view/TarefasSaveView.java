package br.com.tiagods.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;
/*
 */

import br.com.tiagods.view.interfaces.DefaultUtilities;
import br.com.tiagods.view.interfaces.DefaultModelComboBox;
import br.com.tiagods.view.interfaces.DefaultModelComboBox.Modelos;
import javax.swing.JTextArea;

public class TarefasSaveView extends JInternalFrame implements DefaultUtilities {
	private JTextField textField;
	private JTextField txtPassarComoParametro;
	
	public static JDateChooser txData;
	public static JComboBox cbTipo, cbAtendente;
	public static JTextArea txDetalhes;
	public static JFormattedTextField txHora;
	
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
		JPanel panel = new JPanel();
		JPanel panel_1 = new JPanel();
        
		setBounds(0, 0, 1250, 660);
        setBorder(null);
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setBackground(getColor());
        panel.setLayout(null);
        
        panel_1.setBounds(10, 11, 132, 69);
        panel.add(panel_1);
        
        JLabel lblCod = new JLabel("Cod");
        panel_1.add(lblCod);
        
        textField = new JTextField();
        panel_1.add(textField);
        textField.setColumns(10);
        
        JLabel lblClasse = new JLabel("Classe");
        panel_1.add(lblClasse);
        
        txtPassarComoParametro = new JTextField();
        txtPassarComoParametro.setText("passar como parametro e nao como string");
        panel_1.add(txtPassarComoParametro);
        txtPassarComoParametro.setColumns(10);
        
        txData = new JDateChooser();
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(174, 192, 846, 118);
        panel.add(scrollPane);
        
        txDetalhes = new JTextArea();
        scrollPane.setViewportView(txDetalhes);
        
        JPanel panItem = new JPanel();
        panItem.setBounds(174, 148, 846, 33);
        panel.add(panItem);
        
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton rdbtnEmail = new JRadioButton("E-mail");
        group.add(rdbtnEmail);
        panItem.add(rdbtnEmail);
        
        JRadioButton rdbtnProposta = new JRadioButton("Proposta");
        group.add(rdbtnProposta);
        panItem.add(rdbtnProposta);
        
        JRadioButton rdbtnReuniao = new JRadioButton("Reuni\u00E3o");
        group.add(rdbtnReuniao);
        panItem.add(rdbtnReuniao);
        
        JRadioButton rdbtnTelefone = new JRadioButton("Telefone");
        group.add(rdbtnTelefone);
        panItem.add(rdbtnTelefone);
        
        JRadioButton rdbtnVisita = new JRadioButton("Visita");
        group.add(rdbtnVisita);
        panItem.add(rdbtnVisita);
        
        JPanel panEscolha = new JPanel();
        FlowLayout fl_panEscolha = (FlowLayout) panEscolha.getLayout();
        panEscolha.setBounds(174, 332, 846, 33);
        panel.add(panEscolha);
        
        JLabel lblTipo = new JLabel("Tipo:");
        panEscolha.add(lblTipo);
        
        cbTipo = new JComboBox();
        cbTipo.setModel(new DefaultComboBoxModel(Modelos.values()));
        panEscolha.add(cbTipo);
        
        JButton button = new JButton("+");
        panEscolha.add(button);
        
        JLabel lblNewLabel_1 = new JLabel("{Cod***}");
        panEscolha.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("{Name***}");
        panEscolha.add(lblNewLabel_2);
        
        JLabel lblResponsavel = new JLabel("Responsavel:");
        panEscolha.add(lblResponsavel);
        
        cbAtendente = new JComboBox();
        panEscolha.add(cbAtendente);
        
        JLabel lblData = new JLabel("Data:");
        panEscolha.add(lblData);
        
        panEscolha.add(txData);
        
        JLabel lblHora = new JLabel("Hora:");
        panEscolha.add(lblHora);
        
        txHora = new JFormattedTextField();
        txHora.setColumns(5);
        panEscolha.add(txHora);
        
        JButton btnEditar = new JButton("Editar");
        panEscolha.add(btnEditar);
        
        JButton btnCancelar = new JButton("Cancelar");
        panEscolha.add(btnCancelar);
        
        JButton btnSalvar = new JButton("Salvar");
        panEscolha.add(btnSalvar);
        
        JLabel lblNewLabel = new JLabel("quando na\u00F5 for passado parametro de classe  e invocar um jcomponents solitando Negocio, Empresa ou pessoa para salvar");
        lblNewLabel.setBounds(174, 124, 846, 14);
        panel.add(lblNewLabel);

	}
}
