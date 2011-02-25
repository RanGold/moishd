package moishd.common;

public enum IntentResultCodesEnum {

	OK(0),
	Failed(1);
	
	private int requestCode;

	IntentResultCodesEnum(int requestCode) {
		this.requestCode = requestCode;
	}

	public int getCode() {
		return this.requestCode;
	}

}
