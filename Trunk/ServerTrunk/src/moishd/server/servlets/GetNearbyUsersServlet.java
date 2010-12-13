package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetNearbyUsersServlet extends FilterServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1688665969608951036L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		fieldName = "MACAddress";
		servletName = "GetNearbyUsersServlet";
		
		super.doPost(request, response);
	
	}
}
