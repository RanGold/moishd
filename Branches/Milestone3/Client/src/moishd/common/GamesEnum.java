package moishd.common;

public enum GamesEnum {
	Truth("Truth", "Trivia"),
	DareSimonPro("DareSimonPro", "Simon Pro"),
	DareMixing("DareMixing", "Mixing"),
	DareFastClick("DareFastClick", "Fast Click");
	
	private String name;
	private String enumName;

	GamesEnum(String enumName, String name) {
		this.name = name;
		this.enumName = enumName;
	}

	public String toString() {
		return this.enumName;
	}
	
	public String getFullName() {
		return this.name;
	}
}
