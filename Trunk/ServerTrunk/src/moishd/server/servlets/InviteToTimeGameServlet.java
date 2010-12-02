package moishd.server.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.C2DMCommon;
import moishd.server.common.PMF;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.dataObjects.TimeGame;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

public class InviteToTimeGameServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
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
					response.getWriter().println("InviteToTimeGameServlet: user " + user.getEmail() + " not found");
				} else if (users.size() > 1) {
					response.addHeader("Error", "");
					response.getWriter().println("InviteToTimeGameServlet: user " + user.getEmail() + " more than 1 result");
				} else {
					users = (List<MoishdUser>)q.execute(clientUser.getUserGoogleIdentifier());

					if (users.size() == 0) {
						response.addHeader("Error", "");
						response.getWriter().println("InviteToTimeGameServlet: user " + clientUser.getUserGoogleIdentifier() + " not found");
					} else if (users.size() > 1) {
						response.addHeader("Error", "");
						response.getWriter().println("InviteToTimeGameServlet: user " + clientUser.getUserGoogleIdentifier() + " more than 1 result");
					} else {
						TimeGame tg = new TimeGame(user.getEmail(), clientUser.getUserGoogleIdentifier());
						pm.makePersistent(tg);

						response.getWriter().write(String.valueOf(tg.getGameId()));

						HashMap<String, String> payload = new HashMap<String, String>();
						payload.put("GameId", String.valueOf(tg.getGameId()));
						try {
							C2DMCommon.PushGenericMessage(users.get(0).getRegisterID(), 
									C2DMCommon.Actions.GameInvitation.toString(), payload);
						} catch (Exception e) {
							response.addHeader("Error", "");
							response.getWriter().println(e.getMessage());
						}
					}
				}
			} catch (IOException e) {
				response.addHeader("Error", e.getMessage());
			} finally {
				pm.close();
			}
		}
	}
}
