package com.example.projetoicompra.ui.mapas;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.activity.AdicionarComprasManualActivity;
import com.example.projetoicompra.adapter.ItemListAdapter;
import com.example.projetoicompra.adapter.ListaListAdapter;
import com.example.projetoicompra.model.Lista_Compra;

import java.util.List;


public class MapasFragment extends Fragment {

    private ICompraViewModel iCompraViewModel;

    public MapasFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        RecyclerView recyclerViewLista = (RecyclerView) view.findViewById(R.id.recycler_lista);
        ListaListAdapter adapterlista = new ListaListAdapter();
        recyclerViewLista.setAdapter(adapterlista);
        //recyclerViewLista.setLayoutManager(new LinearLayoutManager(this));

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);

        iCompraViewModel.getVm_TodaListaCompra().observe(getViewLifecycleOwner(), new Observer<List<Lista_Compra>>() {
            @Override
            public void onChanged(@Nullable final List<Lista_Compra> lista_compras) {
                adapterlista.setListacompras(lista_compras);
            }
        });


        return inflater.inflate(R.layout.fragment_mapas, container, false);
    }

}
