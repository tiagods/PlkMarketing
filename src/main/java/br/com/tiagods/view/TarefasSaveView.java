package br.com.tiagods.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.toedter.calendar.JDateChooser;
/*
 */

import br.com.tiagods.controller.ControllerTarefasSave;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.view.interfaces.DefaultModelComboBox.Modelos;
import br.com.tiagods.view.interfaces.DefaultUtilities;

public class TarefasSaveView extends JInternalFrame implements DefaultUtilities {
	
	public static JPanel panItem, panel;
	public static JDateChooser txData;
	public static JComboBox cbObject, cbAtendente;
	public static JTextArea txDetalhes;
	public static JLabel txCodigo, txNome;
	public static JFormattedTextField txHora;
	public static JButton btnNovo, btnEditar, btnSalvar, btnCancelar;
	public static JRadioButton rdbtnReuniao, rdbtnProposta, rdbtnEmail,rdbtnVisita, rdbtnTelefone; 
	ControllerTarefasSave controller  = new ControllerTarefasSave();
	
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return DefaultUtilities.super.getColor();
	}
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TarefasSaveView frame = new TarefasSaveView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TarefasSaveView(Tarefa tarefa) {
		initComponents();
		controller.iniciar(tarefa);
	
	}
	private void initComponents(){
		panel = new JPanel();
        
		setBounds(0, 0, 1250, 660);
        setBorder(null);
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setBackground(getColor());
        panel.setLayout(null);
        
        txData = new JDateChooser();
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(94, 192, 1037, 118);
        panel.add(scrollPane);
        
        txDetalhes = new JTextArea();
        scrollPane.setViewportView(txDetalhes);
        
        panItem = new JPanel();
        panItem.setBounds(94, 148, 1037, 33);
        panel.add(panItem);
        
        ButtonGroup group = new ButtonGroup();
        
        rdbtnEmail = new JRadioButton("E-mail");
        rdbtnEmail.setActionCommand("Email");
        rdbtnEmail.addActionListener(controller);
        group.add(rdbtnEmail);
        panItem.add(rdbtnEmail);
        
        rdbtnProposta = new JRadioButton("Proposta");
        rdbtnProposta.setActionCommand("Proposta");
        rdbtnProposta.addActionListener(controller);
        group.add(rdbtnProposta);
        panItem.add(rdbtnProposta);
        
        rdbtnReuniao = new JRadioButton("Reuni\u00E3o");
        rdbtnReuniao.setActionCommand("Reuniao");
        rdbtnReuniao.addActionListener(controller);
        group.add(rdbtnReuniao);
        panItem.add(rdbtnReuniao);
        
        rdbtnTelefone = new JRadioButton("Telefone");
        rdbtnTelefone.setActionCommand("Telefone");
        rdbtnTelefone.addActionListener(controller);
        group.add(rdbtnTelefone);
        panItem.add(rdbtnTelefone);
        
        rdbtnVisita = new JRadioButton("Visita");
        rdbtnVisita.setActionCommand("Visita");
        rdbtnVisita.addActionListener(controller);
        group.add(rdbtnVisita);
        panItem.add(rdbtnVisita);
        
        JPanel panEscolha = new JPanel();
        FlowLayout fl_panEscolha = (FlowLayout) panEscolha.getLayout();
        panEscolha.setBounds(94, 332, 1037, 33);
        panel.add(panEscolha);
        
        JLabel lblTipo = new JLabel("Tipo:");
        panEscolha.add(lblTipo);
        
        cbObject = new JComboBox();
        cbObject.setModel(new DefaultComboBoxModel(Modelos.values()));
        panEscolha.add(cbObject);
        
        JButton button = new JButton("+");
        button.setActionCommand("+");
        button.addActionListener(controller);
        panEscolha.add(button);
        
        txCodigo = new JLabel("{Cod***}");
        panEscolha.add(txCodigo);
        
        txNome = new JLabel("{Name***}");
        panEscolha.add(txNome);
        
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
        
        btnEditar = new JButton("Editar");
        btnEditar.setActionCommand("Editar");
        btnEditar.addActionListener(controller);
        
        btnNovo = new JButton("Novo");
        btnNovo.setActionCommand("Novo");
        panEscolha.add(btnNovo);
        panEscolha.add(btnEditar);
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setActionCommand("Salvar");
        btnSalvar.addActionListener(controller);
        panEscolha.add(btnSalvar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setActionCommand("Cancelar");
        btnCancelar.addActionListener(controller);
        panEscolha.add(btnCancelar);
        
        JLabel lblNewLabel = new JLabel("quando na\u00F5 for passado parametro de classe  e invocar um jcomponents solitando Negocio, Empresa ou pessoa para salvar");
        lblNewLabel.setBounds(174, 124, 846, 14);
        panel.add(lblNewLabel);

	}
}
