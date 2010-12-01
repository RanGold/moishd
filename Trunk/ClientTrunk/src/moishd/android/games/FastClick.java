package moishd.android.games;

import java.util.Random;

import moishd.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FastClick extends Activity{
	Random random = new Random();  
	int i = random.nextInt(20);
	String number;



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.click_fast);       
		final ImageButton click = (ImageButton) findViewById(R.id.clickOnMe);
		final TextView word = (TextView) findViewById(R.id.theWord);

		number = Integer.toString(i);
		word.setText(number);
		word.setTextSize(60);
		word.setTextScaleX(1);
		Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
		word.startAnimation(myAnimation);


		click.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (i == 0) {
					Toast.makeText(FastClick.this, 
							"You made it!", 
							Toast.LENGTH_LONG).show();
				
				}
				else {
				i--;
				number = Integer.toString(i);
				word.setText(number);
			}
			}});
}


}
