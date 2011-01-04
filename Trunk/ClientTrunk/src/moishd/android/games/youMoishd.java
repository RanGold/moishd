package moishd.android.games;

import moishd.android.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class youMoishd extends WinnerAndLoserActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.you_moishd);
		
		ImageView moish = (ImageView) findViewById(R.id.hands);
		
		moish.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//here to open all users list
				checkIfRankNeeded();


			}});
	}
}
