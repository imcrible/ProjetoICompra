package com.example.projetoicompra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;

import java.util.ArrayList;
import java.util.List;

public class ListaListAdapter extends RecyclerView.Adapter<ListaListAdapter.ListaViewHolder> {
    private List<Lista_Compra> listacompras = new ArrayList<>();
    private List<Local_Compra> localCompras = new ArrayList<>();
    private OnItemClickListener ouvidor;

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViewlista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_lista, parent, false);

        return new ListaViewHolder(itemViewlista);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {

        if(listacompras !=null){
        Lista_Compra listaatual = listacompras.get(position);
        //Local_Compra localatual = localCompras.get(position);
        holder.data_compra.setText(listaatual.getData_compra());
        //holder.nome_local.setText(localatual.getRazao_social());
        holder.nome_local.setText(listaatual.getCnpj_local_lista());
        holder.total_compra.setText(listaatual.getTotal_compra());
        }else{
        holder.nome_local.setText("Sem Listas Adicionadas");
        }
    }

    @Override
    public int getItemCount() {
        return listacompras.size();
    }



    public void setListacompras(List<Lista_Compra> listacompras) {
        this.listacompras = listacompras;
        notifyDataSetChanged();
    }

    public Lista_Compra getPosicaoListaCompra(int position){
        return listacompras.get(position);
    }


    class ListaViewHolder extends RecyclerView.ViewHolder {
        private TextView total_compra;
        private TextView nome_local;
        private TextView data_compra;

        private ListaViewHolder(View listaview) {
            super(listaview);
            total_compra = listaview.findViewById(R.id.rv_total_compra);
            nome_local = listaview.findViewById(R.id.rv_nome_local_lista);
            data_compra = listaview.findViewById(R.id.rv_data_compra);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicao = getAdapterPosition();
                    if(ouvidor != null && posicao != RecyclerView.NO_POSITION){
                        ouvidor.onItemClick(listacompras.get(posicao));
                        //ouvidor.onItemClick(listacompras.get(posicao), localCompras.get(posicao));
                    }
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(Lista_Compra listaCompra);
        //void onItemClick(Lista_Compra listaCompra, Local_Compra localCompra);
    }

    public void setOnItemClickListener(OnItemClickListener ouvidor){
        this.ouvidor = ouvidor;
    }
}
