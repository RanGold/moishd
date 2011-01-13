package moishd.android.games;

import moishd.android.ServerCommunication;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentResultCodesEnum;
import moishd.common.PushNotificationTypeEnum;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


public class GameActivity extends Activity{
	public final static int SERVER_ERROR = 0;
	public final static int SERVER_OK = 1;
	public final static int DIDNT_CALL_SERVER = 2;
	String gameId, authString, gameType, action;
	int serverFlag = DIDNT_CALL_SERVER;
	

	protected void onNewIntent (Intent intent){
		action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());
		
		if (action.equals(PushNotificationTypeEnum.GameResult.toString())){
			String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
			int points = intent.getIntExtra(IntentExtraKeysEnum.Points.toString(), -1);
			int newRank = intent.getIntExtra(IntentExtraKeysEnum.Rank.toString(), -1);
			int numOfTrophies = intent.getIntExtra(IntentExtraKeysEnum.NumberOfTrophies.toString(), -1);
			String trophiesString = intent.getStringExtra(IntentExtraKeysEnum.Trophies.toString());
			String nearByGame = intent.getStringExtra(IntentExtraKeysEnum.NearByGame.toString());
			
			Intent intentForResult = new Intent();
			intentForResult.putExtra(IntentExtraKeysEnum.Points.toString(), points);
			intentForResult.putExtra(IntentExtraKeysEnum.NearByGame.toString(), nearByGame);

			if (result.equals("Won")) 
				intentForResult.setClass(this, YouMoishdActivity.class);
			else
				intentForResult.setClass(this, YouHaveBeenMoishdActivity.class);
			
			GetAllExtras();
			SetAllExtras(intentForResult);
			startActivity(intentForResult);
			
			Intent resultIntent = new Intent();
			resultIntent.putExtra(IntentExtraKeysEnum.Trophies.toString(), trophiesString);
			resultIntent.putExtra(IntentExtraKeysEnum.NumberOfTrophies.toString(), numOfTrophies);
			resultIntent.putExtra(IntentExtraKeysEnum.Rank.toString(), newRank);
			resultIntent.putExtra(IntentExtraKeysEnum.ServerResponse.toString(), serverFlag);
			if (serverFlag != SERVER_ERROR){
				setResult(IntentResultCodesEnum.OK.getCode(), resultIntent);
			}else{
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
	}

	protected void Lose(){		
		CommonForWinAndLose();
		boolean serverResponse = ServerCommunication.sendLoseToServer(gameId, authString, gameType);
		setFlag(serverResponse);
	}
	
	protected void setFlag(boolean serverResponse){
	
		if (!serverResponse) {
			serverFlag=SERVER_ERROR;
		}
		else {
			serverFlag=SERVER_OK;
		}
		
	}

}
