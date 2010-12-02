/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package moishd.android;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.c2dm.C2DMBaseReceiver;

public class C2DMReceiver extends C2DMBaseReceiver {

	static final String SENDER_ID = "app.moishd@gmail.com";

	public C2DMReceiver() {
		super(SENDER_ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMessage(Context context, Intent intent) {

		Log.d("TEST", "got Message"); 

		String action = intent.getStringExtra("Action");
		String game_id = intent.getStringExtra("GameId");

		Intent usersTabIntent = new Intent();
		usersTabIntent.setClass(getApplicationContext(), AllOnlineUsersActivity.class);
		usersTabIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		usersTabIntent.putExtra("push_game_id", game_id);

		if (action.equals("GameInvitation")){
			usersTabIntent.putExtra("Action", "game_invitation");
		}
		else if (action.equals("GameDeclined")){
			usersTabIntent.putExtra("Action", "game_declined");
		}
		else if(action.equals("StartGame")){
			usersTabIntent.putExtra("Action", "game_start");
		}
		else if(action.equals("GameResult")){
			usersTabIntent.putExtra("Action", "game_result");
			String result = intent.getStringExtra("Result");
			usersTabIntent.putExtra("Result", result);
		}
		startActivity(usersTabIntent);


		//		Log.d("TEST", "got Message"); 
		//		Log.d("TEST","one of the payloads is: "+intent.getStringExtra("payload2"));
		//
		//		//playing with notifications
		//		String ns = Context.NOTIFICATION_SERVICE;
		//		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		//		CharSequence tickerText = "YESHHHHH - incoming PUSH msg";
		//		long when = System.currentTimeMillis();
		//		Notification notification = new Notification(R.drawable.icon,tickerText, when);
		//		Context contextt = getApplicationContext();
		//		CharSequence contentTitle = "My notification";
		//		CharSequence contentText = "Hello World!";
		//		Intent notificationIntent = new Intent(this, UsersTabWidget.class);
		//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		//
		//		notification.setLatestEventInfo(contextt, contentTitle, contentText, contentIntent);
		//		final int HELLO_ID = 1;
		//
		//		mNotificationManager.notify(HELLO_ID, notification);

	}

	@Override
	public void onError(Context context, String errorId) {
		Log.d("TEST", "got Error"); 
		// TODO Auto-generated method stub
	}

	@Override
	public void onRegistrered(Context context, String registration) {
		Log.d("TEST", "got RegistrationIntent"); 
		DeviceRegistrar.registerWithServer(context, registration);

	}

	/*
    public C2DMReceiver() {
        super(DeviceRegistrar.SENDER_ID);
    }

    @Override
    public void onRegistrered(Context context, String registration) {
      //  DeviceRegistrar.registerWithServer(context, registration);
    }

    @Override
    public void onUnregistered(Context context) {
        SharedPreferences prefs = Prefs.get(context);
        String deviceRegistrationID = prefs.getString("deviceRegistrationID", null);
        DeviceRegistrar.unregisterWithServer(context, deviceRegistrationID);
    }

    @Override
    public void onError(Context context, String errorId) {
        context.sendBroadcast(new Intent("com.google.ctp.UPDATE_UI"));
    }

    @Override
    public void onMessage(Context context, Intent intent) {/*
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String url = (String) extras.get("url");
            String title = (String) extras.get("title");
            String sel = (String) extras.get("sel");
            String debug = (String) extras.get("debug");

            if (debug != null) {
                // server-controlled debug - the server wants to know
                // we received the message, and when. This is not user-controllable,
                // we don't want extra traffic on the server or phone. Server may
                // turn this on for a small percentage of requests or for users
                // who report issues.
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(AppEngineClient.BASE_URL + "/debug?id="
                        + extras.get("collapse_key"));
                // No auth - the purpose is only to generate a log/confirm delivery
                // (to avoid overhead of getting the token)
                try {
                    client.execute(get);
                } catch (ClientProtocolException e) {
                    // ignore
                } catch (IOException e) {
                    // ignore
                }
            }

            if (title != null && url != null && url.startsWith("http")) {
                SharedPreferences settings = Prefs.get(context);
                Intent launchIntent = LauncherUtils.getLaunchIntent(context, title, url, sel);

                // Notify and optionally start activity
                if (settings.getBoolean("launchBrowserOrMaps", true) && launchIntent != null) {
                    LauncherUtils.playNotificationSound(context);
                    context.startActivity(launchIntent);
                } else {
                    if (sel != null && sel.length() > 0) {  // have selection
                        LauncherUtils.generateNotification(context, sel,
                                context.getString(R.string.copied_desktop_clipboard), launchIntent);
                    } else {
                        LauncherUtils.generateNotification(context, url, title, launchIntent);
                    }
                }

                // Record history (for link/maps only)
                if (launchIntent != null && launchIntent.getAction().equals(Intent.ACTION_VIEW)) {
                    HistoryDatabase.get(context).insertHistory(title, url);
                }
            }
        }
	 */
}
