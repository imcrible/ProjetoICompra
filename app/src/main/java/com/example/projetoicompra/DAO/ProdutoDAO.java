package com.example.projetoicompra.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projetoicompra.model.Produto;

import java.util.List;

@Dao
public interface ProdutoDAO {

    @Insert
    void insertProduto(Produto produto);

    @Update
    void updateProduto(Produto produto);

    @Delete
    void deleteProduto(Produto produto);

    @Query("SELECT * FROM produto")
    LiveData<List<Produto>> getTodosProdutos();
}
