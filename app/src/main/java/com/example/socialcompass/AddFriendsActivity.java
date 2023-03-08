package com.example.socialcompass;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import java.util.List;

public class AddFriendsActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public RecyclerView recyclerView;

    private FriendListViewModel viewModel;
    private FriendListAdapter adapter;

    private String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        getIntents();

        viewModel = setupViewModel();
        adapter = setupAdapter(viewModel);
        preferences = getSharedPreferences("main", MODE_PRIVATE);

        setupViews(viewModel, adapter);
    }

    private void getIntents() {
        Bundle extras = getIntent().getExtras();
        userUID = extras.getString("uid");
    }

    private FriendListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(FriendListViewModel.class);
    }

    @NonNull
    private FriendListAdapter setupAdapter(FriendListViewModel viewModel) {
        FriendListAdapter adapter = new FriendListAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnPersonDeleteClickListener(person -> onPersonDeleteClicked(person, viewModel));
        viewModel.getFriends().observe(this, adapter::setFriends);
        return adapter;
    }

    @SuppressLint("RestrictedApi")
    private void setupRecycler(FriendListAdapter adapter) {
        // We store the recycler view in a field _only_ because we will want to access it in tests.
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setupViews(FriendListViewModel viewModel, FriendListAdapter adapter) {
        setupRecycler(adapter);
    }

    /* Mediation Logic */

    public void onPersonDeleteClicked(Person person, FriendListViewModel viewModel) {
        // Delete the note
        Log.d("Adapter", "Deleted friend " + person.name);
        viewModel.delete(person);
    }

    public void onAddClicked(View view) {
        var input = (EditText) findViewById(R.id.input_new_note_title);
        var title = input.getText().toString();
        viewModel.addPerson(title);
        input.setText("");
    }

    public void onStartClicked(View view) {
        // Store all UIDs with SharedPreference
        saveUserUID();
        saveFriendUIDs();
        // Start compass activity
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    public void saveUserUID() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("0", userUID);
        editor.apply();
    }

    public void saveFriendUIDs() {
        List<Person> friendList = viewModel.getFriends().getValue();
        SharedPreferences.Editor editor = preferences.edit();
        for (int i = 1; i <= friendList.size(); i++) {
            editor.putString("" + i, friendList.get(i - 1).uid);
        }
        editor.apply();
    }
}