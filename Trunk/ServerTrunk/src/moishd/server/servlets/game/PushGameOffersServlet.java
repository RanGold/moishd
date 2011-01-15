package moishd.server.servlets.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.LoggerCommon;

import com.google.appengine.api.users.UserServiceFactory;

public class PushGameOffersServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5042195856585763879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		if ((request.getHeader("X-AppEngine-Cron").equals("true") &&
				request.getHeader("User-Agent").equals("AppEngine-Google; (+http://code.google.com/appengine)")) ||
				(UserServiceFactory.getUserService().isUserLoggedIn()
				&& UserServiceFactory.getUserService().isUserAdmin())) {
			Map<String, String> usersSets = DSCommon.GetNearbyUsersSets(1);
			
			for (Map.Entry<String, String> pair : usersSets.entrySet()) {
				HashMap<String, String> payload = new HashMap<String, String>();
				payload.put("GoogleIdOfOpponent", pair.getValue().split("#")[0]);
				payload.put("UserNickNameOfOpponent", pair.getValue().split("#")[1]);
				try {
					LoggerCommon.Get().LogInfo(this, "Register ID: " + pair.getKey() + "\n\r" + 
							"From: " + pair.getValue().split("#")[1]);
					C2DMCommon.PushGenericMessage(pair.getKey(),
							C2DMCommon.Actions.GameOffer.toString(), payload);
				} catch (ServletException e) {
					LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
				}
			}
		}
	}
}
