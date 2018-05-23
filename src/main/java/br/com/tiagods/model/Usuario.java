package br.com.tiagods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Usuario extends Pessoa implements AbstractEntity,Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String login="";
	private String senha="";
	@Column(name="ultimo_acesso")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar ultimoAcesso;
	@Transient
	private int totalTarefas=0;
	@Transient
	private int totalTarefasPendentes=0;
	@Transient
	private int totalEmpresas=0;
	@Transient
	private int totalPessoas=0;
	@Transient
	private int totalNegocios=0;
	
	@ManyToOne 
	@JoinColumn(name="departamento_id")
	private UsuarioDepartamento departamento;
	@ManyToOne 
	@JoinColumn(name="funcao_id")
	private Funcao funcao;
	@Column(name="total_vendas")
	private BigDecimal totalVendas;
	private int ativo=1;
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	/**
	 * @return the ultimoAcesso
	 */
	public Calendar getUltimoAcesso() {
		return ultimoAcesso;
	}
	/**
	 * @param ultimoAcesso the ultimoAcesso to set
	 */
	public void setUltimoAcesso(Calendar ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}
	/**
	 * @return the totalTarefas
	 */
	public int getTotalTarefas() {
		return totalTarefas;
	}
	/**
	 * @param totalTarefas the totalTarefas to set
	 */
	public void setTotalTarefas(int totalTarefas) {
		this.totalTarefas = totalTarefas;
	}
	/**
	 * @return the totalTarefasPendentes
	 */
	public int getTotalTarefasPendentes() {
		return totalTarefasPendentes;
	}
	/**
	 * @param totalTarefasPendentes the totalTarefasPendentes to set
	 */
	public void setTotalTarefasPendentes(int totalTarefasPendentes) {
		this.totalTarefasPendentes = totalTarefasPendentes;
	}
	/**
	 * @return the totalEmpresas
	 */
	public int getTotalEmpresas() {
		return totalEmpresas;
	}
	/**
	 * @param totalEmpresas the totalEmpresas to set
	 */
	public void setTotalEmpresas(int totalEmpresas) {
		this.totalEmpresas = totalEmpresas;
	}
	/**
	 * @return the totalPessoas
	 */
	public int getTotalPessoas() {
		return totalPessoas;
	}
	/**
	 * @param totalPessoas the totalPessoas to set
	 */
	public void setTotalPessoas(int totalPessoas) {
		this.totalPessoas = totalPessoas;
	}
	/**
	 * @return the totalNegocios
	 */
	public int getTotalNegocios() {
		return totalNegocios;
	}
	/**
	 * @param totalNegocios the totalNegocios to set
	 */
	public void setTotalNegocios(int totalNegocios) {
		this.totalNegocios = totalNegocios;
	}
	/**
	 * @return the departamento
	 */
	public UsuarioDepartamento getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(UsuarioDepartamento departamento) {
		this.departamento = departamento;
	}
	/**
	 * @return the funcao
	 */
	public Funcao getFuncao() {
		return funcao;
	}
	/**
	 * @param funcao the funcao to set
	 */
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	/**
	 * @return the totalVendas
	 */
	public BigDecimal getTotalVendas() {
		return totalVendas;
	}
	/**
	 * @param totalVendas the totalVendas to set
	 */
	public void setTotalVendas(BigDecimal totalVendas) {
		this.totalVendas = totalVendas;
	}
	/**
	 * @return the ativo
	 */
	public int getAtivo() {
		return ativo;
	}
	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.login;
	}
}
