package com.example.projetoicompra.BD;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projetoicompra.DAO.ICompraDAO;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;
import com.example.projetoicompra.model.Produto;

@Database(entities = {Produto.class, Local_Compra.class, Lista_Compra.class, Item_Produto_Lista.class}, version = 1)
public abstract class IcompraDataBase extends RoomDatabase {

    private static IcompraDataBase INSTANCIA;

    public abstract ICompraDAO produtoDAO();

    public static synchronized IcompraDataBase getInstance(Context context){
        if(INSTANCIA == null){
            INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                    IcompraDataBase.class, "icompradatabase")
                    .fallbackToDestructiveMigration().build();
        }

        return INSTANCIA;
    }




}
