package com.example.projetoicompra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Produto;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    private List<Produto> produtos = new ArrayList<>();
    private OnItemClickListener ouvidor;


    @Override
    public ItemViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemViewitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new ItemViewHolder(itemViewitem);
    }

    @Override
    public void onBindViewHolder(@NotNull ItemViewHolder holder, int position) {

        if (produtos != null) {
            Produto produtoatual = produtos.get(position);
            holder.nome_produto.setText(produtoatual.getNome_produto());
            holder.quantidade_produto.setText(String.valueOf(produtoatual.getQuantidade()));
            holder.valor_total.setText(holder.decimal2.format(produtoatual.getPreco_total()));
            //holder.valor_total.setText(holder.decimal2.format(String.valueOf(produtoatual.getPreco_total())));
            //holder.id_produto.setText(String.valueOf(produtoatual.getCodigo_produto()));
        } else {
            holder.nome_produto.setText(R.string.msg_lista_vazia);
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

    public Produto getPosicaoProduto(int posicao) {
        return produtos.get(posicao);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView valor_total;
        private TextView nome_produto;
        private TextView quantidade_produto;
        private TextView id_produto;
        private NumberFormat decimal2 = new DecimalFormat(".##");


        private ItemViewHolder(View itemView) {
            super(itemView);

            valor_total = itemView.findViewById(R.id.rv_valor_total);
            nome_produto = itemView.findViewById(R.id.rv_nome_produto);
            quantidade_produto = itemView.findViewById(R.id.rv_quantidade_produto);
            //id_produto = itemView.findViewById(R.id.rv_id_produto);

            itemView.setOnClickListener(v -> {
                int posicao = getAdapterPosition();
                if (ouvidor != null && posicao != RecyclerView.NO_POSITION) {
                    ouvidor.onItemClick(produtos.get(posicao));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Produto produto);
    }

    public void setOnItemClickListener(OnItemClickListener ouvidor) {
        this.ouvidor = ouvidor;
    }

}
