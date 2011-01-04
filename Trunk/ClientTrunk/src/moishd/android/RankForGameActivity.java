package moishd.android;

import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class RankForGameActivity extends Activity{

	String game_type,authToken;
	String gray = "gray";
	String yellos = "yellow";
	ImageView rank1,rank2,rank3,rank4,rank5,done;
	Bitmap yellowStar,grayStar,thankYou;
	int star_rank;

	public void ColorStars(int star){
		rank1 = (ImageView) findViewById(R.id.rank1);
		rank2 = (ImageView) findViewById(R.id.rank2);
		rank3 = (ImageView) findViewById(R.id.rank3);
		rank4 = (ImageView) findViewById(R.id.rank4);
		rank5 = (ImageView) findViewById(R.id.rank5);
		done = (ImageView) findViewById(R.id.done);

		star_rank = star;
		switch(star){
		case 5:
			rank5.setImageBitmap(yellowStar);
		case 4:
			rank4.setImageBitmap(yellowStar);
		case 3:
			rank3.setImageBitmap(yellowStar);
		case 2:
			rank2.setImageBitmap(yellowStar);
		case 1:
			rank1.setImageBitmap(yellowStar);
			break;
		}


		switch(star){
		case 1:
			rank2.setImageBitmap(grayStar);
		case 2:
			rank3.setImageBitmap(grayStar);
		case 3:
			rank4.setImageBitmap(grayStar);
		case 4:
			rank5.setImageBitmap(grayStar);
			break;
		}

		done.setClickable(true);
		done.setVisibility(View.VISIBLE);
		done.setPadding(100, 20, 00, 0);

	}


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.rank_for_game_layout);

		game_type = getIntent().getStringExtra(IntentExtraKeysEnum.GameType.toString());
		authToken = getIntent().getStringExtra(IntentExtraKeysEnum.GoogleAuthToken.toString());

		yellowStar = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_star);
		grayStar = BitmapFactory.decodeResource(getResources(), R.drawable.gray_star);
		thankYou = BitmapFactory.decodeResource(getResources(), R.drawable.thank_you);
		TextView text = (TextView)findViewById(R.id.rank);
		rank1 = (ImageView) findViewById(R.id.rank1);
		rank2 = (ImageView) findViewById(R.id.rank2);
		rank3 = (ImageView) findViewById(R.id.rank3);
		rank4 = (ImageView) findViewById(R.id.rank4);
		rank5 = (ImageView) findViewById(R.id.rank5);
		done = (ImageView) findViewById(R.id.done);

		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf");
		text.setTypeface(fontName);



		rank1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ColorStars(1);

			}
		});	

		rank2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ColorStars(2);
			}
		});

		rank3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ColorStars(3);

			}
		});

		rank4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ColorStars(4);
			}
		});

		rank5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ColorStars(5);
			}
		});

		done.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MyCount count;
				count= new MyCount(2000,1000);
				count.start();
			}
		});



	}

	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}    
		public void onFinish() {
			boolean result = ServerCommunication.sendRankToServer(game_type,star_rank,authToken);
			//TODO check if the result is not an error
			finish();
		}    
		public void onTick(long millisUntilFinished) {
			done = (ImageView) findViewById(R.id.done);
			done.setPadding(50, 20, 50, 0);
			done.setImageBitmap(thankYou);

		}
	}
}
