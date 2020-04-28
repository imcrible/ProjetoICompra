package com.example.projetoicompra.BD;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;

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

public class ICompraRepositorio {

    //inicio instancia acesso classes DAO
    private Item_Produto_ListaDAO itemProdutoListaDAO;
    private Lista_CompraDAO listaCompraDAO;
    private Local_CompraDAO localCompraDAO;
    private ProdutoDAO produtoDAO;
    //fim instancia acesso classes DAO


    //Inicio instancia do tipo liveData que consulta as tabelas
    //Se adicionar algum query a mais nos DAO, é necessário fazer sua instancia aqui.
    private LiveData<List<Produto>> re_TodosProdutos;
    private LiveData<List<Local_Compra>> re_TodoLocalCompra;
    private LiveData<List<Lista_Compra>> re_TodaListaCompra;
    private LiveData<List<Produto>> re_ProdutosQueEstaLista ;
    private LiveData<List<Lista_Compra>> re_ListaPorProdutos;
    private LiveData<Integer> re_LastIdProduto;
    private LiveData<Integer> reLastIdListaCompra;
    //fim instancia do tipo liveData que consulta as tabelas

    static Integer n;

    public ICompraRepositorio(Application application) {
        //Linha abaixo faz a instancia do banco de dados passando como paramento a aplicação
        ICompraDataBase icompraDataBase = ICompraDataBase.getInstance(application);


        //criação de objetos para uso nessa classe, puxando da DAO
        itemProdutoListaDAO = icompraDataBase.itemProdutoListaDAO();

        re_ListaPorProdutos = itemProdutoListaDAO.getListaPorProdutos(n);
        re_LastIdProduto = itemProdutoListaDAO.getLastIdProduto();
        reLastIdListaCompra = itemProdutoListaDAO.getLastIdListaCompra();

        listaCompraDAO = icompraDataBase.listaCompraDAO();
        re_TodaListaCompra = listaCompraDAO.getTodaListaCompra();

        localCompraDAO = icompraDataBase.localCompraDAO();
        re_TodoLocalCompra = localCompraDAO.getTodoLocalCompra();

        produtoDAO = icompraDataBase.produtoDAO();
        re_TodosProdutos = produtoDAO.getTodosProdutos();


        //fim criação de objetos para uso nessa classe, puxando da DAO

    }

    //Inicio bloco insert do repositorio, aqui que as outras classes vão se comunicar
    public void insertRe_Produto(Produto produto) {
        new InsertProdutoAsyncTask(produtoDAO).execute(produto);
    }

    public void insertRe_ListaCompra(Lista_Compra listaCompra) {
        new InsertListaCompraAsyncTask(listaCompraDAO).execute(listaCompra);
    }

    public void insertRe_LocalCompra(Local_Compra localCompra) {
        new InsertLocalCompraAsyncTask(localCompraDAO).execute(localCompra);
    }

    public void insertRe_ItemProdutoLista(Item_Produto_Lista itemProdutoLista) {
        new InsertItemProdutoListaAsyncTask(itemProdutoListaDAO).execute(itemProdutoLista);
    }

    //Fim bloco insert do repositorio, aqui que as outras classes vão se comunicar


    //Inicio bloco liveData/select que vai se comunicar com outras classes
    public LiveData<List<Produto>> getRe_TodosProdutos() {
        return re_TodosProdutos;
    }

    public LiveData<List<Lista_Compra>> getRe_TodaListaCompra() {
        return re_TodaListaCompra;
    }

    public LiveData<List<Local_Compra>> getRe_TodoLocalCompra() {
        return re_TodoLocalCompra;
    }

    public LiveData<List<Produto>> getRe_ProdutosQueEstaLista(Integer num) {

        return re_ProdutosQueEstaLista = itemProdutoListaDAO.getProdutosQueEstaLista(num);
    }

    public LiveData<List<Lista_Compra>> getRe_ListaPorProdutos() {
        return re_ListaPorProdutos;
    }

    public LiveData<Integer> getRe_LastIdProduto(){
        return re_LastIdProduto;
    }

    public LiveData<Integer> getRe_LastIdListaCompra(){
        return reLastIdListaCompra;
    }
    //Fim bloco liveData/select que vai se comunicar com outras classes


    //inicio bloco Insert Async Task

    private static class InsertProdutoAsyncTask extends AsyncTask<Produto, Void, Void> {
        private ProdutoDAO produtoDAO;

        private InsertProdutoAsyncTask(ProdutoDAO produtoDAO) {
            this.produtoDAO = produtoDAO;
        }

        @Override
        protected Void doInBackground(Produto... produtos) {
            produtoDAO.insertProduto(produtos[0]);
            return null;
        }
    }

    private static class InsertListaCompraAsyncTask extends AsyncTask<Lista_Compra, Void, Void> {
        private Lista_CompraDAO listaCompraDAO;

        private InsertListaCompraAsyncTask(Lista_CompraDAO lista_compraDAO) {
            this.listaCompraDAO = lista_compraDAO;
        }

        @Override
        protected Void doInBackground(Lista_Compra... lista_compras) {
            listaCompraDAO.insertListaCompra(lista_compras[0]);
            return null;
        }
    }

    private static class InsertLocalCompraAsyncTask extends AsyncTask<Local_Compra, Void, Void> {
        private Local_CompraDAO localCompraDAO;

        private InsertLocalCompraAsyncTask(Local_CompraDAO local_compraDAO) {
            this.localCompraDAO = local_compraDAO;
        }

        @Override
        protected Void doInBackground(Local_Compra... local_compras) {
            localCompraDAO.insertLocalCompra(local_compras[0]);
            return null;
        }
    }

    private static class InsertItemProdutoListaAsyncTask extends AsyncTask<Item_Produto_Lista, Void, Void> {
        private Item_Produto_ListaDAO itemProdutoListaDAO;

        private InsertItemProdutoListaAsyncTask(Item_Produto_ListaDAO itemProdutoListaDAO) {
            this.itemProdutoListaDAO = itemProdutoListaDAO;
        }

        @Override
        protected Void doInBackground(Item_Produto_Lista... item_produto_listas) {
            itemProdutoListaDAO.insertItemProdutoLista(item_produto_listas[0]);
            return null;
        }
    }

    //Fim do bloco Insert Async Task


}


    /*ICompraRepositorio(Application application){
        IcompraDataBase bd = IcompraDataBase.getInstance(application);
        produtoDAO = bd.produtoDAO();
        getRe_TodosProdutos = produtoDAO.getTodosProdutos();

        localCompraDAO = bd.localCompraDAO();
        getRe_TodoLocalCompra = localCompraDAO.getTodoLocalCompra();

        listaCompraDAO = bd.listaCompraDAO();
        getRe_TodaListaCompra = listaCompraDAO.getTodaListaCompra();

        int n=0;
        itemProdutoListaDAO = bd.itemProdutoListaDAO();
        /*Precisa passar dado getRe_ProdutosQueEstaLista = itemProdutoListaDAO.getProdutosQueEstaLista(n) ;
        /*Precisa passar dado getRe_ListaPorProdutos = itemProdutoListaDAO.getListaPorProdutos(n);

    }*/










