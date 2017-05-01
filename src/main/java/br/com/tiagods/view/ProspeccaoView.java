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
import br.com.tiagods.view.interfaces.DefaultComboBox;

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
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import java.awt.Rectangle;

public class ProspeccaoView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8490414289089315953L;
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
	public static JTable tbPrincipal;
	public static JCheckBox ckConviteEventosCad,ckMaterialCad,ckNewsletterCad,ckConviteEventos,ckMaterial,ckNewsletter;
	public static JTextArea txDetalhesDaOrigem,txResumoContato,txApresentacao;
	public static DefaultComboBox cbTipoContatoCad,cbAtendenteCad;
	public static DefaultComboBox cbListaCad,cbOrigemCad;
	public static DefaultComboBox cbTipoContatoPesquisa,cbOrigem,cbLista,cbAtendente,cbServicos;
	public static JComboBox<String> cbBuscarPor,cbOrdenacao;
	public static JButton btNovaTarefa, btEsconder, btHistorico, btNovo, btEditar, btSalvar, btCancelar, btExcluir, btEmail, btSite;
	public static JRadioButton rbCrescente, rbDecrescente;
	public static JTabbedPane tabbedPane;
	public static JPanel pnPesquisa, pnCadastro,pnCadastroOrigem, pnAuxiliar;
	public static JDateChooser data1, data2;
	public static JButton btOrigemAdd;
	public static JTextField txDataCadastro;
	public static JTextField txCadastradoPor;
	public static JButton btTipoContatoAdd;
	public static JButton btExportarMalaDireta,btnExpMailmktLocaweb;
	public static JTextField txContadorRegistros;
	
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
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 1214, 608);
		panel.add(tabbedPane);
		
		pnPesquisa = new JPanel();
		tabbedPane.addTab("Pesquisa", null, pnPesquisa, null);
		pnPesquisa.setLayout(null);
		
		cbTipoContatoPesquisa = new DefaultComboBox();
		cbTipoContatoPesquisa.setName("TipoContato");
		cbTipoContatoPesquisa.setBounds(10, 11, 110, 20);
		pnPesquisa.add(cbTipoContatoPesquisa);
		
		cbOrigem = new DefaultComboBox();
		cbOrigem.setName("Origem");
		cbOrigem.setBounds(130, 11, 97, 20);
		pnPesquisa.add(cbOrigem);
		
		cbLista = new DefaultComboBox();
		cbLista.setName("Lista");
		cbLista.setBounds(237, 11, 97, 20);
		pnPesquisa.add(cbLista);
		
		cbServicos = new DefaultComboBox();
		cbServicos.setName("Produtos/Servicos");
		cbServicos.setBounds(344, 11, 97, 20);
		pnPesquisa.add(cbServicos);
		
		cbAtendente = new DefaultComboBox();
		cbAtendente.setName("Atendente");
		cbAtendente.setBounds(451, 11, 97, 20);
		pnPesquisa.add(cbAtendente);
		
		ckConviteEventos = new JCheckBox("Convite para Eventos");
		ckConviteEventos.setBounds(554, 11, 142, 23);
		pnPesquisa.add(ckConviteEventos);
		
		ckMaterial = new JCheckBox("Material");
		ckMaterial.setBounds(698, 12, 97, 23);
		pnPesquisa.add(ckMaterial);
		
		ckNewsletter = new JCheckBox("Newsletter");
		ckNewsletter.setBounds(797, 12, 97, 23);
		pnPesquisa.add(ckNewsletter);
		
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
		
		cbBuscarPor = new JComboBox<String>();
		cbBuscarPor.setModel(new DefaultComboBoxModel<String>(new String[] {"ID", "Nome", "Responsavel"}));
		cbBuscarPor.setBounds(218, 57, 103, 20);
		pnPesquisa.add(cbBuscarPor);
		
		JLabel lblClassificarPor = new JLabel("Classificar por:");
		lblClassificarPor.setBounds(366, 60, 110, 14);
		pnPesquisa.add(lblClassificarPor);
		
		cbOrdenacao = new JComboBox<String>();
		cbOrdenacao.setToolTipText("Voc\u00EA consegue organizar com duplo clique no nome da coluna dentro da tabela abaixo, mas campos como DATA E CODIGO n\u00E3o s\u00E3o possiveis de ordenar, por isso dispon\u00EDvel essa fun\u00E7\u00E3o atrav\u00E9s dessa caixa.");
		cbOrdenacao.setModel(new DefaultComboBoxModel<String>(new String[] {"C\u00F3digo", "Data de Cadastro"}));
		cbOrdenacao.setBounds(494, 57, 121, 20);
		pnPesquisa.add(cbOrdenacao);
		
		rbCrescente = new JRadioButton("Crescente");
		rbCrescente.setBounds(623, 56, 109, 23);
		pnPesquisa.add(rbCrescente);
		
		rbDecrescente = new JRadioButton("Decrescente");
		rbDecrescente.setBounds(623, 82, 109, 23);
		pnPesquisa.add(rbDecrescente);
		
		JPanel pnData = new JPanel();
		pnData.setLayout(null);
		pnData.setOpaque(false);
		pnData.setBackground(new Color(250, 250, 250));
		pnData.setBounds(811, 44, 146, 61);
		pnPesquisa.add(pnData);
		
		JLabel lbDataInicio = new JLabel();
		lbDataInicio.setText("de:");
		lbDataInicio.setHorizontalAlignment(SwingConstants.LEFT);
		lbDataInicio.setBounds(10, 11, 22, 20);
		pnData.add(lbDataInicio);
		
		JLabel lbDataFim = new JLabel();
		lbDataFim.setText("at\u00E9");
		lbDataFim.setHorizontalAlignment(SwingConstants.LEFT);
		lbDataFim.setBounds(10, 33, 22, 20);
		pnData.add(lbDataFim);
		
		data1 = new JDateChooser();
		data1.setBounds(36, 33, 100, 20);
		pnData.add(data1);
		
		data2 = new JDateChooser();
		data2.setBounds(36, 11, 100, 20);
		pnData.add(data2);
		
		btExportarMalaDireta = new JButton("Exportar Mala Direta");
		btExportarMalaDireta.setEnabled(false);
		btExportarMalaDireta.setBounds(1049, 56, 150, 23);
		pnPesquisa.add(btExportarMalaDireta);
		
		btnExpMailmktLocaweb = new JButton("Exp. EmailMkt Locaweb");
		btnExpMailmktLocaweb.setEnabled(false);
		btnExpMailmktLocaweb.setBounds(1049, 82, 150, 23);
		pnPesquisa.add(btnExpMailmktLocaweb);
		
		JScrollPane spPesquisa = new JScrollPane();
		spPesquisa.setBounds(10, 116, 947, 453);
		pnPesquisa.add(spPesquisa);
		
		tbPrincipal = new JTable();
		spPesquisa.setViewportView(tbPrincipal);
		
		txContadorRegistros = new JTextField();
		txContadorRegistros.setEnabled(false);
		txContadorRegistros.setBounds(967, 555, 152, 14);
		pnPesquisa.add(txContadorRegistros);
		
		JPanel pnItem = new JPanel();
		tabbedPane.addTab("Cadastro", null, pnItem, null);
		pnItem.setLayout(null);
		
		pnCadastro = new JPanel();
		pnCadastro.setBounds(10, 11, 719, 517);
		pnItem.add(pnCadastro);
		pnCadastro.setLayout(null);
		
		txCodigo = new JTextField();
		txCodigo.setBounds(10, 11, 86, 20);
		pnCadastro.add(txCodigo);
		txCodigo.setEnabled(false);
		txCodigo.setColumns(10);
		
		JLabel label = new JLabel();
		label.setText("Cadastro em:");
		label.setBounds(106, 11, 78, 20);
		pnCadastro.add(label);
		
		txDataCadastro = new JTextField();
		txDataCadastro.setEnabled(false);
		txDataCadastro.setBounds(194, 11, 73, 20);
		pnCadastro.add(txDataCadastro);
		
		txCadastradoPor = new JTextField();
		txCadastradoPor.setEnabled(false);
		txCadastradoPor.setBounds(277, 11, 119, 20);
		pnCadastro.add(txCadastradoPor);
		
		JLabel lblEmpresa = new JLabel("Empresa:");
		lblEmpresa.setBounds(10, 53, 86, 14);
		pnCadastro.add(lblEmpresa);
		
		txEmpresa = new JTextField();
		txEmpresa.setBounds(106, 50, 220, 20);
		pnCadastro.add(txEmpresa);
		txEmpresa.setColumns(10);
		
		JLabel lbAtendenteCad = new JLabel();
		lbAtendenteCad.setText("Atendente:");
		lbAtendenteCad.setBounds(new Rectangle(0, 40, 0, 0));
		lbAtendenteCad.setBounds(333, 50, 78, 20);
		pnCadastro.add(lbAtendenteCad);
		
		cbAtendenteCad = new DefaultComboBox();
		cbAtendenteCad.setName("AtendenteCad");
		cbAtendenteCad.setBounds(new Rectangle(0, 40, 0, 0));
		cbAtendenteCad.setBounds(421, 50, 86, 20);
		pnCadastro.add(cbAtendenteCad);
		
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
		
		btEmail = new JButton();
		btEmail.setToolTipText("Enviar e-mail");
		btEmail.setActionCommand("MailTo");
		btEmail.setBounds(276, 129, 50, 25);
		pnCadastro.add(btEmail);
		
		JLabel lbSite = new JLabel("Site:");
		lbSite.setBounds(333, 137, 86, 14);
		pnCadastro.add(lbSite);
		
		txSite = new JTextField();
		txSite.setBounds(421, 134, 86, 20);
		pnCadastro.add(txSite);
		txSite.setColumns(10);
		
		btSite = new JButton();
		btSite.setToolTipText("Abrir P\u00E1gina");
		btSite.setActionCommand("OpenURL");
		btSite.setBounds(519, 129, 50, 25);
		pnCadastro.add(btSite);
		
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
		
		cbTipoContatoCad = new DefaultComboBox();
		cbTipoContatoCad.setName("TipoContatoCad");
		cbTipoContatoCad.setBounds(421, 165, 86, 20);
		pnCadastro.add(cbTipoContatoCad);
		cbTipoContatoCad.setModel(new DefaultComboBoxModel<String>(new String[] {"Nenhum", "E-Mail", "Mala Direta", "E-Mail e Mala Direta"}));
		
		ckConviteEventosCad = new JCheckBox("Convite para Eventos");
		ckConviteEventosCad.setBounds(333, 192, 140, 23);
		pnCadastro.add(ckConviteEventosCad);
		
		ckMaterialCad = new JCheckBox("Material");
		ckMaterialCad.setBounds(333, 218, 97, 23);
		pnCadastro.add(ckMaterialCad);
		
		ckNewsletterCad = new JCheckBox("Newsletter");
		ckNewsletterCad.setBounds(333, 244, 97, 23);
		pnCadastro.add(ckNewsletterCad);
		
		JTabbedPane tpSubCadastro = new JTabbedPane(JTabbedPane.TOP);
		tpSubCadastro.setBounds(0, 266, 719, 240);
		pnCadastro.add(tpSubCadastro);
		
		pnCadastroOrigem = new JPanel();
		tpSubCadastro.addTab("Origem", null, pnCadastroOrigem, null);
		pnCadastroOrigem.setLayout(null);
		
		JLabel lblOrigem = new JLabel("Origem:");
		lblOrigem.setBounds(10, 81, 46, 14);
		pnCadastroOrigem.add(lblOrigem);
		
		cbOrigemCad = new DefaultComboBox();
		cbOrigemCad.setName("OrigemCad");
		cbOrigemCad.setBounds(175, 78, 130, 20);
		pnCadastroOrigem.add(cbOrigemCad);
		
		JLabel lbDetalhesOrigem = new JLabel("Detalhes da Origem:");
		lbDetalhesOrigem.setBounds(10, 109, 155, 14);
		pnCadastroOrigem.add(lbDetalhesOrigem);
		
		JScrollPane spDetalhesOrigem = new JScrollPane();
		spDetalhesOrigem.setBounds(175, 109, 307, 92);
		pnCadastroOrigem.add(spDetalhesOrigem);
		
		txDetalhesDaOrigem = new JTextArea();
		spDetalhesOrigem.setViewportView(txDetalhesDaOrigem);
		
		JTextPane txtpncomoTeveAcesso = new JTextPane();
		txtpncomoTeveAcesso.setOpaque(false);
		txtpncomoTeveAcesso.setEditable(false);
		txtpncomoTeveAcesso.setText("Como teve acesso ao contato?\r\nLigou para empresa, enviou e-mail, indica\u00E7\u00E3o de algu\u00E9m, assinou newsletter, foi passado pelo marketing etc.");
		txtpncomoTeveAcesso.setBounds(10, 11, 472, 59);
		pnCadastroOrigem.add(txtpncomoTeveAcesso);
		
		btOrigemAdd = new JButton("");
		btOrigemAdd.setName("CriarOrigem");
		btOrigemAdd.setBounds(315, 77, 50, 25);
		pnCadastroOrigem.add(btOrigemAdd);
		
		JPanel pnCadastroResumo = new JPanel();
		tpSubCadastro.addTab("Resumo", null, pnCadastroResumo, null);
		pnCadastroResumo.setLayout(null);
		
		JScrollPane spResumoContato = new JScrollPane();
		spResumoContato.setBounds(175, 70, 307, 131);
		pnCadastroResumo.add(spResumoContato);
		
		txResumoContato = new JTextArea();
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
		
		txApresentacao = new JTextArea();
		spApresentacao.setViewportView(txApresentacao);
		
		JPanel pnLista = new JPanel();
		tpSubCadastro.addTab("Lista", null, pnLista, null);
		pnLista.setLayout(null);
		
		JLabel lblLista = new JLabel("Lista:");
		lblLista.setBounds(10, 15, 86, 14);
		pnLista.add(lblLista);
		
		cbListaCad = new DefaultComboBox();
		cbListaCad.setName("ListaCad");
		cbListaCad.setBounds(98, 12, 86, 20);
		pnLista.add(cbListaCad);
		
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
		
		btTipoContatoAdd = new JButton("");
		btTipoContatoAdd.setActionCommand("TipoContatoAdd");
		btTipoContatoAdd.setName("CriarOrigem");
		btTipoContatoAdd.setBounds(519, 165, 50, 25);
		pnCadastro.add(btTipoContatoAdd);
		
		pnAuxiliar = new JPanel();
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
		
		btEsconder = new JButton("Esconder");
		btEsconder.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btEsconder.setActionCommand("Esconder");
		btEsconder.setBounds(330, 11, 120, 25);
		pnAuxiliar.add(btEsconder);
		
		btHistorico = new JButton();
		btHistorico.setToolTipText("Tarefas");
		btHistorico.setFont(new Font("Dialog", Font.PLAIN, 9));
		btHistorico.setActionCommand("Historico");
		btHistorico.setBounds(137, 532, 90, 25);
		pnItem.add(btHistorico);
		
		btNovo = new JButton();
		btNovo.setToolTipText("Novo");
		btNovo.setName("Novo");
		btNovo.setFont(new Font("Dialog", Font.PLAIN, 9));
		btNovo.setActionCommand("Novo");
		btNovo.setBounds(237, 532, 90, 25);
		pnItem.add(btNovo);
		
		btEditar = new JButton();
		btEditar.setToolTipText("Editar");
		btEditar.setName("Editar");
		btEditar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btEditar.setActionCommand("Editar");
		btEditar.setBounds(337, 532, 90, 25);
		pnItem.add(btEditar);
		
		btSalvar = new JButton();
		btSalvar.setToolTipText("Salvar");
		btSalvar.setName("Salvar");
		btSalvar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btSalvar.setActionCommand("Salvar");
		btSalvar.setBounds(437, 532, 90, 25);
		pnItem.add(btSalvar);
		
		btCancelar = new JButton();
		btCancelar.setToolTipText("Cancelar");
		btCancelar.setName("Cancelar");
		btCancelar.setFont(new Font("Dialog", Font.PLAIN, 9));
		btCancelar.setActionCommand("Cancelar");
		btCancelar.setBounds(537, 532, 90, 25);
		pnItem.add(btCancelar);
		
		btExcluir = new JButton();
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
