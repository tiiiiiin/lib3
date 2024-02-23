package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBookActivity extends AppCompatActivity {
    EditText title_input, author_input, count_input;
    Button update_button;

    String id, title, author, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        count_input = findViewById(R.id.count_input2);
        update_button = findViewById(R.id.update_button);

        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateBookActivity.this);
                myDB.updateData(id, title, author, count);
                
            }
        });
    }
    void getAndSetIntentData(){
        Intent intent = getIntent();
        if(intent.hasExtra("id") && intent.hasExtra("title") &&
                intent.hasExtra("author") && intent.hasExtra("count")) {
            //get data
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author= getIntent().getStringExtra("author");
            count = getIntent().getStringExtra("count");

            //set data
            title_input.setText(title);
            author_input.setText(author);
            count_input.setText(count);


        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}