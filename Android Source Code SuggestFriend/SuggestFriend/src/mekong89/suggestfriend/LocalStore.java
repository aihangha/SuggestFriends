package mekong89.suggestfriend;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalStore {
	public static void saveString(Context context, String key, String value){
		SharedPreferences mPrefs = context.getSharedPreferences("SuggestFriend", Activity.MODE_PRIVATE);
		Editor edit = mPrefs.edit();
		edit.putString(key, value.trim());
		edit.commit();				
	}
	public static String getString(Context context, String key){
		SharedPreferences mPrefs = context.getSharedPreferences("SuggestFriend", Activity.MODE_PRIVATE);
		return mPrefs.getString(key, "");		
	}
	public static void saveInt(Context context, String key, int value){
		SharedPreferences mPrefs = context.getSharedPreferences("SuggestFriend", Activity.MODE_PRIVATE);
		Editor edit = mPrefs.edit();
		edit.putInt(key, value);
		edit.commit();				
	}
	public static int getInt(Context context, String key){
		SharedPreferences mPrefs = context.getSharedPreferences("SuggestFriend", Activity.MODE_PRIVATE);
		return mPrefs.getInt(key, 0);		
	}		
}
