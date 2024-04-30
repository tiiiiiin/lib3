package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddClientActivity extends AppCompatActivity {
    EditText name_input, date_input, email_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client_activity);

        name_input = findViewById(R.id.name_input);
        date_input = findViewById(R.id.date_input);
        email_input = findViewById(R.id.email_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelperClient myDB = new MyDatabaseHelperClient(AddClientActivity.this);
                myDB.addClient(name_input.getText().toString().trim(),
                        date_input.getText().toString().trim(),
                        email_input.getText().toString().trim());
            }
        });
    }
}