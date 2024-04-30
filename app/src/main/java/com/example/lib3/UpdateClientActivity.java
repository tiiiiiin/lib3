package com.example.lib3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateClientActivity extends AppCompatActivity {
    EditText name_input, date_input, email_input;
    Button update_button, delete_button;
    String id, name, data, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        name_input = findViewById(R.id.name_input2);
        date_input = findViewById(R.id.date_input2);
        email_input = findViewById(R.id.email_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelperClient myDB = new MyDatabaseHelperClient(UpdateClientActivity.this);
                name = name_input.getText().toString().trim();
                data = date_input.getText().toString().trim();
                email = email_input.getText().toString().trim();
                myDB.updateData(id, name, data, email);


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
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("data") && getIntent().hasExtra("email")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            data = getIntent().getStringExtra("data");
            email = getIntent().getStringExtra("email");

            //set data
            name_input.setText(name);
            date_input.setText(data);
            email_input.setText(email);
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?" );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelperClient myDB = new MyDatabaseHelperClient(UpdateClientActivity.this);
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