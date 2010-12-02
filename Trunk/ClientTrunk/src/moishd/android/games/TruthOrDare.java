package moishd.android.games;

import moishd.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TruthOrDare extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.truth_or_dare);   
		Button truth = (Button) findViewById(R.id.truth);
		Button dare = (Button) findViewById(R.id.dare);
		
		truth.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(TruthOrDare.this, TruthPart.class);
				startActivity(intent);
				TruthOrDare.this.finish();
			}
		});
		
		dare.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(TruthOrDare.this, SimonPro.class);
				startActivity(intent);
				TruthOrDare.this.finish();
			}
		});
		
		
	}

}
