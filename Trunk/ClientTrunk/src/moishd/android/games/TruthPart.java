package moishd.android.games;

import java.util.Random;

import moishd.android.R;
import moishd.android.ServerCommunication;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TruthPart extends Activity {
	int i;
		
	final String[][] questions ={{"Is tammy's last name is Dagan?","yes"}, 
			{"Is it 2012?","no"},
			{"Is this the first milestone?","yes"},
			{"Is the spoken language in France is hebrew?","no"},
			{"Do dogs have color sight?","no"},
			{"Is Bariloche the capital city of Argentina?", "no"},
			{"Is 'Step on no pets' a palindrome?", "yes"},
			{"Is 'Was it a rat I saw' a palindrome?", "yes"}
		};
	
	public void currectAnswer(){
	
		Toast.makeText(TruthPart.this, 
				"please wait for result", 
				Toast.LENGTH_LONG).show();
		String gameId = getIntent().getStringExtra(IntentExtraKeysEnum.PushGameId.toString());
		String authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
		ServerCommunication.sendWinToServer(gameId, authString);
		finish();
	}
		

	public void wrongAnswer(){
		Toast.makeText(TruthPart.this, 
				"please wait for result", 
				Toast.LENGTH_LONG).show();
		String gameId = getIntent().getStringExtra(IntentExtraKeysEnum.PushGameId.toString());
		String authString = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
		ServerCommunication.sendLoseToServer(gameId, authString);
		finish();
	}

	private void RunAnimations() {     
		Animation a = AnimationUtils.loadAnimation(this, R.anim.animation4);     
		a.reset();     
		ImageView iv = (ImageView) findViewById(R.id.no);     
		iv.clearAnimation();     
		iv.startAnimation(a);       
		a = AnimationUtils.loadAnimation(this, R.anim.animation4);     
		a.reset();     
		iv = (ImageView) findViewById(R.id.yes);     
		iv.clearAnimation();     
		iv.startAnimation(a);       
		a = AnimationUtils.loadAnimation(this, R.anim.animation3);     
		a.reset();     
		TextView tv1 = (TextView) findViewById(R.id.ourquestion);     
		tv1.clearAnimation();     
		tv1.startAnimation(a);
		a = AnimationUtils.loadAnimation(this, R.anim.animation5);     
		a.reset();     
		tv1 = (TextView) findViewById(R.id.question);     
		tv1.clearAnimation();     
		tv1.startAnimation(a);
 } 



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.truth);

		final TextView question = (TextView) findViewById(R.id.question);
		final ImageView yes = (ImageView) findViewById(R.id.yes);
		final ImageView no = (ImageView) findViewById(R.id.no);

		Random random = new Random();  
		i = random.nextInt(100);
		int j = questions.length;
		i= i % j;
		final String theQuestion = questions[i][0];
		
		question.setText(theQuestion);
		question.setTextScaleX(1);
		question.setTextSize(30);
		
		RunAnimations();


		yes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (questions[i][1].compareTo("yes")==0)
					currectAnswer();
				else 
					wrongAnswer();

			}
		});

		no.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (questions[i][1].compareTo("no")==0)
					currectAnswer();
				else 
					wrongAnswer();

			}
		});



	}
}