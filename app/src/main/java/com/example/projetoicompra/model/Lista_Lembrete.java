package com.example.projetoicompra.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Contract;

import java.util.List;

@Entity(tableName = "lista_lembrete")
public class Lista_Lembrete {

    @PrimaryKey
    @ColumnInfo(name = "id_lembrete")
    private Integer id_lembrete;

    @ColumnInfo(name = "nome_lembrete")
    private String nome_lembrete;

    @ColumnInfo(name = "valor_total_lembrete")
    private Double valor_total_lembrete;

    @ColumnInfo(name = "data_lembrete")
    private String data_lembrete;

    @ColumnInfo(name = "ultidprodinsert")
    private Integer ultiidprodinsert;

    public String getNome_lembrete() {
        return nome_lembrete;
    }

    public void setNome_lembrete(String nome_lembrete) {
        this.nome_lembrete = nome_lembrete;
    }

    public Double getValor_total_lembrete() {
        return valor_total_lembrete;
    }

    public void setValor_total_lembrete(Double valor_total_lembrete) {
        this.valor_total_lembrete = valor_total_lembrete;
    }

    public String getData_lembrete() {
        return data_lembrete;
    }

    public void setData_lembrete(String data_lembrete) {
        this.data_lembrete = data_lembrete;
    }

    public Integer getUltiidprodinsert() {
        return ultiidprodinsert;
    }

    public void setUltiidprodinsert(Integer ultiidprodinsert) {
        this.ultiidprodinsert = ultiidprodinsert;
    }

    public Integer getId_lembrete() {
        return id_lembrete;
    }

    public void setId_lembrete(Integer id_lembrete) {
        this.id_lembrete = id_lembrete;
    }

    public Lista_Lembrete(Integer id_lembrete,String nome_lembrete, Double valor_total_lembrete, String data_lembrete, Integer ultiidprodinsert) {
        this.id_lembrete = id_lembrete;
        this.nome_lembrete = nome_lembrete;
        this.valor_total_lembrete = valor_total_lembrete;
        this.data_lembrete = data_lembrete;
        this.ultiidprodinsert = ultiidprodinsert;
    }


    @Ignore
    public Lista_Lembrete(Integer id_lembrete, Integer ultiidprodinsert) {
        this.id_lembrete = id_lembrete;
        this.ultiidprodinsert = ultiidprodinsert;

    }


}




