package moishd.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.dataObjects.MoishdUser;
import moishd.dataObjects.objectToTest;

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
				//List<MoishdUser> detachedUsers = (List<MoishdUser>) pm.detachCopyAll(users);
				
				/*MoishdUser mUser = detachedUsers.get(0);
				detachedUsers.clear();
				mUser.setLocation(pm.detachCopy(mUser.getLocation()));
				mUser.setStats(pm.detachCopy(mUser.getStats()));
				detachedUsers.add(mUser);*/
				
				resp.setContentType("application/json");
				
				Gson g = new Gson();
				String json = g.toJson(users);
				oos.writeObject(json);
			}
			finally {
				oos.flush();				
				oos.close();
				pm.close();
			}
		}
	}
}
