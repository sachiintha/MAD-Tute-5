package com.example.tute5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tute5.database.DBHelper;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editName, editPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editPass = findViewById(R.id.editPass);


    }

    public void saveUser(View view){

        String name = editName.getText().toString();
        String password = editPass.getText().toString();
        DBHelper dbHelper = new DBHelper(this);

        if(name.isEmpty()||password.isEmpty()){

            Toast.makeText(this, "Enter details", Toast.LENGTH_SHORT).show();


        }

        else{

            long inserted = dbHelper.addInfo(name,password);

            if(inserted>0){
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_SHORT).show();
            }


        }
    }


    public void viewAll(View view){
        DBHelper dbHelper = new DBHelper(this);

        List info = dbHelper.readAllInfo();
        List items = new ArrayList<Integer>();

        String[] data = (String[]) info.toArray(new String[0]);

        //Toast.makeText(this, info.toString(), Toast.LENGTH_SHORT).show();

//        Snackbar snackbar = Snackbar.make(view, info.toString(),Snackbar.LENGTH_LONG);
//        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE);
//        snackbar.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Users");
        builder.setItems(data, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String username = data[i].split(":")[0];
                  editName.setText(username);
                    editPass.setText("*************************");

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void deleteUser(View view){
        DBHelper dbHelper = new DBHelper(this);

        String username = editName.getText().toString();

        if(username.isEmpty()){

            Toast.makeText(this, "Select a value", Toast.LENGTH_SHORT).show();
        }else {

            dbHelper.DeleteInfo(username);
            Toast.makeText(this, username + "delted", Toast.LENGTH_SHORT).show();
        }

    }


    public void updateUser(View view){

        DBHelper dbHelper = new DBHelper(this);
        String username = editName.getText().toString();
        String password = editPass.getText().toString();

        if (username.isEmpty() || password.isEmpty()){

            Toast.makeText(this, "Empty username and password", Toast.LENGTH_SHORT).show();
        }else {

            dbHelper.updateInfo(view, username, password);
        }

    }
}