package com.example.a17rp01001.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

public class Users {
    public final Context ctx;
    private Database dbInstance;
    private SQLiteDatabase dbInstanceWritable, dbInstanceReadable;

    public Users(Context context) {
        ctx = context;
        dbInstance = new Database(ctx);
    }

    public JSONObject localLogin(String uname, String pwd) {
        this.dbInstanceReadable = dbInstance.getReadableDatabase();
        JSONObject jsonObject = new JSONObject();
        String feed = "successful";
        Cursor login = dbInstanceReadable.rawQuery("select * from users where email = ? and password = ?", new String[]{ uname, pwd});
        try {
            if (login.getCount() > 0) { //check configuration before starting other activity
                while (login.moveToNext()) {
                    jsonObject.put("status","ok");
                    jsonObject.put("id", login.getString(login.getColumnIndex("id")));
                    jsonObject.put("name", login.getString(login.getColumnIndex("name")));
                    jsonObject.put("email", login.getString(login.getColumnIndex("email")));
                    jsonObject.put("password", login.getString(login.getColumnIndex("password")));
                }
            } else {
                feed = "fail";
                jsonObject.put("status", feed);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        } finally {
            login.close();
            dbInstanceReadable.close();
        }
        return jsonObject;
    }


    public String localSignup(String names, String email, String pwd) {
        this.dbInstanceWritable = dbInstance.getWritableDatabase();
        this.dbInstanceReadable = dbInstance.getReadableDatabase();
        String feed = "ok";
        Cursor login = dbInstanceReadable.rawQuery("select * from users where email = ?", new String[]{email});
        try {
            if (login.getCount() == 0) { //register new user
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", names);
                contentValues.put("email", email);
                contentValues.put("password", pwd);
                long registrar = dbInstanceWritable.insertOrThrow("users", null, contentValues);
            } else {
                feed = "exist";
            }
        } catch (Exception ex) {
            feed = "fail";
            Log.e("reguser", ex.getMessage());
        } finally {
            login.close();
            dbInstanceWritable.close();
            dbInstanceReadable.close();
        }
        return feed;
    }

    public void delete(){
        this.dbInstanceWritable = dbInstance.getWritableDatabase();
        dbInstanceWritable.execSQL("delete from users");
    }
}
