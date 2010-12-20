package moishd.android.games;



import java.util.Random;

import moishd.android.R;
import moishd.android.ServerCommunication;
import moishd.common.ActionByPushNotificationEnum;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SimonPro extends GameActivity{

	Random random = new Random(); 
	String number;
	int arr[] = new int[5],counter=0,i,lastInt=-1,i1=0,i2=0,i3=0, indentify[] = {1,2,3},tr=100;
	TextView word, wrong, explain,tries; 
	Button click1, click2, click3, giveUp;

	private void RunAnimations() {     
		Animation a = AnimationUtils.loadAnimation(this, R.anim.animation4);     
		a.reset();     
		Button b = (Button) findViewById(R.id.clickOnMe1);     
		b.clearAnimation();     
		b.startAnimation(a);       
		b = (Button) findViewById(R.id.clickOnMe2);          
		b.clearAnimation();     
		b.startAnimation(a);            
		b = (Button) findViewById(R.id.clickOnMe3);          
		b.clearAnimation();     
		b.startAnimation(a);       
 } 

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.click_sequence);
		click1 = (Button) findViewById(R.id.clickOnMe1);
		click2 = (Button) findViewById(R.id.clickOnMe2);
		click3 = (Button) findViewById(R.id.clickOnMe3);
		giveUp = (Button) findViewById(R.id.giveUp);
		tries = (TextView) findViewById(R.id.tries);
		explain = (TextView) findViewById(R.id.explain);


		Animation anim = AnimationUtils.loadAnimation(this, R.anim.animation5);
		explain.startAnimation(anim);
		explain.clearAnimation();
		
		
		word = (TextView) findViewById(R.id.theWord);
		word.setTextSize(25);
		word.startAnimation(anim);
		
		
		//fill the array in random ints
		for (int j=0; j<5; j++){
			
			i = random.nextInt(100);
			i = i % 4;
			
			while (i==0 || i==lastInt) {
				i = random.nextInt(100);
				i = i % 4;
			}
			
			lastInt=i;
			arr[j]=i;
		}
		


		word.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				word.setClickable(false);
				MyCount count;
				count= new MyCount(8000,1000);
				count.start();
			}
		});
			
		
		click1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (arr[counter] == indentify[0]){
						counter++;
					if (counter !=5)
						messUp();
					else
						rightAnswer();
					}
				else
					wrongAnswer();
			}
		});
		click2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (arr[counter] == indentify[1]){
						counter++;
						if (counter !=5)
							messUp();
						else
							rightAnswer();
				}
				else
					wrongAnswer();
		
			}
		});		
		
		click3.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				if (arr[counter] == indentify[2]){
					counter++;
					if (counter !=5)
						messUp();
					else
						rightAnswer();
					}
					else
						wrongAnswer();
		
			}
		});
		
		giveUp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String gameId = getIntent().getStringExtra(IntentExtraKeysEnum.PushGameId.toString());
				String authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
				String gameType = getIntent().getStringExtra(IntentExtraKeysEnum.GameType.toString());
				ServerCommunication.sendLoseToServer(gameId, authString, gameType);
				finish();
			}
		});
		
		
		}
	
	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
			counter=0;
			word.setTextSize(30);
			word.setPadding(80, 0, 0, 0);
			word.setText("go go go!!!");
			click1.setVisibility(0);		
			click2.setVisibility(0);
			click3.setVisibility(0);
			RunAnimations();
			giveUp.setVisibility(0);
			tries.setText("number of tries:" + tr);
			tries.setVisibility(0);
			
		}    
		public void onTick(long millisUntilFinished) {
			if (counter != 5) {
				number = Integer.toString(arr[counter]);
				word.setPadding(130, 0, 0, 0);
				word.setTextSize(80);
				word.setText(number);
				counter++;
			}
			else {
				word.setTextSize(40);
				word.setPadding(50, 0, 0, 0);
				word.setText("wait...remember...and...");
		}
		}
		
	}
	
	
/*	public class LittleCount extends CountDownTimer {
		public LittleCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
			wrong = (TextView) findViewById(R.id.wrong);
			if (tr>0)
				wrong.setText("try again from the start");
			else 
				SimonPro.this.finish();
		}
		
		@Override
		public void onTick(long arg0) {
			counter=0;
			wrong = (TextView) findViewById(R.id.wrong);
			tries = (TextView) findViewById(R.id.tries);
			Animation anim = AnimationUtils.loadAnimation(SimonPro.this, R.anim.animation3);
			wrong.setVisibility(0);
			tries.setText("number of tries:" + tr);
			if (tr > 0)
				wrong.setText("WRONG!!!");
			else {
				wrong.setTextSize(40);
				wrong.setText("you lost!");
			}
				
			wrong.startAnimation(anim);
		}
	}*/

	
	public void wrongAnswer(){
		counter=0;
		tr--;
		//LittleCount c1 = new LittleCount(2000,1000);
		//c1.start();
		//wrong = (TextView) findViewById(R.id.wrong);
		wrong = (TextView) findViewById(R.id.wrong);
		tries = (TextView) findViewById(R.id.tries);
		Animation anim = AnimationUtils.loadAnimation(SimonPro.this, R.anim.animation3);
		wrong.setVisibility(0);
		tries.setText("number of tries:" + tr);
		if (tr > 0)
			wrong.setText("WRONG!!!");
		else {
			Lose();
	
		}
		wrong.startAnimation(anim);
		wrong.setVisibility(View.INVISIBLE);

	}
	
	public void rightAnswer(){
		click1 = (Button) findViewById(R.id.clickOnMe1);
		click2 = (Button) findViewById(R.id.clickOnMe2);
		click3 = (Button) findViewById(R.id.clickOnMe3);
		
		click1.setClickable(false);
		click2.setClickable(false);
		click3.setClickable(false);
		Win();
		
	}
	public void messUp(){	

		click1 = (Button) findViewById(R.id.clickOnMe1);
		click2 = (Button) findViewById(R.id.clickOnMe2);
		click3 = (Button) findViewById(R.id.clickOnMe3);
		
		for(int k=0; k<3;k++){	
			i = random.nextInt(100);
			i = i % 4;
			
			
			while (i==0 ||(i==1 && i1 == 1 ) || (i==2 && i2 ==1)|| (i==3 && i3 == 1) ) {
				i = random.nextInt(100);
				i = i % 4;
				//flags
				}
			
			if (i==1)
				i1=1;
			else if (i==2)
				i2=1;
			else
				i3=1;

			indentify[k]=i;
		}
		
		i1=i2=i3=0;
		
		click1.setText(Integer.toString(indentify[0]));
		click2.setText(Integer.toString(indentify[1]));
		click3.setText(Integer.toString(indentify[2]));

	}
	
	
}
