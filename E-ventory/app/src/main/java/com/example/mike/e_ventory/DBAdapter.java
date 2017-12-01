package com.example.mike.e_ventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_TITLE = "title";
    static final String KEY_PRICE = "price";
    static final String KEY_URL = "url";
    static final String KEY_QUANTITY = "quantity";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_VENDOR = "vendor";
    static final String KEY_LISTINGID = "listingid";
    static final String KEY_STATE = "state";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "inventory";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
            "create table inventory (_id integer primary key autoincrement, "
                    + "title text not null unique, price real not null, url text, quantity integer not null, description text not null, vendor text not null, listingid text, state text);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertInventory(String title, float price, String url, int quantity, String description, String vendor, String listingid, String state ) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_PRICE, price);
        initialValues.put(KEY_URL, url);
        initialValues.put(KEY_QUANTITY, quantity);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_VENDOR, vendor);
        initialValues.put(KEY_LISTINGID, listingid);
        initialValues.put(KEY_STATE, state);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteInventoryItem(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllInventory() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_PRICE,KEY_URL,KEY_QUANTITY,KEY_DESCRIPTION,KEY_VENDOR,KEY_LISTINGID,KEY_STATE}, null, null, null, null, null);
    }

    //---retrieves a particular item---
    public Cursor getInventoryItem(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_PRICE,KEY_URL,KEY_QUANTITY,KEY_DESCRIPTION,KEY_VENDOR,KEY_LISTINGID,KEY_STATE}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateInventoryItem(long rowId, String title, float price, String url, int quantity, String description, String vendor, String listingid, String state) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_PRICE, price);
        args.put(KEY_URL, url);
        args.put(KEY_QUANTITY, quantity);
        args.put(KEY_DESCRIPTION, description);
        args.put(KEY_VENDOR, vendor);
        args.put(KEY_LISTINGID, listingid);
        args.put(KEY_STATE, state);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllOrderedBy(String column){
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_PRICE,KEY_URL,KEY_QUANTITY,KEY_DESCRIPTION,KEY_VENDOR}, null, null, null, null, column);
    }

}
