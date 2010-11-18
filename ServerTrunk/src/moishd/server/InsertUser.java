package moishd.server;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class InsertUser extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user != null) {
			MoishdUser muser = new MoishdUser(
					"Ran",
					"http://profile.ak.fbcdn.net/hprofile-ak-snc4/hs475.snc4/49860_791749386_1443_n.jpg",
					new Date(), new MoishdGroup(), new Location(1, 2));

			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(muser);
			} finally {
				pm.close();
			}
		}
		else {
			response.sendRedirect("/LoginServlet");
		}
		response.sendRedirect("/");
	}
}