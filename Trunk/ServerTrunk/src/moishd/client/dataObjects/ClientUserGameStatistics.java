package moishd.client.dataObjects;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import moishd.server.dataObjects.StringIntPair;

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
	
	private List<StringIntPair> gamesPoints;

	public ClientUserGameStatistics() {
		super();
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.rank = 0;
		this.points = 0;
		this.gamesWonInARow = 0;
		this.setGamesPoints(new LinkedList<StringIntPair>());
	}

	public ClientUserGameStatistics(int gamesPlayed, int gamesWon,int rank, int points, int gamesWonInARow, List<StringIntPair> gamesPoints) {
		super();
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.rank = rank;
		this.points = points;
		this.gamesWonInARow = gamesWonInARow;
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

	public void setGamesPoints(List<StringIntPair> gamesPoints) {
		this.gamesPoints = gamesPoints;
	}

	public List<StringIntPair> getGamesPoints() {
		return gamesPoints;
	}
}
