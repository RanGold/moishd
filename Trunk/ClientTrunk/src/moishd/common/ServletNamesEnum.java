package moishd.common;

public enum ServletNamesEnum {

	UserLogin("UserLogin"),
	GetAllUsers("GetAllUsers"),
	GetFriendUsers("GetFriendUsers"),
	GetNearbyUsers("GetNearbyUsers"),
	GetMergedUsers("GetMergedUsers"),
	InviteUser("InviteUser"),
	GetTimeGameInitiator("GetGameInitiator"),
	GameWin("GameWin"),
	GameLose("GameLose"),
	InvitationResponse("InvitationResponse"),
	RegisterUser("RegisterUser"),
	UpdateLocation("UpdateLocation"),
	HasLocation("HasLocation"),
	UnregisterUser("UnregisterUser"),
	Alive("Alive");
	
	private String name;

	ServletNamesEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
