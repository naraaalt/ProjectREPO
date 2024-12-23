package com.example.eventticket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminPanelActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnLogout;
    private EventAdapter adapter;
    private List<Event> eventList = new ArrayList<>();
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        db = new DatabaseHelper(this);

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logout());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(eventList, this::onEditEvent, this::onDeleteEvent);
        recyclerView.setAdapter(adapter);

        loadEvents();

        findViewById(R.id.btnAddEvent).setOnClickListener(view -> {
            Intent intent = new Intent(AdminPanelActivity.this, EventFormActivity.class);
            startActivity(intent);
        });
    }


    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(AdminPanelActivity.this, LoginActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

    private void onEditEvent(Event event) {
        Intent intent = new Intent(AdminPanelActivity.this, EventFormActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }

    private void onDeleteEvent(Event event) {
        if (db.deleteEvent(event.getId())) {
            Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT).show();
            loadEvents();
        } else {
            Toast.makeText(this, "Failed to delete event", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }
}

