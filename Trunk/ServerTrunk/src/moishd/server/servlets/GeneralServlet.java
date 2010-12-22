package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.LoggerCommon;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GeneralServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8735324078914481430L;

	protected UserService userService;
	protected User user;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		userService = UserServiceFactory.getUserService();
		user = userService.getCurrentUser();

		if (user == null) {
			LoggerCommon.Get().LogError(this, response, "Not Logged In");
		}
	}
}