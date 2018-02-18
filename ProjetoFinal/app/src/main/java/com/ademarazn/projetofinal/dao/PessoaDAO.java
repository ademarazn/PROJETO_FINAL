package com.ademarazn.projetofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ademarazn.projetofinal.database.Database;
import com.ademarazn.projetofinal.entidade.Pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ademar Zório Neto on 06/12/2017
 */

public class PessoaDAO {

    private Database database;

    public PessoaDAO(Context context) {
        database = new Database(context, Database.NOME, null, 1);
    }

    /**
     * @param pessoa Objeto da classe Pessoa a ser convertido para ContentValues
     * @return Retorna um objeto do tipo ContentValues, com os dados da Pessoa
     */
    private ContentValues preencherContentValues(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put(Pessoa.NOME, pessoa.getNome());
        values.put(Pessoa.SOBRENOME, pessoa.getSobrenome());
        if (pessoa.getDtnasc() != null) {
            values.put(Pessoa.DTNASC, pessoa.getDtnasc().getTime());
        }
        values.put(Pessoa.EMAIL, pessoa.getEmail());
        values.put(Pessoa.TELEFONE, pessoa.getTelefone());
        values.put(Pessoa.CELULAR, pessoa.getCelular());
        values.put(Pessoa.ENDERECO, pessoa.getEndereco());
        values.put(Pessoa.BAIRRO, pessoa.getBairro());
        values.put(Pessoa.CIDADE, pessoa.getCidade());
        values.put(Pessoa.ESTADO, pessoa.getEstado());
        values.put(Pessoa.CEP, pessoa.getCep());
        return values;
    } // Fim do método preencherContentValues(Pessoa)

    /**
     * @param pessoa Pessoa a ser inserida
     * @return Retorna o id do Pessoa inserida, ou -1 se opcorreu um erro
     * @throws SQLException Restorna uma exceção caso ocorra algum erro na inserção
     */
    public Long inserir(Pessoa pessoa) throws SQLException {
        ContentValues values = preencherContentValues(pessoa);
        return database.getWritableDatabase().insertOrThrow(Pessoa.TABELA, null, values);
    } // Fim do método inserir(Pessoa)

    /**
     * @param pessoa Pessoa a ser alterada
     * @return Retorna a quantidade de linhas afetadas na alteração
     */
    public int alterar(Pessoa pessoa) {
        ContentValues values = preencherContentValues(pessoa);
        return database.getWritableDatabase().update(
                Pessoa.TABELA,
                values,
                Pessoa.IDPESSOA + " = ?",
                new String[]{String.valueOf(pessoa.getIdPessoa())});
    } // Fim do cétodo alterar(Pessoa)

    /**
     * @param idPessoa ID da Pessoa a ser excluída
     * @return Retorna a quantidade de linhas afetadas na exclusão
     */
    public int excluir(Long idPessoa) {
        return database.getWritableDatabase().delete(
                Pessoa.TABELA,
                Pessoa.IDPESSOA + " = ?",
                new String[]{String.valueOf(idPessoa)});
    } // Fim do método excluir(Long)

    public List<Pessoa> buscarPessoas() throws Exception {
        String sql = "SELECT * FROM " + Pessoa.TABELA;
        Cursor cursor = database.getReadableDatabase().rawQuery(sql, null);
        List<Pessoa> pessoas = new ArrayList<>();
        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setIdPessoa(cursor.getLong(cursor.getColumnIndex(Pessoa.IDPESSOA)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(Pessoa.NOME)));
            pessoa.setSobrenome(cursor.getString(cursor.getColumnIndex(Pessoa.SOBRENOME)));
            pessoa.setDtnasc(new Date(cursor.getLong(cursor.getColumnIndex(Pessoa.DTNASC))));
            pessoa.setEmail(cursor.getString(cursor.getColumnIndex(Pessoa.EMAIL)));
            pessoa.setTelefone(cursor.getString(cursor.getColumnIndex(Pessoa.TELEFONE)));
            pessoa.setCelular(cursor.getString(cursor.getColumnIndex(Pessoa.CELULAR)));
            pessoa.setEndereco(cursor.getString(cursor.getColumnIndex(Pessoa.ENDERECO)));
            pessoa.setBairro(cursor.getString(cursor.getColumnIndex(Pessoa.BAIRRO)));
            pessoa.setCidade(cursor.getString(cursor.getColumnIndex(Pessoa.CIDADE)));
            pessoa.setEstado(cursor.getString(cursor.getColumnIndex(Pessoa.ESTADO)));
            pessoa.setCep(cursor.getString(cursor.getColumnIndex(Pessoa.CEP)));
            // Adicionando pessoa na lista de pessoas
            pessoas.add(pessoa);
        }
        cursor.close();
        return pessoas;
    } // Fim do método buscarPorId(Long)

    /**
     * @param idPessoa ID da pessoa a ser buscada
     * @return Retorna a pessoa se encontrar, caso contrário retorna @null
     * @throws Exception Lança uma exceção caso ocorra
     */
    public Pessoa buscarPorId(Long idPessoa) throws Exception {
        String sql = "SELECT * FROM " + Pessoa.TABELA + " WHERE _idpessoa = ?";
        Cursor cursor = database.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(idPessoa)});
        if (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setIdPessoa(cursor.getLong(cursor.getColumnIndex(Pessoa.IDPESSOA)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(Pessoa.NOME)));
            pessoa.setSobrenome(cursor.getString(cursor.getColumnIndex(Pessoa.SOBRENOME)));
            pessoa.setDtnasc(new Date(cursor.getLong(cursor.getColumnIndex(Pessoa.DTNASC))));
            pessoa.setEmail(cursor.getString(cursor.getColumnIndex(Pessoa.EMAIL)));
            pessoa.setTelefone(cursor.getString(cursor.getColumnIndex(Pessoa.TELEFONE)));
            pessoa.setCelular(cursor.getString(cursor.getColumnIndex(Pessoa.CELULAR)));
            pessoa.setEndereco(cursor.getString(cursor.getColumnIndex(Pessoa.ENDERECO)));
            pessoa.setBairro(cursor.getString(cursor.getColumnIndex(Pessoa.BAIRRO)));
            pessoa.setCidade(cursor.getString(cursor.getColumnIndex(Pessoa.CIDADE)));
            pessoa.setEstado(cursor.getString(cursor.getColumnIndex(Pessoa.ESTADO)));
            pessoa.setCep(cursor.getString(cursor.getColumnIndex(Pessoa.CEP)));
            cursor.close();
            return pessoa;
        } else {
            cursor.close();
            return null;
        }
    } // Fim do método buscarPorId(Long)
} // Fim da classe
