package com.example.oficinadobolo.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.oficinadobolo.adapters.ConversorData;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "usuarioID", childColumns = "usuarioID"
))
public class Oficina {

    @PrimaryKey(autoGenerate = true)
    private int oficinaID;
    private String nomeOficina;
    private String descOficina;
    @TypeConverters(ConversorData.class)
    private Date dataOficina;
    private int usuarioID;
    private double latitude; // Novo campo
    private double longitude; // Novo campo

    // Getters e setters

    public int getOficinaID() {
        return oficinaID;
    }

    public void setOficinaID(int oficinaID) {
        this.oficinaID = oficinaID;
    }

    public String getNomeOficina() {
        return nomeOficina;
    }

    public void setNomeOficina(String nomeOficina) {
        this.nomeOficina = nomeOficina;
    }

    public String getDescOficina() {
        return descOficina;
    }

    public void setDescOficina(String descOficina) {
        this.descOficina = descOficina;
    }

    public Date getDataOficina() {
        return dataOficina;
    }

    public void setDataOficina(Date dataOficina) {
        this.dataOficina = dataOficina;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return nomeOficina;
    }
}
