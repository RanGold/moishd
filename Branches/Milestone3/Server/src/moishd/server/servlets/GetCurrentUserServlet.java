package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.GsonCommon;

public class GetCurrentUserServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1007795810024756720L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			GsonCommon.WriteJsonToResponse(mUser.toClientMoishdUser(), response);
		}
	}
}