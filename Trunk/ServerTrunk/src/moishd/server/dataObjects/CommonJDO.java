package moishd.server.dataObjects;

import moishd.server.common.DSCommon;

public class CommonJDO {
	public void SaveChanges() {
		DSCommon.SaveChanges(this);
	}
	
	public CommonJDO DetachCopy() {
		return (DSCommon.DetachThis(this));
	}
}