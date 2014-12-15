package com.example.project;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class table_action extends Activity{
	private static String TAG = "table_action";
	public static String papername = "";
	/////////////////////////////////////
	//POST模块
    String url="http://1.xgbjwenda.sinaapp.com/add"; 
	HttpPost httpRequest=null; 
	/////////////////////////////////
	private Oauth2AccessToken mAccessToken;
	
	/////////////////////////////////
	HttpResponse httpResponse; 
    TextView tv=null; 
	////////////////////////////////////
	private Button button_01,button_02,button_03;
	private SQLiteDatabase mydb;
	private MySQLiteHelper mySQL = new MySQLiteHelper(table_action.this,"Answer.db", 
     		null, 1);
	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.table_action);
	        button_01=(Button)findViewById(R.id.button_01);
	        button_02=(Button)findViewById(R.id.button_02);
	        button_03=(Button)findViewById(R.id.button_03);
	        mAccessToken = AccessTokenKeeper.readAccessToken(this);
	        button_01.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(table_action.this,answer.class);     
				    startActivity(intent);
				}
			});
            button_02.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 mydb = mySQL.getReadableDatabase();
					mydb.delete("Testtable", "_id=?",new String[] {Listview_table.myid+""});
				    Toast.makeText(table_action.this,"删除成功",Toast.LENGTH_LONG).show();
					// TODO Auto-generated method stub
					Intent intent = new Intent(table_action.this,Listview_table.class);
					startActivity(intent);
				}
			});
           button_03.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mAccessToken != null && mAccessToken.isSessionValid()) {
				    int question_num = 0;
				    Boolean flag = false;
					// TODO Auto-generated method stub
				    mydb = mySQL.getReadableDatabase();
				    
				    final Cursor cursor = 
				    mydb.query("Answertable", new String[]{"_id","title","A"
							,"B","C", "D","choose_num","key"},null,null,null,
							null,null);
				    //POST部分
					/*******************************/
				    while(cursor.moveToNext()) {
				    	/*
				    	 * alias 别名
				    	 * papername 试卷名
				    	 * number 试题号
				    	 * content 题目内容
				    	 * type 试题类型
				    	 * optiona 选项A
				    	 * answer 答案 判断
				    	 */
					    ////////////////////
				      final List <NameValuePair> params=new ArrayList<NameValuePair>(); 
				       params.add(new BasicNameValuePair("alias",AliasKeeper.readAlias(table_action.this)));
				       params.add(new BasicNameValuePair("papername",Listview_table.name));
					   int idindex = cursor.getColumnIndex("_id");
					   int id = cursor.getInt(idindex);
					   params.add(new BasicNameValuePair("number",id+""));
					   if(id>Listview_table.table_content[question_num+1])
					   question_num++;	   
					   if(question_num < Listview_table.table_content[0]&&
					      id==Listview_table.table_content[question_num+1]) {
                       flag = true ;
					   question_num++;
					   /////////////////
					   int titleindex = cursor.getColumnIndex("title");
					   String title = cursor.getString(titleindex);
					   params.add(new BasicNameValuePair("content",title)); 
					   //////////////////////////
					   int choose_numindex = cursor.getColumnIndex("choose_num");
					   int choose_num = cursor.getInt(choose_numindex);
					   params.add(new BasicNameValuePair("type",choose_num+""));
					   //////////////////////////答案组件
					   for(int i=0;i<4;i++) {
					   char temp=(char)('A'+i);
					   int Aindex = cursor.getColumnIndex(temp+"");
					   String A = cursor.getString(Aindex);
					   params.add(new BasicNameValuePair("option"+(char)('a'+i),A));
					   }
					   /////////////////////////答案
					   int key_index = cursor.getColumnIndex("key");
					   int key = cursor.getInt(key_index);
					   params.add(new BasicNameValuePair("answer",(char)('A'+key-1)+""));
					   ////////////////////////
					   new Thread() {
		                    @Override
		                public void run() {
					   /*建立HttpPost连接*/ 
		                HttpPost httpRequest = new HttpPost(url); 
		                List <NameValuePair> par=new ArrayList<NameValuePair>();
		                par = params;
				        /*Post运作传送变数必须用NameValuePair[]阵列储存*/ 
				       
				        try { 
				            //发出HTTP request 
				            httpRequest.setEntity(new UrlEncodedFormEntity(par,HTTP.UTF_8)); 
				            //取得HTTP response 
				            httpResponse=new DefaultHttpClient().execute(httpRequest); 
				            //若状态码为200 
				            if(httpResponse.getStatusLine().getStatusCode()==200){ 
				                //取出回应字串 
				                String strResult=EntityUtils.toString(httpResponse.getEntity()); 
				                System.out.println("sucess");
				            }else{ 
				                System.out.println("failed");
				            } 
				        } catch (Exception e) { 
				            // TODO Auto-generated catch block 
				        	System.out.println("exception");
				        } 
					  
					   }
					  
				      }.start();	
				      
				      
					}
				    /***********************************/
				  
				    /******************************/
				
				    Toast.makeText(table_action.this,"POST成功",Toast.LENGTH_LONG).show();
					// TODO Auto-generated method stub
				    
					
				   }
				    
				   if(flag == true) {
					   SQLiteDatabase db = mySQL.getReadableDatabase(); 
					   ContentValues cv = new ContentValues();
					   cv.put("alias",AliasKeeper.readAlias(table_action.this));
					   cv.put("papername", Listview_table.name);
					   db.insert("Resulttable", null, cv);
					   db.close();
				   }
				   
				   SQLiteDatabase db1 = mySQL.getReadableDatabase();
				   db1.delete("Testtable", "_id=?",new String[] {Listview_table.myid+""});
				   
				   /* 发微博给好友 */
				   
				    try {
			        String website ="我发布了新的试题，大家快来答题吧\n"+
	                            "http://1.xgbjwenda.sinaapp.com/" +
	                            URLEncoder.encode(AliasKeeper.readAlias(table_action.this)+
			                            "/"+Listview_table.name, "utf-8");
			        WeiboParameters wbparams = new WeiboParameters();
                    wbparams.put("access_token", mAccessToken.getToken());
                    wbparams.put("status", website);
                    wbparams.put("visible",      "0");//0 所有人  1 好友    
                    wbparams.put("list_id",      "");
               wbparams.put("lat",          "");
               wbparams.put("long",         "");
               wbparams.put("annotations",  "");
               AsyncWeiboRunner.requestAsync(
                       "https://api.weibo.com/2/statuses/update.json", 
                       wbparams, 
                       "POST", 
                       mListener);
					   } catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   
	                
	               
				   /* 发微博给好友 */
				   Intent intent = new Intent(table_action.this,Listview_table.class);
			       startActivity(intent);
			}
		 else {
			       Toast.makeText(table_action.this,"您尚未登录，请先登陆微博",Toast.LENGTH_LONG).show();	
			  }
		 }

		});
              
	 }
	 private RequestListener mListener = new RequestListener() {
         @Override
         public void onComplete(String response) {
             if (!TextUtils.isEmpty(response)) {
                 LogUtil.i(TAG, response);
                 if (response.startsWith("{\"statuses\"")) {
                     // 璋冪敤 StatusList#parse 瑙ｆ瀽瀛楃涓叉垚寰崥鍒楄〃瀵硅薄
                     StatusList statuses = StatusList.parse(response);
                     if (statuses != null && statuses.total_number > 0) {
                         Toast.makeText(table_action.this, 
                                 "获取微博信息成功: " + statuses.statusList.size(), 
                                 Toast.LENGTH_LONG).show();
                     }
                 } else if (response.startsWith("{\"created_at\"")) {
                     // 璋冪敤 Status#parse 瑙ｆ瀽瀛楃涓叉垚寰崥瀵硅薄
                     Status status = Status.parse(response);
                     Toast.makeText(table_action.this, 
                             "上传成功, id = " + status.id, 
                             Toast.LENGTH_LONG).show();
                 } else {
                     Toast.makeText(table_action.this, response, Toast.LENGTH_LONG).show();
                 }
             }
         }

 		@Override
 		public void onWeiboException(WeiboException arg0) {
 			// TODO Auto-generated method stub
 			
 		}
     }; 
}

     