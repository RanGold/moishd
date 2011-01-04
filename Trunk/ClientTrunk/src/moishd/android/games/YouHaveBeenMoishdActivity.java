package moishd.android.games;

import moishd.android.R;
import moishd.common.IntentExtraKeysEnum;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class YouHaveBeenMoishdActivity extends WinnerAndLoserActivity{


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.you_have_been_moishd);

		int points = getIntent().getIntExtra(IntentExtraKeysEnum.Points.toString(), 0);

		ImageView moishdHand = (ImageView) findViewById(R.id.hands);
		TextView text = (TextView) findViewById(R.id.youHaveBeenMoishdText);

		if (points > 0){
			if (points == 1){
				text.setText("Oh no! You have been Moish'd! But hey, you received " + points + " point!");
			}
			else{
				text.setText("Oh no! You have been Moish'd! But hey, you got " + points + " points!");
			}
		}
		else{
			text.setText("Oh no! You have been Moish'd!");
		}

		moishdHand.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkIfRankNeeded();
			}});



	}

}
