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

public class LembreteListAdapter extends RecyclerView.Adapter<LembreteListAdapter.LembreteViewHolder> {
    private List<Lista_Lembrete> lista_lembretes = new ArrayList<>();
    private LembreteListAdapter.OnItemClickListener ouvidor;

    @NonNull
    @Override
    public LembreteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemViewlembrete = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_lembrete, parent, false);

        return new LembreteViewHolder(itemViewlembrete);
    }

    @Override
    public void onBindViewHolder(@NonNull LembreteViewHolder holder, int position) {
        if (lista_lembretes !=null){
            Lista_Lembrete lembreteatual = lista_lembretes.get(position);
            holder.nome_lembrete.setText(lembreteatual.getNome_lembrete());
            holder.valor_total_lembrete.setText(String.valueOf(lembreteatual.getValor_total_lembrete()));
            holder.data_lembrete.setText(lembreteatual.getData_lembrete());
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

    public Lista_Lembrete getPosicaoLembrete(int position){
        return lista_lembretes.get(position);
    }

    class LembreteViewHolder extends RecyclerView.ViewHolder{
        private TextView nome_lembrete;
        private TextView valor_total_lembrete;
        private TextView data_lembrete;



        public LembreteViewHolder(@NonNull View lembreteView) {
            super(lembreteView);

            nome_lembrete = lembreteView.findViewById(R.id.rv_nome_lembrete);
            valor_total_lembrete = lembreteView.findViewById(R.id.rv_valor_total_lembrete);
            data_lembrete = lembreteView.findViewById(R.id.rv_data_lembrete);

            itemView.setOnClickListener(v -> {
                int posicao = getAdapterPosition();
                if(ouvidor != null && posicao != RecyclerView.NO_POSITION){
                    ouvidor.onItemClick(lista_lembretes.get(posicao));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Lista_Lembrete listaLembrete);
    }

    public void setOnItemClickListener(OnItemClickListener ouvidor){
        this.ouvidor = ouvidor;
    }

}
