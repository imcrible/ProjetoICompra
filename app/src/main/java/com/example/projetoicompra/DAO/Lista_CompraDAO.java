package com.example.projetoicompra.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projetoicompra.model.Lista_Compra;

import java.util.List;

@Dao
public interface Lista_CompraDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertListaCompra(Lista_Compra listacompra);

    @Update
    void updateListaCompra(Lista_Compra listacompra);

    @Delete
    void deleteLista_Compra(Lista_Compra listacompra);

    //@Query("SELECT * FROM lista_compra")
    //LiveData<List<Lista_Compra>> getTodaListaCompra();

    @Query("SELECT hora_compra, data_compra, nota_fiscal, total_compra, cnpj_local_lista, local_compra.razao_social  FROM lista_compra join local_compra " +
            "on lista_compra.cnpj_local_lista = local_compra.cnpj_local ")
    LiveData<List<Lista_Compra>> getTodaListaCompra();

}
