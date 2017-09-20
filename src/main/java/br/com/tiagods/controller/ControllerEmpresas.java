/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.EmpresasView.btEsconder;
import static br.com.tiagods.view.EmpresasView.btnCancelar;
import static br.com.tiagods.view.EmpresasView.btnCategoriaAdd;
import static br.com.tiagods.view.EmpresasView.btnEditar;
import static br.com.tiagods.view.EmpresasView.btnEmail;
import static br.com.tiagods.view.EmpresasView.btnExcluir;
import static br.com.tiagods.view.EmpresasView.btnExportar;
import static br.com.tiagods.view.EmpresasView.btnHistorico;
import static br.com.tiagods.view.EmpresasView.btnImportar;
import static br.com.tiagods.view.EmpresasView.btnLink;
import static br.com.tiagods.view.EmpresasView.btnNegocios;
import static br.com.tiagods.view.EmpresasView.btnNivelAdd;
import static br.com.tiagods.view.EmpresasView.btnNovaTarefa;
import static br.com.tiagods.view.EmpresasView.btnNovo;
import static br.com.tiagods.view.EmpresasView.btnOrigemAdd;
import static br.com.tiagods.view.EmpresasView.btnPessoas;
import static br.com.tiagods.view.EmpresasView.btnSalvar;
import static br.com.tiagods.view.EmpresasView.btnServicoAdd;
import static br.com.tiagods.view.EmpresasView.cbAtendente;
import static br.com.tiagods.view.EmpresasView.cbAtendenteCad;
import static br.com.tiagods.view.EmpresasView.cbCategoria;
import static br.com.tiagods.view.EmpresasView.cbCategoriaCad;
import static br.com.tiagods.view.EmpresasView.cbCidade;
import static br.com.tiagods.view.EmpresasView.cbEmpresa;
import static br.com.tiagods.view.EmpresasView.cbEstado;
import static br.com.tiagods.view.EmpresasView.cbLogradouro;
import static br.com.tiagods.view.EmpresasView.cbNivel;
import static br.com.tiagods.view.EmpresasView.cbNivelCad;
import static br.com.tiagods.view.EmpresasView.cbOrigem;
import static br.com.tiagods.view.EmpresasView.cbOrigemCad;
import static br.com.tiagods.view.EmpresasView.cbProdServicos;
import static br.com.tiagods.view.EmpresasView.cbProdServicosCad;
import static br.com.tiagods.view.EmpresasView.data1;
import static br.com.tiagods.view.EmpresasView.data2;
import static br.com.tiagods.view.EmpresasView.pnAuxiliar;
import static br.com.tiagods.view.EmpresasView.pnCabecalho;
import static br.com.tiagods.view.EmpresasView.pnPrincipal;
import static br.com.tiagods.view.EmpresasView.tbAuxiliar;
import static br.com.tiagods.view.EmpresasView.tbPrincipal;
import static br.com.tiagods.view.EmpresasView.txApelido;
import static br.com.tiagods.view.EmpresasView.txBairro;
import static br.com.tiagods.view.EmpresasView.txBuscar;
import static br.com.tiagods.view.EmpresasView.txCadastradoPor;
import static br.com.tiagods.view.EmpresasView.txCelular;
import static br.com.tiagods.view.EmpresasView.txCep;
import static br.com.tiagods.view.EmpresasView.txCnpj;
import static br.com.tiagods.view.EmpresasView.txCodigo;
import static br.com.tiagods.view.EmpresasView.txComplemento;
import static br.com.tiagods.view.EmpresasView.txContador;
import static br.com.tiagods.view.EmpresasView.txDataCadastro;
import static br.com.tiagods.view.EmpresasView.txEmail;
import static br.com.tiagods.view.EmpresasView.txLogradouro;
import static br.com.tiagods.view.EmpresasView.txNome;
import static br.com.tiagods.view.EmpresasView.txNum;
import static br.com.tiagods.view.EmpresasView.txRazaoSocial;
import static br.com.tiagods.view.EmpresasView.txSite;
import static br.com.tiagods.view.EmpresasView.txTelefone;
import static br.com.tiagods.view.MenuView.jDBody;

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
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
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
import br.com.tiagods.model.Empresa;
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
import br.com.tiagods.view.SelecaoDialog;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.interfaces.DefaultEnumModel;
import br.com.tiagods.view.interfaces.ExcelGenerico;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;
import jxl.write.WriteException;
/**
 *
 * @author Tiago
 */
public class ControllerEmpresas implements ActionListener,KeyListener,ItemListener,MouseListener,PropertyChangeListener{
	
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();
	
	List<Empresa> listaEmpresas;
	
	Session session=null;
	Empresa empresa= null;
	Empresa empresaBackup;
	boolean telaEmEdicao = false;
	
	GenericDao dao = new GenericDao();
	
	@SuppressWarnings("unchecked")
	public void iniciar(Empresa empresa){
		cbEmpresa.setEnabled(false);
		cbEmpresa.setToolTipText("Filtro não criado: Aguardando programacao");
		this.empresa=empresa;
    	session = HibernateFactory.getSession();
    	session.beginTransaction();
		JPanel[] panels = {pnCabecalho,pnPrincipal};
    	for(JPanel panel : panels){
    		preencherComboBox(panel);
    	}
    	
    	List<Criterion> criterion = new ArrayList<>();
    	Order order = Order.desc("id");
    	
    	listaEmpresas = (List<Empresa>)(dao.items(Empresa.class, session, criterion, order));
    	preencherTabela(listaEmpresas, tbPrincipal,txContador);
    	if(!listaEmpresas.isEmpty() && empresa==null){
    		this.empresa = listaEmpresas.get(0);
    		preencherFormulario(this.empresa);
    	}
    	else if(empresa!=null){
    		preencherFormulario(empresa);
    	}
    	salvarCancelar();
    	desbloquerFormulario(false, pnPrincipal);
    	setarIcones();
    	session.close();
    	

		LoadingView loading = LoadingView.getInstance();
		loading.fechar();
    }
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String>[] combos=null;
		
		switch(e.getActionCommand()){
		case "Buscar":
			if(txBuscar.getText().trim().length()>=3){
				pesquisar();
			}			
			break;
		case "Novo":
			limparFormulario(pnPrincipal);
			telaEmEdicao = true;
			empresa = null;
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
			preencherTarefas(empresa);
			fechaSessao(open);
			break;
		case "Pessoas":
			pnAuxiliar.setVisible(true);
			//new AuxiliarTabela(new Pessoa(),tbAuxiliar, pessoas);
			break;
		case "Negocios":
			pnAuxiliar.setVisible(true);
			open = recebeSessao();
			List<Criterion> criterios = new ArrayList<>();
			Criterion criterion = Restrictions.eq("empresa", empresa);
			criterios.add(criterion);
			Order order = Order.desc("id");		
			List<Negocio> negocios = (List<Negocio>) dao.items(Negocio.class, session, criterios, order);
			new AuxiliarTabela(new Negocio(),tbAuxiliar, negocios,criterios, order,null);
			fechaSessao(open);
			break;
		case "Esconder":
			pnAuxiliar.setVisible(false);
			break;
		case "CriarCategoria":
			combos = new JComboBox[]{cbCategoria,cbCategoriaCad};
			SelecaoDialog dialog = new SelecaoDialog(new Categoria(), null, null, combos,null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarNivel":
			combos = new JComboBox[]{cbNivel,cbNivelCad};
			dialog = new SelecaoDialog(new Nivel(), null, null, combos,null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarOrigem":
			combos = new JComboBox[]{cbOrigem,cbOrigemCad};
			dialog = new SelecaoDialog(new Origem(), null, null, combos,null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "CriarServico":
			combos = new JComboBox[]{cbProdServicos,cbProdServicosCad};
			dialog = new SelecaoDialog(new Servico(), null, null, combos,null,MenuView.getInstance(),true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			break;
		case "Nova Tarefa":
			if("".equals(txCodigo.getText())){
				String valor;
				if(telaEmEdicao){
					valor="Salve o registro atual e depois clique no Botao Nova Tarefa para criar uma nova tarefa para essa Empresa!\nOu selecione um registro da tabela!";
				}
				else
					valor="Selecione um registro da tabela ou crie uma Nova Empresa";
				JOptionPane.showMessageDialog(MenuView.jDBody, "Não pode criar uma tarefa para uma empresa que ainda não existe!\n"
						+ valor);
			}
			else{
				TarefasSaveView taskView = new TarefasSaveView(null, this.empresa, null,MenuView.getInstance(),true);
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
						empresa = (Empresa) dao.receberObjeto(Empresa.class, Integer.parseInt(txCodigo.getText()), session);
						preencherTarefas(empresa);
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
			try{
				if(txEmail.getText().trim().length()>0){
					urlMail = new URI("mailto", txEmail.getText(), null);
					Desktop.getDesktop().mail(urlMail);
				}
				else
					Desktop.getDesktop().mail();
			}catch(IOException | URISyntaxException ex){
				ex.printStackTrace();
			}
			break;
		case "OpenURL":
			URI browser = null;
			try{
				browser = new URI(txSite.getText());
				Desktop.getDesktop().browse(browser);;
			}catch(IOException | URISyntaxException ex){
				ex.printStackTrace();
			}
			break;
		case "Exportar":
			DefaultTableModel model = (DefaultTableModel)tbPrincipal.getModel();
			if(model.getColumnCount()>1){
				exportarExcel();
			}
			else{
				JOptionPane.showMessageDialog(MenuView.jDBody,"Nenhum registro foi encontrado!");
			}
			break;
		default:
			break;
		}
	}
	private void preencherTarefas(Empresa empresa) {
		List<Criterion> criterios = new ArrayList<>();
		Criterion criterion = Restrictions.eq("empresa", empresa);
		criterios.add(criterion);
		Order order = Order.desc("dataEvento");		
		List<Tarefa> tarefas = (List<Tarefa>) dao.items(Tarefa.class, session, criterios, order);
		new AuxiliarTabela(new Tarefa(),tbAuxiliar, tarefas, criterios, order,null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void exportarExcel(){
		int opcao = JOptionPane.showConfirmDialog(MenuView.jDBody, "Para exportar, realize um filtro\n"
				+"Todos os registros serão exportados para o formato excel, deseja imprimir o filtro realizado",
				"Exportar Negocios",JOptionPane.YES_NO_OPTION);
		if(opcao==JOptionPane.YES_OPTION){
			String export = carregarArquivo("Salvar arquivo");
			if(!"".equals(export)){
				session = HibernateFactory.getSession();
				session.beginTransaction();
				realizarFiltro();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				ArrayList<ArrayList> listaImpressao = new ArrayList<>();
				
				Integer[] colunasLenght = new Integer[]{6,30,29,9,13,15,13,14,14,13,12,11,14,10,10,16,10,19,16,30};
				String[] cabecalho = new String[]{
						"Cod","Nome", "Razao Social","Apelido","CNPJ",
						"E-mail","Site","Telefone","Celular","Criado Em","Atendente","Endereço",
						"Categoria","Nivel","Origem","Servicos/Produtos","Qtde Negócios",
						"Status Negocio","Honorário","Outros Serviços"};
				listaImpressao.add(new ArrayList<>());
				for(String c : cabecalho){
					listaImpressao.get(0).add(c);
				}
				for(int i = 0;i<listaEmpresas.size();i++){
					listaImpressao.add(new ArrayList());
					
					Empresa e = listaEmpresas.get(i);
					PfPj pf = e.getPessoaJuridica();
					
					listaImpressao.get(i+1).add(e.getId());
					listaImpressao.get(i+1).add(e.getNome());
					listaImpressao.get(i+1).add(pf.getRazao()==null?"":pf.getRazao());
					listaImpressao.get(i+1).add(pf.getApelido()==null?"":pf.getApelido());
					listaImpressao.get(i+1).add(e.getCnpj()==null?"":e.getCnpj());
					listaImpressao.get(i+1).add(pf.getEmail());
					listaImpressao.get(i+1).add(pf.getSite());
					listaImpressao.get(i+1).add(pf.getTelefone());
					listaImpressao.get(i+1).add(pf.getCelular());
					listaImpressao.get(i+1).add(dateFormat.format(pf.getCriadoEm()));
					listaImpressao.get(i+1).add(pf.getAtendente().getNome());
					
					StringBuilder endereco = new StringBuilder();
					if(e.getEndereco()!=null && !"".equals(e.getEndereco().getNome().trim())){
						Endereco end = e.getEndereco(); 
						endereco.append(end.getLogradouro());
						endereco.append(" ");
						endereco.append(end.getNome());
						endereco.append(end.getNumero().equals("")?"":", "+end.getNumero());
						endereco.append(end.getComplemento().equals("")?"":" "+end.getComplemento());
						endereco.append(end.getBairro().equals("")?"":" - "+end.getBairro());
						endereco.append(end.getCep().equals("")?"":" - "+end.getCep());
						endereco.append(end.getCidade()==null?"":" - "+end.getCidade().getNome());
						endereco.append(end.getCidade()==null?"":" - "+end.getCidade().getEstado());
					}
					listaImpressao.get(i+1).add(endereco.toString());//endereço completo
					listaImpressao.get(i+1).add(pf.getCategoria()==null?"":pf.getCategoria().getNome());
					listaImpressao.get(i+1).add(pf.getNivel()==null?"":pf.getNivel().getNome());
					listaImpressao.get(i+1).add(pf.getOrigem()==null?"":pf.getOrigem().getNome());
					listaImpressao.get(i+1).add(pf.getServico()==null?"":pf.getServico().getNome());
					
					int qtdNegocios=0;
					StringBuilder valorHonorario = new StringBuilder();
					StringBuilder statusNegocio = new StringBuilder();
					StringBuilder statusServicosNegocios = new StringBuilder();
					
					List<Criterion> criterios = new ArrayList<>();
					criterios.add(Restrictions.eq("empresa", e));
					List<Negocio> listaNegocios = dao.items(Negocio.class, session, criterios, Order.asc("id"));

					if(!listaNegocios.isEmpty()){
						for(Negocio negocio : listaNegocios){
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
							for(ServicoContratado sc : servicos){
								statusServicosNegocios.append(negocio.getId());
								statusServicosNegocios.append(" - ");
								statusServicosNegocios.append(sc.getServicosAgregados().getNome());
								statusServicosNegocios.append(" - ");
								statusServicosNegocios.append(NumberFormat.getCurrencyInstance().format(sc.getValor()));
								statusServicosNegocios.append("\n ");
							}
							
						}
					}
					listaImpressao.get(i+1).add(qtdNegocios);//possui negocio
					listaImpressao.get(i+1).add(statusNegocio.toString());//status do negocio
					listaImpressao.get(i+1).add(valorHonorario.toString());//valor do negocio
					listaImpressao.get(i+1).add(statusServicosNegocios.toString());//valor do negocio
				}
				ExcelGenerico planilha = new ExcelGenerico(export+".xls",listaImpressao,colunasLenght);
				try {
					planilha.gerarExcel();
					JOptionPane.showMessageDialog(null, "Gerado com sucesso em : "+export+".xls");
					Desktop.getDesktop().open(new File(export+".xls"));
				} catch (WriteException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao criar a planilha "+e1);
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao criar a planilha "+e1);
				} catch(NullPointerException e2){
					e2.printStackTrace();
				}
				finally{
					session.close();
				}
			}
		}
	}
	private String carregarArquivo(String title){
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogTitle(title);
        String local = "";
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Planilha do Excel (*.xls)", ".xls"));
        int retorno = chooser.showSaveDialog(null);
        if(retorno==JFileChooser.APPROVE_OPTION){
        	local = chooser.getSelectedFile().getAbsolutePath(); //
        }
        return local;
    }
	@SuppressWarnings("rawtypes")
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component instanceof JComboBox){
				padrao.preencherCombo((JComboBox)component,session, cbEstado);
			}
		}
	}
	private void preencherFormulario(Empresa empresa){
		txCodigo.setText(""+empresa.getId());
		SimpleDateFormat conversor = new SimpleDateFormat("dd/MM/yyyy");
		txCadastradoPor.setText(empresa.getPessoaJuridica().getCriadoPor()==null?"":empresa.getPessoaJuridica().getCriadoPor().getNome());
		
		txDataCadastro.setText(conversor.format(empresa.getPessoaJuridica().getCriadoEm()));
		txNome.setText(empresa.getNome());
		cbAtendenteCad.setSelectedItem(empresa.getPessoaJuridica().getAtendente()==null?"":empresa.getPessoaJuridica().getAtendente().getNome());
		txApelido.setText(empresa.getPessoaJuridica().getApelido());
		txRazaoSocial.setText(empresa.getPessoaJuridica().getRazao());
		txCnpj.setText(empresa.getCnpj());
		cbNivelCad.setSelectedItem(empresa.getPessoaJuridica().getNivel()==null?"":empresa.getPessoaJuridica().getNivel().getNome());
		cbOrigemCad.setSelectedItem(empresa.getPessoaJuridica().getOrigem()==null?"":empresa.getPessoaJuridica().getOrigem().getNome());
		cbCategoriaCad.setSelectedItem(empresa.getPessoaJuridica().getCategoria()==null?"":empresa.getPessoaJuridica().getCategoria().getNome());
		cbProdServicosCad.setSelectedItem(empresa.getPessoaJuridica().getServico()==null?"":empresa.getPessoaJuridica().getServico().getNome());
		txTelefone.setText(empresa.getPessoaJuridica().getTelefone());
		txCelular.setText(empresa.getPessoaJuridica().getCelular());
		txEmail.setText(empresa.getPessoaJuridica().getEmail());
		txSite.setText(empresa.getPessoaJuridica().getSite());
		
		Endereco end = empresa.getEndereco();
		if(end!=null){
			cbLogradouro.setSelectedItem(DefaultEnumModel.Logradouro.valueOf(end.getLogradouro()));
			txLogradouro.setText(end.getNome());
			txNum.setText(end.getNumero());
			txComplemento.setText(end.getComplemento());

			cbEstado.setSelectedItem(end.getCidade()==null?"":end.getCidade().getEstado());
			cbCidade.setSelectedItem(end.getCidade()==null?"":end.getCidade().getNome());
		}
	}
	private void salvarCancelar(){
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		desbloquerFormulario(false, pnPrincipal);
		if(empresaBackup!=null){
			empresa=empresaBackup;
			preencherFormulario(empresa);
		}
		if("".equals(txCodigo.getText()))
			btnExcluir.setEnabled(false);
	}
	private void novoEditar(){
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
	private void pesquisar(){
		List<Empresa> lista = new ArrayList<>();
		for(int i =0;i<listaEmpresas.size();i++){
			String texto = txBuscar.getText().trim().toUpperCase();
			if(listaEmpresas.get(i).getNome().trim().length()>texto.length() && listaEmpresas.get(i).getNome().substring(0,texto.length()).equalsIgnoreCase(texto)){
				lista.add(listaEmpresas.get(i));
			}
		}
		if(!lista.isEmpty()){
			preencherTabela(lista, tbPrincipal,txContador);
		}
	}
	//atualizador de combo box
	@SuppressWarnings("rawtypes")
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED){
			JComboBox combo = (JComboBox)e.getSource();
			if(cbEstado.getSelectedItem()!=null){
				boolean openHere = recebeSessao();
				if("Estado".equals(combo.getName())){
					padrao.preencherCombo(cbCidade,session,cbEstado);
				}
				else
					realizarFiltro();
				fechaSessao(openHere);
			}
		}
	}
	@SuppressWarnings({ "unchecked" })
	private void realizarFiltro(){
		if(!telaEmEdicao){
			List<Criterion> criterios = new ArrayList<>();
			if(!cbCategoria.getSelectedItem().equals(cbCategoria.getName()))
				criterios.add(Restrictions.eq("pessoaJuridica.categoria", padrao.getCategorias((String)cbCategoria.getSelectedItem())));
			if(!cbNivel.getSelectedItem().equals(cbNivel.getName()))
				criterios.add(Restrictions.eq("nivel", padrao.getNiveis((String)cbNivel.getSelectedItem())));
			if(!cbOrigem.getSelectedItem().equals(cbOrigem.getName()))	
				criterios.add(Restrictions.eq("pessoaJuridica.origem", padrao.getOrigens((String)cbOrigem.getSelectedItem())));
			if(!cbProdServicos.getSelectedItem().equals(cbProdServicos.getName()))
				criterios.add(Restrictions.eq("pessoaJuridica.servico", padrao.getServicos((String)cbProdServicos.getSelectedItem())));
			if(!cbAtendente.getSelectedItem().equals(cbAtendente.getName()))
				criterios.add(Restrictions.eq("pessoaJuridica.atendente", padrao.getAtendentes((String)cbAtendente.getSelectedItem())));
			if(!"".equals(txBuscar.getText().trim()))
				criterios.add(Restrictions.ilike("nome", txBuscar.getText().trim()+"%"));
			try{
				Date data01 = data1.getDate();
				Date data02 = data2.getDate();
				if(data02.compareTo(data01)>=0){
					criterios.add(Restrictions.between("pessoaJuridica.criadoEm", data01, data02));
				}
			}catch(NullPointerException e){
				e.getMessage();
			}
			listaEmpresas.clear();
			listaEmpresas = dao.items(Empresa.class, session, criterios, Order.desc("id"));
			preencherTabela(listaEmpresas, tbPrincipal, txContador);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!","Em edicao...",JOptionPane.INFORMATION_MESSAGE);
	}
	private boolean recebeSessao(){
		boolean open = false;
		if(!session.isOpen()){
			session = HibernateFactory.getSession();
			session.beginTransaction();
			open = true;
		}
		return open;
	}
	private void fechaSessao(boolean close){
		if(close){ 
			session.close();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void limparFormulario(Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JTextField){
				((JTextField)c).setText("");
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setSelectedItem("");
			}
			else if(c instanceof JTextArea){
				((JTextArea)c).setText("");
			}
			else if(c instanceof JScrollPane){
				JViewport viewPort = ((JScrollPane)c).getViewport();
				limparFormulario((Container)viewPort);
			}	
		}
	}
	@SuppressWarnings("rawtypes")
	private void desbloquerFormulario(boolean desbloquear,Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JTextField){
				((JTextField)c).setEditable(desbloquear);
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setEnabled(desbloquear);
			}
			else if(c instanceof JTextArea){
				((JTextArea)c).setEnabled(desbloquear);
			}
			else if(c instanceof JScrollPane){
				JViewport viewPort = ((JScrollPane)c).getViewport();
				desbloquerFormulario(desbloquear,(Container)viewPort);
			}	
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int valor = Integer.parseInt((String) tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0));
		if(valor>0 && !telaEmEdicao){
			boolean open = recebeSessao();
			empresa = (Empresa)dao.receberObjeto(Empresa.class, valor, session);
			preencherFormulario(empresa);
			fechaSessao(open);
			pnAuxiliar.setVisible(false);
		}
		else
			JOptionPane.showMessageDialog(jDBody, "Por favor salve o registro em edicao ou cancele para poder realizar novas buscas!",
					"Em edicao...",JOptionPane.INFORMATION_MESSAGE);
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
	private void invocarSalvamento(){
    	PfPj pessoaJuridica;
		Endereco endereco = new Endereco();
		if("".equals(txCodigo.getText())) {
			empresa = new Empresa();
			pessoaJuridica = new PfPj();
			pessoaJuridica.setCriadoEm(new Date());
			pessoaJuridica.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		}
		else{
			pessoaJuridica = empresa.getPessoaJuridica();
		}
		pessoaJuridica.setApelido(txApelido.getText());
		pessoaJuridica.setRazao(txRazaoSocial.getText());
		pessoaJuridica.setTelefone(txTelefone.getText());
		pessoaJuridica.setCelular(txCelular.getText());
		pessoaJuridica.setEmail(txEmail.getText());
		pessoaJuridica.setSite(txSite.getText());
		//se nao tem usuario cadastrado
		if("".equals(cbAtendenteCad.getSelectedItem())){
			pessoaJuridica.setAtendente(UsuarioLogado.getInstance().getUsuario());
		}
		else
			pessoaJuridica.setAtendente(padrao.getAtendentes((String)cbAtendenteCad.getSelectedItem()));
		if(!"".equals(cbNivelCad.getSelectedItem()))
			pessoaJuridica.setNivel(padrao.getNiveis((String)cbNivelCad.getSelectedItem()));
		if(!"".equals(cbProdServicosCad.getSelectedItem()))
				pessoaJuridica.setServico(padrao.getServicos((String)cbProdServicosCad.getSelectedItem()));
		if(!"".equals(cbCategoriaCad.getSelectedItem()))
				pessoaJuridica.setCategoria(padrao.getCategorias((String)cbCategoriaCad.getSelectedItem()));
		if(!"".equals(cbOrigemCad.getSelectedItem()))
				pessoaJuridica.setOrigem(padrao.getOrigens((String)cbOrigemCad.getSelectedItem()));
		empresa.setNome(txNome.getText());
		empresa.setCnpj("".equals(txCnpj.getText().replace(".", "").replace("-", "").replace("/",""))?"":txCnpj.getText().replace(".", "").replace("-", "").replace("/", ""));
		//endereco
		endereco.setLogradouro(cbLogradouro.getSelectedItem().toString());
		endereco.setNome(txLogradouro.getText());
		endereco.setComplemento(txComplemento.getText());
		endereco.setNumero(txNum.getText());
		endereco.setCep(txCep.getText().replace("-", ""));
		endereco.setBairro(txBairro.getText());
		
		if(!"".equals(cbCidade.getSelectedItem())){
			Cidade cidade = padrao.getCidades((String)cbCidade.getSelectedItem());
			endereco.setCidade(cidade);
		}
		empresa.setEndereco(endereco);
		empresa.setPessoaJuridica(pessoaJuridica);
		GenericDao dao = new GenericDao();
		boolean openHere = recebeSessao();
		boolean salvo = dao.salvar(empresa, session);
		fechaSessao(openHere);
		if(salvo) {
			openHere = recebeSessao();
			listaEmpresas = (List<Empresa>)dao.listar(Empresa.class, session);
			preencherFormulario(empresa);
	    	preencherTabela(listaEmpresas, tbPrincipal, txContador);
	    	fechaSessao(openHere);
	    	salvarCancelar();
		}
    }
    @SuppressWarnings("unchecked")
	private void invocarExclusao(){
    	int escolha = JOptionPane.showConfirmDialog(jDBody, "Voce deseja excluir esse registro? "
				+ "\nTodos os históricos serão perdidos, lembre-se que essa ação não tera mais volta!",
				"Pedido de Exclusão", JOptionPane.YES_NO_OPTION);
		if(escolha==JOptionPane.YES_OPTION){
			boolean openHere = recebeSessao();
			
			boolean excluiu = new GenericDao().excluir(empresa,session);
			fechaSessao(openHere);
			if(excluiu){
				limparFormulario(pnPrincipal);
				openHere = recebeSessao();
				List<Criterion> criterion = new ArrayList<>();
		    	Order order = Order.desc("id");
		    	listaEmpresas = (List<Empresa>)(dao.items(Empresa.class, session, criterion, order));
		    	preencherTabela(listaEmpresas, tbPrincipal, txContador);
		    	fechaSessao(openHere);
			}
		}
    }
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try{
			Date data01 = data1.getDate();
			Date data02 = data2.getDate();
			if(data02.compareTo(data01)>=0){
				boolean open  = recebeSessao();
				realizarFiltro();
				fechaSessao(open);
			}
		}catch (NullPointerException e) {
		}
	}
	@SuppressWarnings({ "serial"})
	public void preencherTabela(List<Empresa> lista, JTable table, JLabel txContadorRegistros){
		if(lista.isEmpty()){
			new SemRegistrosJTable(table,"Relação de Empresas");
		}
		else{
			String[] tableHeader = {"ID","NOME","NIVEL","CATEGORIA","ORIGEM","CRIADO EM","ATENDENTE"};
			DefaultTableModel model = new DefaultTableModel(tableHeader,0){
				boolean[] canEdit = new boolean[]{
						false,false,false,false,false,false,false
				};
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit [columnIndex];
				}
			};
			for(int i=0;i<lista.size();i++){
				Empresa em= lista.get(i);
				Object[] linha = new Object[7];
				linha[0] = ""+em.getId(); 
				linha[1] = em.getNome();
				linha[2] = em.getPessoaJuridica().getNivel()==null?"":em.getPessoaJuridica().getNivel().getNome();
				linha[3] = em.getPessoaJuridica().getCategoria()==null?"":em.getPessoaJuridica().getCategoria().getNome();
				linha[4] = em.getPessoaJuridica().getOrigem()==null?"":em.getPessoaJuridica().getOrigem().getNome();
				try{
					Date criadoEm = em.getPessoaJuridica().getCriadoEm();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					linha[5] = sdf.format(criadoEm);
				}catch (NumberFormatException e) {
					linha[5] = "";
				}
				linha[6] = em.getPessoaJuridica().getAtendente()==null?"":em.getPessoaJuridica().getAtendente().getNome();
				model.addRow(linha);
			}
			txContadorRegistros.setText("Total: "+lista.size()+" registros");
			table.setRowHeight(25);
			table.setModel(model);
			table.setAutoCreateRowSorter(true);
			table.setSelectionBackground(Color.orange);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
		}	
	}
	public void setarIcones() throws NullPointerException{
    	ImageIcon iconNovo = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
    	btnNovo.setIcon(recalculate(iconNovo));
    	btnCategoriaAdd.setIcon(iconNovo);
    	btnNivelAdd.setIcon(iconNovo);
    	btnOrigemAdd.setIcon(iconNovo);
    	btnServicoAdd.setIcon(iconNovo);
    	
    	ImageIcon iconEdit = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_edit.png"));
    	btnEditar.setIcon(recalculate(iconEdit));
    	ImageIcon iconSave = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_save.png"));
    	btnSalvar.setIcon(recalculate(iconSave));
    	ImageIcon iconCancel = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_cancel.png"));
    	btnCancelar.setIcon(recalculate(iconCancel));
    	ImageIcon iconTrash = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_trash.png"));
    	btnExcluir.setIcon(recalculate(iconTrash));
    	
    	ImageIcon iconNewTask = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_addtask.png"));
    	btnNovaTarefa.setIcon(recalculate(iconNewTask));
    	ImageIcon iconTask = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_task.png"));
    	btnHistorico.setIcon(recalculate(iconTask));
    	ImageIcon iconNegocios = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_negocios.png"));
    	btnNegocios.setIcon(recalculate(iconNegocios));
    	
    	ImageIcon iconPessoas = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_people.png"));
    	btnPessoas.setIcon(recalculate(iconPessoas));
    	
    	ImageIcon iconEsconder = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_nofixar.png"));
    	btEsconder.setIcon(recalculate(iconEsconder));
    	
    	ImageIcon iconMail = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_mail.png"));
    	btnEmail.setIcon(recalculate(iconMail));
    	ImageIcon iconURL = new ImageIcon(ControllerEmpresas.class.getResource("/br/com/tiagods/utilitarios/button_chrome.png"));
    	btnLink.setIcon(recalculate(iconURL));
    	
    	
    	ImageIcon iconImp = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_import.png"));
    	btnImportar.setIcon(recalculate(iconImp));
    	
    	ImageIcon iconExp = new ImageIcon(ControllerNegocios.class.getResource("/br/com/tiagods/utilitarios/button_export.png"));
    	btnExportar.setIcon(recalculate(iconExp));
    }
    public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
}

