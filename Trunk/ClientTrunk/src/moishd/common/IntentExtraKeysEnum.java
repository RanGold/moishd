package moishd.common;

public enum IntentExtraKeysEnum {

	GoogleAccount("GoogleAccount"),
	GoogleAuthToken("GoogleAuthToken"),
	PushGameId("GameId"),
	PushAction("Action"),
	PushGameResult("Result");
	
	private String name;

	IntentExtraKeysEnum(String name) {
		this.name = name;
	}

	public String getText() {
		return this.name;
	}
}
