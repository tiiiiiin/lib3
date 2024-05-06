package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TrackingActivity extends AppCompatActivity {
    RecyclerView recyclerViewIssuedBooks;
    FloatingActionButton add_button;
    MyDatabaseHelperTracking myDB;

    ArrayList<String> book_id, book_title, reader_name, borrow_date, return_date;
    CustomAdapterTracking customAdapterTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking); // Поменяйте на правильное название вашего layout файла

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
                startActivity(intent);
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
}