package moishd.common;

public enum SharedPreferencesKeysEnum {

	GoogleSharedPreferences("GoogleSharedPreferences"),
	GoogleAccountName("GoogleAccountName"),
	GoogleAuthToken("GoogleAuthToken");

	private String name;

	SharedPreferencesKeysEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
