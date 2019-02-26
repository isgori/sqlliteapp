package com.example.sqlliteapp.DatabaseSQLlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlliteapp.models.UserEntry;

public class MySQLLiteHelper extends SQLiteOpenHelper {

    private static final String Database = "mydatabase.db";
    private static final int dtabaseVersion = 1;
    private static final String createUserTable = " CREATE TABLE " + UserEntry.Tablename + " ("
            + UserEntry._ID + " TEXT,"
            + UserEntry.columnname + " TEXT, "
            + UserEntry.lastColumn + " TEXT, "
            + UserEntry.streetColumn + " TEXT ,"
            + UserEntry.postCodeColumn + " TEXT ,"
            + UserEntry.stateColumn + " TEXT ,"
            + UserEntry.cityColumn + " TEXT ,"
            + UserEntry.emailColumn + " TEXT ,"
            + UserEntry.userNameColumn + " TEXT ,"
            + UserEntry.birthdayColumn + " TEXT ,"
            + UserEntry.phoneColumn + " TEXT ,"
            + UserEntry.cellPhoneColumn + " TEXT ,"
            + UserEntry.pictureColumn + " TEXT "
            + ");";
    public static final String deleteUserTable = "DROP TABLE user";

    public MySQLLiteHelper(Context context) {
        super(context, Database, null, dtabaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(deleteUserTable);

    }

}
