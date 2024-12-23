package com.example.eventticket;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EventFormActivity extends AppCompatActivity {
    private EditText etName, etPlace, etDescription, etPrice;
    private TextView tvDate;
    private Button btnSubmit;
    private DatabaseHelper db;
    private int eventId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);

        db = new DatabaseHelper(this);

        etName = findViewById(R.id.etName);
        etPlace = findViewById(R.id.etPlace);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        tvDate = findViewById(R.id.tvDate);
        btnSubmit = findViewById(R.id.btnSubmit);

        tvDate.setOnClickListener(view -> showDatePicker());

        Intent intent = getIntent();
        if (intent.hasExtra("event")) {
            Event event = (Event) intent.getSerializableExtra("event");
            eventId = event.getId();
            etName.setText(event.getName());
            etPlace.setText(event.getPlace());
            etDescription.setText(event.getDescription());
            tvDate.setText(event.getDate());
            etPrice.setText(String.valueOf(event.getPrice()));
            btnSubmit.setText("Update Event");
        }

        btnSubmit.setOnClickListener(view -> {
            String name = etName.getText().toString();
            String place = etPlace.getText().toString();
            String description = etDescription.getText().toString();
            String date = tvDate.getText().toString();
            double price = Double.parseDouble(etPrice.getText().toString());

            if (eventId == -1) {
                if (db.addEvent(name, place, description, date, price)) {
                    Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
                    clearText();
                } else {
                    Toast.makeText(this, "Failed to add event", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (db.updateEvent(eventId, name, place, description, date, price)) {
                    Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
                    clearText();
                } else {
                    Toast.makeText(this, "Failed to update event", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });
    }

    private void clearText(){
        etName.setText("");
        etPlace.setText("");
        etDescription.setText("");
        etPrice.setText("");

    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> tvDate.setText(year1 + "-" + (month1 + 1) + "-" + dayOfMonth),
                year, month, day);
        datePickerDialog.show();
    }
}

