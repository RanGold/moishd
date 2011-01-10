package moishd.android;

import java.io.IOException;

import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentResultCodesEnum;
import moishd.common.MoishdPreferences;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class AuthorizeGoogleAccountActivity extends Activity {
	
	boolean firstTime = true;
	private AccountManagerFuture<Bundle> bundleToken;
	Account account = null;
	AccountManager accountManager = null;
	boolean needInvalidate = true;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.authorize_google_account_layout);
		LinearLayout view = (LinearLayout) findViewById(R.id.authorizeGoogleAccountLinearView);
		view.setVisibility(View.INVISIBLE);

		Intent intent = getIntent();
		accountManager = AccountManager.get(this);
		account = (Account)intent.getExtras().get(IntentExtraKeysEnum.GoogleAccount.toString());
		bundleToken = accountManager.getAuthToken(account, "ah", false, new GetAuthTokenCallback(), null);
	}

	protected void onResume(){
		super.onResume();
		
		if (firstTime){
			firstTime = false;
		}
		else{
			try {
				bundleToken = accountManager.getAuthToken(account, "ah", false, null, null);
				int i=5;
				while (!bundleToken.isDone() && i>0){
					SystemClock.sleep(1000);
					i--;
				}
				
				if (i==0){
					onError();
				}
				else{
					Bundle result = bundleToken.getResult();
					if (result!=null){
						String authToken = result.getString(AccountManager.KEY_AUTHTOKEN);
						if (authToken == null){
							onError();
						}
						else{
							onGetAuthToken(result);
						}
					}
				}
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (AuthenticatorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {
		public void run(AccountManagerFuture<Bundle> result) {
			Bundle bundle;
			try {
				Log.d("auth","in callback");
				bundle = result.getResult();
				Intent intent = (Intent)bundle.get(AccountManager.KEY_INTENT);
				if(intent != null) {
					// User input required
					startActivity(intent);
				} else {
					String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
					if (needInvalidate){
						needInvalidate=false;
						accountManager.invalidateAuthToken(account.type, authToken);
						onResume();
					}
					onGetAuthToken(bundle);
				}
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (AuthenticatorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	protected void onGetAuthToken(Bundle bundle) {
		MoishdPreferences.setReturnFromAuth(getApplicationContext(), true);
		String auth_token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
		Intent resultIntent = new Intent();
		resultIntent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), auth_token);
		setResult(IntentResultCodesEnum.OK.getCode(), resultIntent);
		finish();
	}
	
	protected void onError(){
		MoishdPreferences.setReturnFromAuth(getApplicationContext(), true);
		Intent resultIntent = new Intent();
		setResult(IntentResultCodesEnum.Failed.getCode(), resultIntent);
		finish();		
	}

}
