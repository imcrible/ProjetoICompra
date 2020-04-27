package com.example.projetoicompra.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "produto")
public class Produto {


    /**@PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "produto_id")
    private int produto_id;*/

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "codigo_produto")
    private Integer codigo_produto;

    @NonNull
    @ColumnInfo(name = "nome_produto")
    private String nome_produto;

    @NonNull
    @ColumnInfo(name = "preco_produto")
    private Double preco_produto;


    @ColumnInfo(name = "quatidade")
    private int quantidade;

    @NonNull
    @ColumnInfo(name = "preco_total")
    private Double preco_total;


    /*public int getProduto_id() {
        return produto_id;
    }*/

    /*public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }*/

    @NonNull
    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(@NonNull String nome_produto) {
        this.nome_produto = nome_produto;
    }

    @NonNull
    public Double getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(@NonNull Double preco_produto) {
        this.preco_produto = preco_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @NonNull
    public Double getPreco_total() {
        return preco_total;
    }

    public void setPreco_total(@NonNull Double preco_total) {
        this.preco_total = preco_total;
    }

    public Integer getCodigo_produto() {
        return codigo_produto;
    }

    public void setCodigo_produto(Integer codigo_produto) {
        this.codigo_produto = codigo_produto;
    }

    public Produto(@NonNull Integer codigo_produto, @NonNull String nome_produto, @NonNull Double preco_produto, int quantidade, @NonNull Double preco_total) {
        this.codigo_produto = codigo_produto;
        this.nome_produto = nome_produto;
        this.preco_produto = preco_produto;
        this.quantidade = quantidade;
        this.preco_total = preco_total;
    }

}
