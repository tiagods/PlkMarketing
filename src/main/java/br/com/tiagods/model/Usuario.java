package br.com.tiagods.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.util.MyStringUtil;

@Entity
public class Usuario extends Pessoa implements Serializable, AbstractEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String senha = "";
	@Column(name = "ultimo_acesso")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar ultimoAcesso;

	@ManyToOne
	@JoinColumn(name = "departamento_id")
	private Departamento departamento;
	@ManyToOne
	@JoinColumn(name = "funcao_id")
	private UsuarioFuncao funcao;
	private int ativo = 1;
	@Embedded
	private PessoaFisica fisica;

	@Column(name = "senha_resetada")
	private boolean senhaResetada = false;

	@Transient
	private String nomeResumido;

	public Usuario() {
	}

	public Usuario(long id,String nomeResumido) {
		this.id = id;
		this.nomeResumido = nomeResumido;
	}
	@PostLoad
	void onLoad(){
		String[] newName = getNome().split(" ");
		String newName2 = newName.length>=2?newName[0]+" "+newName[1]:getNome();
		if(newName.length>2 && MyStringUtil.onList(newName[1])) newName2+=" "+newName[2];
		this.nomeResumido=newName2;
	}

	@PrePersist
	void persist() {
		setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		setCriadoEm(Calendar.getInstance());
	}

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
	 * @return the departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the funcao
	 */
	public UsuarioFuncao getFuncao() {
		return funcao;
	}

	/**
	 * @param funcao the funcao to set
	 */
	public void setFuncao(UsuarioFuncao funcao) {
		this.funcao = funcao;
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

	public PessoaFisica getFisica() {
		return fisica;
	}

	public void setFisica(PessoaFisica fisica) {
		this.fisica = fisica;
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
		return getNomeResumido();
	}

	public boolean isSenhaResetada() {
		return senhaResetada;
	}

	public void setSenhaResetada(boolean senhaResetada) {
		this.senhaResetada = senhaResetada;
	}

    public String getNomeResumido() {
		return nomeResumido;
    }
}