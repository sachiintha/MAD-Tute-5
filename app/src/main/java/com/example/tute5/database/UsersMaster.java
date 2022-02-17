package com.example.tute5.database;

import android.provider.BaseColumns;

public final class UsersMaster {

    private UsersMaster(){}

    public static class User implements BaseColumns{

        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";


    }


}
