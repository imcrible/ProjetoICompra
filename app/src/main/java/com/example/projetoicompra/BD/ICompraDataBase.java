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
public abstract class ICompraDataBase extends RoomDatabase {

    private static final String NOME_BD = "icompradatabase";

    private static ICompraDataBase INSTANCIA;

    public abstract Item_Produto_ListaDAO itemProdutoListaDAO();

    public abstract ProdutoDAO produtoDAO();

    public abstract Local_CompraDAO localCompraDAO();

    public abstract Lista_CompraDAO listaCompraDAO();

    private static final int NUM_DE_THREADS = 4;

    public static synchronized ICompraDataBase getInstance(Context context) {
        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(context.getApplicationContext(), ICompraDataBase.class, NOME_BD)
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

        private PopularDbAsyncTask(ICompraDataBase db) {
            listaCompraDAO = db.listaCompraDAO();
            localCompraDAO = db.localCompraDAO();
            produtoDAO = db.produtoDAO();
            itemProdutoListaDAO = db.itemProdutoListaDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //public Lista_Compra(@NonNull String hora_compra, @NonNull String data_compra, @NonNull String nota_fiscal, @NonNull String total_compra, @NonNull String cnpj_local_lista)
            //public Produto(@NonNull String nome_produto, @NonNull Double preco_produto, int quantidade, @NonNull Double preco_total)
            /*
            localCompraDAO.insertLocalCompra((new Local_Compra("1236515231", "Guanabara", "Rua Ciclano") ));
            listaCompraDAO.insertListaCompra((new Lista_Compra("14:00:00", "12/03/2020", 12181815, "20.0", "1236515231")));
            listaCompraDAO.insertListaCompra((new Lista_Compra("14:00:00", "12/03/2020", 741852, "40.0", "1236515231")));
            produtoDAO.insertProduto((new Produto(123,"Farinha", 6.66, 5, 33.3)));
            produtoDAO.insertProduto((new Produto(321,"Leite", 3.50, 12, 3.5*12)));
            produtoDAO.insertProduto((new Produto(741,"Arroz", 10.50, 2, 10.50*2)));
            itemProdutoListaDAO.insertItemProdutoLista((new Item_Produto_Lista(123, 12181815)));
            itemProdutoListaDAO.insertItemProdutoLista((new Item_Produto_Lista(321, 12181815)));
            itemProdutoListaDAO.insertItemProdutoLista((new Item_Produto_Lista(741, 741852)));

             */
            return null;
        }
    }


}
