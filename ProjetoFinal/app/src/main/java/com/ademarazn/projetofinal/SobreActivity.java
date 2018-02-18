package com.ademarazn.projetofinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ademarazn.projetofinal.app.MessageBox;

public class SobreActivity extends AppCompatActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            MessageBox.show(SobreActivity.this, "Erro", e.getMessage(), null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    } // Fim do método onOptionsItemSelected(MenuItem)
}
