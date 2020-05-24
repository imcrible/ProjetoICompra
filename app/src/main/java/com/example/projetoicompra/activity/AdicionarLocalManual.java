package com.example.projetoicompra.activity;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.model.BaseEndereco;
import com.example.projetoicompra.model.Local_Compra;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle(R.string.titulo_adicionar_local_manual);

        nome_local = findViewById(R.id.nome_local);
        cnpj_local = findViewById(R.id.cnpj_local);
        endereco_local = findViewById(R.id.end_local);

        //linha abaixo responsavel por instanciar todos os metodos da classe viewmodel
        iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);

    }

    public void verificarEndereco(View view) {
        String verificarendereco = endereco_local.getText().toString();

        if ((verificarendereco.equals("") || verificarendereco != null)) {
            Address enderecoverificado = recuperarendereco(verificarendereco);

            if (enderecoverificado != null) {
                BaseEndereco baseEndereco = new BaseEndereco();
                baseEndereco.setCidade(enderecoverificado.getAdminArea());
                baseEndereco.setCep(enderecoverificado.getPostalCode());
                baseEndereco.setBairro(enderecoverificado.getSubLocality());
                baseEndereco.setRua(enderecoverificado.getThoroughfare());
                baseEndereco.setNumero(enderecoverificado.getFeatureName());
                baseEndereco.setLatitude(String.valueOf(enderecoverificado.getLatitude()));
                baseEndereco.setLongitude(String.valueOf(enderecoverificado.getLongitude()));

                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Cidade: " + baseEndereco.getCidade());
                mensagem.append("\nRua: " + baseEndereco.getRua());
                mensagem.append("\nBairro: " + baseEndereco.getBairro());
                mensagem.append("\nNúmero: " + baseEndereco.getNumero());
                mensagem.append("\nCEP: " + baseEndereco.getCep());

                Context context;
                AlertDialog.Builder confirmaEnd = new AlertDialog.Builder(this)
                        .setTitle(R.string.dialog_titulo_confirmar_endereco)
                        .setMessage(mensagem)
                        .setPositiveButton(R.string.dialog_botao_confirmar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                endereco_local.setText(baseEndereco.getRua() + " " + baseEndereco.getNumero() + " - " + baseEndereco.getCep());
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
        } else {
            Toast.makeText(this, R.string.msg_endereco_vazio, Toast.LENGTH_LONG).show();
        }

    }

    private Address recuperarendereco(String endereco) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> listaEndereco = geocoder.getFromLocationName(endereco, 1);
            if (listaEndereco != null && listaEndereco.size() > 0) {
                Address endformat = listaEndereco.get(0);
                return endformat;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void salvarLocal() {
        nomelocal = nome_local.getText().toString();
        cnpjlocal = cnpj_local.getText().toString();
        enderecolocal = endereco_local.getText().toString();

        if (latitude == null || longitude == null) {
            Toast.makeText(this, R.string.msg_lat_long_null, Toast.LENGTH_SHORT).show();

        } else {
            if (nomelocal.trim().isEmpty() || cnpjlocal.trim().isEmpty()) {
                Toast.makeText(this, R.string.msg_tela_vazia, Toast.LENGTH_SHORT).show();
                return;
            } else {

                if(!isCNPJ(cnpjlocal)){
                    Toast.makeText(this, R.string.msg_cnpj_invalido, Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    cnpjlocal = formataCNPJ(cnpjlocal);
                    cnpj_local.setText(cnpjlocal);
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

    public static boolean isCNPJ(String CNPJ) {
        CNPJ = CNPJ.replaceAll("[^0-9]+", "");
        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14)) {
            return (false);
        }
        char dig13, dig14;
        int sm, i, r, num, peso;
        // "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-ésimo caractere do CNPJ em um número:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posição de '0' na tabela ASCII)
                num = (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

            // Verifica se os dígitos calculados conferem com os dígitos informados.
            return (dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13));

        } catch (
                InputMismatchException erro) {
            return (false);
        }
    }

    public static String formataCNPJ(String CNPJ){
        // máscara do CNPJ: 99.999.999.9999-99
        return(CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." +
                CNPJ.substring(5, 8) + "." + CNPJ.substring(8, 12) + "-" +
                CNPJ.substring(12, 14));
    }
}
