package moishd.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class ServerRequest  {
	private final String appDomain = "http://moish-d.appspot.com"; //"http://10.0.2.2:8888/"
	//to be replaced with http://moish-d.appspot.com
	//when change - change also is AndroidUtilty
	
	private DefaultHttpClient http_client;
	private String authToken;
	
	private static ServerRequest instance;

	private ServerRequest() {
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 200);
		ConnManagerParams.setTimeout(params, 15*1000);
		params.setIntParameter(ConnManagerParams.MAX_TOTAL_CONNECTIONS, 200);
		params.setParameter(ConnManagerParams.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(100));

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager cm = (new ThreadSafeClientConnManager(params, schemeRegistry));

		http_client = new DefaultHttpClient(cm, params);
	}
	
	public boolean GetCookie(String auth_token) {
		if (this.authToken != null && !this.authToken.equals(auth_token)) {
			removeCookie();
		}
		this.authToken = auth_token;
		return this.GetCookie();
	}
	
	public boolean GetCookie() {
		MoishdPreferences moishdPreferences = MoishdPreferences.getMoishdPreferences();
		
		if (this.DoesHaveCookie()) {
			return true;
		}
		else {
			Log.d("COOKIE", "Getting cookie, authToken = " + (authToken == null ? "null" : this.authToken));
			Log.d("COOKIE", "Getting cookie");
			if (authToken == null){
				authToken=moishdPreferences.getCurrentGoogleAuthToken();
			}
			return (authToken != null ? GetCookieFromServer(this.authToken) : false);
		}
	}
	
	public void removeCookie(){
		Cookie cookieToRemove = null;
		for(Cookie cookie : http_client.getCookieStore().getCookies()) {
			if(cookie.getName().equals("ACSID")) {
				cookieToRemove = cookie;
			}
		}
		
		if (cookieToRemove != null) {
			Log.d("COOKIE", "removing cookie");
			http_client.getCookieStore().getCookies().remove(cookieToRemove);
		}
	}
	
	public boolean DoesHaveCookie(){
		for(Cookie cookie : http_client.getCookieStore().getCookies()) {
			if(cookie.getName().equals("ACSID"))
				return (cookie.getExpiryDate().after(new Date()));
		}
		
		return false;
	}
	
	public static ServerRequest Get() {
		if (instance == null) {
			instance = new ServerRequest();
		}
		
		return instance;
	}

	private boolean GetCookieFromServer (String token) {
		try {
			// Don't follow redirects
			http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);
			
			HttpGet http_get = new HttpGet(appDomain + "/_ah/login?continue=http://localhost/&auth=" + token);
			HttpResponse response;
			response = http_client.execute(http_get);
			if(response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_MOVED_TEMP) {
				Log.d("COOKIE","Error getting cookie - " + response.getStatusLine().getStatusCode() + 
						" instead of redirect " + HttpURLConnection.HTTP_MOVED_TEMP);
				// Response should be a redirect
				return false;
			}
			
			for(Cookie cookie : http_client.getCookieStore().getCookies()) {
				if(cookie.getName().equals("ACSID")) {
					return true;
				}
			}
		} catch (ClientProtocolException e) {
			Log.d("COOKIE","Error getting cookie - \n\r" + e.getStackTrace()[0].toString());
		} catch (IOException e) {
			Log.d("COOKIE","Error getting cookie - \n\r" + e.getStackTrace()[0].toString());
		} finally {
			http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
		}
		return false;
	}

	public HttpResponse doPost(HttpPost post) throws ClientProtocolException, IOException {
		if (!this.GetCookie()) {
			Log.d("COOKIE","Error getting cookie");
			throw new ClientProtocolException("Error getting cookie");
		}
		http_client.getConnectionManager().closeExpiredConnections();
		HttpResponse response=null;
		try{
		response = http_client.execute(post);
		} catch (ConnectionPoolTimeoutException e){
			Log.d("loc","exception");
		}
		return response;
	}

}
