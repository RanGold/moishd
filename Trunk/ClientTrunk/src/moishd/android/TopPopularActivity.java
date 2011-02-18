package moishd.android;

import java.util.List;

import moishd.common.IntentExtraKeysEnum;
import moishd.common.MoishdPreferences;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

public class TopPopularActivity extends Activity{

	Bitmap trivia,fastClick,clickMemory, mixedWord;
	TextView text, game1,game2,game3,game4,game5;
	ImageView gamepic1,gamepic2,gamepic3,gamepic4,gamepic5;
	String authString;
	int flag=1;

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.top_popular);

		MoishdPreferences moishdPreferences = MoishdPreferences.getMoishdPreferences();
		moishdPreferences.setAvailableStatus(false);

		authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());

		trivia = BitmapFactory.decodeResource(getResources(), R.drawable.trivia_no_name);
		fastClick = BitmapFactory.decodeResource(getResources(), R.drawable.fast_click_no_name);
		clickMemory = BitmapFactory.decodeResource(getResources(), R.drawable.click_memory_no_name);
		mixedWord = BitmapFactory.decodeResource(getResources(), R.drawable.mixed_word_no_name);

		gamepic1 =(ImageView) findViewById (R.id.gamePicture1);
		gamepic2 =(ImageView) findViewById (R.id.gamePicture2);
		gamepic3 =(ImageView) findViewById (R.id.gamePicture3);
		gamepic4 =(ImageView) findViewById (R.id.gamePicture4);
		gamepic5 =(ImageView) findViewById (R.id.gamePicture5);

		text = (TextView) findViewById(R.id.topFive);
		game1 = (TextView) findViewById(R.id.game1);
		game2 = (TextView) findViewById(R.id.game2);
		game3 = (TextView) findViewById(R.id.game3);
		game4 = (TextView) findViewById(R.id.game4);
		game5 = (TextView) findViewById(R.id.game5);

		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/mailrays.ttf");
		text.setTypeface(fontName);
		game1.setTypeface(fontName);
		game2.setTypeface(fontName);
		game3.setTypeface(fontName);
		game4.setTypeface(fontName);
		game5.setTypeface(fontName);

		List<String> topFive =ServerCommunication.getTopFivePopular(authString);
		MyCount count;
		count= new MyCount(30000,1000);
		count.start();

		int listSize = topFive.size();

		switch(listSize){
		case(5): 
			String gameType5 = topFive.get(4).split(":")[0];
			gamepic5.setImageBitmap(setPicture(gameType5));
			game5.setText(setName(gameType5));
		case(4):
			String gameType4 = topFive.get(3).split(":")[0];
			gamepic4.setImageBitmap(setPicture(gameType4));
			game4.setText(setName(gameType4));		
		case(3):
			String gameType3 = topFive.get(2).split(":")[0];
			gamepic3.setImageBitmap(setPicture(gameType3));
			game3.setText(setName(gameType3));		
		case(2):
			String gameType2 = topFive.get(1).split(":")[0];
			gamepic2.setImageBitmap(setPicture(gameType2));
			game2.setText(setName(gameType2));
		case(1):			
			String gameType1 = topFive.get(0).split(":")[0];
			gamepic1.setImageBitmap(setPicture(gameType1));
			game1.setText(setName(gameType1));	}
	}

	private Bitmap setPicture(String gameType){
		if (gameType.equals(IntentExtraKeysEnum.DareFastClick.toString())) {
			return fastClick;
		}
		else if (gameType.equals(IntentExtraKeysEnum.DareMixing.toString())) {
			return mixedWord;
		}
		else if (gameType.equals(IntentExtraKeysEnum.DareSimonPro.toString())) {
			return clickMemory;
		}
		else if (gameType.equals(IntentExtraKeysEnum.Truth.toString())) {
			return trivia;
		}
		else
			return null;

	}

	private String setName(String gameType){
		if (gameType.equals(IntentExtraKeysEnum.DareFastClick.toString())) {
			return "Fast click";
		}
		else if (gameType.equals(IntentExtraKeysEnum.DareMixing.toString())) {
			return "Mixed word";
		}
		else if (gameType.equals(IntentExtraKeysEnum.DareSimonPro.toString())) {
			return "Click memory";
		}
		else if (gameType.equals(IntentExtraKeysEnum.Truth.toString())) {
			return "Trivia";
		}
		else
			return null;
	}

	private class MyCount extends CountDownTimer {

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    

		public void onFinish() {
		}    

		public void onTick(long millisUntilFinished) {
			text = (TextView) findViewById(R.id.topFive);
			if (flag==1) {
				text.setTextColor(Color.RED);
			}
			else if (flag == 2){
				text.setTextColor(Color.MAGENTA);
			}
			else if (flag == 3){
				text.setTextColor(Color.BLACK);
			}
			else{
				text.setTextColor(Color.BLUE);
			}
			flag = (flag+1) % 4;
		}
	}

	@Override 
	public void onBackPressed(){
		//TODO - check if the request has succeeded
		boolean requestToServer = ServerCommunication.setSingleUserUnbusy(authString);
		finish();

	}
}


