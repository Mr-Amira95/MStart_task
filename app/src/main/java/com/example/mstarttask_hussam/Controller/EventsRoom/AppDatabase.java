package com.example.mstarttask_hussam.Controller.EventsRoom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mstarttask_hussam.Model.Beans.Events.Event;

@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();

    // Singleton pattern to get the database instance
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "events_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
