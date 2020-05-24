package com.example.projetoicompra.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Lista_Compra;

import java.util.Calendar;

public class AdicionarListaManualActivity extends AppCompatActivity {

    public static final String EXTRA_PASSAR_CNPJ_LOCAL = "com.example.projetoicompra.activity.EXTRA_CNPJ_LOCAL";
    public static final String EXTRA_PASSAR_NUM_NOTA = "com.example.projetoicompra.activity.EXTRA_NUM_NOTA";
    public static final String EXTRA_PASSAR_DATA_COMPRA = "com.example.projetoicompra.activity.EXTRA_DATA_COMPRA";
    public static final String EXTRA_PASSAR_HORA_COMPRA = "com.example.projetoicompra.activity.EXTRA_HORA_COMPRA";
    public static final String EXTRA_PASSAR_TOTAL_COMPRA = "com.example.projetoicompra.activity.EXTRA_TOTAL_COMPRA";
    public static final String EXTRA_PASSAR_CNPJ_LOCALADDLOCAL = "com.example.projetoicompra.activity.EXTRA_PASSAR_CNPJ_LOCAL";

    private ICompraViewModel iCompraViewModel;

    //private static final int REQUEST_CODE_ADD_ITEM = 1;
    private static final int PASSAR_NUM_NOTA = 3;

    static boolean edicao = false;

    private Button btn_data;
    private Button btn_tempo;
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
    String totalcomprastring;
    Double totalcompra;


    static final int DATA_DIALOG_ID = 0;
    static final int TEMPO_DIALOG_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lista_manual);
        
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle(R.string.titulo_adicionar_lista_manual);

        //findViewById
        num_nota_fiscal = findViewById(R.id.num_nota_fiscal);
        data_compra = findViewById(R.id.data_compra);
        hora_compra = findViewById(R.id.hora_compra);
        total_compra = findViewById(R.id.total_compra);
        btn_data = findViewById(R.id.btn_data);
        btn_tempo = findViewById(R.id.btn_tempo);

        //botão resposnsavel para abrir o dialog que usuario selecionada data
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATA_DIALOG_ID);

            }
        });

        //botão responsavel para abrir o dialog que usuario seleciona o hora e minuto
        btn_tempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TEMPO_DIALOG_ID);
            }
        });

        Intent intencaocnpj = getIntent();
        if(intencaocnpj.hasExtra(EXTRA_PASSAR_CNPJ_LOCALADDLOCAL)){
            cnpjlocal = intencaocnpj.getStringExtra(EXTRA_PASSAR_CNPJ_LOCALADDLOCAL);
        }

        Intent intencao = getIntent();
        if (intencao.hasExtra(EXTRA_PASSAR_NUM_NOTA)) {
            setTitle(R.string.titulo_editar_lista_manual);

            cnpjlocal = intencao.getStringExtra(EXTRA_PASSAR_CNPJ_LOCAL);
            numnotafiscalString = intencao.getStringExtra(EXTRA_PASSAR_NUM_NOTA);
            datacompra = intencao.getStringExtra(EXTRA_PASSAR_DATA_COMPRA);
            horacompra = intencao.getStringExtra(EXTRA_PASSAR_HORA_COMPRA);
            totalcomprastring = intencao.getStringExtra(EXTRA_PASSAR_TOTAL_COMPRA);

            num_nota_fiscal.setText(numnotafiscalString);
            num_nota_fiscal.setEnabled(false);
            data_compra.setText(datacompra);
            btn_data.setText(datacompra);
            hora_compra.setText(horacompra);
            btn_tempo.setText(horacompra);
            total_compra.setText(totalcomprastring);

            edicao = true;
        }
        //essa linha é responsavel por instanciar e deixar disponivel todos os metodos do ViewModel
        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);

    }

    //Metodo que é chamado apos o retorno da intent acionado no botão de adicionar produto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Caso precise tratar algo quando retornar, tratar aqui
    }

    private void salvarLista() {

        //tabela lista
        numnotafiscalString = num_nota_fiscal.getText().toString();
        //Data e hora já tratado no metodo Dialog abaixo.
        datacompra = data_compra.getText().toString();
        horacompra = hora_compra.getText().toString();

        //total da compra vai precisar da soma dos itens;
        totalcomprastring = total_compra.getText().toString();


            if ( cnpjlocal.trim().isEmpty() || datacompra.trim().isEmpty() || numnotafiscalString.trim().isEmpty() || horacompra.trim().isEmpty() || totalcomprastring.trim().isEmpty()) {
                Toast.makeText(this, R.string.msg_tela_vazia, Toast.LENGTH_SHORT).show();
                return;
            } else {
                numnotafiscal = Integer.parseInt(num_nota_fiscal.getText().toString());
                totalcompra = Double.valueOf(total_compra.getText().toString());

                if (edicao == true) {
                    Lista_Compra listaCompra = new Lista_Compra(horacompra, datacompra, numnotafiscal, totalcompra, cnpjlocal);
                    iCompraViewModel.updateVm_ListaCompra(listaCompra);

                    Toast.makeText(this, R.string.msg_att_lista_sucesso, Toast.LENGTH_SHORT).show();
                    edicao = false;
                    Intent intencao = new Intent(getApplicationContext(), ViewListaActivity.class);
                    //intencao.putExtra(ViewListaActivity.PASSAR_NUM_NOTA_FISCAL, numnotafiscal.toString());
                    startActivity(intencao);
                    finish();
                } else {

                    //public Lista_Compra(@NonNull String hora_compra, @NonNull String data_compra, @NonNull String nota_fiscal, @NonNull String total_compra, @NonNull String cnpj_local_lista)
                    Lista_Compra listaCompra = new Lista_Compra(horacompra, datacompra, numnotafiscal, totalcompra, cnpjlocal);
                    iCompraViewModel.insertVm_ListaCompra(listaCompra);

                    Toast.makeText(this, R.string.msg_add_lista_sucesso, Toast.LENGTH_SHORT).show();

                    Intent intencao = new Intent(getApplicationContext(), AdicionarProdutoListaActivity.class);
                    intencao.putExtra(AdicionarProdutoListaActivity.PASSAR_NUM_NOTA_FISCAL, numnotafiscal.toString());
                    startActivityForResult(intencao, PASSAR_NUM_NOTA);
                    finish();
                }
            }
    }

    public Dialog onCreateDialog(int id){
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        switch (id){
            case DATA_DIALOG_ID:
                return new DatePickerDialog(this, ouvinte_data, ano, mes, dia);
            case TEMPO_DIALOG_ID:
                return new TimePickerDialog(this, ouvinte_tempo, hora, minuto, true );
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener ouvinte_data = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String data;

            if(dayOfMonth<10 ){
                data = "0"+dayOfMonth;
            }else{
                data = String.valueOf(dayOfMonth);
            }

            if(month<10){
                data = data+"/"+"0"+(month+1);
            }else{
                data = data+"/"+(month+1);
            }

            data = data+"/"+year;

            data_compra.setText(data);
            btn_data.setText(data);
        }
    };

    private TimePickerDialog.OnTimeSetListener ouvinte_tempo = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String tempo;
            if (hourOfDay<10){
                tempo = "0"+hourOfDay;
            }else{
                tempo = String.valueOf(hourOfDay);
            }

            if(minute<10){
                tempo = tempo+":"+"0"+minute;
            }else{
                tempo = tempo+":"+minute;
            }

            hora_compra.setText(tempo);
            btn_tempo.setText(tempo);
        }
    };



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

    @Override
    protected void onStop() {
        super.onStop();
        edicao=false;
    }
}
