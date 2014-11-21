package com.example.project;

import java.util.ArrayList;
import java.util.Date;
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
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.net.RequestListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends Activity {
	public static String alias;
    private Button btn1 ;
    private Button btn2 ;
    private Button btn3 ;
    private Button btn4 ;
    private Button btn5 ;
    //private Button btn6 ;
    long preTime;
  //服务器网址
    String url = "http://1.xgbjwenda.sinaapp.com/getrankbyphone" ;
    HttpResponse httpResponse = null;
    //
    private static final String TAG = MainActivity.class.getName();
    private Oauth2AccessToken mAccessToken;
    /** 鐢ㄤ簬鑾峰彇寰崥淇℃伅娴佺瓑鎿嶄綔鐨凙PI */
    private StatusesAPI mStatusesAPI;
    
    public static final long TWO_SECOND = 2 * 1000;  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////////////////////////////////////鍒濆鍖�
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        /////////////////////////////////////
        // 鑾峰彇褰撳墠宸蹭繚瀛樿繃鐨� Token
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        // 瀵箂tatusAPI瀹炰緥鍖�
        mStatusesAPI = new StatusesAPI(mAccessToken);
        //瀵规墍鏈夋枃浠剁殑鍏ㄥ眬鍙橀噺杩涜涓�涓竻绌烘搷浣�
        ////////////////////////////////
      
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn1=(Button)findViewById(R.id.btn1);
        //btn6=(Button)findViewById(R.id.btn6);
       
        btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   Intent intent = new Intent();
			   intent.setClass(MainActivity.this, Listview_opentable.class);
			   startActivity(intent);
			}
		});
        btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   Intent intent = new Intent();
			   intent.setClass(MainActivity.this, Listview_table.class);
			   startActivity(intent);
			}
		});
        btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   Intent intent = new Intent();
			   intent.setClass(MainActivity.this, Listview.class);
			   startActivity(intent);
			}
		});
        btn4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   Intent intent = new Intent();
			   intent.setClass(MainActivity.this, WBAuthActivity.class);
			   startActivity(intent);
			}
		});
       
        
            
       
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
             //System.out.println(AccessTokenKeeper.ALISA);
        	System.out.println(AliasKeeper.readAlias(MainActivity.this));
        }
        
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
                        Toast.makeText(MainActivity.this, 
                                "获取状态成功: " + statuses.statusList.size(), 
                                Toast.LENGTH_LONG).show();
                    }
                } else if (response.startsWith("{\"created_at\"")) {
                    // 璋冪敤 Status#parse 瑙ｆ瀽瀛楃涓叉垚寰崥瀵硅薄
                    Status status = Status.parse(response);
                    Toast.makeText(MainActivity.this, 
                            "发送微博成功, id = " + status.id, 
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub
			
		}
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        // 鎴幏鍚庨��閿�  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
            long currentTime = new Date().getTime();  
  
            // 濡傛灉鏃堕棿闂撮殧澶т簬2绉�, 涓嶅鐞�  
            if ((currentTime - preTime) > TWO_SECOND) {  
                // 鏄剧ず娑堟伅  
                Toast.makeText(this, "再次按下退出.",  
                        Toast.LENGTH_SHORT).show();  
  
                // 鏇存柊鏃堕棿  
                preTime = currentTime;  
  
                // 鎴幏浜嬩欢,涓嶅啀澶勭悊  
                return true;  
            } 
            else  ;

        }  
  
        return super.onKeyDown(keyCode, event);  
    }  


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
