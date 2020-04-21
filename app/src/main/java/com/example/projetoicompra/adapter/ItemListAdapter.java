package com.example.projetoicompra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    private List<Produto> produtos = new ArrayList<>();


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViewitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new ItemViewHolder(itemViewitem);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        if (produtos != null) {
            Produto produtoatual = produtos.get(position);
            holder.nome_produto.setText(produtoatual.getNome_produto());
            holder.quantidade_produto.setText(String.valueOf(produtoatual.getQuantidade()));
            holder.valor_total.setText(String.valueOf(produtoatual.getPreco_total()));
        } else {
            holder.nome_produto.setText("Sem itens adicionados");
        }


    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView valor_total;
        private TextView nome_produto;
        private TextView quantidade_produto;


        public ItemViewHolder(View itemView) {
            super(itemView);

            valor_total = itemView.findViewById(R.id.rv_valor_total);
            nome_produto = itemView.findViewById(R.id.rv_nome_produto);
            quantidade_produto = itemView.findViewById(R.id.rv_quantidade_produto);
        }
    }


}
