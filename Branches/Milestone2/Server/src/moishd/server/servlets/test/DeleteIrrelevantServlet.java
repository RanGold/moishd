package moishd.server.servlets.test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.PMF;
import moishd.server.dataObjects.Location;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.dataObjects.UserGameStatistics;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.UserServiceFactory;

public class DeleteIrrelevantServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -141938567919490948L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (!UserServiceFactory.getUserService().isUserLoggedIn()) {
			response.sendRedirect("/test/Login");
		} else {
			if (!UserServiceFactory.getUserService().isUserAdmin()) {
				response.getWriter().println("<p>" +
	                    "You need to be admin to delete Irrelevant.</p>");
				response.getWriter().println("<p>" +
	                    "<a href=\"/" +
	                    "\">Return Home</a>.</p>");
			} else {
				List<Key> locKeys = new LinkedList<Key>();
				List<Key> statsKeys = new LinkedList<Key>();
				
				PersistenceManager pm = PMF.get().getPersistenceManager();
				try {
					@SuppressWarnings("unchecked")
					List<MoishdUser> users = (List<MoishdUser>) pm.newQuery(MoishdUser.class).execute();
					
					for (MoishdUser user : users) {
						locKeys.add(user.getLocation().getLocationId());
						statsKeys.add(user.getStats().getStatsId());
					}	
				} finally {
					pm.close();
				}
				
				pm = PMF.get().getPersistenceManager();
				try {
					@SuppressWarnings("unchecked")
					List<Location> locations = (List<Location>) pm.newQuery(Location.class).execute();
					
					for (Location location : locations) {
						if (!locKeys.contains(location.getLocationId())) {
							pm.deletePersistent(location);
						}
					}	
				} finally {
					pm.close();
				}
				
				pm = PMF.get().getPersistenceManager();
				try {
					@SuppressWarnings("unchecked")
					List<UserGameStatistics> stats = (List<UserGameStatistics>) pm.newQuery(UserGameStatistics.class).execute();
					
					for (UserGameStatistics stat : stats) {
						if (!statsKeys.contains(stat.getStatsId())) {
							pm.deletePersistent(stat);
						}
					}	
				} finally {
					pm.close();
				}
				
				response.sendRedirect("/");
			}
		}
	}
}