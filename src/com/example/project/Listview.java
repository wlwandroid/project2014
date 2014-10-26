package com.example.project;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class Listview extends Activity{

	private MySQLiteHelper mySQLiteHelper;
	private SQLiteDatabase db;
	private ListView       lv;
	private TextView       tv;
	private List<Map<String,Object>> list;
	private Cursor cursor;
	private int mywhich,choose_num,key;
	private int myid;
	private String title;
	private String A,B,C,D;
	private EditText et01;
	private EditText et02;
	
	
	
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
			
			//mywhich=which-1;
			
			
			Builder builder=new Builder(Listview.this);
			builder.setSingleChoiceItems(new String[]{"查看","修改","删除"}, 0, new OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					
				if(which==0){//查看
					//Cursor cursor01=db.query("notepadtable", new String[]{"_id","title","content"}, null, null, null, null, null);
					
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
					
					Toast.makeText(Listview.this, myid+title+content, Toast.LENGTH_LONG).show();	
				}else if(which==2){//修改
					int myidindex=cursor.getColumnIndex("_id");
					myid=cursor.getInt(myidindex);

					db.delete("notepadtable", "_id="+myid, null);
					
					Cursor cursor=db.query("notepadtable", new String[]{"_id","title","content"}, null, null, null, null, null);
					SimpleCursorAdapter sca=new SimpleCursorAdapter(Listview.this, R.layout.item, cursor, new String[]{"_id","title","content"}, new int[]{R.id.TextView01,R.id.TextView02,R.id.TextView03});
					lv.setAdapter(sca);
				}else if(which==1){//删除
				
					
					
					
					Builder builder01=new Builder(Listview.this);
					
					builder01.setTitle("�༭");
					
					LayoutInflater inflater=LayoutInflater.from(Listview.this);
					View view=inflater.inflate(R.layout.updatedialogeview, null);
					et01=(EditText) view.findViewById(R.id.EditText01);
					et02=(EditText) view.findViewById(R.id.EditText02);
					
					builder01.setView(view);
					builder01.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

						public void onClick(DialogInterface dialog, int which) {
							//ȡ������
							int idindex=cursor.getColumnIndex("_id");
							int myid=cursor.getInt(idindex);
							//int titleindex=cursor.getColumnIndex("title");
							//title=cursor.getString(titleindex);
							//int contentindex=cursor.getColumnIndex("content");
							//content=cursor.getString(contentindex);
						String newtitle=et01.getText().toString();
						String newcontent=et02.getText().toString();
						ContentValues cv=new ContentValues();
						cv.put("title", newtitle);
						cv.put("content", newcontent);
						db.update("notepadtable", cv, "_id="+myid, null);
						Cursor cursor=db.query("notepadtable", new String[]{"_id","title","content"}, null, null, null, null, null);
						SimpleCursorAdapter sca=new SimpleCursorAdapter(Listview.this, R.layout.item, cursor, new String[]{"_id","title","content"}, new int[]{R.id.TextView01,R.id.TextView02,R.id.TextView03});
						lv.setAdapter(sca);
						
						}
						
					});
					builder01.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

						public void onClick(DialogInterface dialog, int which) {

							
						}
						
					});
					builder01.show();
				}
				
				
				
				}
				
			});
			builder.show();
		}
		
	});
	
	
	}
}
