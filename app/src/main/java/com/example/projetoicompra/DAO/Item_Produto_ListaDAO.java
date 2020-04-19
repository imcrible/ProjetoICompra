package com.example.projetoicompra.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;
import com.example.projetoicompra.model.Produto;

import java.util.List;

@Dao
public interface Item_Produto_ListaDAO {

    @Insert
    void insertItemProdutoLista (Item_Produto_Lista item_produto_lista);

    @Update
    void updateItemProdutoLista (Item_Produto_Lista item_produto_lista);

    @Delete
    void deleteItemProdutoLista (Item_Produto_Lista item_produto_lista);

    @Query("SELECT * FROM produto "+
            "INNER JOIN item_produto_lista  "+
            "ON produto.produto_id=item_produto_lista.produto_item_id "+
            "WHERE item_produto_lista.lista_item_compra_id=:lista_item_compra_id")
    LiveData<List<Produto>> getProdutosQueEstaLista( int lista_item_compra_id);


    @Query("SELECT * FROM lista_compra "+
            "INNER JOIN item_produto_lista "+
            "ON lista_compra.lista_compra_id=item_produto_lista.lista_item_compra_id "+
            "WHERE item_produto_lista.produto_item_id=:produto_item_id")
    LiveData<List<Lista_Compra>> getListaPorProdutos ( int produto_item_id);





}
