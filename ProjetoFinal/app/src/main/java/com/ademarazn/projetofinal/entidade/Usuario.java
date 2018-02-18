package com.ademarazn.projetofinal.entidade;

/**
 * Created by Ademar Zório Neto on 06/12/2017
 */

public class Usuario {

    // Constantes públicas
    public static final String TABELA = "usuarios";
    public static final String IDUSUARIO = "_idusuario";
    public static final String USUARIO = "usuario";
    public static final String SENHA = "senha";
    public static final String EMAIL = "email";
    public static final String CPF = "cpf";
    public static final String RG = "rg";
    public static final String PASSAPORTE = "passaporte";
    public static final String FKPESSOA = "fkpessoa";

    // Atributos
    private Long idUsuario;
    private String usuario;
    private String senha;
    private String email;
    private String cpf;
    private String rg;
    private String passaporte;
    private Long fkPessoa;

    // Método construtor default
    public Usuario() {
    }

    // Métodos getters and setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(String passaporte) {
        this.passaporte = passaporte;
    }

    public Long getFkPessoa() {
        return fkPessoa;
    }

    public void setFkPessoa(Long fkPessoa) {
        this.fkPessoa = fkPessoa;
    }

    @Override
    public String toString() {
        return usuario + " : " + fkPessoa;
    }
} // Fim da classe
