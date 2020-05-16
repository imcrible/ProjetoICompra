package com.example.projetoicompra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Lista_Lembrete;

import java.util.ArrayList;
import java.util.List;

public class LembreteListAdapter extends RecyclerView.Adapter<LembreteListAdapter.LocalViewHolder> {


    private List<Lista_Lembrete> lista_lembretes = new ArrayList<>();

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemViewlocal = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_lembrete, parent, false);
        return new LocalViewHolder(itemViewlocal);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        if (lista_lembretes !=null){
            Lista_Lembrete lembreteatual = lista_lembretes.get(position);
            holder.nome_lembrete.setText(lembreteatual.getNome_lembrete());
            holder.valor_total_lembrete.setText(String.valueOf(lembreteatual.getValor_total_lembrete()));
            //holder.local_compra.setText();
        }else{
            holder.nome_lembrete.setText("Sem itens adicionados");
        }

    }

    @Override
    public int getItemCount() {
        return lista_lembretes.size();
    }

    public void setLista_lembretes(List<Lista_Lembrete> lista_lembretes){
        this.lista_lembretes = lista_lembretes;
        notifyDataSetChanged();
    }

    class LocalViewHolder extends RecyclerView.ViewHolder{
        private TextView nome_lembrete;
        private TextView valor_total_lembrete;
        private TextView local_compra;


        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);

            nome_lembrete = itemView.findViewById(R.id.rv_nome_lembrete);
            valor_total_lembrete = itemView.findViewById(R.id.rv_valor_total_lembrete);


        }
    }

}
