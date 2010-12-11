package moishd.common;

public enum ServletNamesEnum {

	UserLogin("UserLogin"),
	GetAllUsers("GetAllUsers"),
	InviteUser("InviteUser"),
	GetTimeGameInitiator("GetTimeGameInitiator"),
	GameTimeWin("GameTimeWin"),
	InvitationResponse("InvitationResponse");
	
	private String name;

	ServletNamesEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
