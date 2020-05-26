package com.example.projetoicompra.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Produto;

import java.util.List;

@Dao
public interface Item_Produto_ListaDAO {


    @Insert
    void insertItemProdutoLista(Item_Produto_Lista item_produto_lista);


    @Update
    void updateItemProdutoLista(Item_Produto_Lista item_produto_lista);

    @Delete
    void deleteItemProdutoLista(Item_Produto_Lista item_produto_lista);

    @Query("SELECT codigo_produto, nome_produto, preco_produto, quatidade, preco_total FROM produto " +
            "INNER JOIN item_produto_lista  " +
            "ON produto.codigo_produto=item_produto_lista.produto_item_id " +
            "WHERE item_produto_lista.lista_item_compra_id=:lista_item_compra_id")
    LiveData<List<Produto>> getProdutosQueEstaLista(Integer lista_item_compra_id);


    @Query("SELECT  hora_compra, data_compra, nota_fiscal, total_compra, cnpj_local_lista FROM lista_compra " +
            "INNER JOIN item_produto_lista " +
            "ON lista_compra.nota_fiscal=item_produto_lista.lista_item_compra_id " +
            "WHERE item_produto_lista.produto_item_id=:produto_item_id")
    LiveData<List<Lista_Compra>> getListaPorProdutos(Integer produto_item_id);

    @Query("SELECT codigo_produto from produto order by codigo_produto DESC limit 2")
    LiveData<Integer> getLastIdProduto();

    @Query("SELECT nota_fiscal from lista_compra order by nota_fiscal DESC limit 2")
    LiveData<Integer> getLastIdListaCompra();

}
