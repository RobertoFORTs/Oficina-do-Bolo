package com.example.oficinadobolo.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int usuarioID;
    private String nome;
    private String email;
    private String senha;

    public Usuario(){}

    public Usuario(int usuarioID, String nome, String email, String senha){
        this.usuarioID = usuarioID;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getUsuarioID() {return usuarioID;}

    public void setUsuarioID(int usuarioID) {this.usuarioID = usuarioID;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getSenha() {return senha;}

    public void setSenha(String senha) {this.senha = senha;}

    @Override
    public String toString() {
        return nome;
    }
}
