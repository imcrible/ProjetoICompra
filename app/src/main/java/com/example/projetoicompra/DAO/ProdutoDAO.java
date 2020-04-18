package com.example.projetoicompra.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.projetoicompra.model.Produto;

@Dao
public interface ProdutoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertProduto (Produto produto);

    @Update
    public void updateProduto (Produto produto);



}
