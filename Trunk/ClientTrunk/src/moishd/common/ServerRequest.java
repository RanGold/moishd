package moishd.common;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class ServerRequest  {
	private final String appDomain = "http://moish-d.appspot.com";
	
	private DefaultHttpClient http_client;
	private boolean haveCookie;
	
	private static ServerRequest instance;

	private ServerRequest() {
		http_client = new DefaultHttpClient();
		haveCookie = false;
	}

	public void GetCookie(String auth_token) {
		new GetCookieTask().execute(auth_token);
	}
	
	public boolean DoesHaveCookie(){
		return haveCookie;
	}
	
	public static ServerRequest Get() {
		if (instance == null) {
			instance = new ServerRequest();
		}
		
		return instance;
	}

	private class GetCookieTask extends AsyncTask<String, Void, Boolean> {
		protected Boolean doInBackground(String... tokens) {
			try {
				// Don't follow redirects
				http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);
				
				HttpGet http_get = new HttpGet(appDomain + "/_ah/login?continue=http://localhost/&auth=" + tokens[0]);
				HttpResponse response;
				response = http_client.execute(http_get);
				if(response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_MOVED_TEMP)
					// Response should be a redirect
					return false;
				
				for(Cookie cookie : http_client.getCookieStore().getCookies()) {
					if(cookie.getName().equals("ACSID"))
						return true;
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
			}
			return false;
		}
		
		protected void onPostExecute(Boolean result) {
			haveCookie = true;
		}
	}
	
	public HttpResponse doPost(HttpPost post) throws ClientProtocolException, IOException {
		return http_client.execute(post);
	}
	
	public HttpResponse doGet(HttpGet get) throws ClientProtocolException, IOException {
		return http_client.execute(get);
	}
}
