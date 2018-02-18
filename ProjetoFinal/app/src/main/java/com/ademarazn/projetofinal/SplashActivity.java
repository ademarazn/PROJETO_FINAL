package com.ademarazn.projetofinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    boolean activityVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activityVisible = true;
        new LoadViewTask().execute();
    } // Fim do método onCreate

    @Override
    protected void onResume() {
        super.onResume();
        if (!activityVisible) {
            abrirLogin();
        }
    } // Fim do método onResume

    @Override
    protected void onPause() {
        super.onPause();
        activityVisible = false;
    } // Fim do método onPause

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    } // Fim do método onBackPressed

    private void abrirLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    } // Fim do método abrirLogin

    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Get the current thread's token
                synchronized (this) {
                    this.wait(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result) {
            if (activityVisible) {
                abrirLogin();
            }
        }
    }

}
