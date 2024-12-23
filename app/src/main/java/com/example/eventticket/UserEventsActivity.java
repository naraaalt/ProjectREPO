package com.example.eventticket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserEventsActivity extends AppCompatActivity {
    private Button btnLogout;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<Event> eventList = new ArrayList<>();
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_events);

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "User");

        db = new DatabaseHelper(this);

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome " + username + "!");
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logout());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(eventList, null, null);
        recyclerView.setAdapter(adapter);

        loadEvents();
    }

    private void loadEvents() {
        eventList.clear();
        Cursor cursor = db.getAllEvents();
        while (cursor.moveToNext()) {
            eventList.add(new Event(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getDouble(5)
            ));
        }
        adapter.notifyDataSetChanged();
    }

    private void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(UserEventsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

