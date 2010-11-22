package moishd.android;

import moishd.android.facebook.AsyncFacebookRunner;
import moishd.android.facebook.Facebook;
import moishd.android.facebook.LoginButton;
import moishd.android.facebook.SessionEvents;
import moishd.android.facebook.SessionStore;
import moishd.android.facebook.SessionEvents.AuthListener;
import moishd.android.facebook.SessionEvents.LogoutListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreenActivity extends Activity{
	
    public static final String APP_ID = "108614622540129";

    private static LoginButton mLoginButton;

    private Facebook mFacebook;
    private AsyncFacebookRunner mAsyncRunner;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        mLoginButton = (LoginButton) findViewById(R.id.login);

       	mFacebook = new Facebook(APP_ID);
       	mAsyncRunner = new AsyncFacebookRunner(mFacebook);

        boolean isSessionValid = SessionStore.restore(mFacebook, this);
        SessionEvents.addAuthListener(new MoishdAuthListener());
        SessionEvents.addLogoutListener(new MoishdLogoutListener());
        mLoginButton.init(this, mFacebook);
        
        if (isSessionValid){
        	Intent intent = new Intent().setClass(this, UsersTabWidget.class);
        	startActivity(intent);
        }
	}
	
	protected static void logout(View arg0){
		mLoginButton.logout(arg0);
		
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        mFacebook.authorizeCallback(requestCode, resultCode, data);
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
}