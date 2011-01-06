package moishd.android.games;

import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class MostPopularGameActivity extends Activity{
	TextView text1,text2;
	boolean flag = true;
	String gameId, authString, gameType, action;
	
	protected void SetTexts(){
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.most_popular_game_layout);
		SetTexts();
		
		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf");
		text1.setTypeface(fontName);
		text2.setTypeface(fontName);
		
		MyCount count;
		count= new MyCount(13000,1000);
		count.start();
		
	}
	
	
	
	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
			finish();
		}
		public void onTick(long millisUntilFinished) {
			SetTexts();
			if(flag){
				text1.setTextColor(Color.RED);
				text2.setTextColor(Color.GREEN);
				
			}
			else{
				text1.setTextColor(Color.BLUE);
				text2.setTextColor(Color.MAGENTA);
				
			}
			flag = !flag;
		
		}
	}
}
