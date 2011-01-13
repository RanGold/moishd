package moishd.android;

import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TopMoisherGeneralActivity extends Activity{
	ImageView mixing,simonPro,fastClick,trivia;

	int flag = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.top_moishers_general_layout);

		mixing = (ImageView) findViewById(R.id.mixing);
		simonPro = (ImageView) findViewById(R.id.simonpro);
		fastClick = (ImageView) findViewById(R.id.fastClick);
		trivia = (ImageView) findViewById(R.id.trivia);
		TextView text = (TextView) findViewById(R.id.text);


		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf");
		text.setTypeface(fontName);
		final Intent topMoishers = new Intent(this, TopMoishersActivity.class);


		mixing.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishers.putExtra(IntentExtraKeysEnum.GameType.toString(), IntentExtraKeysEnum.DareMixing.toString());
				startActivityAndFinish(topMoishers);				
			}
		});	


		simonPro.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishers.putExtra(IntentExtraKeysEnum.GameType.toString(), IntentExtraKeysEnum.DareSimonPro.toString());
				startActivityAndFinish(topMoishers);

			}
		});

		fastClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishers.putExtra(IntentExtraKeysEnum.GameType.toString(), IntentExtraKeysEnum.DareFastClick.toString());
				startActivityAndFinish(topMoishers);


			}
		});

		trivia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishers.putExtra(IntentExtraKeysEnum.GameType.toString(), IntentExtraKeysEnum.Truth.toString());
				startActivityAndFinish(topMoishers);

			}
		});
	}

	public void startActivityAndFinish(Intent intent){
		startActivity(intent);
		finish();
	}

}
