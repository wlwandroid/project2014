package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class result extends Activity{
	  
	 private TextView textView1;
	 private Button finish;
	 
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.result);
	        textView1 = (TextView)findViewById(R.id.textView1);
	        finish = (Button)findViewById(R.id.finish);
	        textView1.setText("答对的题数/总答题数："+answer.right_number+"/"+answer.number+
	        		          "\n  得分: "+100.0*answer.right_number/(double)(answer.number)
	        		          +"分");
	        
	        finish.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					answer.number=0;
					answer.right_number=0;
				    Intent intent = new Intent();
				    intent.setClass(result.this,MainActivity.class);
				    startActivity(intent);
				}
			});
	 }
}
