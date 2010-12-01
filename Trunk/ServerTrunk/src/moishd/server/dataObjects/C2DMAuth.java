package moishd.server.dataObjects;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class C2DMAuth implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 494898252622604155L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key AuthId;

    @Persistent
    private String authKey;

    @Persistent
    private Date date;
    
    public C2DMAuth() {
		super();
		this.authKey = "";
	}
    
    public C2DMAuth(String authKey) {
		super();
		this.authKey = authKey;
		this.date = new Date();
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public Key getAuthId() {
		return AuthId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}