package com.ademarazn.projetofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ademarazn.projetofinal.database.Database;
import com.ademarazn.projetofinal.entidade.Usuario;

/**
 * Created by Ademar on 06/12/2017
 */

public class UsuarioDAO {

    private Database database;

    public UsuarioDAO(Context context) {
        database = new Database(context, Database.NOME, null, 1);
    }

    /**
     * @param usuario Objeto da classe Usuario a ser convertido para ContentValues
     * @return Retorna um objeto do tipo ContentValues, com os dados do usuário
     */
    private ContentValues preencherContentValues(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(Usuario.USUARIO, usuario.getUsuario());
        values.put(Usuario.SENHA, usuario.getSenha());
        values.put(Usuario.EMAIL, usuario.getEmail());
        values.put(Usuario.CPF, usuario.getCpf());
        values.put(Usuario.RG, usuario.getRg());
        values.put(Usuario.PASSAPORTE, usuario.getPassaporte());
        values.put(Usuario.FKPESSOA, usuario.getFkPessoa());
        return values;
    }

    /**
     * @param usuario Usuário a ser inserido
     * @return Retorna o id do usuário inserido, ou -1 se ocorreu um erro
     * @throws SQLException
     */
    public Long inserir(Usuario usuario) throws SQLException {
        ContentValues values = preencherContentValues(usuario);
        return database.getWritableDatabase().insertOrThrow(Usuario.TABELA, null, values);
    }

    /**
     * @param usuario Usuário a ser alterado
     * @return Retorna a quantidade de linhas afetadas na alteração
     */
    public int alterar(Usuario usuario) {
        ContentValues values = preencherContentValues(usuario);
        return database.getWritableDatabase().update(
                Usuario.TABELA,
                values,
                Usuario.IDUSUARIO + " = ?",
                new String[]{String.valueOf(usuario.getIdUsuario())});
    }

    /**
     * @param idUsuario ID do usuário a ser excluído
     * @return Retorna a quantidade de linhas afetadas na exclusão
     */
    public int excluir(Long idUsuario) {
        return database.getWritableDatabase().delete(
                Usuario.TABELA,
                Usuario.IDUSUARIO + " = ?",
                new String[]{String.valueOf(idUsuario)});
    }

    /**
     * @param usuario String com o nome de usuário
     * @param senha   String com a senha para entrar
     * @return Retorna o usuário se encontrar, caso contrário retorna @null
     */
    public Usuario verificar(String usuario, String senha) {
        String sql = "SELECT * FROM " + Usuario.TABELA + " WHERE usuario = ? AND senha = ?";
        Cursor cursor = database.getReadableDatabase().rawQuery(sql, new String[]{usuario, senha});
        if (cursor.moveToNext()) {
            Usuario u = new Usuario();
            u.setIdUsuario(cursor.getLong(cursor.getColumnIndex(Usuario.IDUSUARIO)));
            u.setUsuario(cursor.getString(cursor.getColumnIndex(Usuario.USUARIO)));
            u.setSenha(cursor.getString(cursor.getColumnIndex(Usuario.SENHA)));
            u.setEmail(cursor.getString(cursor.getColumnIndex(Usuario.EMAIL)));
            u.setCpf(cursor.getString(cursor.getColumnIndex(Usuario.CPF)));
            u.setRg(cursor.getString(cursor.getColumnIndex(Usuario.RG)));
            u.setPassaporte(cursor.getString(cursor.getColumnIndex(Usuario.PASSAPORTE)));
            u.setFkPessoa(cursor.getLong(cursor.getColumnIndex(Usuario.FKPESSOA)));
            cursor.close();
            return u;
        } else {
            cursor.close();
            return null;
        }
    }

    public Usuario verificarCampo(String campo, String whereArg) {
        String sql = "SELECT * FROM " + Usuario.TABELA + " WHERE " + campo + " = ?";
        Cursor cursor = database.getReadableDatabase().rawQuery(sql, new String[]{whereArg});
        if (cursor.moveToNext()) {
            Usuario u = new Usuario();
            u.setIdUsuario(cursor.getLong(cursor.getColumnIndex(Usuario.IDUSUARIO)));
            u.setUsuario(cursor.getString(cursor.getColumnIndex(Usuario.USUARIO)));
            u.setSenha(cursor.getString(cursor.getColumnIndex(Usuario.SENHA)));
            u.setEmail(cursor.getString(cursor.getColumnIndex(Usuario.EMAIL)));
            u.setCpf(cursor.getString(cursor.getColumnIndex(Usuario.CPF)));
            u.setRg(cursor.getString(cursor.getColumnIndex(Usuario.RG)));
            u.setPassaporte(cursor.getString(cursor.getColumnIndex(Usuario.PASSAPORTE)));
            u.setFkPessoa(cursor.getLong(cursor.getColumnIndex(Usuario.FKPESSOA)));
            cursor.close();
            return u;
        } else {
            cursor.close();
            return null;
        }
    }
}
