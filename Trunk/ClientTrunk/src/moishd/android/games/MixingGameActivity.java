package moishd.android.games;

import java.util.Random;

import moishd.android.R;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MixingGameActivity extends GameActivity{
	TextView TheWordIs, word;


	final String[] ourDict ={"dangerous", 
			"lovely", 
			"believe", 
			"addictive", 
			"energy", 
			"parachute",
			"superman",
			"surprise",
			"dancing",
			"dynamite",
			"celebrate",
			"standing",
			"finger",
			"socks",
			"glasses",
			"cookie",
			"bottle",
			"computer",
			"clock",
			"experience"};


	public String mix1(String str){

		int length = str.length();
		int middle = length/2;
		if (length%2 == 0)
			middle--;

		int offset = 0;

		String result = "";
		for (int j=0; j<length;j++){
			if (middle+ offset == length)
				break;

			if (j==0) {
				result = result + str.charAt(middle);
				j++;
			}
			else {
				result = result + str.charAt(middle+offset);
				if (result.length() < length){
					result = result + str.charAt(middle-offset);


				}
			}
			offset++;
		}

		return result;

	}
	
	public String mix2(String str){
		int length = str.length();
		String result = "";
		
			for (int j=0; j<length;j=j+2) {
				result = result + str.charAt(j);
			}
			for (int j=1; j<length;j=j+2) {
				result = result + str.charAt(j);
			}

		return result;
	}
	
	public String mix3(String str){
		String result="";
		int length = str.length();
		int[] arr = new int[length];
		
		Random random = new Random();  
		
		for (int j=0;j<length;j++){
			int i = random.nextInt(100);
			i = i % length;
			
			while (arr[i] == 1){
				i = random.nextInt(100);
				i = i % length;
			}
			arr[i]=1;
			
			result = result+str.charAt(i);
		}
	
		return result;
		
	}


	
	

//	/** Called when the activity is first created. 
//	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);   
		setContentView(R.layout.mixing);       
		final Button MoishButton = (Button) findViewById(R.id.MoishButton);

		final EditText wordOfUser = (EditText) findViewById(R.id.wordOfUser);
		word = (TextView) findViewById(R.id.theWord);
		
		TheWordIs = (TextView) findViewById(R.id.TheWordIs);
		Animation anim = AnimationUtils.loadAnimation(MixingGameActivity.this, R.anim.animation5);
		TheWordIs.startAnimation(anim);

		LittleCount count = new LittleCount(3000,1000);
		count.start();
		Random random = new Random();  
		int i = random.nextInt(100);
		i = i % ourDict.length;
		final String currentWord = ourDict[i];
		
		String mixedWord;
		int k = random.nextInt(100);
		k = k % 3 +1;
		
		if (k==1)
			mixedWord= mix1(currentWord);
		else if (k==2)
			mixedWord= mix2(currentWord);
		else
			mixedWord= mix3(currentWord);
			
		word.setText(mixedWord);
		word.setTextSize(50);
		word.setTextScaleX(1);


		MoishButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				String wordByUser = wordOfUser.getText().toString();
				if (wordByUser.equals(currentWord)){
						Win();
						MoishButton.setClickable(false);
						}
				
				else {
					Toast.makeText(MixingGameActivity.this, 
							"please try again", 
							Toast.LENGTH_LONG).show();
				}


			}});



	}
	
	public class LittleCount extends CountDownTimer {
		public LittleCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
			word = (TextView) findViewById(R.id.theWord);
			Animation myAnimation = AnimationUtils.loadAnimation(MixingGameActivity.this, R.anim.animation4);
			word.setVisibility(0);
			word.startAnimation(myAnimation);


		}
		
		@Override
		public void onTick(long arg0) {
			
		}
	}

	




}




