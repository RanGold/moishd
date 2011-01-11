package moishd.android.games;

import moishd.android.RankForGameActivity;
import moishd.android.ServerCommunication;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.content.Intent;

public class WinnerAndLoserActivity extends Activity{
	String authString ,gameType,gameNearBy,addToMesseage;
	int textSize;
	boolean needToChangeTextSize = false;
	
	
	public void checkIfGameIsNearBy() {
		gameNearBy = getIntent().getStringExtra(IntentExtraKeysEnum.NearByGame.toString());
		if (gameNearBy.equals("yes")) {
			addToMesseage = "You\'ve got double points for playing with a nearby user.";
		}
		else{
			addToMesseage="";
		}
		
		if (!addToMesseage.equals("")){
			needToChangeTextSize = true;
		}
		
			
	}
	
	public void checkIfRankNeeded(){
		gameType = getIntent().getStringExtra(IntentExtraKeysEnum.GameType.toString());
		authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
		
		if (ServerCommunication.isFirstTimePlayed(gameType, authString)) {
			boolean busy = ServerCommunication.setUserBusy(authString);
			Intent rankForGame = new Intent();
			rankForGame.setClass(this, RankForGameActivity.class);
			rankForGame.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);
			rankForGame.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authString);
			startActivity(rankForGame);
		}
		
		finish();
	}
}