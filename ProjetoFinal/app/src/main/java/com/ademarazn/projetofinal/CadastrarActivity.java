package com.ademarazn.projetofinal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ademarazn.projetofinal.app.MessageBox;
import com.ademarazn.projetofinal.dao.PessoaDAO;
import com.ademarazn.projetofinal.dao.UsuarioDAO;
import com.ademarazn.projetofinal.entidade.Pessoa;
import com.ademarazn.projetofinal.entidade.Usuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CadastrarActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText edtDtNasc;
    private DatePickerDialog.OnDateSetListener date;
    private Boolean isOpen = false;
    private FloatingActionButton fabPlus;
    private FloatingActionButton fabAlterar;
    private FloatingActionButton fabDelete;
    private Animation fabOpen;
    private Animation fabClose;
    private Animation fabRClookwise;
    private Animation fabRanticlookwise;
    private Pessoa pessoa;
    private Usuario u;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            MessageBox.show(CadastrarActivity.this, "Erro", e.getMessage(), null);
        }

        Bundle dados = getIntent().getExtras();
        String title = null;
        if (dados != null) {
            title = dados.getString("title");
        }
        if (title != null) {
            getSupportActionBar().setTitle(title);

            try {
                Usuario user = LoginActivity.usuario;
                Pessoa person = new PessoaDAO(CadastrarActivity.this).buscarPorId(user.getFkPessoa());
                ((EditText) findViewById(R.id.edt_nome)).setText(person.getNome());
                ((EditText) findViewById(R.id.edt_sobrenome)).setText(person.getSobrenome());
                ((EditText) findViewById(R.id.edt_usuario)).setText(user.getUsuario());
                ((EditText) findViewById(R.id.edt_senha)).setText(user.getSenha());
                ((EditText) findViewById(R.id.edt_email)).setText(user.getEmail());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                ((EditText) findViewById(R.id.edt_dtnasc)).setText(format.format(person.getDtnasc()));
                ((EditText) findViewById(R.id.edt_cpf)).setText(user.getCpf());
                ((EditText) findViewById(R.id.edt_rg)).setText(user.getRg());
                ((EditText) findViewById(R.id.edt_passaporte)).setText(user.getPassaporte());
                ((EditText) findViewById(R.id.edt_telefone)).setText(person.getTelefone());
                ((EditText) findViewById(R.id.edt_celular)).setText(person.getTelefone());
                ((EditText) findViewById(R.id.edt_endereco)).setText(person.getEndereco());
                ((EditText) findViewById(R.id.edt_bairro)).setText(person.getBairro());
                ((EditText) findViewById(R.id.edt_cidade)).setText(person.getCidade());
                ((EditText) findViewById(R.id.edt_estado)).setText(person.getEstado());
                ((EditText) findViewById(R.id.edt_cep)).setText(person.getCep());
            } catch (Exception e) {
                MessageBox.show(CadastrarActivity.this, "Erro", e.getMessage(), null);
            }

            findViewById(R.id.fab_incluir).setVisibility(FloatingActionButton.GONE);

            fabPlus = (FloatingActionButton) findViewById(R.id.fab_plus);
            fabAlterar = (FloatingActionButton) findViewById(R.id.fab_alterar);
            fabDelete = (FloatingActionButton) findViewById(R.id.fab_delete);

            fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
            fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

            fabRClookwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
            fabRanticlookwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

            final FloatingActionButton fabP = fabPlus;
            final FloatingActionButton fabA = fabAlterar;
            final FloatingActionButton fabD = fabDelete;

            fabP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isOpen) {
                        fabAlterar.startAnimation(fabClose);
                        fabDelete.startAnimation(fabClose);
                        fabPlus.startAnimation(fabRanticlookwise);
                        fabAlterar.setClickable(false);
                        fabDelete.setClickable(false);
                        isOpen = false;
                    } else {
                        fabAlterar.startAnimation(fabOpen);
                        fabDelete.startAnimation(fabOpen);
                        fabPlus.startAnimation(fabRClookwise);
                        fabAlterar.setClickable(true);
                        fabDelete.setClickable(true);
                        isOpen = true;
                    }

                }
            });

            fabA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fabPlus.startAnimation(fabRanticlookwise);
                    fabAlterar.startAnimation(fabClose);
                    fabDelete.startAnimation(fabClose);
                    fabAlterar.setClickable(false);
                    fabDelete.setClickable(false);
                    isOpen = false;
                    cadastrar(view, "Alterar");
                }
            });

            fabD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fabPlus.startAnimation(fabRanticlookwise);
                    fabAlterar.startAnimation(fabClose);
                    fabDelete.startAnimation(fabClose);
                    fabAlterar.setClickable(false);
                    fabDelete.setClickable(false);
                    isOpen = false;
                    cadastrar(view, "Excluir");
                }
            });
        } else {
            getSupportActionBar().setTitle(R.string.title_activity_cadastrar);
            findViewById(R.id.fab_plus).setVisibility(FloatingActionButton.GONE);
            findViewById(R.id.fab_alterar).setVisibility(FloatingActionButton.GONE);
            findViewById(R.id.fab_delete).setVisibility(FloatingActionButton.GONE);
            FloatingActionButton fabIncluir = (FloatingActionButton) findViewById(R.id.fab_incluir);
            fabIncluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cadastrar(view, "Incluir");
                }
            });
        }


        myCalendar = Calendar.getInstance();
        edtDtNasc = (EditText) findViewById(R.id.edt_dtnasc);
        edtDtNasc.setInputType(InputType.TYPE_NULL);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edtDtNasc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(CadastrarActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            edtDtNasc.setText(null);
                            findViewById(R.id.edt_cpf).requestFocus();
                        }
                    });
                    datePickerDialog.show();
                }
            }
        });

        final EditText edtEstado = (EditText) findViewById(R.id.edt_estado);
        edtEstado.setInputType(InputType.TYPE_NULL);
        edtEstado.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(CadastrarActivity.this);
                    dialog.setTitle("Estado");

                    // Cria um ArrayAdapter usando o array de string e um layout de spinner padrão
                    final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                            .createFromResource(CadastrarActivity.this, R.array.estados,
                                    R.layout.layout_estado);

                    dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtEstado.setText(null);
                            findViewById(R.id.edt_cep).requestFocus();
                            dialog.dismiss();
                        }
                    });

                    dialog.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String estado = arrayAdapter.getItem(which).toString().split("-")[0].trim();
                            edtEstado.setText(estado);
                            findViewById(R.id.edt_cep).requestFocus();
                        }
                    });
                    dialog.show();
                }
            }
        });
    } // Fim do método onCreate

    @Override
    public void onBackPressed() {
        MessageBox.showConfirm(CadastrarActivity.this, null, "Deseja realmente voltar?", 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CadastrarActivity.super.onBackPressed();
                    }
                },
                null);
    } // Fim do método onBackPressed

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        edtDtNasc.setText(sdf.format(myCalendar.getTime()));
        findViewById(R.id.edt_cpf).requestFocus();
    } // Fim do método updateLabel

    private void cadastrar(View v, String acao) {
        String nome = ((EditText) findViewById(R.id.edt_nome)).getText().toString().trim();
        String sobrenome = ((EditText) findViewById(R.id.edt_sobrenome)).getText().toString().trim();
        Date dtNasc = myCalendar.getTime();
        String email = ((EditText) findViewById(R.id.edt_email)).getText().toString().trim();
        String telefone = ((EditText) findViewById(R.id.edt_telefone)).getText().toString().trim();
        String celular = ((EditText) findViewById(R.id.edt_celular)).getText().toString().trim();
        String endereco = ((EditText) findViewById(R.id.edt_endereco)).getText().toString().trim();
        String bairro = ((EditText) findViewById(R.id.edt_bairro)).getText().toString().trim();
        String cidade = ((EditText) findViewById(R.id.edt_cidade)).getText().toString().trim();
        String estado = ((EditText) findViewById(R.id.edt_estado)).getText().toString().trim();
        String cep = ((EditText) findViewById(R.id.edt_cep)).getText().toString().trim();
        final String usuario = ((EditText) findViewById(R.id.edt_usuario)).getText().toString().trim();
        String senha = ((EditText) findViewById(R.id.edt_senha)).getText().toString().trim();
        String cpf = ((EditText) findViewById(R.id.edt_cpf)).getText().toString().trim();
        String rg = ((EditText) findViewById(R.id.edt_rg)).getText().toString().trim();
        String passaporte = ((EditText) findViewById(R.id.edt_passaporte)).getText().toString().trim();

        if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(sobrenome)
                && !TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(senha)
                && !TextUtils.isEmpty(email)) {
            // Nova pessoa
            pessoa = new Pessoa();
            pessoa.setNome(nome);
            pessoa.setSobrenome(sobrenome);
            pessoa.setDtnasc(dtNasc);
            pessoa.setTelefone(telefone);
            pessoa.setCelular(celular);
            pessoa.setEndereco(endereco);
            pessoa.setBairro(bairro);
            pessoa.setCidade(cidade);
            pessoa.setEstado(estado);
            pessoa.setCep(cep);
            try {
                switch (acao) {
                    case "Incluir":
                        if (new UsuarioDAO(CadastrarActivity.this).verificarCampo("usuario", usuario) != null) {
                            Snackbar.make(v, "O usuário inserido já foi cadastrado!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                        if (new UsuarioDAO(CadastrarActivity.this).verificarCampo("email", email) != null) {
                            Snackbar.make(v, "O e-mail inserido já foi cadastrado!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                        // Insere a pessoa e recupera o id dela
                        Long idpessoa = new PessoaDAO(CadastrarActivity.this).inserir(pessoa);
                        // Novo usuário
                        u = new Usuario();
                        u.setUsuario(usuario);
                        u.setSenha(senha);
                        u.setEmail(email);
                        u.setCpf(cpf);
                        u.setRg(rg);
                        u.setPassaporte(passaporte);
                        u.setFkPessoa(idpessoa);
                        new UsuarioDAO(CadastrarActivity.this).inserir(u);
                        MessageBox.show(CadastrarActivity.this, null, "Usuário cadastrado com sucesso!",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        break;
                    case "Alterar": {
                        if (new UsuarioDAO(CadastrarActivity.this).verificarCampo("usuario", usuario) != null
                                && !usuario.equals(LoginActivity.usuario.getUsuario())) {
                            Snackbar.make(v, "O usuário inserido já foi cadastrado!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                        if (new UsuarioDAO(CadastrarActivity.this).verificarCampo("email", email) != null
                                && !email.equals(LoginActivity.usuario.getEmail())) {
                            Snackbar.make(v, "O e-mail inserido já foi cadastrado!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                        // Altera a pessoa e retorna quantidade de linhas afetadas
                        pessoa.setIdPessoa(LoginActivity.usuario.getFkPessoa());
                        // Usuário a ser alterado
                        u = new Usuario();
                        u.setIdUsuario(LoginActivity.usuario.getIdUsuario());
                        u.setUsuario(usuario);
                        u.setSenha(senha);
                        u.setEmail(email);
                        u.setCpf(cpf);
                        u.setRg(rg);
                        u.setPassaporte(passaporte);
                        u.setFkPessoa(LoginActivity.usuario.getFkPessoa());

                        AlertDialog.Builder dialog = new AlertDialog.Builder(CadastrarActivity.this);
                        dialog.setMessage("Deseja realmente alterar?");
                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int rowsAffected = new PessoaDAO(CadastrarActivity.this).alterar(pessoa);
                                if (rowsAffected <= 0) {
                                    MessageBox.show(CadastrarActivity.this, "Erro", "Não foi possível alterar" +
                                            " os dados pessoais do usuário!", null);
                                    return;
                                }
                                rowsAffected = new UsuarioDAO(CadastrarActivity.this).alterar(u);
                                if (rowsAffected <= 0) {
                                    MessageBox.show(CadastrarActivity.this, "Erro", "Não foi possível alterar" +
                                            " os dados do usuário!", null);
                                    return;
                                }
                                LoginActivity.usuario = u;
                                MessageBox.show(CadastrarActivity.this, null, "Usuário alterado com sucesso!",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                            }
                        });
                        dialog.setNegativeButton("Não", null);
                        dialog.show();
                        break;
                    }
                    case "Excluir": {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(CadastrarActivity.this);
                        dialog.setMessage("Deseja realmente excluir?");
                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int rowsAffected = new UsuarioDAO(CadastrarActivity.this).excluir(LoginActivity.usuario.getIdUsuario());
                                if (rowsAffected <= 0) {
                                    MessageBox.show(CadastrarActivity.this, "Erro", "Não foi possível alterar" +
                                            " os dados do usuário!", null);
                                    return;
                                }
                                rowsAffected = new PessoaDAO(CadastrarActivity.this).excluir(LoginActivity.usuario.getFkPessoa());
                                if (rowsAffected <= 0) {
                                    MessageBox.show(CadastrarActivity.this, "Erro", "Não foi possível alterar" +
                                            " os dados pessoais do usuário!", null);
                                    return;
                                }
                                MessageBox.show(CadastrarActivity.this, null, "Usuário excluído com sucesso!",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                LoginActivity.usuario = null;
                                                finish();
                                            }
                                        });
                            }
                        });
                        dialog.setNegativeButton("Não", null);
                        dialog.show();
                        break;
                    }
                }
            } catch (SQLException e) {
                MessageBox.show(CadastrarActivity.this, "Erro", e.getMessage(), null);
            }
        } else {
            Snackbar.make(v, "Preencha todos os campos marcados com um (*) asterisco!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    } // Fim do método cadastrar

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

} // Fim da classe
