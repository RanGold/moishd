package moishd.client.dataObjects;

public enum TrophiesEnum {
	
	TinyMoisher("Tiny Moishser", "Win 10 games", 10),
	MiniMoisher("Mini Moisher", "Win 50 games", 50),
	MasterMoisher("Master Moisher", "Win 100 games", 100),
	SuperMoisher("Super Moisher", "Win 250 games", 250),
	MegaMoisher("Mega Moisher", "Win 500 games", 500),
	TenInARow("10 in a row", "Win 10 games in a row", 15),
	TwentyInARow("20 in a row", "Win 20 games in a row", 30),
	FirstTime("First time", "Win your first game", 5),
	BestFriends("Best friends", "Win 10 of your facebook friends", 50),
	FaceOff("Face off", "Win 10 nearby users", 75);
	
	private String trophyName;
	private String trophyDescription;
	private int trophyPoints;

	TrophiesEnum(String trophyName, String trophyDescription, int trophyPoints){
		this.trophyName = trophyName;
		this.trophyDescription = trophyDescription;
		this.trophyPoints = trophyPoints;
	}

	public String getTrophyName() {
		return trophyName;
	}

	public String getTrophyDescription() {
		return trophyDescription;
	}

	public void setTrophyPoints(int trophyPoints) {
		this.trophyPoints = trophyPoints;
	}

	public int getTrophyPoints() {
		return trophyPoints;
	}
	
}
