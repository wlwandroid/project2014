package com.example.project;

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

import cn.jpush.android.api.JPushInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class rank extends Activity{
	 //服务器网址
    private String url = "http://1.xgbjwenda.sinaapp.com/getrankbyphone" ;
    private HttpResponse httpResponse = null;
    //排行榜字串
    private String Result = "wjw";
    /////////
    private ListView listview;
    protected void onCreate(Bundle savedInstanceState){
    	
    	
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.rankview);
    	
    	Intent intent = getIntent();
    	if(intent.hasExtra(JPushInterface.EXTRA_ALERT))
    	{
    		String temp = intent.getStringExtra(JPushInterface.EXTRA_ALERT);
    		String[] tttt = temp.trim().split("#");
    		if(tttt.length >= 3) {
    			table_openaction.papername = tttt[1];
    			System.out.println(tttt[1]);
    		}
    	}
    	//////
    	new Thread() {
            public void run() {
            	// 第1步：创建HttpPost对象
    			HttpPost httpPost = new HttpPost(url);
    			// 设置HTTP POST请求参数必须用NameValuePair对象
    			String alias = AliasKeeper.readAlias(rank.this);
    			String papername = table_openaction.papername;
    			
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
    				 Result = EntityUtils.toString(httpResponse
    						.getEntity());
    				// 去掉返回结果中的“\r”字符，否则会在结果字符串后面显示一个小方格
    				//tvQueryResult.setText(result.replaceAll("\r", ""));
    				System.out.println(Result);
    			}
    			else System.out.println(httpResponse.getStatusLine().getStatusCode());
    		}catch (Exception e)
    		{
    			System.out.println("big error");
    		}
            }
        }.start();
        
        while(Result == "wjw");
    	//////
    	System.out.println(Result);
    	listview = (ListView)findViewById(R.id.ListView01);
    	String[] temp = Result.trim().split(";");    	
    	for(int i=0;i<temp.length;i++)
    		System.out.println(temp[i]);
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,temp));
        //setContentView(listview);
        
		
		

		//ArrayAdapter<String> aaData = new ArrayAdapter<String>(this,
			//	android.R.layout.simple_list_item_1, data);
    }
   
  
    public void webservice(){
    	new Thread() {
            public void run() {
            	// 第1步：创建HttpPost对象
    			HttpPost httpPost = new HttpPost(url);
    			// 设置HTTP POST请求参数必须用NameValuePair对象
    			String alias = AliasKeeper.readAlias(rank.this);
    			String papername = table_openaction.papername;
    			
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
    				 Result = EntityUtils.toString(httpResponse
    						.getEntity());
    				// 去掉返回结果中的“\r”字符，否则会在结果字符串后面显示一个小方格
    				//tvQueryResult.setText(result.replaceAll("\r", ""));
    				System.out.println(Result);
    			}
    			else System.out.println(httpResponse.getStatusLine().getStatusCode());
    		}catch (Exception e)
    		{
    			System.out.println("big error");
    		}
            }
        }.start();
    }
}
