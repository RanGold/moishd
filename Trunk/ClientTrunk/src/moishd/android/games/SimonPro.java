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

public class SimonPro extends Activity{

	Random random = new Random(); 
	String number;
	int arr[] = new int[5],counter=0,i,lastInt=-1,i1=0,i2=0,i3=0, indentify[] = {1,2,3};
	TextView word;
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
				if (arr[counter] == indentify[0]){
					messUp();
					counter++;
					if (counter == 5)
						rightAnswer();
						
			
				}
				else
					wrongAnswer();
			}
		});
		click2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (arr[counter] == indentify[1]){
					messUp();
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
				if (arr[counter] == indentify[2]){
					messUp();
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
		
		giveUp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Toast.makeText(SimonPro.this, 
						"loser :)", 
						Toast.LENGTH_SHORT).show();
				finish();
				
				
				
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
			giveUp.setVisibility(0);
			
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
		Toast.makeText(SimonPro.this, 
				"please try again", 
				Toast.LENGTH_SHORT).show();
		counter=0;
	}
	
	public void rightAnswer(){
		Toast.makeText(SimonPro.this, 
				"you made it!!!!!!", 
				Toast.LENGTH_SHORT).show();
		finish();
		
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
