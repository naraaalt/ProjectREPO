package com.example.eventticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EventApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table users
    private static final String TABLE_USERS = "users";
    private static final String USERS_COL_ID = "id";
    private static final String USERS_COL_USERNAME = "username";
    private static final String USERS_COL_PASSWORD = "password";
    private static final String USERS_COL_ROLE = "role";

    // Table events
    private static final String TABLE_EVENTS = "events";
    private static final String EVENTS_COL_ID = "id";
    private static final String EVENTS_COL_NAME = "name";
    private static final String EVENTS_COL_PLACE = "place";
    private static final String EVENTS_COL_DESC = "description";
    private static final String EVENTS_COL_DATE = "date";
    private static final String EVENTS_COL_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                USERS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERS_COL_USERNAME + " TEXT," +
                USERS_COL_PASSWORD + " TEXT," +
                USERS_COL_ROLE + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_EVENTS + "(" +
                EVENTS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EVENTS_COL_NAME + " TEXT," +
                EVENTS_COL_PLACE + " TEXT," +
                EVENTS_COL_DESC + " TEXT," +
                EVENTS_COL_DATE + " TEXT," +
                EVENTS_COL_PRICE + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    // Add user
    public boolean addUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COL_USERNAME, username);
        values.put(USERS_COL_PASSWORD, password);
        values.put(USERS_COL_ROLE, role);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Validate login
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS +
                        " WHERE " + USERS_COL_USERNAME + "=? AND " + USERS_COL_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isLoggedIn = cursor.getCount() > 0;
        cursor.close();
        return isLoggedIn;

    }

    // Add event
    public boolean addEvent(String name, String place, String description, String date, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENTS_COL_NAME, name);
        values.put(EVENTS_COL_PLACE, place);
        values.put(EVENTS_COL_DESC, description);
        values.put(EVENTS_COL_DATE, date);
        values.put(EVENTS_COL_PRICE, price);

        long result = db.insert(TABLE_EVENTS, null, values);
        return result != -1;
    }

    // Get all events
    public Cursor getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
    }

    // Delete event
    public boolean deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EVENTS, EVENTS_COL_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    // Update event
    public boolean updateEvent(int id, String name, String place, String description, String date, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENTS_COL_NAME, name);
        values.put(EVENTS_COL_PLACE, place);
        values.put(EVENTS_COL_DESC, description);
        values.put(EVENTS_COL_DATE, date);
        values.put(EVENTS_COL_PRICE, price);

        return db.update(TABLE_EVENTS, values, EVENTS_COL_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }
}