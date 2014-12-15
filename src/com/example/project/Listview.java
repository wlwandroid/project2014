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



public class Listview extends Activity{
	public static int choose_num,key;
	public static int myid;
	public static String title;
	public static String A,B,C,D;
	private EditText et ; 
	private MySQLiteHelper mySQLiteHelper;
	private SQLiteDatabase db;
	private ListView       lv;
	private TextView       tv;
	private List<Map<String,Object>> list;
	private Cursor cursor;
	private int mywhich;
	private Button button_back,button_table,button_create;
	private CheckBox checkbox;
	private EditText et01;
	private EditText et02;
	
	//private SharedPreferences sp;
	
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
   
	mySQLiteHelper=new MySQLiteHelper(Listview.this, "Answer.db", null, 1);
	tv=(TextView) findViewById(R.id.TextView01);
	lv=(ListView) findViewById(R.id.ListView01);
	button_back = (Button)findViewById(R.id.button_back);
	button_table = (Button)findViewById(R.id.button_table);
	button_create = (Button)findViewById(R.id.button_create);
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
	    button_back.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Listview.this,MainActivity.class);
				finish();
			    startActivity(intent);
			}
           

		});
         button_create.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  Intent intent = new Intent();
				   intent.setClass(Listview.this, choose_select.class);
				   startActivity(intent);
			}
           

		});
	    button_table.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View view ;
				TextView textview;
				int length = lv.getChildCount();
				String s = "";
				for(int i=0 ; i<length;i++) {
					 view = getViewByPosition(i, lv);
					  checkbox=(CheckBox)view.findViewById(R.id.checkbox);
					  if(checkbox.isChecked()) {
						  textview=(TextView)view.findViewById(R.id.TextView01);
						 s = s +":"+textview.getText().toString();
					  }
				 }
				if(s=="") {
					Toast.makeText(Listview.this,"问题数目不能为空",Toast.LENGTH_LONG).show();
				}
				else {
					
				    /////////////////////////////////////////
					System.out.println(s);
			        InputName(s);
			       
				    ////////////////////////////////////////
				 /*   cv.put("choose_num",4);
				    cv.put("key", key);  
				    db.insert("Answertable", null, cv);
				    Toast.makeText(choose1.this,"添加到数据库成功",Toast.LENGTH_LONG).show(); */
				}
					
					 
			}
		});
	    
	}
	public void InputName(final String s) {
		
	final MySQLiteHelper mySQLfortable = new MySQLiteHelper(Listview.this,"Answer.db", 
        null, 1);
	  final	 SQLiteDatabase datab = mySQLfortable.getReadableDatabase();
	  final  ContentValues CV = new ContentValues();
	    final EditText name=new EditText(this);
	   final String names="";
	    name.setText(names);
	    new AlertDialog.Builder(this).setTitle("请输入测试的名称")
	    .setIcon(android.R.drawable.ic_dialog_info)
	    .setView(name).setPositiveButton("确定", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface arg0, int arg1) {  
                if(name.getText().toString()==names)
                Toast.makeText(Listview.this,"添加失败，名字不能为空",   
                      Toast.LENGTH_LONG).show();       
                else {
                	   CV.put("name",name.getText().toString());
                	   CV.put("content", s);
                	   datab.insert("testtable", null, CV);
                	   Toast.makeText(Listview.this,"制表成功，请注意查收",   
                               Toast.LENGTH_LONG).show(); 
                }
            }  
        }).setNegativeButton("取消", null).show();  
	    
	}

 public View getViewByPosition(int pos, ListView listView) {
 final int firstListItemPosition = listView.getFirstVisiblePosition();
 final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;
   
  if (pos < firstListItemPosition || pos > lastListItemPosition ) {
    return listView.getAdapter().getView(pos, null, listView);
  } else {
    final int childIndex = pos - firstListItemPosition;
    return listView.getChildAt(childIndex);
  }
} 
}
