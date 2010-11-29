package moishd.android.games;

import moishd.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FastTyping2 extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fast_typing2);
		final Button playAgain = (Button) findViewById(R.id.PlayAgain);
      
		Toast.makeText(FastTyping2.this, 
				"Success! you get one point", 
				Toast.LENGTH_LONG).show();


		playAgain.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(FastTyping2.this, FastTyping.class);
				FastTyping2.this.startActivity(myIntent);
        
		}
		});
			
	}

}
