package moishd.server.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.PMF;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.reflect.TypeToken;

public class RegisterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
		} else {
			try {
				ClientMoishdUser clientUser = 
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<ClientMoishdUser>(){}.getType());

				MoishdUser muser = DSCommon.GetUserByGoogleId(user.getEmail());
				muser.setRegisterID(clientUser.getRegisterID());
				muser.SaveChanges();
			} catch (ClassNotFoundException e) {
				response.addHeader("Error", "");
				response.getWriter().println("RegisterServlet: " + e.getMessage());
			} catch (DataAccessException e) {
				response.addHeader("Error", "");
				response.getWriter().println("RegisterServlet: " + e.getMessage());
			}
		}
	}
}
