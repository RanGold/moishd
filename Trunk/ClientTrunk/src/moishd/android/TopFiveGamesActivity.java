package moishd.android;

import java.util.List;

import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

public class TopFiveGamesActivity extends Activity{
	Bitmap trivia,fastClick,clickMemory, mixedWord, s0,s1,s2,s3,s4,s5,s05,s15,s25,s35,s45;
	String authString,pageRequest;
	ImageView gamepic1,gamepic2,gamepic3,gamepic4,gamepic5, rank1,rank2,rank3,rank4,rank5;
	TextView text;
	int flag = 1;
	
	public Bitmap setPicture(String gameType){
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
	
	public Bitmap setRank(double rank){
		
		if (rank ==0){
			return s0;
			
		}else if (rank > 0 && rank < 1){
			return s05;			
		}else if (rank ==1){
			return s1;
		}
		else if (rank > 1 && rank < 2){
			return s15;
		}
		
		else if (rank ==2){
			return s2;
		}
		else if (rank > 2 && rank < 3){
			return s25;
		}
		
		else if (rank ==3){
			return s3;
		}
		else if (rank > 3 && rank < 4){
			return s35;
		}
		
		else if (rank ==4){
			return s4;
		}
		else if (rank > 4 && rank < 5){
			return s45;
		}
		
		else if (rank ==5){
			return s5;
		}
		else {
			return null;
		}
		
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.get_top_five_layout);  
		
		authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
		pageRequest = getIntent().getStringExtra(IntentExtraKeysEnum.TopFiveRequest.toString());
		
		
		trivia = BitmapFactory.decodeResource(getResources(), R.drawable.trivia_top5);
		fastClick = BitmapFactory.decodeResource(getResources(), R.drawable.fast_click_top5);
		clickMemory = BitmapFactory.decodeResource(getResources(), R.drawable.click_memory_top5);
		mixedWord = BitmapFactory.decodeResource(getResources(), R.drawable.mixed_word_top5);
		s0 = BitmapFactory.decodeResource(getResources(), R.drawable.no_stars);
		s05 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_0_5);
		s1 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_1);
		s15 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_1_0_5);
		s2 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_2);
		s25 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_2_0_5);
		s3 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_3);
		s35 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_3_0_5);
		s4 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_4);
		s45 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_4_0_5);
		s5 = BitmapFactory.decodeResource(getResources(), R.drawable.stars_5);

		gamepic1 =(ImageView) findViewById (R.id.gamePicture1);
		gamepic2 =(ImageView) findViewById (R.id.gamePicture2);
		gamepic3 =(ImageView) findViewById (R.id.gamePicture3);
		gamepic4 =(ImageView) findViewById (R.id.gamePicture4);
		gamepic5 =(ImageView) findViewById (R.id.gamePicture5);
		rank1 =(ImageView) findViewById (R.id.rank1);
		rank2 =(ImageView) findViewById (R.id.rank2);
		rank3 =(ImageView) findViewById (R.id.rank3);
		rank4 =(ImageView) findViewById (R.id.rank4);
		rank5 =(ImageView) findViewById (R.id.rank5);
		text = (TextView) findViewById(R.id.topFive);
		
		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf");
		text.setTypeface(fontName);
		
		
		List<String> topFive;
		if (pageRequest.equals(IntentExtraKeysEnum.TopFiveRanked.toString())){
			text.setText("Moish\'d! top five ranked games");
			topFive = ServerCommunication.getTopFiveRanked(authString);
		}
		else{
			text.setText("Moish\'d! top five popular games");
			topFive = ServerCommunication.getTopFivePopular(authString);
		}
		
		
		
		
		MyCount count;
		count= new MyCount(30000,1000);
		count.start();
		
	
		
		int listSize = topFive.size();
		
		switch(listSize){
		case(5): 
			String gameType5 = topFive.get(4).split(":")[0];
			double rankOfGame5 = Double.parseDouble(topFive.get(4).split(":")[1]);
			gamepic5.setImageBitmap(setPicture(gameType5));
			rank5.setImageBitmap(setRank(rankOfGame5));
		
		case(4):
			String gameType4 = topFive.get(3).split(":")[0];
			double rankOfGame4 = Double.parseDouble(topFive.get(3).split(":")[1]);
			gamepic4.setImageBitmap(setPicture(gameType4));
			rank4.setImageBitmap(setRank(rankOfGame4));
		
		case(3):
			String gameType3 = topFive.get(2).split(":")[0];
			double rankOfGame3 = Double.parseDouble(topFive.get(2).split(":")[1]);
			gamepic3.setImageBitmap(setPicture(gameType3));
			rank3.setImageBitmap(setRank(rankOfGame3));
		
		case(2):
			String gameType2 = topFive.get(1).split(":")[0];
			double rankOfGame2 = Double.parseDouble(topFive.get(1).split(":")[1]);
			gamepic2.setImageBitmap(setPicture(gameType2));
			rank2.setImageBitmap(setRank(rankOfGame2));
		
		case(1):
			String gameType1 = topFive.get(0).split(":")[0];
			double rankOfGame1 = Double.parseDouble(topFive.get(0).split(":")[1]);
			gamepic1.setImageBitmap(setPicture(gameType1));
			rank1.setImageBitmap(setRank(rankOfGame1));
		}
	}
	
	public class MyCount extends CountDownTimer {
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
}


