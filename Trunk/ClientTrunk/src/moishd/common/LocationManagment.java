package moishd.common;

import java.util.Timer;
import java.util.TimerTask;

import moishd.android.ServerCommunication;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class LocationManagment {

	private static LocationManagment locationManagment;
	private LocationManager locationManager ;
	private Criteria criteria = new Criteria();
	private final String TAG = "LOCATION";
	private String authToken ;
	private final int TWO_MINUTES = 1000 * 60 * 2;
	private Location currentBestLocation ;	
	private Timer timer;
	private Handler mHandler = new Handler();
	private LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			// Called when a new location is found by the network location provider.
			Log.d(TAG, "Got Location Changed from "+location.getProvider()+": "
					+"Longitude="+location.getLongitude()+"  Latitude="+location.getLatitude());
			if (isBetterLocation(location, currentBestLocation))
				currentBestLocation = location;
			Log.d(TAG, "decided on location: "
					+"Longitude="+currentBestLocation.getLongitude()+"  Latitude="+currentBestLocation.getLatitude());
			locationManager.removeUpdates(locationListener);
			ServerCommunication.updateLocationInServer(currentBestLocation, authToken);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {}
		public void onProviderEnabled(String provider) {}
		public void onProviderDisabled(String provider) {}
	};

		
	public static LocationManagment getLocationManagment(Context context,String authToken){
		if (locationManagment==null)
			locationManagment =  new LocationManagment(context,authToken);
		return locationManagment;
	}
	
	public Location getLastKnownLocation(){
		//String bestProvider = locationManager.getBestProvider(criteria, true);
		Location locFromGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Location locFromNetwork =locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (locFromGPS!=null)
			return locFromGPS;
		else
			return locFromNetwork;
		//return locationManager.getLastKnownLocation(bestProvider);
	}
	
	public void startUpdateLocation(int minutes){
		if (timer!=null)
			stopUpdateLocation();
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new getCurrentLocationTask(), 5*1000, 60*1000*minutes);
	}
	
	public void stopUpdateLocation(){
		timer.cancel();
	}
	
	private class getCurrentLocationTask extends TimerTask{
		private Runnable run;

		@Override
		public void run() {
			run = new Runnable() {
				public void run() {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 50, locationListener);
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 50, locationListener);
				}
			}; 
			Log.d(TAG, "in TimerTask");
			mHandler.post(run);
		}
	}
	
	private LocationManagment(Context context, String authToken){
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		this.authToken = authToken;
	}
	
	private boolean isBetterLocation(Location location, Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}
	
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
	
	
	
}
