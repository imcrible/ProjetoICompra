package com.example.projetoicompra.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projetoicompra.model.Local_Compra;

import java.util.List;

@Dao
public interface Local_CompraDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertLocalCompra(Local_Compra localCompra);

    @Update
    void updateLocalCompra(Local_Compra localCompra);

    @Delete
    void deleteLocalCompra(Local_Compra localCompra);

    @Query("SELECT * FROM Local_Compra")
    LiveData<List<Local_Compra>> getTodoLocalCompra();
}
