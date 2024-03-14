package com.example.lib3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class activity_add_client extends AppCompatActivity {
    RecyclerView recyclerView_Client;
    FloatingActionButton add_button_Client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        recyclerView_Client = findViewById(R.id.recyclerView_Client);
        add_button_Client = findViewById(R.id.add_button_Client);
        add_button_Client.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_add_client.this, activity_add_client.class);
                startActivity(intent);
            }
        });
    }
}