package moishd.android.games; /* This class is implemented in this package*/

import moishd.android.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/* This is an example of a game, which has a very simple logics- the player 
 * has to click once on one button from the two that appears on his screen.
 * When he does, the game is finished. 
 * If the user clicked on the other button, the game is also finished 
 * A short time after the click, the winner and loser will be announced.
 */
public class ExampleGame extends GameActivity{

	/* initializes the activity.*/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   

		/* sets the layout of the activity to be the one as specified in click_fast xml file.*/
		setContentView(R.layout.click_fast);       

		/* Identify click1,click2 as the button with the id 'clickOnMe1' and 'clickOnMe2'in the click_fast xml file.
		 * This enables the developer to do manipulation on these button, such as deciding what do to
		 * after the player clicks on it.*/
		Button click1=(Button) findViewById(R.id.clickOnMe1);
		Button click2=(Button) findViewById(R.id.clickOnMe2);

		/* Should be implemented every time a button is clickable.
		 * Defines a new click listener for the click1 button.
		 */
		click1.setOnClickListener(new OnClickListener() {

			/*This method decides how should the activity act when the player clicks on the button.*/
			public void onClick(View v) {

				/* Since the user has clicked on the button, the challenge is over and now we want to send
				 * a deciding request to Moish'd! server. This block is copied one by one from Moish'd API.
				 */		
				Win();
			}});
	
		
	
		 /* Defines a new click listener for the click2 button.
		 * This is the button that will lead to the user's loss.
		 */
		
		click2.setOnClickListener(new OnClickListener() {

			/*This method decides how should the activity act when the player clicks on the button.*/
			public void onClick(View v) {
				
				/* Since the user has clicked on the button, the challenge is over and now we want to send
				 * a deciding request to Moish'd! server which makes this user the loser. 
				 */
				Lose();

						}});
	}
	
}
