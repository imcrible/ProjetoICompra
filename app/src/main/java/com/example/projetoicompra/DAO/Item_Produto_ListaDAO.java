package com.example.projetoicompra.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.projetoicompra.model.Item_Produto_Lista;

@Dao
public interface Item_Produto_ListaDAO {

    @Insert
    public void insertItemProdutoLista (Item_Produto_Lista item_produto_lista);

    @Update
    public void updateItemProdutoLista (Item_Produto_Lista item_produto_lista);

}
