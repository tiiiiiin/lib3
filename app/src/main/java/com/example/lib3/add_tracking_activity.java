package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_tracking_activity extends AppCompatActivity {
    AutoCompleteTextView book_input, reader_input;;
    EditText count_input, borrow_date_input, return_date_input;
    Button add_button;

    MyDatabaseHelper myDB;
    MyDatabaseHelperClient myClientDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);

        myDB = new MyDatabaseHelper(this);
        myClientDB = new MyDatabaseHelperClient(this); // Инициализация базы данных клиентов

        book_input = findViewById(R.id.book_input);
        reader_input = findViewById(R.id.reader_input);
        count_input = findViewById(R.id.count_input);
        borrow_date_input = findViewById(R.id.borrow_date_input);
        return_date_input = findViewById(R.id.return_date_input);
        add_button = findViewById(R.id.add_button);

        // Адаптер для AutoCompleteTextView книг
        ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, myDB.getAllBookTitles());
        book_input.setAdapter(bookAdapter);

        // Адаптер для AutoCompleteTextView клиентов
        ArrayAdapter<String> clientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, myClientDB.getAllClientNames());
        reader_input.setAdapter(clientAdapter);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelperTracking databaseHelperTracking = new DatabaseHelperTracking(add_tracking_activity.this);
                boolean isInserted = databaseHelperTracking.insertIssuedBook(
                        book_input.getText().toString().trim(),
                        reader_input.getText().toString().trim(),
                        Integer.valueOf(count_input.getText().toString().trim()),
                        borrow_date_input.getText().toString().trim(),
                        return_date_input.getText().toString().trim());

                if (isInserted) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(add_tracking_activity.this, "Ошибка при добавлении книги.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}