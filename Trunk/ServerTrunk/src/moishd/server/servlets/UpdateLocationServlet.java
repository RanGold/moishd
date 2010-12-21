package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientLocation;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.reflect.TypeToken;

public class UpdateLocationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1785615369997381606L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			LoggerCommon.Get().LogError(this, "Not Logged In");
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
		} else {
			try {
				MoishdUser muser = DSCommon.GetUserByGoogleId(user.getEmail());
				ClientLocation newLocation = 
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<ClientLocation>(){}.getType());
				muser.getLocation().setLatitude(newLocation.getLatitude());
				muser.getLocation().setLongitude(newLocation.getLongitude());
				muser.getLocation().SaveChanges();
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, e.getMessage(), e.getStackTrace());
				response.addHeader("Error", "");
				response.getWriter().println("UserLoginServlet: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				LoggerCommon.Get().LogError(this, e.getMessage(), e.getStackTrace());
				response.addHeader("Error", "");
				response.getWriter().println("UserLoginServlet: " + e.getMessage());
			}
		}
	}
}