package moishd.common;

public enum ActionByPushNotificationEnum {
	
	C2DMError("C2DMError"),
	GameInvitation("GameInvitation"),
	GameDeclined("GameDeclined"),
	PlayerBusy("PlayerBusy"),
	PlayerOffline("PlayerOffline"),
	StartGameTruth("StartGameTruth"),
	StartGameDare("StartGameDare"),
	StartGameDareSimonPro("StartGameDareSimonPro"),
	StartGameDareMixing("StartGameDareMixing"),
	StartGameDareFastClick("StartGameDareFastClick"),
	GameResult("GameResult"),
	GameOffer("GameOffer");
	
	private String name;

	ActionByPushNotificationEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
