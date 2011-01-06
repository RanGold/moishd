package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

public class GamePlayedServlet extends GeneralServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -131296519991903500L;
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {

		super.doPost(request, response);
//TODO - add real logics
		if (user != null) {
			String input = request.getReader().readLine();				
			String gameType = input;
			//TODO COUNTER GAME ++
	}
	}
}
