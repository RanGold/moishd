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

import moishd.android.games.SimonPro;
import moishd.common.ActionByPushNotificationEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.PushNotificationTypeEnum;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.c2dm.C2DMBaseReceiver;

public class C2DMReceiver extends C2DMBaseReceiver {

	static final String SENDER_ID = "app.moishd@gmail.com";

	public C2DMReceiver() {
		super(SENDER_ID);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {

		Log.d("TEST", "got Message"); 

		String action = intent.getStringExtra(IntentExtraKeysEnum.PushAction.toString());
		String game_id = intent.getStringExtra(IntentExtraKeysEnum.PushGameId.toString());

		Intent resultIntent = new Intent();
		resultIntent.putExtra(IntentExtraKeysEnum.PushGameId.toString(), game_id);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (action.equals(PushNotificationTypeEnum.GameInvitation.toString())){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), ActionByPushNotificationEnum.GameInvitation.toString());
		}
		else if (action.equals(PushNotificationTypeEnum.GameDeclined.toString())){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), ActionByPushNotificationEnum.GameDeclined.toString());
		}
		else if(action.equals(PushNotificationTypeEnum.StartGameTruth.toString())){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), ActionByPushNotificationEnum.StartGameTruth.toString());
		}
		
		else if(action.equals(PushNotificationTypeEnum.StartGameDare.toString())){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), ActionByPushNotificationEnum.StartGameDare.toString());
		}
		else if(action.equals(PushNotificationTypeEnum.GameResult.toString())){
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), ActionByPushNotificationEnum.GameResult.toString());
			String result = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString());
			resultIntent.putExtra(IntentExtraKeysEnum.PushGameResult.toString(), result);

			if (result.equals(PushNotificationTypeEnum.Won.toString())){
				resultIntent.setClass(this, AllOnlineUsersActivity.class);
			}
			else{
				resultIntent.setClass(this, SimonPro.class);
			}
		}
		startActivity(resultIntent);
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.d("TEST", "got Error"); 
	}

	@Override
	public void onRegistrered(Context context, String registration) {
		Log.d("TEST", "got RegistrationIntent"); 
		DeviceRegistrar.registerWithServer(context, registration);
	}
	
	@Override
	public void onUnregistered(Context context) {
		Log.d("TEST", "got UnRegistrationIntent");
		DeviceRegistrar.unregisterWithServer(context);
	}
}
