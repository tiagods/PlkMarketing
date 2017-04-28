package br.com.tiagods.view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.tiagods.model.Prospeccao;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;

public class ProspeccaoView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8490414289089315953L;
	public static JTable tbPesquisa;
	public static JTextField txCodigo;
	public static JTextField txEmpresa;
	public static JTextField txNomeContato;
	public static JTextField txTelefone;
	public static JTextField txEmail;
	public static JTextField txCelular;
	public static JTextField txDepartamento;
	public static JTextField txEndereco;
	public static JTextField txSite;
	public static JTextField txBuscar;
	public static JTable tbLista;
	public static JTable tbAuxiliar;

	private JButton btNovaTarefa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProspeccaoView frame = new ProspeccaoView(null);
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
	public ProspeccaoView(Prospeccao prospeccao) {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JTabbedPane tpPrincipal = new JTabbedPane(JTabbedPane.TOP);
		tpPrincipal.setBounds(10, 11, 1214, 608);
		panel.add(tpPrincipal);
		
		JPanel pnPesquisa = new JPanel();
		tpPrincipal.addTab("Pesquisa", null, pnPesquisa, null);
		pnPesquisa.setLayout(null);
		
		JComboBox<String> cbTipoContatoPesquisa = new JComboBox<String>();
		cbTipoContatoPesquisa.setName("Tipo de Contato");
		cbTipoContatoPesquisa.setModel(new DefaultComboBoxModel(new String[] {"Tipo de Contato"}));
		cbTipoContatoPesquisa.setBounds(10, 11, 110, 20);
		pnPesquisa.add(cbTipoContatoPesquisa);
		
		JComboBox<String> cbOrigemPesquisa = new JComboBox<String>();
		cbOrigemPesquisa.setName("Origem");
		cbOrigemPesquisa.setModel(new DefaultComboBoxModel(new String[] {"Origem"}));
		cbOrigemPesquisa.setBounds(130, 11, 97, 20);
		pnPesquisa.add(cbOrigemPesquisa);
		
		JComboBox<String> cbListaPesquisa = new JComboBox<String>();
		cbListaPesquisa.setName("Lista");
		cbListaPesquisa.setModel(new DefaultComboBoxModel(new String[] {"Lista"}));
		cbListaPesquisa.setBounds(237, 11, 97, 20);
		pnPesquisa.add(cbListaPesquisa);
		
		JComboBox<String> cbAtendentePesquisa = new JComboBox<String>();
		cbAtendentePesquisa.setName("Atendente");
		cbAtendentePesquisa.setModel(new DefaultComboBoxModel(new String[] {"Atendente"}));
		cbAtendentePesquisa.setBounds(344, 11, 97, 20);
		pnPesquisa.add(cbAtendentePesquisa);
		
		JCheckBox ckConvitePesquisa = new JCheckBox("Convite para Eventos");
		ckConvitePesquisa.setBounds(447, 10, 142, 23);
		pnPesquisa.add(ckConvitePesquisa);
		
		JCheckBox ckMaterialPesquisa = new JCheckBox("Material");
		ckMaterialPesquisa.setBounds(591, 11, 97, 23);
		pnPesquisa.add(ckMaterialPesquisa);
		
		JCheckBox ckNewsletterPesquisa = new JCheckBox("Newsletter");
		ckNewsletterPesquisa.setBounds(690, 11, 97, 23);
		pnPesquisa.add(ckNewsletterPesquisa);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setBounds(10, 63, 46, 14);
		pnPesquisa.add(lblBuscar);
		
		txBuscar = new JTextField();
		txBuscar.setBounds(66, 57, 86, 20);
		pnPesquisa.add(txBuscar);
		txBuscar.setColumns(10);
		
		JLabel lblPor = new JLabel("por:");
		lblPor.setHorizontalAlignment(SwingConstants.CENTER);
		lblPor.setBounds(162, 63, 46, 14);
		pnPesquisa.add(lblPor);
		
		JComboBox<String> cbPesquisarPor = new JComboBox<String>();
		cbPesquisarPor.setModel(new DefaultComboBoxModel(new String[] {"ID", "Nome", "Responsavel"}));
		cbPesquisarPor.setBounds(218, 57, 103, 20);
		pnPesquisa.add(cbPesquisarPor);
		
		JLabel lblClassificarPor = new JLabel("Classificar por:");
		lblClassificarPor.setBounds(366, 60, 110, 14);
		pnPesquisa.add(lblClassificarPor);
		
		JComboBox<String> cbClassificar = new JComboBox<String>();
		cbClassificar.setToolTipText("Voc\u00EA consegue organizar com duplo clique no nome da coluna dentro da tabela abaixo, mas campos como DATA E CODIGO n\u00E3o s\u00E3o possiveis de ordenar, por isso dispon\u00EDvel essa fun\u00E7\u00E3o atrav\u00E9s dessa caixa.");
		cbClassificar.setModel(new DefaultComboBoxModel(new String[] {"C\u00F3digo", "Data de Cadastro"}));
		cbClassificar.setBounds(494, 57, 121, 20);
		pnPesquisa.add(cbClassificar);
		
		JRadioButton rbCrescente = new JRadioButton("Crescente");
		rbCrescente.setBounds(623, 56, 109, 23);
		pnPesquisa.add(rbCrescente);
		
		JRadioButton rbDecrescente = new JRadioButton("Decrescente");
		rbDecrescente.setBounds(623, 82, 109, 23);
		pnPesquisa.add(rbDecrescente);
		
		JButton btExportarMalaDireta = new JButton("Exportar Mala Direta");
		btExportarMalaDireta.setEnabled(false);
		btExportarMalaDireta.setBounds(807, 56, 150, 23);
		pnPesquisa.add(btExportarMalaDireta);
		
		JButton btnExpMailmktLocaweb = new JButton("Exp. EmailMkt Locaweb");
		btnExpMailmktLocaweb.setEnabled(false);
		btnExpMailmktLocaweb.setBounds(807, 82, 150, 23);
		pnPesquisa.add(btnExpMailmktLocaweb);
		
		JScrollPane spPesquisa = new JScrollPane();
		spPesquisa.setBounds(10, 116, 947, 263);
		pnPesquisa.add(spPesquisa);
		
		tbPesquisa = new JTable();
		spPesquisa.setViewportView(tbPesquisa);
		
		JPanel pnItem = new JPanel();
		tpPrincipal.addTab("Cadastro", null, pnItem, null);
		pnItem.setLayout(null);
		
		JPanel pnCadastro = new JPanel();
		pnCadastro.setBounds(10, 11, 719, 517);
		pnItem.add(pnCadastro);
		pnCadastro.setLayout(null);
		
		txCodigo = new JTextField();
		txCodigo.setBounds(10, 11, 86, 20);
		pnCadastro.add(txCodigo);
		txCodigo.setEnabled(false);
		txCodigo.setColumns(10);
		
		JLabel lblEmpresa = new JLabel("Empresa:");
		lblEmpresa.setBounds(10, 53, 86, 14);
		pnCadastro.add(lblEmpresa);
		
		txEmpresa = new JTextField();
		txEmpresa.setBounds(106, 50, 220, 20);
		pnCadastro.add(txEmpresa);
		txEmpresa.setColumns(10);
		
		JLabel lblResponsavel = new JLabel("Nome Contato:");
		lblResponsavel.setBounds(10, 84, 86, 14);
		pnCadastro.add(lblResponsavel);
		
		txNomeContato = new JTextField();
		txNomeContato.setBounds(106, 78, 86, 20);
		pnCadastro.add(txNomeContato);
		txNomeContato.setColumns(10);
		
		JLabel lblDepartamento = new JLabel("Departamento:");
		lblDepartamento.setBounds(333, 81, 86, 14);
		pnCadastro.add(lblDepartamento);
		
		txDepartamento = new JTextField();
		txDepartamento.setBounds(421, 78, 86, 20);
		pnCadastro.add(txDepartamento);
		txDepartamento.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(10, 109, 86, 14);
		pnCadastro.add(lblTelefone);
		
		txTelefone = new JTextField();
		txTelefone.setBounds(106, 106, 86, 20);
		pnCadastro.add(txTelefone);
		txTelefone.setColumns(10);
		
		JLabel lbCelular = new JLabel("Celular:");
		lbCelular.setBounds(333, 109, 86, 14);
		pnCadastro.add(lbCelular);
		
		txCelular = new JTextField();
		txCelular.setBounds(421, 106, 86, 20);
		pnCadastro.add(txCelular);
		txCelular.setColumns(10);
		
		JLabel lbMail = new JLabel("E-Mail:");
		lbMail.setBounds(10, 137, 86, 14);
		pnCadastro.add(lbMail);
		
		txEmail = new JTextField();
		txEmail.setBounds(106, 134, 150, 20);
		pnCadastro.add(txEmail);
		txEmail.setColumns(10);
		
		JLabel lbSite = new JLabel("Site:");
		lbSite.setBounds(333, 137, 86, 14);
		pnCadastro.add(lbSite);
		
		txSite = new JTextField();
		txSite.setBounds(421, 134, 86, 20);
		pnCadastro.add(txSite);
		txSite.setColumns(10);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(10, 165, 86, 14);
		pnCadastro.add(lblEndereo);
		
		txEndereco = new JTextField();
		txEndereco.setBounds(106, 162, 220, 20);
		pnCadastro.add(txEndereco);
		txEndereco.setColumns(10);
		
		JLabel lblTipoContato = new JLabel("Tipo de Contato:");
		lblTipoContato.setBounds(333, 168, 86, 14);
		pnCadastro.add(lblTipoContato);
		
		JComboBox<String> cbTipoContato = new JComboBox<String>();
		cbTipoContato.setBounds(421, 165, 86, 20);
		pnCadastro.add(cbTipoContato);
		cbTipoContato.setModel(new DefaultComboBoxModel(new String[] {"Nenhum", "E-Mail", "Mala Direta", "E-Mail e Mala Direta"}));
		
		JCheckBox ckConviteEventos = new JCheckBox("Convite para Eventos");
		ckConviteEventos.setBounds(333, 192, 140, 23);
		pnCadastro.add(ckConviteEventos);
		
		JCheckBox ckMaterial = new JCheckBox("Material");
		ckMaterial.setBounds(333, 218, 97, 23);
		pnCadastro.add(ckMaterial);
		
		JCheckBox ckNewsletter = new JCheckBox("Newsletter");
		ckNewsletter.setBounds(333, 244, 97, 23);
		pnCadastro.add(ckNewsletter);
		
		JTabbedPane tpSubCadastro = new JTabbedPane(JTabbedPane.TOP);
		tpSubCadastro.setBounds(0, 266, 719, 240);
		pnCadastro.add(tpSubCadastro);
		
		JPanel pnCadastroOrigem = new JPanel();
		tpSubCadastro.addTab("Origem", null, pnCadastroOrigem, null);
		pnCadastroOrigem.setLayout(null);
		
		JLabel lblOrigem = new JLabel("Origem:");
		lblOrigem.setBounds(10, 81, 46, 14);
		pnCadastroOrigem.add(lblOrigem);
		
		JComboBox cbOrigem = new JComboBox();
		cbOrigem.setBounds(175, 78, 130, 20);
		pnCadastroOrigem.add(cbOrigem);
		
		JLabel lbDetalhesOrigem = new JLabel("Detalhes da Origem:");
		lbDetalhesOrigem.setBounds(10, 109, 155, 14);
		pnCadastroOrigem.add(lbDetalhesOrigem);
		
		JScrollPane spDetalhesOrigem = new JScrollPane();
		spDetalhesOrigem.setBounds(175, 109, 307, 92);
		pnCadastroOrigem.add(spDetalhesOrigem);
		
		JTextArea txDetalhesDaOrigem = new JTextArea();
		spDetalhesOrigem.setViewportView(txDetalhesDaOrigem);
		
		JTextPane txtpncomoTeveAcesso = new JTextPane();
		txtpncomoTeveAcesso.setOpaque(false);
		txtpncomoTeveAcesso.setEditable(false);
		txtpncomoTeveAcesso.setText("Como teve acesso ao contato?\r\nLigou para empresa, enviou e-mail, indica\u00E7\u00E3o de algu\u00E9m, assinou newsletter, foi passado pelo marketing etc.");
		txtpncomoTeveAcesso.setBounds(10, 11, 472, 59);
		pnCadastroOrigem.add(txtpncomoTeveAcesso);
		
		JPanel pnCadastroResumo = new JPanel();
		tpSubCadastro.addTab("Resumo", null, pnCadastroResumo, null);
		pnCadastroResumo.setLayout(null);
		
		JScrollPane spResumoContato = new JScrollPane();
		spResumoContato.setBounds(175, 70, 307, 131);
		pnCadastroResumo.add(spResumoContato);
		
		JTextArea txResumoContato = new JTextArea();
		spResumoContato.setViewportView(txResumoContato);
		
		JLabel lblResumoDoContato = new JLabel("Resumo do Contato:");
		lblResumoDoContato.setBounds(10, 70, 155, 14);
		pnCadastroResumo.add(lblResumoDoContato);
		
		JTextPane txtpnTenteIrPara = new JTextPane();
		txtpnTenteIrPara.setText("Tente ir para a reuni\u00E3o j\u00E1 sabendo o que espera por voc\u00EA, o que o cliente deseja e anote aqui um resumo de sua impress\u00E3o.");
		txtpnTenteIrPara.setOpaque(false);
		txtpnTenteIrPara.setEditable(false);
		txtpnTenteIrPara.setBounds(10, 11, 472, 48);
		pnCadastroResumo.add(txtpnTenteIrPara);
		
		JPanel pnCadastroApresentacao = new JPanel();
		tpSubCadastro.addTab("Apresenta\u00E7\u00E3o sob Medida", null, pnCadastroApresentacao, null);
		pnCadastroApresentacao.setLayout(null);
		
		JTextPane txtpnTentePrepararUm = new JTextPane();
		txtpnTentePrepararUm.setText("Tente preparar um material que se encaixe perfeitamente com as necessidades do cliente, resuma aqui os pontos principais de sua argumenta\u00E7\u00E3o.");
		txtpnTentePrepararUm.setOpaque(false);
		txtpnTentePrepararUm.setEditable(false);
		txtpnTentePrepararUm.setBounds(10, 11, 472, 48);
		pnCadastroApresentacao.add(txtpnTentePrepararUm);
		
		JLabel lblApresentao = new JLabel("Apresenta\u00E7\u00E3o:");
		lblApresentao.setBounds(10, 70, 155, 14);
		pnCadastroApresentacao.add(lblApresentao);
		
		JScrollPane spApresentacao = new JScrollPane();
		spApresentacao.setBounds(175, 70, 307, 131);
		pnCadastroApresentacao.add(spApresentacao);
		
		JTextArea txApresentacao = new JTextArea();
		spApresentacao.setViewportView(txApresentacao);
		
		JPanel pnLista = new JPanel();
		tpSubCadastro.addTab("Lista", null, pnLista, null);
		pnLista.setLayout(null);
		
		JLabel lblLista = new JLabel("Lista:");
		lblLista.setBounds(10, 15, 86, 14);
		pnLista.add(lblLista);
		
		JComboBox cbLista = new JComboBox();
		cbLista.setBounds(98, 12, 86, 20);
		pnLista.add(cbLista);
		
		JButton btListaAdd = new JButton("+");
		btListaAdd.setBounds(194, 11, 35, 23);
		pnLista.add(btListaAdd);
		
		JLabel lbApresentacao = new JLabel("Apresenta\u00E7\u00E3o:");
		lbApresentacao.setBounds(10, 60, 86, 14);
		pnLista.add(lbApresentacao);
		
		JScrollPane spLista = new JScrollPane();
		spLista.setBounds(98, 60, 384, 141);
		pnLista.add(spLista);
		
		tbLista = new JTable();
		spLista.setViewportView(tbLista);
		
		JButton btAdicionarALista = new JButton("+");
		btAdicionarALista.setBounds(447, 11, 35, 23);
		pnLista.add(btAdicionarALista);
		
		JPanel pnAuxiliar = new JPanel();
		pnAuxiliar.setBounds(739, 11, 460, 363);
		pnItem.add(pnAuxiliar);
		pnAuxiliar.setLayout(null);
		
		JScrollPane spAuxiliar = new JScrollPane();
		spAuxiliar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spAuxiliar.setBounds(0, 55, 450, 308);
		pnAuxiliar.add(spAuxiliar);
		
		tbAuxiliar = new JTable();
		spAuxiliar.setViewportView(tbAuxiliar);
		
		btNovaTarefa = new JButton();
		btNovaTarefa.setText("Nova Tarefa");
		btNovaTarefa.setName("Nova Tarefa");
		btNovaTarefa.setFont(new Font("Dialog", Font.PLAIN, 10));
		btNovaTarefa.setActionCommand("Nova Tarefa");
		btNovaTarefa.setBounds(10, 11, 130, 25);
		pnAuxiliar.add(btNovaTarefa);
		
		JButton btEsconder = new JButton("Esconder");
		btEsconder.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btEsconder.setActionCommand("Esconder");
		btEsconder.setBounds(330, 11, 120, 25);
		pnAuxiliar.add(btEsconder);
		
		JButton btHistorico = new JButton();
		btHistorico.setToolTipText("Tarefas");
		btHistorico.setFont(new Font("Dialog", Font.PLAIN, 9));
		btHistorico.setActionCommand("Historico");
		btHistorico.setBounds(137, 532, 90, 25);
		pnItem.add(btHistorico);
		
		JButton btNovo = new JButton();
		btNovo.setToolTipText("Novo");
		btNovo.setName("Novo");
		btNovo.setFont(new Font("Dialog", Font.PLAIN, 9));
		btNovo.setActionCommand("Novo");
		btNovo.setBounds(237, 532, 90, 25);
		pnItem.add(btNovo);
		
		JButton btEditar = new JButton();
		btEditar.setToolTipText("Editar");
		btEditar.setName("Editar");
		btEditar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btEditar.setActionCommand("Editar");
		btEditar.setBounds(337, 532, 90, 25);
		pnItem.add(btEditar);
		
		JButton btSalvar = new JButton();
		btSalvar.setToolTipText("Salvar");
		btSalvar.setName("Salvar");
		btSalvar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btSalvar.setActionCommand("Salvar");
		btSalvar.setBounds(437, 532, 90, 25);
		pnItem.add(btSalvar);
		
		JButton btCancelar = new JButton();
		btCancelar.setToolTipText("Cancelar");
		btCancelar.setName("Cancelar");
		btCancelar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btCancelar.setActionCommand("Cancelar");
		btCancelar.setBounds(537, 532, 90, 25);
		pnItem.add(btCancelar);
		
		JButton btExcluir = new JButton();
		btExcluir.setToolTipText("Excluir");
		btExcluir.setName("Excluir");
		btExcluir.setFont(new Font("Dialog", Font.PLAIN, 9));
		btExcluir.setActionCommand("Excluir");
		btExcluir.setBounds(637, 532, 90, 25);
		pnItem.add(btExcluir);
		pack();
		setBounds(0, 0, 1250, 660);
		
	}
}
