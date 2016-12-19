/*
 * Todos direitos reservados a Tiago Dias de Souza.
 */
package br.com.tiagods.controller;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.hql.internal.ast.tree.ComponentJoin.ComponentFromElementType;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Negocio;
import br.com.tiagods.modelDAO.NegocioDAO;

import static br.com.tiagods.view.NegociosView.*;

/**
 *
 * @author Tiago
 */
public class ControllerNegocios {

	Session session = null;
	Negocio negocio = null;
	PadraoMap padrao = new PadraoMap();
	@SuppressWarnings("unchecked")
	public void iniciar(Negocio negocio){
		this.negocio=negocio;
		session = HibernateFactory.getSession();
		session.beginTransaction();

		JPanel[] panels = {pnPrincipal,pnAndamento,pnCadastro};
		for (JPanel panel : panels) {
			for(Component component : panel.getComponents()){
				if(component instanceof JComboBox)
					padrao.preencherCombo((JComboBox<String>)component, session, null);
				
			}
		}
		List<Negocio> lista = (List<Negocio>)new NegocioDAO().listar(Negocio.class, session);
		preencherTabela(lista, tbNegocios, new JLabel());
		session.close();
    }

	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	private void preencherTabela(List<Negocio> list, JTable table, JLabel txContadorRegistros){
		List<Negocio> lista = list;
		String[] tableHeader = {"ID","NOME","ETAPA","STATUS","ORIGEM","CRIADO EM","ATENDENTE"};

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
				Object[] linha = new Object[7];
				linha[0] = ""+n.getId();
				linha[1] = n.getNome();
				linha[2] = n.getEtapa()==null?"":n.getEtapa().getNome();
				linha[3] = n.getStatus()==null?"":n.getStatus().getNome();
				linha[4] = n.getOrigem()==null?"":n.getOrigem().getNome();
				try{
					Date criadoEm = n.getCriadoEm();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					linha[5] = sdf.format(criadoEm);
				}catch (NumberFormatException e) {
					linha[5] = "";
				}
				linha[6] = n.getAtendente()==null?"":n.getAtendente().getLogin();
				model.addRow(linha);
			}
		}
		txContadorRegistros.setText("Total: "+lista.size()+" registros");
		table.setModel(model);
		table.setAutoCreateRowSorter(true);
		table.setSelectionBackground(Color.CYAN);
	}
}
