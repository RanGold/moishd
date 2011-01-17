package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HasLocationServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8007187587366249474L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		super.doPost(request, response);

		if (user != null) {
			if (mUser.getLocation().isInitialized()) {
				response.addHeader("HasLocation", "");
			}
		}
	}
}
