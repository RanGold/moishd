package moishd.server;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class InsertUser extends HttpServlet {
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
			MoishdUser muser = new MoishdUser(
					user.getNickname(),
					"http://profile.ak.fbcdn.net/hprofile-ak-snc4/hs475.snc4/49860_791749386_1443_n.jpg",
					new Location(1, 2), user.getEmail());

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(MoishdUser.class);
			q.setFilter("userIdentifier == idParam");

			if (((List<MoishdUser>) q.execute(user.getEmail())).size() == 0) {
				try {
					pm.makePersistent(muser);
				} finally {
					pm.close();
				}
			}
			
			response.sendRedirect("/");
		}
	}
}