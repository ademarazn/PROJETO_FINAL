package com.ademarazn.projetofinal.entidade;

/**
 * Created by Ademar Zório Neto on 09/12/2017
 */

public class Contato {

    // Constantes públicas
    public static final String TABELA = "contatos";
    public static final String IDCONTATO = "_idcontato";
    public static final String FKUSUARIO = "fkusuario";
    public static final String FKPESSOA = "fkpessoa";

    // Atributos
    private Long idContato;
    private Long fkUsuario;
    private Long fkPessoa;

    // Método construtor default
    public Contato() {
    }

    // Métodos getters and setters
    public Long getIdContato() {
        return idContato;
    }

    public void setIdContato(Long idContato) {
        this.idContato = idContato;
    }

    public Long getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Long fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public Long getFkPessoa() {
        return fkPessoa;
    }

    public void setFkPessoa(Long fkPessoa) {
        this.fkPessoa = fkPessoa;
    }
} // Fim da classe
