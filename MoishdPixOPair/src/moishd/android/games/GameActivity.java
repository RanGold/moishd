package moishd.android.games;

import moishd.android.ServerCommunication;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentResultCodesEnum;
import moishd.common.MoishdPreferences;
import moishd.common.PushNotificationTypeEnum;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class GameActivity extends Activity{
	public final static int SERVER_ERROR = 0;
	public final static int SERVER_OK = 1;
	public final static int DIDNT_CALL_SERVER = 2;
	protected static final int AN_ERROR_OCCURED = 3;
	String gameId, authString, gameType, action;
	int serverFlag = DIDNT_CALL_SERVER;
	private waitForResponse timerForResponse;
	private boolean timerOn;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AN_ERROR_OCCURED:
				showErrorOccuredDialog();
				break;
			}
		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MoishdPreferences moishdPreferences = MoishdPreferences.getMoishdPreferences();
		moishdPreferences.setAvailableStatus(false);
	}

	protected void onNewIntent (Intent intent){
		if (timerOn){
			timerForResponse.cancel();
		}
		action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());

		if (action.equals(PushNotificationTypeEnum.GameResult.toString())){

			String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
			int points = intent.getIntExtra(IntentExtraKeysEnum.Points.toString(), -1);
			int newRank = intent.getIntExtra(IntentExtraKeysEnum.Rank.toString(), -1);
			int numOfTrophies = intent.getIntExtra(IntentExtraKeysEnum.NumberOfTrophies.toString(), -1);
			String trophiesString = intent.getStringExtra(IntentExtraKeysEnum.Trophies.toString());
			String nearByGame = intent.getStringExtra(IntentExtraKeysEnum.NearByGame.toString());

			Intent intentForResult = new Intent();

			if (result.equals("Won")){ 
				intentForResult.setClass(this, YouMoishdActivity.class);
			}
			else if (result.equals("Lost")) {
				intentForResult.setClass(this, YouHaveBeenMoishdActivity.class);
			}
			//technical lose

			if (!result.equals("LostTechnicly")) {
				intentForResult.putExtra(IntentExtraKeysEnum.Points.toString(), points);
				intentForResult.putExtra(IntentExtraKeysEnum.NearByGame.toString(), nearByGame);
				GetAllExtras();
				SetAllExtras(intentForResult);				
				startActivity(intentForResult);
			}

			Intent resultIntent = new Intent();
			resultIntent.putExtra(IntentExtraKeysEnum.Trophies.toString(), trophiesString);
			resultIntent.putExtra(IntentExtraKeysEnum.NumberOfTrophies.toString(), numOfTrophies);
			resultIntent.putExtra(IntentExtraKeysEnum.Rank.toString(), newRank);
			resultIntent.putExtra(IntentExtraKeysEnum.ServerResponse.toString(), serverFlag);

			if (serverFlag != SERVER_ERROR){
				setResult(IntentResultCodesEnum.OK.getCode(), resultIntent);
			}
			else{
				setResult(IntentResultCodesEnum.Failed.getCode(), resultIntent);
			}
			finish();
		}
	}

	protected void CommonForWinAndLose(){
		Toast.makeText(GameActivity.this, "Please wait for result", Toast.LENGTH_LONG).show();
		GetAllExtras();
	}

	protected void SetAllExtras(Intent intent){
		intent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authString);
		intent.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);	
	}

	protected void GetAllExtras(){
		gameId = getIntent().getStringExtra(IntentExtraKeysEnum.PushGameId.toString());
		authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
		gameType = getIntent().getStringExtra(IntentExtraKeysEnum.GameType.toString());
	}

	protected void Win(){		
		CommonForWinAndLose();
		boolean serverResponse = ServerCommunication.sendWinToServer(gameId, authString, gameType);
		setFlag(serverResponse);
		timerForResponse= new waitForResponse(40000,1000);
		timerForResponse.start();
		timerOn=true;
	}

	protected void Lose(){		
		CommonForWinAndLose();
		boolean serverResponse = ServerCommunication.sendLoseToServer(gameId, authString, gameType);
		setFlag(serverResponse);
		timerForResponse= new waitForResponse(40000,1000);
		timerForResponse.start();
		timerOn=true;
	}

	protected void setFlag(boolean serverResponse){

		if (!serverResponse) {
			serverFlag=SERVER_ERROR;
		}
		else {
			serverFlag=SERVER_OK;
		}
	}

	protected void LoseTechnicly(){
		GetAllExtras();
		boolean serverResponse = ServerCommunication.sendTechnicalLoseToServer(gameId, authString, gameType);
		setFlag(serverResponse);
		timerForResponse= new waitForResponse(20000,1000);
		timerForResponse.start();
		timerOn=true;
	}
	
	@Override
	 public final void onBackPressed(){
		LoseTechnicly();		
	}
	
	private void showErrorOccuredDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("An error occured.")
		.setCancelable(false)
		.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {	
				Intent resultIntent = new Intent();
				setResult(IntentResultCodesEnum.Failed.getCode(), resultIntent);
				finish();
			}});

		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private class waitForResponse extends CountDownTimer {
		
		private waitForResponse(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
			Message registrationErrorMessage = Message.obtain();
			registrationErrorMessage.setTarget(mHandler);
			registrationErrorMessage.what = AN_ERROR_OCCURED;
			registrationErrorMessage.sendToTarget();
		}    

		public void onTick(long millisUntilFinished) {
		}
	}

}
