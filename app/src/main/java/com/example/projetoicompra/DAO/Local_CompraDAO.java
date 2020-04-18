package com.example.projetoicompra.DAO;

import android.view.View;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.projetoicompra.model.Local_Compra;

@Dao
public interface Local_CompraDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertLocalCompra (Local_Compra localCompra);

    @Update
    public void updateLocalCompra (Local_Compra localCompra);

}
