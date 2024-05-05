package com.example.lib3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TrackingActivity extends AppCompatActivity {
    RecyclerView recyclerViewIssuedBooks;
    FloatingActionButton add_button;
    DatabaseHelperTracking myDB;
    EditText searchEditText;
    Button searchButton;
    CustomAdapterTracking customAdapterTracking;

    private static final int ADD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        recyclerViewIssuedBooks = findViewById(R.id.recyclerViewIssuedBooks);
        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.search_button);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackingActivity.this, add_tracking_activity.class);
                startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
            }
        });

        myDB = new DatabaseHelperTracking(this);
        ArrayList<String> bookIds = new ArrayList<>();
        ArrayList<String> bookTitles = new ArrayList<>();
        ArrayList<String> readerNames = new ArrayList<>();
        ArrayList<String> bookCounts = new ArrayList<>();
        ArrayList<String> borrowDates = new ArrayList<>();
        ArrayList<String> returnDates = new ArrayList<>();

        storeDataInArrays(bookIds, bookTitles, readerNames, bookCounts, borrowDates, returnDates);

        customAdapterTracking = new CustomAdapterTracking(this, this, bookIds, bookTitles, readerNames, bookCounts, borrowDates, returnDates, new CustomAdapterTracking.OnItemClickListener() {
            @Override
            public void onItemClick(String bookId, String bookTitle, String readerName, String bookCount, String borrowDate, String returnDate) {

            }
        });
        recyclerViewIssuedBooks.setAdapter(customAdapterTracking);
        recyclerViewIssuedBooks.setLayoutManager(new LinearLayoutManager(TrackingActivity.this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Если результат ОК, обновляем список выданных книг
                updateIssuedBooksList();
            }
        }
    }

    // Метод для обновления списка выданных книг
    private void updateIssuedBooksList() {
        ArrayList<String> bookIds = new ArrayList<>();
        ArrayList<String> bookTitles = new ArrayList<>();
        ArrayList<String> readerNames = new ArrayList<>();
        ArrayList<String> bookCounts = new ArrayList<>();
        ArrayList<String> borrowDates = new ArrayList<>();
        ArrayList<String> returnDates = new ArrayList<>();

        storeDataInArrays(bookIds, bookTitles, readerNames, bookCounts, borrowDates, returnDates);

        customAdapterTracking.updateData(bookIds, bookTitles, readerNames, bookCounts, borrowDates, returnDates);
        // Добавляем эту строку для обновления RecyclerView
        customAdapterTracking.notifyDataSetChanged();
    }

    private void performSearch() {
        String searchText = searchEditText.getText().toString();

        ArrayList<String> bookIds = new ArrayList<>();
        ArrayList<String> bookTitles = new ArrayList<>();
        ArrayList<String> readerNames = new ArrayList<>();
        ArrayList<String> bookCounts = new ArrayList<>();
        ArrayList<String> borrowDates = new ArrayList<>();
        ArrayList<String> returnDates = new ArrayList<>();

        Cursor cursor = myDB.searchData(searchText);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Данные не найдены.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                bookIds.add(cursor.getString(0));
                bookTitles.add(cursor.getString(1));
                readerNames.add(cursor.getString(2));
                bookCounts.add(cursor.getString(3));
                borrowDates.add(cursor.getString(4));
                returnDates.add(cursor.getString(5));
            }
            customAdapterTracking.updateData(bookIds, bookTitles, readerNames, bookCounts, borrowDates, returnDates);
            Toast.makeText(this, "Найдено записей: " + cursor.getCount(), Toast.LENGTH_SHORT).show();
        }
    }

    private void storeDataInArrays(ArrayList<String> bookIds, ArrayList<String> bookTitles, ArrayList<String> readerNames, ArrayList<String> bookCounts, ArrayList<String> borrowDates, ArrayList<String> returnDates) {
        Cursor cursor = myDB.getAllIssuedBooks();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Нет данных.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                bookIds.add(cursor.getString(0));
                bookTitles.add(cursor.getString(1));
                readerNames.add(cursor.getString(2));
                bookCounts.add(cursor.getString(3));
                borrowDates.add(cursor.getString(4));
                returnDates.add(cursor.getString(5));
            }
        }
    }
}
