package com.example.projetoicompra.ui.mapas;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.projetoicompra.R;
import com.example.projetoicompra.activity.ViewLocalActivity;
import com.example.projetoicompra.activity.ViewMapasActivity;


public class MapasFragment extends Fragment {


    public MapasFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = new Intent(getContext(), ViewMapasActivity.class);
        startActivity(intent);


        return inflater.inflate(R.layout.fragment_mapas, container, false);
    }

}
