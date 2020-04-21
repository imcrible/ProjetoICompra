package com.example.projetoicompra.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "item_produto_lista",
        primaryKeys = {"produto_item_id", "lista_item_compra_id"},
        foreignKeys = {
                @ForeignKey(entity = Produto.class,
                        parentColumns = "produto_id",
                        childColumns = "produto_item_id"),
                @ForeignKey(entity = Lista_Compra.class,
                        parentColumns = "lista_compra_id",
                        childColumns = "lista_item_compra_id")},
        indices = {@Index(value = {"produto_item_id"}),
                @Index(value = {"lista_item_compra_id"})})

public class Item_Produto_Lista {

    @ColumnInfo(name = "produto_item_id")
    private int produto_item_id;
    @ColumnInfo(name = "lista_item_compra_id")
    private int lista_item_compra_id;

    public int getProduto_item_id() {
        return produto_item_id;
    }

    public void setProduto_item_id(int produto_item_id) {
        this.produto_item_id = produto_item_id;
    }

    public int getLista_item_compra_id() {
        return lista_item_compra_id;
    }

    public void setLista_item_compra_id(int lista_item_compra_id) {
        this.lista_item_compra_id = lista_item_compra_id;
    }

    @Ignore
    public Item_Produto_Lista(int produto_item_id, int lista_item_compra_id) {
        this.produto_item_id = produto_item_id;
        this.lista_item_compra_id = lista_item_compra_id;
    }

    public Item_Produto_Lista(int produto_item_id) {
        this.produto_item_id = produto_item_id;
    }


}
