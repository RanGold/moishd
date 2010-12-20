package moishd.android;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import moishd.android.facebook.AsyncFacebookRunner;
import moishd.android.facebook.BaseRequestListener;
import moishd.android.facebook.FacebookError;
import moishd.android.facebook.Util;
import moishd.android.games.FastClick;
import moishd.android.games.Mixing;
import moishd.android.games.SimonPro;
import moishd.android.games.TruthOrDare;
import moishd.android.games.TruthPart;
import moishd.client.dataObjects.ClientMoishdUser;
import moishd.common.ActionByPushNotificationEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentRequestCodesEnum;
import moishd.common.SharedPreferencesKeysEnum;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AllOnlineUsersActivity extends Activity {
	
	protected String authToken;
	protected String game_id;
	protected String gameType;
	protected int currentClickPosition;
	private ListView list;
	private AsyncFacebookRunner asyncRunner;
	private static List<ClientMoishdUser> moishdUsers;
	
	private String TAG = "LOCATION-AllOnlineUsers";
	private Handler mHandler = new Handler();
	private Location currentBestLocation ;
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	private Timer timer;
	private LocationManager locationManager ;
	
	private LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			// Called when a new location is found by the network location provider.
			Log.d(TAG, "Got Location Changed from "+location.getProvider()+": "
					+"Longitude="+location.getLongitude()+"  Latitude="+location.getLatitude());
			if (isBetterLocation(location, currentBestLocation))
				currentBestLocation = location;
			Log.d(TAG, "decided on location: "
					+"Longitude="+currentBestLocation.getLongitude()+"  Latitude="+currentBestLocation.getLatitude());
			locationManager.removeUpdates(locationListener);
			ServerCommunication.updateLocationInServer(currentBestLocation, authToken);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {}
		public void onProviderEnabled(String provider) {}
		public void onProviderDisabled(String provider) {}
	};

	
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}
	
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
	
	private void GetCurrentLocation(int minutes){
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new getCurrentLocationTask(), 0, 60*1000*minutes);
	}
	
	private class getCurrentLocationTask extends TimerTask{
		private Runnable run;
		
		@Override
		public void run() {
			run = new Runnable() {
				@Override
				public void run() {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 50, locationListener);
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 50, locationListener);
				}
			}; 
			Log.d(TAG, "in TimerTask");
			mHandler.post(run);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//need the authToken for server requests
		authToken = getGoogleAuthToken();

		boolean isRegistered = isC2DMRegistered();
		if (!isRegistered){
			registerC2DM();
		}

		getAllUsers();

		setContentView(R.layout.all_users_layout);
		list = (ListView) findViewById(R.id.allUsersListView);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				currentClickPosition = arg2;
				inviteUserToMoishDialog();
			}});
		list.setAdapter(new EfficientAdapter(this));
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		GetCurrentLocation(1);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.users_screen_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.RefreshList:
			EfficientAdapter listAdapter = (EfficientAdapter) list.getAdapter();
			moishdUsers = ServerCommunication.getAllUsers(authToken);
			listAdapter.notifyDataSetChanged();
			return true;
		case R.id.logout:
			unregisterC2DM();
			WelcomeScreenActivity.facebookLogout(null);
			finish();
			return true;
		case R.id.facebookFriends:
			return true;
		case R.id.nearbyUsers:
			return true;
		case R.id.allUsers:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onNewIntent (Intent intent){
		
		game_id = intent.getStringExtra(IntentExtraKeysEnum.PushGameId.toString());	
		String action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());
		gameType = intent.getStringExtra(IntentExtraKeysEnum.GameType.toString());
		
		if (action!=null){
			if (action.equals(ActionByPushNotificationEnum.GameInvitation.toString())){
				retrieveInvitation();
			}
			else if (action.equals(ActionByPushNotificationEnum.GameDeclined.toString())){
				userDeclinedToMoishDialog();
				game_id = null;
			}
			else if (action.equals(ActionByPushNotificationEnum.StartGameTruth.toString())){
				startGameTruth();
			}
			else if (action.equals(ActionByPushNotificationEnum.StartGameDare.toString())) {
				startGameDare();
			}
			
			else if (action.equals(ActionByPushNotificationEnum.GameResult.toString())){
				String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
				gameResultDialog(result);
			}
		}
	}

	@Override
	protected void onDestroy (){
		super.onDestroy();
		unregisterC2DM();
		timer.cancel();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentRequestCodesEnum.GetChosenGame.getCode()){
			gameType = data.getStringExtra(IntentExtraKeysEnum.GameType.toString());
			sendInvitationResponse("Accept" + gameType);

			
		}
	}
	
	private void getAllUsers(){
		moishdUsers = ServerCommunication.getAllUsers(authToken);
	}
	
	private void getFriendsUsers(){
		
		asyncRunner.request("me", new ProfileRequestListener());
		moishdUsers = ServerCommunication.getAllUsers(authToken);
	}

	private void inviteUserToMoishDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You've invited  " + moishdUsers.get(currentClickPosition).getUserNick() + " to Moish. Continue?")
		.setCancelable(false)
		.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				inviteUserToMoish(moishdUsers.get(currentClickPosition));
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();
	}

	private void inviteUserToMoish(ClientMoishdUser user){

		game_id = ServerCommunication.inviteUser(user, authToken);

	}

	private void retrieveInvitation(){

		ClientMoishdUser user = ServerCommunication.retrieveInvitation(game_id, authToken);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You've been invited by " + user.getUserNick() + " to Moish.")
		.setCancelable(false)
		.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int id) {
				
				Intent TruthOrDareIntent = new Intent(AllOnlineUsersActivity.this, TruthOrDare.class); //opens the screen of truth and dare for the user to choose
				startActivityForResult(TruthOrDareIntent, IntentRequestCodesEnum.GetChosenGame.getCode());
				dialog.cancel();
			}
		})
		.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				sendInvitationResponse("Decline");	
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();
	}

	private boolean sendInvitationResponse(String response){
		return ServerCommunication.sendInvitationResponse(game_id, response, authToken);

	}

	private void userDeclinedToMoishDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your invitation has been declined")
		.setCancelable(false)
		.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();

	}

	private void startGameDare(){
		Intent intent;
		if (gameType.equals(IntentExtraKeysEnum.DareSimonPro.toString()))
			 intent = new Intent(this, SimonPro.class);
		else if (gameType.equals(IntentExtraKeysEnum.DareMixing.toString()))
			 intent = new Intent(this, Mixing.class);
		else //TODO right now else case is fast click.
			intent = new Intent(this, FastClick.class);
		intent.putExtra(IntentExtraKeysEnum.PushGameId.toString(), game_id);
		intent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
		intent.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);
		startActivity(intent);
	}
	
	private void startGameTruth(){
		Intent intent = new Intent(this, TruthPart.class);
		intent.putExtra(IntentExtraKeysEnum.PushGameId.toString(), game_id);
		intent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
		intent.putExtra(IntentExtraKeysEnum.GameType.toString(), IntentExtraKeysEnum.Truth.toString());
		startActivity(intent);
	}

	private void gameResultDialog(String result){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You've " + result + "!")
		.setCancelable(false)
		.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();

	}

	private String getGoogleAuthToken() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.GoogleSharedPreferences.toString(),Context.MODE_PRIVATE);
		String authString = prefs.getString(SharedPreferencesKeysEnum.GoogleAuthToken.toString(), null);

		return authString;
	}

	private boolean isC2DMRegistered() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.C2dmSharedPreferences.toString(),Context.MODE_PRIVATE);
		boolean isRegistered = prefs.getBoolean(SharedPreferencesKeysEnum.C2dmRegistered.toString(), false);

		return isRegistered;
	}

	private void registerC2DM() {
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
		registrationIntent.putExtra("sender", "app.moishd@gmail.com");
		startService(registrationIntent);

		Context context = getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.C2dmSharedPreferences.toString(),Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesKeysEnum.C2dmRegistered.toString(), true);
		editor.commit();

		Log.d("TEST","Resgistering...");

	}

	private void unregisterC2DM() {

		Intent unregIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
		unregIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		startService(unregIntent);
		
		Context context = getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.C2dmSharedPreferences.toString(),Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesKeysEnum.C2dmRegistered.toString(), false);
		editor.commit();

	}
	
	private class ProfileRequestListener extends BaseRequestListener {

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
				newUser.setMACAddress("123");//TODO need to replace this with the real mac address
				String authString = getGoogleAuthToken();
				boolean registrationComplete = ServerCommunication.enlistUser(newUser, authString);

				if (registrationComplete){
					Intent intent = new Intent().setClass(getApplicationContext(), AllOnlineUsersActivity.class);
					startActivity(intent);
				}
				//TODO if registration fails, need to logout the user, show an error message and quit.

			} catch (JSONException e) {
				Log.w("Moishd-JsonExeption", "JSON Error in response");
			} catch (FacebookError e) {
				Log.w("Moishd-FacebookError", "Facebook Error: " + e.getMessage());
			}
		}
	}

	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		private Bitmap userRank0;
		private Bitmap userRank1;
		private Bitmap userRank2;
		private Bitmap userRank3;
		//private Bitmap userRank4;
		//private Bitmap userRank5;

		//private Bitmap moishd_logo;

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);

			// Icons bound to the rows.

			userRank0 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_0);
			userRank1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_1);
			userRank2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_2);
			userRank3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_3);
			//userRank4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_4);
			//userRank5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_5);

			//moishd_logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.moishd_logo);
		}

		public int getCount() {
			return moishdUsers.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.all_users_list_item, null);

				holder = new ViewHolder();
				holder.userName = (TextView) convertView.findViewById(R.id.text);
				holder.userPicture = (ImageView) convertView.findViewById(R.id.userPicture);
				holder.userRank = (ImageView) convertView.findViewById(R.id.userRank);
				//holder.moishdButton = (ImageButton) convertView.findViewById(R.id.moishdButton);

				convertView.setTag(holder);
			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.userName.setText(moishdUsers.get(position).getUserNick());
			if (position % 4 == 0){
				holder.userRank.setImageBitmap(userRank0);
			}
			else if (position % 4 == 1){
				holder.userRank.setImageBitmap(userRank1);
			}
			else if (position % 4 == 2){
				holder.userRank.setImageBitmap(userRank2);
			}
			else{
				holder.userRank.setImageBitmap(userRank3);
			}

			Drawable userPic = LoadImageFromWebOperations(moishdUsers.get(position).getPictureLink());
			holder.userPicture.setImageDrawable(userPic);

			return convertView;
		}

		private Drawable LoadImageFromWebOperations(String url){
			
			try{
				InputStream is = (InputStream) new URL(url).getContent();
				Drawable d = Drawable.createFromStream(is, "src name");
				return d;
				}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		static class ViewHolder {
			TextView userName;
			ImageView userPicture;
			ImageView userRank;
		}
	}
}
