package com.example.projetoicompra.BD;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projetoicompra.DAO.Item_Produto_ListaDAO;
import com.example.projetoicompra.DAO.Lista_CompraDAO;
import com.example.projetoicompra.DAO.Local_CompraDAO;
import com.example.projetoicompra.DAO.ProdutoDAO;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;
import com.example.projetoicompra.model.Produto;

import java.util.List;

class ICompraRepositorio {

    private Item_Produto_ListaDAO itemProdutoListaDAO;
    private Lista_CompraDAO listaCompraDAO;
    private Local_CompraDAO localCompraDAO;
    private ProdutoDAO produtoDAO;

    //Se adicionar algum query a mais nos DAO, é necessário fazer sua instancia aqui.

    private LiveData<List<Produto>> getRe_TodosProdutos;
    private LiveData<List<Local_Compra>> getRe_TodoLocalCompra;
    private LiveData<List<Lista_Compra>> getRe_TodaListaCompra;
    private LiveData<List<Produto>> getRe_ProdutosQueEstaLista;
    private LiveData<List<Lista_Compra>> getRe_ListaPorProdutos;

    ICompraRepositorio(Application application){
        IcompraDataBase bd = IcompraDataBase.getInstance(application);
        produtoDAO = bd.produtoDAO();
        getRe_TodosProdutos = produtoDAO.getTodosProdutos();

        localCompraDAO = bd.localCompraDAO();
        getRe_TodoLocalCompra = localCompraDAO.getTodoLocalCompra();

        listaCompraDAO = bd.listaCompraDAO();
        getRe_TodaListaCompra = listaCompraDAO.getTodaListaCompra();

        int n=0;
        itemProdutoListaDAO = bd.itemProdutoListaDAO();
        /*Precisa passar dado*/ getRe_ProdutosQueEstaLista = itemProdutoListaDAO.getProdutosQueEstaLista(n) ;
        /*Precisa passar dado*/ getRe_ListaPorProdutos = itemProdutoListaDAO.getListaPorProdutos(n);

    }

    LiveData<List<Produto>> getRe_TodosProdutos(){
        return getRe_TodosProdutos;
    }

    LiveData<List<Local_Compra>> getRe_TodoLocalCompra(){
        return getRe_TodoLocalCompra;
    }

    LiveData<List<Lista_Compra>> getRe_TodaListaCompras(){
        return getRe_TodaListaCompra;
    }

    LiveData<List<Produto>> getRe_ProdutosQueEstaLista(){
        return getRe_ProdutosQueEstaLista;
    }

    LiveData<List<Lista_Compra>> getRe_ListaPorProdutos(){
        return getRe_ListaPorProdutos;
    }

    void insertRe_Produto (Produto produto){
        IcompraDataBase.salvaBanco.execute(() ->{
            produtoDAO.insertProduto(produto);
        });
    }

    void insertRe_LocalCompra (Local_Compra localCompra){
        IcompraDataBase.salvaBanco.execute(() ->{
            localCompraDAO.insertLocalCompra(localCompra);
        });
    }

    void insertRe_ListaCompra (Lista_Compra listaCompra){
        IcompraDataBase.salvaBanco.execute(()->{
            listaCompraDAO.insertListaCompra(listaCompra);
        });
    }

    void inserirRe_ItemProdutoLista (Item_Produto_Lista itemProdutoLista){
        IcompraDataBase.salvaBanco.execute(() ->{
            itemProdutoListaDAO.insertItemProdutoLista(itemProdutoLista);
        });
    }







}
