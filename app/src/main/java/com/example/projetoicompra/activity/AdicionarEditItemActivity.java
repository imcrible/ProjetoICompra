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
import com.example.projetoicompra.DAO.Item_Produto_ListaDAO;
import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Produto;

public class AdicionarEditItemActivity extends AppCompatActivity {

    private EditText codigoproduto;
    private EditText nome_produto;
    private EditText valor_unit_produto;
    private EditText quantidade_produto;
    private TextView valor_total;
    private String numnotafiscal;
    Integer nnf=1;
    static boolean edicao = false;

    LiveData<Integer> listidproduto;
    LiveData<Integer> listidlistaCompra;


    public static final String EXTRACODIGO_PRODUTO = "com.example.projetoicompra.activity.CODIGO_PRODUTO";
    public static final String EXTRANOME_PRODUTO = "com.example.projetoicompra.activity.NOME_PRODUTO";
    public static final String EXTRAVALOR_UNIT_PRODUTO = "com.example.projetoicompra.activity.VALORUNITPRODUTO";
    public static final String EXTRAQUANTIDADE_PRODUTO = "com.example.projetoicompra.activity.QUANTIDADE_PRODUTO";
    public static final String EXTRAVALOR_TOTAL = "com.example.projetoicompra.activity.VALOR_TOTAL";
    public static final String NUM_NOTA_FISCAL = "com.example.projetoicompra.activity.NUM_NOTA_FISCAL";


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
        Intent intencaoedit = getIntent();

        if(intencaoedit.hasExtra(EXTRA_PASSAR_CODIGO_PRODUTO)){
            setTitle("Editar Produto");

            codigoproduto.setText(intencaoedit.getStringExtra(EXTRA_PASSAR_CODIGO_PRODUTO));
            nome_produto.setText(intencaoedit.getStringExtra(EXTRA_PASSAR_NOME_PRODUTO));
            valor_unit_produto.setText((int) intencaoedit.getDoubleExtra(EXTRA_PASSAR_VL_UNIT_PRODUTO, 1));
            quantidade_produto.setText(intencaoedit.getIntExtra(EXTRA_PASSAR_QTD_PRODUTO, 1));
            valor_total.setText(intencaoedit.getIntExtra(EXTRA_PASSAR_VL_TOTAL_PRODUTO,1));
            edicao = true;
        }else {
            setTitle("Adicionar Produto");
        }



        codigoproduto = findViewById(R.id.codigo_produto);
        nome_produto = findViewById(R.id.nome_produto);
        valor_unit_produto = findViewById(R.id.valor_unit_produto);
        quantidade_produto = findViewById(R.id.quantidade_produto);
        valor_total = findViewById(R.id.valor_total);

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);

       listidproduto = iCompraViewModel.getVm_LastIdProduto();
       listidlistaCompra = iCompraViewModel.getVm_LastIdListaCompra();


       Intent intencaonumnota = getIntent();
       if (intencaonumnota.hasExtra(NUM_NOTA_FISCAL)){
           numnotafiscal = intencaonumnota.getStringExtra(NUM_NOTA_FISCAL);
           //Toast.makeText(this, numnotafiscal, Toast.LENGTH_SHORT).show();
           nnf = Integer.parseInt(numnotafiscal);
       }

    }

    private void salvarItem() {
        Integer codigo_produto = Integer.parseInt( codigoproduto.getText().toString() );
        String nomeproduto = nome_produto.getText().toString();
        int qtdproduto = Integer.parseInt(quantidade_produto.getText().toString());
        Double valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());

        Double valortotalproduto = qtdproduto * valorproduto;
        valor_total.setText(valortotalproduto.toString());

        if (nomeproduto.trim().isEmpty()) {
            Toast.makeText(this, "Digite o nome do produto", Toast.LENGTH_SHORT).show();
            return;
        }else{

            if (edicao){
                Produto produto = new Produto(codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                iCompraViewModel.updateVm_Produto(produto);

                Toast.makeText(this, "Produto Atualizado com Sucesso", Toast.LENGTH_SHORT).show();


                edicao = false;
            }else {

                //public Produto(int codigo_produto, @NonNull String nome_produto, @NonNull Double preco_produto, int quantidade, @NonNull Double preco_total)
                Produto produto = new Produto(codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                iCompraViewModel.insertVm_Produto(produto);

                Toast.makeText(this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show();

                addChaveEstrangeira(codigo_produto);

            }

            Intent intent = new Intent(this, ListarProdutoActivity.class);
            intent.putExtra(NUM_NOTA_FISCAL, nnf.toString());
            startActivity(intent);
        }

        /*Intent arquivo = new Intent();

        arquivo.putExtra(EXTRANOME_PRODUTO, nomeproduto);
        arquivo.putExtra(EXTRAVALOR_UNIT_PRODUTO, valorproduto);
        arquivo.putExtra(EXTRAQUANTIDADE_PRODUTO, qtdproduto);
        arquivo.putExtra(EXTRAVALOR_TOTAL, valortotalproduto);


        setResult(RESULT_OK, arquivo);*/
    }

    public void addChaveEstrangeira(Integer codigo_produto){

            //public Item_Produto_Lista(@NonNull String produto_item_id, @NonNull String lista_item_compra_id)


            Item_Produto_Lista itemProdutoLista = new Item_Produto_Lista(codigo_produto, nnf);

            Toast.makeText(this, codigo_produto+" "+nnf, Toast.LENGTH_SHORT).show();

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
