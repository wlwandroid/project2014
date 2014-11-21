package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class AliasKeeper {
	    
	    private static final String PREFERENCES_NAME = "jpush_alias";

	    private static final String KEY_ALIAS           = "alias";
	    
	    public static void writeAlias(Context context, String alias) {
	        if (null == context || null == alias) {
	            return;
	        }
	        
	        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
	        Editor editor = pref.edit();
	        editor.putString(KEY_ALIAS,alias);
	        
	        editor.commit();
	    }

	    /**
	     * 从 SharedPreferences 读取 Token 信息。
	     * 
	     * @param context 应用程序上下文环境
	     * 
	     * @return 返回 Token 对象
	     */
	    public static String readAlias(Context context) {
	        if (null == context) {
	            return null;
	        }
	        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
	        return pref.getString(KEY_ALIAS, "没有alias");
	    }

	    /**
	     * 清空 SharedPreferences 中 Token信息。
	     * 
	     * @param context 应用程序上下文环境
	     */
	    public static void clear(Context context) {
	        if (null == context) {
	            return;
	        }
	        
	        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
	        Editor editor = pref.edit();
	        editor.clear();
	        editor.commit();
	    }
}
