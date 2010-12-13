package moishd.server.common;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.dataObjects.C2DMAuth;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.dataObjects.TimeGame;

public class DSCommon {
	private DSCommon() {
	}
	
	private static List<MoishdUser> DetachCopyRecursively(List<MoishdUser> users, PersistenceManager pm) {
		List<MoishdUser> dUsers = new LinkedList<MoishdUser>();
		
		for (MoishdUser user : users) {
			MoishdUser tempUser = pm.detachCopy(user);
			//tempUser.setLocation(pm.detachCopy(user.getLocation()));
			tempUser.setStats(pm.detachCopy(user.getStats()));
			dUsers.add(tempUser);
		}
		
		return (dUsers);
	}
	
	@SuppressWarnings("unchecked")
	public static List<ClientMoishdUser> GetAllRegisteredClientUsers(String GoogleId, Boolean exclude) 
	throws DataAccessException {
		// TODO Returning just registered users
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);
			
			if (exclude) {
				q.setFilter("userGoogleIdentifier != :idParam");
			}

			List<MoishdUser> users = null;
			if (exclude) {
				users = ((List<MoishdUser>)q.execute(GoogleId));
			} else {
				users = ((List<MoishdUser>)q.execute());
			}
			
			return (MoishdUser.copyToClientMoishdUserList(users));
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

			return (pm.detachCopy(((List<C2DMAuth>) q.execute()).get(0)));
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
	
	@SuppressWarnings("unchecked")
	private static List<MoishdUser> GetUsersByGoogleId(String GoogleId) throws DataAccessException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);
			q.setFilter("userGoogleIdentifier == :idParam");

			return (DetachCopyRecursively((List<MoishdUser>)q.execute(GoogleId), pm));
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	public static Boolean DoesUserByGoogleIdExist(String GoogleId) throws DataAccessException {
		List<MoishdUser> users = GetUsersByGoogleId(GoogleId);
		
		if (users.size() > 1) {
			throw new DataAccessException("user " + GoogleId + " more than 1 result");
		} else {
			return (users.size() != 0);
		}
	}
	
	public static MoishdUser GetUserByGoogleId(String GoogleId) throws DataAccessException {
		List<MoishdUser> users = GetUsersByGoogleId(GoogleId);
		if (users.size() == 0) {
			throw new DataAccessException("user " + GoogleId + " not found");
		} else if (users.size() > 1) {
			throw new DataAccessException("user " + GoogleId + " more than 1 result");
		} else {
			return users.get(0);
		}
	}
	
	public static TimeGame GetTimeGameByIdRecId(String gameId, String recId) throws DataAccessException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(TimeGame.class);
			q.setFilter("playerRecId == :playerId && gameLongId == :gId");

			@SuppressWarnings("unchecked")
			List<TimeGame> games = (List<TimeGame>)q.execute(recId, Long.valueOf(gameId));

			if (games.size() == 0) {
				throw new DataAccessException("game " + gameId + 
						" doesn't exist for " + recId);
			} else if (games.size() > 1) {
				throw new DataAccessException("game " + gameId + 
						" has more than 1 result for " + recId);
			} else {
				return (pm.detachCopy(games.get(0)));
			}
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	public static TimeGame GetTimeGameById(String gameId) throws DataAccessException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(TimeGame.class);
			q.setFilter("gameLongId == :gId");

			@SuppressWarnings("unchecked")
			List<TimeGame> games = (List<TimeGame>)q.execute(Long.valueOf(gameId));

			if (games.size() == 0) {
				throw new DataAccessException("game " + gameId + 
						" doesn't exist");
			} else if (games.size() > 1) {
				throw new DataAccessException("game " + gameId + 
						" has more than 1 result");
			} else {
				return (pm.detachCopy(games.get(0)));
			}
		} catch (NumberFormatException e) {
			throw new DataAccessException("game id " + gameId + 
			" is invalid");
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
}
