package com.example.socialcompass.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.socialcompass.Models.Person;
import com.example.socialcompass.Models.PersonDatabase;
import com.example.socialcompass.Models.PersonRepository;

import java.util.List;

public class FriendListViewModel extends AndroidViewModel {
    private LiveData<List<Person>> friends;
    private final PersonRepository repo;

    public FriendListViewModel(@NonNull Application application) {
        super(application);
        var context = application.getApplicationContext();
        var db = PersonDatabase.provide(context);
        var dao = db.getDao();
        this.repo = new PersonRepository(dao);
    }

    public LiveData<List<Person>> getFriends() {
        if (friends == null) {
            friends = repo.getAllLocal();
        }
        return friends;
    }

    public void addPerson(String uid) {
        if (!repo.existsLocal(uid)) {
            Log.d("API-Test", "add person");
            repo.addPerson(uid);
        }
    }

    public LiveData<List<Person>> getRemoteFriends(String[] uids) {
        return repo.getRemote(uids);
    }

    public void updateUser(Person person) {
        repo.upsertRemote(person);
    }

    public LiveData<Person> getPerson(String uid) {
        return repo.getLocal(uid);
    }

    public void delete(Person person) {
        repo.deleteLocal(person);
    }

    public void mockUrl(String url) { repo.mockUrl(url); }
}
