package moishd.server.servlets.C2DM;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.LoggerCommon;

import com.google.appengine.api.users.UserServiceFactory;

public class C2DMAuthServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8795970457297476756L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
//		Checing recieved headers
//		LoggerCommon.Get().LogInfo("C2DMAuthServlet", "headers start");
//		String headername = ""; 
//		for(@SuppressWarnings("rawtypes")
//		Enumeration e = request.getHeaderNames(); e.hasMoreElements();){
//			headername = (String)e.nextElement();
//			LoggerCommon.Get().LogInfo("C2DMAuthServlet", headername + " - " + request.getHeader(headername));
//		}
//		LoggerCommon.Get().LogInfo("C2DMAuthServlet", "headers end");
		if ((UserServiceFactory.getUserService().isUserLoggedIn()
				&& UserServiceFactory.getUserService().isUserAdmin()) ||
			(request.getHeader("X-AppEngine-Cron").equals("true") &&
				request.getHeader("User-Agent").equals("AppEngine-Google; (+http://code.google.com/appengine)"))) {
			try {
				DSCommon.SetC2DMAuth(C2DMCommon.getAuthToken(true));
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, e.getMessage(), e.getStackTrace());
				response.addHeader("Error", "");
				response.getWriter().write("C2DMAuthServlet" + e.getMessage());
			}
		}
	}
}
