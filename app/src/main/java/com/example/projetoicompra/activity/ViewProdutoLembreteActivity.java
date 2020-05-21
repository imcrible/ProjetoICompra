package com.example.projetoicompra.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.DAO.Lista_Lembrete_DAO;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.ItemListAdapter;
import com.example.projetoicompra.model.Item_Produto_Lembrete;
import com.example.projetoicompra.model.Lista_Lembrete;
import com.example.projetoicompra.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ViewProdutoLembreteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItem;
    private ICompraViewModel iCompraViewModel;
    private String id ;
    private Integer id_lembrete=0;
    private String nome_lembrete;
    private String data_lembrete;
    private Integer ultiidinsetprod;

    Integer idparaproduto;
    Integer codigo_produto;
    String codigo_produtoString;
    String nomeproduto;
    Double qtdproduto;
    Double valorproduto;
    Double valortotalproduto;
    Double valor_total_lembrete = 0.0;
    String nomelembrete;
    String datalembrete;

    private EditText nome_produto;
    private EditText valor_unit_produto;
    private EditText quantidade_produto;

    private List<Produto> produtos = new ArrayList<>();

    public static final String EXTRA_PASSAR_ID_LEMBRETE = "com.exemple.projetoicompra.activity.EXTRA_PASSAR_ID_LEMBRETE";
    public static final String EXTRA_PASSAR_NOME_LEMBRETE = "com.exemple.projetoicompra.activity.EXTRA_PASSAR_NOME_LEMBRETE";
    public static final String EXTRA_PASSAR_VL_TL_LEMBRETE = "com.exemple.projetoicompra.activity.EXTRA_PASSAR_TL_LEMBRETE";
    public static final String EXTRA_PASSAR_DATA_LEMBRETE = "com.exemple.projetoicompra.activity.EXTRA_PASSAR_DATA_LEMBRETE";
    public static final String EXTRA_PASSAR_ULTIIDPRODINSERT = "com.exemple.projetoicompra.activity.EXTRA_PASSAR_ULTIIDPRODINSERT";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produto_lembrete);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        //configura o recycler
        recyclerViewItem = findViewById(R.id.recycler_item);
        setTitle("Produtos do Lembrete");

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setHasFixedSize(true);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_PASSAR_ID_LEMBRETE)){
            id_lembrete = Integer.valueOf(intent.getStringExtra(EXTRA_PASSAR_ID_LEMBRETE));
            nome_lembrete = intent.getStringExtra(EXTRA_PASSAR_NOME_LEMBRETE);
            valor_total_lembrete = Double.valueOf(intent.getStringExtra(EXTRA_PASSAR_VL_TL_LEMBRETE));
            data_lembrete = intent.getStringExtra(EXTRA_PASSAR_DATA_LEMBRETE);
            ultiidinsetprod = Integer.valueOf(intent.getStringExtra(EXTRA_PASSAR_ULTIIDPRODINSERT));
            //idparaproduto = ultiidinsetprod;
        }


        ItemListAdapter adapteritem = new ItemListAdapter();

        recyclerViewItem.setAdapter(adapteritem);

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_ProdutosQueEstaLembrete(id_lembrete).observe(this, new Observer<List<Produto>>() {
            @Override
            public void onChanged(List<Produto> produtos) {
                adapteritem.setProdutos(produtos);
            }
        });

        iCompraViewModel.getVm_UltProdInsert().observe(this, new Observer<List<Lista_Lembrete_DAO.Ultimo>>() {
            @Override
            public void onChanged(List<Lista_Lembrete_DAO.Ultimo> ultimos) {
                ultimoProduto(ultimos.get(0).getUltidprodinsert());
            }
        });

        new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                iCompraViewModel.deleteVm_Produto(adapteritem.getPosicaoProduto(viewHolder.getAdapterPosition()));
                Double valor_tl_lembrete = valor_total_lembrete - adapteritem.getPosicaoProduto(viewHolder.getAdapterPosition()).getPreco_total();
                Lista_Lembrete listaLembrete = new Lista_Lembrete(id_lembrete, nome_lembrete, valor_tl_lembrete, data_lembrete, idparaproduto);
                iCompraViewModel.updateVm_ListaLembrete(listaLembrete);
                Toast.makeText(ViewProdutoLembreteActivity.this, R.string.msg_delete_produto, Toast.LENGTH_SHORT).show();

            }
        })).attachToRecyclerView(recyclerViewItem);

        adapteritem.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Produto produto) {
                Integer alt_codigo_produto = produto.getCodigo_produto();
                String alt_nome_produto = produto.getNome_produto();
                Double alt_valor_produto = produto.getPreco_produto();
                Double alt_qtd_produto = produto.getQuantidade();
                Double alt_vl_tl_produto = produto.getPreco_total();

                alterarProduto(alt_codigo_produto, alt_nome_produto, alt_valor_produto, alt_qtd_produto, alt_vl_tl_produto);
            }
        });

    }

    private void alterarProduto(Integer alt_codigo_produto, String alt_nome_produto, Double alt_valor_produto, Double alt_qtd_produto, Double alt_vl_tl_produto) {

        AlertDialog.Builder alterar_produto = new AlertDialog.Builder(this);
        alterar_produto.setTitle("Alterar Produto");
        alterar_produto.setMessage("Altere as informações do produto");
        View layourproduto = getLayoutInflater().inflate(R.layout.activity_adicionar_produto_lembrete, null);
        alterar_produto.setView(layourproduto);

        nome_produto = layourproduto.findViewById(R.id.nome_produto_lembrete);
        quantidade_produto = layourproduto.findViewById(R.id.quantidade_produto_lembrete);
        valor_unit_produto = layourproduto.findViewById(R.id.valor_unit_produto_lembrete);

        nome_produto.setText(alt_nome_produto);
        quantidade_produto.setText(alt_qtd_produto.toString());
        valor_unit_produto.setText(alt_valor_produto.toString());

        Button btaddmais = (Button) layourproduto.findViewById(R.id.btn_addmaisprodlembrete);
        Button btnconcluilembrete = layourproduto.findViewById(R.id.btn_concluirprodlembrete);
        btaddmais.setVisibility(View.INVISIBLE);
        btnconcluilembrete.setVisibility(View.INVISIBLE);

        alterar_produto.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Produto alt_produto;

                nome_produto = layourproduto.findViewById(R.id.nome_produto_lembrete);
                quantidade_produto = layourproduto.findViewById(R.id.quantidade_produto_lembrete);
                valor_unit_produto = layourproduto.findViewById(R.id.valor_unit_produto_lembrete);

                if( nome_produto.getText().toString().isEmpty() ){
                    Toast.makeText(getApplication(), "Preencha o nome do produto", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if( (quantidade_produto.getText().toString().isEmpty())
                            || (valor_unit_produto.getText().toString().isEmpty()) ){

                        nomeproduto = nome_produto.getText().toString();
                        qtdproduto = 0.0;
                        valorproduto = 0.0;
                        valortotalproduto = qtdproduto * valorproduto;;
                        valor_total_lembrete = valor_total_lembrete + valortotalproduto - alt_vl_tl_produto;

                        alt_produto = new Produto(alt_codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);

                    }else{
                        nomeproduto = nome_produto.getText().toString();
                        qtdproduto = Double.parseDouble(quantidade_produto.getText().toString());
                        valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());
                        valortotalproduto = qtdproduto * valorproduto;
                        valor_total_lembrete = valor_total_lembrete + valortotalproduto - alt_vl_tl_produto;

                        alt_produto = new Produto(alt_codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                    }
                }

                Lista_Lembrete listaLembrete = new Lista_Lembrete(id_lembrete, nomelembrete, valor_total_lembrete, datalembrete, alt_codigo_produto);
                iCompraViewModel.updateVm_Produto(alt_produto);
                iCompraViewModel.updateVm_ListaLembrete(listaLembrete);

                Toast.makeText(ViewProdutoLembreteActivity.this, "Produto atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        alterar_produto.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        Dialog dialog_alt_protudo = alterar_produto.create();
        dialog_alt_protudo.show();
    }

    private void ultimoProduto(Integer ultimoprodinsert){
        idparaproduto = ultimoprodinsert;
    }

    public void adicionarProduto(){
        idparaproduto = idparaproduto +1;

        AlertDialog.Builder adicionar_produto = new AlertDialog.Builder(this);
        View layoutpositivo = getLayoutInflater().inflate(R.layout.activity_adicionar_produto_lembrete, null);

        adicionar_produto.setTitle("Adicionar Produto");
        adicionar_produto.setView(layoutpositivo);
        adicionar_produto.setMessage("Adicione informações do produto");

        AlertDialog dialogproduto = adicionar_produto.create();
        dialogproduto.show();

        Button btaddmais = (Button) layoutpositivo.findViewById(R.id.btn_addmaisprodlembrete);
        btaddmais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome_produto = layoutpositivo.findViewById(R.id.nome_produto_lembrete);
                quantidade_produto = layoutpositivo.findViewById(R.id.quantidade_produto_lembrete);
                valor_unit_produto = layoutpositivo.findViewById(R.id.valor_unit_produto_lembrete);

                if(nome_produto.getText().toString().isEmpty() ){
                    Toast.makeText(getApplication(), "Preencha o nome do produto", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if ( (quantidade_produto.getText().toString().isEmpty())
                            || (valor_unit_produto.getText().toString().isEmpty()) ){
                        codigo_produto = idparaproduto;
                        nomeproduto = nome_produto.getText().toString();
                        qtdproduto = 0.0;
                        valorproduto = 0.0;
                        valortotalproduto = qtdproduto * valorproduto;
                        valor_total_lembrete = valor_total_lembrete + valortotalproduto;

                        Produto produto = new Produto(codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                        produtos.add(produto);

                    }else{
                        codigo_produto = idparaproduto;
                        nomeproduto = nome_produto.getText().toString();
                        qtdproduto = Double.parseDouble(quantidade_produto.getText().toString());
                        valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());
                        valortotalproduto = qtdproduto * valorproduto;
                        valor_total_lembrete = valor_total_lembrete + valortotalproduto;

                        Produto produto = new Produto(codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                        produtos.add(produto);
                    }
                }
                adicionarProduto();
                dialogproduto.dismiss();

            }
        });

        Button btnconcluilembrete = layoutpositivo.findViewById(R.id.btn_concluirprodlembrete);
        btnconcluilembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome_produto = layoutpositivo.findViewById(R.id.nome_produto_lembrete);
                quantidade_produto = layoutpositivo.findViewById(R.id.quantidade_produto_lembrete);
                valor_unit_produto = layoutpositivo.findViewById(R.id.valor_unit_produto_lembrete);

                if( (nome_produto.getText().toString().isEmpty()) ){
                    Toast.makeText(getApplication(), "Erro! É necessário ao menos o nome do produto. Insira novamente o lembrete", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(  (quantidade_produto.getText().toString().isEmpty()) || (valor_unit_produto.getText().toString().isEmpty())){
                        codigo_produto = idparaproduto;
                        nomeproduto = nome_produto.getText().toString();
                        qtdproduto = 0.0;
                        valorproduto = 0.0;
                        valortotalproduto = qtdproduto * valorproduto;;
                        valor_total_lembrete = valor_total_lembrete + valortotalproduto;

                        Produto produto = new Produto(codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                        produtos.add(produto);
                    }else{
                        codigo_produto = idparaproduto;
                        nomeproduto = nome_produto.getText().toString();
                        qtdproduto = Double.parseDouble(quantidade_produto.getText().toString());
                        valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());
                        valortotalproduto = qtdproduto * valorproduto;
                        valor_total_lembrete = valor_total_lembrete + valortotalproduto;

                        Produto produto = new Produto(codigo_produto, nomeproduto, valorproduto, qtdproduto, valortotalproduto);
                        produtos.add(produto);
                    }
                }

                Lista_Lembrete listaLembrete = new Lista_Lembrete(id_lembrete, nome_lembrete, valor_total_lembrete, data_lembrete, idparaproduto);
                iCompraViewModel.updateVm_ListaLembrete(listaLembrete);

                for (int i = 0; i < produtos.size(); i++){
                    Produto produto = produtos.get(i);
                    iCompraViewModel.insertVm_Produto(produto);

                    Item_Produto_Lembrete itemProdutoLembrete = new Item_Produto_Lembrete(produtos.get(i).getCodigo_produto(), id_lembrete);
                    iCompraViewModel.insertVm_ItemProduroLembrete(itemProdutoLembrete);
                }
                Toast.makeText(getApplication(), "Produto Adicionado com sucesso!", Toast.LENGTH_SHORT).show();

                produtos.clear();
                dialogproduto.dismiss();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_produto_lembrete, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemaddProdutoLembrete:
                adicionarProduto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}





