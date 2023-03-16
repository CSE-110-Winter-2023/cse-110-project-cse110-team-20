package com.example.socialcompass.Models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public abstract class PersonDao {
    @Upsert
    public abstract long upsert(Person person);

    @Query("SELECT EXISTS(SELECT 1 FROM people WHERE uid = :uid)")
    public abstract boolean exists(String uid);

    @Query("SELECT * FROM people WHERE uid = :uid")
    public abstract LiveData<Person> get(String uid);

    @Query("SELECT * FROM people")
    public abstract LiveData<List<Person>> getAll();

    @Delete
    public abstract int delete(Person person);
}
