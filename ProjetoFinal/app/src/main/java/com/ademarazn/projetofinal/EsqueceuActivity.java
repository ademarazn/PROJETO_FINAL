package com.ademarazn.projetofinal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ademarazn.projetofinal.app.MessageBox;
import com.ademarazn.projetofinal.dao.UsuarioDAO;
import com.ademarazn.projetofinal.entidade.Usuario;
import com.ademarazn.projetofinal.utils.SendMailAsynTask;

public class EsqueceuActivity extends AppCompatActivity {

    private Long numRandom;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            MessageBox.show(EsqueceuActivity.this, "Erro", e.getMessage(), null);
        }

        numRandom = (long) (Math.random() * 10000000000L);
    } // Fim do método onCreate(Bundle)

    public void enviar(View v) {
        String email = ((EditText) findViewById(R.id.edt_email)).getText().toString().trim();

        if (!email.trim().isEmpty()) {

            Usuario usuario = new UsuarioDAO(EsqueceuActivity.this).verificarCampo("email", email);

            if (!hasInternetConnection()) {
                Snackbar.make(v, "Sem conexão com a internet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }

            if (usuario != null) {
                usuario.setSenha(numRandom.toString());

                new UsuarioDAO(EsqueceuActivity.this).alterar(usuario);

                new SendMailAsynTask(EsqueceuActivity.this, v, email,
                        "Foi solicitado o envio da senha em nosso aplicativo para esse email: " + email
                                + "\nSenha: " + numRandom).execute();
                //call send mail  cunstructor asyntask by  sending perameter
            } else {
                Snackbar.make(v, "Não existe esse e-mail cadastrado!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        } else {
            Snackbar.make(v, "Preencha o campo de e-mail!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    } // Fim do método enviar(View)

    /* Método para verificar a existência de conexão com a internet */
    public boolean hasInternetConnection() {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getActiveNetworkInfo() != null
                    && connectivityManager.getActiveNetworkInfo().isAvailable()
                    && connectivityManager.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            return false;
        }
    } // Fim do método hasInternetConnection()

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    } // Fim do método onOptionsItemSelected(MenuItem)
} // Fim da classe
