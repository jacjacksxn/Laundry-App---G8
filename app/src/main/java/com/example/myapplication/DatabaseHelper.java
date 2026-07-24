package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LaundryDB";
    private static final int DATABASE_VERSION = 6; // Bumped for Hybrid Pricing

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_ADDRESS = "address";

    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SERVICE = "service_name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_PRICING_TYPE = "pricing_type"; // "item" or "weight"
    public static final String COLUMN_QUANTITY = "quantity"; // weight in kg or item count

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SERVICE + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_STATUS + " TEXT,"
                + COLUMN_PRICING_TYPE + " TEXT,"
                + COLUMN_QUANTITY + " REAL" + ")");
        
        db.execSQL("CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_CITY + " TEXT,"
                + COLUMN_ADDRESS + " TEXT" + ")");

        db.execSQL("CREATE TABLE basket (basket_id INTEGER PRIMARY KEY AUTOINCREMENT, item_name TEXT, item_price REAL, quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS basket");
        onCreate(db);
    }

    // User Methods
    public boolean addUser(String u, String p, String e, String ph, String c, String a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COLUMN_USERNAME, u);
        v.put(COLUMN_PASSWORD, p);
        v.put(COLUMN_EMAIL, e);
        v.put(COLUMN_PHONE, ph);
        v.put(COLUMN_CITY, c);
        v.put(COLUMN_ADDRESS, a);
        return db.insert(TABLE_USERS, null, v) != -1;
    }

    public boolean checkUser(String u, String p) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{u, p});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    public Cursor getUser(String u) {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?", new String[]{u});
    }

    // Order Methods
    public Cursor getAllOrders() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }

    public Cursor searchOrders(String query) {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE " + COLUMN_SERVICE + " LIKE ?", new String[]{"%" + query + "%"});
    }

    public long insertHybridOrder(String service, double unitRate, String type, double qty, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COLUMN_SERVICE, service);
        v.put(COLUMN_PRICE, unitRate * qty);
        v.put(COLUMN_PRICING_TYPE, type);
        v.put(COLUMN_QUANTITY, qty);
        v.put(COLUMN_STATUS, status);
        return db.insert(TABLE_ORDERS, null, v);
    }

    public long insertServiceByType(String type, String status) {
        double price;
        switch (type.toLowerCase()) {
            case "ironing": price = 15.0; break;
            case "wash & iron": price = 30.0; break;
            case "dry cleaning": price = 45.0; break;
            case "wash & fold": price = 25.0; break;
            default: price = 20.0;
        }
        return insertHybridOrder(type, price, "item", 1, status);
    }

    public void updateOrder(int id, String service, double price, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COLUMN_SERVICE, service);
        v.put(COLUMN_PRICE, price);
        v.put(COLUMN_STATUS, status);
        db.update(TABLE_ORDERS, v, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteOrder(int id) {
        this.getWritableDatabase().delete(TABLE_ORDERS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
