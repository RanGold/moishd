package moishd.common;

public enum PushNotificationTypeEnum {

	GameInvitation("GameInvitation"),
	GameDeclined("GameDeclined"),
	StartGameTruth("StartGameTruth"),
	StartGameDareSimonPro("StartGameDareSimonPro"),
	StartGameDareMixing("StartGameDareMixing"),
	StartGameDareFastClick("StartGameDareFastClick"),
	GameResult("GameResult"),
	Won("Won"),
	Lost("Lost"),
	LostFirst("LostFirst"),
	WonSecond("WonSecond");

	private String name;

	PushNotificationTypeEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

}
