package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;

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
	protected MoishdUser mUser;
	protected boolean doesExist = true;
	protected boolean checkRegister = true;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		userService = UserServiceFactory.getUserService();
		user = userService.getCurrentUser();

		if (user == null) {
			LoggerCommon.Get().LogError(this, response, "Not Logged In");
		} else {
			try {
				if (doesExist) {
					// TODO minimize this, done because multiple users when update location
					mUser = DSCommon.GetUserByGoogleId(user.getEmail());
					if (checkRegister && !mUser.isRegistered()) {
						LoggerCommon.Get().LogError(this, response,
								"Tried to do an action with unregistered user");
						mUser.InitUser();
						mUser.SaveChanges();
						user = null;
						return;
					}
					mUser.setIsAlive(0);
					mUser.SaveChanges();
					mUser = DSCommon.GetUserByGoogleId(user.getEmail());
				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
				user = null;
			}
		}
	}
}