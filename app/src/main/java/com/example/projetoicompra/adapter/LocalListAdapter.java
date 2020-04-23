package com.example.projetoicompra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Local_Compra;
import com.example.projetoicompra.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class LocalListAdapter extends RecyclerView.Adapter<LocalListAdapter.LocalViewHolder> {


    private List<Local_Compra> local_compras = new ArrayList<>();

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemViewlocal = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_local, parent, false);
        return new LocalViewHolder(itemViewlocal);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        if (local_compras !=null){
            Local_Compra localatual = local_compras.get(position);
            holder.nome_local.setText(localatual.getRazao_social());
            holder.cnpj_local.setText(localatual.getCnpj_local());
            holder.local_compra.setText(localatual.getCoordenadas());
        }else{
            holder.nome_local.setText("Sem itens adicionados");
        }

    }

    @Override
    public int getItemCount() {
        return local_compras.size();
    }

    public void setLocal_compras(List<Local_Compra> local_compras){
        this.local_compras = local_compras;
        notifyDataSetChanged();
    }

    class LocalViewHolder extends RecyclerView.ViewHolder{
        private TextView nome_local;
        private TextView cnpj_local;
        private TextView local_compra;


        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);

            nome_local = itemView.findViewById(R.id.rv_nome_local);
            cnpj_local = itemView.findViewById(R.id.rv_cnpj_local);
            local_compra = itemView.findViewById(R.id.rv_local_compra);

        }
    }

}
