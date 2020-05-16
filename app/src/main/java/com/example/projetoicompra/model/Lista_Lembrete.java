package com.example.projetoicompra.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lista_lembrete")
public class Lista_Lembrete {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nome_lembrete")
    private String nome_lembrete;

    @ColumnInfo(name = "valor_total_lembrete")
    private double valor_total_lembrete;

    public String getNome_lembrete() {
        return nome_lembrete;
    }

    public void setNome_lembrete(String nome_lembrete) {
        this.nome_lembrete = nome_lembrete;
    }

    public double getValor_total_lembrete() {
        return valor_total_lembrete;
    }

    public void setValor_total_lembrete(double valor_total_lembrete) {
        this.valor_total_lembrete = valor_total_lembrete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
