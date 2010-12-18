package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.dataObjects.Location;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.reflect.TypeToken;

public class UserLoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1306793921674383722L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
		} else {
			try {
				ClientMoishdUser newUser = 
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<ClientMoishdUser>(){}.getType());
				
				if (!DSCommon.DoesUserByGoogleIdExist(user.getEmail())) {	
					MoishdUser muser = new MoishdUser(newUser.getUserNick(), newUser.getPictureLink(), 
							user.getEmail(), "NULL", newUser.getFacebookID(), newUser.getMACAddress());
					muser.setLocation(new Location(newUser.getLocation().getxCoordinate(), 
							newUser.getLocation().getyCoordinate()));
					muser.SaveChanges();
				} else {
					MoishdUser curUser = DSCommon.GetUserByGoogleId(user.getEmail()) ;
					if (!curUser.getFacebookID().equals(newUser.getFacebookID())) {
						response.addHeader("Error", "");
						response.getWriter().println("UserLoginServlet: facebook id given " + 
								"differes from the database version");
						return;
					}
					
					curUser.setMACAddress(newUser.getMACAddress());
					curUser.SaveChanges();
				}
			} catch (DataAccessException e) {
				response.addHeader("Error", "");
				response.getWriter().println("UserLoginServlet: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				response.addHeader("Error", "");
				response.getWriter().println("UserLoginServlet: " + e.getMessage());
			}
		}
	}
}