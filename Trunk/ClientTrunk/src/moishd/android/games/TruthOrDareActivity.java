package moishd.android.games;


import java.util.Random;

import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentResultCodesEnum;
import moishd.common.MoishdPreferences;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TruthOrDareActivity extends Activity{
	String gameType;
	int numberOfDareGames = 3;
	TextView text;
	int flag = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.truth_or_dare);
		
		MoishdPreferences moishdPreferences = MoishdPreferences.getMoishdPreferences();
		moishdPreferences.setAvailableStatus(false);
		
		Button truth = (Button) findViewById(R.id.truth);
		Button dare = (Button) findViewById(R.id.dare);
		text = (TextView)findViewById(R.id.choose);
		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/COOPBL.ttf");
		text.setTypeface(fontName);

		MyCount counter = new MyCount(10000, 1000);
		counter.start();
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

	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		public void onFinish() {
		}
		public void onTick(long millisUntilFinished) {
			text = (TextView)findViewById(R.id.choose);
			if(flag % 4 == 1){
				text.setTextColor(Color.RED);

			}
			else if (flag % 4 == 2){
				text.setTextColor(Color.BLUE);
			}
			else if (flag % 4 == 3){
				text.setTextColor(Color.GREEN);
			}
			else
				text.setTextColor(Color.BLACK);
			flag = (flag+1) % 4;

		}
	}
	
	@Override
	public void onBackPressed(){
		return;
	}

}
