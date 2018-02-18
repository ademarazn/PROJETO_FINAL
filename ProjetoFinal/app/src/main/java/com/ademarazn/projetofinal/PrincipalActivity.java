package com.ademarazn.projetofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ademarazn.projetofinal.app.MessageBox;
import com.ademarazn.projetofinal.dao.ContatoDAO;
import com.ademarazn.projetofinal.dao.PessoaDAO;
import com.ademarazn.projetofinal.entidade.Contato;
import com.ademarazn.projetofinal.entidade.Pessoa;

import java.util.List;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView txtNome;
    private TextView txtEmail;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, ContatoActivity.class);
                intent.putExtra("title", "I");
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        txtNome = (TextView) header.findViewById(R.id.txt_nome);
        txtEmail = (TextView) header.findViewById(R.id.txt_email);

        final EditText pesquisar = (EditText) findViewById(R.id.pesquisar);
        pesquisar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    pesquisar(pesquisar.getText().toString());
                } catch (Exception e) {
                    MessageBox.show(PrincipalActivity.this, "Erro", e.getMessage(), null);
                }
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long pkContato = ((ContatoAdapter) ((ListView) findViewById(R.id.listView)).getAdapter()).getItem(position).getIdContato();
                Intent intent = new Intent(PrincipalActivity.this, ContatoActivity.class);
                intent.putExtra("title", "AE");
                intent.putExtra("pkContato", pkContato);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginActivity.usuario == null) {
            finish();
        }

        try {
            List<Contato> contatos = new ContatoDAO(PrincipalActivity.this).buscarContatos(LoginActivity.usuario.getIdUsuario());
            ContatoAdapter adapter = new ContatoAdapter(PrincipalActivity.this, contatos);
            ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
        } catch (Exception e) {
            MessageBox.show(PrincipalActivity.this, "Erro", e.getMessage(), null);
        }

        try {
            Pessoa pessoa = new PessoaDAO(PrincipalActivity.this).buscarPorId(LoginActivity.usuario.getFkPessoa());
            if (pessoa != null) {
                txtNome.setText(pessoa.getNome());
                txtEmail.setText(LoginActivity.usuario.getEmail());
            }
        } catch (Exception e) {
            MessageBox.show(PrincipalActivity.this, "Erro", e.getMessage(), null);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    public void pesquisar(String text) throws Exception {
        List<Contato> contatos = new ContatoDAO(PrincipalActivity.this).buscar(text);
        carregarDados(contatos);
    }

    public void carregarDados(List<Contato> contatos) {
        ListView mostraDados = (ListView) findViewById(R.id.listView);
        ContatoAdapter adapter = new ContatoAdapter(PrincipalActivity.this, contatos);
        mostraDados.setAdapter(adapter);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Intent intent = new Intent(PrincipalActivity.this, CadastrarActivity.class);
            intent.putExtra("title", "Perfil");
            startActivity(intent);
        } else if (id == R.id.nav_sobre) {
            startActivity(new Intent(PrincipalActivity.this, SobreActivity.class));
        } else if (id == R.id.nav_sair) {
            MessageBox.showConfirm(PrincipalActivity.this, "Sair", "Deseja realmente sair?", 0,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PrincipalActivity.super.onBackPressed();
                        }
                    },
                    null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
