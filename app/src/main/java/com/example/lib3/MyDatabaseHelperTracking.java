package com.example.lib3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelperTracking extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "TrackingLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "issued_books";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BOOK_TITLE = "book_title";
    private static final String COLUMN_READER_NAME = "reader_name";
    //private static final String COLUMN_COUNT = "count";
    private static final String COLUMN_BORROW_DATE = "borrow_date";
    private static final String COLUMN_RETURN_DATE = "return_date";

    public MyDatabaseHelperTracking( @Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_TITLE + " TEXT, " +
                COLUMN_READER_NAME + " TEXT, " +
                COLUMN_BORROW_DATE + " TEXT, " +
                COLUMN_RETURN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addTracking(String title, String name, String dateBorrow, String dateReturn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_TITLE, title);
        cv.put(COLUMN_READER_NAME, name);
        //cv.put(COLUMN_COUNT, count);
        cv.put(COLUMN_BORROW_DATE, dateBorrow);
        cv.put(COLUMN_RETURN_DATE, dateReturn);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка при сохранении выданной книги.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Выдача успешно зафиксирована!", Toast.LENGTH_SHORT).show();
        }

    }
    Cursor readAllDataTracking(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteOneRowTracking(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Провал.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Книга возвращена!", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor searchTracking(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_BOOK_TITLE, COLUMN_READER_NAME, COLUMN_BORROW_DATE, COLUMN_RETURN_DATE};
        String selection = COLUMN_BOOK_TITLE + " LIKE '%" + query + "%' OR " + COLUMN_READER_NAME +
                " LIKE '%" + query + "%' OR " + COLUMN_RETURN_DATE + " LIKE '%" + query + "%'";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, null, null, null, null);
        return cursor;
    }


}
