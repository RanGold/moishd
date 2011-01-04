package moishd.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AvailablePreferences {
	private static String prefName = "AVAILABLE_PREFS";
	private static String booleanName = "AVAILABLE";
	
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
}
