package moishd.android.games;

import moishd.android.ServerCommunication;
import moishd.android.games.youHaveBeenMoishd;
import moishd.android.games.youMoishd;
import moishd.common.ActionByPushNotificationEnum;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


public class GameActivity extends Activity{

	String gameId, authString, gameType, action;

	protected void onNewIntent (Intent intent){
		action = getIntent().getStringExtra(IntentExtraKeysEnum.PushAction.toString());



		if (action.equals(ActionByPushNotificationEnum.GameResult.toString())){
			String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
			//gameResultDialog(result);
			Intent intentForResult = new Intent();
			if (result.equals("Won")) 
				intentForResult.setClass(this, youMoishd.class);
			else
				intentForResult.setClass(this, youHaveBeenMoishd.class);
			
			intentForResult.putExtra(IntentExtraKeysEnum.PushGameId.toString(), gameId);
			intentForResult.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authString);
			intentForResult.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);
			
			startActivity(intentForResult);
			finish();

		}
	}


	protected void CommonForWinAndLose(){
		Toast.makeText(GameActivity.this, "Please wait for result", Toast.LENGTH_LONG).show();
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
