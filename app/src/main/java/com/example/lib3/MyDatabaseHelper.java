package com.example.lib3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.TooManyListenersException;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME ="BookLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_COUNT = "book_count";

     MyDatabaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_COUNT + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    void addBook(String title, String author, int count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE ,title);
        cv.put(COLUMN_AUTHOR ,author);
        cv.put(COLUMN_COUNT,count);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Провал.",Toast.LENGTH_SHORT).show();
        }
        else { Toast.makeText(context, "Добавлено успешно!", Toast.LENGTH_SHORT).show();

        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    void updateData(String row_id, String title, String author, String count){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put(COLUMN_TITLE, title);
         cv.put(COLUMN_AUTHOR, author);
         cv.put(COLUMN_COUNT, count);

         long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
         if(result == -1){
             Toast.makeText(context, "Провал обновления данных.", Toast.LENGTH_SHORT).show();
         }else {
             Toast.makeText(context, "Успешное обновление!", Toast.LENGTH_SHORT).show();
         }
    }
    void deleteOneRow(String row_id){
         SQLiteDatabase db = this.getWritableDatabase();
         long result = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});
         if(result == -1){
             Toast.makeText(context,"Провал при удалении.", Toast.LENGTH_SHORT).show();
         } else{
             Toast.makeText(context,"Успешное удаление.", Toast.LENGTH_SHORT).show();
         }
    }
    void deleteAllData(){
         SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("DELETE FROM "+ TABLE_NAME);
    }
    public Cursor searchData(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_COUNT};
        String selection = COLUMN_TITLE + " LIKE '%" + query + "%' OR " + COLUMN_AUTHOR + " LIKE '%" + query + "%'";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, null, null, null, null);
        return cursor;
    }

    public ArrayList<String> getAllBookTitles() {
        ArrayList<String> title = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TITLE + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                title.add(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return title;
    }
}
