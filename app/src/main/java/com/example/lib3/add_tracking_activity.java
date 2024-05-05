package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
                String bookTitle = book_input.getText().toString().trim();
                String readerName = reader_input.getText().toString().trim();
                String countText = count_input.getText().toString().trim();
                String borrowDate = borrow_date_input.getText().toString().trim();
                String returnDate = return_date_input.getText().toString().trim();

                // Проверяем, все ли поля заполнены
                if (bookTitle.isEmpty() || readerName.isEmpty() || countText.isEmpty() || borrowDate.isEmpty() || returnDate.isEmpty()) {
                    Toast.makeText(add_tracking_activity.this, "Пожалуйста, заполните все поля.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Проверяем, корректно ли введено количество книг
                int count;
                try {
                    count = Integer.parseInt(countText);
                    if (count <= 0) {
                        Toast.makeText(add_tracking_activity.this, "Введите корректное количество книг.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(add_tracking_activity.this, "Введите корректное количество книг.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Получаем объект базы данных
                SQLiteDatabase db = myDB.getWritableDatabase();

                // Проверяем, есть ли в базе книг достаточное количество для выдачи
                if (myDB.getBookCount(db, bookTitle) >= count) {
                    // Уменьшаем количество книг в базе на выданное количество
                    boolean decreased = myDB.decreaseBookCount(db, bookTitle, count);
                    if (decreased) {
                        // Добавляем запись о выдаче книги
                        DatabaseHelperTracking databaseHelperTracking = new DatabaseHelperTracking(add_tracking_activity.this);
                        boolean isInserted = databaseHelperTracking.insertIssuedBook(
                                bookTitle,
                                readerName,
                                count,
                                borrowDate,
                                returnDate);

                        if (isInserted) {
                            // Успешно добавили книгу, устанавливаем результат и завершаем активность
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            // Если произошла ошибка при добавлении записи о выдаче, возвращаем книги и выводим сообщение об ошибке
                            myDB.increaseBookCount(db, bookTitle, count);
                            Toast.makeText(add_tracking_activity.this, "Ошибка при добавлении книги.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(add_tracking_activity.this, "Ошибка при уменьшении количества книг.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Если в базе недостаточно книг, выводим сообщение об ошибке
                    Toast.makeText(add_tracking_activity.this, "Недостаточное количество книг в наличии.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}