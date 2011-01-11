package moishd.android.games;

import java.util.Random;

import moishd.android.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class TruthPartGameActivity extends GameActivity {
	int i;
		
	final String[][] questions ={{"Is Tammy's last name Dagan?","yes"}, 
			{"Is it 2012?","no"},
			{"Is this the first milestone?","no"},
			{"Is the spoken language in France is hebrew?","no"},
			{"Do dogs have color sight?","no"},
			{"Is Bariloche the capital city of Argentina?", "no"},
			{"Is 'Step on no pets' a palindrome?", "yes"},
			{"Is 'Was it a rat I saw' a palindrome?", "yes"}
		};
	


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
		final TextView ourQuestion = (TextView) findViewById(R.id.ourquestion);
		final ImageView yes = (ImageView) findViewById(R.id.yes);
		final ImageView no = (ImageView) findViewById(R.id.no);

		
		Random random = new Random();  
		i = random.nextInt(100);
		int j = questions.length;
		i= i % j;
		final String theQuestion = questions[i][0];
		
		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/COOPBL.ttf");
		Typeface fontName2 = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf");
		question.setTypeface(fontName);
		ourQuestion.setTypeface(fontName2);
		
		question.setText(theQuestion);
		question.setTextScaleX(1);
		question.setTextSize(30);
		
		RunAnimations();


		yes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (questions[i][1].compareTo("yes")==0)
					Win();
				else 
					Lose();

			}
		});

		no.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (questions[i][1].compareTo("no")==0)
					Win();
				else 
					Lose();

			}
		});



	}
	
	
	
}