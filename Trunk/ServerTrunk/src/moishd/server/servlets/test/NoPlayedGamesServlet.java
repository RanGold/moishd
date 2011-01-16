package moishd.server.servlets.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.PMF;
import moishd.server.dataObjects.GameStatistics;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.UserServiceFactory;

public class NoPlayedGamesServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9169341762294249196L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (!UserServiceFactory.getUserService().isUserLoggedIn()) {
			response.sendRedirect("/test/Login");
		} else {
			if (!UserServiceFactory.getUserService().isUserAdmin()) {
				response.getWriter().println("<p>" +
	                    "You need to be admin to delete users.</p>");
				response.getWriter().println("<p>" +
	                    "<a href=\"/" +
	                    "\">Return Home</a>.</p>");
			} else {
				PersistenceManager pm = PMF.get().getPersistenceManager();
				
				try {
					@SuppressWarnings("unchecked")
					List<MoishdUser> users = (List<MoishdUser>) pm.newQuery(MoishdUser.class).execute();
					
					for (MoishdUser user : users) {
						Map<String, Integer> map = new HashMap<String, Integer>();
						map.put("Truth", 2);
						map.put("DareMixing", 2);
						map.put("DareSimonPro", 2);
						map.put("DareFastClick", 2);

						user.setGameTypesPlayed(map);
						pm.makePersistent(user);
					}
				} finally {
					pm.close();
				}
				
				pm = PMF.get().getPersistenceManager();
				try {
					@SuppressWarnings("unchecked")
					List<MoishdUser> users = (List<MoishdUser>) pm.newQuery(MoishdUser.class).execute();
					
					for (MoishdUser user : users) {
						user.getTrophies().clear();
						
						if (user.getUserGoogleIdentifier().equals("moishd2011@gmail.com") ||
								user.getUserGoogleIdentifier().equals("moishd2010@gmail.com")) {
							user.getLocation().setLatitude(32.06387);
							user.getLocation().setLongitude(34.779925);
						}
					}
					
					pm.makePersistentAll(users);
				} finally {
					pm.close();
				}
				
				pm = PMF.get().getPersistenceManager();
				try {
					@SuppressWarnings("unchecked")
					List<GameStatistics> games = (List<GameStatistics>) pm.newQuery(GameStatistics.class).execute();
					
					for (GameStatistics game : games) {
						game.setTimesPlayed(10);
						game.setGameRank(3.0);
						game.setRankingNumber(10);
						game.setRankTotal(30);
					}
					
					pm.makePersistentAll(games);
				} finally {
					pm.close();
				}
				
				pm = PMF.get().getPersistenceManager();
				try {
					@SuppressWarnings("unchecked")
					List<MoishdUser> users = (List<MoishdUser>) pm.newQuery(MoishdUser.class).execute();
					
					for (MoishdUser user : users) {
						user.getStats().setPoints(20);
						
						if (user.getUserGoogleIdentifier().equals("moishd2011@gmail.com")) {
							user.getStats().setPoints(10);
						}
					}
					
					pm.makePersistentAll(users);
				} finally {
					pm.close();
				}

				response.sendRedirect("/");
			}
		}
	}
}