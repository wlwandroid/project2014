package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class choose_select extends Activity {
	 private Button btn1,btn2;
	
	 public void onCreate(Bundle savedInstanceState) {  
	       super.onCreate(savedInstanceState);  
	       setContentView(R.layout.choose_select);
	       btn1 = (Button)findViewById(R.id.button1);
	       btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent();
				 intent.setClass(choose_select.this, choose1.class);
				 startActivity(intent);
			}
		});
	       btn2 = (Button)findViewById(R.id.button2);
	       btn2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Intent intent = new Intent();
					 intent.setClass(choose_select.this, choose2.class);
					 startActivity(intent);
				}
			});
	 }
}
