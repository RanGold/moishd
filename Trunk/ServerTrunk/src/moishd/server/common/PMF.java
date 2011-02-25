package moishd.server.common;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

// Singleton access to the datastore
public final class PMF {
    private static PersistenceManagerFactory pmfInstance;
        

    private PMF() {}

    public static PersistenceManagerFactory get() {
    	if (pmfInstance == null) {
    		pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    	}
        
    	return pmfInstance;
    }
}