package com.example.projetoicompra.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoicompra.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AdicionarComprasScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_compras_scan);

        //trecho necessario para iniciar leitua de qrcode
        final Activity essaactivity = this;
        IntentIntegrator intencaoIntegradora = new IntentIntegrator(essaactivity);
        intencaoIntegradora.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intencaoIntegradora.setPrompt("Camera Scan");
        intencaoIntegradora.setCameraId(0);
        intencaoIntegradora.initiateScan();
        //fim trecho necessario
    }

    //inicio dos metodos relacionados ao qrcode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() != null) {
                alertar(intentResult.getContents());
                //Intent intencao = new Intent(this, ScanFragment.class);
                //intencao.putExtra("", intentResult.getContents());
                finish();
            } else {
                alertar("Scan Cancelado");
                //tentar adicionar algum metodo de atraso antes do finish para lançar o toast
                finish();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alertar(String msg) {
        abrirLink(msg);
    }
    //fim dos metodos relacionados ao qrcode

    //metodo com intent explicita de abrir o navegador
    public void abrirLink(String link) {
        Uri pagina = Uri.parse(link);

        Intent intent = new Intent(Intent.ACTION_VIEW, pagina);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
