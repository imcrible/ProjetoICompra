package com.example.projetoicompra.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

    public static final String EXTRANOME_PRODUTO = "com.example.projetoicompra.activity.NOME_PRODUTO";
    public static final String EXTRAVALOR_UNIT_PRODUTO = "com.example.projetoicompra.activity.VALORUNITPRODUTO";
    public static final String EXTRAQUANTIDADE_PRODUTO = "com.example.projetoicompra.activity.QUANTIDADE_PRODUTO";
    public static final String EXTRAVALOR_TOTAL = "com.example.projetoicompra.activity.VALOR_TOTAL";



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

    private void salvarItem(){
        String nomeproduto = nome_produto.getEditText().toString();
        int qtdproduto = Integer.parseInt(quantidade_produto.getText().toString());
        Double valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());

        Double valortotalproduto = qtdproduto * valorproduto;
        valor_total.setText(valortotalproduto.toString());

        if(nomeproduto.trim().isEmpty()){
            Toast.makeText(this, "Coloque o nome do produto", Toast.LENGTH_SHORT).show();
        }

        Intent arquivo = new Intent();

        arquivo.putExtra(EXTRANOME_PRODUTO, nomeproduto);
        arquivo.putExtra(EXTRAVALOR_UNIT_PRODUTO, valorproduto);
        arquivo.putExtra(EXTRAQUANTIDADE_PRODUTO, qtdproduto);
        arquivo.putExtra(EXTRAVALOR_TOTAL, valortotalproduto);

        setResult(RESULT_OK, arquivo);
        finish();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_item, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvarItem:

                salvarItem();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }


    }
}
