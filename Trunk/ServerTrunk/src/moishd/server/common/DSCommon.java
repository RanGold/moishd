package moishd.server.common;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.dataObjects.C2DMAuth;
import moishd.server.dataObjects.CommonJDO;
import moishd.server.dataObjects.Location;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.dataObjects.TimeGame;

public class DSCommon {
	private DSCommon() {
	}
	
	private static List<MoishdUser> DetachCopyRecursively(List<MoishdUser> users, PersistenceManager pm) {
		List<MoishdUser> dUsers = new LinkedList<MoishdUser>();
		
		for (MoishdUser user : users) {
			MoishdUser tempUser = pm.detachCopy(user);
			tempUser.setLocation(pm.detachCopy(user.getLocation()));
			tempUser.setStats(pm.detachCopy(user.getStats()));
			dUsers.add(tempUser);
		}
		
		return (dUsers);
	}
	
	public static List<ClientMoishdUser> GetAllRegisteredClientUsers(String GoogleId, 
			Boolean exclude) throws DataAccessException {
		return GetRegisteredClientUsers(GoogleId, exclude, -1, "NULL", null);
	}
	
	public static List<ClientMoishdUser> GetAllRegisteredClientUsers(String GoogleId, 
			boolean exclude, long amount) throws DataAccessException {
		return GetRegisteredClientUsers(GoogleId, exclude, amount, "NULL", null);
	}
	
	@SuppressWarnings("unchecked")
	private static List<ClientMoishdUser> GetRegisteredClientUsers(String GoogleId, 
			boolean exclude, long amount, String fieldName, List<String> filterValues) 
	throws DataAccessException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);
			List<Object> filterParams = new LinkedList<Object>();
			String filter = "";
			
			if (exclude) {
				q.setFilter("userGoogleIdentifier != :idParam");
				filter = "userGoogleIdentifier != :idParam && ";
				filterParams.add(GoogleId);
			}

			// TODO Check this
			filter += "isRegistered == :trueParam && ";
			q.setFilter(filter);
			filterParams.add(true);
			
			// TODO Check this
			if (amount != -1) {
				q.setRange(0, amount);
			}
			// TODO Check this
			if (!fieldName.equals("NULL")) {
				filter += ":p.contains(" + fieldName + ")";
				q.setFilter(filter);
				filterParams.add(filterValues);
			}
			
			if (filter.endsWith("&& ")) {
				filter = filter.substring(0, filter.length() - 3);
				q.setFilter(filter);
			}
			
			// TODO add filter implementation
			List<MoishdUser> users = (List<MoishdUser>) q.executeWithArray(filterParams.toArray());
//			if (exclude) {
//				users = ((List<MoishdUser>)q.execute(GoogleId));
//			} else {
//				users = ((List<MoishdUser>)q.execute());
//			}
			
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
	public static List<C2DMAuth> GetAllC2DMAuth() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			return (List<C2DMAuth>) 
			(pm.detachCopyAll((List<C2DMAuth>)
					pm.newQuery(C2DMAuth.class).execute()));
		} finally {
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
		C2DMAuth auth;
		if (GetAllC2DMAuth().size() > 0) { 
			auth = GetC2DMAuth();
		} else {
			auth = new C2DMAuth();
		}
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

	public static List<ClientMoishdUser> GetFilteredRegisteredClientUsers(
			String GoogleId, Boolean exclude, Field field, 
			List<String> macAddresses) throws DataAccessException {
		if (!field.getDeclaringClass().getName().equals("MoishdUser")) {
			throw new DataAccessException("Field " + field.getName() + " isn't a part of MoishdUser");
		} else {
			return GetRegisteredClientUsers(GoogleId, exclude, -1, 
					field.getName(), macAddresses);
		}
	}
	
	public static void SaveChanges(CommonJDO jdoObject) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(jdoObject);
		}
		finally {
			pm.close();
		}
	}
	
	public static List<MoishdUser> GetNearbyUsers(MoishdUser user, double distance) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			@SuppressWarnings("unchecked")
			List<Location> locations = (List<Location>) pm.newQuery(Location.class).execute();
			List<MoishdUser> users = new LinkedList<MoishdUser>();
			
			for (Location location : locations) {
				if (CalculateDistance(location.getxCoordinate(), location.getyCoordinate(), 
						user.getLocation().getxCoordinate(), user.getLocation().getyCoordinate()) <= distance) {
					users.add(location.getMoishdUser());
				}
			}
			
			return DetachCopyRecursively(users, pm);
		}
		finally {
			pm.close();
		}
	}
	
	public static double CalculateDistance(double lat1, double long1, double lat2, double long2)
	{
		double d2r = (Math.PI / 180.0);
		
	    double dlong = (long2 - long1) * d2r;
	    double dlat = (lat2 - lat1) * d2r;
	    double a = Math.pow(Math.sin(dlat/2.0), 2) + 
	    Math.cos(lat1*d2r) * Math.cos(lat2*d2r) * 
	    Math.pow(Math.sin(dlong/2.0), 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double d = 6367 * c;

	    return d;
	}
	
	
}
