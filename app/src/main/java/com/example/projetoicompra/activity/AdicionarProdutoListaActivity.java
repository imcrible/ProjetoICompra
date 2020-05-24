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

public class AdicionarProdutoListaActivity extends AppCompatActivity {

    private EditText codigoproduto;
    private EditText nome_produto;
    private EditText valor_unit_produto;
    private EditText quantidade_produto;
    private TextView valor_total;
    private String numnotafiscal;
    Integer nnf = 1;

    Integer codigo_produto;
    String codigo_produtoString;
    String nomeproduto;
    double qtdproduto;
    Double valorproduto;
    Double valortotalproduto;

    static boolean edicao = false;

    LiveData<Integer> listidproduto;
    LiveData<Integer> listidlistaCompra;


    public static final String PASSAR_NUM_NOTA_FISCAL = "com.example.projetoicompra.activity.NUM_NOTA_FISCAL";

    public static final String EXTRA_PASSAR_CODIGO_PRODUTO = "com.example.projetoicompra.activity.EXTRA_PASSAR_CODIGO_PRODUTO";
    public static final String EXTRA_PASSAR_NOME_PRODUTO = "com.example.projetoicompra.activity.EXTRA_PASSAR_NOME_PRODUTO";
    public static final String EXTRA_PASSAR_QTD_PRODUTO = "com.example.projetoicompra.activity.EXTRA_PASSAR_QTD_PRODUTO";
    public static final String EXTRA_PASSAR_VL_UNIT_PRODUTO = "com.example.projetoicompra.activity.EXTRA_PASSAR_VL_UNIT_PRODUTO";
    public static final String EXTRA_PASSAR_VL_TOTAL_PRODUTO = "com.example.projetoicompra.activity.EXTRA_PASSAR_VL_TOTAL_PRODUTO";

    private ICompraViewModel iCompraViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_item);


        //Adiciona o botão de fechar no menu
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle(R.string.titulo_adicionar_produto);

        Intent intencao = getIntent();

        codigoproduto = findViewById(R.id.codigo_produto);
        nome_produto = findViewById(R.id.nome_produto);
        valor_unit_produto = findViewById(R.id.valor_unit_produto);
        quantidade_produto = findViewById(R.id.quantidade_produto);
        valor_total = findViewById(R.id.valor_total);

        if (intencao.hasExtra(EXTRA_PASSAR_CODIGO_PRODUTO)) {
            setTitle(R.string.titulo_editar_produto);
            codigo_produtoString = intencao.getStringExtra(EXTRA_PASSAR_CODIGO_PRODUTO);
            nomeproduto = intencao.getStringExtra(EXTRA_PASSAR_NOME_PRODUTO);
            valorproduto = intencao.getDoubleExtra(EXTRA_PASSAR_VL_UNIT_PRODUTO, 1);
            qtdproduto = intencao.getDoubleExtra(EXTRA_PASSAR_QTD_PRODUTO, 1);
            valortotalproduto = intencao.getDoubleExtra(EXTRA_PASSAR_VL_TOTAL_PRODUTO, 1);
            numnotafiscal = intencao.getStringExtra(PASSAR_NUM_NOTA_FISCAL);
            nnf = Integer.parseInt(numnotafiscal);

            codigoproduto.setText(codigo_produtoString);
            codigoproduto.setEnabled(false);
            nome_produto.setText(nomeproduto);
            valor_unit_produto.setText(valorproduto.toString());
            quantidade_produto.setText(String.valueOf(qtdproduto));
            valor_total.setText(valortotalproduto.toString());
            edicao = true;
        }

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);

        //listidproduto = iCompraViewModel.getVm_LastIdProduto();
        //listidlistaCompra = iCompraViewModel.getVm_LastIdListaCompra();

        if (intencao.hasExtra(PASSAR_NUM_NOTA_FISCAL)) {
            numnotafiscal = intencao.getStringExtra(PASSAR_NUM_NOTA_FISCAL);
            nnf = Integer.parseInt(numnotafiscal);
        }
    }

    private void salvarItem() {

        if ((nome_produto.getText().toString().isEmpty())
                ||(codigoproduto.getText().toString().isEmpty())
                || (quantidade_produto.getText().toString().isEmpty())
                ||  (valor_unit_produto.getText().toString().isEmpty())  ) {
            Toast.makeText(this, R.string.msg_tela_vazia, Toast.LENGTH_SHORT).show();
            return;
        } else {

            codigo_produto = Integer.parseInt(codigoproduto.getText().toString());
            nomeproduto = nome_produto.getText().toString();
            qtdproduto= Double.parseDouble(quantidade_produto.getText().toString());
            valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());
            if(codigo_produto< 1000){
                Toast.makeText(this, R.string.msg_min_digito_cod_produto, Toast.LENGTH_SHORT).show();
                return;
            }else {

                valortotalproduto = qtdproduto * valorproduto;
                valor_total.setText(valortotalproduto.toString());
                if (edicao == true) {
                    Produto produto = new Produto(codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                    iCompraViewModel.updateVm_Produto(produto);

                    Toast.makeText(this, R.string.msg_att_produto_sucesso, Toast.LENGTH_SHORT).show();
                    edicao = false;

                    Intent intencao = new Intent(this, ViewProdutoListaActivity.class);
                    intencao.putExtra(PASSAR_NUM_NOTA_FISCAL, nnf.toString());
                    startActivity(intencao);
                    finish();
                } else {

                    //public Produto(int codigo_produto, @NonNull String nome_produto, @NonNull Double preco_produto, int quantidade, @NonNull Double preco_total)
                    Produto produto = new Produto(codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                    iCompraViewModel.insertVm_Produto(produto);
                    Toast.makeText(this, R.string.msg_add_produto_sucesso, Toast.LENGTH_SHORT).show();
                    addChaveEstrangeira(codigo_produto);

                    Intent intent = new Intent(this, ViewProdutoListaActivity.class);
                    intent.putExtra(PASSAR_NUM_NOTA_FISCAL, nnf.toString());
                    startActivity(intent);
                    finish();
                }
            }

        }

    }

    public void addChaveEstrangeira(Integer codigo_produto) {
        //public Item_Produto_Lista(@NonNull String produto_item_id, @NonNull String lista_item_compra_id)
        Item_Produto_Lista itemProdutoLista = new Item_Produto_Lista(codigo_produto, nnf);

        iCompraViewModel.insertVm_ItemProdutoLista(itemProdutoLista);
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
