package com.example.projetoicompra.ui.historico;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.activity.MainActivity;
import com.example.projetoicompra.activity.ViewListaActivity;
import com.example.projetoicompra.adapter.ListaListAdapter;
import com.example.projetoicompra.model.Lista_Compra;

import java.util.List;

public class HistoricoFragment extends Fragment {

    private ICompraViewModel iCompraViewModel;
    private RecyclerView recyclerViewLista;


    public HistoricoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        Intent intencao = new Intent(getContext(), ViewListaActivity.class);
        startActivity(intencao);


        recyclerViewLista = view.findViewById(R.id.recycler_lista);

        recyclerViewLista.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLista.setHasFixedSize(true);

        final ListaListAdapter adapterlista = new ListaListAdapter();
        recyclerViewLista.setAdapter(adapterlista);


        iCompraViewModel = ViewModelProviders.of(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodaListaCompra().observe(getActivity(), new Observer<List<Lista_Compra>>() {
            @Override
            public void onChanged(List<Lista_Compra> lista_compras) {
                adapterlista.setListacompras(lista_compras);
                //Toast.makeText(getContext(), "Era para aparecer a lista", Toast.LENGTH_SHORT).show();
            }
        });


        return inflater.inflate(R.layout.fragment_historico, container, false);
    }

}
