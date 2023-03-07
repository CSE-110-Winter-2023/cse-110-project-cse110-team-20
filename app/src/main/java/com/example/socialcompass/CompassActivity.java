package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class CompassActivity extends AppCompatActivity {

    SharedPreferences preferences;
    FriendListViewModel viewModel;

    private LocationService locationService;

    private LiveData<List<Person>> people;

    private long prevTime;
    private Person user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        preferences = getSharedPreferences("main", MODE_PRIVATE);
        int numPeople = preferences.getAll().size();

        String userUID = preferences.getString("0", "default_if_not_found");
        String[] friendsUID = new String[numPeople - 1];

        for (int i = 1; i < numPeople; i++) {
            friendsUID[i - 1] = preferences.getString("" + i, "default_if_not_found");
        }

        viewModel = setupViewModel();
        people = viewModel.getRemoteFriends(friendsUID);
        people.observe(this, locList->{
            for (Person person : locList) {
                Log.d("Compass Test", person.toString());
            }
        });

        Log.d("Compass Test", userUID);

        user = new Person("", userUID, 0,0);

        prevTime = 0;
        locationService = locationService.singleton(this);
        locationService.getLocation().observe(this, loc->{
            if (System.currentTimeMillis() - prevTime > 5000) {
                user.latitude = loc.first.floatValue();
                user.longitude = loc.second.floatValue();
                viewModel.updateUser(user);
                prevTime = System.currentTimeMillis();
            }
        });
    }

    private FriendListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(FriendListViewModel.class);
    }
}