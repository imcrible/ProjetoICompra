package com.example.projetoicompra.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.DAO.Item_Produto_ListaDAO;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.ItemListAdapter;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Produto;

import java.util.List;

public class ListarProdutoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItem;
    private ICompraViewModel iCompraViewModel;
    private Lista_Compra listaCompra;
    private Produto produto;

    private String nunnotafiscal;
    Integer nnf;
    private static final int PASSAR_NUM_NOTA=3;
    public static final String NUM_NOTA_FISCAL = "com.example.projetoicompra.activity.NUM_NOTA_FISCAL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produto);

        //configura o recycler
        recyclerViewItem = findViewById(R.id.recycler_item);

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setHasFixedSize(true);

        Intent intencaonunnota = getIntent();
        if(intencaonunnota.hasExtra(NUM_NOTA_FISCAL)){
            nunnotafiscal = intencaonunnota.getStringExtra(NUM_NOTA_FISCAL);
            nnf = Integer.parseInt(nunnotafiscal);
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



        /*iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodosProdutos().observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(@Nullable final List<Produto> produtos) {
                adapteritem.setProdutos(produtos);
            }
        });*/

        /*LiveData<Integer> listidproduto = iCompraViewModel.getVm_LastIdProduto() ;
        int idproduto = listidproduto.getValue();

        LiveData<Integer> listidlistaCompra = iCompraViewModel.getVm_LastIdListaCompra();
        int idlistaCompra = listidlistaCompra.getValue();

        //public Item_Produto_Lista(int produto_item_id, int lista_item_compra_id)
        Item_Produto_Lista itemProdutoLista = new Item_Produto_Lista(idproduto, idlistaCompra);

        iCompraViewModel.insertVm_ItemProdutoLista(itemProdutoLista);*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_item, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdicionarProduto:

                Intent intencao = new Intent(getApplicationContext(), AdicionarItemActivity.class);
                intencao.putExtra(AdicionarItemActivity.NUM_NOTA_FISCAL, nunnotafiscal);
                startActivity(intencao);
                return true;
            case R.id.itemConfirmar:
                //finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
