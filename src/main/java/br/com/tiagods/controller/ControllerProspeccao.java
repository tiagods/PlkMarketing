package br.com.tiagods.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.toedter.calendar.JDateChooser;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Lista;
import br.com.tiagods.model.PfPj;
import br.com.tiagods.model.Prospeccao;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.view.LoadingView;

import static br.com.tiagods.view.ProspeccaoView.*;

public class ControllerProspeccao implements ActionListener,ItemListener,MouseListener,PropertyChangeListener,KeyListener{
	private Session session = null;
	boolean telaEmEdicao = false;
	Prospeccao prospeccao;
	GenericDao dao = new GenericDao();
	List<Prospeccao> listarProspeccao = new ArrayList<Prospeccao>();
	Prospeccao prospeccaoBackup;
	AuxiliarComboBox padrao = AuxiliarComboBox.getInstance();
	
	@SuppressWarnings("unchecked")
	public void iniciar(Prospeccao prospeccao){
		this.prospeccao=prospeccao;
		boolean aberta = abrirSessao();
		JPanel[] panels = {pnPesquisa,pnCadastro,pnCadastroOrigem};
		for (JPanel panel : panels) {
			preencherComboBox(panel);
		}
		List<Criterion> criterion = new ArrayList<>();
		//criterion.add(Restrictions.eq("atendente", UsuarioLogado.getInstance().getUsuario()));  //departamento não se acostumou com a nova regra
		
		listarProspeccao = dao.items(Prospeccao.class, session, criterion, Order.desc("id"));
		preencherTabela(listarProspeccao, tbPrincipal, txContadorRegistros);
		if(this.prospeccao==null && !listarProspeccao.isEmpty()){
			this.prospeccao=listarProspeccao.get(0);
			preencherFormulario(this.prospeccao);
			tabbedPane.setSelectedIndex(0);
		}
		else if(this.prospeccao!=null){
			preencherFormulario(this.prospeccao);
			tabbedPane.setSelectedIndex(1);
		}
		fecharSessao(aberta);
		setarIcones();
		definirAcoes();
		salvarCancelar();
		LoadingView loading = LoadingView.getInstance();
		loading.fechar();
	}
	private boolean abrirSessao(){
		try{
			this.session = HibernateFactory.getSession();
			session.beginTransaction();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Editar":
			//desbloquearFormulario();
			break;
		case "Excluir":
			//invocarExclusao();
			break;
		case "Novo":
			//limpar();
			break;
		default:
			break;
		}
		
	}
	private void definirAcoes(){
		data1.addPropertyChangeListener(this);
		data2.addPropertyChangeListener(this);
		tbPrincipal.addMouseListener(this);
		cbOrigem.addItemListener(this);
		cbServicos.addItemListener(this);
		cbOrdenacao.addItemListener(this);
		cbBuscarPor.addItemListener(this);
		rbCrescente.addItemListener(this);
		rbDecrescente.addItemListener(this);
		cbAtendente.addItemListener(this);
		txBuscar.addKeyListener(this);
		rbCrescente.addItemListener(this);
		tabbedPane.addMouseListener(this);
	}
	private void desbloquearFormulario(boolean desbloquear,Container container){
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
			else if(c instanceof JDateChooser){
				((JDateChooser)c).setEnabled(desbloquear);
			}
			else if(c instanceof JRadioButton){
				((JRadioButton)c).setEnabled(desbloquear);
			}
			else if(c instanceof JScrollPane){
				JViewport viewPort = ((JScrollPane)c).getViewport();
				desbloquearFormulario(desbloquear,(Container)viewPort);
			}
			else if(c instanceof JTabbedPane || c instanceof JPanel){
				desbloquearFormulario(desbloquear,(Container)c);
			}
		}
	}
	private void fecharSessao(boolean fechar){
		if(fechar) session.close();
	}
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
	}
	private void limparFormulario(Container container){
		for(Component c : container.getComponents()){
			if(c instanceof JScrollPane){
				JViewport viewPort = ((JScrollPane)c).getViewport();
				limparFormulario((Container)viewPort);
			}
			else if(c instanceof JTabbedPane || c instanceof JPanel){
				limparFormulario((Container)c);
			}
			else if(c instanceof JComboBox){
				((JComboBox)c).setSelectedIndex(0);
				((JComboBox)c).setSelectedItem("");
			}
			else if(c instanceof JTable){
				DefaultTableModel model = (DefaultTableModel)((JTable)c).getModel();
				while(model.getRowCount()>0){
					model.removeRow(0);
				}
				((JTable)c).setModel(model);
			}
			else if(c instanceof JTextArea){
				((JTextArea)c).setText("");
			}
			else if(c instanceof JTextField){
				((JTextField)c).setText("");
			}
			else if(c instanceof JFormattedTextField){
				((JFormattedTextField)c).setText("");
			}			
		}
		
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void novoEditar(){
		desbloquearFormulario(true, pnCadastro);
		pnAuxiliar.setVisible(true);
		btNovo.setEnabled(false);
		btEditar.setEnabled(false);
		btSalvar.setEnabled(true);
		btCancelar.setEnabled(true);
		btExcluir.setEnabled(false);
		cbAtendenteCad.setSelectedItem(UsuarioLogado.getInstance().getUsuario().getNome());
		txEmail.setEditable(false);
		if(this.prospeccao.getId()>0)
			prospeccaoBackup=prospeccao;	
	}
	private void preencherComboBox(JPanel panel){
		for(Component component : panel.getComponents()){
			if(component.getName()!=null && component instanceof JComboBox)
				padrao.preencherCombo((JComboBox<String>)component, session, null);

		}
	}
	private void preencherFormulario(Prospeccao p){
		txCodigo.setText(""+p.getId());
		txEmpresa.setText(p.getNome());
		txNomeContato.setText(p.getResponsavel());
		txDepartamento.setText(p.getDepartamento());
		
		txEndereco.setText(p.getEndereco());
		PfPj pp = p.getPfpj();  
		txTelefone.setText(pp.getTelefone());
		txCelular.setText(pp.getCelular());
		txEmail.setText(pp.getEmail());
		txSite.setText(pp.getSite());
		
		cbTipoContatoCad.setSelectedItem(p.getTipoContato().getNome());
		ckConviteEventos.setSelected(p.getConviteParaEventos()==1?true:false);
		ckMaterial.setSelected(p.getMaterial()==1?true:false);
		ckNewsletter.setSelected(p.getNewsletter()==1?true:false);
		
		cbOrigemCad.setSelectedItem(pp.getOrigem()==null?"":pp.getOrigem().getNome());
		txDetalhesDaOrigem.setText(pp.getDetalhesOrigem());
		Set<Lista> listas = p.getListas();
		preencherListas(listas);
		Set<Tarefa> tarefas = pp.getTarefas();
		preencherTarefas(tarefas);		
	}
	private void preencherListas(Set<Lista> listas){
		
	}
	private void preencherTarefas(Set<Tarefa> tarefas){
		
	}
	private void preencherTabela(List<Prospeccao> listaProspects, JTable tabela, JTextField txContador){
		
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}
	private void salvarCancelar(){
		btSalvar.setEnabled(false);
		btCancelar.setEnabled(false);
		btNovo.setEnabled(true);
		btEditar.setEnabled(true);
		btExcluir.setEnabled(true);
		if("".equals(txCodigo.getText()))
			btExcluir.setEnabled(false);
		desbloquearFormulario(false, pnCadastro);
		
	}
	public void setarIcones() throws NullPointerException{
    	ImageIcon iconNovo = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
    	btNovo.setIcon(recalculate(iconNovo));
    	btOrigemAdd.setIcon(iconNovo);
    	btTipoContatoAdd.setIcon(iconNovo);
    	ImageIcon iconEdit = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_edit.png"));
    	btEditar.setIcon(recalculate(iconEdit));
    	ImageIcon iconSave = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_save.png"));
    	btSalvar.setIcon(recalculate(iconSave));
    	ImageIcon iconCancel = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_cancel.png"));
    	btCancelar.setIcon(recalculate(iconCancel));
    	ImageIcon iconTrash = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_trash.png"));
    	btExcluir.setIcon(recalculate(iconTrash));

    	ImageIcon iconNewTask = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_addtask.png"));
    	btNovaTarefa.setIcon(recalculate(iconNewTask));
    	ImageIcon iconTask = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_task.png"));
    	btHistorico.setIcon(recalculate(iconTask));

    	ImageIcon iconEsconder = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_nofixar.png"));
    	btEsconder.setIcon(recalculate(iconEsconder));

        ImageIcon iconMail = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_mail.png"));
    	btEmail.setIcon(recalculate(iconMail));
    	ImageIcon iconURL = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_chrome.png"));
    	btSite.setIcon(recalculate(iconURL));

//    	ImageIcon iconImp = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_import.png"));
//    	btnImportar.setIcon(recalculate(iconImp));
//
//    	ImageIcon iconExp = new ImageIcon(ControllerProspeccao.class.getResource("/br/com/tiagods/utilitarios/button_export.png"));
//    	btnExportar.setIcon(recalculate(iconExp));
    }
    public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
    public ImageIcon recalculate(ImageIcon icon, int valor) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()-valor, icon.getIconHeight()-valor, 100));
    	return icon;
    }
	
	
}
