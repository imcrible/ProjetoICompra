package com.example.projetoicompra.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Local_Compra")
public class Local_Compra {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cnpj_local")
    private String cnpj_local;

    @NonNull
    @ColumnInfo(name = "razao_social")
    private String razao_social;

    @ColumnInfo (name = "coordenadas")
    private String coordenadas;

    @NonNull
    public String getCnpj_local() {
        return cnpj_local;
    }

    public void setCnpj_local(@NonNull String cnpj_local) {
        this.cnpj_local = cnpj_local;
    }

    @NonNull
    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(@NonNull String razao_social) {
        this.razao_social = razao_social;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Local_Compra(@NonNull String cnpj_local, @NonNull String razao_social, String coordenadas) {
        this.cnpj_local = cnpj_local;
        this.razao_social = razao_social;
        this.coordenadas = coordenadas;
    }
}
