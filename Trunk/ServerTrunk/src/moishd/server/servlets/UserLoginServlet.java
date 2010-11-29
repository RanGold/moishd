package moishd.server.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.PMF;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

public class UserLoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1306793921674383722L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			try {
				response.getWriter().println("Error: no logged in user");
			} catch (IOException e) {
				response.addHeader("Error", e.getMessage());
			}
		} else {
			Gson g = new Gson();
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				ObjectInputStream ois = new ObjectInputStream(
						request.getInputStream());
				String json = (String) ois.readObject();
				ClientMoishdUser newUser = (ClientMoishdUser) g.fromJson(json,
						ClientMoishdUser.class);

				Query q = pm.newQuery(MoishdUser.class);
				q.setFilter("userIdentifier == idParam");

				if (((List<MoishdUser>) q.execute(user.getEmail())).size() == 0) {
					pm.makePersistent(new MoishdUser(newUser.getUserNick(), "", user.getEmail(), "NULL"));
				}
			} catch (IOException e) {
				response.addHeader("Error", e.getMessage());
			} catch (ClassNotFoundException e) {
				response.addHeader("Error", e.getMessage());
			} finally {
				pm.close();
			}
		}
	}
}