package net.notdot.aeauth;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import moishd.dataObjects.MoishdUser;
import moishd.dataObjects.objectToTest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class AppInfo extends Activity {
	private DefaultHttpClient http_client = new DefaultHttpClient();
	private final String appDomain = "moish-d.appspot.com"; // "10.0.0.2:8888"; //

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_info);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		AccountManager accountManager = AccountManager.get(getApplicationContext());
		Account account = (Account)intent.getExtras().get("account");
		accountManager.getAuthToken(account, "ah", false, new GetAuthTokenCallback(), null);
	}

	private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {
		public void run(AccountManagerFuture<Bundle> result) {
			Bundle bundle;
			try {
				bundle = result.getResult();
				Intent intent = (Intent)bundle.get(AccountManager.KEY_INTENT);
				if(intent != null) {
					// User input required
					startActivity(intent);
				} else {
					onGetAuthToken(bundle);
				}
			} catch (OperationCanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AuthenticatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	protected void onGetAuthToken(Bundle bundle) {
		String auth_token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
		new GetCookieTask().execute(auth_token);
	}

	private class GetCookieTask extends AsyncTask<String, Void, Boolean> {
		protected Boolean doInBackground(String... tokens) {
			try {
				// Don't follow redirects
				http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);
				
				HttpGet http_get = new HttpGet("http://" + appDomain + "/_ah/login?continue=http://localhost/&auth=" + tokens[0]);
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
			new AuthenticatedRequestTask().execute("http://" + appDomain + "/GetAllUsersServlet");
		}
	}

	private class AuthenticatedRequestTask extends AsyncTask<String, Void, HttpResponse> {
		@Override
		protected HttpResponse doInBackground(String... urls) {
			try {
				HttpPost httpPost = new HttpPost(urls[0]);
				return http_client.execute(httpPost);
				/*HttpGet http_get = new HttpGet(urls[0]);
				return http_client.execute(http_get);*/
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(HttpResponse result) {
			try {
				HttpEntity resp_entity = result.getEntity();
				if (resp_entity != null) {
					ObjectInputStream ois = new ObjectInputStream(result.getEntity().getContent());
					String json = (String) ois.readObject();
					Gson g = new Gson();
					@SuppressWarnings("unchecked")
					List<MoishdUser> users = (List<MoishdUser>)g.fromJson(json, new TypeToken<Collection<MoishdUser>>(){}.getType());
					//MoishdUser user = g.fromJson(json, MoishdUser.class);
					//objectToTest obj = g.fromJson(json, objectToTest.class);
				}
				/*
				BufferedReader reader = new BufferedReader(new InputStreamReader(result.getEntity().getContent()));
				String response;
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				response = sb.toString();
				
				Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();*/				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}
		}
	}
}
