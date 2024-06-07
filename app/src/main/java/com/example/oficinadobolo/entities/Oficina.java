package com.example.oficinadobolo.entities;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "usuarioID", childColumns = "usuarioID"
        ))
public class Oficina {

    @PrimaryKey
    private int oficinaID;
    private String nomeOficina;
    private String descOficina;
    private Date dataOficina;
    private int usuarioID;

    public Oficina(){}

    public Oficina(int oficinaID, String nomeOficina, String descOficina, Date dataOficina, int usuarioID) {
        this.oficinaID = oficinaID;
        this.nomeOficina = nomeOficina;
        this.descOficina = descOficina;
        this.dataOficina = dataOficina;
        this.usuarioID = usuarioID;
    }

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

    @Override
    public String toString(){return nomeOficina;}
}
