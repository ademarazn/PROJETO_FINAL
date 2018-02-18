package com.ademarazn.projetofinal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import com.ademarazn.projetofinal.dao.ContatoDAO;
import com.ademarazn.projetofinal.dao.PessoaDAO;
import com.ademarazn.projetofinal.entidade.Contato;
import com.ademarazn.projetofinal.entidade.Pessoa;
import com.ademarazn.projetofinal.entidade.Usuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ContatoActivity extends AppCompatActivity {

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
    private Contato c;
    private Long pkContato;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            MessageBox.show(ContatoActivity.this, "Erro", e.getMessage(), null);
        }

        Bundle dados = getIntent().getExtras();
        String title = null;
        if (dados != null) {
            title = dados.getString("title");
            if (title.equals("AE")) {
                pkContato = dados.getLong("pkContato");
            }
        }
        if (title.equals("AE")) {

            try {
                Long idPessoa = new ContatoDAO(ContatoActivity.this).buscarContatoPk(pkContato).getFkPessoa();
                Pessoa person = new PessoaDAO(ContatoActivity.this).buscarPorId(idPessoa);
                ((EditText) findViewById(R.id.edt_nome)).setText(person.getNome());
                ((EditText) findViewById(R.id.edt_sobrenome)).setText(person.getSobrenome());
                ((EditText) findViewById(R.id.edt_email)).setText(person.getEmail());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                ((EditText) findViewById(R.id.edt_dtnasc)).setText(format.format(person.getDtnasc()));
                ((EditText) findViewById(R.id.edt_telefone)).setText(person.getTelefone());
                ((EditText) findViewById(R.id.edt_celular)).setText(person.getTelefone());
                ((EditText) findViewById(R.id.edt_endereco)).setText(person.getEndereco());
                ((EditText) findViewById(R.id.edt_bairro)).setText(person.getBairro());
                ((EditText) findViewById(R.id.edt_cidade)).setText(person.getCidade());
                ((EditText) findViewById(R.id.edt_estado)).setText(person.getEstado());
                ((EditText) findViewById(R.id.edt_cep)).setText(person.getCep());
            } catch (Exception e) {
                MessageBox.show(ContatoActivity.this, "Erro", e.getMessage(), null);
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
                    Contato(view, "Alterar");
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
                    Contato(view, "Excluir");
                }
            });
        } else {
            findViewById(R.id.fab_plus).setVisibility(FloatingActionButton.GONE);
            findViewById(R.id.fab_alterar).setVisibility(FloatingActionButton.GONE);
            findViewById(R.id.fab_delete).setVisibility(FloatingActionButton.GONE);
            FloatingActionButton fabIncluir = (FloatingActionButton) findViewById(R.id.fab_incluir);
            fabIncluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Contato(view, "Incluir");
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
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ContatoActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            edtDtNasc.setText(null);
                            findViewById(R.id.edt_telefone).requestFocus();
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

                    AlertDialog.Builder dialog = new AlertDialog.Builder(ContatoActivity.this);
                    dialog.setTitle("Estado");

                    // Cria um ArrayAdapter usando o array de string e um layout de spinner padrão
                    final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                            .createFromResource(ContatoActivity.this, R.array.estados,
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
        MessageBox.showConfirm(ContatoActivity.this, null, "Deseja realmente voltar?", 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContatoActivity.super.onBackPressed();
                    }
                },
                null);
    } // Fim do método onBackPressed

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        edtDtNasc.setText(sdf.format(myCalendar.getTime()));
        findViewById(R.id.edt_telefone).requestFocus();
    } // Fim do método updateLabel

    private void Contato(View v, String acao) {
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

        if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(sobrenome)) {
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
                        // Insere a pessoa e recupera o id dela
                        Long idpessoa = new PessoaDAO(ContatoActivity.this).inserir(pessoa);
                        // Novo contato
                        c = new Contato();
                        c.setFkUsuario(LoginActivity.usuario.getIdUsuario());
                        c.setFkPessoa(idpessoa);
                        new ContatoDAO(ContatoActivity.this).inserir(c);
                        MessageBox.show(ContatoActivity.this, null, "Contato cadastrado com sucesso!",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        break;
                    case "Alterar": {
                        // Usuário a ser alterado
                        c = new ContatoDAO(ContatoActivity.this).buscarContatoPk(pkContato);
                        // Altera a pessoa e retorna quantidade de linhas afetadas
                        pessoa.setIdPessoa(c.getFkPessoa());

                        AlertDialog.Builder dialog = new AlertDialog.Builder(ContatoActivity.this);
                        dialog.setMessage("Deseja realmente alterar?");
                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new PessoaDAO(ContatoActivity.this).alterar(pessoa);
                                MessageBox.show(ContatoActivity.this, null, "Contato alterado com sucesso!",
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
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ContatoActivity.this);
                        dialog.setMessage("Deseja realmente excluir?");
                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new ContatoDAO(ContatoActivity.this).excluir(pkContato);
                                MessageBox.show(ContatoActivity.this, null, "Contato excluído com sucesso!",
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
                }
            } catch (Exception e) {
                MessageBox.show(ContatoActivity.this, "Erro", e.getMessage(), null);
            }
        } else {
            Snackbar.make(v, "Preencha todos os campos marcados com um (*) asterisco!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    } // Fim do método Contato

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
