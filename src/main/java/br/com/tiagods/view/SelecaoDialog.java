package br.com.tiagods.view;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import br.com.tiagods.controller.ControllerSeletor;
import br.com.tiagods.view.interfaces.DefaultEnumModel;
import javax.swing.JComboBox;
import java.awt.Font;

public class SelecaoDialog extends JDialog implements DefaultEnumModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -256153764561770404L;
	@Override
	public Object getObject(String valor) {
		return DefaultEnumModel.super.getObject(valor);
	}
	private final JPanel contentPanel = new JPanel();
	public static JPanel pnCadastrar;
	public static JTable tbRelacao;
	private JLabel lbBuscar;
	private JScrollPane scrollPane;
	public static JTextField txBuscar;
	public static JTextField txNome;
	public static JLabel txCodigo;
	public static JButton btnNovo,btnEditar,btnSalvar,btnExcluir,btnCancelar,btOkDialog,btCancelDialog,btnImportarCadastro;
	public static JComboBox<String> comboFiltro;
	ControllerSeletor controller = new ControllerSeletor();
/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public SelecaoDialog(Object object, JTextField labelId, JTextField labelNome,JComboBox[] combobox, JComboBox[] comboNegocios, JFrame parent,boolean modal ) {
		super(parent,modal);
		initComponents();
		controller.iniciar(labelId,labelNome,this,combobox,comboNegocios);
		controller.processarObjeto(object,"","");
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initComponents(){
		setBounds(100, 100, 600, 400);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 102, 574, 204);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			scrollPane = new JScrollPane();
			{
				tbRelacao = new JTable();
				tbRelacao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tbRelacao.addMouseListener(controller);
				tbRelacao.setFillsViewportHeight(true);
				tbRelacao.setSelectionBackground(Color.ORANGE);
				scrollPane.setViewportView(tbRelacao);
			}
		}
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 567, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
		);
		contentPanel.setLayout(gl_contentPanel);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 309, 574, 41);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);
		
		btOkDialog = new JButton("");
		btOkDialog.setToolTipText("OK");
		btOkDialog.setActionCommand("OK");
		btOkDialog.addActionListener(controller);
		buttonPane.add(btOkDialog);
		getRootPane().setDefaultButton(btOkDialog);

		btCancelDialog = new JButton("");
		btCancelDialog.setToolTipText("Cancelar e Sair");
		btCancelDialog.setActionCommand("Desistir");
		btCancelDialog.addActionListener(controller);
		buttonPane.add(btCancelDialog);

		lbBuscar = new JLabel("Buscar:");
		lbBuscar.setBounds(10, 11, 48, 20);
		getContentPane().add(lbBuscar);

		txBuscar = new JTextField();
		txBuscar.setBounds(68, 11, 86, 25);
		getContentPane().add(txBuscar);
		txBuscar.addKeyListener(controller);
		
		comboFiltro = new JComboBox();
		comboFiltro.setBounds(164, 11, 96, 25);
		getContentPane().add(comboFiltro);
		
		
		pnCadastrar = new JPanel();
		pnCadastrar.setBounds(10, 42, 564, 33);
		getContentPane().add(pnCadastrar);
		pnCadastrar.setLayout(null);
		
		txCodigo = new JLabel("");
		txCodigo.setFont(new Font("Tahoma", Font.BOLD, 11));
		txCodigo.setBounds(0, 5, 21, 14);
		pnCadastrar.add(txCodigo);
		
		txNome = new JTextField();
		txNome.setBounds(26, 2, 129, 25);
		pnCadastrar.add(txNome);
		txNome.setColumns(10);
		
		btnNovo = new JButton("");
		btnNovo.setToolTipText("Novo");
		btnNovo.setActionCommand("Novo");
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNovo.setBounds(270, 11, 75, 25);
		btnNovo.addActionListener(controller);
		getContentPane().add(btnNovo);
		
		btnSalvar = new JButton("");
		btnSalvar.setToolTipText("Salvar");
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSalvar.setBounds(174, 1, 75, 25);
		btnSalvar.setActionCommand("Salvar");
		btnSalvar.addActionListener(controller);
		pnCadastrar.add(btnSalvar);
		
		btnCancelar = new JButton("");
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnCancelar.setBounds(260, 2, 75, 25);
		btnCancelar.setActionCommand("Cancelar");
		btnCancelar.addActionListener(controller);
		pnCadastrar.add(btnCancelar);
		
		btnExcluir = new JButton("");
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnExcluir.setBounds(342, 1, 75, 25);
		btnExcluir.setActionCommand("Excluir");
		btnExcluir.addActionListener(controller);
		pnCadastrar.add(btnExcluir);
		
		btnImportarCadastro = new JButton("Importar Cadastro");
		btnImportarCadastro.setToolTipText("Aguarde...em breve ser\u00E1 possivel importar seus contatos atraves da base de clientes Prolink (Cadastro). Previs\u00E3o de entrega em 05/01/2017.");
		btnImportarCadastro.setEnabled(false);
		btnImportarCadastro.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnImportarCadastro.setActionCommand("");
		btnImportarCadastro.setBounds(436, 11, 138, 25);
		getContentPane().add(btnImportarCadastro);
		
		btnEditar = new JButton("");
		btnEditar.setBounds(351, 11, 75, 25);
		getContentPane().add(btnEditar);
		btnEditar.setToolTipText("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEditar.setActionCommand("Editar");
		btnEditar.addActionListener(controller);
		setLocationRelativeTo(null);
	}
}
