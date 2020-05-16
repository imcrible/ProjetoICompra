package com.example.projetoicompra.ui.inicio;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.LembreteListAdapter;
import com.example.projetoicompra.model.Lista_Lembrete;
import com.example.projetoicompra.model.Local_Compra;

import java.util.List;


public class InicioFragment extends Fragment {

    private ICompraViewModel iCompraViewModel;
    private RecyclerView recyclerViewLocal;
    View view;
    public Activity essaactivity;
    private Button btnAddLembrete;


    public InicioFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final InicioFragment essaactivity = this;
        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        recyclerViewLocal = (RecyclerView) view.findViewById(R.id.recycler_lembrete);
        btnAddLembrete = view.findViewById(R.id.bnt_AddLembrete);

        recyclerViewLocal.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLocal.setHasFixedSize(true);

        final LembreteListAdapter adapterlembrete = new LembreteListAdapter();
        recyclerViewLocal.setAdapter(adapterlembrete);

        iCompraViewModel = ViewModelProviders.of(getActivity()).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodaListaLembrete().observe(getViewLifecycleOwner(), new Observer<List<Lista_Lembrete>>() {
            @Override
            public void onChanged(List<Lista_Lembrete> lista_lembretes) {
                adapterlembrete.setLista_lembretes(lista_lembretes);
            }
        });

        btnAddLembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
                View layout = getLayoutInflater().inflate(R.layout.layout_add_lembrete, null);

                dialogo.setTitle("Adicionar Lembrete");
                dialogo.setMessage("Digite um nome para o lembrete");
                dialogo.setView(layout);

                dialogo.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                AlertDialog dialog = dialogo.create();
                dialog.show();
            }
        });


        return view;
    }

}
