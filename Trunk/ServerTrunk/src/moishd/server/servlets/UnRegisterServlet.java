package moishd.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.PMF;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UnRegisterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			try {
				response.addHeader("Error", "");
				response.getWriter().println("Error: no logged in user");
			} catch (IOException e) {
				response.addHeader("Error", e.getMessage());
			}
		} else {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				Query q = pm.newQuery(MoishdUser.class);
				q.setFilter("userGoogleIdentifier == :idParam");

				List<MoishdUser> users = (List<MoishdUser>)q.execute(user.getEmail());
				if (users.size() == 0) {
					response.addHeader("Error", "");
					response.getWriter().println("RegisterServlet: user " + user.getEmail() + " not found");
				} else if (users.size() > 1) {
					response.addHeader("Error", "");
					response.getWriter().println("RegisterServlet: user " + user.getEmail() + " more than 1 result");
				} else {
					users.get(0).setRegisterID("NULL");
					pm.makePersistent(users.get(0));	
				}
			} catch (IOException e) {
				response.addHeader("Error", e.getMessage());
			} finally {
				pm.close();
			}
		}
	}
}
