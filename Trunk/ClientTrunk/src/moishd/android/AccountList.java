package moishd.android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AccountList extends ListActivity {
	protected AccountManager accountManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		accountManager = AccountManager.get(getApplicationContext());
		Account[] accounts = accountManager.getAccountsByType("com.google");
		if (accounts.length == 0){
			returnAccountToCallingActivity(null);
		}
		else if (accounts.length==1){
			returnAccountToCallingActivity(accounts[0]);
		}
		else{
			ListAdapter list =  new ArrayAdapter<Account>(this, R.layout.account_list_item, accounts);
			this.setListAdapter(list);        
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Account account = (Account)getListView().getItemAtPosition(position);
		returnAccountToCallingActivity(account);
	}

	private void returnAccountToCallingActivity(Account account){
		Intent resultIntent = new Intent();
		if (account == null){
			resultIntent.putExtra("account", "noAccount");
			setResult(WelcomeScreenActivity.RESULT_FAILED, resultIntent);
		}
		else{
			resultIntent.putExtra("account", account);
			setResult(WelcomeScreenActivity.RESULT_OK, resultIntent);
		}
		finish();		
	}
}