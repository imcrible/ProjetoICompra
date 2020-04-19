package com.example.projetoicompra.BD;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;
import com.example.projetoicompra.model.Produto;

import java.util.List;

public class ICompraViewModel extends AndroidViewModel {

    private ICompraRepositorio repositorio;

    private LiveData<List<Produto>> getVm_TodosProdutos;
    private LiveData<List<Local_Compra>> getVm_TodoLocalCompra;
    private LiveData<List<Lista_Compra>> getVm_TodaListaCompra;
    private LiveData<List<Produto>> getVm_ProdutosQueEstaLista;
    private LiveData<List<Lista_Compra>> getVm_ListaPorProdutos;


    public ICompraViewModel(@NonNull Application application) {
        super(application);

        repositorio = new ICompraRepositorio(application);

        getVm_TodosProdutos = repositorio.getRe_TodosProdutos();
        getVm_TodoLocalCompra = repositorio.getRe_TodoLocalCompra();
        getVm_TodaListaCompra = repositorio.getRe_TodaListaCompras();
        getVm_ProdutosQueEstaLista = repositorio.getRe_ProdutosQueEstaLista();
        getVm_ListaPorProdutos = repositorio.getRe_ListaPorProdutos();

    }

    LiveData<List<Produto>> getVm_TodosProdutos(){
        return getVm_TodosProdutos;
    }

    LiveData<List<Local_Compra>> getVm_TodoLocalCompra(){
        return getVm_TodoLocalCompra;
    }

    LiveData<List<Lista_Compra>> getVm_TodaListaCompra(){
        return getVm_TodaListaCompra;
    }

    LiveData<List<Produto>> getVm_ProdutosQueEstaLista(){
        return getVm_ProdutosQueEstaLista;
    }

    LiveData<List<Lista_Compra>> getVm_ListaPorProdutos(){
        return getVm_ListaPorProdutos;
    }

    void insertVm_Produto (Produto produto){
        repositorio.insertRe_Produto(produto);
    }

    void insertVm_LocalCompra (Local_Compra localCompra){
        repositorio.insertRe_LocalCompra(localCompra);
    }

    void insertVm_ListaCompra (Lista_Compra listaCompra){
        repositorio.insertRe_ListaCompra(listaCompra);
    }

    void insertVm_ItemProdutoLista (Item_Produto_Lista itemProdutoLista){
        repositorio.inserirRe_ItemProdutoLista(itemProdutoLista);
    }
}
