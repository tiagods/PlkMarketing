package br.com.tiagods.view;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerNegocioCancelado;
import br.com.tiagods.model.Negocio;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class NegocioPerdaDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbMotivo;
	public static JTextArea txDescricao;
	public static JRadioButton rbPreco, rbPrazo, rbProdServico, rbDesistencia;
	public static JDateChooser txData;
	ControllerNegocioCancelado controller = new ControllerNegocioCancelado();
/**
	 * Create the dialog.
	 */
	private Negocio negocio;
	public Negocio getNegocio(){
		return this.negocio;
	}
	public void setNegocio(Negocio negocio){
		this.negocio=negocio;
	}
	public NegocioPerdaDialog(JFrame frame, boolean modal, Negocio negocio) {
		super(frame, modal);
		initComponents();
		controller.iniciar(negocio,this);
	}
	public void initComponents(){
		setBounds(100, 100, 450, 330);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 148, 414, 89);
		getContentPane().add(scrollPane);
		
		txDescricao = new JTextArea();
		txDescricao.setLineWrap(true);
		scrollPane.setViewportView(txDescricao);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(10, 248, 414, 33);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);
		
		lbMotivo = new JLabel("Qual foi o motivo da perda?");
		lbMotivo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbMotivo.setBounds(10, 11, 414, 20);
		getContentPane().add(lbMotivo);
				
		rbPreco = new JRadioButton("Pre\u00E7o");
		rbPreco.setBorderPainted(true);
		rbPreco.setName("Preco");
		rbPreco.setBounds(6, 38, 70, 23);
		getContentPane().add(rbPreco);
		
		rbPrazo = new JRadioButton("Prazo");
		rbPrazo.setBorderPainted(true);
		rbPrazo.setOpaque(false);
		rbPrazo.setName("Prazo");
		rbPrazo.setBounds(85, 38, 70, 23);
		getContentPane().add(rbPrazo);
		
		rbProdServico = new JRadioButton("Produto/Servi\u00E7o");
		rbProdServico.setBorderPainted(true);
		rbProdServico.setOpaque(false);
		rbProdServico.setName("Produto/Servico");
		rbProdServico.setBounds(168, 38, 137, 23);
		getContentPane().add(rbProdServico);
		
		rbDesistencia = new JRadioButton("Desist\u00EAncia");
		rbDesistencia.setBorderPainted(true);
		rbDesistencia.setOpaque(false);
		rbDesistencia.setName("Desistencia");
		rbDesistencia.setBounds(314, 38, 110, 23);
		getContentPane().add(rbDesistencia);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rbDesistencia);
		group.add(rbProdServico);
		group.add(rbPrazo);
		group.add(rbPreco);
		
		JLabel lblQuandoONegcio = new JLabel("Quando o neg\u00F3cio foi perdido?");
		lblQuandoONegcio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblQuandoONegcio.setBounds(10, 68, 210, 20);
		getContentPane().add(lblQuandoONegcio);
		
		txData = new JDateChooser();
		txData.setBounds(229, 68, 100, 20);
		getContentPane().add(txData);
		
		JButton btDefinir = new JButton("Definir");
		btDefinir.setActionCommand("Definir");
		btDefinir.addActionListener(controller);
		buttonPane.add(btDefinir);
		getRootPane().setDefaultButton(btDefinir);

		JButton btCancel = new JButton("Cancelar");
		btCancel.setActionCommand("Cancelar");
		btCancel.addActionListener(controller);
		buttonPane.add(btCancel);
		
		JLabel lbObservacao = new JLabel("Descreva abaixo uma breve observa\u00E7\u00E3o.");
		lbObservacao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbObservacao.setBounds(10, 117, 414, 20);
		getContentPane().add(lbObservacao);
		
		setLocationRelativeTo(null);
	}
}
