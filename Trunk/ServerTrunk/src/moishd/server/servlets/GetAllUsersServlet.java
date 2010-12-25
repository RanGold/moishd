package moishd.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;

public class GetAllUsersServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7171012395494987373L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		checkRegister = false;
		
		super.doPost(request, response);

		if (user != null) {
			long amount = -1;
			try {
				String strAmount = request.getReader().readLine();
				amount = Long.valueOf(strAmount);
			} catch (NumberFormatException e) {
				// Ignoring
			}
			try {
				List<ClientMoishdUser> allUsers = (amount == -1 ? DSCommon
						.GetAllRegisteredClientUsers(user.getEmail(), true,
								amount) : DSCommon.GetAllRegisteredClientUsers(
						user.getEmail(), true));
				GsonCommon.WriteJsonToResponse(allUsers, response);
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
				user = null;
			}
		}
	}
}
