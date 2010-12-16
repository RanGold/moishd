package moishd.common;

public enum ActionByPushNotificationEnum {

	GameInvitation("GameInvitation"),
	GameDeclined("GameDeclined"),
	GameStartTruth("GameStartTruth"),
	GameStartDare("GameStartDare"),
	GameResult("GameResult");
	
	private String name;

	ActionByPushNotificationEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
