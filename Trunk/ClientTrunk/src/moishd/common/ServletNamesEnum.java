package moishd.common;

public enum ServletNamesEnum {

	UserLogin("UserLogin"),
	GetAllUsers("GetAllUsers"),
	GetFriendUsers("GetFriendUsers"),
	GetNearbyUsers("GetNearbyUsers"),
	InviteUser("InviteUser"),
	GetTimeGameInitiator("GetGameInitiator"),
	GameWin("GameWin"),
	GameLose("GameLose"),
	InvitationResponse("InvitationResponse"),
	RegisterUser("RegisterUser"),
	UpdateLocation("UpdateLocation"),
	UnregisterUser("UnregisterUser");
	
	private String name;

	ServletNamesEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
