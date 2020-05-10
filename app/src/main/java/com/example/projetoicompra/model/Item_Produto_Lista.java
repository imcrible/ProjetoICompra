package com.example.projetoicompra.model;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "item_produto_lista",
        primaryKeys = {"produto_item_id", "lista_item_compra_id"},
        foreignKeys = {
                @ForeignKey(entity = Produto.class,
                        parentColumns = "codigo_produto",
                        childColumns = "produto_item_id",
                        onDelete = 5),
                @ForeignKey(entity = Lista_Compra.class,
                        parentColumns = "nota_fiscal",
                        childColumns = "lista_item_compra_id",
                        onDelete = 5)},
        indices = {@Index(value = {"produto_item_id"}),
                @Index(value = {"lista_item_compra_id"})})

public class Item_Produto_Lista {

    @NonNull
    @ColumnInfo(name = "produto_item_id")
    private Integer produto_item_id;

    @NonNull
    @ColumnInfo(name = "lista_item_compra_id")
    private Integer lista_item_compra_id;

    public Integer getProduto_item_id() {
        return produto_item_id;
    }

    public void setProduto_item_id(Integer produto_item_id) {
        this.produto_item_id = produto_item_id;
    }

    public Integer getLista_item_compra_id() {
        return lista_item_compra_id;
    }

    public void setLista_item_compra_id(Integer lista_item_compra_id) {
        this.lista_item_compra_id = lista_item_compra_id;
    }

    public Item_Produto_Lista(@NonNull Integer produto_item_id, @NonNull Integer lista_item_compra_id) {
        this.produto_item_id = produto_item_id;
        this.lista_item_compra_id = lista_item_compra_id;
    }
}
