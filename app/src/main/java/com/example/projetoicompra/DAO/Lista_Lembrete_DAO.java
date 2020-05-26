package com.example.projetoicompra.DAO;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
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
    void deleteListaLembrete(Lista_Lembrete listaLembrete);

    @Query("SELECT * FROM lista_lembrete")
    LiveData<List<Lista_Lembrete>> getTodaListaLembrete();

    @Query("SELECT MAX(ultidprodinsert), MAX(id_lembrete) FROM lista_lembrete")
    LiveData<List<Ultimo>> getUltProdInsert();

    class Ultimo{
    @ColumnInfo(name = "MAX(ultidprodinsert)")
        Integer ultidprodinsert;
    @ColumnInfo(name = "MAX(id_lembrete)")
        Integer id_lembrete;

        public Integer getUltidprodinsert() {
            return ultidprodinsert;
        }

        public void setUltidprodinsert(Integer ultidprodinsert) {
            this.ultidprodinsert = ultidprodinsert;
        }

        public Integer getId_lembrete() {
            return id_lembrete;
        }

        public void setId_lembrete(Integer id_lembrete) {
            this.id_lembrete = id_lembrete;
        }
    }

}
