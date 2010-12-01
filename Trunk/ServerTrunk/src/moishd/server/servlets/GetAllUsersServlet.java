package moishd.server.servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
import com.google.gson.Gson;

public class GetAllUsersServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7171012395494987373L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		PersistenceManager pm = PMF.get().getPersistenceManager();
		ObjectOutputStream oos = null;

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().write("Not Logged In");
		} else {
			try {
				Query q = pm.newQuery(MoishdUser.class);
				q.setFilter("userGoogleIdentifier == :idParam");

				@SuppressWarnings("unchecked")
				List<MoishdUser> users = (List<MoishdUser>)q.execute(user.getEmail());
				if (users.size() == 0) {
					response.addHeader("Error", "");
					response.getWriter().println("RegisterServlet: user " + user.getEmail() + " not found");
				} else if (users.size() > 1) {
					response.addHeader("Error", "");
					response.getWriter().println("RegisterServlet: user " + user.getEmail() + " more than 1 result");
				} else {
					// TODO Returning just registered users 
					@SuppressWarnings("unchecked")
					List<MoishdUser> allUsers = (List<MoishdUser>) pm.newQuery(
							MoishdUser.class, "userGoogleIdentifier != :paramId")
							.execute(user.getEmail());

					response.setContentType("application/json");

					Gson g = new Gson();
					String json = g.toJson(MoishdUser
							.copyToClientMoishdUserList(allUsers));
					oos = new ObjectOutputStream(response.getOutputStream());
					oos.writeObject(json);
				}
			} finally {
				if (oos != null) {
					oos.flush();
					oos.close();
				}

				if (!pm.isClosed()) {
					pm.close();
				}
			}
		}
	}
}
