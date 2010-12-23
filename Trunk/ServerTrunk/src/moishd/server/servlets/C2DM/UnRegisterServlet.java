package moishd.server.servlets.C2DM;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.servlets.GeneralServlet;

public class UnRegisterServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6028249054566845894L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		super.doPost(request, response);

		if (user != null) {
			mUser.InitUser();
		}
	}
}
