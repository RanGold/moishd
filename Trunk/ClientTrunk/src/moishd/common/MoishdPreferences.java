package moishd.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MoishdPreferences {
	private static String prefName = "AVAILABLE_PREFS";
	private static String booleanName = "AVAILABLE";
	private static String prefUserName = "USERNAME_PREF";
	private static String stringName = "NAME";
	
	public static boolean userIsAvailable(Context context) {
		SharedPreferences playingPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
		boolean isPlaying = playingPrefs.getBoolean(booleanName, false);	        
		return isPlaying;
	}
	 
	public static void setAvailableStatus(Context context, boolean status){
		SharedPreferences playingPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
		Editor editor = playingPrefs.edit();
		editor.putBoolean(booleanName, status);
		editor.commit();
	}
	
	public static String getUserName(Context context){
		SharedPreferences userNamePref = context.getSharedPreferences(prefUserName, Context.MODE_PRIVATE);
		String userName = userNamePref.getString(stringName, "");
		return userName;
	}
	
	public static void setUserName(Context context, String name){
		SharedPreferences userNamePref = context.getSharedPreferences(prefUserName, Context.MODE_PRIVATE);
		Editor editor = userNamePref.edit();
		editor.putString(stringName, name);
		editor.commit();
	}
}
