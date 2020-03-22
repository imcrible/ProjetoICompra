package com.example.projetoicompra.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.projetoicompra.R;
import com.example.projetoicompra.ui.scan.ScanFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AdicionarComprasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_compras);

        final Activity essaactivity = this;
        IntentIntegrator intencaoIntegradora = new IntentIntegrator(essaactivity);
        intencaoIntegradora.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intencaoIntegradora.setPrompt("Camera Scan");
        intencaoIntegradora.setCameraId(0);
        intencaoIntegradora.initiateScan();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult != null){
            if(intentResult.getContents() != null){
                alertar(intentResult.getContents());
                //Intent intencao = new Intent(this, ScanFragment.class);
                //intencao.putExtra("", intentResult.getContents());
                finish();
            }else{
                alertar("Scan Cancelado");
            }

        }else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alertar (String msg){
        Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_LONG).show();
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

        return super.onCreateOptionsMenu(menu);


    }
}
