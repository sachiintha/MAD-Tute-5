package com.example.tute5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "Create Table " + UsersMaster.User.TABLE_NAME + " (" +
                        UsersMaster.User._ID + " INTEGER PRIMARY KEY," +
                        UsersMaster.User.COLUMN_NAME_USERNAME + " TEXT," +
                        UsersMaster.User.COLUMN_NAME_PASSWORD + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);



    }

    public Long addInfo(String username, String password){

        SQLiteDatabase db = getWritableDatabase();


        ContentValues values = new ContentValues();


        values.put(UsersMaster.User.COLUMN_NAME_USERNAME, username);
        values.put(UsersMaster.User.COLUMN_NAME_PASSWORD, password);

        return db.insert(UsersMaster.User.TABLE_NAME, null, values);

    }


        public List readAllInfo(){
            SQLiteDatabase db = getReadableDatabase();

            String [] projection = {
                    UsersMaster.User._ID,
                    UsersMaster.User.COLUMN_NAME_USERNAME,
                    UsersMaster.User.COLUMN_NAME_PASSWORD
            };

            String sortOrder = UsersMaster.User.COLUMN_NAME_USERNAME + " DESC";

            Cursor cursor = db.query(
                    UsersMaster.User.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
            );

            List info = new ArrayList<>();

            while (cursor.moveToNext()){
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.User.COLUMN_NAME_USERNAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.User.COLUMN_NAME_PASSWORD));

                info.add(userName+": "+password);
            }
            cursor.close();

            return info;
        }


        public void DeleteInfo(String username){

            SQLiteDatabase db = getReadableDatabase();

            String selection = UsersMaster.User.COLUMN_NAME_PASSWORD + " LIKE ?";
            String[] stringArgs = {username};

            db.delete(UsersMaster.User.TABLE_NAME,selection,stringArgs);

        }

        public void updateInfo(View view, String username, String password){

                SQLiteDatabase db = getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put(UsersMaster.User.COLUMN_NAME_PASSWORD, password);

                String selection = UsersMaster.User.COLUMN_NAME_PASSWORD + " LIKE ?";
                String[] selctionArgs = {username};

                int count = db.update(

                        UsersMaster.User.TABLE_NAME,
                            values,
                                selection,
                                    selctionArgs
                );

            Snackbar snackbar = Snackbar.make(view, count + " rows effected",Snackbar.LENGTH_LONG);
            snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE);
            snackbar.show();

         }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
