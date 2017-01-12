package br.com.tiagods.controller;

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
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Servico;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.SelecaoObjetoDialog;

import static br.com.tiagods.view.SelecaoObjetoDialog.*;

public class ControllerSeletor implements ActionListener,MouseListener,KeyListener {
	
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();
	
	private JLabel labelId;
	private JLabel labelNome;
	private JComboBox<String>[] combobox;
	private SelecaoObjetoDialog view;
	
	private boolean telaEmEdicao = false;
	Object object;
	
	public void iniciar(JLabel labelId, JLabel labelNome,SelecaoObjetoDialog view,JComboBox[] combobox){
		if(labelId!=null && labelNome!=null){
			this.labelId=labelId;
			this.labelNome=labelNome;
			this.labelId.setOpaque(true);
			this.labelNome.setOpaque(true);
			this.labelId.setForeground(Color.ORANGE);
			this.labelNome.setForeground(Color.ORANGE);
		}
		this.view=view;
		this.combobox = combobox;
		pnCadastrar.setVisible(false);
	}
	@SuppressWarnings("unchecked")
	public void processarObjeto(Object object,String atributo, String buscarValor){
		this.object=object;
		if(object != null){
			Session session = HibernateFactory.getSession();
			session.beginTransaction();
			String[] colunas = {"ID", "NOME"};
			String[][] linhas = new String[0][2];
			comboFiltro.setModel(new DefaultComboBoxModel(new String[] {"NOME", "ID"}));
			comboFiltro.setSelectedItem(atributo.toUpperCase());
			Order order = Order.asc("nome");
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
				List<Empresa> lista = dao.items(Empresa.class, session, criterion, order);
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relação de Empresas");
			}
			else if(object instanceof Negocio){
				List<Negocio> lista = dao.items(Negocio.class, session, criterion, order);
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relação de Negócios");
			}
			else if (object instanceof Pessoa){
				List<Pessoa> lista = dao.items(Pessoa.class, session, criterion, order);
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relação de Pessoas");
			}
			else if(object instanceof Categoria){
				List<Categoria> lista = dao.items(Categoria.class, session, criterion, order);
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Categorias");
			}
			else if(object instanceof Nivel){
				List<Nivel> lista = dao.items(Nivel.class, session, criterion, order);
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Niveis");
			}
			else if(object instanceof Origem){
				List<Origem> lista = dao.items(Origem.class, session, criterion, order);
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Origens");
			}
			else if(object instanceof Servico){
				List<Servico> lista = dao.items(Servico.class, session, criterion, order);
				linhas = new String[lista.size()][colunas.length];
				for(int i=0;i<lista.size();i++){
					linhas[i][0] = String.valueOf(lista.get(i).getId());
					linhas[i][1] = lista.get(i).getNome();
				}
				view.setTitle("Relacao de Produtos/Serviços");
			}
			tbRelacao.setModel(new DefaultTableModel(linhas,colunas));
			tbRelacao.setAutoCreateRowSorter(true);
			tbRelacao.getColumnModel().getColumn(0).setPreferredWidth(40);
			session.close();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "OK":
			if(labelId!=null){
				if(!"".equals(txCodigo.getText())){
					int escolha = JOptionPane.showConfirmDialog(MenuView.jDBody, 
							"Registro escolhido: \nCodigo: "
									+txCodigo.getText()+" \nNome: "+txNome.getText()+"\nConfirmar?","Aviso...",JOptionPane.OK_CANCEL_OPTION);
					if(escolha==JOptionPane.OK_OPTION){
						labelId.setText(txCodigo.getText());
						String[] nome = (txNome.getText()).split(" ");
						labelNome.setText(nome[0]);
					}
				}else JOptionPane.showMessageDialog(MenuView.jDBody, "Selecione um registro da tabela!");
			}
			view.dispose();
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
	public void invocarSalvamento(){
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
		if(dao.salvar(novoObjeto,session)){
			limparFormulario(pnCadastrar);
			processarObjeto(this.object,comboFiltro.getSelectedItem().toString().toLowerCase(),"");
			
			reprocessarListaCombo(session);
			salvarCancelar();
		}
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
			else if(object instanceof Servico){
				novoObjeto = dao.receberObjeto(Servico.class, id, session);
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

}
