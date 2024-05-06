package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_tracking_activity extends AppCompatActivity {
    EditText book_input, reader_input, borrow_date_input, return_date_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);

        book_input = findViewById(R.id.book_input);
        reader_input = findViewById(R.id.reader_input);
        //count_input = findViewById(R.id.count_input);
        borrow_date_input = findViewById(R.id.borrow_date_input);
        return_date_input = findViewById(R.id.return_date_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelperTracking myDB = new MyDatabaseHelperTracking(add_tracking_activity.this);
                myDB.addTracking(book_input.getText().toString().trim(),
                        reader_input.getText().toString().trim(),
                        borrow_date_input.getText().toString().trim(),
                        return_date_input.getText().toString().trim());
            }
        });
    }
}