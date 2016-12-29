package br.com.tiagods.view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.controller.ControllerNegocioCancelado;
import br.com.tiagods.model.Negocio;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class PerdaNegocio extends JDialog{
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
	public PerdaNegocio(Negocio negocio) {
		initComponents();
		controller.iniciar(this);
	}
	public void initComponents(){
		setBounds(100, 100, 450, 280);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 414, 97);
		getContentPane().add(scrollPane);
		
		txDescricao = new JTextArea();
		txDescricao.setLineWrap(true);
		scrollPane.setViewportView(txDescricao);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(10, 208, 414, 33);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);
		
		lbMotivo = new JLabel("Qual foi o motivo da perda?");
		lbMotivo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbMotivo.setBounds(10, 11, 414, 20);
		getContentPane().add(lbMotivo);
				
		rbPreco = new JRadioButton("Preço");
		rbPreco.setBounds(6, 38, 90, 23);
		getContentPane().add(rbPreco);
		
		rbPrazo = new JRadioButton("Prazo");
		rbPrazo.setBounds(104, 38, 90, 23);
		getContentPane().add(rbPrazo);
		
		rbProdServico = new JRadioButton("Produto/Serviço");
		rbProdServico.setBounds(207, 38, 109, 23);
		getContentPane().add(rbProdServico);
		
		rbDesistencia = new JRadioButton("Desistência");
		rbDesistencia.setBounds(334, 38, 90, 23);
		getContentPane().add(rbDesistencia);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rbDesistencia);
		group.add(rbProdServico);
		group.add(rbPrazo);
		group.add(rbPreco);
		
		JLabel lblQuandoONegcio = new JLabel("Quando o negócio foi perdido?");
		lblQuandoONegcio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblQuandoONegcio.setBounds(10, 68, 210, 20);
		getContentPane().add(lblQuandoONegcio);
		
		txData = new JDateChooser();
		txData.setBounds(229, 68, 87, 20);
		getContentPane().add(txData);
		
		JButton btDefinir = new JButton("Definir");
		btDefinir.setActionCommand("Definir");
		btDefinir.addActionListener(controller);
		buttonPane.add(btDefinir);
		getRootPane().setDefaultButton(btDefinir);

		JButton btCancel = new JButton("Cancelar");
		btCancel.setActionCommand("Desistir");
		btCancel.addActionListener(controller);
		buttonPane.add(btCancel);
		
		setLocationRelativeTo(null);
	}
}
