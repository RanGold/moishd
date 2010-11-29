package moishd.android;

import java.io.IOException;

import org.apache.http.impl.client.DefaultHttpClient;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		AccountManager accountManager = AccountManager.get(getApplicationContext());
		Account account = (Account)intent.getExtras().get("account");
		accountManager.getAuthToken(account, "ah", false, new GetAuthTokenCallback(), null);	
	}

	@Override
	protected void onResume() {
		super.onResume();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Moish'd! cannot start as it requires a Google account for registration. " +
		"Retry?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				Intent intent = getIntent();
				AccountManager accountManager = AccountManager.get(getApplicationContext());
				Account account = (Account)intent.getExtras().get("account");
				accountManager.getAuthToken(account, "ah", false, new GetAuthTokenCallback(), null);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent resultIntent = new Intent();
				setResult(WelcomeScreenActivity.RESULT_FAILED, resultIntent);	
				finish();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
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
		Intent resultIntent = new Intent();
		resultIntent.putExtra("auth_token", auth_token);
		setResult(WelcomeScreenActivity.RESULT_OK, resultIntent);
		finish();
		//new GetCookieTask().execute(auth_token);
	}

}
