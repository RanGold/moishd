package moishd.android;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import moishd.common.GetUsersByTypeEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentRequestCodesEnum;
import moishd.common.LocationManagment;
import moishd.common.SharedPreferencesKeysEnum;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AllOnlineUsersActivity extends Activity {

	//tammy
	//AssetManager font_try;
	
	private static List<ClientMoishdUser> moishdUsers = new ArrayList<ClientMoishdUser>();
	private static List<ClientMoishdUser> moishdFacebookUsers = new ArrayList<ClientMoishdUser>();//tammy
	private static List<Drawable> usersPictures = new ArrayList<Drawable>();
	private static int[] moishdFaceUsers;//tammy
	private static List<String> friendsID;
	private GetUsersByTypeEnum currentUsersType;
	private GetUsersByTypeEnum previousClickPosition;
	
	private static Typeface fontName, fontHeader;

	private String authToken;

	private String game_id;
	private String gameType;
	private String last_user;
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
	private final int DIALOG_USER_DECLINED = 13;
	private final int DIALOG_HAS_NO_LOCATION = 14;
	private final int DIALOG_ERROR_RETRIEVING_USERS = 15;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_LIST_ADAPTER:
				updateList();
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
   
		setContentView(R.layout.all_users_layout);
		header = (TextView) findViewById(R.id.usersTypeHeader);
		header.setText("All online users");
		header.setTextSize(20);
		
		
		fontName = Typeface.createFromAsset(getAssets(), "fonts/COOPBL.ttf"); //tammy
		fontHeader = Typeface.createFromAsset(getAssets(), "fonts/BROADW.ttf"); //tammy
		
		header.setTypeface(fontHeader); //tammy
		
		serverHasFacebookFriends = false;

		//need the authToken for server requests
		authToken = getGoogleAuthToken();
		asyncRunner = new AsyncFacebookRunner(WelcomeScreenActivity.facebook);

		locationManagment = LocationManagment.getLocationManagment(getApplicationContext(),getGoogleAuthToken());
		locationManagment.startUpdateLocation(1);

		
		/*tammy
		currentUsersType = GetUsersByTypeEnum.AllUsers;
		getUsers(GetUsersByTypeEnum.AllUsers);
*/
		currentUsersType = GetUsersByTypeEnum.MergedUsers;
		getUsers(GetUsersByTypeEnum.MergedUsers);
		list = (ListView) findViewById(R.id.allUsersListView);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				currentClickPosition = arg2;
				inviteUserToMoishDialog();
			}});
		
		
		list.setAdapter(new EfficientAdapter(this));
		
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
			if (currentUsersType != GetUsersByTypeEnum.FacebookFriends){
				getUsers(currentUsersType);
			}
			else{
				getFriendsUsers();
			}
			return true;
		case R.id.logout:
			doQuitActions();
			return true;
		case R.id.facebookFriends:
			getUsers(GetUsersByTypeEnum.FacebookFriends);
			return true;
		case R.id.nearbyUsers:
			getUsers(GetUsersByTypeEnum.NearbyUsers);
			return true;
		case R.id.allUsers:
			/*tammy
			getUsers(GetUsersByTypeEnum.AllUsers);*/
			getUsers(GetUsersByTypeEnum.MergedUsers);
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
				last_user=null;
			}

			else if (action.equals(ActionByPushNotificationEnum.PlayerBusy.toString())){
				userIsBusy(last_user);
				game_id = null;
				last_user = null;
			}
			else if (action.equals(ActionByPushNotificationEnum.StartGameTruth.toString())){
				startGameTruth();
			}
			else if (action.equals(ActionByPushNotificationEnum.StartGameDare.toString())) {
				startGameDare();
			}
			/*
			else if (action.equals(ActionByPushNotificationEnum.GameResult.toString())){
				String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
				//gameResultDialog(result);
				Intent intentForResult = new Intent();
				if (result.equals("Won")) 
					intentForResult.setClass(this, youMoishd.class);
				else
					intentForResult.setClass(this, youHaveBeenMoishd.class);

				startActivity(intentForResult);
			}
			 */
		}
	}

	@Override
	protected void onDestroy (){
		super.onDestroy();
		locationManagment.stopUpdateLocation();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentRequestCodesEnum.GetChosenGame.getCode()){
			gameType = data.getStringExtra(IntentExtraKeysEnum.GameType.toString());
			sendInvitationResponse("Accept" + gameType);
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
		case AllUsers:
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
	private void getMergedUsers(){ 

		if (!serverHasFacebookFriends){
			mainProgressDialog = ProgressDialog.show(this, null, "Retrieving users...", true, false);
			asyncRunner.request("me/friends", new FriendsRequestListener());
		}
		else{
			new GetUsersTask().execute(GetUsersByTypeEnum.MergedUsers.toString(), authToken);
		}
	}
	
	private void getFriendsUsers(){ 

		if (!serverHasFacebookFriends){
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

	private void inviteUserToMoish(ClientMoishdUser user){

		game_id = ServerCommunication.inviteUser(user, authToken);

	}

	private void retrieveInvitation(){

		ClientMoishdUser user = ServerCommunication.retrieveInvitation(game_id, authToken);
		if (user != null){
			Bundle bundle = new Bundle();
			bundle.putString("userName", user.getUserNick());
			showDialog(DIALOG_RETRIEVE_USER_INVITATION, bundle);
		}
		else{
			
		}
	}

	private boolean sendInvitationResponse(String response){
		
		return ServerCommunication.sendInvitationResponse(game_id, response, authToken);
	}

	private void userIsBusy(String invitedUser){

		Bundle bundle = new Bundle();
		bundle.putString("userName", invitedUser);
		showDialog(DIALOG_USER_IS_BUSY, bundle);
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
			intent = new Intent(this, SimonPro.class);
		else if (gameType.equals(IntentExtraKeysEnum.DareMixing.toString()))
			intent = new Intent(this, Mixing.class);
		else //TODO right now else case is fast click.
			intent = new Intent(this, FastClick.class);
		commonForTruthAndDare(intent);
	}

	private void startGameTruth(){
		Intent intent = new Intent(this, TruthPart.class);
		commonForTruthAndDare(intent);
	}

	private void hasNoLocationDialog(){
		showDialog(DIALOG_HAS_NO_LOCATION);
		currentUsersType = previousClickPosition;
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
		new GetUsersTask().execute(GetUsersByTypeEnum.FacebookFriends.toString(), authToken, friendsID);
	}

	private void sendMessageToHandler(final int messageType) {
		Message registrationErrorMessage = Message.obtain();
		registrationErrorMessage.setTarget(mHandler);
		registrationErrorMessage.what = messageType;
		registrationErrorMessage.sendToTarget();
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		switch (id) {

		case DIALOG_INVITE_USER_TO_MOISHD:
			last_user = moishdUsers.get(currentClickPosition).getUserNick();
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
					dialog.cancel();
				}
			});
			return builder.create();  
			
		case DIALOG_RETRIEVE_USER_INVITATION:
			builder.setMessage("You've been invited by " + args.getString("userName") + " to Moish.")
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
			return builder.create();  

		case DIALOG_USER_IS_BUSY:
			builder.setMessage(args.getString("userName") + " is currently playing. Please try again later.")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
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

			builder.setIcon(R.id.icon);
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
			default:
				return null;
	}
	}
	
	private class GetUsersTask extends AsyncTask<Object, Integer, List<Object>> {

		protected void onPreExecute() {
			mainProgressDialog = ProgressDialog.show(AllOnlineUsersActivity.this, null, "Retrieving users...", true, false);
		}

		@SuppressWarnings("unchecked")
		protected List<Object> doInBackground(Object... objects) {

			List <Object> resultList = new ArrayList<Object>();
			List<ClientMoishdUser> moishdUsers = new ArrayList<ClientMoishdUser>();
		
		//	List<ClientMoishdUser> moishdFacebookUsers = new ArrayList<ClientMoishdUser>(); //tammy
			
			List<Drawable> usersPictures = new ArrayList<Drawable>();

			String usersType = (String) objects[0];
			String authToken = (String) objects[1];

			/*if (usersType.equals(GetUsersByTypeEnum.AllUsers.toString())){
				moishdUsers = ServerCommunication.getAllUsers(authToken);
			 		
			}*/ // tammy tammy tammy - Hila - na na banana
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

				//Collections.sort(moishdFacebookUsers);  //tammy
				
				for (int i=0; i < moishdUsers.size(); i++){
					Drawable userPic = LoadImageFromWebOperations(moishdUsers.get(i).getPictureLink());
					usersPictures.add(userPic);
					setProgress((int) ((i / (float) moishdUsers.size()) * 100));
					
				}
				/* //tammy
				
				moishdFaceUsers = new int[moishdUsers.size()];

				for (int i=0,j=0; i < moishdUsers.size() ; i++,j++){
					String moishUser = moishdUsers.get(i).getUserNick();
					String moishFacebookUser = moishdFacebookUsers.get(j).getUserNick();
					if (moishUser.equals(moishFacebookUser))
							moishdFaceUsers[i]=1;
						else  {
							j--;
							moishdFaceUsers[j]=0;
						}
					
				}*/
				
				
				
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
				sendMessageToHandler(UPDATE_LIST_ADAPTER);
			}
			else{
				Integer messageCode = (Integer) resultList.get(0);
				final int messageCodeInt = messageCode.intValue();
				sendMessageToHandler(messageCodeInt);
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

	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		private Bitmap userRank0;
		private Bitmap userRank1;
		private Bitmap userRank2;
		private Bitmap userRank3;
		
		//tammy
		private Bitmap facebookPic;
		private Bitmap nearByUsers;
		
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
			facebookPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.facebook);
			nearByUsers = BitmapFactory.decodeResource(context.getResources(), R.drawable.nbu);
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
				holder.nearBy = (ImageView) convertView.findViewById(R.id.nearByUsers);
				holder.facebookPic = (ImageView) convertView.findViewById(R.id.facebookPic);

				convertView.setTag(holder);
			} else {

				holder = (ViewHolder) convertView.getTag();
			}



			holder.userName.setText(moishdUsers.get(position).getUserNick());	
			holder.userName.setTypeface(fontName);
			holder.userName.setTextSize(17);
		

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

			holder.userPicture.setImageDrawable(usersPictures.get(position));
			
			//tammy

			if (moishdUsers.get(position).isFacebookFriend())
				holder.facebookPic.setImageBitmap(facebookPic);
			if (moishdUsers.get(position).isNearByUser())			
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
