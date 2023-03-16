package com.example.socialcompass.Models;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class PersonDatabase extends RoomDatabase {
    private volatile static PersonDatabase instance = null;

    public abstract PersonDao getDao();

    public synchronized static PersonDatabase provide(Context context) {
        if (instance == null) {
            instance = PersonDatabase.make(context);
        }
        return instance;
    }

    private static PersonDatabase make(Context context) {
        return Room.databaseBuilder(context, PersonDatabase.class, "person_app.db")
                .allowMainThreadQueries()
                .build();
    }

    @VisibleForTesting
    public static void inject(PersonDatabase testDatabase) {
        if (instance != null ) {
            instance.close();
        }
        instance = testDatabase;
    }
}
