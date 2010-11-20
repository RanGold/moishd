package moishd.android;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class HelloTabWidget extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

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
}