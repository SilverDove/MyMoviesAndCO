package com.example.mymoviesco;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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
