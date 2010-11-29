package moishd.serever.servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.jdo.PersistenceManager;
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

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		if (user == null) {
			resp.getWriter().write("Not Logged In");
		}
		else {
			// TODO: check if user exists, else add him
			try {
				@SuppressWarnings("unchecked")
				List<MoishdUser> users = (List<MoishdUser>) pm.newQuery(MoishdUser.class).execute();
				
				resp.setContentType("application/json");
				
				Gson g = new Gson();
				String json = g.toJson(MoishdUser.copyToClientMoishdUserList(users));
				//String json = g.toJson(new objectToTest());
				oos.writeObject(json);
			}
			finally {
				oos.flush();				
				oos.close();
				if (!pm.isClosed()) {
					pm.close();
				}
			}
		}
	}
}
