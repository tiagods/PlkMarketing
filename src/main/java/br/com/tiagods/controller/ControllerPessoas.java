/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.MenuView.jDBody;
import static br.com.tiagods.view.PessoasView.btEsconder;
import static br.com.tiagods.view.PessoasView.btnCancelar;
import static br.com.tiagods.view.PessoasView.btnCategoriaCad;
import static br.com.tiagods.view.PessoasView.btnEditar;
import static br.com.tiagods.view.PessoasView.btnEmail;
import static br.com.tiagods.view.PessoasView.btnEmpresas;
import static br.com.tiagods.view.PessoasView.btnExcluir;
import static br.com.tiagods.view.PessoasView.btnExportar;
import static br.com.tiagods.view.PessoasView.btnHistorico;
import static br.com.tiagods.view.PessoasView.btnImportar;
import static br.com.tiagods.view.PessoasView.btnLink;
import static br.com.tiagods.view.PessoasView.btnLote;
import static br.com.tiagods.view.PessoasView.btnNegocios;
import static br.com.tiagods.view.PessoasView.btnNivelCad;
import static br.com.tiagods.view.PessoasView.btnNovaTarefa;
import static br.com.tiagods.view.PessoasView.btnNovo;
import static br.com.tiagods.view.PessoasView.btnOrigemCad;
import static br.com.tiagods.view.PessoasView.btnSalvar;
import static br.com.tiagods.view.PessoasView.btnServicosCad;
import static br.com.tiagods.view.PessoasView.cbAtendente;
import static br.com.tiagods.view.PessoasView.cbAtendenteCad;
import static br.com.tiagods.view.PessoasView.cbCategoria;
import static br.com.tiagods.view.PessoasView.cbCategoriaCad;
import static br.com.tiagods.view.PessoasView.cbCidade;
import static br.com.tiagods.view.PessoasView.cbEmpresa;
import static br.com.tiagods.view.PessoasView.cbEstado;
import static br.com.tiagods.view.PessoasView.cbLogradouro;
import static br.com.tiagods.view.PessoasView.cbNivel;
import static br.com.tiagods.view.PessoasView.cbNivelCad;
import static br.com.tiagods.view.PessoasView.cbOrigem;
import static br.com.tiagods.view.PessoasView.cbOrigemCad;
import static br.com.tiagods.view.PessoasView.cbProdServicos;
import static br.com.tiagods.view.PessoasView.cbProdServicosCad;
import static br.com.tiagods.view.PessoasView.data1;
import static br.com.tiagods.view.PessoasView.data2;
import static br.com.tiagods.view.PessoasView.pnAuxiliar;
import static br.com.tiagods.view.PessoasView.pnCabecalho;
import static br.com.tiagods.view.PessoasView.pnPrincipal;
import static br.com.tiagods.view.PessoasView.tbAuxiliar;
import static br.com.tiagods.view.PessoasView.tbPrincipal;
import static br.com.tiagods.view.PessoasView.txApelido;
import static br.com.tiagods.view.PessoasView.txBairro;
import static br.com.tiagods.view.PessoasView.txBuscar;
import static br.com.tiagods.view.PessoasView.txCadastradoPor;
import static br.com.tiagods.view.PessoasView.txCelular;
import static br.com.tiagods.view.PessoasView.txCep;
import static br.com.tiagods.view.PessoasView.txCodigo;
import static br.com.tiagods.view.PessoasView.txComplemento;
import static br.com.tiagods.view.PessoasView.txContador;
import static br.com.tiagods.view.PessoasView.txCpf;
import static br.com.tiagods.view.PessoasView.txDataCadastro;
import static br.com.tiagods.view.PessoasView.txDataNascimento;
import static br.com.tiagods.view.PessoasView.txEmail;
import static br.com.tiagods.view.PessoasView.txLogradouro;
import static br.com.tiagods.view.PessoasView.txNome;
import static br.com.tiagods.view.PessoasView.txNum;
import static br.com.tiagods.view.PessoasView.txSite;
import static br.com.tiagods.view.PessoasView.txTelefone;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.ServicoContratado;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.NegociosView;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.dialog.SelecaoDialog;
import br.com.tiagods.view.interfaces.ButtonColumnModel;
import br.com.tiagods.view.interfaces.DefaultEnumModel;
import br.com.tiagods.view.interfaces.ExcelGenerico;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;
import jxl.write.WriteException;

/**
 *
 * @author Tiago
 */
public class ControllerPessoas
		implements ActionListener, KeyListener, ItemListener, MouseListener, PropertyChangeListener {
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();

	List<Pessoa> listaPessoas;
	Session session = null;
	Pessoa pessoa = null;
	Pessoa pessoaBackup;
	boolean telaEmEdicao = false;
	GenericDao dao = new GenericDao();

	@SuppressWarnings("unchecked")
	public void iniciar(Pessoa pessoa) {
		cbEmpresa.setEnabled(false);
		cbEmpresa.setToolTipText("Filtro não criado: Aguardando programação");
		this.pessoa = pessoa;
		session = HibernateFactory.getSession();
		session.beginTransaction();
		JPanel[] panels = { pnCabecalho, pnPrincipal };
		for (JPanel panel : panels) {
			preencherComboBox(panel);
		}
		List<Criterion> criterion = new ArrayList<>();
		Order order = Order.desc("id");
		listaPessoas = (List<Pessoa>) (new GenericDao().items(Pessoa.class, session, criterion, order));
		preencherTabela(listaPessoas, tbPrincipal, txContador);
		if (!listaPessoas.isEmpty() && this.pessoa == null) {
			this.pessoa = listaPessoas.get(0);
			preencherFormulario(this.pessoa);
		} else if (this.pessoa != null) {
			preencherFormulario(this.pessoa);
		}
		salvarCancelar();
		desbloquerFormulario(false, pnPrincipal);
		session.close();
		setarIcones();

		definirAcoes();

		LoadingView loading = LoadingView.getInstance();
		loading.fechar();
	}

	private void definirAcoes() {
		data1.addPropertyChangeListener(this);
		data2.addPropertyChangeListener(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox[] combos = null;
		switch (e.getActionCommand()) {
		case "Buscar":
			if (txBuscar.getText().trim().length() >= 3) {
				pesquisar();
			}
			break;
		case "Novo":
			limparFormulario(pnPrincipal);
			telaEmEdicao = true;
			pessoa = null;
			novoEditar();
			break;
		case "Editar":
			telaEmEdicao = true;
			novoEditar();
			break;
		case "Cancelar":
			salvarCancelar();
			telaEmEdicao = false;
			break;
		case "Excluir":
			invocarExclusao();
			telaEmEdicao = false;
			break;
		case "Salvar":
			invocarSalvamento();
			telaEmEdicao = false;
			desbloquerFormulario(false, pnPrincipal);
			break;
		case "Historico":
			pnAuxiliar.setVisible(true);
			boolean open = recebeSessao();
			preencherTarefas(pessoa);
			fechaSessao(open);
			break;
		case "Empresas":
			pnAuxiliar.setVisible(true);
			// new AuxiliarTabela(new Pessoa(),tbAuxiliar, pessoas);
			break;
		case "Negocios":
			pnAuxiliar.setVisible(true);
			open = recebeSessao();
			List<Criterion> criterios = new ArrayList<>();
			Criterion criterion = Restrictions.eq("pessoa", pessoa);
			criterios.add(criterion);
			Order order = Order.desc("id");
			List<Negocio> negocios = (List<Negocio>) dao.items(Negocio.class, session, criterios, order);
			new AuxiliarTabela(new Negocio(), tbAuxiliar, negocios, criterios, order, null);
			fechaSessao(open);
			break;
		case "Esconder":
			pnAuxiliar.setVisible(false);
			break;
		case "CriarCategoria":
			combos = new JComboBox[] { cbCategoria, cbCategoriaCad };
			SelecaoDialog dialog = new SelecaoDialog(new Categoria(), null, null, combos, null, MenuView.getInstance(),
					true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarOrigem":
			combos = new JComboBox[] { cbOrigem, cbOrigemCad };
			dialog = new SelecaoDialog(new Origem(), null, null, combos, null, MenuView.getInstance(), true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarServico":
			combos = new JComboBox[] { cbProdServicos, cbProdServicosCad };
			dialog = new SelecaoDialog(new Servico(), null, null, combos, null, MenuView.getInstance(), true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarNivel":
			combos = new JComboBox[] { cbNivel, cbNivelCad };
			dialog = new SelecaoDialog(new Nivel(), null, null, combos, null, MenuView.getInstance(), true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "Nova Tarefa":
			if ("".equals(txCodigo.getText())) {
				String valor;
				if (telaEmEdicao) {
					valor = "Salve o registro atual e depois clique no Botao Nova Tarefa para criar uma nova tarefa para essa Pessoa!\nOu selecione um registro da tabela!";
				} else
					valor = "Selecione um registro da tabela ou crie uma Nova Pessoa";
				JOptionPane.showMessageDialog(MenuView.jDBody,
						"Não pode criar uma tarefa para uma pessoa que ainda não existe!\n" + valor);
			} else {
				TarefasSaveView taskView = new TarefasSaveView(null, this.pessoa, null, MenuView.getInstance(), true,
						null, false);
				taskView.setVisible(true);
				taskView.addWindowListener(new WindowListener() {

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowClosed(WindowEvent e) {
						boolean open = recebeSessao();
						pessoa = (Pessoa) dao.receberObjeto(Pessoa.class, Integer.parseInt(txCodigo.getText()),
								session);
						preencherTarefas(pessoa);
						fechaSessao(open);
					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}
				});
			}
			break;
		case "MailTo":
			URI urlMail = null;
			try {
				if (txEmail.getText().trim().length() > 0) {
					urlMail = new URI("mailto", txEmail.getText(), null);
					Desktop.getDesktop().mail(urlMail);
				} else
					Desktop.getDesktop().mail();
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
			break;
		case "OpenURL":
			URI browser = null;
			try {
				browser = new URI(txSite.getText());
				Desktop.getDesktop().browse(browser);
				;
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
			break;
		case "Exportar":
			DefaultTableModel model = (DefaultTableModel) tbPrincipal.getModel();
			if (model.getColumnCount() > 1) {
				exportarExcel();
			} else {
				JOptionPane.showMessageDialog(MenuView.jDBody, "Nenhum registro foi encontrado!");
			}
			break;
		case "Lote":
			Set<Integer> lotes = receberLotes();
			if (lotes != null && !lotes.isEmpty())
				new TarefasSaveView(null, new Pessoa(), null, MenuView.getInstance(), true, lotes, true)
						.setVisible(true);
			break;
		default:
			break;
		}
	}

	private Set<Integer> receberLotes() {
		Set<Integer> lotes = new HashSet<>();
		Object[] opcao = { "Filtro do Sistema", "Digitar registros", "Cancelar" };
		int n = JOptionPane.showOptionDialog(null,
				"Deseja usar os registros filtrados pelo sistema ou informar manualmente\nos registros que devem ser atualizados?",
				"Escolha uma opção", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao,
				opcao[2]);
		if (n == JOptionPane.NO_OPTION) {
			JTextArea ta = new JTextArea(20, 10);
			Object[] acao = { "Concluir", "Cancelar" };
			int escolha = JOptionPane.showOptionDialog(null, new JScrollPane(ta), "Informe o texto dentro da caixa",
					JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, null, acao, acao[1]);
			if (escolha == JOptionPane.YES_OPTION) {
				// System.out.println(ta.getText());
				String[] value = ta.getText().split(" |\n|,|;");
				session = HibernateFactory.getSession();
				session.beginTransaction();
				try {
					boolean naoExiste = false;
					for (String s : value) {
						try {
							int i = Integer.parseInt(s);
							Pessoa p = (Pessoa) dao.receberObjeto(Pessoa.class, i, session);
							if (p != null)
								lotes.add(i);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(MenuView.jDBody,
									"Os registros devem ser informados como numero ex:{1,2,3,4,5}!\n" + ex);
						}
					}
					if (naoExiste)
						JOptionPane.showMessageDialog(MenuView.jDBody,
								"Algun(s) registros não foram reconhecidos pois não existem no sistema!");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MenuView.jDBody,
							"Os registros devem ser informados como numero ex:{1,2,3,4,5}!");
					if (lotes.isEmpty())
						return null;
				} finally {
					session.close();
				}

			}
		} else if (n == JOptionPane.YES_OPTION) {
			DefaultTableModel md2 = (DefaultTableModel) tbPrincipal.getModel();
			int i = 0;
			if (md2.getRowCount() == 0) {
				JOptionPane.showMessageDialog(MenuView.jDBody, "Nenhum registro foi encontrado na tabela!");
				return null;
			}
			while (md2.getRowCount() > i) {
				lotes.add(Integer.parseInt(String.valueOf(md2.getValueAt(i, 0))));
				i++;
			}
		} else
			return null;
		return lotes;
	}

	private void preencherTarefas(Pessoa pessoa) {
		List<Criterion> criterios = new ArrayList<>();
		Criterion criterion = Restrictions.eq("pessoa", pessoa);
		criterios.add(criterion);
		Order order = Order.desc("dataEvento");
		List<Tarefa> tarefas = (List<Tarefa>) dao.items(Tarefa.class, session, criterios, order);
		new AuxiliarTabela(new Tarefa(), tbAuxiliar, tarefas, criterios, order, null);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void exportarExcel() {
		int opcao = JOptionPane.showConfirmDialog(MenuView.jDBody, "Para exportar, realize um filtro\n"
				+ "Todos os registros serão exportados para o formato excel, deseja imprimir o filtro realizado",
				"Exportar Negocios", JOptionPane.YES_NO_OPTION);
		if (opcao == JOptionPane.YES_OPTION) {
			String export = carregarArquivo("Salvar arquivo");
			if (!"".equals(export)) {
				session = HibernateFactory.getSession();
				session.beginTransaction();
				realizarFiltro();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				ArrayList<ArrayList> listaImpressao = new ArrayList<>();

				Integer[] colunasLenght = new Integer[] { 6, 29, 9, 13, 5, 15, 13, 14, 14, 13, 12, 11, 14, 10, 10, 16,
						10, 19, 16, 30 };
				String[] cabecalho = new String[] { "Cod", "Nome", "Apelido", "CPF", "Dt.Nasc", "E-mail", "Site",
						"Telefone", "Celular", "Criado Em", "Atendente", "Endereço", "Categoria", "Nivel", "Origem",
						"Servicos/Produtos", "Qtde Negócios", "Status Negocio", "Honorário", "Outros Serviços" };
				listaImpressao.add(new ArrayList<>());
				for (String c : cabecalho) {
					listaImpressao.get(0).add(c);
				}
				for (int i = 0; i < listaPessoas.size(); i++) {
					listaImpressao.add(new ArrayList());

					Pessoa p = listaPessoas.get(i);
					PfPj pf = p.getPessoaFisica();

					listaImpressao.get(i + 1).add(p.getId());
					listaImpressao.get(i + 1).add(p.getNome());
					listaImpressao.get(i + 1).add(pf.getApelido() == null ? "" : pf.getApelido());
					listaImpressao.get(i + 1).add(p.getCpf());
					listaImpressao.get(i + 1).add(p.getDataNascimento());
					listaImpressao.get(i + 1).add(pf.getEmail());
					listaImpressao.get(i + 1).add(pf.getSite());
					listaImpressao.get(i + 1).add(pf.getTelefone());
					listaImpressao.get(i + 1).add(pf.getCelular());
					listaImpressao.get(i + 1).add(dateFormat.format(pf.getCriadoEm()));
					listaImpressao.get(i + 1).add(pf.getAtendente().getNome());

					StringBuilder endereco = new StringBuilder();
					if (p.getEndereco() != null && !"".equals(p.getEndereco().getNome().trim())) {
						Endereco end = p.getEndereco();
						endereco.append(end.getLogradouro());
						endereco.append(" ");
						endereco.append(end.getNome());
						endereco.append(end.getNumero().equals("") ? "" : ", " + end.getNumero());
						endereco.append(end.getComplemento().equals("") ? "" : " " + end.getComplemento());
						endereco.append(end.getBairro().equals("") ? "" : " - " + end.getBairro());
						endereco.append(end.getCep().equals("") ? "" : " - " + end.getCep());
						endereco.append(end.getCidade() == null ? "" : " - " + end.getCidade().getNome());
						endereco.append(end.getCidade() == null ? "" : " - " + end.getCidade().getEstado());
					}
					listaImpressao.get(i + 1).add(endereco.toString());// endereço completo
					listaImpressao.get(i + 1).add(pf.getCategoria() == null ? "" : pf.getCategoria().getNome());
					listaImpressao.get(i + 1).add(pf.getNivel() == null ? "" : pf.getNivel().getNome());
					listaImpressao.get(i + 1).add(pf.getOrigem() == null ? "" : pf.getOrigem().getNome());
					listaImpressao.get(i + 1).add(pf.getServico() == null ? "" : pf.getServico().getNome());

					int qtdNegocios = 0;
					StringBuilder valorHonorario = new StringBuilder();
					StringBuilder statusNegocio = new StringBuilder();
					StringBuilder statusServicosNegocios = new StringBuilder();

					List<Criterion> criterios = new ArrayList<>();
					criterios.add(Restrictions.eq("pessoa", p));
					List<Negocio> listaNegocios = dao.items(Negocio.class, session, criterios, Order.asc("id"));

					if (!listaNegocios.isEmpty()) {
						for (Negocio negocio : listaNegocios) {
							qtdNegocios++;

							valorHonorario.append(negocio.getId());
							valorHonorario.append("-");
							valorHonorario.append(NumberFormat.getCurrencyInstance().format(negocio.getHonorario()));
							valorHonorario.append("\n ");

							statusNegocio.append(negocio.getId());
							statusNegocio.append("-");
							statusNegocio.append(negocio.getStatus().getNome());
							statusNegocio.append("\n ");

							Set<ServicoContratado> servicos = negocio.getServicosContratados();
							for (ServicoContratado sc : servicos) {
								statusServicosNegocios.append(negocio.getId());
								statusServicosNegocios.append(" - ");
								statusServicosNegocios.append(sc.getServicosAgregados().getNome());
								statusServicosNegocios.append(" - ");
								statusServicosNegocios.append(NumberFormat.getCurrencyInstance().format(sc.getValor()));
								statusServicosNegocios.append("\n ");
							}

						}
					}
					listaImpressao.get(i + 1).add(qtdNegocios);// possui negocio
					listaImpressao.get(i + 1).add(statusNegocio.toString());// status do negocio
					listaImpressao.get(i + 1).add(valorHonorario.toString());// valor do negocio
					listaImpressao.get(i + 1).add(statusServicosNegocios.toString());// valor do negocio
				}
				ExcelGenerico planilha = new ExcelGenerico(export + ".xls", listaImpressao, colunasLenght);
				try {
					planilha.gerarExcel();
					dao.salvarLog(session, UsuarioLogado.getInstance().getUsuario(), "Pessoa", "Exportar",
							"Exportou relatorio xls");
					JOptionPane.showMessageDialog(null, "Gerado com sucesso em : " + export + ".xls");
					Desktop.getDesktop().open(new File(export + ".xls"));
				} catch (WriteException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao criar a planilha " + e1);
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao criar a planilha " + e1);
				} catch (NullPointerException e2) {
					e2.printStackTrace();
				} finally {
					session.close();
				}
			}
		}
	}

	private String carregarArquivo(String title) {
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle(title);
		String local = "";
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Planilha do Excel (*.xls)", ".xls"));
		int retorno = chooser.showSaveDialog(null);
		if (retorno == JFileChooser.APPROVE_OPTION) {
			local = chooser.getSelectedFile().getAbsolutePath(); //
		}
		return local;
	}

	@SuppressWarnings("rawtypes")
	private void preencherComboBox(JPanel panel) {
		for (int i = 0; i < panel.getComponentCount(); i++) {
			if (panel.getComponent(i) instanceof JComboBox) {
				padrao.preencherCombo((JComboBox) panel.getComponent(i), session, cbEstado);
			}
		}
	}

	private void preencherFormulario(Pessoa pessoa) {
		txCodigo.setText("" + pessoa.getId());
		SimpleDateFormat conversor = new SimpleDateFormat("dd/MM/yyyy");
		txCadastradoPor.setText(pessoa.getPessoaFisica().getCriadoPor() == null ? ""
				: pessoa.getPessoaFisica().getCriadoPor().getNome());

		txDataCadastro.setText(conversor.format(pessoa.getPessoaFisica().getCriadoEm()));
		txNome.setText(pessoa.getNome());
		cbAtendenteCad.setSelectedItem(pessoa.getPessoaFisica().getAtendente().getNome());
		txApelido.setText(pessoa.getPessoaFisica().getApelido());
		txCpf.setText(pessoa.getCpf());
		txDataNascimento.setText(pessoa.getDataNascimento());
		cbOrigemCad.setSelectedItem(
				pessoa.getPessoaFisica().getOrigem() == null ? "" : pessoa.getPessoaFisica().getOrigem().getNome());
		cbCategoriaCad.setSelectedItem(pessoa.getPessoaFisica().getCategoria() == null ? ""
				: pessoa.getPessoaFisica().getCategoria().getNome());
		cbNivelCad.setSelectedItem(
				pessoa.getPessoaFisica().getNivel() == null ? "" : pessoa.getPessoaFisica().getNivel().getNome());
		cbProdServicosCad.setSelectedItem(
				pessoa.getPessoaFisica().getServico() == null ? "" : pessoa.getPessoaFisica().getServico().getNome());
		txTelefone.setText(pessoa.getPessoaFisica().getTelefone());
		txCelular.setText(pessoa.getPessoaFisica().getCelular());
		txEmail.setText(pessoa.getPessoaFisica().getEmail());
		txSite.setText(pessoa.getPessoaFisica().getSite());

		Endereco end = pessoa.getEndereco();
		if (end != null) {
			cbLogradouro.setSelectedItem(DefaultEnumModel.Logradouro.valueOf(end.getLogradouro()));
			txLogradouro.setText(end.getNome());
			txNum.setText(end.getNumero());
			txComplemento.setText(end.getComplemento());

			cbEstado.setSelectedItem(end.getCidade() == null ? "" : end.getCidade().getEstado());
			cbCidade.setSelectedItem(end.getCidade() == null ? "" : end.getCidade().getNome());
		}
	}

	private void salvarCancelar() {
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		desbloquerFormulario(false, pnPrincipal);
		if (pessoaBackup != null) {
			pessoa = pessoaBackup;
			preencherFormulario(pessoa);
		}
		if ("".equals(txCodigo.getText()))
			btnExcluir.setEnabled(false);
	}

	private void novoEditar() {
		desbloquerFormulario(true, pnPrincipal);
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnExcluir.setEnabled(false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pesquisar();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		new UnsupportedOperationException();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		new UnsupportedOperationException();
	}

	private void pesquisar() {
		List<Pessoa> lista = new ArrayList<>();
		for (int i = 0; i < listaPessoas.size(); i++) {
			String texto = txBuscar.getText().trim().toUpperCase();
			if (listaPessoas.get(i).getNome().trim().length() > texto.length()
					&& listaPessoas.get(i).getNome().substring(0, texto.length()).equalsIgnoreCase(texto)) {
				lista.add(listaPessoas.get(i));
			}
		}
		if (!lista.isEmpty()) {
			preencherTabela(lista, tbPrincipal, txContador);
		}
	}

	// atualizador de combo box
	@SuppressWarnings("rawtypes")
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			JComboBox combo = (JComboBox) e.getSource();
			if (cbEstado.getSelectedItem() != null) {
				boolean openHere = recebeSessao();
				if ("Estado".equals(combo.getName())) {
					padrao.preencherCombo(cbCidade, session, cbEstado);
				} else
					realizarFiltro();
				fechaSessao(openHere);
			}
		}

	}

	@SuppressWarnings({ "unchecked" })
	private void realizarFiltro() {
		if (!telaEmEdicao) {
			List<Criterion> criterios = new ArrayList<>();
			if (!cbCategoria.getSelectedItem().equals(cbCategoria.getName()))
				criterios.add(Restrictions.eq("pessoaFisica.categoria",
						padrao.getCategorias((String) cbCategoria.getSelectedItem())));
			if (!cbOrigem.getSelectedItem().equals(cbOrigem.getName()))
				criterios.add(
						Restrictions.eq("pessoaFisica.origem", padrao.getOrigens((String) cbOrigem.getSelectedItem())));
			if (!cbProdServicos.getSelectedItem().equals(cbProdServicos.getName()))
				criterios.add(Restrictions.eq("pessoaFisica.servico",
						padrao.getServicos((String) cbProdServicos.getSelectedItem())));
			if (!cbAtendente.getSelectedItem().equals(cbAtendente.getName()))
				criterios.add(Restrictions.eq("pessoaFisica.atendente",
						padrao.getAtendentes((String) cbAtendente.getSelectedItem())));
			if (!"".equals(txBuscar.getText().trim()))
				criterios.add(Restrictions.ilike("nome", txBuscar.getText().trim() + "%"));
			try {
				Date data01 = data1.getDate();
				Date data02 = data2.getDate();
				if (data02.compareTo(data01) >= 0) {
					criterios.add(Restrictions.between("pessoaFisica.criadoEm", data01, data02));
				}
			} catch (NullPointerException e) {
			}
			listaPessoas.clear();
			listaPessoas = dao.items(Pessoa.class, session, criterios, Order.desc("id"));
			preencherTabela(listaPessoas, tbPrincipal, txContador);
		} else
			JOptionPane.showMessageDialog(jDBody,
					"Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!", "Em edicao...",
					JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean recebeSessao() {
		boolean open = false;
		if (!session.isOpen()) {
			session = HibernateFactory.getSession();
			session.beginTransaction();
			open = true;
		}
		return open;
	}

	private void fechaSessao(boolean close) {
		if (close) {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	private void limparFormulario(Container container) {
		for (Component c : container.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			} else if (c instanceof JComboBox) {
				((JComboBox) c).setSelectedItem("");
			} else if (c instanceof JTextArea) {
				((JTextArea) c).setText("");
			} else if (c instanceof JScrollPane) {
				JViewport viewPort = ((JScrollPane) c).getViewport();
				limparFormulario((Container) viewPort);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void desbloquerFormulario(boolean desbloquear, Container container) {
		for (Component c : container.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setEditable(desbloquear);
			} else if (c instanceof JComboBox) {
				((JComboBox) c).setEnabled(desbloquear);
			} else if (c instanceof JTextArea) {
				((JTextArea) c).setEnabled(desbloquear);
			} else if (c instanceof JScrollPane) {
				JViewport viewPort = ((JScrollPane) c).getViewport();
				desbloquerFormulario(desbloquear, (Container) viewPort);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int valor = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0));
		if (valor > 0 && !telaEmEdicao) {
			boolean open = recebeSessao();
			pessoa = (Pessoa) dao.receberObjeto(Pessoa.class, valor, session);
			preencherFormulario(pessoa);
			fechaSessao(open);
			pnAuxiliar.setVisible(false);
		} else
			JOptionPane.showMessageDialog(jDBody,
					"Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!", "Em edição...",
					JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		new UnsupportedOperationException();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		new UnsupportedOperationException();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		new UnsupportedOperationException();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	private void invocarSalvamento() {
		PfPj pessoaFisica;
		Endereco endereco = new Endereco();
		if ("".equals(txCodigo.getText())) {
			pessoa = new Pessoa();
			pessoaFisica = new PfPj();
			pessoaFisica.setCriadoEm(new Date());
			pessoaFisica.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		} else {
			pessoaFisica = pessoa.getPessoaFisica();
		}
		pessoaFisica.setTelefone(txTelefone.getText());
		pessoaFisica.setCelular(txCelular.getText());
		pessoaFisica.setEmail(txEmail.getText());
		pessoaFisica.setSite(txSite.getText());
		pessoaFisica.setApelido(txApelido.getText());
		// se nao tem usuario cadastrado
		if ("".equals(cbAtendenteCad.getSelectedItem())) {
			pessoaFisica.setAtendente(UsuarioLogado.getInstance().getUsuario());
		} else
			pessoaFisica.setAtendente(padrao.getAtendentes((String) cbAtendenteCad.getSelectedItem()));

		if (!"".equals(cbProdServicosCad.getSelectedItem()))
			pessoaFisica.setServico(padrao.getServicos((String) cbProdServicosCad.getSelectedItem()));
		if (!"".equals(cbCategoriaCad.getSelectedItem()))
			pessoaFisica.setCategoria(padrao.getCategorias((String) cbCategoriaCad.getSelectedItem()));
		if (!"".equals(cbOrigemCad.getSelectedItem()))
			pessoaFisica.setOrigem(padrao.getOrigens((String) cbOrigemCad.getSelectedItem()));
		pessoa.setNome(txNome.getText());
		pessoa.setCpf("".equals(txCpf.getText().replace(".", "").replace("-", "")) ? ""
				: txCpf.getText().replace(".", "").replace("-", ""));
		pessoa.setDataNascimento(
				"".equals(txDataNascimento.getText().trim().replace("/", "")) ? "" : txDataNascimento.getText());
		// endereco
		endereco.setLogradouro(cbLogradouro.getSelectedItem().toString());
		endereco.setNome(txLogradouro.getText());
		endereco.setComplemento(txComplemento.getText());
		endereco.setNumero(txNum.getText());
		endereco.setCep(txCep.getText().replace("-", ""));
		endereco.setBairro(txBairro.getText());

		if (!"".equals(cbCidade.getSelectedItem())) {
			Cidade cidade = padrao.getCidades((String) cbCidade.getSelectedItem());
			endereco.setCidade(cidade);
		}
		pessoa.setEndereco(endereco);
		pessoa.setPessoaFisica(pessoaFisica);
		boolean openHere = recebeSessao();
		boolean salvo = dao.salvar(pessoa, session);
		fechaSessao(openHere);
		if (salvo) {
			openHere = recebeSessao();
			List<Criterion> criterion = new ArrayList<>();
			Order order = Order.desc("id");
			listaPessoas = (List<Pessoa>) (new GenericDao().items(Pessoa.class, session, criterion, order));
			preencherFormulario(pessoa);
			preencherTabela(listaPessoas, tbPrincipal, txContador);
			fechaSessao(openHere);
			salvarCancelar();
		}
	}

	@SuppressWarnings("unchecked")
	private void invocarExclusao() {
		int escolha = JOptionPane.showConfirmDialog(jDBody,
				"Você deseja excluir esse registro? "
						+ "\nTodos os históricos serão perdidos, lembre-se que essa ação não terá mais volta!",
				"Pedido de Exclusão", JOptionPane.YES_NO_OPTION);
		if (escolha == JOptionPane.YES_OPTION) {
			boolean openHere = recebeSessao();
			boolean excluiu = dao.excluir(pessoa, session);
			fechaSessao(openHere);
			if (excluiu) {
				limparFormulario(pnPrincipal);
				openHere = recebeSessao();
				List<Criterion> criterion = new ArrayList<>();
				Order order = Order.desc("id");
				listaPessoas = (List<Pessoa>) (new GenericDao().items(Pessoa.class, session, criterion, order));
				preencherTabela(listaPessoas, tbPrincipal, txContador);
				fechaSessao(openHere);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if (data02.compareTo(data01) >= 0) {
				boolean open = recebeSessao();
				realizarFiltro();
				fechaSessao(open);
			}
		} catch (NullPointerException e) {
		}

	}

	@SuppressWarnings("serial")
	public void preencherTabela(List<Pessoa> lista, JTable table, JLabel txContadorRegistros) {
		if (lista.isEmpty()) {
			new SemRegistrosJTable(table, "Relação de Pessoas");
		} else {
			String[] tableHeader = { "ID", "NOME", "CATEGORIA", "ORIGEM", "CRIADO EM", "ATENDENTE", "NEGOCIO", "" };
			DefaultTableModel model = new DefaultTableModel(tableHeader, 0) {
				boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, true };

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}

				@Override
				public Class getColumnClass(int columnIndex) {
					try {
						return getValueAt(0, columnIndex).getClass();
					}catch(NullPointerException e) {
						return ImageIcon.class;
					}
				}
			};
			for (int i = 0; i < lista.size(); i++) {
				Pessoa p = lista.get(i);
				Object[] linha = new Object[8];
				linha[0] = "" + p.getId();
				linha[1] = p.getNome();
				linha[2] = p.getPessoaFisica().getCategoria() == null ? ""
						: p.getPessoaFisica().getCategoria().getNome();
				linha[3] = p.getPessoaFisica().getOrigem() == null ? "" : p.getPessoaFisica().getOrigem().getNome();
				try {
					Date criadoEm = p.getPessoaFisica().getCriadoEm();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					linha[4] = sdf.format(criadoEm);
				} catch (NumberFormatException e) {
					linha[4] = "";
				}
				linha[5] = p.getPessoaFisica().getAtendente() == null ? ""
						: p.getPessoaFisica().getAtendente().getNome();
				linha[6] = null;
				if (p.getUltimoNegocio() != null) {
					if ("Ganho".equals(p.getUltimoNegocio().getStatus().getNome())) {
						linha[6] = recalculate(new ImageIcon(
								ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_winner.png")),
								15);
					} else if ("Perdido".equals(p.getUltimoNegocio().getStatus().getNome()) || 
							"Sem Movimento".equals(p.getUltimoNegocio().getStatus().getNome())) {
						linha[6] = recalculate(new ImageIcon(
								ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_sad.png")), 15);
					} else if ("Em Andamento".equals(p.getUltimoNegocio().getStatus().getNome())) {
						linha[6] = recalculate(new ImageIcon(
								ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_deadline.png")),
								15);
					}
				}
				String bt = p.getUltimoNegocio() == null ? "button_add.png" : "button_negocios.png";
				linha[7] = recalculate(
						new ImageIcon(ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/" + bt)));
				model.addRow(linha);
			}
			txContadorRegistros.setText("Total: " + lista.size() + " registros");
			table.setRowHeight(30);
			table.setModel(model);
			table.setAutoCreateRowSorter(true);
			table.setSelectionBackground(Color.orange);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);

			JButton btAbrir = new ButtonColumnModel(table, 7).getButton();
			btAbrir.setActionCommand("Abrir");
			btAbrir.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String value = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0).toString();
					int id = Integer.parseInt(value);
					session = HibernateFactory.getSession();
					session.beginTransaction();
					Pessoa p = (Pessoa) new GenericDao().receberObjeto(Pessoa.class, id, session);
					Negocio negocio = p.getUltimoNegocio();
					NegociosView viewNegocios = new NegociosView(negocio,p);
					ControllerMenu.getInstance().abrirCorpo(viewNegocios);
					session.close();
				}
			});

		}
	}

	public void setarIcones() throws NullPointerException {
		ImageIcon iconNovo = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
		btnNovo.setIcon(recalculate(iconNovo));
		btnCategoriaCad.setIcon(iconNovo);
		btnNivelCad.setIcon(iconNovo);
		btnOrigemCad.setIcon(iconNovo);
		btnServicosCad.setIcon(iconNovo);

		ImageIcon iconEdit = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_edit.png"));
		btnEditar.setIcon(recalculate(iconEdit));
		ImageIcon iconSave = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_save.png"));
		btnSalvar.setIcon(recalculate(iconSave));
		ImageIcon iconCancel = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_cancel.png"));
		btnCancelar.setIcon(recalculate(iconCancel));
		ImageIcon iconTrash = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_trash.png"));
		btnExcluir.setIcon(recalculate(iconTrash));

		ImageIcon iconNewTask = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_addtask.png"));
		btnNovaTarefa.setIcon(recalculate(iconNewTask));
		ImageIcon iconTask = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_task.png"));
		btnHistorico.setIcon(recalculate(iconTask));
		ImageIcon iconNegocios = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_negocios.png"));
		btnNegocios.setIcon(recalculate(iconNegocios));
		ImageIcon iconEmpresas = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_empresas.png"));
		btnEmpresas.setIcon(recalculate(iconEmpresas));
		ImageIcon iconEsconder = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_nofixar.png"));
		btEsconder.setIcon(recalculate(iconEsconder));

		ImageIcon iconMail = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_mail.png"));
		btnEmail.setIcon(recalculate(iconMail));
		ImageIcon iconURL = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_chrome.png"));
		btnLink.setIcon(recalculate(iconURL));

		ImageIcon iconImp = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_import.png"));
		btnImportar.setIcon(recalculate(iconImp));
		ImageIcon iconExp = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_export.png"));
		btnExportar.setIcon(recalculate(iconExp));
		ImageIcon iconLote = new ImageIcon(
				ControllerPessoas.class.getResource("/br/com/tiagods/utilitarios/button_cascata.png"));
		btnLote.setIcon(recalculate(iconLote));
	}

	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException {
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth() / 2, icon.getIconHeight() / 2, 100));
		return icon;
	}

	public ImageIcon recalculate(ImageIcon icon, int valor) throws NullPointerException {
		icon.setImage(
				icon.getImage().getScaledInstance(icon.getIconWidth() - valor, icon.getIconHeight() - valor, 100));
		return icon;
	}
}
