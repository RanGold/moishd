package moishd.common;

public enum IntentExtraKeysEnum {

	C2DMError("C2DMError"),
	GoogleAccount("GoogleAccount"),
	GoogleAuthToken("GoogleAuthToken"),
	PushGameId("GameId"),
	PushAction("Action"),
	GameType("GameType"),
	PushGameResult("Result"),
	Truth("Truth"),
	DareSimonPro("DareSimonPro"),
	DareMixing("DareMixing"),
	DareFastClick("DareFastClick"),
	MoishdUser("MoishdUser"),
	Points("Points"),
	Rank("Rank"),
	Trophies("Trophies"),
	NumberOfTrophies("NumberOfTrophies");
	
	private String name;

	IntentExtraKeysEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
