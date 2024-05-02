package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText user_field;
    private Button trans_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_field = findViewById(R.id.user_field);
        trans_btn = findViewById(R.id.trans_btn);

        trans_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = user_field.getText().toString().trim();
                String requiredWord = "qwerty";

                if (userInput.equals(requiredWord)) {

                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Неправильное слово", Toast.LENGTH_SHORT).show();
                    user_field.setText(""); // Очищаем поле ввода
                }
            }
        });
    }
}