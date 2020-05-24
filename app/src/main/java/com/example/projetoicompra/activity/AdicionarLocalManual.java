package com.example.projetoicompra.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.model.BaseEndereco;
import com.example.projetoicompra.model.Local_Compra;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AdicionarLocalManual extends AppCompatActivity {
    private ICompraViewModel iCompraViewModel;

    private EditText nome_local;
    protected EditText cnpj_local;
    private EditText endereco_local;

    String nomelocal;
    String cnpjlocal;
    String enderecolocal;
    String latitude;
    String longitude;

    public static final int PASSAR_CPNP_LOCAL = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_local_manual);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle(R.string.titulo_adicionar_local_manual);

        nome_local = findViewById(R.id.nome_local);
        cnpj_local = findViewById(R.id.cnpj_local);
        endereco_local = findViewById(R.id.end_local);

        //linha abaixo responsavel por instanciar todos os metodos da classe viewmodel
        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);

    }

    public void verificarEndereco(View view){
        String verificarendereco = endereco_local.getText().toString();

        if( (verificarendereco.equals("") || verificarendereco != null) ){
            Address enderecoverificado = recuperarendereco(verificarendereco);

            if(enderecoverificado != null){
                BaseEndereco baseEndereco = new BaseEndereco();
                baseEndereco.setCidade(enderecoverificado.getAdminArea());
                baseEndereco.setCep(enderecoverificado.getPostalCode());
                baseEndereco.setBairro(enderecoverificado.getSubLocality());
                baseEndereco.setRua(enderecoverificado.getThoroughfare());
                baseEndereco.setNumero(enderecoverificado.getFeatureName());
                baseEndereco.setLatitude(String.valueOf(enderecoverificado.getLatitude()));
                baseEndereco.setLongitude(String.valueOf(enderecoverificado.getLongitude()));

                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Cidade: "+ baseEndereco.getCidade());
                mensagem.append("\nRua: "+ baseEndereco.getRua());
                mensagem.append("\nBairro: "+ baseEndereco.getBairro());
                mensagem.append("\n Número: "+ baseEndereco.getNumero());
                mensagem.append("\nCEP: "+ baseEndereco.getCep());

                Context context;
                AlertDialog.Builder confirmaEnd = new AlertDialog.Builder(this)
                        .setTitle(R.string.dialog_titulo_confirmar_endereco)
                        .setMessage(mensagem)
                        .setPositiveButton(R.string.dialog_botao_confirmar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                endereco_local.setText(baseEndereco.getRua()+" "+baseEndereco.getNumero()+" - "+baseEndereco.getCep());
                                latitude = baseEndereco.getLatitude();
                                longitude = baseEndereco.getLongitude();
                                salvarLocal();
                            }
                        }).setNegativeButton(R.string.dialog_botao_cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });

                AlertDialog dialog = confirmaEnd.create();
                dialog.show();

            }
        }else{
            Toast.makeText(this, R.string.msg_endereco_vazio, Toast.LENGTH_LONG).show();
        }

    }

    private Address recuperarendereco(String endereco){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> listaEndereco = geocoder.getFromLocationName(endereco, 1);
            if(listaEndereco != null && listaEndereco.size()>0){
                Address endformat = listaEndereco.get(0);
                return endformat;
            }
        }catch (IOException e ){
            e.printStackTrace();
        }

        return null;
    }

    private void salvarLocal(){
        nomelocal = nome_local.getText().toString();
        cnpjlocal = cnpj_local.getText().toString();
        enderecolocal = endereco_local.getText().toString();

        if (latitude==null || longitude == null ){
            Toast.makeText(this, R.string.msg_lat_long_null, Toast.LENGTH_SHORT).show();

        }else{
            if (nomelocal.trim().isEmpty() || cnpjlocal.trim().isEmpty() ) {
                Toast.makeText(this, R.string.msg_tela_vazia, Toast.LENGTH_SHORT).show();
                return;
            } else {
                // public Local_Compra(@NonNull String cnpj_local, @NonNull String razao_social, String coordenadas, String latitude, String longitude)
                Local_Compra localCompra = new Local_Compra(cnpjlocal, nomelocal, enderecolocal, latitude, longitude);
                iCompraViewModel.insertVm_LocalCompra(localCompra);

                Intent intencao = new Intent(getApplicationContext(), AdicionarListaManualActivity.class);
                intencao.putExtra(AdicionarListaManualActivity.EXTRA_PASSAR_CNPJ_LOCALADDLOCAL, cnpjlocal);
                startActivityForResult(intencao, PASSAR_CPNP_LOCAL);
                Toast.makeText(this, R.string.msg_add_local_sucesso, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
