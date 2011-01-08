package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

public class SetSingleUnbusyServlet extends GeneralServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8601073164022922830L;

	/**
	 * 
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		super.doPost(request, response);

		if (user != null) {
			mUser.setBusy(false);
			mUser.SaveChanges();
		}
		
		else{
			response.addHeader("Error", "");
		}
	}
}