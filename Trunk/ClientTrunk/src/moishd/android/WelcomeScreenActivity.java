package moishd.android;

import moishd.android.facebook.AsyncFacebookRunner;
import moishd.android.facebook.BaseRequestListener;
import moishd.android.facebook.Facebook;
import moishd.android.facebook.FacebookError;
import moishd.android.facebook.LoginButton;
import moishd.android.facebook.SessionEvents;
import moishd.android.facebook.SessionStore;
import moishd.android.facebook.Util;
import moishd.android.facebook.SessionEvents.AuthListener;
import moishd.android.facebook.SessionEvents.LogoutListener;
import moishd.client.dataObjects.ClientMoishdUser;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentRequestCodesEnum;
import moishd.common.IntentResultCodesEnum;
import moishd.common.SharedPreferencesKeysEnum;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class WelcomeScreenActivity extends Activity{

	private static final String APP_ID = "108614622540129";

	private Account userGoogleAccount;

	private static LoginButton loginButton;
	private static Facebook facebook;
	private AsyncFacebookRunner asyncRunner;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String googleAuthString = getGoogleAuthString();

		if (googleAuthString == null){
			startGoogleAuth();
		}

		setContentView(R.layout.main);
		loginButton = (LoginButton) findViewById(R.id.login);

		facebook = new Facebook(APP_ID);
		asyncRunner = new AsyncFacebookRunner(facebook);

		boolean isSessionValid = SessionStore.restore(facebook, this);
		SessionEvents.addAuthListener(new MoishdAuthListener());
		SessionEvents.addLogoutListener(new MoishdLogoutListener());
		loginButton.init(this, facebook);

		if (isSessionValid){
			Intent intent = new Intent().setClass(this, AllOnlineUsersActivity.class);
			startActivity(intent);
		}
	}
	
	protected void startGoogleAuth(){
		Intent intent = new Intent(this, AccountList.class);
		startActivityForResult(intent, IntentRequestCodesEnum.PickGoogleAccount.getCode());
	}

	protected void authorizeGoogleAccount(Account account){
		Intent intent = new Intent(this, AuthorizeGoogleAccount.class);
		intent.putExtra(IntentExtraKeysEnum.GoogleAccount.toString(), account);
		startActivityForResult(intent, IntentRequestCodesEnum.GetGoogleAccountToken.getCode());
	}

	protected static void facebookLogout(View arg0){
		loginButton.logout(arg0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == IntentRequestCodesEnum.PickGoogleAccount.getCode()){
			if (resultCode == IntentResultCodesEnum.Failed.getCode()){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Moish'd! cannot start as it requires a Google account for registration. " +
				"Please create one under Settings/Accounts & Sync.")
				.setCancelable(false)
				.setNeutralButton("Finish", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			else{
				userGoogleAccount = (Account) data.getExtras().get(IntentExtraKeysEnum.GoogleAccount.toString());
				authorizeGoogleAccount(userGoogleAccount);
			}
		}

		else if(requestCode == IntentRequestCodesEnum.GetGoogleAccountToken.getCode()){

			if (resultCode == IntentResultCodesEnum.OK.getCode()){
				String authString = data.getExtras().getString(IntentExtraKeysEnum.GoogleAuthToken.toString());
				saveGoogleAuthString(authString);
			}
			else{
				finish();
			}
		}
		else{
			facebook.authorizeCallback(requestCode, resultCode, data);
		}
	}	

	private void saveGoogleAuthString(String authString) {

		Context context = getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.GoogleSharedPreferences.toString(),Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(SharedPreferencesKeysEnum.GoogleAuthToken.toString(), authString);
		editor.commit();
	}

	private String getGoogleAuthString() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.GoogleSharedPreferences.toString(),Context.MODE_PRIVATE);
		String authString = prefs.getString(SharedPreferencesKeysEnum.GoogleAuthToken.toString(), null);

		return authString;
	}

	private void doAuthSucceed(){

		asyncRunner.request("me", new ProfileRequestListener());
	}

	public class MoishdAuthListener implements AuthListener {

		public void onAuthSucceed() {
			doAuthSucceed();
		}

		public void onAuthFail(String error) {
		}
	}

	public class MoishdLogoutListener implements LogoutListener {
		public void onLogoutBegin() {
		}

		public void onLogoutFinish() {
		}
	}

	public class ProfileRequestListener extends BaseRequestListener {

		public void onComplete(final String response) {
			try {
				JSONObject json = Util.parseJson(response);
				final String userName = json.getString("name");
				final String userId = json.getString("id");
				final String pictureLink = "http://graph.facebook.com/" + userId + "/picture";
				
				ClientMoishdUser newUser = new ClientMoishdUser();
				newUser.setUserNick(userName);
				newUser.setFacebookID(userId);
				newUser.setPictureLink(pictureLink);
				
				String authString = getGoogleAuthString();
				boolean registrationComplete = ServerCommunication.enlistUser(newUser, authString);
				
				if (registrationComplete){
					Intent intent = new Intent().setClass(getApplicationContext(), AllOnlineUsersActivity.class);
					startActivity(intent);
				}
				//if registration fails, need to logout the user, show an error message and quit.

			} catch (JSONException e) {
				Log.w("Moishd-JsonExeption", "JSON Error in response");
			} catch (FacebookError e) {
				Log.w("Moishd-FacebookError", "Facebook Error: " + e.getMessage());
			}
		}
	}
}