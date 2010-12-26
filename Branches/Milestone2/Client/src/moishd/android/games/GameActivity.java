package moishd.android.games;

import moishd.android.ServerCommunication;
import moishd.common.ActionByPushNotificationEnum;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


public class GameActivity extends Activity{

	String gameId, authString, gameType;

	protected void onNewIntent (Intent intent){


		gameId = intent.getStringExtra(IntentExtraKeysEnum.PushGameId.toString());	
		String action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());
		gameType = intent.getStringExtra(IntentExtraKeysEnum.GameType.toString());

		if (action.equals(ActionByPushNotificationEnum.GameResult.toString())){
			String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
			//gameResultDialog(result);
			Intent intentForResult = new Intent();
			if (result.equals("Won")) 
				intentForResult.setClass(this, youMoishd.class);
			else
				intentForResult.setClass(this, youHaveBeenMoishd.class);

			startActivity(intentForResult);
			finish();

		}
	}


	/*
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("You've " + result + "!")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					finish();
				}
			});
			AlertDialog alert = builder.create();  
			alert.show();*/



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
