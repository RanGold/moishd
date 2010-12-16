package moishd.common;

public enum ActionByPushNotificationEnum {

	GameInvitation("GameInvitation"),
	GameDeclined("GameDeclined"),
	StartGameTruth("StartGameTruth"),
	StartGameDare("StartGameDare"),
	GameResult("GameResult");
	
	private String name;

	ActionByPushNotificationEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
