package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LaundryDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SERVICE = "service_name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SERVICE + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    // Insert
    public long insertOrder(String service, double price, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE, service);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_STATUS, status);
        return db.insert(TABLE_ORDERS, null, values);
    }

    // Read
    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }

    // Update
    public int updateOrder(int id, String service, double price, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE, service);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_STATUS, status);
        return db.update(TABLE_ORDERS, values, COLUMN_ID + " = ?", new String[]{id + ""});
    }

    // Delete
    public void deleteOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Search
    public Cursor searchOrders(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE " + COLUMN_SERVICE + " LIKE ?", new String[]{"%" + query + "%"});
    }
}
