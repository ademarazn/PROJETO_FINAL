package com.ademarazn.projetofinal.entidade;

import java.util.Date;

/**
 * Created by Ademar Zório Neto on 07/12/2017
 */

public class Pessoa {

    // Constantes públicas
    public static final String TABELA = "pessoas";
    public static final String IDPESSOA = "_idpessoa";
    public static final String NOME = "nome";
    public static final String SOBRENOME = "sobrenome";
    public static final String DTNASC = "dtnasc";
    public static final String EMAIL = "email";
    public static final String TELEFONE = "telefone";
    public static final String CELULAR = "celular";
    public static final String ENDERECO = "endereco";
    public static final String BAIRRO = "bairro";
    public static final String CIDADE = "cidade";
    public static final String ESTADO = "estado";
    public static final String CEP = "cep";

    // Atributos
    private Long idPessoa;
    private String nome;
    private String sobrenome;
    private Date dtnasc;
    private String email;
    private String telefone;
    private String celular;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    // Método construtor default
    public Pessoa() {
    }

    // Métodos getters and setters
    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Date getDtnasc() {
        return dtnasc;
    }

    public void setDtnasc(Date dtnasc) {
        this.dtnasc = dtnasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
} // Fim da classe
