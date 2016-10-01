package com.example.bamble.my_android_app_practice;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "user.db";
    static final int DATABASE_VERSION = 1;
    public static final String NAME_COLUMN = "user";
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,PASSWORD text,FIRSTNAME text,LASTNAME text,USERNAME2 text); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String email, String password, String fname, String lname, String username) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", email);
        newValues.put("PASSWORD", password);
        newValues.put("FIRSTNAME", fname);
        newValues.put("LASTNAME", lname);
        newValues.put("USERNAME2", username);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public String getSinlgeEntry(String em) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{em}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();

            Cursor cursor2 = db.query("LOGIN", null, " USERNAME2=?", new String[]{em}, null, null, null);
            if (cursor2.getCount() < 1) // UserName Not Exist
            {
                cursor2.close();
                return "NOT EXIST";
            }

            cursor2.moveToFirst();
            String password2 = cursor2.getString(cursor2.getColumnIndex("PASSWORD"));
            cursor2.close();
            return password2;

        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public boolean existingValidator(String em) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{em}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();

            Cursor cursor2 = db.query("LOGIN", null, " USERNAME2=?", new String[]{em}, null, null, null);
            if (cursor2.getCount()< 1) // UserName Not Exist
            {
                cursor2.close();
                return false;
                //not exist
            }

            cursor2.moveToFirst();
            String password2 = cursor2.getString(cursor2.getColumnIndex("PASSWORD"));
            cursor2.close();
            return true;

        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return true;
    }
}