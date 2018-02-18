package com.ademarazn.projetofinal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ademarazn.projetofinal.amulyakhare.textdrawable.TextDrawable;
import com.ademarazn.projetofinal.amulyakhare.textdrawable.util.ColorGenerator;
import com.ademarazn.projetofinal.app.MessageBox;
import com.ademarazn.projetofinal.dao.PessoaDAO;
import com.ademarazn.projetofinal.entidade.Contato;
import com.ademarazn.projetofinal.entidade.Pessoa;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ademar Zório Neto on 09/12/2017
 */

public class ContatoAdapter extends BaseAdapter {
    private Activity context;
    private List<Contato> contatos;

    public ContatoAdapter(Activity context, List<Contato> contatos) {
        this.context = context;
        this.contatos = contatos;
    }

    // retorna o tamanho da lista
    public int getCount() {
        return contatos.size();
    }

    // retorna um contato da lista
    public Contato getItem(int posicao) {
        return contatos.get(posicao);
    }

    // retorna a posição de um objeto na lista
    public long getItemId(int posicao) {
        return posicao;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // recupera o contato da posicao atual
        Contato contato = contatos.get(position);

        ViewHolder holder;

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.mostra_dados, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        // atualiza o valor do texto para o nome do contato
        try {
            Pessoa p = new PessoaDAO(context).buscarPorId(contato.getFkPessoa());
            holder.textNome.setText(p.getNome());
            // atualiza o valor do texto para o telefone do contato
            holder.textFone.setText(p.getTelefone());

            // get first letter of each String item
            String firstLetter = String.valueOf(p.getNome()
                    .toUpperCase(Locale.getDefault()).charAt(0));

            ColorGenerator generator = ColorGenerator.DEFAULT;
            int color = generator.getColor(p);

            // atualiza a imagem para a imagem da letra do contato
            TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter,
                    color);
            holder.img.setImageDrawable(drawable);
        } catch (Exception e) {
            MessageBox.show(context, "Erro", e.getMessage(), null);
        }

        return v;
    }

    private class ViewHolder {
        private ImageView img;
        private TextView textNome;
        private TextView textFone;

        private ViewHolder(View v) {
            img = (ImageView) v.findViewById(R.id.img);
            textNome = (TextView) v.findViewById(R.id.itemTitle);
            textFone = (TextView) v.findViewById(R.id.itemDescription);
        }
    }
}
