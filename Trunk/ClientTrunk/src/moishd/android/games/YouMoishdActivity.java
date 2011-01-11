package moishd.android.games;

import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class YouMoishdActivity extends WinnerAndLoserActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.you_moishd);

		int points = getIntent().getIntExtra(IntentExtraKeysEnum.Points.toString(), -1);

		ImageView moishdHand = (ImageView) findViewById(R.id.hands);
		TextView text = (TextView) findViewById(R.id.youMoishdText);
		TextView bottomText  = (TextView) findViewById(R.id.clickOnHand);
		
		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/COOPBL.ttf");
		text.setTypeface(fontName);
		bottomText.setTypeface(fontName);
		
		checkIfGameIsNearBy();
		
		if (points==1){
			text.setText("Great job! You've Moish'd the opponent and won " + points + " point! " + addToMesseage);
		}
		else{
			text.setText("Great job! You've Moish'd the opponent and won " + points + " points! " + addToMesseage);
		}
		if (needToChangeTextSize){
			text.setTextSize(20);
		}
		moishdHand.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
				checkIfRankNeeded();
		


			}});
	}
}
