package com.example.socialcompass.Models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.socialcompass.Utils.LocationAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PersonRepository {
    private final PersonDao dao;
    private final LocationAPI api;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledFuture = null;

    public PersonRepository(PersonDao dao) {
        this.dao = dao;
        this.api = new LocationAPI();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    // Local Methods

    public LiveData<Person> getLocal(String uid) {
        return dao.get(uid);
    }

    public LiveData<List<Person>> getAllLocal() {
        return dao.getAll();
    }

    public void upsertLocal(Person person) {
        dao.upsert(person);
    }

    public void deleteLocal(Person person) {
        dao.delete(person);
    }

    public boolean existsLocal(String title) {
        return dao.exists(title);
    }

    // Remote Methods

    public LiveData<List<Person>> getRemote(String[] title) {
        MutableLiveData<List<Person>> liveData = new MutableLiveData<>();

        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<Person> peopleList = new ArrayList<>();
                for (String uid : title) {
                    peopleList.add(api.get(uid));
                }
                liveData.postValue(peopleList);
                // Log.d("Repo-Test", curr.name);
            }
        }, 0, 5, TimeUnit.SECONDS);

        return liveData;
    }

    public void addPerson(String title) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                upsertLocal(api.get(title));
            }
        }).start();
    }

    public void upsertRemote(Person person) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                api.put(person.name, person.uid, person.latitude, person.longitude);
            }
        }).start();
    }

    public void mockUrl(String url) {
        api.mockUrl(url);
    }
}
