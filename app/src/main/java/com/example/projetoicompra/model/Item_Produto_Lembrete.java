package com.example.projetoicompra.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "item_produto_lembrete",
        primaryKeys = {"produto_item_id", "lembrete_item_id"},
        foreignKeys = {
                        @ForeignKey(entity = Produto.class,
                                    parentColumns ="codigo_produto",
                                    childColumns = "produto_item_id",
                                    onDelete = 5),
                        @ForeignKey(entity = Lista_Lembrete.class,
                                    parentColumns = "id_lembrete",
                                    childColumns = "lembrete_item_id",
                                    onDelete = 5)},
        indices = {@Index(value = {"produto_item_id"}),
                    @Index(value = "lembrete_item_id")})
public class Item_Produto_Lembrete {

    @NonNull
    @ColumnInfo(name = "produto_item_id")
    private Integer produto_item_id;

    @NonNull
    @ColumnInfo(name = "lembrete_item_id")
    private Integer lembrete_item_id;

    @NonNull
    public Integer getProduto_item_id() {
        return produto_item_id;
    }

    public void setProduto_item_id(@NonNull Integer produto_item_id) {
        this.produto_item_id = produto_item_id;
    }

    @NonNull
    public Integer getLembrete_item_id() {
        return lembrete_item_id;
    }

    public void setLembrete_item_id(@NonNull Integer lembrete_item_id) {
        this.lembrete_item_id = lembrete_item_id;
    }

    public Item_Produto_Lembrete(@NonNull Integer produto_item_id, @NonNull Integer lembrete_item_id) {
        this.produto_item_id = produto_item_id;
        this.lembrete_item_id = lembrete_item_id;
    }
}
