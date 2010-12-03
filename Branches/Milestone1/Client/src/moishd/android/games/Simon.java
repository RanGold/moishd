package moishd.android.games;

import java.util.Random;

import moishd.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Simon extends Activity{

	Random random = new Random(); 
	String number;
	int arr[] = new int[5],counter=0,i,lastInt=-1;
	TextView word;
	Button click1, click2, click3;

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
		word = (TextView) findViewById(R.id.theWord);
		word.setTextSize(80);
		MyCount count;
		
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
		

		count= new MyCount(8000,1000);
		count.start();
			
		
		click1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (arr[counter] == 1){
					counter++;
					if (counter ==5)
						rightAnswer();
				}
				else
					wrongAnswer();
			}
		});
		click2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (arr[counter] == 2){
					counter++;
					if (counter ==5)
						rightAnswer();
					
				}
				else
					wrongAnswer();
				
			}
		});		
		
		click3.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				if (arr[counter] == 3){
					counter++;
					if (counter ==5)
						rightAnswer();
				}
				else{
					counter=0;
					wrongAnswer();
				}	
			}
		});
		
		
		}
	
	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
			word.setTextSize(30);
			word.setText("go go go!!!");
			click1.setVisibility(0);		
			click2.setVisibility(0);
			click3.setVisibility(0);
			counter=0;
			RunAnimations();
		}    
		public void onTick(long millisUntilFinished) {
			if (counter != 5) {
				number = Integer.toString(arr[counter]);
				word.setText(number);
				counter++;
			}
			else {
				word.setTextSize(40);
				word.setText("wait...remember...and...");
		}
		}
		
	}
	
	public void wrongAnswer(){
		Toast.makeText(Simon.this, 
				"please try again", 
				Toast.LENGTH_SHORT).show();
		counter=0;
	}
	
	public void rightAnswer(){
		Toast.makeText(Simon.this, 
				"you made it!!!!!!", 
				Toast.LENGTH_SHORT).show();
		finish();
		
	}
	
	
}
