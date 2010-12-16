package moishd.common;

public enum PushNotificationTypeEnum {

	GameInvitation("GameInvitation"),
	GameDeclined("GameDeclined"),
	StartGameTruth("StartGameTruth"),
	StartGameDare("StartGameDare"),
	GameResult("GameResult"),
	Won("Won"),
	Lost("Lost");

	private String name;

	PushNotificationTypeEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

}
