package com.example.projetoicompra.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "item_produto_lista",
        primaryKeys = {"produto_item_id", "lista_item_compra_id"},
        foreignKeys = {
                        @ForeignKey(entity = Produto.class,
                                parentColumns = "produto_id",
                                childColumns = "produto_item_id"),
                        @ForeignKey(entity = Lista_Compra.class,
                                parentColumns = "lista_compra_id",
                                childColumns = "lista_item_compra_id")
        })
public class Item_Produto_Lista {

    private int produto_item_id;
    private int lista_item_compra_id;
}
