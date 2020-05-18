package com.example.projetoicompra.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Lista_Compra;

public class AdicionarListaManualActivity extends AppCompatActivity {

    public static final String EXTRA_PASSAR_NOME_LOCAL = "com.example.projetoicompra.activity.EXTRA_NOME_LOCAL";
    public static final String EXTRA_PASSAR_CNPJ_LOCAL = "com.example.projetoicompra.activity.EXTRA_CNPJ_LOCAL";
    public static final String EXTRA_PASSAR_END_LOCAL = "com.example.projetoicompra.activity.EXTRA_END_LOCAL";
    public static final String EXTRA_PASSAR_NUM_NOTA = "com.example.projetoicompra.activity.EXTRA_NUM_NOTA";
    public static final String EXTRA_PASSAR_DATA_COMPRA = "com.example.projetoicompra.activity.EXTRA_DATA_COMPRA";
    public static final String EXTRA_PASSAR_HORA_COMPRA = "com.example.projetoicompra.activity.EXTRA_HORA_COMPRA";
    public static final String EXTRA_PASSAR_TOTAL_COMPRA = "com.example.projetoicompra.activity.EXTRA_TOTAL_COMPRA";
    public static final String EXTRA_PASSAR_CNPJ_LOCALADDLOCAL = "com.example.projetoicompra.activity.EXTRA_PASSAR_CNPJ_LOCAL";


    private ICompraViewModel iCompraViewModel;


    private static final int REQUEST_CODE_ADD_ITEM = 1;
    private static final int PASSAR_NUM_NOTA = 3;

    static boolean edicao = false;

    //private EditText nome_local;
    //protected EditText cnpj_local;
    //private EditText endereco_local;
    private EditText num_nota_fiscal;
    private EditText data_compra;
    private EditText hora_compra;
    private EditText total_compra;
    //private TextView total_compra;

    String cnpjlocal;
    Integer numnotafiscal;
    String numnotafiscalString;
    String datacompra;
    String horacompra;
    String totalcompra;
    //String latitude;
    //String longitude;

    Double somatotalcompra = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lista_manual);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle("Adicionar Lista");

        //findViewById
        //nome_local = findViewById(R.id.nome_local);
        //cnpj_local = findViewById(R.id.cnpj_local);
        //endereco_local = findViewById(R.id.end_local);
        num_nota_fiscal = findViewById(R.id.num_nota_fiscal);
        data_compra = findViewById(R.id.data_compra);
        hora_compra = findViewById(R.id.hora_compra);
        total_compra = findViewById(R.id.total_compra);

        Intent intencaocnpj = getIntent();
        if(intencaocnpj.hasExtra(EXTRA_PASSAR_CNPJ_LOCALADDLOCAL)){
            cnpjlocal = intencaocnpj.getStringExtra(EXTRA_PASSAR_CNPJ_LOCALADDLOCAL);
        }

        Intent intencao = getIntent();
        if (intencao.hasExtra(EXTRA_PASSAR_NUM_NOTA)) {
            setTitle("Editar Lista");

            cnpjlocal = intencao.getStringExtra(EXTRA_PASSAR_CNPJ_LOCAL);
            numnotafiscalString = intencao.getStringExtra(EXTRA_PASSAR_NUM_NOTA);
            datacompra = intencao.getStringExtra(EXTRA_PASSAR_DATA_COMPRA);
            horacompra = intencao.getStringExtra(EXTRA_PASSAR_HORA_COMPRA);
            totalcompra = intencao.getStringExtra(EXTRA_PASSAR_TOTAL_COMPRA);


            //Toast.makeText(this, cnpjlocal+" "+numnotafiscalString+" "+data_compra+" "+hora_compra, Toast.LENGTH_SHORT).show();

            //cnpj_local.setText(cnpjlocal);
            num_nota_fiscal.setText(numnotafiscalString);
            data_compra.setText(datacompra);
            hora_compra.setText(horacompra);
            total_compra.setText(totalcompra);

            edicao = true;
        }

        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);


        //botão de adicionar produto
        /*FloatingActionButton fab = findViewById(R.id.fab_add_produto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencao = new Intent(getApplicationContext(), AdicionarItemActivity.class);
                startActivityForResult(intencao, REQUEST_CODE_ADD_ITEM);
            }
        });*/
    }

    //Metodo que é chamado apos o retorno da intent acionado no botão de adicionar produto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void salvarLista() {
        //tabela local
        //String nomelocal = nome_local.getText().toString();
        //cnpjlocal = cnpj_local.getText().toString();
        //String enderecolocal = endereco_local.getText().toString();

        //tabela lista
        numnotafiscalString = num_nota_fiscal.getText().toString();
        //SimpleDateFormat dataformato = new SimpleDateFormat("dd/MM/yyyy");
        datacompra = data_compra.getText().toString();
        //Date datacompra = dataformato.parse(datacomprastring);
        //SimpleTimeZone horaformato = new SimpleTimeZone("HHmmss" );
        horacompra = hora_compra.getText().toString();

        //total da compra vai precisar da soma dos itens;
        totalcompra = total_compra.getText().toString();


        //if (latitude==null || longitude == null && edicao==false){
          //  Toast.makeText(this, "Por favor Confirme o endereço", Toast.LENGTH_SHORT).show();

        //}else{

            if (/*nomelocal.trim().isEmpty() ||*/ cnpjlocal.trim().isEmpty() || datacompra.trim().isEmpty() || numnotafiscalString.trim().isEmpty()) {
                Toast.makeText(this, "Por favor revise os campos e os preencha", Toast.LENGTH_SHORT).show();
                return;
            } else {
                numnotafiscal = Integer.parseInt(num_nota_fiscal.getText().toString());

                if (edicao == true) {
                    Lista_Compra listaCompra = new Lista_Compra(horacompra, datacompra, numnotafiscal, totalcompra, cnpjlocal);
                    iCompraViewModel.updateVm_ListaCompra(listaCompra);

                    Toast.makeText(this, "Lista Atualizada Com suscesso", Toast.LENGTH_SHORT).show();

                    edicao = false;
                    finish();
                } else {
                    // public Local_Compra(@NonNull String cnpj_local, @NonNull String razao_social, String coordenadas, String latitude, String longitude)
                    //Local_Compra localCompra = new Local_Compra(cnpjlocal, nomelocal, enderecolocal, latitude, longitude);
                    //iCompraViewModel.insertVm_LocalCompra(localCompra);
                    //Toast.makeText(this, latitude+" "+longitude, Toast.LENGTH_SHORT).show();

                    //public Lista_Compra(@NonNull String hora_compra, @NonNull String data_compra, @NonNull String nota_fiscal, @NonNull String total_compra, @NonNull String cnpj_local_lista)
                    Lista_Compra listaCompra = new Lista_Compra(horacompra, datacompra, numnotafiscal, totalcompra, cnpjlocal);
                    iCompraViewModel.insertVm_ListaCompra(listaCompra);

                    Toast.makeText(this, "Lista Salva! ", Toast.LENGTH_SHORT).show();

                    Intent intencao = new Intent(getApplicationContext(), AdicionarEditItemActivity.class);
                    intencao.putExtra(AdicionarEditItemActivity.NUM_NOTA_FISCAL, numnotafiscal.toString());
                    startActivityForResult(intencao, PASSAR_NUM_NOTA);
                    finish();

                }
            }
        //}

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_salvar_lista, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSalvarLista:
                salvarLista();
                return true;
            case R.id.itemVerListaProduto:
                numnotafiscal = Integer.parseInt(num_nota_fiscal.getText().toString());
                Intent intencao = new Intent(getApplicationContext(), ViewProdutoListaActivity.class);
                intencao.putExtra(ViewProdutoListaActivity.NUM_NOTA_FISCAL, numnotafiscal.toString());
                startActivityForResult(intencao, PASSAR_NUM_NOTA);
                edicao = false;
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}