package moishd.common;

public enum GamesEnum {
	
	Truth("Truth", "Trivia"),
	DareSimonPro("DareSimonPro", "Simon Pro"),
	DareMixing("DareMixing", "Mixed Word"),
	DareFastClick("DareFastClick", "Fast Click"),
	DarePixOPair("StartGameDarePixOPair", "Pix-O-Pair");
	
	private String name;
	private String enumName;

	GamesEnum(String enumName, String name) {
		this.enumName = enumName;
		this.name = name;
	}

	public String toString() {
		return this.enumName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static String getGameName(GamesEnum gameName){

		switch (gameName){
		case DareFastClick:
			return GamesEnum.DareFastClick.toString();
		case DareMixing:
			return GamesEnum.DareMixing.toString();
		case DareSimonPro:
			return GamesEnum.DareSimonPro.toString();
		case Truth:
			return GamesEnum.Truth.toString();
		case DarePixOPair:
			return GamesEnum.DarePixOPair.toString();
		}
		return null;
	}
	
	public static String getGameNameWithSpaces(String gameName){
		
		if (gameName.equals(IntentExtraKeysEnum.DareFastClick.toString())) {
			return GamesEnum.DareFastClick.getName();
		}
		else if (gameName.equals(IntentExtraKeysEnum.DareMixing.toString())) {
			return GamesEnum.DareMixing.getName();
		}
		else if (gameName.equals(IntentExtraKeysEnum.DareSimonPro.toString())) {
			return GamesEnum.DareSimonPro.getName();
		}
		else if (gameName.equals(IntentExtraKeysEnum.Truth.toString())) {
			return GamesEnum.Truth.getName();
		}
		else if (gameName.equals(IntentExtraKeysEnum.DarePixOPair.toString())) {
			return GamesEnum.Truth.getName();
		}
		else{
			return null;
		}
	}
}
