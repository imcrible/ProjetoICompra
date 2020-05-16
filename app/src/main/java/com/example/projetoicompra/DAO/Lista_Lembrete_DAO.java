package com.example.projetoicompra.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projetoicompra.model.Lista_Lembrete;

import java.util.List;

@Dao
public interface Lista_Lembrete_DAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertListaLembrete(Lista_Lembrete listaLembrete);

    @Update
    void updateListaLembrete(Lista_Lembrete listaLembrete);

    @Delete
    void deleteListaLembrrete(Lista_Lembrete listaLembrete);

    @Query("SELECT * FROM lista_lembrete")
    LiveData<List<Lista_Lembrete>> getTodaListaLembrete();

    @Query("SELECT MAX(ultidprodinsert) FROM lista_lembrete")
    LiveData<Integer> getUltProdInsert();

}
