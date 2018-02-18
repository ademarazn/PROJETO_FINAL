package com.ademarazn.projetofinal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.ademarazn.projetofinal.app.MessageBox;

/**
 * Created by Ademar Zório Neto on 06/12/2017
 */

public class Database extends SQLiteOpenHelper {

    public static String NOME = "bdprojetofinal";

    /**
     * @param context Referência da classe Context
     * @param name    Nome do banco de dados
     * @param factory Referente a classe CursorFactory, busca de dados no banco
     * @param version Versão do banco de dados
     */
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(ScriptSQL.getCreateTablePessoas());
            db.execSQL(ScriptSQL.getCreateTableUsuarios());
            db.execSQL(ScriptSQL.getCreateTableContatos());
        } catch (SQLiteException e) {
            MessageBox.show(null, "Erro", e.getMessage(), null);
        }
    } // Fim do método onCreate(SQLiteDatabase)

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    } // Fim do método onUpgrade(SQLiteDatabase, int, int)
} // Fim da classe
