package moishd.android;

import java.util.Timer;
import java.util.TimerTask;

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
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentRequestCodesEnum;
import moishd.common.IntentResultCodesEnum;
import moishd.common.LocationManagment;
import moishd.common.MoishdPreferences;
import moishd.common.SharedPreferencesKeysEnum;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.c2dm.C2DMessaging;

public class WelcomeScreenActivity extends Activity{

	private static final String APP_ID = "108614622540129";

	private Account userGoogleAccount;

	protected static Facebook facebook;
	private static LoginButton loginButton;
	private AsyncFacebookRunner asyncRunner;

	private LocationManagment locationManagment;
	private ConnectivityManager connectivityManager;
	private Location location;
	private ProgressDialog progressDialog ;
	private final int DIALOG_AUTH_TOKEN_DECLINED = 11;
	private final int DIALOG_FACEBOOK_ERROR = 12;
	private final int DIALOG_MOISHD_SERVER_REGISTRATION_ERROR = 13;
	private final int DIALOG_C2DM_ERROR = 14;
	private final int REGISTRATION_COMPLETE = 15;
	private final int START_ACOUNT_SETTINGS = 16;
	private final int DIALOG_NO_ACCOUNTS = 17;
	private final int DIALOG_SHOW_ACCOUNTS = 18;
	private final int DIALOG_NO_INTERNET_CONNECTION = 19;

	private String googleAuthString = null;
	private int numberOfTriesLeft = 3;
	private Timer timer;
	private Handler timerHandler = new Handler();
	private boolean sessionIsValid;
	private Intent intent;
	private String[] names;
	private Account[] accounts ;


	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DIALOG_FACEBOOK_ERROR:
				if (progressDialog != null)
					progressDialog.dismiss();
				showDialog(DIALOG_FACEBOOK_ERROR);
				break;

			case DIALOG_MOISHD_SERVER_REGISTRATION_ERROR:
				if (progressDialog != null)
					progressDialog.dismiss();
				showDialog(DIALOG_MOISHD_SERVER_REGISTRATION_ERROR);
				break;

			case DIALOG_C2DM_ERROR:
				if (progressDialog != null)
					progressDialog.dismiss();
				showDialog(DIALOG_C2DM_ERROR);
				break;
		
			case REGISTRATION_COMPLETE:
				if (progressDialog != null)
					progressDialog.dismiss();
				intent = new Intent().setClass(getApplicationContext(), AllOnlineUsersActivity.class);
				startActivity(intent);	
				break;

			case START_ACOUNT_SETTINGS:
				intent = new Intent(android.provider.Settings.ACTION_SYNC_SETTINGS);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);

			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		loginButton = (LoginButton) findViewById(R.id.login);
		TextView text = (TextView) findViewById(R.id.moishdName);

		Typeface fontOfName = Typeface.createFromAsset(getAssets(), "fonts/COOPBL.ttf");
		text.setTypeface(fontOfName);

		connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); 

		facebook = new Facebook(APP_ID);
		asyncRunner = new AsyncFacebookRunner(facebook);
		//check if the user is logged in into Facebook
		sessionIsValid = SessionStore.restore(facebook, this);
		SessionEvents.addAuthListener(new MoishdAuthListener());
		SessionEvents.addLogoutListener(new MoishdLogoutListener());
		loginButton.init(this, facebook);

		//check if the user already authorized Moish'd! to use his Google Account for registration
		locationManagment = LocationManagment.getLocationManagment(getApplicationContext(), googleAuthString);

		googleAuthString = getGoogleAuthToken();

	}

	@Override
	protected void onResume(){
		super.onResume();
		if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			showDialog(DIALOG_NO_INTERNET_CONNECTION);
		}
		if (!MoishdPreferences.isReturnedFromAuth(getApplicationContext())){
			if (googleAuthString == null){
				startGoogleAuth();
			}
			else if (sessionIsValid){
				doAuthSucceed();
			}
		}
		else{
			//does nothing because the dialog takes care of getting starting startGoogleAuth()
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

		if (requestCode == IntentRequestCodesEnum.GetGoogleAccountToken.getCode()){
			if (resultCode == IntentResultCodesEnum.OK.getCode()){
				MoishdPreferences.setReturnFromAuth(getApplicationContext(), false);
				String authString = data.getExtras().getString(IntentExtraKeysEnum.GoogleAuthToken.toString());
				googleAuthString = authString;
				saveGoogleAuthToken(authString);
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Moish'd! cannot start as it requires a Google account for registration. Retry?")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						MoishdPreferences.setReturnFromAuth(getApplicationContext(), false);
						authorizeGoogleAccount(userGoogleAccount);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {	
						MoishdPreferences.setReturnFromAuth(getApplicationContext(), false);
						moveTaskToBack(true);
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
			if (action.equals(IntentExtraKeysEnum.C2DMError.toString())){
				showDialog(DIALOG_C2DM_ERROR);
			}
		}
	}

	protected static void facebookLogout(View arg0){
		loginButton.logout(arg0);
	}

	//retrieve user's Google account
	private void startGoogleAuth(){
		final AccountManager manager = AccountManager.get(this);
		accounts = manager.getAccountsByType("com.google");
		final int size = accounts.length;
		if (size==0){
			showDialog(DIALOG_NO_ACCOUNTS);
		} 
		else{
			names = new String[size];
			for (int i = 0; i < size; i++) {
				names[i] = accounts[i].name;
			}
			Bundle args = new Bundle();
			args.putStringArray("names", names);
			showDialog(DIALOG_SHOW_ACCOUNTS,args);
		}
	}

	//authorize user's Google account
	private void authorizeGoogleAccount(Account account){

		Intent intent = new Intent(this, AuthorizeGoogleAccountActivity.class);
		intent.putExtra(IntentExtraKeysEnum.GoogleAccount.toString(), account);
		startActivityForResult(intent, IntentRequestCodesEnum.GetGoogleAccountToken.getCode());
	}

	//in case Facebook login process succeeds - retrieve user's Facebook profile for registration process
	private void doAuthSucceed(){
		Log.d("Facebook", "doAuthSucceed");

		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
		registrationIntent.putExtra("sender", "app.moishd@gmail.com");
		startService(registrationIntent);
		Log.d("TEST","Resgistering...");

		progressDialog = ProgressDialog.show(this, null, "Registering with Moish'd! server", true, false);		

		timer=new Timer();
		timer.schedule(new ifRegisteredThanLoginTask(), 3000, 5000);
	}

	private class ifRegisteredThanLoginTask extends TimerTask{
		private Runnable run;

		@Override
		public void run() {
			run = new Runnable() {
				public void run() {
					if (isC2DMRegistered()){
						Log.d("TEST", "in isRegistered");
						timer.cancel();
						asyncRunner.request("me", new ProfileRequestListener(location));
					}
					else{
						if (numberOfTriesLeft == 1) {
							timer.cancel();
							numberOfTriesLeft =3;

							progressDialog.dismiss();
							Message registrationErrorMessage = Message.obtain();
							registrationErrorMessage.setTarget(mHandler);
							registrationErrorMessage.what = DIALOG_C2DM_ERROR;
							registrationErrorMessage.sendToTarget();
						}
						else
							numberOfTriesLeft--;
					}
				}
			}; 
			Log.d("TEST", "in TimerTask");
			timerHandler.post(run);
		}

	}

	private boolean isC2DMRegistered() {

		String registrationId = C2DMessaging.getRegistrationId(this);
		if (registrationId.equals("")){
			return false;
		}
		else{
			return true;
		}
	}

	private void unregisterC2DM() {

		if (isC2DMRegistered()){
			Intent unregIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
			unregIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
			startService(unregIntent);
		}
	}

	//save user's Google account name in SharedPrefernces
	private void saveGoogleAccount(String accountName) {

		Context context = getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.GoogleSharedPreferences.toString(),Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(SharedPreferencesKeysEnum.GoogleAccountName.toString(), accountName);
		editor.commit();
	}

	//retrieve user's Google account name from SharedPrefernces
	@SuppressWarnings("unused")
	private String getGoogleAccount() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.GoogleSharedPreferences.toString(),Context.MODE_PRIVATE);
		String authString = prefs.getString(SharedPreferencesKeysEnum.GoogleAccountName.toString(), null);

		return authString;
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

	private void saveUserName(String userName, String firstName) {

		Context context = getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.FacebookDetails.toString(),Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(SharedPreferencesKeysEnum.FacebookUserName.toString(), userName);
		editor.putString(SharedPreferencesKeysEnum.FacebookFirstName.toString(), firstName);

		editor.commit();
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

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args){
		switch (id){
		case DIALOG_NO_ACCOUNTS:
			super.onPrepareDialog(id, dialog);
			break;
		case DIALOG_SHOW_ACCOUNTS:
			args.putStringArray("names", names);
			super.onPrepareDialog(id, dialog);
			break;
		case DIALOG_AUTH_TOKEN_DECLINED:
			super.onPrepareDialog(id, dialog);
			break;
		case DIALOG_FACEBOOK_ERROR:
			super.onPrepareDialog(id, dialog);	
			break;
		case DIALOG_MOISHD_SERVER_REGISTRATION_ERROR:
			super.onPrepareDialog(id, dialog);
			break;
		case DIALOG_C2DM_ERROR:
			super.onPrepareDialog(id, dialog);
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_NO_ACCOUNTS:
			builder.setTitle("Error");
			builder.setMessage("Moish'd! cannot start as it requires a Google account for registration. " +
			"Please create one under Settings/Accounts & Sync.")
			.setCancelable(false)
			.setNeutralButton("Finish", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Message registrationErrorMessage = Message.obtain();
					registrationErrorMessage.setTarget(mHandler);
					registrationErrorMessage.what = START_ACOUNT_SETTINGS;
					registrationErrorMessage.sendToTarget();
				}
			});
			return builder.create();
			
		case DIALOG_NO_INTERNET_CONNECTION:
			builder.setTitle("Error");
			builder.setMessage("Moish'd! cannot start as it requires internet connection.")
			.setCancelable(false)
			.setNeutralButton("Finish", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					moveTaskToBack(true);
				}
			});
			return builder.create();
		case DIALOG_SHOW_ACCOUNTS:
			builder.setTitle("Select a Google account");
			String[] names = args.getStringArray("names");
			builder.setItems(names, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					userGoogleAccount = accounts[which]; 
					saveGoogleAccount(userGoogleAccount.name);
					authorizeGoogleAccount(userGoogleAccount);
				}	
			});
			return builder.create();
		case DIALOG_AUTH_TOKEN_DECLINED:
			builder.setTitle("Error");
			builder.setMessage("Moish'd! cannot start as it requires your permission to access your Google account for registration needs. " +
			"Retry?")
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					showDialog(DIALOG_SHOW_ACCOUNTS);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//finish();
					moveTaskToBack(true);
				}
			});
			return builder.create();

		case DIALOG_FACEBOOK_ERROR:
			builder.setTitle("Error");
			builder.setMessage("Registration to Facebook server failed. Please retry in a few seconds.")
			.setCancelable(false)
			.setNeutralButton("Quit", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					facebookLogout(null);
					//finish();
					moveTaskToBack(true);
				}
			});
			return builder.create();  

		case DIALOG_MOISHD_SERVER_REGISTRATION_ERROR:
			builder.setTitle("Error");
			builder.setMessage("Registration to Moish'd! server failed.")
			.setCancelable(false)
			.setNeutralButton("Quit", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					facebookLogout(null);
					//finish();
					moveTaskToBack(true);
				}
			});
			return builder.create();  			
		case DIALOG_C2DM_ERROR:
			builder.setTitle("Error");
			builder.setMessage("Registration to Moish'd! server failed. Please retry in a few seconds.")
			.setCancelable(false)
			.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					facebookLogout(null);
				}
			});
			return builder.create();  
		}
		return null;

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
				final String firstName = json.getString("first_name");
				final String userName = json.getString("name");
				final String userId = json.getString("id");
				final String pictureLink = "http://graph.facebook.com/" + userId + "/picture";

				ClientMoishdUser newUser = new ClientMoishdUser();
				newUser.setUserNick(userName);
				newUser.setFacebookID(userId);
				newUser.setPictureLink(pictureLink);
				newUser.setRegisterID(C2DMessaging.getRegistrationId(getApplicationContext()));


				location = locationManagment.getLastKnownLocation();

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
					saveUserName(userName, firstName);
					sendMessageToHandler(REGISTRATION_COMPLETE);					
				}
				else{
					sendMessageToHandler(DIALOG_MOISHD_SERVER_REGISTRATION_ERROR);
				}
			} catch (JSONException e) {
				Log.w("Moishd-JsonExeption", "JSON Error in response");
				sendMessageToHandler(DIALOG_FACEBOOK_ERROR);
			} catch (FacebookError e) {
				Log.w("Moishd-FacebookError", "Facebook Error: " + e.getMessage());
				sendMessageToHandler(DIALOG_FACEBOOK_ERROR);
			}
		}

		public void sendMessageToHandler(final int messageType) {
			Message registrationErrorMessage = Message.obtain();
			registrationErrorMessage.setTarget(mHandler);
			registrationErrorMessage.what = messageType;
			registrationErrorMessage.sendToTarget();
		}

	}

}