package moishd.server.servlets.game;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

public class RankGameServlet extends GeneralServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1562495283256401282L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			String input = request.getReader().readLine();				
			String gameType = input.split(":")[0];
			int rank = Integer.parseInt(input.split(":")[1]);
		}
	}

}
