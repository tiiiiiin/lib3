package com.example.lib3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperTracking extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "databasehelpertracking.db";
    private static final String TABLE_NAME = "issued_books";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "BOOK_TITLE";
    private static final String COL_3 = "READER_NAME";
    private static final String COL_4 = "BOOK_COUNT";
    private static final String COL_5 = "BORROW_DATE";
    private static final String COL_6 = "RETURN_DATE";

    public DatabaseHelperTracking(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " INTEGER, " +
                COL_5 + " TEXT, " +
                COL_6 + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertIssuedBook(String bookTitle, String readerName, int bookCount, String borrowDate, String returnDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, bookTitle);
        contentValues.put(COL_3, readerName);
        contentValues.put(COL_4, bookCount);
        contentValues.put(COL_5, borrowDate);
        contentValues.put(COL_6, returnDate);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllIssuedBooks() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor searchData(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6};
        String selection = COL_2 + " LIKE '%" + query + "%' OR " + COL_3 + " LIKE '%" + query + "%'";
        return db.query(TABLE_NAME, columns, selection, null, null, null, null);
    }
}
