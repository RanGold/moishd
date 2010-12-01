package moishd.server.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.PMF;
import moishd.server.dataObjects.C2DMAuth;

import com.google.appengine.api.users.UserServiceFactory;

public class C2DMAuthServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8795970457297476756L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		if (UserServiceFactory.getUserService().isUserLoggedIn()
				&& UserServiceFactory.getUserService().isUserAdmin()) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				try {
					Query q = pm.newQuery(C2DMAuth.class);
					@SuppressWarnings("unchecked")
					C2DMAuth c2dmAuth = ((List<C2DMAuth>) q.execute()).get(0);
					c2dmAuth.setAuthKey(C2DMCommon.getAuthToken(true));
					c2dmAuth.setDate(new Date());
					pm.makePersistent(c2dmAuth);
				} catch (Exception e) {
					response.addHeader("Error", e.getMessage());
					response.getWriter().println(e.getMessage());
				}
			} catch (IOException e) {
				response.addHeader("Error", e.getMessage());
			} finally {
				pm.close();
			}
		}

	}
}
