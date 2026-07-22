package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LaundryDB";
    private static final int DATABASE_VERSION = 3;

    // Table: Orders
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SERVICE = "service_name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_STATUS = "status";

    // Table: Users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Table: Basket
    public static final String TABLE_BASKET = "basket";
    public static final String COLUMN_BASKET_ID = "basket_id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_PRICE = "item_price";
    public static final String COLUMN_ITEM_QUANTITY = "quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
//creating db schema
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SERVICE + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_BASKET_TABLE = "CREATE TABLE " + TABLE_BASKET + "("
                + COLUMN_BASKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_PRICE + " REAL,"
                + COLUMN_ITEM_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_BASKET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        }
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BASKET);
        }
        onCreate(db);
    }

    // --- USER METHODS ---

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // --- BASKET METHODS ---

    public long addToBasket(String name, double price, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_QUANTITY, qty);
        return db.insert(TABLE_BASKET, null, values);
    }

    public Cursor getBasketItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BASKET, null);
    }

    public void clearBasket() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BASKET, null, null);
    }

    // --- ORDER METHODS ---

    public long insertOrder(String service, double price, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE, service);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_STATUS, status);
        return db.insert(TABLE_ORDERS, null, values);
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }

    public int updateOrder(int id, String service, double price, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE, service);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_STATUS, status);
        return db.update(TABLE_ORDERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor searchOrders(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE " + COLUMN_SERVICE + " LIKE ?", new String[]{"%" + query + "%"});
    }
}
