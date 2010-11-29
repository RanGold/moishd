package moishd.serever.servlets;

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

public class InsertUserServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1306793921674383722L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.sendRedirect("/LoginServlet");
		} else {
			// TODO : add proper constructor
			MoishdUser muser = new MoishdUser(user.getNickname(), "adsasd", user.getEmail(), "1234");

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(MoishdUser.class);
			q.setFilter("userIdentifier == idParam");

			try {
				if (((List<MoishdUser>) q.execute(user.getEmail())).size() == 0) {
					pm.makePersistent(muser);
				}
			} finally {
				pm.close();
			}

			response.sendRedirect("/");
		}
	}
}