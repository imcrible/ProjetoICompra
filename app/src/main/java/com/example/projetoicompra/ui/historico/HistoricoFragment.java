package com.example.projetoicompra.ui.historico;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.projetoicompra.R;
import com.example.projetoicompra.activity.ViewListaActivity;

public class HistoricoFragment extends Fragment {


    public HistoricoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        Intent intencao = new Intent(getContext(), ViewListaActivity.class);
        startActivity(intencao);


        return inflater.inflate(R.layout.fragment_historico, container, false);
    }

}
