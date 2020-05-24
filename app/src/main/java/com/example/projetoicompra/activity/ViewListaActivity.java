package com.example.projetoicompra.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.ListaListAdapter;
import com.example.projetoicompra.model.Lista_Compra;

import java.util.List;

public class ViewListaActivity extends AppCompatActivity {

    private ICompraViewModel iCompraViewModel;
    private RecyclerView recyclerViewLista;
    private static final int EDITAR_LISTA=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lista);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle(R.string.titulo_view_compras);

        recyclerViewLista = findViewById(R.id.recycler_lista);

        recyclerViewLista.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLista.setHasFixedSize(true);

        final ListaListAdapter adapterlista = new ListaListAdapter();
        recyclerViewLista.setAdapter(adapterlista);


        iCompraViewModel = ViewModelProviders.of(this).get(ICompraViewModel.class);
        //trecho abaixo responsavel por exibir e manter atualizado a lista de compras muita atenção com o onChanged
        iCompraViewModel.getVm_TodaListaCompra().observe(this, new Observer<List<Lista_Compra>>() {
            @Override
            public void onChanged(List<Lista_Compra> lista_compras) {
                adapterlista.setListacompras(lista_compras);

            }
        });

        //Trecho que habilita o swipe, onde atraves disso é feito a ação de apagar a lista
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                iCompraViewModel.deleteVm_ListaCompra(adapterlista.getPosicaoListaCompra(viewHolder.getAdapterPosition()));
                Toast.makeText(ViewListaActivity.this, R.string.msg_delete_lista, Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerViewLista);

        //trecho abaixo habilita o clique em um item da View, onde abre para edição
        adapterlista.setOnItemClickListener(new ListaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lista_Compra listaCompra) {
                Intent intencao = new Intent(ViewListaActivity.this, AdicionarListaManualActivity.class);

                intencao.putExtra(AdicionarListaManualActivity.EXTRA_PASSAR_CNPJ_LOCAL, listaCompra.getCnpj_local_lista());
                intencao.putExtra(AdicionarListaManualActivity.EXTRA_PASSAR_DATA_COMPRA, listaCompra.getData_compra());
                intencao.putExtra(AdicionarListaManualActivity.EXTRA_PASSAR_HORA_COMPRA, listaCompra.getHora_compra());
                intencao.putExtra(AdicionarListaManualActivity.EXTRA_PASSAR_NUM_NOTA, listaCompra.getNota_fiscal().toString());
                intencao.putExtra(AdicionarListaManualActivity.EXTRA_PASSAR_TOTAL_COMPRA, listaCompra.getTotal_compra().toString());

                startActivityForResult(intencao, EDITAR_LISTA );
            }
        });

    }
}
