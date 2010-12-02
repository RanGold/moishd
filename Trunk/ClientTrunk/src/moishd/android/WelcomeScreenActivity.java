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

	protected static final int PICK_ACCOUNT_REQUEST = 0;
	protected static final int GET_ACCOUNT_TOKEN_REQUEST = 1;

	protected static final int RESULT_OK = 0;
	protected static final int RESULT_FAILED = 1;

	private static final String APP_ID = "108614622540129";

	private Account userGoogleAccount;
	private static final String GOOGLE_AUTH_PREF = "google_authentication";
	private static final String GOOGLE_AUTH_STRING = "auth_String";

	private static LoginButton loginButton;
	private Facebook facebook;
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
			Intent intent = new Intent().setClass(this, UsersTabWidget.class);
			startActivity(intent);
		}
	}

	protected void startGoogleAuth(){
		Intent intent = new Intent(this, AccountList.class);
		startActivityForResult(intent, PICK_ACCOUNT_REQUEST);
	}

	protected void authorizeGoogleAccount(Account account){
		Intent intent = new Intent(this, AuthorizeGoogleAccount.class);
		intent.putExtra("account", account);
		startActivityForResult(intent, GET_ACCOUNT_TOKEN_REQUEST);
	}

	protected static void facebookLogout(View arg0){
		loginButton.logout(arg0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PICK_ACCOUNT_REQUEST){
			if (resultCode == RESULT_FAILED){
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
				userGoogleAccount = (Account) data.getExtras().get("account");
				authorizeGoogleAccount(userGoogleAccount);
			}
		}

		else if(requestCode == GET_ACCOUNT_TOKEN_REQUEST){

			if (resultCode == RESULT_OK){
				String authString = data.getExtras().getString("auth_token");
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
		SharedPreferences prefs = context.getSharedPreferences(GOOGLE_AUTH_PREF,Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(GOOGLE_AUTH_STRING, authString);
		editor.commit();
	}

	private String getGoogleAuthString() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(GOOGLE_AUTH_PREF,Context.MODE_PRIVATE);
		String authString = prefs.getString(GOOGLE_AUTH_STRING, null);

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
				ClientMoishdUser newUser = new ClientMoishdUser();
				newUser.setUserNick(userName);
				
				String authString = getGoogleAuthString();
				boolean registrationComplete = AndroidUtility.enlistUser(newUser, authString); // what a stupid name.

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