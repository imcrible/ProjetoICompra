package com.example.projetoicompra.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoicompra.R;
import com.google.android.material.textfield.TextInputLayout;

public class AdicionarItemActivity extends AppCompatActivity {

    private TextInputLayout nome_produto;
    private EditText valor_unit_produto;
    private EditText quantidade_produto;
    private TextView valor_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_item);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        nome_produto = findViewById(R.id.nome_produto);
        valor_unit_produto = findViewById(R.id.valor_unit_produto);
        quantidade_produto = findViewById(R.id.quantidade_produto);
        valor_total = findViewById(R.id.valor_total);


    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_add_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.itemSalvarItem :
                Toast.makeText(AdicionarItemActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
