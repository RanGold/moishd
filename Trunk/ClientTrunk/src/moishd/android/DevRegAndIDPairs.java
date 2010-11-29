package moishd.android;

import java.io.Serializable;

public class DevRegAndIDPairs implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String deviceReg;
	
	private String registration_ID;
	
	public DevRegAndIDPairs(String deviceReg, String reg_id){
		this.deviceReg = deviceReg;
		this.registration_ID = reg_id;
	}
	
	public String getDevReg(){
		return deviceReg;
	}
	
	public String getRegId(){
		return registration_ID;
	}	
	
	public DevRegAndIDPairs()
	{
		this.deviceReg = null;
		this.registration_ID = null;
	}

	
}
