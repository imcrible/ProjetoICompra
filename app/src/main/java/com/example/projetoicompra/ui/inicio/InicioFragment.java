package com.example.projetoicompra.ui.inicio;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.DAO.Lista_Lembrete_DAO;
import com.example.projetoicompra.R;
import com.example.projetoicompra.activity.ViewProdutoLembreteActivity;
import com.example.projetoicompra.adapter.LembreteListAdapter;
import com.example.projetoicompra.model.Item_Produto_Lembrete;
import com.example.projetoicompra.model.Lista_Lembrete;
import com.example.projetoicompra.model.Produto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class InicioFragment extends Fragment {

    private ICompraViewModel iCompraViewModel;
    private RecyclerView recyclerViewLembrete;
    View view;
    public Activity essaactivity = getActivity();
    private Button btnAddLembrete;
    Integer idparaproduto = 0;
    Integer idlembrete = 0;

    private EditText codigoproduto;
    private EditText nome_produto;
    private EditText valor_unit_produto;
    private EditText quantidade_produto;
    //private TextView valor_total_lembrete;
    private EditText nome_lembrete;
    private EditText data_lembrete;
    private Button btn_data_lembrete;
    private String numnotafiscal;

    private static final int VIEW_PRODUTO=5;
    static final int DATA_DIALOG_ID = 0;

    Boolean mostrar = false;



    Integer codigo_produto;
    String codigo_produtoString;
    String nomeproduto;
    Double qtdproduto;
    Double valorproduto;
    Double valortotalproduto;
    Double valor_total_lembrete = 0.0;
    String nomelembrete;
    String datalembrete;


    private List<Produto> produtos = new ArrayList<>();

    public InicioFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        recyclerViewLembrete = (RecyclerView) view.findViewById(R.id.recycler_lembrete);
        btnAddLembrete = view.findViewById(R.id.bnt_AddLembrete);

        recyclerViewLembrete.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLembrete.setHasFixedSize(true);

        final LembreteListAdapter adapterlembrete = new LembreteListAdapter();
        recyclerViewLembrete.setAdapter(adapterlembrete);

        iCompraViewModel = ViewModelProviders.of(getActivity()).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodaListaLembrete().observe(getViewLifecycleOwner(), new Observer<List<Lista_Lembrete>>() {
            @Override
            public void onChanged(List<Lista_Lembrete> lista_lembretes) {
                adapterlembrete.setLista_lembretes(lista_lembretes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                iCompraViewModel.deleteVm_ListaLembrete(adapterlembrete.getPosicaoLembrete(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Lembrete Apagado", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerViewLembrete);

        adapterlembrete.setOnItemClickListener(new LembreteListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lista_Lembrete listaLembrete) {
                Intent intencao = new Intent(getContext(), ViewProdutoLembreteActivity.class);

                intencao.putExtra(ViewProdutoLembreteActivity.EXTRA_PASSAR_ID_LEMBRETE, listaLembrete.getId_lembrete().toString());
                intencao.putExtra(ViewProdutoLembreteActivity.EXTRA_PASSAR_NOME_LEMBRETE, listaLembrete.getNome_lembrete().toString());
                intencao.putExtra(ViewProdutoLembreteActivity.EXTRA_PASSAR_VL_TL_LEMBRETE, listaLembrete.getValor_total_lembrete().toString());
                intencao.putExtra(ViewProdutoLembreteActivity.EXTRA_PASSAR_DATA_LEMBRETE, listaLembrete.getData_lembrete().toString());
                intencao.putExtra(ViewProdutoLembreteActivity.EXTRA_PASSAR_ULTIIDPRODINSERT, listaLembrete.getUltiidprodinsert().toString());

                startActivityForResult(intencao, VIEW_PRODUTO );

            }
        });


        btnAddLembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
                View layout = getLayoutInflater().inflate(R.layout.layout_add_lembrete, null);

                dialogo.setTitle("Adicionar Lembrete de Compra");
                dialogo.setCancelable(false);
                dialogo.setMessage("Digite nome e data para o lembrete");
                dialogo.setView(layout);

                nome_lembrete = layout.findViewById(R.id.nome_lembrete);
                data_lembrete = layout.findViewById(R.id.data_lembrete);
                btn_data_lembrete = layout.findViewById(R.id.btn_data_lembrete);
                btn_data_lembrete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCreateDialog(DATA_DIALOG_ID).show();
                    }
                });

                dialogo.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        nome_lembrete = layout.findViewById(R.id.nome_lembrete);
                        data_lembrete = layout.findViewById(R.id.data_lembrete);

                        nomelembrete = nome_lembrete.getText().toString();
                        datalembrete = data_lembrete.getText().toString();
                        mostrar = true;
                        iCompraViewModel = ViewModelProviders.of(getActivity()).get(ICompraViewModel.class);
                        // public Lista_Lembrete(String nome_lembrete, double valor_total_lembrete, String data_lembrete, Integer ultiidprodinsert)
                        //Lista_Lembrete listaLembrete = new Lista_Lembrete(nomelembrete, valor_total_lembrete, datalembrete, idparaproduto);
                        //iCompraViewModel.insertVm_ListaLembrete(listaLembrete);
                        iCompraViewModel.getVm_UltProdInsert().observe(getViewLifecycleOwner(), new Observer<List<Lista_Lembrete_DAO.Ultimo>>() {
                            @Override
                            public void onChanged(List<Lista_Lembrete_DAO.Ultimo> ultimos) {
                                if(mostrar==true) {
                                    if (ultimos.get(0).getUltidprodinsert() == null || ultimos.get(0).getUltidprodinsert() < 0) {
                                        acaoPositiva(-1, 0);
                                        return;
                                    } else {
                                        acaoPositiva(ultimos.get(0).getUltidprodinsert(), (ultimos.get(0).getId_lembrete()) + 1);
                                        return;
                                    }
                                }
                            }
                        });

                    }
                });

                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                AlertDialog dialog = dialogo.create();
                dialog.show();
            }
        });

        return view;
    }

    public Dialog onCreateDialog(int id){
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id){
            case DATA_DIALOG_ID:
                return new DatePickerDialog(getActivity(), ouvinte_data, ano, mes, dia);
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
            data_lembrete.setText(data);
            btn_data_lembrete.setText(data);
        }
    };

    public void acaoPositiva(Integer ultidprod, Integer ultidlembrete ){
        idparaproduto = ultidprod+1;
        idlembrete = ultidlembrete;

        AlertDialog.Builder alertapositivo = new AlertDialog.Builder(getContext());
        View layoutpositivo = getLayoutInflater().inflate(R.layout.layout_add_produto_lembrete, null);

        alertapositivo.setTitle("Adicionar Produto");
        alertapositivo.setView(layoutpositivo);
        alertapositivo.setCancelable(false);
        alertapositivo.setMessage("Adicione informações do produto");

        AlertDialog dialogproduto = alertapositivo.create();
        dialogproduto.show();


        Button btaddmais = (Button) layoutpositivo.findViewById(R.id.btn_addmaisprodlembrete);
        btaddmais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome_produto = layoutpositivo.findViewById(R.id.nome_produto_lembrete);
                quantidade_produto = layoutpositivo.findViewById(R.id.quantidade_produto_lembrete);
                valor_unit_produto = layoutpositivo.findViewById(R.id.valor_unit_produto_lembrete);

                if( nome_produto.getText().toString().isEmpty() ){
                    Toast.makeText(getContext(), "Erro! É necessário ao menos o nome do produto. Insira novamente o lembrete", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if ( (quantidade_produto.getText().toString().isEmpty()) || (valor_unit_produto.getText().toString().isEmpty()) ){

                        if ( (quantidade_produto.getText().toString().isEmpty()) ){
                            qtdproduto = 0.0;
                        }else{
                            qtdproduto = Double.parseDouble(quantidade_produto.getText().toString());
                        }

                        if ( (valor_unit_produto.getText().toString().isEmpty())  ){
                            valorproduto = 0.0;
                        }else{
                            valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());
                        }

                        codigo_produto = idparaproduto;
                        nomeproduto = nome_produto.getText().toString();
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
                acaoPositiva(idparaproduto, idlembrete);
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

                if ((nome_produto.getText().toString().isEmpty())){
                    Toast.makeText(getContext(), "Erro! É necessário ao menos o nome do produto. Insira novamente o lembrete", Toast.LENGTH_SHORT).show();
                    return;

                }else{
                    if(  (quantidade_produto.getText().toString().isEmpty()) || (valor_unit_produto.getText().toString().isEmpty())){

                        if ( (quantidade_produto.getText().toString().isEmpty()) ){
                            qtdproduto = 0.0;
                        }else{
                            qtdproduto = Double.parseDouble(quantidade_produto.getText().toString());
                        }

                        if ( (valor_unit_produto.getText().toString().isEmpty())  ){
                            valorproduto = 0.0;
                        }else{
                            valorproduto = Double.parseDouble(valor_unit_produto.getText().toString());
                        }

                        codigo_produto = idparaproduto;
                        nomeproduto = nome_produto.getText().toString();
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

                Lista_Lembrete listaLembrete = new Lista_Lembrete(idlembrete, nomelembrete, valor_total_lembrete, datalembrete, idparaproduto);
                iCompraViewModel.insertVm_ListaLembrete(listaLembrete);

                for (int i = 0; i < produtos.size(); i++) {
                    Produto produto = produtos.get(i);
                    iCompraViewModel.insertVm_Produto(produto);

                    Item_Produto_Lembrete itemProdutoLembrete = new Item_Produto_Lembrete(produtos.get(i).getCodigo_produto(), idlembrete);
                    iCompraViewModel.insertVm_ItemProduroLembrete(itemProdutoLembrete);
                }

                produtos.clear();
                mostrar=false;
                valor_total_lembrete = 0.0;
                dialogproduto.dismiss();

                Toast.makeText(getContext(), "Lembrete Adicionado com sucesso!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
