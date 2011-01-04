package moishd.android.games;

import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentResultCodesEnum;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseGameActivity extends Activity{
	
	ImageView mixing,simonPro,fastClick,trivia;
	Bitmap 	simonProPic1,simonProPic2,simonProPic3, 
			triviaPic1,triviaPic2,triviaPic3;	
	int flag = 1;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.choose_game_layout);
		

		triviaPic1 = BitmapFactory.decodeResource(getResources(), R.drawable.trivia_1);
		triviaPic2 = BitmapFactory.decodeResource(getResources(), R.drawable.trivia_2);
		triviaPic3 = BitmapFactory.decodeResource(getResources(), R.drawable.trivia_3);
		simonProPic1 = BitmapFactory.decodeResource(getResources(), R.drawable.simonpro_1);
		simonProPic2 = BitmapFactory.decodeResource(getResources(), R.drawable.simonpro_2);
		simonProPic3 = BitmapFactory.decodeResource(getResources(), R.drawable.simonpro_3);
		
		mixing = (ImageView) findViewById(R.id.mixing);
		simonPro = (ImageView) findViewById(R.id.simonpro);
		fastClick = (ImageView) findViewById(R.id.fastClick);
		trivia = (ImageView) findViewById(R.id.trivia);
		TextView text = (TextView) findViewById(R.id.text);
		
		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf");
		text.setTypeface(fontName);
		
		MyCount count;
		count= new MyCount(30000,1000);
		count.start();
		
		mixing.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				returnGameTypeToCallingActivity(IntentExtraKeysEnum.DareMixing.toString());
			}
		});	
		
		simonPro.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				returnGameTypeToCallingActivity(IntentExtraKeysEnum.DareSimonPro.toString());
			
			}
		});
		
		fastClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				returnGameTypeToCallingActivity(IntentExtraKeysEnum.DareFastClick.toString());

			}
		});
		
		trivia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				returnGameTypeToCallingActivity(IntentExtraKeysEnum.Truth.toString());
			}
		});
	}
	
	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
		}    
		public void onTick(long millisUntilFinished) {
			simonPro = (ImageView) findViewById(R.id.simonpro);
			trivia = (ImageView) findViewById(R.id.trivia);
			if (flag==1) {
				simonPro.setImageBitmap(simonProPic1);
				trivia.setImageBitmap(triviaPic2);
				flag = 2;
			}
			else if (flag == 2){
				simonPro.setImageBitmap(simonProPic3);
				trivia.setImageBitmap(triviaPic3);
				flag = 3;
			}
			else {
				simonPro.setImageBitmap(simonProPic2);
				trivia.setImageBitmap(triviaPic1);
				flag = 1;
				
			}
		
		}
	}
	
	private void returnGameTypeToCallingActivity(String gameType){
		Intent GameTypeIntent = new Intent();
		GameTypeIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), gameType);
		setResult(IntentResultCodesEnum.OK.getCode(), GameTypeIntent);
		finish();		
	}


}
