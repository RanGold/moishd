package moishd.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import moishd.android.facebook.AsyncFacebookRunner;
import moishd.android.facebook.Facebook;
import moishd.android.facebook.LoginButton;
import moishd.android.facebook.SessionEvents;
import moishd.android.facebook.SessionStore;
import moishd.android.facebook.SessionEvents.AuthListener;
import moishd.android.facebook.SessionEvents.LogoutListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class WelcomeScreenActivity extends Activity{
	
    protected static final int PICK_ACCOUNT_REQUEST = 0;
    protected static final int GET_ACCOUNT_TOKEN_REQUEST = 1;

    protected static final int RESULT_OK = 0;
    protected static final int RESULT_FAILED = 1;

    private static final String APP_ID = "108614622540129";
	private final String appDomain = "moish-d.appspot.com"; //"localhost:8888/";
	
	private Account userGoogleAccount;
	private String authString;
    
    private static LoginButton loginButton;
    private Facebook facebook;
    private AsyncFacebookRunner asyncRunner;
    
    private SharedPreferences appPref;
	
	private DefaultHttpClient http_client = new DefaultHttpClient();
	
	private AccountManager accountManager;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        
        setContentView(R.layout.main);
        loginButton = (LoginButton) findViewById(R.id.login);

       	facebook = new Facebook(APP_ID);
       	asyncRunner = new AsyncFacebookRunner(facebook);

        boolean isSessionValid = SessionStore.restore(facebook, this);
        SessionEvents.addAuthListener(new MoishdAuthListener());
        SessionEvents.addLogoutListener(new MoishdLogoutListener());
        loginButton.init(this, facebook);
        
        if (isSessionValid){
        	Intent intent = new Intent().setClass(this, UsersTabWidget.class);
        	startActivity(intent);
        }
	}
	
	protected void startGoogleAuth(){
 	   Intent intent = new Intent(this, AccountList.class);
	   startActivityForResult(intent, PICK_ACCOUNT_REQUEST);
	}
	
	protected void authorizeGoogleAccount(Account account){
		Intent intent = new Intent(this, AuthorizeGoogleAccount.class);
		intent.putExtra("account", account);
		startActivityForResult(intent, GET_ACCOUNT_TOKEN_REQUEST);
	}
	
	protected static void logout(View arg0){
		loginButton.logout(arg0);
		
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == PICK_ACCOUNT_REQUEST){
    		if (resultCode == RESULT_FAILED){
    			AlertDialog.Builder builder = new AlertDialog.Builder(this);
    			builder.setMessage("Moish'd! cannot start as it requires a Google account for registration. " +
    					"Please create one under Settings/Accounts & Sync.")
    					.setCancelable(false)
    					.setNeutralButton("Finish", new DialogInterface.OnClickListener() {
     			           public void onClick(DialogInterface dialog, int id) {
   			                finish();
   			           }
   			       });
    			AlertDialog alert = builder.create();
    			alert.show();
    		}
    		else{
    			userGoogleAccount = (Account) data.getExtras().get("account");
    			appPref.edit().putString("userGoogleAccount", userGoogleAccount.name);
    			appPref.edit().commit();
    			authorizeGoogleAccount(userGoogleAccount);
    		}
    	}
    	else if(requestCode == GET_ACCOUNT_TOKEN_REQUEST){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Moish'd! cannot start as it requires a Google account for registration. " +
					"Retry?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                authorizeGoogleAccount(userGoogleAccount);
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   finish();
			           }
			       });
			AlertDialog alert = builder.create();
			
    		if (resultCode == RESULT_OK){
    			authString = data.getExtras().getString("auth_token");
    			appPref.edit().putString("authString", authString);
    			appPref.edit().commit();
    		}
    		else{
    			alert.show();
    		}
    	}
    	else{
    		facebook.authorizeCallback(requestCode, resultCode, data);
    	}
    }
    
    private void doAuthSucceed(){
    	
    	Intent intent = new Intent().setClass(this, UsersTabWidget.class);
    	startActivity(intent);
    }

    public class MoishdAuthListener implements AuthListener {

        public void onAuthSucceed() {
        	doAuthSucceed();
        }

        public void onAuthFail(String error) {
        }
    }

    public class MoishdLogoutListener implements LogoutListener {
        public void onLogoutBegin() {
        }

        public void onLogoutFinish() {
        }
    }
    
    private class GetCookieTask extends AsyncTask<String, Void, Boolean> {
		protected Boolean doInBackground(String... tokens) {
			try {
				// Don't follow redirects
				http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);
				
				HttpGet http_get = new HttpGet("http://" + appDomain + "/_ah/login?continue=http://localhost/&auth=" + tokens[0]);
				HttpResponse response;
				response = http_client.execute(http_get);
				if(response.getStatusLine().getStatusCode() != 302)
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
			new AuthenticatedRequestTask().execute("http://" + appDomain + "/InsertUser");
		}
	}

	private class AuthenticatedRequestTask extends AsyncTask<String, Void, HttpResponse> {
		@Override
		protected HttpResponse doInBackground(String... urls) {
			try {
				HttpGet http_get = new HttpGet(urls[0]);
				return http_client.execute(http_get);
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(result.getEntity().getContent()));
				String response;
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				response = sb.toString();
				
				Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}