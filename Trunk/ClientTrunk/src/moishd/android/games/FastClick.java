package moishd.android.games;



import java.util.Random;

import moishd.android.R;
import moishd.android.ServerCommunication;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.PushNotificationTypeEnum;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FastClick extends Activity{
	Random random = new Random();  
	Random randomForButton = new Random();  
	int i = random.nextInt(20) + 1;

	
	int j;

	String number;
	
	int left,right,top,bottom;
	
	Button click1, click2,click3,click4;
	TextView word;
	
	
/*make only one button appear on the screen*/	
	public void setOneClickable(int i){
		
		 click1=(Button) findViewById(R.id.clickOnMe1);
		 click2=(Button) findViewById(R.id.clickOnMe2);
		 click3=(Button) findViewById(R.id.clickOnMe3);
		 click4=(Button) findViewById(R.id.clickOnMe4);
		if (i==1 || i==4) {
			click2.setClickable(false);
			click2.setVisibility(View.INVISIBLE);
			click3.setClickable(false);
			click3.setVisibility(View.INVISIBLE);
			if (i==1) {
				click4.setClickable(false);
				click4.setVisibility(View.INVISIBLE);
				click1.setVisibility(View.VISIBLE);
				click1.setClickable(true);
			}
			else {
				click1.setClickable(false);
				click1.setVisibility(View.INVISIBLE);
				click4.setVisibility(View.VISIBLE);
				click4.setClickable(true);
			}
		}
		else {
			click1.setClickable(false);
			click1.setVisibility(View.INVISIBLE);
			click4.setClickable(false);
			click4.setVisibility(View.INVISIBLE);
			if (i==2) {
				click3.setClickable(false);
				click3.setVisibility(View.INVISIBLE);
				click2.setVisibility(View.VISIBLE);
				click2.setClickable(true);
			}
			else {
				click2.setClickable(false);
				click2.setVisibility(View.INVISIBLE);
				click3.setVisibility(View.VISIBLE);
				click3.setClickable(true);
			}
		}
	}

	
	/*what do do after each button click. Mutual to all buttons*/
	public void OnClickGeneral(){
		word = (TextView) findViewById(R.id.theWord);
		i--;
		number = Integer.toString(i);
		word.setText(number);
		if (i==0) {
			Toast.makeText(FastClick.this, 
					"please wait for result", 
					Toast.LENGTH_LONG).show();
			String gameId = getIntent().getStringExtra(IntentExtraKeysEnum.PushGameId.toString());
			String authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
			String gameType = getIntent().getStringExtra(IntentExtraKeysEnum.GameType.toString());
			ServerCommunication.sendWinToServer(gameId, authString,gameType);
			finish();

		}
		else {
			j = randomForButton.nextInt(100);
			j = (j % 4) +1;
			setOneClickable(j);
		}
	
	}
			

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.click_fast);       
		word = (TextView) findViewById(R.id.theWord);
		TextView explain = (TextView) findViewById(R.id.explaination);
		click1=(Button) findViewById(R.id.clickOnMe1);
		click2=(Button) findViewById(R.id.clickOnMe2);
		click3=(Button) findViewById(R.id.clickOnMe3);
		click4=(Button) findViewById(R.id.clickOnMe4);

		
		Animation myAnimation2 = AnimationUtils.loadAnimation(this, R.anim.animation3);
		explain.startAnimation(myAnimation2);
		
		
		if (i<10)
			i=i+10;

		
		number = Integer.toString(i);
		word.setText(number);
		word.setTextSize(60);
		word.setTextScaleX(1);

		Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.animation);
		word.startAnimation(myAnimation);
		

		click1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OnClickGeneral();

			}});
		
		click2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OnClickGeneral();

			}});
		
		click3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OnClickGeneral();

			}});
		
		click4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OnClickGeneral();

			}});
	}

	@Override
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

}
