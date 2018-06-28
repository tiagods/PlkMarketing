package br.com.tiagods.repository;

public class Paginacao {
	int totalPaginas = 0;
	long totalRegistros = 0;
	int paginaAtual = 0;// inicia por zero
	int limitePorPagina = 0;
	int primeiroRegistro = 0;

	public Paginacao(int limitePorPagina) {
		this.limitePorPagina=limitePorPagina;
	}
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
		this.totalPaginas = (int) (totalRegistros / limitePorPagina);
		if (totalRegistros % limitePorPagina > 0)
			this.totalPaginas++;
	}
	public int getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;
		if (paginaAtual > 0) {
			this.primeiroRegistro = paginaAtual * limitePorPagina;
		} else
			this.primeiroRegistro = 0;
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
