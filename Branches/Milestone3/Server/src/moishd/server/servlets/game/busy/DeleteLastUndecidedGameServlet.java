package moishd.server.servlets.game.busy;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.DSCommon;

public class DeleteLastUndecidedGameServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8064263201719115977L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (request.getHeader("X-AppEngine-QueueName").equals("inviteQueue")) {
			String initId = request.getParameter("initId");
			String recID = request.getParameter("recId");
			
			DSCommon.DeleteLastUnDecidedGame(initId, recID);
		}
	}
}
