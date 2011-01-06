package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

public class GetMostPopularGameServlet extends GeneralServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 490380246631338919L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			if (mUser.getLocation().isInitialized()) {
				//TODO - get most popular game
				response.addHeader("DareSimonPro", "");
			}
		}
	}

}
