package moishd.client.dataObjects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ClientUserGameStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 586083315477039152L;

	private int gamesPlayed;

	private int gamesWon;

	private int rank;

	private int points;
	
	private int gamesWonInARow;
	
	private int topMoisherPoints;
	
	private Map<String, Integer> gamesPoints;

	public ClientUserGameStatistics() {
		super();
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.rank = 0;
		this.points = 0;
		this.gamesWonInARow = 0;
		this.topMoisherPoints = -1;
		this.setGamesPoints(new HashMap<String, Integer>());
	}

	public ClientUserGameStatistics(int gamesPlayed, int gamesWon,int rank, int points, 
			int gamesWonInARow, int topMoisherPoints, Map<String, Integer> gamesPoints) {
		super();
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.rank = rank;
		this.points = points;
		this.gamesWonInARow = gamesWonInARow;
		this.topMoisherPoints = topMoisherPoints;
		this.setGamesPoints(gamesPoints);
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getGamesWonInARow() {
		return gamesWonInARow;
	}

	public void setGamesWonInARow(int gamesWonInARow) {
		this.gamesWonInARow = gamesWonInARow;
	}

	public void setTopMoisherPoints(int topMoisherPoints) {
		this.topMoisherPoints = topMoisherPoints;
	}

	public int getTopMoisherPoints() {
		return topMoisherPoints;
	}

	public void setGamesPoints(Map<String, Integer> gamesPoints) {
		this.gamesPoints = gamesPoints;
	}

	public Map<String, Integer> getGamesPoints() {
		return gamesPoints;
	}
}
