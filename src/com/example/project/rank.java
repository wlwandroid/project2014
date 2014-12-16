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

import cn.jpush.android.api.JPushInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class rank extends Activity{
	private static String TAG = "rank";
	private Oauth2AccessToken mAccessToken;
	 //服务器网址
    private String url = "http://1.xgbjwenda.sinaapp.com/getrankbyphone" ;
    private HttpResponse httpResponse = null;
    //排行榜字串
    private String Result = "wjw";
    /////////
    private ListView listview;
    private Button button_share;
    protected void onCreate(Bundle savedInstanceState){
    	
    	
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.rankview);
    	
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
    	
    	button_share = (Button)findViewById(R.id.button_share);
        button_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Result = Result.replace(";", "\n");
				Result=Result.replaceAll(" +"," ");
				
				String papername = table_openaction.papername;
				System.out.println(Result);
				//分享
				  
				       
						String website = "小伙伴们在#"+papername+"#中的答题排行榜如下：\n"+
		                                 Result+
		                                 "\n***大家继续踊跃答题啊~\n";
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
						
			    
		 }
	});
    	
    	
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
    
    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                LogUtil.i(TAG, response);
                if (response.startsWith("{\"statuses\"")) {
                    // 璋冪敤 StatusList#parse 瑙ｆ瀽瀛楃涓叉垚寰崥鍒楄〃瀵硅薄
                    StatusList statuses = StatusList.parse(response);
                    if (statuses != null && statuses.total_number > 0) {
                        Toast.makeText(rank.this, 
                                "获取微博信息成功: " + statuses.statusList.size(), 
                                Toast.LENGTH_LONG).show();
                    }
                } else if (response.startsWith("{\"created_at\"")) {
                    // 璋冪敤 Status#parse 瑙ｆ瀽瀛楃涓叉垚寰崥瀵硅薄
                    Status status = Status.parse(response);
                    Toast.makeText(rank.this, 
                            "分享成功", 
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(rank.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub
			
		}
    }; 
  
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
