package moishd.server;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class MoishdGroup implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6574787548336679082L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key groupId;

    @Persistent
    private String name;

	@Persistent
    private Date dateCreated; 
	
	public MoishdGroup() {
	}
	
	public MoishdGroup(String name) {
		this.name = name;
		this.dateCreated = new Date();
	}
	
	public Key getGroupId() {
		return groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

}