/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Etapa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.modelDAO.EmpresaDAO;
import br.com.tiagods.modelDAO.NegocioDAO;
import br.com.tiagods.modelDAO.PessoaDAO;
import br.com.tiagods.view.SelecaoObjeto;
import br.com.tiagods.view.interfaces.DefaultEnumModel.Modelos;

import static br.com.tiagods.view.NegociosView.*;

/**
 *
 * @author Tiago
 */
public class ControllerNegocios implements ActionListener,ItemListener{
	PadraoMap padrao = new PadraoMap();
	Session session = null;
	Negocio negocio = null;
	Negocio negocioBackup = null;
	boolean telaEmEdicao = false;
	List<Negocio> listarNegocios;
	
	@SuppressWarnings("unchecked")
	public void iniciar(Negocio negocio){
		this.negocio=negocio;
		session = HibernateFactory.getSession();
		session.beginTransaction();
		JPanel[] panels = {pnPrincipal,pnAndamento,pnCadastro};
		for (JPanel panel : panels) {
			preencherComboBox(panel);
		}
		listarNegocios = (List<Negocio>)new NegocioDAO().listar(Negocio.class, session);
		preencherTabela(listarNegocios, tbPrincipal, txContador);
		session.close();
    }
	@SuppressWarnings("unchecked")
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component instanceof JComboBox)
				padrao.preencherCombo((JComboBox<String>)component, session, null);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Buscar":
			if(txBuscar.getText().trim().length()>=3){
				pesquisar();
			}			
			break;
		case "Novo":
			limparFormulario(pnPrincipal);
			telaEmEdicao = true;
			negocio = null;
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
			boolean passou = verificarCondicao();
			if(passou) 
				invocarSalvamento();
			telaEmEdicao = false;
			desbloquerFormulario(false, pnPrincipal);
			break;
		case "VincularObjeto":
			SelecaoObjeto dialog = null;
			if(cbObject.getSelectedItem().equals(Modelos.Empresa.toString()))
				dialog =new SelecaoObjeto(new Empresa(),txCodObjeto,txNomeObjeto);
			else if(cbObject.getSelectedItem().equals(Modelos.Pessoa.toString()))
				dialog =new SelecaoObjeto(new Pessoa(),txCodObjeto,txNomeObjeto);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			txCodObjeto.addPropertyChangeListener(new MudarCliente());
			break;
		default:
			boolean open = recebeSessao();
			//realizarFiltro();
			fechaSessao(open);
			break;
		}
	}
	public class MudarCliente implements PropertyChangeListener{

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if(!evt.getOldValue().equals(evt.getNewValue()))
				receberObjeto();
		}
		
	}
	public void receberObjeto(){
		PfPj pfpj = new PfPj();
		session = HibernateFactory.getSession();
		session.beginTransaction();
		if(cbObject.getSelectedItem().equals(Modelos.Empresa.toString())){
			Empresa empresa = (Empresa)new EmpresaDAO().receberObjeto(Empresa.class, Integer.parseInt(txCodObjeto.getText()), session);
			pfpj = empresa.getPessoaJuridica();
		}
		else if(cbObject.getSelectedItem().equals(Modelos.Pessoa.toString())){
			Pessoa pessoa = (Pessoa)new PessoaDAO().receberObjeto(Pessoa.class, Integer.parseInt(txCodObjeto.getText()), session);
			pfpj = pessoa.getPessoaFisica();
		}
		cbCategoriaCad.setSelectedItem(pfpj.getCategoria()==null?"":pfpj.getCategoria().getNome());
		cbOrigemCad.setSelectedItem(pfpj.getOrigem()==null?"":pfpj.getOrigem().getNome());
		cbNivelCad.setSelectedItem(pfpj.getNivel()==null?"":pfpj.getNivel().getNome());
		cbServicosCad.setSelectedItem(pfpj.getServico()==null?"":pfpj.getServico().getNome());
		session.close();
	}
	public void invocarSalvamento(){
		negocio.setNome(txNome.getText().trim());
		if("".equals(txCodigo.getText())){	
			negocio = new Negocio();
			negocio.setCriadoEm(new Date());
			negocio.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		}
		negocio.setStatus(padrao.getStatus((String)cbStatusCad.getSelectedItem()));//
		if("".equals(cbAtendente.getSelectedItem()))
			negocio.setAtendente(UsuarioLogado.getInstance().getUsuario());//
		else
			negocio.setAtendente(padrao.getAtendentes((String)cbAtendente.getSelectedItem()));
		
		if(cbObject.getSelectedItem().equals(Modelos.Empresa)){
			Object o = new EmpresaDAO().receberObjeto(Empresa.class, Integer.parseInt(txCodObjeto.getText()), session);
			negocio.setClasse("Empresa");
			negocio.setEmpresa((Empresa)o);
		}
		else if(cbObject.getSelectedItem().equals(Modelos.Pessoa)){
			Object o = new PessoaDAO().receberObjeto(Pessoa.class, Integer.parseInt(txCodObjeto.getText()), session);
			negocio.setClasse("Pessoa");
			negocio.setPessoa((Pessoa)o);
		}
		negocio.setEtapa(receberEtapa(negocio));
		negocio.setDataInicio(data1.getDate());
		
		if(data2.getDate()!=null)
			negocio.setDataFim(data2.getDate());
		if("".equals(txHonorario.getSelectedText().trim().replace(",", "")))
			negocio.setHonorario(new BigDecimal("0.00"));
		else
			negocio.setHonorario(new BigDecimal(txHonorario.getText().replace(",", ".")));
		negocio.setDescricao(txDescricao.getText().trim());
		
		PfPj pessoaFisicaOrJuridica = new PfPj();
		if(!cbCategoriaCad.getSelectedItem().equals("")){
			pessoaFisicaOrJuridica.setCategoria(padrao.getCategorias((String)cbCategoriaCad.getSelectedItem()));
		}
		if(!cbNivelCad.getSelectedItem().equals("")){
			pessoaFisicaOrJuridica.setNivel(padrao.getNiveis((String)cbNivelCad.getSelectedItem()));
		}
		if(!cbOrigemCad.getSelectedItem().equals("")){
			pessoaFisicaOrJuridica.setOrigem(padrao.getOrigens((String)cbOrigemCad.getSelectedItem()));
		}
		if(!cbServicosCad.getSelectedItem().equals("")){
			pessoaFisicaOrJuridica.setServico(padrao.getServicos((String)cbServicosCad.getSelectedItem()));
		}
		negocio.setPessoaFisicaOrJuridica(pessoaFisicaOrJuridica);
		negocio.setStatus(padrao.getStatus((String)cbStatusCad.getSelectedItem()));
	}
	private Etapa receberEtapa(Negocio negocio){
		String etapa="";
		if(rbContato.isSelected()){
			etapa="Contato";
			negocio.setContato(new Date());
		}
		else if(rbEnvioProposta.isSelected()){
			etapa="Envio de Proposta";
			negocio.setEnvioProposta(new Date());
		}
		else if(rbFechamento.isSelected()){
			etapa="Fechamento";
			negocio.setFechamento(new Date());
		}
		else if(rbFollowup.isSelected()){
			etapa="Follow-up";
			negocio.setFollowUp(new Date());
		}
		else if (rbIndefinida.isSelected()){
			etapa="Idefinida";
			negocio.setIndefinida(new Date());
		}
		negocio.setAndamento(etapa);
		return padrao.getEtapa(etapa);
	}
	private boolean verificarCondicao(){
		StringBuilder builder = new StringBuilder();
		if("".equals(txCodObjeto.getText())){
			builder.append("Primeiro vincule uma Pessoa ou uma Empresa e tente salvar novamente!");
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, builder.toString());
			return false;
		}
		Date novaData1 = null;
		try{
			novaData1 = data1.getDate();
		}catch (Exception e) {
			builder.append("Data de Inicio incorreta!\n");
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, builder.toString());
			return false;
		}
		try{
			Date novaData2 = data2.getDate();
			if(novaData1.compareTo(novaData2)>0){
				builder.append("Data de Fim não pode ser superior a Data de Inicio!\n");
				JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, builder.toString());
				return false;
			}
		}catch (Exception e) {}
		try{
			if("".equals(txHonorario.getSelectedText().trim().replace(",", "")))
				return true;
			else	
				Integer.parseInt(txHonorario.getSelectedText().trim().replace(".","").replace(",", "."));
		}catch (Exception e) {
			builder.append("O valor do honorário informado está incorreto!\n");
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, builder.toString());
			return false;
		}
		return true;
		
	}
	@SuppressWarnings("unchecked")
	private void invocarExclusao(){
    	int escolha = JOptionPane.showConfirmDialog(br.com.tiagods.view.MenuView.jDBody, "Você deseja excluir esse registro? "
				+ "\nTodos os historicos serão perdidos, lembre-se que essa ação não terá mais volta!",
				"Pedido de Exclusao", JOptionPane.YES_NO_OPTION);
		if(escolha==JOptionPane.YES_OPTION){
			NegocioDAO dao = new NegocioDAO();
			boolean openHere = recebeSessao();
			boolean excluiu = dao.excluir(negocio,session);
			fechaSessao(openHere);
			if(excluiu){
				limparFormulario(pnPrincipal);
				openHere = recebeSessao();
				listarNegocios = (List<Negocio>)dao.listar(Negocio.class, session);
		    	preencherTabela(listarNegocios, tbPrincipal, txContador);
		    	fechaSessao(openHere);
			}
		}
    }
	private void salvarCancelar(){
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		desbloquerFormulario(false, pnPrincipal);
		if(negocio!=null){
			negocio=negocioBackup;
			//preencherFormulario(negocio);
		}
		if("".equals(txCodigo.getText()))
			btnExcluir.setEnabled(false);
	}
	private void pesquisar(){
		List<Negocio> lista = new ArrayList<>();
		for(int i =0;i<listarNegocios.size();i++){
			String texto = txBuscar.getText().trim().toUpperCase();
			if(listarNegocios.get(i).getNome().substring(0,texto.length()).equalsIgnoreCase(texto)){
				lista.add(listarNegocios.get(i));
			}
		}
		if(!lista.isEmpty()){
			preencherTabela(lista, tbPrincipal,txContador);
		}else{
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody,"Não foi encontrado registros com o critério informado",
					"Nenhum registro!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private void novoEditar(){
		desbloquerFormulario(true, pnPrincipal);
		btnNovo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnExcluir.setEnabled(false);
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
				limparFormulario((Container)c);
			}	
		}
		txCodigo.setText("");
		txCadastradoPor.setText("");
		txDataCadastro.setText("");
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
				desbloquerFormulario(desbloquear,(Container)c);
			}	
		}
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
	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	private void preencherTabela(List<Negocio> list, JTable table, JLabel txContadorRegistros){
		List<Negocio> lista = list;
		String [] tableHeader = {"ID","NOME","STATUS","ETAPA","ORIGEM","NIVEL","TEMPO","CRIADO EM","ATENDENTE","ABRIR"};
		DefaultTableModel model = new DefaultTableModel(tableHeader,0){
			boolean[] canEdit = new boolean[]{
					false,false,false,false,false,false,false
			};
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		};
		if(!lista.isEmpty()){
			for(int i=0;i<lista.size();i++){
				Negocio n= lista.get(i);
				PfPj fj = n.getPessoaFisicaOrJuridica();
				Object[] linha = new Object[7];
				linha[0] = ""+n.getId();
				linha[1] = n.getNome();
				linha[2] = n.getStatus()==null?"":n.getStatus().getNome();
				linha[3] = n.getEtapa()==null?"":n.getEtapa().getNome();
				
				linha[4] = fj.getOrigem()==null?"":fj.getOrigem().getNome();
				linha[5] = fj.getNivel()==null?"":fj.getNivel().getNome();
				linha[6] = "";
				try{
					Date criadoEm = n.getCriadoEm();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					linha[7] = sdf.format(criadoEm);
				}catch (NumberFormatException e) {
					linha[7] = "";
				}
				if(n.getStatus().getNome().equals("Em Andamento") && n.getDataInicio().compareTo(new Date())>0){
					linha[6]=n.getDataInicio().compareTo(new Date())+" dias atrasados";
				}
				linha[8] = n.getAtendente()==null?"":n.getAtendente().getLogin();
				linha[9] = n.getClasse();
				model.addRow(linha);
			}
		}
		txContadorRegistros.setText("Total: "+lista.size()+" registro(s)s");
		table.setModel(model);
		table.setAutoCreateRowSorter(true);
		table.setSelectionBackground(Color.CYAN);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED){
				txCodObjeto.setText("");
				txCodObjeto.setBackground(new Color(250,250,250));
				txNomeObjeto.setText("");
				txNomeObjeto.setBackground(new Color(250,250,250));
				cbCategoriaCad.setSelectedItem("");
				cbOrigemCad.setSelectedItem("");
				cbNivelCad.setSelectedItem("");
				cbServicosCad.setSelectedItem("");
		}
	}

	
}
