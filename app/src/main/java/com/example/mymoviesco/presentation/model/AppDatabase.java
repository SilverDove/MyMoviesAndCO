package com.example.mymoviesco.presentation.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mymoviesco.data.MovieDao;

@Database(entities = {Movie.class}, version=2)
public abstract class AppDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile AppDatabase INSTANCE;

    // --- DAO ---
    public abstract MovieDao movieDao();

    // --- INSTANCE ---
    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "MyDatabase.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
