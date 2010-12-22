package moishd.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientLocation;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdUser;

import com.google.gson.reflect.TypeToken;

public class UpdateLocationServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1785615369997381606L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {

		super.doPost(request, response);

		if (user != null) {
			try {
				MoishdUser muser = DSCommon.GetUserByGoogleId(user.getEmail());
				ClientLocation newLocation = 
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<ClientLocation>(){}.getType());
				muser.getLocation().setLatitude(newLocation.getLatitude());
				muser.getLocation().setLongitude(newLocation.getLongitude());
				muser.getLocation().SaveChanges();
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ClassNotFoundException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}