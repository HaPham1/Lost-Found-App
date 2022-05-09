package com.example.lostandfoundapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lostandfoundapp.model.Item;
import com.example.lostandfoundapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + String.valueOf(Util.ITEM_ID) + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                        + Util.TYPE + " TEXT," + Util.NAME + " TEXT," + Util.PHONE + " TEXT," + Util.DESCRIPTION + " TEXT," + Util.DATE + " TEXT," + Util.LOCATION + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS ";
        sqLiteDatabase.execSQL(DROP_USER_TABLE + Util.TABLE_NAME + ";");

        onCreate(sqLiteDatabase);
    }

    public long insertItem (Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TYPE, item.getType());
        contentValues.put(Util.NAME, item.getName());
        contentValues.put(Util.PHONE, item.getPhone());
        contentValues.put(Util.DESCRIPTION, item.getDescription());
        contentValues.put(Util.DATE, item.getDate());
        contentValues.put(Util.LOCATION, item.getLocation());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public List<Item> fetchAllItems () {
        List<Item> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setItem_id(cursor.getInt(cursor.getColumnIndex(Util.ITEM_ID)));
                item.setType(cursor.getString(cursor.getColumnIndex(Util.TYPE)));
                item.setName(cursor.getString(cursor.getColumnIndex(Util.NAME)));
                item.setPhone(cursor.getString(cursor.getColumnIndex(Util.PHONE)));
                item.setDescription(cursor.getString(cursor.getColumnIndex(Util.DESCRIPTION)));
                item.setDate(cursor.getString(cursor.getColumnIndex(Util.DATE)));
                item.setLocation(cursor.getString(cursor.getColumnIndex(Util.LOCATION)));
                itemList.add(item);

            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public long deleteItem(String type, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Util.TABLE_NAME, Util.TYPE + "=? AND " + Util.NAME + "=?", new String[] {type, name});
        return result;
    }
}
