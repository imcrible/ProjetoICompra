package com.example.projetoicompra.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projetoicompra.model.Item_Produto_Lembrete;
import com.example.projetoicompra.model.Produto;

import java.util.List;

@Dao
public interface Item_Produto_LembreteDAO {

    @Insert
    void insertItemProdutoLembrete(Item_Produto_Lembrete item_produto_lembrete);

    @Update
    void updateItemProdutoLembrete(Item_Produto_Lembrete item_produto_lembrete);

    @Delete
    void deleteItemProdutoLembrete(Item_Produto_Lembrete item_produto_lembrete);

    @Query("SELECT codigo_produto, nome_produto, preco_produto, quatidade, preco_total FROM produto "+
            "INNER JOIN item_produto_lembrete " +
            "ON produto.codigo_produto=item_produto_lembrete.produto_item_id " +
            "WHERE item_produto_lembrete.produto_item_id=:lembrete_item_produto_id")
    LiveData<List<Produto>> getProdutoQueEstaLembrete(Integer lembrete_item_produto_id);
}
