package com.example.a17rp01001.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "studentinfo.db";
    public static final int DATABASE_VERSION = 1;
    private HashMap hp;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (" +
                "  `id` INTEGER NOT NULL primary key autoincrement," +
                "  `name` TEXT NOT NULL DEFAULT ''," +
                "  `email` TEXT NOT NULL DEFAULT ''," +
                "  `password` TEXT NOT NULL DEFAULT ''" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }


}
