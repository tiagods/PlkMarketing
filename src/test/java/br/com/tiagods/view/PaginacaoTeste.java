package br.com.tiagods.view;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import br.com.tiagods.controller.PersistenciaController;
import br.com.tiagods.model.NegocioTarefa;
import javafx.util.Pair;

public class PaginacaoTeste extends PersistenciaController{
	public class Paginacao {
		int totalPaginas=0;
		long totalRegistros=0;
		int paginaAtual=0;//inicia por zero
		int limitePorPagina=0;
		int primeiroRegistro=0;
		
		public int getTotalPaginas() {
			return totalPaginas;
		}
		public void setTotalPaginas(int totalPaginas) {
			this.totalPaginas = totalPaginas;
		}
		public long getTotalRegistros() {
			return totalRegistros;
		}
		public void setTotalRegistros(long totalRegistros) {
			this.totalRegistros = totalRegistros;
			this.totalPaginas = (int) (totalRegistros/limitePorPagina);
			if(totalRegistros%limitePorPagina>0) this.totalPaginas++;
		}
		public int getPaginaAtual() {
			return paginaAtual;
		}
		public void setPaginaAtual(int paginaAtual) {
			this.paginaAtual = paginaAtual;
			if(paginaAtual>0) {
				this.primeiroRegistro=paginaAtual*limitePorPagina;
			}
			else
				this.primeiroRegistro=0;
		}
		public int getLimitePorPagina() {
			return limitePorPagina;
		}
		public void setLimitePorPagina(int limitePorPagina) {
			this.limitePorPagina = limitePorPagina;
		}
		public int getPrimeiroRegistro() {
			return primeiroRegistro;
		}
		public void setPrimeiroRegistro(int primeiroRegistro) {
			this.primeiroRegistro = primeiroRegistro;
		}
		
	}
	public static void main(String[] args) {
		new PaginacaoTeste().executar();;
		
	}
	
	
	public void executar() {
		Paginacao pag = new Paginacao();
		pag.setLimitePorPagina(100);
		try {
			loadFactory();
			for(int i =0;i<10;i++) {
				pag.setPaginaAtual(i);
				Pair<List<NegocioTarefa>,Paginacao> pair = listar(pag);
				pag = pair.getValue();
				System.out.println(""+"\tTotal Pages: "+pag.getTotalPaginas()+" \tPage: "+pag.getPaginaAtual()+" \t First: "+pag.getPrimeiroRegistro());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
		
	}
	public Pair<List<NegocioTarefa>,Paginacao> listar(Paginacao pag) {
		Criteria criteria = getManager().unwrap(Session.class).createCriteria(NegocioTarefa.class);
		criteria.setFirstResult(pag.getPrimeiroRegistro());
		criteria.setMaxResults(pag.getLimitePorPagina());
		List<NegocioTarefa> firstPage = criteria.list();

		criteria = getManager().unwrap(Session.class).createCriteria(NegocioTarefa.class);
		criteria.setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();
		pag.setTotalRegistros(count);
		return new Pair<>(firstPage, pag);
	}
	
}
