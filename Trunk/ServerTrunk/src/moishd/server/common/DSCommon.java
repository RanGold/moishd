package moishd.server.common;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import moishd.client.dataObjects.ClientLocation;
import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.dataObjects.C2DMAuth;
import moishd.server.dataObjects.CommonJDO;
import moishd.server.dataObjects.GameStatistics;
import moishd.server.dataObjects.Location;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.dataObjects.MoishdUser;

import com.google.gson.reflect.TypeToken;

public class DSCommon {
	private DSCommon() {
	}
	
	private static List<MoishdUser> DetachCopyRecursively(List<MoishdUser> users, PersistenceManager pm) {
		List<MoishdUser> dUsers = new LinkedList<MoishdUser>();
		
		for (MoishdUser user : users) {
			dUsers.add(DetachCopyUser(user, pm));
		}
		
		return (dUsers);
	}
	
	private static MoishdUser DetachCopyUser(MoishdUser user, PersistenceManager pm) {
		MoishdUser tempUser = pm.detachCopy(user);
		tempUser.setLocation(pm.detachCopy(user.getLocation()));
		tempUser.setStats(pm.detachCopy(user.getStats()));
		return tempUser;
	}
	
	private static List<Location> DetachCopyLocations(List<Location> locations, PersistenceManager pm) {
		List<Location> dLocations = new LinkedList<Location>();
		
		for (Location loc : locations) {
			Location tempLoc = pm.detachCopy(loc);
			tempLoc.setMoishdUser(DetachCopyUser(loc.getMoishdUser(),pm));
			dLocations.add(tempLoc);
		}
		
		return (dLocations);
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

			filter += "isRegistered == :trueParam && ";
			q.setFilter(filter);
			filterParams.add(true);
			
			// TODO Check this
			if (amount != -1) {
				q.setRange(0, amount);
			}
			
			if (!fieldName.equals("NULL") && (filterValues.size() > 0)) {
				filter += ":p.contains(" + fieldName + ")";
				q.setFilter(filter);
				filterParams.add(filterValues);
			}
			
			if (filter.endsWith("&& ")) {
				filter = filter.substring(0, filter.length() - 3);
				q.setFilter(filter);
			}
			
			List<MoishdUser> users = (List<MoishdUser>) q.executeWithArray(filterParams.toArray());
			users = DetachCopyRecursively(users, pm);
			
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
	public static List<MoishdUser> GetUsersByGoogleId(String GoogleId) throws DataAccessException {
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
	
	public static MoishdGame GetGameByIdRecId(String gameId, String recId) throws DataAccessException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdGame.class);
			q.setFilter("playerRecId == :playerId && gameLongId == :gId");

			@SuppressWarnings("unchecked")
			List<MoishdGame> games = (List<MoishdGame>)q.execute(recId, Long.valueOf(gameId));

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
	
	public static MoishdGame GetGameById(String gameId) throws DataAccessException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdGame.class);
			q.setFilter("gameLongId == :gId");

			@SuppressWarnings("unchecked")
			List<MoishdGame> games = (List<MoishdGame>)q.execute(Long.valueOf(gameId));

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
			String GoogleId, Boolean exclude, String field, 
			List<String> macAddresses) throws DataAccessException {
		return GetRegisteredClientUsers(GoogleId, exclude, -1, field,
				macAddresses);
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
			Query q = pm.newQuery("SELECT FROM " + Location.class.getName() + " WHERE " +
					"longitude < 200.0");
			
			@SuppressWarnings("unchecked")
			List<Location> locations = (List<Location>) q.execute();
			List<MoishdUser> users = new LinkedList<MoishdUser>();
			
			locations = DetachCopyLocations(locations, pm);
			
			for (Location location : locations) {
				if (CalculateDistance(location, user.getLocation()) <= distance) {
					if (!user.getUserGoogleIdentifier().equals(location.getMoishdUser().getUserGoogleIdentifier())) {
						users.add(location.getMoishdUser());
					}
				}
			}
			
			return users;
		}
		finally {
			pm.close();
		}
	}
	
	public static Map<String, String> GetNearbyUsersSets(double distance) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query q = pm.newQuery("SELECT FROM " + Location.class.getName() + " WHERE " +
					"longitude < 200.0");
			
			@SuppressWarnings("unchecked")
			List<Location> locations = (List<Location>) q.execute();
			Map<String, String> usersSets = new HashMap<String, String>();
			
			locations = DetachCopyLocations(locations, pm);
			
			for (Location location : locations) {
				String curUserGI = location.getMoishdUser().getUserGoogleIdentifier();
				int curPoints = location.getMoishdUser().getStats().getPoints();
				for (Location secLocation : locations) {
					if (!curUserGI.equals(secLocation.getMoishdUser().getUserGoogleIdentifier()) &&
							(!location.getMoishdUser().isBusy()) &&
							(!secLocation.getMoishdUser().isBusy()) &&
							(CalculateDistance(location, secLocation) <= distance) &&
							(curPoints < secLocation.getMoishdUser().getStats().getPoints())) {
						usersSets.put(location.getMoishdUser().getRegisterID(), 
								secLocation.getMoishdUser().getUserGoogleIdentifier() + "#" +
								secLocation.getMoishdUser().getUserNick());
						break;
					}
				}
			}
			
			return usersSets;
		}
		finally {
			pm.close();
		}
	}
	
	public static double CalculateDistance(Location location1, Location location2) {
		return CalculateDistance(location1.toClientLocaion(), location2.toClientLocaion());
	}
	
	public static double CalculateDistance(ClientLocation location1, ClientLocation location2) {
		return CalculateDistance(location1.getLatitude(), location1.getLongitude(), 
				location2.getLatitude(), location2.getLongitude());
	}
	
	private static double CalculateDistance(double lat1, double long1, double lat2, double long2)
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
	
	public static List<String> GetExistingFacebookIds(List<String> facebookIds) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);

			String filter = ":p.contains(facebookID)";
			q.setFilter(filter);
			
			@SuppressWarnings("unchecked")
			List<MoishdUser> users = (List<MoishdUser>) q.execute(facebookIds);
			users = (List<MoishdUser>) pm.detachCopyAll(users);
			
			List<String> existingFacebookIds = new LinkedList<String>();
			for (MoishdUser user : users) {
				existingFacebookIds.add(user.getFacebookID());
			}
			
			return (existingFacebookIds);
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	public static List<String> GetCheckAliveRegisterIds() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);

			q.setFilter("isAlive == :1");
			
			@SuppressWarnings("unchecked")
			List<MoishdUser> users = (List<MoishdUser>) q.execute(1);
			users = (List<MoishdUser>) pm.detachCopyAll(users);
			
			List<String> checkAliveRegisterIds = new LinkedList<String>();
			for (MoishdUser user : users) {
				user.setIsAlive(2);
				checkAliveRegisterIds.add(user.getRegisterID());
			}
			pm.makePersistentAll(users);
			
			return (checkAliveRegisterIds);
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	public static void SetCheckAliveRegisterIds() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);

			q.setFilter("isAlive == :1");
			
			@SuppressWarnings("unchecked")
			List<MoishdUser> users = (List<MoishdUser>) q.execute(0);
			users = (List<MoishdUser>) pm.detachCopyAll(users);
			
			for (MoishdUser user : users) {
				user.setIsAlive(1);
			}
			pm.makePersistentAll(users);
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	public static void UnregisterDisconnectedUsers() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(MoishdUser.class);

			q.setFilter("isAlive == :1 && isRegistered == :trueParam");
			
			@SuppressWarnings("unchecked")
			List<MoishdUser> users = (List<MoishdUser>) q.execute(2, true);
			users = DSCommon.DetachCopyRecursively(users, pm);
			
			for (MoishdUser user : users) {
				user.InitUser();
				user.setIsAlive(2);
			}
			pm.makePersistentAll(users);
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}

	public static CommonJDO DetachThis(CommonJDO jdoObject) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if (new TypeToken<MoishdUser>(){}.getType().toString().split(" ")[1].equals(jdoObject.getClass().getName())) {
				return DetachCopyUser((MoishdUser)jdoObject, pm);
			} else {
				return (pm.detachCopy(jdoObject));
			}
		}
		finally {
			pm.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<GameStatistics> GetGameStatsByType(String gameType) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(GameStatistics.class);
			q.setFilter("gameType == :typeParam");

			return ((List<GameStatistics>)pm.detachCopyAll((List<GameStatistics>)q.execute(gameType)));
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String GetMostPopularGame() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(GameStatistics.class);
			q.setOrdering("gameRank descending");
			
			List<GameStatistics> stats = (List<GameStatistics>)pm.detachCopyAll((List<GameStatistics>)q.execute());
			
			double popularPoints = 0;
			String popularName = "DareSimonPro";
			if (stats.size() == 0) {
				LoggerCommon.Get().LogInfo("DSCommon", "No stats for any game returning default - DareSimonPro");
			}
			int maxPlayed = 0;
			for (GameStatistics stat : stats) {
				if (stat.getTimesPlayed() > maxPlayed) {
					maxPlayed = stat.getTimesPlayed();
				}
			}
			for (GameStatistics stat : stats) {
				double temp = stat.getRankTotal() / (double)5 * 0.5 +
				(double)stat.getTimesPlayed() / (double)maxPlayed * 0.5; 
				if (temp > popularPoints) {
					popularName = stat.getGameType();
					popularPoints = temp;
				}
			}

			return (popularName);
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}
	
	public static List<String> GetTopFiveGames(String field) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			q = pm.newQuery(GameStatistics.class);
			q.setOrdering(field + " descending");
			q.setRange(0, 4);
			
			@SuppressWarnings("unchecked")
			List<GameStatistics> stats = (List<GameStatistics>)pm.detachCopyAll((List<GameStatistics>)q.execute());
			LinkedList<String> topFive = new LinkedList<String>();
			if (stats.size() == 0) {
				LoggerCommon.Get().LogInfo("DSCommon", "No ranks for any game returning default - none");
			}

			for (GameStatistics stat : stats) {
				topFive.add(stat.getGameType() + ":" + stat.getGameRank());
			}

			return topFive;
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}

	public static void DeleteGameById(Long gameId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		try {
			LoggerCommon.Get().LogInfo("DSCommon", "Deleting game " + gameId);
			q = pm.newQuery(MoishdGame.class);
			q.setFilter("gameLongId == :id");

			q.deletePersistentAll(gameId);
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
	}

	public static void DeleteLastUnDecidedGame(String initId,
			String recId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = null;
		long gameId = -1;
		try {
			q = pm.newQuery(MoishdGame.class);
			q.setFilter("playerInitId == :id1 && playerRecId == :id2 && gameType == :ND");
			q.setOrdering("initiated ascending");
			q.setRange(0, 1);
			LoggerCommon.Get().LogInfo("DSCommon", "initId: " + initId + " recId: " + recId);
			
			@SuppressWarnings("unchecked")
			List<MoishdGame> mg = (List<MoishdGame>)q.executeWithArray(initId, recId, "NotDecided");
			
			if (mg.size() == 0) {
				LoggerCommon.Get().LogInfo("DSCommon", "No games match");
			} else {
				gameId = mg.get(0).getGameLongId();
			}
			
			LoggerCommon.Get().LogInfo("DSCommon", String.valueOf(gameId));
		}
		finally {
			if (q != null) {
				q.closeAll();
			}
			pm.close();
		}
		
		if (gameId != -1) {
			DSCommon.DeleteGameById(gameId);
		}
	}
}
