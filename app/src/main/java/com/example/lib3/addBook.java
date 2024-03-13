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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class addBook extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_count;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        recyclerView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addBook.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB  = new MyDatabaseHelper(addBook.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_count = new ArrayList<>();


        storeDataInArrays();

        customAdapter = new CustomAdapter(addBook.this,this, book_id, book_title, book_author, book_count);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(addBook.this));

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

    }
    private void performSearch() {
        EditText searchEditText = findViewById(R.id.search_edit_text);
        String searchText = searchEditText.getText().toString();

        ArrayList<String> searchedBookId = new ArrayList<>();
        ArrayList<String> searchedBookTitle = new ArrayList<>();
        ArrayList<String> searchedBookAuthor = new ArrayList<>();
        ArrayList<String> searchedBookCount = new ArrayList<>();

        Cursor cursor = myDB.searchData(searchText); // Выполняем поиск в базе данных
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No matching data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                searchedBookId.add(cursor.getString(0));
                searchedBookTitle.add(cursor.getString(1));
                searchedBookAuthor.add(cursor.getString(2));
                searchedBookCount.add(cursor.getString(3));
            }
            // Обновляем адаптер RecyclerView с результатами поиска
            customAdapter.updateData(searchedBookId, searchedBookTitle, searchedBookAuthor, searchedBookCount);
            Log.d("SearchActivity", "Search results: " + searchedBookTitle.toString()); // Отладочный вывод
        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_count.add(cursor.getString(3));

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.delete_all) {
            confirmDialog();;
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all ?" );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //MyDatabaseHelper myDB = new MyDatabaseHelper(addBook.this);
                //Toast.makeText(this,"Delete ", Toast.LENGTH_SHORT).show();
                MyDatabaseHelper myDB = new MyDatabaseHelper(addBook.this);
                myDB.deleteAllData();

                Intent intent = new Intent(addBook.this, addBook.class);
                startActivity(intent);
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