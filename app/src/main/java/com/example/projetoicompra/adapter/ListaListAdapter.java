package com.example.projetoicompra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.R;
import com.example.projetoicompra.activity.ViewListaActivity;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ListaListAdapter extends RecyclerView.Adapter<ListaListAdapter.ListaViewHolder> {
    private List<Lista_Compra> listacompras = new ArrayList<>();
    private OnItemClickListener ouvidor;

    @NotNull
    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViewlista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_lista, parent, false);

        return new ListaViewHolder(itemViewlista);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {

        if(listacompras !=null){
        Lista_Compra listaatual = listacompras.get(position);

        holder.data_compra.setText(listaatual.getData_compra());
        holder.nome_local.setText(listaatual.getRazao_social());
        holder.total_compra.setText((holder.decimal2.format(listaatual.getTotal_compra())));
        //holder.total_compra.setText(holder.decimal2.format(listaatual.getTotal_compra().toString()));
        }else{
        holder.nome_local.setText(R.string.msg_lista_vazia);
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
        private NumberFormat decimal2 = new DecimalFormat(".##");

        private ListaViewHolder(View listaview) {
            super(listaview);
            total_compra = listaview.findViewById(R.id.rv_total_compra);
            nome_local = listaview.findViewById(R.id.rv_nome_local_lista);
            data_compra = listaview.findViewById(R.id.rv_data_compra);

            itemView.setOnClickListener(v -> {
                int posicao = getAdapterPosition();
                if(ouvidor != null && posicao != RecyclerView.NO_POSITION){
                    ouvidor.onItemClick(listacompras.get(posicao));
                    //ouvidor.onItemClick(listacompras.get(posicao), localCompras.get(posicao));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Lista_Compra listaCompra);
    }

    public void setOnItemClickListener(OnItemClickListener ouvidor){
        this.ouvidor = ouvidor;
    }
}
