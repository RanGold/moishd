package moishd.common;

public enum GamesEnum {
	Truth("Trivia"),
	DareSimonPro("Simon Pro"),
	DareMixing("Mixing"),
	DareFastClick("Fast Click");
	
	private String name;

	GamesEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
