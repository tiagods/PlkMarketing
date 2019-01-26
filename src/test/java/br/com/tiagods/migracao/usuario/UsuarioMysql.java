package br.com.tiagods.migracao.usuario;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Transient;

    /**
     *
     * @author Tiago
     */
    //table login
    public class UsuarioMysql implements Serializable{
    private int cod;
    private String usuario;
    private String senha;
    private String departamento;
    private int mensagem;
    private int mensagemDepartamento;
    private String email;
    private String nome;
    private int nivel;
    private int ativo;
    private int codigoAnterior;
    /**
     * @return the cod
     */
    public int getCod() {
        return cod;
    }

    /**
     * @param cod the cod to set
     */
    public void setCod(int cod) {
        this.cod = cod;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the mensagem
     */
    public int getMensagem() {
        return mensagem;
    }

    /**
     * @param mensagem the mensagem to set
     */
    public void setMensagem(int mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * @return the mensagemDepartamento
     */
    public int getMensagemDepartamento() {
        return mensagemDepartamento;
    }

    /**
     * @param mensagemDepartamento the mensagemDepartamento to set
     */
    public void setMensagemDepartamento(int mensagemDepartamento) {
        this.mensagemDepartamento = mensagemDepartamento;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
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

    @Override
    public String toString() {
        return this.usuario; //To change body of generated methods, choose Tools | Templates.
    }
    
}
