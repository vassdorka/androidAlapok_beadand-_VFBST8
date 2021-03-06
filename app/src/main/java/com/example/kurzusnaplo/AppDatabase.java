package com.example.kurzusnaplo;


import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version= 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;

    private static String DATABASE_NAME = "database";

    public synchronized static AppDatabase getInstance(Context context){
        if ( database == null ){
            database = Room.databaseBuilder(context.getApplicationContext(),
                       AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract MainDao dao();
}
