package com.example.projetoicompra.BD;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projetoicompra.DAO.Lista_Lembrete_DAO;
import com.example.projetoicompra.model.Item_Produto_Lembrete;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Lista_Lembrete;
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
    private LiveData<Integer> vm_LastIdProduto;
    private LiveData<Integer> vm_LastIdListaCompra;
    private LiveData<List<Lista_Lembrete>> vm_TodaListaLembrete;
    private LiveData<List<Lista_Lembrete_DAO.Ultimo>> vm_UltProdInsert;
    private LiveData<List<Produto>> vm_ProdutosQueEstaLembrete;

    static Integer n;

    public ICompraViewModel(@NonNull Application application) {
        super(application);

        repositorio = new ICompraRepositorio(application);

        vm_TodosProdutos = repositorio.getRe_TodosProdutos();
        vm_TodoLocalCompra = repositorio.getRe_TodoLocalCompra();
        vm_TodaListaCompra = repositorio.getRe_TodaListaCompra();

        vm_LastIdProduto = repositorio.getRe_LastIdProduto();
        vm_LastIdListaCompra= repositorio.getRe_LastIdListaCompra();

        vm_TodaListaLembrete = repositorio.getRe_TodaListaLembrete();
        vm_UltProdInsert = repositorio.getRe_UltProdInsert();

    }

    public LiveData<List<Produto>> getVm_TodosProdutos() {
        return vm_TodosProdutos;
    }

    public LiveData<List<Local_Compra>> getVm_TodoLocalCompra() {
        return vm_TodoLocalCompra;
    }

    public LiveData<List<Lista_Compra>> getVm_TodaListaCompra() {
        return vm_TodaListaCompra;
    }

    public LiveData<List<Produto>> getVm_ProdutosQueEstaLista(Integer num) {
        return vm_ProdutosQueEstaLista = repositorio.getRe_ProdutosQueEstaLista(num);
    }

    public LiveData<List<Lista_Compra>> getVm_ListaPorProdutos(Integer num) {
        return vm_ListaPorProdutos = repositorio.getRe_ListaPorProdutos(num);
    }

    public LiveData<Integer> getVm_LastIdProduto(){
        return vm_LastIdProduto;
    }

    public LiveData<Integer> getVm_LastIdListaCompra(){
        return vm_LastIdListaCompra;
    }

    public LiveData<List<Lista_Lembrete>> getVm_TodaListaLembrete(){
        return vm_TodaListaLembrete;
    }

    public LiveData<List<Lista_Lembrete_DAO.Ultimo>> getVm_UltProdInsert(){
        return vm_UltProdInsert;
    }

    public LiveData<List<Produto>> getVm_ProdutosQueEstaLembrete(Integer num){
        return vm_ProdutosQueEstaLembrete = repositorio.getRe_ProdutosQueEstaLembrete(num);
    }



    //Inicio Bloco Insert
    public void insertVm_Produto(Produto produto) {
        repositorio.insertRe_Produto(produto);
    }

    public void insertVm_LocalCompra(Local_Compra localCompra) {
        repositorio.insertRe_LocalCompra(localCompra);
    }

    public void insertVm_ListaCompra(Lista_Compra listaCompra) {
        repositorio.insertRe_ListaCompra(listaCompra);
    }

    public void insertVm_ItemProdutoLista(Item_Produto_Lista itemProdutoLista) {
        repositorio.insertRe_ItemProdutoLista(itemProdutoLista);
    }

    public void insertVm_ListaLembrete(Lista_Lembrete listaLembrete){
        repositorio.insertRe_Lista_Lembrete(listaLembrete);
    }

    public void insertVm_ItemProduroLembrete(Item_Produto_Lembrete itemProdutoLembrete){
        repositorio.insertRe_ItemProdutoLembrete(itemProdutoLembrete);
    }
    //Fim Bloco Insert

    //Inicio Bloco Update
    public void updateVm_Produto(Produto produto) {
        repositorio.updateRe_Produto(produto);
    }

    public void updateVm_LocalCompra(Local_Compra localCompra) {
        repositorio.updateRe_LocalCompra(localCompra);
    }

    public void updateVm_ListaCompra(Lista_Compra listaCompra) {
        repositorio.updateRe_ListaCompra(listaCompra);
    }

    public void updateVm_ItemProdutoLista(Item_Produto_Lista itemProdutoLista) {
        repositorio.updateRe_ItemProdutoLista(itemProdutoLista);
    }

    public void updateVm_ListaLembrete(Lista_Lembrete listaLembrete){
        repositorio.updateRe_Lista_Lembrete(listaLembrete);
    }
    //Fim do bloco Update

    //Inicio do bloco Delete
    public void deleteVm_Produto(Produto produto) {
        repositorio.deleteRe_Produto(produto);
    }

    public void deleteVm_LocalCompra(Local_Compra localCompra) {
        repositorio.deleteRe_LocalCompra(localCompra);
    }

    public void deleteVm_ListaCompra(Lista_Compra listaCompra) {
        repositorio.deleteRe_ListaCompra(listaCompra);
    }

    public void deleteVm_ItemProdutoLista(Item_Produto_Lista itemProdutoLista) {
        repositorio.deleteRe_ItemProdutoLista(itemProdutoLista);
    }

    public void deleteVm_ListaLembrete(Lista_Lembrete listaLembrete){
        repositorio.deleteRe_ListaLembrete(listaLembrete);
    }
    //Fim do bloco delete

}
