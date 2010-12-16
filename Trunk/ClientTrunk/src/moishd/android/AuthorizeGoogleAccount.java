package moishd.android;

import java.io.IOException;

import moishd.common.IntentExtraKeysEnum;
import moishd.common.IntentResultCodesEnum;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class AuthorizeGoogleAccount extends Activity {
	
	boolean firstTime = true;
	private AccountManagerFuture<Bundle> bundle;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		AccountManager accountManager = AccountManager.get(getApplicationContext());
		Account account = (Account)intent.getExtras().get(IntentExtraKeysEnum.GoogleAccount.toString());

		bundle = accountManager.getAuthToken(account, "ah", false, new GetAuthTokenCallback(), null);
	}

	protected void onResume(){
		super.onResume();
		
		if (firstTime){
			firstTime = false;
		}
		else{
			try {
				Bundle result = bundle.getResult();
				if (result!=null){
					String authToken = result.getString(AccountManager.KEY_AUTHTOKEN);
					if (authToken == null){
						Intent resultIntent = new Intent();
						setResult(IntentResultCodesEnum.Failed.getCode(), resultIntent);
						finish();
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
				bundle = result.getResult();
				Intent intent = (Intent)bundle.get(AccountManager.KEY_INTENT);
				if(intent != null) {
					// User input required
					startActivity(intent);
				} else {
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
		String auth_token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
		Intent resultIntent = new Intent();
		resultIntent.putExtra(IntentExtraKeysEnum.GoogleAuthToken.toString(), auth_token);
		setResult(IntentResultCodesEnum.OK.getCode(), resultIntent);
		finish();
	}

}
