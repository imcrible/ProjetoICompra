package com.example.projetoicompra.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.LembreteListAdapter;
import com.example.projetoicompra.model.Local_Compra;

import java.util.List;

public class ViewLembreteActivity extends AppCompatActivity {

    private ICompraViewModel iCompraViewModel;
    private RecyclerView recyclerViewLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lembrete);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        /*recyclerViewLocal = findViewById(R.id.recycler_local);

        recyclerViewLocal.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLocal.setHasFixedSize(true);

        final LembreteListAdapter adapterlocal = new LembreteListAdapter();
        recyclerViewLocal.setAdapter(adapterlocal);

        iCompraViewModel = ViewModelProviders.of(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodoLocalCompra().observe(this, new Observer<List<Local_Compra>>() {
            @Override
            public void onChanged(List<Local_Compra> local_compras) {
                adapterlocal.setLocal_compras(local_compras);
            }
        });*/
    }
}
