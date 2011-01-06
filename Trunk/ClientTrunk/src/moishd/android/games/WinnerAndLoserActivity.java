package moishd.android.games;

import moishd.android.RankForGameActivity;
import moishd.android.ServerCommunication;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.content.Intent;

public class WinnerAndLoserActivity extends Activity{
	String authString ,gameType;
	
	public void checkIfRankNeeded(){
		gameType = getIntent().getStringExtra(IntentExtraKeysEnum.GameType.toString());
		authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
		
		if (ServerCommunication.isFirstTimePlayed(gameType, authString)) {
			Intent rankForGame = new Intent();
			rankForGame.setClass(this, RankForGameActivity.class);
			rankForGame.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);
			rankForGame.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authString);
			startActivity(rankForGame);
		}
		
		finish();
	}
}