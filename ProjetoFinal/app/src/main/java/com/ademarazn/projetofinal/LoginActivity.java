package com.ademarazn.projetofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ademarazn.projetofinal.dao.UsuarioDAO;
import com.ademarazn.projetofinal.entidade.Usuario;

public class LoginActivity extends AppCompatActivity {

    public static Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    } // Fim do método onCreate

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    } // Fim do método onBackPressed

    @Override
    protected void onResume() {
        ((EditText) findViewById(R.id.edt_senha)).setText(null);
        usuario = new Usuario();
        super.onResume();
    }

    public void entrar(View v) {
        String usuario = ((EditText) findViewById(R.id.edt_usuario)).getText().toString().trim();
        String senha = ((EditText) findViewById(R.id.edt_senha)).getText().toString().trim();
        if (!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(senha)) {
            LoginActivity.usuario = new UsuarioDAO(LoginActivity.this).verificar(usuario, senha);
            if (LoginActivity.usuario != null) {
                startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
            } else {
                Snackbar.make(v, "Usuário e/ou senha inválidos!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    } // Fim do método entrar(View)

    public void esqueceu(View v) {
        startActivity(new Intent(LoginActivity.this, EsqueceuActivity.class));
    } // Fim do método esqueceu(View)

    public void cadastrar(View v) {
        startActivity(new Intent(LoginActivity.this, CadastrarActivity.class));
    } // Fim do método cadastrar(View)

    public void sobre(View view) {
        startActivity(new Intent(LoginActivity.this, SobreActivity.class));
    }
} // Fim da classe
