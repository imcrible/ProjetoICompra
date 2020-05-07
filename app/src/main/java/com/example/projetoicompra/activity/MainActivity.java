package com.example.projetoicompra.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.BD.Permissoes;
import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import android.Manifest;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ICompraViewModel iCompraViewModel;

    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final int REQUEST_CODE_ADD_MANUAL_LISTA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab_add_lista);

        Permissoes.validarPermissoes(permissoes, this, 1);

        final Activity essaactivity = this;

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencao = new Intent(essaactivity, AdicionarComprasManualActivity.class);
                //startActivityForResult(intencao, REQUEST_CODE_ADD_MANUAL_LISTA);
                startActivity(intencao);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicial, R.id.nav_historico, R.id.nav_mapas, R.id.nav_scan)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == REQUEST_CODE_ADD_MANUAL_LISTA && resultCode == RESULT_OK) {

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

           /* //public Local_Compra(@NonNull String cnpj_local, @NonNull String razao_social, String coordenadas)
            Local_Compra localCompra = new Local_Compra(cnpj_local, nome_local, end_local);
            iCompraViewModel.insertVm_LocalCompra(localCompra);

            //public Lista_Compra(@NonNull String hora_compra, @NonNull String data_compra, @NonNull String nota_fiscal, @NonNull String total_compra, @NonNull String cnpj_local_lista)
            Lista_Compra listaCompra = new Lista_Compra(hora_compra, datacomprastring, num_nota, totalcompra, cnpj_local);
            iCompraViewModel.insertVm_ListaCompra(listaCompra);

            //para adicionar a chave estrangeira
            //int idlistaCompra = listaCompra.getLista_compra_id();
            //Item_Produto_Lista itemProdutoLista = new Item_Produto_Lista(idlistaCompra);
            //iCompraViewModel.insertVm_ItemProdutoLista(itemProdutoLista);

            Toast.makeText(this, "Lista Salva", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Houve um problema para salvar a lista", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado : grantResults){
            if( permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
