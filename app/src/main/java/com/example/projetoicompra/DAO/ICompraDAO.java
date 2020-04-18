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
public interface ICompraDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduto (Produto produto);

    @Update
    void updateProduto (Produto produto);

    @Delete
    void deleteProduto (Produto produto);

    @Query("SELECT * FROM produto")
    LiveData<List<Produto>> getTodosProdutos();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertLocalCompra (Local_Compra localCompra);

    @Update
    void updateLocalCompra (Local_Compra localCompra);

    @Delete
    void deleteLocalCompra (Local_Compra localCompra);

    @Query("SELECT * FROM Local_Compra")
    LiveData<List<Local_Compra>> getTodoLocalCompra();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertListaCompra (Lista_Compra listacompra);

    @Update
    void updateListaCompra (Lista_Compra listacompra);

    @Delete
    void deleteLista_Compra (Lista_Compra listacompra);

    @Query("SELECT * FROM lista_compra")
    LiveData<List<Lista_Compra>> getTodaListaCompra();

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
    LiveData<List<Produto>> getProdutosQueEstaLista(final int lista_item_compra_id);


    @Query("SELECT * FROM lista_compra "+
            "INNER JOIN item_produto_lista "+
            "ON lista_compra.lista_compra_id=item_produto_lista.lista_item_compra_id "+
            "WHERE item_produto_lista.produto_item_id=:produto_item_id")
    LiveData<List<Lista_Compra>> getListaPorProdutos (final int produto_item_id);





}
