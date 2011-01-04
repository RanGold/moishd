package moishd.android.games;

import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
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
		if (points==1){
			text.setText("Great job! You've Moish'd the opponent and won " + points + " point!");
		}
		else{
			text.setText("Great job! You've Moish'd the opponent and won " + points + " points!");
		}

		moishdHand.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//here to open all users list
				checkIfRankNeeded();


			}});
	}
}
