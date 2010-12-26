package moishd.client.dataObjects;

import java.io.Serializable;

public class ClientUserGameStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 586083315477039152L;

	private int gamesPlayed;

	private int gamesWon;

	private int rank;

	private int points;

	public ClientUserGameStatistics() {
		super();
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.rank = 0;
		this.points = 0;
	}

	public ClientUserGameStatistics(int gamesPlayed, int gamesWon,
			int rank, int points) {
		super();
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.rank = rank;
		this.points = points;
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

}
