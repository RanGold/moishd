package moishd.server.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.LoggerCommon;

import com.google.appengine.api.users.UserServiceFactory;

public class CheckAliveServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -373046401736802970L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		if ((request.getHeader("X-AppEngine-Cron").equals("true") &&
				request.getHeader("User-Agent").equals("AppEngine-Google; (+http://code.google.com/appengine)")) ||
				(UserServiceFactory.getUserService().isUserLoggedIn()
				&& UserServiceFactory.getUserService().isUserAdmin())) {
			DSCommon.UnregisterDisconnectedUsers();
			List<String> registerIds = DSCommon.GetCheckAliveRegisterIds();
			HashMap<String, String> payload = new HashMap<String, String>();
			
			for (String registerId : registerIds) {
				try {
					C2DMCommon.PushGenericMessage(registerId, 
							C2DMCommon.Actions.CheckAlive.toString(), payload);
				} catch (ServletException e) {
					LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
				}
			}
			
			DSCommon.SetCheckAliveRegisterIds();
		}
	}
}
