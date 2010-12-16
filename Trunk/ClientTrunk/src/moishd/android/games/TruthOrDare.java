package moishd.android.games;


import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentResultCodesEnum;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TruthOrDare extends Activity{
	String gameType;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.truth_or_dare);   
		Button truth = (Button) findViewById(R.id.truth);
		Button dare = (Button) findViewById(R.id.dare);
		
		truth.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				gameType = "Truth";
				returnGameTypeToCallingActivity(gameType);
			}
		});
		
		dare.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				gameType = "Dare";
				returnGameTypeToCallingActivity(gameType);
			}
		});
		
		
	}
	
	private void returnGameTypeToCallingActivity(String gameType){
		Intent GameTypeIntent = new Intent();
		if (gameType.compareTo("Truth")==0)
			GameTypeIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), "Truth");
		
		else
			GameTypeIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), "Dare");
		
		setResult(IntentResultCodesEnum.OK.getCode(), GameTypeIntent);
		finish();		
	}

}
