package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

public class DisplayUIDActivity extends AppCompatActivity {
    private PersonRepository repo;
    TextView titleText;
    TextView uidText;
    Button nextBtn;

    private String name;
    private String UID;
    private float longitude;
    private float latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_uidactivity);

        wireWidgets();

        getIntents();
        Log.d("API-Test", "" + longitude + " " + latitude);

        UID = generateUID();
        Person mainUser = new Person(name, UID, latitude, longitude);

        uidText.setText(UID);
        Log.d("API-Test", UID);

        var db = PersonDatabase.provide(this);
        var dao = db.getDao();
        repo = new PersonRepository(dao);
        repo.upsertRemote(mainUser);
    }

    private void wireWidgets() {
        titleText = findViewById(R.id.titleText);
        uidText = findViewById(R.id.uid);
        nextBtn = findViewById(R.id.next_btn);
    }

    private String generateUID(){
        return UUID.randomUUID().toString();
    }

    private void getIntents(){
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        latitude = extras.getFloat("lat");
        longitude = extras.getFloat("lon");
    }

    public void onNextClicked(View view) {
        Intent intent = new Intent(this, AddFriendsActivity.class);
        intent.putExtra("uid", UID);
        startActivity(intent);
    }
}