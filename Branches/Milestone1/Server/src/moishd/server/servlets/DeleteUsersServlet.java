package moishd.server.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import moishd.server.common.PMF;
import moishd.server.dataObjects.MoishdUser;

public class DeleteUsersServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -141938567919490948L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (UserServiceFactory.getUserService().isUserLoggedIn()
				&& UserServiceFactory.getUserService().isUserAdmin()) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.newQuery(MoishdUser.class).deletePersistentAll();
			} finally {
				pm.close();
			}
			response.sendRedirect("/");
		}
	}
}