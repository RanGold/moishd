package moishd.common;

public enum IntentRequestCodesEnum {
	
	PickGoogleAccount(0),
	GetGoogleAccountToken(1);
	
	private int requestCode;

	IntentRequestCodesEnum(int requestCode) {
		this.requestCode = requestCode;
	}

	public int getCode() {
		return this.requestCode;
	}

}
