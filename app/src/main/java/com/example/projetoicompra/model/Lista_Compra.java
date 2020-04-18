package com.example.projetoicompra.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "lista_compra",
        foreignKeys = @ForeignKey(entity = Local_Compra.class,
                                            parentColumns = "cnpj_local",
                                            childColumns = "cnpj_local_lista"))
public class Lista_Compra {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "lista_compra_id")
    private int lista_compra_id;

    @NonNull
    @ColumnInfo (name = "hora_compra")
    private String hora_compra;

    @NonNull
    @ColumnInfo (name = "data_compra")
    private String data_compra;

    @NonNull
    @ColumnInfo (name = "nota_fiscal")
    private String nota_fiscal;

    @NonNull
    @ColumnInfo (name = "total_compra")
    private Double total_compra;

    @NonNull
    @ColumnInfo (name = "cnpj_local_lista")
    private String cnpj_local_lista;

    public int getLista_compra_id() {
        return lista_compra_id;
    }

    public void setLista_compra_id(int lista_compra_id) {
        this.lista_compra_id = lista_compra_id;
    }

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
    public String getNota_fiscal() {
        return nota_fiscal;
    }

    public void setNota_fiscal(@NonNull String nota_fiscal) {
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

    public Lista_Compra(int lista_compra_id, @NonNull String hora_compra, @NonNull String data_compra, @NonNull String nota_fiscal, @NonNull Double total_compra, @NonNull String cnpj_local_lista) {
        this.lista_compra_id = lista_compra_id;
        this.hora_compra = hora_compra;
        this.data_compra = data_compra;
        this.nota_fiscal = nota_fiscal;
        this.total_compra = total_compra;
        this.cnpj_local_lista = cnpj_local_lista;
    }
}