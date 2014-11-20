package com.example.project;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class Listview_opentable extends Activity{
	public static int myid;
	public static String alias,papername;
	public static int[] table_content = new int[31];//制表有个上限 ，不能超过30道题 第一项纪录题目的个数
	private EditText et ; 
	private MySQLiteHelper mySQLfortable;
	private SQLiteDatabase db;
	private ListView       lv;
	private TextView       tv;
	private List<Map<String,Object>> list;
	private Cursor cursor;
	private Button button_back;
	private EditText et01;
	private EditText et02;
	
	//private SharedPreferences sp;
	
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_table);
   
	mySQLfortable=new MySQLiteHelper(Listview_opentable.this, "Answer.db", null, 1);
	tv=(TextView) findViewById(R.id.TextView01);
	lv=(ListView) findViewById(R.id.ListView01);
	button_back = (Button)findViewById(R.id.button_back);
	//
	 db=mySQLfortable.getReadableDatabase();
	cursor=db.query("Resulttable", new String[]{"_id","alias","papername"}, null, null, null, null, null);
	if(cursor.getCount()>0){
	tv.setVisibility(View.GONE);
	}
	SimpleCursorAdapter sca=new SimpleCursorAdapter(Listview_opentable.this, R.layout.item_table, cursor, new String[]{"_id","papername"}, new int[]{R.id.TextView01,R.id.TextView02});
	lv.setAdapter(sca);
	//
	lv.setOnItemClickListener(new OnItemClickListener(){

		public void onItemClick(AdapterView<?> arg0, View arg1, int which,
				long arg3) {
	
					int myidindex=cursor.getColumnIndex("_id");
					myid=cursor.getInt(myidindex);
					int titleindex=cursor.getColumnIndex("alias");
					alias=cursor.getString(titleindex);
					int contentindex=cursor.getColumnIndex("papername");
					papername=cursor.getString(contentindex);
					
					//sp = Listview.this.getSharedPreferences("temp_question", MODE_PRIVATE);
		            Intent intent = new Intent(Listview_opentable.this,table_openaction.class);     
				    startActivity(intent);	
				
				}
			
		         
		});
	    button_back.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Listview_opentable.this,MainActivity.class);
			    startActivity(intent);
			}
           

		});
	   
	    
	}
}
