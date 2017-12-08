package br.com.tiagods.controller;

import static br.com.tiagods.view.dialog.SelecaoDialog.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Lista;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Prospeccao;
import br.com.tiagods.model.ProspeccaoTipoContato;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.ServicoAgregado;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.dialog.SelecaoDialog;

public class ControllerSeletor implements ActionListener,MouseListener,KeyListener {
	
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();
	
	private JTextField labelId;
	private JTextField labelNome;
	private JComboBox<String>[] combobox;
	private SelecaoDialog view;
	private JComboBox<String>[] comboNegocios;
	
	private boolean telaEmEdicao = false;
	Object object;
	GenericDao dao= new GenericDao();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void iniciar(JTextField labelId, JTextField labelNome,SelecaoDialog view,JComboBox[] combobox, JComboBox[] comboNegocios){
		if(labelId!=null && labelNome!=null){
			this.labelId=labelId;
			this.labelNome=labelNome;
			this.labelId.setOpaque(true);
			this.labelNome.setOpaque(true);
			this.labelId.setForeground(Color.BLUE);
			this.labelNome.setForeground(Color.BLUE);
		}
		this.view=view;
		this.combobox = combobox;
		this.comboNegocios = comboNegocios;
		pnCadastrar.setVisible(false);
		setarIcones();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processarObjeto(Object object,String atributo, String buscarValor){
		this.object=object;
		if(object != null){
			Session session = HibernateFactory.getSession();
			session.beginTransaction();
			String[] colunas = {"ID", "NOME"};
			String[][] linhas = null;
			List<Criterion> criterion = new ArrayList<>();
			if(!"".equals(buscarValor)){
				if("id".equals(atributo)){
					try{
						int valor = Integer.parseInt(buscarValor);
						criterion.add(Restrictions.idEq(valor));
					}catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, "O valor digitado não é um numero valido para ID, será ignorado na busca!");
					}
				}
				else
					criterion.add(Restrictions.ilike(atributo, buscarValor+"%"));
			}
			GenericDao dao = new GenericDao();
			if(object instanceof Empresa){
				List<Empresa> lista = dao.items(Empresa.class, session, criterion, Order.desc("id"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relação de Empresas");
			}
			if(object instanceof Lista){
				List<Lista> lista = dao.items(Lista.class, session, criterion, Order.desc("id"));
				colunas = new String[]{"ID","NOME","DETALHES"};
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
					linhas[i][2] = lista.get(i).getDetalhes();
				}
				view.setTitle("Relação de Listas");
			}
			else if(object instanceof Negocio){
				List<Negocio> lista = dao.items(Negocio.class, session, criterion, Order.desc("id"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relação de Negócios");
			}
			else if (object instanceof Pessoa){
				List<Pessoa> lista = dao.items(Pessoa.class, session, criterion, Order.desc("id"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relação de Pessoas");
			}
			else if (object instanceof Prospeccao){
				List<Prospeccao> lista = dao.items(Prospeccao.class, session, criterion, Order.desc("id"));
				colunas = new String[]{"ID","NOME","RESPONSAVEL"};
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
					linhas[i][2] = lista.get(i).getResponsavel();
				}
				view.setTitle("Relação de Prospects");
			}
			else if (object instanceof ProspeccaoTipoContato){
				List<ProspeccaoTipoContato> lista = dao.items(ProspeccaoTipoContato.class, session, criterion, Order.desc("id"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relação de Tipo de Contato");
			}

			else if(object instanceof Categoria){
				List<Categoria> lista = dao.items(Categoria.class, session, criterion, Order.asc("nome"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Categorias");
			}
			else if(object instanceof Nivel){
				List<Nivel> lista = dao.items(Nivel.class, session, criterion, Order.asc("nome"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Niveis");
			}
			else if(object instanceof Origem){
				List<Origem> lista = dao.items(Origem.class, session, criterion, Order.asc("nome"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Origens");
			}
			else if(object instanceof Servico){
				List<Servico> lista = dao.items(Servico.class, session, criterion, Order.asc("nome"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Produtos/Serviços");
			}
			else if(object instanceof ServicoAgregado){
				List<ServicoAgregado> lista = dao.items(ServicoAgregado.class, session, criterion, Order.asc("nome"));
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Serviços a Contratar");
			}
			comboFiltro.setModel(new DefaultComboBoxModel(colunas));
			comboFiltro.setSelectedItem(atributo.toUpperCase());
			
			tbRelacao.setModel(new DefaultTableModel(linhas,colunas));
			tbRelacao.setAutoCreateRowSorter(true);
			tbRelacao.getColumnModel().getColumn(0).setMaxWidth(40);
			session.close();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "OK":
			transferirDados();
			break;
		case "Desistir":
			sair();
			break;
		case "Novo":
			if(object instanceof Negocio){
				JOptionPane.showMessageDialog(MenuView.jDBody, "Não é possivel criar ou editar um registro de um  NEGOCIO diretamente. \nClique sobre o menu Negocios","Acesso proibido",JOptionPane.WARNING_MESSAGE);
			}
			else{
				pnCadastrar.setVisible(true);
				txCodigo.setText("");
				txNome.setText("");
				novoEditar();
			}
			break;
		case "Editar":
			if(object instanceof Negocio){
				JOptionPane.showMessageDialog(MenuView.jDBody, "Não é possivel criar ou editar um registro de um  NEGOCIO diretamente. \nClique sobre o menu Negocios","Acesso proibido",JOptionPane.WARNING_MESSAGE);
			}
			else if(!"".equals(txCodigo.getText())){
				pnCadastrar.setVisible(true);
				int o = JOptionPane.showConfirmDialog(MenuView.jDBody, 
						"Fique ciente que qualquer alteração irá interferir diretamente nos cadastros e relatorios de outras tabelas,\n"
						+ "Se você mudar o nome desse registro, as outras tabelas que também dependendem desse registro também terão seus atributos nomeados!\n"
						+ "Você pode nomear desde que aceite essas regras"
						+ "","Leia-me...",JOptionPane.YES_NO_OPTION);
				if(o==JOptionPane.OK_OPTION)
					novoEditar();
			}
			else JOptionPane.showMessageDialog(MenuView.jDBody, "Selecione um registro da tabela!");
			break;
		case "Salvar":
			if(txNome.getText().trim().length()>=3)
				invocarSalvamento();
			else
				JOptionPane.showMessageDialog(MenuView.jDBody, "Informe 3 ou mais caracteres para continuar!");
			break;
		case "Cancelar":
			salvarCancelar();
			break;
		case "Excluir":
			if(object instanceof Negocio){
				JOptionPane.showMessageDialog(MenuView.jDBody, "Não é possivel criar ou editar um registro de um  NEGOCIO diretamente. \nClique sobre o menu Negocios","Acesso proibido",JOptionPane.WARNING_MESSAGE);
			}
			else invocarExclusao();
			break;
		default:
			break;
		}
	}
	public void transferirDados(){
		if(labelId!=null){
			if(!"".equals(txCodigo.getText())){
				int escolha = JOptionPane.showConfirmDialog(MenuView.jDBody, 
						"Registro escolhido: \nCodigo: "
								+txCodigo.getText()+" \nNome: "+txNome.getText()+"\nConfirmar?","Aviso...",JOptionPane.OK_CANCEL_OPTION);
				if(escolha==JOptionPane.OK_OPTION){
					labelId.setText(txCodigo.getText());
					labelNome.setText(txNome.getText());
					if(comboNegocios!=null){
						if(object instanceof Pessoa || object instanceof Empresa || object instanceof Prospeccao){
							int option = JOptionPane.showConfirmDialog(MenuView.jDBody,
									"Deseja vincular os dados da "+object.getClass().getSimpleName()+": "+txCodigo.getText()+" e nome "+txNome.getText()+" ?"+
											"\nSe clicar em Sim, CATEGORIA, ORIGEM, NIVEL E SERVICO de "+txNome.getText()+
											" serão usados no cadastro do negócio?\nDeseja sobrescrever os valores?"
											,"Substituir classificações", JOptionPane.YES_NO_OPTION);
							if(option==JOptionPane.YES_OPTION){
								Session session = HibernateFactory.getSession();
								session.beginTransaction();
								if(object instanceof Empresa){
									Empresa empresa = (Empresa)dao.receberObjeto(Empresa.class, Integer.parseInt(txCodigo.getText()), session);
									comboNegocios[0].setSelectedItem(empresa.getPessoaJuridica().getCategoria()==null?"":empresa.getPessoaJuridica().getCategoria().getNome());
									comboNegocios[1].setSelectedItem(empresa.getPessoaJuridica().getOrigem()==null?"":empresa.getPessoaJuridica().getOrigem().getNome());
									comboNegocios[2].setSelectedItem(empresa.getPessoaJuridica().getNivel()==null?"":empresa.getPessoaJuridica().getNivel().getNome());
									comboNegocios[3].setSelectedItem(empresa.getPessoaJuridica().getServico()==null?"":empresa.getPessoaJuridica().getServico().getNome());
								}
								else if(object instanceof Pessoa){
									Pessoa pessoa = (Pessoa)dao.receberObjeto(Pessoa.class, Integer.parseInt(txCodigo.getText()), session);
									comboNegocios[0].setSelectedItem(pessoa.getPessoaFisica().getCategoria()==null?"":pessoa.getPessoaFisica().getCategoria().getNome());
									comboNegocios[1].setSelectedItem(pessoa.getPessoaFisica().getOrigem()==null?"":pessoa.getPessoaFisica().getOrigem().getNome());
									comboNegocios[2].setSelectedItem(pessoa.getPessoaFisica().getNivel()==null?"":pessoa.getPessoaFisica().getNivel().getNome());
									comboNegocios[3].setSelectedItem(pessoa.getPessoaFisica().getServico()==null?"":pessoa.getPessoaFisica().getServico().getNome());
								}
								else if(object instanceof Prospeccao){
									Prospeccao prospeccao = (Prospeccao)dao.receberObjeto(Prospeccao.class, Integer.parseInt(txCodigo.getText()), session);
									comboNegocios[0].setSelectedItem("");
									comboNegocios[1].setSelectedItem(prospeccao.getPfpj().getOrigem()==null?"":prospeccao.getPfpj().getOrigem().getNome());
									comboNegocios[2].setSelectedItem("");
									comboNegocios[3].setSelectedItem(prospeccao.getPfpj().getServico()==null?"":prospeccao.getPfpj().getServico().getNome());
								}
								session.close();
							}
						}
					}
				}
				view.dispose();
			}
			else 
				JOptionPane.showMessageDialog(MenuView.jDBody, "Selecione um registro da tabela!");
		}
		else
			view.dispose();
	}
	
	public void sair(){
		if(telaEmEdicao){
			int escolha = JOptionPane.showConfirmDialog(MenuView.jDBody,
					"Um registro encontra-se em edição,\n"
					+ "dados não salvos poderão ser perdidos,\n"
					+ "mesmo assim deseja sair e abandonar o cadastro? \n"
					+ "Clique em OK para SAIR", 
					"Cadastro aberto...", JOptionPane.OK_CANCEL_OPTION);
			if(escolha == JOptionPane.OK_OPTION)
				view.dispose();
		}
		else
			view.dispose();
	}
	private void invocarSalvamento(){
		GenericDao dao = new GenericDao();
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		Object novoObjeto = null;
		if(object instanceof Empresa){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Empresa();
				PfPj pessoaJuridica = new PfPj();
				pessoaJuridica.setCriadoEm(new Date());
				pessoaJuridica.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
				pessoaJuridica.setAtendente(UsuarioLogado.getInstance().getUsuario());
				((Empresa)novoObjeto).setPessoaJuridica(pessoaJuridica);
			}
			else 
				novoObjeto = (Empresa)dao.receberObjeto(Empresa.class, Integer.parseInt(txCodigo.getText()), session);
			((Empresa)novoObjeto).setNome(txNome.getText().trim());
		}		
		else if(object instanceof Lista){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Lista();
				((Lista)novoObjeto).setCriadoEm(new Date());
				((Lista)novoObjeto).setCriadoPor(UsuarioLogado.getInstance().getUsuario());
			}
			else 
				novoObjeto = (Lista)dao.receberObjeto(Lista.class, Integer.parseInt(txCodigo.getText()), session);
			((Lista)novoObjeto).setNome(txNome.getText().trim());
			((Lista)novoObjeto).setDetalhes(txDetalhes.getText().trim());
		}
		else if(object instanceof Negocio){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Negocio();
				((Negocio)novoObjeto).setCriadoEm(new Date());
				((Negocio)novoObjeto).setCriadoPor(UsuarioLogado.getInstance().getUsuario());
				((Negocio)novoObjeto).setAtendente(UsuarioLogado.getInstance().getUsuario());
			}
			else 
				novoObjeto = (Negocio)dao.receberObjeto(Negocio.class, Integer.parseInt(txCodigo.getText()), session);
			((Negocio)novoObjeto).setNome(txNome.getText().trim());
		}
		else if(object instanceof Pessoa){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Pessoa();
				PfPj pessoaFisica = new PfPj();
				pessoaFisica.setCriadoEm(new Date());
				pessoaFisica.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
				pessoaFisica.setAtendente(UsuarioLogado.getInstance().getUsuario());
				((Pessoa)novoObjeto).setPessoaFisica(pessoaFisica);
			}
			else 
				novoObjeto = (Pessoa)dao.receberObjeto(Pessoa.class, Integer.parseInt(txCodigo.getText()), session);
			((Pessoa)novoObjeto).setNome(txNome.getText().trim());
		}
		else if(object instanceof Prospeccao){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Prospeccao();
				PfPj pfpj = new PfPj();
				pfpj.setCriadoEm(new Date());
				pfpj.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
				pfpj.setAtendente(UsuarioLogado.getInstance().getUsuario());
				((Prospeccao)novoObjeto).setPfpj(pfpj);
			}
			else 
				novoObjeto = (Prospeccao)dao.receberObjeto(Prospeccao.class, Integer.parseInt(txCodigo.getText()), session);
			((Prospeccao)novoObjeto).setNome(txNome.getText().trim());
		}
		else if(object instanceof ProspeccaoTipoContato){
			if("".equals(txCodigo.getText())){
				novoObjeto = new ProspeccaoTipoContato();
			}
			else 
				novoObjeto = (ProspeccaoTipoContato)dao.receberObjeto(ProspeccaoTipoContato.class, Integer.parseInt(txCodigo.getText()), session);
			((ProspeccaoTipoContato)novoObjeto).setNome(txNome.getText().trim());
		}
		else if(object instanceof Categoria){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Categoria();
			}
			else 
				novoObjeto = (Categoria)dao.receberObjeto(Categoria.class, Integer.parseInt(txCodigo.getText()), session);
			((Categoria)novoObjeto).setNome(txNome.getText().trim());
		}
		else if(object instanceof Nivel){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Nivel();
			}
			else 
				novoObjeto = (Nivel)dao.receberObjeto(Nivel.class, Integer.parseInt(txCodigo.getText()), session);
			((Nivel)novoObjeto).setNome(txNome.getText().trim());
		}
		else if(object instanceof Origem){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Origem();
			}
			else 
				novoObjeto = (Origem)dao.receberObjeto(Origem.class, Integer.parseInt(txCodigo.getText()), session);
			((Origem)novoObjeto).setNome(txNome.getText().trim());
		}
		else if(object instanceof Servico){
			if("".equals(txCodigo.getText())){
				novoObjeto = new Servico();
			}
			else 
				novoObjeto = (Servico)dao.receberObjeto(Servico.class, Integer.parseInt(txCodigo.getText()), session);
			((Servico)novoObjeto).setNome(txNome.getText().trim());
		}
		else if(object instanceof ServicoAgregado){
			if("".equals(txCodigo.getText())){
				novoObjeto = new ServicoAgregado();
			}
			else 
				novoObjeto = (ServicoAgregado)dao.receberObjeto(ServicoAgregado.class, Integer.parseInt(txCodigo.getText()), session);
			((ServicoAgregado)novoObjeto).setNome(txNome.getText().trim());
		}
		if(novoObjeto!=null) {
			if(dao.salvar(novoObjeto,session)){
				limparFormulario(pnCadastrar);
				processarObjeto(this.object,comboFiltro.getSelectedItem().toString().toLowerCase(),"");
				reprocessarListaCombo(session);
				salvarCancelar();
			}
		}
		else
			JOptionPane.showMessageDialog(null, "Não permitido para esse tipo de registro.");
		session.close();
	}
	public void invocarExclusao(){
		int escolha = JOptionPane.showConfirmDialog(MenuView.jDBody,
				"Você esta tentando deletar um registro,\n"
				+ "qualquer outro campo que dependa desse registro será removido,\n"
				+ "Cuidado, essa alteração não terá mais volta!\n"
				+ "mesmo assim deseja excluir o registro? \n"
				+ "Clique em OK para excluir!", 
				"Pedido de remoção...", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		if(escolha == JOptionPane.OK_OPTION && !"".equals(txCodigo.getText())){
			Session session = HibernateFactory.getSession();
			session.beginTransaction();
			GenericDao dao = new GenericDao();
			Object novoObjeto = null;
			int id = Integer.parseInt(txCodigo.getText());
			if(object instanceof Empresa){
				novoObjeto = dao.receberObjeto(Empresa.class, id, session);
			}
			else if(object instanceof Lista){
				novoObjeto = dao.receberObjeto(Lista.class, id, session);
			}
			else if(object instanceof Negocio){
				novoObjeto = dao.receberObjeto(Negocio.class, id, session);
			}
			else if(object instanceof Pessoa){
				novoObjeto = dao.receberObjeto(Pessoa.class, id, session);
			}
			else if(object instanceof Categoria){
				novoObjeto = dao.receberObjeto(Categoria.class, id, session);
			}
			else if(object instanceof Nivel){
				novoObjeto = dao.receberObjeto(Nivel.class, id, session);
			}
			else if(object instanceof Origem){
				novoObjeto = dao.receberObjeto(Origem.class, id, session);
			}
			else if(object instanceof Prospeccao){
				novoObjeto = dao.receberObjeto(Prospeccao.class, id, session);
			}
			else if(object instanceof Servico){
				novoObjeto = dao.receberObjeto(Servico.class, id, session);
			}
			else if(object instanceof ServicoAgregado){
				novoObjeto = dao.receberObjeto(ServicoAgregado.class, id, session);
			}
			if(dao.excluir(novoObjeto, session)){
				limparFormulario(pnCadastrar);
				processarObjeto(this.object,comboFiltro.getSelectedItem().toString().toLowerCase(),"");
				salvarCancelar();
				reprocessarListaCombo(session);
			}
			session.close();
		}
		else
			JOptionPane.showMessageDialog(MenuView.jDBody, "Nenhum registro selecionado para exclusao");
	}
	@SuppressWarnings("unused")
	private void enviarFiltros(Object novoObjeto){
		int id=0;
		String value="";
		if(object instanceof Empresa){
			id=((Empresa) object).getId();
			value=((Empresa) object).getNome();
		}
		else if(object instanceof Negocio){
			id=((Negocio) object).getId();
			value=((Negocio) object).getNome();
		}
		else if(object instanceof Pessoa){
			id=((Pessoa) object).getId();
			value=((Pessoa) object).getNome();
		}
		else if(object instanceof Prospeccao){
			id=((Prospeccao) object).getId();
			value=((Prospeccao) object).getResponsavel();
		}
		else if(object instanceof Categoria){
			id=((Categoria) object).getId();
			value=((Categoria) object).getNome();
		}
		else if(object instanceof Nivel){
			id=((Nivel) object).getId();
			value=((Nivel) object).getNome();
		}
		else if(object instanceof Origem){
			id=((Origem) object).getId();
			value=((Origem) object).getNome();
		}
		else if(object instanceof Servico){
			id=((Servico) object).getId();
			value=((Servico) object).getNome();
		}
		else if(object instanceof ServicoAgregado){
			id=((ServicoAgregado) object).getId();
			value=((ServicoAgregado) object).getNome();
		}
	}
	private void salvarCancelar(){
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		desbloquerFormulario(false, pnCadastrar);
		txBuscar.setText("");
		if("".equals(txCodigo.getText()))
			btnExcluir.setEnabled(false);
		telaEmEdicao = false;
	}
	private void novoEditar(){
		desbloquerFormulario(true, pnCadastrar);
		if(object instanceof Lista){
			txDetalhes.setEnabled(true);
		}
		else
			txDetalhes.setEnabled(false);
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		txBuscar.setText("");
		if(!"".equals(txCodigo.getText()))
			btnExcluir.setEnabled(true);
		telaEmEdicao = true;
	}
	
	public void desbloquerFormulario(boolean desbloquear,Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JTextField){
				((JTextField)c).setEditable(desbloquear);
			}	
		}
	}
	public void limparFormulario(Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JTextField){
				((JTextField)c).setText("");
			}	
			if(c instanceof JLabel){
				((JLabel)c).setText("");
			}	
		}
	}
	public void reprocessarListaCombo(Session session){
		if(combobox!=null){
			for(JComboBox<String> combo : combobox){
				padrao.preencherCombo(combo, session, null);
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(tbRelacao.getSelectedRow()!=-1){
			if(telaEmEdicao) 
				salvarCancelar();
			txCodigo.setText((String)tbRelacao.getValueAt(tbRelacao.getSelectedRow(), 0));
			txNome.setText((String)tbRelacao.getValueAt(tbRelacao.getSelectedRow(), 1));
			if(object instanceof Lista)
				txDetalhes.setText((String)tbRelacao.getValueAt(tbRelacao.getSelectedRow(), 2));
			else
				txDetalhes.setText("");
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		new UnsupportedOperationException();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		String texto = txBuscar.getText().trim().toUpperCase();
		processarObjeto(object,comboFiltro.getSelectedItem().toString().toLowerCase(),texto);
	}
	public void setarIcones() throws NullPointerException{
		ImageIcon iconNovo = new ImageIcon(ControllerSeletor.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
		btnNovo.setIcon(recalculate(iconNovo));
		ImageIcon iconEdit = new ImageIcon(ControllerSeletor.class.getResource("/br/com/tiagods/utilitarios/button_edit.png"));
		btnEditar.setIcon(recalculate(iconEdit));
		ImageIcon iconSave = new ImageIcon(ControllerSeletor.class.getResource("/br/com/tiagods/utilitarios/button_save.png"));
		btnSalvar.setIcon(recalculate(iconSave));
		ImageIcon iconCancel = new ImageIcon(ControllerSeletor.class.getResource("/br/com/tiagods/utilitarios/button_cancel.png"));
		btnCancelar.setIcon(recalculate(iconCancel));
		ImageIcon iconTrash = new ImageIcon(ControllerSeletor.class.getResource("/br/com/tiagods/utilitarios/button_trash.png"));
		btnExcluir.setIcon(recalculate(iconTrash));
		
		ImageIcon iconImport= new ImageIcon(ControllerSeletor.class.getResource("/br/com/tiagods/utilitarios/button_import.png"));
		btnImportarCadastro.setIcon(recalculate(iconImport));
		
		ImageIcon iconOK = new ImageIcon(ControllerSeletor.class.getResource("/br/com/tiagods/utilitarios/button_ok.png"));
		btOkDialog.setIcon(recalculate(iconOK));
		ImageIcon iconExit = new ImageIcon(ControllerSeletor.class.getResource("/br/com/tiagods/utilitarios/button_exit.png"));
		btCancelDialog.setIcon(recalculate(iconExit));
	}
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
		return icon;
	}
}
