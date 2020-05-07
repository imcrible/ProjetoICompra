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

    @ColumnInfo(name = "coordenadas")
    private String coordenadas;

    @ColumnInfo(name= "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Local_Compra(@NonNull String cnpj_local, @NonNull String razao_social, String coordenadas, String latitude, String longitude) {
        this.cnpj_local = cnpj_local;
        this.razao_social = razao_social;
        this.coordenadas = coordenadas;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
