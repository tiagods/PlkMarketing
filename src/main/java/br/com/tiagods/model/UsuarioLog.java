package br.com.tiagods.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="USUARIO_LOG")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioLog implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USU_LOG_COD")
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="USU_LOG_DATA")
	private Calendar data;
	@ManyToOne
	@JoinColumn(name="USU_LOG_USUARIO_ID")
	private Usuario usuario;
	@Column(name="USU_LOG_MENU")
	private String menu;
	@Column(name="USU_LOG_ACAO")
	private String acao;
	@Column(name="USU_LOG_DESCRICAO")
	private String descricao;
	@Column(name="USU_LOG_MAQUINA")
	private String maquina;
}
