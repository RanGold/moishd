package moishd.common;

public enum IntentExtraKeysEnum {

	C2DMError("C2DMError"),
	GoogleAccount("GoogleAccount"),
	GoogleAuthToken("GoogleAuthToken"),
	GoogleAuthTokenOfOpponent("GoogleAuthTokenOfOpponent"),
	UserNickNameOfOpponent("UserNickNameOfOpponent"),
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
	NumberOfTrophies("NumberOfTrophies"),
	InitName("InitName"),
	RecName("RecName"),
	NearByGame("NearByGame");
	
	private String name;

	IntentExtraKeysEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
