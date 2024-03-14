package com.example.lib3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelperClient extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "ClientLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "client";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "client_name";
    private static final String COLUMN_BIRTH = "date_of_birth";
    private static final String COLUMN_EMAIL = "client_email";

    public MyDatabaseHelperClient(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + "TEXT, " +
                COLUMN_BIRTH + "TEXT, " +
                COLUMN_EMAIL + "TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
