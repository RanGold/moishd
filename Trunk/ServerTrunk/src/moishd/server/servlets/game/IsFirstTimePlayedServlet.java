package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

public class IsFirstTimePlayedServlet extends GeneralServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2789807610738481493L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
	
		super.doPost(request, response);
		
		if (user != null) {
			if (true) { //TODO - mock up, for now it is ALWAYS the first time played
				response.addHeader("FirstTimePlayed", "");
			}
		}
	}

}
