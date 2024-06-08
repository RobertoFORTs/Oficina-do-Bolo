package com.example.oficinadobolo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.oficinadobolo.adapters.ConversorData;
import com.example.oficinadobolo.dao.BoloDao;
import com.example.oficinadobolo.dao.OficinaDao;
import com.example.oficinadobolo.dao.UsuarioDao;
import com.example.oficinadobolo.entities.Bolo;
import com.example.oficinadobolo.entities.Oficina;
import com.example.oficinadobolo.entities.Usuario;

@Database(entities = {Usuario.class, Oficina.class, Bolo.class}, version = 2)
@TypeConverters({ConversorData.class})
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase INSTANCE;
    private static LocalDatabase getDataBase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "OficinaDoBolo").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public abstract UsuarioDao usuarioModel();
    public abstract BoloDao boloModel();
    public abstract OficinaDao oficinaModel();

}
