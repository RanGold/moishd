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

import moishd.android.games.FastClickGameActivity;
import moishd.android.games.MixingGameActivity;
import moishd.android.games.SimonProGameActivity;
import moishd.android.games.TruthPartGameActivity;
import moishd.common.MoishdPreferences;
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
		Log.d("Tammy",action);
		
		Intent resultIntent = new Intent();
		resultIntent.putExtra(IntentExtraKeysEnum.PushGameId.toString(), game_id);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		boolean startIntent = true;

		if (action.equals(PushNotificationTypeEnum.CheckAlive.toString())){
			startIntent = false;
			ServerCommunication.sendAlive();
		}
		
		else if (action.equals(PushNotificationTypeEnum.GameCanceled.toString())){
			String initName = intent.getStringExtra(IntentExtraKeysEnum.InitName.toString());
			String recName = intent.getStringExtra(IntentExtraKeysEnum.RecName.toString());
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.GameCanceled.toString());
			resultIntent.putExtra(IntentExtraKeysEnum.InitName.toString(), initName);
			resultIntent.putExtra(IntentExtraKeysEnum.RecName.toString(), recName);
		}

		else if (action.equals(PushNotificationTypeEnum.GameOffer.toString())){
			String authTokenOfOpponent = intent.getStringExtra(IntentExtraKeysEnum.GoogleIdOfOpponent.toString());
			Log.d("Tammy","google id " + authTokenOfOpponent);
			String UserNickNameOfOpponent = intent.getStringExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString());
			Log.d("Tammy","user nick of opponent " + UserNickNameOfOpponent);
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.GameOffer.toString());
			resultIntent.putExtra(IntentExtraKeysEnum.GoogleIdOfOpponent.toString(), authTokenOfOpponent);
			resultIntent.putExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString(), UserNickNameOfOpponent);
		}

		else if (action.equals(PushNotificationTypeEnum.GameInvitation.toString())){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.GameInvitation.toString());
			String inviterName = intent.getStringExtra("InviterName");
			resultIntent.putExtra("Inviter", inviterName);
		}
		
		else if (action.equals(PushNotificationTypeEnum.GameDeclined.toString())){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.GameDeclined.toString());
		}
		else if (action.equals(PushNotificationTypeEnum.PlayerBusy.toString())){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			String opponent_nick_name = intent.getStringExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString()); 
			resultIntent.putExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString(), opponent_nick_name);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.PlayerBusy.toString());
		}
		else if (action.equals(PushNotificationTypeEnum.PlayerOffline.toString())){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			String opponent_nick_name = intent.getStringExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString()); 
			resultIntent.putExtra(IntentExtraKeysEnum.UserNickNameOfOpponent.toString(), opponent_nick_name);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.PlayerOffline.toString());
		}
		else if (action.substring(0,7).equals("Popular")){
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.PopularGame.toString());
			resultIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), action.substring(16));
		}
		else if(action.equals(PushNotificationTypeEnum.StartGameTruth.toString())){
			MoishdPreferences.setAvailableStatus(context, true);
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.StartGameTruth.toString());
			resultIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), action.substring(9));
		}

		else if(action.equals(PushNotificationTypeEnum.StartGameDareSimonPro.toString())||
				action.equals(PushNotificationTypeEnum.StartGameDareMixing.toString())||
				action.equals(PushNotificationTypeEnum.StartGameDareFastClick.toString())){
			MoishdPreferences.setAvailableStatus(context, true);
			resultIntent.setClass(this, AllOnlineUsersActivity.class);
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.StartGameDare.toString());
			resultIntent.putExtra(IntentExtraKeysEnum.GameType.toString(), action.substring(9));
		}
		else if(action.equals(PushNotificationTypeEnum.GameResult.toString())){
			MoishdPreferences.setAvailableStatus(context, false);
			
			resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), PushNotificationTypeEnum.GameResult.toString());
			Log.d("C2DM", resultIntent.getStringExtra(IntentExtraKeysEnum.PushAction.toString()));
			
			int points = Integer.valueOf((intent.getStringExtra(IntentExtraKeysEnum.Points.toString())));
			resultIntent.putExtra(IntentExtraKeysEnum.Points.toString(), points);

			String newRankString = intent.getStringExtra(IntentExtraKeysEnum.Rank.toString());
			if (newRankString != null){
				int newRank = Integer.valueOf(newRankString);
				resultIntent.putExtra(IntentExtraKeysEnum.Rank.toString(), newRank);
			}
			
			int numberOfTropies = Integer.valueOf(intent.getStringExtra(IntentExtraKeysEnum.NumberOfTrophies.toString()));	
			String trophiesString = intent.getStringExtra(IntentExtraKeysEnum.Trophies.toString());
			String nearByGame = intent.getStringExtra(IntentExtraKeysEnum.NearByGame.toString());
			resultIntent.putExtra(IntentExtraKeysEnum.Trophies.toString(), trophiesString);				
			resultIntent.putExtra(IntentExtraKeysEnum.NumberOfTrophies.toString(), numberOfTropies);
			resultIntent.putExtra(IntentExtraKeysEnum.NearByGame.toString(), nearByGame);
			

			String resultWithGameType = intent.getStringExtra(IntentExtraKeysEnum.PushGameResult.toString()); 
			int placeToCut = resultWithGameType.indexOf(":");
			String result = resultWithGameType.substring(0,placeToCut); //TODO: hila string index out of bounds
			String gameType = resultWithGameType.substring(placeToCut+1);

			resultIntent.putExtra(IntentExtraKeysEnum.PushGameResult.toString(), result);
		
			
			if (gameType.equals(IntentExtraKeysEnum.Truth.toString()))
				resultIntent.setClass(this, TruthPartGameActivity.class);
			else if (gameType.equals(IntentExtraKeysEnum.DareSimonPro.toString()))
				resultIntent.setClass(this, SimonProGameActivity.class);
			else if (gameType.equals(IntentExtraKeysEnum.DareMixing.toString()))
				resultIntent.setClass(this, MixingGameActivity.class);
			else if (gameType.equals(IntentExtraKeysEnum.DareFastClick.toString()))
				resultIntent.setClass(this, FastClickGameActivity.class);
		}
		Log.d("TEST", "action is " + action);
		
		if (startIntent){
			startActivity(resultIntent);
		}
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.d("TEST", "got Error");
		Intent resultIntent = new Intent();
		resultIntent.putExtra(IntentExtraKeysEnum.PushAction.toString(), IntentExtraKeysEnum.C2DMError.toString());
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		resultIntent.setClass(this, WelcomeScreenActivity.class);
	}


	@Override
	public void onRegistrered(Context context, String registration) {
		Log.d("TEST", "got RegistrationIntent"); 
		//DeviceRegistrar.registerWithServer(context, registration);
	}

	@Override
	public void onUnregistered(Context context) {
		Log.d("TEST", "got UnRegistrationIntent");
		DeviceRegistrar.unregisterWithServer(context);
	}
}
