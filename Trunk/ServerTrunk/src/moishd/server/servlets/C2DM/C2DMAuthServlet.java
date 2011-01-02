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
//		LoggerCommon.Get().LogInfo("adsd", "check1");
//		String headername = ""; 
//		for(@SuppressWarnings("rawtypes")
//		Enumeration e = request.getHeaderNames(); e.hasMoreElements();){
//			headername = (String)e.nextElement();
//			LoggerCommon.Get().LogInfo("adsd", headername + " - " + request.getHeader(headername));
//		}
//		LoggerCommon.Get().LogInfo("adsd", "check2");
		if ((request.getHeader("X-AppEngine-Cron").equals("true") &&
				request.getHeader("User-Agent").equals("AppEngine-Google; (+http://code.google.com/appengine)")) ||
				(UserServiceFactory.getUserService().isUserLoggedIn()
				&& UserServiceFactory.getUserService().isUserAdmin())) {
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
