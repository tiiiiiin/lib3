package com.example.lib3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHelperClient extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "ClientLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "client";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "client_name";
    private static final String COLUMN_BIRTH = "date_of_birth";
    private static final String COLUMN_EMAIL = "client_email";

    MyDatabaseHelperClient(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_BIRTH + " TEXT, " +
                COLUMN_EMAIL + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addClient(String client_name, String date_of_birth, String client_email ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, client_name);
        cv.put(COLUMN_BIRTH, date_of_birth);
        cv.put(COLUMN_EMAIL, client_email);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Провал.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Успешно добавлено!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String name, String data, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_BIRTH, data);
        cv.put(COLUMN_EMAIL, email);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Провал обновления данных.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Успешное обновление!", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Провал удаление данных.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Успешное удаление!", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor searchClient(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_BIRTH, COLUMN_EMAIL};
        String selection = COLUMN_NAME + " LIKE '%" + query + "%' OR " + COLUMN_EMAIL + " LIKE '%" + query + "%'";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, null, null, null, null);
        return cursor;
    }

    public ArrayList<String> getAllClientNames() {
        ArrayList<String> name = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                name.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return name;
    }
}
