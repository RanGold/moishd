package moishd.android;

import java.util.concurrent.locks.LockSupport;

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
import moishd.client.dataObjects.ClientLocation;
import moishd.client.dataObjects.ClientMoishdUser;
import moishd.common.ActionByPushNotificationEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentRequestCodesEnum;
import moishd.common.IntentResultCodesEnum;
import moishd.common.LocationManagment;
import moishd.common.SharedPreferencesKeysEnum;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.android.c2dm.C2DMessaging;

public class WelcomeScreenActivity extends Activity{

	private static final String APP_ID = "108614622540129";

	private Account userGoogleAccount;

	protected static Facebook facebook;
	private static LoginButton loginButton;
	private AsyncFacebookRunner asyncRunner;

	private LocationManagment locationManagment;
	private Location location;
	private ProgressDialog progressDialog;

	private final int SHOW_FACEBOOK_ERROR_DIALOG = 0;
	private final int SHOW_MOISHD_SERVER_REGISTRATION_ERROR_DIALOG = 1;
	private final int SHOW_C2DM_ERROR_DIALOG = 2;
	private final int REGISTRATION_COMPLETE = 3;


	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_FACEBOOK_ERROR_DIALOG:
				progressDialog.dismiss();
				registrationToMoishdServerFailed();
				break;

			case SHOW_MOISHD_SERVER_REGISTRATION_ERROR_DIALOG:
				progressDialog.dismiss();
				registrationToFacebookFailed();
				break;

			case SHOW_C2DM_ERROR_DIALOG:
				progressDialog.dismiss();
				registrationToC2dmFailed();
				break;
				
			case REGISTRATION_COMPLETE:
				progressDialog.dismiss();
				Intent intent = new Intent().setClass(getApplicationContext(), AllOnlineUsersActivity.class);
				startActivity(intent);	
				break;
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		loginButton = (LoginButton) findViewById(R.id.login);

		facebook = new Facebook(APP_ID);
		asyncRunner = new AsyncFacebookRunner(facebook);

		//check if the user is logged in into Facebook
		boolean isSessionValid = SessionStore.restore(facebook, this);
		SessionEvents.addAuthListener(new MoishdAuthListener());
		SessionEvents.addLogoutListener(new MoishdLogoutListener());
		loginButton.init(this, facebook);

		//check if the user already authorized Moish'd! to use his Google Account for registration
		String googleAuthString = getGoogleAuthToken();

		if (googleAuthString == null){
			startGoogleAuth();
		}
		
		locationManagment = LocationManagment.getLocationManagment(getApplicationContext(),getGoogleAuthToken());
		location = locationManagment.getLastKnownLocation();
		
		//registerC2DM();
		boolean c2dmRegisteredSuccessfully = true;

		if (c2dmRegisteredSuccessfully){
			if (isSessionValid){
				doAuthSucceed();
			}
		}
		else{
			Message registrationErrorMessage = Message.obtain();
			registrationErrorMessage.setTarget(mHandler);
			registrationErrorMessage.what = SHOW_C2DM_ERROR_DIALOG;
			registrationErrorMessage.sendToTarget();
		}
	}

	@Override
	protected void onDestroy (){

		if(isC2DMRegistered()){
			unregisterC2DM();
		}
		super.onDestroy();
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
				saveGoogleAuthToken(authString);
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Moish'd! cannot start as it requires a Google account for registration. Retry?")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						authorizeGoogleAccount(userGoogleAccount);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {	
						finish();
					}});

				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		else if (requestCode == IntentRequestCodesEnum.FacebookAuth.getCode()){
			facebook.authorizeCallback(requestCode, resultCode, data);
		}
	}	

	@Override
	protected void onNewIntent (Intent intent){

		String action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());

		if (action!=null){
			if (action.equals(ActionByPushNotificationEnum.C2DMError.toString())){
				C2DMError();
			}
		}
	}

	protected static void facebookLogout(View arg0){
		loginButton.logout(arg0);
	}

	//retrieve user's Google account
	private void startGoogleAuth(){
		Intent intent = new Intent(this, AccountList.class);
		startActivityForResult(intent, IntentRequestCodesEnum.PickGoogleAccount.getCode());
	}

	//authorize user's Google account
	private void authorizeGoogleAccount(Account account){
		Intent intent = new Intent(this, AuthorizeGoogleAccount.class);
		intent.putExtra(IntentExtraKeysEnum.GoogleAccount.toString(), account);
		startActivityForResult(intent, IntentRequestCodesEnum.GetGoogleAccountToken.getCode());
	}

	//in case Facebook login process succeeds - retrieve user's Facebook profile for registration process
	private void doAuthSucceed(){
		progressDialog = ProgressDialog.show(this, null, "Registering with Moish'd! server", true, false);
		asyncRunner.request("me", new ProfileRequestListener(location));
	}

	private boolean isC2DMRegistered() {

		String registrationId = C2DMessaging.getRegistrationId(this);
		if (registrationId == null){
			return false;
		}
		else{
			return true;
		}
	}

	private boolean registerC2DM() {

		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
		registrationIntent.putExtra("sender", "app.moishd@gmail.com");
		startService(registrationIntent);

		Log.d("TEST","Resgistering...");

		int numberOfTriesLeft = 3;
		long waitTime = 30000;
		boolean wasInterrupted;

		while (numberOfTriesLeft > 0){

			LockSupport.parkNanos(waitTime);
			if (Thread.interrupted()){
				wasInterrupted  = true;
			}

			boolean isRegistered = isC2DMRegistered(); 
			if (!isRegistered){
				numberOfTriesLeft--;
				waitTime = waitTime * 2;
			}
			else{
				return true;
			}
		}
		return false;
	}

	private void unregisterC2DM() {

		Intent unregIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
		unregIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		startService(unregIntent);
	}

	private void C2DMError(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Connection to game server failed.")
		.setCancelable(false)
		.setPositiveButton("Retry", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int id) {
				registerC2DM();
			}
		})
		.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				//doQuitActions();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();		
	}

	//save user's GoogleAuthToken in SharedPrefernces
	private void saveGoogleAuthToken(String authToken) {

		Context context = getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.GoogleSharedPreferences.toString(),Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(SharedPreferencesKeysEnum.GoogleAuthToken.toString(), authToken);
		editor.commit();
	}

	//retrieve user's GoogleAuthToken from SharedPrefernces
	private String getGoogleAuthToken() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.GoogleSharedPreferences.toString(),Context.MODE_PRIVATE);
		String authString = prefs.getString(SharedPreferencesKeysEnum.GoogleAuthToken.toString(), null);

		return authString;
	}

	private void registrationToMoishdServerFailed(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Registration to Moish'd! server failed.")
		.setCancelable(false)
		.setNeutralButton("Quit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				facebookLogout(null);
				finish();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();
	}

	private void registrationToC2dmFailed(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Registration to Moish'd! server failed.")
		.setCancelable(false)
		.setNeutralButton("Quit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				facebookLogout(null);
				finish();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();
	}

	private void registrationToFacebookFailed(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Registration to Facebook server failed.")
		.setCancelable(false)
		.setNeutralButton("Quit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				facebookLogout(null);
				finish();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();
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
			unregisterC2DM();
		}
	}

	//listener for incoming HttpResponse containing user's Facebook profile. Continues registration process
	public class ProfileRequestListener extends BaseRequestListener {
		private Location location;


		public ProfileRequestListener(Location location){
			this.location = location;
		}

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
				newUser.setMACAddress("123");

				ClientLocation loc;
				if (location != null)				 
					loc = new ClientLocation(location.getLongitude(), location.getLatitude()) ;
				else{ 
					loc = new ClientLocation(200,200);
				}
				newUser.setLocation(loc);

				String authString = getGoogleAuthToken();
				boolean registrationComplete = ServerCommunication.enlistUser(newUser, authString);
				if (registrationComplete){
					boolean c2dmRegisteredSuccessfully = registerC2DM();
					if (c2dmRegisteredSuccessfully){
						sendMessageToHandler(REGISTRATION_COMPLETE);					
					}
					else{
						sendMessageToHandler(SHOW_C2DM_ERROR_DIALOG);
					}
				}
				else{
					sendMessageToHandler(SHOW_MOISHD_SERVER_REGISTRATION_ERROR_DIALOG);
				}
			} catch (JSONException e) {
				Log.w("Moishd-JsonExeption", "JSON Error in response");
				sendMessageToHandler(SHOW_FACEBOOK_ERROR_DIALOG);
			} catch (FacebookError e) {
				Log.w("Moishd-FacebookError", "Facebook Error: " + e.getMessage());
				sendMessageToHandler(SHOW_FACEBOOK_ERROR_DIALOG);
			}
		}

		private void sendMessageToHandler(final int messageType) {
			Message registrationErrorMessage = Message.obtain();
			registrationErrorMessage.setTarget(mHandler);
			registrationErrorMessage.what = messageType;
			registrationErrorMessage.sendToTarget();
		}
	}

}