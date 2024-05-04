package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void book(View view) {
        Intent intent = new Intent(this, addBook.class);
        startActivity(intent);
    }

    public void client(View view) {
        Intent intent = new Intent( this, activity_add_client.class);
        startActivity(intent);
    }
    public void going(View view){
        Intent intent = new Intent(this, TrackingActivity.class);
        startActivity(intent);
    }

}