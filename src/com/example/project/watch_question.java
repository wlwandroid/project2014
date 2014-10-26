package com.example.project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class watch_question extends Activity{
		 private TextView id,context,optionA,optionB,optionC,optionD,key_text;
		   @Override 
		   public void onCreate(Bundle savedInstanceState) {  
		       super.onCreate(savedInstanceState);  
		       setContentView(R.layout.watch_question);
		        //System.out.println("why!");
			    id = (TextView)findViewById(R.id.textView1);
			    context = (TextView)findViewById(R.id.textView2);
			    optionA = (TextView)findViewById(R.id.textView3);
			    optionB = (TextView)findViewById(R.id.textView4);
			    optionC = (TextView)findViewById(R.id.textView5);
			    optionD = (TextView)findViewById(R.id.textView6);
			    key_text = (TextView)findViewById(R.id.textView7);
			    id.setText(Listview.myid+"");
			    context.setText("   "+Listview.title);
			    optionA.setText("A. "+Listview.A);
			    optionB.setText("B. "+Listview.B);
			    optionC.setText("c. "+Listview.C);
			    optionD.setText("D. "+Listview.D);
			    key_text.setText("答案为： "+(char)(Listview.key+'A'-1)); 
		   }
    }

