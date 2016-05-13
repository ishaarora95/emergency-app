package com.example.isha.emergency;

/**
 * Created by Isha on 4/14/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {
    Context context;
    public DBAdapter(Context context) {

        super(context, "dbname", null, 5);
        this.context=context;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table if not exists users(id integer primary key autoincrement, name text,password text)");
        db.execSQL("create table if not exists userContacts(id integer primary key autoincrement, name text,contact text,user text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS userContacts");
        // Create tables again
        onCreate(db);

    }

    public Boolean contactcheck(String name) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor=db.rawQuery("select id from userContacts where name='" + name
                    + "' and user='"+ com.example.isha.emergency.MainActivity.user+"'" , null);

            if (cursor != null && cursor.getCount()>0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void insertContact( Econtacts obj) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("insert into userContacts(name,contact,user) values ('"+ obj.get_ename()	+ "','"
                    + obj.get_econtacts()
                    + "','"+ com.example.isha.emergency.MainActivity.user +"' );");
            db.close(); // Closing database connection
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insert( String name, String password) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("insert into users(name,password) values ('"+ name	+ "','"
                    + password
                    + "');");
            db.close(); // Closing database connection
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Boolean usercheck(String name) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor=db.rawQuery("select id from users where name='" + name
                    + "'", null);

            if (cursor != null && cursor.getCount()>0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Econtacts> getAllContacts(){
        ArrayList<Econtacts> list=new ArrayList<>();
        String selectQuery =
                "SELECT  id,name,contact,user FROM userContacts where user='"+ com.example.isha.emergency.MainActivity.user+"'";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    list.add(new Econtacts(cursor.getString(2),cursor.getString(1)));
                }
                while(cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast.makeText(context, "in select" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("select", e.getMessage());
        }
        return list;

    }


    public Boolean logincheck(String name, String password) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery("select id from users where name='" + name
                    + "' and password='" + password + "'", null);

            if (cursor != null && cursor.getCount() > 0){
                Toast.makeText(context, "true", Toast.LENGTH_LONG).show();
            return true;
        }
            else {
                Toast.makeText(context, "false", Toast.LENGTH_LONG).show();
                return false;


            }
        } catch (Exception e) {
            Toast.makeText(context, "error" + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }
public void delete(String index)
{
    SQLiteDatabase db = this.getReadableDatabase();

    db.execSQL("delete from userContacts where contact='"+index+"'");


    db.close();
}
}