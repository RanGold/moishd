package moishd.android;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.common.GamesEnum;
import moishd.common.IntentExtraKeysEnum;
import moishd.common.MoishdPreferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TopMoishersActivity extends Activity{

	private static List<ClientMoishdUser> topMoishers = new ArrayList<ClientMoishdUser>();
	private static List<Drawable> topMoishersPictures = new ArrayList<Drawable>();

	private ListView list;
	private static Typeface fontNameForList;
	private static Typeface fontName;

	private final int ERROR_RETRIEVING_USERS_DIALOG = 1;
	private final int UPDATE_LIST_ADAPTER = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_LIST_ADAPTER:
				updateList();
				break;
			case ERROR_RETRIEVING_USERS_DIALOG:
				showErrorRetrievingTopMoisers();
				break;
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.top_moishers_layout);
		
		MoishdPreferences moishdPreferences = MoishdPreferences.getMoishdPreferences();
		moishdPreferences.setAvailableStatus(false);
		
		fontName = Typeface.createFromAsset(getAssets(), "fonts/mailrays.ttf");			
		fontNameForList = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf");

		GamesEnum gameName = (GamesEnum) getIntent().getExtras().get(IntentExtraKeysEnum.GameType.toString());
		String authToken = (String) getIntent().getExtras().get(IntentExtraKeysEnum.GoogleAuthToken.toString());

		if (topMoishers == null){
			topMoishers = new ArrayList<ClientMoishdUser>();
		}
		TextView header = (TextView) findViewById(R.id.top_moishers_header);
		header.setText(gameName.getFullName()+ "\n Top Moishers\n");
		header.setTypeface(fontName);
		header.setTextSize(25);
		new GetTopMoishersTask().execute(GamesEnum.getGameName(gameName), authToken);

		list = (ListView) findViewById(R.id.topMoishersList);
		list.setAdapter(new EfficientAdapter(this));
	}
	
	@Override 
	public void onBackPressed(){
		topMoishers =  new ArrayList<ClientMoishdUser>();
		finish();
	}

	private void updateList() {
		EfficientAdapter listAdapter = (EfficientAdapter) list.getAdapter();
		listAdapter.notifyDataSetChanged();
	}

	private void showErrorRetrievingTopMoisers(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Error retrieving Top Moishers from server.")
		.setCancelable(false)
		.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create(); 
		alert.show();
	}

	private class GetTopMoishersTask extends AsyncTask<String, Integer, List<Object>> {

		private ProgressDialog mainProgressDialog;
		protected void onPreExecute() {
			mainProgressDialog = ProgressDialog.show(TopMoishersActivity.this, null, "Retrieving Top Moishers", true, false);
		}

		protected List<Object> doInBackground(String... strings) {

			List <Object> resultList = new ArrayList<Object>();
			List<ClientMoishdUser> topMoishers = new ArrayList<ClientMoishdUser>();	
			List<Drawable> usersPictures = new ArrayList<Drawable>();

			String authToken = strings[1];
			String gameName = strings[0];
			topMoishers = ServerCommunication.getTopMoishers(gameName, authToken);

			if (topMoishers == null){
				resultList.add(ERROR_RETRIEVING_USERS_DIALOG);
				return resultList;
			}
			else{
				Collections.sort(topMoishers);
				Comparator<? super ClientMoishdUser> comparator = new PointsComparator();
				Collections.sort(topMoishers, comparator);
				for (int i=0; i < topMoishers.size(); i++){
					Drawable userPic = LoadImageFromWebOperations(topMoishers.get(i).getPictureLink());
					usersPictures.add(userPic);
				}

				resultList.add(topMoishers);
				resultList.add(usersPictures);
				return resultList;
			}
		}

		protected void onProgressUpdate(Integer... progress) {
			onProgressUpdate(progress[0]);
		}

		@SuppressWarnings("unchecked")
		protected void onPostExecute(List<Object> resultList) {

			if (resultList.size() == 2){
				topMoishers = (List<ClientMoishdUser>) resultList.get(0);
				topMoishersPictures = (List<Drawable>) resultList.get(1);
				sendMessageToHandler(UPDATE_LIST_ADAPTER);
			}
			else{
				Integer messageCode = (Integer) resultList.get(0);
				final int messageCodeInt = messageCode.intValue();
				sendMessageToHandler(messageCodeInt);
			}

			if (mainProgressDialog != null){
				mainProgressDialog.dismiss();
			}
		}

		private Drawable LoadImageFromWebOperations(String url){

			try{
				InputStream is = (InputStream) new URL(url).getContent();
				Drawable d = Drawable.createFromStream(is, "src name");
				return d;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		private void sendMessageToHandler(final int messageType) {
			Message registrationErrorMessage = Message.obtain();
			registrationErrorMessage.setTarget(mHandler);
			registrationErrorMessage.what = messageType;
			registrationErrorMessage.sendToTarget();
		}

		class PointsComparator implements Comparator<ClientMoishdUser> {

			public int compare(ClientMoishdUser userA, ClientMoishdUser userB) {

				if (userA.getStats().getTopMoisherPoints() > userB.getStats().getTopMoisherPoints()) {
					return -1;
				}
				else{
					return 1;
				}
			}
		}
	}

	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return topMoishers.size();
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
				convertView = mInflater.inflate(R.layout.top_moishers_list_item, null);

				holder = new ViewHolder();
				holder.userPic = (ImageView) convertView.findViewById(R.id.userPic);
				holder.userName = (TextView) convertView.findViewById(R.id.userName);
				holder.userName.setTypeface(fontNameForList);
				holder.points = (TextView) convertView.findViewById(R.id.points);
				holder.points.setTypeface(fontNameForList);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.userPic.setImageDrawable(topMoishersPictures.get(position));
			holder.userName.setText(topMoishers.get(position).getUserNick());
			holder.points.setText(String.valueOf(topMoishers.get(position).getStats().getTopMoisherPoints()));

			return convertView;
		}

		static class ViewHolder {
			ImageView userPic;
			TextView userName;
			TextView points;
		}
	}
}
