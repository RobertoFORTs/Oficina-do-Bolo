package com.example.oficinadobolo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.oficinadobolo.entities.Bolo;

import java.util.List;

@Dao
public interface BoloDao {
    @Insert
    void insertAll(Bolo... bolo);
    @Query("SELECT * FROM Bolo WHERE boloID = :id LIMIT 1")
    Bolo getBolo(int id);
    @Update
    void update(Bolo... bolo);
    @Delete
    void delete(Bolo... bolo);
    @Query("SELECT * FROM Bolo")
    List<Bolo> getAll();

}
