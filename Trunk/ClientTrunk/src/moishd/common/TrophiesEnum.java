package moishd.common;

public enum TrophiesEnum {
	
	TinyMoisher("Tiny Moishser", "Win 10 games"),
	MiniMoisher("Mini Moisher", "Win 50 games"),
	MasterMoisher("Master Moisher", "Win 100 games"),
	SuperMoisher("Super Moisher", "Win 250 games"),
	MegaMoisher("Mega Moisher", "Win 500 games"),
	TenInARow("10 in a row", "Win 10 games in a row"),
	TwentyInARow("20 in a row", "Win 20 games in a row"),
	FirstTime("First time", "Win your first game"),
	BestFriends("Best friends", "Win 10 of your facebook friends"),
	FaceOff("Face off", "Win 10 nearby users");
	
	private String trophyName;
	private String trophyDescription;

	TrophiesEnum(String trophyName, String trophyDescription){
		this.trophyName = trophyName;
		this.trophyDescription = trophyDescription;
	}

	public String getTrophyName() {
		return trophyName;
	}

	public String getTrophyDescription() {
		return trophyDescription;
	}
	
}
