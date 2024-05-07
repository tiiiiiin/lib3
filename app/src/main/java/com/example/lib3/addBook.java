package com.example.lib3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class addBook extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_count;
    CustomAdapter customAdapter;
    private static final int ADD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        recyclerView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addBook.this, AddActivity.class);
                startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
            }
        });

        myDB = new MyDatabaseHelper(addBook.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_count = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(addBook.this, this, book_id, book_title, book_author, book_count);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(addBook.this));

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }
    private void performSearch() {
        EditText searchEditText = findViewById(R.id.search_edit_text);
        String searchText = searchEditText.getText().toString();

        ArrayList<String> searchedBookId = new ArrayList<>();
        ArrayList<String> searchedBookTitle = new ArrayList<>();
        ArrayList<String> searchedBookAuthor = new ArrayList<>();
        ArrayList<String> searchedBookCount = new ArrayList<>();

        Cursor cursor = myDB.searchData(searchText);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Данные не найдены.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                searchedBookId.add(cursor.getString(0));
                searchedBookTitle.add(cursor.getString(1));
                searchedBookAuthor.add(cursor.getString(2));
                searchedBookCount.add(cursor.getString(3));
            }
            customAdapter.updateData(searchedBookId, searchedBookTitle, searchedBookAuthor, searchedBookCount);
            Log.d("SearchActivity", "Search results: " + searchedBookTitle.toString());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            storeDataInArrays();
            customAdapter.notifyDataSetChanged();
        }
    }
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Нет данных.", Toast.LENGTH_SHORT).show();
        } else {
            book_id.clear(); // Очищаем списки перед обновлением данных
            book_title.clear();
            book_author.clear();
            book_count.clear();
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_count.add(cursor.getString(3));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}