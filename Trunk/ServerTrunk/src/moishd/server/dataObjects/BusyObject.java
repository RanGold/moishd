package moishd.server.dataObjects;

import java.io.Serializable;

public class BusyObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8554703976722772351L;

	private String userId;

	private boolean isBusy;
	
	private String busyWith;

	public BusyObject() {
		super();
	}

	public BusyObject(String userId, boolean isBusy, String busyWith) {
		super();
		this.setUserId(userId);
		this.setBusy(isBusy);
		this.setBusyWith(busyWith);
	}
	
	public BusyObject(String userId, boolean isBusy) {
		super();
		this.setUserId(userId);
		this.setBusy(isBusy);
		this.setBusyWith("");
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusyWith(String busyWith) {
		this.busyWith = busyWith;
	}

	public String getBusyWith() {
		return busyWith;
	}
	
	
}
