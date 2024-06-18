package com.example.oficinadobolo.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.oficinadobolo.entities.Usuario;

import java.util.List;
@Dao
public interface UsuarioDao {
    @Insert
    void insertAll(Usuario... usuario);
    @Query("SELECT * FROM Usuario WHERE usuarioID = :id LIMIT 1")
    Usuario getUsuario(int id);
    @Update
    void update(Usuario... usuario);
    @Delete
    void delete(Usuario... usuario);
    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM Usuario WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario getUsuarioPorEmailSenha(String email, String senha);

    @Query("SELECT * FROM Usuario WHERE email = :email AND senha = :senha")
    Usuario login(String email, String senha);
}
