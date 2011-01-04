package moishd.android.games;

import moishd.android.ServerCommunication;
import moishd.common.ActionByPushNotificationEnum;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


public class GameActivity extends Activity{

	String gameId, authString, gameType, action;

	protected void onNewIntent (Intent intent){
		action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());
		
		if (action.equals(ActionByPushNotificationEnum.GameResult.toString())){
			String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
			//gameResultDialog(result);
			Intent intentForResult = new Intent();
			if (result.equals("Won")) 
				intentForResult.setClass(this, youMoishd.class);
			else
				intentForResult.setClass(this, youHaveBeenMoishd.class);
			
			GetAllExtras();
			SetAllExtras(intentForResult);
			startActivity(intentForResult);
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
		ServerCommunication.sendWinToServer(gameId, authString, gameType);
	}

	protected void Lose(){		
		CommonForWinAndLose();
		ServerCommunication.sendLoseToServer(gameId, authString, gameType);
	}

}
