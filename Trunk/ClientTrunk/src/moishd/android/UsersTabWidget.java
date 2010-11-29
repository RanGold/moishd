package moishd.android;

import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class UsersTabWidget extends TabActivity {
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.users_tab_layout);

		//register to c2dm. once registered a registration_id will be received via c2dmreceiver 
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
		registrationIntent.putExtra("sender", "app.moishd@gmail.com");
		startService(registrationIntent);
		Log.d("TEST","Resgistering...");
        
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost();  // The activity TabHost
		TabHost.TabSpec spec;  // Resusable TabSpec for each tab
		Intent intent;  // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, AllOnlineUsersActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("allUsers").setIndicator("All Online Users", res.getDrawable(R.drawable.icon_users)).setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, NearbyUsersActivity.class);
		spec = tabHost.newTabSpec("nearbyUsers").setIndicator("Nearby Users", res.getDrawable(R.drawable.icon_users)).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(2);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.users_screen_menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	  switch (item.getItemId()) {
	  case R.id.trophies:
		  return true;
	  case R.id.welcomeScreen:
		  WelcomeScreenActivity.logout(null);
		  finish();
	    return true;
	  default:
	    return super.onOptionsItemSelected(item);
	  }
	}
	
}