package com.example.eventticket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper db;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (username.equals("admin") && password.equals("admin123")) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("role", "admin");
                editor.putBoolean("IS_ADMIN", true);
                editor.apply();

                Toast.makeText(this, "Welcome Admin!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this, AdminPanelActivity.class));
                finish();
            } else if (db.validateUser(username, password)) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME", username);
                editor.putBoolean("IS_LOGGED_IN", true);
                editor.apply();

                Toast.makeText(this, "Login Successfull!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, UserEventsActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }

        });

        btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}

