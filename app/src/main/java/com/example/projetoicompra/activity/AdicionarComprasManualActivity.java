package com.example.projetoicompra.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.ItemListAdapter;
import com.example.projetoicompra.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdicionarComprasManualActivity extends AppCompatActivity {

    public static final String EXTRA_NOME_LOCAL = "com.example.projetoicompra.activity.EXTRA_NOME_LOCAL";
    public static final String EXTRA_CNPJ_LOCAL = "com.example.projetoicompra.activity.EXTRA_CNPJ_LOCAL";
    public static final String EXTRA_END_LOCAL = "com.example.projetoicompra.activity.EXTRA_END_LOCAL";
    public static final String EXTRA_NUM_NOTA = "com.example.projetoicompra.activity.EXTRA_NUM_NOTA";
    public static final String EXTRA_DATA_COMPRA = "com.example.projetoicompra.activity.EXTRA_DATA_COMPRA";
    public static final String EXTRA_HORA_COMPRA = "com.example.projetoicompra.activity.EXTRA_HORA_COMPRA";
    public static final String EXTRA_TOTAL_COMPRA = "com.example.projetoicompra.activity.EXTRA_TOTAL_COMPRA";


    private ICompraViewModel iCompraViewModel;

    private RecyclerView recyclerViewItem;

    private static final int REQUEST_CODE_ADD_ITEM = 1;

    private EditText nome_local;
    protected EditText cnpj_local;
    private EditText endereco_local;
    private EditText num_nota_fiscal;
    private EditText data_compra;
    private EditText hora_compra;
    private TextView total_compra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_compras_manual);
        Toolbar toolbar = findViewById(R.id.toolbarLista);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        //findViewById
        nome_local = findViewById(R.id.nome_local);
        cnpj_local = findViewById(R.id.cnpj_local);
        endereco_local = findViewById(R.id.end_local);
        num_nota_fiscal = findViewById(R.id.num_nota_fiscal);
        data_compra = findViewById(R.id.data_compra);
        hora_compra = findViewById(R.id.hora_compra);
        total_compra = findViewById(R.id.total_compra);


        //configura o recycler
        recyclerViewItem = findViewById(R.id.recycler_item);

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setHasFixedSize(true);

        ItemListAdapter adapteritem = new ItemListAdapter();

        recyclerViewItem.setAdapter(adapteritem);

        iCompraViewModel = ViewModelProviders.of(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodosProdutos().observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(List<Produto> produtos) {
                adapteritem.setProdutos(produtos);
            }
        });

        /*iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodosProdutos().observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(@Nullable final List<Produto> produtos) {
                adapteritem.setProdutos(produtos);
                //Toast.makeText(getApplicationContext(), "Aqui era pra aparecer os itens", Toast.LENGTH_SHORT).show();
            }
        });*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencao = new Intent(getApplicationContext(), AdicionarItemActivity.class);
                startActivityForResult(intencao, REQUEST_CODE_ADD_ITEM);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            String nomeproduto = data.getStringExtra(AdicionarItemActivity.EXTRANOME_PRODUTO);
            int qtdproduto = data.getIntExtra(AdicionarItemActivity.EXTRAQUANTIDADE_PRODUTO, 0);
            Double vl_unit_produto = data.getDoubleExtra(AdicionarItemActivity.EXTRAVALOR_UNIT_PRODUTO, 0.0);
            Double valortotalproduto = data.getDoubleExtra(AdicionarItemActivity.EXTRAVALOR_TOTAL, 0.0);


            //public Produto(@NonNull String nome_produto, @NonNull Double preco_produto, int quantidade, @NonNull Double preco_total)
            Produto produto = new Produto(nomeproduto, vl_unit_produto, qtdproduto, valortotalproduto);
            iCompraViewModel.insertVm_Produto(produto);


            //para adicionar a chave estrangeira
            //int idproduto = produto.getProduto_id();
            //Item_Produto_Lista itemProdutoLista = new Item_Produto_Lista(idproduto);
            //iCompraViewModel.insertVm_ItemProdutoLista(itemProdutoLista);

            Toast.makeText(this, "Item salvo com sucesso!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Houve um problema para adicionar o item", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarLista() {
        //tabela local
        String nomelocal = nome_local.getText().toString();
        String cnpjlocal = cnpj_local.getText().toString();
        String enderecolocal = endereco_local.getText().toString();

        //tabela lista
        String numnotafiscal = num_nota_fiscal.getText().toString();
        SimpleDateFormat dataformato = new SimpleDateFormat("dd/MM/yyyy");
        String datacompra = data_compra.getText().toString();
        //Date datacompra = dataformato.parse(datacomprastring);
        //SimpleTimeZone horaformato = new SimpleTimeZone("HHmmss" );
        String horacompra = hora_compra.getText().toString();

        //total da compra vai precisar da soma dos itens;
        String totalcompra = "2.0";

        if (nomelocal.trim().isEmpty() || cnpjlocal.trim().isEmpty() || datacompra.trim().isEmpty()) {
            Toast.makeText(this, "Por favor revise os campos e os preencha", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent arquivo = new Intent();
        arquivo.putExtra(EXTRA_NOME_LOCAL, nomelocal);
        arquivo.putExtra(EXTRA_CNPJ_LOCAL, cnpjlocal);
        arquivo.putExtra(EXTRA_END_LOCAL, enderecolocal);

        arquivo.putExtra(EXTRA_NUM_NOTA, numnotafiscal);
        arquivo.putExtra(EXTRA_DATA_COMPRA, datacompra);
        arquivo.putExtra(EXTRA_HORA_COMPRA, horacompra);
        arquivo.putExtra(EXTRA_TOTAL_COMPRA, totalcompra);

        setResult(RESULT_OK, arquivo);
        finish();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_add_lista, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSalvarLista:
                salvarLista();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

}
