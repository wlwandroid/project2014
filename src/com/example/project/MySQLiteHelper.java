package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE Resulttable( _id INTEGER PRIMARY KEY AUTOINCREMENT, alias TEXT, papername TEXT)");
		db.execSQL("CREATE TABLE Testtable( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, content TEXT)");
		db.execSQL("CREATE TABLE Answertable( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, A TEXT, B TEXT, C TEXT, D TEXT, choose_num INTEGER, key INTEGER)");
		db.execSQL("CREATE TABLE Opentable( _id INTEGER PRIMARY KEY AUTOINCREMENT, alias TEXT, papername TEXT, title TEXT, A TEXT, B TEXT, C TEXT, D TEXT, choose_num INTEGER, key INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		    db.execSQL("DROP TABLE IF EXISTS Answertable");  
	        db.execSQL("DROP TABLE IF EXISTS Testtable");
	        db.execSQL("DROP TABLE IF EXISTS Opentable"); 
	        db.execSQL("DROP TABLE IF EXISTS Resulttable"); 
	        onCreate(db);  
	}
    
}
