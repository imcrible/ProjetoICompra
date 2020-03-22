package com.example.projetoicompra.ui.scan;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetoicompra.activity.AdicionarComprasActivity;
import com.example.projetoicompra.R;

public class ScanFragment extends Fragment {

    private TextView textoScan;


    public ScanFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intencao = new Intent(getContext(), AdicionarComprasActivity.class);
        startActivity(intencao);

        //textoScan = textoScan.findViewById(R.id.textScan);

        //String recuperado = intencao.getIntent().getSerializableExtra("");


        return inflater.inflate(R.layout.fragment_scan, container, false);
    }


}
