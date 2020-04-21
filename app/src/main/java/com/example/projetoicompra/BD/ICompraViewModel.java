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

    private LiveData<List<Produto>> vm_TodosProdutos;
    private LiveData<List<Local_Compra>> vm_TodoLocalCompra;
    private LiveData<List<Lista_Compra>> vm_TodaListaCompra;
    private LiveData<List<Produto>> vm_ProdutosQueEstaLista;
    private LiveData<List<Lista_Compra>> vm_ListaPorProdutos;


    public ICompraViewModel(Application application) {
        super(application);

        repositorio = new ICompraRepositorio(application);

        vm_TodosProdutos = repositorio.getRe_TodosProdutos();
        vm_TodoLocalCompra = repositorio.getRe_TodoLocalCompra();
        vm_TodaListaCompra = repositorio.getRe_TodaListaCompra();
        vm_ProdutosQueEstaLista = repositorio.getRe_ProdutosQueEstaLista();
        vm_ListaPorProdutos = repositorio.getRe_ListaPorProdutos();

    }

    public LiveData<List<Produto>> getVm_TodosProdutos(){
        return vm_TodosProdutos;
    }

    public LiveData<List<Local_Compra>> getVm_TodoLocalCompra(){
        return vm_TodoLocalCompra;
    }

    public LiveData<List<Lista_Compra>> getVm_TodaListaCompra(){
        return vm_TodaListaCompra;
    }

    public LiveData<List<Produto>> getVm_ProdutosQueEstaLista(){
        return vm_ProdutosQueEstaLista;
    }

    public LiveData<List<Lista_Compra>> getVm_ListaPorProdutos(){
        return vm_ListaPorProdutos;
    }

    public void insertVm_Produto (Produto produto){
        repositorio.insertRe_Produto(produto);
    }

    public void insertVm_LocalCompra (Local_Compra localCompra){
        repositorio.insertRe_LocalCompra(localCompra);
    }

    public void insertVm_ListaCompra (Lista_Compra listaCompra){
        repositorio.insertRe_ListaCompra(listaCompra);
    }

    public void insertVm_ItemProdutoLista (Item_Produto_Lista itemProdutoLista){
        repositorio.insertRe_ItemProdutoLista(itemProdutoLista);
    }
}
