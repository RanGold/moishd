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

public class RegisterServlet extends HttpServlet {
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
			Gson g = new Gson();
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				ObjectInputStream ois = new ObjectInputStream(
						request.getInputStream());
				String json = "";
				try {
					json = (String) ois.readObject();
				} catch (ClassNotFoundException e) {
					response.addHeader("Error", "");
					response.getWriter().println(e.getMessage());
					return;
				}
				
				ClientMoishdUser clientUser = (ClientMoishdUser) g.fromJson(json,
						ClientMoishdUser.class);

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
					users.get(0).setRegisterID(clientUser.getRegisterID());
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
