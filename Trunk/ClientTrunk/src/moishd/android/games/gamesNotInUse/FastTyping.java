package moishd.android.games.gamesNotInUse;

import java.util.Random;

import moishd.android.R;
import moishd.android.games.GameActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class FastTyping extends GameActivity {

	final String[] ourDict ={"dangerous", "lovely", "believe", "addictive", "energy", "parachute"};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.fast_typing);       
		final Button MoishButton = (Button) findViewById(R.id.MoishButton);
		final ImageView finish = (ImageView) findViewById(R.id.finishTheGame);
		final EditText wordOfUser = (EditText) findViewById(R.id.wordOfUser);
		final TextView word = (TextView) findViewById(R.id.theWord);


		Random random = new Random();  
		int i = random.nextInt(5);
		final String currentWord = ourDict[i];
		
		word.setText(currentWord);
		word.setTextSize(60);
		word.setTextScaleX(1);
		Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.animation4);
		word.startAnimation(myAnimation);
		word.startAnimation(myAnimation);
		
		finish.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					Lose();
			}
		});

		MoishButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String wordByUser = wordOfUser.getText().toString();
				if (wordByUser.equals(currentWord)){
					Win();
				}
				else {
					Toast.makeText(FastTyping.this, 
							"please try again", 
							Toast.LENGTH_LONG).show();
				}


			}});
		


	}


}


