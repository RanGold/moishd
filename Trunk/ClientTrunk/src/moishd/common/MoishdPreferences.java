package moishd.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MoishdPreferences {
	private static MoishdPreferences THEMoishdPreferences;
	private static Context AppContext;
	private static SharedPreferences genericSharedPreferences;
	
	public static MoishdPreferences getMoishdPreferences (Context context){
		if (THEMoishdPreferences==null)
			THEMoishdPreferences =  new MoishdPreferences(context);
		return THEMoishdPreferences;
	}
	
	public static MoishdPreferences getMoishdPreferences(){
		return getMoishdPreferences(null);
	}
	
	private MoishdPreferences (Context context){
		AppContext = context;
		genericSharedPreferences = context.getSharedPreferences("Moishd_Preferences",Context.MODE_PRIVATE);
	}
	
		
	public boolean userIsAvailable() {
		return getBooleanFromSP(SharedPreferencesKeysEnum.AvailableStatus);
	}
	 
	public void setAvailableStatus(boolean status){
		setBooleanToSP(SharedPreferencesKeysEnum.AvailableStatus, status);
	}
	
	public String getUserName(){
		return getStringFromSP(SharedPreferencesKeysEnum.UserName);
	}
	
	public void setUserName(String name){
		setStringToSP(SharedPreferencesKeysEnum.UserName, name);
	}
	
	public boolean isReturnedFromAuth(){
		return getBooleanFromSP(SharedPreferencesKeysEnum.ReturnedFroAuth);
	}
	
	public void setReturnFromAuth(boolean value){
		setBooleanToSP(SharedPreferencesKeysEnum.ReturnedFroAuth, value);
	}
	
	public String getGoogleAuthToken(){
		return getStringFromSP(SharedPreferencesKeysEnum.GoogleAuthToken);
	}
	
	public void setGoogleAuthToken(String token){
		setStringToSP(SharedPreferencesKeysEnum.GoogleAuthToken, token);
	}
	
	public String getFacebookUserName(){
		return getStringFromSP(SharedPreferencesKeysEnum.FacebookUserName);
	}
	
	public void setFacebookUserName(String userName){
		setStringToSP(SharedPreferencesKeysEnum.FacebookUserName, userName);
	}
	
	public String getFacebookFirstName(){
		return getStringFromSP(SharedPreferencesKeysEnum.FacebookFirstName);
	}
	
	public void setFacebookFirstName(String firstName){
		setStringToSP(SharedPreferencesKeysEnum.FacebookFirstName, firstName);
	}

	
	//public String 
	

	
	//**************  private Getters + Setters ******************/
	private String getStringFromSP(SharedPreferencesKeysEnum key){
		return genericSharedPreferences.getString(key.toString(), "");
	}
	
	private boolean getBooleanFromSP(SharedPreferencesKeysEnum key){
		return genericSharedPreferences.getBoolean(key.toString(), false);
	}
	
	private void setStringToSP(SharedPreferencesKeysEnum key,String value){
		Editor editor = genericSharedPreferences.edit();
		editor.putString(key.toString(), value);
		editor.commit();
	}
	
	private void setBooleanToSP(SharedPreferencesKeysEnum key, boolean value){
		Editor editor = genericSharedPreferences.edit();
		editor.putBoolean(key.toString(), value);
		editor.commit();		
	}
	
	
	
	
}
