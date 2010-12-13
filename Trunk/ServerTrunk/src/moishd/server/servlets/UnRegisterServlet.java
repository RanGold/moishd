package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UnRegisterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6028249054566845894L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user == null) {
			response.addHeader("Error", "");
			response.getWriter().println("Error: no logged in user");
		} else {
			try {
				MoishdUser muser = DSCommon.GetUserByGoogleId(user.getEmail());
				muser.setRegisterID("NULL");
				muser.setRegistered(false);
				muser.SaveChanges();
			} catch (DataAccessException e) {
				response.addHeader("Error", "");
				response.getWriter().println("UnRegisterServlet: " + e.getMessage());
			}
		}
	}
}
