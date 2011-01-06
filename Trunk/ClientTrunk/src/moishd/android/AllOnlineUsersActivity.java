package moishd.android;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import moishd.android.facebook.AsyncFacebookRunner;
import moishd.android.facebook.BaseDialogListener;
import moishd.android.facebook.BaseRequestListener;
import moishd.android.facebook.DialogError;
import moishd.android.facebook.FacebookError;
import moishd.android.facebook.Util;
import moishd.android.games.ChooseGameActivity;
import moishd.android.games.FastClickGameActivity;
import moishd.android.games.MixingGameActivity;
import moishd.android.games.SimonProGameActivity;
import moishd.android.games.TruthOrDareActivity;
import moishd.android.games.TruthPartGameActivity;
import moishd.client.dataObjects.ClientMoishdUser;
import moishd.common.GetUsersByTypeEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentRequestCodesEnum;
import moishd.common.LocationManagment;
import moishd.common.PushNotificationTypeEnum;
import moishd.common.SharedPreferencesKeysEnum;
import moishd.common.AvailablePreferences;

import java.util.Timer;
import java.util.TimerTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import moishd.android.games.MostPopularGameActivity;

public class AllOnlineUsersActivity extends Activity{

	private static List<ClientMoishdUser> moishdUsers = new ArrayList<ClientMoishdUser>();
	private static List<Drawable> usersPictures = new ArrayList<Drawable>();
	private static List<String> friendsID;
	private static GetUsersByTypeEnum currentUsersType;
	private GetUsersByTypeEnum previousClickPosition;

	private static Typeface fontName, fontHeader;

	private String firstName;
	private String authToken;

	private String game_id;
	private String gameType;
	private String last_user;
	private String opponent_auth_token;
	private String opponent_nick_name;
	private boolean serverHasFacebookFriends;

	private int currentClickPosition;

	private TextView header;
	private ListView list;

	private AsyncFacebookRunner asyncRunner;

	private ProgressDialog mainProgressDialog;

	private LocationManagment locationManagment;
	private final int UPDATE_LIST_ADAPTER = 0;
	private final int FACEBOOK_FRIENDS_FIRST_RETRIEVAL_COMPLETED = 1;
	private final int HAS_NO_LOCATION_DIALOG = 2;
	private final int ERROR_RETRIEVING_USERS_DIALOG = 3;
	private final int START_LOCATION_SETTINGS = 4;

	private final int DIALOG_INVITE_USER_TO_MOISHD = 10;
	private final int DIALOG_RETRIEVE_USER_INVITATION = 11;
	private final int DIALOG_USER_IS_BUSY = 12;
	private final int DIALOG_USER_IS_OFFLINE = 13;
	private final int DIALOG_USER_DECLINED = 14;
	private final int DIALOG_HAS_NO_LOCATION = 15;
	private final int DIALOG_ERROR_RETRIEVING_USERS = 16;
	private final int DIALOG_HAS_NO_LOCATION_BEGINNING = 17;
	private final int DIALOG_RANK_UPDATED = 18;
	private final int DIALOG_TROPHIES_UPDATED = 19;
	private final int DIALOG_GET_GAME_OFFER = 20;
	private final int FACEBOOK_POST_RANK_UPDATED = 30;

	private Handler autoRefreshHandler = new Handler();

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_LIST_ADAPTER:
				updateList();
				needRefresh = false;
				break;
			case FACEBOOK_FRIENDS_FIRST_RETRIEVAL_COMPLETED:
				facebookFriendsFirstRetrievalCompleted(); 
				break;
			case HAS_NO_LOCATION_DIALOG:
				hasNoLocationDialog();
				break;
			case ERROR_RETRIEVING_USERS_DIALOG:
				errorRetrievingUsersDialog();
				break;
			case START_LOCATION_SETTINGS:
				openLocationSettings();
				break;
			}
		}
	};
	private boolean needRefresh = false;
	Timer refreshTimer;
	int MINUTE = 1000*60;
	int REFRESH_INTERVAL = 5*MINUTE;
	

	

	private class autoRefreshTask extends TimerTask {
		private Runnable run;
		
		@Override
		public void run() {
			run = new Runnable() {
				public void run() {
					executeRefresh();					
				}
			};
			autoRefreshHandler.post(run);
		}
	}

	private void activateTimer(){
		refreshTimer = new Timer();
		refreshTimer.schedule(new autoRefreshTask(), 60*1000, REFRESH_INTERVAL);
	}
	
	private void restartTimer(){
		refreshTimer.cancel();
		activateTimer();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.all_users_layout);

		fontName = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf"); 
		fontHeader = Typeface.createFromAsset(getAssets(), "fonts/BROADW.ttf"); 

		header = (TextView) findViewById(R.id.usersTypeHeader);
		header.setText("All online users");
		header.setTextSize(20);
		header.setTypeface(fontHeader);

		//need the authToken for server requests
		authToken = getGoogleAuthToken();
		firstName = getFacebookUserName(false);

		asyncRunner = new AsyncFacebookRunner(WelcomeScreenActivity.facebook);

		locationManagment = LocationManagment.getLocationManagment(getApplicationContext(),getGoogleAuthToken());
		locationManagment.startUpdateLocation(1);

		serverHasFacebookFriends = false;
		currentUsersType = GetUsersByTypeEnum.MergedUsers;
		getUsers(GetUsersByTypeEnum.MergedUsers);

		list = (ListView) findViewById(R.id.allUsersListView);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				currentClickPosition = arg2;
				inviteUserToMoishDialog();
			}});


		list.setAdapter(new EfficientAdapter(this));

		if(!ServerCommunication.hasLocation(authToken))
			showDialog(DIALOG_HAS_NO_LOCATION_BEGINNING);
		
		activateTimer();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.users_screen_menu, menu);
		return true;
	}

	private void executeRefresh(){
		if (currentUsersType.equals(GetUsersByTypeEnum.FacebookFriends))
			getFriendsUsers();
		else if (currentUsersType.equals(GetUsersByTypeEnum.MergedUsers))
			getMergedUsers();
		else
			getUsers(currentUsersType);		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.RefreshList:
			executeRefresh();
			restartTimer();
			return true;
		case R.id.logout:
			doQuitActions();
			restartTimer();
			return true;
		case R.id.facebookFriends:
			getUsers(GetUsersByTypeEnum.FacebookFriends);
			restartTimer();
			return true;
		case R.id.nearbyUsers:
			getUsers(GetUsersByTypeEnum.NearbyUsers);
			restartTimer();
			return true;
		case R.id.allUsers:
			getUsers(GetUsersByTypeEnum.MergedUsers);
			restartTimer();
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
		
		//String gameTypeNoRank = gameType.substring(0, gameType.length() - 1);

		if (action!=null){
			if (action.equals(PushNotificationTypeEnum.GameInvitation.toString())){
				retrieveInvitation();
			}
			else if (action.equals(PushNotificationTypeEnum.GameDeclined.toString())){
				userDeclinedToMoishDialog();
				game_id = null;
				last_user=null;
			}

			else if (action.equals(PushNotificationTypeEnum.PlayerBusy.toString())){
				userIsBusy(last_user);
				game_id = null;
				last_user = null;
			}
			else if (action.equals(PushNotificationTypeEnum.PlayerOffline.toString())){
				userIsOffline(last_user);
				game_id = null;
				last_user = null;
			}

			else if (action.equals(PushNotificationTypeEnum.PopularGame.toString())){
				StartGamePopular();
			}

			else if (action.equals(PushNotificationTypeEnum.StartGameTruth.toString())){

				startGameTruth();
			}
			else if (action.equals(PushNotificationTypeEnum.StartGameDare.toString())) {
				startGameDare();
			}
			else if(action.equals(PushNotificationTypeEnum.GameOffer.toString())){
				opponent_auth_token =  intent.getStringExtra(IntentExtraKeysEnum.GoogleAuthTokenOfOpponent.toString());
				opponent_nick_name =  intent.getStringExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString());
				GetGameOfferDialog();
			}
			else if (action.equals(PushNotificationTypeEnum.RankUpdated.toString())){
				int newRank = intent.getIntExtra(IntentExtraKeysEnum.Rank.toString(), 0);	
				rankUpdated(newRank);
			}
			else if (action.equals(PushNotificationTypeEnum.TrophiesUpdated.toString())){
				int numberOfTropies = intent.getIntExtra(IntentExtraKeysEnum.NumberOfTrophies.toString(), 0);
				String trophiesString = intent.getStringExtra(IntentExtraKeysEnum.Trophies.toString());
				trophiesUpdate(numberOfTropies, trophiesString);
			}
		}

	}

	@Override
	protected void onDestroy (){
		super.onDestroy();
		locationManagment.stopUpdateLocation();
		refreshTimer.cancel();
	}
	@Override
	protected void onResume(){
		super.onResume();
		AvailablePreferences.setAvailableStatus(getApplicationContext(), true);
		if (needRefresh)
			sendMessageToHandler(UPDATE_LIST_ADAPTER);		
	}
	
	protected void onPause(){
		super.onPause();
		AvailablePreferences.setAvailableStatus(getApplicationContext(), false);
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentRequestCodesEnum.GetChosenGame.getCode()){
			gameType = data.getStringExtra(IntentExtraKeysEnum.GameType.toString());
			Log.d("Tammy",gameType);
			sendInvitationResponse("Accept" + gameType, "");
		}
	}

	private void doQuitActions() {
		WelcomeScreenActivity.facebookLogout(null);
		finish();
	}

	private void getUsers(GetUsersByTypeEnum usersType){

		previousClickPosition = currentUsersType;
		currentUsersType = usersType;

		switch(usersType){
		//case AllUsers://tammy
		case NearbyUsers:
			new GetUsersTask().execute(usersType.toString(), authToken);
			break;
		case FacebookFriends:
			getFriendsUsers();
			break;
			//tammy - add case of merged list
		case MergedUsers:
			getMergedUsers();
			break;
		}
	}
	//tammy - exactly like the following method
	private void getMergedUsers(){ 

		if (!serverHasFacebookFriends){
			if (AvailablePreferences.userIsAvailable(getApplicationContext()))
				mainProgressDialog = ProgressDialog.show(this, null, "Retrieving users...", true, false);
			asyncRunner.request("me/friends", new FriendsRequestListener());
		}
		else{
			new GetUsersTask().execute(GetUsersByTypeEnum.MergedUsers.toString(), authToken);
		}
	}

	private void getFriendsUsers(){ 

		if (!serverHasFacebookFriends){
			if (AvailablePreferences.userIsAvailable(getApplicationContext()))
				mainProgressDialog = ProgressDialog.show(this, null, "Retrieving users...", true, false);
			asyncRunner.request("me/friends", new FriendsRequestListener());
		}
		else{
			new GetUsersTask().execute(GetUsersByTypeEnum.FacebookFriends.toString(), authToken);
		}
	}

	private void inviteUserToMoishDialog(){

		showDialog(DIALOG_INVITE_USER_TO_MOISHD);
	}
	
	private void GetGameOfferDialog(){

		showDialog(DIALOG_GET_GAME_OFFER);
	}

	private void inviteUserToMoish(ClientMoishdUser user){

		game_id = ServerCommunication.inviteUser(user.getUserGoogleIdentifier(), authToken);

	}
	
	private void inviteUserOfferedByServerToMoish(String authTokenOfOpponent){

		game_id = ServerCommunication.inviteUser(authTokenOfOpponent, authToken);

	}
	

	private void retrieveInvitation(){

		ClientMoishdUser user = ServerCommunication.retrieveInvitation(game_id, authToken);
		if (user != null){
			Bundle bundle = new Bundle();
			Log.d("Tammy",user.getUserNick());
			bundle.putString("userName", user.getUserNick());
			showDialog(DIALOG_RETRIEVE_USER_INVITATION, bundle);
		}
		else{

		}
	}

	private boolean sendInvitationResponse(String response, String isPopular){

		return ServerCommunication.sendInvitationResponse(game_id, response, authToken, isPopular);
	}

	private void userIsBusy(String invitedUser){

		Bundle bundle = new Bundle();
		bundle.putString("userName", invitedUser);
		showDialog(DIALOG_USER_IS_BUSY, bundle);
	}

	private void userIsOffline(String invitedUser){

		Bundle bundle = new Bundle();
		bundle.putString("userName", invitedUser);
		showDialog(DIALOG_USER_IS_OFFLINE, bundle);
	}


	private void userDeclinedToMoishDialog(){
		showDialog(DIALOG_USER_DECLINED);

	}

	/* Common commands for both truth and dare games.
	 * Preparing the intent, adding all necessary details into it.
	 */
	private void commonForTruthAndDare(Intent intent){
		intent.putExtra(IntentExtraKeysEnum.PushGameId.toString(), game_id);
		intent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
		intent.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);
		startActivity(intent);
	}

	private void startGameDare(){
		Intent intent;
		if (gameType.equals(IntentExtraKeysEnum.DareSimonPro.toString()))
			intent = new Intent(this, SimonProGameActivity.class);
		else if (gameType.equals(IntentExtraKeysEnum.DareMixing.toString()))
			intent = new Intent(this, MixingGameActivity.class);
		else //TODO right now else case is fast click.
			intent = new Intent(this, FastClickGameActivity.class);
		commonForTruthAndDare(intent);
	}

	private void startGameTruth(){
		Intent intent = new Intent(this, TruthPartGameActivity.class);
		commonForTruthAndDare(intent);
	}
	
	private void StartGamePopular(){
		Intent intent = new Intent(this, MostPopularGameActivity.class);
		commonForTruthAndDare(intent);
		
	}

	private void hasNoLocationDialog(){
		showDialog(DIALOG_HAS_NO_LOCATION);
		currentUsersType = previousClickPosition;
	}

	private void rankUpdated(int newRank) {
		Bundle bundle = new Bundle();
		bundle.putInt("Rank", newRank);
		showDialog(DIALOG_RANK_UPDATED, bundle);		
	}

	private void trophiesUpdate(int numberOfTropies, String trophiesString) {

		String[] trophiesList = trophiesString.split("#");
		assert(numberOfTropies == trophiesList.length);

	}

	private void openLocationSettings(){
		Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
	}

	private void errorRetrievingUsersDialog(){

		mainProgressDialog.dismiss();
		showDialog(DIALOG_ERROR_RETRIEVING_USERS);
	}

	private String getGoogleAuthToken() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.GoogleSharedPreferences.toString(),Context.MODE_PRIVATE);
		String authString = prefs.getString(SharedPreferencesKeysEnum.GoogleAuthToken.toString(), null);

		return authString;
	}

	private String getFacebookUserName(boolean fullName) {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(SharedPreferencesKeysEnum.FacebookDetails.toString(),Context.MODE_PRIVATE);

		if (fullName){
			return prefs.getString(SharedPreferencesKeysEnum.FacebookUserName.toString(), null);
		}
		else{
			return prefs.getString(SharedPreferencesKeysEnum.FacebookFirstName.toString(), null);
		}
	}


	private void updateList() {
		switch(currentUsersType){
		case MergedUsers:
		case AllUsers:
			header.setText("All online users");
			break;
		case NearbyUsers:
			header.setText("Nearby users");
			break;
		case FacebookFriends:
			header.setText("Online Facebook friends");
			break;
		}
		header.setTypeface(fontHeader); //tammy
		EfficientAdapter listAdapter = (EfficientAdapter) list.getAdapter();
		listAdapter.notifyDataSetChanged();
	}

	private void facebookFriendsFirstRetrievalCompleted() { 
		mainProgressDialog.dismiss();
		serverHasFacebookFriends = true;
		if (currentUsersType.equals(GetUsersByTypeEnum.FacebookFriends)) //tammy
			new GetUsersTask().execute(GetUsersByTypeEnum.FacebookFriends.toString(), authToken, friendsID);
		else if (currentUsersType.equals(GetUsersByTypeEnum.MergedUsers)) //tammy
			new GetUsersTask().execute(GetUsersByTypeEnum.MergedUsers.toString(), authToken, friendsID);			
	}

	private void sendMessageToHandler(final int messageType) {
		Message registrationErrorMessage = Message.obtain();
		registrationErrorMessage.setTarget(mHandler);
		registrationErrorMessage.what = messageType;
		registrationErrorMessage.sendToTarget();
	}

	protected void postOnFacebookWall(int code, Bundle bundle) {
		Bundle parameters = new Bundle();
		String message;

		switch(code){
		case FACEBOOK_POST_RANK_UPDATED:
			message = firstName +"'s Moish'd! rank has just been updated!";
			parameters.putString("description", message);
			parameters.putString("name", "Moish'd! rank updated");

		}	
		parameters.putString("link", "http://www.facebook.com/apps/application.php?id=108614622540129");
		parameters.putString("picture", "http://moishd.googlecode.com/files/moishd");
		WelcomeScreenActivity.facebook.dialog(this,"stream.publish", parameters, new PostDialogListener());

	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch (id) {

		case DIALOG_INVITE_USER_TO_MOISHD:
			//TODO - check why the same name is applied each time.
			last_user = moishdUsers.get(currentClickPosition).getUserNick();
			Log.d("Tammy", last_user);
			builder.setMessage("You've invited  " + last_user + " to Moish. Continue?")
			.setCancelable(false)
			.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					inviteUserToMoish(moishdUsers.get(currentClickPosition));
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_INVITE_USER_TO_MOISHD);
				}
			});
			return builder.create();  
			
		case DIALOG_GET_GAME_OFFER:
			builder.setMessage("Hey! " + opponent_nick_name + " has a higher rank than you! Would you like to show him/her what you've got?!")
			.setCancelable(false)
			.setPositiveButton("Oh yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_GET_GAME_OFFER);
					inviteUserOfferedByServerToMoish(opponent_auth_token);
				}
			})
			.setNegativeButton("No, thank you", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_GET_GAME_OFFER);
					}
			});
			return builder.create();
			

		case DIALOG_RETRIEVE_USER_INVITATION:
			builder.setMessage("You've been invited by " + args.getString("userName") + " to Moish.")
			.setCancelable(false)
			.setPositiveButton("Accept", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {
					Intent chooseGame = new Intent();
					Random random = new Random();  
					int i = random.nextInt(100);
					i = i % 4;
					if (i==0 || i==1) {
						chooseGame.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
						chooseGame.setClass(AllOnlineUsersActivity.this,TruthOrDareActivity.class); 
						startActivityForResult(chooseGame, IntentRequestCodesEnum.GetChosenGame.getCode());
					}
					else if (i==2){
						chooseGame.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
						chooseGame.setClass(AllOnlineUsersActivity.this,ChooseGameActivity.class);
						startActivityForResult(chooseGame, IntentRequestCodesEnum.GetChosenGame.getCode());
					}
					else {
						String mostPopular = ServerCommunication.getMostPopularGame(authToken);
						Log.d("Tammy",mostPopular);
						sendInvitationResponse("Accept" + mostPopular, "Popular");
					}
					dismissAndRemoveDialog(DIALOG_RETRIEVE_USER_INVITATION);		
					}
			})
			.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					sendInvitationResponse("Decline", "");  
					dismissAndRemoveDialog(DIALOG_RETRIEVE_USER_INVITATION);		
					}
			});
			return builder.create();  

		case DIALOG_RANK_UPDATED:
			final int newRank = args.getInt("Rank");
			builder.setMessage("Congratulationsm, your rank has been updated! Your new rank is " + newRank)
			.setCancelable(false)
			.setPositiveButton("Share", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {
					Bundle bundle = new Bundle();
					bundle.putInt("rank", newRank);
					postOnFacebookWall(FACEBOOK_POST_RANK_UPDATED, bundle);
				}
			})
			.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_RANK_UPDATED);		
				}
			});
			return builder.create(); 

		case DIALOG_TROPHIES_UPDATED:
			String [] trophiesList = args.getStringArray("TrophiesList");
			builder.setMessage("Congratulationsm, you've earned the following trophies: " + trophiesList)
			.setCancelable(false)
			.setPositiveButton("Share", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {
					//					Bundle bundle = new Bundle();
					//					bundle.putInt("rank", newRank);
					//					postOnFacebookWall(FACEBOOK_POST_RANK_UPDATED, bundle);
					dismissAndRemoveDialog(DIALOG_TROPHIES_UPDATED);		
				}
			})
			.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_TROPHIES_UPDATED);		
				}
			});
			return builder.create(); 
			
		case DIALOG_USER_IS_BUSY:
			builder.setMessage(args.getString("userName") + " is currently playing. Please try again later.")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_USER_IS_BUSY);		
				}
			});
			return builder.create(); 

		case DIALOG_USER_IS_OFFLINE:
			builder.setMessage(args.getString("userName") + " is offline. Please refresh your list.")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_USER_IS_OFFLINE);		
				}
			});
			return builder.create();  

		case DIALOG_USER_DECLINED:
			builder.setMessage("Your invitation has been declined")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			return builder.create();  

		case DIALOG_HAS_NO_LOCATION:
			builder.setMessage("Location hasn't been settled yet. Would you like to configure Location Settings?")
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					sendMessageToHandler(START_LOCATION_SETTINGS);
					dialog.cancel();
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			return builder.create();  

		case DIALOG_ERROR_RETRIEVING_USERS:
			builder.setMessage("Error retrieving users from server.")
			.setCancelable(false)
			.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					getUsers(currentUsersType);
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();		
					currentUsersType = previousClickPosition;
				}
			});
			return builder.create(); 

		case DIALOG_HAS_NO_LOCATION_BEGINNING:
			builder.setMessage("Location hasn't been settled yet. Hence, all users will appear as not in your range.")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			return builder.create();  

		default:
			return null;
		}
	}
	
	private void dismissAndRemoveDialog(int code){
		dismissDialog(code);
		removeDialog(code);	
	}

	private class GetUsersTask extends AsyncTask<Object, Integer, List<Object>> {

		protected void onPreExecute() {
			if (AvailablePreferences.userIsAvailable(getApplicationContext()))
				mainProgressDialog = ProgressDialog.show(AllOnlineUsersActivity.this, null, "Retrieving users...", true, false);
		}

		@SuppressWarnings("unchecked")
		protected List<Object> doInBackground(Object... objects) {

			List <Object> resultList = new ArrayList<Object>();
			List<ClientMoishdUser> moishdUsers = new ArrayList<ClientMoishdUser>();	
			List<Drawable> usersPictures = new ArrayList<Drawable>();

			String usersType = (String) objects[0];
			String authToken = (String) objects[1];

			/*if (usersType.equals(GetUsersByTypeEnum.AllUsers.toString())){
				moishdUsers = ServerCommunication.getAllUsers(authToken);

			}*/ 
			// tammy - merged list has got to have also the facebook friends' list
			if (usersType.equals(GetUsersByTypeEnum.MergedUsers.toString())){
				List<String> friendsID;
				if (objects.length == 3){
					friendsID = (List<String>) objects[2];
				}
				else{
					friendsID = new ArrayList<String>();
				}

				moishdUsers = ServerCommunication.getMergedUsers(friendsID, authToken);
			}

			else if (usersType.equals(GetUsersByTypeEnum.NearbyUsers.toString())){
				if (ServerCommunication.hasLocation(authToken) == true) {

					moishdUsers = ServerCommunication.getNearbyUsers(authToken);
				}
				else{
					resultList.add(HAS_NO_LOCATION_DIALOG);
					return resultList;
				}
			}
			else if (usersType.equals(GetUsersByTypeEnum.FacebookFriends.toString())){
				List<String> friendsID;
				if (objects.length == 3){
					friendsID = (List<String>) objects[2];
				}
				else{
					friendsID = new ArrayList<String>();
				}

				moishdUsers = ServerCommunication.getFacebookFriends(friendsID, authToken);
			}

			if (moishdUsers == null){
				resultList.add(ERROR_RETRIEVING_USERS_DIALOG);
				return resultList;
			}
			else{
				Collections.sort(moishdUsers);

				for (int i=0; i < moishdUsers.size(); i++){
					Drawable userPic = LoadImageFromWebOperations(moishdUsers.get(i).getPictureLink());
					usersPictures.add(userPic);
					/*boolean friend = moishdUsers.get(i).isFacebookFriend();
					String isfriend="";
					if (friend)
						isfriend="True";
					else
						isfriend="False"

					Log.d("AllOnlineUsersActivity", isfriend);*/
					setProgress((int) ((i / (float) moishdUsers.size()) * 100));

				}

				resultList.add(moishdUsers);
				resultList.add(usersPictures);
				return resultList;
			}
		}

		protected void onProgressUpdate(Integer... progress) {
			onProgressUpdate(progress[0]);
		}

		@SuppressWarnings("unchecked")
		protected void onPostExecute(List<Object> resultList) {
			if (resultList.size() == 2){
				moishdUsers = (List<ClientMoishdUser>) resultList.get(0);
				usersPictures = (List<Drawable>) resultList.get(1);
				
				if (AvailablePreferences.userIsAvailable(getApplicationContext()))
					sendMessageToHandler(UPDATE_LIST_ADAPTER);
				else 
					needRefresh = true;
			}
			else{
				if (!AvailablePreferences.userIsAvailable(getApplicationContext())){
					Integer messageCode = (Integer) resultList.get(0);
					final int messageCodeInt = messageCode.intValue();
					sendMessageToHandler(messageCodeInt);
				}
			}
			mainProgressDialog.dismiss();
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
	}

	private class FriendsRequestListener extends BaseRequestListener {

		public void onComplete(final String response) {
			try {
				JSONObject json = Util.parseJson(response);
				JSONArray friends = json.getJSONArray("data");
				friendsID = new ArrayList<String>();
				for (int i=0; i < friends.length(); i++){
					friendsID.add(((JSONObject) friends.get(i)).getString("id"));
				}
				sendMessageToHandler(FACEBOOK_FRIENDS_FIRST_RETRIEVAL_COMPLETED);

			} catch (JSONException e) {
				Log.w("Moishd-JsonExeption", "JSON Error in response");
				sendMessageToHandler(ERROR_RETRIEVING_USERS_DIALOG);
			} catch (FacebookError e) {
				Log.w("Moishd-FacebookError", "Facebook Error: " + e.getMessage());
				sendMessageToHandler(ERROR_RETRIEVING_USERS_DIALOG);
			}
		}

	}

	private class PostDialogListener extends BaseDialogListener {

		public void onComplete(Bundle values) {

			final String postId = values.getString("post_id");

			if (postId != null) {

				// "Wall post made..."

			} else {
				// "No wall post made..."
			}

		}

		public void onFacebookError(FacebookError e) {}
		public void onCancel() {}
		public void onError(DialogError e) {}

	}

	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		private Bitmap userRank0;
		private Bitmap userRank1;
		private Bitmap userRank2;
		private Bitmap userRank3;
		private Bitmap userRank4;
		private Bitmap userRank5;

		private Bitmap facebookPic;
		private Bitmap noPic;
		private Bitmap nearByUsers;
		private Bitmap noNearBy;


		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);

			// Icons bound to the rows.
			facebookPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.facebook);
			nearByUsers = BitmapFactory.decodeResource(context.getResources(), R.drawable.world);
			noPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_facebook_friend);
			noNearBy = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_world);
			userRank0 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_0);
			userRank1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_1);
			userRank2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_2);
			userRank3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_3);
			userRank4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_4);
			userRank5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_5);		
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

		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.all_users_list_item, null);

				holder = new ViewHolder();
				holder.userName = (TextView) convertView.findViewById(R.id.text);
				holder.userPicture = (ImageView) convertView.findViewById(R.id.userPicture);
				holder.userRank = (ImageView) convertView.findViewById(R.id.userRank);
				holder.nearBy = (ImageView) convertView.findViewById(R.id.nearByUsers);
				holder.facebookPic = (ImageView) convertView.findViewById(R.id.facebookPic);

				convertView.setTag(holder);
			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.userName.setText(moishdUsers.get(position).getUserNick());	
			holder.userName.setTypeface(fontName);
			holder.userName.setTextSize(17);

			switch(moishdUsers.get(position).getStats().getRank()){
			case 0:
				holder.userRank.setImageBitmap(userRank0);
				break;
			case 1:
				holder.userRank.setImageBitmap(userRank1);
				break;
			case 2:
				holder.userRank.setImageBitmap(userRank2);
				break;
			case 3:
				holder.userRank.setImageBitmap(userRank3);
				break;
			case 4:
				holder.userRank.setImageBitmap(userRank4);
				break;
			case 5:
				holder.userRank.setImageBitmap(userRank5);
				break;
			}

			holder.userRank.setTag(position);
			holder.userRank.setClickable(true);
			holder.userRank.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					int position = (Integer) view.getTag();
					Intent intent = new Intent(view.getContext(), UserStatisticsActivity.class);
					intent.putExtra(IntentExtraKeysEnum.MoishdUser.toString(), moishdUsers.get(position));
					view.getContext().startActivity(intent);
				}
			});
			holder.userPicture.setImageDrawable(usersPictures.get(position));

			//tammy

			/*	boolean friend = moishdUsers.get(position).isFacebookFriend();
			String isfriend="";
			if (friend)
				isfriend="True moment before show";
			else
				isfriend="False moment before show";

			Log.d("AllOnlineUsersActivity", isfriend);*/

			if (currentUsersType.equals(GetUsersByTypeEnum.FacebookFriends))
				holder.facebookPic.setImageBitmap(facebookPic);

			else if (currentUsersType.equals(GetUsersByTypeEnum.MergedUsers)){
				if (moishdUsers.get(position).isFacebookFriend()){
					holder.facebookPic.setImageBitmap(facebookPic);
				}
				else
					holder.facebookPic.setImageBitmap(noPic);

				if (moishdUsers.get(position).isNearByUser())			
					holder.nearBy.setImageBitmap(nearByUsers);
				else
					holder.nearBy.setImageBitmap(noNearBy);
			}

			else if (currentUsersType.equals(GetUsersByTypeEnum.NearbyUsers))
				holder.nearBy.setImageBitmap(nearByUsers);


			return convertView;
		}

		static class ViewHolder {
			TextView userName;
			ImageView userPicture;
			ImageView userRank;
			ImageView nearBy;
			ImageView facebookPic;
		}
	}
}
