package com.example.lib3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class activity_add_client extends AppCompatActivity {
    RecyclerView recyclerView_Client;
    FloatingActionButton add_button_Client;
    MyDatabaseHelperClient myDB;
    ArrayList<String> client_id, name_client, date_of_birth, client_email;
    CustomAdapterClients customAdapterClients;
    private static final int ADD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        recyclerView_Client = findViewById(R.id.recyclerView_Client);
        add_button_Client = findViewById(R.id.add_button_Client);
        add_button_Client.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_add_client.this, AddClientActivity.class);
                startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
            }
        });

        myDB = new MyDatabaseHelperClient(activity_add_client.this);
        client_id = new ArrayList<>();
        name_client = new ArrayList<>();
        date_of_birth = new ArrayList<>();
        client_email = new ArrayList<>();

        // Здесь добавляем вызов storeDataInArraysClients()
        storeDataInArraysClients();

        customAdapterClients = new CustomAdapterClients(activity_add_client.this, this, client_id, name_client,
                date_of_birth, client_email);
        recyclerView_Client.setAdapter(customAdapterClients);
        recyclerView_Client.setLayoutManager(new LinearLayoutManager(activity_add_client.this));

        Button searchButton = findViewById(R.id.search_button_Client);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

    }
    private void performSearch() {
        EditText searchEditText = findViewById(R.id.search_edit_text_Client);
        String searchText = searchEditText.getText().toString();

        ArrayList<String> searchedClientId = new ArrayList<>();
        ArrayList<String> searchedClientName = new ArrayList<>();
        ArrayList<String> searchedClientDate = new ArrayList<>();
        ArrayList<String> searchedClientEmail = new ArrayList<>();

        Cursor cursor = myDB.searchClient(searchText);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No matching data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                searchedClientId.add(cursor.getString(0));
                searchedClientName.add(cursor.getString(1));
                searchedClientDate.add(cursor.getString(2));
                searchedClientEmail.add(cursor.getString(3));
            }
            // Обновляем адаптер RecyclerView с результатами поиска
            customAdapterClients.updateData(searchedClientId, searchedClientName, searchedClientDate, searchedClientEmail);
            Log.d("SearchActivity", "Search results: " + searchedClientName.toString()); // Отладочный вывод
        }

        // Если строка поиска пуста, показываем все данные
        if (searchText.isEmpty()) {
            storeDataInArraysClients();
            customAdapterClients.updateData(client_id, name_client, date_of_birth, client_email);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            storeDataInArraysClients(); // Обновляем данные после добавления книги
            customAdapterClients.notifyDataSetChanged(); // Уведомляем адаптер о изменениях
        }
    }

    void storeDataInArraysClients() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            clearDataInArraysClients(); // Очищаем списки перед обновлением данных
            while(cursor.moveToNext()){
                client_id.add(cursor.getString(0));
                name_client.add(cursor.getString(1));
                date_of_birth.add(cursor.getString(2));
                client_email.add(cursor.getString(3));
            }
            Log.d("Data", "Client IDs: " + client_id.toString());
        }
    }

    // Метод для очистки списков клиентов
    void clearDataInArraysClients() {
        client_id.clear();
        name_client.clear();
        date_of_birth.clear();
        client_email.clear();
    }
}

