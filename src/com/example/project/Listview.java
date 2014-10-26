package com.example.project;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class Listview extends Activity{
	public static int choose_num,key;
	public static int myid;
	public static String title;
	public static String A,B,C,D;
	private MySQLiteHelper mySQLiteHelper;
	private SQLiteDatabase db;
	private ListView       lv;
	private TextView       tv;
	private List<Map<String,Object>> list;
	private Cursor cursor;
	private int mywhich;
	
	private EditText et01;
	private EditText et02;
	//private SharedPreferences sp;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
	
	mySQLiteHelper=new MySQLiteHelper(Listview.this, "Answer.db", null, 1);
	tv=(TextView) findViewById(R.id.TextView01);
	lv=(ListView) findViewById(R.id.ListView01);
	//
	 db=mySQLiteHelper.getReadableDatabase();
	 cursor=db.query("Answertable", new String[]{"_id","title","A","B","C","D","choose_num"
			 ,"key"}, null, null, null, null, null);
	if(cursor.getCount()>0){
	tv.setVisibility(View.GONE);
	}
	SimpleCursorAdapter sca=new SimpleCursorAdapter(Listview.this, R.layout.item, cursor, new String[]{"_id","title"}, new int[]{R.id.TextView01,R.id.TextView02});
	lv.setAdapter(sca);
	//
	lv.setOnItemClickListener(new OnItemClickListener(){

		public void onItemClick(AdapterView<?> arg0, View arg1, int which,
				long arg3) {
	
					int myidindex=cursor.getColumnIndex("_id");
					myid=cursor.getInt(myidindex);
					int titleindex=cursor.getColumnIndex("title");
					title=cursor.getString(titleindex);
					int Aindex=cursor.getColumnIndex("A");
					A=cursor.getString(Aindex);
					int Bindex=cursor.getColumnIndex("B");
					B=cursor.getString(Bindex);
					int Cindex=cursor.getColumnIndex("C");
					C=cursor.getString(Cindex);
					int Dindex=cursor.getColumnIndex("D");
					D=cursor.getString(Dindex);
					int choose_numindex=cursor.getColumnIndex("choose_num");
					choose_num = cursor.getInt(choose_numindex);
					int keyindex=cursor.getColumnIndex("key");
					key = cursor.getInt(keyindex); 
					//sp = Listview.this.getSharedPreferences("temp_question", MODE_PRIVATE);
				     Intent intent = new Intent(Listview.this,watch_question.class);
				     
				     startActivity(intent);	
				
				}
			
		         
		});
	}
}
