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
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

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
		ConnManagerParams.setTimeout(params, 20*1000);
		//params.setIntParameter("MaxTotalConnections", 100);
		
		//HttpConnectionManagerParams.setMaxTotalConnections(params, 100);
		//HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
	//	HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		// Create and initialize scheme registry 
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		// Create an HttpClient with the ThreadSafeClientConnManager.
		// This connection manager must be used if more than one thread will
		// be using the HttpClient.
		ClientConnectionManager cm = (new ThreadSafeClientConnManager(params, schemeRegistry));

		http_client = new DefaultHttpClient(cm, params);
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
