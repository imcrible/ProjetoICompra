package com.example.projetoicompra.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.projetoicompra.model.Lista_Compra;

@Dao
public interface Lista_CompraDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertListaCompra (Lista_Compra lista_compra);

    @Update
    public void updateListaCompra (Lista_Compra lista_compra);

}
