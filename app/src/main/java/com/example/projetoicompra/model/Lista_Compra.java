package com.example.projetoicompra.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;



@Entity(tableName = "lista_compra",
        foreignKeys = @ForeignKey(entity = Local_Compra.class,
                parentColumns = "cnpj_local",
                childColumns = "cnpj_local_lista",
                onDelete = 5),
        indices = {@Index(value = {"cnpj_local_lista"})})
public class Lista_Compra {

    @NonNull
    @ColumnInfo(name = "hora_compra")
    private String hora_compra;

    @NonNull
    @ColumnInfo(name = "data_compra")
    private String data_compra;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nota_fiscal")
    private Integer nota_fiscal;

    @NonNull
    @ColumnInfo(name = "total_compra")
    private Double total_compra;

    @NonNull
    @ColumnInfo(name = "cnpj_local_lista")
    private String cnpj_local_lista;

    @NonNull
    public String getHora_compra() {
        return hora_compra;
    }

    public void setHora_compra(@NonNull String hora_compra) {
        this.hora_compra = hora_compra;
    }

    @NonNull
    public String getData_compra() {
        return data_compra;
    }

    public void setData_compra(@NonNull String data_compra) {
        this.data_compra = data_compra;
    }

    @NonNull
    public Integer getNota_fiscal() {
        return nota_fiscal;
    }

    public void setNota_fiscal(@NonNull Integer nota_fiscal) {
        this.nota_fiscal = nota_fiscal;
    }

    @NonNull
    public Double getTotal_compra() {
        return total_compra;
    }

    public void setTotal_compra(@NonNull Double total_compra) {
        this.total_compra = total_compra;
    }

    @NonNull
    public String getCnpj_local_lista() {
        return cnpj_local_lista;
    }

    public void setCnpj_local_lista(@NonNull String cnpj_local_lista) {
        this.cnpj_local_lista = cnpj_local_lista;
    }

    public Lista_Compra(@NonNull String hora_compra, @NonNull String data_compra,
                        @NonNull Integer nota_fiscal, @NonNull Double total_compra,
                        @NonNull String cnpj_local_lista) {
        this.hora_compra = hora_compra;
        this.data_compra = data_compra;
        this.nota_fiscal = nota_fiscal;
        this.total_compra = total_compra;
        this.cnpj_local_lista = cnpj_local_lista;
        razao_social = "vazio";
    }

    private String razao_social;

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    @Ignore
    public Lista_Compra(@NonNull String hora_compra, @NonNull String data_compra,
                        @NonNull Integer nota_fiscal, @NonNull Double total_compra,
                        @NonNull String cnpj_local_lista, String razao_social) {
        this.hora_compra = hora_compra;
        this.data_compra = data_compra;
        this.nota_fiscal = nota_fiscal;
        this.total_compra = total_compra;
        this.cnpj_local_lista = cnpj_local_lista;
        this.razao_social = razao_social;
    }
}
