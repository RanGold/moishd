package moishd.server.servlets.test;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.PMF;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.UserServiceFactory;

public class UnBusyUsersServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8784884759201930744L;

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
						user.setNotBusy();
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