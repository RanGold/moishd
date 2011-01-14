package moishd.android;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.common.GamesEnum;
import moishd.common.IntentExtraKeysEnum;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TopMoishersActivity extends Activity{
	
	static List<ClientMoishdUser> topMoishers;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		
		setContentView(R.layout.top_moishers_layout);

		Typeface fontName = Typeface.createFromAsset(getAssets(), "fonts/FORTE.ttf");
		
		GamesEnum gameName = (GamesEnum) getIntent().getExtras().get(IntentExtraKeysEnum.GameType.toString());
		String authToken = (String) getIntent().getExtras().get(IntentExtraKeysEnum.GoogleAuthToken.toString());
		Log.d("TEST", "GAME ENUM TO STRING " + gameName.toString());
		topMoishers = ServerCommunication.getTopMoishers(getGameName(gameName), authToken);
		
		if (topMoishers == null){
			topMoishers = new ArrayList<ClientMoishdUser>();
		}
		TextView header = (TextView) findViewById(R.id.top_moishers_header);
		header.setText(gameName.getFullName()+ " Top Moishers\n");
		header.setTypeface(fontName);
		header.setTextSize(20);
		
		ListView list = (ListView) findViewById(R.id.topMoishersList);
		list.setAdapter(new EfficientAdapter(this));
	}
	
	private String getGameName(GamesEnum gameName){
		
		switch (gameName){
		case DareFastClick:
			return GamesEnum.DareFastClick.toString();
		case DareMixing:
			return GamesEnum.DareFastClick.toString();
		case DareSimonPro:
			return GamesEnum.DareFastClick.toString();
		case Truth:
			return GamesEnum.DareFastClick.toString();
		}
		return null;
	}
	
	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		private Bitmap blankImage;
		
		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);

			// Icons bound to the rows.
			blankImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_facebook);
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
				holder.blankImage = (ImageView) convertView.findViewById(R.id.blankPic);
				holder.userPic = (ImageView) convertView.findViewById(R.id.userPic);
				holder.userName = (TextView) convertView.findViewById(R.id.userName);
				holder.points = (TextView) convertView.findViewById(R.id.points);

				convertView.setTag(holder);
			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.blankImage.setImageBitmap(blankImage);
			Drawable userPic = LoadImageFromWebOperations(topMoishers.get(position).getPictureLink());
			holder.userPic.setImageDrawable(userPic);
			holder.userName.setText(topMoishers.get(position).getUserNick());
			holder.points.setText(topMoishers.get(position).getStats().getTopMoisherPoints());
			
			return convertView;
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

		static class ViewHolder {
			ImageView blankImage;
			ImageView userPic;
			TextView userName;
			TextView points;
		}
	}

}
