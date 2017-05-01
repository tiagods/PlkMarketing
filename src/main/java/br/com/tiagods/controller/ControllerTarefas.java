/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import static br.com.tiagods.view.TarefasView.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Empresa;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.model.Pessoa;
import br.com.tiagods.model.Prospeccao;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modeldao.GenericDao;
import br.com.tiagods.modeldao.TarefaDao;
import br.com.tiagods.modeldao.UsuarioDao;
import br.com.tiagods.modeldao.UsuarioLogado;
import br.com.tiagods.view.EmpresasView;
import br.com.tiagods.view.LoadingView;
import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.NegociosView;
import br.com.tiagods.view.PessoasView;
import br.com.tiagods.view.ProspeccaoView;
import br.com.tiagods.view.TarefasSaveView;
import br.com.tiagods.view.TarefasView;
import br.com.tiagods.view.interfaces.ButtonColumnModel;
import br.com.tiagods.view.interfaces.SemRegistrosJTable;
/*
 * @author Tiago
 */
public class ControllerTarefas implements ActionListener, MouseListener,PropertyChangeListener,ItemListener {
	Calendar data1;
	Calendar data2;
	Usuario userSessao;
	Session session;
	int finalizado=0;

	Set<TipoTarefa> tipoTarefas =  new HashSet<>();
	Map<String,Usuario> atendentes = new HashMap<>();
	Map<String,TipoTarefa> tipoTarefasMapa = new HashMap<>();
	
	List<Tarefa> listaTarefas;
	
	String pendente = "Aberto";
	String fechado = "Finalizado";
	
	GenericDao dao = new GenericDao();
	
	@SuppressWarnings("unchecked")
	public void iniciar(Date data1, Date data2, Usuario usuario){
		this.userSessao=usuario;
		ativarBotao(ckVisita);
		ativarBotao(ckReuniao);
		ativarBotao(ckProposta);
		ativarBotao(ckTelefone);
		ativarBotao(ckEmail);
		ckPendentes.setSelected(true);
		try{
			jData1.setDate(data1);
			jData2.setDate(data2);
		}catch(Exception e){
		}
		boolean hoje = verificarSeHoje(data1,data2);
		if(hoje){
			rbHoje.setSelected(true);
			mostrarDatas(pnData, false);
		}
		else{
			rbDefinirData.setSelected(true);
			mostrarDatas(pnData, true);
		}
		session = HibernateFactory.getSession();
		carregarAtendentes();
		cbAtendentes.setSelectedItem(usuario.getLogin());
		carregarTipoTarefas();
		
		List<Criterion> criterion = new ArrayList<>();
		Criterion criterio =  Restrictions.eq("atendente", usuario);
		Criterion criterio2 = Restrictions.between("dataEvento", jData1.getDate(), jData2.getDate());
		Criterion criterio3 = Restrictions.eq("finalizado", 0);
		criterion.add(criterio);
		criterion.add(criterio2);
		criterion.add(criterio3);
		Order order = Order.desc("id");
		listaTarefas = (List<Tarefa>)(new GenericDao().items(Tarefa.class, session, criterion, order));
		preencherTabela(tbPrincipal, listaTarefas, txContador);
		session.close();
		definirAcoes();
		try{
			setarIcons();
		}catch (NullPointerException e) {
		}

		LoadingView loading = LoadingView.getInstance();
		loading.fechar();
	}
	private void definirAcoes(){
		jData1.addPropertyChangeListener(this);
		jData2.addPropertyChangeListener(this);
		cbAtendentes.addItemListener(this);
	}
	public void ativarBotao(JCheckBox radio){
		radio.setSelected(true);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(validarDatas())
			buscar();
			
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.DESELECTED){
			buscar();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() instanceof JTable && tbPrincipal.getSelectedRow()>=0 && tbPrincipal.getSelectedColumn()==6){
//			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, 
//					tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), tbPrincipal.getSelectedColumn()), 
//					"Detalhes", 
//					JOptionPane.DEFAULT_OPTION);
		}
		else if(e.getComponent() instanceof JCheckBox){
			JCheckBox ck = (JCheckBox)e.getComponent();
			if(ck.isSelected()){
				tipoTarefas.add(tipoTarefasMapa.get(ck.getName()));
				buscar();
//				ck.setOpaque(true);
//				ck.setBackground(Color.orange);
			}
			else{
				tipoTarefas.remove(tipoTarefasMapa.get(ck.getName()));
				buscar();
//				ck.setOpaque(false);
			}
		}

	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "CriarTarefa":
			TarefasSaveView view = new TarefasSaveView(null,null,MenuView.getInstance(),true);
			view.setVisible(true);
			break;
		case "Status":
			buscar();
			break;
		case "Prazo":
			boolean value =false;
			if(rbDefinirData.isSelected()){
				value = true;
			}
			mostrarDatas(pnData,value);
			if(rbDefinirData.isSelected()){
				jData1.setDate(new Date());
				jData2.setDate(new Date());
			}
			buscar();
			break;
		default:
			break;
		}
		
	}
	@SuppressWarnings("unchecked")
	private void carregarAtendentes(){
		List<Usuario> lista = new UsuarioDao().listar(Usuario.class,session);
		cbAtendentes.removeAllItems();
		cbAtendentes.addItem("Todos");
		Set<String> arvore = new TreeSet<>();
		Iterator<Usuario> iterator = lista.listIterator();
		while(iterator.hasNext()){
			Usuario u = iterator.next();
			arvore.add(u.getLogin());
			atendentes.put(u.getLogin(), u);
		}
		arvore.forEach(c->{cbAtendentes.addItem(c);});
	}
	@SuppressWarnings("unchecked")
	private void carregarTipoTarefas(){
		List<TipoTarefa> lista = (List<TipoTarefa>)dao.listar(TipoTarefa.class, session);
		lista.forEach(c->{
			tipoTarefas.add(c);
			tipoTarefasMapa.put(c.getNome(), c);
		});
	}
	@SuppressWarnings("unchecked")
	public boolean buscar(){
		List<Criterion> criterios = new ArrayList<>();
		if(!tipoTarefas.isEmpty()){
			Criterion criterion = Restrictions.in("tipoTarefa", tipoTarefas.toArray());
			criterios.add(criterion);
		}
		else{
			Criterion criterion = Restrictions.isNull("tipoTarefa");
			criterios.add(criterion);
		}
		if(rbHoje.isSelected()){
			Date hoje = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(hoje);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(hoje);
			calendar2.set(Calendar.HOUR_OF_DAY, 23);
			calendar2.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			
			Criterion criterion = Restrictions.between("dataEvento",calendar.getTime(), calendar2.getTime());
			criterios.add(criterion);
		}
		else if(rbEssaSemana.isSelected()){
			receberDatasSemana();
			Criterion criterion = Restrictions.between("dataEvento", data1.getTime(),data2.getTime());
			criterios.add(criterion);
		}
		else if(rbDefinirData.isSelected() && validarDatas()){
			data2.setTime(jData2.getDate());
			data2.set(Calendar.HOUR_OF_DAY,23);
			data2.set(Calendar.MINUTE, 59);
			data2.set(Calendar.SECOND, 59);
			Criterion criterion = Restrictions.between("dataEvento", jData1.getDate(), data2.getTime());
			criterios.add(criterion);
		}
		else if(!validarDatas()){
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody,
					"Data informada esta incorreta, \nVerifique o valor informado e tente novamente",
					"Erro na data!",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(ckPendentes.isSelected() && !ckFinalizados.isSelected()){
			Criterion criterion = Restrictions.eq("finalizado", 0);
			criterios.add(criterion);
		}
		else if(!ckPendentes.isSelected() && ckFinalizados.isSelected()){
			Criterion criterion = Restrictions.eq("finalizado", 1);
			criterios.add(criterion);
		}
		else
			ckPendentes.setSelected(true);
		
		if(!"Todos".equalsIgnoreCase((String)cbAtendentes.getSelectedItem())){
			Criterion criterion = Restrictions.eq("atendente", atendentes.get(cbAtendentes.getSelectedItem()));
			criterios.add(criterion);
		}
		session = HibernateFactory.getSession();
		session.beginTransaction();
		Order order = Order.desc("dataEvento");
		listaTarefas = (List<Tarefa>)(new GenericDao().items(Tarefa.class, session, criterios, order));
		preencherTabela(tbPrincipal, listaTarefas, txContador);
		tbPrincipal.addMouseListener(this);
		session.close();
		return true;
	}
	
	private void receberDatasSemana(){
		LocalDate dataHoje = LocalDate.now();
		DayOfWeek day = dataHoje.getDayOfWeek();
		switch(day.getValue()){
		case 1:
			processarSemana(1,7);
			break;
		case 2:
			processarSemana(0,6);
			break;
		case 3:
			processarSemana(-1,5);
			break;
		case 4:
			processarSemana(-2,4);
			break;
		case 5:
			processarSemana(-3,3);
			break;
		case 6:
			processarSemana(-4,2);
			break;
		case 7:
			processarSemana(-5,1);
			break;
		default:
			break;
		}
	}
	private void processarSemana(int diaSegunda, int diaDomingo){
		LocalDate dataHoje = LocalDate.now();
		LocalDate novaDataHoje = dataHoje.plusDays(diaSegunda);
		data1 = Calendar.getInstance();
		data2 = Calendar.getInstance();
		data1.set(novaDataHoje.getYear(), novaDataHoje.getMonthValue()-1, novaDataHoje.getDayOfMonth()-1,0,0,0);
		LocalDate novaDataFimDeSemana = dataHoje.plusDays(diaDomingo);
		data2.set(novaDataFimDeSemana.getYear(), novaDataFimDeSemana.getMonthValue()-1, novaDataFimDeSemana.getDayOfMonth()-1,23,59,59);
	}
	@SuppressWarnings("serial")
	private void preencherTabela(JTable table, List<Tarefa> lista, JLabel txContador){
		if(lista.isEmpty()){
			new SemRegistrosJTable(table,"Relação de Tarefas");
		}
		else{
			String[] tableHeader = {"ID","PRAZO","ANDAMENTO","TIPO","NOME","STATUS",
					"DETALHES","ATENDENTE", "FINALIZADO","ABRIR","EDITAR","EXCLUIR"};
			DefaultTableModel model = new DefaultTableModel(tableHeader,0){
				/**
				 * 
				 */
				
				boolean[] canEdit = new boolean[]{
						false,false,false,false,false,false,true,false,true,true,true,true
				};
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit [columnIndex];
				}
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Class getColumnClass(int columnIndex) {
					return getValueAt(0, columnIndex).getClass();
				}
				
			};
			for(int i=0;i<lista.size();i++){
				Tarefa t = lista.get(i);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				
				Object[] o = new Object[12];
				o[0] = t.getId();
				o[1] = sdf.format(t.getDataEvento());
				o[2] = t.getTipoTarefa().getNome();
				o[3] = t.getClasse();
				if(Empresa.class.getSimpleName().equals(t.getClasse()))
					o[4] = t.getEmpresa()==null?"Erro: Empresa desassociada":t.getEmpresa().getNome();
				else if(Negocio.class.getSimpleName().equals(t.getClasse()))
					o[4] = t.getNegocio()==null?"Erro: Negocio desassociado":t.getNegocio().getNome();
				else if(Pessoa.class.getSimpleName().equals(t.getClasse()))
					o[4] = t.getPessoa()==null?"Erro: Pessoa desassociada":t.getPessoa().getNome();
				else if(Pessoa.class.getSimpleName().equals(t.getClasse()))
					o[4] = t.getProspeccao()==null?"Erro: Prospeccao desassociada":t.getProspeccao().getNome();
				else
					o[4] = "Erro";
				if(t.getFinalizado()==0)
					o[5] = pendente;
				else
					o[5] = fechado;
				o[6] = t.getDescricao();
				o[7] = t.getAtendente().getLogin();
				if(t.getFinalizado()==0)
					o[8] =Boolean.FALSE;
				else
					o[8]=Boolean.TRUE;
				
				String imageName ="";
				if("Empresa".equals(t.getClasse())){
					imageName ="button_empresas.png";
				}
				else if("Negocio".equals(t.getClasse())){
					imageName ="button_negocios.png";
				}
				else if("Prospeccao".equals(t.getClasse())){
					imageName ="button_prospeccao.png";
				}
				else
					imageName ="button_people.png";
				o[9]= recalculate(new ImageIcon(ControllerTarefas.class
						.getResource("/br/com/tiagods/utilitarios/"+imageName)));
				o[10] = recalculate(new ImageIcon(ControllerTarefas.class
						.getResource("/br/com/tiagods/utilitarios/button_edit.png")));//"Editar";
				o[11] = recalculate(new ImageIcon(ControllerTarefas.class
						.getResource("/br/com/tiagods/utilitarios/button_trash.png")));//"Excluir";
				model.addRow(o);
			}
			table.setModel(model);
			
			JCheckBox ckFinalize = new JCheckBox();
			TableColumn col = table.getColumnModel().getColumn(8);
			col.setCellEditor(new DefaultCellEditor(ckFinalize));
			ckFinalize.setActionCommand("Finalizar");
			ckFinalize.addActionListener(new AcaoInTable());
			
//			TableCellRenderer tcv = new JTableCelulaColor(ckFinalize.isSelected());
//			TableColumn column1 = table.getColumnModel().getColumn(1);
//			column1.setCellRenderer(tcv);
			
			table.getColumnModel().getColumn(6).setCellRenderer(new TextAreaRenderer());
			table.getColumnModel().getColumn(6).setCellEditor(new TextAreaEditor());
			table.setRowHeight(40);
			
			JButton btAbrir = new ButtonColumnModel(table,9).getButton();
			btAbrir.setActionCommand("Abrir");
			btAbrir.addActionListener(new AcaoInTable());

			JButton btEdit = new ButtonColumnModel(table,10).getButton();
			btEdit.setActionCommand("Editar");
			btEdit.addActionListener(new AcaoInTable());

			JButton btExcluir = new ButtonColumnModel(table,11).getButton();
			btExcluir.setActionCommand("Excluir");
			btExcluir.addActionListener(new AcaoInTable());
			
			table.setAutoCreateRowSorter(true);
			table.setSelectionBackground(Color.orange);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
//			table.getColumnModel().getColumn(0).setMaxWidth(0);
//			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(6).setPreferredWidth(100);
			table.getColumnModel().getColumn(9).setMaxWidth(40);
			table.getColumnModel().getColumn(10).setMaxWidth(40);
			table.getColumnModel().getColumn(11).setMaxWidth(40);

		}
		txContador.setText("Total: "+lista.size()+" tarefa(s) ");
	}
	public class AcaoInTable implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			session = HibernateFactory.getSession();
			session.beginTransaction();
			switch(e.getActionCommand()){
			case "Finalizar":
				finalizar(session);
				break;
			case "Abrir":
				abrirCadastro(session);
				break;
			case "Editar":
				int valor = (int) tbPrincipal.getModel().getValueAt(tbPrincipal.getSelectedRow(), 0);
				Tarefa tarefa = (Tarefa)new TarefaDao().receberObjeto(Tarefa.class, valor, session);
				TarefasSaveView viewTarefas = new TarefasSaveView(tarefa,null,MenuView.getInstance(),true);
				viewTarefas.setVisible(true);
				break;
			case "Excluir":
				excluir(session);
				break;
			default:
				break;
			}
			session.close();
		}
		
	}
	public void finalizar(Session session){
		boolean value = (boolean)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 8);
		int id = (int)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0);
		String status = (String)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5);

		if(!value && pendente.equals(status)){
			TarefaDao dao = new TarefaDao();
			Tarefa thisTar = (Tarefa) dao.receberObjeto(Tarefa.class, id, session);
			thisTar.setFinalizado(1);
			if(dao.salvar(thisTar, session)){
				tbPrincipal.setValueAt(fechado, tbPrincipal.getSelectedRow(), 5);
			}

		}
		else if(value && fechado.equals(status)){
			TarefaDao dao = new TarefaDao();
			Tarefa thisTar = (Tarefa) dao.receberObjeto(Tarefa.class, id, session);
			thisTar.setFinalizado(0);
			if(dao.salvar(thisTar, session)){
				tbPrincipal.setValueAt(pendente, tbPrincipal.getSelectedRow(), 5);
			}
		}
		buscar();
	}
	public void excluir(Session session){
		int row = tbPrincipal.getSelectedRow();
		int i = JOptionPane.showConfirmDialog(MenuView.jDBody, 
				"Deseja excluir a seguinte tarefa: "+tbPrincipal.getValueAt(row, 0)+" andamento: "+tbPrincipal.getValueAt(row, 2)+
				" relacionado a "+tbPrincipal.getValueAt(row, 3)+" de nome:"+tbPrincipal.getValueAt(row, 4)+
				" com status :"+tbPrincipal.getValueAt(row, 5)+"?","Pedido de remoção!",JOptionPane.YES_NO_OPTION);
		if(i==JOptionPane.OK_OPTION){
			TarefaDao dao = new TarefaDao();
			int id = (int)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0);
			Tarefa tRemove = (Tarefa)dao.receberObjeto(Tarefa.class, id, session);
			if(tRemove.getCriadoPor()== UsuarioLogado.getInstance().getUsuario()){
				if(dao.excluir(tRemove, session))
					buscar();
			}
			else{
				JOptionPane.showConfirmDialog(MenuView.jDBody, "Apenas o criador da Tarefa ("+tRemove.getCriadoPor().getNome()+")pode excluir esse registro",
						"Acesso não permitido!",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	@SuppressWarnings("static-access")
	public void abrirCadastro(Session session){
		String value = (String)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3);
		int id = (int)tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0);
		Tarefa transfer = (Tarefa) new TarefaDao().receberObjeto(Tarefa.class, id, session);
		if("Empresa".equals(value)){
			Empresa empresa = transfer.getEmpresa();
			EmpresasView viewEmpresas = new EmpresasView(empresa);
			ControllerMenu.getInstance().abrirCorpo(viewEmpresas);
		}
		else if("Negocio".equals(value)){
			Negocio negocio = transfer.getNegocio();
			NegociosView viewNegocios = new NegociosView(negocio);
			ControllerMenu.getInstance().abrirCorpo(viewNegocios);
		}
		else if("Pessoa".equals(value)){
			Pessoa pessoa = transfer.getPessoa();
			PessoasView viewPessoa = new PessoasView(pessoa);
			ControllerMenu.getInstance().abrirCorpo(viewPessoa);
		}
		else if("Prospeccao".equals(value)){
			Prospeccao prospeccao = transfer.getProspeccao();
			ProspeccaoView viewProspeccao = new ProspeccaoView(prospeccao);
			ControllerMenu.getInstance().abrirCorpo(viewProspeccao);
		}
	}
	private boolean validarDatas(){
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try{
			calendar.setTime(jData1.getDate());
			calendar2.setTime(jData2.getDate());
			return calendar2.compareTo(calendar)>=0;
		}catch(NullPointerException e){
			e.printStackTrace();
			return false;
		}
	}
	private boolean verificarSeHoje(Date data1,Date data2){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data1).equals(sdf.format(new Date())) && sdf.format(data1).equals(sdf.format(data2));
	}
	public void mostrarDatas(JPanel panel, boolean esconder){
		panel.setVisible(esconder);
	}
	
	public class TextAreaRenderer extends JScrollPane implements TableCellRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextArea textarea;

		public TextAreaRenderer() {
			textarea = new JTextArea();
			textarea.setLineWrap(true);
			textarea.setWrapStyleWord(true);
			//textarea.setBorder(new TitledBorder("This is a JTextArea"));
			getViewport().add(textarea);
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus,
				int row, int column)
		{
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
				textarea.setForeground(table.getSelectionForeground());
				textarea.setBackground(table.getSelectionBackground());
			}else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
				textarea.setForeground(table.getForeground());
				textarea.setBackground(table.getBackground());
			}
			textarea.setText((String) value); 
			textarea.setCaretPosition(0);
			return this;
		}
	}
	public class TextAreaEditor extends DefaultCellEditor {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8056096378619799509L;
		protected JScrollPane scrollpane;
		protected JTextArea textarea; 

		public TextAreaEditor() {
			super(new JCheckBox());
			scrollpane = new JScrollPane();
			textarea = new JTextArea();
			textarea.setLineWrap(true);
			textarea.setWrapStyleWord(true);
			//textarea.setBorder(new TitledBorder("This is a JTextArea"));
			scrollpane.getViewport().add(textarea);
		}

		public Component getTableCellEditorComponent(JTable table, Object value,
				boolean isSelected, int row, int column) {
			textarea.setText((String) value);

			return scrollpane;
		}

		public Object getCellEditorValue() {
			return textarea.getText();
		}
	}
	public void setarIcons() throws NullPointerException {
		ImageIcon iconEmail = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_email.png"));
        ckEmail.setIcon(recalculate(iconEmail));
        ckEmail.setBorderPainted(true);
        ImageIcon iconProposta = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_proposta.png"));
        ckProposta.setIcon(recalculate(iconProposta));
        ckProposta.setBorderPainted(true);
        ImageIcon iconReuniao = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_reuniao.png"));
        ckReuniao.setIcon(recalculate(iconReuniao));
        ckReuniao.setBorderPainted(true);
        ImageIcon iconPhone = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_fone.png"));
        ckTelefone.setIcon(recalculate(iconPhone));
        ckTelefone.setBorderPainted(true);
        ImageIcon iconVisita = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_visita.png"));
        ckVisita.setIcon(recalculate(iconVisita));  
        ckVisita.setBorderPainted(true);
        
        ImageIcon iconTaskNew = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/button_add.png"));
        btNovaTarefa.setIcon(recalculate(iconTaskNew));
        
        ImageIcon iconFin = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/ok.png"));
        ckFinalizados.setIcon(recalculate(iconFin));
        ckFinalizados.setBorderPainted(true);
        ImageIcon iconPe = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/no.png"));
        ckPendentes.setIcon(recalculate(iconPe));
        ckPendentes.setBorderPainted(true);
        ImageIcon iconTudo = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_tudo.png"));
        rbTudo.setIcon(recalculate(iconTudo));
        rbTudo.setBorderPainted(true);
        ImageIcon iconHoje = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_hoje.png"));
        rbHoje.setIcon(recalculate(iconHoje));
        rbHoje.setBorderPainted(true);
        ImageIcon iconSemana = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_semana.png"));
        rbEssaSemana.setIcon(recalculate(iconSemana));
        rbEssaSemana.setBorderPainted(true);
        ImageIcon iconDefinir = new ImageIcon(TarefasView.class.getResource("/br/com/tiagods/utilitarios/tarefas_person.png"));
        rbDefinirData.setIcon(recalculate(iconDefinir));
        rbDefinirData.setBorderPainted(true);
        
        
	}
	public ImageIcon recalculate(ImageIcon icon) throws NullPointerException{
    	icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()/2, icon.getIconHeight()/2, 100));
    	return icon;
    }
	class Colorir extends JLabel implements TableCellRenderer{
        /**
		 *
		 */
		boolean valor;
		private static final long serialVersionUID = 3906288238715470468L;
		public Colorir(boolean valor){
            this.setOpaque(true);
            this.valor = valor;
        }

        public Component getTableCellRendererComponent(
            JTable table,
            Object value, boolean isSelected, boolean hasFocus,
               int row, int column){

        	if(valor==false){
        		if(value.toString().equals("Perdido")){
	                setBackground(Color.RED);
	                setForeground(Color.WHITE);
	            }
	            else if(value.toString().equals("Ganho")){
	                setBackground(Color.GREEN);
	                setForeground(Color.black);
	            }
	        }
            else{
            	setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
    	
            setFont(table.getFont());
            setText(value.toString());
            return this;
        }

      public void validate() {}
      public void revalidate() {}
      protected void firePropertyChange(String propertyName,
         Object oldValue, Object newValue) {}
      public void firePropertyChange(String propertyName,
         boolean oldValue, boolean newValue) {}
    }
}
