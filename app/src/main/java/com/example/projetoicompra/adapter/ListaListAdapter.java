package com.example.projetoicompra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Lista_Compra;

import java.util.ArrayList;
import java.util.List;

public class ListaListAdapter extends RecyclerView.Adapter<ListaListAdapter.ListaViewHolder> {
    private List<Lista_Compra> listacompras = new ArrayList<>();


    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemViewlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_lista, parent, false);

        return new ListaViewHolder(itemViewlista);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        if(listacompras !=null){
            Lista_Compra listaatual = listacompras.get(position);
            holder.data_compra.setText(listaatual.getData_compra());
            holder.nome_local.setText(listaatual.getCnpj_local_lista());
        }else{
            holder.nome_local.setText("Sem Listas Adicionadas");
        }


    }

    @Override
    public int getItemCount() {
        if(listacompras != null){
            return listacompras.size();
        }else return 0;
    }

    public void setListacompras(List<Lista_Compra> listacompras){
        this.listacompras = listacompras;
        notifyDataSetChanged();
    }

    class ListaViewHolder extends RecyclerView.ViewHolder{
        private final TextView total_compra;
        private final TextView nome_local;
        private final TextView data_compra;

        private ListaViewHolder (View listaview){
            super(listaview);
            total_compra = listaview.findViewById(R.id.rv_total_compra);
            nome_local = listaview.findViewById(R.id.rv_nome_local);
            data_compra = listaview.findViewById(R.id.rv_data_compra);


        }

    }
}