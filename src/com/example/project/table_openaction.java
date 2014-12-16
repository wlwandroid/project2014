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

public class table_openaction extends Activity{
	private static String TAG = "table_openaction";
	public static String papername = "";
	/////////////////////////////////////
	//POST模块
	String url="http://1.xgbjwenda.sinaapp.com/admin"; 
	HttpPost httpRequest=null; 
	HttpResponse httpResponse; 
	/////////////////////////////////
	private Oauth2AccessToken mAccessToken;
	
	/////////////////////////////////
	private SQLiteDatabase mydb;
	private MySQLiteHelper mySQL = new MySQLiteHelper(table_openaction.this,"Answer.db", 
     		null, 1);
	////////////////////////////////////
	private Button button_01,button_02,button_03,button_04;
	
	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.table_openaction);
	        
	        mAccessToken = AccessTokenKeeper.readAccessToken(this);
	        
	        button_01=(Button)findViewById(R.id.button_01);
	        button_02=(Button)findViewById(R.id.button_02);
	        button_03=(Button)findViewById(R.id.button_03);
	        button_04=(Button)findViewById(R.id.button_04);
	        
	        button_01.setOnClickListener(new OnClickListener() {//排行榜
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					papername = Listview_opentable.papername;
					Intent intent = new Intent(table_openaction.this,rank.class);     
				    startActivity(intent);
				}
			});
            button_02.setOnClickListener(new OnClickListener() {//广播一下
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					  
				    try {
			        String website ="求答题\n"+
	                            "http://1.xgbjwenda.sinaapp.com/" +
	                            URLEncoder.encode(AliasKeeper.readAlias(table_openaction.this)+
			                            "/"+Listview_opentable.papername, "utf-8");
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
				   
	                
				}
			});
           button_04.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					 new Thread() {
		                    @Override
		                    public void run() {
		                    	// 第1步：创建HttpPost对象
		        				HttpPost httpPost = new HttpPost(url);
		        				// 设置HTTP POST请求参数必须用NameValuePair对象
		        				String alias = AliasKeeper.readAlias(table_openaction.this);
		        				String papername = Listview_opentable.papername;
		        				
		        				List<NameValuePair> params = new ArrayList<NameValuePair>();
		        				params.add(new BasicNameValuePair("alias",alias));
		        				params.add(new BasicNameValuePair("papername",papername));
		        				try {	
		        				// 设置HTTP POST请求参数
		        				httpPost.setEntity(new UrlEncodedFormEntity(params,
		        						HTTP.UTF_8));
		        				// 第2步：使用execute方法发送HTTP POST请求，并返回HttpResponse对象
		        				
		        				//System.out.println("1");
		        				httpResponse = new DefaultHttpClient().execute(httpPost);
		        				//System.out.println("2");
		        				if (httpResponse.getStatusLine().getStatusCode() == 200)
		        				{
		        					// 第3步：使用getEntity方法获得返回结果
		        					String result = EntityUtils.toString(httpResponse
		        							.getEntity());
		        					// 去掉返回结果中的“\r”字符，否则会在结果字符串后面显示一个小方格
		        					//tvQueryResult.setText(result.replaceAll("\r", ""));
		        					Toast.makeText(table_openaction.this,"删除试卷成功",Toast.LENGTH_LONG).show();
		        				}
		        				else System.out.println(httpResponse.getStatusLine().getStatusCode());
		        			}catch (Exception e)
		        			{
		        				//Toast.makeText(table_openaction.this,"删除试卷失败",Toast.LENGTH_LONG).show();
		        			}
		                    }
		                }.start();
		                
		                mydb = mySQL.getReadableDatabase();
						mydb.delete("Resulttable", "_id=?",new String[] {Listview_opentable.myid+""});
					    Toast.makeText(table_openaction.this,"删除成功",Toast.LENGTH_LONG).show();
						// TODO Auto-generated method stub
						Intent intent = new Intent(table_openaction.this,Listview_opentable.class);
						startActivity(intent);
					
				}
				
		});
           button_03.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				
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
                         Toast.makeText(table_openaction.this, 
                                 "获取微博信息成功: " + statuses.statusList.size(), 
                                 Toast.LENGTH_LONG).show();
                     }
                 } else if (response.startsWith("{\"created_at\"")) {
                     // 璋冪敤 Status#parse 瑙ｆ瀽瀛楃涓叉垚寰崥瀵硅薄
                     Status status = Status.parse(response);
                     Toast.makeText(table_openaction.this, 
                             "上传成功, id = " + status.id, 
                             Toast.LENGTH_LONG).show();
                 } else {
                     Toast.makeText(table_openaction.this, response, Toast.LENGTH_LONG).show();
                 }
             }
         }

 		@Override
 		public void onWeiboException(WeiboException arg0) {
 			// TODO Auto-generated method stub
 			
 		}
     }; 
}

     