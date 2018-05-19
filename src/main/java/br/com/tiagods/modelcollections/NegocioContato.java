package br.com.tiagods.modelcollections;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.com.tiagods.model.PessoaFisica;
import br.com.tiagods.model.PessoaJuridica;
import br.com.tiagods.model.Tarefa;

//@Entity
//@Table(name="negocio_contato"
public class NegocioContato extends Pessoa{
	public enum Tipo{
		FISICA,JURIDICA
	}
	private Tipo tipo;
	
	private PessoaFisica pessoaFisica;
	private PessoaJuridica pessoaJuridica;
	
	//@ManyToOne
	//@JoinColumn(name = "PES_ULT_NEGOCIO_COD")
	
	private NegocioProposta ultimoNegocio;
	private Long id;
	//@Embedded
	private NegocioPadrao padrao;
	//@Embedded
	private Pessoa pessoa;
	//private Set<NegocioPessoa> pessoas = new LinkedHashSet<>();
	private Set<NegocioProposta> negocios = new LinkedHashSet<>();
	private Set<Tarefa> tarefas = new LinkedHashSet<>();
}
