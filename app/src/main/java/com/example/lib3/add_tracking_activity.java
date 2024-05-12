package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class add_tracking_activity extends AppCompatActivity {
    AutoCompleteTextView book_input, reader_input;
    EditText borrow_date_input, return_date_input;
    Button add_button;
    MyDatabaseHelper myDB;
    MyDatabaseHelperClient clientDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);
        myDB = new MyDatabaseHelper(add_tracking_activity.this);
        clientDB = new MyDatabaseHelperClient(add_tracking_activity.this);

        book_input = findViewById(R.id.book_input);
        reader_input = findViewById(R.id.reader_input);
        //count_input = findViewById(R.id.count_input);
        borrow_date_input = findViewById(R.id.borrow_date_input);
        return_date_input = findViewById(R.id.return_date_input);
        add_button = findViewById(R.id.add_button);

        // Получаем предложения для книг из базы данных
        ArrayList<String> bookSuggestions = myDB.getBookSuggestions();
        ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bookSuggestions);
        book_input.setAdapter(bookAdapter);

        // Получаем предложения для читателей из базы данных
        ArrayList<String> readerSuggestions = clientDB.getClientSuggestions();
        ArrayAdapter<String> readerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, readerSuggestions);
        reader_input.setAdapter(readerAdapter);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelperTracking myDBTracking = new MyDatabaseHelperTracking(add_tracking_activity.this);
                myDBTracking.addTracking(book_input.getText().toString().trim(),
                        reader_input.getText().toString().trim(),
                        borrow_date_input.getText().toString().trim(),
                        return_date_input.getText().toString().trim());
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}