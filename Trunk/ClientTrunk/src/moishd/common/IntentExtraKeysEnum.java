package moishd.common;

public enum IntentExtraKeysEnum {

	GoogleAccount("GoogleAccount"),
	GoogleAuthToken("GoogleAuthToken"),
	PushGameId("GameId"),
	PushAction("Action"),
	GameType("GameType"),
	PushGameResult("Result"),
	Truth("Truth"),
	DareSimonPro("DareSimonPro"),
	DareMixing("DareMixing"),
	DareFastClick("DareFastClick");
	
	private String name;

	IntentExtraKeysEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
