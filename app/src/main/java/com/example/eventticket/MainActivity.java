package com.example.eventticket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", null);

        if (role != null) {
            if (role.equals("admin")) {
                startActivity(new Intent(MainActivity.this, AdminPanelActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, UserEventsActivity.class));
            }
            finish();
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}

