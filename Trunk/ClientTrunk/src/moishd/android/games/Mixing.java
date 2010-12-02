package moishd.android.games;

import java.util.Random;

import moishd.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Mixing extends Activity{


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
			"computer"};


	public String mix(String str){

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

	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.mixing);       
		final Button MoishButton = (Button) findViewById(R.id.MoishButton);

		final EditText wordOfUser = (EditText) findViewById(R.id.wordOfUser);
		final TextView word = (TextView) findViewById(R.id.theWord);


		Random random = new Random();  
		int i = random.nextInt(100);
		i = i % ourDict.length;
		final String currentWord = ourDict[i];
		final String mixedWord = mix(currentWord);


		word.setText(mixedWord);
		word.setTextSize(60);
		word.setTextScaleX(1);
		Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.animation4);
		word.startAnimation(myAnimation);


		MoishButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				String wordByUser = wordOfUser.getText().toString();
				if (wordByUser.compareTo(currentWord)==0){
					Intent intent = new Intent(Mixing.this, SimonPro.class);
					startActivity(intent);
				}
				
				else {
					Toast.makeText(Mixing.this, 
							"please try again", 
							Toast.LENGTH_LONG).show();
				}


			}});



	}


}




