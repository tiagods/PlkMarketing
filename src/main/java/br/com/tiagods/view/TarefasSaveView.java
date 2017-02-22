package br.com.tiagods.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;
/*
 */

import br.com.tiagods.controller.ControllerTarefasSave;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.view.interfaces.DefaultComboBox;
import br.com.tiagods.view.interfaces.DefaultEnumModel.Modelos;
import br.com.tiagods.view.interfaces.DefaultUtilities;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;

public class TarefasSaveView extends JDialog implements DefaultUtilities {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JPanel pnItem,pnRelacionamento,pnDetalhes, pnBotoes;
	private JPanel panel;
	public static JDateChooser txData;
	@SuppressWarnings("rawtypes")
	public static JComboBox cbObject; 
	public static DefaultComboBox cbAtendente;
	public static JTextArea txDetalhes;
	public static JLabel txCodigoObjeto, txNomeObjeto, txQuantidade ;
	public static JFormattedTextField txHora;
	public static JButton btnNovo, btnEditar, btnSalvar, btnCancelar, btnAssociacao;
	public static JRadioButton rdbtnReuniao, rdbtnProposta, rdbtnEmail,rdbtnVisita, rdbtnTelefone; 
	public static JCheckBox ckFinalizado;
	ControllerTarefasSave controller  = new ControllerTarefasSave();
	@Override	public Color getColor() {
		// TODO Auto-generated method stub
		return DefaultUtilities.super.getColor();
	}
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public TarefasSaveView(Tarefa tarefa,Object object, JFrame frame, boolean modal) {
		super(frame,modal);
		initComponents();
		controller.iniciar(this,tarefa, object);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents(){
		panel = new JPanel();
        
		setBounds(0, 0, 600, 450);
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setBackground(getColor());
        panel.setLayout(null);
        
        txData = new JDateChooser();
        txData.setOpaque(false);
        txData.setPreferredSize(new Dimension(100, 20));
        
        JTextArea txtrDescrevaUmBreve = new JTextArea();
        txtrDescrevaUmBreve.setOpaque(false);
        txtrDescrevaUmBreve.setEditable(false);
        txtrDescrevaUmBreve.setLineWrap(true);
        txtrDescrevaUmBreve.setWrapStyleWord(true);
        txtrDescrevaUmBreve.setTabSize(10);
        txtrDescrevaUmBreve.setRows(3);
        txtrDescrevaUmBreve.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtrDescrevaUmBreve.setText("Descreva um breve resumo da tarefa com data e hora que voc\u00EA fez ou ir\u00E1 realizar para um Negocio, ou apenas um contato e o sistema enviar\u00E1 um alerta para o seu e-mail.");
        txtrDescrevaUmBreve.setBounds(10, 11, 560, 46);
        panel.add(txtrDescrevaUmBreve);
        
        pnItem = new JPanel();
        pnItem.setBounds(10, 68, 560, 33);
        pnItem.setBackground(getColor());
        panel.add(pnItem);
        
        ButtonGroup group = new ButtonGroup();
        
        rdbtnEmail = new JRadioButton("E-mail");
        rdbtnEmail.setOpaque(false);
        rdbtnEmail.setActionCommand("Email");
        rdbtnEmail.addActionListener(controller);
        group.add(rdbtnEmail);
        pnItem.add(rdbtnEmail);
        
        rdbtnProposta = new JRadioButton("Proposta");
        rdbtnProposta.setOpaque(false);
        rdbtnProposta.setActionCommand("Proposta");
        rdbtnProposta.addActionListener(controller);
        group.add(rdbtnProposta);
        pnItem.add(rdbtnProposta);
        
        rdbtnReuniao = new JRadioButton("Reuni\u00E3o");
        rdbtnReuniao.setOpaque(false);
        rdbtnReuniao.setActionCommand("Reuniao");
        rdbtnReuniao.addActionListener(controller);
        group.add(rdbtnReuniao);
        pnItem.add(rdbtnReuniao);
        
        rdbtnTelefone = new JRadioButton("Telefone");
        rdbtnTelefone.setOpaque(false);
        rdbtnTelefone.setActionCommand("Telefone");
        rdbtnTelefone.addActionListener(controller);
        group.add(rdbtnTelefone);
        pnItem.add(rdbtnTelefone);
        
        rdbtnVisita = new JRadioButton("Visita");
        rdbtnVisita.setOpaque(false);
        rdbtnVisita.setActionCommand("Visita");
        rdbtnVisita.addActionListener(controller);
        group.add(rdbtnVisita);
        pnItem.add(rdbtnVisita);
        
        txQuantidade = new JLabel("");
        txQuantidade.setFont(new Font("Tahoma", Font.BOLD, 11));
        txQuantidade.setForeground(Color.BLUE);
        txQuantidade.setHorizontalAlignment(SwingConstants.RIGHT);
        txQuantidade.setBounds(449, 231, 121, 14);
        panel.add(txQuantidade);
        
        pnDetalhes = new JPanel();
        pnDetalhes.setBounds(10, 112, 560, 108);
        panel.add(pnDetalhes);
        pnDetalhes.setLayout(new GridLayout(0, 1, 0, 0));
        
        txDetalhes = new JTextArea();
        txDetalhes.setLineWrap(true);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(txDetalhes);
        pnDetalhes.add(scrollPane);
        
        pnRelacionamento = new JPanel();
        FlowLayout flowLayout = (FlowLayout) pnRelacionamento.getLayout();
        flowLayout.setAlignment(FlowLayout.LEADING);
        pnRelacionamento.setOpaque(false);
        pnRelacionamento.setBounds(10, 263, 560, 41);
        panel.add(pnRelacionamento);
        
        JLabel lblTipo = new JLabel("Tipo:");
        pnRelacionamento.add(lblTipo);
        
        cbObject = new JComboBox();
        pnRelacionamento.add(cbObject);
        cbObject.setOpaque(false);
        cbObject.setModel(new DefaultComboBoxModel(Modelos.values()));
        
        btnAssociacao = new JButton("");
        btnAssociacao.setToolTipText("Selecionar um cadastro");
        pnRelacionamento.add(btnAssociacao);
        btnAssociacao.setOpaque(false);
        btnAssociacao.setActionCommand("ChamarDialog");
        
        txCodigoObjeto = new JLabel("");
        txCodigoObjeto.setBackground(Color.WHITE);
        txCodigoObjeto.setOpaque(true);
        txCodigoObjeto.setForeground(Color.BLUE);
        txCodigoObjeto.setFont(new Font("Tahoma", Font.BOLD, 11));
        pnRelacionamento.add(txCodigoObjeto);
        
        txNomeObjeto = new JLabel("");
        txNomeObjeto.setBackground(Color.WHITE);
        txNomeObjeto.setOpaque(true);
        txNomeObjeto.setForeground(Color.BLUE);
        txNomeObjeto.setFont(new Font("Tahoma", Font.BOLD, 11));
        pnRelacionamento.add(txNomeObjeto);
        btnAssociacao.addActionListener(controller);
        cbObject.addItemListener(controller);
        
        JPanel panEscolha = new JPanel();
        FlowLayout fl_panEscolha = (FlowLayout) panEscolha.getLayout();
        fl_panEscolha.setAlignment(FlowLayout.LEADING);
        panEscolha.setBounds(10, 315, 560, 33);
        panEscolha.setBackground(getColor());
        panel.add(panEscolha);
        
        JLabel lblResponsavel = new JLabel("Responsavel:");
        panEscolha.add(lblResponsavel);
        
        cbAtendente = new DefaultComboBox();
        panEscolha.add(cbAtendente);
        cbAtendente.setOpaque(false);
        cbAtendente.setPreferredSize(new Dimension(100,20));
        
        JLabel lblData = new JLabel("Data:");
        panEscolha.add(lblData);
        
        panEscolha.add(txData);
        
        JLabel lblHora = new JLabel("Hora:");
        panEscolha.add(lblHora);
        
        MaskFormatter hora=null;
        try{
        	hora = new MaskFormatter("##:##");
        }catch(Exception e){
        	e.printStackTrace();
        }
        txHora = new JFormattedTextField(hora);
        txHora.setOpaque(false);
        txHora.setColumns(5);
        panEscolha.add(txHora);
        
        ckFinalizado = new JCheckBox("J\u00E1 foi realizado?");
        ckFinalizado.setOpaque(false);
        panEscolha.add(ckFinalizado);
        
        pnBotoes = new JPanel();
        pnBotoes.setOpaque(false);
        pnBotoes.setBounds(10, 359, 560, 42);
        panel.add(pnBotoes);
        
        btnNovo = new JButton("");
        btnNovo.setToolTipText("Novo");
        btnNovo.setOpaque(false);
        pnBotoes.add(btnNovo);
        btnNovo.setActionCommand("Novo");
        
        btnEditar = new JButton("");
        btnEditar.setToolTipText("Editar");
        btnEditar.setOpaque(false);
        pnBotoes.add(btnEditar);
        btnEditar.setActionCommand("Editar");
        
        btnSalvar = new JButton("");
        btnSalvar.setToolTipText("Salvar");
        btnSalvar.setOpaque(false);
        pnBotoes.add(btnSalvar);
        btnSalvar.setActionCommand("Salvar");
        
        btnCancelar = new JButton("");
        btnCancelar.setToolTipText("Cancelar");
        btnCancelar.setOpaque(false);
        btnCancelar.setActionCommand("Cancelar");
        pnBotoes.add(btnCancelar);
        
        btnCancelar.addActionListener(controller);
        btnSalvar.addActionListener(controller);
        btnEditar.addActionListener(controller);
        btnNovo.addActionListener(controller);
        
        setLocationRelativeTo(null);
	}
}
