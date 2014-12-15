package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class watch_question extends Activity{
	     public static Boolean flag=false;
 		 private TextView id,context,optionA,optionB,optionC,optionD,key_text;
		 private Button button1,button2,button3;
		 private MySQLiteHelper mySQLiteHelper ;
		 private SQLiteDatabase db;
		 private EditText question,A,B,C,D;
		   @Override 
		   public void onCreate(Bundle savedInstanceState) {  
		       super.onCreate(savedInstanceState);  
		       setContentView(R.layout.watch);
		       mySQLiteHelper = new MySQLiteHelper(watch_question.this,"Answer.db",null,1);
		       db = mySQLiteHelper.getReadableDatabase();
		        //System.out.println("why!");
			    id = (TextView)findViewById(R.id.tv01);
			    context = (TextView)findViewById(R.id.tv02);
			    optionA = (TextView)findViewById(R.id.textview01);
			    optionB = (TextView)findViewById(R.id.textview02);
			    optionC = (TextView)findViewById(R.id.textview03);
			    optionD = (TextView)findViewById(R.id.textview04);
			    key_text = (TextView)findViewById(R.id.tv03);
			    button1 = (Button)findViewById(R.id.bt01);
			    button2 = (Button)findViewById(R.id.bt02);
			    button3 = (Button)findViewById(R.id.bt03);
			    id.setText(Listview.myid+"");
			    context.setText("       "+Listview.title);
			    if(Listview.choose_num == 4){
			    optionA.setText("A. "+Listview.A);
			    optionB.setText("B. "+Listview.B);
			    optionC.setText("c. "+Listview.C);
			    optionD.setText("D. "+Listview.D);
			    key_text.setText("答案为： "+(char)(Listview.key+'A'-1)); 
			    }
			    else if(Listview.choose_num == 2){
			    optionA.setText("");
				optionB.setText("");
				optionC.setText("A.是");
				optionD.setText("B.不是");
				key_text.setText("答案为： "+(char)(Listview.key+'A'-1)); 	
			    }
			    button1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						db.delete("Answertable", "_id=?",new String[] {Listview.myid+""});
					    Toast.makeText(watch_question.this,"删除成功",Toast.LENGTH_LONG).show();
						// TODO Auto-generated method stub
						Intent intent = new Intent(watch_question.this,Listview.class);
						startActivity(intent);
					}
				});
			    button2.setOnClickListener(new OnClickListener() {
			    	
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//db.delete("Answertable", "_id=?",new String[] {Listview.myid+""});
					        flag = true ;
					        if(Listview.choose_num == 4){
					        Intent intent = new Intent(watch_question.this,choose1.class);
					        startActivity(intent);
					        }
					        if(Listview.choose_num == 2){
						        Intent intent = new Intent(watch_question.this,choose2.class);
						        startActivity(intent);
						    }
					}
				});
                button3.setOnClickListener(new OnClickListener() {
			    	
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					  Intent intent = new Intent(watch_question.this,Listview.class);
					  startActivity(intent);
					}
				});
		   }
    }

