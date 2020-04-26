package com.example.projetoicompra.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Produto;

public class AdicionarItemActivity extends AppCompatActivity {

    private EditText nome_produto;
    private EditText valor_unit_produto;
    private EditText quantidade_produto;
    private TextView valor_total;

    public static final String EXTRANOME_PRODUTO = "com.example.projetoicompra.activity.NOME_PRODUTO";
    public static final String EXTRAVALOR_UNIT_PRODUTO = "com.example.projetoicompra.activity.VALORUNITPRODUTO";
    public static final String EXTRAQUANTIDADE_PRODUTO = "com.example.projetoicompra.activity.QUANTIDADE_PRODUTO";
    public static final String EXTRAVALOR_TOTAL = "com.example.projetoicompra.activity.VALOR_TOTAL";

    private ICompraViewModel iCompraViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_item);

        //Adiciona o botão de fechar no menu
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        nome_produto = findViewById(R.id.nome_produto);
        valor_unit_produto = findViewById(R.id.valor_unit_produto);
        quantidade_produto = findViewById(R.id.quantidade_produto);
        valor_total = findViewById(R.id.valor_total);

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);


    }

    private void salvarItem() {
        String nomeproduto = nome_produto.getText().toString();
        int qtdproduto = Integer.parseInt(quantidade_produto.getText().toString());
        Double valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());

        Double valortotalproduto = qtdproduto * valorproduto;
        valor_total.setText(valortotalproduto.toString());

        if (nomeproduto.trim().isEmpty()) {
            Toast.makeText(this, "Digite o nome do produto", Toast.LENGTH_SHORT).show();
            return;
        }else{

            //public Produto(@NonNull String nome_produto, @NonNull Double preco_produto, int quantidade, @NonNull Double preco_total)
            Produto produto = new Produto(nomeproduto, valorproduto, qtdproduto, valortotalproduto);
            iCompraViewModel.insertVm_Produto(produto);

            Toast.makeText(this, "Item salvo com sucesso!", Toast.LENGTH_SHORT).show();

            //addChaveEstrangeira();

            Intent intent = new Intent(this, ListarProdutoActivity.class);
            startActivity(intent);
        }

        /*Intent arquivo = new Intent();

        arquivo.putExtra(EXTRANOME_PRODUTO, nomeproduto);
        arquivo.putExtra(EXTRAVALOR_UNIT_PRODUTO, valorproduto);
        arquivo.putExtra(EXTRAQUANTIDADE_PRODUTO, qtdproduto);
        arquivo.putExtra(EXTRAVALOR_TOTAL, valortotalproduto);


        setResult(RESULT_OK, arquivo);*/
    }

    public void addChaveEstrangeira(){


        LiveData<Integer> listidproduto = iCompraViewModel.getVm_LastIdProduto() ;
        LiveData<Integer> listidlistaCompra = iCompraViewModel.getVm_LastIdListaCompra();
        if (listidproduto == null || listidlistaCompra== null){
            Toast.makeText(this, "ID PRODUTO ou ID LISTA vazio", Toast.LENGTH_SHORT).show();
        }else {

            int idproduto = listidproduto.getValue();

            int idlistaCompra = listidlistaCompra.getValue();

            //public Item_Produto_Lista(int produto_item_id, int lista_item_compra_id)
            Item_Produto_Lista itemProdutoLista = new Item_Produto_Lista(idproduto, idlistaCompra);

            iCompraViewModel.insertVm_ItemProdutoLista(itemProdutoLista);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_salvar_item, menu);

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
