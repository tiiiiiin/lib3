package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_tracking_activity extends AppCompatActivity {
    EditText book_input, reader_input, count_input, borrow_date_input, return_date_input;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);  book_input = findViewById(R.id.book_input);
        reader_input = findViewById(R.id.reader_input);
        count_input = findViewById(R.id.count_input);
        borrow_date_input = findViewById(R.id.borrow_date_input);
        return_date_input = findViewById(R.id.return_date_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelperTracking myDB = new DatabaseHelperTracking(add_tracking_activity.this);
                boolean isInserted = myDB.insertIssuedBook(
                        book_input.getText().toString().trim(),
                        reader_input.getText().toString().trim(),
                        Integer.valueOf(count_input.getText().toString().trim()),
                        borrow_date_input.getText().toString().trim(),
                        return_date_input.getText().toString().trim());

                if (isInserted) {
                    // Добавляем setResult после добавления книги в базу данных
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish(); // Завершаем активность после добавления книги
                } else {
                    // В случае ошибки при добавлении, показываем сообщение
                    // Можно также добавить дополнительную логику обработки ошибки
                    // Например, показать диалог с предложением повторить попытку
                    // или показать сообщение об ошибке пользователю
                    // В данном примере просто показываем короткое уведомление Toast
                    Toast.makeText(add_tracking_activity.this, "Ошибка при добавлении книги.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}