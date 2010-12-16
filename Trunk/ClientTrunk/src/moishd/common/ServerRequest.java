package moishd.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

public class ServerRequest  {
	private final String appDomain = "http://moish-d.appspot.com"; //"http://10.0.2.2:8888/"
	//to be replaced with http://moish-d.appspot.com
	//when change - change also is AndroidUtilty
	
	private DefaultHttpClient http_client;
	private String authToken;
	
	private static ServerRequest instance;

	private ServerRequest() {
		http_client = new DefaultHttpClient();
	}

	public boolean GetCookie(String auth_token) {
		this.authToken = auth_token;
		return this.GetCookie();
	}
	
	public boolean GetCookie() {
		if (this.DoesHaveCookie()) {
			return true;
		}
		else {
			return (authToken != null ? GetCookieFromServer(this.authToken) : false);
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

	public HttpResponse doPost(HttpPost post) throws ClientProtocolException, IOException {
		if (!this.GetCookie()) {
			throw new ClientProtocolException("Error getting cookie");
		}
		return http_client.execute(post);
	}
	
	public HttpResponse doGet(HttpGet get) throws ClientProtocolException, IOException {
		if (!this.GetCookie()) {
			throw new ClientProtocolException("Error getting cookie");
		}
		return http_client.execute(get);
	}
}
