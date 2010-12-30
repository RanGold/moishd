package moishd.common;

public enum PushNotificationTypeEnum {

	GameInvitation("GameInvitation"),
	GameDeclined("GameDeclined"),
	PlayerBusy("PlayerBusy"),
	StartGameTruth("StartGameTruth"),
	StartGameDareSimonPro("StartGameDareSimonPro"),
	StartGameDareMixing("StartGameDareMixing"),
	StartGameDareFastClick("StartGameDareFastClick"),
	GameResult("GameResult"),
	Won("Won"),
	Lost("Lost"),
	LostFirst("LostFirst"),
	WonSecond("WonSecond"),
	CheckAlive("CheckAlive");

	private String name;

	PushNotificationTypeEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

}
