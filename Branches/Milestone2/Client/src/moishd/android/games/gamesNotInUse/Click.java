package moishd.android.games.gamesNotInUse;

import java.util.Random;

import moishd.android.R;
import moishd.android.games.Mixing;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Click extends Activity{
	Random random = new Random();  
	int i;
	String number;
	int arr[] = new int[10];
	int counter=0;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.click);       
		final Button click1 = (Button) findViewById(R.id.clickOnMe1);
		final Button click2 = (Button) findViewById(R.id.clickOnMe2);
		final Button click3 = (Button) findViewById(R.id.clickOnMe3);
		final TextView word = (TextView) findViewById(R.id.theWord);


		for (int j=0; j<10; j++){
			i = random.nextInt(100);
			i = i % 4;
			
			while (i==0) {
				i = random.nextInt(100);
				i = i % 4;
			}
			arr[j]=i;
		}
		
		
		number = Integer.toString(arr[counter]);
		word.setText(number);
		word.setTextSize(60);
		word.setTextScaleX(1);
		Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
		word.startAnimation(myAnimation);
		
		
		click1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (arr[counter] == 1){
					counter++;
					if (counter <10){
						number = Integer.toString(arr[counter]);
						word.setText(number);
					}
					else {
						Intent intent = new Intent(Click.this, Mixing.class);
						startActivity(intent);
					}
				}
				else {
					//wrong.setText("Wrong!");
				//	wrong.setText("");
					
				}
			}
		});
		
		click2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (arr[counter] == 2){
					counter++;
					if (counter <10){
						number = Integer.toString(arr[counter]);
						word.setText(number);
					}
					else {
						Intent intent = new Intent(Click.this, Mixing.class);
						startActivity(intent);
					}
				}
				else {
					//wrong.setText("Wrong!");
					//wrong.setText("");
					
				}
			}
		});
		click3.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				if (arr[counter] == 3){
					counter++;
					if (counter <10){
						number = Integer.toString(arr[counter]);
						word.setText(number);
					}
					else {
						Intent intent = new Intent(Click.this, Mixing.class);
						startActivity(intent);
						}
					}
				else {
	//				wrong.setText("Wrong!");
		//			wrong.setText("");
					
				}
				
				}
			});	
		}



}
