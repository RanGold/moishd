package moishd.server;

import com.google.appengine.api.datastore.Key;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class GameStatistics {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key statsId;

    @Persistent
    private Integer gamesPlayed;
    
    @Persistent
    private Integer gamesWon;
    
    @Persistent
    private Integer rank;
    
    @Persistent
    private Integer points;
    
    @Persistent
    private List<Trophy> trophies;

    public GameStatistics() {
    	this.gamesPlayed = 0;
    	this.gamesWon = 0;
    	this.rank = -1;
    	this.points = 0;
    	trophies = new LinkedList<Trophy>();
    }
    
    public GameStatistics(Integer gamesPlayed, Integer gamesWon, Integer rank, Integer points) {
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.rank = rank;
        this.points = points;
        trophies = new LinkedList<Trophy>();
    }

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public Integer getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Key getStatsId() {
		return statsId;
	}

	public List<Trophy> getTrophies() {
		return trophies;
	}
    
    
/*
    public Key getKey() {
        return key;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/
}