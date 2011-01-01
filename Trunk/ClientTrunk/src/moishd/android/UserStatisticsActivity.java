package moishd.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.client.dataObjects.ClientTrophy;
import moishd.client.dataObjects.ClientUserGameStatistics;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.TrophiesEnum;
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

	private ClientMoishdUser user;
	private List<ClientTrophy> userTrophies;
	private ClientUserGameStatistics userGameStatistics;
	private Map<String, Boolean> allTrophiesMap;
	private ListView list;
	static List<Trophy> trophiesList;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
		
		ClientMoishdUser moishdUser = (ClientMoishdUser) getIntent().getExtras().get(IntentExtraKeysEnum.MoishdUser.toString());
		userGameStatistics = moishdUser.getStats();
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
		setRankIcon(userRankPic);

		//Just until we have a real trophies list sent from the server
		userTrophies = new ArrayList<ClientTrophy>();
		userTrophies.add(new ClientTrophy(TrophiesEnum.TinyMoisher.toString()));
		userTrophies.add(new ClientTrophy(TrophiesEnum.BestFriends.toString()));
		userTrophies.add(new ClientTrophy(TrophiesEnum.TenInARow.toString()));

		populateNeedToAchieveTrophiesMap();

		for (int i = 0; i < userTrophies.size(); i++){
			String currentTrophy = userTrophies.get(i).getName();
			allTrophiesMap.put(currentTrophy, true);
		}
		
		createTrophyListFromMap();

		list = (ListView) findViewById(R.id.trophiesList);
		list.setAdapter(new EfficientAdapter(this));
	}

	private void setRankIcon(ImageView userRankPic) {
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
		allTrophiesMap.put(TrophiesEnum.BestFriends.toString(), false);
		allTrophiesMap.put(TrophiesEnum.FaceOff.toString(), false);
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
		trophiesList.add(new Trophy(TrophiesEnum.BestFriends, allTrophiesMap.get(TrophiesEnum.BestFriends.toString())));
		trophiesList.add(new Trophy(TrophiesEnum.FaceOff, allTrophiesMap.get(TrophiesEnum.FaceOff.toString())));
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
