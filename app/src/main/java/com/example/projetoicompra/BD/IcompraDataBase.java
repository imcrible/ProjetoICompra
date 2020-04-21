package com.example.projetoicompra.BD;

import android.content.Context;
import android.os.AsyncTask;

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

@Database(entities = {Produto.class, Local_Compra.class, Lista_Compra.class, Item_Produto_Lista.class}, version = 2, exportSchema = false)
public abstract class IcompraDataBase extends RoomDatabase {

    private static final String NOME_BD = "icompradatabase";

    private static IcompraDataBase INSTANCIA;

    public abstract Item_Produto_ListaDAO itemProdutoListaDAO();

    public abstract ProdutoDAO produtoDAO();

    public abstract Local_CompraDAO localCompraDAO();

    public abstract Lista_CompraDAO listaCompraDAO();

    private static final int NUM_DE_THREADS = 4;

    public static synchronized IcompraDataBase getInstance(Context context) {
        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(context.getApplicationContext(), IcompraDataBase.class, NOME_BD)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return INSTANCIA;
    }

    ;


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopularDbAsyncTask(INSTANCIA).execute();

        }
    };

    private static class PopularDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private Lista_CompraDAO listaCompraDAO;
        private Local_CompraDAO localCompraDAO;
        private ProdutoDAO produtoDAO;
        private Item_Produto_ListaDAO itemProdutoListaDAO;

        private PopularDbAsyncTask(IcompraDataBase db) {
            listaCompraDAO = db.listaCompraDAO();
            localCompraDAO = db.localCompraDAO();
            produtoDAO = db.produtoDAO();
            itemProdutoListaDAO = db.itemProdutoListaDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //public Lista_Compra(@NonNull String hora_compra, @NonNull String data_compra, @NonNull String nota_fiscal, @NonNull String total_compra, @NonNull String cnpj_local_lista)
            //public Produto(@NonNull String nome_produto, @NonNull Double preco_produto, int quantidade, @NonNull Double preco_total)
            //listaCompraDAO.insertListaCompra((new Lista_Compra("14:00:00", "12/02/2020", "12181815", "20.0", "1236515231")));
            produtoDAO.insertProduto((new Produto("Farinha", 6.66, 5, 33.3)));
            return null;
        }
    }


}
