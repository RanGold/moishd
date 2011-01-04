package moishd.android.games;


import java.util.Random;

import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentResultCodesEnum;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TruthOrDareActivity extends Activity{
	String gameType;
	int numberOfDareGames = 3;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.truth_or_dare);   
		Button truth = (Button) findViewById(R.id.truth);
		Button dare = (Button) findViewById(R.id.dare);
		
		truth.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				gameType = IntentExtraKeysEnum.Truth.toString();
				returnGameTypeToCallingActivity(gameType);
			}
		});
		
		dare.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Random random = new Random();  
				int i = random.nextInt(100);
				
				i = i % numberOfDareGames;
				if (i==0)
					gameType = IntentExtraKeysEnum.DareSimonPro.toString();
					
				else if (i==1)
					gameType = IntentExtraKeysEnum.DareMixing.toString();
					
				else
					gameType = IntentExtraKeysEnum.DareFastClick.toString();


				returnGameTypeToCallingActivity(gameType);
			}
		});
		
		
	}
	
	private void returnGameTypeToCallingActivity(String gameType){
		Intent GameTypeIntent = new Intent();
		GameTypeIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);
		setResult(IntentResultCodesEnum.OK.getCode(), GameTypeIntent);
		finish();		
	}

}
