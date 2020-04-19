package com.example.projetoicompra.BD;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projetoicompra.DAO.Item_Produto_ListaDAO;
import com.example.projetoicompra.DAO.Lista_CompraDAO;
import com.example.projetoicompra.DAO.Local_CompraDAO;
import com.example.projetoicompra.DAO.ProdutoDAO;
import com.example.projetoicompra.model.Item_Produto_Lista;
import com.example.projetoicompra.model.Lista_Compra;
import com.example.projetoicompra.model.Local_Compra;
import com.example.projetoicompra.model.Produto;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Produto.class, Local_Compra.class, Lista_Compra.class, Item_Produto_Lista.class}, version = 1)
  abstract class IcompraDataBase extends RoomDatabase {

     private static final String NOME_BD = "icompradatabase";

    private static IcompraDataBase INSTANCIA;

    public abstract Item_Produto_ListaDAO itemProdutoListaDAO();

    public abstract ProdutoDAO produtoDAO();

    public abstract Local_CompraDAO localCompraDAO();

    public abstract Lista_CompraDAO listaCompraDAO();

    private static final int NUM_DE_THREADS = 4;

    static final ExecutorService salvaBanco = Executors.newFixedThreadPool(NUM_DE_THREADS);

    public static synchronized IcompraDataBase getInstance(Context context){
        if(INSTANCIA == null){
            INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                    IcompraDataBase.class, NOME_BD)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }

        return INSTANCIA;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
