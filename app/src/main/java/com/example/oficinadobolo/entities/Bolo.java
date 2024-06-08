package com.example.oficinadobolo.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Bolo {

    @PrimaryKey
    private int boloID;
    private String nomeBolo;
    private String descBolo;
    private String ingredientes;

    public Bolo(){}

    public Bolo(int boloID, String nomeBolo, String descBolo, String ingredientes) {
        this.boloID = boloID;
        this.nomeBolo = nomeBolo;
        this.descBolo = descBolo;
        this.ingredientes = ingredientes;
    }

    public int getBoloID() {
        return boloID;
    }

    public void setBoloID(int boloID) {
        this.boloID = boloID;
    }

    public String getNomeBolo() {
        return nomeBolo;
    }

    public void setNomeBolo(String nomeBolo) {
        this.nomeBolo = nomeBolo;
    }

    public void setDescBolo(String descBolo) {
        this.descBolo = descBolo;
    }
    public String getDescBolo(){return descBolo;}

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString(){return nomeBolo;}
}
