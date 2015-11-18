package com.jijiyan.todoapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jijiyan.todoapp.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhyuan on 11/17/2015.
 */
public class ItemDBHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "toDoApp";
    private static final int DATABASE_VERSION = 1;
    // Table Names
    private static final String TABLE_ITEM = "item";
    // Item Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_NAME = "name";
    private static final String KEY_ITEM_NOTE = "note";
    private static final String KEY_ITEM_DESCRIPTION = "description";
    private static ItemDBHelper INSTANCE;
    private static final String TAG = ItemDBHelper.class.getName();

    public static synchronized ItemDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (INSTANCE == null) {
            INSTANCE = new ItemDBHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY," +
                KEY_ITEM_NAME + " TEXT," +
                KEY_ITEM_NOTE + " TEXT," +
                KEY_ITEM_DESCRIPTION + " TEXT" +
                ")";

        db.execSQL(CREATE_ITEM_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
            onCreate(db);
        }
    }

    // Insert a post into the database
    public void addItem(Item item) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_NAME, item.getName());
            values.put(KEY_ITEM_NOTE, item.getNote());
            values.put(KEY_ITEM_DESCRIPTION, item.getDescription());
            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_ITEM, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String ITEMS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_ITEM);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setName(cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME)));
                    item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID)));
                    item.setNote(cursor.getString(cursor.getColumnIndex(KEY_ITEM_NOTE)));
                    item.setDescription(cursor.getString(cursor.getColumnIndex(KEY_ITEM_DESCRIPTION)));
                    items.add(item);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }

    public void updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_NAME, item.getName());
            values.put(KEY_ITEM_NOTE, item.getNote());
            values.put(KEY_ITEM_DESCRIPTION, item.getDescription());
            db.update(TABLE_ITEM, values, KEY_ITEM_ID + " = ?", new String[]{String.valueOf(item.getId())});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to update item");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_ITEM, KEY_ITEM_ID + " = ?", new String[]{String.valueOf(item.getId())});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete item");
        } finally {
            db.endTransaction();
        }
    }
}
