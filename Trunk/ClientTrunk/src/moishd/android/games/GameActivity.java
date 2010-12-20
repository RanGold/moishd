package moishd.android.games;


import moishd.android.ServerCommunication;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.PushNotificationTypeEnum;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;


public class GameActivity extends Activity{
	
	String gameId, authString, gameType;
	
	protected void onNewIntent (Intent intent){
		
		String action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());
		if (action.equals(PushNotificationTypeEnum.GameResult.toString())){
			String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("You've " + result + "!")
			.setCancelable(false)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					finish();
				}
			});
			AlertDialog alert = builder.create();  
			alert.show();
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
		finish();
	}
	
	protected void Lose(){		
		CommonForWinAndLose();
		ServerCommunication.sendLoseToServer(gameId, authString, gameType);
		finish();
	}
	
}
