package moishd.android;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import moishd.android.facebook.AsyncFacebookRunner;
import moishd.android.facebook.BaseDialogListener;
import moishd.android.facebook.BaseRequestListener;
import moishd.android.facebook.DialogError;
import moishd.android.facebook.FacebookError;
import moishd.android.facebook.Util;
import moishd.android.games.ChooseGameActivity;
import moishd.android.games.FastClickGameActivity;
import moishd.android.games.MixingGameActivity;
import moishd.android.games.MostPopularGameActivity;
import moishd.android.games.SimonProGameActivity;
import moishd.android.games.TruthOrDareActivity;
import moishd.android.games.TruthPartGameActivity;
import moishd.client.dataObjects.ClientMoishdUser;
import moishd.client.dataObjects.TrophiesEnum;
import moishd.common.GetUsersByTypeEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentRequestCodesEnum;
import moishd.common.IntentResultCodesEnum;
import moishd.common.LocationManagment;
import moishd.common.MoishdPreferences;
import moishd.common.PushNotificationTypeEnum;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class AllOnlineUsersActivity extends Activity{

	private static List<ClientMoishdUser> moishdUsers = new ArrayList<ClientMoishdUser>();
	private static List<Drawable> usersPictures = new ArrayList<Drawable>();
	private static List<String> friendsID;
	private static GetUsersByTypeEnum currentUsersType;
	private GetUsersByTypeEnum previousUserType;

	private static Typeface fontName, fontHeader;

	private String firstName;
	private String authToken;

	private String game_id;
	private String gameType;
	private String opponent_google_id;
	private String opponent_nick_name;
	private String initName;
	private String recName;
	private String myUserName;
	private boolean serverHasFacebookFriends;
	private int newRank;
	private String trophiesList;

	private int currentClickPosition;
	private MoishdPreferences moishdPreferences ;
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
	private final int DIALOG_RANK_AND_TROPHIES_UPDATED = 20;
	private final int DIALOG_GET_GAME_OFFER = 21;
	private final int DIALOG_USER_CANCELED_GAME = 22;
	private final int FACEBOOK_POST_RANK_UPDATED = 30;
	private final int FACEBOOK_POST_TROPHIES_UPDATED = 31;
	private final int FACEBOOK_POST_RANK_AND_TROPHIES_UPDATED = 32;
	private final int DIALOG_SERVER_ERROR = 33;
	
	private final int MY_QUIT_NOT_SET = 0;
	private final int MY_QUIT_QUIT = 1;
	private final int MY_QUIT_NOT_QUIT = 2;
	private int myQuit = MY_QUIT_NOT_SET;

	
	private waitForResponse timerForResponse;
	private boolean timerOn = false;

	private boolean needRefresh = false;
	Timer refreshTimer;
	int MINUTE = 1000*60;
	int REFRESH_INTERVAL = 5*MINUTE;
	
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
			case DIALOG_SERVER_ERROR:
				serverErrorDialog();
				break;
			}
		}
	};

	@Override
	protected void onSaveInstanceState (Bundle outState){
		outState.putInt("MY_QUIT", myQuit);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.all_users_layout);

		moishdPreferences = MoishdPreferences.getMoishdPreferences();
		fontName = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf"); 
		fontHeader = Typeface.createFromAsset(getAssets(), "fonts/BROADW.ttf"); 

		header = (TextView) findViewById(R.id.usersTypeHeader);
		header.setText("All online users");
		header.setTextSize(20);
		header.setTypeface(fontHeader);

		//need the authToken for server requests
		firstName = getFacebookUserName(false);

		asyncRunner = new AsyncFacebookRunner(WelcomeScreenActivity.facebook);

		String authToken = moishdPreferences.getCurrentGoogleAuthToken();
		locationManagment = LocationManagment.getLocationManagment(getApplicationContext(),authToken);
		locationManagment.startUpdateLocation(1);

		myUserName = moishdPreferences.getUserName();

		serverHasFacebookFriends = false;
		
		if (savedInstanceState==null){
			moishdPreferences.setAvailableStatus(true);
		} else {
			myQuit = savedInstanceState.getInt("MY_QUIT");
			if (myQuit==MY_QUIT_QUIT){
				moishdPreferences.setAvailableStatus(false);
			}
		}
		currentUsersType = GetUsersByTypeEnum.MergedUsers;
		getUsers(GetUsersByTypeEnum.MergedUsers);

		list = (ListView) findViewById(R.id.allUsersListView);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				currentClickPosition = arg2;
				inviteUserToMoishDialog();
			}});


		list.setAdapter(new EfficientAdapter(this));

		int hasLocationStatus = ServerCommunication.hasLocation(authToken);
		if (hasLocationStatus == ServerCommunication.HAS_LOCATION_FALSE){
			showDialog(DIALOG_HAS_NO_LOCATION_BEGINNING);
		} 
		else if (hasLocationStatus == ServerCommunication.SERVER_ERROR){
			sendMessageToHandler(DIALOG_SERVER_ERROR);
		}
		
		activateRefreshTimer();
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
			executeRefresh();
			restartTimer();
			return true;
		case R.id.logout:
			doQuitActions(true);
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
		case R.id.statistics:
			displayOwnStatistics();
			return true;
		case R.id.topFivePopular:
			displayTopPopularGames();
			return true;
		case R.id.topMoishers:
			displayTopMoishers();
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
	
	public void turnOffTimer(){
		if (timerOn){
			timerForResponse.cancel();
			timerOn=false;
		}
	}

	@Override
	protected void onNewIntent (Intent intent){
		game_id = intent.getStringExtra(IntentExtraKeysEnum.PushGameId.toString());	
		String action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());
		gameType = intent.getStringExtra(IntentExtraKeysEnum.GameType.toString());

		if (action!=null){
			if (action.equals(PushNotificationTypeEnum.Disconnect.toString())){
				doQuitActions(false);
				}
			else if (action.equals(PushNotificationTypeEnum.GameInvitation.toString())){
				String inviterName = intent.getStringExtra("Inviter");
				retrieveInvitation(inviterName);
			}
			else if (action.equals(PushNotificationTypeEnum.GameDeclined.toString())){
				Log.d("Amico","cancel timer on gameDeclined");
				turnOffTimer();
				userDeclinedToMoishDialog();
				game_id = null;
			}

			else if (action.equals(PushNotificationTypeEnum.GameCanceled.toString())){
				turnOffTimer();
				initName = intent.getStringExtra(IntentExtraKeysEnum.InitName.toString());
				recName = intent.getStringExtra(IntentExtraKeysEnum.RecName.toString());
				userCanceledGameDialog();
				game_id = null;
			}

			else if (action.equals(PushNotificationTypeEnum.PlayerBusy.toString())){
				turnOffTimer();
				opponent_nick_name = intent.getStringExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString());
				userIsBusy(opponent_nick_name);
				game_id = null;
				opponent_nick_name=null;	
			}
			else if (action.equals(PushNotificationTypeEnum.PlayerOffline.toString())){
				turnOffTimer();
				opponent_nick_name = intent.getStringExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString());
				userIsOffline(opponent_nick_name);
				game_id = null;
				opponent_nick_name=null;
			}

			else if (action.equals(PushNotificationTypeEnum.PopularGame.toString())){
				turnOffTimer();
				StartGamePopular();
			}
			else if (action.equals(PushNotificationTypeEnum.StartGameTruth.toString())){
				turnOffTimer();
				startGameTruth();	
			}
			else if (action.equals(PushNotificationTypeEnum.StartGameDare.toString())) {
				turnOffTimer();
				startGameDare();
			}
			else if(action.equals(PushNotificationTypeEnum.GameOffer.toString())){
				opponent_google_id =  intent.getStringExtra(IntentExtraKeysEnum.GoogleIdOfOpponent.toString());
				Log.d("Tammy", "allonline" + opponent_google_id);
				opponent_nick_name =  intent.getStringExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString());
				Log.d("Tammy", "allonline" + opponent_nick_name);
				GetGameOfferDialog();
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
		moishdPreferences.setAvailableStatus(true);
		if (needRefresh)
			sendMessageToHandler(UPDATE_LIST_ADAPTER);		
	}

	protected void onPause(){

		myQuit = MY_QUIT_NOT_QUIT;
		super.onPause();
		moishdPreferences.setAvailableStatus(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode ==IntentResultCodesEnum.Failed.getCode()){
			sendMessageToHandler(DIALOG_SERVER_ERROR);
		}
		
		else if (requestCode == IntentRequestCodesEnum.GetChosenGame.getCode()){
			gameType = data.getStringExtra(IntentExtraKeysEnum.GameType.toString());
			sendInvitationResponse("Accept" + gameType, "");
		}
		else if (requestCode == IntentRequestCodesEnum.GameRequestCode.getCode()){
			int newRank = data.getIntExtra(IntentExtraKeysEnum.Rank.toString(), -1);
			int numOfTrophies = data.getIntExtra(IntentExtraKeysEnum.NumberOfTrophies.toString(), -1);

			String trophiesString;
			if (numOfTrophies != -1){
				trophiesString = data.getStringExtra(IntentExtraKeysEnum.Trophies.toString());
			}
			else{
				trophiesString = null;
			}

			if (newRank != -1 && numOfTrophies == 0){
				rankUpdated(newRank);
			}
			else if (newRank == -1 && numOfTrophies != 0){
				trophiesUpdated(numOfTrophies, trophiesString);
			}
			else if (newRank != -1 && numOfTrophies != -1){
				rankAndTrophiesUpdated(newRank, numOfTrophies, trophiesString);
			}
		}
		else if (requestCode == IntentRequestCodesEnum.StartPopularGame.getCode()){
			Intent intent = new Intent();
			if (gameType.equals(IntentExtraKeysEnum.Truth.toString())){
				intent.setClass(this, TruthPartGameActivity.class);
			}
			else{
				setGameDare(intent);
			}
			commonForTruthAndDare(intent);
		}
	}
	
	private void executeRefresh(){
		if (currentUsersType.equals(GetUsersByTypeEnum.FacebookFriends)){
			getFriendsUsers();
		}
		else if (currentUsersType.equals(GetUsersByTypeEnum.MergedUsers)){
			getMergedUsers();
		}
		else{
			getUsers(currentUsersType);	
		}
	}

	private void activateRefreshTimer(){
		refreshTimer = new Timer();
		refreshTimer.schedule(new autoRefreshTask(), 60*1000, REFRESH_INTERVAL);
	}

	private void restartTimer(){
		refreshTimer.cancel();
		activateRefreshTimer();
	}
	
	private void doQuitActions(boolean unregisterC2dm) {
		if (!unregisterC2dm){
			WelcomeScreenActivity.unregisterC2DM = false;
		}
		WelcomeScreenActivity.facebookLogout(null);
		myQuit = MY_QUIT_QUIT;
		refreshTimer.cancel();
		
		finish();
	}

	private void getUsers(GetUsersByTypeEnum usersType){

		previousUserType = currentUsersType;
		currentUsersType = usersType;

		switch(usersType){
		case NearbyUsers:
			new GetUsersTask().execute(usersType.toString(), authToken);
			break;
		case FacebookFriends:
			getFriendsUsers();
			break;
		case MergedUsers:
			getMergedUsers();
			break;
		}
	}
	//tammy - exactly like the following method
	private void getMergedUsers(){ 

		if (!serverHasFacebookFriends){
			commonForFriendsUsersAndMergedUsers();
		}
		else{
			new GetUsersTask().execute(GetUsersByTypeEnum.MergedUsers.toString(), authToken);
		}
	}

	private void getFriendsUsers(){ 

		if (!serverHasFacebookFriends){
			commonForFriendsUsersAndMergedUsers();
		}
		else{
			new GetUsersTask().execute(GetUsersByTypeEnum.FacebookFriends.toString(), authToken);
		}
	}
	
	private void commonForFriendsUsersAndMergedUsers(){
		if (moishdPreferences.userIsAvailable()) {
			mainProgressDialog = ProgressDialog.show(this, null, "Retrieving users...", true, false);
		}
		asyncRunner.request("me/friends", new FriendsRequestListener());
	
	}

	private void displayOwnStatistics() {

		ClientMoishdUser me = ServerCommunication.getCurrentUser(authToken);
		if (me == null){
			sendMessageToHandler(DIALOG_SERVER_ERROR);
		}else {
			Intent intent = new Intent(this, UserStatisticsActivity.class);
			intent.putExtra(IntentExtraKeysEnum.MoishdUser.toString(), me);
			startActivity(intent);
		}
	}
	
	private void displayTopPopularGames() {
		Intent intent = new Intent(this, TopPopularActivity.class);
		intent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
		startActivity(intent);
	}
	
	private void displayTopMoishers(){
		Intent intent = new Intent(this, TopMoisherGeneralActivity.class);
		intent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
		startActivity(intent);
	}

	private void inviteUserToMoishDialog(){
		Bundle args = new Bundle();
		args.putString("UserInviting", moishdUsers.get(currentClickPosition).getUserNick());
		showDialog(DIALOG_INVITE_USER_TO_MOISHD, args);
	}

	private void GetGameOfferDialog(){

		showDialog(DIALOG_GET_GAME_OFFER);
	}

	private void inviteUserToMoish(ClientMoishdUser user){
		boolean inviteSucceeded = ServerCommunication.inviteUser(user.getUserGoogleIdentifier(), authToken);
		if (!inviteSucceeded){
			sendMessageToHandler(DIALOG_SERVER_ERROR);
		} /*else{
			game_id = returnedGameId;
		}*/
	}

	private void inviteUserOfferedByServerToMoish(String authTokenOfOpponent){
		boolean inviteSucceeded = ServerCommunication.inviteUser(authTokenOfOpponent, authToken);
		if (!inviteSucceeded){
			sendMessageToHandler(DIALOG_SERVER_ERROR);
		}/* else{
			game_id = returnedGameId;
		}*/
	}

	private void retrieveInvitation(String inviterName){
		Bundle bundle = new Bundle();
		Log.d("Tammy",inviterName);
		bundle.putString("userName", inviterName);
		showDialog(DIALOG_RETRIEVE_USER_INVITATION, bundle);
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
	private void userCanceledGameDialog(){
		showDialog(DIALOG_USER_CANCELED_GAME);
	}
	/* Common commands for both truth and dare games.
	 * Preparing the intent, adding all necessary details into it.
	 */
	private void commonForTruthAndDare(Intent intent){
		intent.putExtra(IntentExtraKeysEnum.PushGameId.toString(), game_id);
		intent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
		intent.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);
		startActivityForResult(intent, IntentRequestCodesEnum.GameRequestCode.getCode());
	}

	private void startGameDare(){
		Intent intent = new Intent();
		setGameDare(intent);
		commonForTruthAndDare(intent);
	}

	private void startGameTruth(){
		Intent intent = new Intent(this, TruthPartGameActivity.class);
		commonForTruthAndDare(intent);
	}

	private void setGameDare(Intent intent){
		if (gameType.equals(IntentExtraKeysEnum.DareSimonPro.toString()))
			intent.setClass(this, SimonProGameActivity.class);
		else if (gameType.equals(IntentExtraKeysEnum.DareMixing.toString()))
			intent.setClass(this, MixingGameActivity.class);
		else //TODO right now else case is fast click.
			intent.setClass(this, FastClickGameActivity.class);
	}

	private void StartGamePopular(){
		Intent popularScreen = new Intent(AllOnlineUsersActivity.this, MostPopularGameActivity.class);
		startActivityForResult(popularScreen, IntentRequestCodesEnum.StartPopularGame.getCode());
	}

	private void hasNoLocationDialog(){
		showDialog(DIALOG_HAS_NO_LOCATION);
		currentUsersType = previousUserType;
	}

	private void rankUpdated(int newRank) {
		Bundle bundle = new Bundle();
		bundle.putInt("rank", newRank);
		showDialog(DIALOG_RANK_UPDATED, bundle);		
	}

	private void trophiesUpdated(int numberOfTrophies, String trophiesString) {

		String[] trophiesList = trophiesString.split("#");
		assert(numberOfTrophies == trophiesList.length);

		Bundle bundle = new Bundle();
		bundle.putStringArray("trophiesList", trophiesList);

		showDialog(DIALOG_TROPHIES_UPDATED, bundle);		
	}

	private void rankAndTrophiesUpdated(int newRank, int numOfTrophies,String trophiesString){
		
		String[] trophiesList = trophiesString.split("#");
		assert(numOfTrophies == trophiesList.length);

		Bundle bundle = new Bundle();
		bundle.putStringArray("trophiesList", trophiesList);
		bundle.putInt("rank", newRank);
		showDialog(DIALOG_RANK_AND_TROPHIES_UPDATED, bundle);
	}

	private void openLocationSettings(){
		Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
	}

	private void errorRetrievingUsersDialog(){

		mainProgressDialog.dismiss();
		showDialog(DIALOG_ERROR_RETRIEVING_USERS);
	}
	
	private void serverErrorDialog(){
		mainProgressDialog.dismiss();
		showDialog(DIALOG_SERVER_ERROR);
	}

	private String getFacebookUserName(boolean fullName) {
		if (fullName){
			return moishdPreferences.getFacebookUserName();
		}
		else{
			return moishdPreferences.getFacebookFirstName();
		}
	}

	private void updateList() {
		switch(currentUsersType){
		case MergedUsers:
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
		
		moishdPreferences.setAvailableStatus(false);
		Bundle parameters = new Bundle();
		String message;
 
		switch(code){
		case FACEBOOK_POST_RANK_UPDATED:
			message = firstName +"'s Moish'd! rank has just been updated!";
			parameters.putString("description", message);
			parameters.putString("name", "Moish'd! rank updated");

		case FACEBOOK_POST_TROPHIES_UPDATED:
			String trophiesList = bundle.getString("trophiesList");
			Log.d("Tammy", "FACEBOOK_POST_TROPHIES_UPDATED " + trophiesList);
			message = firstName +"'s Moish'd! trophies have just been updated! New trophies receieved:\n" + trophiesList;
			parameters.putString("description", message);
			parameters.putString("name", "Moish'd! trophies have been updated!");
			
		case FACEBOOK_POST_RANK_AND_TROPHIES_UPDATED:
			String trophies = bundle.getString("trophiesList");
			Log.d("Tammy", "FACEBOOK_POST_RANK_AND_TROPHIES_UPDATED " + trophies);
			message = firstName +"'s Moish'd! rank and trophies has just been updated! \n" +
			"New trophies receieved:\n" + trophies;
			parameters.putString("description", message);
			parameters.putString("name", "Moish'd! rank and trophies have been updated!");
		}		
		parameters.putString("link", "http://www.facebook.com/apps/application.php?id=108614622540129");
		parameters.putString("picture", "http://moishd.googlecode.com/files/moishd");
		WelcomeScreenActivity.facebook.dialog(this,"stream.publish", parameters, new PostDialogListener());

		moishdPreferences.setAvailableStatus(true);
	}
	
	@Override
	protected void onPrepareDialog (int id, Dialog dialog, Bundle args){
		moishdPreferences.setAvailableStatus(false);
		switch (id){

		case DIALOG_USER_CANCELED_GAME:
			String user;
			Log.d("Tammy", "init name is " + initName);
			Log.d("Tammy", "rec name is " + recName);
			Log.d("Tammy", "my name is " + myUserName);
			if (myUserName.equals(initName)){
				user = recName;
				Log.d("TEST", "BUSY USER IS " + user);
			}
			else{
				user = initName;
				Log.d("TEST", "BUSY USER IS " + user);
			}
			((AlertDialog)dialog).setMessage("The game with " + user + " has been canceled.");
			super.onPrepareDialog(id, dialog, args);
			break;
		default:  
			super.onPrepareDialog(id, dialog, args);
			break;
			
		
		}
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch (id) {

		case DIALOG_SERVER_ERROR:
		//	builder.setTitle("Error");
			builder.setMessage("There was an error connecting to Moish'd! server. please check your Internet connection, and try to login again.")
			.setCancelable(false)
			.setNegativeButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_SERVER_ERROR, true);
					doQuitActions(true);
				}
			});
			return builder.create(); 
		
		case DIALOG_INVITE_USER_TO_MOISHD:
			//TODO - check why the same name is applied each time.
			String last_user = args.getString("UserInviting");
			
			builder.setMessage("You've invited " + last_user + " to Moish. Continue?")
			.setCancelable(false)
			.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_INVITE_USER_TO_MOISHD, true);
					inviteUserToMoish(moishdUsers.get(currentClickPosition));
					timerForResponse= new waitForResponse(40000,1000);
					timerForResponse.start();
					timerOn=true;
					
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_INVITE_USER_TO_MOISHD, true);
				}
			});
			return builder.create();  

		case DIALOG_GET_GAME_OFFER:
			builder.setMessage("Hey! " + opponent_nick_name + " has a higher rank than you! Would you like to show him/her what you've got?!")
			.setCancelable(false)
			.setPositiveButton("Oh yes!", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_GET_GAME_OFFER, true);
					inviteUserOfferedByServerToMoish(opponent_google_id);
					opponent_google_id=null;
					opponent_nick_name=null;
				}
			})
			.setNegativeButton("No, thank you", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_GET_GAME_OFFER, true);
					opponent_google_id=null;
					opponent_nick_name=null;
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
					boolean invitationResult = false;
					boolean isBusy = ServerCommunication.IsBusy(authToken);
					
					
					if (isBusy) {
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
							if (mostPopular==null){
								sendMessageToHandler(DIALOG_SERVER_ERROR);
							}
							invitationResult = sendInvitationResponse("Accept" + mostPopular, "Popular");
							if (invitationResult==false){
								sendMessageToHandler(DIALOG_SERVER_ERROR);
							}
						}
					}
					else{
						invitationResult = sendInvitationResponse("AcceptTruth","");
						if (invitationResult==false){
							sendMessageToHandler(DIALOG_SERVER_ERROR);
						}
					}
					dismissAndRemoveDialog(DIALOG_RETRIEVE_USER_INVITATION, true);
					
				}
			})
			.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					sendInvitationResponse("Decline", "");  
					dismissAndRemoveDialog(DIALOG_RETRIEVE_USER_INVITATION, true);
				}
			});
			return builder.create();  

		case DIALOG_RANK_UPDATED:
			newRank = args.getInt("rank");
			builder.setMessage("Congratulations, your rank has been updated! Your new rank is " + newRank)
			.setCancelable(false)
			.setPositiveButton("Share", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {
					Bundle bundle = new Bundle();
					bundle.putInt("rank", newRank);
					dismissAndRemoveDialog(DIALOG_RANK_UPDATED, false);
					postOnFacebookWall(FACEBOOK_POST_RANK_UPDATED, bundle);
				}
			})
			.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_RANK_UPDATED, true);		
				}
			});
			return builder.create(); 

		case DIALOG_RANK_AND_TROPHIES_UPDATED:
			newRank = args.getInt("rank");
			trophiesList = stringArrayToString(args.getStringArray("trophiesList"));
			Log.d("Tammy", "DIALOG_RANK_AND_TROPHIES_UPDATED " + trophiesList);
			builder.setMessage("Congratulations, your rank and trophies have been updated!" +
					"Your new rank is " + newRank + "\n" +
					"You've earned the following trophies: " + trophiesList)
			.setCancelable(false)
			.setPositiveButton("Share", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {
					Bundle bundle = new Bundle();
					bundle.putInt("rank", newRank);
					bundle.putString("trophiesList", trophiesList);
					dismissAndRemoveDialog(DIALOG_RANK_AND_TROPHIES_UPDATED, false);		
					postOnFacebookWall(FACEBOOK_POST_RANK_AND_TROPHIES_UPDATED, bundle);
				}
			})
			.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_TROPHIES_UPDATED, true);		
				}
			});
			return builder.create(); 
			
		case DIALOG_TROPHIES_UPDATED:
			trophiesList = stringArrayToString(args.getStringArray("trophiesList"));
			Log.d("Tammy", "DIALOG_TROPHIES_UPDATED " + trophiesList);
			builder.setMessage("Congratulations, you've earned the following trophies: \n" + trophiesList)
			.setCancelable(false)
			.setPositiveButton("Share", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {
					Bundle bundle = new Bundle();
					bundle.putString("trophiesList", trophiesList);
					dismissAndRemoveDialog(DIALOG_TROPHIES_UPDATED, false);		
					postOnFacebookWall(FACEBOOK_POST_RANK_UPDATED, bundle);
				}
			})
			.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_TROPHIES_UPDATED, true);		
				}
			});
			return builder.create(); 

		case DIALOG_USER_IS_BUSY:
			Log.d("Tammy", "busy");
			builder.setMessage(args.getString("userName") + " is currently playing. Please try again later.")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_USER_IS_BUSY, true);		
				}
			});
			return builder.create(); 

		case DIALOG_USER_IS_OFFLINE:
			Log.d("Tammy", "offline");
			builder.setMessage(args.getString("userName") + " is offline. Please refresh your list.")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_USER_IS_OFFLINE, true);		
				}
			});
			return builder.create();  

		case DIALOG_USER_DECLINED:
			builder.setMessage("Your invitation has been declined")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					cancelDialog(dialog);
				}
			});
			return builder.create();  

		case DIALOG_USER_CANCELED_GAME:
			//TODO cancel game
			String user;
			Log.d("Tammy", "init name is " + initName);
			Log.d("Tammy", "rec name is " + recName);
			Log.d("Tammy", "my name is " + myUserName);
			if (myUserName.equals(initName)){
				user = recName;
			}
			else{
				user = initName;
			}
			builder.setMessage("The game with " + user + " has been canceled.")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissAndRemoveDialog(DIALOG_USER_CANCELED_GAME, true);
					initName=null;
					recName=null;
				}
			});
			return builder.create(); 

		case DIALOG_HAS_NO_LOCATION:
			builder.setMessage("Location hasn't been settled yet. Would you like to configure Location Settings?")
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					sendMessageToHandler(START_LOCATION_SETTINGS);
					cancelDialog(dialog);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					cancelDialog(dialog);
				}
			});
			return builder.create();  

		case DIALOG_ERROR_RETRIEVING_USERS:
			builder.setMessage("Error retrieving users from server.")
			.setCancelable(false)
			.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					cancelDialog(dialog);
					getUsers(currentUsersType);
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					cancelDialog(dialog);		
					currentUsersType = previousUserType;
				}
			});
			return builder.create(); 

		case DIALOG_HAS_NO_LOCATION_BEGINNING:
			builder.setMessage("Location hasn't been settled yet. Hence, all users will appear as not in your range.")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					cancelDialog(dialog);
				}
			});
			return builder.create();  

		default:
			return null;
		}
	}

	private void dismissAndRemoveDialog(int code, boolean changeStatusToAvailable){
		if (changeStatusToAvailable){
			moishdPreferences.setAvailableStatus(true);
		}
		dismissDialog(code);
		removeDialog(code);	
	}
	
	private void cancelDialog(DialogInterface dialog){
		moishdPreferences.setAvailableStatus(true);
		dialog.cancel();		
	}

	private String stringArrayToString(String [] userTrophies){

		StringBuilder builder = new StringBuilder();		
		String currentTrophy;
		for (int i=0; i < userTrophies.length; i++){
			currentTrophy = userTrophies[i];
			if (currentTrophy.equals(TrophiesEnum.BestFriends.toString())){
				builder.append(TrophiesEnum.BestFriends.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.FaceOff.toString())){
				builder.append(TrophiesEnum.FaceOff.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.FirstTime.toString())){
				builder.append(TrophiesEnum.FirstTime.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.MasterMoisher.toString())){
				builder.append(TrophiesEnum.MasterMoisher.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.MegaMoisher.toString())){
				builder.append(TrophiesEnum.MegaMoisher.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.MiniMoisher.toString())){
				builder.append(TrophiesEnum.MiniMoisher.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.SuperMoisher.toString())){
				builder.append(TrophiesEnum.SuperMoisher.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.TenInARow.toString())){
				builder.append(TrophiesEnum.TenInARow.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.TinyMoisher.toString())){
				builder.append(TrophiesEnum.TinyMoisher.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.TwentyInARow.toString())){
				builder.append(TrophiesEnum.TwentyInARow.getTrophyName());
			}
			else if (currentTrophy.equals(TrophiesEnum.GoogleTrophy.toString())){
				builder.append(TrophiesEnum.GoogleTrophy.getTrophyName());
			}
			
			if (i < userTrophies.length - 1 && i>0){
					builder.append(",\n");
				}
			else{
					builder.append("\n");
				}
			
		}

		return builder.toString();
	}

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
	
	private class GetUsersTask extends AsyncTask<Object, Integer, List<Object>> {

		protected void onPreExecute() {
			if (moishdPreferences.userIsAvailable())
				mainProgressDialog = ProgressDialog.show(AllOnlineUsersActivity.this, null, "Retrieving users...", true, false);
		}

		@SuppressWarnings("unchecked")
		protected List<Object> doInBackground(Object... objects) {

			List <Object> resultList = new ArrayList<Object>();
			List<ClientMoishdUser> moishdUsers = new ArrayList<ClientMoishdUser>();	
			List<Drawable> usersPictures = new ArrayList<Drawable>();

			String usersType = (String) objects[0];
			String authToken = (String) objects[1];


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
				int hasLocationStatus = ServerCommunication.hasLocation(authToken);
				switch (hasLocationStatus){
				case ServerCommunication.HAS_LOCATION_TRUE:
					moishdUsers = ServerCommunication.getNearbyUsers(authToken);
					break;
				case ServerCommunication.HAS_LOCATION_FALSE:
					resultList.add(HAS_NO_LOCATION_DIALOG);
					return resultList;
				case ServerCommunication.SERVER_ERROR:
					resultList.add(DIALOG_SERVER_ERROR);
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

				if (moishdPreferences.userIsAvailable())
					sendMessageToHandler(UPDATE_LIST_ADAPTER);
				else 
					needRefresh = true;
			}
			else{
				if (moishdPreferences.userIsAvailable()){
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

			moishdPreferences.setAvailableStatus(false);

			final String postId = values.getString("post_id");

			if (postId != null) {

				// "Wall post made..."

			} else {
				// "No wall post made..."
			}

			moishdPreferences.setAvailableStatus(true);

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

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);

			// Icons bound to the rows.
			facebookPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.facebook);
			nearByUsers = BitmapFactory.decodeResource(context.getResources(), R.drawable.world);
			noPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_facebook);
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

				if (moishdUsers.get(position).isFacebookFriend()){
				
					holder.facebookPic.setImageBitmap(facebookPic);
					if (moishdUsers.get(position).isNearByUser()){
						holder.nearBy.setImageBitmap(nearByUsers);
					}
					else{
						holder.nearBy.setImageBitmap(noPic);
					}
						
				}
				
				else if (moishdUsers.get(position).isNearByUser()){
					holder.facebookPic.setImageBitmap(nearByUsers);
					holder.nearBy.setImageBitmap(noPic);
					
				} 
				else {
					holder.facebookPic.setImageBitmap(noPic);
					holder.nearBy.setImageBitmap(noPic);
				}
			


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
	private class waitForResponse extends CountDownTimer {
		private waitForResponse(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
			ServerCommunication.cancelGame(authToken);
		}    
		
		public void onTick(long millisUntilFinished) {
		}
	}

}
