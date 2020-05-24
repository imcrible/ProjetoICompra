package com.example.projetoicompra.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.ItemListAdapter;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Produto;

import java.util.List;

public class ViewProdutoListaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItem;
    private ICompraViewModel iCompraViewModel;
    private Lista_Compra listaCompra;
    private Produto produto;

    private String nunnotafiscal;
    Integer nnf;
    private String valortotalprodutostring;
    Double valor_total_produto;
    Double sumvltotproduto =0.0;

    private static final int PASSAR_NUM_NOTA=3;
    private static final int EDITAR_PRODUTO=2;
    public static final String NUM_NOTA_FISCAL = "com.example.projetoicompra.activity.NUM_NOTA_FISCAL";
    public static final String EXTRA_RECEBER_VL_TOTAL_PRODUTO = "com.example.projetoicompra.activity.EXTRA_PASSAR_VL_TOTAL_PRODUTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produto_lista);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle(R.string.titulo_view_produtos_lista);

        //configura o recycler
        recyclerViewItem = findViewById(R.id.recycler_item);

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setHasFixedSize(true);

        Intent intencaonunnota = getIntent();
        if(intencaonunnota.hasExtra(NUM_NOTA_FISCAL)){
            nunnotafiscal = intencaonunnota.getStringExtra(NUM_NOTA_FISCAL);
            nnf = Integer.parseInt(nunnotafiscal);

            valortotalprodutostring = intencaonunnota.getStringExtra(EXTRA_RECEBER_VL_TOTAL_PRODUTO);
        }

        ItemListAdapter adapteritem = new ItemListAdapter();

        recyclerViewItem.setAdapter(adapteritem);

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_ProdutosQueEstaLista(nnf).observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(@Nullable final List<Produto> produtos) {
                adapteritem.setProdutos(produtos);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                iCompraViewModel.deleteVm_Produto(adapteritem.getPosicaoProduto(viewHolder.getAdapterPosition()));
                Toast.makeText(ViewProdutoListaActivity.this, R.string.msg_delete_produto, Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerViewItem);

        adapteritem.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Produto produto) {
                Intent intencao = new Intent(ViewProdutoListaActivity.this, AdicionarProdutoListaActivity.class);

                intencao.putExtra(AdicionarProdutoListaActivity.EXTRA_PASSAR_CODIGO_PRODUTO, produto.getCodigo_produto().toString());
                intencao.putExtra(AdicionarProdutoListaActivity.EXTRA_PASSAR_NOME_PRODUTO, produto.getNome_produto());
                intencao.putExtra(AdicionarProdutoListaActivity.EXTRA_PASSAR_QTD_PRODUTO, produto.getQuantidade());
                intencao.putExtra(AdicionarProdutoListaActivity.EXTRA_PASSAR_VL_UNIT_PRODUTO, produto.getPreco_produto());
                intencao.putExtra(AdicionarProdutoListaActivity.EXTRA_PASSAR_VL_TOTAL_PRODUTO, produto.getPreco_total());
                intencao.putExtra(AdicionarProdutoListaActivity.PASSAR_NUM_NOTA_FISCAL, nnf.toString());

                startActivityForResult(intencao, EDITAR_PRODUTO);
                finish();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_produto_lista, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdicionarProduto:

                Intent intencao = new Intent(getApplicationContext(), AdicionarProdutoListaActivity.class);
                intencao.putExtra(AdicionarProdutoListaActivity.PASSAR_NUM_NOTA_FISCAL, nunnotafiscal);
                startActivity(intencao);
                return true;
            case R.id.itemConfirmar:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
