package moishd.common;

import moishd.android.facebook.Facebook;

public enum IntentRequestCodesEnum {
	
	PickGoogleAccount(0),
	GetGoogleAccountToken(1),
	FacebookAuth(Facebook.DEFAULT_AUTH_ACTIVITY_CODE),
	GetChosenGame(2);
	
	private int requestCode;

	IntentRequestCodesEnum(int requestCode) {
		this.requestCode = requestCode;
	}

	public int getCode() {
		return this.requestCode;
	}

}
