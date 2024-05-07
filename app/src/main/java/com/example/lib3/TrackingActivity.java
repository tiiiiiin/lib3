package com.example.lib3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TrackingActivity extends AppCompatActivity {
    RecyclerView recyclerViewIssuedBooks;
    FloatingActionButton add_button;
    MyDatabaseHelperTracking myDB;

    ArrayList<String> book_id, book_title, reader_name, borrow_date, return_date;
    CustomAdapterTracking customAdapterTracking;
    Button search_button;
    private static final int ADD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        recyclerViewIssuedBooks = findViewById(R.id.recyclerViewIssuedBooks);
        add_button = findViewById(R.id.add_button);

        myDB = new MyDatabaseHelperTracking(TrackingActivity.this);

        // Инициализируем списки
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        reader_name = new ArrayList<>();
        borrow_date = new ArrayList<>();
        return_date = new ArrayList<>();

        // Загружаем данные
        storeDataInArraysTracking();

        // Инициализируем адаптер и устанавливаем его
        customAdapterTracking = new CustomAdapterTracking(TrackingActivity.this, book_id, book_title, reader_name, borrow_date, return_date);
        recyclerViewIssuedBooks.setAdapter(customAdapterTracking);
        recyclerViewIssuedBooks.setLayoutManager(new LinearLayoutManager(TrackingActivity.this));

        // Обработчик клика кнопки "Добавить"
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackingActivity.this, add_tracking_activity.class);
                startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
            }
        });
        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
        customAdapterTracking.setOnItemClickListener(new CustomAdapterTracking.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Создаем диалоговое окно подтверждения
                AlertDialog.Builder builder = new AlertDialog.Builder(TrackingActivity.this);
                builder.setTitle("Подтверждение возврата");
                builder.setMessage("Книгу вернули?");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Удаляем данные из базы данных
                        myDB.deleteOneRowTracking(book_id.get(position));
                        // Удаляем элемент из списка
                        book_id.remove(position);
                        book_title.remove(position);
                        reader_name.remove(position);
                        borrow_date.remove(position);
                        return_date.remove(position);
                        customAdapterTracking.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    // Загрузка данных из базы данных
    void storeDataInArraysTracking() {
        Cursor cursor = myDB.readAllDataTracking();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Нет данных.", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                reader_name.add(cursor.getString(2));
                borrow_date.add(cursor.getString(3));
                return_date.add(cursor.getString(4));
            }
            Log.d("Data", "Client IDs: " + book_id.toString());
        }
    }

    private void performSearch() {
        EditText searchEditText = findViewById(R.id.search_edit_text);
        String searchText = searchEditText.getText().toString();

        ArrayList<String> searchedBookId = new ArrayList<>();
        ArrayList<String> searchedBookTitle = new ArrayList<>();
        ArrayList<String> searchedBookReader = new ArrayList<>();
        ArrayList<String> searchedBorrowDate = new ArrayList<>();
        ArrayList<String> searchedReturnDate = new ArrayList<>();

        Cursor cursor = myDB.searchTracking(searchText);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Данные не найдены.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                searchedBookId.add(cursor.getString(0));
                searchedBookTitle.add(cursor.getString(1));
                searchedBookReader.add(cursor.getString(2));
                searchedBorrowDate.add(cursor.getString(3));
                searchedReturnDate.add(cursor.getString(4));
            }
            customAdapterTracking.updateData(searchedBookId, searchedBookTitle, searchedBookReader, searchedBorrowDate, searchedReturnDate);
            Log.d("SearchActivity", "Search results: " + searchedBookTitle.toString());
        }
    }
}