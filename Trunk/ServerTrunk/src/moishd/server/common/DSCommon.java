package moishd.server.common;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import moishd.server.dataObjects.C2DMAuth;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.dataObjects.TimeGame;

public class DSCommon {
	private DSCommon() {
	}
	
	@SuppressWarnings("unchecked")
	public static List<MoishdUser> GetAllRegisteredUsers(String GoogleId, Boolean exclude) 
	throws DataAccessException {
		// TODO Returning just registered users
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);
			
			if (exclude) {
				q.setFilter("userGoogleIdentifier != :idParam");
			}

			if (exclude) {
				return ((List<MoishdUser>)q.execute(GoogleId));
			} else {
				return ((List<MoishdUser>)q.execute());
			}
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static C2DMAuth GetC2DMAuth() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(C2DMAuth.class);

			return (((List<C2DMAuth>) q.execute()).get(0));
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	public static void SetC2DMAuth(String newAuth) {
		C2DMAuth auth = GetC2DMAuth();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			auth.setAuthKey(newAuth);
			auth.setDate(new Date());
			pm.makePersistent(auth);
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	public static MoishdUser GetUserByGoogleId(String GoogleId) throws DataAccessException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);
			q.setFilter("userGoogleIdentifier == :idParam");

			@SuppressWarnings("unchecked")
			List<MoishdUser> users = (List<MoishdUser>)q.execute(GoogleId);
			if (users.size() == 0) {
				throw new DataAccessException("user " + GoogleId + " not found");
			} else if (users.size() > 1) {
				throw new DataAccessException("user " + GoogleId + " more than 1 result");
			} else {
				return users.get(0);
			}
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	public static TimeGame GetTimeGameByIdRecId(String gameId, String recId) throws DataAccessException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(TimeGame.class);
			q.setFilter("playerRecId = :playerId && gameId == :gId");

			@SuppressWarnings("unchecked")
			List<TimeGame> games = (List<TimeGame>)q.execute(recId, gameId);

			if (games.size() == 0) {
				throw new DataAccessException("game " + gameId + 
						" doesn't exist for " + recId);
			} else if (games.size() > 1) {
				throw new DataAccessException("game " + gameId + 
						" doesn't exist for " + recId + " more than 1 result");
			} else {
				return (games.get(0));
			}
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
}
