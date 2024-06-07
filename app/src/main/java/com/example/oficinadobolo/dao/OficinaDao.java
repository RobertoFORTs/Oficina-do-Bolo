package com.example.oficinadobolo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.oficinadobolo.entities.Oficina;

import java.util.List;

@Dao
public interface OficinaDao {
    @Insert
    void insertAll(Oficina... oficina);
    @Query("SELECT * FROM Oficina WHERE oficinaID = :id LIMIT 1")
    Oficina getOficina(int id);
    @Update
    void update(Oficina... oficina);
    @Delete
    void delete(Oficina... oficina);
    @Query("SELECT * FROM Oficina")
    List<Oficina> getAll();

}
