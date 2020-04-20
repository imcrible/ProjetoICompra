package com.example.projetoicompra.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.adapter.ListaListAdapter;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;
import com.example.projetoicompra.ui.scan.ScanFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ICompraViewModel iCompraViewModel;

    public static final int REQUEST_CODE_ADD_MANUAL_LISTA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        final Activity essaactivity = this;

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);

        iCompraViewModel.getVm_TodaListaCompra().observe(this, new Observer<List<Lista_Compra>>() {
            @Override
            public void onChanged(List<Lista_Compra> lista_compras) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intencao = new Intent(essaactivity, AdicionarComprasManualActivity.class);
               startActivityForResult(intencao, REQUEST_CODE_ADD_MANUAL_LISTA);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicial, R.id.nav_historico, R.id.nav_mapas, R.id.nav_scan )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD_MANUAL_LISTA && resultCode==RESULT_OK){

            //tabela local
            String nome_local = data.getStringExtra(AdicionarComprasManualActivity.EXTRA_NOME_LOCAL);
            String cnpj_local = data.getStringExtra(AdicionarComprasManualActivity.EXTRA_CNPJ_LOCAL);
            String end_local = data.getStringExtra(AdicionarComprasManualActivity.EXTRA_END_LOCAL);

            //tabela lista
            String num_nota = data.getStringExtra(AdicionarComprasManualActivity.EXTRA_NUM_NOTA);
            String hora_compra = data.getStringExtra(AdicionarComprasManualActivity.EXTRA_HORA_COMPRA);
            String totalcompra = data.getStringExtra(AdicionarComprasManualActivity.EXTRA_TOTAL_COMPRA);

            String datacomprastring = data.getStringExtra(AdicionarComprasManualActivity.EXTRA_DATA_COMPRA);

            SimpleDateFormat dataformato = new SimpleDateFormat("dd/MM/yyyy");
            /*Date datacompra = null;
            try {
                datacompra = dataformato.parse(data_compratring);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/


            //public Local_Compra(@NonNull String cnpj_local, @NonNull String razao_social, String coordenadas)
            Local_Compra localCompra = new Local_Compra(cnpj_local, nome_local, end_local);
            iCompraViewModel.insertVm_LocalCompra(localCompra);

            //public Lista_Compra(@NonNull String hora_compra, @NonNull Date data_compra, @NonNull String nota_fiscal, @NonNull Double total_compra, @NonNull String cnpj_local_lista)
            Lista_Compra listaCompra = new Lista_Compra(hora_compra, datacomprastring, num_nota, totalcompra, cnpj_local);
            iCompraViewModel.insertVm_ListaCompra(listaCompra);

            //para adicionar a chave estrangeira
            //int idlistaCompra = listaCompra.getLista_compra_id();
            //Item_Produto_Lista itemProdutoLista = new Item_Produto_Lista(idlistaCompra);
            //iCompraViewModel.insertVm_ItemProdutoLista(itemProdutoLista);

            Toast.makeText(this, "Lista Salva", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Houve um problema para salvar a lista", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
