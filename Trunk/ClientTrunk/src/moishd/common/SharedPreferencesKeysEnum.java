package moishd.common;

public enum SharedPreferencesKeysEnum {

	GoogleSharedPreferences("GoogleSharedPreferences"),
	GoogleAuthToken("GoogleAuthToken"),
	C2dmSharedPreferences("C2dmSharedPreferences"),
	C2dmRegistered("C2dmRegistered");
	
	private String name;

	SharedPreferencesKeysEnum(String name) {
		this.name = name;
	}

	public String getText() {
		return this.name;
	}
}
