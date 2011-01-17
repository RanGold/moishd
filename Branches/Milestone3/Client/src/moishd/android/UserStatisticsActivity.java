package moishd.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.client.dataObjects.ClientTrophy;
import moishd.client.dataObjects.ClientUserGameStatistics;
import moishd.client.dataObjects.TrophiesEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.MoishdPreferences;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserStatisticsActivity extends Activity {

	private Map<String, Boolean> allTrophiesMap;
	private ArrayList<ClientTrophy> userTrophiesToDetailedList;
	static List<Trophy> trophiesList;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
		
		ClientMoishdUser moishdUser = (ClientMoishdUser) getIntent().getExtras().get(IntentExtraKeysEnum.MoishdUser.toString());
		ClientUserGameStatistics userGameStatistics = moishdUser.getStats();
		
		MoishdPreferences moishdPreferences = MoishdPreferences.getMoishdPreferences();
		moishdPreferences.setAvailableStatus(false);
		
		setContentView(R.layout.users_statistics_layout);
		TextView header = (TextView) findViewById(R.id.games_statistics_header);

		header.setText(moishdUser.getUserNick() + " - Game Statistics");
		header.setTextSize(17);
		header.setPadding(0, 5, 0, 5);
		header.setTextColor(Color.RED);

		TextView gameStatistics = (TextView) findViewById(R.id.games_statistics);

		gameStatistics.setText(
				"Total games played: " + userGameStatistics.getGamesPlayed() + "\n" + 
				"Games won: " + userGameStatistics.getGamesWon() + "\n" + 
				"Games won in a row: " + userGameStatistics.getGamesWonInARow() + "\n" +
				"Points: " + userGameStatistics.getPoints() + "\n" +
				"Rank:");
		
		ImageView userRankPic = (ImageView) findViewById(R.id.rankPic);
		setRankIcon(userGameStatistics, userRankPic);
		
		List<TrophiesEnum> userTrophies = moishdUser.getTrophies();		
		userTrophiesToDetailedList = new ArrayList<ClientTrophy>();
		convertUserTrophiesToDetailedList(userTrophies);

		populateNeedToAchieveTrophiesMap();

		for (int i = 0; i < userTrophies.size(); i++){
			String currentTrophy = userTrophies.get(i).toString();
			allTrophiesMap.put(currentTrophy, true);
		}
		
		createTrophyListFromMap();

		ListView list = (ListView) findViewById(R.id.trophiesList);
		list.setAdapter(new EfficientAdapter(this));
	}

	private void setRankIcon(ClientUserGameStatistics userGameStatistics, ImageView userRankPic) {
		switch(userGameStatistics.getRank()){
		case 0:
			userRankPic.setImageResource(R.drawable.rank_0);
			break;
		case 1:
			userRankPic.setImageResource(R.drawable.rank_1);
			break;
		case 2:
			userRankPic.setImageResource(R.drawable.rank_2);
			break;
		case 3:
			userRankPic.setImageResource(R.drawable.rank_3);
			break;
		case 4:
			userRankPic.setImageResource(R.drawable.rank_4);
			break;
		case 5:
			userRankPic.setImageResource(R.drawable.rank_5);
			break;
		}
	}

	private void populateNeedToAchieveTrophiesMap(){

		allTrophiesMap = new TreeMap<String, Boolean>();
		allTrophiesMap.put(TrophiesEnum.FirstTime.toString(), false);
		allTrophiesMap.put(TrophiesEnum.TinyMoisher.toString(), false);
		allTrophiesMap.put(TrophiesEnum.MiniMoisher.toString(), false);
		allTrophiesMap.put(TrophiesEnum.MasterMoisher.toString(), false);
		allTrophiesMap.put(TrophiesEnum.SuperMoisher.toString(), false);
		allTrophiesMap.put(TrophiesEnum.MegaMoisher.toString(), false);
		allTrophiesMap.put(TrophiesEnum.TenInARow.toString(), false);
		allTrophiesMap.put(TrophiesEnum.TwentyInARow.toString(), false);
		allTrophiesMap.put(TrophiesEnum.GoogleTrophy.toString(), false);
	}
	
	private void convertUserTrophiesToDetailedList(List<TrophiesEnum> userTrophies){
		
		TrophiesEnum currentTrophy;
		for (int i=0; i < userTrophies.size(); i++){
			currentTrophy = userTrophies.get(i);
			switch(currentTrophy){
			case BestFriends:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.BestFriends.toString(), TrophiesEnum.BestFriends.getTrophyPoints()));
				break;
			case FaceOff:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.FaceOff.toString(), TrophiesEnum.FaceOff.getTrophyPoints()));
				break;
			case FirstTime:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.FirstTime.toString(), TrophiesEnum.FirstTime.getTrophyPoints()));
				break;
			case MasterMoisher:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.MasterMoisher.toString(), TrophiesEnum.MasterMoisher.getTrophyPoints()));
				break;
			case MegaMoisher:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.MegaMoisher.toString(), TrophiesEnum.MegaMoisher.getTrophyPoints()));
				break;
			case MiniMoisher:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.MiniMoisher.toString(), TrophiesEnum.MiniMoisher.getTrophyPoints()));
				break;
			case SuperMoisher:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.SuperMoisher.toString(), TrophiesEnum.SuperMoisher.getTrophyPoints()));
				break;
			case TenInARow:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.TenInARow.toString(), TrophiesEnum.TenInARow.getTrophyPoints()));
				break;
			case TinyMoisher:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.TinyMoisher.toString(), TrophiesEnum.TinyMoisher.getTrophyPoints()));
				break;
			case TwentyInARow:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.TwentyInARow.toString(), TrophiesEnum.TwentyInARow.getTrophyPoints()));
				break;
			case GoogleTrophy:
				userTrophiesToDetailedList.add(new ClientTrophy(TrophiesEnum.GoogleTrophy.toString(), TrophiesEnum.GoogleTrophy.getTrophyPoints()));
				break;
			}
			
		}
	}
	
	private void createTrophyListFromMap() {
		
		trophiesList = new ArrayList<Trophy>();
		trophiesList.add(new Trophy(TrophiesEnum.FirstTime, allTrophiesMap.get(TrophiesEnum.FirstTime.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.TinyMoisher, allTrophiesMap.get(TrophiesEnum.TinyMoisher.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.MiniMoisher, allTrophiesMap.get(TrophiesEnum.MiniMoisher.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.MasterMoisher, allTrophiesMap.get(TrophiesEnum.MasterMoisher.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.SuperMoisher, allTrophiesMap.get(TrophiesEnum.SuperMoisher.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.MegaMoisher, allTrophiesMap.get(TrophiesEnum.MegaMoisher.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.TenInARow, allTrophiesMap.get(TrophiesEnum.TenInARow.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.TwentyInARow, allTrophiesMap.get(TrophiesEnum.TwentyInARow.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.GoogleTrophy, allTrophiesMap.get(TrophiesEnum.GoogleTrophy.toString())));
	}

	private class Trophy{

		TrophiesEnum trophyName;
		boolean isEnabled;

		public Trophy(TrophiesEnum supermoisher, boolean isEnabled){
			this.trophyName = supermoisher;
			this.isEnabled = isEnabled;
		}
	}

	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		private Bitmap trophyEnabled;
		private Bitmap trophyDisabled;
		private Bitmap trophyLocked;
		private Bitmap trophyUnlocked;

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);

			// Icons bound to the rows.
			trophyEnabled = BitmapFactory.decodeResource(context.getResources(), R.drawable.trophy_enabled);
			trophyDisabled = BitmapFactory.decodeResource(context.getResources(), R.drawable.trophy_disabled);
			trophyLocked = BitmapFactory.decodeResource(context.getResources(), R.drawable.locked_torphy);
			trophyUnlocked = BitmapFactory.decodeResource(context.getResources(), R.drawable.unlocked_trophy);

		}

		public int getCount() {
			return trophiesList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.trophies_list_item, null);

				holder = new ViewHolder();
				holder.trophyNameAndDesc = (TextView) convertView.findViewById(R.id.trophyNameAndDesc);
				holder.trophy = (ImageView) convertView.findViewById(R.id.trophyPic);
				holder.locked = (ImageView) convertView.findViewById(R.id.trophyLockedPic);

				convertView.setTag(holder);
			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.trophyNameAndDesc.setText(trophiesList.get(position).trophyName.getTrophyName() + " - \n" + trophiesList.get(position).trophyName.getTrophyDescription());	
			if (trophiesList.get(position).isEnabled){
				holder.trophy.setImageBitmap(trophyEnabled);
				holder.locked.setImageBitmap(trophyUnlocked);
			}
			else{
				holder.trophy.setImageBitmap(trophyDisabled);
				holder.locked.setImageBitmap(trophyLocked);
			}
			
			return convertView;
		}

		static class ViewHolder {
			TextView trophyNameAndDesc;
			ImageView trophy;
			ImageView locked;
		}
	}
}
