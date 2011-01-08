package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

public class SetBusyServlet extends GeneralServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9100868769523828754L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		super.doPost(request, response);

		if (user != null) {
			mUser.setBusy(true);
			mUser.SaveChanges();
		}
		else{
			response.addHeader("Error", "");
		}
			
	}
}



