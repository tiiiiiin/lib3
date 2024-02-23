package com.example.lib3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBookActivity extends AppCompatActivity {
    EditText title_input, author_input, count_input;
    Button update_button, delete_button;

    String id, title, author, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        count_input = findViewById(R.id.count_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();


        ActionBar ab = getSupportActionBar();
        if( ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateBookActivity.this);
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                count = count_input.getText().toString().trim();
                myDB.updateData(id, title, author, count);
                
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
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
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?" );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateBookActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}