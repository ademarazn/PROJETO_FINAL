package com.ademarazn.projetofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ademarazn.projetofinal.database.Database;
import com.ademarazn.projetofinal.entidade.Contato;
import com.ademarazn.projetofinal.entidade.Pessoa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ademar on 09/12/2017
 */

public class ContatoDAO {

    private Database database;

    public ContatoDAO(Context context) {
        database = new Database(context, Database.NOME, null, 1);
    }

    /**
     * @param contato Objeto da classe Contato a ser convertido para ContentValues
     * @return Retorna um objeto do tipo ContentValues, com os dados do contato
     */
    private ContentValues preencherContentValues(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(Contato.FKUSUARIO, contato.getFkUsuario());
        values.put(Contato.FKPESSOA, contato.getFkPessoa());
        return values;
    }

    /**
     * @param contato Contato a ser inserido
     * @return Retorna o id do contato inserido, ou -1 se ocorreu um erro
     * @throws SQLException
     */
    public Long inserir(Contato contato) throws SQLException {
        ContentValues values = preencherContentValues(contato);
        return database.getWritableDatabase().insertOrThrow(Contato.TABELA, null, values);
    }

    /**
     * @param contato Contato a ser alterado
     * @return Retorna a quantidade de linhas afetadas na alteração
     */
    public int alterar(Contato contato) {
        ContentValues values = preencherContentValues(contato);
        return database.getWritableDatabase().update(
                Contato.TABELA,
                values,
                Contato.IDCONTATO + " = ?",
                new String[]{String.valueOf(contato.getIdContato())});
    }

    /**
     * @param idContato ID do contato a ser excluído
     * @return Retorna a quantidade de linhas afetadas na exclusão
     */
    public int excluir(Long idContato) {
        return database.getWritableDatabase().delete(
                Contato.TABELA,
                Contato.IDCONTATO + " = ?",
                new String[]{String.valueOf(idContato)});
    }

    public List<Contato> buscarContatos(Long idUsuario) throws Exception {
        String sql = "SELECT * FROM " + Contato.TABELA + " WHERE fkusuario = ?";
        Cursor cursor = database.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(idUsuario)});
        List<Contato> contatos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setIdContato(cursor.getLong(cursor.getColumnIndex(Contato.IDCONTATO)));
            contato.setFkUsuario(cursor.getLong(cursor.getColumnIndex(Contato.FKUSUARIO)));
            contato.setFkPessoa(cursor.getLong(cursor.getColumnIndex(Contato.FKPESSOA)));
            // Adicionando o contato na lista de contatos
            contatos.add(contato);
        }
        cursor.close();
        return contatos;
    } // Fim do método buscarContatos()

    public Contato buscarContatoPk(Long idContato) throws Exception {
        String sql = "SELECT * FROM " + Contato.TABELA + " WHERE _idcontato = ?";
        Cursor cursor = database.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(idContato)});
        if (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setIdContato(cursor.getLong(cursor.getColumnIndex(Contato.IDCONTATO)));
            contato.setFkUsuario(cursor.getLong(cursor.getColumnIndex(Contato.FKUSUARIO)));
            contato.setFkPessoa(cursor.getLong(cursor.getColumnIndex(Contato.FKPESSOA)));
            cursor.close();
            return contato;
        } else {
            cursor.close();
            return null;
        }
    } // Fim do método buscarContatoPk()

    public List<Contato> buscar(String texto) throws Exception {
        String sql = "SELECT * FROM " + Contato.TABELA + ", " + Pessoa.TABELA +
                " WHERE " + Contato.TABELA + ".fkpessoa = " + Pessoa.TABELA + "._idpessoa" +
                " and nome LIKE '" + texto + "%'";
        Cursor cursor = database.getReadableDatabase().rawQuery(sql, null);
        List<Contato> contatos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setIdContato(cursor.getLong(cursor.getColumnIndex(Contato.IDCONTATO)));
            contato.setFkUsuario(cursor.getLong(cursor.getColumnIndex(Contato.FKUSUARIO)));
            contato.setFkPessoa(cursor.getLong(cursor.getColumnIndex(Contato.FKPESSOA)));
            contatos.add(contato);
        }
        cursor.close();
        return contatos;
    } // Fim do método buscarContatoPk()
}
