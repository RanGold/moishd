package moishd.android.games;

import java.util.Random;

import moishd.android.R;
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

	final String[][] questions ={{"Is tammy's last name is Dagan?","yes"}, 
			{"is it 2012?","no"},
			{"is this the first milestone","yes"},
			{"is the spoken language in France is hebrew?","no"}};
	public void currectAnswer(){
		Toast.makeText(TruthPart.this, 
				"good job", 
				Toast.LENGTH_LONG).show();
	}

	public void wrongAnswer(){
		Toast.makeText(TruthPart.this, 
				"wrong answer!", 
				Toast.LENGTH_LONG).show();
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
		final int i = random.nextInt(3);
		final String theQuestion = questions[i][0];
		
		question.setText(theQuestion);
		question.setTextScaleX(1);
		question.setTextSize(30);
		
		RunAnimations();
	//	Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.animation5);
//		question.startAnimation(myAnimation);


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