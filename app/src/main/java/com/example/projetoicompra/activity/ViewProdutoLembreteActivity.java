package com.example.projetoicompra.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.ItemListAdapter;
import com.example.projetoicompra.model.Produto;

import java.util.List;

public class ViewProdutoLembreteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItem;
    private ICompraViewModel iCompraViewModel;
    private String id ;
    private Integer id_lembrete=0;

    public static final String EXTRA_PASSAR_ID_LEMBRETE = "com.exemple.projetoicompra.activity.EXTRA_PASSAR_ID_LEMBRETE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produto_lembrete);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        //configura o recycler
        recyclerViewItem = findViewById(R.id.recycler_item);
        setTitle("Produtos do Lembrete");

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setHasFixedSize(true);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_PASSAR_ID_LEMBRETE)){
            id_lembrete = Integer.valueOf(intent.getStringExtra(EXTRA_PASSAR_ID_LEMBRETE));
        }


        ItemListAdapter adapteritem = new ItemListAdapter();

        recyclerViewItem.setAdapter(adapteritem);

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_ProdutosQueEstaLembrete(id_lembrete).observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(List<Produto> produtos) {
                adapteritem.setProdutos(produtos);
            }
        });

        new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                iCompraViewModel.deleteVm_Produto(adapteritem.getPosicaoProduto(viewHolder.getAdapterPosition()));
                Toast.makeText(ViewProdutoLembreteActivity.this, R.string.msg_delete_produto, Toast.LENGTH_SHORT).show();

            }
        })).attachToRecyclerView(recyclerViewItem);
    }
}
