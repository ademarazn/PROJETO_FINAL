package com.ademarazn.projetofinal.database;

import com.ademarazn.projetofinal.entidade.Contato;
import com.ademarazn.projetofinal.entidade.Pessoa;
import com.ademarazn.projetofinal.entidade.Usuario;

/**
 * Created by Ademar Zório Neto on 06/12/2017
 */

class ScriptSQL {
    /**
     * @return Retorna uma String contendo o SQL para criar a tabela usuarios
     */
    static String getCreateTableUsuarios() {
        return "CREATE TABLE IF NOT EXISTS " + Usuario.TABELA + " ( " +
                Usuario.IDUSUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Usuario.USUARIO + " VARCHAR(20) NOT NULL UNIQUE, " +
                Usuario.SENHA + " VARCHAR(16) NOT NULL, " +
                Usuario.EMAIL + " VARCHAR(50) NOT NULL UNIQUE, " +
                Usuario.CPF + " VARCHAR(11) NULL, " +
                Usuario.RG + " VARCHAR(9) NULL, " +
                Usuario.PASSAPORTE + " VARCHAR(11) NULL, " +
                Usuario.FKPESSOA + " INTEGER REFERENCES pessoas (_idpessoa) " +
                "); ";
    } // Fim do método getCreateTableUsuarios()

    /**
     * @return Retorna uma String contendo o SQL para criar a tabela pessoas
     */
    static String getCreateTablePessoas() {
        return "CREATE TABLE IF NOT EXISTS " + Pessoa.TABELA + " ( " +
                Pessoa.IDPESSOA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Pessoa.NOME + " VARCHAR(20) NOT NULL, " +
                Pessoa.SOBRENOME + " VARCHAR(25) NOT NULL, " +
                Pessoa.DTNASC + " DATE NULL, " +
                Pessoa.EMAIL + " VARCHAR(50) NULL, " +
                Pessoa.TELEFONE + " VARCHAR(10) NULL, " +
                Pessoa.CELULAR + " VARCHAR(11) NULL, " +
                Pessoa.ENDERECO + " VARCHAR(45) NULL, " +
                Pessoa.BAIRRO + " VARCHAR(45) NULL, " +
                Pessoa.CIDADE + " VARCHAR(45) NULL, " +
                Pessoa.ESTADO + " VARCHAR(25) NULL, " +
                Pessoa.CEP + " VARCHAR(8) NULL " +
                "); ";
    } // Fim do método getCreateTablePessoas()

    /**
     * @return Retorna uma String contendo o SQL para criar a tabela contatos
     */
    static String getCreateTableContatos() {
        return "CREATE TABLE IF NOT EXISTS " + Contato.TABELA + " ( " +
                Contato.IDCONTATO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contato.FKUSUARIO + " INTEGER REFERENCES usuarios (_idusuario), " +
                Contato.FKPESSOA + " INTEGER REFERENCES pessoas (_idpessoa) " +
                "); ";
    } // Fim do método getCreateTableContatos()
} // Fim da classe ScriptSQL
