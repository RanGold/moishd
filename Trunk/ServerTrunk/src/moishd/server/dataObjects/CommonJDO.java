package moishd.server.dataObjects;

import javax.jdo.PersistenceManager;

import moishd.server.common.PMF;

public class CommonJDO {
	public void SaveChanges() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(this);
		}
		finally {
			pm.close();
		}
	}
}