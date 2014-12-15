package com.example.project;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class choose2 extends Activity {
	     private  EditText question;
	     private  Button finish;
	     private  RadioButton a,b;
	     private  int id;
	     MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(choose2.this,"Answer.db", 
	        		null, 1);
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.choose2);
	        finish = (Button)findViewById(R.id.button1);
	        question = (EditText)findViewById(R.id.editText1);
	       
	        b = (RadioButton)findViewById(R.id.radio1);
	        a = (RadioButton)findViewById(R.id.radio0);
	        if(watch_question.flag == true) {
		        
	        	id = Listview.myid;
		        question.setText(Listview.title);
		        
	        }    
	        finish.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String Question = question.getText().toString();
			     
			        int   key ;
			        //
			        if(a.isChecked())key=1;
			        else if(b.isChecked())key=2;
			        else 
			        {
			        	Toast.makeText(choose2.this,"请为题目设计一个答案",Toast.LENGTH_LONG).show();
			        	return ;
			        }
			      
					SQLiteDatabase db = mySQLiteHelper.getReadableDatabase();
				    ContentValues cv = new ContentValues();
				    String whereClause = "_id=?"; 
				    
				    cv.put("title", Question);
				    cv.put("A", "是");
				    cv.put("B", "不是");
				    cv.put("choose_num",2);
				    cv.put("key", key);  
				    /*
				     * ContentValues values = new ContentValues();  
                       values.put("username", username);  
                       values.put("info", info);  
                       String whereClause = "id=?";  
                       String[] whereArgs = new String[] { String.valueOf(id) };  
                       mDb.update(table, values, whereClause, whereArgs); 
				     */
				    if(watch_question.flag == true){
				    	watch_question.flag = false ;
				    	String[] whereArgs = new String[] { String.valueOf(id) };  
	                    db.update("Answertable", cv, whereClause, whereArgs); 
				    }
				    else 
				    db.insert("Answertable", null, cv);
				    
				    Toast.makeText(choose2.this,"添加到数据库成功",Toast.LENGTH_LONG).show();
				    Intent intent = new Intent();
				    intent.setClass(choose2.this, Listview.class);
				    finish();
				    startActivity(intent);
				    db.close();
				}
			});
	        //对取得的数据进行保存
	       
	 }
}
