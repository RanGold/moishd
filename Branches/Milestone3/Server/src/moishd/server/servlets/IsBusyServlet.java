package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IsBusyServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8401273278133734085L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		checkBusy = false;
		super.doPost(request, response);

		if (user != null) {
			if (mUser.isBusy()) {
				response.addHeader("Busy", "");
			}
		}
	}
}
