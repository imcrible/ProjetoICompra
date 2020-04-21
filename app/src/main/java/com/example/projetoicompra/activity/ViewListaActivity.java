package com.example.projetoicompra.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lista);

        recyclerViewLista = findViewById(R.id.recycler_lista);

        recyclerViewLista.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLista.setHasFixedSize(true);

        final ListaListAdapter adapterlista = new ListaListAdapter();
        recyclerViewLista.setAdapter(adapterlista);


        iCompraViewModel = ViewModelProviders.of(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodaListaCompra().observe(this, new Observer<List<Lista_Compra>>() {
            @Override
            public void onChanged(List<Lista_Compra> lista_compras) {
                adapterlista.setListacompras(lista_compras);
            }
        });

        /*iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodaListaCompra().observe(this, new Observer<List<Lista_Compra>>() {
            @Override
            public void onChanged(@Nullable final List<Lista_Compra> lista_compras) {
                adapterlista.setListacompras(lista_compras);
                //Toast.makeText(ViewListaActivity.this, "é pra aparecer a lista", Toast.LENGTH_SHORT).show();
            }
        });*/


    }
}