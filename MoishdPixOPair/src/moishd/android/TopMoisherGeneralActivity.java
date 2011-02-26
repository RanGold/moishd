package moishd.android;

import moishd.common.GamesEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.MoishdPreferences;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TopMoisherGeneralActivity extends Activity{
	
	private Intent topMoishersIntent;
	private String authToken;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.top_moishers_general_layout);
		
		MoishdPreferences moishdPreferences = MoishdPreferences.getMoishdPreferences();
		moishdPreferences.setAvailableStatus(false);

		authToken = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());
		
		ImageView mixing = (ImageView) findViewById(R.id.gameIconMixing);
		ImageView simonPro = (ImageView) findViewById(R.id.gameIconSimonPro);
		ImageView fastClick = (ImageView) findViewById(R.id.gameIconFastClick);
		ImageView trivia = (ImageView) findViewById(R.id.gameIconTrivia);
		ImageView pixOPair = (ImageView) findViewById(R.id.gameIconPixOPair);
		TextView text = (TextView) findViewById(R.id.text);

		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/mailrays.ttf");
		text.setTypeface(fontName);
		
		topMoishersIntent = new Intent(this, TopMoishersActivity.class);
		topMoishersIntent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), authToken);
		
		mixing.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishersIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), GamesEnum.DareMixing);
				startActivity(topMoishersIntent);
			}
		});	

		simonPro.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishersIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), GamesEnum.DareSimonPro);
				startActivity(topMoishersIntent);
			}
		});

		fastClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishersIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), GamesEnum.DareFastClick);
				startActivity(topMoishersIntent);
			}
		});
		
		pixOPair.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishersIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), GamesEnum.DarePixOPair);
				startActivity(topMoishersIntent);
			}
		});

		trivia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				topMoishersIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), GamesEnum.Truth);
				startActivity(topMoishersIntent);
			}
		});
	}

	public void startActivityAndFinish(Intent intent){
		startActivity(intent);
		finish();
	}
	
	@Override 
	public void onBackPressed(){
		MoishdPreferences moishdPreferences = MoishdPreferences.getMoishdPreferences();
		moishdPreferences.setAvailableStatus(true);
		//TODO check if there isn't an error returned.
		finish();
		
	}

}
